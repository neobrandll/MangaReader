package com.NitroReader.utilities;

import com.sun.xml.internal.ws.wsdl.writer.document.soap.Body;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

public class SendEmails implements Runnable {
    private int manga_id;
    private final String username = "sivlejose89@gmail.com";
    private final String password = "26638992";


    public SendEmails(int manga_id) {
        this.manga_id = manga_id;
    }

    @Override
    public void run() {
        PropertiesReader props = PropertiesReader.getInstance();
        DBAccess dbAccess = DBAccess.getInstance();
        Connection con = dbAccess.createConnection();
        ResultSet rs;
        Properties emailProperties;
        Session mailSession;
        MimeMessage emailMessage;
        String emailPort = "587"; //GMAIL'S SMTP PORT

        try(PreparedStatement pstm = con.prepareStatement(props.getValue("querySSub"), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)){
            pstm.setInt(1, manga_id);
            rs = pstm.executeQuery();

            emailProperties = System.getProperties();
            emailProperties.put("mail.smtp.port", emailPort);
            emailProperties.put("mail.smtp.auth", "true");
            emailProperties.put("mail.smtp.starttls.enable", "true");
            String emailSubject = "testing Emails";

            mailSession = Session.getDefaultInstance(emailProperties, null);
            mailSession.setDebug(true);
            emailMessage = new MimeMessage(mailSession);
            emailMessage.setSubject(emailSubject);

            String emailHost = "smtp.gmail.com";
            String fromUser = "sivlejose89";
            String fromUserEmailPassword = "26638992";
            Transport transport = mailSession.getTransport("smtp");
            transport.connect(emailHost, fromUser, fromUserEmailPassword);

            if (rs.next()){
                BodyPart imageBody = new MimeBodyPart();
                javax.activation.DataSource fds = new FileDataSource(props.getValue("direction") + rs.getString(4));
                imageBody.setDataHandler(new DataHandler(fds));
                imageBody.setHeader("Content-ID", "<image>");
                rs.beforeFirst();
                while (rs.next()){ //getting all the emails and set different content per recipient
                    MimeMultipart multipart = new MimeMultipart("related");
                    BodyPart messageBody = new MimeBodyPart();
                    emailMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(rs.getString(1)));
                    messageBody.setContent(getMessage(rs.getString(2), rs.getString(3)), "text/html");
                    multipart.addBodyPart(messageBody);
                    multipart.addBodyPart(imageBody);
                    emailMessage.setContent(multipart);//for a html email
                    transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
                }
            }
            transport.close();
            System.out.println("Email sent successfully.");
        } catch (AddressException | SQLException | NullPointerException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    //html content of the message to send
    private String getMessage(String name, String manga){
        String message = null;
        message = "<section style=\"width: 100%; display:flex; flex-direction: column; font-family: sans-serif;\">\n" +
                "        <h1 style=\"text-align: center; font-size: 40px;\">Hola!</h1>\n" +
                "        <h2 style=\"font-size: 30px;\">Como estas " + name + "!</h2>\n" +
                "        <p style=\"font-size: 20px;\"> <strong>Queremos informarte que hay un nuevo capitulo del manga " + manga +"!\n" +
                "            <br>Ven Y leelo!\n" +
                "        </strong></p>\n" +
                "        <img style=\"text-align: center; object-fit:container;\" src=\"cid:image\" alt=\"Imagen\">\n" +
                "    </section>";
        return message;
    }
}
