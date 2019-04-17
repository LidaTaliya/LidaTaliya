package Lab5;

import Client.Friend;

public abstract class BestFriendDecorator extends Friend {

    protected final Friend friend;

    public BestFriendDecorator(Friend friend) {
        super();
        this.friend = friend;
    }
}