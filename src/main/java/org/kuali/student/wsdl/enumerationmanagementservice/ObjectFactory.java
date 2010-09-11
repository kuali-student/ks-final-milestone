
package org.kuali.student.wsdl.enumerationmanagementservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.kuali.student.wsdl.enumerationmanagementservice package. 
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

    private final static QName _RemoveEnumeratedValue_QNAME = new QName("http://student.kuali.org/wsdl/EnumerationManagementService", "removeEnumeratedValue");
    private final static QName _UpdateEnumeratedValueResponse_QNAME = new QName("http://student.kuali.org/wsdl/EnumerationManagementService", "updateEnumeratedValueResponse");
    private final static QName _GetEnumeration_QNAME = new QName("http://student.kuali.org/wsdl/EnumerationManagementService", "getEnumeration");
    private final static QName _AddEnumeratedValue_QNAME = new QName("http://student.kuali.org/wsdl/EnumerationManagementService", "addEnumeratedValue");
    private final static QName _UpdateEnumeratedValue_QNAME = new QName("http://student.kuali.org/wsdl/EnumerationManagementService", "updateEnumeratedValue");
    private final static QName _GetEnumerationMetasResponse_QNAME = new QName("http://student.kuali.org/wsdl/EnumerationManagementService", "getEnumerationMetasResponse");
    private final static QName _GetEnumerationMetaResponse_QNAME = new QName("http://student.kuali.org/wsdl/EnumerationManagementService", "getEnumerationMetaResponse");
    private final static QName _GetEnumerationResponse_QNAME = new QName("http://student.kuali.org/wsdl/EnumerationManagementService", "getEnumerationResponse");
    private final static QName _GetEnumerationMetas_QNAME = new QName("http://student.kuali.org/wsdl/EnumerationManagementService", "getEnumerationMetas");
    private final static QName _RemoveEnumeratedValueResponse_QNAME = new QName("http://student.kuali.org/wsdl/EnumerationManagementService", "removeEnumeratedValueResponse");
    private final static QName _GetEnumerationMeta_QNAME = new QName("http://student.kuali.org/wsdl/EnumerationManagementService", "getEnumerationMeta");
    private final static QName _AddEnumeratedValueResponse_QNAME = new QName("http://student.kuali.org/wsdl/EnumerationManagementService", "addEnumeratedValueResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.kuali.student.wsdl.enumerationmanagementservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetEnumerationMetasResponse }
     * 
     */
    public GetEnumerationMetasResponse createGetEnumerationMetasResponse() {
        return new GetEnumerationMetasResponse();
    }

    /**
     * Create an instance of {@link UpdateEnumeratedValue }
     * 
     */
    public UpdateEnumeratedValue createUpdateEnumeratedValue() {
        return new UpdateEnumeratedValue();
    }

    /**
     * Create an instance of {@link UpdateEnumeratedValueResponse }
     * 
     */
    public UpdateEnumeratedValueResponse createUpdateEnumeratedValueResponse() {
        return new UpdateEnumeratedValueResponse();
    }

    /**
     * Create an instance of {@link AddEnumeratedValue }
     * 
     */
    public AddEnumeratedValue createAddEnumeratedValue() {
        return new AddEnumeratedValue();
    }

    /**
     * Create an instance of {@link GetEnumerationResponse }
     * 
     */
    public GetEnumerationResponse createGetEnumerationResponse() {
        return new GetEnumerationResponse();
    }

    /**
     * Create an instance of {@link GetEnumeration }
     * 
     */
    public GetEnumeration createGetEnumeration() {
        return new GetEnumeration();
    }

    /**
     * Create an instance of {@link GetEnumerationMeta }
     * 
     */
    public GetEnumerationMeta createGetEnumerationMeta() {
        return new GetEnumerationMeta();
    }

    /**
     * Create an instance of {@link GetEnumerationMetaResponse }
     * 
     */
    public GetEnumerationMetaResponse createGetEnumerationMetaResponse() {
        return new GetEnumerationMetaResponse();
    }

    /**
     * Create an instance of {@link AddEnumeratedValueResponse }
     * 
     */
    public AddEnumeratedValueResponse createAddEnumeratedValueResponse() {
        return new AddEnumeratedValueResponse();
    }

    /**
     * Create an instance of {@link RemoveEnumeratedValue }
     * 
     */
    public RemoveEnumeratedValue createRemoveEnumeratedValue() {
        return new RemoveEnumeratedValue();
    }

    /**
     * Create an instance of {@link GetEnumerationMetas }
     * 
     */
    public GetEnumerationMetas createGetEnumerationMetas() {
        return new GetEnumerationMetas();
    }

    /**
     * Create an instance of {@link RemoveEnumeratedValueResponse }
     * 
     */
    public RemoveEnumeratedValueResponse createRemoveEnumeratedValueResponse() {
        return new RemoveEnumeratedValueResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveEnumeratedValue }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/EnumerationManagementService", name = "removeEnumeratedValue")
    public JAXBElement<RemoveEnumeratedValue> createRemoveEnumeratedValue(RemoveEnumeratedValue value) {
        return new JAXBElement<RemoveEnumeratedValue>(_RemoveEnumeratedValue_QNAME, RemoveEnumeratedValue.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateEnumeratedValueResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/EnumerationManagementService", name = "updateEnumeratedValueResponse")
    public JAXBElement<UpdateEnumeratedValueResponse> createUpdateEnumeratedValueResponse(UpdateEnumeratedValueResponse value) {
        return new JAXBElement<UpdateEnumeratedValueResponse>(_UpdateEnumeratedValueResponse_QNAME, UpdateEnumeratedValueResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetEnumeration }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/EnumerationManagementService", name = "getEnumeration")
    public JAXBElement<GetEnumeration> createGetEnumeration(GetEnumeration value) {
        return new JAXBElement<GetEnumeration>(_GetEnumeration_QNAME, GetEnumeration.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddEnumeratedValue }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/EnumerationManagementService", name = "addEnumeratedValue")
    public JAXBElement<AddEnumeratedValue> createAddEnumeratedValue(AddEnumeratedValue value) {
        return new JAXBElement<AddEnumeratedValue>(_AddEnumeratedValue_QNAME, AddEnumeratedValue.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateEnumeratedValue }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/EnumerationManagementService", name = "updateEnumeratedValue")
    public JAXBElement<UpdateEnumeratedValue> createUpdateEnumeratedValue(UpdateEnumeratedValue value) {
        return new JAXBElement<UpdateEnumeratedValue>(_UpdateEnumeratedValue_QNAME, UpdateEnumeratedValue.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetEnumerationMetasResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/EnumerationManagementService", name = "getEnumerationMetasResponse")
    public JAXBElement<GetEnumerationMetasResponse> createGetEnumerationMetasResponse(GetEnumerationMetasResponse value) {
        return new JAXBElement<GetEnumerationMetasResponse>(_GetEnumerationMetasResponse_QNAME, GetEnumerationMetasResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetEnumerationMetaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/EnumerationManagementService", name = "getEnumerationMetaResponse")
    public JAXBElement<GetEnumerationMetaResponse> createGetEnumerationMetaResponse(GetEnumerationMetaResponse value) {
        return new JAXBElement<GetEnumerationMetaResponse>(_GetEnumerationMetaResponse_QNAME, GetEnumerationMetaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetEnumerationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/EnumerationManagementService", name = "getEnumerationResponse")
    public JAXBElement<GetEnumerationResponse> createGetEnumerationResponse(GetEnumerationResponse value) {
        return new JAXBElement<GetEnumerationResponse>(_GetEnumerationResponse_QNAME, GetEnumerationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetEnumerationMetas }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/EnumerationManagementService", name = "getEnumerationMetas")
    public JAXBElement<GetEnumerationMetas> createGetEnumerationMetas(GetEnumerationMetas value) {
        return new JAXBElement<GetEnumerationMetas>(_GetEnumerationMetas_QNAME, GetEnumerationMetas.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveEnumeratedValueResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/EnumerationManagementService", name = "removeEnumeratedValueResponse")
    public JAXBElement<RemoveEnumeratedValueResponse> createRemoveEnumeratedValueResponse(RemoveEnumeratedValueResponse value) {
        return new JAXBElement<RemoveEnumeratedValueResponse>(_RemoveEnumeratedValueResponse_QNAME, RemoveEnumeratedValueResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetEnumerationMeta }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/EnumerationManagementService", name = "getEnumerationMeta")
    public JAXBElement<GetEnumerationMeta> createGetEnumerationMeta(GetEnumerationMeta value) {
        return new JAXBElement<GetEnumerationMeta>(_GetEnumerationMeta_QNAME, GetEnumerationMeta.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddEnumeratedValueResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/EnumerationManagementService", name = "addEnumeratedValueResponse")
    public JAXBElement<AddEnumeratedValueResponse> createAddEnumeratedValueResponse(AddEnumeratedValueResponse value) {
        return new JAXBElement<AddEnumeratedValueResponse>(_AddEnumeratedValueResponse_QNAME, AddEnumeratedValueResponse.class, null, value);
    }

}
