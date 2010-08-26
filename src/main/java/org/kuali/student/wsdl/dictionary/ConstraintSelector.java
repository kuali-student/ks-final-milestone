
package org.kuali.student.wsdl.dictionary;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for constraintSelector complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="constraintSelector">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="minValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="maxValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="minLength" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="maxLength" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="validChars" type="{http://student.kuali.org/wsdl/dictionary}validCharsConstraint" minOccurs="0"/>
 *         &lt;element name="minOccurs" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="maxOccurs" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="require" type="{http://student.kuali.org/wsdl/dictionary}requireConstraint" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="case" type="{http://student.kuali.org/wsdl/dictionary}caseConstraint" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="typeStateCase" type="{http://student.kuali.org/wsdl/dictionary}typeStateCaseConstraint" minOccurs="0"/>
 *         &lt;element name="lookup" type="{http://student.kuali.org/wsdl/dictionary}lookupConstraint" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="occurs" type="{http://student.kuali.org/wsdl/dictionary}occursConstraint" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="className" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="key" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="serverSide" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="locale" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "constraintSelector", propOrder = {
    "minValue",
    "maxValue",
    "minLength",
    "maxLength",
    "validChars",
    "minOccurs",
    "maxOccurs",
    "require",
    "_case",
    "typeStateCase",
    "lookup",
    "occurs"
})
public class ConstraintSelector {

    protected String minValue;
    protected String maxValue;
    protected Integer minLength;
    protected String maxLength;
    protected ValidCharsConstraint validChars;
    protected Integer minOccurs;
    protected String maxOccurs;
    protected List<RequireConstraint> require;
    @XmlElement(name = "case")
    protected List<CaseConstraint> _case;
    protected TypeStateCaseConstraint typeStateCase;
    protected List<LookupConstraint> lookup;
    protected List<OccursConstraint> occurs;
    @XmlAttribute
    protected String className;
    @XmlAttribute(required = true)
    protected String key;
    @XmlAttribute
    protected String id;
    @XmlAttribute(required = true)
    protected boolean serverSide;
    @XmlAttribute
    protected String locale;

    /**
     * Gets the value of the minValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMinValue() {
        return minValue;
    }

    /**
     * Sets the value of the minValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMinValue(String value) {
        this.minValue = value;
    }

    /**
     * Gets the value of the maxValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaxValue() {
        return maxValue;
    }

    /**
     * Sets the value of the maxValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaxValue(String value) {
        this.maxValue = value;
    }

    /**
     * Gets the value of the minLength property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMinLength() {
        return minLength;
    }

    /**
     * Sets the value of the minLength property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMinLength(Integer value) {
        this.minLength = value;
    }

    /**
     * Gets the value of the maxLength property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaxLength() {
        return maxLength;
    }

    /**
     * Sets the value of the maxLength property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaxLength(String value) {
        this.maxLength = value;
    }

    /**
     * Gets the value of the validChars property.
     * 
     * @return
     *     possible object is
     *     {@link ValidCharsConstraint }
     *     
     */
    public ValidCharsConstraint getValidChars() {
        return validChars;
    }

    /**
     * Sets the value of the validChars property.
     * 
     * @param value
     *     allowed object is
     *     {@link ValidCharsConstraint }
     *     
     */
    public void setValidChars(ValidCharsConstraint value) {
        this.validChars = value;
    }

    /**
     * Gets the value of the minOccurs property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMinOccurs() {
        return minOccurs;
    }

    /**
     * Sets the value of the minOccurs property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMinOccurs(Integer value) {
        this.minOccurs = value;
    }

    /**
     * Gets the value of the maxOccurs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaxOccurs() {
        return maxOccurs;
    }

    /**
     * Sets the value of the maxOccurs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaxOccurs(String value) {
        this.maxOccurs = value;
    }

    /**
     * Gets the value of the require property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the require property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRequire().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RequireConstraint }
     * 
     * 
     */
    public List<RequireConstraint> getRequire() {
        if (require == null) {
            require = new ArrayList<RequireConstraint>();
        }
        return this.require;
    }

    /**
     * Gets the value of the case property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the case property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCase().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CaseConstraint }
     * 
     * 
     */
    public List<CaseConstraint> getCase() {
        if (_case == null) {
            _case = new ArrayList<CaseConstraint>();
        }
        return this._case;
    }

    /**
     * Gets the value of the typeStateCase property.
     * 
     * @return
     *     possible object is
     *     {@link TypeStateCaseConstraint }
     *     
     */
    public TypeStateCaseConstraint getTypeStateCase() {
        return typeStateCase;
    }

    /**
     * Sets the value of the typeStateCase property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeStateCaseConstraint }
     *     
     */
    public void setTypeStateCase(TypeStateCaseConstraint value) {
        this.typeStateCase = value;
    }

    /**
     * Gets the value of the lookup property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the lookup property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLookup().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LookupConstraint }
     * 
     * 
     */
    public List<LookupConstraint> getLookup() {
        if (lookup == null) {
            lookup = new ArrayList<LookupConstraint>();
        }
        return this.lookup;
    }

    /**
     * Gets the value of the occurs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the occurs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOccurs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OccursConstraint }
     * 
     * 
     */
    public List<OccursConstraint> getOccurs() {
        if (occurs == null) {
            occurs = new ArrayList<OccursConstraint>();
        }
        return this.occurs;
    }

    /**
     * Gets the value of the className property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClassName() {
        return className;
    }

    /**
     * Sets the value of the className property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClassName(String value) {
        this.className = value;
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
     * Gets the value of the serverSide property.
     * 
     */
    public boolean isServerSide() {
        return serverSide;
    }

    /**
     * Sets the value of the serverSide property.
     * 
     */
    public void setServerSide(boolean value) {
        this.serverSide = value;
    }

    /**
     * Gets the value of the locale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocale() {
        return locale;
    }

    /**
     * Sets the value of the locale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocale(String value) {
        this.locale = value;
    }

}
