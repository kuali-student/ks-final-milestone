package org.kuali.student.ui.personidentity.client.view.lu.fastTree;

import java.util.List;

import org.kuali.student.ui.personidentity.client.controller.LearningUnitController;
import org.kuali.student.ui.personidentity.client.model.lu.GwtCluInfo;
import org.kuali.student.ui.personidentity.client.model.lu.GwtLuTypeInfo;
import org.kuali.student.ui.personidentity.client.view.lu.CluDisplayItem;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.widgetideas.client.FastTreeItem;

public class LuTypeFastItem extends FastTreeItem { 
    /**
     * This overridden method ...
     * 
     * @see com.google.gwt.widgetideas.client.FastTreeItem#ensureChildren()
     */
    @Override
    protected void ensureChildren() {
        this.populateChildern();
    }


    GwtLuTypeInfo   tInfo;

    public LuTypeFastItem(GwtLuTypeInfo info) {
        super();
        this.tInfo = info;
        this.setText(tInfo.getDescription());
        this.addItem("");
        this.becomeInteriorNode();
    }

    protected void populateChildern(){
        LearningUnitController.findClusForLuType(tInfo.getLuTypeKey(), new AsyncCallback(){
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());                  
            }

            public void onSuccess(Object result) {
                List<GwtCluInfo> lRet = (List<GwtCluInfo>)result;
                if(lRet != null){
                    removeItems();
                    
                    for(GwtCluInfo gcd : lRet){
                        
                        addItem(new CluDisplayFastItem(gcd));
                        
                    }
                }
            }});
    }
    
    
    public GwtLuTypeInfo getTypeInfo(){
        return this.tInfo;
    }
    
}
