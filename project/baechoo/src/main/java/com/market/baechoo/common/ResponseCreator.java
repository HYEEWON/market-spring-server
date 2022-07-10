package com.market.baechoo.common;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ResponseCreator {

    public void createAlert(HttpServletResponse response, String message) throws IOException {
        response.setContentType("text/html; charset=euc-kr");
        PrintWriter out = response.getWriter();
        out.println("<script>alert('" + message + "'); </script>");
        out.flush();
    }

    public void createAlert(HttpServletResponse response, String message, String url) throws IOException {
        response.setContentType("text/html; charset=euc-kr");
        PrintWriter out = response.getWriter();
        out.println("<script>alert('" + message + "'); location.href='" + url + "';</script>");
        out.flush();
    }

    public void addCookie(HttpServletResponse response, Cookie cookie) {
        cookie.setMaxAge(1 * 24 * 60 * 60); // 1일
        cookie.setSecure(true);
        cookie.setHttpOnly(true);

        response.addCookie(cookie);
    }
}
