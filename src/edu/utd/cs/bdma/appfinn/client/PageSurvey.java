package edu.utd.cs.bdma.appfinn.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
//import com.google.gwt.user.client.ui.Label;

import java.util.ArrayList;

import javax.swing.text.AttributeSet.ColorAttribute;

import org.apache.tools.ant.taskdefs.Sleep;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.thirdparty.javascript.jscomp.graph.GraphColoring.Color;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.AutoFitWidthApproach;
import com.smartgwt.client.types.Autofit;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.MultipleAppearance;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Slider;

import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.SliderItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridEditorContext;
import com.smartgwt.client.widgets.grid.ListGridEditorCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.EditorExitEvent;
import com.smartgwt.client.widgets.grid.events.EditorExitHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
//import com.sun.java.swing.plaf.windows.resources.windows;
import com.smartgwt.client.widgets.events.ClickEvent;  
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.KeyDownEvent;
import com.smartgwt.client.widgets.events.KeyDownHandler;  


public class PageSurvey extends Composite{
	
	private final DBConnectionAsync rpcDB = (DBConnectionAsync) GWT.create(DBConnection.class); // ok
	static ListGrid countryGrid; // ok
	public static boolean drawFlag = false; // ok
	static DataSource countryDS;
	public static VerticalPanel vpanel= new VerticalPanel();
	public static VerticalPanel GridPanel = new VerticalPanel();
	public static VerticalPanel submitpanel = new VerticalPanel();
	static Label L = new Label("ddd");
	public static TextItem textItem = new TextItem(); 
	public static Button btn=  new Button();
		
	public PageSurvey() {		
		initWidget(this.vpanel);
//		L= new Label("amir");
//		vpanel.add(L);
//		vpanel.add(new com.google.gwt.user.client.ui.Label("Amir"));
//		vpanel.add(new HTML("<h1> Smart Phone Apps Fingerprinting </h1>"
//				+ "<p>"
//				+ "Apps Data Management is a tool designed by the UT Dallas Big Data Management and Analytics lab <br> which is directed by <a href=\"https://www.utdallas.edu/~lkhan/\" target=\"_blank\" >Professor Latifur Khan</a>."
//				+ "</p>"
//				
//				+ "<p>"
//				+ "The tool facilitates Data Collection/Dynamic Analysis and Data Analytics. Data Collection enables the user to install <br> mobile apps, open them, and collect traces (as TCP packets). Such traces are used as input vectors <br> for Data Analytics where different machine learning algorithms can be applied."
//				+ "</p>"
//
//				+ "<p>"
//				+ "Please send email to <b> dml.utd@gmail.com </b> for any comments or concenrs. Thank you."
//				+ "</p>"
//	
//	));
		countryDS = CountryDS.getInstance(); 
		
	}

