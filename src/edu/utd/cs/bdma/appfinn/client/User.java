package edu.utd.cs.bdma.appfinn.client;

import com.google.gwt.user.client.rpc.IsSerializable;

public class User implements IsSerializable {
	private String username;
	private String password;
	private int userID;
	private boolean grantAccess;
	private String adminLevel; // survey admin page

	@SuppressWarnings("unused")
	private User() {
		// just here because GWT wants it.
	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
		this.grantAccess = false;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isGrantAcecss() {
		return grantAccess;
	}

	public void setGrantAccess(boolean grantAcess) {
		this.grantAccess = grantAcess;
	}

	// changes for survey admin page
	public String getAdminLevel() {
		return adminLevel;
	}

	public void setAdminLevel(String adminLevel) {
		this.adminLevel = adminLevel;
	}
}
