package com.NitroReader.services;

import com.NitroReader.utilities.DBAccess;
import com.NitroReader.utilities.NitroEncrypted;
import com.NitroReader.utilities.PropertiesReader;
import com.NitroReader.utilities.ValidateField;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Field;
import models.Response;
import models.UserRegister;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.Collectors;

public class RegisterService {

    //METHOD FOR USER REGISTER
    private static void registerUser(HttpServletRequest request, HttpServletResponse response, UserRegister userR, ObjectMapper objM) throws IOException {
        PropertiesReader props = PropertiesReader.getInstance();
        DBAccess dbAccess = DBAccess.getInstance();
        PreparedStatement pstm;
        ResultSet rs;
        java.util.Date date = new java.util.Date();
        java.sql.Date finaldate = new java.sql.Date(date.getTime());
        String r = "";
        PrintWriter out = response.getWriter();
        Response<Field> res = new Response();
        Field fields = new Field();
        String encryptedPass = NitroEncrypted.getNitroPassword(userR.getPassword());

        try {
            fields.setValidEmail(true);
            fields.setValidName(true);
            fields.setValidPassword(true);
            fields.setValidUsername(true);
            Connection con = dbAccess.createConnection();
            pstm = con.prepareStatement(props.getValue("qryCheckUser"), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            pstm.setString(1, userR.getUser());
            rs = pstm.executeQuery();

            if (rs.next()){ // Se verifica si ya el usuario se encuentra registrado si no, lo registra
                System.out.println(props.getValue("datedRegister"));
                res.setMessage(props.getValue("datedRegister"));
                res.setStatus(404);
                res.setData(fields);
                r = objM.writeValueAsString(res);
            } else {
                System.out.println(props.getValue("newRegister"));
                pstm = con.prepareStatement(props.getValue("queryRegister"), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                pstm.setInt(1,0);
                pstm.setString(2, encryptedPass);
                pstm.setString(3, userR.getUser());
                pstm.setString(4, userR.getName());
                pstm.setString(5, userR.getMail());
                pstm.setDate(6, finaldate);
                pstm.executeUpdate();
                res.setMessage(props.getValue("newRegister"));
                res.setStatus(200);
                res.setData(fields);
                r = objM.writeValueAsString(res);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            System.out.println(r);
            out.print(r);
        }



    }


    //METHOD TO VALIDATE THE FIELDS OF THE REGISTER
    public static void validateTheFields(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ObjectMapper objM = new ObjectMapper();
        UserRegister userR = objM.readValue(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())), UserRegister.class);
        boolean validEmail = ValidateField.ValidateF(1, userR.getMail());
        boolean validPassword = ValidateField.ValidateF(2, userR.getPassword());
        boolean validName = ValidateField.ValidateF(3, userR.getName());
        boolean validUsername = ValidateField.ValidateF(4, userR.getUser());

        if (validEmail && validPassword && validName && validUsername){
            registerUser(request, response, userR, objM);
        } else {
            Field fields = new Field();
            PrintWriter out = response.getWriter();
            Response res = new Response();
            res.setMessage("Some of the fields are not filled correctly");
            res.setStatus(404);
            fields.setValidEmail(validEmail);
            fields.setValidPassword(validPassword);
            fields.setValidName(validName);
            fields.setValidUsername(validUsername);
            out.print(objM.writeValueAsString(res));

        }
    }
}
