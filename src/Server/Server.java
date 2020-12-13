package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
	private static ServerSocket server = null;
    public static int port = 1234;
    public static int numThread = 40;
    public static ArrayList<Worker> workers = new ArrayList<>();
    public static ArrayList<Room> rooms = new ArrayList<>();
    public static Integer[] arr_rd;
    public static Integer[] point_bonus;
    public static BufferedReader stdIn = null; 
    public static String minute,choose;
    
  
    public static void main(String args[]) throws IOException 
    { 
    	
    	stdIn = new BufferedReader(new InputStreamReader(System.in));
    	System.out.print("Nhap vao so phut : ");
    	minute = stdIn.readLine();
    	System.out.println("so phut : " +minute);
//    	System.out.print("Nhap 1 de random 100 so, chon 2 de random 100 so : ");
//    	 choose= stdIn.readLine();
    	 

    	
	   
    	
    	
    	
	    
    	Random_So rd = new Random_So();
		arr_rd= rd.so();
		
		point_bonus=rd.so();
		
		

		
		
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
                String user="";
                Worker worker = new Worker(socket, Integer.toString(id),user) {};
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
