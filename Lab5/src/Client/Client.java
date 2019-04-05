package Client;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Client {
   static Path path;
   static File file;

    private static boolean isNumber(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private static void menu() {
        System.out.println("Выберите команду - введите число от 1 до 8:");
        System.out.println("1 - добавить нового друга по ключу");
        System.out.println("2 - удалить из коллекции друзей, превышающие заданные");
        System.out.println("3 - вывести в строковом представление всех друзей в коллекции");
        System.out.println("4 - добавить в коллекцию все данные из файла");
        System.out.println("5 - вывести информацию о коллекции");
        System.out.println("6 - удалить из коллекции друга по ключу");
        System.out.println("7 - удалить из коллекции друзей, ключ которых превышает заданный");
        System.out.println("8 - выход из меню (запуск программы)");
    }

private static void importFromFile(DatagramSocket fromserver) throws IOException{
    try{
        FileInputStream fr=new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(fr));

        byte[] str;
        String strLine;
        while((strLine = br.readLine()) != null){
            str=strLine.getBytes();
            DatagramPacket export = new DatagramPacket(str, str.length, InetAddress.getByName("localhost"), 4444);
            fromserver.send(export);
        }
        String EndSending="EndSending";
        str=EndSending.getBytes();
        DatagramPacket end=new DatagramPacket(str,str.length,InetAddress.getByName("localhost"),4444);
        fromserver.send(end);

    }
    catch (FileNotFoundException e){
        System.out.println("Файл не найден");
        System.exit(0);
    }
    }

    public static void main(String[] args) throws IOException {

        try{
            path= Paths.get(System.getenv("Friendss"));
            file = path.toFile();}
        catch (NullPointerException e){
            System.out.println("Проверьте переменную окружения.");
            System.exit(0);
        }


        DatagramSocket fromserver = new DatagramSocket();
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

       /* byte[] b4 = "4".getBytes();
        DatagramPacket dp4 = new DatagramPacket(b4, b4.length, InetAddress.getByName("localhost"), 4444);
        fromserver.send(dp4);
            importFromFile(fromserver);*/

           // menu();
        String s="4";
            while (s!=null) {
                byte[] b = s.getBytes();
                DatagramPacket dp = new DatagramPacket(b, b.length, InetAddress.getByName("localhost"), 4444);
                fromserver.send(dp);

                if (s.equalsIgnoreCase("exit")) break;
                if (s.equals("4")){
                    importFromFile(fromserver);
                }

                byte[] buffer = new byte[65536];
                DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
                fromserver.receive(reply);
                byte[] data = reply.getData();
                String s1 = new String(data, 0, reply.getLength());
                if(s1.equals("menu")) {menu();}
                else{
                    System.out.println(s1); }
                s=in.readLine();
            }


       //fromserver = new Socket("localhost", 4444);
       //для запуска через терминал
       /* fromserver = new DatagramSocket(args[0],4444);

        if (args.length==0) {
            System.out.println("use: client hostname");
            System.exit(-1);
        }

        System.out.println("Connecting to... "+args[0]);
        //System.out.println("Connecting to...");


        BufferedReader in  = new BufferedReader(new InputStreamReader(fromserver.getInputStream()));
        PrintWriter out = new PrintWriter(fromserver.getOutputStream(),true);
        BufferedReader inu = new BufferedReader(new InputStreamReader(System.in));

        String fuser,fserver;
        menu();
        while ((fuser = inu.readLine())!=null) {
            if (fuser.equalsIgnoreCase("exit")){ break;}else{
                //menu();
                out.println(fuser);
                fserver = in.readLine();
                out.println(fuser);
                System.out.println(fserver);
            }
        }

        out.close();
        in.close();
        inu.close();
        fromserver.close();*/


    }
}