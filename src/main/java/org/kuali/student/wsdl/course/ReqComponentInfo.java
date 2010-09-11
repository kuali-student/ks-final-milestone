
package org.kuali.student.wsdl.course;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for reqComponentInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="reqComponentInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="desc" type="{http://student.kuali.org/wsdl/course}richTextInfo" minOccurs="0"/>
 *         &lt;element name="reqCompFields" type="{http://student.kuali.org/wsdl/course}reqCompFieldInfo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="effectiveDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="expirationDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="metaInfo" type="{http://student.kuali.org/wsdl/course}metaInfo" minOccurs="0"/>
 *         &lt;element name="requiredComponentType" type="{http://student.kuali.org/wsdl/course}reqComponentTypeInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="state" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="naturalLanguageTranslation" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "reqComponentInfo", propOrder = {
    "desc",
    "reqCompFields",
    "effectiveDate",
    "expirationDate",
    "metaInfo",
    "requiredComponentType"
})
public class ReqComponentInfo {

    protected RichTextInfo desc;
    protected List<ReqCompFieldInfo> reqCompFields;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar effectiveDate;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar expirationDate;
    protected MetaInfo metaInfo;
    protected ReqComponentTypeInfo requiredComponentType;
    @XmlAttribute(name = "type")
    protected String type;
    @XmlAttribute(name = "state")
    protected String state;
    @XmlAttribute(name = "id")
    protected String id;
    @XmlAttribute(name = "naturalLanguageTranslation")
    protected String naturalLanguageTranslation;

    /**
     * Gets the value of the desc property.
     * 
     * @return
     *     possible object is
     *     {@link RichTextInfo }
     *     
     */
    public RichTextInfo getDesc() {
        return desc;
    }

    /**
     * Sets the value of the desc property.
     * 
     * @param value
     *     allowed object is
     *     {@link RichTextInfo }
     *     
     */
    public void setDesc(RichTextInfo value) {
        this.desc = value;
    }

    /**
     * Gets the value of the reqCompFields property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the reqCompFields property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReqCompFields().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReqCompFieldInfo }
     * 
     * 
     */
    public List<ReqCompFieldInfo> getReqCompFields() {
        if (reqCompFields == null) {
            reqCompFields = new ArrayList<ReqCompFieldInfo>();
        }
        return this.reqCompFields;
    }

    /**
     * Gets the value of the effectiveDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * Sets the value of the effectiveDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEffectiveDate(XMLGregorianCalendar value) {
        this.effectiveDate = value;
    }

    /**
     * Gets the value of the expirationDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getExpirationDate() {
        return expirationDate;
    }

    /**
     * Sets the value of the expirationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setExpirationDate(XMLGregorianCalendar value) {
        this.expirationDate = value;
    }

    /**
     * Gets the value of the metaInfo property.
     * 
     * @return
     *     possible object is
     *     {@link MetaInfo }
     *     
     */
    public MetaInfo getMetaInfo() {
        return metaInfo;
    }

    /**
     * Sets the value of the metaInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link MetaInfo }
     *     
     */
    public void setMetaInfo(MetaInfo value) {
        this.metaInfo = value;
    }

    /**
     * Gets the value of the requiredComponentType property.
     * 
     * @return
     *     possible object is
     *     {@link ReqComponentTypeInfo }
     *     
     */
    public ReqComponentTypeInfo getRequiredComponentType() {
        return requiredComponentType;
    }

    /**
     * Sets the value of the requiredComponentType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReqComponentTypeInfo }
     *     
     */
    public void setRequiredComponentType(ReqComponentTypeInfo value) {
        this.requiredComponentType = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Gets the value of the state property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the value of the state property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setState(String value) {
        this.state = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the naturalLanguageTranslation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNaturalLanguageTranslation() {
        return naturalLanguageTranslation;
    }

    /**
     * Sets the value of the naturalLanguageTranslation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNaturalLanguageTranslation(String value) {
        this.naturalLanguageTranslation = value;
    }

}
