package org.bgbm.biovel.drf.client.widgets;

import org.bgbm.biovel.drf.client.js.ChecklistInfoJS;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.text.shared.SafeHtmlRenderer;

public class CheckListInfoIRRenderer  implements SafeHtmlRenderer<String> {

	    private static CheckListInfoIRRenderer instance;

	    protected CheckListInfoIRRenderer() {
	    }

	    public static CheckListInfoIRRenderer getInstance() {
	        if (instance == null) {
	            instance = new CheckListInfoIRRenderer();
	        }
	        return instance;
	    }

	    @Override
	    public SafeHtml render(String url) {
	        SafeHtml html = SafeHtmlUtils.fromSafeConstant("");
	        if (url != null && !url.equals("")) {
	            html = SafeHtmlUtils.fromSafeConstant("<i class='icon-external-link'></i>");
	        }
	        return html;
	    }

	    @Override
	    public void render(String url, SafeHtmlBuilder builder) {
	        builder.append(render(url));
	    }

	}



