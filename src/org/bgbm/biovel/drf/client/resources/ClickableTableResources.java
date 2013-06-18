package org.bgbm.biovel.drf.client.resources;

import com.google.gwt.user.cellview.client.CellTable;

public interface ClickableTableResources extends CellTable.Resources 
{ 
  @Source(value = { CellTable.Style.DEFAULT_CSS, "ClickableCellTable.css" }) 
  CellTable.Style cellTableStyle(); 
} 
