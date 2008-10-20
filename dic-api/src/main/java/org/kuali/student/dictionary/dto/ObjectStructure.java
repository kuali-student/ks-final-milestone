package org.kuali.student.dictionary.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class ObjectStructure implements Serializable{

    private static final long serialVersionUID = 7664540007997918206L;
    
    @XmlAttribute
    String type;
    @XmlAttribute
    String objectTypeKey;
    
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
    
}
