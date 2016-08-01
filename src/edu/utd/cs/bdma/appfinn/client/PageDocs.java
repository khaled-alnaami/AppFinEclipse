package edu.utd.cs.bdma.appfinn.client;

import java.util.Map;


import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesServicePb.ImagesCompositeRequest.Builder;
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
	Anchor aDyer;
	Anchor aKhaled;
	
	CaptionPanel docsCP = new CaptionPanel("Docs");
	
	public PageDocs(){
		initWidget(this.vPanel);
		
		aDyer = new Anchor();
		aDyer.getElement().getStyle().setCursor(Cursor.POINTER); 
		aDyer.setHTML("Peek-a-Boo, I Still See You: Why Efficient Traffic Analysis Countermeasures Fail");
		aDyer.addClickHandler(new aDyerClickHandler());
		
		aKhaled = new Anchor();
		aKhaled.setHTML("P2V: Effective Website Fingerprinting Using Vector Space Representations");
		aKhaled.addClickHandler(new aKhaledClickHandler());
		aKhaled.getElement().getStyle().setCursor(Cursor.POINTER); 
		
		vPanel.add(aDyer);
		vPanel.add(aKhaled);
		
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
			String fileInfo = "?filename=p2vKhaled.pdf";
			
			String url = link + docsServlet + fileInfo;
			
			Window.open( url, "_top", "status=0,toolbar=0,menubar=0,location=0");
			
		}
		
	}
	

}
