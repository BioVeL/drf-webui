package org.bgbm.biovel.drf.client.ui;

import org.bgbm.biovel.drf.client.js.InputChooserJS;
import org.bgbm.biovel.drf.client.js.PmrpcJS;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.widget.client.TextButton;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.FlexTable;

public class InputChooser implements EntryPoint {

	@Override
	public void onModuleLoad() {
		PmrpcJS.initialize();	  
		// Use RootPanel.get() to get the entire body element
		final InputChooserJS icjs = (InputChooserJS)JavaScriptObject.createObject();
		RootPanel rootPanel = RootPanel.get();
		rootPanel.setSize("1024", "768");
		
		FlexTable flexTable = new FlexTable();
		flexTable.setBorderWidth(0);
		flexTable.setCellPadding(10);
		rootPanel.add(flexTable, 10, 10);
		flexTable.setSize("319px", "202px");
		Label lblChooseInputFile = new Label("Choose Input file");
		flexTable.setWidget(0, 0, lblChooseInputFile);
		lblChooseInputFile.setStyleName("biovel-header-label");
		
		FormPanel formPanel = new FormPanel();
		flexTable.setWidget(1, 0, formPanel);
		formPanel.setMethod(FormPanel.METHOD_POST);
		formPanel.setSize("285px", "42px");
		
		FileUpload fileUpload = new FileUpload();
		formPanel.setWidget(fileUpload);
		fileUpload.setSize("100%", "100%");
		fileUpload.setName("fileInput");
		flexTable.getFlexCellFormatter().setColSpan(0, 0, 1);
		flexTable.getFlexCellFormatter().setColSpan(1, 0, 1);
		
		Label lblEncoding = new Label("encoding : ");
		flexTable.setWidget(2, 0, lblEncoding);
		lblEncoding.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		lblEncoding.setSize("125px", "24px");
		
		final TextBox txtbxUtf = new TextBox();
		flexTable.setWidget(2, 1, txtbxUtf);
		txtbxUtf.setText("UTF-8");
		txtbxUtf.setSize("140px", "16px");
		flexTable.getFlexCellFormatter().setColSpan(0, 0, 2);
		flexTable.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.getCellFormatter().setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		flexTable.getFlexCellFormatter().setColSpan(1, 0, 2);
		
		TextButton txtbtnOk = new TextButton("Submit");
		flexTable.setWidget(3, 0, txtbtnOk);
		
		TextButton txtbtnNewButton = new TextButton("CANCEL");
		flexTable.setWidget(3, 1, txtbtnNewButton);
		flexTable.getCellFormatter().setHorizontalAlignment(3, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		txtbtnNewButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				icjs.doReplyCANCEL();
			}
		});
		txtbtnOk.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				String encoding = (txtbxUtf.getText().equals("")?"UTF-8":txtbxUtf.getText());
				icjs.doFileReplyOK(encoding);
				
			}
		});

	}
}
