package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

import BLL.UserBLL;
import DAL.User;
import DAL.UserDAL;

public class Worker implements Runnable{
	
	BufferedReader in;
	BufferedWriter out;
	ObjectOutputStream outobj;
	ObjectInputStream inobj;
	private Socket socket;
	public User user = null;
	private String id;
	private int idroom=-1;
	private Integer[] arr;
	private Integer[] temp;
	UserBLL userBLL = new UserBLL();
	public ObjectOutputStream oos;
	public int vitri=0;
	ArrayList<Integer> dapan = new ArrayList<>();
	public Worker(Socket socket, String id) {
		this.socket = socket;
		this.id = id;
	}
	
	public void send(String s)
	{
		try {
			out.write(s);
			out.newLine();
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void run()
	{
		System.out.println("Client " + socket.toString() + "accepted");
		try {
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            send("xxx");
			String input = "";
			while(true)
			{
				input = in.readLine();
				System.out.println(input);
                if(input.equals("close")) break;
                if(input.equals("test"))
                {
                	send("abc");
                }
                if(input.equals("vaoroom"))
                {
                	for(Room room : Server.rooms)
	                {
                		if(room.getPlayer1()!=null && room.getPlayer2()!=null)
                		{
                			temp = room.gettemp();
	                		idroom = room.getId();
	                		arr = room.getArr();
	                		String s ="mangne";
	                		for(int i : arr)
	        				{
	        					s+="#"+i;
	        				}
	                		send(s);
	            			send("guidulieuxong");
	        				 s ="number#"+temp[room.getPlayer2().vitri];
	        				send(s);
	        				for(int i : room.getPlayer1().dapan)
	        				{
	        					s="nguoixem1dung#"+i;
	        					send(s);
	        				}
	        				for(int i : room.getPlayer2().dapan)
	        				{
	        					s="nguoixem2dung#"+i;
	        					send(s);
	        				}
	        				break;
                		}
	                }
                }
                if(input.equals("sotieptheo"))
                	{
                		vitri++;
                		for(Worker worker : Server.workers)
		        		{
		        			if(worker.idroom==idroom)
		        			{
		        				String s ="number#"+temp[vitri];
		    					worker.send(s);
		        			}
		        		}
                	}
                
                if(input.equals("room"))
                {
	                for(Room room : Server.rooms)
	                {
	                	if(room.getPlayer1()==null)
	                	{
	                		temp = room.gettemp();
	                		arr = room.getArr();
	                		room.setPlayer1(this);
	                		idroom = room.getId();
	                		System.out.println("rom1 conect");
	                		break;
	                		
	                	}
	                	if(room.getPlayer2()==null)
	                	{
	                		temp = room.gettemp();
	                		room.setPlayer2(this);
	                		idroom = room.getId();
	                		arr = room.getArr();
	                		System.out.println("rom2 conect");
	                		for(Worker worker : Server.workers)
	    	        		{
	    	        			if(worker.idroom==idroom)
	    	        			{
	    	        				//worker.oos = new ObjectOutputStream(worker.socket.getOutputStream());
	    	            			//worker.oos.writeObject(arr);
	    	        				String s ="mangne";
	    	        				for(int i : arr)
	    	        				{
	    	        					s+="#"+i;
	    	        				}
	    	        				worker.send(s);
	    	            			//worker.oos.flush();
	    	            			worker.send("guidulieuxong");
	    	        				 s ="number#"+temp[0];
	    	        				worker.send(s);
	    	        				
	    	        			}
	    	        		}
	                		break;
	                	}
	                }
                }
                StringTokenizer cat = new StringTokenizer(input, "#");
                if(cat.countTokens()>1)
                {
                	String s=cat.nextToken();
	                if(s.equals("click"))
					{
	                	s=cat.nextToken();
	                	int x = Integer.parseInt(s);
	                	System.out.println(temp[vitri]);
	                	if(x==temp[vitri])
	                	{
	                		dapan.add(x);
	                		int dem=0;
		                	for(Worker worker : Server.workers)
			        		{
			        			if(worker.idroom==idroom && !worker.id.equals(id)&&dem==0)
			        			{
			        				for(Room room: Server.rooms)
			        				{
			        					//if(room.getPlayer1()==null)break;
			        					if(room.getPlayer1().id==worker.id)
			        					{
			        						worker.out.write("dichdung1"+"\n");
					    					worker.out.flush();
					    					break;
			        					}
			        					if(room.getPlayer2().id==worker.id)
			        					{
			        						worker.out.write("dichdung2"+"\n");
					    					worker.out.flush();
					    					break;
			        					}
			        				}
			    					dem++;
			    					continue;
			        			}
			        			if(worker.idroom==idroom && !worker.id.equals(id)&&dem>0)
			        			{
			        				for(Room room: Server.rooms)
			        				{
			        					if(room.getId()==idroom)
			        					{
			        						if(room.getPlayer1().id.equals(id))
			        						{
			        							worker.out.write("nguoixem1dung#"+x+"\n");
			        							worker.out.flush();
			        						}
			        						if(room.getPlayer2().id.equals(id))
			        						{
			        							worker.out.write("nguoixem2dung#"+x+"\n");
			        							worker.out.flush();
			        						}
			        						
			        					}
			        					
			        				}
			    					
			        			}
			        		}
		                	for(Room room: Server.rooms)
		                	{
		                		if(room.getPlayer1().id==id)
		                		{
		                			send("dung1");
		                			break;
		                		}
		                		if(room.getPlayer2().id==id)
		                		{
		                			{
		                				send("dung2");	
		                				break;
		                			}
		                		}
		                	}
	                	}
	                	else
	                	{
	                		System.out.println("sai");
	                		send("nguoichoi#sai");
	                	}
	                	
					}
	                if(s.equals("dangnhap"))
					{
						String username = cat.nextToken();
						String pass = cat.nextToken();
						UserDAL dal = new UserDAL();
						try {
							for(User x : dal.getAlluser())
							{
								if(x==null)break;
								if(x.getUsername().equals(username) && x.getPassword().equals(pass))
								{
									user = x;
									send("dangnhap#ok");
									break;
								}
							}
							if(user==null) 
							{
								send("dangnhap#fail");
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
                }
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
