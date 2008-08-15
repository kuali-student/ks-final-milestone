package org.kuali.student.ui.personidentity.client.view.lu.fastTree;

import java.util.List;

import org.kuali.student.ui.personidentity.client.controller.LearningUnitController;
import org.kuali.student.ui.personidentity.client.model.lu.GwtCluInfo;
import org.kuali.student.ui.personidentity.client.model.lu.GwtLuiDisplay;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.widgetideas.client.FastTreeItem;

public class CluDisplayFastItem extends FastTreeItem {

    GwtCluInfo cDisp = null;

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
     * @see com.google.gwt.widgetideas.client.FastTreeItem#ensureChildren()
     */
    @Override
    protected void ensureChildren() {
        this.populateChildern();
    }

    
}
