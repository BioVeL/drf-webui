package org.bgbm.biovel.drf.client.ui;

import java.net.MalformedURLException;


import org.bgbm.biovel.drf.client.js.SubWorkflowChooserJS;
import org.bgbm.biovel.drf.client.js.TaxoServiceProviderChooserJS;
import org.bgbm.biovel.drf.client.utils.JSUtils;
import org.bgbm.biovel.drf.client.widgets.ClickableLabel;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.widget.client.TextButton;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ScrollPanel;


public class SubWorkflowChooser implements EntryPoint {

	private static final String ERR_NO_REFINE = "<p>Cannot access Google Refine server at ip : </p>"; 
	private static RootPanel rootPanel;

	private static RadioButton rdbtnTaxonomicNameResolution;
	private static RadioButton rdbtnDataQuality;
	private static RadioButton rdbtnDataSelection;
	private static RadioButton rdbtnEndWorkflow;

	private static VerticalPanel statusVPanel;
	private static TextButton txtbtnOk;
	private static String localHost = "127.0.0.1";
	private static String refineInteractionServer = "127.0.0.1";
	private static String refineServer = "127.0.0.1";
	private static String refinePort = "3333";

	private static boolean settingsVisible = false;

	/**
	 * This is the entry point method.
	 */
	@Override
	public void onModuleLoad() {
		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element

		rootPanel = RootPanel.get();
		rootPanel.setSize("1024", "768");
		
		initComponents();		
	}

	public static void initComponents() {	

		FlexTable flexTable = new FlexTable();
		flexTable.setCellPadding(10);
		rootPanel.add(flexTable, 10, 10);
		flexTable.setSize("597px", "273px");


		Label lblChooseSubworkflow = new Label("Choose Sub-Workflow");
		flexTable.setWidget(0, 0, lblChooseSubworkflow);
		lblChooseSubworkflow.setWordWrap(false);
		lblChooseSubworkflow.setStyleName("biovel-header-label");

		VerticalPanel verticalPanel = new VerticalPanel();
		flexTable.setWidget(1, 0, verticalPanel);
		verticalPanel.setSize("577px", "144px");
		flexTable.getCellFormatter().setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_CENTER);

		txtbtnOk = new TextButton("OK");
		flexTable.setWidget(2, 0, txtbtnOk);		

		statusVPanel = new VerticalPanel();
		rootPanel.add(statusVPanel, 74, 471);
		verticalPanel.setCellHorizontalAlignment(statusVPanel, HasHorizontalAlignment.ALIGN_CENTER);
		statusVPanel.setSize("453px", "91px");

