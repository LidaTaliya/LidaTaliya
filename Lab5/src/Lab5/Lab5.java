package Lab5;

public class Lab5{


    public static void main(String[] args) {
        Parent mother= new Parent("Мама", false);
        Kid kid=new Kid("Малыш",0.8,true);
        Dog pudel=new Dog(Size.S,Color.Black,"пудель",0.9);
        Carlson carlson=new Carlson("Карлсон", true);

        mother.Breathe(kid);
        mother.AttitudeToCarlson(kid);

        Group g=new Group();

        g.AddKid(kid);
        g.AddPudel(pudel);
        g.AddCarlson(carlson);

        Friend[] friends={new Friend("Гунилла", carlson, 0.7),new Friend("Кристер", carlson, 0.8)};
        g.AddFriends(friends);
        Friend[] FriendsWhoGo=g.WhoIsGoing();

        g.AddFriendsWhoGo(FriendsWhoGo);
        g.BeHappyToGoToSchool();
        g.AllWalk();

        kid.BeHappy(friends, carlson);
        pudel.appear();
        pudel.wantToGo(kid);
        g.CrossTheStreet();
        pudel.goToKid(kid);
        pudel.toSniffTheKnees(kid);
        pudel.toYelp();
        try{
            kid.BeHappyWithDog(pudel);}
        catch (KidNoLoveDogs e){
            System.out.print(e.getMessage());
        }
        pudel.FeelTheAttitude(kid);
        pudel.LoveEveryone(kid);
        kid.LoveDog(pudel);
        kid.ActionsWithDog(pudel);
        kid.Sounds(pudel);
        try{
            pudel.ThinkSo();
        } catch(NoThinkSo e){
            System.out.print(e.getMessage());
        }
        pudel.JumpAndYelp();
        g.TurnToStreet();
        pudel.Run();
    }}