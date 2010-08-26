
package org.kuali.student.wsdl.commentservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import org.kuali.student.wsdl.comment.CommentInfo;


/**
 * <p>Java class for validateComment complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="validateComment">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="validationType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="commentInfo" type="{http://student.kuali.org/wsdl/comment}commentInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "validateComment", propOrder = {
    "validationType",
    "commentInfo"
})
public class ValidateComment {

    protected String validationType;
    protected CommentInfo commentInfo;

    /**
     * Gets the value of the validationType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValidationType() {
        return validationType;
    }

    /**
     * Sets the value of the validationType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValidationType(String value) {
        this.validationType = value;
    }

    /**
     * Gets the value of the commentInfo property.
     * 
     * @return
     *     possible object is
     *     {@link CommentInfo }
     *     
     */
    public CommentInfo getCommentInfo() {
        return commentInfo;
    }

    /**
     * Sets the value of the commentInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link CommentInfo }
     *     
     */
    public void setCommentInfo(CommentInfo value) {
        this.commentInfo = value;
    }

}
