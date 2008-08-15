package org.kuali.student.ui.personidentity.client.view.lu.fastTree;

import java.util.List;

import org.kuali.student.commons.ui.mvc.client.Controller;
import org.kuali.student.commons.ui.mvc.client.EventTypeHierarchy;
import org.kuali.student.commons.ui.mvc.client.EventTypeRegistry;
import org.kuali.student.commons.ui.mvc.client.MVC;
import org.kuali.student.ui.personidentity.client.controller.LearningUnitController;
import org.kuali.student.ui.personidentity.client.model.lu.GwtCluInfo;
import org.kuali.student.ui.personidentity.client.model.lu.GwtLuiDisplay;
import org.kuali.student.ui.personidentity.client.view.SearchWidget.SearchWidgetSearch;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.widgetideas.client.FastTreeItem;

public class CluDisplayFastItem extends FastTreeItem {
    GwtCluInfo cDisp = null;
                   
    public static class CluDisplayFastItemSelect extends LuFastTreePanel.LuFastTreePanelEvent {
        static {
            EventTypeRegistry.register(CluDisplayFastItemSelect.class, new CluDisplayFastItemSelect().getHierarchy());
        }
        public EventTypeHierarchy getHierarchy() {
            return super.getHierarchy().add(CluDisplayFastItemSelect.class);
        }
    }
    static {
        new CluDisplayFastItemSelect(); 
    }

    public CluDisplayFastItem(GwtCluInfo disp) {
        super();
        this.cDisp = disp;
        this.setText(cDisp.getCluShortName());
        this.addItem("");
        this.becomeInteriorNode();
    }
    
    
    protected void populateChildern(){
        LearningUnitController.findLuisForClu(cDisp.getCluId(),null, new AsyncCallback(){
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());                  
            }

            public void onSuccess(Object result) {
                List<GwtLuiDisplay> lRet = (List<GwtLuiDisplay>)result;
                if(lRet != null){
                    removeItems();
                    
                    for(GwtLuiDisplay gcd : lRet){                              
                       addItem(new LuiFastTreeItem(gcd));                                
                    }
                }
            }});
    }
    
    /**
     * @return the cDisp
     */
    public GwtCluInfo getCDisp() {
        return cDisp;
    }


    /**
     * This overridden method ...
     * 
     * @see com.google.gwt.widgetideas.client.FastTreeItem#onSelected()
     */
    
    protected void onSelected() {
        Controller c = MVC.findParentController(this.getTree());
        c.getEventDispatcher().fireEvent(CluDisplayFastItemSelect.class, cDisp);
    }


    /**
     * This overridden method ...
     * 
     * @see com.google.gwt.widgetideas.client.FastTreeItem#ensureChildren()
     */
    @Override
    protected void ensureChildren() {
        this.populateChildern();
    }

    
}
