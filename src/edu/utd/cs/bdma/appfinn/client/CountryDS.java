package edu.utd.cs.bdma.appfinn.client;


import java.util.ArrayList;
import java.util.List;

import org.apache.jasper.tagplugins.jstl.ForEach;
import org.apache.tools.ant.taskdefs.Sleep;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.grid.ListGridRecord;
//import com.sun.java.swing.plaf.windows.resources.windows;
  

    public class CountryDS extends DataSource {  
	
	//public static ListGridRecord[] list = new ListGridRecord[5];
    	
    public static ListGridRecord[] list;

	private RecordList rlist = new RecordList();
	
	
	private ArrayList<ListGridRecord> LLLL = new ArrayList<ListGridRecord>();

	

	
	// private  list = null;

	private static CountryDS instance = null;  
	 
	private final  DBConnectionAsync rpcDB = (DBConnectionAsync) GWT.create(DBConnection.class);
     
    public static CountryDS getInstance() {  
         if (instance == null) {  
             instance = new CountryDS("localCountryDS");  
             
         }  
         
         return instance;  
     }  
     
    
    public void CountryDSInitialize(String id) {  
        setID(id);  
          
        DataSourceIntegerField pkField = new DataSourceIntegerField("pk");  
        pkField.setHidden(true);   
		  
        pkField.setPrimaryKey(true);  
        
        DataSourceTextField QuestionCodeField = new DataSourceTextField("QNo", "QuestionNo"); 
        QuestionCodeField.setRequired(true);  

        DataSourceTextField QuestionField = new DataSourceTextField("Question", "Question");  
        DataSourceTextField TypeField = new DataSourceTextField("Type", "QuestionType");
        DataSourceTextField InstField = new DataSourceTextField("Instruction", "Instruction");
        DataSourceTextField AnswerField = new DataSourceTextField("Answers", "QuestionAnswer");  
        
        DataSourceField[] fields = {pkField,QuestionCodeField, QuestionField,TypeField,InstField,AnswerField};
        setClientOnly(true);
        
		setFields(fields );
        
        //setTestData(getNewRecords()); 
		 //setTestData(getRecordsFromDB());
		 //setTestData(getRecordsFromDB2());
		 //setTestData(getRecordsFromDB3());
		 //getRecordsFromDB3();
		setTestData(list);
          
    }  

    public CountryDS(String id) {
    	setClientOnly(true);
		getRecordsFromDB3();
		
	}
          
    public CountryDS() {
		// TODO Auto-generated constructor stub
	}

	private static ListGridRecord[] records;    
        
   
    public void getRecordsFromDB3()
    {
    	
    	String query = "select * from surveyQuestions;";
    	
    
        rpcDB.getFieldsSurvey(query, new AsyncCallback<List<Question>>() {
			
			@Override
			public void onSuccess(List<Question> result) {
				
				list= new ListGridRecord[result.size()];
				
				for (int j = 0; j < result.size(); j++) {
					    Question question=  (Question)result.get(j);
		                ListGridRecord R = new ListGridRecord(); 
		                int k = j+1;
		                
		                if(question.QType.equals("Singleselect multiple choice") )  
		                   R= createRecord(question.QID,Integer.toString(question.QID) ,question.QText,question.QType," Please select one choice, Click to select ",question.QAnswer) ;
		                else
		                	if(question.QType.equals("Multiselect multiple choice"))
				                   R= createRecord(question.QID,Integer.toString(question.QID) ,question.QText,question.QType," Please select , You can Select one or More choices, Click to select ",question.QAnswer) ;
		                	else 
		                		if(question.QType.equals("Openended Text"))
					                   R= createRecord(question.QID,Integer.toString(question.QID) ,question.QText,question.QType," Please write your answer in the textbox ",question.QAnswer) ;

		                		else
					                   R= createRecord(question.QID,Integer.toString(question.QID) ,question.QText,question.QType," Please select the rate , 1 is lowest rate , 5 is the highest rate ",question.QAnswer) ;

//		                if(question.QType.equals("Singleselect multiple choice") )  
//			                   R= createRecord(k,Integer.toString(k) ,question.QText,question.QType," Please select one choice, Click to select ",question.QAnswer) ;
//			                else
//			                	if(question.QType.equals("Multiselect multiple choice"))
//					                   R= createRecord(k,Integer.toString(k) ,question.QText,question.QType," Please select , You can Select one or More choices, Click to select ",question.QAnswer) ;
//			                	else 
//			                		if(question.QType.equals("Openended Text"))
//						                   R= createRecord(k,Integer.toString(k) ,question.QText,question.QType," Please write your answer in the textbox ",question.QAnswer) ;
//
//			                		else
//						                   R= createRecord(k,Integer.toString(k) ,question.QText,question.QType," Please select the rate , 1 is lowest rate , 5 is the highest rate ",question.QAnswer) ;
		                LLLL.add(R);
		        
				}
				
				for (int i = 0; i < LLLL.size(); i++) {
					list[i] = LLLL.get(i);
					String sttt = list[i].getAttribute("Question");
					String SS = sttt+"H";
				}
				//GetREcordsFromDB4(list);
				
				CountryDSInitialize("localCountryDS");
				//GridSmart.BuildGrid();
				PageSurvey.BuildGrid();
		         
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
        
      
    }

    public void GetREcordsFromDB4(ListGridRecord[] L)
    {
    	String sttt = L[0].getAttribute("Question");
    	String S = sttt+" L";
    	list= L;
    	String stttt = list[0].getAttribute("Question");
    	//setTestData(L);
    	String St = sttt+" L";
    	
    }
    
   
    
   /* public static ListGridRecord[] getRecords() {  
        if (records == null) {  
            records = getNewRecords();    
        }    
        return records;    
    } */   
   
   
    public static ListGridRecord createRecord(int pk, String QNo, String Question, String QType,String inst, String Answer) {  
        ListGridRecord record = new ListGridRecord();
        record.setAttribute("pk", pk);
        record.setAttribute("QNo", QNo);  
        record.setAttribute("Question", Question);  
        record.setAttribute("Type", QType); 
        record.setAttribute("Instruction", inst);  
        record.setAttribute("Answers", Answer);  
         
        return record;  
    }  
    
     
    
}  