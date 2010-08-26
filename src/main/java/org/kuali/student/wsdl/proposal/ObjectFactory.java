
package org.kuali.student.wsdl.proposal;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import org.kuali.student.wsdl.dictionary.GetObjectStructure;
import org.kuali.student.wsdl.dictionary.GetObjectStructureResponse;
import org.kuali.student.wsdl.dictionary.GetObjectTypes;
import org.kuali.student.wsdl.dictionary.GetObjectTypesResponse;
import org.kuali.student.wsdl.exceptions.AlreadyExistsException;
import org.kuali.student.wsdl.exceptions.DataValidationErrorException;
import org.kuali.student.wsdl.exceptions.DependentObjectsExistException;
import org.kuali.student.wsdl.exceptions.DoesNotExistException;
import org.kuali.student.wsdl.exceptions.InvalidParameterException;
import org.kuali.student.wsdl.exceptions.MissingParameterException;
import org.kuali.student.wsdl.exceptions.OperationFailedException;
import org.kuali.student.wsdl.exceptions.PermissionDeniedException;
import org.kuali.student.wsdl.exceptions.VersionMismatchException;
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
 * generated in the org.kuali.student.wsdl.proposal package. 
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

    private final static QName _GetSearchResultType_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "getSearchResultType");
    private final static QName _DoesNotExistException_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "DoesNotExistException");
    private final static QName _GetSearchResultTypesResponse_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "getSearchResultTypesResponse");
    private final static QName _GetSearchCriteriaTypeResponse_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "getSearchCriteriaTypeResponse");
    private final static QName _Search_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "search");
    private final static QName _GetProposalTypesResponse_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "getProposalTypesResponse");
    private final static QName _GetProposalsByIdListResponse_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "getProposalsByIdListResponse");
    private final static QName _DeleteProposalResponse_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "deleteProposalResponse");
    private final static QName _CreateProposalDocRelation_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "createProposalDocRelation");
    private final static QName _GetSearchResultTypes_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "getSearchResultTypes");
    private final static QName _GetProposalDocRelationsByProposal_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "getProposalDocRelationsByProposal");
    private final static QName _GetProposalDocRelationsByType_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "getProposalDocRelationsByType");
    private final static QName _UpdateProposalResponse_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "updateProposalResponse");
    private final static QName _UpdateProposalDocRelation_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "updateProposalDocRelation");
    private final static QName _CreateProposal_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "createProposal");
    private final static QName _CreateProposalResponse_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "createProposalResponse");
    private final static QName _UpdateProposalDocRelationResponse_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "updateProposalDocRelationResponse");
    private final static QName _GetSearchTypesByCriteria_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "getSearchTypesByCriteria");
    private final static QName _GetSearchTypesByResult_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "getSearchTypesByResult");
    private final static QName _GetSearchTypesByResultResponse_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "getSearchTypesByResultResponse");
    private final static QName _GetProposalsByProposalTypeResponse_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "getProposalsByProposalTypeResponse");
    private final static QName _ValidateProposalDocRelationResponse_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "validateProposalDocRelationResponse");
    private final static QName _GetAllowedProposalDocRelationTypesForProposalType_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "getAllowedProposalDocRelationTypesForProposalType");
    private final static QName _GetProposalDocRelationsByIdListResponse_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "getProposalDocRelationsByIdListResponse");
    private final static QName _AlreadyExistsException_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "AlreadyExistsException");
    private final static QName _GetProposalTypes_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "getProposalTypes");
    private final static QName _VersionMismatchException_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "VersionMismatchException");
    private final static QName _OperationFailedException_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "OperationFailedException");
    private final static QName _GetObjectStructure_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "getObjectStructure");
    private final static QName _GetSearchCriteriaTypes_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "getSearchCriteriaTypes");
    private final static QName _GetSearchCriteriaType_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "getSearchCriteriaType");
    private final static QName _GetObjectStructureResponse_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "getObjectStructureResponse");
    private final static QName _GetProposalTypeResponse_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "getProposalTypeResponse");
    private final static QName _DeleteProposal_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "deleteProposal");
    private final static QName _GetProposalTypesForReferenceType_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "getProposalTypesForReferenceType");
    private final static QName _GetProposalDocRelationsByDocument_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "getProposalDocRelationsByDocument");
    private final static QName _GetSearchTypesResponse_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "getSearchTypesResponse");
    private final static QName _ValidateProposalResponse_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "validateProposalResponse");
    private final static QName _GetProposalDocRelationResponse_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "getProposalDocRelationResponse");
    private final static QName _GetProposalsByReference_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "getProposalsByReference");
    private final static QName _ValidateProposalDocRelation_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "validateProposalDocRelation");
    private final static QName _GetSearchResultTypeResponse_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "getSearchResultTypeResponse");
    private final static QName _GetReferenceTypesResponse_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "getReferenceTypesResponse");
    private final static QName _GetAllowedProposalDocRelationTypesForProposalTypeResponse_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "getAllowedProposalDocRelationTypesForProposalTypeResponse");
    private final static QName _MissingParameterException_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "MissingParameterException");
    private final static QName _DeleteProposalDocRelationResponse_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "deleteProposalDocRelationResponse");
    private final static QName _GetProposalTypesForReferenceTypeResponse_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "getProposalTypesForReferenceTypeResponse");
    private final static QName _GetProposalDocRelationsByIdList_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "getProposalDocRelationsByIdList");
    private final static QName _GetProposalDocRelationTypes_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "getProposalDocRelationTypes");
    private final static QName _GetProposal_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "getProposal");
    private final static QName _GetSearchCriteriaTypesResponse_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "getSearchCriteriaTypesResponse");
    private final static QName _GetProposalsByProposalType_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "getProposalsByProposalType");
    private final static QName _GetProposalResponse_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "getProposalResponse");
    private final static QName _GetProposalDocRelation_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "getProposalDocRelation");
    private final static QName _ValidateProposal_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "validateProposal");
    private final static QName _GetProposalDocRelationTypesResponse_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "getProposalDocRelationTypesResponse");
    private final static QName _GetProposalDocRelationsByProposalResponse_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "getProposalDocRelationsByProposalResponse");
    private final static QName _GetProposalDocRelationsByDocumentResponse_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "getProposalDocRelationsByDocumentResponse");
    private final static QName _DeleteProposalDocRelation_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "deleteProposalDocRelation");
    private final static QName _GetSearchTypeResponse_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "getSearchTypeResponse");
    private final static QName _UpdateProposal_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "updateProposal");
    private final static QName _InvalidParameterException_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "InvalidParameterException");
    private final static QName _GetObjectTypes_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "getObjectTypes");
    private final static QName _GetSearchType_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "getSearchType");
    private final static QName _PermissionDeniedException_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "PermissionDeniedException");
    private final static QName _GetReferenceTypes_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "getReferenceTypes");
    private final static QName _DataValidationErrorException_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "DataValidationErrorException");
    private final static QName _GetObjectTypesResponse_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "getObjectTypesResponse");
    private final static QName _GetProposalType_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "getProposalType");
    private final static QName _DependentObjectsExistException_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "DependentObjectsExistException");
    private final static QName _GetProposalDocRelationsByTypeResponse_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "getProposalDocRelationsByTypeResponse");
    private final static QName _GetProposalDocRelationType_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "getProposalDocRelationType");
    private final static QName _CreateProposalDocRelationResponse_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "createProposalDocRelationResponse");
    private final static QName _GetProposalsByState_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "getProposalsByState");
    private final static QName _GetProposalsByStateResponse_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "getProposalsByStateResponse");
    private final static QName _GetSearchTypesByCriteriaResponse_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "getSearchTypesByCriteriaResponse");
    private final static QName _GetProposalDocRelationTypeResponse_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "getProposalDocRelationTypeResponse");
    private final static QName _GetProposalsByIdList_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "getProposalsByIdList");
    private final static QName _GetProposalsByReferenceResponse_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "getProposalsByReferenceResponse");
    private final static QName _SearchResponse_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "searchResponse");
    private final static QName _GetSearchTypes_QNAME = new QName("http://student.kuali.org/wsdl/proposal", "getSearchTypes");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.kuali.student.wsdl.proposal
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetProposalsByIdList }
     * 
     */
    public GetProposalsByIdList createGetProposalsByIdList() {
        return new GetProposalsByIdList();
    }

    /**
     * Create an instance of {@link CreateProposalDocRelationResponse }
     * 
     */
    public CreateProposalDocRelationResponse createCreateProposalDocRelationResponse() {
        return new CreateProposalDocRelationResponse();
    }

    /**
     * Create an instance of {@link GetProposalTypesForReferenceTypeResponse }
     * 
     */
    public GetProposalTypesForReferenceTypeResponse createGetProposalTypesForReferenceTypeResponse() {
        return new GetProposalTypesForReferenceTypeResponse();
    }

    /**
     * Create an instance of {@link GetProposalsByState }
     * 
     */
    public GetProposalsByState createGetProposalsByState() {
        return new GetProposalsByState();
    }

    /**
     * Create an instance of {@link GetProposal }
     * 
     */
    public GetProposal createGetProposal() {
        return new GetProposal();
    }

    /**
     * Create an instance of {@link GetProposalDocRelationsByDocumentResponse }
     * 
     */
    public GetProposalDocRelationsByDocumentResponse createGetProposalDocRelationsByDocumentResponse() {
        return new GetProposalDocRelationsByDocumentResponse();
    }

    /**
     * Create an instance of {@link ProposalDocRelationInfo }
     * 
     */
    public ProposalDocRelationInfo createProposalDocRelationInfo() {
        return new ProposalDocRelationInfo();
    }

    /**
     * Create an instance of {@link UpdateProposalResponse }
     * 
     */
    public UpdateProposalResponse createUpdateProposalResponse() {
        return new UpdateProposalResponse();
    }

    /**
     * Create an instance of {@link DeleteProposalDocRelation }
     * 
     */
    public DeleteProposalDocRelation createDeleteProposalDocRelation() {
        return new DeleteProposalDocRelation();
    }

    /**
     * Create an instance of {@link ValidateProposal }
     * 
     */
    public ValidateProposal createValidateProposal() {
        return new ValidateProposal();
    }

    /**
     * Create an instance of {@link ValidationResultInfo }
     * 
     */
    public ValidationResultInfo createValidationResultInfo() {
        return new ValidationResultInfo();
    }

    /**
     * Create an instance of {@link ValidCharsConstraint }
     * 
     */
    public ValidCharsConstraint createValidCharsConstraint() {
        return new ValidCharsConstraint();
    }

    /**
     * Create an instance of {@link ProposalTypeInfo }
     * 
     */
    public ProposalTypeInfo createProposalTypeInfo() {
        return new ProposalTypeInfo();
    }

    /**
     * Create an instance of {@link MustOccurConstraint }
     * 
     */
    public MustOccurConstraint createMustOccurConstraint() {
        return new MustOccurConstraint();
    }

    /**
     * Create an instance of {@link GetProposalsByReferenceResponse }
     * 
     */
    public GetProposalsByReferenceResponse createGetProposalsByReferenceResponse() {
        return new GetProposalsByReferenceResponse();
    }

    /**
     * Create an instance of {@link Constraint }
     * 
     */
    public Constraint createConstraint() {
        return new Constraint();
    }

    /**
     * Create an instance of {@link GetProposalsByProposalTypeResponse }
     * 
     */
    public GetProposalsByProposalTypeResponse createGetProposalsByProposalTypeResponse() {
        return new GetProposalsByProposalTypeResponse();
    }

    /**
     * Create an instance of {@link DeleteProposalDocRelationResponse }
     * 
     */
    public DeleteProposalDocRelationResponse createDeleteProposalDocRelationResponse() {
        return new DeleteProposalDocRelationResponse();
    }

    /**
     * Create an instance of {@link GetProposalTypesResponse }
     * 
     */
    public GetProposalTypesResponse createGetProposalTypesResponse() {
        return new GetProposalTypesResponse();
    }

    /**
     * Create an instance of {@link GetProposalsByReference }
     * 
     */
    public GetProposalsByReference createGetProposalsByReference() {
        return new GetProposalsByReference();
    }

    /**
     * Create an instance of {@link GetProposalDocRelationsByType }
     * 
     */
    public GetProposalDocRelationsByType createGetProposalDocRelationsByType() {
        return new GetProposalDocRelationsByType();
    }

    /**
     * Create an instance of {@link GetProposalDocRelationsByIdListResponse }
     * 
     */
    public GetProposalDocRelationsByIdListResponse createGetProposalDocRelationsByIdListResponse() {
        return new GetProposalDocRelationsByIdListResponse();
    }

    /**
     * Create an instance of {@link ProposalInfo }
     * 
     */
    public ProposalInfo createProposalInfo() {
        return new ProposalInfo();
    }

    /**
     * Create an instance of {@link ValidateProposalDocRelationResponse }
     * 
     */
    public ValidateProposalDocRelationResponse createValidateProposalDocRelationResponse() {
        return new ValidateProposalDocRelationResponse();
    }

    /**
     * Create an instance of {@link GetProposalDocRelationsByDocument }
     * 
     */
    public GetProposalDocRelationsByDocument createGetProposalDocRelationsByDocument() {
        return new GetProposalDocRelationsByDocument();
    }

    /**
     * Create an instance of {@link GetReferenceTypes }
     * 
     */
    public GetReferenceTypes createGetReferenceTypes() {
        return new GetReferenceTypes();
    }

    /**
     * Create an instance of {@link UpdateProposalDocRelation }
     * 
     */
    public UpdateProposalDocRelation createUpdateProposalDocRelation() {
        return new UpdateProposalDocRelation();
    }

    /**
     * Create an instance of {@link GetProposalTypes }
     * 
     */
    public GetProposalTypes createGetProposalTypes() {
        return new GetProposalTypes();
    }

    /**
     * Create an instance of {@link LookupConstraint }
     * 
     */
    public LookupConstraint createLookupConstraint() {
        return new LookupConstraint();
    }

    /**
     * Create an instance of {@link GetProposalsByProposalType }
     * 
     */
    public GetProposalsByProposalType createGetProposalsByProposalType() {
        return new GetProposalsByProposalType();
    }

    /**
     * Create an instance of {@link GetProposalDocRelationTypes }
     * 
     */
    public GetProposalDocRelationTypes createGetProposalDocRelationTypes() {
        return new GetProposalDocRelationTypes();
    }

    /**
     * Create an instance of {@link GetProposalTypeResponse }
     * 
     */
    public GetProposalTypeResponse createGetProposalTypeResponse() {
        return new GetProposalTypeResponse();
    }

    /**
     * Create an instance of {@link GetProposalsByStateResponse }
     * 
     */
    public GetProposalsByStateResponse createGetProposalsByStateResponse() {
        return new GetProposalsByStateResponse();
    }

    /**
     * Create an instance of {@link UpdateProposalDocRelationResponse }
     * 
     */
    public UpdateProposalDocRelationResponse createUpdateProposalDocRelationResponse() {
        return new UpdateProposalDocRelationResponse();
    }

    /**
     * Create an instance of {@link GetProposalDocRelationTypesResponse }
     * 
     */
    public GetProposalDocRelationTypesResponse createGetProposalDocRelationTypesResponse() {
        return new GetProposalDocRelationTypesResponse();
    }

    /**
     * Create an instance of {@link RichTextInfo }
     * 
     */
    public RichTextInfo createRichTextInfo() {
        return new RichTextInfo();
    }

    /**
     * Create an instance of {@link CaseConstraint }
     * 
     */
    public CaseConstraint createCaseConstraint() {
        return new CaseConstraint();
    }

    /**
     * Create an instance of {@link GetAllowedProposalDocRelationTypesForProposalType }
     * 
     */
    public GetAllowedProposalDocRelationTypesForProposalType createGetAllowedProposalDocRelationTypesForProposalType() {
        return new GetAllowedProposalDocRelationTypesForProposalType();
    }

    /**
     * Create an instance of {@link GetProposalsByIdListResponse }
     * 
     */
    public GetProposalsByIdListResponse createGetProposalsByIdListResponse() {
        return new GetProposalsByIdListResponse();
    }

    /**
     * Create an instance of {@link GetAllowedProposalDocRelationTypesForProposalTypeResponse }
     * 
     */
    public GetAllowedProposalDocRelationTypesForProposalTypeResponse createGetAllowedProposalDocRelationTypesForProposalTypeResponse() {
        return new GetAllowedProposalDocRelationTypesForProposalTypeResponse();
    }

    /**
     * Create an instance of {@link GetProposalTypesForReferenceType }
     * 
     */
    public GetProposalTypesForReferenceType createGetProposalTypesForReferenceType() {
        return new GetProposalTypesForReferenceType();
    }

    /**
     * Create an instance of {@link CreateProposal }
     * 
     */
    public CreateProposal createCreateProposal() {
        return new CreateProposal();
    }

    /**
     * Create an instance of {@link GetReferenceTypesResponse }
     * 
     */
    public GetReferenceTypesResponse createGetReferenceTypesResponse() {
        return new GetReferenceTypesResponse();
    }

    /**
     * Create an instance of {@link StatusInfo }
     * 
     */
    public StatusInfo createStatusInfo() {
        return new StatusInfo();
    }

    /**
     * Create an instance of {@link Attribute }
     * 
     */
    public Attribute createAttribute() {
        return new Attribute();
    }

    /**
     * Create an instance of {@link GetProposalDocRelationsByProposal }
     * 
     */
    public GetProposalDocRelationsByProposal createGetProposalDocRelationsByProposal() {
        return new GetProposalDocRelationsByProposal();
    }

    /**
     * Create an instance of {@link MetaInfo }
     * 
     */
    public MetaInfo createMetaInfo() {
        return new MetaInfo();
    }

    /**
     * Create an instance of {@link GetProposalDocRelationResponse }
     * 
     */
    public GetProposalDocRelationResponse createGetProposalDocRelationResponse() {
        return new GetProposalDocRelationResponse();
    }

    /**
     * Create an instance of {@link FieldDefinition }
     * 
     */
    public FieldDefinition createFieldDefinition() {
        return new FieldDefinition();
    }

    /**
     * Create an instance of {@link BaseConstraint }
     * 
     */
    public BaseConstraint createBaseConstraint() {
        return new BaseConstraint();
    }

    /**
     * Create an instance of {@link ObjectStructureDefinition }
     * 
     */
    public ObjectStructureDefinition createObjectStructureDefinition() {
        return new ObjectStructureDefinition();
    }

    /**
     * Create an instance of {@link DeleteProposal }
     * 
     */
    public DeleteProposal createDeleteProposal() {
        return new DeleteProposal();
    }

    /**
     * Create an instance of {@link GetProposalType }
     * 
     */
    public GetProposalType createGetProposalType() {
        return new GetProposalType();
    }

    /**
     * Create an instance of {@link ObjectStructureDefinitionWrapper }
     * 
     */
    public ObjectStructureDefinitionWrapper createObjectStructureDefinitionWrapper() {
        return new ObjectStructureDefinitionWrapper();
    }

    /**
     * Create an instance of {@link CreateProposalDocRelation }
     * 
     */
    public CreateProposalDocRelation createCreateProposalDocRelation() {
        return new CreateProposalDocRelation();
    }

    /**
     * Create an instance of {@link WhenConstraint }
     * 
     */
    public WhenConstraint createWhenConstraint() {
        return new WhenConstraint();
    }

    /**
     * Create an instance of {@link CommonLookupParam }
     * 
     */
    public CommonLookupParam createCommonLookupParam() {
        return new CommonLookupParam();
    }

    /**
     * Create an instance of {@link GetProposalDocRelationsByIdList }
     * 
     */
    public GetProposalDocRelationsByIdList createGetProposalDocRelationsByIdList() {
        return new GetProposalDocRelationsByIdList();
    }

    /**
     * Create an instance of {@link ValidateProposalResponse }
     * 
     */
    public ValidateProposalResponse createValidateProposalResponse() {
        return new ValidateProposalResponse();
    }

    /**
     * Create an instance of {@link RequiredConstraint }
     * 
     */
    public RequiredConstraint createRequiredConstraint() {
        return new RequiredConstraint();
    }

    /**
     * Create an instance of {@link GetProposalDocRelationsByProposalResponse }
     * 
     */
    public GetProposalDocRelationsByProposalResponse createGetProposalDocRelationsByProposalResponse() {
        return new GetProposalDocRelationsByProposalResponse();
    }

    /**
     * Create an instance of {@link UpdateProposal }
     * 
     */
    public UpdateProposal createUpdateProposal() {
        return new UpdateProposal();
    }

    /**
     * Create an instance of {@link ValidateProposalDocRelation }
     * 
     */
    public ValidateProposalDocRelation createValidateProposalDocRelation() {
        return new ValidateProposalDocRelation();
    }

    /**
     * Create an instance of {@link GetProposalResponse }
     * 
     */
    public GetProposalResponse createGetProposalResponse() {
        return new GetProposalResponse();
    }

    /**
     * Create an instance of {@link GetProposalDocRelationsByTypeResponse }
     * 
     */
    public GetProposalDocRelationsByTypeResponse createGetProposalDocRelationsByTypeResponse() {
        return new GetProposalDocRelationsByTypeResponse();
    }

    /**
     * Create an instance of {@link GetProposalDocRelationType }
     * 
     */
    public GetProposalDocRelationType createGetProposalDocRelationType() {
        return new GetProposalDocRelationType();
    }

    /**
     * Create an instance of {@link JaxbAttributeList }
     * 
     */
    public JaxbAttributeList createJaxbAttributeList() {
        return new JaxbAttributeList();
    }

    /**
     * Create an instance of {@link DeleteProposalResponse }
     * 
     */
    public DeleteProposalResponse createDeleteProposalResponse() {
        return new DeleteProposalResponse();
    }

    /**
     * Create an instance of {@link CreateProposalResponse }
     * 
     */
    public CreateProposalResponse createCreateProposalResponse() {
        return new CreateProposalResponse();
    }

    /**
     * Create an instance of {@link GetProposalDocRelation }
     * 
     */
    public GetProposalDocRelation createGetProposalDocRelation() {
        return new GetProposalDocRelation();
    }

    /**
     * Create an instance of {@link ProposalDocRelationTypeInfo }
     * 
     */
    public ProposalDocRelationTypeInfo createProposalDocRelationTypeInfo() {
        return new ProposalDocRelationTypeInfo();
    }

    /**
     * Create an instance of {@link GetProposalDocRelationTypeResponse }
     * 
     */
    public GetProposalDocRelationTypeResponse createGetProposalDocRelationTypeResponse() {
        return new GetProposalDocRelationTypeResponse();
    }

    /**
     * Create an instance of {@link CommonLookup }
     * 
     */
    public CommonLookup createCommonLookup() {
        return new CommonLookup();
    }

    /**
     * Create an instance of {@link ReferenceTypeInfo }
     * 
     */
    public ReferenceTypeInfo createReferenceTypeInfo() {
        return new ReferenceTypeInfo();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchResultType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "getSearchResultType")
    public JAXBElement<GetSearchResultType> createGetSearchResultType(GetSearchResultType value) {
        return new JAXBElement<GetSearchResultType>(_GetSearchResultType_QNAME, GetSearchResultType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DoesNotExistException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "DoesNotExistException")
    public JAXBElement<DoesNotExistException> createDoesNotExistException(DoesNotExistException value) {
        return new JAXBElement<DoesNotExistException>(_DoesNotExistException_QNAME, DoesNotExistException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchResultTypesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "getSearchResultTypesResponse")
    public JAXBElement<GetSearchResultTypesResponse> createGetSearchResultTypesResponse(GetSearchResultTypesResponse value) {
        return new JAXBElement<GetSearchResultTypesResponse>(_GetSearchResultTypesResponse_QNAME, GetSearchResultTypesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchCriteriaTypeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "getSearchCriteriaTypeResponse")
    public JAXBElement<GetSearchCriteriaTypeResponse> createGetSearchCriteriaTypeResponse(GetSearchCriteriaTypeResponse value) {
        return new JAXBElement<GetSearchCriteriaTypeResponse>(_GetSearchCriteriaTypeResponse_QNAME, GetSearchCriteriaTypeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Search }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "search")
    public JAXBElement<Search> createSearch(Search value) {
        return new JAXBElement<Search>(_Search_QNAME, Search.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProposalTypesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "getProposalTypesResponse")
    public JAXBElement<GetProposalTypesResponse> createGetProposalTypesResponse(GetProposalTypesResponse value) {
        return new JAXBElement<GetProposalTypesResponse>(_GetProposalTypesResponse_QNAME, GetProposalTypesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProposalsByIdListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "getProposalsByIdListResponse")
    public JAXBElement<GetProposalsByIdListResponse> createGetProposalsByIdListResponse(GetProposalsByIdListResponse value) {
        return new JAXBElement<GetProposalsByIdListResponse>(_GetProposalsByIdListResponse_QNAME, GetProposalsByIdListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteProposalResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "deleteProposalResponse")
    public JAXBElement<DeleteProposalResponse> createDeleteProposalResponse(DeleteProposalResponse value) {
        return new JAXBElement<DeleteProposalResponse>(_DeleteProposalResponse_QNAME, DeleteProposalResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateProposalDocRelation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "createProposalDocRelation")
    public JAXBElement<CreateProposalDocRelation> createCreateProposalDocRelation(CreateProposalDocRelation value) {
        return new JAXBElement<CreateProposalDocRelation>(_CreateProposalDocRelation_QNAME, CreateProposalDocRelation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchResultTypes }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "getSearchResultTypes")
    public JAXBElement<GetSearchResultTypes> createGetSearchResultTypes(GetSearchResultTypes value) {
        return new JAXBElement<GetSearchResultTypes>(_GetSearchResultTypes_QNAME, GetSearchResultTypes.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProposalDocRelationsByProposal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "getProposalDocRelationsByProposal")
    public JAXBElement<GetProposalDocRelationsByProposal> createGetProposalDocRelationsByProposal(GetProposalDocRelationsByProposal value) {
        return new JAXBElement<GetProposalDocRelationsByProposal>(_GetProposalDocRelationsByProposal_QNAME, GetProposalDocRelationsByProposal.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProposalDocRelationsByType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "getProposalDocRelationsByType")
    public JAXBElement<GetProposalDocRelationsByType> createGetProposalDocRelationsByType(GetProposalDocRelationsByType value) {
        return new JAXBElement<GetProposalDocRelationsByType>(_GetProposalDocRelationsByType_QNAME, GetProposalDocRelationsByType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateProposalResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "updateProposalResponse")
    public JAXBElement<UpdateProposalResponse> createUpdateProposalResponse(UpdateProposalResponse value) {
        return new JAXBElement<UpdateProposalResponse>(_UpdateProposalResponse_QNAME, UpdateProposalResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateProposalDocRelation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "updateProposalDocRelation")
    public JAXBElement<UpdateProposalDocRelation> createUpdateProposalDocRelation(UpdateProposalDocRelation value) {
        return new JAXBElement<UpdateProposalDocRelation>(_UpdateProposalDocRelation_QNAME, UpdateProposalDocRelation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateProposal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "createProposal")
    public JAXBElement<CreateProposal> createCreateProposal(CreateProposal value) {
        return new JAXBElement<CreateProposal>(_CreateProposal_QNAME, CreateProposal.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateProposalResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "createProposalResponse")
    public JAXBElement<CreateProposalResponse> createCreateProposalResponse(CreateProposalResponse value) {
        return new JAXBElement<CreateProposalResponse>(_CreateProposalResponse_QNAME, CreateProposalResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateProposalDocRelationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "updateProposalDocRelationResponse")
    public JAXBElement<UpdateProposalDocRelationResponse> createUpdateProposalDocRelationResponse(UpdateProposalDocRelationResponse value) {
        return new JAXBElement<UpdateProposalDocRelationResponse>(_UpdateProposalDocRelationResponse_QNAME, UpdateProposalDocRelationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchTypesByCriteria }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "getSearchTypesByCriteria")
    public JAXBElement<GetSearchTypesByCriteria> createGetSearchTypesByCriteria(GetSearchTypesByCriteria value) {
        return new JAXBElement<GetSearchTypesByCriteria>(_GetSearchTypesByCriteria_QNAME, GetSearchTypesByCriteria.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchTypesByResult }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "getSearchTypesByResult")
    public JAXBElement<GetSearchTypesByResult> createGetSearchTypesByResult(GetSearchTypesByResult value) {
        return new JAXBElement<GetSearchTypesByResult>(_GetSearchTypesByResult_QNAME, GetSearchTypesByResult.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchTypesByResultResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "getSearchTypesByResultResponse")
    public JAXBElement<GetSearchTypesByResultResponse> createGetSearchTypesByResultResponse(GetSearchTypesByResultResponse value) {
        return new JAXBElement<GetSearchTypesByResultResponse>(_GetSearchTypesByResultResponse_QNAME, GetSearchTypesByResultResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProposalsByProposalTypeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "getProposalsByProposalTypeResponse")
    public JAXBElement<GetProposalsByProposalTypeResponse> createGetProposalsByProposalTypeResponse(GetProposalsByProposalTypeResponse value) {
        return new JAXBElement<GetProposalsByProposalTypeResponse>(_GetProposalsByProposalTypeResponse_QNAME, GetProposalsByProposalTypeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateProposalDocRelationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "validateProposalDocRelationResponse")
    public JAXBElement<ValidateProposalDocRelationResponse> createValidateProposalDocRelationResponse(ValidateProposalDocRelationResponse value) {
        return new JAXBElement<ValidateProposalDocRelationResponse>(_ValidateProposalDocRelationResponse_QNAME, ValidateProposalDocRelationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllowedProposalDocRelationTypesForProposalType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "getAllowedProposalDocRelationTypesForProposalType")
    public JAXBElement<GetAllowedProposalDocRelationTypesForProposalType> createGetAllowedProposalDocRelationTypesForProposalType(GetAllowedProposalDocRelationTypesForProposalType value) {
        return new JAXBElement<GetAllowedProposalDocRelationTypesForProposalType>(_GetAllowedProposalDocRelationTypesForProposalType_QNAME, GetAllowedProposalDocRelationTypesForProposalType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProposalDocRelationsByIdListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "getProposalDocRelationsByIdListResponse")
    public JAXBElement<GetProposalDocRelationsByIdListResponse> createGetProposalDocRelationsByIdListResponse(GetProposalDocRelationsByIdListResponse value) {
        return new JAXBElement<GetProposalDocRelationsByIdListResponse>(_GetProposalDocRelationsByIdListResponse_QNAME, GetProposalDocRelationsByIdListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AlreadyExistsException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "AlreadyExistsException")
    public JAXBElement<AlreadyExistsException> createAlreadyExistsException(AlreadyExistsException value) {
        return new JAXBElement<AlreadyExistsException>(_AlreadyExistsException_QNAME, AlreadyExistsException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProposalTypes }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "getProposalTypes")
    public JAXBElement<GetProposalTypes> createGetProposalTypes(GetProposalTypes value) {
        return new JAXBElement<GetProposalTypes>(_GetProposalTypes_QNAME, GetProposalTypes.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VersionMismatchException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "VersionMismatchException")
    public JAXBElement<VersionMismatchException> createVersionMismatchException(VersionMismatchException value) {
        return new JAXBElement<VersionMismatchException>(_VersionMismatchException_QNAME, VersionMismatchException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OperationFailedException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "OperationFailedException")
    public JAXBElement<OperationFailedException> createOperationFailedException(OperationFailedException value) {
        return new JAXBElement<OperationFailedException>(_OperationFailedException_QNAME, OperationFailedException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetObjectStructure }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "getObjectStructure")
    public JAXBElement<GetObjectStructure> createGetObjectStructure(GetObjectStructure value) {
        return new JAXBElement<GetObjectStructure>(_GetObjectStructure_QNAME, GetObjectStructure.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchCriteriaTypes }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "getSearchCriteriaTypes")
    public JAXBElement<GetSearchCriteriaTypes> createGetSearchCriteriaTypes(GetSearchCriteriaTypes value) {
        return new JAXBElement<GetSearchCriteriaTypes>(_GetSearchCriteriaTypes_QNAME, GetSearchCriteriaTypes.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchCriteriaType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "getSearchCriteriaType")
    public JAXBElement<GetSearchCriteriaType> createGetSearchCriteriaType(GetSearchCriteriaType value) {
        return new JAXBElement<GetSearchCriteriaType>(_GetSearchCriteriaType_QNAME, GetSearchCriteriaType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetObjectStructureResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "getObjectStructureResponse")
    public JAXBElement<GetObjectStructureResponse> createGetObjectStructureResponse(GetObjectStructureResponse value) {
        return new JAXBElement<GetObjectStructureResponse>(_GetObjectStructureResponse_QNAME, GetObjectStructureResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProposalTypeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "getProposalTypeResponse")
    public JAXBElement<GetProposalTypeResponse> createGetProposalTypeResponse(GetProposalTypeResponse value) {
        return new JAXBElement<GetProposalTypeResponse>(_GetProposalTypeResponse_QNAME, GetProposalTypeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteProposal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "deleteProposal")
    public JAXBElement<DeleteProposal> createDeleteProposal(DeleteProposal value) {
        return new JAXBElement<DeleteProposal>(_DeleteProposal_QNAME, DeleteProposal.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProposalTypesForReferenceType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "getProposalTypesForReferenceType")
    public JAXBElement<GetProposalTypesForReferenceType> createGetProposalTypesForReferenceType(GetProposalTypesForReferenceType value) {
        return new JAXBElement<GetProposalTypesForReferenceType>(_GetProposalTypesForReferenceType_QNAME, GetProposalTypesForReferenceType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProposalDocRelationsByDocument }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "getProposalDocRelationsByDocument")
    public JAXBElement<GetProposalDocRelationsByDocument> createGetProposalDocRelationsByDocument(GetProposalDocRelationsByDocument value) {
        return new JAXBElement<GetProposalDocRelationsByDocument>(_GetProposalDocRelationsByDocument_QNAME, GetProposalDocRelationsByDocument.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchTypesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "getSearchTypesResponse")
    public JAXBElement<GetSearchTypesResponse> createGetSearchTypesResponse(GetSearchTypesResponse value) {
        return new JAXBElement<GetSearchTypesResponse>(_GetSearchTypesResponse_QNAME, GetSearchTypesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateProposalResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "validateProposalResponse")
    public JAXBElement<ValidateProposalResponse> createValidateProposalResponse(ValidateProposalResponse value) {
        return new JAXBElement<ValidateProposalResponse>(_ValidateProposalResponse_QNAME, ValidateProposalResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProposalDocRelationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "getProposalDocRelationResponse")
    public JAXBElement<GetProposalDocRelationResponse> createGetProposalDocRelationResponse(GetProposalDocRelationResponse value) {
        return new JAXBElement<GetProposalDocRelationResponse>(_GetProposalDocRelationResponse_QNAME, GetProposalDocRelationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProposalsByReference }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "getProposalsByReference")
    public JAXBElement<GetProposalsByReference> createGetProposalsByReference(GetProposalsByReference value) {
        return new JAXBElement<GetProposalsByReference>(_GetProposalsByReference_QNAME, GetProposalsByReference.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateProposalDocRelation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "validateProposalDocRelation")
    public JAXBElement<ValidateProposalDocRelation> createValidateProposalDocRelation(ValidateProposalDocRelation value) {
        return new JAXBElement<ValidateProposalDocRelation>(_ValidateProposalDocRelation_QNAME, ValidateProposalDocRelation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchResultTypeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "getSearchResultTypeResponse")
    public JAXBElement<GetSearchResultTypeResponse> createGetSearchResultTypeResponse(GetSearchResultTypeResponse value) {
        return new JAXBElement<GetSearchResultTypeResponse>(_GetSearchResultTypeResponse_QNAME, GetSearchResultTypeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetReferenceTypesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "getReferenceTypesResponse")
    public JAXBElement<GetReferenceTypesResponse> createGetReferenceTypesResponse(GetReferenceTypesResponse value) {
        return new JAXBElement<GetReferenceTypesResponse>(_GetReferenceTypesResponse_QNAME, GetReferenceTypesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllowedProposalDocRelationTypesForProposalTypeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "getAllowedProposalDocRelationTypesForProposalTypeResponse")
    public JAXBElement<GetAllowedProposalDocRelationTypesForProposalTypeResponse> createGetAllowedProposalDocRelationTypesForProposalTypeResponse(GetAllowedProposalDocRelationTypesForProposalTypeResponse value) {
        return new JAXBElement<GetAllowedProposalDocRelationTypesForProposalTypeResponse>(_GetAllowedProposalDocRelationTypesForProposalTypeResponse_QNAME, GetAllowedProposalDocRelationTypesForProposalTypeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MissingParameterException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "MissingParameterException")
    public JAXBElement<MissingParameterException> createMissingParameterException(MissingParameterException value) {
        return new JAXBElement<MissingParameterException>(_MissingParameterException_QNAME, MissingParameterException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteProposalDocRelationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "deleteProposalDocRelationResponse")
    public JAXBElement<DeleteProposalDocRelationResponse> createDeleteProposalDocRelationResponse(DeleteProposalDocRelationResponse value) {
        return new JAXBElement<DeleteProposalDocRelationResponse>(_DeleteProposalDocRelationResponse_QNAME, DeleteProposalDocRelationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProposalTypesForReferenceTypeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "getProposalTypesForReferenceTypeResponse")
    public JAXBElement<GetProposalTypesForReferenceTypeResponse> createGetProposalTypesForReferenceTypeResponse(GetProposalTypesForReferenceTypeResponse value) {
        return new JAXBElement<GetProposalTypesForReferenceTypeResponse>(_GetProposalTypesForReferenceTypeResponse_QNAME, GetProposalTypesForReferenceTypeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProposalDocRelationsByIdList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "getProposalDocRelationsByIdList")
    public JAXBElement<GetProposalDocRelationsByIdList> createGetProposalDocRelationsByIdList(GetProposalDocRelationsByIdList value) {
        return new JAXBElement<GetProposalDocRelationsByIdList>(_GetProposalDocRelationsByIdList_QNAME, GetProposalDocRelationsByIdList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProposalDocRelationTypes }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "getProposalDocRelationTypes")
    public JAXBElement<GetProposalDocRelationTypes> createGetProposalDocRelationTypes(GetProposalDocRelationTypes value) {
        return new JAXBElement<GetProposalDocRelationTypes>(_GetProposalDocRelationTypes_QNAME, GetProposalDocRelationTypes.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProposal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "getProposal")
    public JAXBElement<GetProposal> createGetProposal(GetProposal value) {
        return new JAXBElement<GetProposal>(_GetProposal_QNAME, GetProposal.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchCriteriaTypesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "getSearchCriteriaTypesResponse")
    public JAXBElement<GetSearchCriteriaTypesResponse> createGetSearchCriteriaTypesResponse(GetSearchCriteriaTypesResponse value) {
        return new JAXBElement<GetSearchCriteriaTypesResponse>(_GetSearchCriteriaTypesResponse_QNAME, GetSearchCriteriaTypesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProposalsByProposalType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "getProposalsByProposalType")
    public JAXBElement<GetProposalsByProposalType> createGetProposalsByProposalType(GetProposalsByProposalType value) {
        return new JAXBElement<GetProposalsByProposalType>(_GetProposalsByProposalType_QNAME, GetProposalsByProposalType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProposalResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "getProposalResponse")
    public JAXBElement<GetProposalResponse> createGetProposalResponse(GetProposalResponse value) {
        return new JAXBElement<GetProposalResponse>(_GetProposalResponse_QNAME, GetProposalResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProposalDocRelation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "getProposalDocRelation")
    public JAXBElement<GetProposalDocRelation> createGetProposalDocRelation(GetProposalDocRelation value) {
        return new JAXBElement<GetProposalDocRelation>(_GetProposalDocRelation_QNAME, GetProposalDocRelation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateProposal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "validateProposal")
    public JAXBElement<ValidateProposal> createValidateProposal(ValidateProposal value) {
        return new JAXBElement<ValidateProposal>(_ValidateProposal_QNAME, ValidateProposal.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProposalDocRelationTypesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "getProposalDocRelationTypesResponse")
    public JAXBElement<GetProposalDocRelationTypesResponse> createGetProposalDocRelationTypesResponse(GetProposalDocRelationTypesResponse value) {
        return new JAXBElement<GetProposalDocRelationTypesResponse>(_GetProposalDocRelationTypesResponse_QNAME, GetProposalDocRelationTypesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProposalDocRelationsByProposalResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "getProposalDocRelationsByProposalResponse")
    public JAXBElement<GetProposalDocRelationsByProposalResponse> createGetProposalDocRelationsByProposalResponse(GetProposalDocRelationsByProposalResponse value) {
        return new JAXBElement<GetProposalDocRelationsByProposalResponse>(_GetProposalDocRelationsByProposalResponse_QNAME, GetProposalDocRelationsByProposalResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProposalDocRelationsByDocumentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "getProposalDocRelationsByDocumentResponse")
    public JAXBElement<GetProposalDocRelationsByDocumentResponse> createGetProposalDocRelationsByDocumentResponse(GetProposalDocRelationsByDocumentResponse value) {
        return new JAXBElement<GetProposalDocRelationsByDocumentResponse>(_GetProposalDocRelationsByDocumentResponse_QNAME, GetProposalDocRelationsByDocumentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteProposalDocRelation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "deleteProposalDocRelation")
    public JAXBElement<DeleteProposalDocRelation> createDeleteProposalDocRelation(DeleteProposalDocRelation value) {
        return new JAXBElement<DeleteProposalDocRelation>(_DeleteProposalDocRelation_QNAME, DeleteProposalDocRelation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchTypeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "getSearchTypeResponse")
    public JAXBElement<GetSearchTypeResponse> createGetSearchTypeResponse(GetSearchTypeResponse value) {
        return new JAXBElement<GetSearchTypeResponse>(_GetSearchTypeResponse_QNAME, GetSearchTypeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateProposal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "updateProposal")
    public JAXBElement<UpdateProposal> createUpdateProposal(UpdateProposal value) {
        return new JAXBElement<UpdateProposal>(_UpdateProposal_QNAME, UpdateProposal.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InvalidParameterException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "InvalidParameterException")
    public JAXBElement<InvalidParameterException> createInvalidParameterException(InvalidParameterException value) {
        return new JAXBElement<InvalidParameterException>(_InvalidParameterException_QNAME, InvalidParameterException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetObjectTypes }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "getObjectTypes")
    public JAXBElement<GetObjectTypes> createGetObjectTypes(GetObjectTypes value) {
        return new JAXBElement<GetObjectTypes>(_GetObjectTypes_QNAME, GetObjectTypes.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "getSearchType")
    public JAXBElement<GetSearchType> createGetSearchType(GetSearchType value) {
        return new JAXBElement<GetSearchType>(_GetSearchType_QNAME, GetSearchType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PermissionDeniedException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "PermissionDeniedException")
    public JAXBElement<PermissionDeniedException> createPermissionDeniedException(PermissionDeniedException value) {
        return new JAXBElement<PermissionDeniedException>(_PermissionDeniedException_QNAME, PermissionDeniedException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetReferenceTypes }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "getReferenceTypes")
    public JAXBElement<GetReferenceTypes> createGetReferenceTypes(GetReferenceTypes value) {
        return new JAXBElement<GetReferenceTypes>(_GetReferenceTypes_QNAME, GetReferenceTypes.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DataValidationErrorException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "DataValidationErrorException")
    public JAXBElement<DataValidationErrorException> createDataValidationErrorException(DataValidationErrorException value) {
        return new JAXBElement<DataValidationErrorException>(_DataValidationErrorException_QNAME, DataValidationErrorException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetObjectTypesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "getObjectTypesResponse")
    public JAXBElement<GetObjectTypesResponse> createGetObjectTypesResponse(GetObjectTypesResponse value) {
        return new JAXBElement<GetObjectTypesResponse>(_GetObjectTypesResponse_QNAME, GetObjectTypesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProposalType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "getProposalType")
    public JAXBElement<GetProposalType> createGetProposalType(GetProposalType value) {
        return new JAXBElement<GetProposalType>(_GetProposalType_QNAME, GetProposalType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DependentObjectsExistException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "DependentObjectsExistException")
    public JAXBElement<DependentObjectsExistException> createDependentObjectsExistException(DependentObjectsExistException value) {
        return new JAXBElement<DependentObjectsExistException>(_DependentObjectsExistException_QNAME, DependentObjectsExistException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProposalDocRelationsByTypeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "getProposalDocRelationsByTypeResponse")
    public JAXBElement<GetProposalDocRelationsByTypeResponse> createGetProposalDocRelationsByTypeResponse(GetProposalDocRelationsByTypeResponse value) {
        return new JAXBElement<GetProposalDocRelationsByTypeResponse>(_GetProposalDocRelationsByTypeResponse_QNAME, GetProposalDocRelationsByTypeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProposalDocRelationType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "getProposalDocRelationType")
    public JAXBElement<GetProposalDocRelationType> createGetProposalDocRelationType(GetProposalDocRelationType value) {
        return new JAXBElement<GetProposalDocRelationType>(_GetProposalDocRelationType_QNAME, GetProposalDocRelationType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateProposalDocRelationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "createProposalDocRelationResponse")
    public JAXBElement<CreateProposalDocRelationResponse> createCreateProposalDocRelationResponse(CreateProposalDocRelationResponse value) {
        return new JAXBElement<CreateProposalDocRelationResponse>(_CreateProposalDocRelationResponse_QNAME, CreateProposalDocRelationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProposalsByState }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "getProposalsByState")
    public JAXBElement<GetProposalsByState> createGetProposalsByState(GetProposalsByState value) {
        return new JAXBElement<GetProposalsByState>(_GetProposalsByState_QNAME, GetProposalsByState.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProposalsByStateResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "getProposalsByStateResponse")
    public JAXBElement<GetProposalsByStateResponse> createGetProposalsByStateResponse(GetProposalsByStateResponse value) {
        return new JAXBElement<GetProposalsByStateResponse>(_GetProposalsByStateResponse_QNAME, GetProposalsByStateResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchTypesByCriteriaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "getSearchTypesByCriteriaResponse")
    public JAXBElement<GetSearchTypesByCriteriaResponse> createGetSearchTypesByCriteriaResponse(GetSearchTypesByCriteriaResponse value) {
        return new JAXBElement<GetSearchTypesByCriteriaResponse>(_GetSearchTypesByCriteriaResponse_QNAME, GetSearchTypesByCriteriaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProposalDocRelationTypeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "getProposalDocRelationTypeResponse")
    public JAXBElement<GetProposalDocRelationTypeResponse> createGetProposalDocRelationTypeResponse(GetProposalDocRelationTypeResponse value) {
        return new JAXBElement<GetProposalDocRelationTypeResponse>(_GetProposalDocRelationTypeResponse_QNAME, GetProposalDocRelationTypeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProposalsByIdList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "getProposalsByIdList")
    public JAXBElement<GetProposalsByIdList> createGetProposalsByIdList(GetProposalsByIdList value) {
        return new JAXBElement<GetProposalsByIdList>(_GetProposalsByIdList_QNAME, GetProposalsByIdList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProposalsByReferenceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "getProposalsByReferenceResponse")
    public JAXBElement<GetProposalsByReferenceResponse> createGetProposalsByReferenceResponse(GetProposalsByReferenceResponse value) {
        return new JAXBElement<GetProposalsByReferenceResponse>(_GetProposalsByReferenceResponse_QNAME, GetProposalsByReferenceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "searchResponse")
    public JAXBElement<SearchResponse> createSearchResponse(SearchResponse value) {
        return new JAXBElement<SearchResponse>(_SearchResponse_QNAME, SearchResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchTypes }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/proposal", name = "getSearchTypes")
    public JAXBElement<GetSearchTypes> createGetSearchTypes(GetSearchTypes value) {
        return new JAXBElement<GetSearchTypes>(_GetSearchTypes_QNAME, GetSearchTypes.class, null, value);
    }

}
