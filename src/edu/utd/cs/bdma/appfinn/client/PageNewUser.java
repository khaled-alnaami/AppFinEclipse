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

public class PageNewUser extends Composite {
	VerticalPanel vPanel = new VerticalPanel();
	VerticalPanel newUserCPPanel = new VerticalPanel();
	VerticalPanel newUserPanel = new VerticalPanel();
	HorizontalPanel submitButtonPanel = new HorizontalPanel();
	VerticalPanel loadingImagePanel = new VerticalPanel();

	Grid g = new Grid(7, 2);
	Grid gValidateCode = new Grid(1, 2);

	Label fnameLbl = new Label("First Name");
	TextBox fnameTxt = new TextBox();

	Label lnameLbl = new Label("Last Name");
	TextBox lnameTxt = new TextBox();

	Label phoneLbl = new Label("Phone");
	TextBox phoneTxt = new TextBox();

	Label emailLbl = new Label("Email");
	TextBox emailTxt = new TextBox();

	Label userNameLbl = new Label("Username");
	TextBox userNameTxt = new TextBox();

	Label passwordLbl = new Label("Password");
	PasswordTextBox passwordTxt = new PasswordTextBox();

	Label passwordLbl2 = new Label("Confirm Password");
	PasswordTextBox passwordTxt2 = new PasswordTextBox();

	Label emailCodeLbl = new Label("Email Code");
	TextBox emailCodeTxt = new TextBox();

	// loading image
	Image loadingImage = new Image();

	Button submitBtn = new Button("Submit");

	Button cancelBtn = new Button("Cancel");
	
	Button generateCodeBtn = new Button("Generate Code");
	
	Button verifyBtn = new Button("Verify");

	CaptionPanel newUserCP = new CaptionPanel("New User");

	// Database
	private final DBConnectionAsync rpcDB = (DBConnectionAsync) GWT.create(DBConnection.class);

	AppFin entryPoint = null;

	// Validation
	ArrayList<Object> requiredControlsList = new ArrayList<Object>();
	HashMap<Object, String> validControlList = new HashMap<Object, String>();

	String emailCode = "";

//	TextBox isDuplicateDBEntryTxt = new TextBox();

	public PageNewUser(AppFin entryPoint) {
		this.entryPoint = entryPoint;
		initWidget(this.vPanel);

//		isDuplicateDBEntryTxt.setText("");

		newUserCP.addStyleName("loginCP");

		setStyle();

		// empty line seperator
		newUserPanel.add(new HTML("<table><tr>" + "<td colspan=\"100\">" + "</td>"
				+ "</tr><tr></tr><tr></tr><tr></tr><tr></tr></table>"));

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

		gValidateCode.setWidget(0, 0, emailCodeLbl);
		gValidateCode.setWidget(0, 1, emailCodeTxt);
		gValidateCode.setVisible(false);
		newUserPanel.add(gValidateCode);

		// empty line separator
		newUserPanel.add(new HTML("<table><tr>" + "<td colspan=\"100\">" + "</td>"
				+ "</tr><tr></tr><tr></tr><tr></tr><tr></tr></table>"));

		submitButtonPanel.setStyleName("center");

		submitBtn.addClickHandler(new submitBtnClickHandler());

		submitButtonPanel.add(submitBtn);

		cancelBtn.addClickHandler(new cancelBtnClickHandler());

		verifyBtn.setVisible(false);
		submitButtonPanel.add(verifyBtn);
		verifyBtn.addClickHandler(new verifyBtnClickHandler());

		submitButtonPanel.add(cancelBtn);

		newUserPanel.add(submitButtonPanel);
		newUserCPPanel.add(newUserPanel);

		// set image source
		loadingImage.setUrl("images/loading.gif");
		loadingImagePanel.add(loadingImage);
		loadingImagePanel.setVisible(false);
		newUserCPPanel.add(loadingImagePanel);

//		newUserCPPanel.add(isDuplicateDBEntryTxt);
		generateCodeBtn.setVisible(false);
		newUserCPPanel.add(generateCodeBtn);
		generateCodeBtn.addClickHandler(new generateCodeBtnClickHandler());

		newUserCP.add(newUserCPPanel);

		vPanel.addStyleName("center");
		vPanel.add(newUserCP);

	}

