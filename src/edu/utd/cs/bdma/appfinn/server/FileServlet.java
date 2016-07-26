package edu.utd.cs.bdma.appfinn.server;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet(name="download",urlPatterns={"/appfin/download"})

public class FileServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String relativeLocation = "/appsData/";
		response.getWriter().write("inside FileServlet\n\n");

		String foldersLocation = getServletContext().getRealPath(relativeLocation);
		// System.out.println("inside doGet");
		File f = new File(foldersLocation);
		File[] files = f.listFiles();
		String[] fileNames = new String[files.length];
		int count = 0;
		for (File file : files) {
			fileNames[count] = file.getName();
			count++;
		}

		// obtains response's output stream
		ServletOutputStream outStream = response.getOutputStream();
		for (String fileName : fileNames) {
			outStream.write(fileName.getBytes("UTF-8"));
		}
		outStream.close();

		return;
	}

}