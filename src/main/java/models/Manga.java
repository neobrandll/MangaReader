package models;

import java.util.ArrayList;

public class Manga {
    private int user_id;
    private int manga_id;
    private String manga_name;
    private String manga_synopsis;
    private boolean manga_status;
    private String[] genre_id;
    private ArrayList<String> genres;
    private String location;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getManga_name() {
        return manga_name;
    }

    public void setManga_name(String manga_name) {
        this.manga_name = manga_name;
    }

    public String getManga_synopsis() {
        return manga_synopsis;
    }

    public void setManga_synopsis(String manga_synopsis) {
        this.manga_synopsis = manga_synopsis;
    }

    public boolean isManga_status() {
        return manga_status;
    }

    public void setManga_status(boolean manga_status) {
        this.manga_status = manga_status;
    }

    public String[] getGenre_id() {
        return genre_id;
    }

    public void setGenre_id(String[] genre_id) {
        this.genre_id = genre_id;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getManga_id() {
        return manga_id;
    }

    public void setManga_id(int manga_id) {
        this.manga_id = manga_id;
    }
}
