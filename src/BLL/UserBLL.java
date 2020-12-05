package BLL;

import java.net.Socket;
import java.util.Arrays;
import java.util.Collections;

import DAL.UserDAL;

public class UserBLL {
	
	public UserBLL() {}
	public Integer[] so(int qty)
	{
		Integer[] arr = new Integer[qty];
	    for (int i = 0; i <arr.length; i++) {
	        arr[i] = i+1;
	    }
	    Collections.shuffle(Arrays.asList(arr));
		return arr;
	}
	
}
