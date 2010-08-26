
package org.kuali.student.wsdl.commentservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import org.kuali.student.wsdl.comment.TagInfo;


/**
 * <p>Java class for addTag complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="addTag">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="referenceId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="referenceTypeKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tagInfo" type="{http://student.kuali.org/wsdl/comment}tagInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addTag", propOrder = {
    "referenceId",
    "referenceTypeKey",
    "tagInfo"
})
public class AddTag {

    protected String referenceId;
    protected String referenceTypeKey;
    protected TagInfo tagInfo;

    /**
     * Gets the value of the referenceId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferenceId() {
        return referenceId;
    }

    /**
     * Sets the value of the referenceId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferenceId(String value) {
        this.referenceId = value;
    }

    /**
     * Gets the value of the referenceTypeKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferenceTypeKey() {
        return referenceTypeKey;
    }

    /**
     * Sets the value of the referenceTypeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferenceTypeKey(String value) {
        this.referenceTypeKey = value;
    }

    /**
     * Gets the value of the tagInfo property.
     * 
     * @return
     *     possible object is
     *     {@link TagInfo }
     *     
     */
    public TagInfo getTagInfo() {
        return tagInfo;
    }

    /**
     * Sets the value of the tagInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link TagInfo }
     *     
     */
    public void setTagInfo(TagInfo value) {
        this.tagInfo = value;
    }

}
