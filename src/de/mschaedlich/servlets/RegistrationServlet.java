package de.mschaedlich.servlets;

import de.mschaedlich.domain.Registration;
import de.mschaedlich.util.UserAdministration;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import sun.java2d.loops.ProcessPath;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.midi.SysexMessage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * Created by MAXIMILIAN on 13.05.2017.
 */
@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getParameter("username") != null &&
                request.getParameter("email") != null &&
                request.getParameter("password") != null &&
                request.getParameter("repeatPassword") != null) {
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String mailRegex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
            if(email.matches(mailRegex)) {
                String password = DigestUtils.sha256Hex(request.getParameter("password"));
                String repeatPassword = DigestUtils.sha256Hex(request.getParameter("repeatPassword"));
                if(password.equalsIgnoreCase(repeatPassword)) {
                    Registration registration = new Registration();
                    registration.setEmail(email);
                    registration.setRegistationDate(new Date());
                    registration.setUsername(username);
                    registration.setPassword(password);
                    String hash = UserAdministration.addRegistration(registration);
                    if(!hash.equalsIgnoreCase("ERROR")) {
                        if (System.getProperty("younotes.config.location") != null) {
                            File mailPropertiesFile = new File(System.getProperty("younotes.config.location") + File.separator + "mail.properties");
                            if(mailPropertiesFile.exists()) {
                                Properties mailProperties = new Properties();
                                FileInputStream fis = new FileInputStream(mailPropertiesFile);
                                try {
                                    mailProperties.load(fis);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } finally {
                                    if(fis != null)
                                        fis.close();
                                }

                                try {
                                    HtmlEmail mail = new HtmlEmail();
                                    mail.setHostName(mailProperties.getProperty("mail.smtp.host"));
                                    mail.setSmtpPort(Integer.parseInt(mailProperties.getProperty("mail.smtp.port")));
                                    mail.setAuthentication(mailProperties.getProperty("mail.smtp.username"), mailProperties.getProperty("mail.smtp.password"));
                                    mail.setStartTLSEnabled(true);
                                    mail.setFrom(mailProperties.getProperty("mail.smtp.username"));
                                    mail.setSubject("Registrierung YouNotes");
                                    mail.setMsg("This is a test mail ... :-)");
                                    File mailTemplateFile = new File(System.getProperty("younotes.config.location") + File.separator + "mailTemplate.html");
                                    if(mailTemplateFile.exists()) {
                                        String mailContent = new String(Files.readAllBytes(mailTemplateFile.toPath()), Charset.forName("UTF-8"));

                                        String activationLink = mailProperties.getProperty("activationLink.urlbase") + "?activation=" + hash;
                                        mailContent = mailContent.replaceAll("##ACTIVATIONLINK##", activationLink);

                                        mailContent = mailContent.replaceAll("##USERNAME##", username);

                                        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:MM");
                                        mailContent = mailContent.replaceAll("##REGISTRATIONDATE##", sdf.format(registration.getRegistationDate()));
                                        mail.setHtmlMsg(mailContent);
                                    }
                                    mail.addTo(registration.getEmail());

                                    mail.send();
                                } catch (EmailException e) {
                                    e.printStackTrace();
                                }
                                response.sendRedirect("login.jsp");
                            }
                        }
                    }
                }
            }
        } else {
            response.sendRedirect("login.jsp");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getParameter("activation") != null) {
            String activationCode = request.getParameter("activation");
            boolean success = UserAdministration.checkRegistration(activationCode);
            response.setContentType("text/html");
            if(success) {
                response.getWriter().append("Ihr Registrierung war erfolgreich, sie können sich nun anmelden<a href=\"login.jsp\">Anmeldung</a>");
            } else {
                response.getWriter().append("Ihre Registrierung war leider nicht erfolgreich. Bitte versuchen Sie es erneut");
            }
        }
    }
}
