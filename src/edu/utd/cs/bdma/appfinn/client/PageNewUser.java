package edu.utd.cs.bdma.appfinn.client;

import java.util.Map;


import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesServicePb.ImagesCompositeRequest.Builder;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;


public class PageNewUser extends Composite {
	VerticalPanel vPanel = new VerticalPanel();
	VerticalPanel newUserPanel = new VerticalPanel();
	HorizontalPanel submitButtonPanel = new HorizontalPanel();
	
	Grid g = new Grid(7, 2);
	Label fnameLbl = new Label("First Name");
	TextBox fnameTxt = new TextBox();

	Label lnameLbl = new Label("Last Name");
	TextBox lnameTxt = new TextBox();
	
	Label phoneLbl = new Label("Phone");
	TextBox phoneTxt = new TextBox();

	Label emailLbl = new Label("Email");
	TextBox emailTxt = new TextBox();
	
	Label userNameLbl = new Label("User Name");
	TextBox userNameTxt = new TextBox();
	
	Label passwordLbl = new Label("Password");
	PasswordTextBox passwordTxt = new PasswordTextBox();
	
	Label passwordLbl2 = new Label("Confirm Password");
	PasswordTextBox passwordTxt2 = new PasswordTextBox();
		
	Button submitBtn = new Button("Submit");
	
	CaptionPanel newUserCP = new CaptionPanel("New User");
	
	Anchor aCancel = new Anchor();
		
	// Database
	private final DBConnectionAsync rpcDB = (DBConnectionAsync) GWT.create(DBConnection.class);
	
	AppFin entryPoint = null;
	
	public PageNewUser(AppFin entryPoint){
		this.entryPoint = entryPoint;
		initWidget(this.vPanel);
//		vPanel.setBorderWidth(1);
		newUserCP.addStyleName("loginCP");
		
		g.setWidget(0, 0, fnameLbl);
		g.setWidget(0, 1, fnameTxt);
		
		g.setWidget(1, 0, lnameLbl);
		g.setWidget(1, 1, lnameTxt);
		
		g.setWidget(2, 0, phoneLbl);
		g.setWidget(2, 1, phoneTxt);
		
		g.setWidget(3, 0, emailLbl);
		g.setWidget(3, 1, emailTxt);
		
		g.setWidget(4, 0, userNameLbl);
		g.setWidget(4, 1, userNameTxt);
		
		g.setWidget(5, 0, passwordLbl);
		g.setWidget(5, 1, passwordTxt);
	
		g.setWidget(6, 0, passwordLbl2);
		g.setWidget(6, 1, passwordTxt2);
		
		newUserPanel.add(g);
		
		submitButtonPanel.setStyleName("center");
		
		submitBtn.addClickHandler(new submitBtnClickHandler());
		
		submitButtonPanel.add(submitBtn);
		
		aCancel.setHTML("<p> &nbsp; Cancel </p>");
		aCancel.addClickHandler(new aCancelClickHandler());
		aCancel.getElement().getStyle().setCursor(Cursor.POINTER); 
		submitButtonPanel.add(aCancel);
		
		newUserPanel.add(submitButtonPanel);
		newUserCP.add(newUserPanel);
		
		vPanel.addStyleName("center");
		vPanel.add(newUserCP);
				
	}
	
	private class submitBtnClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			if (validateData()){
				insert();
			}
			
		}
		
		public boolean validateData(){
			// code to validate data entry
			return true;
		}
		
		public void insert(){
			String FirstName = "'" + fnameTxt.getText().trim() + "',";
			String LastName = "'" + lnameTxt.getText().trim() + "',";
			String Phone = "'" + phoneTxt.getText().trim() + "',";
			String Email = "'" + emailTxt.getText().trim() + "',";
			String UserName = "'" + userNameTxt.getText().trim() + "',";
			String password = "'" + passwordTxt.getText().trim() + "',";
			String AdminLevel = "'5'";
					
			// id is incremented automatically
			String sqlCmd = "insert into tblUser "
					      + "(FirstName, "
					      + "LastName, "
					      + "Phone, "
					      + "Email, "
					      + "UserName, "
						  + "UserPassword, "
						  + "AdminLevel) "
						  + "values "
						  + "("
						  + FirstName
						  + LastName
						  + Phone
						  + Email
						  + UserName
						  + password
						  + AdminLevel
						  + ");"
						  ;
//					"insert into tblUser values ('2', 'dml2', 'dmllab2', '9728834137', 'dml.utd@gmail.com', 'dml2', 'dml212345', '1');";
			
			
			System.out.println("insider PageNewUser.insert()");
			rpcDB.insertOrUpdate(sqlCmd, new AsyncCallback<Boolean>() {
				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					Window.alert("Database connection failure! Please contact admin!");
				}

				@Override
				public void onSuccess(Boolean validInsert) {
					// TODO Auto-generated method stub
					if (validInsert == true) {
						Window.alert("Record inserted successfully!.");
						RootPanel.get("test").remove(entryPoint.pageNewUser);
						RootPanel.get("test").add(entryPoint.loginParentPanel);
					} else {
						Window.alert("Record insert error!.");
						Window.Location.reload();
					}
					
				}
			});
		}
	}

	
	
	private class aCancelClickHandler implements ClickHandler {
		
		@Override
		public void onClick(ClickEvent event) {
			
			RootPanel.get("test").remove(entryPoint.pageNewUser);
			RootPanel.get("test").add(entryPoint.loginParentPanel);
			
		}
	}
}
