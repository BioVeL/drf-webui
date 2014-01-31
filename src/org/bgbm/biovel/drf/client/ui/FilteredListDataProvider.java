package org.bgbm.biovel.drf.client.ui;

import com.google.gwt.user.client.Window;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.Range;

import java.util.ArrayList;
import java.util.List;

/**
 * This code has been taken from http://www.artiom.pro/2012/09/gwt-celltable-filtering.html
 * 
 */
public class FilteredListDataProvider<T> extends ListDataProvider<T> {

    private String filterString = "";
    private boolean substringSearch = false;
    public final IFilter<T> filter;

    public FilteredListDataProvider(IFilter<T> filter) {
        this.filter = filter;
    }

    public String getFilter() {
        return filterString;
    }

    public void setFilter(String filter, boolean substringSearch) {
        this.filterString = filter;
        this.substringSearch = substringSearch;
        refresh();
    }

    public void resetFilter() {
        filterString = null;
        refresh();
    }

    public boolean hasFilter() {
        return !filterString.isEmpty();
    }

    @Override
    protected void updateRowData(HasData display, int start, List values) {
        if (!hasFilter() || filter == null) { // we don't need to filter, so call base class
            super.updateRowData(display, start, values);
        } else {
            int end = start + values.size();
            Range range = display.getVisibleRange();
            int curStart = range.getStart();
            int curLength = range.getLength();
            int curEnd = curStart + curLength;           
            if (start == curStart || (curStart < end && curEnd > start)) {
                int realStart = curStart < start ? start : curStart;
                int realEnd = curEnd > end ? end : curEnd;
                int realLength = realEnd - realStart;
                List<T> resulted = new ArrayList<T>(realLength);
                for (int i = realStart - start; i < realStart - start + realLength; i++) {
                    if (filter.isValid((T) values.get(i), getFilter(), substringSearch)) {
                        resulted.add((T) values.get(i));
                    }
                }
                display.setRowData(realStart, resulted);
                display.setRowCount(resulted.size());
            }
        }
    }
}
