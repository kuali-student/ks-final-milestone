
package org.kuali.student.wsdl.dictionary;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.kuali.student.wsdl.course.ObjectStructureDefinitionWrapper;


/**
 * <p>Java class for getObjectStructureResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getObjectStructureResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://student.kuali.org/wsdl/course}objectStructureDefinitionWrapper" minOccurs="0" form="unqualified"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getObjectStructureResponse", propOrder = {
    "_return"
})
public class GetObjectStructureResponse {

    @XmlElement(name = "return", namespace = "")
    protected ObjectStructureDefinitionWrapper _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link ObjectStructureDefinitionWrapper }
     *     
     */
    public ObjectStructureDefinitionWrapper getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectStructureDefinitionWrapper }
     *     
     */
    public void setReturn(ObjectStructureDefinitionWrapper value) {
        this._return = value;
    }

}
