package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Worker implements Runnable{
	private Socket socket;
	BufferedReader in;
	BufferedWriter out;
	private String id;
	private int idroom;
	
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
	                		room.setPlayer2(this);
	                		idroom = room.getId();
	                		for(Worker worker : Server.workers)
	                		{
	                			if(worker.idroom==idroom)
	                			{
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
