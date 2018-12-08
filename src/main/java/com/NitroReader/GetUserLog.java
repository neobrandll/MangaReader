package com.NitroReader;

import com.NitroReader.services.ServiceMethods;
import com.NitroReader.utilities.PropertiesReader;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Manga;
import models.Response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;

@WebServlet("/GetUserLog")
public class GetUserLog extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObjectMapper objM = new ObjectMapper();
        Response<HashMap<String, Object>> res = new Response<>();
        HashMap<String, Object> data = new HashMap<>();
        PropertiesReader props = PropertiesReader.getInstance();
        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("name") != null && session.getAttribute("id") != null){
            data.put("name", session.getAttribute("name"));
            data.put("id", session.getAttribute("id"));
            ServiceMethods.setResponse(res, 200, props.getValue("userLogged"), data);

            System.out.println(props.getValue("userLogged"));
        }else{
            ServiceMethods.setResponse(res, 404, props.getValue("notLogged"), null);
            System.out.println(props.getValue("notLogged"));
        }
    }
}
