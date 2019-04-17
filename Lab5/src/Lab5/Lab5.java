package Lab5;

import Client.Friend;
import org.apache.commons.lang.SerializationUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.*;
import java.nio.file.Path;
import java.util.*;


import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;


public class Lab5 {

    //объявление нашей будущей коллекции друзей
    static Map<String, Friend> friends1 = new HashMap<String, Friend>() {
    };
    static Scanner scan = new Scanner(System.in);
    static Date date;
    static int countFriends;
    static ArrayList<String> StrFriends= new ArrayList<String>();


    //указание путь к json файлу через переменную окружения


    static Path path;
    static File file;

    //метод, чтобы прочитать json с помощью scanner(возвращает список, состоящий из json объектов)
/*    private static ArrayList<JSONObject> ReadJSON(File file) throws FileNotFoundException, ParseException {
        Scanner scanner = new Scanner(file);
        ArrayList<JSONObject> json = new ArrayList<JSONObject>();
        date = new Date();
        while (scanner.hasNext()) {
            JSONObject obj = (JSONObject) new JSONParser().parse(scanner.nextLine());
            json.add(obj);
        }
        scanner.close();
        return json;
    }*/


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


    private static <K extends Comparable, V extends Comparable> Map<K, V> sortByValues(Map<K, V> map) {
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
  /*  public static void imports(Map<String, Friend> newMap) {
        try {
            friends1 = AddFromFile(file, friends1);
            if (countFriends == 0) {
                System.out.println("В коллекцию ничего не добавлено.");
            } else {
                System.out.println("Добавление успешно завершено. В коллекцию добавлено " + countFriends + " друзей.");
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден :(");

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


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


    private static boolean AddFromFile(ArrayList<String> strfr){
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
        // String number = scan.nextLine();
        if (!isNumber(number)) {
            // System.out.println("Вы ввели некорректный ключ или ребёнок с таким ключом уже существует.");
            return "нет ключа";
        }
        c = friends1.entrySet().stream()
                .anyMatch(x -> x.getKey().equals(number));
        if (c) {
            // System.out.println("Вы ввели некорректный ключ или ребёнок с таким ключом уже существует.");
            return "нет ключа";
        }
        return number;
    }

    private static String KidsName() {
        String name = scan.nextLine();
        boolean name1 = false;
        name1 = friends1.entrySet().stream()
                .anyMatch(x -> x.getValue().name.equals(name));
        if (!name1) {
            System.out.println("Вы ввели некорректное имя или ребёнка с таким именем не существует.");
        }
        return name;
    }



    private static String Exchange(String str, DatagramSocket server, DatagramPacket incoming, InetAddress address, int port) {
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

    private static void Sending(String str, DatagramSocket server, DatagramPacket incoming, InetAddress address, int port) {
        try {
            DatagramPacket Menu = new DatagramPacket("menu".getBytes(), 4, incoming.getAddress(), incoming.getPort());
            System.out.println(str);
            DatagramPacket dp2 = new DatagramPacket(str.getBytes(), str.getBytes().length, incoming.getAddress(), incoming.getPort());
            server.send(dp2);
            server.send(Menu);

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


    public static void main(String[] args) throws IOException {

        /**
         *
         */
        friends1 = new ConcurrentHashMap<String, Friend>() {
        };

        DatagramSocket servers = null;
        try {
            servers = new DatagramSocket(4444);
            byte[] buffer = new byte[65536];
            DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);


            System.out.println("Ожидаем данные...");


            while (incoming != null) {

                servers.receive(incoming);
                byte[] data = incoming.getData();
                String s = new String(data, 0, incoming.getLength());

                if (s.equalsIgnoreCase("exit")) break;
                System.out.println("Клиент выбрал команду " + s + ". Ожидаем выполнения...");

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
                    Sending(sout1, servers, incoming, incoming.getAddress(), incoming.getPort());

                        }

                    else if(s.equals("2")){
                        String sout2 = "Введите имя ребёнка";
                         String s2 = Exchange(sout2, servers, incoming, incoming.getAddress(), incoming.getPort());
                        boolean b2=remove_greater(s2);
                        String sout3;
                        if (b2){
                            sout3="Удаление успешно завершено.";
                        } else {
                            sout3="Детей с именем больше заданного нет.";
                        }
                        Sending(sout3,servers,incoming,incoming.getAddress(),incoming.getPort());}
                    else if(s.equals("3")){
                        Sending(show(), servers, incoming, incoming.getAddress(), incoming.getPort());
                    }
                    else if (s.equals("4")){
                        while (true){
                            servers.receive(incoming);
                             byte[] data1 = incoming.getData();
                             String s1 = new String(data1, 0, incoming.getLength());
                             System.out.println(s1);
                             if(!s1.equals("EndSending")){
                                 StrFriends.add(s1);}
                             else{
                            break;}
                        }
                        boolean b4=AddFromFile(StrFriends);
                        friends1=sortByValues(friends1);
                        friends1.entrySet().stream().forEach(
                                (friend) -> System.out.println(friend.getValue().name)
                        );
                        String imp;
                        if (b4){
                            imp="Добавление успешно завершено. В коллекцию добавлено " + countFriends + " друзей.";}else{
                            imp="В коллекцию ничего не добавлено";
                        }
                    Sending(imp,servers,incoming,incoming.getAddress(),incoming.getPort());

                    }
                    else if(s.equals("5")){
                        Sending(info(),servers,incoming,incoming.getAddress(),incoming.getPort());}
                    else if(s.equals("6")) {
                        String sout6;
                        if (friends1.isEmpty()) {
                            sout6="Коллекция пустая";
                        } else {
                            String sout7="Введите ключ ребенка:";
                            String s6=Exchange(sout7,servers,incoming,incoming.getAddress(),incoming.getPort());
                            boolean b6=remove(s6);
                            if (b6){
                                sout6="Удаление успешно завершено.";
                            }else{
                                sout6="Ребёнок с таким ключом не найден.";
                            }
                        }
                        Sending(sout6,servers,incoming,incoming.getAddress(),incoming.getPort());
                    } else if(s.equals("7")) {
                String sout6 = "Введите ключ ребенка";
                String s6 = Exchange(sout6, servers, incoming, incoming.getAddress(), incoming.getPort());
                boolean b6 = remove_greater_key(s6, friends1);
                String sout7;
                if (b6) {
                    sout7 = "Удаление успешно завершено.";
                } else {
                    sout7 = "Возникла ошибка(Коллекция пуста, некорректный ключ или нет детей с ключом, выше заданного)";
                }
                Sending(sout7, servers, incoming, incoming.getAddress(), incoming.getPort());
            }else if(s.equals("8")){
                        try {
                            for (Map.Entry<String, Friend> entry : friends1.entrySet()){
                              /*  ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                ObjectOutputStream oos = new ObjectOutputStream(baos);
                                    oos.writeObject(entry.getValue());
                                    byte[] Buf = baos.toByteArray();*/
                              //byte [] Buf=serialize(entry.getValue());
                                byte[] Buf = SerializationUtils.serialize(entry.getValue());
                                    DatagramPacket packet = new DatagramPacket(Buf, Buf.length,incoming.getAddress(), incoming.getPort());
                                    servers.send(packet);
                                    System.out.println(entry.getValue().name+" отправлен(а)");

                                //oos.flush();
                                //oos.close();
                            }

                        String end="End Sending";
                        DatagramPacket endSending= new DatagramPacket(end.getBytes(), end.getBytes().length,incoming.getAddress(), incoming.getPort());
                        servers.send(endSending);
                        System.out.println(end);

                        }catch(IOException e){
                            e.printStackTrace();
                        }

                }
            }
            }
        catch (IOException e) {
            System.out.println("ошибка(");
            System.exit(-1);
        }
                /**
                 *
                 */



/*
        Friend[] friends = MakeArray();

        //консольное приложение
        menu();

        String a = scan.nextLine();
        while (!a.equals("8")) {
            if (a.equals("1")) {
                System.out.println("Введите ключ ребенка");
                insert(KidsKey(), friends1);
            }
            if (a.equals("2")) {
                System.out.println("Введите имя ребёнка");
                remove_greater(KidsName(), friends1);
            }
            if (a.equals("3")){

                show(friends1);}
            if (a.equals("4")){
                Map<String,Friend> newMap=friends1;
                imports(newMap);}
            if (a.equals("5"))
                info(friends1);
            if (a.equals("6")){
                remove(friends1);}
            if (a.equals("7")) {
                remove_greater_key(friends1);
            }
            menu();
            a = scan.nextLine();
            if (a.equals("8")) {
                try {
                    WriteInFile(friends1);
                } catch (FileNotFoundException e) {
                    System.out.println("Файл не найден :(");
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
*/servers.close();
            }
        }
