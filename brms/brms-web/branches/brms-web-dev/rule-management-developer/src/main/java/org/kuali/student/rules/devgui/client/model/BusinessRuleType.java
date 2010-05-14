package org.kuali.student.rules.devgui.client.model;

import org.kuali.student.commons.ui.mvc.client.model.ModelObject;

public class BusinessRuleType implements ModelObject {
    private static final long serialVersionUID = 123123142351351L;

    private String id;
    private String key;
    private String anchorTypeKey;

    public String getUniqueId() {
        return id;
    }

    /**
     * @return the key
     */
    public final String getKey() {
        return key;
    }

    /**
     * @param key
     *            the key to set
     */
    public final void setKey(String key) {
        this.key = key;
    }

    /**
     * @return the anchorTypeKey
     */
    public final String getAnchorTypeKey() {
        return anchorTypeKey;
    }

    /**
     * @param anchorTypeKey
     *            the anchorTypeKey to set
     */
    public final void setAnchorTypeKey(String anchorTypeKey) {
        this.anchorTypeKey = anchorTypeKey;
    }

    /**
     * @return the serialVersionUID
     */
    public static final long getSerialVersionUID() {
        return serialVersionUID;
    }
}