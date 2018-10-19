package models;

public class ResponseRegister {
    private String message;
    private Integer status;
    private Boolean validEmail;
    private Boolean validPassword;
    private Boolean validName;
    private Boolean validUser;


    public Boolean getValidEmail() {
        return validEmail;
    }

    public void setValidEmail(Boolean validEmail) {
        this.validEmail = validEmail;
    }

    public Boolean getValidPassword() {
        return validPassword;
    }

    public void setValidPassword(Boolean validPassword) {
        this.validPassword = validPassword;
    }

    public Boolean getValidName() {
        return validName;
    }

    public void setValidName(Boolean validName) {
        this.validName = validName;
    }

    public Boolean getValidUser() {
        return validUser;
    }

    public void setValidUser(Boolean validUser) {
        this.validUser = validUser;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
