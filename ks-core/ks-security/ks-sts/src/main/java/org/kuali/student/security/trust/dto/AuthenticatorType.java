
package org.kuali.student.security.trust.dto;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.w3c.dom.Element;


/**
 * <p>Java class for AuthenticatorType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AuthenticatorType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://schemas.xmlsoap.org/ws/2005/02/trust}CombinedHash" minOccurs="0"/>
 *         &lt;any processContents='lax' namespace='##other' maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AuthenticatorType", propOrder = {
    "combinedHash",
    "any"
})
public class AuthenticatorType {

    @XmlElement(name = "CombinedHash")
    protected byte[] combinedHash;
    @XmlAnyElement(lax = true)
    protected List<Object> any;

    /**
     * Gets the value of the combinedHash property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getCombinedHash() {
        return combinedHash;
    }

    /**
     * Sets the value of the combinedHash property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setCombinedHash(byte[] value) {
        this.combinedHash = new byte[value.length];
        System.arraycopy(value, 0, this.combinedHash, 0, value.length);
//        this.combinedHash = ((byte[]) value);
    }

    /**
     * Gets the value of the any property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the any property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAny().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Object }
     * {@link Element }
     * 
     * 
     */
    public List<Object> getAny() {
        if (any == null) {
            any = new ArrayList<Object>();
        }
        return this.any;
    }

}
