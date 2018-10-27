package com.NitroReader.utilities;

public class ValidateField {

    private static PropertiesReader props = PropertiesReader.getInstance();
    private static boolean valid;
    public static boolean ValidateF(int regex, String text){
        if (regex == 1){
            valid = text.matches(props.getValue("regexEmail.regexp"));
        } else if (regex == 2){
            valid = text.matches(props.getValue("regexPassword.regexp"));
        } else if (regex == 3){
            valid = text.matches(props.getValue("regexNombre"));
        } else if (regex == 4){
            valid = text.matches(props.getValue("regexUser"));
        }
        return valid;
    }

}
