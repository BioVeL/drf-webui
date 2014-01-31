package org.bgbm.biovel.drf.client.ui;

public interface IFilter<T> {
	public boolean isValid(T value, String filter, boolean substringSearch);  
}
