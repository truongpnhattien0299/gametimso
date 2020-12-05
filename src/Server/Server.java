package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
	private static ServerSocket server = null;
    public static int port = 1234;
    public static int numThread = 40;
    public static ArrayList<Worker> workers = new ArrayList<>();
    public static ArrayList<Room> rooms = new ArrayList<>();
  
    public static void main(String args[]) throws IOException 
    { 
        ExecutorService executor = Executors.newFixedThreadPool(numThread);
        try
        { 
            server = new ServerSocket(port); 
            System.out.println("Server binding at port " + port); 
            System.out.println("Waiting for a client ..."); 
            for(int i=0;i<10;i++)
            {
                Room room = new Room(i);
                rooms.add(room);
            }
            int id=0;
            while(true)
            {
                Socket socket = server.accept(); 
                Worker worker = new Worker(socket, Integer.toString(id)) {};
                executor.execute(worker);
                workers.add(worker);
                id++;
            }
        }catch(IOException i) 
        { 
            System.err.println(i); 
        } 
        finally{
            if(server!=null)
                server.close();
        } 
    } 
}
