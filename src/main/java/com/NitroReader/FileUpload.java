package com.NitroReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;

@MultipartConfig
@WebServlet("/FileUpload")
public class FileUpload extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Part file = request.getPart("file");
        InputStream filecontent = file.getInputStream();
        OutputStream os = null;
        try {
            String baseDir = "c:/test";
            int c = new File(baseDir).listFiles().length;
            c++;
            os = new FileOutputStream(baseDir + "/" + c+".png");
            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = filecontent.read(bytes)) != -1) {
                os.write(bytes, 0, read);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (filecontent != null) {
                filecontent.close();
            }
            if (os != null) {
                os.close();
            }
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
