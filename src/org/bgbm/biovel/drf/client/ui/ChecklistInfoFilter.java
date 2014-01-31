package org.bgbm.biovel.drf.client.ui;

import org.bgbm.biovel.drf.client.js.ChecklistInfoJS;

public class ChecklistInfoFilter implements IFilter<ChecklistInfoJS> {

	@Override
	public boolean isValid(ChecklistInfoJS value, String filter, boolean substringSearch) {
		if(substringSearch) {
			return value.getLabel().toLowerCase().contains(filter.toLowerCase());
		} else {
			return value.getLabel().toLowerCase().startsWith(filter.toLowerCase());
		}
	}

}