	private void setStyle() {
		// TODO Auto-generated method stub
		// labels style
		fnameLbl.setStyleName("Labels");
		lnameLbl.setStyleName("Labels");
		phoneLbl.setStyleName("Labels");
		emailLbl.setStyleName("Labels");
		userNameLbl.setStyleName("Labels");
		passwordLbl.setStyleName("Labels");
		passwordLbl2.setStyleName("Labels");
		emailCodeLbl.setStyleName("Labels");

		// textboxes style
		fnameTxt.setStyleName("TextBox");
		lnameTxt.setStyleName("TextBox");
		emailTxt.setStyleName("TextBox");
		phoneTxt.setStyleName("TextBox");
		userNameTxt.setStyleName("TextBox");
		passwordTxt.setStyleName("TextBox");
		passwordTxt2.setStyleName("TextBox");
		emailCodeTxt.setStyleName("TextBox");

		submitBtn.setStyleName("Button_White");
		cancelBtn.setStyleName("Button_White");
		verifyBtn.setStyleName("Button_White");

		submitBtn.getElement().getStyle().setCursor(Cursor.POINTER);
		cancelBtn.getElement().getStyle().setCursor(Cursor.POINTER);
		verifyBtn.getElement().getStyle().setCursor(Cursor.POINTER);
	}

	private class submitBtnClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (validateData()) {
				validateDuplicateDBRecords();
//				if (validateDuplicateDBRecords()) {
//					validateEmailCode();
//				}
			}

		}

		private void validateDuplicateDBRecords() {
			// check database if same email or username
			// if (duplicateDBEntryDetected().equals("YES")) {
			// Window.alert("Email and/or username have been already used.
			// Please use (an)other one(s).");
			// return false;
			// }

			duplicateDBEntryDetected();

//			while (!isDuplicateDBEntryTxt.getText().equals("YES")) {
//				// wait until duplicateDBEntryDetected async call is done
//				if (newUserPanel.isVisible() || isDuplicateDBEntryTxt.getText().equals("YES")){
//					break;
//				}
//			}
//
//			if (isDuplicateDBEntryTxt.getText().trim().equals("YES")) {
//				Window.alert("Email and/or username have been already used. Please use (an)other one(s).");
//				newUserPanel.setVisible(true);
//				loadingImagePanel.setVisible(false);
//				return false;
//			}
//			return true;
		}

