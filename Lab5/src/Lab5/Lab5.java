package Lab5;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;


public class Lab5 {
    //объявление нашей будущей коллекции друзей(за ключ берём возраст друга)
    static Map<String, Friend> friends1 = new TreeMap<String, Friend>();
    //метод, чтобы прочитать json с помощью scanner(возвращает список, состоящий из json объектов)
    public static ArrayList<JSONObject> ReadJSON(File file) throws FileNotFoundException, ParseException {
        Scanner scanner = new Scanner(file);
        ArrayList<JSONObject> json = new ArrayList<JSONObject>();
        while (scanner.hasNext()) {
            JSONObject obj = (JSONObject) new JSONParser().parse(scanner.nextLine());
            json.add(obj);
        }
        scanner.close();
        return json;
    }
    public static Friend[] MakeArray(){
        //создание массива друзей на основе коллекции(чтоб не переписывать большую часть кода, которая была расчитана на массив)
        Friend[] friends = new Friend[(friends1.size())];
        int i = 0;
        for (Map.Entry<String, Friend> e : friends1.entrySet()) {
            friends[i] = e.getValue();
            i = i + 1;
        }
        return friends;
    }

    public static void menu() {
        System.out.println("Выберите команду");
        System.out.println("1 - внести в коллекцию друга по ключу(возрасту)");
        System.out.println("2 - удалить из коллекции друзей, превышающие заданные");
        System.out.println("3 - вывести в строковом представление всех друзей в коллекции");
        System.out.println("4 - добавить в коллекцию все данные из файла");
        System.out.println("5 - вывести информацию о коллекции");
        System.out.println("6 - удалить из коллекции друга по ключу");
        System.out.println("7 - удалить из коллекции друзей, ключ которых превышает заданный");
        System.out.println("8 - выход из приложения");
    }

