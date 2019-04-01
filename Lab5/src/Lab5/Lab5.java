package Lab5;

import com.sun.xml.internal.ws.server.ServerRtException;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.io.File;
import java.io.FileNotFoundException;
import java.net.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.concurrent.ConcurrentMap;


public class Lab5 {

    //объявление нашей будущей коллекции друзей
    static Map<String, Friend> friends1 = new HashMap<String, Friend>() {
    };
    static Scanner scan = new Scanner(System.in);
    static Date date;
    static int countFriends;


    //указание путь к json файлу через переменную окружения


    static Path path;
    static File file;

    //метод, чтобы прочитать json с помощью scanner(возвращает список, состоящий из json объектов)
    private static ArrayList<JSONObject> ReadJSON(File file) throws FileNotFoundException, ParseException {
        Scanner scanner = new Scanner(file);
        ArrayList<JSONObject> json = new ArrayList<JSONObject>();
        date = new Date();
        while (scanner.hasNext()) {
            JSONObject obj = (JSONObject) new JSONParser().parse(scanner.nextLine());
            json.add(obj);
        }
        scanner.close();
        return json;
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

 /*   private static void menu() {
        System.out.println("Выберите команду - введите число от 1 до 8:");
        System.out.println("1 - добавить нового друга по ключу");
        System.out.println("2 - удалить из коллекции друзей, превышающие заданные");
        System.out.println("3 - вывести в строковом представление всех друзей в коллекции");
        System.out.println("4 - добавить в коллекцию все данные из файла");
        System.out.println("5 - вывести информацию о коллекции");
        System.out.println("6 - удалить из коллекции друга по ключу");
        System.out.println("7 - удалить из коллекции друзей, ключ которых превышает заданный");
        System.out.println("8 - выход из меню (запуск программы)");
    }*/


    /**
     * Данный метод используется для того, чтобы добавить новый элемент коллекции по заданному ключу.
     * Для добавления элемента пользователю будет предложено ввести имя ребенка, "Карлсон", если ребенок знаком с Карлсоном, вероятность пойти с Малышом.
     * На вход в метод подаётся заранее проверенный на повторение ключ добавляемого ребёнка и коллекция, в которую ребёнок добавляется.
     */
   /* public static void insert(String key, Map<String, Friend> ourMap) {
        //Scanner sc = new Scanner(System.in);
        if (key.equals("нет ключа")) {
        } else {
            System.out.println("Введите имя ребенка");
            String name = scan.nextLine();
            if (!isLetters(name)) {
                System.out.println("Вы ввели некорректное имя.");
            } else {
                System.out.println("Введите \"Карлсон\", если ребёнок знаком с Карлсоном");
                String carl = scan.nextLine();

                System.out.println("Введите его возможность пойти с Малышом");
                String chance1 = scan.nextLine();
                if (!isNumber(chance1)) {
                    System.out.println("Вы ввели неккоректную возможность.");
                } else {
                    double chance = Double.parseDouble(chance1);
                    Friend newFriend = new Friend(name, carl, chance, key);
                    ourMap.put(key, newFriend);
                    System.out.println("Друг добавлен");
                }
            }
        }

    }*/
    public static boolean insert(String fr){
        try {String[] fr1=fr.split(",");
        String key=KidsKey(fr1[4]);
        if (!key.equals("нет ключа")||isLetters(fr1[0])||isNumber(fr1[2])||isNumber(fr1[3])){
            Friend newFriend=new Friend(fr1[0],fr1[1],Double.parseDouble(fr1[2]),key,Double.parseDouble(fr1[3]));
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
    public static void show() {
        friends1 = sortByValues(friends1);
        if (friends1.isEmpty()) {
            System.out.println("Коллекция пустая");
        } else {
            friends1.entrySet().stream().forEach(
                    (friend) -> System.out.println(friend.getValue().name)
            );
        }
    }

    /**
     * Данный метод используется для того, чтобы импортировать всех друзей из JSON файла в коллекцию.
     * Чтобы выполнить эту команду, пользователю будет предложено ввети название переменной окружения
     * На вход в данный метод подаётся коллекция, в которую мы импортируем данные из файла.
     */
    public static void imports(Map<String, Friend> newMap) {
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
    public static void info(Map<String, Friend> ourMap) {
        System.out.println("В коллеции типа TreeMap хранятся данные о " + ourMap.size() + " друзьях . Дата заполнения из файла json: " + date + " .Значениями являются экземпляры класса детей. Обратите внимание на то, что конкретному ребёнку соответствует конкретный ключ.");
    }


    /**
     * Данный метод используется для того, чтобы удалить из коллекции ребенка с конкретным ключом.
     * Чтобы выполнить эту операцию, пользователю будет предложено ввести ключ ребенка, которого он желает удалить.
     * На вход в данный метод подаётся коллекция, из которой происходит удаление.
     */
    public static void remove(Map<String, Friend> ourMap) {
        boolean c;
        if (ourMap.isEmpty()) {
            System.out.println("Коллекция пустая");
        } else {
            System.out.println("Введите ключ ребенка");
            String number = scan.nextLine();
            c = ourMap.entrySet().stream().anyMatch(fr -> fr.getKey().equals(number));
            if (c) {
                ourMap.remove(number);
                System.out.println("Удаление успешно завершено.");
            } else {
                System.out.println("Ребёнок с таким ключом не найден.");
            }
        }
    }


    /**
     * Данный метод используется для того, чтобы удалить из коллекции друзей, ключ которых больше введенного.
     * Чтобы выполнить эту команду, пользователю будет предложено ввести ключ ребенка.
     * На вход в данный метод подаётся коллекция, из которой происходит удаление.
     */
    public static void remove_greater_key(Map<String, Friend> ourMap) {
        long c;
        int c1 = ourMap.size();
        if (ourMap.isEmpty())
            System.out.println("Коллеция пуста");
        else {
            int count = 0;
            Scanner sc = new Scanner(System.in);
            System.out.println("Введите ключ ребенка");
            String number = sc.nextLine();
            if (!isNumber(number)) {
                System.out.println("Вы ввели некорректный ключ.");
            } else {
                c = ourMap.entrySet().stream()
                        .filter(x -> Integer.parseInt(x.getKey()) <= Integer.parseInt(number))
                        .count();
                if (c < c1) {
                    System.out.println("Удаление успешно завершено.");
                } else {
                    System.out.println("Нет друзей с ключом больше введенного.");
                }
            }
        }
    }


    private static Map AddFromFile(File file, Map<String, Friend> ourMap) throws FileNotFoundException, ParseException {
        countFriends = 0;
        ArrayList<JSONObject> jsons = ReadJSON(file);
        for (JSONObject obj : jsons) {
            Friend fr = new Friend((String) obj.get("name"), (String) obj.get("Carlson"), Double.parseDouble((String) obj.get("ChanceToWalk")), (String) obj.get("number"),Double.parseDouble((String) obj.get("DistanceFromSchool")));
            boolean c = false;
            c = ourMap.entrySet().stream()
                    .anyMatch(x -> x.getKey().equals(fr.number));
            if (!c) {
                ourMap.put(fr.number, fr);
                countFriends++;
            }
        }
        return ourMap;
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

    private static void WriteInFile(Map<String, Friend> ourMap) throws IOException, FileNotFoundException {
        FileWriter fstream1 = new FileWriter(path.toFile());// конструктор с одним параметром - для перезаписи
        BufferedWriter out1 = new BufferedWriter(fstream1); //  создаём буферезированный поток
        out1.write(""); // очищаем, перезаписав поверх пустую строку
        out1.close(); // закрываем

        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(path.toFile()));
        //String carl;
        /*for (Iterator<Map.Entry<String, Friend>> element = friends.entrySet().iterator(); element.hasNext(); ) {
            Map.Entry<String, Friend> it = element.next();
            if (it.getValue().MeetCarlson) {
                carl = "Карлсон";
            } else {
                carl = "не Карлсон";
            }
            String fr = "{\"name\": \"" + it.getValue().name + "\",\"Carlson\":\"" + carl + "\",\"ChanceToWalk\":\"" + it.getValue().ChanceToWalk + "\",\"number\":\"" + it.getValue().number + "\"}";
            stream.write(fr.getBytes());
            stream.write(System.lineSeparator().getBytes());
        }*/
        ourMap.entrySet().stream()
                .forEach(x -> {
                    if (x.getValue().MeetCarlson) {
                        String fr = "{\"name\": \"" + x.getValue().name + "\",\"Carlson\":\"" + "Карлсон" + "\",\"ChanceToWalk\":\"" + x.getValue().ChanceToWalk + "\",\"number\":\"" + x.getValue().number + "\"}";
                        try {
                            stream.write(fr.getBytes());
                            stream.write(System.lineSeparator().getBytes());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        String fr = "{\"name\": \"" + x.getValue().name + "\",\"Carlson\":\"" + "не Карлсон" + "\",\"ChanceToWalk\":\"" + x.getValue().ChanceToWalk + "\",\"number\":\"" + x.getValue().number + "\"}";
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
private static String Exchange(String str,DatagramSocket server,DatagramPacket incoming,InetAddress address,int port){
    String s1=null;
    DatagramPacket dp=new DatagramPacket(str.getBytes(),str.getBytes().length, address,port );
    try{server.send(dp);
    server.receive(incoming);
    byte[] data1 = incoming.getData();
    s1 = new String(data1, 0, incoming.getLength());
    }
    catch(IOException e){
        e.printStackTrace();
    }
    return s1;
}
private static void Sending(String str,DatagramSocket server,DatagramPacket incoming,InetAddress address,int port){
   try{ DatagramPacket Menu = new DatagramPacket("menu".getBytes(), 4, incoming.getAddress(), incoming.getPort());
    System.out.println(str);
    DatagramPacket dp2 = new DatagramPacket(str.getBytes(), str.getBytes().length, incoming.getAddress(), incoming.getPort());
    server.send(dp2);
    server.send(Menu);}
   catch(IOException e){
       e.printStackTrace();
   }
}
//static String menu="menu";
    public static void main(String[] args) throws IOException {

        /**
         *
         */
        friends1=new ConcurrentHashMap<String, Friend>() {};

        DatagramSocket servers = null;
        try {
            servers = new DatagramSocket(4444);
            byte[] buffer = new byte[65536];
            DatagramPacket incoming=new DatagramPacket(buffer, buffer.length);


            System.out.println("Ожидаем данные...");


            while (incoming!=null) {

                servers.receive(incoming);
                byte[] data = incoming.getData();
                String s = new String(data, 0, incoming.getLength());

                if (s.equalsIgnoreCase("exit")) break;
                System.out.println(s);

                    if (s.equals("1")){
                        String sout = "Введите через запятую информацию о добавляемом ребёнке: его имя,\"Карлсон\"(если он знаком с Карлсоном), вероятность пойти с Малышом, расстояние до школы и ключ. ";
                        String s1 = Exchange(sout, servers, incoming, incoming.getAddress(), incoming.getPort());
                        String sout1;
                        boolean b = insert(s1);
                        if (b) {
                            sout1 = "Друг добавлен";
                        } else {
                            sout1 = "Проверьте корректность введенных данных.";
                        }
                        Sending(sout1,servers,incoming,incoming.getAddress(),incoming.getPort());

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
                        Sending(sout3,servers,incoming,incoming.getAddress(),incoming.getPort());
                    }else if(s.equals("3")){}
                    else if (s.equals("4")){
                        //c этим траблы какие-то
                    }


            }

            }
        catch (IOException e) {
            System.out.println("ошибка(");
            System.exit(-1);
        }

        /*Socket fromclient = null;
        try {
            System.out.print("Waiting for a client...");
            fromclient = servers.accept();
            System.out.println("Client connected");
        } catch (IOException e) {
            System.out.println("Can't accept");
            System.exit(-1);
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(fromclient.getInputStream()));
        PrintWriter out = new PrintWriter(fromclient.getOutputStream(), true);

        String input, output;
        System.out.println("Wait for messages");
        while ((input = in.readLine()) != null) {
            if (input.equalsIgnoreCase("exit")) break;
            //out.println("S ::: "+input);
            System.out.println(input);
            while (!input.equals("8")) {
                if (input.equals("1")) {
                   // System.out.println("Введите ключ ребенка");
                    BufferedReader in1 = new BufferedReader(new InputStreamReader(fromclient.getInputStream()));
                    PrintWriter out1 = new PrintWriter(fromclient.getOutputStream(),true);
                    out1.println("Введите через запятую информацию о добавляемом ребёнке: его имя,\"Карлсон\"(если он знаком с Карлсоном), вероятность пойти с Малышом, ключ. ");
                    //boolean b=insert(in1.readLine(), friends1);
                    if (in1.readLine()!=null||insert(in1.readLine())){
                        out1.println("Друг добавлен");
                        out1.flush();
                    }else {
                        out1.println("Проверьте корректность введенных данных.");
                        out1.flush();
                    }
                    in1.close();

                }

                if (input.equals("2")) {
                    System.out.println("Введите имя ребёнка");
                    remove_greater(KidsName(), friends1);
                }
                if (input.equals("3")) {
                    show(friends1);
                }
                if (input.equals("4")) {
                    Map<String, Friend> newMap = friends1;
                    imports(newMap);
                }
                if (input.equals("5"))
                    info(friends1);
                if (input.equals("6")) {
                    remove(friends1);
                }
                if (input.equals("7")) {
                    remove_greater_key(friends1);
                }
                if (input.equals("8")) {
                    try {
                        WriteInFile(friends1);
                    } catch (FileNotFoundException e) {
                        System.out.println("Файл не найден :(");
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                out.close();
                in.close();
                fromclient.close();
                servers.close();*/


                /**
                 *
                 */



/*
        //само чтение json и добавление экземпляров друзей
        try {
            friends1 = AddFromFile(file,friends1);
        } catch (FileNotFoundException e) {
            System.out.println("Файл с друзьями не найден :(");
            System.exit(0);
        } catch (ParseException e) {
            //e.printStackTrace();
            System.out.println("Некорректные данные в файле. Проверьте, что данные записаны в формате JSON.");
            System.exit(0);
        }

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
        }

*/
            }
        }
    //}
//}