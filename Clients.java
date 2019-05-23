//package Lab5;
import Client.Client;
import Lab5.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Clients {
static String[] str={"3","5"};
static String[] str2={"6","1","4"};
static String[] str3={"2","3","3"};
  static Path path= Paths.get(System.getenv("Friendss"));
   File file = path.toFile();
    public static void main(String[] args) {
        try {
                Client cl = new Client(path);
                cl.Connect();
                cl.Start(str);
                Client cl2=new Client(path);
                cl2.Connect();
                cl2.Start(str2);
                Client cl3=new Client(path);
                cl3.Connect();
                cl3.Start(str3);
            }catch(IOException e){
                e.printStackTrace();
            }
    }
}