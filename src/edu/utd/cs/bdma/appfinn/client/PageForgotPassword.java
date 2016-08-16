package edu.utd.cs.bdma.appfinn.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//import com.google.appengine.api.images.Image;
//import com.google.appengine.api.images.ImagesServicePb.ImagesCompositeRequest.Builder;
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
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PageForgotPassword extends Composite {
	VerticalPanel vPanel = new VerticalPanel();
	VerticalPanel forgotPasswordPanel = new VerticalPanel();
	HorizontalPanel submitButtonPanel = new HorizontalPanel();

	Grid gWritePassword = new Grid(1, 2);

	Label emailLbl = new Label("Email");
	TextBox emailTxt = new TextBox();

	Button submitBtn = new Button("Submit");

	Button cancelBtn = new Button("Cancel");

	CaptionPanel cPanel = new CaptionPanel("New User");

	// Database
	private final DBConnectionAsync rpcDB = (DBConnectionAsync) GWT.create(DBConnection.class);

	AppFin entryPoint = null;

	// Validation
	ArrayList<Object> requiredControlsList = new ArrayList<Object>();
	HashMap<Object, String> validControlList = new HashMap<Object, String>();

	public PageForgotPassword(AppFin entryPoint) {
		this.entryPoint = entryPoint;
		initWidget(this.vPanel);

		// isDuplicateDBEntryTxt.setText("");

		cPanel.addStyleName("loginCP");

		setStyle();

		// empty line separator
		forgotPasswordPanel.add(new HTML("<table><tr>" + "<td colspan=\"100\">" + "</td>"
				+ "</tr><tr></tr><tr></tr><tr></tr><tr></tr></table>"));

		gWritePassword.setWidget(0, 0, emailLbl);
		gWritePassword.setWidget(0, 1, emailTxt);
		forgotPasswordPanel.add(gWritePassword);

		// empty line separator
		forgotPasswordPanel.add(new HTML("<table><tr>" + "<td colspan=\"100\">" + "</td>"
				+ "</tr><tr></tr><tr></tr><tr></tr><tr></tr></table>"));

		submitButtonPanel.setStyleName("center");

		submitBtn.addClickHandler(new submitBtnClickHandler());

		submitButtonPanel.add(submitBtn);

		cancelBtn.addClickHandler(new cancelBtnClickHandler());

		submitButtonPanel.add(cancelBtn);

		forgotPasswordPanel.add(submitButtonPanel);

		cPanel.add(forgotPasswordPanel);

		vPanel.addStyleName("center");
		vPanel.add(cPanel);

	}

	private void setStyle() {
		// TODO Auto-generated method stub
		// labels style
		emailLbl.setStyleName("Labels");

		// textboxes style
		emailTxt.setStyleName("TextBox");

		submitBtn.setStyleName("Button_White");
		cancelBtn.setStyleName("Button_White");

		submitBtn.getElement().getStyle().setCursor(Cursor.POINTER);
		cancelBtn.getElement().getStyle().setCursor(Cursor.POINTER);
	}

	private class submitBtnClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (validateData()) {
				 validateEmail();
			}

		}

		public boolean validateData() {
			// code to validate data entry
			// add the widgets (text boxes) to an array list

			// required fields
			if (requiredFieldDetected()) {
				Window.alert("Please fill in required fields.");
				return false;
			}

			// regular expression
			if (invalidFieldDetected()) {
				Window.alert("Please fix data format in marked fields.");
				return false;
			}

			return true;
		}

		private boolean requiredFieldDetected() {
			requiredControlsList = new ArrayList<Object>();

			requiredControlsList.add(emailTxt);

			if (Utils.CheckRequiredField(requiredControlsList))
				return true;

			return false;
		}

		private boolean invalidFieldDetected() {
			validControlList = new HashMap<Object, String>();

			validControlList.put(emailTxt, "email");

			if (Utils.CheckValidField(validControlList)) {
				return true;
			}

			return false;
		}

		void validateEmail() {
			String link = "http://" + Window.Location.getHostName() + ":" + Window.Location.getPort() + "/appfin/";
			String sqlCmd = "select * from tblUser where Email = '" + emailTxt.getText().trim() + "';";
			rpcDB.checkRecordSendEmail(sqlCmd, link, new AsyncCallback<Boolean>() {
				@Override
				public void onFailure(Throwable caught) {
					Window.alert("Error checking email in Database entry! Please contact Admin.");
				}

				@Override
				public void onSuccess(Boolean validEmail) {
					if (validEmail == true) {
						Window.alert("The username and password has been sent to your email.");
						RootPanel.get("test").remove(entryPoint.pageForgotPassword);
						RootPanel.get("test").add(entryPoint.loginParentPanel);
					} else {
						
					}
				}
			});

		}
		
	}

	private class cancelBtnClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			RootPanel.get("test").remove(entryPoint.pageNewUser);
			RootPanel.get("test").add(entryPoint.loginParentPanel);

		}
	}



	private void generateCodeSendEmail() {
		String link = "http://" + Window.Location.getHostName() + ":" + Window.Location.getPort() + "/appfin/";
		rpcDB.generateCodeSendEmail(emailTxt.getText().trim(), link, new AsyncCallback<Boolean>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error generating code! Please contact Admin.");
			}

			@Override
			public void onSuccess(Boolean code) {
				Window.alert("Please check your email, fill in the Email Code, and Verify.");
			}
		});

	}
}
