package org.kuali.student.common.ui.client.configurable.mvc;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.ModelDefinition;
import org.kuali.student.core.assembly.data.QueryPath;

import com.google.gwt.user.client.ui.Widget;

public abstract class Configurer {
    protected ModelDefinition modelDefinition;
    protected String type = "";
    protected String state = "";
    protected String groupName = "";
//    public abstract void configure(ConfigurableLayout layout);
    public void setModelDefinition(ModelDefinition modelDefinition){
        this.modelDefinition = modelDefinition;
    }

    public ModelDefinition getModelDefinition() {
        return modelDefinition;
    }

    protected MessageKeyInfo generateMessageInfo(String labelKey) {
        return new MessageKeyInfo(groupName, type, state, labelKey);
    }
    
    protected String getLabel(String labelKey) {
        return Application.getApplicationContext().getUILabel(groupName, type, state, labelKey);
    }

    protected SectionTitle getH1Title(String labelKey) {
        return SectionTitle.generateH1Title(getLabel(labelKey));
    }

    protected SectionTitle getH2Title(String labelKey) {
        return SectionTitle.generateH2Title(getLabel(labelKey));
    }

    protected SectionTitle getH3Title(String labelKey) {
        return SectionTitle.generateH3Title(getLabel(labelKey));
    }

    protected SectionTitle getH4Title(String labelKey) {
        return SectionTitle.generateH4Title(getLabel(labelKey));
    }

    protected SectionTitle getH5Title(String labelKey) {
        return SectionTitle.generateH5Title(getLabel(labelKey));
    }

    // TODO - when DOL is pushed farther down into LOBuilder,
    // revert these 5 methods to returning void again.
    public FieldDescriptor addField(Section section, String fieldKey) {
        return addField(section, fieldKey, null, null, null);
    }    
    public FieldDescriptor addField(Section section, String fieldKey, MessageKeyInfo messageKey) {
        return addField(section, fieldKey, messageKey, null, null);
    }
    public FieldDescriptor addField(Section section, String fieldKey, MessageKeyInfo messageKey, Widget widget) {
        return addField(section, fieldKey, messageKey, widget, null);
    }
    public FieldDescriptor addField(Section section, String fieldKey, MessageKeyInfo messageKey, String parentPath) {
        return addField(section, fieldKey, messageKey, null, parentPath);
    }
    
    public FieldDescriptor addField(Section section, String fieldKey, MessageKeyInfo messageKey, Widget widget, String parentPath) {
        QueryPath path = QueryPath.concat(parentPath, fieldKey);
        Metadata meta = modelDefinition.getMetadata(path);

        FieldDescriptor fd = new FieldDescriptor(path.toString(), messageKey, meta);
        if (widget != null) {
            fd.setFieldWidget(widget);
        }
        section.addField(fd);
        return fd;
    }
    
    public FieldDescriptor addReadOnlyField(Section section, String fieldKey) {
        return addReadOnlyField(section, fieldKey, null, null, null);
    }    
    public FieldDescriptor addReadOnlyField(Section section, String fieldKey, MessageKeyInfo messageKey) {
        return addReadOnlyField(section, fieldKey, messageKey, null, null);
    }
    public FieldDescriptor addReadOnlyField(Section section, String fieldKey, MessageKeyInfo messageKey, Widget widget) {
        return addReadOnlyField(section, fieldKey, messageKey, widget, null);
    }
    public FieldDescriptor addReadOnlyField(Section section, String fieldKey, MessageKeyInfo messageKey, String parentPath) {
        return addReadOnlyField(section, fieldKey, messageKey, null, parentPath);
    }
    
    public FieldDescriptor addReadOnlyField(Section section, String fieldKey, MessageKeyInfo messageKey, Widget widget, String parentPath) {
        QueryPath path = QueryPath.concat(parentPath, fieldKey);
        Metadata meta = modelDefinition.getMetadata(path);

        FieldDescriptor fd = new FieldDescriptorReadOnly(path.toString(), messageKey, meta);
        if (widget != null) {
            fd.setFieldWidget(widget);
        }
        section.addField(fd);
        return fd;
    }
}
