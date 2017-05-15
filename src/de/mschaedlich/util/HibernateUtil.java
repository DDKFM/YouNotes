package de.mschaedlich.util;


import de.mschaedlich.domain.Notice;
import de.mschaedlich.domain.Registration;
import de.mschaedlich.domain.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.io.File;

/**
 * Created by maxsc on 10.05.2017.
 */
public class HibernateUtil {

    private static SessionFactory factory = null;
    public static void init() {
        if(System.getProperty("younotes.config.location") != null) {
            File configFile = new File(System.getProperty("younotes.config.location") + File.separator + "hibernate.cfg.xml");
            Configuration config = new Configuration();
            config.addAnnotatedClass(User.class);
            config.addAnnotatedClass(Notice.class);
            config.addAnnotatedClass(Registration.class);
            config.setProperty(Environment.HBM2DDL_AUTO, "update");
            config.configure(configFile);

            factory = config.buildSessionFactory();
            UserAdministration.setSessionFactory(factory);
            NoticeAdministration.setSessionFactory(factory);
        }
    }
}
