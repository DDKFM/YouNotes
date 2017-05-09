package de.mschaedlich.servlets;

import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by MAXIMILIAN on 09.05.2017.
 */
/*
* Mapping-Path for the Servlet
* */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getParameter("username") != null && req.getParameter("password") != null) {
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            password = DigestUtils.sha256Hex(password);
            String root = DigestUtils.sha256Hex("root");
            if(password.equalsIgnoreCase(root)) {
                req.getSession().setAttribute("username", username);
                resp.sendRedirect("index.jsp");
            } else {
                resp.sendRedirect("login.jsp");
            }
        } else {
            resp.sendRedirect("login.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
    public static boolean isLoggedIn(HttpServletRequest request) {
        return request.getSession().getAttribute("username") != null;
    }
}
