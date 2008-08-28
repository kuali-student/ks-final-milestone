
package org.kuali.student.rules.rulesmanagement.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "findAnchorsByAnchorType", namespace = "http://student.kuali.org/poc/wsdl/brms/rulesmanagement")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "findAnchorsByAnchorType", namespace = "http://student.kuali.org/poc/wsdl/brms/rulesmanagement")
public class FindAnchorsByAnchorType {

    @XmlElement(name = "anchorTypeKey", namespace = "")
    private String anchorTypeKey;

    /**
     * 
     * @return
     *     returns String
     */
    public String getAnchorTypeKey() {
        return this.anchorTypeKey;
    }

    /**
     * 
     * @param anchorTypeKey
     *     the value for the anchorTypeKey property
     */
    public void setAnchorTypeKey(String anchorTypeKey) {
        this.anchorTypeKey = anchorTypeKey;
    }

}
