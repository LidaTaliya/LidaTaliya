package Lab7;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Properties;
import java.util.Random;

public class Email {
    static Transport transport;
    static Properties properties;
    static Session session;
    static MimeMessage message;
    static  String to;//="lidiapetroshenok@gmail.com"; kolegova999@mail.ru
    static  String from = "r3140@mail.ru";
    static  String host = "smtp.mail.ru";// mail server host
    static int t1=50000;
    static int t2=50000;
    String password;
    public Email(String em) {
        this.to = em;
    }

public  String CreatePassword(int len) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    System.out.println("Your Password: ");
    String charsCaps = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String nums = "0123456789";
    String passSymbols = charsCaps + nums;
    Random rnd = new Random();
    char[] password = new char[len];

    for (int i = 0; i < len; i++) {
        password[i] = passSymbols.charAt(rnd.nextInt(passSymbols.length()));
    }
    //System.out.println("char: "+password);
    MessageDigest md = MessageDigest.getInstance("SHA-1");
    md.reset();
    String passwd = new String(password);
    String salt = "*1#)3Ko(%";
    String pepper = "*&^mVLC(#";
    byte[] data = (pepper + passwd + salt).getBytes();
    byte[] hashbytes = md.digest(data);
    String parol=new String(hashbytes);
    return parol;
}


public boolean SendEmail() throws MessagingException, NoSuchAlgorithmException, UnsupportedEncodingException{
    /*    password=CreatePassword(10);
        System.out.println(password);
    System.out.println(to);
    properties = System.getProperties();
    properties.put("mail.smtp.host", host);
    //properties.put("mail.smtp.starttls.enable", true);
    properties.put("mail.smtp.connectiontimeout", t1);
    properties.put("mail.smtp.timeout", t2);
    properties.put("mail.smtp.auth", true); //"true"
    properties.put("mail.smtp.port", 465); //"465"
    session = Session.getDefaultInstance(properties, new javax.mail.Authenticator(){
        protected PasswordAuthentication getPasswordAuthentication(){
            return new PasswordAuthentication("r3140@mail.ru",
                    "itmoshechka");
        }
    });
    session.setDebug(true);
        message = new MimeMessage(session);
        message.setSender(new InternetAddress(from));
        message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
        message.setSubject("Успешная регистрация", "KOI8-R");
        message.setText("Hello, this is example of sending email  ");
        System.out.println(message);
        transport = session.getTransport("smtp");
        transport.connect(host,from,"itmoshechka");
        transport.sendMessage(message,message.getAllRecipients());
        //transport.send(message);
        System.out.println("message sent successfully....");
        transport.close();
    return true;*/
    Properties prop = System.getProperties();
    prop.setProperty("mail.smtp.host", host);
    //prop.put("mail.smtp.host", host);
    Session sess = Session.getDefaultInstance(prop);
    try {
        MimeMessage msg = new MimeMessage(sess);
        msg.setFrom(new InternetAddress(from));
        msg.addRecipient(Message.RecipientType.TO,
                new InternetAddress(to));
        msg.setSubject("Your secret password");
        msg.setText("Password: T&^9hs09\21");
        transport = session.getTransport("smtp");
        transport.connect(host,(Integer) prop.get("mail.smtp.port"),from,"itmoshechka");
        transport.sendMessage(message,message.getAllRecipients());
        transport.send(message);
        transport.close();
        //Transport.send(msg);
    } catch (MessagingException e) {
        e.printStackTrace();
        return false;
    }
    return true;
}
}
