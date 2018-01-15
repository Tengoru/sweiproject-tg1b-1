package base.acitvitymeter;

/**
 * Created by Sümeyye on 14.01.2018.
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.io.*;
import java.util.Properties;
import java.util.Date;

import java.util.UUID;
import javax.mail.*;

import javax.mail.internet.*;
import com.sun.mail.smtp.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;



@RestController
@RequestMapping("email")
public class MailController {
    @Autowired
    private MailRepository emailRepo;
    @Autowired
    public JavaMailSender sender;

    @PostMapping
    public String create(@RequestBody(required = false) Mail input) {
        Mail save = new Mail(input.getEmail(), false, UUID.randomUUID().toString());

        String[] bunch = input.getEmail().split("@");
        if ((bunch[bunch.length - 1]).equals("hm.edu") || (bunch[bunch.length - 1]).equals("calpoly.edu")) {
            emailRepo.save(save);
            sendMail(input.getEmail(), save.getSecretKey());
            return "{\"msg\": \"ok\"}";
        } else {
            return "{\"msg\": \"Address not authorized. Please use @hm.edu or @calpoly.edu.\"}";
        }
    }

    @ExceptionHandler
    void handleIllegalArgumentException(IllegalArgumentException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }

    void sendMail(String to_mail, String secret) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to_mail);
            message.setSubject("Verification for posting an Activity");
            message.setText(secret);

            sender.send(message);

        }finally{}

    }
}
