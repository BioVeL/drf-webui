package org.bgbm.biovel.drf.client.js;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class ChecklistInfoJS extends JavaScriptObject {                              // (1)
  // Overlay types always have protected, zero argument constructors.
  protected ChecklistInfoJS() {}                                              // (2)

  // JSNI methods to get stock data.
  public final native String getId() /*-{ return this.id; }-*/; // (3)
  
  public final native String getLabel() /*-{ return this.label; }-*/;
  
  public final native String getUrl() /*-{ return this.url; }-*/;
  
  public final native String getCopyrightUrl() /*-{ return this.copyrightUrl; }-*/;
  
  public final native boolean getUse() /*-{ return this.use; }-*/;
  public final native void setUse(boolean use) /*-{ this.use = use; }-*/;
  
  public final native JsArray<ChecklistInfoJS> getSubChecklists() /*-{ return this.subChecklists; }-*/;
  public final native void setSubChecklists(JsArray<ChecklistInfoJS> subChecklists) /*-{ this.subChecklists = subChecklists; }-*/;
}
