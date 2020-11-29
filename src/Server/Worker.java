package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

import BLL.UserBLL;

public class Worker implements Runnable{
	
	BufferedReader in;
	BufferedWriter out;
	ObjectOutputStream outobj;
	ObjectInputStream inobj;
	private Socket socket;
	
	private String id;
	private int idroom=-1;
	private Integer[] arr;
	private Integer[] temp;
	UserBLL userBLL = new UserBLL();
	
	public Worker(Socket socket, String id) {
		this.socket = socket;
		this.id = id;
	}

	public void run()
	{
		System.out.println("Client " + socket.toString() + "accepted");
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			outobj = new ObjectOutputStream(socket.getOutputStream());
			inobj = new ObjectInputStream(socket.getInputStream());
			
			String input = "";
			int vitri=0;
			while(true)
			{
				input = in.readLine();
				System.out.println(input);
                if(input.equals("close")) break;
                if(input.equals("test"))
                {
                	out.write("abc\n");
                	out.flush();
                }
                if(input.equals("sotieptheo"))
                	{
                		vitri++;
                		for(Worker worker : Server.workers)
		        		{
		        			if(worker.idroom==idroom)
		        			{
		        				worker.out.write("number#"+temp[vitri]+"\n");
		    					worker.out.flush();
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
	                		break;
	                	}
	                	if(room.getPlayer2()==null)
	                	{
	                		temp = room.gettemp();
	                		room.setPlayer2(this);
	                		idroom = room.getId();
	                		arr = room.getArr();
	                		for(Worker worker : Server.workers)
	    	        		{
	    	        			if(worker.idroom==idroom)
	    	        			{
	    	            			worker.outobj.writeObject(arr);
	    	            			worker.outobj.flush();
	    	        				worker.out.write("number#"+temp[0]+"\n");
	    	        				worker.out.flush();
	    	        			}
	    	        		}
	                		break;
	                	}
	                }
                }
                StringTokenizer cat = new StringTokenizer(input, "#");
                System.out.println(cat.countTokens());
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
		                	for(Worker worker : Server.workers)
			        		{
			        			if(worker.idroom==idroom && !worker.id.equals(id))
			        			{
			        				worker.out.write("dichdung"+"\n");
			    					worker.out.flush();
			        			}
			        		}
		                	out.write("dung"+"\n");
	    					out.flush();
	    					System.out.println("dung");
	                	}
	                	else
	                	{
	                		System.out.println("sai");
	                		out.write("sai\n");
	    					out.flush();
	                	}
	                	
					}
                }
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
