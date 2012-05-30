package org.kuali.student.common.ui.client.widgets.field.layout.button;

import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.ConfirmCancelEnum;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;


public class ConfirmCancelGroup extends ButtonGroup<ConfirmCancelEnum>{
	public ConfirmCancelGroup(){
		super();
		createButton(ConfirmCancelEnum.CONFIRM);
		createButton(ConfirmCancelEnum.CANCEL);
	}

	private void createButton(final ConfirmCancelEnum type){
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
