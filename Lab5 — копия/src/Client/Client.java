package Client;

import Lab5.*;
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
import java.util.concurrent.locks.ReentrantLock;

import static org.apache.commons.lang.SerializationUtils.*;
public class Client extends Thread {

    static Path path;
    static File file;
    static InetSocketAddress hostAddress;
    static DatagramChannel channel;
    static Map<String, Friend> friends1 = new HashMap<String, Friend>() {
    };

    ReentrantLock l;
    String first;
    String second;
    String third;
    String key;
    public Client(ReentrantLock lock,String first1,String second2,String third3){
        l=lock;
        this.first=first1;
        this.second=second2;
        this.third=third3;
    }
    public Client(ReentrantLock lock,String first1,String second2,String third3,String key){
        l=lock;
        this.first=first1;
        this.second=second2;
        this.third=third3;
        this.key=key;
    }
    static Friend[] friends;
    static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    private static Friend[] MakeArray() {
        Friend[] friends = new Friend[(friends1.size())];
        int i = 0;
        for (Map.Entry<String, Friend> e : friends1.entrySet()) {
            friends[i] = e.getValue();
            i = i + 1;
        }
        return friends;
    }

    private static void StartStory() {


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
        FileWriter fstream1 = new FileWriter(path.toFile());
        BufferedWriter out1 = new BufferedWriter(fstream1);
        out1.write("");
        out1.close();

        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(path.toFile()));
        ourMap.entrySet().stream()
                .forEach(x -> {
                    if (x.getValue().MeetCarlson) {
                        String fr = "{\"name\":\"" + x.getValue().name + "\",\"Carlson\":\"" + "Карлсон" + "\",\"ChanceToWalk\":\"" + x.getValue().ChanceToWalk + "\",\"number\":\"" + x.getValue().number + "\",\"DistanceFromSchool\":\"" + x.getValue().DistanceFromSchool + "\"}";
                        try {
                            stream.write(fr.getBytes());
                            stream.write(System.lineSeparator().getBytes());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        String fr = "{\"name\":\"" + x.getValue().name + "\",\"Carlson\":\"" + "не Карлсон" + "\",\"ChanceToWalk\":\"" + x.getValue().ChanceToWalk + "\",\"number\":\"" + x.getValue().number + "\",\"DistanceFromSchool\":\"" + x.getValue().DistanceFromSchool + "\"}";
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

    private static void menu() {
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

    private static void importFromFile(DatagramChannel channel, InetSocketAddress hostAddress) throws IOException, PortUnreachableException {
        try {
            FileInputStream fr = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fr));

            ByteBuffer str;
            String strLine;
            while ((strLine = br.readLine()) != null) {

                str = ByteBuffer.wrap(strLine.getBytes());
                channel.send(str, hostAddress);
            }
            String EndSending = "EndSending";
            ByteBuffer end = ByteBuffer.wrap(EndSending.getBytes());
            channel.send(end, hostAddress);

        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден");
            System.exit(0);
        } catch (PortUnreachableException e) {
            System.out.println("Сервер недоступен");
            System.exit(0);
        }
    }

    private static String ReceiveData(DatagramChannel channel, byte[] buffer) throws IOException {
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        channel.socket().receive(packet);

        byte[] data = packet.getData();
        String s1 = new String(data, 0, packet.getLength());
        return s1;
    }

    private static void imports(DatagramChannel channel, InetSocketAddress hostAddress) throws IOException {
        ByteBuffer b = ByteBuffer.wrap("4".getBytes());
        channel.send(b, hostAddress);
        try {
            importFromFile(channel, hostAddress);
        } catch (PortUnreachableException e) {
            System.out.println("Сервер недоступен");
        }
    }
private static String Commands(String s) throws IOException{
    String out;
  //  while (true) {
    if (!s.equals("4")) {
        ByteBuffer b = ByteBuffer.wrap(s.getBytes());
        channel.send(b, hostAddress);
    }
        if (s.equals("Close Server")) {
            //System.out.println("Сокет закрыт");
            out="Сокет закрыт";
            System.exit(0);
        }
        //}
      //  if (s.equalsIgnoreCase("exit")) break;
        if (s.equals("4")) {
            imports(channel, hostAddress);
        }
        if (s.equals("8")) {
            while (true) {
                byte[] buffer = new byte[65536];
                String s1 = ReceiveData(channel, buffer);
                if (s1.equals("End Sending")) {
                    //System.out.println("Друзья переданы");
                    out="Друзья переданы";
                    break;
                } else {
                    try {
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
            WriteInFile(friends1);
            friends = MakeArray();
            StartStory();


        }
        byte[] buffer = new byte[65536];
        String s1 = ReceiveData(channel, buffer);
     //   if (s1.equals("menu")) {
         //   menu();
      //  } else {
            out=s1;
    //    }
       // s = in.readLine();

return out;
   // }
}
    public void run() {
        try {
            hostAddress = new InetSocketAddress("localhost", 5555);
            channel = DatagramChannel.open();
            channel.connect(hostAddress);

            ByteBuffer b2 = ByteBuffer.wrap("I'm a client!".getBytes());
            channel.send(b2, hostAddress);
            friends1 = new ConcurrentHashMap<String, Friend>() {
            };
            try {
                path = Paths.get(System.getenv("Friendss"));
                file = path.toFile();
            } catch (NullPointerException e) {
                System.out.println("Проверьте переменную окружения.");
                System.exit(0);
            }
            l.lock();
            System.out.println(Thread.currentThread()+first+":"+Commands(first));
            l.unlock();
            Thread.sleep(40);
            l.lock();
            System.out.println(Thread.currentThread()+second+":"+Commands(second));
            if (key!=null){
                System.out.println(Thread.currentThread()+second+": "+key+" "+Commands(key));
            }
            l.unlock();
            Thread.sleep(40);
            l.lock();
            System.out.println(Thread.currentThread()+third+":"+Commands(third));
            l.unlock();

            //channel.close();
            in.close();
            this.interrupt();

        }catch (IOException e){
            e.printStackTrace();
        }catch(InterruptedException e){
           e.printStackTrace();}

    }
}