    //метод для добавления в коллекцию элемента с заданным ключом
    public static void insert(String key, Map<String, Friend> ourMap) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите имя ребенка");
        String name = sc.nextLine();
        System.out.println("Введите Карлсон или не Карлсон");
        String carl = sc.nextLine();
        System.out.println("Введите его возможность пойти с Малышом");
        double chance = Double.parseDouble(sc.nextLine());
        Friend newFriend = new Friend(name, carl, chance, key);
        ourMap.put(key, newFriend);
        System.out.println("Друг добавлен");
    }

    //метод, удаляющий из коллекции элементы, превышающий заданный
    public static void remove_greater(Map<String, Friend> ourMap) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите возраст ребенка(ключ)");
        int age = sc.nextInt();
        for (Iterator<Map.Entry<String, Friend>> element = ourMap.entrySet().iterator(); element.hasNext();) {
            Map.Entry<String, Friend> it = element.next();
            if (String.valueOf(age).equals(it.getValue().age))
                break;
            else
                element.remove();
        }
        System.out.println("Ребенок удален из коллекции");
    }


    //метод, выводящий элементы коллекции
    public static void show(Map<String, Friend> ourMap) {
        for (Map.Entry<String, Friend> element : ourMap.entrySet()) {
            System.out.println(element.getValue().name);
        }
    }

    //метод, добавляющий элементы из файла в коллекцию
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
    public static void info(Map<String, Friend> ourMap){
        System.out.println("В коллекции типа TreeMap хранятся данные о " + ourMap.size()+" друзьях. Ключом является возраст детей, а значением сам ребенок");
    }

    //метод, удаляющий элемент по ключу
    public static void remove(String key, Map<String, Friend> ourMap) {
        ourMap.remove(key);
        System.out.println("Удаление успешно завершено.");
    }

    //метод, удаляющий все элементы, ключ которых превышает заданный
    public static void remove_greater_key(Map<String, Friend> ourMap) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите возраст ребенка(ключ)");
        int age = sc.nextInt();
        for (Iterator<Map.Entry<String, Friend>> element = ourMap.entrySet().iterator(); element.hasNext();) {
            Map.Entry<String, Friend> it = element.next();
            if (age < Integer.parseInt(it.getKey()))
                element.remove();
        }
    }
    public static TreeMap AddFromFile(File file, TreeMap friends1) throws FileNotFoundException, ParseException{
        ArrayList<JSONObject> jsons = ReadJSON(file);
        for (JSONObject obj : jsons) {
            Friend fr = new Friend((String) obj.get("name"), (String) obj.get("carlson"), Double.parseDouble((String) obj.get("ChanceToWalk")), (String) obj.get("age"));
            friends1.put((String) obj.get("age"), fr);
    }
    return friends1;
    }

    public static void main(String[] args) {

        //указание путь к json файлу
        //String path="/Users/lida/Desktop/ITMO/джафка/LidaTaliya/Friends2.json";
        String path="C:\\IDE\\LidaTaliya\\Friends.json";

        File file= new File(path);
        Friend[] friends=MakeArray();


        //само чтение json и добавление экземпляров друзей
        try {
            friends1=AddFromFile(file, (TreeMap)friends1);
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден :(");

        } catch (ParseException e) {
            e.printStackTrace();
        }




        //консольное приложение
        Scanner scan = new Scanner(System.in);
        menu();
        int a = scan.nextInt();
        while (a != 8) {
            if (a == 1) {
                System.out.println("Введите ключ(возраст ребенка)");
                int age = scan.nextInt();
                insert(String.valueOf(age), friends1);
            }
            if (a == 2)
                remove_greater(friends1);
            if (a==3)
                show(friends1);
            if (a==4)
                imports(path);
            if (a==5)
                info(friends1);
            if (a==6){
                System.out.println("Введите возраст(ключ) ребенка");
                int age = scan.nextInt();
                remove(String.valueOf(age), friends1);
            }
            if (a==7) {
                remove_greater_key(friends1);
            }
            menu();
            a = scan.nextInt();

        }
        if (a==8){
            try {
                FileWriter fstream1 = new FileWriter(path);// конструктор с одним параметром - для перезаписи
                BufferedWriter out1 = new BufferedWriter(fstream1); //  создаём буферезированный поток
                out1.write(""); // очищаем, перезаписав поверх пустую строку
                out1.close(); // закрываем

                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(path));
                friends=MakeArray();
                for (int j = 0; j < friends.length; j++) {
                    String carl;
                    if (friends[j].MeetCarlson){
                        carl="Карлсон";
                    }else{
                        carl="не Карлсон";
                    }
                    String fr= "{\"name\": \""+friends[j].name+"\",\"Carlson\":\""+carl+"\",\"ChanceToWalk\":\""+friends[j].ChanceToWalk+"\",\"age\":\""+friends[j].age+"\"}";
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
            System.out.println("Изменения в коллекции друзей успешно сохранены в файл. Ещё увидимся:)");
            System.exit(0);

        }
        for (Map.Entry<String, Friend> e : friends1.entrySet()) {
            System.out.println(e.getValue().name);
        }





        /*Parent mother= new Parent("Мама", false);



        Parent mother= new Parent("Мама", false);

        Kid kid=new Kid("Малыш",0.8,true);
        Dog pudel=new Dog(Size.S,Color.Black,"пудель",0.9);
        Carlson carlson=new Carlson("Карлсон", true);

        mother.Breathe(kid);
        mother.AttitudeToCarlson(kid);

        Group g=new Group();

        g.AddKid(kid);
        g.AddPudel(pudel);
        g.AddCarlson(carlson);


        g.AddFriends(friends);
        Friend[] FriendsWhoGo=g.WhoIsGoing();

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
        try{
            kid.BeHappyWithDog(pudel);}
        catch (KidNoLoveDogs e){
            System.out.print(e.getMessage());
        }
        pudel.FeelTheAttitude(kid);
        pudel.LoveEveryone(kid);
        kid.LoveDog(pudel);
        kid.ActionsWithDog(pudel);
        kid.Sounds(pudel);
        try{
            pudel.ThinkSo();
        } catch(NoThinkSo e){
            System.out.print(e.getMessage());
        }
        pudel.JumpAndYelp();
        g.TurnToStreet();

        pudel.Run();*/


    }
}


