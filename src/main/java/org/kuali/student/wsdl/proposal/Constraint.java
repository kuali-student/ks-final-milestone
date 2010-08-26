
package org.kuali.student.wsdl.proposal;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for constraint complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="constraint">
 *   &lt;complexContent>
 *     &lt;extension base="{http://student.kuali.org/wsdl/proposal}baseConstraint">
 *       &lt;sequence>
 *         &lt;element name="serverSide" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="customValidatorClass" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="locale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="exclusiveMin" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="inclusiveMax" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="minLength" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="maxLength" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="validChars" type="{http://student.kuali.org/wsdl/proposal}validCharsConstraint" minOccurs="0"/>
 *         &lt;element name="minOccurs" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="maxOccurs" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="caseConstraint" type="{http://student.kuali.org/wsdl/proposal}caseConstraint" minOccurs="0"/>
 *         &lt;element name="requireConstraint" type="{http://student.kuali.org/wsdl/proposal}requiredConstraint" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="occursConstraint" type="{http://student.kuali.org/wsdl/proposal}mustOccurConstraint" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="lookupDefinition" type="{http://student.kuali.org/wsdl/proposal}lookupConstraint" minOccurs="0"/>
 *         &lt;element name="lookupContextPath" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "constraint", propOrder = {
    "serverSide",
    "customValidatorClass",
    "locale",
    "exclusiveMin",
    "inclusiveMax",
    "minLength",
    "maxLength",
    "validChars",
    "minOccurs",
    "maxOccurs",
    "caseConstraint",
    "requireConstraint",
    "occursConstraint",
    "lookupDefinition",
    "lookupContextPath"
})
@XmlSeeAlso({
    FieldDefinition.class
})
public class Constraint
    extends BaseConstraint
{

    protected boolean serverSide;
    protected String customValidatorClass;
    protected String locale;
    protected String exclusiveMin;
    protected String inclusiveMax;
    protected Integer minLength;
    protected String maxLength;
    protected ValidCharsConstraint validChars;
    protected Integer minOccurs;
    protected String maxOccurs;
    protected CaseConstraint caseConstraint;
    protected List<RequiredConstraint> requireConstraint;
    protected List<MustOccurConstraint> occursConstraint;
    protected LookupConstraint lookupDefinition;
    protected String lookupContextPath;

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
     * Gets the value of the customValidatorClass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomValidatorClass() {
        return customValidatorClass;
    }

    /**
     * Sets the value of the customValidatorClass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomValidatorClass(String value) {
        this.customValidatorClass = value;
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

    /**
     * Gets the value of the exclusiveMin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExclusiveMin() {
        return exclusiveMin;
    }

    /**
     * Sets the value of the exclusiveMin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExclusiveMin(String value) {
        this.exclusiveMin = value;
    }

    /**
     * Gets the value of the inclusiveMax property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInclusiveMax() {
        return inclusiveMax;
    }

    /**
     * Sets the value of the inclusiveMax property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInclusiveMax(String value) {
        this.inclusiveMax = value;
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
     * Gets the value of the caseConstraint property.
     * 
     * @return
     *     possible object is
     *     {@link CaseConstraint }
     *     
     */
    public CaseConstraint getCaseConstraint() {
        return caseConstraint;
    }

    /**
     * Sets the value of the caseConstraint property.
     * 
     * @param value
     *     allowed object is
     *     {@link CaseConstraint }
     *     
     */
    public void setCaseConstraint(CaseConstraint value) {
        this.caseConstraint = value;
    }

    /**
     * Gets the value of the requireConstraint property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the requireConstraint property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRequireConstraint().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RequiredConstraint }
     * 
     * 
     */
    public List<RequiredConstraint> getRequireConstraint() {
        if (requireConstraint == null) {
            requireConstraint = new ArrayList<RequiredConstraint>();
        }
        return this.requireConstraint;
    }

    /**
     * Gets the value of the occursConstraint property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the occursConstraint property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOccursConstraint().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MustOccurConstraint }
     * 
     * 
     */
    public List<MustOccurConstraint> getOccursConstraint() {
        if (occursConstraint == null) {
            occursConstraint = new ArrayList<MustOccurConstraint>();
        }
        return this.occursConstraint;
    }

    /**
     * Gets the value of the lookupDefinition property.
     * 
     * @return
     *     possible object is
     *     {@link LookupConstraint }
     *     
     */
    public LookupConstraint getLookupDefinition() {
        return lookupDefinition;
    }

    /**
     * Sets the value of the lookupDefinition property.
     * 
     * @param value
     *     allowed object is
     *     {@link LookupConstraint }
     *     
     */
    public void setLookupDefinition(LookupConstraint value) {
        this.lookupDefinition = value;
    }

    /**
     * Gets the value of the lookupContextPath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLookupContextPath() {
        return lookupContextPath;
    }

    /**
     * Sets the value of the lookupContextPath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLookupContextPath(String value) {
        this.lookupContextPath = value;
    }

}
