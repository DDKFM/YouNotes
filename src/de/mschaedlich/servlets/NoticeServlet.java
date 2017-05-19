package de.mschaedlich.servlets;

import de.mschaedlich.domain.Notice;
import de.mschaedlich.util.NoticeAdministration;
import org.hibernate.Session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
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
                if(requested != null) {
                    if (requested != null && requested.equalsIgnoreCase("ALL")) {
                        String jsonResult = "{\"data\": [";
                        List<Notice> noticeList = NoticeAdministration.getNoticeByUsername(username);
                        for (Notice notice : noticeList) {
                            String noticeObject = "{";
                            noticeObject += "\"id\": " + notice.getNoticeId() + ", ";
                            noticeObject += "\"title\": \"" + notice.getTitle() + "\", ";
                            String content = notice.getContent().replace("\n", "<br/>").replace("\r", "<br/>");
                            noticeObject += "\"content\": \"" + content + "\", ";
                            noticeObject += "\"color\": \"" + notice.getColor() + "\"";
                            noticeObject += "}";
                            if (noticeList.indexOf(notice) < noticeList.size() - 1) {
                                jsonResult += noticeObject + ", ";
                            } else {
                                jsonResult += noticeObject;
                            }
                        }
                        jsonResult += "]}";
                        response.setStatus(200);
                        response.getWriter().append(jsonResult);
                    } else if (requested.equalsIgnoreCase("remove")) {
                        if(request.getParameter("noticeID") != null) {
                            String noticeId = request.getParameter("noticeID");
                            int noticeInteger = -1;
                            try {
                                noticeInteger = Integer.parseInt(noticeId);
                            } catch (NumberFormatException e) {
                                noticeInteger = -1;
                            }
                            if(noticeInteger != -1) {
                                NoticeAdministration.removeNotice(noticeInteger);
                                response.getWriter().append("{\"status\": \"OK\"");
                            }
                        }
                    } else if(requested.equalsIgnoreCase("add")) {
                        String title = new String(request.getParameter("title").getBytes(), Charset.forName("UTF-8"));
                        String content = new String(request.getParameter("content").getBytes(), Charset.forName("UTF-8"));
                        if(title != null && content != null) {
                            NoticeAdministration.addNote(username, title, content);
                            response.sendRedirect("dash.jsp");
                        }
                    }
                }
            }
        } else {
            response.sendRedirect("login.jsp");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
