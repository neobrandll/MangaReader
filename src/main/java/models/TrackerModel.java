package models;

public class TrackerModel {
    private Integer manga_id;
    private Integer user_id;
    private Integer chapter_id;
    private Integer page_tracker;
    private Boolean finished;
    private String option;
    private Integer status;
    private String message;

    public Integer getManga_id() {
        return manga_id;
    }

    public void setManga_id(Integer manga_id) {
        this.manga_id = manga_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getChapter_id() {
        return chapter_id;
    }

    public void setChapter_id(Integer chapter_id) {
        this.chapter_id = chapter_id;
    }

    public Integer getPage_tracker() {
        return page_tracker;
    }

    public void setPage_tracker(Integer page_tracker) {
        this.page_tracker = page_tracker;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}








