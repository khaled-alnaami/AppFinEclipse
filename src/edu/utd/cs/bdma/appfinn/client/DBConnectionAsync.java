package edu.utd.cs.bdma.appfinn.client;

import com.google.gwt.user.client.rpc.AsyncCallback;


public interface DBConnectionAsync {
	public void authenticateUser(String user, String pass, AsyncCallback<User> callback);
	public void insertOrUpdate(String sqlCmd, AsyncCallback<Boolean> callback);
	public void generateCodeSendEmail(String email, AsyncCallback<Boolean> callback);
	public void checkRecord(String sqlCmd, AsyncCallback<Boolean> callback);
}
