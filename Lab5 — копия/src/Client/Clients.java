package Client;
import Client.Client;

import java.util.concurrent.locks.ReentrantLock;
import Lab5.*;

public class Clients {
   static long start = System.currentTimeMillis();

    public static void main(String[] args) {
       try {
        new Thread(new Lab5()).start();
        ReentrantLock lock1 = new ReentrantLock();

        Client cl1 = new Client(lock1, "4", "3", "5");
        cl1.start();

        Client cl2 = new Client(lock1, "3", "7", "5", "2");
        //cl2.join();
        cl2.start();

        Client cl3 = new Client(lock1, "5", "6", "4", "1");
        Client cl4=new Client(lock1, "4", "3", "5");
        Client cl5=new Client(lock1, "3", "4", "5");
        Client cl6 = new Client(lock1, "5", "7", "4", "2");
        //cl3.join();
        cl3.start();
        cl4.start();
        cl5.start();
        cl6.start();
        cl6.join();

       // lock1.lock();
        long time = System.currentTimeMillis() - start;
        System.out.println(time);
        Lab5.GetSocket().close();
        } catch(InterruptedException e){
        e.printStackTrace();}

    }
}
