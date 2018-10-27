package com.NitroReader.utilities;

import javax.servlet.http.HttpSession;

public class AttrSession {

    private AttrSession() {
    }

    public static void setAttribute(HttpSession session, String key, Object value) {
        session.setAttribute(key, value);
    }
}
