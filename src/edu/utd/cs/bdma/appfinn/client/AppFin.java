package edu.utd.cs.bdma.appfinn.client;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.http.client.Request;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabBar;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class AppFin implements EntryPoint {

	///// private String link = "http://127.0.0.1:8888/appfin/";
	///// private String link = "http://127.0.0.1:8080/appfin/";
	///// private String link = Window.Location.getHostName() + ":" +
	///// Window.Location.getPort() + "/";

	private String dataDir = "/data/kld/android_apps_project/app_fingerprinting/";
	String[] folderNames = new String[] { "d" };
	private String appsDataFolder = "appsData/";

	private CaptionPanel loginCP = new CaptionPanel("Sign in");
	private CaptionPanel downloadCP = new CaptionPanel("Download Folder");
	private CaptionPanel classifiersCP = new CaptionPanel("Classifier");
	private CaptionPanel defensesCP = new CaptionPanel("Defense");
	private CaptionPanel featuresCP = new CaptionPanel("Bi-Directional Features");
	private CaptionPanel datasetsCP = new CaptionPanel("Dataset");
	private CaptionPanel bucketsCP = new CaptionPanel("Bucket Size");
	private CaptionPanel appsCP = new CaptionPanel("Number of Apps");
	private CaptionPanel testingCP = new CaptionPanel("Number of Testing Instances");
	private CaptionPanel trainingCP = new CaptionPanel("Number of Training Instances");
	private CaptionPanel trialsCP = new CaptionPanel("Number of Trials");

	// private VerticalPanel loginParentPanel = new VerticalPanel();
	public VerticalPanel loginParentPanel = new VerticalPanel();
	private VerticalPanel loginTextPanel = new VerticalPanel();
	private Grid userGrid = new Grid(2, 2);
	private VerticalPanel loginPanel = new VerticalPanel();
	private VerticalPanel loginButtonPanel = new VerticalPanel();
	private VerticalPanel mainPanel = new VerticalPanel();
	private VerticalPanel collectPanel = new VerticalPanel();
	private VerticalPanel displayPanel = new VerticalPanel();
	private VerticalPanel testPanel = new VerticalPanel();
	private VerticalPanel analyticsPanel = new VerticalPanel();
	private VerticalPanel buttonPanel = new VerticalPanel();
	private VerticalPanel logoutPanel = new VerticalPanel();
	private VerticalPanel homePanel = new VerticalPanel();
	private VerticalPanel errorPanel = new VerticalPanel();

	private HorizontalPanel userPanel = new HorizontalPanel();
	private HorizontalPanel passPanel = new HorizontalPanel();
	private HorizontalPanel appSelect = new HorizontalPanel();
	private HorizontalPanel loadingPanel = new HorizontalPanel();
	private VerticalPanel classifierSelect = new VerticalPanel();
	private VerticalPanel defenseSelect = new VerticalPanel();
	private VerticalPanel datasetSelect = new VerticalPanel();
	private HorizontalPanel analytics = new HorizontalPanel();

	private TextBox userTextBox = new TextBox();
	private PasswordTextBox passTextBox = new PasswordTextBox();
	private TextBox bucketTextBox = new TextBox();
	private TextBox appTextBox = new TextBox();
	private TextBox trialTextBox = new TextBox();
	private TextBox trainingTextBox = new TextBox();
	private TextBox testingTextBox = new TextBox();

	private Button loginButton = new Button("Login");
	private Button downloadButton = new Button("Download");
	private Button refreshButton = new Button("Refresh");
	private Button addAppButton = new Button("Add");
	private Button doneButton = new Button("Collect");
	private Button analyticsButton = new Button("Run");
	private Button clearButton = new Button("Clear");
	private Button clearAnalyticsButton = new Button("Reset");

	private Hyperlink logout = new Hyperlink("Logout", "Logout");

	private TabPanel tabs = new TabPanel();

	private Label userLabel = new Label("Username");
	private Label passLabel = new Label("Password");
	private Label tempLabel = new Label("Fill in all fields. If there are empty fields, input will not be accepted. "
			+ "Multiple features can be selected.");
	private Label testLabel = new Label("Errors with the following: ");
	private Label classifierLabel = new Label("Classifiers");
	private Label defenseLabel = new Label("Defenses");
	private Label datasetLabel = new Label("Datasets");
	private Label numAppsLabel = new Label("Number of Apps");
	private Label bucketLabel = new Label("Bucket Size");
	private Label numTrainingLabel = new Label("Training Instances");
	private Label numTestingLabel = new Label("Testing Instances");
	private Label numTrialsLabel = new Label("Trials");

	private Label collectionProgress = new Label("Progress ..."); // Khaled
//	private Label analyticsReplyLabel = null;

	private ListBox folderList = new ListBox();
	private ListBox appList = new ListBox();
	private ListBox classifierList = new ListBox();
	private ListBox datasetList = new ListBox();
	private ListBox defenseList = new ListBox();
	private ListBox featureList = new ListBox();

	private Anchor aNewUser = new Anchor();
	private Anchor aForgotPassword = new Anchor();

	private ArrayList<String> selectedItems = new ArrayList<String>();
	private FlexTable flexTable = new FlexTable();
	private String apps = "";
	private String call = "";
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);

	private int classifier = -1;
	private int defense = -1;
	private int dataset = -1;
	private int numApps = 0;
	private int bucketSize = 0;
	// private int ackPackets = 0;
	private int ackPackets = 1; // Khaled
	private int numTrials = -1;
	private int numTraining = -1;
	private int numTesting = -1;
	private String concatFeatures = "";
	private int packetSize = 0;
	private int uniBurstSize = 0;
	private int uniBurstTime = 0;
	private int uniBurstNumber = 0;
	private int biBurstSize = 0;
	private int biBurstTime = 0;
	private int privacySet = 2;
	private int NUMAPPSINDATASET = 100;
	private int MAXBUCKETSIZE = 100;
	private int MAXNUMTRAINING = 100;
	private int MAXNUMTESTING = 100;
	private int MAXNUMTRIALS = 100;

	private boolean boolNumApps;
	private boolean boolBucket;
	private boolean boolNumTraining;
	private boolean boolNumTesting;
	private boolean boolNumTrials;

	// Database
	private final DBConnectionAsync rpcDB = (DBConnectionAsync) GWT.create(DBConnection.class);

	PageNewUser pageNewUser = null;
	PageForgotPassword pageForgotPassword = null;

	ArrayList<TextBox> requiredControlsList = new ArrayList<TextBox>();
	HashMap<Object, String> validControlList = new HashMap<Object, String>();

	/**
	 * Entry point method.
	 */
	public void onModuleLoad() {

		// setup user and pass panels
		// userLabel.addStyleDependentName("loginLabel");
		// userPanel.add(userLabel);
		// userPanel.add(userTextBox);
		// passLabel.addStyleDependentName("loginLabel");
		// passPanel.add(passLabel);
		// passPanel.add(passTextBox);

		setStyle();

		
		loginTextPanel.add(new HTML("<table><tr>"
			+ "<td class=\"tds\" colspan=\"100\">"
			+ "<image src='images/secure_key.jpg'></image></td>"
			+ "</tr></table>"));
		
		// empty line seperator
		loginTextPanel.add(new HTML("<table><tr>"
				+ "<td colspan=\"100\">"
				+ "</td>"
				+ "</tr><tr></tr><tr></tr><tr></tr><tr></tr></table>"));
		
		userGrid.setWidget(0, 0, userLabel);
		userGrid.setWidget(0, 1, userTextBox);
		userGrid.setWidget(1, 0, passLabel);
		userGrid.setWidget(1, 1, passTextBox);

		// setup login panel
		loginTextPanel.add(userGrid);
		
		// empty line seperator
		loginTextPanel.add(new HTML("<table><tr>"
				+ "<td colspan=\"100\">"
				+ "</td>"
				+ "</tr><tr></tr><tr></tr><tr></tr><tr></tr></table>"));
		
//		loginTextPanel.add(passPanel);
		loginTextPanel.addStyleName("center");

		loginButton.addStyleDependentName("login");
//		loginButton.setStyleName("Button_White");
		loginButtonPanel.add(loginButton);
		loginButtonPanel.addStyleName("center");

		loginPanel.add(loginTextPanel);
		loginPanel.add(loginButtonPanel);
		loginPanel.addStyleName("center");

		// empty line seperator
		loginButtonPanel.add(new HTML("<table><tr>"
				+ "<td colspan=\"100\">"
				+ "</td>"
				+ "</tr><tr></tr><tr></tr><tr></tr><tr></tr></table>"));
		
		// setup caption and parent panels
		loginCP.add(loginPanel);
		loginCP.addStyleName("loginCP");
		loginParentPanel.add(loginCP);
		loginParentPanel.addStyleName("center");

		// new user
		aNewUser.setHTML("<p> Create an account </p>");
		aNewUser.addClickHandler(new aNewUserClickHandler(this));
		aNewUser.getElement().getStyle().setCursor(Cursor.POINTER);
		loginButtonPanel.add(aNewUser);
		
		// forgot password
		aForgotPassword.setHTML("<p> Forgot Password? </p>");
		aForgotPassword.addClickHandler(new aForgotPasswordClickHandler(this));
		aForgotPassword.getElement().getStyle().setCursor(Cursor.POINTER);
		loginButtonPanel.add(aForgotPassword);

		// Associate the Main panel with the HTML host page.
		RootPanel.get("test").add(loginParentPanel);

		// Move cursor focus to the user box.
		Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
			public void execute() {
				userTextBox.setFocus(true);
			}
		});

		// Listen for mouse events on the Login button.
		loginButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (validateData()) {
					final String user = userTextBox.getText().trim();
					final String pass = passTextBox.getValue().trim();
					authinticateUser(user, pass);
				}
				// if (user == "USER" && pass == "PASS") {
				// RootPanel.get("test").remove(loginParentPanel);
				// onTabs();
				// } else {
				// Window.alert("Not a valid username or password.");
				// Window.Location.reload();
				// }
			}

		});

		// Listen for keyboard events on the Login button.
		passTextBox.addKeyDownHandler(new KeyDownHandler() {
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					final String user = userTextBox.getText().toUpperCase().trim();
					final String pass = passTextBox.getValue().toUpperCase().trim();
					authinticateUser(user, pass);
					// if (user == "USER" && pass == "PASS") {
					// RootPanel.get("test").remove(loginParentPanel);
					// onTabs();
					// } else {
					// Window.alert("Not a valid username or password.");
					// Window.Location.reload();
					// }
				}
			}
		});
	}

	/**
	 * Successful login.
	 */
	@SuppressWarnings("deprecation")
	private void onTabs() {

		// add to root
		RootPanel.get("test").add(mainPanel);

		/** Home Tab **/

		// setup home tab
		tabs.insert(homePanel, "Download", 0);

		// click handler for the Home tab
		homeClickHandler();

		// request server for array of foldernames, string.splti
		// File folder = new File(dataDir);

		// folderList.addItem("here " + folderNames[0]);

		getFolderNames();

		// for(String name : folderNames){
		// folderList.addItem(name);
		// }
		downloadButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				String folderName = folderList.getSelectedItemText();
				if (folderName == "") {
					return;
				}
				// String fileInfo = "?foldername="+appsDataFolder+folderName;
				String fileInfo = "?foldername=" + folderName;
				// private String link = "http://127.0.0.1:8080/appfin/";
				String link = "http://" + Window.Location.getHostName() + ":" + Window.Location.getPort() + "/appfin/";
				String url = link + "download" + fileInfo;

				Window.open(url, "_top", "status=0,toolbar=0,menubar=0,location=0");
			}
		});

		downloadCP.add(folderList);
		homePanel.add(downloadCP);
		homePanel.add(downloadButton);

		// load home tab
