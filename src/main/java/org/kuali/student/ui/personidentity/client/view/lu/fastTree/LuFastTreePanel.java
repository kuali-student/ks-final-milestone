package org.kuali.student.ui.personidentity.client.view.lu.fastTree;

import java.util.List;

import org.kuali.student.commons.ui.mvc.client.EventTypeHierarchy;
import org.kuali.student.commons.ui.mvc.client.EventTypeRegistry;
import org.kuali.student.commons.ui.mvc.client.MVCEvent;
import org.kuali.student.ui.personidentity.client.controller.LearningUnitController;
import org.kuali.student.ui.personidentity.client.model.lu.GwtLuTypeInfo;
import org.kuali.student.ui.personidentity.client.view.SearchWidget.SearchWidgetSearch;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.widgetideas.client.FastTree;

public class LuFastTreePanel extends Composite {

    public static class LuFastTreePanelEvent extends MVCEvent {
        static {
            EventTypeRegistry.register(LuFastTreePanelEvent.class, new SearchWidgetSearch().getHierarchy());
        }
        public EventTypeHierarchy getHierarchy() {
            return super.getHierarchy().add(LuFastTreePanelEvent.class);
        }
    }
    
    static{
        new LuFastTreePanelEvent();
    }
    
    VerticalPanel panel = new VerticalPanel();
    FastTree        luTree = new FastTree();
    
    boolean loaded = false;
    
    public LuFastTreePanel() {       
        super();
        
        panel.add(luTree);
        
        initWidget(panel);
    }

    protected void onLoad() {
        super.onLoad();
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
