package com.NitroReader.services;

import com.NitroReader.utilities.DBAccess;
import com.NitroReader.utilities.PropertiesReader;
import com.fasterxml.jackson.core.JsonProcessingException;
import models.Manga;
import models.MangaOuter;
import models.Response;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SearchService {

    //METHOD FOR SEARCH MANGAS
    public static void searchManga(String manga_name, PrintWriter out) throws JsonProcessingException {
        PropertiesReader props = PropertiesReader.getInstance();
        DBAccess dbAccess = DBAccess.getInstance();
        Connection con = dbAccess.createConnection();
        Manga data = new Manga();
        ArrayList<MangaOuter> mangas = new ArrayList<>();
        ResultSet rs = null;
        try(PreparedStatement pstm = con.prepareStatement(props.getValue("querySearch"), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            pstm.setString(1, "%" + manga_name + "%");
            rs = pstm.executeQuery();
            if (rs.next()){
                rs.beforeFirst();
                while (rs.next()){
                    MangaOuter info = new MangaOuter();
                    info.setManga_id(rs.getInt(1));
                    info.setManga_name(rs.getString(2));
                    info.setManga_synopsis(rs.getString(3));
                    info.setManga_location(rs.getString(4));
                    mangas.add(info);
                }
                data.setSearchManga(mangas);
                ResBuilderService.BuildOk(data, out);
            }else{
                ResBuilderService.BuildResError(out);
            }
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            ResBuilderService.BuildResError(out);
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

    //METHOD TO FETCH ALL MY MANGAS
    public static void myMangas(PrintWriter out, int user_id) throws JsonProcessingException {
        PropertiesReader props = PropertiesReader.getInstance();
        DBAccess dbAccess = DBAccess.getInstance();
        Connection con = dbAccess.createConnection();
        Manga data = new Manga();
        ArrayList<MangaOuter> mangas = new ArrayList<>();
        ResultSet rs = null;
        try(PreparedStatement pstm = con.prepareStatement(props.getValue("querySearchMM"), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            pstm.setInt(1, user_id);
            rs = pstm.executeQuery();
            if (rs.next()){
                rs.beforeFirst();
                while (rs.next()){
                    MangaOuter info = new MangaOuter();
                    info.setManga_id(rs.getInt(1));
                    info.setManga_name(rs.getString(2));
                    info.setManga_synopsis(rs.getString(3));
                    info.setManga_location(rs.getString(4));
                    mangas.add(info);
                }
                data.setSearchManga(mangas);
                ResBuilderService.BuildOk(data, out);
            }else{
                ResBuilderService.BuildResError(out);
            }
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            ResBuilderService.BuildResError(out);
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

    public static void homeSearch(PrintWriter out) throws JsonProcessingException {
        PropertiesReader props = PropertiesReader.getInstance();
        DBAccess dbAccess = DBAccess.getInstance();
        Connection con = dbAccess.createConnection();
        Manga data = new Manga();
        ArrayList<MangaOuter> mangas = new ArrayList<>();
        ResultSet rs = null;
        try(PreparedStatement pstm = con.prepareStatement(props.getValue("queryHomeS"), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            rs = pstm.executeQuery();
            if (rs.next()){
                rs.beforeFirst();
                while (rs.next()){
                    MangaOuter info = new MangaOuter();
                    info.setManga_id(rs.getInt(1));
                    info.setManga_name(rs.getString(2));
                    info.setManga_location(rs.getString(3));
                    mangas.add(info);
                }
                data.setSearchManga(mangas);
                ResBuilderService.BuildOk(data, out);
            }else{
                ResBuilderService.BuildResError(out);
            }
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            ResBuilderService.BuildResError(out);
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