//		tabs.selectTab(0);

		// add to main panel
		mainPanel.add(tabs);
		mainPanel.addStyleName("center");

		/** Data Collection Tab **/

		// setup drop down
		appList.clear();
		appList.addItem("Select an app");
		appList.addItem("aabasoft.example.tracker.apk");
		appList.addItem("aabasoft.presents.currency.converter.apk");
		appList.addItem("abshahin.soft.kredit.apk");
		appList.addItem("abshahin.soft.valyuta.apk");
		appList.addItem("ace.easyaccounts.apk");
		appList.addItem("ackmaniac.currencyfxrates.apk");
		appList.addItem("acore.taxauscal.apk");
		appList.addItem("actforex.trader.apk");
		appList.addItem("ag.boersego.myrmecaphaga.apk");

		// setup flex table
		flexTable.clear();
		flexTable.setText(0, 0, "App Name");
		flexTable.getRowFormatter().addStyleName(0, "listHeader");
		flexTable.addStyleName("watchList");

		// loadingPanel.add(new HTML("<image
		// src='http://i16.photobucket.com/albums/b34/Andra1/gif%20images/c190997e.gif'
		// alt='Loading Text'></image>"));
		loadingPanel.add(new HTML("<image src='images/hourglass.gif' alt='Loading Text'></image>"));
		loadingPanel.add(new HTML("<p>The request is being processed. Please wait.</p>"));
		loadingPanel.setVisible(false);

		collectPanel.add(loadingPanel);
		collectPanel.add(flexTable);

		// get url address from server
		String url = "https://rtcxp.com/screen/?s=android_capture&p=utdbdma";

		// set up live display
		displayPanel.add(new HTML("<h2 style=text-align:center;>Live Display</h2>"));
		displayPanel.add(new HTML("<div id=container><iframe id=embed src=" + url + " scrolling=no></iframe></div>"));
		HorizontalPanel container = new HorizontalPanel();
		container.add(collectPanel);
		container.add(displayPanel);
		container.setCellWidth(collectPanel, "50%");
		container.setCellWidth(displayPanel, "50%");

		// setup selection panel
		appSelect.add(appList);
		appSelect.add(addAppButton);
		appSelect.add(doneButton);
		appSelect.add(clearButton);
		appSelect.add(refreshButton);
		appSelect.addStyleName("addPanel");

		// setup collection tab
		testPanel.add(appSelect);
		testPanel.add(container);
		tabs.insert(testPanel, "Data Collection/Dynamic Analysis", 1);

		// add button response
		addAppButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				final String appName = appList.getSelectedValue();
				if (appName != "Select an app" && !isPresent(appName)) {
					int row = flexTable.getRowCount();
					selectedItems.add(appName);
					flexTable.setText(row, 0, appName);
				}
			}
		});

		// done button response
		doneButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (selectedItems.size() == 0){
					Window.alert("Please select some apps!");
				} else {
					collectPanel.add(loadingPanel);
					loadingPanel.setVisible(true);
					tabs.setStyleName("pointer");
					Scheduler.get().scheduleDeferred(new ScheduledCommand() {
						public void execute() {
							onTesting();

						}
					});					
				}

			}
		});

		// clear button response
		clearButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				flexTable.removeAllRows();
				flexTable.setText(0, 0, "App Name");
				flexTable.getRowFormatter().addStyleName(0, "listHeader");
				flexTable.addStyleName("watchList");
				selectedItems.clear();
			}
		});
		refreshButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				DivElement frame = (DivElement) Document.get().getElementById("embed");
				frame.setAttribute("src", frame.getAttribute("src"));
			}
		});

		/** Data Analytics Tab **/

		// Creates a drop down for classifiers
		classifierList.clear();
		classifierList.addItem("Select a Classifier", "-1");
		classifierList.addItem("Liberatore", "0");
		classifierList.addItem("Jaccard", "2");
		classifierList.addItem("Panchenko", "3");
		classifierList.addItem("Dyer Bandwidth", "4");
		classifierList.addItem("Lu Edit Distance", "5");
		classifierList.addItem("Herrmann", "6");
		classifierList.addItem("Dyer Time", "10");
		classifierList.addItem("Dyer VNG", "14");
		classifierList.addItem("Dyer VNG++", "15");
		classifierList.addItem("Bi-Directional", "23");

		classifierList.addChangeHandler(new classifierListChangeHandler());
		
		// Creates a drop down for defenses
		defenseList.clear();
		defenseList.addItem("Select a Defense", "-1");
		defenseList.addItem("No Defense", "0");
		defenseList.addItem("Pad to MTU", "1");
		defenseList.addItem("Session Random 255", "2");
		defenseList.addItem("Packet Random 255", "3");
		defenseList.addItem("Pad Random MTU", "4");
		defenseList.addItem("Exponential Pad", "5");
		defenseList.addItem("Linear Pad", "6");
		defenseList.addItem("Mice-Elephants Pad", "7");
		defenseList.addItem("Direct Target Sampling", "8");
		defenseList.addItem("Traffic Morphing", "9");

		// Creates a drop down for data sets
		datasetList.clear();
		datasetList.addItem("Select a Dataset", "-1");
		// datasetList.addItem("2016-7-7-1530", "0");
		// datasetList.addItem("2016-7-8-1330", "1");
		// datasetList.addItem("2016-7-9-1130", "2");
		datasetList.addItem("Android Apps Dataset", "4"); // Added by Khaled,
															// 7/16/2016

		// feature list
		featureList.clear();
		featureList.setMultipleSelect(true); // no a drop down list anymore
		featureList.addItem("Packet Size");
		featureList.addItem("Uniburst Size");
		featureList.addItem("Uniburst Time");
		featureList.addItem("Uniburst Number");
		featureList.addItem("Biburst Size");
		featureList.addItem("Biburst Time");
		featureList.addItem("ACK Packets");

		// 10/28/2016, select Bi-Di features by default (except ACK Packets)
		// loops through selected features
		setFeatureList();
		
		// classifier panel
		classifiersCP.add(classifierList);
		featuresCP.add(featureList);
		trialTextBox.setText("");
		trialsCP.add(trialTextBox);
		classifierSelect.add(classifiersCP);
		classifierSelect.add(featuresCP);
		classifierSelect.add(trialsCP);
		classifierSelect.addStyleDependentName("paramaterList");

		// defense panel
		defensesCP.add(defenseList);
		appTextBox.setText("");
		appsCP.add(appTextBox);
		trainingTextBox.setText("");
		trainingCP.add(trainingTextBox);
		defenseSelect.add(defensesCP);
		defenseSelect.add(appsCP);
		defenseSelect.add(trainingCP);
		defenseSelect.addStyleDependentName("parameterList");

		// dataset panel
		datasetsCP.add(datasetList);
		bucketTextBox.setText("");
		bucketsCP.add(bucketTextBox);
		testingTextBox.setText("");
		testingCP.add(testingTextBox);
		datasetSelect.add(datasetsCP);
		datasetSelect.add(bucketsCP);
		datasetSelect.add(testingCP);
		datasetSelect.addStyleDependentName("parameterList");

		// panel elements
		analytics.add(classifierSelect);
		analytics.add(defenseSelect);
		analytics.add(datasetSelect);
		buttonPanel.add(analyticsButton);
		buttonPanel.add(clearAnalyticsButton);
		buttonPanel.addStyleName("center");

		// default values
		// appTextBox.setText("0"); // default values modified by khaled
		// bucketTextBox.setText("0");
		// trainingTextBox.setText("0");
		// testingTextBox.setText("0");
		// trialTextBox.setText("0");
		appTextBox.setText("2000");
		bucketTextBox.setText("10");
		trainingTextBox.setText("16");
		testingTextBox.setText("4");
		trialTextBox.setText("1");

		// docs tab
		StaticAnalysis staticAnalysisPage = new StaticAnalysis();
		tabs.insert(staticAnalysisPage, "Static Analysis", 2);
		
		// setup analytics tab
		analyticsPanel.add(analytics);
		analyticsPanel.add(buttonPanel);
		tabs.insert(analyticsPanel, "Data Analytics", 3);

		// collect response. click Run in Data Analytics
		analyticsButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				analyticsPanel.remove(errorPanel);
				errorPanel.clear();
				classifier = Integer.parseInt(classifierList.getSelectedValue());
				defense = Integer.parseInt(defenseList.getSelectedValue());
				dataset = Integer.parseInt(datasetList.getSelectedValue());
				
				// 10/27/2016 data validation

				if (! validDataAnalyticsFields())
					return;
				
				numApps = Integer.parseInt(appTextBox.getText().trim());
				bucketSize = Integer.parseInt(bucketTextBox.getText().trim());
				numTraining = Integer.parseInt(trainingTextBox.getText().trim());
				numTesting = Integer.parseInt(testingTextBox.getText().trim());
				numTrials = Integer.parseInt(trialTextBox.getText().trim());

				// loops through selected features
				for (int i = 0; i < featureList.getItemCount(); i++) {
					if (featureList.isItemSelected(i)) {
						concatFeatures += featureList.getItemText(i);
					}
				}

				boolNumApps = verifyNumApps(numApps);
				boolBucket = verifyBucketSize(bucketSize);
				boolNumTraining = verifyNumTraining(numTraining);
				boolNumTesting = verifyNumTesting(numTesting);
				boolNumTrials = verifyNumTrials(numTrials);

				// verifies no empty fields
				if (classifier == -1 || defense == -1 || dataset == -1 || numApps == 0 || bucketSize == 0
						|| numTraining == 0 || numTesting == 0 || numTrials == 0) {
					// Window.alert("Make a selection.");
					errorPanel.add(testLabel);
					if (classifier == -1) {
						errorPanel.add(classifierLabel);
					}
					if (defense == -1) {
						errorPanel.add(defenseLabel);
					}
					if (dataset == -1) {
						errorPanel.add(datasetLabel);
					}
					if (boolNumApps) {
						errorPanel.add(numAppsLabel);
					}
					if (boolBucket) {
						errorPanel.add(bucketLabel);
					}
					if (boolNumTraining) {
						errorPanel.add(numTrainingLabel);
					}
					if (boolNumTesting) {
						errorPanel.add(numTestingLabel);
					}
					if (boolNumTrials) {
						errorPanel.add(numTrialsLabel);
					}
					analyticsPanel.add(errorPanel);

				} else {
					run();
					// TabBar barA = tabs.getTabBar();
					// barA.setTabEnabled(0, false);
					// barA.setTabEnabled(1, false);
				}
			}
		});

		// clears analytics input
		clearAnalyticsButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				setFeatureList();

				classifierList.setItemSelected(0, true);
				defenseList.setItemSelected(0, true);
				datasetList.setItemSelected(0, true);

