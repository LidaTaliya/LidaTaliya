package Client;

import Lab7.*;
import org.apache.commons.lang.SerializationUtils;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.apache.commons.lang.SerializationUtils.*;

public class Client {
   static Path path;
   static File file;
    static Map<String, Friend> friends1 = new HashMap<String, Friend>() {
    };
    static Friend[] friends;
   static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static boolean isNumber(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private static Friend[] MakeArray() {
        Friend[] friends = new Friend[(friends1.size())];
        int i = 0;
        for (Map.Entry<String, Friend> e : friends1.entrySet()) {
            friends[i] = e.getValue();
            i = i + 1;
        }
        return friends;
    }
    private static void StartStory(){


        Parent mother = new Parent("Мама", false);
        Kid kid = new Kid("Малыш", 0.8, true);
        Dog pudel = new Dog(Size.S, Color.Black, "пудель", 0.9);
        Carlson carlson = new Carlson("Карлсон", true);
        mother.Breathe(kid);
        mother.AttitudeToCarlson(kid);
        Group g = new Group();
        g.AddKid(kid);
        g.AddPudel(pudel);
        g.AddCarlson(carlson);
        g.AddFriends(friends);
        Friend[] FriendsWhoGo = g.WhoIsGoing();
        g.AddFriendsWhoGo(FriendsWhoGo);
        g.BeHappyToGoToSchool();
        g.AllWalk();
        kid.BeHappy(friends, carlson);
        pudel.appear();
        pudel.wantToGo(kid);
        g.CrossTheStreet();
        pudel.goToKid(kid);
        pudel.toSniffTheKnees(kid);
        pudel.toYelp();
        try {
            kid.BeHappyWithDog(pudel);
        } catch (KidNoLoveDogs e) {
            System.out.print(e.getMessage());
        }
        pudel.FeelTheAttitude(kid);
        pudel.LoveEveryone(kid);
        kid.LoveDog(pudel);
        kid.ActionsWithDog(pudel);
        kid.Sounds(pudel);
        try {
            pudel.ThinkSo();
        } catch (NoThinkSo e) {
            System.out.print(e.getMessage());
        }
        pudel.JumpAndYelp();
        g.TurnToStreet();
        pudel.Run();
        System.exit(0);

    }

    private static void WriteInFile(Map<String, Friend> ourMap) throws IOException, FileNotFoundException {
        FileWriter fstream1 = new FileWriter(path.toFile());// конструктор с одним параметром - для перезаписи
        BufferedWriter out1 = new BufferedWriter(fstream1); //  создаём буферезированный поток
        out1.write(""); // очищаем, перезаписав поверх пустую строку
        out1.close(); // закрываем

        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(path.toFile()));
        ourMap.entrySet().stream()
                .forEach(x -> {
                    if (x.getValue().MeetCarlson) {
                        String fr = "{\"name\":\"" + x.getValue().name + "\",\"Carlson\":\"" + "Карлсон" + "\",\"ChanceToWalk\":\"" + x.getValue().ChanceToWalk + "\",\"number\":\"" + x.getValue().number + "\",\"DistanceFromSchool\":\"" + x.getValue().DistanceFromSchool+"\"}";
                        try {
                            stream.write(fr.getBytes());
                            stream.write(System.lineSeparator().getBytes());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        String fr = "{\"name\":\"" + x.getValue().name + "\",\"Carlson\":\"" + "не Карлсон" + "\",\"ChanceToWalk\":\"" + x.getValue().ChanceToWalk + "\",\"number\":\"" + x.getValue().number + "\",\"DistanceFromSchool\":\"" + x.getValue().DistanceFromSchool+"\"}";
                        try {
                            stream.write(fr.getBytes());
                            stream.write(System.lineSeparator().getBytes());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
        stream.close();
    }
private static void menu(){
      System.out.println("Выберите команду - введите число от 1 до 8:");
        System.out.println("1 - добавить нового друга по ключу");
        System.out.println("2 - удалить из коллекции друзей, превышающие заданные");
        System.out.println("3 - вывести в строковом представление всех друзей в коллекции");
        System.out.println("4 - добавить в коллекцию все данные из файла");
        System.out.println("5 - вывести информацию о коллекции");
        System.out.println("6 - удалить из коллекции друга по ключу");
        System.out.println("7 - удалить из коллекции друзей, ключ которых превышает заданный");
        System.out.println("8 - выход из меню (запуск программы)");
}

private static void importFromFile(DatagramChannel channel,InetSocketAddress hostAddress) throws IOException,PortUnreachableException{
    try{
        FileInputStream fr=new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(fr));

      //  byte[] str;
        ByteBuffer str;
        String strLine;
        while((strLine = br.readLine()) != null){
            //str=strLine.getBytes();
            str = ByteBuffer.wrap(strLine.getBytes());
           // DatagramPacket export = new DatagramPacket(str, str.length, InetAddress.getByName("localhost"), 4444);
            channel.send(str,hostAddress);
        }
        String EndSending="EndSending";
        ByteBuffer end = ByteBuffer.wrap(EndSending.getBytes());
        //DatagramPacket end=new DatagramPacket(str,str.length,InetAddress.getByName("localhost"),4444);
        channel.send(end,hostAddress);

    }
    catch (FileNotFoundException e){
        System.out.println("Файл не найден");
        System.exit(0);
    }catch (PortUnreachableException e){
        System.out.println("Сервер недоступен");
        System.exit(0);
    }
    }
    private static String ReceiveData(DatagramChannel channel,byte[] buffer) throws IOException{
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        channel.socket().receive(packet);

        byte[] data = packet.getData();
        String s1 = new String(data, 0, packet.getLength());
        return s1;
    }

    private static void imports(DatagramChannel channel, InetSocketAddress hostAddress) throws IOException{
        ByteBuffer b = ByteBuffer.wrap("4".getBytes());
        channel.send(b,hostAddress);
        try{
            importFromFile(channel,hostAddress);}
        catch(PortUnreachableException e){
            System.out.println("Сервер недоступен");
        }
    }

    public static void main(String[] args) throws IOException {

        friends1 = new ConcurrentHashMap<String, Friend>() {
        };
        try{
            path= Paths.get(System.getenv("Friendss"));
            file = path.toFile();}
        catch (NullPointerException e){
            System.out.println("Проверьте переменную окружения.");
            System.exit(0);
        }


      //  DatagramSocket fromserver = new DatagramSocket();

        InetSocketAddress hostAddress;
        if (args==null){
            hostAddress = new InetSocketAddress("localhost", 4444);
        }else{
        //hostAddress = new InetSocketAddress("localhost", Integer.parseInt(args[0]));}
            hostAddress = new InetSocketAddress("localhost", 4444);}
        DatagramChannel channel = DatagramChannel.open();
       // channel.socket().setBroadcast(true);
//        channel.bind(hostAddress);
        //channel.configureBlocking(false);

        channel.connect(hostAddress);


        ByteBuffer b2 = ByteBuffer.wrap("I'm a client!".getBytes());
        channel.send(b2,hostAddress);

            String s = "";
            imports(channel, hostAddress);

            while (true) {

                if (!s.equals("4")) {
                    ByteBuffer b = ByteBuffer.wrap(s.getBytes());
                    channel.send(b, hostAddress);
                }

                if (s.equalsIgnoreCase("exit")) break;
                if (s.equals("4")) {
                    imports(channel, hostAddress);
                }
                if (s.equals("8")) {
                    while (true) {
                        byte[] buffer = new byte[65536];
                        String s1 = ReceiveData(channel, buffer);
                        // ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
                        //ObjectInputStream ois = new ObjectInputStream(bais);
                        // ois.close();
                        if (s1.equals("End Sending")) {
                            System.out.println("Друзья переданы");
                            break;
                        } else {
                            try {
                                // Friend fr = (Friend) ois.readObject();

                                Friend fr = (Friend) deserialize(buffer);

                                boolean key = friends1.entrySet().stream()
                                        .anyMatch(x -> x.getValue().number.equals(fr.number));
                                if (!key) {
                                    friends1.put(fr.number, fr);
                                }

                            } catch (NoClassDefFoundError e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    friends1.entrySet().stream().forEach(
                            (friend) -> System.out.println(friend.getValue().name)
                    );
                    WriteInFile(friends1);
                    friends = MakeArray();
                    StartStory();


                }
                byte[] buffer = new byte[65536];
                String s1 = ReceiveData(channel, buffer);
                if (s1.equals("menu")) {
                    menu();
                    //s=in.readLine();
                } else {
                    System.out.println(s1);
                }

                s = in.readLine();
            }

        channel.close();
        in.close();

    }
}