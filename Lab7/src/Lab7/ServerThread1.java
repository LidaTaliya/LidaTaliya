package Lab7;


import org.apache.commons.lang.SerializationUtils;

import javax.mail.MessagingException;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Map;

import static Lab7.Lab7.*;

public class ServerThread1 extends Thread {

    private InetAddress addr;//адрес клиента
    private int port;
    //private DatagramPacket incoming;
    // byte[] buffer = new byte[65536];
    DatagramPacket incoming;
    //= new DatagramPacket(buffer, buffer.length);
    DatagramSocket servers;


    public ServerThread1(DatagramSocket socket, DatagramPacket incoming1, InetAddress address, int port1) throws IOException {
        addr = address;
        port = port1;
        servers = socket;
        incoming = incoming1;
    }

    public void run() {
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/lida";
            String login = "postgres";
            String password = "postgres";
            Connection con = DriverManager.getConnection(url, login, password);
            try {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM CLIENTS");
                while (rs.next()) {
                    String str = rs.getString("login") + ":" + rs.getString(1);
                    System.out.println(str);
                }
                rs.close();
                stmt.close();
            } finally {
                con.close();
            }
            String em1="Введите свой адрес электронной почты:";
            String em2=Exchange(em1,servers, incoming, incoming.getAddress(), incoming.getPort());
            //  Email em=new Email(em2);
            System.out.println(em2);
            Email em=new Email(em2);
            boolean SuccelfullySent=false;
            try{
                SuccelfullySent=em.SendEmail();}
            catch(MessagingException e){
                System.out.println("Не удалось отправить сообщение");
            }catch(NoSuchAlgorithmException e){
                e.printStackTrace();
            }catch(UnsupportedEncodingException e){
                e.printStackTrace();
            }
        //    catch (NullPointerException e){
          //      e.printStackTrace();
           // }
if (SuccelfullySent){
        while (true) {
            servers.receive(incoming);
            byte[] data = incoming.getData();
            String s = new String(data, 0, incoming.getLength());

            if (s.equalsIgnoreCase("exit")) break;
          //  System.out.println("Клиент выбрал команду " + s + ". Ожидаем выполнения...");
                System.out.println(s);
            if (s.equals("1")) {

                String sout = "Введите через запятую информацию о добавляемом ребёнке: его имя,\"Карлсон\"(если он знаком с Карлсоном), вероятность пойти с Малышом, расстояние до школы и ключ. ";
                String s1 = Exchange(sout, servers, incoming, incoming.getAddress(), incoming.getPort());
                String sout1;
                boolean b = insert(s1);
                if (b) {
                    sout1 = "Друг добавлен";
                } else {
                    sout1 = "Проверьте корректность введенных данных.";
                }
                Sending(sout1, servers, incoming);

            } else if (s.equals("2")) {
                String sout2 = "Введите имя ребёнка";
                String s2 = Exchange(sout2, servers, incoming, incoming.getAddress(), incoming.getPort());
                boolean b2 = remove_greater(s2);
                String sout3;
                if (b2) {
                    sout3 = "Удаление успешно завершено.";
                } else {
                    sout3 = "Детей с именем больше заданного нет.";
                }
                Sending(sout3, servers, incoming);
            } else if (s.equals("3")) {
                Sending(show(), servers, incoming);
            } else if (s.equals("4")) {
                while (true) {
                    servers.receive(incoming);
                    byte[] data1 = incoming.getData();
                    String s1 = new String(data1, 0, incoming.getLength());
                    System.out.println(s1);
                    if (!s1.equals("EndSending")) {
                        StrFriends.add(s1);
                    } else {
                        break;
                    }
                }
                boolean b4 = AddFromFile(StrFriends);
                friends1 = sortByValues(friends1);
                friends1.entrySet().stream().forEach(
                        (friend) -> System.out.println(friend.getValue().name)
                );
                String imp;
                if (b4) {
                    imp = "Добавление успешно завершено. В коллекцию добавлено " + countFriends + " друзей.";
                } else {
                    imp = "В коллекцию ничего не добавлено";
                }
                Sending(imp, servers, incoming);

            } else if (s.equals("5")) {
                Sending(info(), servers, incoming);
            } else if (s.equals("6")) {
                String sout6;
                if (friends1.isEmpty()) {
                    sout6 = "Коллекция пустая";
                } else {
                    String sout7 = "Введите ключ ребенка:";
                    String s6 = Exchange(sout7, servers, incoming, incoming.getAddress(), incoming.getPort());
                    boolean b6 = remove(s6);
                    if (b6) {
                        sout6 = "Удаление успешно завершено.";
                    } else {
                        sout6 = "Ребёнок с таким ключом не найден.";
                    }
                }
                Sending(sout6, servers, incoming);
            } else if (s.equals("7")) {
                String sout6 = "Введите ключ ребенка";
                String s6 = Exchange(sout6, servers, incoming, incoming.getAddress(), incoming.getPort());
                boolean b6 = remove_greater_key(s6, friends1);
                String sout7;
                if (b6) {
                    sout7 = "Удаление успешно завершено.";
                } else {
                    sout7 = "Возникла ошибка(Коллекция пуста, некорректный ключ или нет детей с ключом, выше заданного)";
                }
                Sending(sout7, servers, incoming);
            } else if (s.equals("8")) {
                try {
                    for (Map.Entry<String, Friend> entry : friends1.entrySet()) {
                        byte[] Buf = SerializationUtils.serialize(entry.getValue());
                        DatagramPacket packet = new DatagramPacket(Buf, Buf.length, incoming.getAddress(), incoming.getPort());
                        servers.send(packet);
                        System.out.println(entry.getValue().name + " отправлен(а)");
                    }

                    String end = "End Sending";
                    DatagramPacket endSending = new DatagramPacket(end.getBytes(), end.getBytes().length, incoming.getAddress(), incoming.getPort());
                    servers.send(endSending);
                    System.out.println(end);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }}else{
    System.out.println("Письмо не отправлено");
}
    } catch(
    IOException e)

    {
        System.out.println("Сокет закрыт");
        System.exit(-1);
    }catch(
    ClassNotFoundException e)

    {
        e.printStackTrace();
    }catch(
    SQLException e)

    {
        e.printStackTrace();
    }


}
}
