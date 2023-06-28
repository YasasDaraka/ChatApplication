import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private static List<Handler> clients = new ArrayList<> ();


    public static void main(String[] args) {
        try {
            ServerSocket serverSocket=new ServerSocket (3008);

            for (int i = 0; i < 5; i++) {
                Socket socket = serverSocket.accept();

                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                String clientName = dataInputStream.readUTF();
                System.out.println("Client connected: " + clientName);

                Handler clientHandler = new Handler(socket,clientName);
                clients.add(clientHandler);
                clientHandler.start ();
            }

        } catch (Exception e) {
            e.printStackTrace ();




        }
    }
    public static void broadcastMessage(String message,Socket socket) {
        for (Handler client : clients) {
               if(client.clientSocket.getPort() != socket.getPort()){
                   client.sendMessage (message);
               }
            }
        }

}