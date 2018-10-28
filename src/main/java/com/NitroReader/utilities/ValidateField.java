package com.NitroReader.utilities;

public class ValidateField {

    private static PropertiesReader props = PropertiesReader.getInstance();
    private static boolean valid;
    public static boolean ValidateF(int regex, String text){
        switch (regex)
        {
            case 1:
                valid = text.matches(props.getValue("regexEmail.regexp"));
                break;
            case 2:
                valid = text.matches(props.getValue("regexPassword.regexp"));
                break;
            case 3:
                valid = text.matches(props.getValue("regexNombre"));
                break;
            case 4:
                valid = text.matches(props.getValue("regexUser"));
                break;
        }
        return valid;
    }

}
