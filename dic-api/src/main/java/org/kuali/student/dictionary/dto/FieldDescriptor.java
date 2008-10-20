package org.kuali.student.dictionary.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class FieldDescriptor implements Serializable {

    private static final long serialVersionUID = -7981138053455188764L;
    
    @XmlElement
    String dataType;
    @XmlElement
    int minLength;
    @XmlElement
    int maxLength;
    @XmlElement
    String validChars;
    @XmlElement
    String invalidChars;    
    @XmlElement
    EnumFieldView enumFieldView;    
    @XmlElement
    int minOccurs;
    @XmlElement
    int maxOccurs;
    
    /**
     * @return the dataType
     */
    public String getDataType() {
        return dataType;
    }
    /**
     * @param dataType the dataType to set
     */
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
    /**
     * @return the minLength
     */
    public int getMinLength() {
        return minLength;
    }
    /**
     * @param minLength the minLength to set
     */
    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }
    /**
     * @return the maxLength
     */
    public int getMaxLength() {
        return maxLength;
    }
    /**
     * @param maxLength the maxLength to set
     */
    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }
    /**
     * @return the validChars
     */
    public String getValidChars() {
        return validChars;
    }
    /**
     * @param validChars the validChars to set
     */
    public void setValidChars(String validChars) {
        this.validChars = validChars;
    }
    /**
     * @return the invalidChars
     */
    public String getInvalidChars() {
        return invalidChars;
    }
    /**
     * @param invalidChars the invalidChars to set
     */
    public void setInvalidChars(String invalidChars) {
        this.invalidChars = invalidChars;
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
        result = prime * result + ((dataType == null) ? 0 : dataType.hashCode());
        result = prime * result + ((enumFieldView == null) ? 0 : enumFieldView.hashCode());
        result = prime * result + ((invalidChars == null) ? 0 : invalidChars.hashCode());
        result = prime * result + maxLength;
        result = prime * result + maxOccurs;
        result = prime * result + minLength;
        result = prime * result + minOccurs;
        result = prime * result + ((validChars == null) ? 0 : validChars.hashCode());
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
        FieldDescriptor other = (FieldDescriptor) obj;
        if (dataType == null) {
            if (other.dataType != null) {
                return false;
            }
        } else if (!dataType.equals(other.dataType)) {
            return false;
        }
        if (enumFieldView == null) {
            if (other.enumFieldView != null) {
                return false;
            }
        } else if (!enumFieldView.equals(other.enumFieldView)) {
            return false;
        }
        if (invalidChars == null) {
            if (other.invalidChars != null) {
                return false;
            }
        } else if (!invalidChars.equals(other.invalidChars)) {
            return false;
        }
        if (maxLength != other.maxLength) {
            return false;
        }
        if (maxOccurs != other.maxOccurs) {
            return false;
        }
        if (minLength != other.minLength) {
            return false;
        }
        if (minOccurs != other.minOccurs) {
            return false;
        }
        if (validChars == null) {
            if (other.validChars != null) {
                return false;
            }
        } else if (!validChars.equals(other.validChars)) {
            return false;
        }
        return true;
    }
  
   
    
}
