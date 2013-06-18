package org.bgbm.biovel.drf.client.js;


import com.google.gwt.core.client.JsArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.Window;

public class InputChooserJS extends PmrpcJS {                             
  // Overlay types always have protected, zero argument constructors.
  protected InputChooserJS() {
	  
  }                                             
  
  public final void okReply(){
	  String json = new JSONObject(this).toString();	  
	  setJSONStr(json);	  
	  Window.alert(new JSONObject(getJSON()).toString());
	  doReplyOK();
  }

}
