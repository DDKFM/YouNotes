package de.mschaedlich.servlets;

import de.mschaedlich.domain.Notice;
import de.mschaedlich.util.NoticeAdministration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by MAXIMILIAN on 13.05.2017.
 */
@WebServlet("/notice")
public class NoticeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/json");
        if(request.getSession().getAttribute("username") != null) {
            String username = request.getSession().getAttribute("username").toString();
            if (request.getParameter("requested") != null) {
                String requested = request.getParameter("requested");
                if (requested != null && requested.equalsIgnoreCase("ALL")) {
                    String jsonResult = "{\"data\": [";
                    List<Notice> noticeList  = NoticeAdministration.getNoticeByUsername(username);
                    for (Notice notice : noticeList) {
                        String noticeObject = "{";
                        noticeObject += "\"id\": " + notice.getNoticeId() + ", ";
                        noticeObject += "\"title\": \"" + notice.getTitle() + "\", ";
                        noticeObject += "\"content\": \"" + notice.getContent() + "\", ";
                        noticeObject += "}";
                        if(noticeList.indexOf(notice) < noticeList.size() - 1) {
                            jsonResult += noticeObject + ", ";
                        } else {
                            jsonResult += noticeObject;
                        }
                    }
                    jsonResult += "]}";
                }
            }
        } else {
            response.sendRedirect("login.jsp");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
