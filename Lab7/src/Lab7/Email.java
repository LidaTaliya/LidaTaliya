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
   // static Transport transport;
    static Properties properties;
    static Session session;
    static MimeMessage message;
    static  String to;//="lidiapetroshenok@gmail.com";// kolegova999@mail.ru
    static  String from = "r3140@mail.ru";
    static  String host = "smtp.mail.ru";// mail server host
    static int t1=50000;
    static int t2=50000;
   // String password;
    public Email(String em) {
        this.to = em;
    }

public static String CreatePassword(int len) {
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
    String passwd = new String(password);
    //String parol=new String(hashbytes);
    //System.out.println(passwd);
    return passwd;
}
public static String DoHash(String pass){
    byte[] hashbytes;
    StringBuffer code=new StringBuffer();
    hashbytes=null;
        try {

            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.reset();
            String salt = "*1#)3Ko(%";
            String pepper = "*&^mVLC(#";
            byte[] data = (pepper + pass + salt).getBytes();
            hashbytes = md.digest(data);
            for (int i=0;i<hashbytes.length;++i){
                code.append(Integer.toHexString(0x0100+hashbytes[i]&0x00FF).substring(1));
            }
        }

        catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }
   // System.out.println("Это пароль"+code.toString());
    return code.toString();
}


public boolean SendEmail(String password){
    Properties prop = new Properties();
    System.out.println(password);
    prop.put("mail.smtp.host", "smtp.mail.ru");
   // prop.put("mail.debug", "true");
    prop.put("mail.smtp.auth", "true");
    prop.put("mail.smtp.user", from);
    prop.put("mail.smtp.port", 465);
    prop.put("mail.smtp.socketFactory.port", 465);
    //prop.put("mail.smtp.host", host);
    Session sess = Session.getInstance(prop,null);
    //sess.setDebug(true);
    try {
        MimeMessage msg = new MimeMessage(sess);
        msg.setFrom(new InternetAddress(from));
        msg.addRecipient(Message.RecipientType.TO,
                new InternetAddress(to));
        msg.setSubject("Your secret password");
        msg.setText("Password: "+password);
        Transport transport = sess.getTransport("smtps");
        transport.connect(host,465,from,"itmoshechka");
        msg.saveChanges();
        transport.sendMessage(msg,msg.getAllRecipients());
        //transport.send(msg);
        transport.close();
        //Transport.send(msg);
    } catch (MessagingException e) {
        e.printStackTrace();
        return false;
    }
    return true;
}
}
