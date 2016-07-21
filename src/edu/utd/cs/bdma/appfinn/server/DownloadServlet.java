package edu.utd.cs.bdma.appfinn.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet(name="download",urlPatterns={"/appfin/download"})

public class DownloadServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String relativeLocation = "/appsData/";
		response.getWriter().write("inside DownloadServlet\n\n");
		
		String foldersLocation = getServletContext().getRealPath(relativeLocation);
		//System.out.println("inside doGet");
		File f = new File(foldersLocation);
		f.listFiles();
		try {
			String folderName = request.getParameter("foldername");
			//get source folder from foldername
			
			ZipUtils appZip = new ZipUtils();
			appZip.OUTPUT_ZIP_FILE = "WEB-INF/Files.zip";
			appZip.SOURCE_FOLDER = foldersLocation+folderName; 
			response.getWriter().write("SOURCE_FOLDER " + appZip.SOURCE_FOLDER);
			appZip.generateFileList(new File(appZip.SOURCE_FOLDER));
			appZip.zipIt(appZip.OUTPUT_ZIP_FILE);
			
			String filePath = "WEB-INF/Files.zip";
			File downloadFile = new File(filePath);//source_folder
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
			response.setHeader(headerKey, headerValue);

			// obtains response's output stream
			ServletOutputStream outStream = response.getOutputStream();

			byte[] buffer = new byte[4096];
			int bytesRead = -1;

			while ((bytesRead = inStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, bytesRead);
			}

			inStream.close();
			outStream.close();
		} catch (Exception e) {
			response.getWriter().write("\nException: \n");
			response.getWriter().write(e.toString());
		}
		
		return;
	}
	
	
}
