package Lab5;

public class Friend extends MenCreature implements Comparable<Friend> {
    double ChanceToWalk;
    String number;
    boolean isTheBestFriend;
    double DistanceFromSchool;
    Friend ( String name1, String carlson, double k, String num,double dist){
        this.name=name1;
        this.ChanceToWalk=k;
        this.number = num;
        this.isTheBestFriend = false;
        this.DistanceFromSchool=dist;
        if (carlson.equals("Карлсон")){
            this.MeetCarlson=true;
        }
    }
    public boolean walk() {
        if (Math.random() <ChanceToWalk ) {
            return true;
        } else {return false;}
    }
    Friend(){}
    @Override
    public int compareTo(Friend o) {
        if (this.name.compareTo(o.name)==0){
            return 0;
        }else if(this.name.compareTo(o.name)<0){
            return -1;
        }else{
            return 1;
        }
    }
}