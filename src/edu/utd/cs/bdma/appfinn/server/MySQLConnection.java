package edu.utd.cs.bdma.appfinn.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.servlet.ServletException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import edu.utd.cs.bdma.appfinn.client.DBConnection;
import edu.utd.cs.bdma.appfinn.client.User;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class MySQLConnection extends RemoteServiceServlet implements DBConnection {
	private Connection conn = null;
	private String status;
	private String url = "jdbc:mysql://104.155.147.107/apps"; // url =
																// "jdbc:mysql://yourDBserver/yourDBname"
	private String user = "root";
	private String pass = "dml12345";

	// public MySQLConnection() {
	// try {
	// System.out.println("Try block from inside the mysql conn constructor.");
	// Class.forName("com.mysql.jdbc.Driver");//.newInstance();
	// conn = DriverManager.getConnection(url, user, pass);
	// } catch (Exception e) {
	// // NEVER catch exceptions like this
	// System.out.println("Exception from inside the mysql conn constructor.");
	// System.out.println(e.getMessage());
	// }
	// }

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		try {
			System.out.println("Try block from inside the mysql conn constructor.");
			Class.forName("com.mysql.jdbc.Driver");// .newInstance();
			conn = DriverManager.getConnection(url, user, pass);
		} catch (Exception e) {
			// NEVER catch exceptions like this
			System.out.println("Exception from inside the mysql conn constructor.");
			System.out.println(e.getMessage());
		}
	}

	public User authenticateUser(String username, String pass) {
		boolean validUser = false;
		System.out.println("inside authenticateUser");
		User user = new User(username, pass);
		String sqlCmd = "select * from tblUser where username = \"" + username + "\" AND " + "userpassword = \"" + pass
				+ "\"";
		System.out.println("sql cmd: " + sqlCmd);
		try {
			
			conn = DriverManager.getConnection(url, this.user, this.pass);
			
			// System.out.println(conn.toString());
			PreparedStatement ps = conn.prepareStatement(sqlCmd);
			ResultSet result = ps.executeQuery();

			while (result.next()) {
				validUser = true;
				// System.out.println("string 1: " + result.getString(1));

				user.setGrantAccess(validUser);
				// if (user.getUsername().equals(username) &&
				// user.getPassword().equals(pass)){
				// validUser = true;
				// }
			}
			result.close();
			ps.close();
		} catch (SQLException sqle) {
			// do stuff on fail
			System.out.println(sqle.toString());
		} catch (Exception e) {
			System.out.println(e.toString());
			System.out.println(e.getMessage());
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}

		return user;
	}

	@Override
	public Boolean insertOrUpdate(String sqlCmd) {
		boolean validInsertOrUpdate = false;
		System.out.println("inside insert or update records.");
		PreparedStatement ps = null;

		System.out.println("sql cmd: " + sqlCmd);
		try {
			conn = DriverManager.getConnection(url, this.user, this.pass);
			
			ps = conn.prepareStatement(sqlCmd);
			
			ps.executeUpdate();

			validInsertOrUpdate = true;

		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				
				if (ps != null)
					ps.close();

				if (conn != null)
					conn.close();
				
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}

		return validInsertOrUpdate;
	}
}