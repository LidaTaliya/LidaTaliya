package Lab5;


import java.io.File;
import java.net.*;
import java.nio.file.Path;
import java.util.*;


import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;


public class Lab5 implements Runnable{

    static Map<String, Friend> friends1 = new HashMap<String, Friend>() {
    };
    static Scanner scan = new Scanner(System.in);
    static Date date;
    static int countFriends;
    static ArrayList<String> StrFriends= new ArrayList<String>();
    static Path path;
    static File file;
    static DatagramSocket servers;

    private static boolean isNumber(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }


    private static boolean isLetters(String str) {
        char[] chars = str.toCharArray();
        for (char c : chars) {
            if (!Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }

    public static <K extends Comparable, V extends Comparable> Map<K, V> sortByValues(Map<K, V> map) {
        List<Map.Entry<K, V>> entries = new LinkedList<Map.Entry<K, V>>(map.entrySet());

        Collections.sort(entries, (o1, o2) -> {
            Friend fr1 = (Friend) o1.getValue();
            Friend fr2 = (Friend) o2.getValue();
            return fr1.compareTo(fr2);
        });
        Map<K, V> sortedMap = new LinkedHashMap<K, V>();

        for (Map.Entry<K, V> entry : entries) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }


    /**
     * Данный метод используется для того, чтобы добавить новый элемент коллекции по заданному ключу.
     * Для добавления элемента пользователю будет предложено ввести имя ребенка, "Карлсон", если ребенок знаком с Карлсоном, вероятность пойти с Малышом.
     * На вход в метод подаётся заранее проверенный на повторение ключ добавляемого ребёнка и коллекция, в которую ребёнок добавляется.
     */
    public static boolean insert(String fr){
        try {String[] fr1=fr.split(",");
        String key=KidsKey(fr1[4]);
        if (!key.equals("нет ключа")||isLetters(fr1[0])||isNumber(fr1[2])||isNumber(fr1[3])){
            Friend newFriend=new Friend(fr1[0],fr1[1],Double.parseDouble(fr1[2]),key,Double.parseDouble(fr1[3]),date);
            friends1.put(key,newFriend);
            return true;
        }else{
           return false;
        }}
        catch(Exception e){
            return false;
        }
    }

    /**
     * Данный метод используется для того, чтобы удалить из коллекции друзей, имена которых по алфавиту позже введенного имени, если ребенок с введенным именем имеется.
     * Чтобы совершить данную команду, пользователю будет предложено ввести имя ребенка.
     * На вход в данный метод подаётся имя ребёнка, и коллекция, из которой происходит удаление.
     */
    public static boolean remove_greater(String name) {
        boolean c;
        c = friends1.entrySet().removeIf(element -> element.getValue().name.compareTo(name) > 0);
        if (c) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * Данный метод используется для того, чтобы вывести имена всех друзей, находящихся в коллекции.
     * На вход в данный метод подаётся коллекция, информацию о которой нам необходимо вывести.
     */
    public static String show() {
        friends1 = sortByValues(friends1);
        String result;
        if (friends1.isEmpty()) {
            result = "Коллекция пустая";
        } else {
            result = "На данный момент в коллекции находятся:";
            for (Map.Entry<String, Friend> entry : friends1.entrySet()){
                result = result + " " + entry.getValue().name;
            }
        }
        return result;
    }

    /**
     * Данный метод используется для того, чтобы импортировать всех друзей из JSON файла в коллекцию.
     * Чтобы выполнить эту команду, пользователю будет предложено ввети название переменной окружения
     * На вход в данный метод подаётся коллекция, в которую мы импортируем данные из файла.
     */


    /**
     * Данный метод используется для того, чтобы вывести информацию о коллекции.
     * На вход в данный метод подаётся коллекция, информацию о которой нам необходимо вывести.
     */
    public static String info() {
        return "В коллеции типа TreeMap хранятся данные о " + friends1.size() + " друзьях . Дата заполнения из файла json: " + date + " .Значениями являются экземпляры класса детей. Обратите внимание на то, что конкретному ребёнку соответствует конкретный ключ.";
    }


    /**
     * Данный метод используется для того, чтобы удалить из коллекции ребенка с конкретным ключом.
     * Чтобы выполнить эту операцию, пользователю будет предложено ввести ключ ребенка, которого он желает удалить.
     * На вход в данный метод подаётся коллекция, из которой происходит удаление.
     */
    public static boolean remove(String key) {
        boolean c;
        System.out.println("Введите ключ ребенка");
        c = friends1.entrySet().stream().anyMatch(fr -> fr.getKey().equals(key));
        if (c) {
            friends1.remove(key);
            return true;
        } else {
            return false;
        }
    }


    /**
     * Данный метод используется для того, чтобы удалить из коллекции друзей, ключ которых больше введенного.
     * Чтобы выполнить эту команду, пользователю будет предложено ввести ключ ребенка.
     * На вход в данный метод подаётся коллекция, из которой происходит удаление.
     */
    public static boolean remove_greater_key(String number,Map<String, Friend> ourMap) {
        long c;
        int c1 = ourMap.size();
        if (ourMap.isEmpty()) {
            return false;
        } else {
            int count = 0;
            if (!isNumber(number)) {
                return false;
            } else {
                c = ourMap.entrySet().stream()
                        .filter(x -> Integer.parseInt(x.getKey()) <= Integer.parseInt(number))
                        .count();
                if (c < c1) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    }


    public static boolean AddFromFile(ArrayList<String> strfr){
        countFriends = 0;
      int name;
      int carl;
      int ctw;
      int num;
      int dfs;
      for (String str: strfr){
          name=str.lastIndexOf("name");
          carl=str.lastIndexOf("Carlson");
          ctw=str.lastIndexOf("ChanceToWalk");
          num=str.lastIndexOf("number");
          dfs=str.lastIndexOf("DistanceFromSchool");
            date=new Date();
          Friend fr=new Friend(str.substring(name+7,carl-3),str.substring(carl+10,ctw-3),Double.parseDouble(str.substring(ctw+15,num-3)),str.substring(num+9,dfs-3),Double.parseDouble(str.substring(dfs+21,str.length()-3)),date);
          boolean c=false;
          c = friends1.entrySet().stream()
                  .anyMatch(x -> x.getKey().equals(fr.number));
          if (!c) {
              friends1.put(fr.number, fr);
              countFriends++;
          }
      }
      if (countFriends>0){
          return true;
      }else{
          return false;
      }

    }

    private static String KidsKey(String number) {
        boolean c;
        if (!isNumber(number)) {
            return "нет ключа";
        }
        c = friends1.entrySet().stream()
                .anyMatch(x -> x.getKey().equals(number));
        if (c) {
            return "нет ключа";
        }
        return number;
    }

    public static String Exchange(String str, DatagramSocket server, DatagramPacket incoming, InetAddress address, int port) {
        String s1 = null;
        DatagramPacket dp = new DatagramPacket(str.getBytes(), str.getBytes().length, address, port);
        try {
            server.send(dp);
            server.receive(incoming);
            byte[] data1 = incoming.getData();
            s1 = new String(data1, 0, incoming.getLength());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s1;
    }

    public static void Sending(String str, DatagramSocket server, DatagramPacket incoming) {
        try {
           // DatagramPacket Menu = new DatagramPacket("menu".getBytes(), 4, incoming.getAddress(), incoming.getPort());
          //  System.out.println(str);
            DatagramPacket dp2 = new DatagramPacket(str.getBytes(), str.getBytes().length, incoming.getAddress(), incoming.getPort());
            server.send(dp2);
          //  server.send(Menu);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static byte[] serialize(Object object) {
        if (object == null) {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            oos.flush();
        }
        catch (IOException ex) {
            throw new IllegalArgumentException("Failed to serialize object of type: " + object.getClass(), ex);
        }
        System.out.println(Arrays.toString(baos.toByteArray()));
        return baos.toByteArray();
    }


    public void run() {
        try {
            friends1 = new ConcurrentHashMap<String, Friend>() {
            };
            synchronized (friends1) {
                servers = new DatagramSocket(5555);
                try {
                    byte[] buffer = new byte[65536];
                    DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);


                    System.out.println("Ожидаем данные...");

                    servers.receive(incoming);
                    byte[] data = incoming.getData();
                    String s = new String(data, 0, incoming.getLength());
                    if (s.equals("I'm a client!")) {
                        System.out.println("Клиент подключен");
                        ServerThread1 th1 = new ServerThread1(servers, incoming, incoming.getAddress(), incoming.getPort());
                        new Thread(th1).start();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

               // BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
               // String exit = in.readLine();
               // if (exit.equalsIgnoreCase("exit")) {
                 //   servers.close();
               // }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
        }