	public static void BuildGrid()
    {
    	countryGrid = new ListGrid();
    	
    	/*countryGrid = new ListGrid() {  
            @Override  
            protected Canvas createRecordComponent(final ListGridRecord record, Integer colNum) {  
  
                String fieldName = this.getFieldName(colNum);  
                
                if (fieldName.equals("buttonField")) {  
                	
                	
                	VLayout recordCanvas = new VLayout();  
                	
                    recordCanvas.setHeight100();  
                    recordCanvas.setWidth100(); 
                    //recordCanvas.setAutoHeight();
                    recordCanvas.setAlign(Alignment.CENTER);
                    
                	VerticalPanel vp = new VerticalPanel();
                    IButton button = new IButton();  
                    button.setHeight(30);  
                    button.setWidth(65);                      
                    //button.setIcon("flags/16/" + record.getAttribute("countryCode") + ".png");  
                    button.setTitle("Save");  
                    button.addClickHandler(new ClickHandler() {  
                        public void onClick(ClickEvent event) {  
                        	countryGrid.endEditing();
              				countryGrid.saveAllEdits();
              				//countryGrid.setRowEndEditAction(RowEndEditAction.DONE);
                        }  
                    });  
                    vp.add(button);
                    recordCanvas.addMember(button);;
                    return recordCanvas;  
                } else {  
                    return null;  
                }  
  
            }  
        };*/  
    	
    	
        countryGrid.setShowRecordComponents(true);          
        countryGrid.setShowRecordComponentsByCell(true); 
    	//countryGrid = new ListGrid();  
        //countryGrid.setWidth100();
    	//countryGrid.setWidth("90%");
    	countryGrid.setWidth100();

    	//countryGrid.autoFitFiels();
        //countryGrid.setHeight100();
       // countryGrid.setDefaultHeight(100);  
    	//countryGrid.setAutoFitWidthApproach(AutoFitWidthApproach.BOTH);
        countryGrid.setHeaderHeight(30);
        countryGrid.setAlign(Alignment.CENTER);
        countryGrid.setShowAllRecords(Boolean.TRUE);  
       
        countryGrid.setAutoFetchData(Boolean.TRUE); 
        countryGrid.setWrapCells(true);  
        countryGrid.setFixedRecordHeights(false); 
        countryGrid.setAutoFitWidthApproach(AutoFitWidthApproach.BOTH);  
        countryGrid.setBaseStyle("myBoxedGridCell");
        countryGrid.setHeaderBaseStyle("myBoxedGridHeader");
        countryGrid.setHeaderHoverStyle("myBoxedGridHeader");
        countryGrid.setCellHeight(70);
        countryGrid.setAutoFitData(Autofit.BOTH);
        countryGrid.setShowRowNumbers(true);
              
        
        countryGrid.setShowAllRecords(true);  
        
        countryGrid.setBodyOverflow(Overflow.VISIBLE);  
        countryGrid.setOverflow(Overflow.VISIBLE);  
        countryGrid.setLeaveScrollbarGap(false);  
        
        //countryGrid.setAutoFitMaxRecords(3);
        //countryGrid.setLeft(30);
        //countryGrid.setRight(50);
        
        // represent counter for questions
        ListGridField QIDField = new ListGridField("QNo", "QuestionNo"); 
        QIDField.setAutoFitWidth(true);
        QIDField.setAutoFitWidthApproach(AutoFitWidthApproach.BOTH);
        QIDField.setAlign(Alignment.CENTER);
        QIDField.setCanEdit(false);
        QIDField.setCanSort(false);
        QIDField.setCanDragResize(false);
        QIDField.addRecordClickHandler(new RecordClickHandler() {
			
			@Override
			public void onRecordClick(RecordClickEvent event) {
				// TODO Auto-generated method stub
				countryGrid.endEditing();
  				countryGrid.saveAllEdits();
				
			}
		});
        
        
        // represent the question itself
        ListGridField QField = new ListGridField("Question", "Question"); 
        QField.setMultiple(true);
        QField.setAlign(Alignment.CENTER);
        QField.setCanEdit(false);
        QField.setCanSort(false);
        QField.setCanDragResize(false);
        
        QField.addRecordClickHandler(new RecordClickHandler() {
			
			@Override
			public void onRecordClick(RecordClickEvent event) {
				// TODO Auto-generated method stub
				countryGrid.endEditing();
  				countryGrid.saveAllEdits();
				
			}
		});
        
        
        // represent the type of question (single choice , multi choice , open ended )
        ListGridField QTypeField = new ListGridField("Type", "QuestionType");
        QTypeField.setAlign(Alignment.CENTER);
        QTypeField.setCanEdit(false);
        QTypeField.setCanSort(false);
        QTypeField.setCanDragResize(false);
        
        QTypeField.addRecordClickHandler(new RecordClickHandler() {
			
			@Override
			public void onRecordClick(RecordClickEvent event) {
				// TODO Auto-generated method stub
				countryGrid.endEditing();
  				countryGrid.saveAllEdits();
				
			}
		});
        
        ListGridField QInstField = new ListGridField("Instruction", "Instruction");
        QInstField.setAlign(Alignment.CENTER);
        QInstField.setCanEdit(false);
        QInstField.setCanSort(false);
        QInstField.setCanDragResize(false);
        
        QInstField.addRecordClickHandler(new RecordClickHandler() {
			
			@Override
			public void onRecordClick(RecordClickEvent event) {
				// TODO Auto-generated method stub
				countryGrid.endEditing();
  				countryGrid.saveAllEdits();
				
			}
		});
        
        // represent the set of answers if the type of the question is choice and is empty if its is openended
        //ListGridField QAnswerField = new ListGridField("Answers", "QuestionAnswer");
        ListGridField QAnswerField = new ListGridField("AnswersList","Please click any where below to answer");
        //QAnswerField.setMultiple(true);
        QAnswerField.setAlign(Alignment.CENTER);
        QAnswerField.setCanSort(false);
        QAnswerField.setCanDragResize(false);
        //QAnswerField.setCanEdit(true);
        
        //ListGridField iconField = new ListGridField("buttonField","Save");
        //iconField.setAlign(Alignment.CENTER);
        //iconField.setCanEdit(true);
       
        
        // adding the fields to the listgrid 
        
        ListGridField[] fields ={QIDField,QField,QTypeField,QInstField, QAnswerField};
		countryGrid.setFields(fields ); 
		
		
		countryGrid.setCanEdit(true);
		countryGrid.setEditEvent(ListGridEditEvent.CLICK);  
        countryGrid.setEditByCell(true); 
        countryGrid.setModalEditing(true);
        countryGrid.setAutoSaveEdits(true);
        countryGrid.setCanSort(false);
        
        countryGrid.setShowHeaderContextMenu(false);
        countryGrid.setShowHeaderMenuButton(false);
        
        //countryGrid.set
        
        
        //countryGrid.setRowEndEditAction(RowEndEditAction.DONE);
        //countryGrid.setListEndEditAction(RowEndEditAction.DONE);
        //countryGrid.setAutoSaveEdits(true);
        
       // countryGrid.addMous
        
		
        countryGrid.setDataSource(countryDS);  
        
		countryGrid.setEditorCustomizer(new ListGridEditorCustomizer() {
			
			@Override
			public FormItem getEditor(ListGridEditorContext context) {
				ListGridField field = context.getEditField(); 
				
				
				
				if (field.getName().equals("AnswersList")) 
				{
					ListGridRecord record = context.getEditedRecord();
					String id = record.getAttribute("Type"); 
					SelectItem selectItemMultipleGrid = new SelectItem();  
					selectItemMultipleGrid.setMultipleAppearance(MultipleAppearance.PICKLIST); 
					//selectItemMultipleGrid.setShowTitle(false);  
					//selectItemMultipleGrid.setDefaultValues("select");
					selectItemMultipleGrid.setHint("Click to select");
					selectItemMultipleGrid.setTitle("Please");
					//selectItemMultipleGrid.setDefaultToFirstOption(true);
					selectItemMultipleGrid.setShowHintInField(true);
					//selectItemMultipleGrid.setSaveOnEnter(true);
					//selectItemMultipleGrid.setCellStyle("gwtLabelmessage");
					//selectItemMultipleGrid.setControlStyle("gwtLabelmessage");
					selectItemMultipleGrid.setPickListBaseStyle("myBoxedGridHeader");
					
					Helper1 t = new Helper1();
					String  Ans = record.getAttribute("QNo");
				
					String [] choices = null;
					choices = t.GetChoices(Ans).split(",");
								
					switch (id) {  
					case "Multiselect multiple choice":
						selectItemMultipleGrid.setValueMap(choices);
						selectItemMultipleGrid.setMultiple(true);  
						selectItemMultipleGrid.setDefaultToFirstOption(true);
						
					    return selectItemMultipleGrid;  
					
					case "Singleselect multiple choice":
												
					      selectItemMultipleGrid.setValueMap(choices);
					      selectItemMultipleGrid.setMultiple(false);
					      selectItemMultipleGrid.setDefaultToFirstOption(true);
					      return selectItemMultipleGrid;  
					      
					case "rating":
//						Slider sliderProperties = new Slider();
//						sliderProperties.addStyleName("hSlider");
//						sliderProperties.setMargin(2); 
//						sliderProperties.setAutoWidth();
//						sliderProperties.setBackgroundColor("blue");
//                        SliderItem sliderItem = new SliderItem();  
//                        sliderItem.setTitle("Slider");
//                        sliderItem.setMinValue(1.0);
//                        sliderItem.setMaxValue(5.0);
//                        sliderItem.setNumValues(5);
//                        sliderItem.setDefaultValue(4);
//                        sliderItem.setAutoChildProperties("Slider", sliderProperties);                        
//                        return sliderItem;  
							
						  selectItemMultipleGrid.setValueMap("1","2","3","4","5");
					      selectItemMultipleGrid.setMultiple(false);
					      selectItemMultipleGrid.setDefaultToFirstOption(true);
					      return selectItemMultipleGrid; 
						
					case "Openended Text": 
						//TextItem textItem = new TextItem(); 
						//textItem.setWidth(100);
						textItem.setHeight(70);
						textItem.setDefaultValue("Insert Text Here");
						textItem.setCellStyle("simpleCellOverList");
						textItem.setControlStyle("simpleCellOverList");
						textItem.setHoverStyle("simpleCellOverList");
						textItem.setPrintTextBoxStyle("simpleCellOverList");
						textItem.addEditorExitHandler(new com.smartgwt.client.widgets.form.fields.events.EditorExitHandler() {
							
							@Override
							public void onEditorExit(com.smartgwt.client.widgets.form.fields.events.EditorExitEvent event) {
								// TODO Auto-generated method stub
								textItem.setValue(textItem.getEnteredValue());
								textItem.hide();
							}
						});
						 
                        return textItem;  
					default:  
					return context.getDefaultProperties();  
					}  
				}
				return context.getDefaultProperties();
			}
		});
		
		
		vpanel.add(btn);
		btn.setVisible(false);
		btn.addClickHandler(new com.google.gwt.event.dom.client.ClickHandler() {
			
			@Override
			public void onClick(com.google.gwt.event.dom.client.ClickEvent event) {
				
				
			}
		});
		
//		countryGrid.addKeyDownHandler(new KeyDownHandler() {
//			
//			@Override
//			public void onKeyDown(KeyDownEvent event) {
//				if (event.) {
//					
//				}
//			}
//		});
		
		/*countryGrid.setCanSelectCells(true);
		countryGrid.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				CellSelection selection = countryGrid.getCellSelection();  
                int[][] selectedCells = selection.getSelectedCells();  
               
                for (int i = 0; i < selectedCells.length; i++) {  
                   if( selectedCells[i][1] != 3)
                   {
                	 countryGrid.endEditing();
       				 countryGrid.saveAllEdits();
                   }
                }  
               
			}
		});*/
		
		/*countryGrid.addFocusChangedHandler(new FocusChangedHandler() {
			
			@Override
			public void onFocusChanged(FocusChangedEvent event) {
				// TODO Auto-generated method stub
				if(event.getHasFocus() == false)
				{
					countryGrid.endEditing();
      				countryGrid.saveAllEdits();

				}
			}
		});*/
		
		countryGrid.addEditorExitHandler(new EditorExitHandler() {
			
			@Override
			public void onEditorExit(EditorExitEvent event) {
				btn.click();
				//DomEvent.fireNativeEvent(Document.get().createKeyDownEvent(false, false, false, false, KeyCodes.KEY_ENTER), countryGrid);
			}
		});
		
		
        countryGrid.hideField("Type");
        countryGrid.hideField("QNo");
        countryGrid.hideField("Instruction");

        Label introLabel = new Label();
        introLabel.setBaseStyle("myLowGridCell");
        introLabel.setWidth("100%");
        introLabel.setHeight(70);
        //introLabel.setLeft(20);
        //introLabel.setRight(20);
        //L.setTop(submitBtn.getHeight()+30);
        introLabel.setAlign(Alignment.CENTER);
        introLabel.setContents("Thank you for taking the time to complete the following website  evaluation. The information you provide will be used to help us improve the content of the website and monitor its quality.");
        
        introLabel.setPadding(10);  
        introLabel.setBackgroundColor("white");  
        introLabel.setBorder("0px solid #c0c0c0");
        
        //introLabel.setBorder("1px solid #c0c0c0");  

        introLabel.setKeepInParentRect(true);
        
        GridPanel.add(introLabel);
        
        GridPanel.add(new HTML("<table><tr>"
				+ "<td colspan=\"100\">"
				+ "</td>"
				+ "</tr><tr></tr><tr></tr><tr></tr><tr></tr></table>"));
        
        GridPanel.add(countryGrid);
        
 
        
        IButton submitBtn = new IButton("Submit Survey");  
        //submitBtn.setTop(350); 
        submitBtn.setWidth("150");
        submitBtn.setHeight(40);
        submitBtn.setLeft(50);
        //submitBtn.setShowShadow(true);  
        //submitBtn.setShadowSoftness(4);  
        //submitBtn.setShadowOffset(6);  
        submitBtn.setBaseStyle("cssButton");
        
       
        
        submitBtn.addClickHandler(new submitBtnClickHandler1());
        
        //submitbutton.setWidth100();
        //submitbutton.setHeight100();
        //submitbutton.addChild(submitBtn);
        //submitbutton.setTop(countryGrid.getHeight()+150);

        //submitbutton.addStyleName("h1");
        submitpanel.add(new HTML("<table><tr>"
				+ "<td colspan=\"100\">"
				+ "</td>"
				+ "</tr><tr></tr><tr></tr><tr></tr><tr></tr></table>"));
        submitpanel.add(submitBtn);
        submitpanel.add(new HTML("<table><tr>"
				+ "<td colspan=\"100\">"
				+ "</td>"
				+ "</tr><tr></tr><tr></tr><tr></tr><tr></tr></table>"));
        L.setVisible(false);
       // L.setStyleName("h1");
        
        L.setBaseStyle("myLowGridCell");
        L.setWidth(700);
        L.setHeight(50);
        L.setLeft(50);
        //L.setTop(submitBtn.getHeight()+30);
        L.setAlign(Alignment.CENTER);
        
        L.setPadding(10);  
        L.setBackgroundColor("white");  
        L.setBorder("1px solid #c0c0c0");  
        L.setShowShadow(true);  
        L.setShadowSoftness(4);  
        L.setShadowOffset(6);  
        L.setKeepInParentRect(true); 
        
        //L.setTop(350);
       
        //submitbutton.addChild(L);
        submitpanel.add(L);
        
        //submitpanel.sinkEvents(Event.CLICK);
        //submitpanel.addHandler(new BtnClickHandler1(), ClickEvent.getType());
        
        FocusPanel focusPanel = new FocusPanel();

        focusPanel.addClickHandler(new com.google.gwt.event.dom.client.ClickHandler() {
			
			@Override
			public void onClick(com.google.gwt.event.dom.client.ClickEvent event) {
				// TODO Auto-generated method stub
				//Window.alert("panel");
				countryGrid.endEditing();
				countryGrid.saveAllEdits();
			}
		});
        
        focusPanel.add(submitpanel);
        
        vpanel.add(GridPanel);
        vpanel.add(focusPanel);
        
        
        
        //RootPanel.get().add(vpanel);
        //countryGrid.draw();

    }
	
	
	public static void DrawLabel(String Text)
	{
	    	
	        L.setContents(Text);
	        L.setVisible(true);
	        
	}
	

}


