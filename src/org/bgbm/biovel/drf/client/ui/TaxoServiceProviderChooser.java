package org.bgbm.biovel.drf.client.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.bgbm.biovel.drf.client.js.ChecklistInfoJS;
import org.bgbm.biovel.drf.client.js.TaxoServiceProviderChooserJS;
import org.bgbm.biovel.drf.client.resources.ClickableTableResources;
import org.bgbm.biovel.drf.client.utils.JSUtils;
import org.bgbm.biovel.drf.client.widgets.ChecklistInfoCIRCell;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.widget.client.TextButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.user.client.ui.SimpleCheckBox;
import com.google.gwt.user.client.ui.CheckBox;

public class TaxoServiceProviderChooser implements EntryPoint {

	static RootPanel rootPanel;
	private static List<String> agreedCpList;

	@Override
	public void onModuleLoad() {

		// Use RootPanel.get() to get the entire body element
		rootPanel = RootPanel.get();
		rootPanel.setSize("1024", "768");		

		agreedCpList = new ArrayList<String>();
		TaxoServiceProviderChooserJS.init();		
		initComponents();


	}

	public static void initComponents() {		
		final TaxoServiceProviderChooserJS tccjs = (TaxoServiceProviderChooserJS)JavaScriptObject.createObject();					
		final JsArray<ChecklistInfoJS> checklists= tccjs.getAllServices();

		final List<ChecklistInfoJS> stdChecklists = new ArrayList<ChecklistInfoJS>();
		final List<ChecklistInfoJS> aggChecklists = new ArrayList<ChecklistInfoJS>();

		for(int i =0;i < checklists.length();i++) {
			ChecklistInfoJS ci = checklists.get(i);

			if(ci.getSubChecklists() != null) {
				aggChecklists.add(ci);
			} else {				
				stdChecklists.add(ci);

			}
		}


		FlexTable flexTable = new FlexTable();
		flexTable.setCellPadding(10);
		rootPanel.add(flexTable, 81, 41);
		flexTable.setSize("100px", "100px");

		String header = "";
		String docTitle = "";
		String stdTitle = "";
		String aggTitle = "";

		if(Window.Location.getParameter("type")!= null && Window.Location.getParameter("type").equals("occ")) {
			header = "Choose Occurrence Bank to target";
			docTitle = "Occurrence Bank Chooser";
			stdTitle = "Standard Occurrence Banks";
			aggTitle = "";
		} else {
			header = "Choose Taxonomic Checklist to target";
			docTitle = "Checklist Chooser";
			stdTitle = "Standard Checklists";
			aggTitle = "Aggregated Checklists";
		}

		if (Document.get() != null) {
			Document.get().setTitle (docTitle);
		}

		Label lblNewLabel = new Label(header);
		flexTable.setWidget(0, 0, lblNewLabel);
		lblNewLabel.setSize("667px", "");
		lblNewLabel.setStyleName("biovel-header-label");
		flexTable.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);

		VerticalPanel verticalPanel_1 = new VerticalPanel();
		flexTable.setWidget(1, 0, verticalPanel_1);
		verticalPanel_1.setSize("666px", "492px");

		HorizontalPanel horizontalPanel = new HorizontalPanel();
		verticalPanel_1.add(horizontalPanel);
		horizontalPanel.setSize("315px", "");

		VerticalPanel verticalPanel = new VerticalPanel();
		horizontalPanel.add(verticalPanel);
		verticalPanel.setSize("400px", "");

		Label lblNewLabel_1 = new Label(stdTitle);
		lblNewLabel_1.setStyleName("biovel-table-label");
		verticalPanel.add(lblNewLabel_1);
		verticalPanel.setCellHorizontalAlignment(lblNewLabel_1, HasHorizontalAlignment.ALIGN_CENTER);



		final CellTable<ChecklistInfoJS> stdChecklistCellTable = 
				new CellTable<ChecklistInfoJS>(stdChecklists.size(),GWT.<ClickableTableResources>create(ClickableTableResources.class));

