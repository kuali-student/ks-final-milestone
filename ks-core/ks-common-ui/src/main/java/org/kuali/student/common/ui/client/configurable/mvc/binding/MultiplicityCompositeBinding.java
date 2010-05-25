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

import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.DisplayMultiplicityComposite;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityComposite;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityItem;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.core.assembly.data.Data.Property;

/**
 * This just goes through each item in the multiplicity composite and calls it's binding
 * 
 * @author Kuali Student Team
 */
public class MultiplicityCompositeBinding extends ModelWidgetBindingSupport<MultiplicityComposite> {
    public static MultiplicityCompositeBinding INSTANCE = new MultiplicityCompositeBinding();

    private MultiplicityCompositeBinding() {};

    /**
     * @see org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBindingSupport#setModelValue(java.lang.Object,
     *      org.kuali.student.common.ui.client.mvc.DataModel, java.lang.String)
     */
    @Override
    public void setModelValue(MultiplicityComposite mcWidget, DataModel model, String path) {
        for (MultiplicityItem item : mcWidget.getItems()) {
            MultiplicityItemBinding.INSTANCE.setModelValue(item, model, path);
        }
        for (MultiplicityItem item : mcWidget.getRemovedItems()) {
            //Check flag to add model binding for only those elements that are either added to the DB or 
            // loading frm the DB. 
            if(item.isCreated()==false){
                MultiplicityItemBinding.INSTANCE.setModelValue(item, model, path);
            }
        }
    }

    /**
     * @see org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBindingSupport#setWidgetValue(java.lang.Object,
     *      org.kuali.student.common.ui.client.mvc.DataModel, java.lang.String)
     */
    @Override
    public void setWidgetValue(MultiplicityComposite mcWidget, DataModel model, String path) {
        mcWidget.clear();

        QueryPath qPath = QueryPath.parse(path);
        Data data = null;
        if(model!=null){
        	data = model.get(qPath);
        }

        if (data != null) {
            Iterator<Property> itr = data.iterator();
            while (itr.hasNext()) {
                Property p = (Property) itr.next();

                if (p.getKey() instanceof Integer && !isItemDeleted(model, path, (Integer)p.getKey(), mcWidget)) {                
                    MultiplicityItem item = mcWidget.addItem();
                    item.setCreated(false);
                    item.setItemKey((Integer) p.getKey());
                    MultiplicityItemBinding.INSTANCE.setWidgetValue(item, model, path);
                } else {
                	mcWidget.incrementItemKey();
                }
            }
        }
    }
    
    /**
     * Checks to see if an item at the given index in the multiplicity has been deleted
     * 
     * @param model
     * @param path
     * @param index
     */
    public boolean isItemDeleted(DataModel model, String path, Integer index, MultiplicityComposite mcWidget){
    	boolean isDeleted = false;
    	
    	// If this is a read only widget deletions aren't possible so no point checking
    	if (!(mcWidget instanceof DisplayMultiplicityComposite)) {

    		QueryPath runtimeDeletedPath = QueryPath.concat(path, String.valueOf(index), MultiplicityItemBinding.RT_DELETED);

    		Boolean runtimeDeleted = model.get(runtimeDeletedPath);
    		if (runtimeDeleted != null){
    			isDeleted = runtimeDeleted;
    		}
    		return isDeleted;
    	}
  	
    	return isDeleted;
    }


}
