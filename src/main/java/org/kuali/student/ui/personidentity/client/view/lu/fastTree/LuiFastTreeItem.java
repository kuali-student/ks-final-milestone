package org.kuali.student.ui.personidentity.client.view.lu.fastTree;

import org.kuali.student.commons.ui.mvc.client.Controller;
import org.kuali.student.commons.ui.mvc.client.EventTypeHierarchy;
import org.kuali.student.commons.ui.mvc.client.EventTypeRegistry;
import org.kuali.student.commons.ui.mvc.client.MVC;
import org.kuali.student.ui.personidentity.client.model.lu.GwtLuiDisplay;
import org.kuali.student.ui.personidentity.client.view.lu.fastTree.CluDisplayFastItem.CluDisplayFastItemSelect;

import com.google.gwt.widgetideas.client.FastTreeItem;

public class LuiFastTreeItem extends FastTreeItem {
    GwtLuiDisplay disp = null;

    
    public static class LuiFastTreeItemSelect extends LuFastTreePanel.LuFastTreePanelEvent {
        static {
            EventTypeRegistry.register(LuiFastTreeItemSelect.class, new LuiFastTreeItemSelect().getHierarchy());
        }
        public EventTypeHierarchy getHierarchy() {
            return super.getHierarchy().add(LuiFastTreeItemSelect.class);
        }
    }
    static {
        new LuiFastTreeItemSelect(); 
    }
    
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
    
    protected void onSelected() {
        Controller c = MVC.findParentController(this.getTree());
        c.getEventDispatcher().fireEvent(LuiFastTreeItemSelect.class, disp);
    }
    
}
