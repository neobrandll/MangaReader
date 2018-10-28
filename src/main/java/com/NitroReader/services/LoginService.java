package com.NitroReader.services;

import com.NitroReader.utilities.AttrSession;
import com.NitroReader.utilities.DBAccess;
import com.NitroReader.utilities.NitroEncrypted;
import com.NitroReader.utilities.PropertiesReader;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.LoginData;
import models.Response;
import models.UserLogin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.Collectors;

public class LoginService {

    //METHOD FOR THE LOGIN
    public static void startSession (HttpServletRequest request, HttpServletResponse response) throws IOException {
        DBAccess dbAccess = DBAccess.getInstance();
        PreparedStatement pstm;
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        ObjectMapper objM = new ObjectMapper();
        PropertiesReader props = PropertiesReader.getInstance();
        UserLogin userL = objM.readValue(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())), UserLogin.class);
        String encryptedPass = NitroEncrypted.getNitroPassword(userL.getPassword());
        Response<LoginData> res = new Response<>();
        LoginData loginData = new LoginData();
        String r;


        try {
            Connection con = dbAccess.createConnection();
            pstm = con.prepareStatement(props.getValue("queryLogin"), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            pstm.setString(1, userL.getUser());
            pstm.setString(2, encryptedPass);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()){

                if (rs.getInt("type_id") == 1){
                    System.out.println(props.getValue("isAdmin") + ": " + userL.getUser());
                    res.setMessage("Correct Username and Password!");
                    res.setStatus(200);
                    loginData.setAdmin(true);
                    loginData.setName(rs.getString(props.getValue("retName")));
                    res.setData(loginData);
                    AttrSession.setAttribute(session, "name", rs.getString(props.getValue("retName")));
                    AttrSession.setAttribute(session, "id", rs.getInt(props.getValue("retID")));
                }else{
                    System.out.println(props.getValue("isnotAdmin") + ": " + userL.getUser());
                    res.setMessage("Correct Username and Password!");
                    res.setStatus(200);
                    loginData.setAdmin(false);
                    loginData.setName(rs.getString(props.getValue("retName")));
                    res.setData(loginData);
                    AttrSession.setAttribute(session, "name", rs.getString(props.getValue("retName")));
                    AttrSession.setAttribute(session, "id", rs.getInt(props.getValue("retID")));
                }
            } else{
                System.out.println("This username or password entered isn't correct");
                res.setMessage("This username or password entered isn't correct");
                res.setStatus(404);
                loginData.setAdmin(false);
                loginData.setName("");
                res.setData(loginData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            r = objM.writeValueAsString(res);
            System.out.println(r);
            out.print(r);
        }

    }

    //METHOD TO GET THE NAME OF THE CURRENT SESSION
    public static void getSession (HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        ObjectMapper objM = new ObjectMapper();
        HttpSession session = request.getSession();
        Response<LoginData> res = new Response<>();
        LoginData loginData = new LoginData();
        String r;

        if (session.isNew()){
            System.out.println("Session not started!");
            res.setMessage("Session not started!");
            loginData.setAdmin(true);
            res.setStatus(403);
            loginData.setName("");
            res.setData(loginData);
            session.invalidate();

        } else {
            System.out.println("Session started");
            res.setMessage("Session started");
            res.setStatus(200);
            loginData.setAdmin(true);
            loginData.setName((String) session.getAttribute("name"));
            res.setData(loginData);
        }

        r = objM.writeValueAsString(res);
        System.out.println(r);
        out.print(r);
    }
}
