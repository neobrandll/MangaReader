package com.NitroReader.services;

import com.NitroReader.utilities.DBAccess;
import com.NitroReader.utilities.PropertiesReader;
import models.Manga;
import models.Response;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class SubscriptionService {
    //METHOD TO SUBSCRIBE MANGA
    public static void setSubscription(Response<HashMap<String, Object>> res, Manga manga){
        PropertiesReader props = PropertiesReader.getInstance();
        DBAccess dbAccess = DBAccess.getInstance();
        Connection con = dbAccess.createConnection();
        HashMap<String, Object> data = new HashMap<>();

        try(PreparedStatement pstm = con.prepareStatement(props.getValue("querySub"))){
            pstm.setInt(1, manga.getUser_id());
            pstm.setInt(2, manga.getManga_id());
            pstm.executeUpdate();
            data.put("subscribe", true);

            ServiceMethods.setResponse(res, 201, "Subscribed sucessful!", data);
        } catch (SQLException | NullPointerException e) {
            System.out.println("Error dealing the subscribe request" + e.getMessage());
            ServiceMethods.setResponse(res, 500, "Error dealing the subscribe request", null);
        }finally {
            dbAccess.closeConnection(con);
        }
    }

    //METHOD TO DELETE SUBSCRIBE
    public static void deleteSubscription(Response<HashMap<String, Object>> res, Manga manga){
        PropertiesReader props = PropertiesReader.getInstance();
        DBAccess dbAccess = DBAccess.getInstance();
        Connection con = dbAccess.createConnection();
        HashMap<String, Object> data = new HashMap<>();

        try(PreparedStatement pstm = con.prepareStatement(props.getValue("queryDSub"))){
            pstm.setInt(1, manga.getUser_id());
            pstm.setInt(2, manga.getManga_id());
            pstm.executeUpdate();
            data.put("subscribe", false);

            ServiceMethods.setResponse(res, 200, "Subscription Deleted!", data);

        } catch (SQLException | NullPointerException e) {
            System.out.println("Error deleting Subscription" + e.getMessage());
            ServiceMethods.setResponse(res, 500, "Errror deleting Subscription", null);
        }finally {
            dbAccess.closeConnection(con);
        }
    }
    static boolean userSubscribe(PreparedStatement pstm, int manga_id, int user_id) throws SQLException {
        ResultSet rs = null;
        boolean isSubscribed = false;
        try {
            pstm.setInt(1, user_id);
            pstm.setInt(2, manga_id);
            rs = pstm.executeQuery();
            if (rs.next()){
                isSubscribed = true;
            }
        }finally {
            if (rs != null){
                rs.close();
            }
        }
        return isSubscribed;
    }

}
