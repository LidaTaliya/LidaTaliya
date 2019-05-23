package Lab5;


import org.apache.commons.lang.SerializationUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Map;

import static Lab5.Lab5.*;

public class ServerThread1 extends Thread {

   private InetAddress addr;//адрес клиента
    private int port;
    DatagramPacket incoming ;
    DatagramSocket servers;


    public ServerThread1(DatagramSocket socket, DatagramPacket incoming1,InetAddress address,int port1) throws IOException {
        addr = address;
        port=port1;
        servers=socket;
        incoming=incoming1;
    }
    public void run(){
        try {
        while(true){
            servers.receive(incoming);
            byte[] data = incoming.getData();
            String s = new String(data, 0, incoming.getLength());

            if (s.equalsIgnoreCase("exit")) break;
            if (s.equals("Close Server")){servers.close();}
          //  System.out.println("Клиент выбрал команду " + s + ". Ожидаем выполнения...");

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

            }
            else if (s.equals("2")) {
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
                   // System.out.println(s1);
                    if (!s1.equals("EndSending")) {
                        StrFriends.add(s1);
                    } else {
                        break;
                    }
                }
                boolean b4 = AddFromFile(StrFriends);
                friends1 = sortByValues(friends1);
             /*   friends1.entrySet().stream().forEach(
                        (friend) -> System.out.println(friend.getValue().name)
                );*/
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
                String sout8 = "Введите ключ ребенка";
                String s7 = Exchange(sout8, servers, incoming, incoming.getAddress(), incoming.getPort());
                boolean b6 = remove_greater_key(s7, friends1);
                String sout9;
                if (b6) {
                    sout9 = "Удаление успешно завершено.";
                } else {
                    sout9 = "Возникла ошибка(Коллекция пуста, некорректный ключ или нет детей с ключом, выше заданного)";
                }
                Sending(sout9, servers, incoming);
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
        }
    } catch (IOException e) {
        System.out.println("Сокет закрыт");
        System.exit(-1);
    }



}}
