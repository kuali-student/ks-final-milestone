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

package org.kuali.student.common.ui.client.configurable.mvc;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.ListType;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.StringType;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;

@Deprecated
public class SelectItemWidgetBinding implements PropertyBinding<KSSelectItemWidgetAbstract>{

    public static SelectItemWidgetBinding INSTANCE = new SelectItemWidgetBinding();
    
    private SelectItemWidgetBinding(){}
    
    @Override
    public ModelDTOValue getValue(KSSelectItemWidgetAbstract object) {
        ModelDTOValue modelValue = null;
        if (object.isMultipleSelect()){         
            modelValue = new ListType();
            
            List<ModelDTOValue> modelValueList = new ArrayList<ModelDTOValue>();                        
            List<String> selectedItems = object.getSelectedItems();
            for (String stringItem:selectedItems){
                StringType stringTypeItem = new StringType();
                stringTypeItem.set(stringItem);
                modelValueList.add(stringTypeItem);
            }
             
            ((ListType)modelValue).set(modelValueList);
        } else {                        
             modelValue = new StringType();
            ((StringType) modelValue).set(object.getSelectedItem());
        }
        return modelValue;
    }

    @Override
    public void setValue(KSSelectItemWidgetAbstract object, ModelDTOValue value) {
        object.clear();
        if(value instanceof StringType){
            //is a single id
            String id = ((StringType) value).get();
            object.selectItem(id);
        }
        else if(value instanceof ListType){
            //is multiple ids
            List<ModelDTOValue> ids = ((ListType) value).get();
            for(ModelDTOValue mv : ids){
                if(mv instanceof StringType){
                    String id = ((StringType) mv).get();
                    object.selectItem(id);
                }
            }    
        }
    }
}
