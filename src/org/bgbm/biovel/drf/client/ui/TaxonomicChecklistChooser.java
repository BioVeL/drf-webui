package org.bgbm.biovel.drf.client.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.bgbm.biovel.drf.client.js.TaxonomicChecklistChooserJS;
import org.bgbm.biovel.drf.client.resources.ClickableTableResources;


import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.regexp.shared.SplitResult;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.widget.client.TextButton;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.cellview.client.RowHoverEvent;

public class TaxonomicChecklistChooser implements EntryPoint {

	static RootPanel rootPanel;

	public static class Checklist {
		public String name;
		public String datasetId;
		public Boolean chosen;

		public Checklist(String name, String datasetId, Boolean chosen) {
			this.name = name;
			this.datasetId = datasetId;
			this.chosen = chosen;
		}
	}

	private static Checklist colChecklist = new Checklist("Catalogue Of Life", "0",false);
	private static final List<Checklist> STD_CHECKLISTS = Arrays.asList(
			colChecklist
			);
	private static final List<Checklist> AGG_CHECKLISTS = Arrays.asList(
			new Checklist("GBIF Checklist Bank", "0", false)
			);



	@Override
	public void onModuleLoad() {

		// Use RootPanel.get() to get the entire body element
		rootPanel = RootPanel.get();
		rootPanel.setSize("1024", "768");		
		TaxonomicChecklistChooserJS.init();		
		initComponents();
	}

	public static void initComponents() {		
		final TaxonomicChecklistChooserJS tccjs = (TaxonomicChecklistChooserJS)JavaScriptObject.createObject();
		JsArrayString checklists = TaxonomicChecklistChooserJS.getGbifChecklists();						

		final List<Checklist> aggSubChecklists = new ArrayList();
		for(int i =0;i < checklists.length();i++) {
			String name_id[] = checklists.get(i).trim().split(":");
			aggSubChecklists.add(new Checklist(name_id[0],name_id[1],false));
		}

		final List<Checklist> AGG_SUB_CHECKLISTS = Arrays.asList();



		FlexTable flexTable = new FlexTable();
		flexTable.setCellPadding(10);
		rootPanel.add(flexTable, 81, 41);
		flexTable.setSize("100px", "100px");

		Label lblNewLabel = new Label("Choose Taxonomic Checklist to target");
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
		verticalPanel.setSize("267px", "");

		Label lblNewLabel_1 = new Label("Standard Checklists");
		lblNewLabel_1.setStyleName("biovel-table-label");
		verticalPanel.add(lblNewLabel_1);
		verticalPanel.setCellHorizontalAlignment(lblNewLabel_1, HasHorizontalAlignment.ALIGN_CENTER);

		CellTable<Checklist> stdChklistCellTable = new CellTable<Checklist>(STD_CHECKLISTS.size(), GWT.<ClickableTableResources>create(ClickableTableResources.class));

		stdChklistCellTable.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.DISABLED);
		verticalPanel.add(stdChklistCellTable);
		stdChklistCellTable.setSize("255px", "");

		Column<Checklist, Boolean> stdChooseColumn = new Column<Checklist, Boolean>(new CheckboxCell()) {
			@Override
			public Boolean getValue(Checklist object) {
				return object.chosen;
			}
		};
		stdChooseColumn.setFieldUpdater(new FieldUpdater<Checklist, Boolean>() {
			@Override
			public void update(int index, Checklist object, Boolean value) {
				object.chosen = value;	         
			}
		});
		stdChklistCellTable.addColumn(stdChooseColumn);

		TextColumn<Checklist> stdNameColumn = new TextColumn<Checklist>() {
			@Override
			public String getValue(Checklist object) {
				return object.name;
			}
		};
		stdChklistCellTable.addColumn(stdNameColumn);
		stdChklistCellTable.setRowData(0,STD_CHECKLISTS);

		Label lblNewLabel_2 = new Label("  ");
		verticalPanel.add(lblNewLabel_2);
		lblNewLabel_2.setHeight("22px");

		Label lblAggregatedChecklists = new Label("Aggregated Checklists");
		lblAggregatedChecklists.setStyleName("biovel-table-label");
		verticalPanel.add(lblAggregatedChecklists);
		verticalPanel.setCellHorizontalAlignment(lblAggregatedChecklists, HasHorizontalAlignment.ALIGN_CENTER);


		CellTable<Checklist> aggChecklistCellTable = new CellTable<Checklist>(AGG_CHECKLISTS.size(),GWT.<ClickableTableResources>create(ClickableTableResources.class));
		aggChecklistCellTable.setTableLayoutFixed(true);
		verticalPanel.add(aggChecklistCellTable);
		verticalPanel.setCellHeight(aggChecklistCellTable, "10px");
		aggChecklistCellTable.setSize("255px", "");

		TextColumn<Checklist> aggNameColumn = new TextColumn<Checklist>() {
			@Override
			public String getValue(Checklist object) {
				return object.name;
			}
		};
		aggChecklistCellTable.addColumn(aggNameColumn);

		aggChecklistCellTable.setRowCount(AGG_CHECKLISTS.size(), true);	    
		aggChecklistCellTable.setRowData(0,AGG_CHECKLISTS);


		ScrollPanel scrollPanel = new ScrollPanel();
		horizontalPanel.add(scrollPanel);
		scrollPanel.setSize("398px", "492px");

		final CellTable<Checklist> aggListCellTable = new CellTable<Checklist>(aggSubChecklists.size(),GWT.<ClickableTableResources>create(ClickableTableResources.class));
		scrollPanel.setWidget(aggListCellTable);
		aggListCellTable.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.DISABLED);
		aggListCellTable.setSize("100%", "");

		final SingleSelectionModel<Checklist> selectionModel = new SingleSelectionModel<Checklist>();
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				Checklist selected = selectionModel.getSelectedObject();
				aggListCellTable.setVisible(true);
			}
		});
		aggChecklistCellTable.setSelectionModel(selectionModel);

		Column<Checklist, Boolean> aggListChooseColumn = new Column<Checklist, Boolean>(new CheckboxCell()) {
			@Override
			public Boolean getValue(Checklist object) {
				return object.chosen;
			}
		};
		aggListCellTable.addColumn(aggListChooseColumn);
		aggListChooseColumn.setFieldUpdater(new FieldUpdater<Checklist, Boolean>() {
			@Override
			public void update(int index, Checklist object, Boolean value) {
				object.chosen = value;

			}
		});
		TextColumn<Checklist> aggListNameColumn = new TextColumn<Checklist>() {
			@Override
			public String getValue(Checklist object) {
				return object.name;
			}
		};
		aggListCellTable.addColumn(aggListNameColumn);
		aggListCellTable.setRowData(0,aggSubChecklists);
		aggListCellTable.setVisible(false);

		TextButton txtbtnOk = new TextButton("OK");
		flexTable.setWidget(2, 0, txtbtnOk);
		txtbtnOk.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if(colChecklist.chosen) {
					tccjs.setColSelected(true);
				} else {
					tccjs.setColSelected(false);
				}

				JsArrayString gbifSel = (JsArrayString)JavaScriptObject.createArray();
				Iterator<Checklist> itr = aggSubChecklists.iterator();
				String gbifChkList = "";
				int index = 0;
				while(itr.hasNext()) {
					Checklist clist = (Checklist)itr.next();
					if(clist.chosen) {
						gbifChkList = clist.name + ":" + clist.datasetId;
						gbifSel.set(index,gbifChkList);
						index++;
					}
				}

				tccjs.setSelectedGbifChecklists(gbifSel);				
				tccjs.okReply();
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
}

