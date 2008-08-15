package org.kuali.student.ui.personidentity.client.view.lu.fastTree;

import java.util.List;

import org.kuali.student.ui.personidentity.client.controller.LearningUnitController;
import org.kuali.student.ui.personidentity.client.model.lu.GwtLuTypeInfo;
import org.kuali.student.ui.personidentity.client.view.lu.LuTypeItem;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.widgetideas.client.FastTree;

public class LuFastTreePanel extends Composite {

    VerticalPanel panel = new VerticalPanel();
    FastTree        luTree = new FastTree();
    
    boolean loaded = false;
    
    public LuFastTreePanel() {       
        super();
        
        panel.add(luTree);
        
        initWidget(panel);
    }

    protected void init(){
        if(!loaded){
            loaded = true;
            LearningUnitController.findLuTypes(new AsyncCallback(){
                public void onFailure(Throwable caught) {
                    Window.alert(caught.getMessage());                      
                }
                public void onSuccess(Object result) {
                    List<GwtLuTypeInfo> lTypes = (List<GwtLuTypeInfo>)result;
                    if(lTypes != null){
                        populateTree(lTypes);
                    }                       
                }});

        }
    }

    protected void populateTree(List<GwtLuTypeInfo> lTypes){
        if(lTypes != null){
            this.luTree.removeItems();            
            for(GwtLuTypeInfo luTypeInfo: lTypes){              
                LuTypeFastItem luItem = new LuTypeFastItem(luTypeInfo);
                this.luTree.addItem(luItem);
            }
        }
        
    }

}
