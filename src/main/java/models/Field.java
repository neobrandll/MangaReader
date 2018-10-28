package models;

public class Field {
    private boolean validEmail;
    private boolean validPassword;
    private boolean validUsername;
    private boolean validName;

    public boolean isValidEmail() {
        return validEmail;
    }

    public void setValidEmail(boolean validEmail) {
        this.validEmail = validEmail;
    }

    public boolean isValidPassword() {
        return validPassword;
    }

    public void setValidPassword(boolean validPassword) {
        this.validPassword = validPassword;
    }

    public boolean isValidUsername() {
        return validUsername;
    }

    public void setValidUsername(boolean validUsername) {
        this.validUsername = validUsername;
    }

    public boolean isValidName() {
        return validName;
    }

    public void setValidName(boolean validName) {
        this.validName = validName;
    }
}
