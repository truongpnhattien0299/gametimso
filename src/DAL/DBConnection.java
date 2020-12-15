package DAL;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class DBConnection {
	public Connection getConnection() throws SQLException {
		final String url = "jdbc:mysql://remotemysql.com/N9sAqushb3";
		final String user = "N9sAqushb3";
		final String pass = "5HZZmDRyKL";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			return (Connection) DriverManager.getConnection(url, user, pass);
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;

	}
}