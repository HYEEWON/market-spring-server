package com.market.baechoo.common;

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
}
