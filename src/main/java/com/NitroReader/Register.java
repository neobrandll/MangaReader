package com.NitroReader;

import java.sql.DriverManager;
import com.NitroReader.utilities.DBAccess;
import com.NitroReader.utilities.NitroEncrypted;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Response;
import models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Calendar;
import java.util.stream.Collectors;
import com.NitroReader.utilities.PropertiesReader;

@WebServlet("/Register")
public class Register extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        registerUser(request, response);
    }


    //METHOD FOR USER REGISTER
    public void registerUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PropertiesReader props = PropertiesReader.getInstance();
        ObjectMapper objM = new ObjectMapper();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        java.util.Date date = new java.util.Date();
        java.sql.Date finaldate = new java.sql.Date(date.getTime());
        Boolean registrado = null;
        String r = "";
        PrintWriter out = response.getWriter();
        Response<User> res = new Response<>();
        User user = objM.readValue(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())), User.class);
        String nitroP = NitroEncrypted.getNitroPassword(user.getPassword());

        try {
            Class.forName(props.getValue("dbDriver"));
            con = DriverManager.getConnection(props.getValue("dbURL"), props.getValue("dbUser"), props.getValue("dbPassword"));
            pstm = con.prepareStatement(props.getValue("queryLogin"), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            pstm.setString(1, user.getUser());
            rs = pstm.executeQuery();
//            System.out.println(rs);
            if (rs.next()){ // Se verifica si ya el usuario se encuentra registrado si no, lo registra
                registrado = false;
                System.out.println(props.getValue("datedRegister"));
            } else {
                registrado = true;
                System.out.println(props.getValue("newRegister"));
                pstm = con.prepareStatement(props.getValue("queryRegister"), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                pstm.setInt(1,0);
                pstm.setString(2, nitroP);
                pstm.setString(3, user.getUser());
                pstm.setString(4, user.getName());
                pstm.setString(5, user.getMail());
                pstm.setDate(6, finaldate );
                pstm.executeQuery();
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {

            try {
                pstm.close();
                con.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if (registrado) {
                res.setData(user);
                res.setMessage(props.getValue("newRegister"));
                res.setStatus(200);
            } else {
                res.setData(user);
                res.setMessage(props.getValue("datedRegister"));
                res.setStatus(404);
            }

        }

            r = objM.writeValueAsString(res);
            System.out.println(r);
            out.print(r);

    }
//    public boolean queryRegister (PreparedStatement pstm, User user, Connection )
}
