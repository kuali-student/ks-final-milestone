package org.kuali.student.common.ui.client.configurable.mvc.binding;

import java.util.Iterator;

import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Data.Property;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HasText;

public class ListToTextBinding implements ModelWidgetBinding<HasText> {
	
	private String innerObjectKey;
	
	public ListToTextBinding(String innerObjectKey) {
		this.innerObjectKey = innerObjectKey;
	}
	
	public ListToTextBinding() {
	}

	@Override
	public void setModelValue(HasText widget, DataModel model, String path) {
		GWT.log("ListToTextBindings cannot be used for setting into the model!");
	}

	@Override
	public void setWidgetValue(HasText widget, DataModel model, String path) {
		widget.setText(getStringList(model, path));
	}
	
	public String getStringList(DataModel model, String path) {
		Data data = model.get(path);
		String resultString = "";
		if(data!=null){
			Iterator<Property> iter = data.realPropertyIterator();
			
			while(iter.hasNext()){
				Property prop = iter.next();
				if (prop.getKey() instanceof Integer){
                	Integer number = (Integer)prop.getKey();
                	Object value = data.get(number);
                	if(value instanceof Data){
                		DataModel m = new DataModel();
                		m.setRoot((Data)value);
                		Object innerObject = m.get(innerObjectKey);
                		resultString = resultString + innerObject.toString() + ", ";
                	}
                	else{
                		resultString = resultString + value.toString() + ", ";
                	}
                }
			}
		}
		if(!resultString.isEmpty()){
			resultString = resultString.trim();
			resultString = resultString.substring(0, resultString.length() - 1);
		}
		return resultString;
	}

	/**
	 * If the expected list is a list of data, which value is the value to use in this
	 * text list (by key).  If this is not set, it is assumed the list is a list of simple data
	 * @param innerObjectKey
	 */
	public void setInnerObjectKey(String innerObjectKey) {
		this.innerObjectKey = innerObjectKey;
	}

	public String getInnerObjectKey() {
		return innerObjectKey;
	}

}
