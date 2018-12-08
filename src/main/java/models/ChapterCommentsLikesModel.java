package models;

import java.util.List;

public class ChapterCommentsLikesModel {
    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    private String user_name;
    private String comment;
    private int user_id;
    private int manga_id;
    private int LikesChapter;
    private int chapter_id;
    private String newComment;
    private List<CommentsManga> comments;
    private boolean logged;
    private String switchState;
    private int comment_id;
    private boolean owned;

    public boolean isOwned() {
        return owned;
    }

    public void setOwned(boolean owned) {
        this.owned = owned;
    }

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public String getSwitchState() {
        return switchState;
    }

    public void setSwitchState(String switchState) {
        this.switchState = switchState;
    }



    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    public int getLikesChapter() {
        return LikesChapter;
    }

    public void setLikesChapter(int likesChapter) {
        LikesChapter = likesChapter;
    }

    public boolean isLike() {
        return Like;
    }

    public void setLike(boolean like) {
        Like = like;
    }

    private boolean Like;

    public int getChapter_id() {
        return chapter_id;
    }

    public void setChapter_id(int chapter_id) {
        this.chapter_id = chapter_id;
    }


    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }


    public int getManga_id() {
        return manga_id;
    }

    public void setManga_id(int manga_id) {
        this.manga_id = manga_id;
    }

    public String getNewComment() {
        return newComment;
    }

    public void setNewComment(String newComment) {
        this.newComment = newComment;
    }

    public List<CommentsManga> getComments() {
        return comments;
    }

    public void setComments(List<CommentsManga> comments) {
        this.comments = comments;
    }


    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}

