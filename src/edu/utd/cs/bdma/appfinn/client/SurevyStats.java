package edu.utd.cs.bdma.appfinn.client;


import com.google.gwt.user.client.rpc.IsSerializable;


public class SurevyStats implements IsSerializable {
	
	private String QuestionId;
	private String QuestionText;
	private String OptionChosen;
	private int userCount;
	
	@SuppressWarnings("unused")
	private SurevyStats()
	{
		
	}
	
	//constructor that sets the value of the fields;
	public SurevyStats(String QuestionId, String QuestionText, String OptionChosen, int userCount){
		this.setQuestionId(QuestionId);
		this.setQuestionText(QuestionText);
		this.setOptionChosen(OptionChosen);
		this.setUserCount(userCount);		
	}

	public String getQuestionId() {
		return this.QuestionId;
	}

	public void setQuestionId(String questionId) {
		this.QuestionId = questionId;
	}

	public String getOptionChosen() {
		return this.OptionChosen;
	}

	public void setOptionChosen(String optionChosen) {
		this.OptionChosen = optionChosen;
	}

	public int getUserCount() {
		return this.userCount;
	}

	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}

	public String getQuestionText() {
		return this.QuestionText;
	}

	public void setQuestionText(String questionText) {
		this.QuestionText = questionText;
	}
	
}
