package com.NitroReader;

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

@WebServlet("/Register")
public class Register extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObjectMapper objMapper = new ObjectMapper();
//        Connection con= null;
//        PreparedStatement pstm = null;
//        ResultSet rs = null;
//        Date date = (Date) Calendar.getInstance().getTime();
//        java.sql.Timestamp currentTime= new java.sql.Timestamp(date.getTime());
    try{
       // Class.forName("org.postgresql.Driver");
        //con = DriverManager.getConnection("postgresql://localhost:5432/NitroReader","postgres","masterkey");
        // pstm = con.prepareStatement("INSERT into users(type_id, user_password, user_username, user_name, user_email, user_creation_time) VALUES(?,?,?,?,?,?);");
        Response<User> res = new Response<>();
        res.setMessage("El registro fue realizado");
        res.setStatus(200);
        User user = objMapper.readValue(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())), User.class);
        res.setData(user);
        //pstm.setInt(1,0);
        //pstm.setString(2, user.getPassword());
       // pstm.setString(3, user.getUser());
//        pstm.setString(4, user.getName());
//        pstm.setString(5, user.getMail());
//        pstm.setTimestamp(6, currentTime);
        String r = objMapper.writeValueAsString(res);
        System.out.println(r);
        response.getWriter().print(r);

    }catch(IOException e){
    e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        out.print("hola mundo");
    }
}
