package de.mschaedlich.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by MAXIMILIAN on 13.05.2017.
 */
@Entity
@Table(name = "registration")
public class Registration {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer registrationID;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;
    @Column(name = "registrationUrl")
    private String registrationURL;

    @Column(name = "registrationDate")
    private Date registationDate;

    public Registration() {

    }

    public Integer getRegistrationID() {
        return registrationID;
    }

    public void setRegistrationID(Integer registrationID) {
        this.registrationID = registrationID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegistrationURL() {
        return registrationURL;
    }

    public void setRegistrationURL(String registrationURL) {
        this.registrationURL = registrationURL;
    }

    public Date getRegistationDate() {
        return registationDate;
    }

    public void setRegistationDate(Date registationDate) {
        this.registationDate = registationDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
