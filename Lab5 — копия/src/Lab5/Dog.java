package Lab5;

public class Dog implements Animal {
    String size;
    String color;
    String Breed;
    Double ChanceToAppear;
    final String WhoIsit="щенок";
   public Dog(Size var1, Color var2, String Breed1 ,double chance){
       this.size=toString(var1);
       this.color=toString(var2);
       this.Breed=Breed1;
       this.ChanceToAppear=chance;
    }
    private String toString(Size var1) {
        return var1.string;
    }
    private String toString(Color var1) {
        return var1.string;
    }

public void toSniffTheKnees(Kid kid){
        if (0.65<kid.AttractPudels) {
            System.out.print("Он обнюхал коленки " + kid.name+"a ");
        }else { System.out.print("Он решил не нюхать коленки "+ kid.name+"а ");}
    }
public void toYelp(){
        if (Math.random()>0.4){
        System.out.print("и дружески тявкнул. ");}
        else{
            System.out.print("и тявкнул. ");
        }
}
public void goToKid(Kid kid){
        if (0.6<kid.AttractPudels){
            System.out.print("к "+ kid.name+"у подбежал ");
            System.out.print(this.size+" "+ this.color+" "+this.Breed+". ");}
    else{
        System.out.print(this.Breed+" не захотел подбегать к "+ kid.name+"у.");
        System.exit(0);
        }
    }
    public void appear() {
        if (Math.random() > ChanceToAppear) {
            System.out.print(" Никто больше не появлялся, и ничто не помешало спокойно дойти домой. ");
            System.exit(0);
        } else {
            System.out.print(" Тут появилось ещё одно существо");;
        }
    }
    public void wantToGo(Kid kid){
        if (0.4<kid.AttractPudels){
            System.out.print(", которое тоже захотело пойти вместе. ");
        }else{
            System.out.print(", но оно не захотело пойти вместе. ");
            System.exit(0);
        }
    }
   public boolean walk(){
        return true;
    }
    public void FeelTheAttitude(Kid kid){
        if (kid.WantDog){
            System.out.print("Должно быть, "+this.WhoIsit+" это почувствовал: он бежал вприпрыжку по мостовой, норовя прижаться к ноге "+kid.name+"а .");
        }else{
            System.out.print("Должно быть, "+this.WhoIsit+" это почувствовал: он погрустнел и решил не приставать к "+kid.name+"у .");
        }
    }
    public void LoveEveryone(Kid kid){
        System.out.print("У "+this.size+" "+this.WhoIsit+" был такой вид, будто он готов любить всех на свете, только бы его любили.");
        if (!kid.WantDog){
            System.exit(0);
        }
    }
    public void ThinkSo(){
           if (Math.random()<0.3){
               throw new NoThinkSo("Но "+this.WhoIsit+" так не думал, он был стеснительный.");
           }else{
               System.out.print(" "+this.WhoIsit+" вилял хвостом, всячески давая понять, что он тоже так думает. ");
           }
    }
    public void JumpAndYelp(){
        if (Math.random()>0.6){
            System.out.print("Он радостно прыгал и лаял");
        }else{
            System.out.print("Он прыгал и лаял");
        }
    }
    public void Run(){
        System.out.print(",побежал вслед за ними.");
    }
}
