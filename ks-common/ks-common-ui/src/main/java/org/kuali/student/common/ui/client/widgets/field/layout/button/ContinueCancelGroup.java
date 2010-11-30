package org.kuali.student.common.ui.client.widgets.field.layout.button;

import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;


public class ContinueCancelGroup extends ButtonGroup<ButtonEnumerations.ContinueCancelEnum>{
	public ContinueCancelGroup(){
		super();
		createButton(ButtonEnumerations.ContinueCancelEnum.CONTINUE);
		createButton(ButtonEnumerations.ContinueCancelEnum.CANCEL);
	}

	private void createButton(final ButtonEnumerations.ContinueCancelEnum type){
		KSButton button = new KSButton(type.getText(), type.getStyle());

		button.addClickHandler(new ClickHandler(){

            @Override
            public void onClick(ClickEvent event) {
                sendCallbacks(type);
            }
        });
        this.addButton(button);
        buttonMap.put(type, button);
	}

}
