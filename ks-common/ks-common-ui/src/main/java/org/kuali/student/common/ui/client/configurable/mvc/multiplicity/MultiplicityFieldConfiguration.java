package org.kuali.student.common.ui.client.configurable.mvc.multiplicity;

import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBinding;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.core.assembly.data.Metadata;

public class MultiplicityFieldConfiguration {
    
    private String fieldPath;
    private MessageKeyInfo messageKeyInfo;
    private Metadata metadata;
    private MultiplicityFieldWidgetInitializer fieldWidgetInitializer;
    @SuppressWarnings("unchecked")
	private ModelWidgetBinding modelWidgetBinding;
    private boolean isRequired;
	private boolean optional = false;
    
    public MultiplicityFieldConfiguration(
            String fieldPath,
            MessageKeyInfo messageKeyInfo,
            Metadata metadata,
            MultiplicityFieldWidgetInitializer fieldWidgetInitializer) {
        setFieldPath(fieldPath);
        setMessageKeyInfo(messageKeyInfo);
        setMetadata(metadata);
        setFieldWidgetInitializer(fieldWidgetInitializer);
    }
    
    public String getFieldPath() {
        return fieldPath;
    }
    public void setFieldPath(String fieldPath) {
        this.fieldPath = fieldPath;
    }
    public MessageKeyInfo getMessageKeyInfo() {
        return messageKeyInfo;
    }
    public void setMessageKeyInfo(MessageKeyInfo messageKeyInfo) {
        this.messageKeyInfo = messageKeyInfo;
    }
    public Metadata getMetadata() {
        return metadata;
    }
    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }
    public MultiplicityFieldWidgetInitializer getFieldWidgetInitializer() {
        return fieldWidgetInitializer;
    }
    public void setFieldWidgetInitializer(MultiplicityFieldWidgetInitializer fieldWidgetInitializer) {
        this.fieldWidgetInitializer = fieldWidgetInitializer;
    }

    public boolean isRequired() {
        return isRequired;
    }

    public void setRequired(boolean isRequired) {
        this.isRequired = isRequired;
    }

	public void setModelWidgetBinding(ModelWidgetBinding modelWidgetBinding) {
		this.modelWidgetBinding = modelWidgetBinding;
	}

	public ModelWidgetBinding getModelWidgetBinding() {
		return modelWidgetBinding;
	}
	
	
	/**
	 * Sets the optional flag
	 * Fields that are optional should not be displayed if there is no data in some cases,
	 * it is up to the section implementation whether or not to honor this flag
	 * @param optional
	 */
	public void setOptional(boolean optional){
		this.optional = optional;
	}
	
	/**
	 * Fields that are optional should not be displayed if there is no data in some cases,
	 * it is up to the section implementation whether or not to honor this flag
	 */
	public boolean isOptional(){
		return optional;
	}
}
