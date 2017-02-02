package edu.utd.cs.bdma.appfinn.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.MalformedURLException;
//import java.net.URL;
//import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dev.json.JsonString;
//import com.google.appengine.api.images.Image;
//import com.google.appengine.api.images.ImagesServicePb.ImagesCompositeRequest.Builder;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;


public class StaticAnalysis extends Composite {
	
	VerticalPanel vPanel = new VerticalPanel();
	Anchor aUserManual;
	
	String FirstName = "f";
	String LastName = "l";
	String Email = "e";
	String Password = "p";
	String Username = "u";
	public String userFields = "?";


	private final DBConnectionAsync rpcDB = (DBConnectionAsync) GWT.create(DBConnection.class);

	
	//CaptionPanel docsCP = new CaptionPanel("StatAnalysis");
	private Logger logger = Logger.getLogger("");
	
	public StaticAnalysis()
	
	{
		initWidget(this.vPanel);
		vPanel.setBorderWidth(1);
		
		aUserManual = new Anchor();
		aUserManual.setHTML("<p> &nbsp; Go To Static Analysis Page &nbsp; </p>");
		aUserManual.addClickHandler(new aUserManualClickHandler());
		aUserManual.getElement().getStyle().setCursor(Cursor.POINTER); 
		
		//Username = username;
		//Password = pass;
		
		// Amir comment one line
		//getUserDetails(Username, Password);		
		vPanel.add(aUserManual);
				
	}
	public void getUserDetails(String username, String pass){
	    
    	String sqlCmd = "select FirstName, LastName, Email from tblUser where username = \"" + username + "\" AND " + "userpassword = \"" + pass + "\"";
    	String columnNames[] = {"FirstName", "LastName", "Email"};

    	rpcDB.getFields(sqlCmd, columnNames,new AsyncCallback<String[]>(){
    		@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Window.alert("Database connection failure! Please contact admin!");
			}

			@Override
			public void onSuccess(String[] result) 
			{
				// TODO Auto-generated method stub
				//Window.alert("user Exists!");
				//userFields = Arrays.toString(result);
				
				FirstName = result[0];
				LastName = result[1];
				Email = result[2];
			}

			
			    		
    	});}
	
	StringBuffer postData = new StringBuffer();

	private class aUserManualClickHandler implements ClickHandler {

		//String url = "http://130.211.195.191:3030/login";
		//String boxurl = "http://130.211.195.191:3030/login?email="+URL.encode(Email)+"&password="+URL.encode(Password)+"&fname="+URL.encode(FirstName)+"&lname="+URL.encode(LastName);
		//RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, URL.encode(url));
		
			
		@Override
		public void onClick(ClickEvent event) {

			
			String link = "http://130.211.195.191:3030";
			Window.open( link, "_top", "status=0,toolbar=0,menubar=0,location=0");

	
		    

		}}}
	
	

