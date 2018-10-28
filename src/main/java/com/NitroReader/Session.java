package com.NitroReader;


import com.NitroReader.utilities.AttrSession;
import com.NitroReader.utilities.DBAccess;
import com.NitroReader.utilities.NitroEncrypted;
import com.NitroReader.utilities.PropertiesReader;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.ResponseLogin;
import models.UserLogin;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.stream.Collectors;

@WebServlet("/Session")
public class Session extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        startSession(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        ObjectMapper objM = new ObjectMapper();
        HttpSession session = request.getSession();
        ResponseLogin res = new ResponseLogin();
        String r;

        if(session.isNew()){
            System.out.println("session not started");
            res.setMessage("session not started");
            res.setStatus(304);
            session.invalidate();
        }else{
            res.setStatus(200);
            res.setMessage("session finished");
            session.invalidate();
        }
        r = objM.writeValueAsString(res);
        System.out.println(r);
        out.print(r);
    }

    //METHOD FOR THE LOGIN
    private void startSession (HttpServletRequest request, HttpServletResponse response) throws IOException {
        Connection con;
        PreparedStatement pstm;
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        ObjectMapper objM = new ObjectMapper();
        PropertiesReader props = PropertiesReader.getInstance();
        UserLogin userL = objM.readValue(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())), UserLogin.class);
        String encryptedPass = NitroEncrypted.getNitroPassword(userL.getPassword());
        ResponseLogin res = new ResponseLogin();
        String r;


        try {
            con = DBAccess.getConnection();
            pstm = con.prepareStatement(props.getValue("queryLogin"), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            pstm.setString(1, userL.getUser());
            pstm.setString(2, encryptedPass);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()){

                    if (rs.getInt("type_id") == 1){
                        System.out.println(props.getValue("isAdmin") + ": " + userL.getUser());
                        res.setMessage("Correct Username and Password!");
                        res.setStatus(200);
                        res.setAdmin(true);
                        res.setName(rs.getString(props.getValue("retName")));
                        AttrSession.setAttribute(session, "name", rs.getString(props.getValue("retName")));
                        AttrSession.setAttribute(session, "id", rs.getInt(props.getValue("retID")));
                    }else{
                        System.out.println(props.getValue("isnotAdmin") + ": " + userL.getUser());
                        res.setMessage("Correct Username and Password!");
                        res.setStatus(200);
                        res.setAdmin(false);
                        res.setName(rs.getString(props.getValue("retName")));
                        AttrSession.setAttribute(session, "name", rs.getString(props.getValue("retName")));
                        AttrSession.setAttribute(session, "id", rs.getInt(props.getValue("retID")));
                    }
                } else{
                System.out.println("This username or password entered isn't correct");
                res.setMessage("This username or password entered isn't correct");
                res.setStatus(404);
                res.setAdmin(false);
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
    //NO EN USO!!
    private void getSession (HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        ObjectMapper objM = new ObjectMapper();
        HttpSession session = request.getSession();
        ResponseLogin res = new ResponseLogin();
        String r;

        if (session.isNew()){
            System.out.println("Session not started!");
            res.setMessage("Session not started!");
            res.setAdmin(true);
            res.setStatus(403);
            res.setName("");
            session.invalidate();

        } else {
            System.out.println("Session started");
            res.setMessage("Session started");
            res.setStatus(200);
            res.setAdmin(true);
            res.setName((String) session.getAttribute("name"));
        }

        r = objM.writeValueAsString(res);
        System.out.println(r);
        out.print(r);
    }
}
