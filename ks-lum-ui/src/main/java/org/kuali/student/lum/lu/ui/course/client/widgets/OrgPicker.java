package org.kuali.student.lum.lu.ui.course.client.widgets;

import org.kuali.student.common.ui.client.widgets.suggestbox.KSAdvancedSearchWindow;
import org.kuali.student.common.ui.client.widgets.suggestbox.KSSuggestBox;
import org.kuali.student.common.ui.client.widgets.suggestbox.SearchSuggestOracle;
import org.kuali.student.core.organization.ui.client.service.OrgRpcService;
import org.kuali.student.core.organization.ui.client.service.OrgRpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.VerticalPanel;

public class OrgPicker extends Composite implements HasValue<String>{
	
	private OrgRpcServiceAsync orgRpcServiceAsync = GWT.create(OrgRpcService.class);
	final SearchSuggestOracle orgSearchOracle = new SearchSuggestOracle(orgRpcServiceAsync,
	        "org.search.orgByShortName", 
	        "org.queryParam.orgShortName",
	        "org.queryParam.orgId", 
	        "org.resultColumn.orgId", 
	        "org.resultColumn.orgShortName");
    final KSSuggestBox suggestBox = new KSSuggestBox(orgSearchOracle);
	
	final KSAdvancedSearchWindow orgSearchWidget = new KSAdvancedSearchWindow(orgRpcServiceAsync, "org.search.orgQuickViewByHierarchyShortName","org.resultColumn.orgId");   
    
    VerticalPanel root = new VerticalPanel();
    
	public OrgPicker() {
		super();

		initWidget(root);
		orgSearchOracle.setTextWidget(suggestBox.getTextBox());
		root.add(suggestBox);

	}

	@Override
	public String getValue() {
		return suggestBox.getSelectedId();
	}

	@Override
	public void setValue(String value) {
	    suggestBox.setValue(value);
	}

	@Override
	public void setValue(String value, boolean fireEvents) {
		// TODO Auto-generated method stub
		setValue(value);		
	}

	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> handler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void fireEvent(GwtEvent<?> event) {
		// TODO Auto-generated method stub
		
	}
}
