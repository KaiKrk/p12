package com.oc.p12.Service;

import com.oc.p12.Bean.MailDetails;
import com.sun.mail.smtp.SMTPTransport;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailService {
    MailDetails mailDetails = new MailDetails();

    public void sendEmailForPickup(String email) throws Exception {


        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        System.out.println(" email : " + mailDetails.getMyAccountEmail() + "  " + mailDetails.getPassword());

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailDetails.getMyAccountEmail(), mailDetails.getPassword());
            }
        });
        Message message = prepareMessage(session, mailDetails.getMyAccountEmail(), email);
        System.out.println(message);
        Transport.send(message);

    }

    private Message prepareMessage(Session session, String myAccountEmail, String recepient) throws MessagingException {

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject(mailDetails.getSubject());
            return message;
        } catch (AddressException e) {
            e.printStackTrace();
        }
        return null;
    }
}