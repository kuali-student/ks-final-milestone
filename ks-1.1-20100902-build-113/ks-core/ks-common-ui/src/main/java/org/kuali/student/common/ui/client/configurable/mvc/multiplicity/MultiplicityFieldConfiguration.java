package org.kuali.student.common.ui.client.configurable.mvc.multiplicity;

import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.core.assembly.data.Metadata;

public class MultiplicityFieldConfiguration {
    
    private String fieldPath;
    private MessageKeyInfo messageKeyInfo;
    private Metadata metadata;
    private MultiplicityFieldWidgetInitializer fieldWidgetInitializer;
    
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
}
