package edu.utd.cs.bdma.appfinn.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void greetServer(String input, AsyncCallback<String> callback) throws IllegalArgumentException;

	void getFileNames(String username, AsyncCallback<String[]> callback);
}
