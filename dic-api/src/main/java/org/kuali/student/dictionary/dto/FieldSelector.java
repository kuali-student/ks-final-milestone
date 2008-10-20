package org.kuali.student.dictionary.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class FieldSelector implements Serializable{

    private static final long serialVersionUID = -3040078969426920483L;
    @XmlAttribute
    String  key;
    @XmlElement
    FieldDescriptor fieldDescriptor;
    @XmlElement
    boolean readOnly;
    @XmlElement
    boolean isSelector;
    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }
    /**
     * @param key the key to set
     */
    public void setKey(String key) {
        this.key = key;
    }
    /**
     * @return the fieldDescriptor
     */
    public FieldDescriptor getFieldDescriptor() {
        return fieldDescriptor;
    }
    /**
     * @param fieldDescriptor the fieldDescriptor to set
     */
    public void setFieldDescriptor(FieldDescriptor fieldDescriptor) {
        this.fieldDescriptor = fieldDescriptor;
    }
    /**
     * @return the readOnly
     */
    public boolean isReadOnly() {
        return readOnly;
    }
    /**
     * @param readOnly the readOnly to set
     */
    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }
    /**
     * @return the isSelector
     */
    public boolean isSelector() {
        return isSelector;
    }
    /**
     * @param isSelector the isSelector to set
     */
    public void setSelector(boolean isSelector) {
        this.isSelector = isSelector;
    }
    /**
     * This overridden method ...
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((fieldDescriptor == null) ? 0 : fieldDescriptor.hashCode());
        result = prime * result + (isSelector ? 1231 : 1237);
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        result = prime * result + (readOnly ? 1231 : 1237);
        return result;
    }
    /**
     * This overridden method ...
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        FieldSelector other = (FieldSelector) obj;
        if (fieldDescriptor == null) {
            if (other.fieldDescriptor != null) {
                return false;
            }
        } else if (!fieldDescriptor.equals(other.fieldDescriptor)) {
            return false;
        }
        if (isSelector != other.isSelector) {
            return false;
        }
        if (key == null) {
            if (other.key != null) {
                return false;
            }
        } else if (!key.equals(other.key)) {
            return false;
        }
        if (readOnly != other.readOnly) {
            return false;
        }
        return true;
    }
    
    
}
