/**
 * 
 */
package org.kuali.student.common.ui.client.configurable.mvc;


import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public abstract class MultiplicityCompositeWithLabels extends MultiplicityComposite {
	{
		useDeleteLabel = true;
	}
	
    @Override
    protected Widget generateAddWidget() {
    	Label addWidget =  new Label(addItemLabel);
    	addWidget.addStyleName("KS-Multiplicity-Labels");
    	addWidget.addClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
                addItem();
            }            
        });
    	return addWidget;
    }
}