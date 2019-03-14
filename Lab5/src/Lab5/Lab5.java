package Lab5;

import com.sun.xml.internal.ws.server.ServerRtException;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;


public class Lab5 {

    //объявление нашей будущей коллекции друзей
    static Map<String, Friend> friends1 = new TreeMap<String, Friend>();
    static Scanner scan = new Scanner(System.in);
    static Date date;
    static int countFriends;

    //указание путь к json файлу через переменную окружения


    static Path path;
    static File file;

    //метод, чтобы прочитать json с помощью scanner(возвращает список, состоящий из json объектов)
    private static ArrayList<JSONObject> ReadJSON(File file) throws FileNotFoundException, ParseException{
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
            return  fr1.compareTo(fr2);
        });
        Map<K, V> sortedMap = new LinkedHashMap<K, V>();

        for (Map.Entry<K, V> entry : entries) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
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


    /**
     * Данный метод используется для того, чтобы добавить новый элемент коллекции по заданному ключу.
     * Для добавления элемента пользователю будет предложено ввести имя ребенка, "Карлсон", если ребенок знаком с Карлсоном, вероятность пойти с Малышом.
     * На вход в метод подаётся заранее проверенный на повторение ключ добавляемого ребёнка и коллекция, в которую ребёнок добавляется.
     */
    public static void insert(String key, Map<String, Friend> ourMap) {
        //Scanner sc = new Scanner(System.in);
        if (key.equals("нет ключа")) {
        } else {
            System.out.println("Введите имя ребенка");
            String name = scan.nextLine();
            if (!isLetters(name)) {
                System.out.println("Вы ввели некорректное имя.");
                //name = sc.nextLine();
            } else {
                System.out.println("Введите \"Карлсон\", если ребёнок знаком с Карлсоном");
                String carl = scan.nextLine();

                System.out.println("Введите его возможность пойти с Малышом");
                String chance1 = scan.nextLine();
                if (!isNumber(chance1)) {
                    System.out.println("Вы ввели неккоректную возможность.");
                    //chance1 = scan.nextLine();
                } else {
                    double chance = Double.parseDouble(chance1);
                    Friend newFriend = new Friend(name, carl, chance, key);
                    ourMap.put(key, newFriend);
                    System.out.println("Друг добавлен");
                }
            }
        }
    }
    /**
     * Данный метод используется для того, чтобы удалить из коллекции друзей, имена которых по алфавиту позже введенного имени, если ребенок с введенным именем имеется.
     * Чтобы совершить данную команду, пользователю будет предложено ввести имя ребенка.
     * На вход в данный метод подаётся имя ребёнка, и коллекция, из которой происходит удаление.
     */
    public static void remove_greater(String name, Map<String, Friend> ourMap) {
        int count=0;
        /*for (Iterator<Map.Entry<String, Friend>> element = ourMap.entrySet().iterator(); element.hasNext(); ) {
            Map.Entry<String, Friend> it = element.next();
            if (it.getValue().name.compareTo(name) > 0) {
                element.remove();
                count++;
            }
        }*/
        Iterator<Map.Entry<String, Friend>> it= ourMap.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String,Friend> element=it.next();
            if (element.getValue().name.compareTo(name)>0){
                it.remove();
                count++;
            }
        }
        if (count != 0) {
            System.out.println("Удаление успешно завершено.");
        } else {
            System.out.println("Детей с именем больше заданного нет.");
        }
    }


    /**
     * Данный метод используется для того, чтобы вывести имена всех друзей, находящихся в коллекции.
     * На вход в данный метод подаётся коллекция, информацию о которой нам необходимо вывести.
     */
    public static void show(Map<String, Friend> ourMap) {
        ourMap = sortByValues(ourMap);
        if (ourMap.isEmpty()){
            System.out.println("Коллекция пустая");
        }else {
            ourMap.forEach(
                    (s, friend) -> System.out.println(friend.name)
            );
        }
    }

    /**
     * Данный метод используется для того, чтобы импортировать всех друзей из JSON файла в коллекцию.
     * Чтобы выполнить эту команду, пользователю будет предложено ввети название переменной окружения
     * На вход в данный метод подаётся коллекция, в которую мы импортируем данные из файла.
     */
    public static void imports(Map<String,Friend> newMap) {
        try {
            friends1=AddFromFile(file,friends1);
            if (countFriends==0){
                System.out.println("В коллекцию ничего не добавлено.");
            }else{
                System.out.println("Добавление успешно завершено. В коллекцию добавлено "+countFriends+" друзей.");
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
        if (ourMap.isEmpty()) {
            System.out.println("Коллекция пустая");
        } else {
            System.out.println("Введите ключ ребенка");
            String number = scan.nextLine();
            Friend fr = ourMap.remove(number);
            if (fr == null || !isNumber(number)) {
                System.out.println("Ребёнок с таким ключом не найден.");
            } else {
                System.out.println("Удаление успешно завершено.");
            }
        }
    }


    /**
     * Данный метод используется для того, чтобы удалить из коллекции друзей, ключ которых больше введенного.
     * Чтобы выполнить эту команду, пользователю будет предложено ввести ключ ребенка.
     * На вход в данный метод подаётся коллекция, из которой происходит удаление.
     */
    public static void remove_greater_key(Map<String, Friend> ourMap) {
        if (ourMap.isEmpty())
            System.out.println("Коллеция пуста");
        else {
            int count = 0;
            Scanner sc = new Scanner(System.in);
            System.out.println("Введите ключ ребенка");
            String number = sc.nextLine();
            if (!isNumber(number)) {
                System.out.println("Вы ввели некорректный ключ.");
                //number=sc.nextLine();
            } else {
                for (Iterator<Map.Entry<String, Friend>> element = ourMap.entrySet().iterator(); element.hasNext(); ) {
                    Map.Entry<String, Friend> it = element.next();
                    if (Integer.parseInt(number) < Integer.parseInt(it.getKey())) {
                        element.remove();
                        count++;
                    }
                }
                if (count != 0) {
                    System.out.println("Удаление успешно завершено.");
                } else {
                    System.out.println("Нет друзей с ключом больше введенного.");
                }
            }
        }
    }


    private static Map AddFromFile(File file, Map<String,Friend> ourMap) throws FileNotFoundException, ParseException{
        countFriends=0;
        ArrayList<JSONObject> jsons = ReadJSON(file);
        for (JSONObject obj : jsons) {
            Friend fr = new Friend((String) obj.get("name"), (String) obj.get("Carlson"), Double.parseDouble((String) obj.get("ChanceToWalk")), (String) obj.get("number"));
            boolean c=false;
            for (Iterator<Map.Entry<String, Friend>> element = ourMap.entrySet().iterator(); element.hasNext(); ) {
                Map.Entry<String, Friend> it = element.next();
                if (fr.number.equals(it.getKey())){
                c=true;
                break;}
            }
             if (!c){
                 ourMap.put(fr.number, fr);
                 countFriends++;
             }
        }
        return ourMap;
    }


    private static String KidsKey() {
        String number = scan.nextLine();
        if (!isNumber(number)) {
            System.out.println("Вы ввели некорректный ключ или ребёнок с таким ключом уже существует.");
            //number = scan.nextLine();
            return "нет ключа";
        }
        for (Iterator<Map.Entry<String, Friend>> element = friends1.entrySet().iterator(); element.hasNext(); ) {
            Map.Entry<String, Friend> it = element.next();
            if (number.equals( it.getKey())) {
                System.out.println("Вы ввели некорректный ключ или ребёнок с таким ключом уже существует.");
                return "нет ключа";
            }
        }
        return number;
    }

    private static String KidsName() {
        String name = scan.nextLine();
        boolean name1 = false;
        for (Iterator<Map.Entry<String, Friend>> element = friends1.entrySet().iterator(); element.hasNext(); ) {
            Map.Entry<String, Friend> it = element.next();
            if (name.equals(it.getValue().name)) {
                name1 = true;
                break;
            }
        }
        if (!name1) {
            System.out.println("Вы ввели некорректное имя или ребёнка с таким именем не существует.");
            //menu();
        }
        return name;
    }
    private static void WriteInFile(Map<String,Friend> friends) throws IOException,FileNotFoundException{
        FileWriter fstream1 = new FileWriter(path.toFile());// конструктор с одним параметром - для перезаписи
        BufferedWriter out1 = new BufferedWriter(fstream1); //  создаём буферезированный поток
        out1.write(""); // очищаем, перезаписав поверх пустую строку
        out1.close(); // закрываем

        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(path.toFile()));
        String carl;
        for (Iterator<Map.Entry<String, Friend>> element = friends.entrySet().iterator(); element.hasNext(); ) {
            Map.Entry<String, Friend> it = element.next();
            if (it.getValue().MeetCarlson) {
                carl = "Карлсон";
            } else {
                carl = "не Карлсон";
            }
            String fr = "{\"name\": \"" + it.getValue().name + "\",\"Carlson\":\"" + carl + "\",\"ChanceToWalk\":\"" + it.getValue().ChanceToWalk + "\",\"number\":\"" + it.getValue().number + "\"}";
            stream.write(fr.getBytes());
            stream.write(System.lineSeparator().getBytes());
        }
        stream.close();
        }


    public static void main(String[] args) {
        try{
        path = Paths.get(System.getenv("Friendss"));
        file = path.toFile();}
        catch (NullPointerException e){
            System.out.println("Проверьте переменную окружения.");
            System.exit(0);
        }

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


    }
}
