
package org.kuali.student.wsdl.dictionary;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for typeStateCaseConstraint complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="typeStateCaseConstraint">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="typeStateWhen" type="{http://student.kuali.org/wsdl/dictionary}typeStateWhenConstraint" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "typeStateCaseConstraint", propOrder = {
    "typeStateWhen"
})
public class TypeStateCaseConstraint {

    protected List<TypeStateWhenConstraint> typeStateWhen;

    /**
     * Gets the value of the typeStateWhen property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the typeStateWhen property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTypeStateWhen().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TypeStateWhenConstraint }
     * 
     * 
     */
    public List<TypeStateWhenConstraint> getTypeStateWhen() {
        if (typeStateWhen == null) {
            typeStateWhen = new ArrayList<TypeStateWhenConstraint>();
        }
        return this.typeStateWhen;
    }

}
