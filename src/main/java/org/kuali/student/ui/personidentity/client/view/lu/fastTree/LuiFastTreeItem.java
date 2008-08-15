package org.kuali.student.ui.personidentity.client.view.lu.fastTree;

import org.kuali.student.ui.personidentity.client.model.lu.GwtLuiDisplay;

import com.google.gwt.widgetideas.client.FastTreeItem;

public class LuiFastTreeItem extends FastTreeItem {
    GwtLuiDisplay disp = null;

    public LuiFastTreeItem(GwtLuiDisplay disp) {
        super();
        this.disp = disp;
        this.setText(disp.getCluDisplay().getCluShortName() + "-" + disp.getLuiCode());
    }

    /**
     * @return the disp
     */
    public GwtLuiDisplay getDisp() {
        return disp;
    }
    
}
