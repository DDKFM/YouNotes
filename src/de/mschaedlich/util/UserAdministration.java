package de.mschaedlich.util;

import de.mschaedlich.domain.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by maxsc on 11.05.2017.
 */
public class UserAdministration {
    private static SessionFactory factory = null;

    public static User getUserByUsername(String username) {
        Session session = factory.openSession();
        User user = null;
        try {
            List<User> users = session.createQuery("FROM User WHERE username = '" + username + "'").getResultList();
            if(users != null && !users.isEmpty()) {
                user = users.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return user;
    }
    public static void setSessionFactory(SessionFactory factory) {
        UserAdministration.factory = factory;
    }
}
