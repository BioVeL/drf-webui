package org.bgbm.biovel.drf.client.ui;

import org.bgbm.biovel.drf.client.js.SubWorkflowChooserJS;
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


public class SubWorkflowChooser implements EntryPoint {

	private static final String ERR_NO_REFINE = "<p>Cannot access Google Refine server at ip : </p>"; 
	private RootPanel rootPanel;
	
	private static RadioButton rdbtnTaxonomicNameResolution;
	private static RadioButton rdbtnDataQuality;
	private static RadioButton rdbtnDataSelection;
	private static RadioButton rdbtnEndWorkflow;
	
	private static VerticalPanel statusVPanel;
	private static TextButton txtbtnOk;
	private static String refineHost = "127.0.0.1";
	private static String refinePort = "3333";
	
	private boolean settingsVisible = false;
	
	/**
	 * This is the entry point method.
	 */
	@Override
	public void onModuleLoad() {
		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		
		rootPanel = RootPanel.get();
		rootPanel.setSize("1024", "768");

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
		rootPanel.add(statusVPanel, 40, 313);
		verticalPanel.setCellHorizontalAlignment(statusVPanel, HasHorizontalAlignment.ALIGN_CENTER);
		statusVPanel.setSize("453px", "91px");
		
		Image image = new Image("filesaver/gwt/standard/images/loading.gif");
		statusVPanel.add(image);
		statusVPanel.setCellHorizontalAlignment(image, HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.setCellHorizontalAlignment(image, HasHorizontalAlignment.ALIGN_CENTER);
		
		Grid grid = new Grid(4, 2);
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
				
						rdbtnDataQuality = new RadioButton("wfchooser", "Data Quality (Google Refine)");
						grid.setWidget(1, 0, rdbtnDataQuality);
						rdbtnDataQuality.setWidth("318px");
						//newRefineErrorPopup("127:0.0..1:3333");

						rdbtnDataQuality.setStyleName("biovel-radiobutton");
						
						final PushButton dqEditButton = new PushButton("");
						grid.setWidget(1, 1, dqEditButton);
						
								dqEditButton.setHTML("<i class=\"icon-edit icon-large\"></i>");
								dqEditButton.setHeight("20");
								rdbtnDataSelection = new RadioButton("wfchooser", "Data Selection (BioSTIF)");
								grid.setWidget(2, 0, rdbtnDataSelection);
								
										rdbtnDataSelection.setStyleName("biovel-radiobutton");
										
										final PushButton dsEditButton = new PushButton("");
										grid.setWidget(2, 1, dsEditButton);
										dsEditButton.setEnabled(false);
										dsEditButton.setHTML("<i class=\"icon-edit icon-large\"></i>");
										dsEditButton.setHeight("20");
										
												rdbtnEndWorkflow = new RadioButton("wfchooser", "End Workflow");
												grid.setWidget(3, 0, rdbtnEndWorkflow);
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
		refineIPTBox.setText("127.0.0.1");
		refineIPTBox.setHeight("30");
		
		Label portLabel = new Label("Port : ");
		refineGrid.setWidget(1, 0, portLabel);
		portLabel.setStyleName("biovel-label");
		portLabel.setSize("104px", "30");
		
		final TextBox refinePortTBox = new TextBox();
		refineGrid.setWidget(1, 1, refinePortTBox);
		refinePortTBox.setText("3333");
		
		dqEditButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if(settingsVisible) {
					settingsPanel.setVisible(false);
				} else {
					//Window.alert("parent url : " + Document.get().getReferrer());
					String parentUrl = Document.get().getReferrer();
					//String parentUrl = "http://localhost:8080/interaction/interaction2e01ce7a540945e7bb490733c20e9247.html";
					refineHost = "";
					if(parentUrl.equals("")) {		
						refineHost = Window.Location.getHostName();
					} else {						
						String pattern = "^http://([^:]+):([^/]+)/(.*?)$";
						refineHost = parentUrl.replaceAll(pattern, "$1"); 
						//Window.alert("host : " + refineHost);						
					}	
					refineIPTBox.setText(refineHost);
					
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
					refineHost = refineIPTBox.getText();
					refinePort = refinePortTBox.getText();
					String imageUrl = "http://" + refineHost + ":" + refinePort + "/extension/biovel/resources/images/biovel.jpg";
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
		
		Label lblGoogleRefineIs = new Label("Google Refine is not accessible at IP : " + refineHost + ":" + refinePort);
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
		
		Label lblTavernaLite = new Label("If the workflow is being executed on a Taverna Lite installation please contact support@biovel.eu");
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
}
