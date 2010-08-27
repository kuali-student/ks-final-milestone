
package org.kuali.student.security.policy.dto;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.kuali.student.security.policy.dto package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _All_QNAME = new QName("http://schemas.xmlsoap.org/ws/2004/09/policy", "All");
    private final static QName _ExactlyOne_QNAME = new QName("http://schemas.xmlsoap.org/ws/2004/09/policy", "ExactlyOne");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.kuali.student.security.policy.dto
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PolicyReference }
     * 
     */
    public PolicyReference createPolicyReference() {
        return new PolicyReference();
    }

    /**
     * Create an instance of {@link Policy }
     * 
     */
    public Policy createPolicy() {
        return new Policy();
    }

    /**
     * Create an instance of {@link AppliesTo }
     * 
     */
    public AppliesTo createAppliesTo() {
        return new AppliesTo();
    }

    /**
     * Create an instance of {@link OperatorContentType }
     * 
     */
    public OperatorContentType createOperatorContentType() {
        return new OperatorContentType();
    }

    /**
     * Create an instance of {@link PolicyAttachment }
     * 
     */
    public PolicyAttachment createPolicyAttachment() {
        return new PolicyAttachment();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OperatorContentType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2004/09/policy", name = "All")
    public JAXBElement<OperatorContentType> createAll(OperatorContentType value) {
        return new JAXBElement<OperatorContentType>(_All_QNAME, OperatorContentType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OperatorContentType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2004/09/policy", name = "ExactlyOne")
    public JAXBElement<OperatorContentType> createExactlyOne(OperatorContentType value) {
        return new JAXBElement<OperatorContentType>(_ExactlyOne_QNAME, OperatorContentType.class, null, value);
    }

}
