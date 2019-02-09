package Lab5;

public class Friend extends MenCreature {
    double ChanceToWalk;
    Friend ( String name1, Carlson carlson, double k){
        this.name=name1;
        this.ChanceToWalk=k;
        Carlson carlson1= new Carlson("Карлсон", true);
        if (carlson1.equals(carlson)){
            this.MeetCarlson=true;
        }
    }
    public boolean walk() {
        if (Math.random() <ChanceToWalk ) {
            return true;
        } else {return false;}
    }
}
