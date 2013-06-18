package org.bgbm.biovel.drf.client.js;


import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.Window;



public class TaxonomicConceptChooserJS extends PmrpcJS {                             
  // Overlay types always have protected, zero argument constructors.
  protected TaxonomicConceptChooserJS() {
	  
	  
  };                                             

  // JSNI methods to set options
  public final native void setNames(JsArrayString names) /*-{ this.names = names; }-*/;  

  
  // JSNI methods to get options
  public final native JsArrayString getNames() /*-{ return this.names; }-*/;  
    
  public static native JsArrayString getSynReqRes() /*-{   	  	
  		return $wnd.synreqres; 
  	}-*/;
	  

  public static native boolean init() /*-{ 
  		this.colSelected = false;
  		this.selectedGbifChecklists = [];
  		$wnd.test =  $entry(@org.bgbm.biovel.drf.client.ui.TaxonomicConceptChooser::initComponents());
  		
  		$wnd.pmrpc.call({
      		destination : 'publish',
      		publicProcedureName : 'setTitle',
      		params : ['Taxonomic Concept Chooser'],
     	});
   		$wnd.pmrpc.call({
      		destination : "publish",
      		publicProcedureName : "getInputData",
     		params : [],
      		onSuccess : function(retVal) {      				
        		$wnd.synreqres = retVal.returnValue.synreqres_list;          
        		//$wnd.alert($wnd.synreqres); 			
        		$wnd.test();        			        		        			      			             
      		},
      		onFailure : function() {        
      		}
   		});    
   		  		    	     	
  		return true;
  	}-*/;
  

  
  public final void okReply(){
	  
	  String json = new JSONObject(this).toString();	  
	  setJSONStr(json);	 
	  //Window.alert(json);
	  doReplyOK();
  }
}

