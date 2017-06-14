/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.timecut.model;

import java.util.Properties;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author cavca
 */
public class MailerActivationCode {

    public void sendMail(String correo, String valCode) {
        String d_email = "cesaralejandrovc@gmail.com",
                d_password = "giwhrwddmjdoimmj",
                d_host = "smtp.gmail.com",
                d_port = "587",
                m_to = correo,
                m_subject = "TimeCut - Codigo de activacion de cuenta",
                m_text = "Su codigo de activacion es\""+valCode+"\".\nFavor de introducirlo en la aplicación.";

        Properties props = new Properties();
        props.put("mail.smtp.host", d_host);
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.port", d_port);
        props.setProperty("mail.smtp.user", d_email);
        props.setProperty("mail.smtp.auth", "true");
//        props.put("mail.smtp.debug", "true");
//        props.put("mail.smtp.socketFactory.port", d_port);
//        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//        props.put("mail.smtp.socketFactory.fallback", "false");
        try {

            Session session = Session.getDefaultInstance(props, null);
            BodyPart text = new MimeBodyPart();
            text.setText(m_text);
            session.setDebug(true);

            MimeMultipart m = new MimeMultipart();
            m.addBodyPart(text);

            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(d_email));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(m_to));
            msg.setSubject(m_subject);
            msg.setContent(m);

            Transport transport = session.getTransport("smtp");
            transport.connect(d_email, d_password);
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();

        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
