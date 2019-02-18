package Lab5;

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


public class Lab5 {

    //объявление нашей будущей коллекции друзей
    static Map<String, Friend> friends1 = new TreeMap<String, Friend>();
    static Scanner scan = new Scanner(System.in);
    static Date date;
    //указание путь к json файлу через переменную окружения
    static Path path = Paths.get(System.getenv("Friendss"));

    static File file= path.toFile();

    //метод, чтобы прочитать json с помощью scanner(возвращает список, состоящий из json объектов)
    private static ArrayList<JSONObject> ReadJSON(File file) throws FileNotFoundException, ParseException {
        Scanner scanner = new Scanner(file);
        ArrayList<JSONObject> json = new ArrayList<JSONObject>();
        date= new Date();
        while (scanner.hasNext()) {
            JSONObject obj = (JSONObject) new JSONParser().parse(scanner.nextLine());
            json.add(obj);
        }
        scanner.close();
        return json;
    }
    private static Friend[] MakeArray(){
        //создание массива друзей на основе коллекции(чтоб не переписывать большую часть кода, которая была расчитана на массив)
        Friend[] friends = new Friend[(friends1.size())];
        int i = 0;
        for (Map.Entry<String, Friend> e : friends1.entrySet()) {
            friends[i] = e.getValue();
            i = i + 1;
        }
        return friends;
    }
    private static boolean isNumber(String str){
        try{
            double d =Double.parseDouble(str);
        }catch(NumberFormatException e){
            return false;
        }
        return true;
    }
    private static boolean isLetters(String str){
        char[] chars=str.toCharArray();
        for (char c: chars){
            if (!Character.isLetter(c)){
                return false;
            }
        }
        return true;
    }


    private static void menu() {
        System.out.println("Выберите команду");
        System.out.println("1 - добавить нового друга по ключу");
        System.out.println("2 - удалить из коллекции друзей, превышающие заданные");
        System.out.println("3 - вывести в строковом представление всех друзей в коллекции");
        System.out.println("4 - добавить в коллекцию все данные из файла");
        System.out.println("5 - вывести информацию о коллекции");
        System.out.println("6 - удалить из коллекции друга по ключу");
        System.out.println("7 - удалить из коллекции друзей, ключ которых превышает заданный");
        System.out.println("8 - выход из меню (запуск программы)");
    }

    //метод для добавления в коллекцию элемента с заданным ключом
    /** add new element with a concrete key */
    public static void insert(String key, Map<String, Friend> ourMap) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите имя ребенка");
        String name=sc.nextLine();
        while (!isLetters(name)){
            System.out.println("Вы ввели некорректное имя. Введите заново:");
            name=sc.nextLine();
        }
        System.out.println("Введите \"Карлсон\", если ребёнок знаком с Карлсоном");
        String carl = sc.nextLine();

        System.out.println("Введите его возможность пойти с Малышом(через запятую)");
        String chance1 = sc.nextLine();
        while (!isNumber(chance1)){
            System.out.println("Вы ввели неккоректную возможность. Введите заново:");
            chance1=sc.nextLine();
        }
        double chance=Double.parseDouble(chance1);

