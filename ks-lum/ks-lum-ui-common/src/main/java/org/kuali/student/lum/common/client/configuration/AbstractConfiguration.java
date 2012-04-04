package org.kuali.student.lum.common.client.configuration;

import org.kuali.student.common.ui.client.configurable.mvc.Configurer;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;

/**
 * Skeletal implementation of {@link Configuration}.
 *
 * @author Igor
 */
public abstract class AbstractConfiguration implements Configuration {

    protected Configurer configurer;

    public void setConfigurer(Configurer configurer) {
        this.configurer = configurer;
    }

    @Override
    public void applyRestrictions() {
    }

    @Override
    public boolean checkPermission(DataModel model) {
        return false;
    }

    @Override
    public void removeRestrictions() {

    }
    
    /**
     * Generates a message info to be used in your field descriptor to get the label for the field.
     * Used by the field descriptor with the application context to determine the label to show based on
     * the labelKey
     * @param labelKey key of the message - must match a message in your messages (stored in the db)
     * @return
     */
    public MessageKeyInfo generateMessageInfo(String labelKey) {
        return this.configurer.generateMessageInfo(labelKey);
    }
    
    /**
     * Gets the string corresponding to the label key passed in from the application messages
     * @param labelKey
     * @return
     */
    public String getLabel(String labelKey) {
        return this.configurer.getLabel(labelKey);
    }
}
