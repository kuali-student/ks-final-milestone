package org.kuali.student.dictionary.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class ObjectStructure implements Serializable{

    private static final long serialVersionUID = 7664540007997918206L;
    
    @XmlAttribute
    TypeSelector type;
    @XmlAttribute
    String objectTypeKey;
    
    /**
     * @return the type
     */
    public TypeSelector getType() {
        return type;
    }
    /**
     * @param type the type to set
     */
    public void setType(TypeSelector type) {
        this.type = type;
    }
    /**
     * @return the objectTypeKey
     */
    public String getObjectTypeKey() {
        return objectTypeKey;
    }
    /**
     * @param objectTypeKey the objectTypeKey to set
     */
    public void setObjectTypeKey(String objectTypeKey) {
        this.objectTypeKey = objectTypeKey;
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
        result = prime * result + ((objectTypeKey == null) ? 0 : objectTypeKey.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
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
        ObjectStructure other = (ObjectStructure) obj;
        if (objectTypeKey == null) {
            if (other.objectTypeKey != null) {
                return false;
            }
        } else if (!objectTypeKey.equals(other.objectTypeKey)) {
            return false;
        }
        if (type == null) {
            if (other.type != null) {
                return false;
            }
        } else if (!type.equals(other.type)) {
            return false;
        }
        return true;
    }
    
}
