package org.kuali.student.lum.lu.ui.main.client.configuration;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeEvent;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeHandler;
import org.kuali.student.common.ui.client.widgets.search.KSPicker;
import org.kuali.student.common.ui.client.widgets.suggestbox.KSSuggestBox;
import org.kuali.student.r1.common.assembly.data.Data.Value;
import org.kuali.student.r1.common.assembly.data.Metadata;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class CopyCourseSearchPanel extends Composite {
	Callback<Boolean> validationCallback;
	private String currentSelection = null;
	private HTML validationErrorLabel = null;
	private VerticalPanel validationPanel = null;
	private KSPicker[] pickers = null;
	
	protected CopyCourseSearchPanel(Metadata searchMetadata, final Callback<Boolean> validationCallback, String label, String invalidErrorLabel, String[] dropDownLabels, String[] searchIds) {
		
		if(dropDownLabels==null || searchIds==null || dropDownLabels.length != searchIds.length || searchIds.length<1){
			throw new RuntimeException("Invalid Parameters");
		}
		
		this.validationCallback = validationCallback;
		
        final VerticalPanel copyCourseSearchPanel = new VerticalPanel();

        
        Label copyToCourseLabel = new Label(label);

        copyToCourseLabel.addStyleName("bold");
        
        validationErrorLabel = new HTML("<UL><LI>"+invalidErrorLabel+"</LI></UL>");
        validationErrorLabel.addStyleName("ks-form-module-validation-errors");
        validationErrorLabel.addStyleName("ks-form-module-validation-inline");

        validationErrorLabel.setVisible(false);
        
        validationPanel = new VerticalPanel();
        
        copyCourseSearchPanel.add(copyToCourseLabel);
        copyCourseSearchPanel.add(validationPanel);
        
        HorizontalPanel inputPanel = new HorizontalPanel();
        inputPanel.addStyleName("ks-form-module-elements");
        inputPanel.addStyleName("ks-form-module-single-line-margin");
        final ListBox courseSelectionMethodDropdown = new ListBox();

        for(int i=0;i<searchIds.length;i++){
            courseSelectionMethodDropdown.addItem(dropDownLabels[i], String.valueOf(i));
        }
        inputPanel.add(courseSelectionMethodDropdown);
        
        pickers = new KSPicker[searchIds.length];
        for(int i=0;i<searchIds.length;i++){
        	Metadata metadata = searchMetadata.getProperties().get(searchIds[i]);
        	KSPicker picker = new KSPicker(metadata.getInitialLookup(), null);
        	picker.addValueChangeCallback(new Callback<Value>(){
    			public void exec(Value result) {
    				currentSelection = null;
    				clearValidation();
    				validationCallback.exec(false);
    			}
            });
        	picker.addSelectionChangeHandler(new SelectionChangeHandler() {
    			public void onSelectionChange(SelectionChangeEvent event) {
    				Widget w = event.getWidget();
    				currentSelection = ((KSSuggestBox)w).getValue();
    				if(currentSelection!=null){
    					validationCallback.exec(true);
    				}
    			}
    		});
        	picker.addFocusLostCallback(new Callback<Boolean>() {
    			@Override
    			public void exec(Boolean result) {
    				if(currentSelection==null){
    					addError();
    					validationCallback.exec(false);
    				}
    			}
    		});
        	pickers[i] = picker;
        }
        
        final SimplePanel coursePickerHolder = new SimplePanel();
        coursePickerHolder.setWidget(pickers[0]);
        coursePickerHolder.addStyleName("KS-indented");
        inputPanel.add(coursePickerHolder);
        
        validationPanel.add(inputPanel);
        validationPanel.add(validationErrorLabel);
        
        copyCourseSearchPanel.add(validationPanel);
        
        copyCourseSearchPanel.setVisible(false);
        
        //Handle the switching of pickers by the drop down
        courseSelectionMethodDropdown.addChangeHandler(new ChangeHandler(){
			public void onChange(ChangeEvent event) {
				clear();
				coursePickerHolder.setWidget(pickers[courseSelectionMethodDropdown.getSelectedIndex()]);
			}
        });

        copyCourseSearchPanel.addStyleName("KS-indented");
        initWidget(copyCourseSearchPanel);
	}
	
	public void clearValidation(){
		validationErrorLabel.setVisible(false);
		validationPanel.removeStyleName("error");
	}
	
	public void addError(){
		validationErrorLabel.setVisible(true);
		validationPanel.addStyleName("error");
		
	}

	public void clear() {
		for(KSPicker picker:pickers){
			picker.clear();
		}
		clearValidation();
		currentSelection = null;
	}
	
	public String getValue(){
		return currentSelection;
	}
}
