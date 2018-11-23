package com.NitroReader;

import com.NitroReader.services.SubscriptionService;
import com.NitroReader.utilities.DBAccess;
import com.NitroReader.utilities.PropertiesReader;
import com.fasterxml.jackson.annotation.JsonInclude;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.stream.Collectors;

@WebServlet("/Subscription")
public class Subscription extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObjectMapper objM = new ObjectMapper();
        objM.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objM.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        Manga manga = objM.readValue(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())), Manga.class);
        HttpSession session = request.getSession(false);
        manga.setUser_id((int) session.getAttribute("id"));
        Response<HashMap<String, Object>> res = new Response<>();
        SubscriptionService.setSubscription(res, manga);
        String r = objM.writeValueAsString(res);
        System.out.println(r);
        response.getWriter().print(r);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper objM = new ObjectMapper();
        objM.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objM.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        HttpSession session = req.getSession(false);
        Response<HashMap<String,Object>> res = new Response<>();
        Manga manga = objM.readValue(req.getReader().lines().collect(Collectors.joining(System.lineSeparator())), Manga.class);
        manga.setUser_id((int) session.getAttribute("id"));
        SubscriptionService.deleteSubscription(res, manga);
        String r = objM.writeValueAsString(res);
        System.out.println(r);
        resp.getWriter().print(r);
    }
}
