package org.kuali.student.ui.personidentity.client.view.lu.fastTree;

import java.util.List;

import org.kuali.student.commons.ui.mvc.client.Controller;
import org.kuali.student.commons.ui.mvc.client.EventTypeHierarchy;
import org.kuali.student.commons.ui.mvc.client.EventTypeRegistry;
import org.kuali.student.commons.ui.mvc.client.MVC;
import org.kuali.student.ui.personidentity.client.controller.LearningUnitController;
import org.kuali.student.ui.personidentity.client.model.lu.GwtCluInfo;
import org.kuali.student.ui.personidentity.client.model.lu.GwtLuTypeInfo;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.widgetideas.client.FastTreeItem;

public class LuTypeFastItem extends FastTreeItem {

    public static class LuTypeFastItemPopulated extends LuFastTreePanel.LuFastTreePanelEvent {
        static {
            EventTypeRegistry.register(LuTypeFastItemPopulated.class, new LuTypeFastItemPopulated().getHierarchy());
        }

        public EventTypeHierarchy getHierarchy() {
            return super.getHierarchy().add(LuTypeFastItemPopulated.class);
        }
    }

    static {
        new LuTypeFastItemPopulated();
    }
    
    

    GwtLuTypeInfo tInfo;
    boolean ensureChildren = true;

    public LuTypeFastItem(GwtLuTypeInfo info) {
        super();
        this.tInfo = info;
        this.setText(tInfo.getDescription());
        this.addItem("");
        this.becomeInteriorNode();
    }
    
    /**
     * @param ensureChildren the ensureChildren to set
     */
    public void setEnsureChildren(boolean ensureChildren) {
        this.ensureChildren = ensureChildren;
    }

    protected void ensureChildren() {
        if(this.ensureChildren)
            this.populateChildren();
    }

    public void populateChildren() {
        LearningUnitController.findClusForLuType(tInfo.getLuTypeKey(), new AsyncCallback() {
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }

            public void onSuccess(Object result) {
                List<GwtCluInfo> lRet = (List<GwtCluInfo>) result;
                if (lRet != null) {
                    removeItems();

                    for (GwtCluInfo gcd : lRet) {

                        addItem(new CluDisplayFastItem(gcd));

                    }
                }
                Controller c = MVC.findParentController(getTree());
                c.getEventDispatcher().fireEvent(LuTypeFastItemPopulated.class, lRet);
            }
        });
    }

    public GwtLuTypeInfo getTypeInfo() {
        return this.tInfo;
    }

}
