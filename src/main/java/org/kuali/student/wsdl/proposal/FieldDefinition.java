
package org.kuali.student.wsdl.proposal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for fieldDefinition complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="fieldDefinition">
 *   &lt;complexContent>
 *     &lt;extension base="{http://student.kuali.org/wsdl/proposal}constraint">
 *       &lt;sequence>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataType" type="{http://student.kuali.org/wsdl/proposal}dataType" minOccurs="0"/>
 *         &lt;element name="dataObjectStructure" type="{http://www.w3.org/2001/XMLSchema}IDREF" minOccurs="0"/>
 *         &lt;element name="dynamic" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="defaultValue" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *         &lt;element name="defaultValuePath" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="readOnly" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="hide" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="mask" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="partialMask" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="partialMaskFormatter" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="maskFormatter" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fieldDefinition", propOrder = {
    "name",
    "dataType",
    "dataObjectStructure",
    "dynamic",
    "defaultValue",
    "defaultValuePath",
    "readOnly",
    "hide",
    "mask",
    "partialMask",
    "partialMaskFormatter",
    "maskFormatter"
})
public class FieldDefinition
    extends Constraint
{

    protected String name;
    protected DataType dataType;
    @XmlIDREF
    @XmlSchemaType(name = "IDREF")
    protected Object dataObjectStructure;
    protected boolean dynamic;
    protected Object defaultValue;
    protected String defaultValuePath;
    protected boolean readOnly;
    protected boolean hide;
    protected boolean mask;
    protected boolean partialMask;
    protected String partialMaskFormatter;
    protected String maskFormatter;

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
     * Gets the value of the dataObjectStructure property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getDataObjectStructure() {
        return dataObjectStructure;
    }

    /**
     * Sets the value of the dataObjectStructure property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setDataObjectStructure(Object value) {
        this.dataObjectStructure = value;
    }

    /**
     * Gets the value of the dynamic property.
     * 
     */
    public boolean isDynamic() {
        return dynamic;
    }

    /**
     * Sets the value of the dynamic property.
     * 
     */
    public void setDynamic(boolean value) {
        this.dynamic = value;
    }

    /**
     * Gets the value of the defaultValue property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getDefaultValue() {
        return defaultValue;
    }

    /**
     * Sets the value of the defaultValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setDefaultValue(Object value) {
        this.defaultValue = value;
    }

    /**
     * Gets the value of the defaultValuePath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDefaultValuePath() {
        return defaultValuePath;
    }

    /**
     * Sets the value of the defaultValuePath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDefaultValuePath(String value) {
        this.defaultValuePath = value;
    }

    /**
     * Gets the value of the readOnly property.
     * 
     */
    public boolean isReadOnly() {
        return readOnly;
    }

    /**
     * Sets the value of the readOnly property.
     * 
     */
    public void setReadOnly(boolean value) {
        this.readOnly = value;
    }

    /**
     * Gets the value of the hide property.
     * 
     */
    public boolean isHide() {
        return hide;
    }

    /**
     * Sets the value of the hide property.
     * 
     */
    public void setHide(boolean value) {
        this.hide = value;
    }

    /**
     * Gets the value of the mask property.
     * 
     */
    public boolean isMask() {
        return mask;
    }

    /**
     * Sets the value of the mask property.
     * 
     */
    public void setMask(boolean value) {
        this.mask = value;
    }

    /**
     * Gets the value of the partialMask property.
     * 
     */
    public boolean isPartialMask() {
        return partialMask;
    }

    /**
     * Sets the value of the partialMask property.
     * 
     */
    public void setPartialMask(boolean value) {
        this.partialMask = value;
    }

    /**
     * Gets the value of the partialMaskFormatter property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartialMaskFormatter() {
        return partialMaskFormatter;
    }

    /**
     * Sets the value of the partialMaskFormatter property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartialMaskFormatter(String value) {
        this.partialMaskFormatter = value;
    }

    /**
     * Gets the value of the maskFormatter property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaskFormatter() {
        return maskFormatter;
    }

    /**
     * Sets the value of the maskFormatter property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaskFormatter(String value) {
        this.maskFormatter = value;
    }

}
