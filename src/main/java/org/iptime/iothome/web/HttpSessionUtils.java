package org.iptime.iothome.web;

import javax.servlet.http.HttpSession;

import org.iptime.iothome.domain.User;

public class HttpSessionUtils {
    public static final String USER_SESSION_KEY = "sessionUser";
    
    public static boolean isLoginUser(HttpSession session) {
        Object sessionUser = session.getAttribute(USER_SESSION_KEY);
        
        if(sessionUser == null)
            return false;
        else
            return true;
    }
    
    public static User getUserFromSession(HttpSession session) {
        if(!isLoginUser(session))
            return null;
        else
            return (User)session.getAttribute(USER_SESSION_KEY);
    }
}
