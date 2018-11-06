package com.NitroReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.ChapterModel;
import org.apache.commons.io.FileUtils;



@WebServlet("/Chapter")
public class Chapter extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String baseDir = "D:\\Users\\Brandon\\Documentos\\URU\\WEB 2\\WorkSpace\\NitroReader\\NitroReader\\src\\main\\webapp\\probarCAPITULOS";
        ObjectMapper objM = new ObjectMapper();
        objM.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objM.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        String r;
        PrintWriter out = response.getWriter();
        ChapterModel res = objM.readValue(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())), ChapterModel.class);

        try {
            int c = (new File(baseDir).listFiles().length) + 1;
            FileUtils.forceMkdir(new File(baseDir + "\\" + res.getMessage()));

            r = objM.writeValueAsString(res);
            System.out.println(r);
            out.print(r);
        } catch (Error e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String option = request.getParameter("option");
        String currentChap = request.getParameter("currentChap");
        ChapterModel res = new ChapterModel();
        ObjectMapper objM = new ObjectMapper();
        objM.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objM.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        String r;
        PrintWriter out = response.getWriter();
        switch (option) {
            case "delete":
                try{
                    FileUtils.deleteDirectory(new File("D:\\Users\\Brandon\\Documentos\\URU\\WEB 2\\WorkSpace\\NitroReader\\NitroReader\\src\\main\\webapp\\probarCAPITULOS\\"+currentChap));
                    res.setMessage("el capitulo se ha borrado correctamente");
                    r = objM.writeValueAsString(res);
                    System.out.println(r);
                    out.print(r);
                    }
                    catch (Error e){
                    e.printStackTrace();
                    }

                break;
            case "getchapter":
                String baseDir = "D:\\Users\\Brandon\\Documentos\\URU\\WEB 2\\WorkSpace\\NitroReader\\NitroReader\\src\\main\\webapp\\probarCAPITULOS\\"+currentChap;
                String serveDir = "http://localhost:8080\\NitroReader\\probarCAPITULOS\\" + currentChap;
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
                String dirManga = "D:\\Users\\Brandon\\Documentos\\URU\\WEB 2\\WorkSpace\\NitroReader\\NitroReader\\src\\main\\webapp\\probarCAPITULOS";
                try{
                    File folder = new File(dirManga);
                    File[] listOfFiles = folder.listFiles();
                    List<Integer> listnames = new ArrayList<>();
                    for (int i= 0; i<listOfFiles.length; i++){
                        listnames.add(Integer.parseInt(listOfFiles[i].getName()));
                    }
                    listnames.sort(Comparator.naturalOrder());
                    System.out.println(listnames);
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
