package edu.utd.cs.bdma.appfinn.server;

import edu.utd.cs.bdma.appfinn.client.GreetingService;
import edu.utd.cs.bdma.appfinn.shared.FieldVerifier;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements GreetingService {

	final static String fileLocation = "/appsData/";
	
	public String greetServer(String input) throws IllegalArgumentException {

		Socket socket = null;
//	    OutputStreamWriter outToServer;
	    String serverMsg = null;
	    String inFromServerLine = "";
	    
		try {
			// contacting the server socket
			if (input.startsWith("python")){
				// data analytics
				// to Main2.java
				socket = new Socket("127.0.0.1", 9998);
			} else {
				// data collection
				// to Main.java
				socket = new Socket("127.0.0.1", 9999);
			}
			
			
//			outToServer =new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
			PrintStream PS = new PrintStream(socket.getOutputStream());
	        // forwarding the apps to the server
//			outToServer.write(input, 0, input.length());
//			outToServer.flush();
	        PS.println(input);
	        
			// read
			// start reading msg from server
		    BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			
//			socket.setSoTimeout(0); 
//			while ((inFromServerLine = inFromServer.readLine()) != null){
//				System.out.println(inFromServerLine);
//				serverMsg = inFromServerLine;
//				if (serverMsg.startsWith("End")){
//					inFromServer.close();
//					break;
//				}
//			}
			
//		    socket.setSoTimeout(0);
//			inFromServerLine = inFromServer.readLine();
//
//			inFromServer.close();
			// end reading msg from server
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
	        try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
		
//		return "success!!";
		return inFromServerLine;
	}
	
	// modified on 10/26/2016
	public String[] getFileNames(String username){
        /*
		File folder = new File(getServletContext().getRealPath(fileLocation)); ///changed by sayeed
//		File folder = new File(fileLocation);
		File[] listOfFiles = folder.listFiles();
		String[] fileNames = new String[listOfFiles.length];
		fileNames[0] = "before getting file names";
		int count = 0;
		
		for (File file : listOfFiles) {
			// 10/26/2016
			String[] fileNameList = file.getName().split("_");
			if (fileNameList.length == 2 && username.equalsIgnoreCase(fileNameList[1])){
				fileNames[count] = file.getName();
				count++;
			}
		}
		*/
		File folder = new File(getServletContext().getRealPath(fileLocation)); ///changed by sayeed
//		File folder = new File(fileLocation);
		File[] listOfFiles = folder.listFiles();
		List<String> fileNames = new ArrayList<>();
		
		for (File file : listOfFiles) {
			String[] fileNameList = file.getName().split("_");
			if (fileNameList.length == 2 && username.equalsIgnoreCase(fileNameList[1])){
				fileNames.add(file.getName());
			}
		}
		
		if (fileNames.size() == 0)
			fileNames.add(""); // dummy empty string

		// convert the arraylist to a string array
		String[] fileNamesArray = fileNames.toArray(new String[fileNames.size()]);
		return fileNamesArray;

	}

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}
}
