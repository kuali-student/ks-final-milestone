
package org.kuali.student.wsdl.dictionary;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for lookupConstraint complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="lookupConstraint">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="lookupKey" type="{http://student.kuali.org/wsdl/dictionary}lookupKeyConstraint" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="search" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="searchField" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "lookupConstraint", namespace = "http://student.kuali.org/wsdl/dictionary", propOrder = {
    "lookupKey"
})
public class LookupConstraint {

    protected List<LookupKeyConstraint> lookupKey;
    @XmlAttribute(name = "search")
    protected String search;
    @XmlAttribute(name = "searchField")
    protected String searchField;

    /**
     * Gets the value of the lookupKey property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the lookupKey property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLookupKey().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LookupKeyConstraint }
     * 
     * 
     */
    public List<LookupKeyConstraint> getLookupKey() {
        if (lookupKey == null) {
            lookupKey = new ArrayList<LookupKeyConstraint>();
        }
        return this.lookupKey;
    }

    /**
     * Gets the value of the search property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSearch() {
        return search;
    }

    /**
     * Sets the value of the search property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSearch(String value) {
        this.search = value;
    }

    /**
     * Gets the value of the searchField property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSearchField() {
        return searchField;
    }

    /**
     * Sets the value of the searchField property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSearchField(String value) {
        this.searchField = value;
    }

}
