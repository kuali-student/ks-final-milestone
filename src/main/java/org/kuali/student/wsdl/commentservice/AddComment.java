
package org.kuali.student.wsdl.commentservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import org.kuali.student.wsdl.comment.CommentInfo;


/**
 * <p>Java class for addComment complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="addComment">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="referenceId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="referenceTypeKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "addComment", propOrder = {
    "referenceId",
    "referenceTypeKey",
    "commentInfo"
})
public class AddComment {

    protected String referenceId;
    protected String referenceTypeKey;
    protected CommentInfo commentInfo;

    /**
     * Gets the value of the referenceId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferenceId() {
        return referenceId;
    }

    /**
     * Sets the value of the referenceId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferenceId(String value) {
        this.referenceId = value;
    }

    /**
     * Gets the value of the referenceTypeKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferenceTypeKey() {
        return referenceTypeKey;
    }

    /**
     * Sets the value of the referenceTypeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferenceTypeKey(String value) {
        this.referenceTypeKey = value;
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
