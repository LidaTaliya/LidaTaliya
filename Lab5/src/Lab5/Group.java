package Lab5;

import java.util.Arrays;

public class Group {
    Kid kid;
    Dog pudel;
    Friend[] AllFriends;
    Friend[] FriendsWhoGo;
    Carlson carlson;
    public void AddKid(Kid kid1){
        this.kid=kid1;
    }
    public void AddPudel(Dog pudel1){
        this.pudel=pudel1;
    }
    public void AddFriends(Friend[] friends1){
        this.AllFriends=friends1;
    }
    public void AddCarlson(Carlson carlson1){
        this.carlson=carlson1;
    }
    public void AddFriendsWhoGo(Friend[] friends){
        this.FriendsWhoGo=friends;
    }
    public void BeHappyToGoToSchool(){
        if (AllFriends.length!=0){
            System.out.print("...В тот день "+kid.name+"у было приятно идти в школу, потому что ему многое надо было обсудить с ");
            for (int i = 0; i < AllFriends.length; i++){
                System.out.print(AllFriends[i].name+" ");
            }
            System.out.print(". ");
        }else{
            System.out.print("...В тот день "+kid.name+"у не было приятно идти в школу, потому что ему не с кем было обсудить то, что происходит.");
        }
    }
    public Friend[] WhoIsGoing(){
        int j=0;
        Friend[] FriendsWhoGo=new Friend[AllFriends.length];
        for (int i = 0; i < AllFriends.length; i++) {
            if (AllFriends[i].walk()) {
                FriendsWhoGo[j]=AllFriends[i];
                j++;
            }}
        return FriendsWhoGo;
    }

    private String NamesOfFriends(){
        String s = "с ";
        for (int i = 0; i < FriendsWhoGo.length; i++) {
            if (FriendsWhoGo[i]!=null){
                s = s + FriendsWhoGo[i].name + " ";
            }else break;
        }
        if (s=="с "){s="один ";}
        return s;
    }

    public void AllWalk() {
        if (Arrays.equals(AllFriends,FriendsWhoGo)&&AllFriends.length!=0){
            System.out.print("Домой они шли, как всегда, вместе. ");
        } else {
            System.out.print("Домой "+kid.name+" шёл "+NamesOfFriends()+".");

        }
    }

    public void CrossTheStreet() {
        if (Arrays.equals(AllFriends,FriendsWhoGo)&&AllFriends.length!=0){
            System.out.print("Когда ребята собрались переходить улицу, ");
        } else {
            System.out.print("Когда "+kid.name+" "+NamesOfFriends()+"собрались переходить улицу, ");
        }
    }
    public void TurnToStreet(){
        if (Arrays.equals(AllFriends,FriendsWhoGo)&&AllFriends.length!=0){
            System.out.print(", а когда дети свернули на свою улицу ");
        } else {
            System.out.print(", а когда "+kid.name+" "+NamesOfFriends()+"свернул на свою улицу ");
        }
    }



}