//		private void validateEmailCode() {
//			submitBtn.setVisible(false);
//			g.setVisible(false);
//			gValidateCode.setVisible(true);
//			verifyBtn.setVisible(true);
//
//			generateCodeSendEmail();
//
//			// sendEmail();
//			// checkCode(); button
//
//			// Window.alert("Please check your email, fill in the Email Code,
//			// and Verify.");
//			// return false;
//		}
//
//		private void generateCodeSendEmail() {
//			String link = "http://" + Window.Location.getHostName() + ":" + Window.Location.getPort() + "/appfin/";
//			rpcDB.generateCodeSendEmail(emailTxt.getText().trim(), link, new AsyncCallback<Boolean>() {
//				@Override
//				public void onFailure(Throwable caught) {
//					Window.alert("Error generating code! Please contact Admin.");
//				}
//
//				@Override
//				public void onSuccess(Boolean code) {
//					Window.alert("Please check your email, fill in the Email Code, and Verify.");
//				}
//			});
//
//		}

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
				String alertStr = "Please fix data format in marked fields.\n\n";
				alertStr += "First Name, Last Name, and Username:\n";
				alertStr += "--------------------\n";
				alertStr += "Allowed: lower case, 0-9, underscore, or hyphen.\n";
				alertStr += "Length: at least 3 characters and maximum length of 15.\n\n";
				alertStr += "Password:\n";
				alertStr += "---------\n";
				alertStr += "Must contain a mix of: digits, lower case, upper case, and special characters.\n";
				alertStr += "Length: at least 8 characters.";
				
				Window.alert(alertStr);
				return false;
			}

			if (passwordMismatch()) {
				Window.alert("Please make sure both passwords match.");
				return false;
			}

			return true;
		}

		private void duplicateDBEntryDetected() {

			System.out.println("inside duplicate");
			String sqlCmd = "select * from tblUser where Email = '" + emailTxt.getText().trim() + "' or UserName = '"
					+ userNameTxt.getText().trim() + "';";
			System.out.println("duplicate sqlCmd: " + sqlCmd);

			newUserPanel.setVisible(false);
			loadingImagePanel.setVisible(true);

			rpcDB.checkRecord(sqlCmd, new AsyncCallback<Boolean>() {

				@Override
				public void onFailure(Throwable caught) {
					Window.alert("Error checking duplicate Database entry! Please contact Admin.");
					newUserPanel.setVisible(true);
					loadingImagePanel.setVisible(false);
				}

				@Override
				public void onSuccess(Boolean duplicateEntry) {
					if (duplicateEntry == true) {
						Window.alert("Email and/or username have been already used. Please use (an)other one(s).");
					} else {
						// fire button click
						generateCodeBtn.click();
					}
					newUserPanel.setVisible(true);
					loadingImagePanel.setVisible(false);
				}

			});
			
			// newUserPanel.setVisible(false);
			// loadingImagePanel.setVisible(true);

			// emailCodeTxt.setText(((Boolean)isDuplicateDBEntry).toString());
			// emailCodeTxt.setText("gv" +
			// ((Boolean)gv.isDuplicateDBEntry()).toString());
			// emailCodeTxt.setText(Double.toString(isDupDouble[1]));
			// if (emailCodeTxt.getText().trim().equals("inside onSuccess")){
			// isDuplicateDBEntry = true;
			// emailCodeTxt.getText().trim().equals("inside onSuccess");
			// }
			// return isDuplicateDBEntry;
		}

		private boolean requiredFieldDetected() {
			requiredControlsList = new ArrayList<Object>();

			requiredControlsList.add(fnameTxt);
			requiredControlsList.add(lnameTxt);
			requiredControlsList.add(emailTxt);
			requiredControlsList.add(userNameTxt);
			requiredControlsList.add(passwordTxt);
			requiredControlsList.add(passwordTxt2);

			if (Utils.CheckRequiredField(requiredControlsList))
				return true;

			return false;
		}

		private boolean invalidFieldDetected() {
			validControlList = new HashMap<Object, String>();

			validControlList.put(fnameTxt, "name");
			validControlList.put(lnameTxt, "name");

			if (!phoneTxt.getText().trim().isEmpty())
				validControlList.put(phoneTxt, "phone");

			validControlList.put(emailTxt, "email");

			validControlList.put(userNameTxt, "name");

			validControlList.put(passwordTxt, "pass");
			validControlList.put(passwordTxt2, "pass");

			if (Utils.CheckValidField(validControlList)) {
				return true;
			}

			return false;
		}

		private boolean passwordMismatch() {
			if (!passwordTxt.getText().equals(passwordTxt2.getText()))
				return true;

			return false;
		}
		/***
		 * public void insert() { String FirstName = "'" +
		 * fnameTxt.getText().trim() + "',"; String LastName = "'" +
		 * lnameTxt.getText().trim() + "',"; String Phone = "'" +
		 * phoneTxt.getText().trim() + "',"; String Email = "'" +
		 * emailTxt.getText().trim() + "',"; String UserName = "'" +
		 * userNameTxt.getText().trim() + "',"; String password = "'" +
		 * passwordTxt.getText().trim() + "',"; String AdminLevel = "'5'";
		 * 
		 * // id is incremented automatically String sqlCmd = "insert into
		 * tblUser " + "(FirstName, " + "LastName, " + "Phone, " + "Email, " +
		 * "UserName, " + "UserPassword, " + "AdminLevel) " + "values " + "(" +
		 * FirstName + LastName + Phone + Email + UserName + password +
		 * AdminLevel + ");"; // "insert into tblUser values ('2', 'dml2',
		 * 'dmllab2', // '9728834137', 'dml.utd@gmail.com', 'dml2', 'dml212345',
		 * '1');";
		 * 
		 * System.out.println("inside PageNewUser.insert()");
		 * rpcDB.insertOrUpdate(sqlCmd, new AsyncCallback<Boolean>() {
		 * 
		 * @Override public void onFailure(Throwable caught) { // TODO
		 *           Auto-generated method stub Window.alert("Database
		 *           connection failure! Please contact admin!"); }
		 * 
		 * @Override public void onSuccess(Boolean validInsert) { // TODO
		 *           Auto-generated method stub if (validInsert == true) {
		 *           Window.alert("Record inserted successfully!.");
		 *           RootPanel.get("test").remove(entryPoint.pageNewUser);
		 *           RootPanel.get("test").add(entryPoint.loginParentPanel); }
		 *           else { Window.alert("Record insert error!."); //
		 *           Window.Location.reload(); }
		 * 
		 *           } }); }
		 **/
	}

	private class cancelBtnClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			RootPanel.get("test").remove(entryPoint.pageNewUser);
			RootPanel.get("test").add(entryPoint.loginParentPanel);

		}
	}

	private class verifyBtnClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			requiredControlsList = new ArrayList<Object>();
			requiredControlsList.add(emailCodeTxt);
			if (Utils.CheckRequiredField(requiredControlsList)) {
				Window.alert("Please fill in required field(s).");
			} else {
				String sqlCmd = "select * from tblCodes where Code = '" + emailCodeTxt.getText().trim()
						+ "' and Email = '" + emailTxt.getText().trim() + "';";
				rpcDB.checkRecord(sqlCmd, new AsyncCallback<Boolean>() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Error checking code! Please contact Admin.");
					}

					@Override
					public void onSuccess(Boolean codeCorrect) {
						if (codeCorrect) {
							insert();

						} else {
							Window.alert("Not a valid code.");
						}
					}
				});
			}

		}

		public void insert() {
			String FirstName = "'" + fnameTxt.getText().trim() + "',";
			String LastName = "'" + lnameTxt.getText().trim() + "',";
			String Phone = "'" + phoneTxt.getText().trim() + "',";
			String Email = "'" + emailTxt.getText().trim() + "',";
			String UserName = "'" + userNameTxt.getText().trim() + "',";
			String password = "'" + passwordTxt.getText().trim() + "',";
			String AdminLevel = "'5'";

			// id is incremented automatically
			String sqlCmd = "insert into tblUser " + "(FirstName, " + "LastName, " + "Phone, " + "Email, "
					+ "UserName, " + "UserPassword, " + "AdminLevel) " + "values " + "(" + FirstName + LastName + Phone
					+ Email + UserName + password + AdminLevel + ");";
			// "insert into tblUser values ('2', 'dml2', 'dmllab2',
			// '9728834137', 'dml.utd@gmail.com', 'dml2', 'dml212345', '1');";

			System.out.println("inside PageNewUser.insert()");
			rpcDB.insertOrUpdate(sqlCmd, new AsyncCallback<Boolean>() {
				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					Window.alert("Database connection failure! Please contact Admin.");
				}

				@Override
				public void onSuccess(Boolean validInsert) {
					// TODO Auto-generated method stub
					if (validInsert == true) {
						Window.alert("Record inserted successfully!.");
						RootPanel.get("test").remove(entryPoint.pageNewUser);
						RootPanel.get("test").add(entryPoint.loginParentPanel);

					} else {
						Window.alert("Record insert error! Please contact Admin.");
						// Window.Location.reload();
					}

				}
			});
		}
	}
	
	private class generateCodeBtnClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			validateEmailCode();
		}
	}
	
	private void validateEmailCode() {
		submitBtn.setVisible(false);
		g.setVisible(false);
		gValidateCode.setVisible(true);
		verifyBtn.setVisible(true);

		generateCodeSendEmail();

		// sendEmail();
		// checkCode(); button

		// Window.alert("Please check your email, fill in the Email Code,
		// and Verify.");
		// return false;
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
