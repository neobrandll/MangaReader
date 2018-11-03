package com.NitroReader;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.util.Collection;

@MultipartConfig
@WebServlet("/MultFilesUp")
public class MultFilesUp extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Collection<Part> files = request.getParts();
        InputStream filecontent = null;
        OutputStream os = null;
        PrintWriter out = response.getWriter();
        try {
            String baseDir = "D:\\Users\\Brandon\\Documentos\\URU\\WEB 2\\WorkSpace\\NitroReader\\NitroReader\\src\\main\\webapp\\test";
            FileUtils.cleanDirectory(new File(baseDir));
            int c = new File(baseDir).listFiles().length;
            c++;
            for (Part file : files) {
                filecontent = file.getInputStream();
                os = new FileOutputStream(baseDir + "/" + c+".png");
                c++;
                int read = 0;
                byte[] bytes = new byte[1024];
                while ((read = filecontent.read(bytes)) != -1) {
                    os.write(bytes, 0, read);
                }
                if (filecontent != null) {
                    filecontent.close();
                }
                if (os != null) {
                    os.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        PrintWriter out = response.getWriter();
        JSONObject item = new JSONObject();
        try{
            String baseDir = "D:\\Users\\Brandon\\Documentos\\URU\\WEB 2\\WorkSpace\\NitroReader\\NitroReader\\src\\main\\webapp\\test";
        int numoffiles = new File(baseDir).listFiles().length;
            System.out.println( numoffiles);
        for(int i = 1; i<=numoffiles ; i++){
            item.put("direccion"+(i),"http://localhost:8080/NitroReader/Test"+"/"+i+".png");
        }
       }
       catch(Exception e){
           e.printStackTrace();
       }finally {
            System.out.println(item);
            out.print(item);
        }

    }
}
