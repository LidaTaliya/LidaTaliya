package Lab5;

public class Friend extends MenCreature {
    double ChanceToWalk;
    String number;
    Friend ( String name1, String carlson, double k, String num){
        this.name=name1;
        this.ChanceToWalk=k;
        this.number = num;
        if (carlson.equals("Карлсон")){
            this.MeetCarlson=true;
        }
    }
    public boolean walk() {
        if (Math.random() <ChanceToWalk ) {
            return true;
        } else {return false;}
    }
}
