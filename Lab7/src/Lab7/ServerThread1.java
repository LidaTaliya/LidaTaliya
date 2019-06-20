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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import static Lab7.Lab7.*;

public class ServerThread1 extends Thread {
    Connection con;
    String url = "jdbc:postgresql://localhost:5432/lida";
    String login = "postgres";
    String password = "postgres";
    String RegLOGIN;
    int id=0;
    private InetAddress addr;//адрес клиента
    private int port;
    String hash;
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
            con = DriverManager.getConnection(url, login, password);
            String em1="Введите свой адрес электронной почты:";
            RegLOGIN=Exchange(em1,servers, incoming, incoming.getAddress(), incoming.getPort());
            System.out.println(RegLOGIN);
            PreparedStatement ps0=con.prepareStatement("SELECT login FROM Clients WHERE login=?");
            ps0.setString(1,RegLOGIN);
            ResultSet logins=ps0.executeQuery();
            String pas="";
            if (!logins.next()){
                Email em=new Email(RegLOGIN);
                boolean SuccelfullySent=false;
                String pass=Email.CreatePassword(8);
                System.out.println(pass);
                hash=em.DoHash(pass);
                SuccelfullySent=em.SendEmail(pass);
                PreparedStatement ps=con.prepareStatement("INSERT INTO Clients (login, password) VALUES (?,?);");
                ps.setString(1,RegLOGIN);
                ps.setString(2,hash);
                int i=ps.executeUpdate();

                PreparedStatement pss2=con.prepareStatement("INSERT INTO Collections VALUES(?,null);");
                pss2.setInt(1,id);
                pss2.executeUpdate();
                if (i==1){
                    String regi="Регистрация прошла успешно. Введите пароль, который мы выслали Вам на почту: ";
                    pas=Exchange(regi,servers, incoming, incoming.getAddress(), incoming.getPort());
                }
            }else{
                String cl="Введите пароль, высланный Вам на почту во время регистрации:";
                pas=Exchange(cl,servers, incoming, incoming.getAddress(), incoming.getPort());
            }

            PreparedStatement pss=con.prepareStatement("SELECT id FROM Clients WHERE login=?;");
            pss.setString(1,RegLOGIN);
            ResultSet rss=pss.executeQuery();
            if (rss.next()){
                id=rss.getInt(1);
            }
            System.out.println("Это id "+id);

            String ClientsPass=Email.DoHash(pas);
            PreparedStatement ps2=con.prepareStatement("SELECT password FROM Clients WHERE login=?");
            ps2.setString(1,RegLOGIN);
            ResultSet registr=ps2.executeQuery();
            String BasePass=null;
            if (registr.next()){
                BasePass =registr.getString(1);
               System.out.println(BasePass);
            }
            String sr;
            while (!ClientsPass.equals(BasePass)){
                sr="Пароль неверный. Повторите ввод или введите 'exit' для выхода";
                String pas2=Exchange(sr,servers, incoming, incoming.getAddress(), incoming.getPort());
                if (pas2.equalsIgnoreCase("exit")){
                    System.exit(0);
                }
                ClientsPass=Email.DoHash(pas2);
                System.out.println(ClientsPass);
            }
            sr="Вы успешно авторизованы. Нажмите 'Enter' для продолжения.";
            Sending(sr,servers,incoming);
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
               /* while (true) {
                    servers.receive(incoming);
                    byte[] data1 = incoming.getData();
                    String s1 = new String(data1, 0, incoming.getLength());
                    System.out.println(s1);
                    if (!s1.equals("EndSending")) {
                        StrFriends.add(s1);
                    } else {
                        break;
                    }
                }*/
               PreparedStatement PSfindfriends =con.prepareStatement("SELECT collection FROM Collections WHERE id=?;");
               PSfindfriends.setInt(1,id);
               ResultSet RSfriends=PSfindfriends.executeQuery();
               String AllFriends="";
                boolean b4=false;
               if (RSfriends.next()) {
                   AllFriends = RSfriends.getString("collection");
               }
                try{
               if (!AllFriends.equals(null)){
                   String[] ArrayFr=null;

                   ArrayFr = AllFriends.split("%\n%");
                       for (int i = 0; i < ArrayFr.length; i++) {
                           StrFriends.add(ArrayFr[i]);
                       }
                       b4 = AddFromFile(StrFriends);
                       friends1 = sortByValues(friends1);
                       friends1.entrySet().stream().forEach(
                               (friend) -> System.out.println(friend.getValue().name)
                       );}
               else{
                   b4=false;
               }
                }
                 catch(NullPointerException e){
                    b4=false;
                }
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
            } else if (s.equals("9")) {
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
                        ArrayList<String> strfr;
                        strfr=WriteAsJson(friends1);
                        String AllFriends="";
                        for (String fr:strfr ){
                            AllFriends=String.format("%s\n%s",AllFriends,fr);
                        }
                        System.out.println(AllFriends);
                        //PreparedStatement ps=con.prepareStatement("INSERT INTO Collections (id, collection) SELECT Clients.id FROM Clients INNER JOIN Collections ON Clients.id=Collections.id WHERE login=? VALUES(Collection.id,?);");
                        PreparedStatement ps=con.prepareStatement("UPDATE Collections SET collection=? WHERE id=?");
                        System.out.println("Это id "+id);
                        ps.setString(1,AllFriends);
                        ps.setInt(2,id);
                        int i=ps.executeUpdate();


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else if(s.equals("8")) {
                String log = "Введите логин пользователя, чью коллекцию вы хотите посмотреть";

                String log2 = Exchange(log, servers, incoming, incoming.getAddress(), incoming.getPort());

                System.out.println(log2);

                PreparedStatement login = con.prepareStatement("SELECT collection FROM Collections INNER JOIN Clients ON Collections.id=Clients.id WHERE Clients.login=? ;");
                login.setString(1, log2);
                ResultSet RSanotherCol = login.executeQuery();
                String another = "";
                if (RSanotherCol.next()) {
                    another = RSanotherCol.getString("collection");
                }
                System.out.println(another);

                String[] AnotherFriends;

                if (another.equals(null)||another.equals("")) {
                    String end11 = "End Sending!";
                    DatagramPacket endd = new DatagramPacket(end11.getBytes(), end11.getBytes().length, incoming.getAddress(), incoming.getPort());
                    servers.send(endd);
                    String pusto = "В коллекции пользователя " + log2 + " нет друзей, либо пользователь с таким логином не найден.";
                    Sending(pusto, servers, incoming);
                } else{
                    AnotherFriends = another.split("\n");

                String newFr = "Друзья, содержащиеся в коллекции пользователя " + log2 + " :";
                DatagramPacket newfr = new DatagramPacket(newFr.getBytes(), newFr.getBytes().length, incoming.getAddress(), incoming.getPort());
                servers.send(newfr);
                for (int i = 1; i < AnotherFriends.length; i++) {
                    int name = AnotherFriends[i].lastIndexOf("name");
                    int carl = AnotherFriends[i].lastIndexOf("Carlson");
                    String AnotherName = AnotherFriends[i].substring(name + 7, carl - 3);
                    DatagramPacket frnew = new DatagramPacket(AnotherName.getBytes(), AnotherName.getBytes().length, incoming.getAddress(), incoming.getPort());
                    servers.send(frnew);
                }
                String end1 = "End Sending!";
                Sending(end1, servers, incoming);
            }
            }
            else{
                String what="Повторите ввод команды";
                Sending(what,servers,incoming);
            }
        }
con.close();
//}else{
    System.out.println("Письмо не отправлено");
//}
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
