package org.bgbm.biovel.drf.client.js;


import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.Window;

public class FileSaverJS extends PmrpcJS {                             
  // Overlay types always have protected, zero argument constructors.
  protected FileSaverJS() {}                                             
  
  // JSNI methods to set options
  public final native void setEndWorkflowOK(boolean endWorkflowOK) /*-{ this.endWorkflowOK = endWorkflowOK; }-*/; 
  
  // JSNI methods to get options
  public final native boolean getEndWorkflowOK() /*-{ return this.endWorkflowOK; }-*/; 
  
  public static native String getOutputFileUrl() /*-{   	  	
		return $wnd.outputFileUrl; 
	}-*/;
  public static native boolean init() /*-{ 
	this.outputFileUrl = "";	
	$wnd.initComponents =  $entry(@org.bgbm.biovel.drf.client.ui.FileSaver::initComponents());
	
	$wnd.pmrpc.call({
		destination : 'publish',
		publicProcedureName : 'setTitle',
		params : ['File Saver'],
	});
		$wnd.pmrpc.call({
		destination : "publish",
		publicProcedureName : "getInputData",
		params : [],
		onSuccess : function(retVal) {    			
  			$wnd.outputFileUrl = retVal.returnValue.outputFile;          				
  			//$wnd.alert($wnd.outputFileUrl); 			
  			$wnd.initComponents();        			        		        			      			             
			},
		onFailure : function() {        
		}
		}); 
		  		    	     	
	return true;
}-*/;
  
  public final void okReply(){
	  String json = new JSONObject(this).toString();	  
	  setJSONStr(json);	  	  
	  doReplyOK();
  }

}
