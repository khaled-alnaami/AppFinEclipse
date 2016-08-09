package edu.utd.cs.bdma.appfinn.client;

import com.google.gwt.user.client.rpc.IsSerializable;

public class User implements IsSerializable {
	private String username;
	private String password;
	private boolean grantAccess;

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
}