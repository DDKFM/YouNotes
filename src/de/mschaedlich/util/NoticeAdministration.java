package de.mschaedlich.util;

import de.mschaedlich.domain.Notice;
import de.mschaedlich.domain.Registration;
import de.mschaedlich.domain.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by maxsc on 11.05.2017.
 */
public class NoticeAdministration {
    private static SessionFactory factory = null;

    public static List<Notice> getNoticeByUsername(String username) {
        Session session = factory.openSession();
        if(session != null) {
            try {
                List<Notice> result = session.createQuery("FROM Notice WHERE author.username = '" + username + "'").getResultList();
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                session.close();
            }
        }
        return new ArrayList<Notice>();
    }
    public static void removeNotice(int noticeId) {
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        if(session != null) {
            try {
                List<Notice> result = session.createQuery("FROM Notice WHERE noticeId = '" + noticeId + "'").getResultList();
                if(result != null && !result.isEmpty()) {
                    Notice removableNotice = result.get(0);
                    session.delete(removableNotice);
                    transaction.commit();
                }
            } catch (Exception e) {
                e.printStackTrace();
                transaction.rollback();
            } finally {
                session.close();
            }
        }
    }
    public static void addNote(String username, String title, String content) {
        Session session = factory.openSession();
        if(session != null) {
            Transaction transaction = session.beginTransaction();
            try {
                Notice notice = new Notice();
                User user = UserAdministration.getUserByUsername(username);
                notice.setAuthor(user);
                notice.setColor("#000000");
                notice.setContent(content);
                notice.setLastModified(new Date());
                notice.setCreated(new Date());
                notice.setTitle(title);
                session.save(notice);
                transaction.commit();
            } catch(Exception e) {
                transaction.rollback();
            } finally {
                if(session != null)
                    session.close();
            }
        }
    }

    public static void setSessionFactory(SessionFactory factory) {
        NoticeAdministration.factory = factory;
    }
}
