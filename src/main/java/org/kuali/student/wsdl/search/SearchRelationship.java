
package org.kuali.student.wsdl.search;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for searchRelationship complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="searchRelationship">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="lhs" type="{http://student.kuali.org/wsdl/search}typeAttribute" minOccurs="0"/>
 *         &lt;element name="rhs" type="{http://student.kuali.org/wsdl/search}typeAttribute" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "searchRelationship", propOrder = {
    "lhs",
    "rhs"
})
public class SearchRelationship {

    protected TypeAttribute lhs;
    protected TypeAttribute rhs;

    /**
     * Gets the value of the lhs property.
     * 
     * @return
     *     possible object is
     *     {@link TypeAttribute }
     *     
     */
    public TypeAttribute getLhs() {
        return lhs;
    }

    /**
     * Sets the value of the lhs property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeAttribute }
     *     
     */
    public void setLhs(TypeAttribute value) {
        this.lhs = value;
    }

    /**
     * Gets the value of the rhs property.
     * 
     * @return
     *     possible object is
     *     {@link TypeAttribute }
     *     
     */
    public TypeAttribute getRhs() {
        return rhs;
    }

    /**
     * Sets the value of the rhs property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeAttribute }
     *     
     */
    public void setRhs(TypeAttribute value) {
        this.rhs = value;
    }

}
