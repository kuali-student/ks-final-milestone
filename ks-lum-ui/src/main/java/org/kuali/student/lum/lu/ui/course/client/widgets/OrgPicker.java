package org.kuali.student.lum.lu.ui.course.client.widgets;

import java.util.List;

import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.suggestbox.KSAdvancedSearchRpc;
import org.kuali.student.core.organization.ui.client.service.OrgRpcService;
import org.kuali.student.core.organization.ui.client.service.OrgRpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.VerticalPanel;

public class OrgPicker extends Composite implements HasValue<String>{
	
	private OrgRpcServiceAsync orgRpcServiceAsync = GWT.create(OrgRpcService.class);
	final KSAdvancedSearchRpc orgSearchWidget = new KSAdvancedSearchRpc(orgRpcServiceAsync, "org.search.orgQuickViewByHierarchyShortName");
    private KSTextBox textBox = new KSTextBox();
    
    VerticalPanel root = new VerticalPanel();
    
	public OrgPicker() {
		super();
		initWidget(root);
		root.add(textBox);
		orgSearchWidget.setMultipleSelect(false);
		orgSearchWidget.addSelectionHandler(new SelectionHandler<List<String>>(){
			public void onSelection(SelectionEvent<List<String>> event) {
				if(event.getSelectedItem()!=null&&event.getSelectedItem().size()>0){
					textBox.setValue(event.getSelectedItem().get(0));
				}
			}
		});
		root.add(orgSearchWidget);
	}

	@Override
	public String getValue() {
		return textBox.getValue();
	}

	@Override
	public void setValue(String value) {
		textBox.setValue(value);
	}

	@Override
	public void setValue(String value, boolean fireEvents) {
		// TODO Auto-generated method stub
		textBox.setValue(value);		
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
