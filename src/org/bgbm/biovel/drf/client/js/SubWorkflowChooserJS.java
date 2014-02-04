
package org.bgbm.biovel.drf.client.js;


import com.google.gwt.core.client.JsArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.Window;

public class SubWorkflowChooserJS extends PmrpcJS {                             
  // Overlay types always have protected, zero argument constructors.
  protected SubWorkflowChooserJS() {}                   

    public final native void init() /*-{ 
                 this.tnrocShouldRun = []; 
                 this.dataQualityShouldRun = [];
                 this.dataSelectionShouldRun = [];
                 this.endWorkflowShouldRun = [];
                 this.endWorkflow = false;
                 this.refineHost = "127.0.0.1:3333"; 
                 this.refineServer = "127.0.0.1"; 
                 this.refinePort = "3333"; 
                 $wnd.emptydata = false;               
         }-*/;

  // JSNI methods to set options
  public final native void setTnrocShouldRun() /*-{ this.tnrocShouldRun = ["shouldRun"]; }-*/; 
  public final native void setDataQualityShouldRun() /*-{ this.dataQualityShouldRun = ["shouldRun"]; }-*/;
  public final native void setRefineHost(String host) /*-{ this.refineHost = host; }-*/;
  public final native void setRefineServer(String server) /*-{ this.refineServer = server; }-*/;
  public final native void setRefinePort(String port) /*-{ this.refinePort = port; }-*/;
  public final native void setDataSelectionShouldRun() /*-{ this.dataSelectionShouldRun = ["shouldRun"]; }-*/;
  public final native void setEndWorkflowShouldRun() /*-{ this.endWorkflowShouldRun = ["shouldRun"]; this.endWorkflow = true;}-*/;
  
  // JSNI methods to get options
  public final native JsArray getTnrocShouldRun() /*-{ return this.tnrocShouldRun; }-*/; 
  public final native JsArray getDataQualityShouldRun() /*-{ return this.dataQualityShouldRun; }-*/;
  public final native String getRefineHost() /*-{ return this.refineHost; }-*/;
  public final native String getRefineServer() /*-{ return this.refineServer; }-*/;
  public final native String getRefinePort() /*-{ return this.refinePort; }-*/;
  public final native JsArray getDataSelectionShouldRun() /*-{ return this.dataSelectionShouldRun; }-*/;
  public final native JsArray getEndWorkflowShouldRun() /*-{ return this.endWorkflowShouldRun; }-*/;
  public final native boolean getEndWorkflow() /*-{ return this.endWorkflow; }-*/;
  public static native String getEmptyDataFlag() /*-{ return $wnd.emptydata; }-*/;
  
  public static native boolean initView() /*-{ 
	
	$wnd.callswc =  $entry(@org.bgbm.biovel.drf.client.ui.SubWorkflowChooser::initComponents());
	$wnd.pmrpc.call({
		destination : "publish",
		publicProcedureName : "getInputData",
		params : [],
		onSuccess : function(retVal) {      				
  		$wnd.emptydata = retVal.returnValue.emptyData;          
  		//$wnd.alert($wnd.emptydata); 			
  		$wnd.callswc();        			        		        			      			             
		},
		onFailure : function() {        
		}
	});    
		  		    	     	
	return true;
}-*/;
  
  public final void okReply(){
	  String json = new JSONObject(this).toString();	  
	  setJSONStr(json);	  
	  //Window.alert(new JSONObject(getJSON()).toString());
	  doReplyOK();
  }

}
