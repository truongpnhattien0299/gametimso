package DAL;

import java.net.*;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import Server.Room;
import Server.Server;
import Server.Worker;

import java.awt.Color;
import java.io.*; 

public class Cl_Connect 
{ 
	public static Socket socket; 
	public static BufferedWriter out = null;
	public static BufferedReader in = null;

	
	private ExecutorService executor;
	Integer[] myMessageArray= new Integer[100];
	String[] arr_result;
	public static String s;
	
	public static ObjectInputStream inobj;
	 public static ObjectOutputStream outobj;

	public Cl_Connect(String address, int port) throws UnknownHostException, IOException 
	{ 
		socket = new Socket(address, port); 
		System.out.println("Connected"); 
		out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        
        outobj = new ObjectOutputStream(socket.getOutputStream());
        
        inobj = new ObjectInputStream(socket.getInputStream());  
	}
	
	
	
	
	
	
	
	
	public Boolean Login(String user,String pass) throws IOException, ClassNotFoundException
	{
		
		
//		while(true)
//		{
			String line = "";
			String[] login = new String[3];
			login[0]="cl_login";
			login[1]=user;
			login[2]=pass;
			System.out.println(login[1]);
		    
			outobj.writeObject(login);
			outobj.flush();
				
				arr_result = (String[]) inobj.readObject();
				 if(arr_result[0].equals("success")) {
		            	return true;
		            }
				  return false;
//		}
		
			
           
            
          

	}
	
	
	
	
	
	

	
	
	

	
	
	

	
	
}
