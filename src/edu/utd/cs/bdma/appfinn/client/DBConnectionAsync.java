package edu.utd.cs.bdma.appfinn.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;


public interface DBConnectionAsync {
	public void authenticateUser(String user, String pass, AsyncCallback<User> callback);
	public void insertOrUpdate(String sqlCmd, AsyncCallback<Boolean> callback);
	public void generateCodeSendEmail(String email, String link, AsyncCallback<Boolean> callback);
	public void checkRecord(String sqlCmd, AsyncCallback<Boolean> callback);
	public void getFields(String sqlCmd, String [] columnNames, AsyncCallback<String[]> callback);
	public void checkRecordSendEmail(String email, String link, AsyncCallback<Boolean> callback);
	public void getFieldsSurvey(String sqlCmd, AsyncCallback<List<Question>> callback);
	public void InsertAllRecords(int UserID, List<Question> AllAnswers, AsyncCallback<Boolean> callback);
	
	public void getSurveyStatistics(String sqlCmd, AsyncCallback<List<SurevyStats>> callback);
	public void getSingleIntValue(String sqlCmd, AsyncCallback<String> callback);
	
}
