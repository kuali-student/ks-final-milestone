/*
 * Copyright 2007 The Kuali Foundation Licensed under the Educational Community License, Version 1.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.common.ui.client.configurable.mvc.binding;

import org.kuali.student.common.assembly.client.DataModel;
import org.kuali.student.common.assembly.client.QueryPath;
import org.kuali.student.common.ui.client.configurable.mvc.Section;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityItem;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;

/**
 * This binding simply adds an index key to the multiplicity item's parent path and then calls the binding 
 * ModelWidgetBinding of the underlying multiplicity item's widget.
 * 
 * @author Kuali Student Team
 */
public class MultiplicityItemBinding implements ModelWidgetBinding<MultiplicityItem> {
    public static MultiplicityItemBinding INSTANCE = new MultiplicityItemBinding();
    
    private MultiplicityItemBinding(){};

    
    /**
     * @see org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBinding#setModelValue(java.lang.Object,
     *      org.kuali.student.common.assembly.client.DataModel, java.lang.String)
     */
    @Override
    public void setModelValue(MultiplicityItem multiplicityItem, DataModel model, String path) {
        String itemPath = path + QueryPath.getPathSeparator() + multiplicityItem.getItemKey();
        Widget widget = multiplicityItem.getItemWidget();
        if (widget instanceof Section) {
            SectionBinding.INSTANCE.setModelValue((Section) widget, model, itemPath);
        } else if (widget instanceof ModelWidgetBinding) {
            ((ModelWidgetBinding) widget).setModelValue(widget, model, path);
        } else {
            GWT.log(itemPath + " has no widget binding.", null);
        }
        
        //Multiplicity metadata?
        QueryPath qPath = QueryPath.parse(itemPath + QueryPath.getPathSeparator() + "crudCreated");
        model.set(qPath, new Boolean(multiplicityItem.isCreated()));

        qPath = QueryPath.parse(itemPath + QueryPath.getPathSeparator() + "crudDeleted");        
        model.set(qPath, new Boolean(multiplicityItem.isDeleted()));    
    }

    /**
     * @see org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBinding#setWidgetValue(java.lang.Object,
     *      org.kuali.student.common.assembly.client.DataModel, java.lang.String)
     */
    @Override
    public void setWidgetValue(MultiplicityItem multiplicityItem, DataModel model, String path) {
        String itemPath = path + QueryPath.getPathSeparator() + multiplicityItem.getItemKey();
        Widget widget = multiplicityItem.getItemWidget();
        if (widget instanceof Section){
            SectionBinding.INSTANCE.setWidgetValue((Section)widget, model, itemPath);
        } else if (widget instanceof ModelWidgetBinding){
            ((ModelWidgetBinding)widget).setWidgetValue(widget, model, path);
        } else {
            GWT.log(itemPath + " has no widget binding.", null);
        }        

        //Multiplicity metadata?
        QueryPath qPath = QueryPath.parse(itemPath + QueryPath.getPathSeparator() + "crudCreated");
        Boolean existing = model.get(qPath);
        if (existing != null){
            multiplicityItem.setCreated(existing.booleanValue());
        }

        qPath = QueryPath.parse(itemPath + QueryPath.getPathSeparator() + "crudDeleted");
        Boolean deleted = model.get(qPath);
        if (existing != null){
            multiplicityItem.setCreated(deleted.booleanValue());
        }
    }

}
