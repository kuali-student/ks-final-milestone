package org.kuali.student.common.ui.client.widgets.buttongroups;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.OkEnum;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.YesNoEnum;
import org.kuali.student.common.ui.client.widgets.buttonlayout.ButtonRow;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

public class OkGroup extends ButtonGroup<OkEnum>{
    
    public OkGroup(Callback<OkEnum> callback){
        layout = new ButtonRow();
        this.addCallback(callback);
        
        addButton(OkEnum.Ok);
        
        this.initWidget(layout);
    }
    
    private void addButton(final OkEnum type){
        KSButton button = new KSButton(type.getText(), new ClickHandler(){
            
            @Override
            public void onClick(ClickEvent event) {
                sendCallbacks(type);
            }
        });
        ((ButtonRow) layout).addButtonToSecondaryGroup(button);
        buttonMap.put(type, button);
    }    
}
