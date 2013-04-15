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

package org.kuali.student.lum.lu.ui.tools.client.widgets.itemlist;

import java.util.List;

import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBindingSupport;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.r1.common.assembly.data.QueryPath;

public abstract class ItemListFieldBinding<V extends ItemValue<V>> extends ModelWidgetBindingSupport<ItemList<V>> {

    @Override
    public void setModelValue(ItemList<V> widget, DataModel model, String path) {
        QueryPath qPath = QueryPath.parse(path);
        List<V> values = widget.getValue();
        Data valuesData = new Data();
        if (values != null) {
            for (V val : values) {
                Data valueData = new Data();
                setItemData(val, valueData);
                valuesData.add(valueData);
            }
        }
        model.set(qPath, valuesData);
    }

    @Override
    public void setWidgetValue(ItemList<V> widget, DataModel model, String path) {
        QueryPath qPath = QueryPath.parse(path);
        Data valueData = model.get(qPath);
        List<V> values = null;
        
        if (valueData != null) {
            values = getItemList(valueData);
            widget.setValue(values);
        } else {
            widget.setValue(null);
        }
    }
    
    /**
     * Used by setModelValue to set values specific to the type of Item
     * @param val
     * @param data
     */
    public abstract void setItemData(V val, Data data);
    
    public abstract List<V> getItemList(Data data);
    
}
