package com.NitroReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import com.NitroReader.services.ServiceMethods;
import com.NitroReader.utilities.DBAccess;
import com.NitroReader.utilities.PropertiesReader;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.ChapterModel;
import org.apache.commons.io.FileUtils;

import static java.lang.Integer.*;


@WebServlet("/Chapter")
public class Chapter extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PropertiesReader props = PropertiesReader.getInstance();
        ObjectMapper objM = new ObjectMapper();
        objM.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objM.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        String r;
        PrintWriter out = response.getWriter();
        ChapterModel res = objM.readValue(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())), ChapterModel.class);
        String baseDir = "D:\\Users\\Brandon\\Documentos\\URU\\WEB 2\\WorkSpace\\NitroReader\\NitroReader\\src\\main\\webapp\\library\\"+ res.getMangaid();
        DBAccess dbAccess = DBAccess.getInstance();
        Connection con = dbAccess.createConnection();
        try(PreparedStatement pstm = con.prepareStatement(props.getValue("queryIChapter"))) {
            FileUtils.forceMkdir(new File(baseDir + "\\" + res.getChapternum()));
            pstm.setInt(1, Integer.parseInt(res.getMangaid()));
            pstm.setInt(2, Integer.parseInt(res.getChapternum()));
            pstm.setString(3, "wip");
            pstm.setDate(4, ServiceMethods.getDate());
            pstm.setString(5, res.getMangaid() + "/" + res.getChapternum());
            pstm.setInt(6, 0);
            pstm.executeUpdate();
            System.out.println("se creo la carpeta");
            r = objM.writeValueAsString(res);
            System.out.println(r);
            out.print(r);
        } catch (Error | SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PropertiesReader props = PropertiesReader.getInstance();
        DBAccess dbAccess = DBAccess.getInstance();
        Connection con = dbAccess.createConnection();
        String option = request.getParameter("option");
        String currentChap = request.getParameter("currentChap");
        String mangaid = request.getParameter("mangaid");
        ChapterModel res = new ChapterModel();
        ObjectMapper objM = new ObjectMapper();
        objM.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objM.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        String r;
        PrintWriter out = response.getWriter();
        switch (option) {
            case "delete":
                try(PreparedStatement pstm = con.prepareStatement(props.getValue("queryDChapter"))){
                    FileUtils.deleteDirectory(new File("D:\\Users\\Brandon\\Documentos\\URU\\WEB 2\\WorkSpace\\NitroReader\\NitroReader\\src\\main\\webapp\\library\\"+mangaid+"\\"+currentChap));
                    pstm.setInt(1, Integer.parseInt(mangaid));
                    pstm.setInt(2, Integer.parseInt(currentChap));
                    pstm.executeUpdate();
                    res.setMessage("el capitulo se ha borrado correctamente");
                    r = objM.writeValueAsString(res);
                    System.out.println(r);
                    out.print(r);
                }
                catch (Error | SQLException e){
                    e.printStackTrace();
                }

                break;
            case "getchapter":
                String baseDir = "D:\\Users\\Brandon\\Documentos\\URU\\WEB 2\\WorkSpace\\NitroReader\\NitroReader\\src\\main\\webapp\\library\\"+mangaid+"\\"+currentChap;
                String serveDir = "http://localhost:8080\\NitroReader\\library\\"+mangaid+"\\"+currentChap;
                try{
                    int c = new File(baseDir).listFiles().length;
                    res.setMax(c);
                    res.setFiledir(serveDir);
                    r = objM.writeValueAsString(res);
                    System.out.println(r);
                    out.print(r);
                }catch (Error e){
                    e.printStackTrace();
                }
                break;
            case "getnumchapters":
                String dirManga = "D:\\Users\\Brandon\\Documentos\\URU\\WEB 2\\WorkSpace\\NitroReader\\NitroReader\\src\\main\\webapp\\library\\"+mangaid;
                try{

                        FileFilter directoryFilter = new FileFilter() {
                            public boolean accept(File file) {
                                return file.isDirectory();
                            }
                        };

//

                    File folder = new File(dirManga);
                    File[] listOfFiles = folder.listFiles(directoryFilter);
                    List<Integer> listnames = new ArrayList<>();
                    for (int i= 0; i<listOfFiles.length; i++){
                        if(listOfFiles[i].isDirectory()){
                        listnames.add(parseInt(listOfFiles[i].getName()));
                    }}
                    listnames.sort(Comparator.naturalOrder());
                    HashMap<String , String> item = new HashMap<>();
                    for (int i= 0; i<listnames.size(); i++){
                        item.put("nombre"+(i),(listnames.get(i)).toString());
                    }
                    r = objM.writeValueAsString(item);
                    System.out.println(r);
                    out.print(r);

                }catch (Error e){
                    e.printStackTrace();
                }
                break;


        }

    }
}
