package edu.utd.cs.bdma.appfinn.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import edu.utd.cs.bdma.appfinn.client.Question;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("database")
public interface DBConnection extends RemoteService {
	public User authenticateUser(String user, String pass);
	public Boolean insertOrUpdate(String sqlCmd);
	public Boolean generateCodeSendEmail(String email, String link);
	public Boolean checkRecord(String sqlCmd);
	public String [] getFields(String sqlCmd, String [] columnNames);
	public Boolean checkRecordSendEmail(String email, String link);
	
	public List<Question> getFieldsSurvey(String sqlCmd);
	public Boolean  InsertAllRecords ( int UserID, List<Question> AllAnswers);
	
	//for user admin page
	List<SurevyStats> getSurveyStatistics(String sqlCmd);
	String getSingleIntValue(String sqlCmd);
}
