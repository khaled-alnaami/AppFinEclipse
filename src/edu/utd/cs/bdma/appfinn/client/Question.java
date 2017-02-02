package edu.utd.cs.bdma.appfinn.client;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class Question implements IsSerializable  {

	public int QID;
	public String QAnswer;
	public String QType;
	public String QText;
	
	@SuppressWarnings("unused")
	private Question()
	{
		
	}
	
	public Question(int QID,String QType,String QText, String QAns)
	{
		this.QID= QID;
		this.QType=QType;
		this.QText=QText;
		this.QAnswer = QAns;
	}
}
