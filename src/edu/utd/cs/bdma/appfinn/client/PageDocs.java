package edu.utd.cs.bdma.appfinn.client;

import java.util.Map;


//import com.google.appengine.api.images.Image;
//import com.google.appengine.api.images.ImagesServicePb.ImagesCompositeRequest.Builder;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PageDocs extends Composite {
	VerticalPanel vPanel = new VerticalPanel();
	Anchor aUserManual;
	Anchor aDyer;
	Anchor aKhaled;
	Anchor aKhaled2;
	
	
	CaptionPanel docsCP = new CaptionPanel("Docs");
	
	public PageDocs(){
		initWidget(this.vPanel);
		vPanel.setBorderWidth(1);
		
		aUserManual = new Anchor();
		aUserManual.setHTML("<p> &nbsp; User Manual &nbsp; </p>");
		aUserManual.addClickHandler(new aUserManualClickHandler());
		aUserManual.getElement().getStyle().setCursor(Cursor.POINTER); 
		
		aDyer = new Anchor();
		aDyer.getElement().getStyle().setCursor(Cursor.POINTER); 
		aDyer.setHTML("<p> &nbsp; Paper: Peek-a-Boo, I Still See You: Why Efficient Traffic Analysis Countermeasures Fail &nbsp; </p>");
		aDyer.addClickHandler(new aDyerClickHandler());
		
		aKhaled = new Anchor();
		aKhaled.setHTML("<p> &nbsp; Paper: Adaptive Encrypted Traffic Fingerprinting With Bi-Directional Dependence  &nbsp; </p>");
		aKhaled.addClickHandler(new aKhaledClickHandler());
		aKhaled.getElement().getStyle().setCursor(Cursor.POINTER); 
	
		aKhaled2 = new Anchor();
		aKhaled2.setHTML("<p> &nbsp; Paper: P2V: Effective Website Fingerprinting Using Vector Space Representations &nbsp; </p>");
		aKhaled2.addClickHandler(new aKhaled2ClickHandler());
		aKhaled2.getElement().getStyle().setCursor(Cursor.POINTER); 
		
		vPanel.add(aUserManual);
		vPanel.add(aKhaled);
		vPanel.add(aKhaled2);
		vPanel.add(aDyer);
				
	}
	
	private class aUserManualClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			String link = "http://"+Window.Location.getHostName()+":"+Window.Location.getPort()+"/appfin/";
			String docsServlet = "downloadDocs";
			String fileInfo = "?filename=Documentation.pdf";
			
			String url = link + docsServlet + fileInfo;
			
			Window.open( url, "_top", "status=0,toolbar=0,menubar=0,location=0");
			
		}
	}
	
	private class aDyerClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			
			String link = "http://"+Window.Location.getHostName()+":"+Window.Location.getPort()+"/appfin/";
			String docsServlet = "downloadDocs";
			String fileInfo = "?filename=peekabooDyer.pdf";
			
			String url = link + docsServlet + fileInfo;
			
			Window.open( url, "_top", "status=0,toolbar=0,menubar=0,location=0");
			
		}	
	}
	
	private class aKhaledClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			String link = "http://"+Window.Location.getHostName()+":"+Window.Location.getPort()+"/appfin/";
			String docsServlet = "downloadDocs";
			String fileInfo = "?filename=ACSAC16_Traffic_Analysis.pdf";
			
			String url = link + docsServlet + fileInfo;
			
			Window.open( url, "_top", "status=0,toolbar=0,menubar=0,location=0");
			
		}
	}
	
	private class aKhaled2ClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			String link = "http://"+Window.Location.getHostName()+":"+Window.Location.getPort()+"/appfin/";
			String docsServlet = "downloadDocs";
			String fileInfo = "?filename=p2vKhaled.pdf";
			
			String url = link + docsServlet + fileInfo;
			
			Window.open( url, "_top", "status=0,toolbar=0,menubar=0,location=0");
			
		}
	}
	
}
