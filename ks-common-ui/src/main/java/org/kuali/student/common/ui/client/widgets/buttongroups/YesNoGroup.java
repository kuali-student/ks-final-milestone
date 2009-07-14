package org.kuali.student.common.ui.client.widgets.buttongroups;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.YesNoEnum;
import org.kuali.student.common.ui.client.widgets.buttonlayout.ButtonRow;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

public class YesNoGroup extends ButtonGroup<YesNoEnum>{
    
    public YesNoGroup(Callback<YesNoEnum> callback){
        layout = new ButtonRow();
        this.addCallback(callback);
        
        addButton(YesNoEnum.NO);
        addButtonToSecondaryGroup(YesNoEnum.YES);
        
        this.initWidget(layout);
    }
    
    private void addButton(final YesNoEnum type){
        KSButton button = new KSButton(type.getText(), new ClickHandler(){
            
            @Override
            public void onClick(ClickEvent event) {
                sendCallbacks(type);
            }
        });
        layout.addButton(button);
        buttonMap.put(type, button);
    }
    
    private void addButtonToSecondaryGroup(final YesNoEnum type){
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
