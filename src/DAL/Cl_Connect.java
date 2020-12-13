package DAL;

import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Server.Rank;
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
	public static ObjectInputStream inobj;
	public static ObjectOutputStream outobj;

	
	private ExecutorService executor;
	Integer[] myMessageArray= new Integer[100];
	public static String[] arr_result = new String[3];
	public static String s;
	
	

	public Cl_Connect() throws UnknownHostException, IOException 
	{ 
		
	}
	
	
	
	public void CreateSocket(String address, int port) throws UnknownHostException, IOException {
	
	socket = new Socket(address, port); 
	System.out.println("Connected"); 
	out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    
    outobj = new ObjectOutputStream(socket.getOutputStream());
    
    inobj = new ObjectInputStream(socket.getInputStream());  
	
	}
	public Boolean Login(String user,String pass) throws IOException, ClassNotFoundException
	{

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

	}
	
	
	
	
	 public List<Rank_cl> findAll() throws IOException, ClassNotFoundException {
		 
		 System.out.println("vao find all");
		 
	 List<Rank> rankList = new ArrayList<Rank>();
	 List<Rank_cl> rankList_cl = new ArrayList<Rank_cl>();
	 
	 rankList = (List<Rank>) inobj.readObject();
	 rankList.forEach((Rank) -> {
          Rank_cl rank_cl = new Rank_cl(Rank.getUserId(),Rank.getUsername(),Rank.getPoint(),Rank.getN_match(),Rank.getWin(),Rank.getLose()); 
          rankList_cl.add(rank_cl);    
	 });
	 
	 return rankList_cl;
	 }
	 
	 
	
	
	
	
	
	

	
	
	

	
	
	

	
	
}
