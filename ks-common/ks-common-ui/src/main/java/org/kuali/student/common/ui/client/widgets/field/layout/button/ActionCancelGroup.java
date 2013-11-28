package org.kuali.student.common.ui.client.widgets.field.layout.button;

import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.ButtonEnum;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;


public class ActionCancelGroup extends ButtonGroup<ButtonEnum> {
	public ActionCancelGroup(ButtonEnum actionButtonEum, ButtonEnum cancelButtonEum){
		super();
		createButton(actionButtonEum.getActionType());
		createButton(cancelButtonEum.getCancelType());
	}

	private void createButton(final ButtonEnum buttonEum){
	    
		KSButton button = new KSButton(buttonEum.getText(), buttonEum.getStyle());

		button.addClickHandler(new ClickHandler(){

            @Override
            public void onClick(ClickEvent event) {
                sendCallbacks(buttonEum);
            }
        });
        this.addButton(button);
        buttonMap.put(buttonEum, button);
	}

}
