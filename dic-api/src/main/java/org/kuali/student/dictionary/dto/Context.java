package org.kuali.student.dictionary.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Context implements Serializable {

    private static final long serialVersionUID = 4612205991849119609L;
    
    @XmlAttribute
    String  type;
    @XmlElement
    List<String> contextValues;
    /**
     * @return the type
     */
    public String getType() {
        return type;
    }
    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }
    /**
     * @return the contextValues
     */
    public List<String> getContextValues() {
        return contextValues;
    }
    /**
     * @param contextValues the contextValues to set
     */
    public void setContextValues(List<String> contextValues) {
        this.contextValues = contextValues;
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
        result = prime * result + ((contextValues == null) ? 0 : contextValues.hashCode());
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
        Context other = (Context) obj;
        if (contextValues == null) {
            if (other.contextValues != null) {
                return false;
            }
        } else if (!contextValues.equals(other.contextValues)) {
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