class Helper1
{
	public String GetChoices(String QN)
    {
        ListGridRecord []  R =CountryDS.list;
        
        String st ="";
        for (int i = 0; i < R.length; i++) {
		 if (R[i].getAttribute("QNo").equals(QN))
		 {
			   st = R[i].getAttribute("Answers");
			  
			  
		 }
		}
        
        
		return st;
    	
    
    }

}


class submitBtnClickHandler1 implements ClickHandler {

	
	public static  boolean InsertFlag= false;
	
	@Override
	public void onClick(ClickEvent event) {
        PageSurvey.countryGrid.endEditing();
		PageSurvey.countryGrid.saveAllEdits();
		ListGridRecord [] CurrentData =new ListGridRecord[PageSurvey.countryGrid.getTotalRows()];
        //CurrentData =  GridSmart.countryGrid.getRecords();
        
        //Window.alert(CurrentData[0].getAttribute("AnswersList"));
        //Window.alert(GridSmart.countryGrid.getEditedRecord(0).getAttribute("AnswersList"));

        
        for (int i = 0; i < PageSurvey.countryGrid.getTotalRows(); i++) {
			CurrentData[i]= (ListGridRecord) PageSurvey.countryGrid.getEditedRecord(i);
			//if(CurrentData[i].getAttribute("AnswersList").equals(null))
			//{
			//	CurrentData[i].setAttribute("AnswersList","");
			//}
		}
        
        //Window.alert(CurrentData[4].getAttribute("AnswersList"));
        
        InsertRecordsToDB(CurrentData, Integer.parseInt(AppFin.UserIDTextBox.getText()));   
	}
	
	
    
