package Lab5;

public class Friend extends MenCreature {
    double ChanceToWalk;
    String age;
    Friend ( String name1, String carlson, double k, String years){
        this.name=name1;
        this.ChanceToWalk=k;
        this.age = years;
        if (carlson=="Карлсон"){
            this.MeetCarlson=true;
        }
    }
    public boolean walk() {
        if (Math.random() <ChanceToWalk ) {
            return true;
        } else {return false;}
    }
}
