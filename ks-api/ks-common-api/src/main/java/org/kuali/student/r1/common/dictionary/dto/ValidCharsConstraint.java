package org.kuali.student.r1.common.dictionary.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Deprecated
@XmlAccessorType(XmlAccessType.FIELD)
public class ValidCharsConstraint extends BaseConstraint {
	    
    // TODO: Should this be a list of values
    @XmlElement
    protected String value; // (regex or list of valid chars)

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }
    

}