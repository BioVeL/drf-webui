package org.bgbm.biovel.drf.client.widgets;

import org.bgbm.biovel.drf.client.js.ChecklistInfoJS;

import com.google.gwt.cell.client.AbstractSafeHtmlCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.Window;




public class ChecklistInfoCIRCell extends AbstractSafeHtmlCell<String>  {

	    

	    public ChecklistInfoCIRCell() {
	        super(CheckListInfoIRRenderer.getInstance(), "click");	        
	    }

	    @Override
	    public void onBrowserEvent(Context context, Element parent, String value,
	            NativeEvent event, ValueUpdater<String> valueUpdater) {
	        super.onBrowserEvent(context, parent, value, event, valueUpdater);
	        if ("click".equals(event.getType())) {
	            doAction(value);
	        }
	    }

	    @Override
	    protected void onEnterKeyDown(Context context, Element parent, String value,
	            NativeEvent event, ValueUpdater<String> valueUpdater) {
	    	doAction(value);
	    }

	    @Override
	    protected void render(com.google.gwt.cell.client.Cell.Context context,
	            SafeHtml data, SafeHtmlBuilder sb) {
	        if (data != null) {
	            sb.append(data);
	        }
	    }
	    
	    private void doAction(String url) {
	    	Window.open(url, "_blank", "");
	    }

	}
