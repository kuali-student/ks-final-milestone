package org.kuali.student.common.ui.client.widgets;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.validator.DataModelValidator;
import org.kuali.student.common.ui.client.widgets.field.layout.element.AbbrButton;
import org.kuali.student.common.ui.client.widgets.field.layout.element.LabelPanel;
import org.kuali.student.common.ui.client.widgets.field.layout.element.AbbrButton.AbbrButtonType;
import org.kuali.student.r1.common.assembly.data.QueryPath;
import org.kuali.student.r2.common.dto.ValidationResultInfo;

import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasBlurHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;

public class ListOfStringWidget extends Composite implements HasBlurHandlers{
    private String addItemText;
	
	private boolean loaded = false;
	private FlowPanel mainPanel = new FlowPanel();
	private FlowPanel itemsPanel = new FlowPanel();
	private ArrayList<String> values = new ArrayList<String>();
	final KSTextBox inputText = new KSTextBox();
	private FieldDescriptor fd;
	private DataModelValidator validator = new DataModelValidator();
	public ListOfStringWidget(String addItemText) {
		super();
		super.initWidget(mainPanel);
		this.addItemText = addItemText;
	}

	public void onLoad() {
        if (!loaded) {            
        	KSButton addItemButton = new KSButton(addItemText);
            addItemButton.addClickHandler(new ClickHandler(){
				public void onClick(ClickEvent event) {
					//add validate
					List<ValidationResultInfo> results = new ArrayList<ValidationResultInfo>();
					validator.doValidateString(inputText.getText(),QueryPath.parse(fd.getFieldKey()), fd.getMetadata(), results);
					if(results.isEmpty()){
						addListItem(inputText.getText());
						inputText.setText("");
					}else if(fd != null && fd.getFieldElement() != null){
						for(ValidationResultInfo vr:results){
							fd.getFieldElement().processValidationResult(vr);
						}
					}
				}
            });
            mainPanel.add(inputText);
            mainPanel.add(addItemButton);
            mainPanel.add(itemsPanel);            
            loaded = true;
        }
    }
	
	public ArrayList<String> getStringValues(){
		return values;
	}
	
	public void setStringValues(ArrayList<String> values){
		itemsPanel.clear();
		if(values!=null){
			for(String value:values){
				addListItem(value);
			}
		}
	}
	
	protected void addListItem(String itemValue){
		final FlowPanel item = new FlowPanel();
		final String curVal = itemValue;
		if (!values.contains(itemValue)) {
	        
			String fieldHTMLId = HTMLPanel.createUniqueId();
	        LabelPanel fieldTitle = new LabelPanel(itemValue, fieldHTMLId);
	        
	    	AbbrButton delButton = new AbbrButton(AbbrButtonType.DELETE);
	    	delButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					itemsPanel.remove(item);
					values.remove(curVal);
				}
			});
	        fieldTitle.add(delButton);
	        item.add(fieldTitle);
			itemsPanel.add(item);
			values.add(curVal);
		}
	}

	@Override
	public HandlerRegistration addBlurHandler(BlurHandler handler) {
		return inputText.addBlurHandler(handler);
	}

	public void setFd(FieldDescriptor fd) {
		this.fd = fd;
	}
}
