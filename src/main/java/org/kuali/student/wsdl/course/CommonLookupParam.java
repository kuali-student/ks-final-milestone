
package org.kuali.student.wsdl.course;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for commonLookupParam complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="commonLookupParam">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="caseSensitive" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="childLookup" type="{http://student.kuali.org/wsdl/course}commonLookup" minOccurs="0"/>
 *         &lt;element name="dataType" type="{http://student.kuali.org/wsdl/course}dataType" minOccurs="0"/>
 *         &lt;element name="defaultValueList" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="defaultValueString" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="desc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fieldPath" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="key" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="optional" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="usage" type="{http://student.kuali.org/wsdl/course}usage" minOccurs="0"/>
 *         &lt;element name="widget" type="{http://student.kuali.org/wsdl/course}widget" minOccurs="0"/>
 *         &lt;element name="writeAccess" type="{http://student.kuali.org/wsdl/course}writeAccess" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "commonLookupParam", propOrder = {
    "caseSensitive",
    "childLookup",
    "dataType",
    "defaultValueList",
    "defaultValueString",
    "desc",
    "fieldPath",
    "key",
    "name",
    "optional",
    "usage",
    "widget",
    "writeAccess"
})
public class CommonLookupParam {

    protected boolean caseSensitive;
    protected CommonLookup childLookup;
    protected DataType dataType;
    @XmlElement(nillable = true)
    protected List<String> defaultValueList;
    protected String defaultValueString;
    protected String desc;
    protected String fieldPath;
    protected String key;
    protected String name;
    protected boolean optional;
    protected Usage usage;
    protected Widget widget;
    protected WriteAccess writeAccess;

    /**
     * Gets the value of the caseSensitive property.
     * 
     */
    public boolean isCaseSensitive() {
        return caseSensitive;
    }

    /**
     * Sets the value of the caseSensitive property.
     * 
     */
    public void setCaseSensitive(boolean value) {
        this.caseSensitive = value;
    }

    /**
     * Gets the value of the childLookup property.
     * 
     * @return
     *     possible object is
     *     {@link CommonLookup }
     *     
     */
    public CommonLookup getChildLookup() {
        return childLookup;
    }

    /**
     * Sets the value of the childLookup property.
     * 
     * @param value
     *     allowed object is
     *     {@link CommonLookup }
     *     
     */
    public void setChildLookup(CommonLookup value) {
        this.childLookup = value;
    }

    /**
     * Gets the value of the dataType property.
     * 
     * @return
     *     possible object is
     *     {@link DataType }
     *     
     */
    public DataType getDataType() {
        return dataType;
    }

    /**
     * Sets the value of the dataType property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataType }
     *     
     */
    public void setDataType(DataType value) {
        this.dataType = value;
    }

    /**
     * Gets the value of the defaultValueList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the defaultValueList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDefaultValueList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getDefaultValueList() {
        if (defaultValueList == null) {
            defaultValueList = new ArrayList<String>();
        }
        return this.defaultValueList;
    }

    /**
     * Gets the value of the defaultValueString property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDefaultValueString() {
        return defaultValueString;
    }

    /**
     * Sets the value of the defaultValueString property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDefaultValueString(String value) {
        this.defaultValueString = value;
    }

    /**
     * Gets the value of the desc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDesc() {
        return desc;
    }

    /**
     * Sets the value of the desc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDesc(String value) {
        this.desc = value;
    }

    /**
     * Gets the value of the fieldPath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFieldPath() {
        return fieldPath;
    }

    /**
     * Sets the value of the fieldPath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFieldPath(String value) {
        this.fieldPath = value;
    }

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKey(String value) {
        this.key = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the optional property.
     * 
     */
    public boolean isOptional() {
        return optional;
    }

    /**
     * Sets the value of the optional property.
     * 
     */
    public void setOptional(boolean value) {
        this.optional = value;
    }

    /**
     * Gets the value of the usage property.
     * 
     * @return
     *     possible object is
     *     {@link Usage }
     *     
     */
    public Usage getUsage() {
        return usage;
    }

    /**
     * Sets the value of the usage property.
     * 
     * @param value
     *     allowed object is
     *     {@link Usage }
     *     
     */
    public void setUsage(Usage value) {
        this.usage = value;
    }

    /**
     * Gets the value of the widget property.
     * 
     * @return
     *     possible object is
     *     {@link Widget }
     *     
     */
    public Widget getWidget() {
        return widget;
    }

    /**
     * Sets the value of the widget property.
     * 
     * @param value
     *     allowed object is
     *     {@link Widget }
     *     
     */
    public void setWidget(Widget value) {
        this.widget = value;
    }

    /**
     * Gets the value of the writeAccess property.
     * 
     * @return
     *     possible object is
     *     {@link WriteAccess }
     *     
     */
    public WriteAccess getWriteAccess() {
        return writeAccess;
    }

    /**
     * Sets the value of the writeAccess property.
     * 
     * @param value
     *     allowed object is
     *     {@link WriteAccess }
     *     
     */
    public void setWriteAccess(WriteAccess value) {
        this.writeAccess = value;
    }

}
