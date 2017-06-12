/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.timecut.model;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeBodyPart;
import org.ow2.bonita.connector.email.SMTPAuthenticator;

public final class Mailer {    
    private static final String 
    FROM        = "timecut.notificaciones@gmail.com",
    PASSWORD    = "&continue=https%3A%2F",
    HOST        = "smtp.gmail.com",
    PORT        = "465";
    private String
    To          = "carlos.escom.ipn@gmail.com",
    Subject     = "Notificaciones TimeCut - Usted tiene tareas por hacer",
    Content     = "";
    Properties Properties;

    public Mailer(String To, String Subject, String Content) {
        this.To = To;
        this.Subject = Subject;
        this.Content = Content;
        Properties = new Properties();
    };

    public String getTo() {
        return To;
    }

    public void setTo(String To) {
        this.To = To;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String Subject) {
        this.Subject = Subject;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String Content) {
        this.Content = Content;
    }
        
    public void sendAsText() {
        Properties.put("mail.smtp.host", HOST);
        Properties.put("mail.smtp.user", FROM);
        Properties.put("mail.smtp.port", PORT);
        Properties.put("mail.smtp.auth", "true");
        Properties.put("mail.smtp.starttls.enable","true");
        Properties.put("mail.smtp.debug", "true");
        Properties.put("mail.smtp.socketFactory.port", PORT);
        Properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        Properties.put("mail.smtp.socketFactory.fallback", "false");

        SMTPAuthenticator auth = new SMTPAuthenticator(FROM, PASSWORD);
        Session session = Session.getInstance(Properties, auth);
        session.setDebug(true);

        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(FROM));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(To));
            msg.setSubject(Subject);
            
            Multipart multipart = new MimeMultipart("alternative");

            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setContent(Content,"text/plain");

            multipart.addBodyPart(textPart);
            msg.setContent( multipart );
            
            Transport transport = session.getTransport("smtps");
            transport.connect(HOST, Integer.valueOf(PORT), FROM, PASSWORD);
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
