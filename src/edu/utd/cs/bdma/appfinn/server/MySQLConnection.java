package edu.utd.cs.bdma.appfinn.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.Vector;

import javax.servlet.ServletException;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
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

	@Override
	public Boolean generateCodeSendEmail(String emailto) {
		PreparedStatement ps = null;
		String uuid = UUID.randomUUID().toString();
		String code = uuid.split("-")[0];
		System.out.println("code: " + code);

		String sqlCmd = "insert into tblCodes (Code) values ('" + code + "')";
		boolean validInsertOrUpdate = insertOrUpdate(sqlCmd);

		String msg = "Hi, \nHere is your code: " + code
				+ " \nThank you. \n Big Data Analytics and Management Lab. Computer Science. UT Dallas.";
		String subject = "Your code from the Big Data Analytics and Management Lab.";
		sendEmail(emailto, subject, msg);

		return validInsertOrUpdate;
	}

	private void sendEmail(String emailto, String subject, String msg) {
		String link = "http://" + Window.Location.getHostName() + ":" + Window.Location.getPort() + "/appfin/";
		String parameters = "?emailto=" + emailto;
		parameters += "&subject=" + subject;
		parameters += "&msg=" + msg;
		String url = link + "email" + parameters;
		System.out.println("email url: " + url);

		// Window.open( url, "_top", "status=0,toolbar=0,menubar=0,location=0");

		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);
		try {
			Request response = builder.sendRequest(null, new RequestCallback() {
				public void onError(Request request, Throwable exception) {
					Window.alert("Send Email failed. " + exception.toString());
				}

				public void onResponseReceived(Request request, Response response) {
					Window.alert("Send Email succeded. ");
				}
			});
		} catch (RequestException e) {
			e.printStackTrace();
		}

	}

	public Boolean checkRecord(String sqlCmd) {
		boolean recordExists = false;
		System.out.println("sql cmd: " + sqlCmd);
		try {

			conn = DriverManager.getConnection(url, this.user, this.pass);

			// System.out.println(conn.toString());
			PreparedStatement ps = conn.prepareStatement(sqlCmd);
			ResultSet result = ps.executeQuery();

			while (result.next()) {
				recordExists = true;
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

		return recordExists;
	}
}