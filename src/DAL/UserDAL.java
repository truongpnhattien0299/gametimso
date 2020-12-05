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
	 Statement stm = null;
	public UserDAL() {
		DBConnection conn = new DBConnection();
		try {
			stm = conn.getConnection().createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public User[] getAlluser() throws SQLException
	{
		ResultSet rs = stm.executeQuery("select * from user");
        User[] List = new User[50];
         int i =0;
         // show data
         while (rs.next()) {
                 User x = new User(rs.getInt(1),rs.getString(2),rs.getString(3));
                 List[i]=x;
                 i++;
             } 
         List[i]=null;
         return List;
		
	}
	
}
