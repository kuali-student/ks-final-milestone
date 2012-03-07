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

import org.kuali.student.common.assembly.data.QueryPath;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityGroupItem;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.sections.SectionBinding;
import org.kuali.student.common.ui.client.mvc.DataModel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;

/**
 * This binding simply adds an index key to the multiplicity item's parent path and then calls the binding ModelWidgetBinding
 * of the underlying multiplicity item's widget.
 *
 * @author Kuali Student Team
 */
public class MultiplicityGroupItemBinding extends ModelWidgetBindingSupport<MultiplicityGroupItem> {
    public static MultiplicityGroupItemBinding INSTANCE = new MultiplicityGroupItemBinding();

    protected static final String RT_CREATED = "_runtimeData" + QueryPath.getPathSeparator() + "created";
    protected static final String RT_UPDATED = "_runtimeData" + QueryPath.getPathSeparator() + "updated";
    protected static final String RT_DELETED = "_runtimeData" + QueryPath.getPathSeparator() + "deleted";

    private MultiplicityGroupItemBinding() {};

    /**
     * @see ModelWidgetBindingSupport#setModelValue(Object,
     *      org.kuali.student.common.ui.client.mvc.DataModel, String)
     */
    @Override
    public void setModelValue(MultiplicityGroupItem multiplicityItem, DataModel model, String path) {
        String itemPath = QueryPath.concat(path, String.valueOf(multiplicityItem.getItemKey())).toString();
    	String itemRuntimePath = itemPath;
        Widget widget = multiplicityItem.getItemWidget();
        
        boolean multiplicityItemIsDirty = false;
        if (widget instanceof Section) {        	
        	if (((Section)widget).isDirty()){
        		//Only update model if multiplicity section is dirty.
	        	itemPath = "";
	            SectionBinding.INSTANCE.setModelValue((Section) widget, model, itemPath);
	            multiplicityItemIsDirty = true;
        	} 
        } else if (widget instanceof ModelWidgetBinding) {
            ((ModelWidgetBinding) widget).setModelValue(widget, model, path);
            multiplicityItemIsDirty = true;
        } else {
            GWT.log(itemPath + " has no widget binding.", null);
        }

        // Multiplicity metadata?
       if (multiplicityItemIsDirty || multiplicityItem.isDeleted()){
	        QueryPath qPath;
	        if (multiplicityItem.isCreated()) {
	            qPath = QueryPath.parse(itemRuntimePath + QueryPath.getPathSeparator() + RT_CREATED);
	        } else if (multiplicityItem.isDeleted()) {
	            qPath = QueryPath.parse(itemRuntimePath + QueryPath.getPathSeparator() + RT_DELETED);
	        } else {
	            qPath = QueryPath.parse(itemRuntimePath + QueryPath.getPathSeparator() + RT_UPDATED);
	        }
	        try {
	            Boolean oldValue = model.get(qPath);
	            Boolean newValue = true;
	            if (!nullsafeEquals(oldValue, newValue)) {
	                model.set(qPath, newValue);
	                setDirtyFlag(model, qPath);
	            }
	        } catch (IllegalArgumentException e) {
	            // model.get(qPath) will throw this exception if there is no such path
	            GWT.log("Warning: Ignoring error attempting to setValue for " + widget.getClass().getName() + " path: " + qPath, e);
	        }
       }

    }

    /**
     * @see ModelWidgetBindingSupport#setWidgetValue(Object,
     *      org.kuali.student.common.ui.client.mvc.DataModel, String)
     */
    @Override
    public void setWidgetValue(MultiplicityGroupItem multiplicityItem, DataModel model, String path) {
//        String itemPath = path + QueryPath.getPathSeparator() + multiplicityItem.getItemKey();
        String itemPath ="";
        Widget widget = multiplicityItem.getItemWidget();
        if (widget instanceof Section) {
            SectionBinding.INSTANCE.setWidgetValue((Section) widget, model, itemPath);
        } else if (widget instanceof ModelWidgetBinding) {
            ((ModelWidgetBinding) widget).setWidgetValue(widget, model, path);
        } else {
            GWT.log(itemPath + " has no widget binding.", null);
        }

        multiplicityItem.setCreated(false);
        multiplicityItem.setCreated(false);
    }


}