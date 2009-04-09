package org.kuali.student.core.organization.ui.client.view;

import java.util.List;

import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.core.organization.dto.OrgHierarchyInfo;
import org.kuali.student.core.organization.ui.client.service.OrgRpcService;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class OrgLocateChart extends Composite {

    Panel w = new VerticalPanel();
    
    boolean loaded = false;
    
    public OrgLocateChart() {
        super.initWidget(w);
    }

    protected void onLoad(){
        if (!loaded){
            w.setWidth("100%");
            
            //this is a terrible idea
            addStyleName("KS-Org-Chart");
            
            OrgRpcService.Util.getInstance().getOrgHierarchies(new AsyncCallback<List<OrgHierarchyInfo>>(){
                public void onFailure(Throwable caught) {
                    Window.alert(caught.getMessage());
                }

                public void onSuccess(List<OrgHierarchyInfo> result) {
                    if(result != null){
                        for(OrgHierarchyInfo orgHierarchyInfo:result){
                            KSLabel label = new KSLabel(orgHierarchyInfo.getName()+" Hierarchy");
                            label.addStyleName("page-title");
                            w.add(label);
                            w.add(new OrgChartWidget(orgHierarchyInfo.getRootOrgId(),orgHierarchyInfo.getId(),0));
                        }                
                    }
                }
            });
            
            loaded = true;
        }       
    }
}
