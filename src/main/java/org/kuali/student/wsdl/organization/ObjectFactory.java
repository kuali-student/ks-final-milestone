
package org.kuali.student.wsdl.organization;

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
import org.kuali.student.wsdl.exceptions.DoesNotExistException;
import org.kuali.student.wsdl.exceptions.InvalidParameterException;
import org.kuali.student.wsdl.exceptions.MissingParameterException;
import org.kuali.student.wsdl.exceptions.OperationFailedException;
import org.kuali.student.wsdl.exceptions.PermissionDeniedException;
import org.kuali.student.wsdl.exceptions.VersionMismatchException;
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
 * generated in the org.kuali.student.wsdl.organization package. 
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

    private final static QName _GetAllOrgPersonRelationsByOrgResponse_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getAllOrgPersonRelationsByOrgResponse");
    private final static QName _GetOrgPersonRelationTypes_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getOrgPersonRelationTypes");
    private final static QName _GetOrgOrgRelationTypes_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getOrgOrgRelationTypes");
    private final static QName _UpdateOrgPersonRelation_QNAME = new QName("http://student.kuali.org/wsdl/organization", "updateOrgPersonRelation");
    private final static QName _GetOrgPersonRelationType_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getOrgPersonRelationType");
    private final static QName _IsDescendantResponse_QNAME = new QName("http://student.kuali.org/wsdl/organization", "isDescendantResponse");
    private final static QName _RemovePositionRestrictionFromOrgResponse_QNAME = new QName("http://student.kuali.org/wsdl/organization", "removePositionRestrictionFromOrgResponse");
    private final static QName _AddPositionRestrictionToOrgResponse_QNAME = new QName("http://student.kuali.org/wsdl/organization", "addPositionRestrictionToOrgResponse");
    private final static QName _RemoveOrgOrgRelation_QNAME = new QName("http://student.kuali.org/wsdl/organization", "removeOrgOrgRelation");
    private final static QName _GetSearchTypesResponse_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getSearchTypesResponse");
    private final static QName _GetOrganizationResponse_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getOrganizationResponse");
    private final static QName _GetPersonIdsForOrgByRelationTypeResponse_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getPersonIdsForOrgByRelationTypeResponse");
    private final static QName _GetOrgPersonRelationsByOrg_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getOrgPersonRelationsByOrg");
    private final static QName _ValidateOrgOrgRelationResponse_QNAME = new QName("http://student.kuali.org/wsdl/organization", "validateOrgOrgRelationResponse");
    private final static QName _GetOrgPersonRelationTypesResponse_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getOrgPersonRelationTypesResponse");
    private final static QName _GetOrgPersonRelationsByPerson_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getOrgPersonRelationsByPerson");
    private final static QName _GetOrgTypes_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getOrgTypes");
    private final static QName _GetAllOrgPersonRelationsByPersonResponse_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getAllOrgPersonRelationsByPersonResponse");
    private final static QName _GetOrgOrgRelationTypesForOrgHierarchy_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getOrgOrgRelationTypesForOrgHierarchy");
    private final static QName _ValidateOrgOrgRelation_QNAME = new QName("http://student.kuali.org/wsdl/organization", "validateOrgOrgRelation");
    private final static QName _GetOrgType_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getOrgType");
    private final static QName _GetObjectStructureResponse_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getObjectStructureResponse");
    private final static QName _GetOrgOrgRelationTypesForOrgTypeResponse_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getOrgOrgRelationTypesForOrgTypeResponse");
    private final static QName _GetOrgOrgRelationTypesForOrgHierarchyResponse_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getOrgOrgRelationTypesForOrgHierarchyResponse");
    private final static QName _VersionMismatchException_QNAME = new QName("http://student.kuali.org/wsdl/organization", "VersionMismatchException");
    private final static QName _GetOrgPersonRelationTypesForOrgType_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getOrgPersonRelationTypesForOrgType");
    private final static QName _OperationFailedException_QNAME = new QName("http://student.kuali.org/wsdl/organization", "OperationFailedException");
    private final static QName _GetAllOrgPersonRelationsByPerson_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getAllOrgPersonRelationsByPerson");
    private final static QName _AlreadyExistsException_QNAME = new QName("http://student.kuali.org/wsdl/organization", "AlreadyExistsException");
    private final static QName _GetObjectStructure_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getObjectStructure");
    private final static QName _GetSearchCriteriaTypes_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getSearchCriteriaTypes");
    private final static QName _GetAllDescendantsResponse_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getAllDescendantsResponse");
    private final static QName _GetSearchCriteriaType_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getSearchCriteriaType");
    private final static QName _GetAllAncestors_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getAllAncestors");
    private final static QName _GetPersonIdsForOrgByRelationType_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getPersonIdsForOrgByRelationType");
    private final static QName _GetSearchTypesByResultResponse_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getSearchTypesByResultResponse");
    private final static QName _CreateOrganizationResponse_QNAME = new QName("http://student.kuali.org/wsdl/organization", "createOrganizationResponse");
    private final static QName _UpdateOrganization_QNAME = new QName("http://student.kuali.org/wsdl/organization", "updateOrganization");
    private final static QName _GetSearchTypesByCriteria_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getSearchTypesByCriteria");
    private final static QName _GetSearchTypesByResult_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getSearchTypesByResult");
    private final static QName _GetOrgTypeResponse_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getOrgTypeResponse");
    private final static QName _DeleteOrganizationResponse_QNAME = new QName("http://student.kuali.org/wsdl/organization", "deleteOrganizationResponse");
    private final static QName _HasOrgPersonRelationResponse_QNAME = new QName("http://student.kuali.org/wsdl/organization", "hasOrgPersonRelationResponse");
    private final static QName _CreateOrgOrgRelation_QNAME = new QName("http://student.kuali.org/wsdl/organization", "createOrgOrgRelation");
    private final static QName _GetOrgPersonRelationsByPersonResponse_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getOrgPersonRelationsByPersonResponse");
    private final static QName _ValidateOrgPositionRestriction_QNAME = new QName("http://student.kuali.org/wsdl/organization", "validateOrgPositionRestriction");
    private final static QName _CreateOrgPersonRelationResponse_QNAME = new QName("http://student.kuali.org/wsdl/organization", "createOrgPersonRelationResponse");
    private final static QName _HasOrgOrgRelation_QNAME = new QName("http://student.kuali.org/wsdl/organization", "hasOrgOrgRelation");
    private final static QName _ValidateOrgPositionRestrictionResponse_QNAME = new QName("http://student.kuali.org/wsdl/organization", "validateOrgPositionRestrictionResponse");
    private final static QName _DeleteOrganization_QNAME = new QName("http://student.kuali.org/wsdl/organization", "deleteOrganization");
    private final static QName _GetOrgPersonRelationTypeResponse_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getOrgPersonRelationTypeResponse");
    private final static QName _GetAllOrgPersonRelationsByOrg_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getAllOrgPersonRelationsByOrg");
    private final static QName _GetPositionRestrictionsByOrgResponse_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getPositionRestrictionsByOrgResponse");
    private final static QName _GetOrgPersonRelationsByOrgResponse_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getOrgPersonRelationsByOrgResponse");
    private final static QName _GetOrgOrgRelationTypeResponse_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getOrgOrgRelationTypeResponse");
    private final static QName _GetSearchResultTypes_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getSearchResultTypes");
    private final static QName _ValidateOrgPersonRelation_QNAME = new QName("http://student.kuali.org/wsdl/organization", "validateOrgPersonRelation");
    private final static QName _GetSearchResultType_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getSearchResultType");
    private final static QName _DoesNotExistException_QNAME = new QName("http://student.kuali.org/wsdl/organization", "DoesNotExistException");
    private final static QName _GetOrgOrgRelationsByIdList_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getOrgOrgRelationsByIdList");
    private final static QName _GetOrgHierarchies_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getOrgHierarchies");
    private final static QName _Search_QNAME = new QName("http://student.kuali.org/wsdl/organization", "search");
    private final static QName _GetSearchResultTypesResponse_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getSearchResultTypesResponse");
    private final static QName _GetSearchCriteriaTypeResponse_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getSearchCriteriaTypeResponse");
    private final static QName _RemoveOrgPersonRelation_QNAME = new QName("http://student.kuali.org/wsdl/organization", "removeOrgPersonRelation");
    private final static QName _GetOrgOrgRelationType_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getOrgOrgRelationType");
    private final static QName _GetAllDescendants_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getAllDescendants");
    private final static QName _SearchResponse_QNAME = new QName("http://student.kuali.org/wsdl/organization", "searchResponse");
    private final static QName _GetOrgPersonRelationsByIdList_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getOrgPersonRelationsByIdList");
    private final static QName _GetSearchTypes_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getSearchTypes");
    private final static QName _ValidateOrgPersonRelationResponse_QNAME = new QName("http://student.kuali.org/wsdl/organization", "validateOrgPersonRelationResponse");
    private final static QName _ValidateOrg_QNAME = new QName("http://student.kuali.org/wsdl/organization", "validateOrg");
    private final static QName _GetOrgOrgRelationResponse_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getOrgOrgRelationResponse");
    private final static QName _GetOrgPersonRelation_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getOrgPersonRelation");
    private final static QName _RemoveOrgOrgRelationResponse_QNAME = new QName("http://student.kuali.org/wsdl/organization", "removeOrgOrgRelationResponse");
    private final static QName _GetOrgOrgRelationsByOrg_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getOrgOrgRelationsByOrg");
    private final static QName _GetOrgOrgRelationTypesResponse_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getOrgOrgRelationTypesResponse");
    private final static QName _GetSearchTypesByCriteriaResponse_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getSearchTypesByCriteriaResponse");
    private final static QName _GetOrgOrgRelationsByIdListResponse_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getOrgOrgRelationsByIdListResponse");
    private final static QName _RemoveOrgPersonRelationResponse_QNAME = new QName("http://student.kuali.org/wsdl/organization", "removeOrgPersonRelationResponse");
    private final static QName _UpdateOrganizationResponse_QNAME = new QName("http://student.kuali.org/wsdl/organization", "updateOrganizationResponse");
    private final static QName _CreateOrgPersonRelation_QNAME = new QName("http://student.kuali.org/wsdl/organization", "createOrgPersonRelation");
    private final static QName _GetOrgOrgRelationTypesForOrgType_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getOrgOrgRelationTypesForOrgType");
    private final static QName _GetAllAncestorsResponse_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getAllAncestorsResponse");
    private final static QName _GetOrgOrgRelationsByOrgResponse_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getOrgOrgRelationsByOrgResponse");
    private final static QName _GetOrganization_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getOrganization");
    private final static QName _GetObjectTypesResponse_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getObjectTypesResponse");
    private final static QName _UpdatePositionRestrictionForOrg_QNAME = new QName("http://student.kuali.org/wsdl/organization", "updatePositionRestrictionForOrg");
    private final static QName _GetOrgOrgRelationsByRelatedOrgResponse_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getOrgOrgRelationsByRelatedOrgResponse");
    private final static QName _GetOrgTreeResponse_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getOrgTreeResponse");
    private final static QName _GetOrgPersonRelationsByIdListResponse_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getOrgPersonRelationsByIdListResponse");
    private final static QName _CreateOrgOrgRelationResponse_QNAME = new QName("http://student.kuali.org/wsdl/organization", "createOrgOrgRelationResponse");
    private final static QName _GetSearchType_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getSearchType");
    private final static QName _PermissionDeniedException_QNAME = new QName("http://student.kuali.org/wsdl/organization", "PermissionDeniedException");
    private final static QName _GetOrgOrgRelationsByRelatedOrg_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getOrgOrgRelationsByRelatedOrg");
    private final static QName _RemovePositionRestrictionFromOrg_QNAME = new QName("http://student.kuali.org/wsdl/organization", "removePositionRestrictionFromOrg");
    private final static QName _GetOrgPersonRelationTypesForOrgTypeResponse_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getOrgPersonRelationTypesForOrgTypeResponse");
    private final static QName _GetOrgHierarchyResponse_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getOrgHierarchyResponse");
    private final static QName _DataValidationErrorException_QNAME = new QName("http://student.kuali.org/wsdl/organization", "DataValidationErrorException");
    private final static QName _GetOrgTree_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getOrgTree");
    private final static QName _UpdateOrgOrgRelationResponse_QNAME = new QName("http://student.kuali.org/wsdl/organization", "updateOrgOrgRelationResponse");
    private final static QName _GetSearchTypeResponse_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getSearchTypeResponse");
    private final static QName _GetObjectTypes_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getObjectTypes");
    private final static QName _GetOrgHierarchiesResponse_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getOrgHierarchiesResponse");
    private final static QName _GetOrgHierarchy_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getOrgHierarchy");
    private final static QName _InvalidParameterException_QNAME = new QName("http://student.kuali.org/wsdl/organization", "InvalidParameterException");
    private final static QName _IsDescendant_QNAME = new QName("http://student.kuali.org/wsdl/organization", "isDescendant");
    private final static QName _HasOrgPersonRelation_QNAME = new QName("http://student.kuali.org/wsdl/organization", "hasOrgPersonRelation");
    private final static QName _GetPositionRestrictionsByOrg_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getPositionRestrictionsByOrg");
    private final static QName _UpdateOrgPersonRelationResponse_QNAME = new QName("http://student.kuali.org/wsdl/organization", "updateOrgPersonRelationResponse");
    private final static QName _AddPositionRestrictionToOrg_QNAME = new QName("http://student.kuali.org/wsdl/organization", "addPositionRestrictionToOrg");
    private final static QName _GetOrgTypesResponse_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getOrgTypesResponse");
    private final static QName _UpdatePositionRestrictionForOrgResponse_QNAME = new QName("http://student.kuali.org/wsdl/organization", "updatePositionRestrictionForOrgResponse");
    private final static QName _ValidateOrgResponse_QNAME = new QName("http://student.kuali.org/wsdl/organization", "validateOrgResponse");
    private final static QName _GetOrgPersonRelationResponse_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getOrgPersonRelationResponse");
    private final static QName _CreateOrganization_QNAME = new QName("http://student.kuali.org/wsdl/organization", "createOrganization");
    private final static QName _GetOrganizationsByIdList_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getOrganizationsByIdList");
    private final static QName _GetSearchCriteriaTypesResponse_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getSearchCriteriaTypesResponse");
    private final static QName _GetSearchResultTypeResponse_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getSearchResultTypeResponse");
    private final static QName _GetOrgOrgRelation_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getOrgOrgRelation");
    private final static QName _HasOrgOrgRelationResponse_QNAME = new QName("http://student.kuali.org/wsdl/organization", "hasOrgOrgRelationResponse");
    private final static QName _GetOrganizationsByIdListResponse_QNAME = new QName("http://student.kuali.org/wsdl/organization", "getOrganizationsByIdListResponse");
    private final static QName _MissingParameterException_QNAME = new QName("http://student.kuali.org/wsdl/organization", "MissingParameterException");
    private final static QName _UpdateOrgOrgRelation_QNAME = new QName("http://student.kuali.org/wsdl/organization", "updateOrgOrgRelation");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.kuali.student.wsdl.organization
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetAllAncestorsResponse }
     * 
     */
    public GetAllAncestorsResponse createGetAllAncestorsResponse() {
        return new GetAllAncestorsResponse();
    }

    /**
     * Create an instance of {@link Attribute }
     * 
     */
    public Attribute createAttribute() {
        return new Attribute();
    }

    /**
     * Create an instance of {@link ValidateOrgPositionRestrictionResponse }
     * 
     */
    public ValidateOrgPositionRestrictionResponse createValidateOrgPositionRestrictionResponse() {
        return new ValidateOrgPositionRestrictionResponse();
    }

    /**
     * Create an instance of {@link IsDescendantResponse }
     * 
     */
    public IsDescendantResponse createIsDescendantResponse() {
        return new IsDescendantResponse();
    }

    /**
     * Create an instance of {@link GetAllOrgPersonRelationsByOrgResponse }
     * 
     */
    public GetAllOrgPersonRelationsByOrgResponse createGetAllOrgPersonRelationsByOrgResponse() {
        return new GetAllOrgPersonRelationsByOrgResponse();
    }

    /**
     * Create an instance of {@link ValidationResultInfo }
     * 
     */
    public ValidationResultInfo createValidationResultInfo() {
        return new ValidationResultInfo();
    }

    /**
     * Create an instance of {@link GetOrgOrgRelationsByRelatedOrg }
     * 
     */
    public GetOrgOrgRelationsByRelatedOrg createGetOrgOrgRelationsByRelatedOrg() {
        return new GetOrgOrgRelationsByRelatedOrg();
    }

    /**
     * Create an instance of {@link GetOrgPersonRelation }
     * 
     */
    public GetOrgPersonRelation createGetOrgPersonRelation() {
        return new GetOrgPersonRelation();
    }

    /**
     * Create an instance of {@link GetOrgHierarchy }
     * 
     */
    public GetOrgHierarchy createGetOrgHierarchy() {
        return new GetOrgHierarchy();
    }

    /**
     * Create an instance of {@link AddPositionRestrictionToOrgResponse }
     * 
     */
    public AddPositionRestrictionToOrgResponse createAddPositionRestrictionToOrgResponse() {
        return new AddPositionRestrictionToOrgResponse();
    }

    /**
     * Create an instance of {@link GetOrgHierarchiesResponse }
     * 
     */
    public GetOrgHierarchiesResponse createGetOrgHierarchiesResponse() {
        return new GetOrgHierarchiesResponse();
    }

    /**
     * Create an instance of {@link GetOrgOrgRelationTypes }
     * 
     */
    public GetOrgOrgRelationTypes createGetOrgOrgRelationTypes() {
        return new GetOrgOrgRelationTypes();
    }

    /**
     * Create an instance of {@link CreateOrgOrgRelation }
     * 
     */
    public CreateOrgOrgRelation createCreateOrgOrgRelation() {
        return new CreateOrgOrgRelation();
    }

    /**
     * Create an instance of {@link GetOrgOrgRelationsByRelatedOrgResponse }
     * 
     */
    public GetOrgOrgRelationsByRelatedOrgResponse createGetOrgOrgRelationsByRelatedOrgResponse() {
        return new GetOrgOrgRelationsByRelatedOrgResponse();
    }

    /**
     * Create an instance of {@link GetOrgTreeResponse }
     * 
     */
    public GetOrgTreeResponse createGetOrgTreeResponse() {
        return new GetOrgTreeResponse();
    }

    /**
     * Create an instance of {@link GetOrgHierarchies }
     * 
     */
    public GetOrgHierarchies createGetOrgHierarchies() {
        return new GetOrgHierarchies();
    }

    /**
     * Create an instance of {@link GetOrgOrgRelationsByOrgResponse }
     * 
     */
    public GetOrgOrgRelationsByOrgResponse createGetOrgOrgRelationsByOrgResponse() {
        return new GetOrgOrgRelationsByOrgResponse();
    }

    /**
     * Create an instance of {@link AddPositionRestrictionToOrg }
     * 
     */
    public AddPositionRestrictionToOrg createAddPositionRestrictionToOrg() {
        return new AddPositionRestrictionToOrg();
    }

    /**
     * Create an instance of {@link GetAllAncestors }
     * 
     */
    public GetAllAncestors createGetAllAncestors() {
        return new GetAllAncestors();
    }

    /**
     * Create an instance of {@link UpdateOrgPersonRelation }
     * 
     */
    public UpdateOrgPersonRelation createUpdateOrgPersonRelation() {
        return new UpdateOrgPersonRelation();
    }

    /**
     * Create an instance of {@link GetOrgOrgRelationTypesForOrgHierarchy }
     * 
     */
    public GetOrgOrgRelationTypesForOrgHierarchy createGetOrgOrgRelationTypesForOrgHierarchy() {
        return new GetOrgOrgRelationTypesForOrgHierarchy();
    }

    /**
     * Create an instance of {@link RemoveOrgPersonRelationResponse }
     * 
     */
    public RemoveOrgPersonRelationResponse createRemoveOrgPersonRelationResponse() {
        return new RemoveOrgPersonRelationResponse();
    }

    /**
     * Create an instance of {@link GetOrgTypes }
     * 
     */
    public GetOrgTypes createGetOrgTypes() {
        return new GetOrgTypes();
    }

    /**
     * Create an instance of {@link OrgOrgRelationTypeInfo }
     * 
     */
    public OrgOrgRelationTypeInfo createOrgOrgRelationTypeInfo() {
        return new OrgOrgRelationTypeInfo();
    }

    /**
     * Create an instance of {@link OrgTypeInfo }
     * 
     */
    public OrgTypeInfo createOrgTypeInfo() {
        return new OrgTypeInfo();
    }

    /**
     * Create an instance of {@link TimeAmountInfo }
     * 
     */
    public TimeAmountInfo createTimeAmountInfo() {
        return new TimeAmountInfo();
    }

    /**
     * Create an instance of {@link GetOrganizationResponse }
     * 
     */
    public GetOrganizationResponse createGetOrganizationResponse() {
        return new GetOrganizationResponse();
    }

    /**
     * Create an instance of {@link IsDescendant }
     * 
     */
    public IsDescendant createIsDescendant() {
        return new IsDescendant();
    }

    /**
     * Create an instance of {@link GetOrgPersonRelationsByPersonResponse }
     * 
     */
    public GetOrgPersonRelationsByPersonResponse createGetOrgPersonRelationsByPersonResponse() {
        return new GetOrgPersonRelationsByPersonResponse();
    }

    /**
     * Create an instance of {@link GetOrgPersonRelationsByOrgResponse }
     * 
     */
    public GetOrgPersonRelationsByOrgResponse createGetOrgPersonRelationsByOrgResponse() {
        return new GetOrgPersonRelationsByOrgResponse();
    }

    /**
     * Create an instance of {@link DeleteOrganizationResponse }
     * 
     */
    public DeleteOrganizationResponse createDeleteOrganizationResponse() {
        return new DeleteOrganizationResponse();
    }

    /**
     * Create an instance of {@link RemoveOrgOrgRelationResponse }
     * 
     */
    public RemoveOrgOrgRelationResponse createRemoveOrgOrgRelationResponse() {
        return new RemoveOrgOrgRelationResponse();
    }

    /**
     * Create an instance of {@link RemovePositionRestrictionFromOrgResponse }
     * 
     */
    public RemovePositionRestrictionFromOrgResponse createRemovePositionRestrictionFromOrgResponse() {
        return new RemovePositionRestrictionFromOrgResponse();
    }

    /**
     * Create an instance of {@link CreateOrgPersonRelationResponse }
     * 
     */
    public CreateOrgPersonRelationResponse createCreateOrgPersonRelationResponse() {
        return new CreateOrgPersonRelationResponse();
    }

    /**
     * Create an instance of {@link ValidateOrgResponse }
     * 
     */
    public ValidateOrgResponse createValidateOrgResponse() {
        return new ValidateOrgResponse();
    }

    /**
     * Create an instance of {@link OrgPositionRestrictionInfo }
     * 
     */
    public OrgPositionRestrictionInfo createOrgPositionRestrictionInfo() {
        return new OrgPositionRestrictionInfo();
    }

    /**
     * Create an instance of {@link ValidateOrgOrgRelationResponse }
     * 
     */
    public ValidateOrgOrgRelationResponse createValidateOrgOrgRelationResponse() {
        return new ValidateOrgOrgRelationResponse();
    }

    /**
     * Create an instance of {@link GetOrgHierarchyResponse }
     * 
     */
    public GetOrgHierarchyResponse createGetOrgHierarchyResponse() {
        return new GetOrgHierarchyResponse();
    }

    /**
     * Create an instance of {@link GetOrganization }
     * 
     */
    public GetOrganization createGetOrganization() {
        return new GetOrganization();
    }

    /**
     * Create an instance of {@link GetAllDescendantsResponse }
     * 
     */
    public GetAllDescendantsResponse createGetAllDescendantsResponse() {
        return new GetAllDescendantsResponse();
    }

    /**
     * Create an instance of {@link CreateOrganizationResponse }
     * 
     */
    public CreateOrganizationResponse createCreateOrganizationResponse() {
        return new CreateOrganizationResponse();
    }

    /**
     * Create an instance of {@link GetAllDescendants }
     * 
     */
    public GetAllDescendants createGetAllDescendants() {
        return new GetAllDescendants();
    }

    /**
     * Create an instance of {@link GetOrganizationsByIdList }
     * 
     */
    public GetOrganizationsByIdList createGetOrganizationsByIdList() {
        return new GetOrganizationsByIdList();
    }

    /**
     * Create an instance of {@link UpdateOrgPersonRelationResponse }
     * 
     */
    public UpdateOrgPersonRelationResponse createUpdateOrgPersonRelationResponse() {
        return new UpdateOrgPersonRelationResponse();
    }

    /**
     * Create an instance of {@link GetOrgPersonRelationsByOrg }
     * 
     */
    public GetOrgPersonRelationsByOrg createGetOrgPersonRelationsByOrg() {
        return new GetOrgPersonRelationsByOrg();
    }

    /**
     * Create an instance of {@link HasOrgPersonRelation }
     * 
     */
    public HasOrgPersonRelation createHasOrgPersonRelation() {
        return new HasOrgPersonRelation();
    }

    /**
     * Create an instance of {@link GetOrgOrgRelationTypesForOrgHierarchyResponse }
     * 
     */
    public GetOrgOrgRelationTypesForOrgHierarchyResponse createGetOrgOrgRelationTypesForOrgHierarchyResponse() {
        return new GetOrgOrgRelationTypesForOrgHierarchyResponse();
    }

    /**
     * Create an instance of {@link GetOrgOrgRelationTypesForOrgTypeResponse }
     * 
     */
    public GetOrgOrgRelationTypesForOrgTypeResponse createGetOrgOrgRelationTypesForOrgTypeResponse() {
        return new GetOrgOrgRelationTypesForOrgTypeResponse();
    }

    /**
     * Create an instance of {@link ValidateOrgPositionRestriction }
     * 
     */
    public ValidateOrgPositionRestriction createValidateOrgPositionRestriction() {
        return new ValidateOrgPositionRestriction();
    }

    /**
     * Create an instance of {@link GetOrgOrgRelationsByIdList }
     * 
     */
    public GetOrgOrgRelationsByIdList createGetOrgOrgRelationsByIdList() {
        return new GetOrgOrgRelationsByIdList();
    }

    /**
     * Create an instance of {@link OrgHierarchyInfo }
     * 
     */
    public OrgHierarchyInfo createOrgHierarchyInfo() {
        return new OrgHierarchyInfo();
    }

    /**
     * Create an instance of {@link UpdateOrgOrgRelation }
     * 
     */
    public UpdateOrgOrgRelation createUpdateOrgOrgRelation() {
        return new UpdateOrgOrgRelation();
    }

    /**
     * Create an instance of {@link HasOrgPersonRelationResponse }
     * 
     */
    public HasOrgPersonRelationResponse createHasOrgPersonRelationResponse() {
        return new HasOrgPersonRelationResponse();
    }

    /**
     * Create an instance of {@link GetOrgPersonRelationsByIdList }
     * 
     */
    public GetOrgPersonRelationsByIdList createGetOrgPersonRelationsByIdList() {
        return new GetOrgPersonRelationsByIdList();
    }

    /**
     * Create an instance of {@link SearchResponse }
     * 
     */
    public SearchResponse createSearchResponse() {
        return new SearchResponse();
    }

    /**
     * Create an instance of {@link GetOrgOrgRelationsByIdListResponse }
     * 
     */
    public GetOrgOrgRelationsByIdListResponse createGetOrgOrgRelationsByIdListResponse() {
        return new GetOrgOrgRelationsByIdListResponse();
    }

    /**
     * Create an instance of {@link CreateOrgOrgRelationResponse }
     * 
     */
    public CreateOrgOrgRelationResponse createCreateOrgOrgRelationResponse() {
        return new CreateOrgOrgRelationResponse();
    }

    /**
     * Create an instance of {@link GetOrgOrgRelationTypeResponse }
     * 
     */
    public GetOrgOrgRelationTypeResponse createGetOrgOrgRelationTypeResponse() {
        return new GetOrgOrgRelationTypeResponse();
    }

    /**
     * Create an instance of {@link OrgInfo }
     * 
     */
    public OrgInfo createOrgInfo() {
        return new OrgInfo();
    }

    /**
     * Create an instance of {@link GetOrgOrgRelationResponse }
     * 
     */
    public GetOrgOrgRelationResponse createGetOrgOrgRelationResponse() {
        return new GetOrgOrgRelationResponse();
    }

    /**
     * Create an instance of {@link GetOrgType }
     * 
     */
    public GetOrgType createGetOrgType() {
        return new GetOrgType();
    }

    /**
     * Create an instance of {@link GetOrgPersonRelationsByIdListResponse }
     * 
     */
    public GetOrgPersonRelationsByIdListResponse createGetOrgPersonRelationsByIdListResponse() {
        return new GetOrgPersonRelationsByIdListResponse();
    }

    /**
     * Create an instance of {@link GetPositionRestrictionsByOrgResponse }
     * 
     */
    public GetPositionRestrictionsByOrgResponse createGetPositionRestrictionsByOrgResponse() {
        return new GetPositionRestrictionsByOrgResponse();
    }

    /**
     * Create an instance of {@link MetaInfo }
     * 
     */
    public MetaInfo createMetaInfo() {
        return new MetaInfo();
    }

    /**
     * Create an instance of {@link GetAllOrgPersonRelationsByPersonResponse }
     * 
     */
    public GetAllOrgPersonRelationsByPersonResponse createGetAllOrgPersonRelationsByPersonResponse() {
        return new GetAllOrgPersonRelationsByPersonResponse();
    }

    /**
     * Create an instance of {@link HasOrgOrgRelationResponse }
     * 
     */
    public HasOrgOrgRelationResponse createHasOrgOrgRelationResponse() {
        return new HasOrgOrgRelationResponse();
    }

    /**
     * Create an instance of {@link GetOrgTree }
     * 
     */
    public GetOrgTree createGetOrgTree() {
        return new GetOrgTree();
    }

    /**
     * Create an instance of {@link Search }
     * 
     */
    public Search createSearch() {
        return new Search();
    }

    /**
     * Create an instance of {@link ValidateOrgPersonRelationResponse }
     * 
     */
    public ValidateOrgPersonRelationResponse createValidateOrgPersonRelationResponse() {
        return new ValidateOrgPersonRelationResponse();
    }

    /**
     * Create an instance of {@link OrgPersonRelationTypeInfo }
     * 
     */
    public OrgPersonRelationTypeInfo createOrgPersonRelationTypeInfo() {
        return new OrgPersonRelationTypeInfo();
    }

    /**
     * Create an instance of {@link GetOrgOrgRelationTypesResponse }
     * 
     */
    public GetOrgOrgRelationTypesResponse createGetOrgOrgRelationTypesResponse() {
        return new GetOrgOrgRelationTypesResponse();
    }

    /**
     * Create an instance of {@link UpdateOrgOrgRelationResponse }
     * 
     */
    public UpdateOrgOrgRelationResponse createUpdateOrgOrgRelationResponse() {
        return new UpdateOrgOrgRelationResponse();
    }

    /**
     * Create an instance of {@link GetOrgPersonRelationTypeResponse }
     * 
     */
    public GetOrgPersonRelationTypeResponse createGetOrgPersonRelationTypeResponse() {
        return new GetOrgPersonRelationTypeResponse();
    }

    /**
     * Create an instance of {@link UpdatePositionRestrictionForOrg }
     * 
     */
    public UpdatePositionRestrictionForOrg createUpdatePositionRestrictionForOrg() {
        return new UpdatePositionRestrictionForOrg();
    }

    /**
     * Create an instance of {@link GetOrgOrgRelationsByOrg }
     * 
     */
    public GetOrgOrgRelationsByOrg createGetOrgOrgRelationsByOrg() {
        return new GetOrgOrgRelationsByOrg();
    }

    /**
     * Create an instance of {@link HasOrgOrgRelation }
     * 
     */
    public HasOrgOrgRelation createHasOrgOrgRelation() {
        return new HasOrgOrgRelation();
    }

    /**
     * Create an instance of {@link GetOrgOrgRelationTypesForOrgType }
     * 
     */
    public GetOrgOrgRelationTypesForOrgType createGetOrgOrgRelationTypesForOrgType() {
        return new GetOrgOrgRelationTypesForOrgType();
    }

    /**
     * Create an instance of {@link GetOrgPersonRelationResponse }
     * 
     */
    public GetOrgPersonRelationResponse createGetOrgPersonRelationResponse() {
        return new GetOrgPersonRelationResponse();
    }

    /**
     * Create an instance of {@link CreateOrganization }
     * 
     */
    public CreateOrganization createCreateOrganization() {
        return new CreateOrganization();
    }

    /**
     * Create an instance of {@link UpdateOrganization }
     * 
     */
    public UpdateOrganization createUpdateOrganization() {
        return new UpdateOrganization();
    }

    /**
     * Create an instance of {@link GetOrgPersonRelationTypesForOrgTypeResponse }
     * 
     */
    public GetOrgPersonRelationTypesForOrgTypeResponse createGetOrgPersonRelationTypesForOrgTypeResponse() {
        return new GetOrgPersonRelationTypesForOrgTypeResponse();
    }

    /**
     * Create an instance of {@link OrgPersonRelationInfo }
     * 
     */
    public OrgPersonRelationInfo createOrgPersonRelationInfo() {
        return new OrgPersonRelationInfo();
    }

    /**
     * Create an instance of {@link GetOrgPersonRelationsByPerson }
     * 
     */
    public GetOrgPersonRelationsByPerson createGetOrgPersonRelationsByPerson() {
        return new GetOrgPersonRelationsByPerson();
    }

    /**
     * Create an instance of {@link UpdateOrganizationResponse }
     * 
     */
    public UpdateOrganizationResponse createUpdateOrganizationResponse() {
        return new UpdateOrganizationResponse();
    }

    /**
     * Create an instance of {@link GetOrgPersonRelationTypesForOrgType }
     * 
     */
    public GetOrgPersonRelationTypesForOrgType createGetOrgPersonRelationTypesForOrgType() {
        return new GetOrgPersonRelationTypesForOrgType();
    }

    /**
     * Create an instance of {@link GetOrgPersonRelationTypesResponse }
     * 
     */
    public GetOrgPersonRelationTypesResponse createGetOrgPersonRelationTypesResponse() {
        return new GetOrgPersonRelationTypesResponse();
    }

    /**
     * Create an instance of {@link GetOrgPersonRelationType }
     * 
     */
    public GetOrgPersonRelationType createGetOrgPersonRelationType() {
        return new GetOrgPersonRelationType();
    }

    /**
     * Create an instance of {@link GetPersonIdsForOrgByRelationTypeResponse }
     * 
     */
    public GetPersonIdsForOrgByRelationTypeResponse createGetPersonIdsForOrgByRelationTypeResponse() {
        return new GetPersonIdsForOrgByRelationTypeResponse();
    }

    /**
     * Create an instance of {@link StatusInfo }
     * 
     */
    public StatusInfo createStatusInfo() {
        return new StatusInfo();
    }

    /**
     * Create an instance of {@link ValidateOrgPersonRelation }
     * 
     */
    public ValidateOrgPersonRelation createValidateOrgPersonRelation() {
        return new ValidateOrgPersonRelation();
    }

    /**
     * Create an instance of {@link GetOrgOrgRelationType }
     * 
     */
    public GetOrgOrgRelationType createGetOrgOrgRelationType() {
        return new GetOrgOrgRelationType();
    }

    /**
     * Create an instance of {@link GetAllOrgPersonRelationsByOrg }
     * 
     */
    public GetAllOrgPersonRelationsByOrg createGetAllOrgPersonRelationsByOrg() {
        return new GetAllOrgPersonRelationsByOrg();
    }

    /**
     * Create an instance of {@link OrgTreeInfo }
     * 
     */
    public OrgTreeInfo createOrgTreeInfo() {
        return new OrgTreeInfo();
    }

    /**
     * Create an instance of {@link CreateOrgPersonRelation }
     * 
     */
    public CreateOrgPersonRelation createCreateOrgPersonRelation() {
        return new CreateOrgPersonRelation();
    }

    /**
     * Create an instance of {@link ValidateOrg }
     * 
     */
    public ValidateOrg createValidateOrg() {
        return new ValidateOrg();
    }

    /**
     * Create an instance of {@link GetOrgTypesResponse }
     * 
     */
    public GetOrgTypesResponse createGetOrgTypesResponse() {
        return new GetOrgTypesResponse();
    }

    /**
     * Create an instance of {@link OrgCodeInfo }
     * 
     */
    public OrgCodeInfo createOrgCodeInfo() {
        return new OrgCodeInfo();
    }

    /**
     * Create an instance of {@link GetAllOrgPersonRelationsByPerson }
     * 
     */
    public GetAllOrgPersonRelationsByPerson createGetAllOrgPersonRelationsByPerson() {
        return new GetAllOrgPersonRelationsByPerson();
    }

    /**
     * Create an instance of {@link GetPersonIdsForOrgByRelationType }
     * 
     */
    public GetPersonIdsForOrgByRelationType createGetPersonIdsForOrgByRelationType() {
        return new GetPersonIdsForOrgByRelationType();
    }

    /**
     * Create an instance of {@link GetOrgPersonRelationTypes }
     * 
     */
    public GetOrgPersonRelationTypes createGetOrgPersonRelationTypes() {
        return new GetOrgPersonRelationTypes();
    }

    /**
     * Create an instance of {@link RemovePositionRestrictionFromOrg }
     * 
     */
    public RemovePositionRestrictionFromOrg createRemovePositionRestrictionFromOrg() {
        return new RemovePositionRestrictionFromOrg();
    }

    /**
     * Create an instance of {@link GetOrgOrgRelation }
     * 
     */
    public GetOrgOrgRelation createGetOrgOrgRelation() {
        return new GetOrgOrgRelation();
    }

    /**
     * Create an instance of {@link OrgOrgRelationInfo }
     * 
     */
    public OrgOrgRelationInfo createOrgOrgRelationInfo() {
        return new OrgOrgRelationInfo();
    }

    /**
     * Create an instance of {@link JaxbAttributeList }
     * 
     */
    public JaxbAttributeList createJaxbAttributeList() {
        return new JaxbAttributeList();
    }

    /**
     * Create an instance of {@link UpdatePositionRestrictionForOrgResponse }
     * 
     */
    public UpdatePositionRestrictionForOrgResponse createUpdatePositionRestrictionForOrgResponse() {
        return new UpdatePositionRestrictionForOrgResponse();
    }

    /**
     * Create an instance of {@link RemoveOrgOrgRelation }
     * 
     */
    public RemoveOrgOrgRelation createRemoveOrgOrgRelation() {
        return new RemoveOrgOrgRelation();
    }

    /**
     * Create an instance of {@link GetPositionRestrictionsByOrg }
     * 
     */
    public GetPositionRestrictionsByOrg createGetPositionRestrictionsByOrg() {
        return new GetPositionRestrictionsByOrg();
    }

    /**
     * Create an instance of {@link DeleteOrganization }
     * 
     */
    public DeleteOrganization createDeleteOrganization() {
        return new DeleteOrganization();
    }

    /**
     * Create an instance of {@link RemoveOrgPersonRelation }
     * 
     */
    public RemoveOrgPersonRelation createRemoveOrgPersonRelation() {
        return new RemoveOrgPersonRelation();
    }

    /**
     * Create an instance of {@link GetOrganizationsByIdListResponse }
     * 
     */
    public GetOrganizationsByIdListResponse createGetOrganizationsByIdListResponse() {
        return new GetOrganizationsByIdListResponse();
    }

    /**
     * Create an instance of {@link GetOrgTypeResponse }
     * 
     */
    public GetOrgTypeResponse createGetOrgTypeResponse() {
        return new GetOrgTypeResponse();
    }

    /**
     * Create an instance of {@link ValidateOrgOrgRelation }
     * 
     */
    public ValidateOrgOrgRelation createValidateOrgOrgRelation() {
        return new ValidateOrgOrgRelation();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllOrgPersonRelationsByOrgResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getAllOrgPersonRelationsByOrgResponse")
    public JAXBElement<GetAllOrgPersonRelationsByOrgResponse> createGetAllOrgPersonRelationsByOrgResponse(GetAllOrgPersonRelationsByOrgResponse value) {
        return new JAXBElement<GetAllOrgPersonRelationsByOrgResponse>(_GetAllOrgPersonRelationsByOrgResponse_QNAME, GetAllOrgPersonRelationsByOrgResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOrgPersonRelationTypes }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getOrgPersonRelationTypes")
    public JAXBElement<GetOrgPersonRelationTypes> createGetOrgPersonRelationTypes(GetOrgPersonRelationTypes value) {
        return new JAXBElement<GetOrgPersonRelationTypes>(_GetOrgPersonRelationTypes_QNAME, GetOrgPersonRelationTypes.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOrgOrgRelationTypes }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getOrgOrgRelationTypes")
    public JAXBElement<GetOrgOrgRelationTypes> createGetOrgOrgRelationTypes(GetOrgOrgRelationTypes value) {
        return new JAXBElement<GetOrgOrgRelationTypes>(_GetOrgOrgRelationTypes_QNAME, GetOrgOrgRelationTypes.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateOrgPersonRelation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "updateOrgPersonRelation")
    public JAXBElement<UpdateOrgPersonRelation> createUpdateOrgPersonRelation(UpdateOrgPersonRelation value) {
        return new JAXBElement<UpdateOrgPersonRelation>(_UpdateOrgPersonRelation_QNAME, UpdateOrgPersonRelation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOrgPersonRelationType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getOrgPersonRelationType")
    public JAXBElement<GetOrgPersonRelationType> createGetOrgPersonRelationType(GetOrgPersonRelationType value) {
        return new JAXBElement<GetOrgPersonRelationType>(_GetOrgPersonRelationType_QNAME, GetOrgPersonRelationType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsDescendantResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "isDescendantResponse")
    public JAXBElement<IsDescendantResponse> createIsDescendantResponse(IsDescendantResponse value) {
        return new JAXBElement<IsDescendantResponse>(_IsDescendantResponse_QNAME, IsDescendantResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemovePositionRestrictionFromOrgResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "removePositionRestrictionFromOrgResponse")
    public JAXBElement<RemovePositionRestrictionFromOrgResponse> createRemovePositionRestrictionFromOrgResponse(RemovePositionRestrictionFromOrgResponse value) {
        return new JAXBElement<RemovePositionRestrictionFromOrgResponse>(_RemovePositionRestrictionFromOrgResponse_QNAME, RemovePositionRestrictionFromOrgResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddPositionRestrictionToOrgResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "addPositionRestrictionToOrgResponse")
    public JAXBElement<AddPositionRestrictionToOrgResponse> createAddPositionRestrictionToOrgResponse(AddPositionRestrictionToOrgResponse value) {
        return new JAXBElement<AddPositionRestrictionToOrgResponse>(_AddPositionRestrictionToOrgResponse_QNAME, AddPositionRestrictionToOrgResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveOrgOrgRelation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "removeOrgOrgRelation")
    public JAXBElement<RemoveOrgOrgRelation> createRemoveOrgOrgRelation(RemoveOrgOrgRelation value) {
        return new JAXBElement<RemoveOrgOrgRelation>(_RemoveOrgOrgRelation_QNAME, RemoveOrgOrgRelation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchTypesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getSearchTypesResponse")
    public JAXBElement<GetSearchTypesResponse> createGetSearchTypesResponse(GetSearchTypesResponse value) {
        return new JAXBElement<GetSearchTypesResponse>(_GetSearchTypesResponse_QNAME, GetSearchTypesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOrganizationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getOrganizationResponse")
    public JAXBElement<GetOrganizationResponse> createGetOrganizationResponse(GetOrganizationResponse value) {
        return new JAXBElement<GetOrganizationResponse>(_GetOrganizationResponse_QNAME, GetOrganizationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPersonIdsForOrgByRelationTypeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getPersonIdsForOrgByRelationTypeResponse")
    public JAXBElement<GetPersonIdsForOrgByRelationTypeResponse> createGetPersonIdsForOrgByRelationTypeResponse(GetPersonIdsForOrgByRelationTypeResponse value) {
        return new JAXBElement<GetPersonIdsForOrgByRelationTypeResponse>(_GetPersonIdsForOrgByRelationTypeResponse_QNAME, GetPersonIdsForOrgByRelationTypeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOrgPersonRelationsByOrg }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getOrgPersonRelationsByOrg")
    public JAXBElement<GetOrgPersonRelationsByOrg> createGetOrgPersonRelationsByOrg(GetOrgPersonRelationsByOrg value) {
        return new JAXBElement<GetOrgPersonRelationsByOrg>(_GetOrgPersonRelationsByOrg_QNAME, GetOrgPersonRelationsByOrg.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateOrgOrgRelationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "validateOrgOrgRelationResponse")
    public JAXBElement<ValidateOrgOrgRelationResponse> createValidateOrgOrgRelationResponse(ValidateOrgOrgRelationResponse value) {
        return new JAXBElement<ValidateOrgOrgRelationResponse>(_ValidateOrgOrgRelationResponse_QNAME, ValidateOrgOrgRelationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOrgPersonRelationTypesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getOrgPersonRelationTypesResponse")
    public JAXBElement<GetOrgPersonRelationTypesResponse> createGetOrgPersonRelationTypesResponse(GetOrgPersonRelationTypesResponse value) {
        return new JAXBElement<GetOrgPersonRelationTypesResponse>(_GetOrgPersonRelationTypesResponse_QNAME, GetOrgPersonRelationTypesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOrgPersonRelationsByPerson }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getOrgPersonRelationsByPerson")
    public JAXBElement<GetOrgPersonRelationsByPerson> createGetOrgPersonRelationsByPerson(GetOrgPersonRelationsByPerson value) {
        return new JAXBElement<GetOrgPersonRelationsByPerson>(_GetOrgPersonRelationsByPerson_QNAME, GetOrgPersonRelationsByPerson.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOrgTypes }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getOrgTypes")
    public JAXBElement<GetOrgTypes> createGetOrgTypes(GetOrgTypes value) {
        return new JAXBElement<GetOrgTypes>(_GetOrgTypes_QNAME, GetOrgTypes.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllOrgPersonRelationsByPersonResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getAllOrgPersonRelationsByPersonResponse")
    public JAXBElement<GetAllOrgPersonRelationsByPersonResponse> createGetAllOrgPersonRelationsByPersonResponse(GetAllOrgPersonRelationsByPersonResponse value) {
        return new JAXBElement<GetAllOrgPersonRelationsByPersonResponse>(_GetAllOrgPersonRelationsByPersonResponse_QNAME, GetAllOrgPersonRelationsByPersonResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOrgOrgRelationTypesForOrgHierarchy }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getOrgOrgRelationTypesForOrgHierarchy")
    public JAXBElement<GetOrgOrgRelationTypesForOrgHierarchy> createGetOrgOrgRelationTypesForOrgHierarchy(GetOrgOrgRelationTypesForOrgHierarchy value) {
        return new JAXBElement<GetOrgOrgRelationTypesForOrgHierarchy>(_GetOrgOrgRelationTypesForOrgHierarchy_QNAME, GetOrgOrgRelationTypesForOrgHierarchy.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateOrgOrgRelation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "validateOrgOrgRelation")
    public JAXBElement<ValidateOrgOrgRelation> createValidateOrgOrgRelation(ValidateOrgOrgRelation value) {
        return new JAXBElement<ValidateOrgOrgRelation>(_ValidateOrgOrgRelation_QNAME, ValidateOrgOrgRelation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOrgType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getOrgType")
    public JAXBElement<GetOrgType> createGetOrgType(GetOrgType value) {
        return new JAXBElement<GetOrgType>(_GetOrgType_QNAME, GetOrgType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetObjectStructureResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getObjectStructureResponse")
    public JAXBElement<GetObjectStructureResponse> createGetObjectStructureResponse(GetObjectStructureResponse value) {
        return new JAXBElement<GetObjectStructureResponse>(_GetObjectStructureResponse_QNAME, GetObjectStructureResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOrgOrgRelationTypesForOrgTypeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getOrgOrgRelationTypesForOrgTypeResponse")
    public JAXBElement<GetOrgOrgRelationTypesForOrgTypeResponse> createGetOrgOrgRelationTypesForOrgTypeResponse(GetOrgOrgRelationTypesForOrgTypeResponse value) {
        return new JAXBElement<GetOrgOrgRelationTypesForOrgTypeResponse>(_GetOrgOrgRelationTypesForOrgTypeResponse_QNAME, GetOrgOrgRelationTypesForOrgTypeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOrgOrgRelationTypesForOrgHierarchyResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getOrgOrgRelationTypesForOrgHierarchyResponse")
    public JAXBElement<GetOrgOrgRelationTypesForOrgHierarchyResponse> createGetOrgOrgRelationTypesForOrgHierarchyResponse(GetOrgOrgRelationTypesForOrgHierarchyResponse value) {
        return new JAXBElement<GetOrgOrgRelationTypesForOrgHierarchyResponse>(_GetOrgOrgRelationTypesForOrgHierarchyResponse_QNAME, GetOrgOrgRelationTypesForOrgHierarchyResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VersionMismatchException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "VersionMismatchException")
    public JAXBElement<VersionMismatchException> createVersionMismatchException(VersionMismatchException value) {
        return new JAXBElement<VersionMismatchException>(_VersionMismatchException_QNAME, VersionMismatchException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOrgPersonRelationTypesForOrgType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getOrgPersonRelationTypesForOrgType")
    public JAXBElement<GetOrgPersonRelationTypesForOrgType> createGetOrgPersonRelationTypesForOrgType(GetOrgPersonRelationTypesForOrgType value) {
        return new JAXBElement<GetOrgPersonRelationTypesForOrgType>(_GetOrgPersonRelationTypesForOrgType_QNAME, GetOrgPersonRelationTypesForOrgType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OperationFailedException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "OperationFailedException")
    public JAXBElement<OperationFailedException> createOperationFailedException(OperationFailedException value) {
        return new JAXBElement<OperationFailedException>(_OperationFailedException_QNAME, OperationFailedException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllOrgPersonRelationsByPerson }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getAllOrgPersonRelationsByPerson")
    public JAXBElement<GetAllOrgPersonRelationsByPerson> createGetAllOrgPersonRelationsByPerson(GetAllOrgPersonRelationsByPerson value) {
        return new JAXBElement<GetAllOrgPersonRelationsByPerson>(_GetAllOrgPersonRelationsByPerson_QNAME, GetAllOrgPersonRelationsByPerson.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AlreadyExistsException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "AlreadyExistsException")
    public JAXBElement<AlreadyExistsException> createAlreadyExistsException(AlreadyExistsException value) {
        return new JAXBElement<AlreadyExistsException>(_AlreadyExistsException_QNAME, AlreadyExistsException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetObjectStructure }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getObjectStructure")
    public JAXBElement<GetObjectStructure> createGetObjectStructure(GetObjectStructure value) {
        return new JAXBElement<GetObjectStructure>(_GetObjectStructure_QNAME, GetObjectStructure.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchCriteriaTypes }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getSearchCriteriaTypes")
    public JAXBElement<GetSearchCriteriaTypes> createGetSearchCriteriaTypes(GetSearchCriteriaTypes value) {
        return new JAXBElement<GetSearchCriteriaTypes>(_GetSearchCriteriaTypes_QNAME, GetSearchCriteriaTypes.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllDescendantsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getAllDescendantsResponse")
    public JAXBElement<GetAllDescendantsResponse> createGetAllDescendantsResponse(GetAllDescendantsResponse value) {
        return new JAXBElement<GetAllDescendantsResponse>(_GetAllDescendantsResponse_QNAME, GetAllDescendantsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchCriteriaType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getSearchCriteriaType")
    public JAXBElement<GetSearchCriteriaType> createGetSearchCriteriaType(GetSearchCriteriaType value) {
        return new JAXBElement<GetSearchCriteriaType>(_GetSearchCriteriaType_QNAME, GetSearchCriteriaType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllAncestors }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getAllAncestors")
    public JAXBElement<GetAllAncestors> createGetAllAncestors(GetAllAncestors value) {
        return new JAXBElement<GetAllAncestors>(_GetAllAncestors_QNAME, GetAllAncestors.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPersonIdsForOrgByRelationType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getPersonIdsForOrgByRelationType")
    public JAXBElement<GetPersonIdsForOrgByRelationType> createGetPersonIdsForOrgByRelationType(GetPersonIdsForOrgByRelationType value) {
        return new JAXBElement<GetPersonIdsForOrgByRelationType>(_GetPersonIdsForOrgByRelationType_QNAME, GetPersonIdsForOrgByRelationType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchTypesByResultResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getSearchTypesByResultResponse")
    public JAXBElement<GetSearchTypesByResultResponse> createGetSearchTypesByResultResponse(GetSearchTypesByResultResponse value) {
        return new JAXBElement<GetSearchTypesByResultResponse>(_GetSearchTypesByResultResponse_QNAME, GetSearchTypesByResultResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateOrganizationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "createOrganizationResponse")
    public JAXBElement<CreateOrganizationResponse> createCreateOrganizationResponse(CreateOrganizationResponse value) {
        return new JAXBElement<CreateOrganizationResponse>(_CreateOrganizationResponse_QNAME, CreateOrganizationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateOrganization }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "updateOrganization")
    public JAXBElement<UpdateOrganization> createUpdateOrganization(UpdateOrganization value) {
        return new JAXBElement<UpdateOrganization>(_UpdateOrganization_QNAME, UpdateOrganization.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchTypesByCriteria }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getSearchTypesByCriteria")
    public JAXBElement<GetSearchTypesByCriteria> createGetSearchTypesByCriteria(GetSearchTypesByCriteria value) {
        return new JAXBElement<GetSearchTypesByCriteria>(_GetSearchTypesByCriteria_QNAME, GetSearchTypesByCriteria.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchTypesByResult }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getSearchTypesByResult")
    public JAXBElement<GetSearchTypesByResult> createGetSearchTypesByResult(GetSearchTypesByResult value) {
        return new JAXBElement<GetSearchTypesByResult>(_GetSearchTypesByResult_QNAME, GetSearchTypesByResult.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOrgTypeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getOrgTypeResponse")
    public JAXBElement<GetOrgTypeResponse> createGetOrgTypeResponse(GetOrgTypeResponse value) {
        return new JAXBElement<GetOrgTypeResponse>(_GetOrgTypeResponse_QNAME, GetOrgTypeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteOrganizationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "deleteOrganizationResponse")
    public JAXBElement<DeleteOrganizationResponse> createDeleteOrganizationResponse(DeleteOrganizationResponse value) {
        return new JAXBElement<DeleteOrganizationResponse>(_DeleteOrganizationResponse_QNAME, DeleteOrganizationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HasOrgPersonRelationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "hasOrgPersonRelationResponse")
    public JAXBElement<HasOrgPersonRelationResponse> createHasOrgPersonRelationResponse(HasOrgPersonRelationResponse value) {
        return new JAXBElement<HasOrgPersonRelationResponse>(_HasOrgPersonRelationResponse_QNAME, HasOrgPersonRelationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateOrgOrgRelation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "createOrgOrgRelation")
    public JAXBElement<CreateOrgOrgRelation> createCreateOrgOrgRelation(CreateOrgOrgRelation value) {
        return new JAXBElement<CreateOrgOrgRelation>(_CreateOrgOrgRelation_QNAME, CreateOrgOrgRelation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOrgPersonRelationsByPersonResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getOrgPersonRelationsByPersonResponse")
    public JAXBElement<GetOrgPersonRelationsByPersonResponse> createGetOrgPersonRelationsByPersonResponse(GetOrgPersonRelationsByPersonResponse value) {
        return new JAXBElement<GetOrgPersonRelationsByPersonResponse>(_GetOrgPersonRelationsByPersonResponse_QNAME, GetOrgPersonRelationsByPersonResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateOrgPositionRestriction }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "validateOrgPositionRestriction")
    public JAXBElement<ValidateOrgPositionRestriction> createValidateOrgPositionRestriction(ValidateOrgPositionRestriction value) {
        return new JAXBElement<ValidateOrgPositionRestriction>(_ValidateOrgPositionRestriction_QNAME, ValidateOrgPositionRestriction.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateOrgPersonRelationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "createOrgPersonRelationResponse")
    public JAXBElement<CreateOrgPersonRelationResponse> createCreateOrgPersonRelationResponse(CreateOrgPersonRelationResponse value) {
        return new JAXBElement<CreateOrgPersonRelationResponse>(_CreateOrgPersonRelationResponse_QNAME, CreateOrgPersonRelationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HasOrgOrgRelation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "hasOrgOrgRelation")
    public JAXBElement<HasOrgOrgRelation> createHasOrgOrgRelation(HasOrgOrgRelation value) {
        return new JAXBElement<HasOrgOrgRelation>(_HasOrgOrgRelation_QNAME, HasOrgOrgRelation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateOrgPositionRestrictionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "validateOrgPositionRestrictionResponse")
    public JAXBElement<ValidateOrgPositionRestrictionResponse> createValidateOrgPositionRestrictionResponse(ValidateOrgPositionRestrictionResponse value) {
        return new JAXBElement<ValidateOrgPositionRestrictionResponse>(_ValidateOrgPositionRestrictionResponse_QNAME, ValidateOrgPositionRestrictionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteOrganization }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "deleteOrganization")
    public JAXBElement<DeleteOrganization> createDeleteOrganization(DeleteOrganization value) {
        return new JAXBElement<DeleteOrganization>(_DeleteOrganization_QNAME, DeleteOrganization.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOrgPersonRelationTypeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getOrgPersonRelationTypeResponse")
    public JAXBElement<GetOrgPersonRelationTypeResponse> createGetOrgPersonRelationTypeResponse(GetOrgPersonRelationTypeResponse value) {
        return new JAXBElement<GetOrgPersonRelationTypeResponse>(_GetOrgPersonRelationTypeResponse_QNAME, GetOrgPersonRelationTypeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllOrgPersonRelationsByOrg }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getAllOrgPersonRelationsByOrg")
    public JAXBElement<GetAllOrgPersonRelationsByOrg> createGetAllOrgPersonRelationsByOrg(GetAllOrgPersonRelationsByOrg value) {
        return new JAXBElement<GetAllOrgPersonRelationsByOrg>(_GetAllOrgPersonRelationsByOrg_QNAME, GetAllOrgPersonRelationsByOrg.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPositionRestrictionsByOrgResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getPositionRestrictionsByOrgResponse")
    public JAXBElement<GetPositionRestrictionsByOrgResponse> createGetPositionRestrictionsByOrgResponse(GetPositionRestrictionsByOrgResponse value) {
        return new JAXBElement<GetPositionRestrictionsByOrgResponse>(_GetPositionRestrictionsByOrgResponse_QNAME, GetPositionRestrictionsByOrgResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOrgPersonRelationsByOrgResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getOrgPersonRelationsByOrgResponse")
    public JAXBElement<GetOrgPersonRelationsByOrgResponse> createGetOrgPersonRelationsByOrgResponse(GetOrgPersonRelationsByOrgResponse value) {
        return new JAXBElement<GetOrgPersonRelationsByOrgResponse>(_GetOrgPersonRelationsByOrgResponse_QNAME, GetOrgPersonRelationsByOrgResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOrgOrgRelationTypeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getOrgOrgRelationTypeResponse")
    public JAXBElement<GetOrgOrgRelationTypeResponse> createGetOrgOrgRelationTypeResponse(GetOrgOrgRelationTypeResponse value) {
        return new JAXBElement<GetOrgOrgRelationTypeResponse>(_GetOrgOrgRelationTypeResponse_QNAME, GetOrgOrgRelationTypeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchResultTypes }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getSearchResultTypes")
    public JAXBElement<GetSearchResultTypes> createGetSearchResultTypes(GetSearchResultTypes value) {
        return new JAXBElement<GetSearchResultTypes>(_GetSearchResultTypes_QNAME, GetSearchResultTypes.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateOrgPersonRelation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "validateOrgPersonRelation")
    public JAXBElement<ValidateOrgPersonRelation> createValidateOrgPersonRelation(ValidateOrgPersonRelation value) {
        return new JAXBElement<ValidateOrgPersonRelation>(_ValidateOrgPersonRelation_QNAME, ValidateOrgPersonRelation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchResultType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getSearchResultType")
    public JAXBElement<GetSearchResultType> createGetSearchResultType(GetSearchResultType value) {
        return new JAXBElement<GetSearchResultType>(_GetSearchResultType_QNAME, GetSearchResultType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DoesNotExistException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "DoesNotExistException")
    public JAXBElement<DoesNotExistException> createDoesNotExistException(DoesNotExistException value) {
        return new JAXBElement<DoesNotExistException>(_DoesNotExistException_QNAME, DoesNotExistException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOrgOrgRelationsByIdList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getOrgOrgRelationsByIdList")
    public JAXBElement<GetOrgOrgRelationsByIdList> createGetOrgOrgRelationsByIdList(GetOrgOrgRelationsByIdList value) {
        return new JAXBElement<GetOrgOrgRelationsByIdList>(_GetOrgOrgRelationsByIdList_QNAME, GetOrgOrgRelationsByIdList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOrgHierarchies }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getOrgHierarchies")
    public JAXBElement<GetOrgHierarchies> createGetOrgHierarchies(GetOrgHierarchies value) {
        return new JAXBElement<GetOrgHierarchies>(_GetOrgHierarchies_QNAME, GetOrgHierarchies.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Search }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "search")
    public JAXBElement<Search> createSearch(Search value) {
        return new JAXBElement<Search>(_Search_QNAME, Search.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchResultTypesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getSearchResultTypesResponse")
    public JAXBElement<GetSearchResultTypesResponse> createGetSearchResultTypesResponse(GetSearchResultTypesResponse value) {
        return new JAXBElement<GetSearchResultTypesResponse>(_GetSearchResultTypesResponse_QNAME, GetSearchResultTypesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchCriteriaTypeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getSearchCriteriaTypeResponse")
    public JAXBElement<GetSearchCriteriaTypeResponse> createGetSearchCriteriaTypeResponse(GetSearchCriteriaTypeResponse value) {
        return new JAXBElement<GetSearchCriteriaTypeResponse>(_GetSearchCriteriaTypeResponse_QNAME, GetSearchCriteriaTypeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveOrgPersonRelation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "removeOrgPersonRelation")
    public JAXBElement<RemoveOrgPersonRelation> createRemoveOrgPersonRelation(RemoveOrgPersonRelation value) {
        return new JAXBElement<RemoveOrgPersonRelation>(_RemoveOrgPersonRelation_QNAME, RemoveOrgPersonRelation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOrgOrgRelationType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getOrgOrgRelationType")
    public JAXBElement<GetOrgOrgRelationType> createGetOrgOrgRelationType(GetOrgOrgRelationType value) {
        return new JAXBElement<GetOrgOrgRelationType>(_GetOrgOrgRelationType_QNAME, GetOrgOrgRelationType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllDescendants }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getAllDescendants")
    public JAXBElement<GetAllDescendants> createGetAllDescendants(GetAllDescendants value) {
        return new JAXBElement<GetAllDescendants>(_GetAllDescendants_QNAME, GetAllDescendants.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "searchResponse")
    public JAXBElement<SearchResponse> createSearchResponse(SearchResponse value) {
        return new JAXBElement<SearchResponse>(_SearchResponse_QNAME, SearchResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOrgPersonRelationsByIdList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getOrgPersonRelationsByIdList")
    public JAXBElement<GetOrgPersonRelationsByIdList> createGetOrgPersonRelationsByIdList(GetOrgPersonRelationsByIdList value) {
        return new JAXBElement<GetOrgPersonRelationsByIdList>(_GetOrgPersonRelationsByIdList_QNAME, GetOrgPersonRelationsByIdList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchTypes }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getSearchTypes")
    public JAXBElement<GetSearchTypes> createGetSearchTypes(GetSearchTypes value) {
        return new JAXBElement<GetSearchTypes>(_GetSearchTypes_QNAME, GetSearchTypes.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateOrgPersonRelationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "validateOrgPersonRelationResponse")
    public JAXBElement<ValidateOrgPersonRelationResponse> createValidateOrgPersonRelationResponse(ValidateOrgPersonRelationResponse value) {
        return new JAXBElement<ValidateOrgPersonRelationResponse>(_ValidateOrgPersonRelationResponse_QNAME, ValidateOrgPersonRelationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateOrg }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "validateOrg")
    public JAXBElement<ValidateOrg> createValidateOrg(ValidateOrg value) {
        return new JAXBElement<ValidateOrg>(_ValidateOrg_QNAME, ValidateOrg.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOrgOrgRelationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getOrgOrgRelationResponse")
    public JAXBElement<GetOrgOrgRelationResponse> createGetOrgOrgRelationResponse(GetOrgOrgRelationResponse value) {
        return new JAXBElement<GetOrgOrgRelationResponse>(_GetOrgOrgRelationResponse_QNAME, GetOrgOrgRelationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOrgPersonRelation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getOrgPersonRelation")
    public JAXBElement<GetOrgPersonRelation> createGetOrgPersonRelation(GetOrgPersonRelation value) {
        return new JAXBElement<GetOrgPersonRelation>(_GetOrgPersonRelation_QNAME, GetOrgPersonRelation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveOrgOrgRelationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "removeOrgOrgRelationResponse")
    public JAXBElement<RemoveOrgOrgRelationResponse> createRemoveOrgOrgRelationResponse(RemoveOrgOrgRelationResponse value) {
        return new JAXBElement<RemoveOrgOrgRelationResponse>(_RemoveOrgOrgRelationResponse_QNAME, RemoveOrgOrgRelationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOrgOrgRelationsByOrg }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getOrgOrgRelationsByOrg")
    public JAXBElement<GetOrgOrgRelationsByOrg> createGetOrgOrgRelationsByOrg(GetOrgOrgRelationsByOrg value) {
        return new JAXBElement<GetOrgOrgRelationsByOrg>(_GetOrgOrgRelationsByOrg_QNAME, GetOrgOrgRelationsByOrg.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOrgOrgRelationTypesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getOrgOrgRelationTypesResponse")
    public JAXBElement<GetOrgOrgRelationTypesResponse> createGetOrgOrgRelationTypesResponse(GetOrgOrgRelationTypesResponse value) {
        return new JAXBElement<GetOrgOrgRelationTypesResponse>(_GetOrgOrgRelationTypesResponse_QNAME, GetOrgOrgRelationTypesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchTypesByCriteriaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getSearchTypesByCriteriaResponse")
    public JAXBElement<GetSearchTypesByCriteriaResponse> createGetSearchTypesByCriteriaResponse(GetSearchTypesByCriteriaResponse value) {
        return new JAXBElement<GetSearchTypesByCriteriaResponse>(_GetSearchTypesByCriteriaResponse_QNAME, GetSearchTypesByCriteriaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOrgOrgRelationsByIdListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getOrgOrgRelationsByIdListResponse")
    public JAXBElement<GetOrgOrgRelationsByIdListResponse> createGetOrgOrgRelationsByIdListResponse(GetOrgOrgRelationsByIdListResponse value) {
        return new JAXBElement<GetOrgOrgRelationsByIdListResponse>(_GetOrgOrgRelationsByIdListResponse_QNAME, GetOrgOrgRelationsByIdListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveOrgPersonRelationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "removeOrgPersonRelationResponse")
    public JAXBElement<RemoveOrgPersonRelationResponse> createRemoveOrgPersonRelationResponse(RemoveOrgPersonRelationResponse value) {
        return new JAXBElement<RemoveOrgPersonRelationResponse>(_RemoveOrgPersonRelationResponse_QNAME, RemoveOrgPersonRelationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateOrganizationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "updateOrganizationResponse")
    public JAXBElement<UpdateOrganizationResponse> createUpdateOrganizationResponse(UpdateOrganizationResponse value) {
        return new JAXBElement<UpdateOrganizationResponse>(_UpdateOrganizationResponse_QNAME, UpdateOrganizationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateOrgPersonRelation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "createOrgPersonRelation")
    public JAXBElement<CreateOrgPersonRelation> createCreateOrgPersonRelation(CreateOrgPersonRelation value) {
        return new JAXBElement<CreateOrgPersonRelation>(_CreateOrgPersonRelation_QNAME, CreateOrgPersonRelation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOrgOrgRelationTypesForOrgType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getOrgOrgRelationTypesForOrgType")
    public JAXBElement<GetOrgOrgRelationTypesForOrgType> createGetOrgOrgRelationTypesForOrgType(GetOrgOrgRelationTypesForOrgType value) {
        return new JAXBElement<GetOrgOrgRelationTypesForOrgType>(_GetOrgOrgRelationTypesForOrgType_QNAME, GetOrgOrgRelationTypesForOrgType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllAncestorsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getAllAncestorsResponse")
    public JAXBElement<GetAllAncestorsResponse> createGetAllAncestorsResponse(GetAllAncestorsResponse value) {
        return new JAXBElement<GetAllAncestorsResponse>(_GetAllAncestorsResponse_QNAME, GetAllAncestorsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOrgOrgRelationsByOrgResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getOrgOrgRelationsByOrgResponse")
    public JAXBElement<GetOrgOrgRelationsByOrgResponse> createGetOrgOrgRelationsByOrgResponse(GetOrgOrgRelationsByOrgResponse value) {
        return new JAXBElement<GetOrgOrgRelationsByOrgResponse>(_GetOrgOrgRelationsByOrgResponse_QNAME, GetOrgOrgRelationsByOrgResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOrganization }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getOrganization")
    public JAXBElement<GetOrganization> createGetOrganization(GetOrganization value) {
        return new JAXBElement<GetOrganization>(_GetOrganization_QNAME, GetOrganization.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetObjectTypesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getObjectTypesResponse")
    public JAXBElement<GetObjectTypesResponse> createGetObjectTypesResponse(GetObjectTypesResponse value) {
        return new JAXBElement<GetObjectTypesResponse>(_GetObjectTypesResponse_QNAME, GetObjectTypesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdatePositionRestrictionForOrg }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "updatePositionRestrictionForOrg")
    public JAXBElement<UpdatePositionRestrictionForOrg> createUpdatePositionRestrictionForOrg(UpdatePositionRestrictionForOrg value) {
        return new JAXBElement<UpdatePositionRestrictionForOrg>(_UpdatePositionRestrictionForOrg_QNAME, UpdatePositionRestrictionForOrg.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOrgOrgRelationsByRelatedOrgResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getOrgOrgRelationsByRelatedOrgResponse")
    public JAXBElement<GetOrgOrgRelationsByRelatedOrgResponse> createGetOrgOrgRelationsByRelatedOrgResponse(GetOrgOrgRelationsByRelatedOrgResponse value) {
        return new JAXBElement<GetOrgOrgRelationsByRelatedOrgResponse>(_GetOrgOrgRelationsByRelatedOrgResponse_QNAME, GetOrgOrgRelationsByRelatedOrgResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOrgTreeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getOrgTreeResponse")
    public JAXBElement<GetOrgTreeResponse> createGetOrgTreeResponse(GetOrgTreeResponse value) {
        return new JAXBElement<GetOrgTreeResponse>(_GetOrgTreeResponse_QNAME, GetOrgTreeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOrgPersonRelationsByIdListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getOrgPersonRelationsByIdListResponse")
    public JAXBElement<GetOrgPersonRelationsByIdListResponse> createGetOrgPersonRelationsByIdListResponse(GetOrgPersonRelationsByIdListResponse value) {
        return new JAXBElement<GetOrgPersonRelationsByIdListResponse>(_GetOrgPersonRelationsByIdListResponse_QNAME, GetOrgPersonRelationsByIdListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateOrgOrgRelationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "createOrgOrgRelationResponse")
    public JAXBElement<CreateOrgOrgRelationResponse> createCreateOrgOrgRelationResponse(CreateOrgOrgRelationResponse value) {
        return new JAXBElement<CreateOrgOrgRelationResponse>(_CreateOrgOrgRelationResponse_QNAME, CreateOrgOrgRelationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getSearchType")
    public JAXBElement<GetSearchType> createGetSearchType(GetSearchType value) {
        return new JAXBElement<GetSearchType>(_GetSearchType_QNAME, GetSearchType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PermissionDeniedException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "PermissionDeniedException")
    public JAXBElement<PermissionDeniedException> createPermissionDeniedException(PermissionDeniedException value) {
        return new JAXBElement<PermissionDeniedException>(_PermissionDeniedException_QNAME, PermissionDeniedException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOrgOrgRelationsByRelatedOrg }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getOrgOrgRelationsByRelatedOrg")
    public JAXBElement<GetOrgOrgRelationsByRelatedOrg> createGetOrgOrgRelationsByRelatedOrg(GetOrgOrgRelationsByRelatedOrg value) {
        return new JAXBElement<GetOrgOrgRelationsByRelatedOrg>(_GetOrgOrgRelationsByRelatedOrg_QNAME, GetOrgOrgRelationsByRelatedOrg.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemovePositionRestrictionFromOrg }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "removePositionRestrictionFromOrg")
    public JAXBElement<RemovePositionRestrictionFromOrg> createRemovePositionRestrictionFromOrg(RemovePositionRestrictionFromOrg value) {
        return new JAXBElement<RemovePositionRestrictionFromOrg>(_RemovePositionRestrictionFromOrg_QNAME, RemovePositionRestrictionFromOrg.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOrgPersonRelationTypesForOrgTypeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getOrgPersonRelationTypesForOrgTypeResponse")
    public JAXBElement<GetOrgPersonRelationTypesForOrgTypeResponse> createGetOrgPersonRelationTypesForOrgTypeResponse(GetOrgPersonRelationTypesForOrgTypeResponse value) {
        return new JAXBElement<GetOrgPersonRelationTypesForOrgTypeResponse>(_GetOrgPersonRelationTypesForOrgTypeResponse_QNAME, GetOrgPersonRelationTypesForOrgTypeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOrgHierarchyResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getOrgHierarchyResponse")
    public JAXBElement<GetOrgHierarchyResponse> createGetOrgHierarchyResponse(GetOrgHierarchyResponse value) {
        return new JAXBElement<GetOrgHierarchyResponse>(_GetOrgHierarchyResponse_QNAME, GetOrgHierarchyResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DataValidationErrorException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "DataValidationErrorException")
    public JAXBElement<DataValidationErrorException> createDataValidationErrorException(DataValidationErrorException value) {
        return new JAXBElement<DataValidationErrorException>(_DataValidationErrorException_QNAME, DataValidationErrorException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOrgTree }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getOrgTree")
    public JAXBElement<GetOrgTree> createGetOrgTree(GetOrgTree value) {
        return new JAXBElement<GetOrgTree>(_GetOrgTree_QNAME, GetOrgTree.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateOrgOrgRelationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "updateOrgOrgRelationResponse")
    public JAXBElement<UpdateOrgOrgRelationResponse> createUpdateOrgOrgRelationResponse(UpdateOrgOrgRelationResponse value) {
        return new JAXBElement<UpdateOrgOrgRelationResponse>(_UpdateOrgOrgRelationResponse_QNAME, UpdateOrgOrgRelationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchTypeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getSearchTypeResponse")
    public JAXBElement<GetSearchTypeResponse> createGetSearchTypeResponse(GetSearchTypeResponse value) {
        return new JAXBElement<GetSearchTypeResponse>(_GetSearchTypeResponse_QNAME, GetSearchTypeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetObjectTypes }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getObjectTypes")
    public JAXBElement<GetObjectTypes> createGetObjectTypes(GetObjectTypes value) {
        return new JAXBElement<GetObjectTypes>(_GetObjectTypes_QNAME, GetObjectTypes.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOrgHierarchiesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getOrgHierarchiesResponse")
    public JAXBElement<GetOrgHierarchiesResponse> createGetOrgHierarchiesResponse(GetOrgHierarchiesResponse value) {
        return new JAXBElement<GetOrgHierarchiesResponse>(_GetOrgHierarchiesResponse_QNAME, GetOrgHierarchiesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOrgHierarchy }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getOrgHierarchy")
    public JAXBElement<GetOrgHierarchy> createGetOrgHierarchy(GetOrgHierarchy value) {
        return new JAXBElement<GetOrgHierarchy>(_GetOrgHierarchy_QNAME, GetOrgHierarchy.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InvalidParameterException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "InvalidParameterException")
    public JAXBElement<InvalidParameterException> createInvalidParameterException(InvalidParameterException value) {
        return new JAXBElement<InvalidParameterException>(_InvalidParameterException_QNAME, InvalidParameterException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsDescendant }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "isDescendant")
    public JAXBElement<IsDescendant> createIsDescendant(IsDescendant value) {
        return new JAXBElement<IsDescendant>(_IsDescendant_QNAME, IsDescendant.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HasOrgPersonRelation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "hasOrgPersonRelation")
    public JAXBElement<HasOrgPersonRelation> createHasOrgPersonRelation(HasOrgPersonRelation value) {
        return new JAXBElement<HasOrgPersonRelation>(_HasOrgPersonRelation_QNAME, HasOrgPersonRelation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPositionRestrictionsByOrg }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getPositionRestrictionsByOrg")
    public JAXBElement<GetPositionRestrictionsByOrg> createGetPositionRestrictionsByOrg(GetPositionRestrictionsByOrg value) {
        return new JAXBElement<GetPositionRestrictionsByOrg>(_GetPositionRestrictionsByOrg_QNAME, GetPositionRestrictionsByOrg.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateOrgPersonRelationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "updateOrgPersonRelationResponse")
    public JAXBElement<UpdateOrgPersonRelationResponse> createUpdateOrgPersonRelationResponse(UpdateOrgPersonRelationResponse value) {
        return new JAXBElement<UpdateOrgPersonRelationResponse>(_UpdateOrgPersonRelationResponse_QNAME, UpdateOrgPersonRelationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddPositionRestrictionToOrg }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "addPositionRestrictionToOrg")
    public JAXBElement<AddPositionRestrictionToOrg> createAddPositionRestrictionToOrg(AddPositionRestrictionToOrg value) {
        return new JAXBElement<AddPositionRestrictionToOrg>(_AddPositionRestrictionToOrg_QNAME, AddPositionRestrictionToOrg.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOrgTypesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getOrgTypesResponse")
    public JAXBElement<GetOrgTypesResponse> createGetOrgTypesResponse(GetOrgTypesResponse value) {
        return new JAXBElement<GetOrgTypesResponse>(_GetOrgTypesResponse_QNAME, GetOrgTypesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdatePositionRestrictionForOrgResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "updatePositionRestrictionForOrgResponse")
    public JAXBElement<UpdatePositionRestrictionForOrgResponse> createUpdatePositionRestrictionForOrgResponse(UpdatePositionRestrictionForOrgResponse value) {
        return new JAXBElement<UpdatePositionRestrictionForOrgResponse>(_UpdatePositionRestrictionForOrgResponse_QNAME, UpdatePositionRestrictionForOrgResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateOrgResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "validateOrgResponse")
    public JAXBElement<ValidateOrgResponse> createValidateOrgResponse(ValidateOrgResponse value) {
        return new JAXBElement<ValidateOrgResponse>(_ValidateOrgResponse_QNAME, ValidateOrgResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOrgPersonRelationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getOrgPersonRelationResponse")
    public JAXBElement<GetOrgPersonRelationResponse> createGetOrgPersonRelationResponse(GetOrgPersonRelationResponse value) {
        return new JAXBElement<GetOrgPersonRelationResponse>(_GetOrgPersonRelationResponse_QNAME, GetOrgPersonRelationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateOrganization }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "createOrganization")
    public JAXBElement<CreateOrganization> createCreateOrganization(CreateOrganization value) {
        return new JAXBElement<CreateOrganization>(_CreateOrganization_QNAME, CreateOrganization.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOrganizationsByIdList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getOrganizationsByIdList")
    public JAXBElement<GetOrganizationsByIdList> createGetOrganizationsByIdList(GetOrganizationsByIdList value) {
        return new JAXBElement<GetOrganizationsByIdList>(_GetOrganizationsByIdList_QNAME, GetOrganizationsByIdList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchCriteriaTypesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getSearchCriteriaTypesResponse")
    public JAXBElement<GetSearchCriteriaTypesResponse> createGetSearchCriteriaTypesResponse(GetSearchCriteriaTypesResponse value) {
        return new JAXBElement<GetSearchCriteriaTypesResponse>(_GetSearchCriteriaTypesResponse_QNAME, GetSearchCriteriaTypesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSearchResultTypeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getSearchResultTypeResponse")
    public JAXBElement<GetSearchResultTypeResponse> createGetSearchResultTypeResponse(GetSearchResultTypeResponse value) {
        return new JAXBElement<GetSearchResultTypeResponse>(_GetSearchResultTypeResponse_QNAME, GetSearchResultTypeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOrgOrgRelation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getOrgOrgRelation")
    public JAXBElement<GetOrgOrgRelation> createGetOrgOrgRelation(GetOrgOrgRelation value) {
        return new JAXBElement<GetOrgOrgRelation>(_GetOrgOrgRelation_QNAME, GetOrgOrgRelation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HasOrgOrgRelationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "hasOrgOrgRelationResponse")
    public JAXBElement<HasOrgOrgRelationResponse> createHasOrgOrgRelationResponse(HasOrgOrgRelationResponse value) {
        return new JAXBElement<HasOrgOrgRelationResponse>(_HasOrgOrgRelationResponse_QNAME, HasOrgOrgRelationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOrganizationsByIdListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "getOrganizationsByIdListResponse")
    public JAXBElement<GetOrganizationsByIdListResponse> createGetOrganizationsByIdListResponse(GetOrganizationsByIdListResponse value) {
        return new JAXBElement<GetOrganizationsByIdListResponse>(_GetOrganizationsByIdListResponse_QNAME, GetOrganizationsByIdListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MissingParameterException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "MissingParameterException")
    public JAXBElement<MissingParameterException> createMissingParameterException(MissingParameterException value) {
        return new JAXBElement<MissingParameterException>(_MissingParameterException_QNAME, MissingParameterException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateOrgOrgRelation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://student.kuali.org/wsdl/organization", name = "updateOrgOrgRelation")
    public JAXBElement<UpdateOrgOrgRelation> createUpdateOrgOrgRelation(UpdateOrgOrgRelation value) {
        return new JAXBElement<UpdateOrgOrgRelation>(_UpdateOrgOrgRelation_QNAME, UpdateOrgOrgRelation.class, null, value);
    }

}
