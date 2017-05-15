package de.mschaedlich.util;

import de.mschaedlich.domain.Registration;
import de.mschaedlich.domain.User;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.security.SecureRandom;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
    public static String addRegistration(Registration registration) {
        Session session = factory.openSession();
        if (session != null) {
            String hash = "ERROR";
            Transaction transaction = session.beginTransaction();
            try {
                String hashValue = DigestUtils.sha256Hex(UUID.randomUUID().toString().getBytes());
                registration.setRegistrationURL(hashValue);
                session.save(registration);
                transaction.commit();
                hash = hashValue;
            } catch(Exception e) {
                transaction.rollback();
            } finally {
                session.close();
                return hash;
            }
        }
        return "ERROR";
    }
    public static boolean checkRegistration(String activationCode) {
        Session session = factory.openSession();
        if(session != null) {
            try {
                List<Registration> registrations = session.createQuery("FROM Registration WHERE registrationURL = '" + activationCode + "'").getResultList();
                if(registrations != null && !registrations.isEmpty()) {
                    Registration registration = registrations.get(0);
                    if(new Date().getTime() - registration.getRegistationDate().getTime() < 86400000) {
                        //Registration correct
                        User user = new User();
                        user.setPassword(registration.getPassword());
                        user.setUsername(registration.getUsername());
                        user.setBlocked(false);
                        Transaction transaction = session.beginTransaction();
                        try {
                            session.remove(registration);
                            session.save(user);
                            transaction.commit();
                            return true;
                        } catch(Exception e) {
                            transaction.rollback();
                            return false;
                        } finally {

                        }
                    } else {
                        return false;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            } finally {
                session.close();
            }
        }
        return false;
    }
    public static void setSessionFactory(SessionFactory factory) {
        UserAdministration.factory = factory;
    }
}
