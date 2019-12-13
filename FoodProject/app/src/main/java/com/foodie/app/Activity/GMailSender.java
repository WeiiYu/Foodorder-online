package com.foodie.app.Activity;
import com.provider.JSSEProvider;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Security;
import java.util.Properties;


public class GMailSender extends javax.mail.Authenticator {
    private String mailhost = "smtp.gmail.com";
    private String user;
    private String password;
    private Session session;
    // private variable to send the email

    static {
        Security.addProvider(new JSSEProvider());
        // security provider
    }

    public GMailSender(String user, String password) {
        this.user = user;
        this.password = password;
// user,password of Gmail
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        // SETpROPERTY
        props.setProperty("mail.host", mailhost);
        //MAILHOST
        props.put("mail.smtp.auth", "true");
        //auth
        props.put("mail.smtp.port", "465");
        //Gmail port
        props.put("mail.smtp.socketFactory.port", "465");
        //Gmail port
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        //SMTP socketFactory.class
        props.put("mail.smtp.socketFactory.fallback", "false");
        //Fall back
        props.setProperty("mail.smtp.quitwait", "false");
//quite wait
        session = Session.getDefaultInstance(props, this);
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(user, password);
        // user passwork check if they are correct
    }

    public synchronized void sendMail(String subject, String body,
                                      String sender, String recipients) throws Exception {
        MimeMessage message = new MimeMessage(session);
        DataHandler handler = new DataHandler(new ByteArrayDataSource(body.getBytes(), "text/plain"));
        message.setSender(new InternetAddress(sender));
        message.setSubject(subject);
        message.setDataHandler(handler);

        if (recipients.indexOf(',') > 0)
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients));
        else
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipients));

        Transport.send(message);
    }
    // this is send Email method


    public class ByteArrayDataSource implements DataSource {
        private byte[] data;
        private String type;
        //two variables data and type

        public ByteArrayDataSource(byte[] data, String type) {
            super();
            this.data = data;
            this.type = type;
            //ByteArrayDataSource Method
        }


        public void setType(String type) {
            this.type = type;
        }
        // set method

        public String getContentType() {
            if (type == null)
                return "application/octet-stream";
            else
                return type;
            //get method
        }

        public InputStream getInputStream() throws IOException {
            return new ByteArrayInputStream(data);
            //get method
        }

        public String getName() {
            return "ByteArrayDataSource";
        }
//get name method
        public OutputStream getOutputStream() throws IOException {
            throw new IOException("Not Supported");
        }
    }//getOutputStream method


}