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
import java.util.List;

import org.kuali.student.common.assembly.data.Data;
import org.kuali.student.common.assembly.data.QueryPath;
import org.kuali.student.common.assembly.data.Data.DataValue;
import org.kuali.student.common.assembly.data.Data.Property;
import org.kuali.student.common.assembly.data.Data.StringValue;
import org.kuali.student.common.assembly.data.Data.Value;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;

import com.google.gwt.user.client.ui.Widget;

/**
 * Model widget binding for KSSelectItemWidgetAbstract widgets
 * 
 * @author Kuali Student Team
 *
 */
public class SelectItemWidgetBinding extends ModelWidgetBindingSupport<KSSelectItemWidgetAbstract> {

    public static SelectItemWidgetBinding INSTANCE = new SelectItemWidgetBinding();

    private SelectItemWidgetBinding() {}

    @Override
    public void setModelValue(KSSelectItemWidgetAbstract object, DataModel model, String path) {
        QueryPath qPath = QueryPath.parse(path);


        if (object.isMultipleSelect()) {
        	Data newValue = (Data)getWidgetValue(object).get();
            Data oldValue = model.get(qPath);
            if (!nullsafeEquals(oldValue, newValue)) {
                model.set(qPath, newValue);
                setDirtyFlag(model, qPath);
            }
        } else {
            String newValue = object.getSelectedItem();
            String oldValue = model.get(qPath);
            if (!nullsafeEquals(oldValue, newValue)) {
                model.set(qPath, object.getSelectedItem());
                setDirtyFlag(model, qPath);
            }
        }

    }

    @Override
    public void setWidgetValue(KSSelectItemWidgetAbstract object, final DataModel model, final String path) {
        QueryPath qPath = QueryPath.parse(path);
        Object value = model.get(qPath);
        setWidgetValue(object, value);
    }
    
    /**
     * Helper method get list item widget's values as a Data object
     * 
     * @param object
     * @param value
     * @return
     */
    public Value getWidgetValue(KSSelectItemWidgetAbstract object){
    	Value value;
    	
        if (object.isMultipleSelect()) {
        	Data data = new Data();
        	List<String> selectedItems = object.getSelectedItems();
	        for (String stringItem : selectedItems) {
	           data.add(stringItem);
	        }
	        value = new Data.DataValue(data);
        } else {
        	value = new Data.StringValue(object.getSelectedItem());
        }

        return value;
    }
    
    /**
     * Helper method to set Data object to a list item widget
     * @param object
     * @param value
     */
    public void setWidgetValue(KSSelectItemWidgetAbstract object, final Object value){
    	Callback<Widget> selectListItemsCallback = new Callback<Widget>(){
            @Override
            public void exec(Widget widget) {

                ((KSSelectItemWidgetAbstract)widget).clear();
                if (value != null){
	                if (value instanceof String || value instanceof StringValue) {
	                	String itemId = (String)(value instanceof String ? value:((StringValue)value).get());
	                    ((KSSelectItemWidgetAbstract)widget).selectItem(itemId);
	                } else if (value instanceof Data || value instanceof DataValue) {
	                	Data data = (Data)(value instanceof Data ? value:((DataValue)value).get());
	                    for (Iterator itr = data.iterator(); itr.hasNext();) {
	                        Property p = (Property) itr.next();
	                        ((KSSelectItemWidgetAbstract)widget).selectItem((String) p.getValue());
	                    }
	                }
                }
            }            
        };

        if (!object.isInitialized()){
            object.addWidgetReadyCallback(selectListItemsCallback);
        } else{
            selectListItemsCallback.exec(object);
        }    	
    }
    

}
