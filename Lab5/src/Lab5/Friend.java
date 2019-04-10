package Lab5;

import java.io.Serializable;
import java.util.Date;

public class Friend extends MenCreature implements Comparable<Friend>, Serializable {
    private static final long serialVersionUID = 1L;

   public double ChanceToWalk;
    public String number;
    boolean isTheBestFriend;
   public double DistanceFromSchool;
   public Date date;
    Friend ( String name1, String carlson, double k, String num,double dist,Date date){
        this.name=name1;
        this.ChanceToWalk=k;
        this.number = num;
        this.isTheBestFriend = false;
        this.DistanceFromSchool=dist;
        this.date=date;
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