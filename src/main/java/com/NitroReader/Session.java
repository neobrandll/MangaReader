package com.NitroReader;


import com.NitroReader.services.LoginService;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.LoginData;
import models.Response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet("/Session")
public class Session extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LoginService.startSession(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        ObjectMapper objM = new ObjectMapper();
        HttpSession session = request.getSession();
        Response<LoginData> res = new Response<>();
        String r;

        if(session.isNew()){
            System.out.println("session not started");
            res.setMessage("session not started");
            res.setStatus(304);
            res.setData(null);
            session.invalidate();
        }else{
            res.setStatus(200);
            res.setMessage("session finished");
            res.setData(null);
            session.invalidate();
        }
        r = objM.writeValueAsString(res);
        System.out.println(r);
        out.print(r);
    }

}
