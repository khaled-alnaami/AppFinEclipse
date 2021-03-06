package edu.utd.cs.bdma.appfinn.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.Vector;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import edu.utd.cs.bdma.appfinn.client.Question;

import edu.utd.cs.bdma.appfinn.client.DBConnection;
import edu.utd.cs.bdma.appfinn.client.User;

import edu.utd.cs.bdma.appfinn.client.SurevyStats;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class MySQLConnection extends RemoteServiceServlet implements DBConnection {
	private Connection conn = null;
	private String status;
	// private String url = "jdbc:mysql://104.155.147.107/apps"; // url =
	// "jdbc:mysql://yourDBserver/yourDBname"
	private String url = "jdbc:mysql://104.155.139.104/apps";
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
			// System.out.println("Try block from inside the mysql conn
			// constructor.");
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
		// System.out.println("inside authenticateUser");
		User user = new User(username, pass);
		String sqlCmd = "select * from tblUser where username = \"" + username + "\" AND " + "userpassword = \"" + pass
				+ "\"";
		// System.out.println("sql cmd: " + sqlCmd);
		try {

			conn = DriverManager.getConnection(url, this.user, this.pass);

			// System.out.println(conn.toString());
			PreparedStatement ps = conn.prepareStatement(sqlCmd);
			ResultSet result = ps.executeQuery();

			while (result.next()) {
				validUser = true;

				int ID = result.getInt("UserID");
				user.setUserID(ID);

				// System.out.println("string 1: " + result.getString(1));

				user.setGrantAccess(validUser);
				// if (user.getUsername().equals(username) &&
				// user.getPassword().equals(pass)){
				// validUser = true;
				// }
				// changes for survey admin page
				System.out.println("admin level: " + result.getString("AdminLevel"));
				user.setAdminLevel(result.getString("AdminLevel"));
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
		// System.out.println("inside insert or update records.");
		PreparedStatement ps = null;

		// System.out.println("sql cmd: " + sqlCmd);
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
	public Boolean generateCodeSendEmail(String emailto, String link) {
		PreparedStatement ps = null;
		String uuid = UUID.randomUUID().toString();
		String code = uuid.split("-")[0];
		// System.out.println("code: " + code);

		String sqlCmd = "insert into tblCodes (Code, Email) values ('" + code + "', '" + emailto + "')";
		boolean validInsertOrUpdate = insertOrUpdate(sqlCmd);

		String msg = "Dear New User, \n\nHere is your code: " + code
				+ " \n\nThank you. \nSmart Phone Apps Data Management. \nBig Data Management and Analytics Lab. \nComputer Science. \nUT Dallas.";

		String subject = "Your code from the Big Data Management and Analytics Lab.";

		sendEmail(link, emailto, subject, msg);

		return validInsertOrUpdate;
	}
	//
	// private void sendEmail(String link, String emailto, String subject,
	// String msg) {
	//
	//// String link = "http://" + Window.Location.getHostName() + ":" +
	// Window.Location.getPort() + "/appfin/";
	//// System.out.println("link: " + link);
	// String parameters = "?emailto=" + emailto;
	//// System.out.println("parameters1: " + parameters);
	// parameters += "&subject=" + subject;
	//// System.out.println("parameters2: " + parameters);
	// parameters += "&msg=" + msg;
	//// System.out.println("parameters3: " + parameters);
	// String url = link + "email" + parameters;
	//// System.out.println("email url: " + url);
	//
	// // Window.open( url, "_top", "status=0,toolbar=0,menubar=0,location=0");
	//
	//
	//
	// try {
	// RequestBuilder builder = new RequestBuilder(RequestBuilder.GET,
	// URL.encode(url));
	// Request response = builder.sendRequest(null, new RequestCallback() {
	// public void onError(Request request, Throwable exception) {
	// Window.alert("Send Email failed. " + exception.toString());
	// }
	//
	// public void onResponseReceived(Request request, Response response) {
	// Window.alert("Send Email succeded. ");
	// }
	// });
	// } catch (RequestException e) {
	// System.out.println("inside sendEmail request exception: " +
	// e.getMessage());
	// } catch (Exception e){
	// System.out.println("inside seneEmail exception: " + e.getMessage());
	// }
	//
	//
	// }

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
		// System.out.println("recordExists: " + recordExists);
		return recordExists;
	}

	public String[] getFields(String sqlCmd, String[] columnNames) {
		String[] columnValues = new String[columnNames.length];
		String recordStr = "";
		boolean recordExists = false;
		System.out.println("get record sql cmd: " + sqlCmd);
		try {

			conn = DriverManager.getConnection(url, this.user, this.pass);

			// System.out.println(conn.toString());
			PreparedStatement ps = conn.prepareStatement(sqlCmd);
			ResultSet result = ps.executeQuery();

			while (result.next()) {
				System.out.println("UserName" + result.getString("UserName"));
				System.out.println("UserPassword" + result.getString("UserPassword"));
				int i = 0;
				for (String columnName : columnNames) {
					columnValues[i++] = result.getString(columnName);
				}
				// recordStr = result.getString(column);
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
		System.out.println("returned columnValues: " + Arrays.toString(columnValues));
		return columnValues;
	}

	public void sendEmail(String link, String emailto, String subject, String msg) {
		final String username = "dml.utd@gmail.com";
		final String password = "dml12345";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailto));
			message.setSubject(subject);
			message.setText(msg);

			Transport.send(message);

			System.out.println("Send Email Done");

		} catch (MessagingException e) {
			System.out.println("sendEmail MessagingException" + e.getMessage());
			e.printStackTrace();
		} catch (RuntimeException e) {
			System.out.println("sendEmail RuntimeException" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("sendEmail Exception" + e.getMessage());

			System.out.println(e.toString());
			e.printStackTrace();
		}
	}

	@Override
	public Boolean checkRecordSendEmail(String email, String link) {

		boolean validEmail = false;
		String sqlCmd = "select * from tblUser where Email = '" + email + "';";
		String columnNames[] = { "UserName", "UserPassword" }; // get username
																// and password
		String[] columnValues = getFields(sqlCmd, columnNames);
		System.out.print("columnValues: " + Arrays.toString(columnValues)); // [null,
																			// null]
																			// if
																			// no
																			// data
																			// returned
																			// from
																			// DB
		if (columnValues[0] != null || columnValues[1] != null) {
			validEmail = true;

			String msg = "Dear User, \n\nHere is the data you requested:\nUsername: " + columnValues[0] + "\nPa$$w0rd: "
					+ columnValues[1] + ""
					+ " \n\nThank you. \nSmart Phone Apps Data Management. \nBig Data Management and Analytics Lab. \nComputer Science. \nUT Dallas.";

			String subject = "Your data from the Big Data Management and Analytics Lab.";

			sendEmail(link, email, subject, msg);
		}

		return validEmail;
	}

	public List<Question> getFieldsSurvey(String sqlCmd) {

		List<Question> list = new ArrayList<Question>();
		Question ques = null;

		try {

			conn = DriverManager.getConnection(url, this.user, this.pass);
			System.out.println("Connection " + conn.toString());
			PreparedStatement ps = conn.prepareStatement(sqlCmd);
			ResultSet result = ps.executeQuery();
			while (result.next()) {

				int ID = result.getInt("QID");

				System.out.println("QID " + Integer.toString(ID));
				String Type = result.getString("QType");
				System.out.println("Qtype " + Type);
				String Text = result.getString("QText");
				String Ans = result.getString("QAnswers");
				ques = new Question(ID, Type, Text, Ans);
				list.add(ques);
			}

			result.close();
			ps.close();
		} catch (SQLException sqle) {
			// do stuff on fail
			System.out.println(sqle.toString());
			sqle.printStackTrace();
		} catch (Exception e) {

			System.out.println(e.toString());
			e.printStackTrace();

			// System.out.println(e.getMessage());
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// System.out.println(e.getMessage());
			}
		}

		return list;
	}

	public Boolean InsertAllRecords(int UserID, List<Question> AllAnswers) {
		System.out.println("-------------------------------------------------------");

		String sqlcmd = "";
		ArrayList<Boolean> InsertIndicator = new ArrayList<Boolean>();
		boolean finalIndicator = true;
		boolean validInsert = false;
		for (Question Q : AllAnswers) {
			if (Q != null) {
				sqlcmd = "insert into UserAnswer (UserID,QID,Answer) values ('" + UserID + "', '" + Q.QID + "', '"
						+ Q.QAnswer + "')";
				validInsert = insertOrUpdate(sqlcmd);
				System.out.println(" inside " + validInsert);
				InsertIndicator.add(validInsert);
			}
		}

		for (boolean B : InsertIndicator) {
			if (B == false) {
				System.out.println(" outside " + B);
				finalIndicator = false;
			}
		}

		System.out.println(" last " + finalIndicator);

		return finalIndicator;

	}

	// get survey statistics from table
	public List<SurevyStats> getSurveyStatistics(String sqlCmd) {

		List<SurevyStats> list = new ArrayList<SurevyStats>();

		try {

			conn = DriverManager.getConnection(url, this.user, this.pass);
			System.out.println("Connection " + conn.toString());
			System.out.println("SQL Query " + sqlCmd);
			PreparedStatement ps = conn.prepareStatement(sqlCmd);
			ResultSet result = ps.executeQuery();
			while (result.next()) {

				String qId = result.getString(1);
				String qText = result.getString(2);
				String option = result.getString(3);
				int count = result.getInt(4);

				SurevyStats details = new SurevyStats(qId, qText, option, count);
				list.add(details);
			}
			// System.out.println("outside try");
			result.close();
			ps.close();
		} catch (SQLException sqle) {
			// do stuff on fail
			System.out.println(sqle.toString());
			sqle.printStackTrace();
		} catch (Exception e) {

			System.out.println(e.toString());
			e.printStackTrace();

		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
		return list;
	}

	// get admin level from user table
	public String getAdminLevel(String sqlCmd) {

		String alevel = null;
		try {

			conn = DriverManager.getConnection(url, this.user, this.pass);
			System.out.println("Connection " + conn.toString());
			PreparedStatement ps = conn.prepareStatement(sqlCmd);
			ResultSet result = ps.executeQuery();
			alevel = result.getString(1);
			System.out.println("level  " + result.toString() + "    " + result.getString(1));

			result.close();
			ps.close();
		} catch (SQLException sqle) {
			// do stuff on fail
			System.out.println(sqle.toString());
			sqle.printStackTrace();
		} catch (Exception e) {

			System.out.println(e.toString());
			e.printStackTrace();

		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}

		return alevel;
	}

	// get one integer value
	public String getSingleIntValue(String sqlCmd) {

		// String alevel = null;
		String count = null;
		try {

			conn = DriverManager.getConnection(url, this.user, this.pass);
			System.out.println("Connection " + conn.toString());
			PreparedStatement ps = conn.prepareStatement(sqlCmd);
			ResultSet result = ps.executeQuery();
			// alevel = result.getString(1);
			if (result.next()) {
				count = String.valueOf(result.getInt(1));
				// System.out.println("level "+result.toString()+"
				// "+result.getString(1));
			}
			result.close();
			ps.close();
		} catch (SQLException sqle) {
			// do stuff on fail
			System.out.println(sqle.toString());
			sqle.printStackTrace();
		} catch (Exception e) {

			System.out.println(e.toString());
			e.printStackTrace();

		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}

		return count;
	}

}