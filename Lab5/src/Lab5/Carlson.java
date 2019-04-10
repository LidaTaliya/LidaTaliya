package Lab5;

public class Carlson extends StrangeMenCreature {
   public Carlson(String name1, boolean fly){
        this.AbilityToFly=fly;
        this.name=name1;
    }
  public boolean walk(){
       fly();
       return true;
   }
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Carlson carl=(Carlson) obj;
        return (name == carl.name)&&(AbilityToFly==carl.AbilityToFly);

    }

    @Override
    public int hashCode() {
        final int prime=31;
        int result=1;
        result=prime*result+name.hashCode();
        return result;
    }
}
