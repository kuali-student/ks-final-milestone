
package org.kuali.student.wsdl.search;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for searchTypeInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="searchTypeInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="desc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="searchResultTypeInfo" type="{http://student.kuali.org/wsdl/search}searchResultTypeInfo" minOccurs="0"/>
 *         &lt;element name="searchCriteriaTypeInfo" type="{http://student.kuali.org/wsdl/search}searchCriteriaTypeInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="key" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "searchTypeInfo", propOrder = {
    "name",
    "desc",
    "searchResultTypeInfo",
    "searchCriteriaTypeInfo"
})
public class SearchTypeInfo {

    protected String name;
    protected String desc;
    protected SearchResultTypeInfo searchResultTypeInfo;
    protected SearchCriteriaTypeInfo searchCriteriaTypeInfo;
    @XmlAttribute
    protected String key;

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
     * Gets the value of the searchResultTypeInfo property.
     * 
     * @return
     *     possible object is
     *     {@link SearchResultTypeInfo }
     *     
     */
    public SearchResultTypeInfo getSearchResultTypeInfo() {
        return searchResultTypeInfo;
    }

    /**
     * Sets the value of the searchResultTypeInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link SearchResultTypeInfo }
     *     
     */
    public void setSearchResultTypeInfo(SearchResultTypeInfo value) {
        this.searchResultTypeInfo = value;
    }

    /**
     * Gets the value of the searchCriteriaTypeInfo property.
     * 
     * @return
     *     possible object is
     *     {@link SearchCriteriaTypeInfo }
     *     
     */
    public SearchCriteriaTypeInfo getSearchCriteriaTypeInfo() {
        return searchCriteriaTypeInfo;
    }

    /**
     * Sets the value of the searchCriteriaTypeInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link SearchCriteriaTypeInfo }
     *     
     */
    public void setSearchCriteriaTypeInfo(SearchCriteriaTypeInfo value) {
        this.searchCriteriaTypeInfo = value;
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

}
