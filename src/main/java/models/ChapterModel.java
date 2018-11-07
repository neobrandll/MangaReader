package models;

public class ChapterModel {
    private String message;
    private int max;
    private String filedir;
    private String chapternum;
    private String  mangaid;    

    public String getChapternum() {
        return chapternum;
    }

    public void setChapternum(String chapternum) {
        this.chapternum = chapternum;
    }


    public String getMangaid() {
        return mangaid;
    }

    public void setMangaid(String mangaid) {
        this.mangaid = mangaid;
    }



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public String getFiledir() {
        return filedir;
    }

    public void setFiledir(String filedir) {
        this.filedir = filedir;
    }


}
