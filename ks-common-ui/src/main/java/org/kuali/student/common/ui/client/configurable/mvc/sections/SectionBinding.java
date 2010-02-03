package org.kuali.student.common.ui.client.configurable.mvc.sections;

import java.util.List;

import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBinding;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBindingSupport;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.core.assembly.data.QueryPath;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;

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
