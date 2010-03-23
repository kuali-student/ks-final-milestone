package org.kuali.student.lum.lu.ui.tools.client.configuration.itemlist;

import java.util.List;

import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBindingSupport;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.QueryPath;

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
