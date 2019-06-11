package Client;
import Client.Client;

import java.util.concurrent.locks.ReentrantLock;
import Lab5.*;

public class Clients {
   static long start = System.currentTimeMillis();

    public static void main(String[] args) {
        new Thread(new Lab5()).start();
        ReentrantLock lock1 = new ReentrantLock();
        Client cl1 = new Client(lock1, "4", "3", "5");
        cl1.run();
        Client cl2 = new Client(lock1, "3", "7", "5", "2");
        cl2.run();
        Client cl3 = new Client(lock1, "5", "6", "4", "1");
        cl3.run();
        Client cl4=new Client(lock1, "4", "3", "5");
        Client cl5=new Client(lock1, "3", "4", "5");
        Client cl6 = new Client(lock1, "5", "7", "4", "2");
        cl4.run();
        cl5.run();
        cl6.run();
        long time = System.currentTimeMillis() - start;
        System.out.println(time);
        System.exit(0);

    }
}
