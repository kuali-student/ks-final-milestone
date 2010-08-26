
package org.kuali.student.wsdl.course;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for reqComponentTypeInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="reqComponentTypeInfo">
 *   &lt;complexContent>
 *     &lt;extension base="{http://student.kuali.org/wsdl/course}typeInfo">
 *       &lt;sequence>
 *         &lt;element name="reqCompFieldTypes" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="reqCompFieldType" type="{http://student.kuali.org/wsdl/course}reqCompFieldTypeInfo" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "reqComponentTypeInfo", propOrder = {
    "reqCompFieldTypes"
})
public class ReqComponentTypeInfo
    extends TypeInfo
{

    protected ReqComponentTypeInfo.ReqCompFieldTypes reqCompFieldTypes;

    /**
     * Gets the value of the reqCompFieldTypes property.
     * 
     * @return
     *     possible object is
     *     {@link ReqComponentTypeInfo.ReqCompFieldTypes }
     *     
     */
    public ReqComponentTypeInfo.ReqCompFieldTypes getReqCompFieldTypes() {
        return reqCompFieldTypes;
    }

    /**
     * Sets the value of the reqCompFieldTypes property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReqComponentTypeInfo.ReqCompFieldTypes }
     *     
     */
    public void setReqCompFieldTypes(ReqComponentTypeInfo.ReqCompFieldTypes value) {
        this.reqCompFieldTypes = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="reqCompFieldType" type="{http://student.kuali.org/wsdl/course}reqCompFieldTypeInfo" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "reqCompFieldType"
    })
    public static class ReqCompFieldTypes {

        protected List<ReqCompFieldTypeInfo> reqCompFieldType;

        /**
         * Gets the value of the reqCompFieldType property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the reqCompFieldType property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getReqCompFieldType().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ReqCompFieldTypeInfo }
         * 
         * 
         */
        public List<ReqCompFieldTypeInfo> getReqCompFieldType() {
            if (reqCompFieldType == null) {
                reqCompFieldType = new ArrayList<ReqCompFieldTypeInfo>();
            }
            return this.reqCompFieldType;
        }

    }

}
