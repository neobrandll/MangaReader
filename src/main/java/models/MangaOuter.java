package models;

public class MangaOuter {
    private int manga_id;
    private String manga_name;
    private String manga_synopsis;
    private String manga_location;

    public String getManga_synopsis() {
        return manga_synopsis;
    }

    public void setManga_synopsis(String manga_synopsis) {
        this.manga_synopsis = manga_synopsis;
    }

    public String getManga_location() {
        return manga_location;
    }

    public void setManga_location(String manga_location) {
        this.manga_location = manga_location;
    }

    public int getManga_id() {
        return manga_id;
    }

    public void setManga_id(int manga_id) {
        this.manga_id = manga_id;
    }

    public String getManga_name() {
        return manga_name;
    }

    public void setManga_name(String manga_name) {
        this.manga_name = manga_name;
    }
}
