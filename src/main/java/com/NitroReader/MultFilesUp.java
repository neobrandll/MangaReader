package com.NitroReader;

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
        try {
            String baseDir = "c:/test";
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

    }
}
