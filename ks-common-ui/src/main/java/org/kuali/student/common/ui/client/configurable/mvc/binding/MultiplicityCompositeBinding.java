/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.common.ui.client.configurable.mvc.binding;

import java.util.Iterator;

import org.kuali.student.common.assembly.client.Data;
import org.kuali.student.common.assembly.client.DataModel;
import org.kuali.student.common.assembly.client.QueryPath;
import org.kuali.student.common.assembly.client.Data.Property;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityComposite;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityItem;

/**
 * This just goes through each item in the multiplicity composite and calls it's binding 
 * 
 * @author Kuali Student Team
 *
 */
public class MultiplicityCompositeBinding implements ModelWidgetBinding<MultiplicityComposite> {
    public static MultiplicityCompositeBinding INSTANCE = new MultiplicityCompositeBinding();
    
    private MultiplicityCompositeBinding(){};

    
    /**
     * @see org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBinding#setModelValue(java.lang.Object, org.kuali.student.common.assembly.client.DataModel, java.lang.String)
     */
    @Override
    public void setModelValue(MultiplicityComposite mcWidget, DataModel model, String path) {
        for (MultiplicityItem item:mcWidget.getItems()){
            MultiplicityItemBinding.INSTANCE.setModelValue(item, model, path);
        }
    }

    /**
     * @see org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBinding#setWidgetValue(java.lang.Object, org.kuali.student.common.assembly.client.DataModel, java.lang.String)
     */
    @Override
    public void setWidgetValue(MultiplicityComposite mcWidget, DataModel model, String path) {
        mcWidget.clear();
        
        QueryPath qPath = QueryPath.parse(path);
        Data data = model.get(qPath);

        if (data != null){
            Iterator<Property> itr = data.iterator();
            while (itr.hasNext()){
                Property p = (Property) itr.next();
                
                //FIXME: Not sure if this is a good way to get multiplicity item key            
                if (p.getKey() instanceof Integer){
                    MultiplicityItem item = mcWidget.addItem();        
                    //FIXME: Is assumption correct that data passed to setValue() has already been persisted
                    item.setCreated(false);
                    item.setItemKey((Integer)p.getKey());
                    MultiplicityItemBinding.INSTANCE.setWidgetValue(item, model, path);                
                }
            }
        }
    }

}
