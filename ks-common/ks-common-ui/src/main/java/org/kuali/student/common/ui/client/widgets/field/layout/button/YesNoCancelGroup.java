package org.kuali.student.common.ui.client.widgets.field.layout.button;

import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.YesNoCancelEnum;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

@Deprecated
public class YesNoCancelGroup extends ButtonGroup<YesNoCancelEnum>{
	public YesNoCancelGroup(){
		super();
		createButton(YesNoCancelEnum.YES);
		createButton(YesNoCancelEnum.NO);
		createButton(YesNoCancelEnum.CANCEL);
	}

	private void createButton(final YesNoCancelEnum type){
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
