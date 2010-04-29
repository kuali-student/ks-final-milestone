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

import java.util.List;

import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.core.assembly.data.QueryPath;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;

/**
 * This binding class can be used to bind fields (including fields in nested sections) to the model. The binding of all
 * fields happen relative to the path string supplied. If path="" none, items will be bound to the root of the model.
 * 
 * @author Kuali Student Team
 */
public class SectionBinding extends ModelWidgetBindingSupport<Section> {
    public static SectionBinding INSTANCE = new SectionBinding();

    private SectionBinding() {};

    /**
     * @see org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBindingSupport#setModelValue(java.lang.Object,
     *      org.kuali.student.common.ui.client.mvc.DataModel, java.lang.String)
     */
    @Override
    public void setModelValue(Section section, DataModel model, String path) {
        List<FieldDescriptor> fields = section.getFields();

        for (int i = 0; i < fields.size(); i++) {
            FieldDescriptor field = (FieldDescriptor) fields.get(i);
            String fieldPath = path + QueryPath.getPathSeparator() + field.getFieldKey();
            fieldPath = fieldPath.trim();
            if (fieldPath.startsWith(QueryPath.getPathSeparator())) {
                fieldPath = fieldPath.substring(QueryPath.getPathSeparator().length());
            }
            ModelWidgetBinding binding = field.getModelWidgetBinding();
            if (binding != null) {
                Widget w = field.getFieldWidget();
                binding.setModelValue(w, model, fieldPath);
            } else {
                GWT.log(field.getFieldKey() + " has no widget binding.", null);
            }
        }
        for (Section s : section.getSections()) {
            s.updateModel(model);
        }
    }

    /**
     * @see org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBindingSupport#setWidgetValue(java.lang.Object,
     *      org.kuali.student.common.ui.client.mvc.DataModel, java.lang.String)
     */
    @Override
    public void setWidgetValue(Section section, DataModel model, String path) {
        List<FieldDescriptor> fields = section.getFields();

        for (int i = 0; i < fields.size(); i++) {
            FieldDescriptor field = (FieldDescriptor) fields.get(i);

            ModelWidgetBinding binding = field.getModelWidgetBinding();
            String fieldPath = path + QueryPath.getPathSeparator() + field.getFieldKey();
            if (binding != null) {
                Widget w = field.getFieldWidget();
                binding.setWidgetValue(w, model, fieldPath);
            } else {
                GWT.log(field.getFieldKey() + " has no widget binding.", null);
            }
        }
        for (Section s : section.getSections()) {
            s.updateView(model);
        }
    }

}
