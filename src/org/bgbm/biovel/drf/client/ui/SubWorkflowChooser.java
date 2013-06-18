package org.bgbm.biovel.drf.client.ui;

import org.bgbm.biovel.drf.client.js.SubWorkflowChooserJS;
import org.bgbm.biovel.drf.client.widgets.ClickableLabel;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.widget.client.TextButton;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.HTMLPanel;


public class SubWorkflowChooser implements EntryPoint {

	private static final String ERR_NO_REFINE = "<p>Cannot access Google Refine server at ip : </p>"; 
	private RootPanel rootPanel;
	
	/**
	 * This is the entry point method.
	 */
	@Override
	public void onModuleLoad() {
		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		final SubWorkflowChooserJS swcjs = (SubWorkflowChooserJS)JavaScriptObject.createObject();
		swcjs.init();
		rootPanel = RootPanel.get();
		rootPanel.setSize("1024", "768");

		FlexTable flexTable = new FlexTable();
		flexTable.setCellPadding(10);
		rootPanel.add(flexTable, 10, 10);
		flexTable.setSize("507px", "249px");

						
		Label lblChooseSubworkflow = new Label("Choose Sub-Workflow");
		flexTable.setWidget(0, 0, lblChooseSubworkflow);
		lblChooseSubworkflow.setWordWrap(false);
		lblChooseSubworkflow.setStyleName("biovel-header-label");

		VerticalPanel verticalPanel = new VerticalPanel();
		flexTable.setWidget(1, 0, verticalPanel);
		verticalPanel.setSize("457px", "131px");
		//newJarDependencyErrorPopup();
		final RadioButton rdbtnTaxonomicNameResolution = new RadioButton("new name", "Taxonomic Name Resolution / Occurrence Retrieval");
		rdbtnTaxonomicNameResolution.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				PopupPanel pp = newJarDependencyErrorPopup();
				pp.center();
				pp.show();
			}
		});
		rdbtnTaxonomicNameResolution.setStyleName("biovel-radiobutton");
		verticalPanel.add(rdbtnTaxonomicNameResolution);

		final RadioButton rdbtnDataQuality = new RadioButton("new name", "Data Quality / Cleaning");
		//newRefineErrorPopup();
		rdbtnDataQuality.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				PopupPanel pp = newRefineErrorPopup();
				pp.center();
				pp.show();
			}
		});
		rdbtnDataQuality.setStyleName("biovel-radiobutton");
		verticalPanel.add(rdbtnDataQuality);

		final RadioButton rdbtnDataSelection = new RadioButton("new name", "Data Selection");
		rdbtnDataSelection.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
			}
		});
		rdbtnDataSelection.setStyleName("biovel-radiobutton");
		verticalPanel.add(rdbtnDataSelection);

		final RadioButton rdbtnEndWorkflow = new RadioButton("new name", "End Workflow");
		rdbtnEndWorkflow.setValue(true);
		rdbtnEndWorkflow.setStyleName("biovel-radiobutton");
		rdbtnEndWorkflow.setHTML("End Workflow");
		verticalPanel.add(rdbtnEndWorkflow);
		flexTable.getCellFormatter().setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_CENTER);

		TextButton txtbtnOk = new TextButton("OK");
		flexTable.setWidget(2, 0, txtbtnOk);		

		txtbtnOk.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
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
		});

	}
	
	public PopupPanel newRefineErrorPopup() {
		final PopupPanel popupPanel = new PopupPanel(); 
		//rootPanel.add(popupPanel, 10, 290);
		
		popupPanel.setSize("495px", "174px");

		VerticalPanel errRefineVPanel = new VerticalPanel();
		errRefineVPanel.setSpacing(10);
		popupPanel.setWidget(errRefineVPanel);
		errRefineVPanel.setSize("461px", "172px");
		
		Label lblGoogleRefineIs = new Label("Google Refine is not accessible at IP :");
		errRefineVPanel.add(lblGoogleRefineIs);
		
		Label lblNewLabel = new Label("Please check if the server is running");
		errRefineVPanel.add(lblNewLabel);
		
		Label lblToInstallRefine = new Label("To install Google Refine please refer to installation instructions on the BioVel");
		errRefineVPanel.add(lblToInstallRefine);
		lblToInstallRefine.setWidth("451px");
		
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
