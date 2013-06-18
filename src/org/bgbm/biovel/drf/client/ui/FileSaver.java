package org.bgbm.biovel.drf.client.ui;

import org.bgbm.biovel.drf.client.js.FileSaverJS;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.widget.client.TextButton;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Label;

public class FileSaver implements EntryPoint {

	@Override
	public void onModuleLoad() {
		FileSaverJS.init();
		initComponents();

	}
	
	public static void initComponents() {
		RootPanel rootPanel = RootPanel.get();
		rootPanel.setStyleName("biovel-output-link");
		rootPanel.setSize("1024", "768");
		
		Anchor anchor = new Anchor();
		
		anchor.setHref(FileSaverJS.getOutputFileUrl());
		anchor.getElement().setAttribute("download", "DRFOutput.csv");
		anchor.setStyleName("biovel-output-link");
		anchor.setText("download output file");
		rootPanel.add(anchor, 10, 46);
		anchor.setSize("172px", "36px");
		
		Label lblNewLabel = new Label("Data Refinement Workflow completed successfully  !!");
		lblNewLabel.setStyleName("biovel-header-label");
		rootPanel.add(lblNewLabel, 10, 10);
		
		TextButton txtbtnOk = new TextButton("OK");
		txtbtnOk.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				FileSaverJS fsjs = (FileSaverJS)JavaScriptObject.createObject();
				fsjs.setEndWorkflowOK(true);
				fsjs.okReply();
			}
		});
		rootPanel.add(txtbtnOk, 10, 88);
	}
}
