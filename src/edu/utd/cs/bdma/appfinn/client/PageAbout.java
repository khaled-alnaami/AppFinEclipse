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
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PageAbout extends Composite {
	VerticalPanel vPanel = new VerticalPanel();

	public PageAbout(){
		initWidget(this.vPanel);
		vPanel.setBorderWidth(1);
		
		vPanel.add(new HTML("<h1> Smart Phone Apps Fingerprinting </h1>"
							+ "<p>"
							+ "Apps Data Management is a tool designed by the UT Dallas Big Data Analytics and Management lab <br> which is directed by <a href=\"https://www.utdallas.edu/~lkhan/\" target=\"_blank\" >Professor Latifur Khan</a>."
							+ "</p>"
							
							+ "<p>"
							+ "The tool facilitates Data Collection/Dynamic Analysis and Data Analytics. Data Collection enables the user to install <br> mobile apps, open them, and collect traces (as TCP packets). Such traces are used as input vectors <br> for Data Analytics where different machine learning algorithms can be applied."
							+ "</p>"

							+ "<p>"
							+ "Please send email to <b> dml.utd@gmail.com </b> for any comments or concenrs. Thank you."
							+ "</p>"
				
				));
		
	
	}
	
}
