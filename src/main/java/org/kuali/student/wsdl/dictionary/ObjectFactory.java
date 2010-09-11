
package org.kuali.student.wsdl.dictionary;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.kuali.student.wsdl.dictionary package. 
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

    private final static QName _GetObjectStructureResponse_QNAME = new QName("http://student.kuali.org/wsdl/dictionary", "getObjectStructureResponse");
    private final static QName _GetObjectTypesResponse_QNAME = new QName("http://student.kuali.org/wsdl/dictionary", "getObjectTypesResponse");
    private final static QName _GetObjectStructure_QNAME = new QName("http://student.kuali.org/wsdl/dictionary", "getObjectStructure");
    private final static QName _GetObjectTypes_QNAME = new QName("http://student.kuali.org/wsdl/dictionary", "getObjectTypes");
    private final static QName _FieldDescriptor_QNAME = new QName("http://student.kuali.org/wsdl/dictionary", "fieldDescriptor");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.kuali.student.wsdl.dictionary
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link LookupConstraint }
     * 
     */
    public LookupConstraint createLookupConstraint() {
        return new LookupConstraint();
    }

    /**
     * Create an instance of {@link ValidCharsConstraint }
     * 
     */
    public ValidCharsConstraint createValidCharsConstraint() {
        return new ValidCharsConstraint();
    }

    /**
     * Create an instance of {@link ObjectStructure }
     * 
     */
    public ObjectStructure createObjectStructure() {
        return new ObjectStructure();
    }

    /**
     * Create an instance of {@link GetObjectTypes }
     * 
     */
    public GetObjectTypes createGetObjectTypes() {
        return new GetObjectTypes();
    }

    /**
     * Create an instance of {@link FieldDescriptor }
     * 
     */
    public FieldDescriptor createFieldDescriptor() {
        return new FieldDescriptor();
    }

    /**
     * Create an instance of {@link ConstraintDescriptor }
     * 
     */
    public ConstraintDescriptor createConstraintDescriptor() {
        return new ConstraintDescriptor();
    }

    /**
     * Create an instance of {@link State }
     * 
     */
    public State createState() {
        return new State();
    }

    /**
     * Create an instance of {@link WhenConstraint }
     * 
     */
    public WhenConstraint createWhenConstraint() {
        return new WhenConstraint();
    }

    /**
     * Create an instance of {@link SearchSelector }
     * 
     */
    public SearchSelector createSearchSelector() {
        return new SearchSelector();
    }

    /**
     * Create an instance of {@link LookupKeyConstraint }
     * 
     */
    public LookupKeyConstraint createLookupKeyConstraint() {
        return new LookupKeyConstraint();
    }

    /**
     * Create an instance of {@link Field }
     * 
     */
    public Field createField() {
        return new Field();
    }

    /**
     * Create an instance of {@link TypeStateCaseConstraint }
     * 
     */
    public TypeStateCaseConstraint createTypeStateCaseConstraint() {
        return new TypeStateCaseConstraint();
    }

    /**
     * Create an instance of {@link OccursConstraint }
     * 
     */
    public OccursConstraint createOccursConstraint() {
        return new OccursConstraint();
    }

    /**
     * Create an instance of {@link GetObjectStructureResponse }
     * 
     */
    public GetObjectStructureResponse createGetObjectStructureResponse() {
        return new GetObjectStructureResponse();
    }

    /**
     * Create an instance of {@link ConstraintSelector }
     * 
     */
    public ConstraintSelector createConstraintSelector() {
        return new ConstraintSelector();
    }

    /**
     * Create an instance of {@link TypeStateWhenConstraint }
     * 
     */
    public TypeStateWhenConstraint createTypeStateWhenConstraint() {
        return new TypeStateWhenConstraint();
    }

    /**
     * Create an instance of {@link GetObjectTypesResponse }
     * 
     */
    public GetObjectTypesResponse createGetObjectTypesResponse() {
        return new GetObjectTypesResponse();
    }

    /**
     * Create an instance of {@link RequireConstraint }
     * 
     */
    public RequireConstraint createRequireConstraint() {
        return new RequireConstraint();
    }

    /**
     * Create an instance of {@link GetObjectStructure }
     * 
     */
    public GetObjectStructure createGetObjectStructure() {
        return new GetObjectStructure();
    }

    /**
     * Create an instance of {@link Type }
     * 
     */
    public Type createType() {
        return new Type();
    }

    /**
     * Create an instance of {@link CaseConstraint }
     * 
     */
    public CaseConstraint createCaseConstraint() {
        return new CaseConstraint();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetObjectStructureResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/dictionary", name = "getObjectStructureResponse")
    public JAXBElement<GetObjectStructureResponse> createGetObjectStructureResponse(GetObjectStructureResponse value) {
        return new JAXBElement<GetObjectStructureResponse>(_GetObjectStructureResponse_QNAME, GetObjectStructureResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetObjectTypesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/dictionary", name = "getObjectTypesResponse")
    public JAXBElement<GetObjectTypesResponse> createGetObjectTypesResponse(GetObjectTypesResponse value) {
        return new JAXBElement<GetObjectTypesResponse>(_GetObjectTypesResponse_QNAME, GetObjectTypesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetObjectStructure }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/dictionary", name = "getObjectStructure")
    public JAXBElement<GetObjectStructure> createGetObjectStructure(GetObjectStructure value) {
        return new JAXBElement<GetObjectStructure>(_GetObjectStructure_QNAME, GetObjectStructure.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetObjectTypes }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/dictionary", name = "getObjectTypes")
    public JAXBElement<GetObjectTypes> createGetObjectTypes(GetObjectTypes value) {
        return new JAXBElement<GetObjectTypes>(_GetObjectTypes_QNAME, GetObjectTypes.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FieldDescriptor }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/dictionary", name = "fieldDescriptor")
    public JAXBElement<FieldDescriptor> createFieldDescriptor(FieldDescriptor value) {
        return new JAXBElement<FieldDescriptor>(_FieldDescriptor_QNAME, FieldDescriptor.class, null, value);
    }

}
