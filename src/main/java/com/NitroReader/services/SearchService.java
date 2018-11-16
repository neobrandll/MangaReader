package com.NitroReader.services;

import com.NitroReader.utilities.DBAccess;
import com.NitroReader.utilities.PropertiesReader;
import models.Manga;
import models.MangaOuter;
import models.Response;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SearchService {

    //METHOD FOR SEARCH MANGAS
    public static void searchManga(Manga manga, Response<Manga> res){
        PropertiesReader props = PropertiesReader.getInstance();
        DBAccess dbAccess = DBAccess.getInstance();
        Connection con = dbAccess.createConnection();
        Manga data = new Manga();
        ArrayList<MangaOuter> mangas = new ArrayList<>();
        ResultSet rs = null;
        try(PreparedStatement pstm = con.prepareStatement(props.getValue("querySearch"), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            pstm.setString(1, "%" + manga.getManga_name() + "%");
            rs = pstm.executeQuery();
            if (rs.next()){
                rs.beforeFirst();
                while (rs.next()){
                    MangaOuter info = new MangaOuter();
                    info.setManga_id(rs.getInt(1));
                    info.setManga_name(rs.getString(2));
                    mangas.add(info);
                }
                data.setSearchManga(mangas);
                ServiceMethods.setResponse(res, 200, "OK", data);
            }else{
                ServiceMethods.setResponse(res, 404, props.getValue("mangaNotFound"), null);
            }
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            ServiceMethods.setResponse(res, 404, props.getValue("mangaNotFound"), null);
        } finally {
            if (con != null){
                dbAccess.closeConnection(con);
            }
            if (rs != null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
