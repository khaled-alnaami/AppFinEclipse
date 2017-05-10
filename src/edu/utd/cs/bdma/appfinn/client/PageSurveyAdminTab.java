package edu.utd.cs.bdma.appfinn.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dev.protobuf.DescriptorProtos.FieldDescriptorProto.Label;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.AutoFitWidthApproach;
import com.smartgwt.client.types.Autofit;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

//import edu.utd.cs.bdma.appfinn.client.PageNewUser.generateCodeBtnClickHandler;

/*this program displays the user survey detials in the following form
  <name of the survey question> <question option> <count of people selected this option>
 */

public class PageSurveyAdminTab extends Composite {

	private final DBConnectionAsync rpcDB = (DBConnectionAsync) GWT.create(DBConnection.class);
	VerticalPanel vpanel = new VerticalPanel();
	public List<SurevyStats> surveyInfo;
	int count;
	// static ListGrid showGrid;
	public static List<SurevyStats> surveydata;
	private TextBox textBox = new TextBox();
	public int indexOfSurvey = 0;
	Button generateCodeBtn = new Button("Generate Code");
	ListGrid showGrid;

	public PageSurveyAdminTab() {
		// checkUserAccessType();
		initWidget(this.vpanel);
		vpanel.setBorderWidth(1);

		showGrid = new ListGrid();

		setUpGrid(showGrid);
		
		// get database records
		// retrieve total number of users who took the survey

		// int numUsers = fetchTotalUsers();
		// Window.alert("Fine 1!!!");
		// Window.alert(String.valueOf(numUsers));
		// retrieve all user survey answers from database
		generateCodeBtn.setVisible(false);
		vpanel.add(generateCodeBtn);
		generateCodeBtn.addClickHandler(new generateCodeBtnClickHandler());

		// surveydata = fetchSurveyAnswerDetails();
		// fetchSurveyAnswerDetails(); // method no called form AppFin.java
		/**
		 * commented by Khaled May 10, 2017 to fix the sync problem //ListGrid
		 * showGrid = new ListGrid(); showGrid.setWidth(500);
		 * showGrid.setHeight(300); showGrid.setTop(10);
		 * //showGrid.setBottom(30); showGrid.setAlternateRecordStyles(true);
		 * showGrid.setShowAllRecords(true); showGrid.setShowEmptyMessage(true);
		 * showGrid.setAlwaysShowScrollbars(true); showGrid.setLeft(40);
		 * 
		 * 
		 * ListGridField QTextField = new ListGridField("QText", "Question");
		 * ListGridField ATextField = new ListGridField("AText", "Answer");
		 * ListGridField countField = new ListGridField("count", "User Count");
		 * 
		 * showGrid.setFields(new ListGridField[] {QTextField, ATextField,
		 * countField}); showGrid.setCanResizeFields(true);
		 * 
		 * //Window.alert("Fine till now!!!");
		 * 
		 * //surveydata = fetchSurveyAnswerDetails();
		 * 
		 * 
		 * vpanel.add(showGrid);
		 **/

	}

	private ListGridRecord[] getListridRecords(List<SurevyStats> surveydata) {
		ListGridRecord records[] = null;
		if (surveydata != null) {
			records = new ListGridRecord[surveydata.size()];
			for (int cntr = 0; cntr < surveydata.size(); cntr++) {
				ListGridRecord record = new ListGridRecord();
				SurevyStats emp = surveydata.get(cntr);
				record.setAttribute("QText", emp.getQuestionText());
				record.setAttribute("AText", emp.getOptionChosen());
				record.setAttribute("count", emp.getUserCount());
				records[cntr] = record;
			}
		}
		return records;
	}

