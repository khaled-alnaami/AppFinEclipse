package edu.utd.cs.bdma.appfinn.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("database")
public interface DBConnection extends RemoteService {
	public User authenticateUser(String user, String pass);
	public Boolean insertOrUpdate(String sqlCmd);
}