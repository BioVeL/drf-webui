package org.bgbm.biovel.drf.client.widgets;

import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;

public class ClickableLabel extends Label {
	
	NewTabClickHandler ntch;
	public ClickableLabel() {
		
		init();
	}
	
	public ClickableLabel(String text) {
		super(text);
		init();
	}
		
	private void init() {
		getElement().getStyle().setCursor(Cursor.POINTER); 
		this.ntch = new NewTabClickHandler();
		addClickHandler(ntch);
	}
	public void setHref(String href) {		
		ntch.setHref(href);
	}
	class NewTabClickHandler implements ClickHandler {
		
		String href = "";
		public void onClick(ClickEvent event) {
			Window.open(href, "_blank", "");
		}
		
		public void setHref(String href) {
			this.href = href;
		}
		
		public String getHref() {
			return this.href;
		}
	}
}

