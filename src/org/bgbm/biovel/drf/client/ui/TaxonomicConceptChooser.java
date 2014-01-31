package org.bgbm.biovel.drf.client.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bgbm.biovel.drf.client.data.ChecklistInfo;
import org.bgbm.biovel.drf.client.data.SciName;
import org.bgbm.biovel.drf.client.js.TaxonomicConceptChooserJS;
import org.bgbm.biovel.drf.client.resources.ClickableTableResources;
import org.bgbm.biovel.drf.client.utils.DRFUtils;
import org.bgbm.biovel.drf.client.widgets.ClickableLabel;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.widget.client.TextButton;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.FlexTable;


public class TaxonomicConceptChooser implements EntryPoint {

	static RootPanel rootPanel;
	final static TabPanel tabPanel = new TabPanel();
	static List<SciName> namesList;
	static List<ListDataProvider> visibleDataProviders;

	@Override
	public void onModuleLoad() {
		TaxonomicConceptChooserJS.init();
		//initComponents();
		//SciName sn1 = new SciName();
		//sn1.name = "sci name 1";
		//addNewTab("NEW",null,null);

		visibleDataProviders = new ArrayList<ListDataProvider>();

	}

	public static void initComponents() {

		final TaxonomicConceptChooserJS tccjs = (TaxonomicConceptChooserJS)JavaScriptObject.createObject();
		JsArrayString synreqresArray = TaxonomicConceptChooserJS.getSynReqRes();						

		final List<String> synreqresList = new ArrayList();
		for(int i =0;i < synreqresArray.length();i++) {			
			synreqresList.add(synreqresArray.get(i));
		}
		Set names = DRFUtils.buildNames(synreqresList); 

		rootPanel = RootPanel.get();
		rootPanel.setSize("1024", "768");
		namesList = new ArrayList<SciName>(names);

		final SingleSelectionModel<SciName> selectionModel = new SingleSelectionModel<SciName>();

		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				SciName selected = selectionModel.getSelectedObject();
				tabPanel.clear();		        
				setupOutputNameTables(selected);		        
			}
		});
		

		FlexTable flexTable = new FlexTable();
		rootPanel.add(flexTable, 10, 10);
		flexTable.setSize("100px", "100px");

		Label lblNewLabel_1 = new Label("Resolve Taxonomic Concept");
		flexTable.setWidget(0, 0, lblNewLabel_1);
		lblNewLabel_1.setStyleName("biovel-header-label");

		HorizontalPanel horizontalPanel = new HorizontalPanel();
		flexTable.setWidget(1, 0, horizontalPanel);
		horizontalPanel.setSize("674px", "452px");

		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.setSpacing(10);
		horizontalPanel.add(verticalPanel);
		verticalPanel.setSize("210px", "454px");

		Label lblNewLabel = new Label("Input Names");
		lblNewLabel.setStyleName("biovel-table-label");
		verticalPanel.add(lblNewLabel);
		verticalPanel.setCellHorizontalAlignment(lblNewLabel, HasHorizontalAlignment.ALIGN_CENTER);

		ScrollPanel inputScrollPanel = new ScrollPanel();
		verticalPanel.add(inputScrollPanel);
		inputScrollPanel.setSize("190px", "394px");

		CellTable<SciName> inputTable = new CellTable<SciName>(names.size(),GWT.<ClickableTableResources>create(ClickableTableResources.class));		
		inputScrollPanel.setWidget(inputTable);
		inputTable.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.DISABLED);
		inputTable.setSize("100%", "");

		TextColumn<SciName> inputNamesColumn = new TextColumn<SciName>() {
			@Override
			public String getValue(SciName sciName) {
				return sciName.name;
			}
		};
		inputTable.addColumn(inputNamesColumn);
		inputTable.setRowData(0,namesList);				
		inputTable.setSelectionModel(selectionModel);



		horizontalPanel.add(tabPanel);
		tabPanel.setSize("462px", "");

		TextButton txtbtnRetrievegbifSpecies = new TextButton("Retrieve species occurrence data");


		txtbtnRetrievegbifSpecies.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				List uniqueNamesList = SciName.buildUniqueNameList(namesList);
				JsArrayString names = (JsArrayString)JavaScriptObject.createArray();
				Iterator<String> itr = uniqueNamesList.iterator();
				String name = "";
				int index = 0;
				while(itr.hasNext()) {
					name = (String)itr.next();
					names.set(index,name);
					index++;					
				}
				tccjs.setNames(names);				
				tccjs.okReply();
			}
		});

		flexTable.setWidget(2, 0, txtbtnRetrievegbifSpecies);
		flexTable.getFlexCellFormatter().setColSpan(0, 0, 2);

		TextButton txtbtnCancel = new TextButton("CANCEL");
		txtbtnCancel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				tccjs.doReplyCANCEL();
			}
		});
		flexTable.setWidget(2, 1, txtbtnCancel);
		flexTable.getFlexCellFormatter().setColSpan(1, 0, 2);
		flexTable.getCellFormatter().setHorizontalAlignment(2, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		flexTable.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);
		selectionModel.setSelected(namesList.get(0), true);

	}

	private static void setupOutputNameTables(SciName sciName) {
		visibleDataProviders.clear();
		Iterator itrAccMap = sciName.getAccNameMap().entrySet().iterator(); 	
		while (itrAccMap.hasNext()) {
			Map.Entry pairs = (Map.Entry)itrAccMap.next();	     
			String checklist = pairs.getKey().toString();
			SciName accName = (SciName) pairs.getValue();
			List<SciName> accNameList = new ArrayList<SciName>();
			if (accName != null && !accName.name.equals("")) {
				accNameList.add(accName);

			}
			List<SciName> synonymList = sciName.getSynMap().get(checklist);
			addNewTab(checklist, accNameList, synonymList);

		}
		if(tabPanel.getTabBar().getTabCount() > 0) {
			tabPanel.selectTab(0);
		}
	}

	private static void addNewTab(String tabName, List<SciName> accNameList, List<SciName> synonymList) {

		VerticalPanel verticalPanel_1 = new VerticalPanel();
		verticalPanel_1.setSpacing(10);
		tabPanel.add(verticalPanel_1, tabName, false);
		verticalPanel_1.setSize("403px", "");

		HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
		verticalPanel_1.add(horizontalPanel_1);

		
		ClickableLabel lblChecklisturl = new ClickableLabel("checklist_url");
		lblChecklisturl.setStyleName("biovel-ext-link");
		horizontalPanel_1.add(lblChecklisturl);
		ChecklistInfo clinfo = DRFUtils.checklistInfoMap.get(tabName);
		String targetUrl = (clinfo == null)?"":clinfo.getChecklist_url();
		lblChecklisturl.setHref(targetUrl);

		ScrollPanel accNameScrollPanel = new ScrollPanel();
		verticalPanel_1.add(accNameScrollPanel);
		accNameScrollPanel.setSize("404px", "115px");
		
		int accNameSize = 0;
		if(accNameList != null && !accNameList.isEmpty()) {
			accNameSize = accNameList.size();
		}
		CellTable<SciName> accNamesCellTable = new CellTable<SciName>(accNameSize,GWT.<ClickableTableResources>create(ClickableTableResources.class));		
		accNameScrollPanel.setWidget(accNamesCellTable);
		accNamesCellTable.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.DISABLED);
		accNamesCellTable.setSize("100%", "");

		Column<SciName, Boolean> accNameChooseColumn = new Column<SciName, Boolean>(new CheckboxCell()) {
			@Override
			public Boolean getValue(SciName sciName) {
				return (Boolean) !sciName.exclude;
			}
		};
		accNameChooseColumn.setFieldUpdater(new FieldUpdater<SciName, Boolean>() {
			@Override
			public void update(int index, SciName sciName, Boolean value) {
				sciName.exclude = !value;	         
				SciName.filterName(namesList, sciName.name, sciName.exclude);
				refreshVisibleDataProviders();
			}
		});
		accNamesCellTable.addColumn(accNameChooseColumn);
		accNamesCellTable.setColumnWidth(accNameChooseColumn, "130px");

		Column<SciName, String> accNameTextColumn  = new Column<SciName, String>(new ClickableTextCell()) {
			@Override
			public String getValue(SciName sciName) {
				return sciName.name;
			}
		};
		accNameTextColumn.setFieldUpdater(new FieldUpdater<SciName, String>(){
			@Override
			public void update(int index, SciName sciName, String value){
				//Window.alert(sciName.getSource().url);
				Window.open(sciName.getSource().url, "_blank", "");
			}
		});
		accNamesCellTable.addColumn(accNameTextColumn, "Accepted Name");

		ScrollPanel synScrollPanel = new ScrollPanel();
		verticalPanel_1.add(synScrollPanel);
		synScrollPanel.setSize("404px", "239px");
		
		int synSize = 0;
		if(synonymList != null && !synonymList.isEmpty()) {
			synSize = synonymList.size();
		}
		CellTable<SciName> synonymsCellTable = new CellTable<SciName>(synSize,GWT.<ClickableTableResources>create(ClickableTableResources.class));
		synScrollPanel.setWidget(synonymsCellTable);

		synonymsCellTable.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.DISABLED);
		synonymsCellTable.setSize("100%", "");

		Column<SciName, Boolean> synChooseColumn = new Column<SciName, Boolean>(new CheckboxCell()) {
			public Boolean getValue(SciName sciName) {
				return (Boolean) !sciName.exclude;
			}
		};
		synChooseColumn.setFieldUpdater(new FieldUpdater<SciName, Boolean>() {
			@Override
			public void update(int index, SciName sciName, Boolean value) {
				sciName.exclude = !value;	
				SciName.filterName(namesList, sciName.name, sciName.exclude);
				refreshVisibleDataProviders();
			}
		});
		synonymsCellTable.addColumn(synChooseColumn);
		synonymsCellTable.setColumnWidth(synChooseColumn, "130px");


		Column<SciName, String> synTextColumn  = new Column<SciName, String>(new ClickableTextCell()) {
			public String getValue(SciName sciName) {
				return sciName.name;
			}
		};
		synTextColumn.setFieldUpdater(new FieldUpdater<SciName, String>(){
			@Override
			public void update(int index, SciName sciName, String value){				  
				Window.open(sciName.getSource().url, "_blank", "");
			}
		});
		synonymsCellTable.addColumn(synTextColumn, "Synonym");


		if(synonymList == null || synonymList.isEmpty()) {
			synonymsCellTable.setVisibleRange(0,0);		
		} else {
			ListDataProvider<SciName> dataProvider = new ListDataProvider<SciName>(synonymList);
			dataProvider.addDataDisplay(synonymsCellTable);
			visibleDataProviders.add(dataProvider);

		}

		
		if(accNameList == null || accNameList.isEmpty()) {
			accNamesCellTable.setVisibleRange(0,0);

		} else {
			ListDataProvider<SciName> dataProvider = new ListDataProvider<SciName>(accNameList);
			dataProvider.addDataDisplay(accNamesCellTable);
			visibleDataProviders.add(dataProvider);

		}

	}

	private static void refreshVisibleDataProviders() {
		Iterator itrVDP = visibleDataProviders.iterator();
		while(itrVDP.hasNext()) {
			ListDataProvider ldp = (ListDataProvider)itrVDP.next();
			ldp.refresh();
		}
	}
}
