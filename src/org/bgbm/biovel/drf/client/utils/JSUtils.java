package org.bgbm.biovel.drf.client.utils;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.user.client.Window;

public class JSUtils extends JavaScriptObject {
	
	protected JSUtils() {}
	
	public static <T extends JavaScriptObject> List<T> convertToJavaList(JsArray<T> jsa) {
		List<T> objList = new ArrayList<T>();
		for(int i = 0;i<jsa.length();i++) {			
			objList.add(jsa.get(i));
		}
		return objList;
	}
	
	public static final native void pingRefine(String imageUrl) /*-{ 
		    
    	var image = new Image();
        image.src = imageUrl + "?pingedat=" + new Date().getTime();    
		image.onload = function() {
			if(image.height > 0){    			
    			$wnd.refineReply = $entry(@org.bgbm.biovel.drf.client.ui.SubWorkflowChooser::reply());
    			$wnd.refineReply(); 
			}
		};
		
		image.onerror = function () {    			
    			$wnd.refineError = $entry(@org.bgbm.biovel.drf.client.ui.SubWorkflowChooser::newRefineErrorPopup());
    			$wnd.refineError(); 			
		}
  		
    }-*/;  
	
	public static final native String getParentUrl() /*-{ 
		
		var parentUrl = $doc.referrer;
		alert(parentUrl);
//	    var isInIframe = ($wnd.parent != $wnd);
//	    
//
//	    if (isInIframe) {
//	        parentUrl = $doc.referrer;
//	    }

	    return parentUrl;
    }-*/;
}
