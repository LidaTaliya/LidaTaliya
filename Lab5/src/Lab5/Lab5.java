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
import java.util.stream.IntStream;


public class Lab5 {

    //объявление нашей будущей коллекции друзей
    static Map<String, Friend> friends1 = new TreeMap<String, Friend>();
    static Scanner scan = new Scanner(System.in);
    static Date date;

    //указание путь к json файлу через переменную окружения
    static Path path = Paths.get(System.getenv("Friendss"));

    static File file = path.toFile();

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
        //создание массива друзей на основе коллекции(чтоб не переписывать большую часть кода, которая была расчитана на массив)
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


    private static <K extends Comparable, V extends Comparable> Map<K, V> sortByValues(TreeMap<K, V> map) {
        List<Map.Entry<K, V>> entries = new LinkedList<Map.Entry<K, V>>(map.entrySet());

        Collections.sort(entries, new Comparator<Map.Entry<K, V>>() {

            @Override
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                Friend fr1 = (Friend) o1.getValue();
                Friend fr2 = (Friend) o2.getValue();
                return fr1.name.compareTo(fr2.name);
            }
        });
        Map<K, V> sortedMap = new LinkedHashMap<K, V>();

        for (Map.Entry<K, V> entry : entries) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

   /* private static boolean checkPath(ArrayList<String> arrayOfPaths, String newPath) {
        boolean result = true;
        for (String path : arrayOfPaths) {
            if (path.equals(newPath)) {
                System.out.println("Вы уже использовали данный файл. В коллекцию ничего не добавлено.");
                result = false;
            }
        }
        if (result)
            arrayOfPaths.add(newPath);
        return result;
    }*/


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
     * Чтобы совершить данную команду, пользователю будет предложено ввести имя ребенка
     */
    public static void remove_greater(String name, Map<String, Friend> ourMap) {
        int count = 0;
        for (Iterator<Map.Entry<String, Friend>> element = ourMap.entrySet().iterator(); element.hasNext(); ) {
            Map.Entry<String, Friend> it = element.next();
            if (it.getValue().name.compareTo(name) > 0) {
                element.remove();
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
     */
    public static void show(Map<String, Friend> ourMap) {
        if (ourMap.isEmpty()){
            System.out.println("Коллекция пустая");
        }else {
        for (Map.Entry<String, Friend> element : ourMap.entrySet()) {
            System.out.println(element.getValue().name);
        }
        }
    }

    private static boolean ContainsCollections(Map<String,Friend> MapFromFile, Map<String,Friend> MapFromProgram){
        String [] ArrayFromFile= new String[MapFromFile.size()];
        String [] ArrayFromProgram=new String[MapFromProgram.size()];
        int i=0;
        int i2=0;
        for (Iterator<Map.Entry<String, Friend>> element = MapFromFile.entrySet().iterator(); element.hasNext(); ) {
            Map.Entry<String, Friend> it1 = element.next();
            ArrayFromFile[i]=it1.getKey();
            i++;
        }
        for (Iterator<Map.Entry<String, Friend>> element = MapFromProgram.entrySet().iterator(); element.hasNext(); ) {
            Map.Entry<String, Friend> it2 = element.next();
            ArrayFromProgram[i2]=it2.getKey();
            i2++;
        }
        Set<String> FromFile = new HashSet<>(Arrays.asList(ArrayFromFile));
        Set<String> FromProgram = new HashSet<>(Arrays.asList(ArrayFromProgram));
        if (FromFile.containsAll(FromProgram)){
            return true;
        }else{
            return false;
        }

    }

    /**
     * Данный метод используется для того, чтобы импортировать всех друзей из JSON файла в коллекцию.
     * Чтобы выполнить эту команду, пользователю будет предложено ввети название переменной окружения
     */
    //public static void imports(String newPath) {
    public static void imports(Map<String,Friend> newMap) {
        //Path ourNewPath = Paths.get(System.getenv(newPath));
        //File file = ourNewPath.toFile();
        try {
            //friends1 = AddFromFile(file,friends1);
            /*if (friends1.isEmpty()){
                System.out.println();
            }*/
            if (ContainsCollections(AddFromFile(file,friends1),newMap)){
                System.out.println("В коллекцию ничего не добавлено.");
            }else {
                System.out.println("Добавление успешно завершено.");
            }
            friends1=AddFromFile(file,friends1);
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден :(");

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }



    /**
     * Данный метод используется для того, чтобы вывести информацию о коллекции.
     */
    public static void info(Map<String, Friend> ourMap) {
        System.out.println("В коллеции типа TreeMap хранятся данные о " + ourMap.size() + " друзьях . Дата заполнения из файла json: " + date + " .Значениями являются экземпляры класса детей. Обратите внимание на то, что конкретному ребёнку соответствует конкретный ключ.");
    }


    /**
     * Данный метод используется для того, чтобы удалить из коллекции ребенка с конкретным ключом.
     * Чтобы выполнить эту операцию, пользователю будет предложено ввести ключ ребенка, которого он желает удалить
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
     * Чтобы выполнить эту команду, пользователю будет предложено ввести ключ ребенка
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
             if (!c) ourMap.put((String)obj.get("number"), fr);
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
                // KidsKey();
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

    public static void main(String[] args) {

       /* ArrayList<String> usedPaths = new ArrayList<String>();
        String firstPath = "Friendss";
        usedPaths.add(firstPath);*/

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

        friends1 = sortByValues((TreeMap) friends1);
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
                scan.nextLine();
                System.out.println("Введите имя ребёнка");
                remove_greater(KidsName(), friends1);
            }
            if (a.equals("3")){
                //friends1 = sortByValues((TreeMap) friends1);
                show(friends1);}
            if (a.equals("4")){
                /*System.out.println("Введите название переменной окружения");
                String newPath=" ";
                try {
                    newPath = scan.next();
                } catch (NullPointerException e){
                    System.out.println("Вы ввели неккоректное название переменной окружения.");
                }
                if (checkPath(usedPaths, newPath)) {
                    imports(newPath);
                    System.out.println("Все друзья из файла успешно добавлены");
                }}*/
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
                        FileWriter fstream1 = new FileWriter(path.toFile());// конструктор с одним параметром - для перезаписи
                        BufferedWriter out1 = new BufferedWriter(fstream1); //  создаём буферезированный поток
                        out1.write(""); // очищаем, перезаписав поверх пустую строку
                        out1.close(); // закрываем

                        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(path.toFile()));
                        friends = MakeArray();
                        for (int j = 0; j < friends.length; j++) {
                            String carl;
                            if (friends[j].MeetCarlson) {
                                carl = "Карлсон";
                            } else {
                                carl = "не Карлсон";
                            }
                            String fr = "{\"name\": \"" + friends[j].name + "\",\"Carlson\":\"" + carl + "\",\"ChanceToWalk\":\"" + friends[j].ChanceToWalk + "\",\"number\":\"" + friends[j].number + "\"}";
                            stream.write(fr.getBytes());
                            stream.write(System.lineSeparator().getBytes());
                        }
                        stream.close();
                    } catch (FileNotFoundException e) {
                        System.out.println("Файл не найден :(");
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    //System.out.println("Изменения в коллекции друзей успешно сохранены в файл.");

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
