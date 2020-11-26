package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

import BLL.UserBLL;

public class Worker implements Runnable{
	private Socket socket;
	BufferedReader in;
	BufferedWriter out;
	ObjectOutputStream outobj;
	ObjectInputStream inobj;
	private String id;
	private int idroom;
	private Integer[] arr;
	public Worker(Socket socket, String id) {
		this.socket = socket;
		this.id = id;
	}

	public void run()
	{
		UserBLL userBLL = new UserBLL();
		System.out.println("Client " + socket.toString() + "accepted");
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			outobj = new ObjectOutputStream(socket.getOutputStream());
			inobj = new ObjectInputStream(socket.getInputStream());
			
			String input = "";
			while(true)
			{
				input = in.readLine();
				System.out.println(input);
                if(input.equals("close")) break;
                if(input.equals("room"))
	                for(Room room : Server.rooms)
	                {
	                	if(room.getPlayer1()==null)
	                	{
	                		room.setPlayer1(this);
	                		idroom = room.getId();
	                		break;
	                	}
	                	if(room.getPlayer2()==null)
	                	{
	                		arr = userBLL.so(100);
	                		room.setPlayer2(this);
	                		idroom = room.getId();
	                		for(Worker worker : Server.workers)
	                		{
	                			if(worker.idroom==idroom)
	                			{
	    	            			worker.outobj.writeObject(arr);
	    	            			worker.outobj.flush();
	                				worker.out.write("start \n");
	                				worker.out.flush();
	                			}
	                		}
	                		break;
	                	}
	                }	
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
