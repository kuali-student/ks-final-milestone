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

package org.kuali.student.common.ui.client.configurable.mvc.binding.wip;

import java.util.Iterator;

import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBindingSupport;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.wip.MultiplicityGroup;
import org.kuali.student.common.ui.client.configurable.mvc.sections.wip.MultiplicityItemSection;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.core.assembly.data.Data.Property;

/**
 * This goes through each item in the MultiplicityGroup and calls it's binding
 *
 * @author Kuali Student Team
 */
public class MultiplicityGroupBinding extends ModelWidgetBindingSupport<MultiplicityGroup> {
    public static MultiplicityGroupBinding INSTANCE = new MultiplicityGroupBinding();

    /**
     * 
     *      !!!!!! WORK IN PROGRESS  !!!!!!
     *     
     */
    public MultiplicityGroupBinding() {};

    /**
     * @see ModelWidgetBindingSupport#setModelValue(Object,
     *      org.kuali.student.common.ui.client.mvc.DataModel, String)
     */
    @Override
    public void setModelValue(MultiplicityGroup mcWidget, DataModel model, String path) {
        for (MultiplicityItemSection item : mcWidget.getItems()) {
            MultiplicityItemBinding.INSTANCE.setModelValue(item, model, mcWidget.getParentPath());
        }
        for (MultiplicityItemSection item : mcWidget.getRemovedItems()) {
            //Check flag to add model binding for only those elements that are either added to the DB or
            // loading frm the DB.
            if(item.isCreated()==false){
                MultiplicityItemBinding.INSTANCE.setModelValue(item, model, path);
            }
        }
    }

    /**
     * @see ModelWidgetBindingSupport#setWidgetValue(Object,
     *      org.kuali.student.common.ui.client.mvc.DataModel, String)
     */
    public void setWidgetValue(MultiplicityGroup mg, DataModel model, String path) {
        mg.clear();

        String fieldPath = mg.translatePath(path);
 
		mg.setParentPath(fieldPath);

        QueryPath qPath = QueryPath.parse(fieldPath);
        Data data = null;
        if(model!=null){
        	data = model.get(qPath);
        }

        if (data != null) {
            Iterator<Property> itr = data.iterator();
            while (itr.hasNext()) {
                Property p = (Property) itr.next();

                if (p.getKey() instanceof Integer && !isItemDeleted(model, path, (Integer)p.getKey(), mg)) {
                	MultiplicityItemSection mgi = mg.createItem();
                    mgi.setCreated(false);
                    mgi.setItemKey((Integer) p.getKey());
                    MultiplicityItemBinding.INSTANCE.setWidgetValue(mgi, model, fieldPath);
                } else {
                	mg.incrementItemKey();
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
    public boolean isItemDeleted(DataModel model, String path, Integer index, MultiplicityGroup mcWidget){
    	boolean isDeleted = false;

    	// If multiplicity read only no point checking for deletions
    	if (!mcWidget.getConfig().isUpdateable()) {

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