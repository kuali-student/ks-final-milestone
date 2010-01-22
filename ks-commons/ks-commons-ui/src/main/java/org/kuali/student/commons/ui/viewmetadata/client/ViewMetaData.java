package org.kuali.student.commons.ui.viewmetadata.client;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.kuali.student.commons.ui.messages.client.Messages;

/**
 * Bean containing view metadata
 */
public class ViewMetaData implements Serializable {
    private static final long serialVersionUID = -4220576995454965057L;

    private String viewName;
    private Map<String, FieldMetaData> fields = new HashMap<String, FieldMetaData>();
    private Messages messages = null;
    private Map<String, String> configuration = null;
    private Set<String> inheritedViews = null;

    /**
     * 
     * Returns a Map<String, String> containing basic configuration information about the view.
     * 
     * @return Map<String, String> containing basic configuration information about the view.
     */
    public Map<String, String> getConfiguration() {
        return configuration;
    }

    /**
     * 
     * Sets a Map<String, String> containing basic configuration information about the view.
     * 
     * @param configuration Map<String, String> containing basic configuration information about the view.
     */
    public void setConfiguration(Map<String, String> configuration) {
        this.configuration = configuration;
    }
    

    /**
     * Returns the internationalization messages associated with the view
     * 
     * @return the internationalization messages associated with the view
     */
    public Messages getMessages() {
        return messages;
    }

    /**
     * Sets the internationalization messages associated with the view
     * 
     * @param messages
     *            the internationalization messages associated with the view
     */
    public void setMessages(Messages messages) {
        this.messages = messages;
    }

    /**
     * Creates an empty ViewMetaData object
     */
    public ViewMetaData() {
        super();
    }

    /**
     * Creates a ViewMetaData object with the specified view name
     * 
     * @param viewName
     *            the view name
     */
    public ViewMetaData(String viewName) {
        this.viewName = viewName;
    }

    /**
     * Returns the view name
     * 
     * @return the view name
     */
    public String getViewName() {
        return viewName;
    }

    /**
     * Sets the view name
     * 
     * @param viewName
     *            the view name
     */
    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    /**
     * Adds a field to the view
     * 
     * @param fmd
     *            the FieldMetaData object representing the field being added
     */
    public void addField(FieldMetaData fmd) {
        fmd.setParentView(this);
        fields.put(fmd.getFieldName(), fmd);
    }

    /**
     * Returns a Map containing the fields associated with the view
     * 
     * @return a Map containing the fields associated with the view
     */
    public Map<String, FieldMetaData> getFields() {
        return fields;
    }
    
    public synchronized Set<String> getInheritedViews() {
        if (inheritedViews == null) {
            inheritedViews = new HashSet<String>();
            String depends = configuration.get("depends");
            depends = (depends == null) ? "" : depends.trim();
            String[] arr = depends.split(",");
            for (String s : arr) {
                s = s.trim();
                if (s.length() > 0) {
                    inheritedViews.add(s);
                }
            }
        }
        
        return inheritedViews;
    }
    

}
