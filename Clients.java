import Client.Client;
import Lab5.*;
import java.util.concurrent.locks.ReentrantLock;

public class Clients {
    public static void main(String[] args){
        new Thread(new Lab5()).start();
        ReentrantLock lock=new ReentrantLock();
        new Thread(new Client(lock,"4","3","5")).start();
        new Thread(new Client(lock,"5","6","4","1")).start();
        new Thread(new Client(lock,"3","7","5","2")).start();
    }
}
