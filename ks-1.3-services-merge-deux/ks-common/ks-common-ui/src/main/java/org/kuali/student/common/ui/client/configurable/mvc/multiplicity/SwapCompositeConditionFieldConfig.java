package org.kuali.student.common.ui.client.configurable.mvc.multiplicity;

public class SwapCompositeConditionFieldConfig {

    private MultiplicityFieldConfiguration multiplicityFieldConfiguration;
    private MultiplicityConfiguration multiplicityConfiguration;
    
    public SwapCompositeConditionFieldConfig(MultiplicityFieldConfiguration multiplicityFieldConfiguration,
            MultiplicityConfiguration multiplicityConfiguration) {
        setMultiplicityFieldConfiguration(multiplicityFieldConfiguration);
        setMultiplicityConfiguration(multiplicityConfiguration);
    }

    public MultiplicityFieldConfiguration getMultiplicityFieldConfiguration() {
        return multiplicityFieldConfiguration;
    }
    public void setMultiplicityFieldConfiguration(MultiplicityFieldConfiguration multiplicityFieldConfiguration) {
        this.multiplicityFieldConfiguration = multiplicityFieldConfiguration;
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
