package Lab7;


public class Kid  extends MenCreature{
    Double AttractPudels;
    Boolean WantDog;
   public Kid(String name1, double AttractPudels1,boolean WantDog1){this.name=name1;this.AttractPudels=AttractPudels1;this.WantDog=WantDog1;}
    public void BeHappy(Friend[] friends, Carlson carlson) {
        boolean AllMeetCarlson = true;
        try{
            if (friends.length!=0){
                for (int i = 0; i < friends.length; i++) {
                    if (friends[i].MeetCarlson) {
                        AllMeetCarlson = AllMeetCarlson && true;
                    } else {
                        AllMeetCarlson = AllMeetCarlson && false;
                    }
                }
                if (AllMeetCarlson) {
                    System.out.print(this.name + " радовался, потому что теперь ребята тоже были знакомы с " + carlson.name + "ом.");
                } else {
                    System.out.print(this.name + " не радовался, потому что ");
                    for (int i = 0; i < friends.length; i++) {
                        if (!friends[i].MeetCarlson) {
                            System.out.print(friends[i].name + " ");
                        }
                    }
                    System.out.print("не были знакомы с " + carlson.name + "ом.");
                }
            }
            else {
                throw new NullPointerException();
            }}catch(NullPointerException e){
            System.out.print(this.name + " не радовался, потому что ему некого было познакомить с "+ carlson.name + "ом.");
        }
    }
    public boolean walk(){return true;}
    public void BeHappyWithDog(Dog pudel) throws KidNoLoveDogs{
        if (this.WantDog){
            System.out.print(this.name+" был бы счастлив переводить "+pudel.WhoIsit+" через все перекрёстки города. ");
        }else{
            throw new KidNoLoveDogs("Но "+this.name+"у было всё равно, он не любил собак. ");
        }
    }
    public void LoveDog(Dog pudel){
        System.out.print("И "+this.name+" полюбил этого "+pudel.WhoIsit+". О, как он его полюбил! ");
    }
    public void ActionsWithDog(Dog pudel){
        System.out.print("Он нагнулся к "+pudel.WhoIsit+" и принялся ");
        double k=Math.random();
        if (k>0.7){
            System.out.print("ласкать его ");
        }else if(k>0.5){
            System.out.print("гладить его ");
        }else if(k>0.3){
            System.out.print("тихонько присвистывать ");
        }else{
            System.out.print("причмокивать ");
        }
        System.out.print(". ");
    }
    public void Sounds(Dog pudel){
        System.out.print("Эти нежные звуки должны были означать, что "+pudel.color+" "+pudel.Breed+" - ");
        if (Math.random()>0.2){
            System.out.print("самый симпатичный, самый распрекрасный "+pudel.WhoIsit+" на свете.");
        }else {
            System.out.print("хороший "+pudel.WhoIsit+". ");
        }
    }


}



