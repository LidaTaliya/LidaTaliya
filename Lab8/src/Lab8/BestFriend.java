package Lab8;


public class BestFriend extends BestFriendDecorator {

    public BestFriend(Friend friend) {
        super(friend);
    }

    public String becomeTheBestFriend(Friend friend){
        friend.isTheBestFriend = true;
        return friend.name + "Стал лучшим другом малыша";
    }

    public String tellTheSecret(Friend friend){
        if (friend.isTheBestFriend){
            return "Другу можно доверять";
        }
        return "Друг не является лучшим, не стоит ему доверять секрет";
    }
}