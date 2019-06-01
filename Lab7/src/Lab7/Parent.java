package Lab7;

public class Parent extends MenCreature{
   public Parent(String name1, boolean meetCarlson){
        this.name=name1;
        this.MeetCarlson=meetCarlson;
    }
  public boolean walk(){return true;}
  public void Breathe(Kid kid){
      if (kid.WantDog){
          System.out.print(this.name+" вздохнул(а). Ну вот, опять "+kid.name+" заговорил о своей вожделенной собаке! ");
      }else{
          System.out.print(kid.name+" не наскучивал "+this.name+" разговорами о собаке, так как он её не хотел. ");
      }
  }
  public void AttitudeToCarlson(Kid kid){
        if (!this.MeetCarlson&&kid.WantDog){
            System.out.print("Это было почти так же невыносимо, как и разговоры о Карлсоне, который живёт на крыше. ");
        }else if(this.MeetCarlson&&kid.WantDog){
            System.out.print("Это было невыносимо по сравнению с разговорами о Карлсоне, который живёт на крыше. ");
        }else if(!this.MeetCarlson&&!kid.WantDog){
            System.out.print("Зато он постоянно говорил о Карлсоне, который живёт на крыше, и это было невыносимо. ");
        }else{
            System.out.print("Он часто говорил о Карлсоне, который живёт на крыше, и "+this.name+" это нравилось, так как она тоже была с ним знакома. ");
        }

  }


}
