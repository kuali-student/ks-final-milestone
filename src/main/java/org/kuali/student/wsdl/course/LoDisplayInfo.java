
package org.kuali.student.wsdl.course;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for loDisplayInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="loDisplayInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="loInfo" type="{http://student.kuali.org/wsdl/course}loInfo" minOccurs="0"/>
 *         &lt;element name="loDisplayInfoList" type="{http://student.kuali.org/wsdl/course}loDisplayInfo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="parentRelType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="parentLoRelationid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="loCategoryInfoList" type="{http://student.kuali.org/wsdl/course}loCategoryInfo" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "loDisplayInfo", propOrder = {
    "loInfo",
    "loDisplayInfoList",
    "parentRelType",
    "parentLoRelationid",
    "loCategoryInfoList"
})
public class LoDisplayInfo {

    protected LoInfo loInfo;
    protected List<LoDisplayInfo> loDisplayInfoList;
    protected String parentRelType;
    protected String parentLoRelationid;
    protected List<LoCategoryInfo> loCategoryInfoList;

    /**
     * Gets the value of the loInfo property.
     * 
     * @return
     *     possible object is
     *     {@link LoInfo }
     *     
     */
    public LoInfo getLoInfo() {
        return loInfo;
    }

    /**
     * Sets the value of the loInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link LoInfo }
     *     
     */
    public void setLoInfo(LoInfo value) {
        this.loInfo = value;
    }

    /**
     * Gets the value of the loDisplayInfoList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the loDisplayInfoList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLoDisplayInfoList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LoDisplayInfo }
     * 
     * 
     */
    public List<LoDisplayInfo> getLoDisplayInfoList() {
        if (loDisplayInfoList == null) {
            loDisplayInfoList = new ArrayList<LoDisplayInfo>();
        }
        return this.loDisplayInfoList;
    }

    /**
     * Gets the value of the parentRelType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParentRelType() {
        return parentRelType;
    }

    /**
     * Sets the value of the parentRelType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParentRelType(String value) {
        this.parentRelType = value;
    }

    /**
     * Gets the value of the parentLoRelationid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParentLoRelationid() {
        return parentLoRelationid;
    }

    /**
     * Sets the value of the parentLoRelationid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParentLoRelationid(String value) {
        this.parentLoRelationid = value;
    }

    /**
     * Gets the value of the loCategoryInfoList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the loCategoryInfoList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLoCategoryInfoList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LoCategoryInfo }
     * 
     * 
     */
    public List<LoCategoryInfo> getLoCategoryInfoList() {
        if (loCategoryInfoList == null) {
            loCategoryInfoList = new ArrayList<LoCategoryInfo>();
        }
        return this.loCategoryInfoList;
    }

}
