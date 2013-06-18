package org.bgbm.biovel.drf.client.js;


import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.json.client.JSONObject;



public class TaxonomicChecklistChooserJS extends PmrpcJS {                             
  // Overlay types always have protected, zero argument constructors.
  protected TaxonomicChecklistChooserJS() {}                                             

  // JSNI methods to set options
  public final native void setColSelected(boolean cs) /*-{ this.colSelected = cs; }-*/; 
  public final native void setSelectedGbifChecklists(JsArrayString gbifSel) /*-{ this.selectedGbifChecklists = gbifSel; }-*/;

  
  // JSNI methods to get options
  public final native boolean getColSelected() /*-{ return this.colSelected; }-*/; 
  public final native JsArrayString getSelectedGbifChecklists() /*-{ return this.selectedGbifChecklists; }-*/;
  
  
  public static native JsArrayString getGbifChecklists() /*-{   	  	
  		return $wnd.gbifChecklists; 
  	}-*/;
	  

  public static native boolean init() /*-{ 
  		this.colSelected = false;
  		this.selectedGbifChecklists = [];
  		$wnd.test =  $entry(@org.bgbm.biovel.drf.client.ui.TaxonomicChecklistChooser::initComponents());
  		
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
        		$wnd.gbifChecklists = retVal.returnValue.gbifChecklists;          
        		//$wnd.alert($wnd.gbifChecklists); 			
        		$wnd.test();        			        		        			      			             
      		},
      		onFailure : function() {        
      		}
   		}); 
   	    //$wnd.gbifChecklists = ['3i - Cicadellinae Database:1079','3i - Deltocephalinae Database:1082'];
   		//$wnd.test();    
   		  		    	     	
  		return true;
  	}-*/;
  

  
  public final void okReply(){
	  
	  String json = new JSONObject(this).toString();	  
	  setJSONStr(json);	 
	  //Window.alert(json);
	  doReplyOK();
  }
}
