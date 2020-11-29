package DAL;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collections;

public class UserDAL {
	public UserDAL() {}
	public Boolean Login(String username, String password)
	{
		DBConnection conn = new DBConnection();
		try {
			Statement stm = conn.getConnection().createStatement();
			String sql = "SELECT count(*) FROM user WHERE username='"+ username +"' and password='"+ password +"'";
			ResultSet resultSet = stm.executeQuery(sql);
			if(resultSet.next())
			{
				System.out.println(resultSet.getString(1));
				if(resultSet.getString(1).equals("1"))
					return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Integer[] so(int qty)
	{
		Integer[] arr = new Integer[qty];
	    for (int i = 0; i <arr.length; i++) {
	        arr[i] = i+1;
	    }
	    Collections.shuffle(Arrays.asList(arr));
		return arr;
	}
	
	
	public Socket create_socket(String host, int port)
	{
		Socket socket = null;
		try {
			socket = new Socket(host, port);
			System.out.println("Client connection");
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return socket;
	}
}