//				appTextBox.setText("0");
//				bucketTextBox.setText("0");
//				trainingTextBox.setText("0");
//				testingTextBox.setText("0");
//				trialTextBox.setText("0");
				
				// Oct 28, 2016
				appTextBox.setText("2000");
				bucketTextBox.setText("10");
				trainingTextBox.setText("16");
				testingTextBox.setText("4");
				trialTextBox.setText("1");
			}
		});

		/** logout panel **/

		logoutPanel.add(logout);
		logoutPanel.addStyleName("center");
		mainPanel.add(logoutPanel);

		logout.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Window.Location.reload();
			}
		});

		// docs tab
		PageDocs pageDocs = new PageDocs();
		tabs.insert(pageDocs, "Documentation", 4);

		PageAbout pageAbout = new PageAbout();
		tabs.insert(pageAbout, "About", 5);
		
		PageAbout pageAbout2 = new PageAbout();
		tabs.insert(pageAbout2, "About2", 6);
		
		tabs.selectTab(1); // showing Data Collection/Dynamic Analysis first after login
	}

	/**
	 * Runs test for data collection.
	 */
	private void onTesting() {
		loadingPanel.setVisible(true);
		apps = "";
		// reformat into ? parameters
		for (String each : selectedItems) {
			apps += each;
			apps += ";";
		}
		// 10/26/16
		apps += "_" + userTextBox.getText().trim(); // app1;app2;...;appn;_username
		requestSocket(apps);

	}

	/**
	 * Runs test for data analytics.
	 */
	private void run() {
		
		// gets the corresponding feature index
		if (concatFeatures.contains("Packet Size")) {
			packetSize = 1;
		}
		if (concatFeatures.contains("Uniburst Size")) {
			uniBurstSize = 1;
		}
		if (concatFeatures.contains("Uniburst Time")) {
			uniBurstTime = 1;
		}
		if (concatFeatures.contains("Uniburst Number")) {
			uniBurstNumber = 1;
		}
		if (concatFeatures.contains("Biburst Size")) {
			biBurstSize = 1;
		}
		if (concatFeatures.contains("Biburst Time")) {
			biBurstTime = 1;
		}
		if (concatFeatures.contains("ACK Packets")) {
			// ackPackets = 1; // Khaled
			ackPackets = 0;
		}

		call = "python mainBiDirectionLatest.py" + " -N " + numApps + " -k " + bucketSize + " -d " + dataset + " -C "
				+ classifier + " -c " + defense + " -n " + numTrials + " -t " + numTraining + " -T " + numTesting
				+ " -D " + packetSize + " -E " + uniBurstSize + " -F " + uniBurstTime + " -G " + uniBurstNumber + " -H "
				+ biBurstSize + " -I " + biBurstTime + " -A " + ackPackets + " -U " + userTextBox.getText().trim();

		// start testing script, Khaled
		testLabel = new Label("Script running: " + call);
		errorPanel.add(testLabel);
		analyticsPanel.add(errorPanel);
		// end testing script, Khaled

		// call the server which implements the socket
		// call = "python mainBiDirectionLatest.py -C 3 -k 20 -c 0 -t 16 -T 4 -N
		// 2000 -d 4 -n 1 -D 1 -E 1 -F 1 -G 1 -H 1 -I 1 -A 1 -V 0 -b 600";
		/***
		 * greetingService.greetServer(call, new AsyncCallback<String>() {
		 * public void onFailure(Throwable caught) {
		 * 
		 * }
		 * 
		 * public void onSuccess(String result) {
		 * 
		 * } });
		 ***/
		// String link =
		// "http://"+Window.Location.getHostName()+":"+Window.Location.getPort()+"/appfin/";
		// String script = "?script="+call;
		// String url = link + "data" + script;
		//
		// Window.open( url, "_top", "status=0,toolbar=0,menubar=0,location=0");
		analyticsPanel.add(loadingPanel);
		loadingPanel.setVisible(true);
		tabs.setStyleName("pointer");
		requestSocket2(call);

	}

	/**
	 * Tests if present in array.
	 */
	private Boolean isPresent(String name) {
		for (String each : selectedItems)
			if (each.contentEquals(name))
				return true;
		return false;
	}

	private boolean verifyNumApps(int i) {
		if (i > NUMAPPSINDATASET || i <= 0) {

			return true;
		} else {

			return false;
		}
	}

	private boolean verifyBucketSize(int i) {
		if (i > MAXBUCKETSIZE || i <= 0) {

			return true;
		} else {

			return false;
		}
	}

	private boolean verifyNumTraining(int i) {
		if (i > MAXNUMTRAINING || i <= 0) {

			return true;

		} else {

			return false;
		}
	}

	private boolean verifyNumTesting(int i) {
		if (i > MAXNUMTESTING || i <= 0) {

			return true;
		} else {

			return false;
		}
	}

	private boolean verifyNumTrials(int i) {
		if (i > MAXNUMTRIALS || i <= 0) {

			return true;
		} else {

			return false;

		}
	}

	// get method to servlet
	public void requestSocket(String apps) {
		loadingPanel.setVisible(true);

		String link = "http://" + Window.Location.getHostName() + ":" + Window.Location.getPort() + "/appfin/";
		String script = "?script=" + apps;
		String url = link + "data" + script;

		// Window.open( url, "_top", "status=0,toolbar=0,menubar=0,location=0");

		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);
		try {
			Request response = builder.sendRequest(null, new RequestCallback() {
				public void onError(Request request, Throwable exception) {
					// openWindow("Request Failed", exception.toString());
					Window.alert("Request failed. " + exception.toString());
					// Window.Location.reload();
					loadingPanel.setVisible(false);
					tabs.removeStyleName("pointer");
				}

				public void onResponseReceived(Request request, Response response) {
					// openWindow("Request Succeded", response.getText());
					Window.alert("Request succeded. " + response.getText());
					// Window.Location.reload();
					loadingPanel.setVisible(false);
					tabs.removeStyleName("pointer");
				}
			});
		} catch (RequestException e) {
			e.printStackTrace();
		}
	}

	// get method to servlet
	public void requestSocket2(String call) {
		String link = "http://" + Window.Location.getHostName() + ":" + Window.Location.getPort() + "/appfin/";
		String script = "?script=" + call;
		String url = link + "data" + script;

		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);
		try {
			Request response = builder.sendRequest(null, new RequestCallback() {
				public void onError(Request request, Throwable exception) {
					// openWindow("Request Failed", exception.toString());
					// analyticsReplyLabel = new Label("request failed
					// "+exception.toString());
					// errorPanel.add(analyticsReplyLabel);
					// analyticsPanel.add(errorPanel);
					Window.alert("Request failed. " + exception.toString());
					loadingPanel.setVisible(false);
					tabs.removeStyleName("pointer");
					// Window.Location.reload();
				}

				public void onResponseReceived(Request request, Response response) {
					// openWindow("Request Succeded", response.getText());
					// analyticsReplyLabel = new Label("request succeded
					// "+response.getText());
					// errorPanel.add(analyticsReplyLabel);
					// analyticsPanel.add(errorPanel);
					Window.alert("Request succeded. " + response.getText());
					loadingPanel.setVisible(false);
					tabs.removeStyleName("pointer");
					// Window.Location.reload();
				}
			});
		} catch (RequestException e) {
			e.printStackTrace();
		}
	}

	// To get pcap and output folder names
	// modified 10/26/2016
	void getFolderNames() {
		greetingService.getFileNames(userTextBox.getText().trim(), new AsyncCallback<String[]>() {
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				folderNames[0] = "inside onFailure";
				folderList.addItem("inside onFailure");
			}

			@Override
			public void onSuccess(String[] result) {
				// TODO Auto-generated method stub
				folderNames = result;
				folderList.clear();
				for (String name : folderNames) {
					folderList.addItem(name);
				}
			}
		});
	}

	void homeClickHandler() {
		tabs.addSelectionHandler(new SelectionHandler<Integer>() {

			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				// TODO Auto-generated method stub
				// Download tab
				if (event.getSelectedItem() == 0) {
					// Code
					getFolderNames();
				} 
				// Data Analytics tab
				else if (event.getSelectedItem() == 3){
					clearAnalyticsButton.click();
				}

			}
		});
	}

	void authinticateUser(String user, String pass) {
		rpcDB.authenticateUser(user, pass, new AsyncCallback<User>() {
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Window.alert("Database connection failure! Please contact admin!");
			}

			@Override
			public void onSuccess(User user) {
				// TODO Auto-generated method stub
				if (user.isGrantAcecss() == true) {
					RootPanel.get("test").remove(loginParentPanel);
					onTabs();
				} else {
					Window.alert("Not a valid username or password.");
					Window.Location.reload();
				}

			}
		});

	}

	public static native void openWindow(String url, String message)/*-{
		var myWindow = window.open(url, "_blank",
				"status=0,toolbar=0,menubar=0,location=0,width=200,height=100");
		myWindow.document.write(message);
	}-*/;

	private class aNewUserClickHandler implements ClickHandler {

		AppFin entryPoint = null;

		public aNewUserClickHandler(AppFin entryPoint) {
			this.entryPoint = entryPoint;
		}

		@Override
		public void onClick(ClickEvent event) {
			System.out.println("inside the anchor");
			pageNewUser = new PageNewUser(entryPoint);
			RootPanel.get("test").remove(loginParentPanel);
			RootPanel.get("test").add(pageNewUser);

		}
	}

	private class aForgotPasswordClickHandler implements ClickHandler {

		AppFin entryPoint = null;

		public aForgotPasswordClickHandler(AppFin entryPoint) {
			this.entryPoint = entryPoint;
		}

		@Override
		public void onClick(ClickEvent event) {
			pageForgotPassword = new PageForgotPassword(entryPoint);
			RootPanel.get("test").remove(loginParentPanel);
			RootPanel.get("test").add(pageForgotPassword);

		}
	}
	
	private void setStyle() {
		
		// textboxes
		userTextBox.setStyleName("TextBox");
		passTextBox.setStyleName("TextBox");
		bucketTextBox.setStyleName("TextBox");
		appTextBox.setStyleName("TextBox");
		trialTextBox.setStyleName("TextBox");
		trainingTextBox.setStyleName("TextBox");
		testingTextBox.setStyleName("TextBox");
		
		
		// labels
		userLabel.setStyleName("Labels");
		passLabel.setStyleName("Labels");

		tempLabel.setStyleName("Labels");
		testLabel.setStyleName("Labels");
		classifierLabel.setStyleName("Labels");
		defenseLabel.setStyleName("Labels");
		datasetLabel.setStyleName("Labels");
		numAppsLabel.setStyleName("Labels");
		bucketLabel.setStyleName("Labels");
		numTrainingLabel.setStyleName("Labels");
		numTestingLabel.setStyleName("Labels");
		numTrialsLabel.setStyleName("Labels");

		collectionProgress.setStyleName("Labels");
		
		// buttons
		loginButton.setStyleName("Button_White");
		loginButton.getElement().getStyle().setCursor(Cursor.POINTER);
	
		downloadButton.setStyleName("Button_White");
		downloadButton.getElement().getStyle().setCursor(Cursor.POINTER);
		
		refreshButton.setStyleName("Button_White");
		refreshButton.getElement().getStyle().setCursor(Cursor.POINTER);
		
		addAppButton.setStyleName("Button_White");
		addAppButton.getElement().getStyle().setCursor(Cursor.POINTER);
		
		doneButton.setStyleName("Button_White");
		doneButton.getElement().getStyle().setCursor(Cursor.POINTER);
		
		analyticsButton.setStyleName("Button_White");
		analyticsButton.getElement().getStyle().setCursor(Cursor.POINTER);
		
		clearButton.setStyleName("Button_White");
		clearButton.getElement().getStyle().setCursor(Cursor.POINTER);
		
		clearAnalyticsButton.setStyleName("Button_White");
		clearAnalyticsButton.getElement().getStyle().setCursor(Cursor.POINTER);
		
	}

	public boolean validateData() {
		// code to validate data entry
		// add the widgets (text boxes) to an array list
		requiredControlsList = new ArrayList<TextBox>();
		requiredControlsList.add(userTextBox);
		requiredControlsList.add(passTextBox);

		if (Utils.CheckRequiredField(requiredControlsList)) {
			Window.alert("Please fill in required fields.");
			return false;
		}

		return true;
	}
	
	public boolean validDataAnalyticsFields(){
		requiredControlsList = new ArrayList<TextBox>();
		requiredControlsList.add(appTextBox);
		requiredControlsList.add(bucketTextBox);
		requiredControlsList.add(trialTextBox);
		requiredControlsList.add(trainingTextBox);
		requiredControlsList.add(testingTextBox);

		if (Utils.CheckRequiredField(requiredControlsList)) {
			Window.alert("Please fill in required fields.");
			return false;
		}
		
		// validate numbers
		validControlList = new HashMap<Object, String>();
		
		validControlList.put(appTextBox, "int");
		validControlList.put(bucketTextBox, "int");
		validControlList.put(trialTextBox, "int");
		validControlList.put(trainingTextBox, "int");
		validControlList.put(testingTextBox, "int");
		
		if (Utils.CheckValidField(validControlList)) { // returns true if invalid data detected
			Window.alert("Please fix highlighted fields.");
			return false;
		}
		
		// validate range in textboxes
		validControlList = new HashMap<Object, String>();
		
		validControlList.put(appTextBox, "10_2000");
		validControlList.put(bucketTextBox, "2_10");
		validControlList.put(trialTextBox, "1_1");
		validControlList.put(trainingTextBox, "1_16");
		validControlList.put(testingTextBox, "1_4");		

		if (Utils.CheckNumberRange(validControlList)) { // returns true if invalid data detected
			Window.alert("Please fix highlighted fields. Field ranges: (10 <= Number of Apps <= 2000) (2 <= Bucket Size <= 10) (Trials = 1) (1  <= Num Train Inst <= 16) (1  <= Num Test Inst <= 4)");
			return false;
		}
		
		return true;
	}
	
	
	private class classifierListChangeHandler implements ChangeHandler {

		public void onChange(ChangeEvent event) {
			// TODO Auto-generated method stub
			if (classifierList.getSelectedValue() == "23"){
				setFeatureList();
				featureList.setEnabled(true);
			}				
			else
				featureList.setEnabled(false);
			
		}
	}
	
	public void setFeatureList(){
		// 10/28/2016, select Bi-Di features by default (except ACK Packets)
		// loops through selected features
		for (int i = 0; i < featureList.getItemCount(); i++) {
			//featureList.setItemSelected(i, false);
			featureList.setItemSelected(i, true);
			
			// ACK Packets
			if (i == featureList.getItemCount() - 1)
				featureList.setItemSelected(i, false);
		}
	}
}
