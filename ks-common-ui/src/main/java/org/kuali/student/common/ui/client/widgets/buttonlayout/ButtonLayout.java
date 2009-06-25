package org.kuali.student.common.ui.client.widgets.buttonlayout;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.widgets.KSButton;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public abstract class ButtonLayout extends Composite{
    protected List<KSButton> buttons = new ArrayList<KSButton>();
    public abstract void addButton(KSButton button);

    public abstract void setContent(Widget w);
    public List<KSButton> getButtons(){
        return buttons;
    }
    
}
