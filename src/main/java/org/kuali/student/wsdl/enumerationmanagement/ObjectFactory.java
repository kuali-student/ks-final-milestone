
package org.kuali.student.wsdl.enumerationmanagement;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import org.kuali.student.wsdl.enumerationmanagementservice.AddEnumeratedValue;
import org.kuali.student.wsdl.enumerationmanagementservice.AddEnumeratedValueResponse;
import org.kuali.student.wsdl.enumerationmanagementservice.GetEnumeration;
import org.kuali.student.wsdl.enumerationmanagementservice.GetEnumerationMeta;
import org.kuali.student.wsdl.enumerationmanagementservice.GetEnumerationMetaResponse;
import org.kuali.student.wsdl.enumerationmanagementservice.GetEnumerationMetas;
import org.kuali.student.wsdl.enumerationmanagementservice.GetEnumerationMetasResponse;
import org.kuali.student.wsdl.enumerationmanagementservice.GetEnumerationResponse;
import org.kuali.student.wsdl.enumerationmanagementservice.RemoveEnumeratedValue;
import org.kuali.student.wsdl.enumerationmanagementservice.RemoveEnumeratedValueResponse;
import org.kuali.student.wsdl.enumerationmanagementservice.UpdateEnumeratedValue;
import org.kuali.student.wsdl.enumerationmanagementservice.UpdateEnumeratedValueResponse;
import org.kuali.student.wsdl.exceptions.AlreadyExistsException;
import org.kuali.student.wsdl.exceptions.DoesNotExistException;
import org.kuali.student.wsdl.exceptions.InvalidParameterException;
import org.kuali.student.wsdl.exceptions.MissingParameterException;
import org.kuali.student.wsdl.exceptions.OperationFailedException;
import org.kuali.student.wsdl.exceptions.PermissionDeniedException;
import org.kuali.student.wsdl.organization.Search;
import org.kuali.student.wsdl.organization.SearchResponse;
import org.kuali.student.wsdl.search.GetSearchCriteriaType;
import org.kuali.student.wsdl.search.GetSearchCriteriaTypeResponse;
import org.kuali.student.wsdl.search.GetSearchCriteriaTypes;
import org.kuali.student.wsdl.search.GetSearchCriteriaTypesResponse;
import org.kuali.student.wsdl.search.GetSearchResultType;
import org.kuali.student.wsdl.search.GetSearchResultTypeResponse;
import org.kuali.student.wsdl.search.GetSearchResultTypes;
import org.kuali.student.wsdl.search.GetSearchResultTypesResponse;
import org.kuali.student.wsdl.search.GetSearchType;
import org.kuali.student.wsdl.search.GetSearchTypeResponse;
import org.kuali.student.wsdl.search.GetSearchTypes;
import org.kuali.student.wsdl.search.GetSearchTypesByCriteria;
import org.kuali.student.wsdl.search.GetSearchTypesByCriteriaResponse;
import org.kuali.student.wsdl.search.GetSearchTypesByResult;
import org.kuali.student.wsdl.search.GetSearchTypesByResultResponse;
import org.kuali.student.wsdl.search.GetSearchTypesResponse;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.kuali.student.wsdl.enumerationmanagement package. 
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

    private final static QName _GetSearchResultTypes_QNAME = new QName("http://student.kuali.org/wsdl/enumerationmanagement", "getSearchResultTypes");
    private final static QName _GetEnumerationMetaResponse_QNAME = new QName("http://student.kuali.org/wsdl/enumerationmanagement", "getEnumerationMetaResponse");
    private final static QName _GetEnumerationResponse_QNAME = new QName("http://student.kuali.org/wsdl/enumerationmanagement", "getEnumerationResponse");
    private final static QName _GetSearchCriteriaTypesResponse_QNAME = new QName("http://student.kuali.org/wsdl/enumerationmanagement", "getSearchCriteriaTypesResponse");
    private final static QName _GetEnumerationMetasResponse_QNAME = new QName("http://student.kuali.org/wsdl/enumerationmanagement", "getEnumerationMetasResponse");
    private final static QName _Search_QNAME = new QName("http://student.kuali.org/wsdl/enumerationmanagement", "search");
    private final static QName _MissingParameterException_QNAME = new QName("http://student.kuali.org/wsdl/enumerationmanagement", "MissingParameterException");
    private final static QName _GetSearchResultTypesResponse_QNAME = new QName("http://student.kuali.org/wsdl/enumerationmanagement", "getSearchResultTypesResponse");
    private final static QName _GetSearchCriteriaTypeResponse_QNAME = new QName("http://student.kuali.org/wsdl/enumerationmanagement", "getSearchCriteriaTypeResponse");
    private final static QName _DoesNotExistException_QNAME = new QName("http://student.kuali.org/wsdl/enumerationmanagement", "DoesNotExistException");
    private final static QName _GetSearchResultTypeResponse_QNAME = new QName("http://student.kuali.org/wsdl/enumerationmanagement", "getSearchResultTypeResponse");
    private final static QName _GetSearchResultType_QNAME = new QName("http://student.kuali.org/wsdl/enumerationmanagement", "getSearchResultType");
    private final static QName _InvalidParameterException_QNAME = new QName("http://student.kuali.org/wsdl/enumerationmanagement", "InvalidParameterException");
    private final static QName _GetSearchTypesByResultResponse_QNAME = new QName("http://student.kuali.org/wsdl/enumerationmanagement", "getSearchTypesByResultResponse");
    private final static QName _GetSearchTypesByResult_QNAME = new QName("http://student.kuali.org/wsdl/enumerationmanagement", "getSearchTypesByResult");
    private final static QName _GetEnumerationMetas_QNAME = new QName("http://student.kuali.org/wsdl/enumerationmanagement", "getEnumerationMetas");
    private final static QName _GetSearchTypeResponse_QNAME = new QName("http://student.kuali.org/wsdl/enumerationmanagement", "getSearchTypeResponse");
    private final static QName _GetSearchTypesByCriteria_QNAME = new QName("http://student.kuali.org/wsdl/enumerationmanagement", "getSearchTypesByCriteria");
    private final static QName _AddEnumeratedValueResponse_QNAME = new QName("http://student.kuali.org/wsdl/enumerationmanagement", "addEnumeratedValueResponse");
    private final static QName _GetEnumerationMeta_QNAME = new QName("http://student.kuali.org/wsdl/enumerationmanagement", "getEnumerationMeta");
    private final static QName _RemoveEnumeratedValueResponse_QNAME = new QName("http://student.kuali.org/wsdl/enumerationmanagement", "removeEnumeratedValueResponse");
    private final static QName _GetSearchCriteriaType_QNAME = new QName("http://student.kuali.org/wsdl/enumerationmanagement", "getSearchCriteriaType");
    private final static QName _GetSearchCriteriaTypes_QNAME = new QName("http://student.kuali.org/wsdl/enumerationmanagement", "getSearchCriteriaTypes");
    private final static QName _UpdateEnumeratedValueResponse_QNAME = new QName("http://student.kuali.org/wsdl/enumerationmanagement", "updateEnumeratedValueResponse");
    private final static QName _GetSearchType_QNAME = new QName("http://student.kuali.org/wsdl/enumerationmanagement", "getSearchType");
    private final static QName _PermissionDeniedException_QNAME = new QName("http://student.kuali.org/wsdl/enumerationmanagement", "PermissionDeniedException");
    private final static QName _OperationFailedException_QNAME = new QName("http://student.kuali.org/wsdl/enumerationmanagement", "OperationFailedException");
    private final static QName _AlreadyExistsException_QNAME = new QName("http://student.kuali.org/wsdl/enumerationmanagement", "AlreadyExistsException");
    private final static QName _RemoveEnumeratedValue_QNAME = new QName("http://student.kuali.org/wsdl/enumerationmanagement", "removeEnumeratedValue");
    private final static QName _GetSearchTypes_QNAME = new QName("http://student.kuali.org/wsdl/enumerationmanagement", "getSearchTypes");
    private final static QName _SearchResponse_QNAME = new QName("http://student.kuali.org/wsdl/enumerationmanagement", "searchResponse");
    private final static QName _GetEnumeration_QNAME = new QName("http://student.kuali.org/wsdl/enumerationmanagement", "getEnumeration");
    private final static QName _UpdateEnumeratedValue_QNAME = new QName("http://student.kuali.org/wsdl/enumerationmanagement", "updateEnumeratedValue");
    private final static QName _AddEnumeratedValue_QNAME = new QName("http://student.kuali.org/wsdl/enumerationmanagement", "addEnumeratedValue");
    private final static QName _GetSearchTypesResponse_QNAME = new QName("http://student.kuali.org/wsdl/enumerationmanagement", "getSearchTypesResponse");
    private final static QName _GetSearchTypesByCriteriaResponse_QNAME = new QName("http://student.kuali.org/wsdl/enumerationmanagement", "getSearchTypesByCriteriaResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.kuali.student.wsdl.enumerationmanagement
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link EnumeratedValueFieldInfo }
     * 
     */
    public EnumeratedValueFieldInfo createEnumeratedValueFieldInfo() {
        return new EnumeratedValueFieldInfo();
    }

    /**
     * Create an instance of {@link EnumeratedValueInfo }
     * 
     */
    public EnumeratedValueInfo createEnumeratedValueInfo() {
        return new EnumeratedValueInfo();
    }

    /**
     * Create an instance of {@link Attribute }
     * 
     */
    public Attribute createAttribute() {
        return new Attribute();
    }

    /**
     * Create an instance of {@link StatusInfo }
     * 
     */
    public StatusInfo createStatusInfo() {
        return new StatusInfo();
    }

    /**
     * Create an instance of {@link EnumContextValueInfo }
     * 
     */
    public EnumContextValueInfo createEnumContextValueInfo() {
        return new EnumContextValueInfo();
    }

    /**
     * Create an instance of {@link FieldDescriptorInfo }
     * 
     */
    public FieldDescriptorInfo createFieldDescriptorInfo() {
        return new FieldDescriptorInfo();
    }

    /**
     * Create an instance of {@link EnumerationMetaInfo }
     * 
     */
    public EnumerationMetaInfo createEnumerationMetaInfo() {
        return new EnumerationMetaInfo();
    }

    /**
     * Create an instance of {@link EnumFieldViewInfo }
     * 
     */
    public EnumFieldViewInfo createEnumFieldViewInfo() {
        return new EnumFieldViewInfo();
    }

    /**
     * Create an instance of {@link JaxbAttributeList }
     * 
     */
    public JaxbAttributeList createJaxbAttributeList() {
        return new JaxbAttributeList();
    }

    /**
     * Create an instance of {@link EnumContextInfo }
     * 
     */
    public EnumContextInfo createEnumContextInfo() {
        return new EnumContextInfo();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchResultTypes }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/enumerationmanagement", name = "getSearchResultTypes")
    public JAXBElement<GetSearchResultTypes> createGetSearchResultTypes(GetSearchResultTypes value) {
        return new JAXBElement<GetSearchResultTypes>(_GetSearchResultTypes_QNAME, GetSearchResultTypes.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetEnumerationMetaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/enumerationmanagement", name = "getEnumerationMetaResponse")
    public JAXBElement<GetEnumerationMetaResponse> createGetEnumerationMetaResponse(GetEnumerationMetaResponse value) {
        return new JAXBElement<GetEnumerationMetaResponse>(_GetEnumerationMetaResponse_QNAME, GetEnumerationMetaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetEnumerationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/enumerationmanagement", name = "getEnumerationResponse")
    public JAXBElement<GetEnumerationResponse> createGetEnumerationResponse(GetEnumerationResponse value) {
        return new JAXBElement<GetEnumerationResponse>(_GetEnumerationResponse_QNAME, GetEnumerationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchCriteriaTypesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/enumerationmanagement", name = "getSearchCriteriaTypesResponse")
    public JAXBElement<GetSearchCriteriaTypesResponse> createGetSearchCriteriaTypesResponse(GetSearchCriteriaTypesResponse value) {
        return new JAXBElement<GetSearchCriteriaTypesResponse>(_GetSearchCriteriaTypesResponse_QNAME, GetSearchCriteriaTypesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetEnumerationMetasResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/enumerationmanagement", name = "getEnumerationMetasResponse")
    public JAXBElement<GetEnumerationMetasResponse> createGetEnumerationMetasResponse(GetEnumerationMetasResponse value) {
        return new JAXBElement<GetEnumerationMetasResponse>(_GetEnumerationMetasResponse_QNAME, GetEnumerationMetasResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Search }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/enumerationmanagement", name = "search")
    public JAXBElement<Search> createSearch(Search value) {
        return new JAXBElement<Search>(_Search_QNAME, Search.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MissingParameterException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/enumerationmanagement", name = "MissingParameterException")
    public JAXBElement<MissingParameterException> createMissingParameterException(MissingParameterException value) {
        return new JAXBElement<MissingParameterException>(_MissingParameterException_QNAME, MissingParameterException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchResultTypesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/enumerationmanagement", name = "getSearchResultTypesResponse")
    public JAXBElement<GetSearchResultTypesResponse> createGetSearchResultTypesResponse(GetSearchResultTypesResponse value) {
        return new JAXBElement<GetSearchResultTypesResponse>(_GetSearchResultTypesResponse_QNAME, GetSearchResultTypesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchCriteriaTypeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/enumerationmanagement", name = "getSearchCriteriaTypeResponse")
    public JAXBElement<GetSearchCriteriaTypeResponse> createGetSearchCriteriaTypeResponse(GetSearchCriteriaTypeResponse value) {
        return new JAXBElement<GetSearchCriteriaTypeResponse>(_GetSearchCriteriaTypeResponse_QNAME, GetSearchCriteriaTypeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DoesNotExistException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/enumerationmanagement", name = "DoesNotExistException")
    public JAXBElement<DoesNotExistException> createDoesNotExistException(DoesNotExistException value) {
        return new JAXBElement<DoesNotExistException>(_DoesNotExistException_QNAME, DoesNotExistException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchResultTypeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/enumerationmanagement", name = "getSearchResultTypeResponse")
    public JAXBElement<GetSearchResultTypeResponse> createGetSearchResultTypeResponse(GetSearchResultTypeResponse value) {
        return new JAXBElement<GetSearchResultTypeResponse>(_GetSearchResultTypeResponse_QNAME, GetSearchResultTypeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchResultType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/enumerationmanagement", name = "getSearchResultType")
    public JAXBElement<GetSearchResultType> createGetSearchResultType(GetSearchResultType value) {
        return new JAXBElement<GetSearchResultType>(_GetSearchResultType_QNAME, GetSearchResultType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InvalidParameterException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/enumerationmanagement", name = "InvalidParameterException")
    public JAXBElement<InvalidParameterException> createInvalidParameterException(InvalidParameterException value) {
        return new JAXBElement<InvalidParameterException>(_InvalidParameterException_QNAME, InvalidParameterException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchTypesByResultResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/enumerationmanagement", name = "getSearchTypesByResultResponse")
    public JAXBElement<GetSearchTypesByResultResponse> createGetSearchTypesByResultResponse(GetSearchTypesByResultResponse value) {
        return new JAXBElement<GetSearchTypesByResultResponse>(_GetSearchTypesByResultResponse_QNAME, GetSearchTypesByResultResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchTypesByResult }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/enumerationmanagement", name = "getSearchTypesByResult")
    public JAXBElement<GetSearchTypesByResult> createGetSearchTypesByResult(GetSearchTypesByResult value) {
        return new JAXBElement<GetSearchTypesByResult>(_GetSearchTypesByResult_QNAME, GetSearchTypesByResult.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetEnumerationMetas }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/enumerationmanagement", name = "getEnumerationMetas")
    public JAXBElement<GetEnumerationMetas> createGetEnumerationMetas(GetEnumerationMetas value) {
        return new JAXBElement<GetEnumerationMetas>(_GetEnumerationMetas_QNAME, GetEnumerationMetas.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchTypeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/enumerationmanagement", name = "getSearchTypeResponse")
    public JAXBElement<GetSearchTypeResponse> createGetSearchTypeResponse(GetSearchTypeResponse value) {
        return new JAXBElement<GetSearchTypeResponse>(_GetSearchTypeResponse_QNAME, GetSearchTypeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchTypesByCriteria }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/enumerationmanagement", name = "getSearchTypesByCriteria")
    public JAXBElement<GetSearchTypesByCriteria> createGetSearchTypesByCriteria(GetSearchTypesByCriteria value) {
        return new JAXBElement<GetSearchTypesByCriteria>(_GetSearchTypesByCriteria_QNAME, GetSearchTypesByCriteria.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddEnumeratedValueResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/enumerationmanagement", name = "addEnumeratedValueResponse")
    public JAXBElement<AddEnumeratedValueResponse> createAddEnumeratedValueResponse(AddEnumeratedValueResponse value) {
        return new JAXBElement<AddEnumeratedValueResponse>(_AddEnumeratedValueResponse_QNAME, AddEnumeratedValueResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetEnumerationMeta }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/enumerationmanagement", name = "getEnumerationMeta")
    public JAXBElement<GetEnumerationMeta> createGetEnumerationMeta(GetEnumerationMeta value) {
        return new JAXBElement<GetEnumerationMeta>(_GetEnumerationMeta_QNAME, GetEnumerationMeta.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveEnumeratedValueResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/enumerationmanagement", name = "removeEnumeratedValueResponse")
    public JAXBElement<RemoveEnumeratedValueResponse> createRemoveEnumeratedValueResponse(RemoveEnumeratedValueResponse value) {
        return new JAXBElement<RemoveEnumeratedValueResponse>(_RemoveEnumeratedValueResponse_QNAME, RemoveEnumeratedValueResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchCriteriaType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/enumerationmanagement", name = "getSearchCriteriaType")
    public JAXBElement<GetSearchCriteriaType> createGetSearchCriteriaType(GetSearchCriteriaType value) {
        return new JAXBElement<GetSearchCriteriaType>(_GetSearchCriteriaType_QNAME, GetSearchCriteriaType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchCriteriaTypes }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/enumerationmanagement", name = "getSearchCriteriaTypes")
    public JAXBElement<GetSearchCriteriaTypes> createGetSearchCriteriaTypes(GetSearchCriteriaTypes value) {
        return new JAXBElement<GetSearchCriteriaTypes>(_GetSearchCriteriaTypes_QNAME, GetSearchCriteriaTypes.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateEnumeratedValueResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/enumerationmanagement", name = "updateEnumeratedValueResponse")
    public JAXBElement<UpdateEnumeratedValueResponse> createUpdateEnumeratedValueResponse(UpdateEnumeratedValueResponse value) {
        return new JAXBElement<UpdateEnumeratedValueResponse>(_UpdateEnumeratedValueResponse_QNAME, UpdateEnumeratedValueResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/enumerationmanagement", name = "getSearchType")
    public JAXBElement<GetSearchType> createGetSearchType(GetSearchType value) {
        return new JAXBElement<GetSearchType>(_GetSearchType_QNAME, GetSearchType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PermissionDeniedException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/enumerationmanagement", name = "PermissionDeniedException")
    public JAXBElement<PermissionDeniedException> createPermissionDeniedException(PermissionDeniedException value) {
        return new JAXBElement<PermissionDeniedException>(_PermissionDeniedException_QNAME, PermissionDeniedException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OperationFailedException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/enumerationmanagement", name = "OperationFailedException")
    public JAXBElement<OperationFailedException> createOperationFailedException(OperationFailedException value) {
        return new JAXBElement<OperationFailedException>(_OperationFailedException_QNAME, OperationFailedException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AlreadyExistsException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/enumerationmanagement", name = "AlreadyExistsException")
    public JAXBElement<AlreadyExistsException> createAlreadyExistsException(AlreadyExistsException value) {
        return new JAXBElement<AlreadyExistsException>(_AlreadyExistsException_QNAME, AlreadyExistsException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveEnumeratedValue }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/enumerationmanagement", name = "removeEnumeratedValue")
    public JAXBElement<RemoveEnumeratedValue> createRemoveEnumeratedValue(RemoveEnumeratedValue value) {
        return new JAXBElement<RemoveEnumeratedValue>(_RemoveEnumeratedValue_QNAME, RemoveEnumeratedValue.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchTypes }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/enumerationmanagement", name = "getSearchTypes")
    public JAXBElement<GetSearchTypes> createGetSearchTypes(GetSearchTypes value) {
        return new JAXBElement<GetSearchTypes>(_GetSearchTypes_QNAME, GetSearchTypes.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/enumerationmanagement", name = "searchResponse")
    public JAXBElement<SearchResponse> createSearchResponse(SearchResponse value) {
        return new JAXBElement<SearchResponse>(_SearchResponse_QNAME, SearchResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetEnumeration }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/enumerationmanagement", name = "getEnumeration")
    public JAXBElement<GetEnumeration> createGetEnumeration(GetEnumeration value) {
        return new JAXBElement<GetEnumeration>(_GetEnumeration_QNAME, GetEnumeration.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateEnumeratedValue }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/enumerationmanagement", name = "updateEnumeratedValue")
    public JAXBElement<UpdateEnumeratedValue> createUpdateEnumeratedValue(UpdateEnumeratedValue value) {
        return new JAXBElement<UpdateEnumeratedValue>(_UpdateEnumeratedValue_QNAME, UpdateEnumeratedValue.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddEnumeratedValue }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/enumerationmanagement", name = "addEnumeratedValue")
    public JAXBElement<AddEnumeratedValue> createAddEnumeratedValue(AddEnumeratedValue value) {
        return new JAXBElement<AddEnumeratedValue>(_AddEnumeratedValue_QNAME, AddEnumeratedValue.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchTypesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/enumerationmanagement", name = "getSearchTypesResponse")
    public JAXBElement<GetSearchTypesResponse> createGetSearchTypesResponse(GetSearchTypesResponse value) {
        return new JAXBElement<GetSearchTypesResponse>(_GetSearchTypesResponse_QNAME, GetSearchTypesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchTypesByCriteriaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/enumerationmanagement", name = "getSearchTypesByCriteriaResponse")
    public JAXBElement<GetSearchTypesByCriteriaResponse> createGetSearchTypesByCriteriaResponse(GetSearchTypesByCriteriaResponse value) {
        return new JAXBElement<GetSearchTypesByCriteriaResponse>(_GetSearchTypesByCriteriaResponse_QNAME, GetSearchTypesByCriteriaResponse.class, null, value);
    }

}
