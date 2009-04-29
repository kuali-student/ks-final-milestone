package org.kuali.student.common.ui.client.widgets.menus;

public class MenuSelectEvent extends MenuEvent<MenuEventHandler>{
    public static final Type<MenuEventHandler> TYPE = new Type<MenuEventHandler>();
    
    @Override
    public Type<MenuEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(MenuEventHandler handler) {
       handler.onSelect(this);  
    }

}
