package Lab5;

public class Friend extends MenCreature {
    double ChanceToWalk;
    Friend ( String name1, String carlson, double k){
        this.name=name1;
        this.ChanceToWalk=k;
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
