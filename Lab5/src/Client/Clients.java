package Client;
import Lab5.*;

import java.awt.*;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;

public class Clients {
    static long start= System.currentTimeMillis();
    public static void main(String[] args){
       // try{
        new Thread(new Lab5()).start();
        ReentrantLock lock1=new ReentrantLock();
      //  ReentrantLock lock2=new ReentrantLock();
        //ReentrantLock lock3=new ReentrantLock();
        Client cl1=new Client(lock1,"4","3","5");
        cl1.start();
       // cl1.join();
      //  lock1.lock();
      //  if(lock1.isLocked()) {
            Client cl2 = new Client(lock1, "3", "7", "5", "2");
            cl2.start();
            // cl2.join();
            //lock2.lock();
            Client cl3 = new Client(lock1, "5", "6", "4", "1");
            cl3.start();
            // cl3.join();
            //lock3.lock();
        
            long time = System.currentTimeMillis() - start;
            System.out.println(time);
            //} catch(InterruptedException e){
        }        //e.printStackTrace();}

   // }
}
