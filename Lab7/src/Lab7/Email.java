package Lab7;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Email {
    static MimeMessage message;
    static  String to;
    static  String from = "lydiapetroshenok@gmail.com";
    static  String host = "localhost";            // mail server host

    public Email(String em) {
        this.to = em;
    }
public void SendEmail() throws MessagingException{
    Transport.send(message);
    System.out.println("message sent successfully....");
}
    public static void main(String[] args) {
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        Session session = Session.getDefaultInstance(properties);
        try {
            message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
            message.setSubject("Успешная регистрация", "KOI8-R");
            message.setText("Hello, this is example of sending email  ");

        }catch (MessagingException mex) {mex.printStackTrace();}
    }
}
