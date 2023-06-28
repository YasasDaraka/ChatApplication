import javafx.scene.text.Text;

import java.io.*;
import java.net.Socket;

public class Handler extends Thread{
    Socket clientSocket;
    DataOutputStream dataOutputStream;
    String name;

    public Handler(Socket socket, String name) {
        this.clientSocket = socket;
        this.name=name;
    }

    public void run() {
        try {
            dataOutputStream=new DataOutputStream(clientSocket.getOutputStream());
            DataInputStream dataInputStream = new DataInputStream(clientSocket.getInputStream());

            while (true){
                String message = dataInputStream.readUTF();

                if (message.equalsIgnoreCase("CLOSE")){
                    System.out.println("Chat Closed");
                    break;
                }
                String[] tokens = message.split(" ");
                String cmd = tokens[0];
                System.out.println(cmd);

                String[] msgToAr = message.split(" ");
                String st = "";
                for (int i = 0; i < msgToAr.length - 1; i++) {
                    st += msgToAr[i + 1] + " ";
                }

                if (st.startsWith("img")) {

                    Server.broadcastMessage(message,this.clientSocket);

                }else {
                    String received=this.name+" : "+message;
                    Server.broadcastMessage(received,this.clientSocket);
                }


            }
            clientSocket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public void sendMessage(String reply) {
        try {
            dataOutputStream.writeUTF(reply);
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}



