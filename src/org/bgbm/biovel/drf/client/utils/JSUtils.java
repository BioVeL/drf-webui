package org.bgbm.biovel.drf.client.utils;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Window;

public class JSUtils {
	
	public static <T extends JavaScriptObject> List<T> convertToJavaList(JsArray<T> jsa) {
		List<T> objList = new ArrayList<T>();
		for(int i = 0;i<jsa.length();i++) {			
			objList.add(jsa.get(i));
		}
		return objList;
	}
}
