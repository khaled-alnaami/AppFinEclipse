package edu.utd.cs.bdma.appfinn.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet(name="download",urlPatterns={"/appfin/download"})

public class SocketServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String input = request.getParameter("input");
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
		ServletOutputStream outStream = response.getOutputStream();
			outStream.write(inFromServerLine.getBytes("UTF-8"));
	}
	
	
}