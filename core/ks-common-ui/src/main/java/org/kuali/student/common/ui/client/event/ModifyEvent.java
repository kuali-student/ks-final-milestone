package org.kuali.student.common.ui.client.event;

import org.kuali.student.common.ui.client.mvc.ApplicationEvent;

import com.google.gwt.event.shared.GwtEvent.Type;

public class ModifyEvent<ModifyType extends Enum<?>> extends ApplicationEvent<ModifyHandler>{
    public static final Type<ModifyHandler> TYPE = new Type<ModifyHandler>();
    
    private ModifyType modifyType;
    
    public ModifyEvent(){
        
    }
    
    public ModifyEvent(ModifyType modifyType){
        this.modifyType = modifyType;
    }
    
    @Override
    protected void dispatch(ModifyHandler handler) {
        handler.onModify(this);
    }

    @Override
    public Type<ModifyHandler> getAssociatedType() {
        return TYPE;
    }
    
    public ModifyType getModifyType(){
        return this.modifyType;
    }

   
    
}
