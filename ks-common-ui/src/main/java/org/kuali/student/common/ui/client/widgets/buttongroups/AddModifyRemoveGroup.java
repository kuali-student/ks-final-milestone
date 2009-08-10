package org.kuali.student.common.ui.client.widgets.buttongroups;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.AddModifyRemoveEnum;
import org.kuali.student.common.ui.client.widgets.buttonlayout.ButtonColumn;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

public class AddModifyRemoveGroup extends ButtonGroup<AddModifyRemoveEnum>{
    
    public AddModifyRemoveGroup(Callback<AddModifyRemoveEnum> callback){
        layout = new ButtonColumn();
        this.addCallback(callback);
        
        addButton(AddModifyRemoveEnum.ADD);
        addButton(AddModifyRemoveEnum.MODIFY);
        addButton(AddModifyRemoveEnum.REMOVE);
        
        this.initWidget(layout);
    }
    
    private void addButton(final AddModifyRemoveEnum type){
        KSButton button = new KSButton(type.getText(), new ClickHandler(){
            
            @Override
            public void onClick(ClickEvent event) {
                sendCallbacks(type);
            }
        });
        layout.addButton(button);
        buttonMap.put(type, button);
    }
}
