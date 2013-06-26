package org.bgbm.biovel.drf.client.ui;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.bgbm.biovel.drf.client.js.ChecklistInfoJS;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.widget.client.TextButton;

public class UIFactory {
	
	public static SelectionChangeEvent.Handler createCopyrightHandler(final MultiSelectionModel<ChecklistInfoJS> sm, final List<String> agreedCpList) {
		return new SelectionChangeEvent.Handler() {
			private Set<ChecklistInfoJS> selected = null;
			public void onSelectionChange(SelectionChangeEvent event) {
				if(selected != null) {
					Iterator<ChecklistInfoJS> selItr = selected.iterator();
					while(selItr.hasNext()) {
						ChecklistInfoJS cijs = selItr.next();
						cijs.setUse(false);
					}
				}
				selected = sm.getSelectedSet();
				Iterator<ChecklistInfoJS> stdItr = selected.iterator();
				while(stdItr.hasNext()) {
					ChecklistInfoJS ci = stdItr.next();
					if(sm.isSelected(ci) && !agreedCpList.contains(ci.getCopyrightUrl())){
						popupCopyright(ci, sm);
					}					
					ci.setUse(sm.isSelected(ci));					
				}				
			}
			
			private void popupCopyright(final ChecklistInfoJS ci, final SelectionModel<ChecklistInfoJS> sm) {
				if(ci.getCopyrightUrl() == null || ci.getCopyrightUrl().equals("")) {
					ci.setUse(true);
					return;
				}
				final PopupPanel popupPanel = new PopupPanel();
				//rootPanel.add(popupPanel, 10, 10);
				popupPanel.setSize("1024px", "700px");
				popupPanel.setGlassEnabled(true);
				VerticalPanel verticalPanel = new VerticalPanel();
				verticalPanel.setSpacing(10);
				popupPanel.setWidget(verticalPanel);
				verticalPanel.setSize("100%", "682px");
				
				Frame frame = new Frame(ci.getCopyrightUrl());
				//Frame frame = new Frame();
				verticalPanel.add(frame);
				frame.setSize("769px", "600px");
				
				AbsolutePanel absolutePanel = new AbsolutePanel();
				verticalPanel.add(absolutePanel);
				absolutePanel.setHeight("53px");
				
				Label lblDoYouAgree = new Label("Do you agree with the terms and conditions ?");
				absolutePanel.add(lblDoYouAgree, 10, 9);
				lblDoYouAgree.setSize("527px", "37px");
				
				TextButton yesBt = new TextButton("Yes");
				yesBt.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						agreedCpList.add(ci.getCopyrightUrl());
						ci.setUse(true);						
						sm.setSelected(ci,true);
						popupPanel.hide();
						
					}
				});
				absolutePanel.add(yesBt, 290, 9);
				
				TextButton noBt = new TextButton("No");
				noBt.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						agreedCpList.remove(ci.getCopyrightUrl());
						ci.setUse(false);						
						sm.setSelected(ci,false);				
						popupPanel.hide();
					}
				});
				absolutePanel.add(noBt, 361, 9);
				
				popupPanel.center();
				popupPanel.show();
			}
		};
	}

}