	private final DBConnectionAsync rpcDB = (DBConnectionAsync) GWT.create(DBConnection.class);
    
	private ArrayList<Question> QuestionAnswers = new ArrayList<Question>();

	protected JavaScriptObject jsObj;

    public  void InsertRecordsToDB(ListGridRecord[] AnswerList, int UserID)
    {
        	
    	
    	for (int i = 0; i < AnswerList.length; i++) {
		   ListGridRecord R = new ListGridRecord();
		   
		   R = AnswerList[i];
		   int QID = Integer.parseInt(R.getAttribute("QNo")); 
		   String QuestionType = R.getAttribute("Type");
		   String QuestionText=R.getAttribute("Question");
		   String QAnswer =R.getAttribute("AnswersList");
		   if(QAnswer == null)
		   {			   
			   QAnswer="";
		   }
		   //Window.alert(QAnswer);
		   Question Q = new Question(QID,QuestionType, QuestionText, QAnswer);
		   QuestionAnswers.add(Q);
		  
		}
    	
        rpcDB.InsertAllRecords(UserID, QuestionAnswers, new AsyncCallback<Boolean>() {
			
			@Override
			public void onSuccess(Boolean result) {
				// TODO Auto-generated method stub
				if(result == true)
				{
				   PageSurvey.DrawLabel("Thank You , Your Survey submitted succefully");
				}
				else
				{
					   PageSurvey.DrawLabel("your survey is already submitted ");

				}
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				   PageSurvey.DrawLabel("Sorry , we can not submit your survey now , please try later ");

			}
		});
		
    }
   
	
	
}





