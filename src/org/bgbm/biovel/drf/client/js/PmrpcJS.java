package org.bgbm.biovel.drf.client.js;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;

public class PmrpcJS extends JavaScriptObject {      
	
	// Overlay types always have protected, zero argument constructors.
	  protected PmrpcJS() {
		  
		 
	  } 
	  
	  public final static native boolean initialize() /*-{ 
	   $wnd.Base64 = {
	
			// private property
			_keyStr : "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",
	
			// public method for encoding
			encode : function (input) {
				//$wnd.alert("in encode");
		    var output = "";
		    var chr1, chr2, chr3, enc1, enc2, enc3, enc4;
		    var i = 0;
		
		    //input = $wnd.Base64._utf8_encode(input);
		
		    while (i < input.length) {
		
		        chr1 = input.charCodeAt(i++);
		        chr2 = input.charCodeAt(i++);
		        chr3 = input.charCodeAt(i++);
		
		        enc1 = chr1 >> 2;
		        enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
		        enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
		        enc4 = chr3 & 63;
		
		        if (isNaN(chr2)) {
		            enc3 = enc4 = 64;
		        } else if (isNaN(chr3)) {
		            enc4 = 64;
		        }
		
		        output = output +
		        this._keyStr.charAt(enc1) + this._keyStr.charAt(enc2) +
		        this._keyStr.charAt(enc3) + this._keyStr.charAt(enc4);
		
		    }
	        
	    	return output;
			},
			// private method for UTF-8 encoding
			_utf8_encode : function (string) {
    		string = string.replace(/\r\n/g,"\n");
    		var utftext = "";

		    for (var n = 0; n < string.length; n++) {
		
		        var c = string.charCodeAt(n);
		
		        if (c < 128) {
		            utftext += String.fromCharCode(c);
		        }
		        else if((c > 127) && (c < 2048)) {
		            utftext += String.fromCharCode((c >> 6) | 192);
		            utftext += String.fromCharCode((c & 63) | 128);
		        }
		        else {
		            utftext += String.fromCharCode((c >> 12) | 224);
		            utftext += String.fromCharCode(((c >> 6) & 63) | 128);
		            utftext += String.fromCharCode((c & 63) | 128);
		        }
		
		    }

    		return utftext;
			}
	  	}
	
	}-*/; 
	  
	  public final native boolean doReplyOK() /*-{ 
 	   $wnd.pmrpc.call({
      		destination : "publish",
          	publicProcedureName : "reply",
          	params : ["OK", this.json],
          	onSuccess : function()
         		{
         	 		$doc.getElementsByTagName('body')[0].innerHTML='<h1><center>Data submitted</center></h1>';          	 		
         	 	},
         	onFailure: function()
         	 	{
         	 		$doc.getElementsByTagName('body')[0].innerHTML='<h1><center>Data submission failed</center></h1>';
         	 	} 

         });
      
      return true;
 	
 	}-*/; 
	  
	  public final native boolean doReplyCANCEL() /*-{ 
	  	$wnd.pmrpc.call({
          destination : "publish",
          publicProcedureName : "reply",
          params : ["Cancelled", {}],
          onSuccess : function() {
          	$doc.getElementsByTagName('body')[0].innerHTML='<h1><center>Interaction output cancellation successful</center></h1>'; 
          	},
          onFailure: function() {
          	$doc.getElementsByTagName('body')[0].innerHTML='<h1><center>Interaction output cancellation failure</center></h1>'
          	}
       	});
		return true; 	
 	}-*/; 
	  
	  public final native boolean doFileReplyOK(String encoding) /*-{ 
	  	
	  	
  		var f = $doc.getElementsByName("fileInput")[0].files[0];
		if(!f) {
			$wnd.alert("No Input file chosen");
			return;
		}
  		var body = $doc.getElementById("body");

  		var reader = new FileReader();
		//reader.readAsText(f);
		reader.readAsText(f);
  		reader.onload = (function(theFile) {
        	return function(e) {
          		var text = e.target.result;
          		//text = "test";
          		$wnd.pmrpc.call({
            		destination : "publish",
            		publicProcedureName : "reply",
            		params : ["OK",
              			{"file_contents" : text}],
          			onSuccess : function()
         				{
         	 				$doc.getElementsByTagName('body')[0].innerHTML='<h1><center>Data submitted</center></h1>';          	 		
         	 			},
		         	onFailure: function()
		         	 	{
		         	 		$doc.getElementsByTagName('body')[0].innerHTML='<h1><center>Data submission failed</center></h1>';
		         	 	} 
           		});
           		//$wnd.alert(text)
        		};
      		})(f);


  		return true;

	}-*/; 
	  
	  public final native boolean doGetInputData() /*-{ 
	   $wnd.pmrpc.call({
             destination : "publish",
             publicProcedureName : "getInputData",
             params : [],
             onSuccess : function(retVal) {
               myInputValue = retVal.returnValue.myInputPort;
               //$wnd.alert(myInputValue)
             },
             onFailure : function() {
                // Code to deal with inability to read input values
             }
          });

     return true;
	
	}-*/; 
	  
	  public final native void setJSON(JavaScriptObject json) /*-{ this.json = json;	}-*/;
	  public final native void setJSONStr(String jsonStr) /*-{ this.json = eval('(' + jsonStr + ')');	}-*/;
	  public final native JavaScriptObject getJSON() /*-{ return this.json; }-*/;
}
