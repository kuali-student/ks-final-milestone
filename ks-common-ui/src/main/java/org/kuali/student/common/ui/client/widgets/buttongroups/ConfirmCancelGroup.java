package org.kuali.student.common.ui.client.widgets.buttongroups;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.ConfirmCancelEnum;
import org.kuali.student.common.ui.client.widgets.buttonlayout.ButtonRow;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

public class ConfirmCancelGroup extends ButtonGroup<ConfirmCancelEnum>{
    
    public ConfirmCancelGroup(Callback<ConfirmCancelEnum> callback){
        layout = new ButtonRow();
        this.addCallback(callback);
        
        addButton(ConfirmCancelEnum.CANCEL);
        addButtonToSecondaryGroup(ConfirmCancelEnum.CONFIRM);
        
        this.initWidget(layout);
    }
    
    private void addButton(final ConfirmCancelEnum type){
        KSButton button = new KSButton(type.getText(), new ClickHandler(){
            
            @Override
            public void onClick(ClickEvent event) {
                sendCallbacks(type);
            }
        });
        layout.addButton(button);
        buttonMap.put(type, button);
    }
    
    private void addButtonToSecondaryGroup(final ConfirmCancelEnum type){
        KSButton button = new KSButton(type.getText(), new ClickHandler(){
            
            @Override
            public void onClick(ClickEvent event) {
                sendCallbacks(type);
            }
        });
        ((ButtonRow)layout).addButtonToSecondaryGroup(button);
        buttonMap.put(type, button);
    }
}
