
package org.kuali.student.wsdl.course;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for statementTreeViewInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="statementTreeViewInfo">
 *   &lt;complexContent>
 *     &lt;extension base="{http://student.kuali.org/wsdl/course}abstractStatementInfo">
 *       &lt;sequence>
 *         &lt;element name="statements" type="{http://student.kuali.org/wsdl/course}statementTreeViewInfo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="reqComponents" type="{http://student.kuali.org/wsdl/course}reqComponentInfo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="naturalLanguageTranslation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "statementTreeViewInfo", propOrder = {
    "statements",
    "reqComponents",
    "naturalLanguageTranslation"
})
public class StatementTreeViewInfo
    extends AbstractStatementInfo
{

    protected List<StatementTreeViewInfo> statements;
    protected List<ReqComponentInfo> reqComponents;
    protected String naturalLanguageTranslation;

    /**
     * Gets the value of the statements property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the statements property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStatements().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StatementTreeViewInfo }
     * 
     * 
     */
    public List<StatementTreeViewInfo> getStatements() {
        if (statements == null) {
            statements = new ArrayList<StatementTreeViewInfo>();
        }
        return this.statements;
    }

    /**
     * Gets the value of the reqComponents property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the reqComponents property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReqComponents().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReqComponentInfo }
     * 
     * 
     */
    public List<ReqComponentInfo> getReqComponents() {
        if (reqComponents == null) {
            reqComponents = new ArrayList<ReqComponentInfo>();
        }
        return this.reqComponents;
    }

    /**
     * Gets the value of the naturalLanguageTranslation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNaturalLanguageTranslation() {
        return naturalLanguageTranslation;
    }

    /**
     * Sets the value of the naturalLanguageTranslation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNaturalLanguageTranslation(String value) {
        this.naturalLanguageTranslation = value;
    }

}