	private int fetchTotalUsers() {

		String sqlCmd = "select count(distinct(UserID)) from UserAnswer;";

		rpcDB.getSingleIntValue(sqlCmd, new AsyncCallback<String>() {
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Window.alert("Database connection failure! Please contact admin!");
			}

			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				count = Integer.parseInt(result);
				Window.alert("You have Logged In as Admin!");

			}
		});
		return count;

	}

	// private List<SurevyStats> fetchSurveyAnswerDetails() {
	void fetchSurveyAnswerDetails() {
		// TODO Auto-generated method stub
		String sqlCmd = "select a.QID, b.QText, a.Answer, count(a.UserID) from UserAnswer as a, surveyQuestions as b where a.QID = b.QID and a.Answer <> \" \" group by a.QID, a.Answer having a.QID < 5;";

		rpcDB.getSurveyStatistics(sqlCmd, new AsyncCallback<List<SurevyStats>>() {

			@Override
			public void onSuccess(List<SurevyStats> result) {
				// TODO Auto-generated method stub
				// Window.alert("You have Logged In as Admin!"); // commented by
				// khaled
				// surveyInfo = result;
				// Window.alert("You have access to the survey results!"); //
				// commented by khaled
				surveydata = result;
				generateCodeBtn.click(); // for synchronization issues, populate
											// data to the grid in the click
											// handler method
			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Window.alert("Database connection failure! Please contact admin!");
			}
		});
		// return surveyInfo;
	}

	private class generateCodeBtnClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// Window.alert("here!!!");
			for (SurevyStats s : surveydata) {
				System.out.println(s.getOptionChosen());
			}
			showGrid.setData(getListridRecords(surveydata));

			// Start: moved from constructor, for the sync issues, khaled May
			// 10, 2017
			/**
			showGrid.setWidth(500);
			showGrid.setHeight(300);
			showGrid.setTop(10);
			// showGrid.setBottom(30);
			showGrid.setAlternateRecordStyles(true);
			showGrid.setShowAllRecords(true);
			showGrid.setShowEmptyMessage(true);
			showGrid.setAlwaysShowScrollbars(true);
			showGrid.setLeft(40);
 			**/
			
			ListGridField QTextField = new ListGridField("QText", "Question");
			ListGridField ATextField = new ListGridField("AText", "Answer");
			ListGridField countField = new ListGridField("count", "User Count");

			showGrid.setFields(new ListGridField[] { QTextField, ATextField, countField });
			showGrid.setCanResizeFields(true);

			vpanel.add(showGrid);
			// End: moved from constructor, for the sync issues, khaled May 10,
			// 2017

		}
	}
	
	void setUpGrid(ListGrid grid){
        grid.setShowRecordComponents(true);          
        grid.setShowRecordComponentsByCell(true); 
    	//countryGrid = new ListGrid();  
        //countryGrid.setWidth100();
    	//countryGrid.setWidth("90%");
    	grid.setWidth100();
    	
    	//countryGrid.autoFitFiels();
        //countryGrid.setHeight100();
       // countryGrid.setDefaultHeight(100);  
    	//countryGrid.setAutoFitWidthApproach(AutoFitWidthApproach.BOTH);
        grid.setHeaderHeight(30);
        grid.setAlign(Alignment.CENTER);
        grid.setShowAllRecords(Boolean.TRUE);  
       
        grid.setAutoFetchData(Boolean.TRUE); 
        grid.setWrapCells(true);  
        grid.setFixedRecordHeights(false); 
        grid.setAutoFitWidthApproach(AutoFitWidthApproach.BOTH);  
        grid.setBaseStyle("myBoxedGridCell");
        grid.setHeaderBaseStyle("myBoxedGridHeader");
        grid.setHeaderHoverStyle("myBoxedGridHeader");
        grid.setCellHeight(70);
        grid.setAutoFitData(Autofit.BOTH);
        grid.setShowRowNumbers(true);
              
        
        grid.setShowAllRecords(true);  
        
        grid.setBodyOverflow(Overflow.VISIBLE);  
        grid.setOverflow(Overflow.VISIBLE);  
        grid.setLeaveScrollbarGap(false);
	}

}

class SurveyRecord extends ListGridRecord {

	public SurveyRecord() {
	}

	public SurveyRecord(String question, String answer, int count) {
		setQuestion(question);
		setAnswer(answer);
		setCount(count);
	}

	private String getCount() {
		// TODO Auto-generated method stub
		return getAttributeAsString("countField");

	}

	private void setCount(int count) {
		// TODO Auto-generated method stub
		setAttribute("countField", count);
	}

	private void setAnswer(String answer) {
		// TODO Auto-generated method stub
		setAttribute("ATextField", answer);
	}

	private String getAnswer() {
		// TODO Auto-generated method stub
		return getAttributeAsString("ATextField");

	}

	private void setQuestion(String question) {
		// TODO Auto-generated method stub
		setAttribute("QTextField", question);

	}

	private String getQuestion() {
		// TODO Auto-generated method stub
		return getAttributeAsString("QTextField");

	}
}

class SurveyData {

	private SurveyRecord[] records;
	public List<SurevyStats> surveydata;
	SurveyRecord[] recs;
	int index = 0;

	public SurveyData(List<SurevyStats> s) {
		this.surveydata = s;
	}

	public SurveyRecord[] getRecords() {
		if (records == null) {
			records = getNewRecords();
		}
		return records;
	}

	public SurveyRecord[] getNewRecords() {

		for (SurevyStats sinfo : this.surveydata) {
			System.out.println(sinfo.getQuestionText());
			recs[index] = new SurveyRecord(sinfo.getQuestionText(), sinfo.getOptionChosen(), sinfo.getUserCount());
			index++;
		}
		return recs;
	}

}
