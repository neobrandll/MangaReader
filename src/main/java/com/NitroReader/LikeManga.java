package com.NitroReader;

import com.NitroReader.Command.*;
import com.NitroReader.services.LikeMangaService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import models.Manga;
import models.Response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.stream.Collectors;

@WebServlet("/LikeManga")
public class LikeManga extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObjectMapper objM = new ObjectMapper();
        HttpSession session = request.getSession(false);
        objM.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objM.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        objM.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);

        PrintWriter out = response.getWriter();

        Manga manga = objM.readValue(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())), Manga.class);
        int userid = (int) session.getAttribute("id");
        int mangaid = manga.getManga_id();
        String switchState = manga.getSwitchState();

        Like like = new Like();
        Command flipUplike = new FlipUpLike(like);
        Command flipDownlike = new FlipDownLike(like);
        SwitchLike mySwitch = new SwitchLike(flipUplike, flipDownlike);
        switch (switchState) {
            case "ON":
                mySwitch.flipUp(userid, mangaid, out);
                break;
            case "OFF":
                mySwitch.FlipDown(userid, mangaid, out);
                break;

        }


    }
}
