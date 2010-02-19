/*
 * Copyright 2009 The Kuali Foundation Licensed under the Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.common.ui.client.configurable.mvc.binding;

import java.util.Iterator;
import java.util.List;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.core.assembly.data.Data.Property;

import com.google.gwt.user.client.ui.Widget;

public class SelectItemWidgetBinding extends ModelWidgetBindingSupport<KSSelectItemWidgetAbstract> {

    public static SelectItemWidgetBinding INSTANCE = new SelectItemWidgetBinding();

    private SelectItemWidgetBinding() {}

    @Override
    public void setModelValue(KSSelectItemWidgetAbstract object, DataModel model, String path) {
        QueryPath qPath = QueryPath.parse(path);

        if (object.isMultipleSelect()) {
            // TODO: Clear existing data rather than create new one?

            Data newValue = new Data();

            List<String> selectedItems = object.getSelectedItems();
            for (String stringItem : selectedItems) {
                newValue.add(stringItem);
            }

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
        
        Callback<Widget> selectListItemsCallback = new Callback<Widget>(){
            @Override
            public void exec(Widget widget) {
                QueryPath qPath = QueryPath.parse(path);
                Object value = model.get(qPath);

                // TODO Will Gomes - THIS METHOD NEEDS JAVADOCS
                ((KSSelectItemWidgetAbstract)widget).clear();
                if (value instanceof String) {
                    // is a single id
                    ((KSSelectItemWidgetAbstract)widget).selectItem((String) value);
                } else if (value instanceof Data) {
                    for (Iterator itr = ((Data) value).iterator(); itr.hasNext();) {
                        Property p = (Property) itr.next();
                        ((KSSelectItemWidgetAbstract)widget).selectItem((String) p.getValue());
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