		Image image = new Image("filesaver/gwt/standard/images/loading.gif");
		statusVPanel.add(image);
		statusVPanel.setCellHorizontalAlignment(image, HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.setCellHorizontalAlignment(image, HasHorizontalAlignment.ALIGN_CENTER);

		Grid grid = new Grid(7, 2);
		grid.setCellPadding(5);
		verticalPanel.add(grid);
		grid.setSize("577px", "145px");
		//newJarDependencyErrorPopup();
		rdbtnTaxonomicNameResolution = new RadioButton("wfchooser", "Taxonomic Name Resolution / Occurrence Retrieval");
		grid.setWidget(0, 0, rdbtnTaxonomicNameResolution);
		rdbtnTaxonomicNameResolution.setWidth("523px");

		rdbtnTaxonomicNameResolution.setStyleName("biovel-label");

		final PushButton tnrEditButton = new PushButton("");
		grid.setWidget(0, 1, tnrEditButton);
		tnrEditButton.setEnabled(false);
		tnrEditButton.setHTML("<i class=\"icon-edit icon-large\"></i>");
		tnrEditButton.setHeight("20");

		HTML htmlNewHtml = new HTML("<div style=\"padding-left: 25px;\">Taxonomic Name Resolution requires the input data file to contain a column named 'nameComplete', as explained <a href=\"https://wiki.biovel.eu/display/doc/Preparing+your+input+data+for+Data+Refinement\">here</a>. Other columns (if present) will not be considered for the purposes of the workflow run. </div>", true);
		htmlNewHtml.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		grid.setWidget(1, 0, htmlNewHtml);

		rdbtnDataQuality = new RadioButton("wfchooser", "Data Quality (Google Refine)");
		grid.setWidget(2, 0, rdbtnDataQuality);
		rdbtnDataQuality.setWidth("318px");
		//newRefineErrorPopup("127:0.0..1:3333");

		rdbtnDataQuality.setStyleName("biovel-radiobutton");

		HTML htmlThePossibilitiesFor = new HTML("<div style=\"padding-left: 25px;\">The possibilities for improving the quality of data are flexible and depend on the content of the input data file. As long as the file contains at least one named column of data the workflow will run. </div>", true);
		grid.setWidget(3, 0, htmlThePossibilitiesFor);


		rdbtnDataSelection = new RadioButton("wfchooser", "Data Selection (BioSTIF)");
		grid.setWidget(4, 0, rdbtnDataSelection);

		rdbtnDataSelection.setStyleName("biovel-radiobutton");

		final PushButton dsEditButton = new PushButton("");
		grid.setWidget(4, 1, dsEditButton);
		dsEditButton.setEnabled(false);
		dsEditButton.setHTML("<i class=\"icon-edit icon-large\"></i>");
		dsEditButton.setHeight("20");

		HTML htmlBiostifRequiresThe = new HTML("<div style=\"padding-left: 25px;\">Geographical Data Selection (BioSTIF) requires the input data file to contain columns named 'decimalLatitude' and 'decimalLongitude' with valid coordinates, as explained <a href=\"https://wiki.biovel.eu/display/doc/Preparing+your+input+data+for+Data+Refinement\">here</a>. Do not select this option if your data file does not contain this information. </div>", true);
		grid.setWidget(5, 0, htmlBiostifRequiresThe);

		rdbtnEndWorkflow = new RadioButton("wfchooser", "End Workflow");
		grid.setWidget(6, 0, rdbtnEndWorkflow);
		rdbtnEndWorkflow.setValue(true);
		rdbtnEndWorkflow.setStyleName("biovel-radiobutton");
		rdbtnEndWorkflow.setHTML("End Workflow");



		image.setSize("97px", "83px");

		final Label lblStatus = new Label("");
		statusVPanel.add(lblStatus);
		statusVPanel.setCellVerticalAlignment(lblStatus, HasVerticalAlignment.ALIGN_MIDDLE);
		statusVPanel.setCellHorizontalAlignment(lblStatus, HasHorizontalAlignment.ALIGN_CENTER);
		statusVPanel.setVisible(false);
		lblStatus.setSize("224px", "25");

		final CaptionPanel settingsPanel = new CaptionPanel("");
		settingsPanel.setCaptionHTML("");
		rootPanel.add(settingsPanel, 629, 10);
		settingsPanel.setSize("401px", "257px");
		settingsPanel.setVisible(false);
		final VerticalPanel refinePanel = new VerticalPanel();
		refinePanel.setSpacing(5);
		settingsPanel.setContentWidget(refinePanel);
		refinePanel.setSize("399px", "129px");



		Label titleLabel = new Label("Google Refine Settings");
		titleLabel.setStyleName("biovel-header-label");
		titleLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		refinePanel.add(titleLabel);

		Grid refineGrid = new Grid(2, 2);
		refinePanel.add(refineGrid);
		refineGrid.setWidth("388px");

		Label ipLabel = new Label("IP Address : ");
		refineGrid.setWidget(0, 0, ipLabel);
		ipLabel.setStyleName("biovel-label");
		ipLabel.setSize("193px", "30");

		final TextBox refineIPTBox = new TextBox();
		refineGrid.setWidget(0, 1, refineIPTBox);
		localHost = "127.0.0.1";
		//Window.alert("parent url : " + Document.get().getReferrer());
		String parentUrl = Document.get().getReferrer();
		//String parentUrl = "http://localhost:8080/interaction/interaction2e01ce7a540945e7bb490733c20e9247.html";
		//parentUrl = "http://portal.at.biovel.eu:3333/runs/4";
		if(parentUrl.equals("")) {		
			localHost = Window.Location.getHostName();
		} else {									
			localHost = getHost(parentUrl);			
		}	
		refineInteractionServer = localHost;
		if(refineInteractionServer.endsWith("biovel.eu")) {
			refineServer = "90.147.102.41";
			refinePort = "80";
		} else {
			refineServer = "127.0.0.1";
			refinePort = "3333";
		}
		//Window.alert("host : " + refineHost);					
		refineIPTBox.setText(refineInteractionServer);
		refineIPTBox.setHeight("30");

		Label portLabel = new Label("Port : ");
		refineGrid.setWidget(1, 0, portLabel);
		portLabel.setStyleName("biovel-label");
		portLabel.setSize("104px", "30");

		final TextBox refinePortTBox = new TextBox();
		refineGrid.setWidget(1, 1, refinePortTBox);
		
		refinePortTBox.setText(refinePort);

		final PushButton dqEditButton = new PushButton("");
		grid.setWidget(2, 1, dqEditButton);

		dqEditButton.setHTML("<i class=\"icon-edit icon-large\"></i>");
		dqEditButton.setHeight("20");


		dqEditButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if(settingsVisible) {
					refineInteractionServer = refineIPTBox.getText();
					refinePort = refinePortTBox.getText();
					settingsPanel.setVisible(false);
				} else {
					refineIPTBox.setText(refineInteractionServer);	
					refinePortTBox.setText(refinePort);
					settingsPanel.setVisible(true);
					refinePanel.setVisible(true);
					settingsPanel.setCaptionText("Data Quality Settings");
				}
				settingsVisible = !settingsVisible;
			}
		});

		txtbtnOk.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				if (rdbtnTaxonomicNameResolution.getValue()) {
					reply();
				}

				if(rdbtnDataQuality.getValue()) {			
					refineInteractionServer = refineIPTBox.getText();
					refinePort = refinePortTBox.getText();
					if(refineInteractionServer.equals(localHost)) {
						refineServer = "127.0.0.1";
					} else {
						refineServer = refineInteractionServer;
					}
					String imageUrl = "http://" + refineInteractionServer + ":" + refinePort + "/extension/biovel/resources/images/biovel.jpg";
					JSUtils.pingRefine(imageUrl);					
					statusVPanel.setVisible(true);
					txtbtnOk.setEnabled(false);
					lblStatus.setText("Checking Refine server ....");
				}

				if(rdbtnDataSelection.getValue()) {
					reply();
				}

				if(rdbtnEndWorkflow.getValue()) {
					reply();
				}

			}
		});

		if(SubWorkflowChooserJS.getEmptyDataFlag().equals("true")) {
			//Window.alert("Intermediate data is empty. Workflow will terminate now");
			newEmptyDataErrorPopup();						

		}

	}

	public static void reply() {
		statusVPanel.setVisible(false);
		txtbtnOk.setEnabled(true);
		final SubWorkflowChooserJS swcjs = (SubWorkflowChooserJS)JavaScriptObject.createObject();
		swcjs.init();

		if (rdbtnTaxonomicNameResolution.getValue()) {
			swcjs.setTnrocShouldRun();
		}
		if(rdbtnDataQuality.getValue()) {			
			swcjs.setDataQualityShouldRun();	
			Window.alert("refineInteractionServer : " + refineInteractionServer 
					+ " , refineServer : " + refineServer 
					+ " , refinePort : " + refinePort);					
			swcjs.setRefineHost(refineInteractionServer  + ":" + refinePort);		
			swcjs.setRefineServer(refineServer);
			swcjs.setRefinePort(refinePort);
		}
		if(rdbtnDataSelection.getValue()) {
			swcjs.setDataSelectionShouldRun();
		}
		if(rdbtnEndWorkflow.getValue()) {
			swcjs.setEndWorkflowShouldRun();
		}

		swcjs.okReply();
	}


	public static void newRefineErrorPopup() {
		statusVPanel.setVisible(false);
		txtbtnOk.setEnabled(true);
		final PopupPanel popupPanel = new PopupPanel(); 
		//rootPanel.add(popupPanel, 10, 290);
		popupPanel.setSize("507px", "178px");
		popupPanel.setGlassEnabled(true);

		VerticalPanel errRefineVPanel = new VerticalPanel();
		errRefineVPanel.setSpacing(10);
		popupPanel.setWidget(errRefineVPanel);
		errRefineVPanel.setSize("607px", "172px");		

		Label lblGoogleRefineIs = new Label("Google Refine is not accessible at IP : " + refineInteractionServer + ":" + refinePort);
		errRefineVPanel.add(lblGoogleRefineIs);

		Label lblNewLabel = new Label("If the workflow is being executed on the Taverna Workbench, please check if the refine server is running.");
		errRefineVPanel.add(lblNewLabel);

		HorizontalPanel horizontalPanel = new HorizontalPanel();
		errRefineVPanel.add(horizontalPanel);

		Label lblInstallRefine = new Label("To install Google Refine locally please refer to installation instructions on the BioVel");
		lblInstallRefine.setWordWrap(false);
		horizontalPanel.add(lblInstallRefine);
		lblInstallRefine.setWidth("493px");

		ClickableLabel lblWikiPage = new ClickableLabel("wiki page");
		horizontalPanel.add(lblWikiPage);
		lblWikiPage.setText("wiki page.");
		lblWikiPage.setStyleName("biovel-ext-link");
		lblWikiPage.setHref("https://wiki.biovel.eu/display/doc/Installing+and+running+DR+Workflow+on+Taverna+Workbench");

		Label lblTavernaLite = new Label("If the workflow is being executed on a BioVeL Portal installation please contact support@biovel.eu");
		errRefineVPanel.add(lblTavernaLite);
		TextButton errtxtbtnClose = new TextButton("OK");
		errRefineVPanel.add(errtxtbtnClose);
		errtxtbtnClose.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				popupPanel.hide();
			}
		});
		popupPanel.center();
		popupPanel.show();		
	}

	public PopupPanel newJarDependencyErrorPopup() {
		final PopupPanel popupPanel = new PopupPanel(); 
		//rootPanel.add(popupPanel, 10, 290);
		popupPanel.setSize("253px", "137px");

		VerticalPanel errRefineVPanel = new VerticalPanel();
		errRefineVPanel.setSpacing(10);
		popupPanel.setWidget(errRefineVPanel);
		errRefineVPanel.setSize("217px", "128px");

		Label lblGoogleRefineIs = new Label("BioVeL DRF Plugin not installed !");
		errRefineVPanel.add(lblGoogleRefineIs);

		Label lblToInstallRefine = new Label("To install the plugin please refer to the installation instructions on the BioVeL");
		errRefineVPanel.add(lblToInstallRefine);
		lblToInstallRefine.setWidth("233px");

		ClickableLabel lblWikiPage = new ClickableLabel("wiki page");
		lblWikiPage.setStyleName("biovel-ext-link");
		lblWikiPage.setHref("https://wiki.biovel.eu/display/doc/Installing+DR+Workflow+on+Taverna+Workbench");
		errRefineVPanel.add(lblWikiPage);
		TextButton errtxtbtnClose = new TextButton("OK");
		errRefineVPanel.add(errtxtbtnClose);
		errtxtbtnClose.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				popupPanel.hide();
			}
		});

		return popupPanel;
	}

	public static void newEmptyDataErrorPopup() {
		final PopupPanel popupPanel = new PopupPanel(); 
		//rootPanel.add(popupPanel, 10, 290);
		popupPanel.setSize("400px", "137px");
		popupPanel.setGlassEnabled(true);

		VerticalPanel errEmptyDataPanel = new VerticalPanel();
		errEmptyDataPanel.setSpacing(10);
		popupPanel.setWidget(errEmptyDataPanel);
		errEmptyDataPanel.setSize("217px", "128px");

		Label lblEmptyData = new Label("Input Data is empty.");
		errEmptyDataPanel.add(lblEmptyData);

		Label lblEndLabel = new Label(" Workflow will terminate now!");
		errEmptyDataPanel.add(lblEndLabel);

		TextButton errtxtbtnClose = new TextButton("OK");
		errEmptyDataPanel.add(errtxtbtnClose);
		errtxtbtnClose.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				popupPanel.hide();
				reply();
			}
		});

		popupPanel.center();
		popupPanel.show();		
	}
	
	public static String getHost(String url){
	    if(url == null || url.length() == 0)
	        return "";

	    int doubleslash = url.indexOf("//");
	    if(doubleslash == -1)
	        doubleslash = 0;
	    else
	        doubleslash += 2;

	    int end = url.indexOf('/', doubleslash);
	    end = end >= 0 ? end : url.length();

	    int port = url.indexOf(':', doubleslash);
	    end = (port > 0 && port < end) ? port : end;

	    return url.substring(doubleslash, end);
	}
}
