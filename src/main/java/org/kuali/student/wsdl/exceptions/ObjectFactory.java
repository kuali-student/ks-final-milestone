
package org.kuali.student.wsdl.exceptions;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.kuali.student.wsdl.exceptions package. 
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

    private final static QName _CircularRelationshipException_QNAME = new QName("http://student.kuali.org/wsdl/exceptions", "CircularRelationshipException");
    private final static QName _DataValidationErrorException_QNAME = new QName("http://student.kuali.org/wsdl/exceptions", "DataValidationErrorException");
    private final static QName _AlreadyExistsException_QNAME = new QName("http://student.kuali.org/wsdl/exceptions", "AlreadyExistsException");
    private final static QName _VersionMismatchException_QNAME = new QName("http://student.kuali.org/wsdl/exceptions", "VersionMismatchException");
    private final static QName _OperationFailedException_QNAME = new QName("http://student.kuali.org/wsdl/exceptions", "OperationFailedException");
    private final static QName _PermissionDeniedException_QNAME = new QName("http://student.kuali.org/wsdl/exceptions", "PermissionDeniedException");
    private final static QName _UnsupportedActionException_QNAME = new QName("http://student.kuali.org/wsdl/exceptions", "UnsupportedActionException");
    private final static QName _DependentObjectsExistException_QNAME = new QName("http://student.kuali.org/wsdl/exceptions", "DependentObjectsExistException");
    private final static QName _InvalidParameterException_QNAME = new QName("http://student.kuali.org/wsdl/exceptions", "InvalidParameterException");
    private final static QName _MissingParameterException_QNAME = new QName("http://student.kuali.org/wsdl/exceptions", "MissingParameterException");
    private final static QName _DoesNotExistException_QNAME = new QName("http://student.kuali.org/wsdl/exceptions", "DoesNotExistException");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.kuali.student.wsdl.exceptions
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PermissionDeniedException }
     * 
     */
    public PermissionDeniedException createPermissionDeniedException() {
        return new PermissionDeniedException();
    }

    /**
     * Create an instance of {@link DependentObjectsExistException }
     * 
     */
    public DependentObjectsExistException createDependentObjectsExistException() {
        return new DependentObjectsExistException();
    }

    /**
     * Create an instance of {@link DoesNotExistException }
     * 
     */
    public DoesNotExistException createDoesNotExistException() {
        return new DoesNotExistException();
    }

    /**
     * Create an instance of {@link AlreadyExistsException }
     * 
     */
    public AlreadyExistsException createAlreadyExistsException() {
        return new AlreadyExistsException();
    }

    /**
     * Create an instance of {@link UnsupportedActionException }
     * 
     */
    public UnsupportedActionException createUnsupportedActionException() {
        return new UnsupportedActionException();
    }

    /**
     * Create an instance of {@link CircularRelationshipException }
     * 
     */
    public CircularRelationshipException createCircularRelationshipException() {
        return new CircularRelationshipException();
    }

    /**
     * Create an instance of {@link InvalidParameterException }
     * 
     */
    public InvalidParameterException createInvalidParameterException() {
        return new InvalidParameterException();
    }

    /**
     * Create an instance of {@link MissingParameterException }
     * 
     */
    public MissingParameterException createMissingParameterException() {
        return new MissingParameterException();
    }

    /**
     * Create an instance of {@link DataValidationErrorException }
     * 
     */
    public DataValidationErrorException createDataValidationErrorException() {
        return new DataValidationErrorException();
    }

    /**
     * Create an instance of {@link VersionMismatchException }
     * 
     */
    public VersionMismatchException createVersionMismatchException() {
        return new VersionMismatchException();
    }

    /**
     * Create an instance of {@link OperationFailedException }
     * 
     */
    public OperationFailedException createOperationFailedException() {
        return new OperationFailedException();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CircularRelationshipException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/exceptions", name = "CircularRelationshipException")
    public JAXBElement<CircularRelationshipException> createCircularRelationshipException(CircularRelationshipException value) {
        return new JAXBElement<CircularRelationshipException>(_CircularRelationshipException_QNAME, CircularRelationshipException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DataValidationErrorException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/exceptions", name = "DataValidationErrorException")
    public JAXBElement<DataValidationErrorException> createDataValidationErrorException(DataValidationErrorException value) {
        return new JAXBElement<DataValidationErrorException>(_DataValidationErrorException_QNAME, DataValidationErrorException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AlreadyExistsException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/exceptions", name = "AlreadyExistsException")
    public JAXBElement<AlreadyExistsException> createAlreadyExistsException(AlreadyExistsException value) {
        return new JAXBElement<AlreadyExistsException>(_AlreadyExistsException_QNAME, AlreadyExistsException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VersionMismatchException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/exceptions", name = "VersionMismatchException")
    public JAXBElement<VersionMismatchException> createVersionMismatchException(VersionMismatchException value) {
        return new JAXBElement<VersionMismatchException>(_VersionMismatchException_QNAME, VersionMismatchException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OperationFailedException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/exceptions", name = "OperationFailedException")
    public JAXBElement<OperationFailedException> createOperationFailedException(OperationFailedException value) {
        return new JAXBElement<OperationFailedException>(_OperationFailedException_QNAME, OperationFailedException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PermissionDeniedException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/exceptions", name = "PermissionDeniedException")
    public JAXBElement<PermissionDeniedException> createPermissionDeniedException(PermissionDeniedException value) {
        return new JAXBElement<PermissionDeniedException>(_PermissionDeniedException_QNAME, PermissionDeniedException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UnsupportedActionException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/exceptions", name = "UnsupportedActionException")
    public JAXBElement<UnsupportedActionException> createUnsupportedActionException(UnsupportedActionException value) {
        return new JAXBElement<UnsupportedActionException>(_UnsupportedActionException_QNAME, UnsupportedActionException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DependentObjectsExistException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/exceptions", name = "DependentObjectsExistException")
    public JAXBElement<DependentObjectsExistException> createDependentObjectsExistException(DependentObjectsExistException value) {
        return new JAXBElement<DependentObjectsExistException>(_DependentObjectsExistException_QNAME, DependentObjectsExistException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InvalidParameterException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/exceptions", name = "InvalidParameterException")
    public JAXBElement<InvalidParameterException> createInvalidParameterException(InvalidParameterException value) {
        return new JAXBElement<InvalidParameterException>(_InvalidParameterException_QNAME, InvalidParameterException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MissingParameterException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/exceptions", name = "MissingParameterException")
    public JAXBElement<MissingParameterException> createMissingParameterException(MissingParameterException value) {
        return new JAXBElement<MissingParameterException>(_MissingParameterException_QNAME, MissingParameterException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DoesNotExistException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/exceptions", name = "DoesNotExistException")
    public JAXBElement<DoesNotExistException> createDoesNotExistException(DoesNotExistException value) {
        return new JAXBElement<DoesNotExistException>(_DoesNotExistException_QNAME, DoesNotExistException.class, null, value);
    }

}