		final ListDataProvider<ChecklistInfoJS> stdDataProvider = new ListDataProvider<ChecklistInfoJS>();

		final MultiSelectionModel<ChecklistInfoJS> stdSelectionModel = new MultiSelectionModel<ChecklistInfoJS>();		
		stdChecklistCellTable.setSelectionModel(stdSelectionModel,
				DefaultSelectionEventManager.<ChecklistInfoJS> createCheckboxManager());
		stdSelectionModel.addSelectionChangeHandler(UIFactory.createCopyrightHandler(stdSelectionModel,agreedCpList));

		// Add the cellList to the dataProvider.
		stdDataProvider.addDataDisplay(stdChecklistCellTable);
		stdDataProvider.setList(stdChecklists);
		stdChecklistCellTable.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.DISABLED);
		verticalPanel.add(stdChecklistCellTable);
		stdChecklistCellTable.setSize("390px", "");

		Column<ChecklistInfoJS, Boolean> stdChooseColumn = new Column<ChecklistInfoJS, Boolean>(new CheckboxCell()) {
			@Override
			public Boolean getValue(ChecklistInfoJS object) {				
				return stdSelectionModel.isSelected(object);
			}
		};		

		stdChecklistCellTable.addColumn(stdChooseColumn);

		Column<ChecklistInfoJS, String> stdNameColumn = new Column<ChecklistInfoJS, String>(new ClickableTextCell()) {
			@Override
			public String getValue(ChecklistInfoJS object) {
				return object.getLabel();
			}
		};

		stdChecklistCellTable.addColumn(stdNameColumn);

		Column<ChecklistInfoJS, String> stdUrlColumn = new Column<ChecklistInfoJS, String>(new ChecklistInfoCIRCell()) {
			@Override
			public String getValue(ChecklistInfoJS object) {
				return object.getUrl();
			}
		};

		stdChecklistCellTable.addColumn(stdUrlColumn);
		stdChecklistCellTable.setColumnWidth(stdUrlColumn, "50");		

		Label lblNewLabel_2 = new Label("  ");
		verticalPanel.add(lblNewLabel_2);
		lblNewLabel_2.setHeight("22px");

		Label lblAggregatedChecklists = new Label(aggTitle);
		lblAggregatedChecklists.setStyleName("biovel-table-label");
		verticalPanel.add(lblAggregatedChecklists);
		verticalPanel.setCellHorizontalAlignment(lblAggregatedChecklists, HasHorizontalAlignment.ALIGN_CENTER);


		CellTable<ChecklistInfoJS> aggChecklistCellTable = 
				new CellTable<ChecklistInfoJS>(aggChecklists.size(),GWT.<ClickableTableResources>create(ClickableTableResources.class));
		aggChecklistCellTable.setTableLayoutFixed(true);
		verticalPanel.add(aggChecklistCellTable);
		verticalPanel.setCellHeight(aggChecklistCellTable, "10px");
		aggChecklistCellTable.setSize("390px", "");

		Column<ChecklistInfoJS, String> aggNameColumn = new Column<ChecklistInfoJS, String>(new ClickableTextCell()) {
			@Override
			public String getValue(ChecklistInfoJS object) {
				return object.getLabel();
			}
		};

		aggChecklistCellTable.addColumn(aggNameColumn);

		Column<ChecklistInfoJS, String> aggUrlColumn = new Column<ChecklistInfoJS, String>(new ChecklistInfoCIRCell()) {
			@Override
			public String getValue(ChecklistInfoJS object) {
				return object.getUrl();
			}
		};

		aggChecklistCellTable.addColumn(aggUrlColumn);
		aggChecklistCellTable.setColumnWidth(aggUrlColumn, "50");

		aggChecklistCellTable.setRowCount(aggChecklists.size(), true);	    
		aggChecklistCellTable.setRowData(0,aggChecklists);

		final CellTable<ChecklistInfoJS> aggListCellTable = 
				new CellTable<ChecklistInfoJS>(15,GWT.<ClickableTableResources>create(ClickableTableResources.class));

		//final ListDataProvider<ChecklistInfoJS> ciDataProvider = new ListDataProvider<ChecklistInfoJS>();
		
		final VerticalPanel aggListVPanel = new VerticalPanel();
		aggListVPanel.setSpacing(5);
		horizontalPanel.add(aggListVPanel);
		

		aggListVPanel.setVisible(false);
		aggListCellTable.setVisible(true);
		
		final FilteredListDataProvider<ChecklistInfoJS> ciDataProvider = new FilteredListDataProvider<ChecklistInfoJS>(new ChecklistInfoFilter());
		
		HorizontalPanel filterHPanel = new HorizontalPanel();
		filterHPanel.setSpacing(5);
		aggListVPanel.add(filterHPanel);
		filterHPanel.setWidth("410px");
		
		Label filterLbl = new Label("filter : ");
		filterHPanel.add(filterLbl);
		filterHPanel.setCellVerticalAlignment(filterLbl, HasVerticalAlignment.ALIGN_MIDDLE);
		filterHPanel.setCellHorizontalAlignment(filterLbl, HasHorizontalAlignment.ALIGN_CENTER);
		filterLbl.setWidth("46px");
		
		final TextBox aggListFilterTBox = new TextBox();
		filterHPanel.add(aggListFilterTBox);
		aggListVPanel.setCellVerticalAlignment(aggListFilterTBox, HasVerticalAlignment.ALIGN_MIDDLE);
		aggListVPanel.setCellHorizontalAlignment(aggListFilterTBox, HasHorizontalAlignment.ALIGN_CENTER);
		aggListFilterTBox.setWidth("254px");
		aggListFilterTBox.setVisible(true);
		
		final CheckBox substringCBox = new CheckBox("substring");
		filterHPanel.add(substringCBox);
		substringCBox.setWidth("72px");
		
		substringCBox.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				ciDataProvider.setFilter(aggListFilterTBox.getText(), event.getValue());
			}
		});
					
		aggListFilterTBox.addKeyUpHandler(new KeyUpHandler() {
			public void onKeyUp(KeyUpEvent event) {
				ciDataProvider.setFilter(aggListFilterTBox.getText(), substringCBox.getValue());
			}
		});
		aggListFilterTBox.addValueChangeHandler(new ValueChangeHandler<String>() {
			public void onValueChange(ValueChangeEvent<String> event) {
				ciDataProvider.setFilter(aggListFilterTBox.getText(), substringCBox.getValue());
			}
		});
		

		
		
		final SingleSelectionModel<ChecklistInfoJS> aggSelectionModel = new SingleSelectionModel<ChecklistInfoJS>();
		aggSelectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				ChecklistInfoJS selected = aggSelectionModel.getSelectedObject();				
				JsArray<ChecklistInfoJS> subArray = selected.getSubChecklists();
				aggListVPanel.setVisible(false);
				aggListFilterTBox.setText("");
				substringCBox.setValue(false);
				if(subArray != null && subArray.length() > 0) {					
					List<ChecklistInfoJS> subList = JSUtils.convertToJavaList(selected.getSubChecklists());
					ciDataProvider.setList(subList);
					aggListCellTable.setPageSize(subList.size());
					aggListVPanel.setVisible(true);					
				} 
				ciDataProvider.setFilter(aggListFilterTBox.getText(), substringCBox.getValue());
			}
		});
		aggChecklistCellTable.setSelectionModel(aggSelectionModel);


		final MultiSelectionModel<ChecklistInfoJS> aggListSelectionModel = new MultiSelectionModel<ChecklistInfoJS>();		
		aggListSelectionModel.addSelectionChangeHandler(UIFactory.createCopyrightHandler(aggListSelectionModel,agreedCpList));

		

		

		

		



		ScrollPanel scrollPanel = new ScrollPanel();
		aggListVPanel.add(scrollPanel);
		scrollPanel.setSize("398px", "492px");


		// Add the cellList to the dataProvider.
		ciDataProvider.addDataDisplay(aggListCellTable);
		scrollPanel.setWidget(aggListCellTable);
		aggListCellTable.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.DISABLED);
		aggListCellTable.setSize("100%", "");

		aggListCellTable.setSelectionModel(aggListSelectionModel,
				DefaultSelectionEventManager.<ChecklistInfoJS> createCheckboxManager());

		Column<ChecklistInfoJS, Boolean> aggListChooseColumn = new Column<ChecklistInfoJS, Boolean>(new CheckboxCell()) {
			@Override
			public Boolean getValue(ChecklistInfoJS object) {
				return aggListSelectionModel.isSelected(object);
			}
		};



		aggListCellTable.addColumn(aggListChooseColumn);

		Column<ChecklistInfoJS, String> aggListNameColumn = new Column<ChecklistInfoJS, String>(new ClickableTextCell()) {
			@Override
			public String getValue(ChecklistInfoJS object) {
				return object.getLabel();
			}
		};

		aggListCellTable.addColumn(aggListNameColumn);

		Column<ChecklistInfoJS, String> aggListUrlColumn = new Column<ChecklistInfoJS, String>(new ChecklistInfoCIRCell()) {
			@Override
			public String getValue(ChecklistInfoJS object) {
				return object.getUrl();
			}
		};

		aggListCellTable.addColumn(aggListUrlColumn);

		TextButton txtbtnOk = new TextButton("OK");
		flexTable.setWidget(2, 0, txtbtnOk);
		txtbtnOk.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				JsArrayString updatedChecklists = removeUnused(stdChecklists,aggChecklists);
				if(updatedChecklists.length() == 0) {
					Window.alert("No checklist has been chosen");
				} else {
					tccjs.setChosenServices(updatedChecklists);							
					tccjs.okReply();
				}
			}
		});
		flexTable.getFlexCellFormatter().setColSpan(0, 0, 2);
		flexTable.getFlexCellFormatter().setColSpan(1, 0, 2);

		TextButton txtbtnCancel = new TextButton("CANCEL");
		flexTable.setWidget(2, 1, txtbtnCancel);
		flexTable.getCellFormatter().setHorizontalAlignment(2, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		txtbtnCancel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				tccjs.doReplyCANCEL();
			}
		});
		List<Boolean> DAYS = Arrays.asList(true);
		// Create a cell to render each value in the list.
		CheckboxCell textCell = new CheckboxCell();
	}

	private static JsArrayString removeUnused(List<ChecklistInfoJS> stdChecklists,List<ChecklistInfoJS> aggChecklists) {

		JsArrayString checklists = (JsArrayString)JavaScriptObject.createArray();
		int count = 0;

		Iterator<ChecklistInfoJS> stdItr = stdChecklists.iterator();
		while(stdItr.hasNext()) {
			ChecklistInfoJS ci = stdItr.next();

			if(ci.getUse()) {
				String ciStr = ci.getId() + ";" + ci.getId() + ";" + ci.getLabel() + ";" + ci.getUrl() + ";" + ci.getCopyrightUrl();
				checklists.push(ciStr);

				count++;
			}
		}

		Iterator<ChecklistInfoJS> aggItr = aggChecklists.iterator();
		while(aggItr.hasNext()) {
			ChecklistInfoJS ci = aggItr.next();
			List<ChecklistInfoJS> subLists = JSUtils.convertToJavaList(ci.getSubChecklists());
			Iterator<ChecklistInfoJS> aggSubItr = subLists.iterator();

			while(aggSubItr.hasNext()) {
				ChecklistInfoJS subci = aggSubItr.next();
				if(subci.getUse()) {									
					checklists.push(ci.getId() + ";" + subci.getId() + ";" + subci.getLabel() + ";" + subci.getUrl() + ";" + subci.getCopyrightUrl());		
					count++;					
				}
			}

		}
		return checklists;
	}
}

