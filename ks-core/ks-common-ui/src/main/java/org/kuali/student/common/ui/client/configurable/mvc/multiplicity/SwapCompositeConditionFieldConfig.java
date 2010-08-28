package org.kuali.student.common.ui.client.configurable.mvc.multiplicity;

import java.util.List;

import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;

public class SwapCompositeConditionFieldConfig {

    private FieldDescriptor fieldDescriptorConfig;
    private MultiplicityConfiguration multiplicityConfiguration;
    
    public SwapCompositeConditionFieldConfig(FieldDescriptor fieldDescriptorConfig,
            MultiplicityConfiguration multiplicityConfiguration) {
        setFieldDescriptorConfig(fieldDescriptorConfig);
        setMultiplicityConfiguration(multiplicityConfiguration);
    }

    public FieldDescriptor getFieldDescriptorConfig() {
        return fieldDescriptorConfig;
    }
    public void setFieldDescriptorConfig(FieldDescriptor fieldDescriptorConfig) {
        this.fieldDescriptorConfig = fieldDescriptorConfig;
    }
    public MultiplicityConfiguration getMultiplicityConfiguration() {
        return multiplicityConfiguration;
    }
    public void setMultiplicityConfiguration(MultiplicityConfiguration multiplicityConfiguration) {
        this.multiplicityConfiguration = multiplicityConfiguration;
    }
    public boolean useMultiplicity() {
        boolean useMultiplicity = false;
        if (multiplicityConfiguration != null) {
            useMultiplicity = true;
        }
        return useMultiplicity;
    }
}
