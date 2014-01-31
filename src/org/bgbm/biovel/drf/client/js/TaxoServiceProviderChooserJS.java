package org.bgbm.biovel.drf.client.js;


import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.Window;



public class TaxoServiceProviderChooserJS extends PmrpcJS {                             
  // Overlay types always have protected, zero argument constructors.
  protected TaxoServiceProviderChooserJS() {}                                             
   
  public final native void setChosenServices(JsArrayString cijsarray) /*-{   	  	
	this.chosenServices = cijsarray; 
  }-*/;
  
  public final native JsArrayString getChosenServices() /*-{   	  	
	return this.chosenServices;
  }-*/;
  
  public final native JsArray<ChecklistInfoJS> getAllServices() /*-{   	  	
  		return $wnd.allServices; 
  }-*/;
  
 
	  

  public static native boolean init() /*-{ 
		this.chosenChecklists = [];
  		$wnd.initComponents =  $entry(@org.bgbm.biovel.drf.client.ui.TaxoServiceProviderChooser::initComponents());  		
  		$wnd.pmrpc.call({
      		destination : 'publish',
      		publicProcedureName : 'setTitle',
      		params : ['Choose Taxonomic Checklist to target'],
     	});
   		$wnd.pmrpc.call({
      		destination : "publish",
      		publicProcedureName : "getInputData",
     		params : [],
      		onSuccess : function(retVal) {      
      			
        		$wnd.allServices = eval(retVal.returnValue.allServices);      
        		//$wnd.alert(retVal.returnValue.allChecklists);		 		       		            	
        		$wnd.initComponents();        			        		        			      			             
      		},
      		onFailure : function() {        
      		}
   		}); 
   		  		    	     	
  		return true;
  	}-*/;
  

  
  public final void okReply(){	  	  
	
	 String jsonStr = new JSONObject(this).toString();	 
	 
	 setJSONStr(jsonStr);	 
	 doReplyOK();
  }
}
