package ua.util;

import javax.servlet.http.HttpServletRequest;

public class RequestUtil {

    public static boolean isAjax(HttpServletRequest request){
        if (request == null) return false;
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }
}
