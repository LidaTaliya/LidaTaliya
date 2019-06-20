package Client;
import Client.Client;

import java.util.concurrent.locks.ReentrantLock;
import Lab5.*;

public class Clients {
   static long start = System.currentTimeMillis();

    public static void main(String[] args) {
       try {
        //new Thread(new Lab5()).start();
        Lab5 serv=new Lab5();
        serv.start();

        ReentrantLock lock1 = new ReentrantLock();

        Client cl1 = new Client(lock1, "4", "3", "5");
        cl1.start();
     //cl1.join();

        Client cl2 = new Client(lock1, "3", "7", "5", "2");
        //cl2.join();
        cl2.start();
    // cl2.join();

        Client cl3 = new Client(lock1, "5", "6", "4", "1");

        cl3.start();
        cl3.join();
        serv.join();
        lock1.lock();
        //if (cl1.isInterrupted()&cl2.isInterrupted()&cl3.isInterrupted()){

        long time = System.currentTimeMillis() - start;
        System.out.println(time);
        //Lab5.GetSocket().close();// }
        } catch(InterruptedException e){
        e.printStackTrace();}

    }
}
