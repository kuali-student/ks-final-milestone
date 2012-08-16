/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.common.ui.client.configurable.mvc.binding;

import java.util.Iterator;

import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.HasDataValue;
import org.kuali.student.common.ui.client.widgets.list.KSSelectedList;
import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.r1.common.assembly.data.Data.DataValue;
import org.kuali.student.r1.common.assembly.data.Data.Property;
import org.kuali.student.r1.common.assembly.data.Data.StringValue;
import org.kuali.student.r1.common.assembly.data.Data.Value;
import org.kuali.student.r1.common.assembly.data.QueryPath;

/**
 * Binding which can be used to bind a comma separated dynamic attribute list with KSAbstractSelectItem widgets
 * List widgets only work with lists represented by a DataValue in the data model.  This binding converts the
 * list values between a DataValue expected by the widget to/from comma separated string value expected by the
 * model when using a dynamic attribute.  
 * 
 * @author Kuali Student Team
 *
 */
public class DynamicAttributeListBinding extends ModelWidgetBindingSupport<HasDataValue>{

	public static DynamicAttributeListBinding INSTANCE = new DynamicAttributeListBinding();
	
	private DynamicAttributeListBinding(){}
	
	@Override
	public void setModelValue(HasDataValue widget, DataModel model, String path) {
		QueryPath qPath = QueryPath.parse(path);
        Value value = widget.getValue();
        if (!nullsafeEquals(model.get(qPath), value)) {
            setDirtyFlag(model, qPath);
        }
        if(value != null){        	
        	StringValue stringValue;
        	if (widget instanceof KSSelectedList ){
        		//This gets a value with _runtimeData translations for all selected items,
        		//otherwise translations get lost and KSSelectedList would have to lookup values via a search call.
        		stringValue = convertDataValueWithTranslationsToStringValue(((KSSelectedList)widget).getValueWithTranslations());
        	} else {
        		stringValue = convertDataValueToStringValue((DataValue)value);
        	}
        	
        	model.set(qPath, stringValue);        	
        }
	}
	
	@Override
	public void setWidgetValue(HasDataValue widget, DataModel model, String path) {
			
		QueryPath qPath = QueryPath.parse(path);
		Object value = null;
		if(model!=null){
        	value = model.get(qPath);
        }
                
        if (value != null && widget != null && value instanceof String) {
        	DataValue dataValue = convertStringValueToDataValue((String)value);
        	
        	widget.setValue(dataValue);
        }
	}

	private StringValue convertDataValueWithTranslationsToStringValue(
			Value valueWithTranslations) {
		// TODO Auto-generated method stub
		return null;
	}	
	
	/**
	 * Converts a list represented by DataValue to a list of values as a comma delimited StringValue

	 * @param dataValue DataValue representing a list object
	 * @return the list converted to a comma delimited StringValue
	 */
	private StringValue convertDataValueToStringValue(DataValue dataValue) {
		StringBuffer sbValue = new StringBuffer();
		
		Data data = dataValue.get();
		Iterator<Property> propertyIterator = data.realPropertyIterator();
		while(propertyIterator.hasNext()){
			Property property = propertyIterator.next();
			String propertyValue = property.getValue();
			sbValue.append(",");
			sbValue.append(propertyValue);
		}
		
		StringValue stringValue = new StringValue(sbValue.toString().substring(1));
		
		return stringValue; 
	}


	/**
	 * Converts a list represented by a comma delimited string so to a DataValue so it can be added used to
	 * set into a list widget.
	 * 
	 * @param stringValue a comma separated list of values
	 * @return DataValue representation of stringValue
	 */
	private DataValue convertStringValueToDataValue(String stringValue) {
		Data data = new Data();
		
		String[] stringValues = stringValue.split(",");
		for (String value:stringValues){
			data.add(value);
		}
		
		DataValue dataValue = new DataValue(data);
		
		return dataValue;
	}

}
