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

import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityItem;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.sections.SectionBinding;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.r1.common.assembly.data.QueryPath;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;

/**
 * This binding simply adds an index key to the multiplicity item's parent path and then calls the binding ModelWidgetBinding
 * of the underlying multiplicity item's widget.
 * 
 * @author Kuali Student Team
 */

/**
 * @deprecated
 */
public class MultiplicityItemBinding extends ModelWidgetBindingSupport<MultiplicityItem> {
    public static MultiplicityItemBinding INSTANCE = new MultiplicityItemBinding();

    protected static final String RT_CREATED = "_runtimeData" + QueryPath.getPathSeparator() + "created";
    protected static final String RT_UPDATED = "_runtimeData" + QueryPath.getPathSeparator() + "updated";
    protected static final String RT_DELETED = "_runtimeData" + QueryPath.getPathSeparator() + "deleted";

    private MultiplicityItemBinding() {};

    /**
     * @see org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBindingSupport#setModelValue(java.lang.Object,
     *      org.kuali.student.common.ui.client.mvc.DataModel, java.lang.String)
     */
    @Override
    public void setModelValue(MultiplicityItem multiplicityItem, DataModel model, String path) {
        // TODO modify this method to use QueryPath.add to build paths, rather than string manipulation
        String itemPath = path + QueryPath.getPathSeparator() + multiplicityItem.getItemKey();
    	String mutiRuntimePath = itemPath;
        Widget widget = multiplicityItem.getItemWidget();
        if (widget instanceof Section) {
        	itemPath = "";
            SectionBinding.INSTANCE.setModelValue((Section) widget, model, itemPath);
        } else if (widget instanceof ModelWidgetBinding) {
            ((ModelWidgetBinding) widget).setModelValue(widget, model, path);
        } else {
            GWT.log(itemPath + " has no widget binding.", null);
        }

        // Multiplicity metadata?
       QueryPath qPath;
        if (multiplicityItem.isCreated()) {
            qPath = QueryPath.parse(mutiRuntimePath + QueryPath.getPathSeparator() + RT_CREATED);
        } else if (multiplicityItem.isDeleted()) {
            qPath = QueryPath.parse(mutiRuntimePath + QueryPath.getPathSeparator() + RT_DELETED);
        } else {
            qPath = QueryPath.parse(mutiRuntimePath + QueryPath.getPathSeparator() + RT_UPDATED);
        }
        try {
            Boolean oldValue = model.get(qPath);
            Boolean newValue = true;
            if (!nullsafeEquals(oldValue, newValue)) {
                model.set(qPath, newValue);
                setDirtyFlag(model, qPath);
            }
        } catch (java.lang.IllegalArgumentException e) {
            // model.get(qPath) will throw this exception if there is no such path
            GWT.log("Warning: Ignoring error attempting to setValue for " + widget.getClass().getName() + " path: " + qPath, e);
        }

    }

    /**
     * @see org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBindingSupport#setWidgetValue(java.lang.Object,
     *      org.kuali.student.common.ui.client.mvc.DataModel, java.lang.String)
     */
    @Override
    public void setWidgetValue(MultiplicityItem multiplicityItem, DataModel model, String path) {
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
