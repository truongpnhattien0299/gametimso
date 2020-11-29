package BLL;

import java.net.Socket;

import DAL.UserDAL;

public class UserBLL {
	
	UserDAL userDAL = new UserDAL();
	public Boolean Login(String username, String password)
	{
		return userDAL.Login(username, password);
	}
	
	public Integer[] so(int qty)
	{
		return userDAL.so(qty);
	}
	
	
	public Socket CreateSocket(String host, int port)
	{
		return userDAL.create_socket(host, port);
	}
}
