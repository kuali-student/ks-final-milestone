package org.kuali.student.common.ui.client.configurable.mvc.sections;

/**
 * 
 * An event handler interface that handles events on the SwapSection class.  
 * 
 * @author SW Genis sw@psybergate.co.za
 *
 */
public interface SwapEventHandler {

    /**
     * 
     * Called whenever another sections is displayed.
     * 
     * @param key
     */
    public void onShowSwappableSection(String key, Section section);
    
    /**
     * 
     * Called whenever a section is removed.
     * 
     * @param key
     */
    public void onRemoveSwappableSection(String key, Section section);
}
