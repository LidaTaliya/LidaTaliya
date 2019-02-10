package Lab5;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Lab5{
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

    public static void main(String[] args) {

        //указание путь к json файлу
        File file= new File("/Users/lida/IdeaProjects/untitled/src/Lab3/Friends.json");
        //объявление нашей будущей коллекции друзей(за ключ берём имя друга)
        Map<String,Friend> friends1=new TreeMap<String,Friend>();
        //само чтение json и добавление экземпляров друзей
        try {
            ArrayList<JSONObject> jsons = ReadJSON(file);
            for(JSONObject obj : jsons){
                Friend fr=new Friend((String)obj.get("name"),(String)obj.get("carlson"),Double.parseDouble((String)obj.get("ChanceToWalk")));
                friends1.put((String)obj.get("name"),fr);
            }
        } catch(FileNotFoundException e) {
            System.out.println("Файл не найден :(");
        }catch (ParseException e) {
            e.printStackTrace();
        }


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

//создание массива друзей на основе коллекции(чтоб не переписывать большую часть кода, которая была расчитана на массив)
        Friend[] friends=new Friend[(friends1.size())];
        int i=0;
        for(Map.Entry<String,Friend> e : friends1.entrySet()){
            friends[i]= e.getValue();
            i=i+1;
        }

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
        pudel.Run();
    }}