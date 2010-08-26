
package org.kuali.student.wsdl.course;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for createCourseStatement complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="createCourseStatement">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="courseId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="statementTreeViewInfo" type="{http://student.kuali.org/wsdl/course}statementTreeViewInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "createCourseStatement", propOrder = {
    "courseId",
    "statementTreeViewInfo"
})
public class CreateCourseStatement {

    protected String courseId;
    protected StatementTreeViewInfo statementTreeViewInfo;

    /**
     * Gets the value of the courseId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCourseId() {
        return courseId;
    }

    /**
     * Sets the value of the courseId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCourseId(String value) {
        this.courseId = value;
    }

    /**
     * Gets the value of the statementTreeViewInfo property.
     * 
     * @return
     *     possible object is
     *     {@link StatementTreeViewInfo }
     *     
     */
    public StatementTreeViewInfo getStatementTreeViewInfo() {
        return statementTreeViewInfo;
    }

    /**
     * Sets the value of the statementTreeViewInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link StatementTreeViewInfo }
     *     
     */
    public void setStatementTreeViewInfo(StatementTreeViewInfo value) {
        this.statementTreeViewInfo = value;
    }

}
