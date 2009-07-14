package org.kuali.student.common.ui.client.widgets.menus;

import com.google.gwt.event.shared.EventHandler;

public interface MenuEventHandler extends EventHandler{
    public void onSelect(MenuSelectEvent e);
    public void onChange(MenuChangeEvent e);
}
