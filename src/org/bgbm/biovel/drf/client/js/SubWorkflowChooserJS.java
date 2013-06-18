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
  	}-*/;
  // JSNI methods to set options
  public final native void setTnrocShouldRun() /*-{ this.tnrocShouldRun = ["shouldRun"]; }-*/; 
  public final native void setDataQualityShouldRun() /*-{ this.dataQualityShouldRun = ["shouldRun"]; }-*/;
  public final native void setDataSelectionShouldRun() /*-{ this.dataSelectionShouldRun = ["shouldRun"]; }-*/;
  public final native void setEndWorkflowShouldRun() /*-{ this.endWorkflowShouldRun = ["shouldRun"]; this.endWorkflow = true;}-*/;
  
  // JSNI methods to get options
  public final native JsArray getTnrocShouldRun() /*-{ return this.tnrocShouldRun; }-*/; 
  public final native JsArray getDataQualityShouldRun() /*-{ return this.dataQualityShouldRun; }-*/;
  public final native JsArray getDataSelectionShouldRun() /*-{ return this.dataSelectionShouldRun; }-*/;
  public final native JsArray getEndWorkflowShouldRun() /*-{ return this.endWorkflowShouldRun; }-*/;
  public final native boolean getEndWorkflow() /*-{ return this.endWorkflow; }-*/;
  
  public final void okReply(){
	  String json = new JSONObject(this).toString();	  
	  setJSONStr(json);	  
	  //Window.alert(new JSONObject(getJSON()).toString());
	  doReplyOK();
  }

}
