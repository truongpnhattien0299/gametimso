package DAL;

import java.net.*;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

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
	

	private static String key;
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
    
    
    String input = "";
	input = in.readLine();
	String giaima = decrypt(input, "QuynhAnh");
	giaima=giaima.trim();
	StringTokenizer cat = new StringTokenizer(giaima, "#");
	String s= cat.nextToken();
	if(s.equals("key")) {
		key=cat.nextToken();
	}
	
//	System.out.println("key_cl : "  +key);
	
	}
	
	public static String getKey()
	{
		return key;
	}
	public Boolean Login(String user,String pass) throws IOException, ClassNotFoundException
	{

			String login = "dangnhap#";
			
			login = login+user+"#"+pass+"\n";
			String mahoa = encrypt(login, key);
//			
		    
			out.write(""+mahoa+"\n");
			out.flush();
				
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
	 while (true)
	 {
	 rankList = (List<Rank>) inobj.readObject();
	 rankList.forEach((Rank) -> {
          Rank_cl rank_cl = new Rank_cl(Rank.getUserId(),Rank.getUsername(),Rank.getPoint(),Rank.getN_match(),Rank.getWin(),Rank.getLose()); 
          rankList_cl.add(rank_cl);    
	 });
	 
	 return rankList_cl;
	 }
	 }
	 
 public String user_rank() throws IOException, ClassNotFoundException {
		 
		 while (true)
		 {
	 String user = in.readLine();
	 System.out.println("us"  +user);
	 return user;
		 }
	 
	 
	 }
 
 
 
 
 public String encrypt(String strToEncrypt, String myKey) {
     try {
           MessageDigest sha = MessageDigest.getInstance("SHA-1");
           byte[] key = myKey.getBytes("UTF-8");
           key = sha.digest(key);
           key = Arrays.copyOf(key, 16);
           SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
           Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
           cipher.init(Cipher.ENCRYPT_MODE, secretKey);
           return java.util.Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
     } catch (Exception e) {
           System.out.println(e.toString());
     }
     return null;
   }

   public String decrypt(String strToDecrypt, String myKey) {
     try {
           MessageDigest sha = MessageDigest.getInstance("SHA-1");
           byte[] key = myKey.getBytes("UTF-8");
           key = sha.digest(key);
           key = Arrays.copyOf(key, 16);
           SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
           Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
           cipher.init(Cipher.DECRYPT_MODE, secretKey);
           return new String(cipher.doFinal(java.util.Base64.getDecoder().decode(strToDecrypt)));
     } catch (Exception e) {
           System.out.println(e.toString());
     }
     return null;
}
	
	
	
	
	
	

	
	
	

	
	
	

	
	
}
