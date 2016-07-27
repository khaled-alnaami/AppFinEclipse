package edu.utd.cs.bdma.appfinn.regularServlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DataServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		Socket socket = null;
//	    OutputStreamWriter outToServer;
	    String serverMsg = null;
	    String inFromServerLine = "";
	    String input = request.getParameter("script");
	    System.out.println("input passed: " + input);
	    
		try {
			// contacting the server socket
			if (input.startsWith("python")){
				// data analytics
				// to Main2.java
				socket = new Socket("127.0.0.1", 9998);
			} else {
				// data collection
				// to Main.java
				System.out.println("inside socket");
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
			
			
			socket.setSoTimeout(0); 
			while ((inFromServerLine = inFromServer.readLine()) != null){
				System.out.println(inFromServerLine);
				serverMsg = inFromServerLine;
				if (serverMsg.startsWith("End")){
					inFromServer.close();
					break;
				}
			}
			
			
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
		
		response.getWriter().write(serverMsg);
		
//		response.getWriter().write("salam");
	}
}
