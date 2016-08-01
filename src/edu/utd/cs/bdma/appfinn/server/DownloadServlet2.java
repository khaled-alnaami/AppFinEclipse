package edu.utd.cs.bdma.appfinn.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet(name="download",urlPatterns={"/appfin/download"})

public class DownloadServlet2 extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String relativeLocation = "/files/";
		
		String foldersLocation = getServletContext().getRealPath(relativeLocation);

		try {
			String fileName = request.getParameter("filename");

			String filePath = foldersLocation+fileName;
			System.out.println("file path: " + filePath);
			File downloadFile = new File(filePath);//source_folder
//			response.getWriter().write("Zip file: " + downloadFile.getName());
			FileInputStream inStream = new FileInputStream(downloadFile);

			// if you want to use a relative path to context root:
			String relativePath = getServletContext().getRealPath("");
			//System.out.println("relativePath = " + relativePath);

			// obtains ServletContext
			ServletContext context = getServletContext();
			
			// gets MIME type of the file
			String mimeType = context.getMimeType(filePath);
			if (mimeType == null) {
				// set to binary type if MIME mapping not found
				mimeType = "application/octet-stream";
			}
			System.out.println("MIME type: " + mimeType);
			
			// modifies response
			response.setContentType(mimeType);
			response.setContentLength((int) downloadFile.length());
			
			// forces download
			
			
			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
//			String headerValue = "attachment; filename=" + foldersLocation+downloadFile.getName();
//			String headerValue = "attachment; filename=" + downloadFile.getName();
//			String headerValue = "attachment; filename=" + "2016.zip";
//			response.setContentType("application/zip");
			response.setHeader(headerKey, headerValue);

			// obtains response's output stream
			ServletOutputStream outStream = response.getOutputStream();

			byte[] buffer = new byte[4096];
			int bytesRead = -1;

			while ((bytesRead = inStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, bytesRead);
			}

			inStream.close();
//			outStream.flush();
			outStream.close();
			
		} catch (Exception e) {
			response.getWriter().write("\nException: \n");
			response.getWriter().write(e.toString());
		}

		return;
	}
	
	
}
