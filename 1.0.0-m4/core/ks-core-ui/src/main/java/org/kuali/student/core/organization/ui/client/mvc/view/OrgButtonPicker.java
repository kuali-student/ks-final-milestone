package org.kuali.student.core.organization.ui.client.mvc.view;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.suggestbox.KSAdvancedSearchWindow;
import org.kuali.student.core.organization.dto.OrgInfo;
import org.kuali.student.core.organization.ui.client.service.OrgRpcService;
import org.kuali.student.core.organization.ui.client.service.OrgRpcServiceAsync;
//import org.kuali.student.core.organization.ui.client.view.SingleListItem;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class OrgButtonPicker extends Composite implements HasText{

	//How to tie in with model to select hierarchy?
	
	private OrgRpcServiceAsync orgRpcServiceAsync = GWT.create(OrgRpcService.class);
	private KSTextBox orgIDBox = new KSTextBox();
	private OrgInfo orgInfoReturned = new OrgInfo();
	private KSButton orgSearchButton = new KSButton("Find Org", new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {
            final KSAdvancedSearchWindow orgSearchPopup = new KSAdvancedSearchWindow(orgRpcServiceAsync, "org.search.orgQuickViewByHierarchyShortName", "org.resultColumn.orgId", "Find Organization");
            
            orgSearchPopup.addSelectionHandler(new SelectionHandler<List<String>>(){
                @Override
                public void onSelection(SelectionEvent<List<String>> event) {
                    String orgId = event.getSelectedItem().get(0);
                    orgRpcServiceAsync.getOrganization(orgId, new AsyncCallback<OrgInfo>(){
                        public void onFailure(Throwable caught) {
                            Window.alert(caught.getMessage());
                        }

                        public void onSuccess(OrgInfo orgInfo) {
                            orgInfoReturned = orgInfo;
                            orgIDBox.setValue(orgInfo.getLongName());
                            orgSearchPopup.hide();
                        }            
                    });                                        
                }
            });
            
            orgSearchPopup.show();
        }});;
        
    HorizontalPanel root = new HorizontalPanel();
        
//    VerticalPanel root = new VerticalPanel();
    public OrgButtonPicker(){
        super();
        initWidget(root);
        root.add(orgIDBox);
        root.add(orgSearchButton);
    }
	@Override
	public void onLoad() {
		super.onLoad();
		

	}
    @Override
    public String getText() {
        
        return orgInfoReturned.getId();
    }
    @Override
    public void setText(String text) {
        
        
    }
	
	
}