        Friend newFriend = new Friend(name, carl, chance, key);
        ourMap.put(key, newFriend);
        System.out.println("Друг добавлен");
    }

    //метод, удаляющий из коллекции элементы, превышающий заданный
    /** remove all elements bigger than a concrete element*/
    public static void remove_greater(Map<String, Friend> ourMap) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите строку JSON");
        String str=sc.nextLine();
        
        /*try {
            scan = new Scanner(file);
            while (scan.hasNextLine()){
                String st=scan.nextLine();
                if (str.equals(st)){
                    break;
                }else{

                }
            }
        }
        System.out.println("Ребенок успешно удален из коллекции");*/
    }


    //метод, выводящий элементы коллекции
    /** show all elements*/
    public static void show(Map<String, Friend> ourMap) {
        for (Map.Entry<String, Friend> element : ourMap.entrySet()) {
            System.out.println(element.getValue().name);
        }
    }

    //метод, добавляющий элементы из файла в коллекцию
    /** import all elements from file*/
    public static void imports(String path){
        File file= new File(path);
        try {
            friends1=AddFromFile(file, (TreeMap)friends1);
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден :(");

        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("Все друзья из файла успешно добавлены");
    }

    //метод, выводящий информацию о коллекции
    /** show information about collection */
    public static void info(Map<String, Friend> ourMap){
        System.out.println("В коллеции типа TreeMap хранятся данные о " + ourMap.size()+" друзьях . Дата заполнения из файла json: "+date+" .Значениями являются экхемпляры класса детей.");
    }

    //метод, удаляющий элемент по ключу
    /**  remove element with a concrete key*/
    public static void remove(Map<String, Friend> ourMap) {
        scan.nextLine();
        System.out.println("Введите ключ ребенка");
        String number = scan.nextLine();
        Friend fr=ourMap.remove(number);
        while (fr==null||!isNumber(number)) {
            System.out.println("Ребёнок с таким ключом не найден. Введите ключ заново:");
            number=scan.nextLine();
            fr=ourMap.remove(number);
        }
        System.out.println("Удаление успешно завершено.");
    }

    //метод, удаляющий все элементы, ключ которых превышает заданный
    /** remove all elements with a key bigger than a concrete key*/
    public static void remove_greater_key(Map<String, Friend> ourMap) {
        int count=0;
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите ключ ребенка");
        String number = sc.nextLine();
        while (!isNumber(number)) {
            System.out.println("Вы ввели некорректный ключ. Введите заново:");
            number=sc.nextLine();
        }
        for (Iterator<Map.Entry<String, Friend>> element = ourMap.entrySet().iterator(); element.hasNext();) {
            Map.Entry<String, Friend> it = element.next();
            if (Integer.parseInt(number) < Integer.parseInt(it.getKey())){
                element.remove();
                count++;
        }}
        if (count!=0){
        System.out.println("Удаление успешно завершено.");}
        else{
            System.out.println("Нет друзей с ключом больше введенного.");
        }
    }


    private static TreeMap AddFromFile(File file, TreeMap friends1) throws FileNotFoundException, ParseException{
        ArrayList<JSONObject> jsons = ReadJSON(file);
        for (JSONObject obj : jsons) {
            Friend fr = new Friend((String) obj.get("name"), (String) obj.get("carlson"), Double.parseDouble((String) obj.get("ChanceToWalk")), (String) obj.get("number"));
            friends1.put((String) obj.get("number"), fr);
        }
        return friends1;
    }

    private static String KidsKey(){
        String number = scan.nextLine();
        while (!isNumber(number)) {
            System.out.println("Вы ввели некорректный ключ или ребёнок с таким ключом уже существует. Введите заново:");
            number= scan.nextLine();
        }
        for (Iterator<Map.Entry<String, Friend>> element = friends1.entrySet().iterator(); element.hasNext();) {
            Map.Entry<String, Friend> it = element.next();
            if (Integer.parseInt(number)==Integer.parseInt(it.getKey())){
                System.out.println("Вы ввели некорректный ключ или ребёнок с таким ключом уже существует. Введите заново:");
                KidsKey();
            }
        }
        return number;
    }

    public static void main(String[] args) {


        Friend[] friends=MakeArray();


        //само чтение json и добавление экземпляров друзей
        try {
            friends1=AddFromFile(file, (TreeMap)friends1);
        } catch (FileNotFoundException e) {
            System.out.println("Файл с друзьями не найден :(");

        } catch (ParseException e) {
            e.printStackTrace();
        }




        //консольное приложение
        menu();
        int a = scan.nextInt();
        while (a != 8) {
            if (a == 1){
                scan.nextLine();
                System.out.println("Введите ключ ребенка");
                insert(KidsKey(), friends1);}
            if (a == 2)
                remove_greater(friends1);
            if (a==3)
                show(friends1);
            if (a==4)
                imports(path.toString());
            if (a==5)
                info(friends1);
            if (a==6)
                remove(friends1);
            if (a==7){
                remove_greater_key(friends1);
            }
            menu();
            a = scan.nextInt();
        }
        if (a==8){
            try {
                FileWriter fstream1 = new FileWriter(path.toFile());// конструктор с одним параметром - для перезаписи
                BufferedWriter out1 = new BufferedWriter(fstream1); //  создаём буферезированный поток
                out1.write(""); // очищаем, перезаписав поверх пустую строку
                out1.close(); // закрываем

                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(path.toFile()));
                friends=MakeArray();
                for (int j = 0; j < friends.length; j++) {
                    String carl;
                    if (friends[j].MeetCarlson){
                        carl="Карлсон";
                    }else{
                        carl="не Карлсон";
                    }
                    String fr= "{\"name\": \""+friends[j].name+"\",\"Carlson\":\""+carl+"\",\"ChanceToWalk\":\""+friends[j].ChanceToWalk+"\",\"number\":\""+friends[j].number+"\"}";
                    stream.write(fr.getBytes());
                    stream.write(System.lineSeparator().getBytes());
                }
                stream.close();
            } catch (FileNotFoundException e) {
                System.out.println("Файл не найден :(");
                e.printStackTrace();
            } catch (IOException e){
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