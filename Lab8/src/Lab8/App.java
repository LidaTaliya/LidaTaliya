package Lab8;
import Client.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class App {
    public DatagramChannel channel;
    public InetSocketAddress hostAddress;
    public App(DatagramChannel channel1, InetSocketAddress hostAddress1){
        this.channel=channel1;
        this.hostAddress=hostAddress1;
    }
    public static String reg;
    static String login;
   public static String pass;
    public void SendInfo(String text){
        try{
            ByteBuffer b = ByteBuffer.wrap(text.getBytes());
            channel.send(b, hostAddress);
        }

        catch (IOException ex){
            System.out.println("Не удалось связаться с сервером");
            System.exit(0);
        }
    }

    public void SwingApp() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int sizeWidth = 600;
        int sizeHeight = 300;
        int locationX = (screenSize.width - sizeWidth) / 2;
        int locationY = (screenSize.height - sizeHeight) / 2;
        JFrame frame = new JFrame("Добро пожаловать!");
        frame.setBounds(locationX, locationY, sizeWidth, sizeHeight);
        JLabel label = new JLabel("Введите свой адрес электронной почты");
        JButton button = new JButton("Далее");
        Box box = Box.createVerticalBox();
        frame.getContentPane().add(box);
        button.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        label.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        JTextField text = new JTextField("example@mail.ru", 30);
        text.setMaximumSize(new Dimension(Integer.MAX_VALUE, text.getMinimumSize().height));
        box.add(label);
        box.add(text);
        box.add(button);
    /*    text.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                // JOptionPane.showMessageDialog(frame,
                //       "Ваше слово: " + text.getText());
            }
        });*/

        Box box2 = Box.createVerticalBox();


        JPasswordField password = new JPasswordField(10);
        password.setEchoChar('*');
        //password.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        password.setMaximumSize(new Dimension(Integer.MAX_VALUE, text.getMinimumSize().height));
        JButton button2 = new JButton("Войти");
        button2.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        JLabel label2=new JLabel();
        label2.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        box2.add(label2);
        box2.add(password);
        box2.add(button2);
        button.addActionListener((ae) ->
        {
            String t="";
            try {
                login = text.getText();
                SendInfo(login);
                t=Lab8.ReceiveData(channel);
            }catch(IOException e){
                e.printStackTrace();
           }
            label2.setText(t);
            box.setVisible(false);
            frame.getContentPane().add(box2);
            box2.setVisible(true);
        });
        button2.addActionListener((ae)->
        {  try{
            pass =new String(password.getPassword());
            SendInfo(pass);
            String s=Lab8.ReceiveData(channel);
            if(!s.equals("Вы успешно авторизованы.")){
                JOptionPane.showMessageDialog(frame, s);
            }else{
                box2.setVisible(false);
            }
           }catch(IOException e){
               e.printStackTrace();
           }


        });
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);


    }
  //  public static void main(String[] args) {
//
 //   }
}
