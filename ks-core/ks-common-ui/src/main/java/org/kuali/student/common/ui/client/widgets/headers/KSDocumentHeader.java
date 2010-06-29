package org.kuali.student.common.ui.client.widgets.headers;

import org.kuali.student.common.ui.client.widgets.field.layout.element.SpanPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class KSDocumentHeader extends Composite {

	private static KSDocumentHeaderUiBinder uiBinder = GWT
			.create(KSDocumentHeaderUiBinder.class);

	interface KSDocumentHeaderUiBinder extends
			UiBinder<Widget, KSDocumentHeader> {
	}
	@UiField
	HTML headerHTML;

	@UiField
	HTML infoLabel;

	@UiField
	SpanPanel widgetPanel;
	
	public KSDocumentHeader() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	public void setTitle(String header){
		headerHTML.setHTML("<h2>"+header+"</h2>");
	}
    public void addWidget(Widget w){
    	if(w != null){
    		if(widgetPanel.getElement().hasChildNodes()){
    			widgetPanel.add(new HTML("<span style='float: left; margin-left: .7em; margin-right: .7em'>|</span>"));
    		}
    		widgetPanel.add(w);
    	}
    	w.getElement().setAttribute("style", "float: left");
    }
    public void setInfo(String info){
    	infoLabel.setText(info);
    }
   
}
