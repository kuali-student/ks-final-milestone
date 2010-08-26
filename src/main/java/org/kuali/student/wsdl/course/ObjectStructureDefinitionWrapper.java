
package org.kuali.student.wsdl.course;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for objectStructureDefinitionWrapper complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="objectStructureDefinitionWrapper">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="rootDefinitionName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="definitions" type="{http://student.kuali.org/wsdl/course}objectStructureDefinition" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "objectStructureDefinitionWrapper", propOrder = {
    "rootDefinitionName",
    "definitions"
})
public class ObjectStructureDefinitionWrapper {

    protected String rootDefinitionName;
    @XmlElement(nillable = true)
    protected List<ObjectStructureDefinition> definitions;

    /**
     * Gets the value of the rootDefinitionName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRootDefinitionName() {
        return rootDefinitionName;
    }

    /**
     * Sets the value of the rootDefinitionName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRootDefinitionName(String value) {
        this.rootDefinitionName = value;
    }

    /**
     * Gets the value of the definitions property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the definitions property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDefinitions().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ObjectStructureDefinition }
     * 
     * 
     */
    public List<ObjectStructureDefinition> getDefinitions() {
        if (definitions == null) {
            definitions = new ArrayList<ObjectStructureDefinition>();
        }
        return this.definitions;
    }

}
