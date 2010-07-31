package org.kuali.student.common.ui.client.widgets.table.scroll;

import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class TableCellWidget extends Composite implements  HasClickHandlers, HasChangeHandlers {
	Widget defaultTableEditor;
	public TableCellWidget(Object value){
		if(value instanceof Boolean){
			defaultTableEditor = new CheckBox();	
		}else {
			defaultTableEditor = new EditableLabel();
		}
		super.initWidget(defaultTableEditor);
	}
	public Object getCellEditorValue() {
		if (defaultTableEditor instanceof EditableLabel) {
			return ((EditableLabel)defaultTableEditor).getText();
		}else if(defaultTableEditor instanceof CheckBox){
			return ((CheckBox)defaultTableEditor).getValue();
		}
		return "";
	}
	public void setCellEditorValue(Object value) {
		if (defaultTableEditor instanceof EditableLabel) {
			if(value == null){
				((EditableLabel)defaultTableEditor).setText("");				
			}else{
				((EditableLabel)defaultTableEditor).setText(value.toString());
			}
		}else if(defaultTableEditor instanceof CheckBox){
			if(value == null){
				((CheckBox)defaultTableEditor).setValue(false);				
			}else{
				((CheckBox)defaultTableEditor).setValue((Boolean)value);
			}
			
		}

	}
	
	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		if(defaultTableEditor instanceof CheckBox){
			return ((CheckBox)defaultTableEditor).addClickHandler(handler) ;
		}else if(defaultTableEditor instanceof EditableLabel) {
			return ((EditableLabel)defaultTableEditor).addClickHandler(handler) ;
		}
		return null;
	}
	@Override
	public HandlerRegistration addChangeHandler(ChangeHandler handler) {
		//if(defaultTableEditor instanceof CheckBox){
		//	return ((CheckBox)defaultTableEditor).addChangeHandler(handler) ;
		//}else 
			if(defaultTableEditor instanceof EditableLabel) {
			return ((EditableLabel)defaultTableEditor).addChangeHandler(handler) ;
		}
		return null;
	}
}
