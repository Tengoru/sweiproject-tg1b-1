package base.acitvitymeter;

/**
 * Created by Sümeyye on 14.01.2018.
 */

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Entity
public class Mail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String email;
    private boolean verified;
    private String secretKey;


    Mail(String mail, boolean verified, String secret) {
        this.email = mail;
        this.verified = verified;
        this.secretKey = secret;
    }
    public Long getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setSecretKey(String secret_key) {
        this.secretKey = secret_key;
    }

    public String getSecretKey() {

        return secretKey;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
