package org.kuali.student.lum.organization.service;

import java.util.List;

import javax.jws.WebParam;

import org.kuali.student.common.ws.exceptions.AlreadyExistsException;
import org.kuali.student.common.ws.exceptions.DataValidationErrorException;
import org.kuali.student.common.ws.exceptions.DoesNotExistException;
import org.kuali.student.common.ws.exceptions.InvalidParameterException;
import org.kuali.student.common.ws.exceptions.MissingParameterException;
import org.kuali.student.common.ws.exceptions.OperationFailedException;
import org.kuali.student.common.ws.exceptions.PermissionDeniedException;
import org.kuali.student.common.ws.exceptions.VersionMismatchException;
import org.kuali.student.core.dto.Status;
import org.kuali.student.core.dictionary.service.DictionaryService;
import org.kuali.student.core.search.service.SearchService;
import org.kuali.student.core.validation.dto.ValidationResult;
import org.kuali.student.lum.organization.dto.OrgCriteria;
import org.kuali.student.lum.organization.dto.OrgHierarchyInfo;
import org.kuali.student.lum.organization.dto.OrgInfo;
import org.kuali.student.lum.organization.dto.OrgOrgRelationCriteria;
import org.kuali.student.lum.organization.dto.OrgOrgRelationInfo;
import org.kuali.student.lum.organization.dto.OrgOrgRelationTypeInfo;
import org.kuali.student.lum.organization.dto.OrgPersonRelationCriteria;
import org.kuali.student.lum.organization.dto.OrgPersonRelationInfo;
import org.kuali.student.lum.organization.dto.OrgPersonRelationTypeInfo;
import org.kuali.student.lum.organization.dto.OrgPositionRestrictionInfo;
import org.kuali.student.lum.organization.dto.OrgTypeInfo;

public interface OrganizationService extends DictionaryService, SearchService{



	/**
	 * Retrieves the list of organization hierarchies known by this service.
	 * 
	 * @param None
	 *            No parameters
	 * @return list of organization hierarchies
	 * @throws OperationFailedException
	 *             unable to complete request
	 */
	public List<OrgHierarchyInfo> findOrgHierarchies()
			throws OperationFailedException;

	/**
	 * Retrieves information about a specific organization hierarchy.
	 * 
	 * @param orgHierarchyKey
	 *            organization hierarchy identifier
	 * @return information about the specified organization hierarchy
	 * @throws DoesNotExistException
	 *             orgHierarchy not found
	 * @throws InvalidParameterException
	 *             invalid orgHierarchyKey
	 * @throws MissingParameterException
	 *             missing orgHierarchyKey
	 * @throws OperationFailedException
	 *             unable to complete request
	 */
	public OrgHierarchyInfo fetchOrgHierarchy(
			@WebParam(name = "orgHierarchyKey")
			String orgHierarchyKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException;

	/**
	 * Retrieves the list of types of organizations known by this service.
	 * 
	 * @param None
	 *            No parameters
	 * @return List of organization types
	 * @throws OperationFailedException
	 *             unable to complete request
	 */
	public List<OrgTypeInfo> findOrgTypes() throws OperationFailedException;

	/**
	 * Retrieves information about the specified type of organization.
	 * 
	 * @param orgTypeKey
	 *            organization type identifier
	 * @return organization type
	 * @throws DoesNotExistException
	 *             orgType not found
	 * @throws InvalidParameterException
	 *             invalid orgTypeKey
	 * @throws MissingParameterException
	 *             missing orgTypeKey
	 * @throws OperationFailedException
	 *             unable to complete request
	 */
	public OrgTypeInfo fetchOrgType(@WebParam(name = "orgTypeKey")
	String orgTypeKey) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException;

	/**
	 * Retrieves the list of all types of relationships between organizations
	 * known to the service.
	 * 
	 * @param None
	 *            No parameters
	 * @return list of organization to organization relationship types
	 * @throws OperationFailedException
	 *             unable to complete request
	 */
	public List<OrgOrgRelationTypeInfo> findOrgOrgRelationTypes()
			throws OperationFailedException;

	/**
	 * Retrieves information about the specified type of relationship between
	 * organizations.
	 * 
	 * @param orgOrgRelationTypeKey
	 *            organization organization relationship type identifier
	 * @return organization organization relationship type information
	 * @throws DoesNotExistException
	 *             orgOrgRelationType not found
	 * @throws InvalidParameterException
	 *             invalid orgOrgRelationTypeKey
	 * @throws MissingParameterException
	 *             missing orgOrgRelationTypeKey
	 * @throws OperationFailedException
	 *             unable to complete request
	 */
	public OrgOrgRelationTypeInfo fetchOrgOrgRelationType(
			@WebParam(name = "orgOrgRelationTypeKey")
			String orgOrgRelationTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException;

	/**
	 * Retrieves the types of relationships between organizations that are
	 * allowed for a particular type of organization.
	 * 
	 * @param orgTypeKey
	 *            organization type identifier
	 * @return list of relationship types between organizations for the
	 *         specified organization type
	 * @throws DoesNotExistException
	 *             orgType not found
	 * @throws InvalidParameterException
	 *             invalid orgTypeKey
	 * @throws MissingParameterException
	 *             missing orgTypeKey
	 * @throws OperationFailedException
	 *             unable to complete request
	 */
	public List<OrgOrgRelationTypeInfo> findOrgOrgRelationTypesForOrgType(
			@WebParam(name = "orgTypeKey")
			String orgTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException;

	/**
	 * Retrieves the types of relationships between organizations that are
	 * allowed for a particular organization hierarchy.
	 * 
	 * @param orgHierarchyKey
	 *            organization hierarchy identifier
	 * @return list of relationship types between organizations
	 * @throws DoesNotExistException
	 *             orgHierarchy not found
	 * @throws InvalidParameterException
	 *             invalid orgHierarchyKey
	 * @throws MissingParameterException
	 *             missing orgHierarchyKey
	 * @throws OperationFailedException
	 *             unable to complete request
	 */
	public List<OrgOrgRelationTypeInfo> findOrgOrgRelationTypesForOrgHierarchy(
			@WebParam(name = "orgHierarchyKey")
			String orgHierarchyKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException;

	/**
	 * Retrieves all types of relationships between an organization and a person
	 * known by this service.
	 * 
	 * @param None
	 *            No parameters
	 * @return list of all organization person relationship types
	 * @throws OperationFailedException
	 *             unable to complete request
	 */
	public List<OrgPersonRelationTypeInfo> findOrgPersonRelationTypes()
			throws OperationFailedException;

	/**
	 * Retrieves information about a type of relationship between an
	 * organization and a person.
	 * 
	 * @param orgPersonRelationTypeKey
	 *            organization person relationship type identifier
	 * @return organization person relationship type information
	 * @throws DoesNotExistException
	 *             orgType not found
	 * @throws InvalidParameterException
	 *             invalid orgTypeKey
	 * @throws MissingParameterException
	 *             missing orgTypeKey
	 * @throws OperationFailedException
	 *             unable to complete request
	 */
	public OrgPersonRelationTypeInfo fetchOrgPersonRelationType(
			@WebParam(name = "orgPersonRelationTypeKey")
			String orgPersonRelationTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException;

	/**
	 * Retrieves the types of relationships between an organization and a person
	 * that are allowed for a particular organization type.
	 * 
	 * @param orgTypeKey
	 *            organization type identifier
	 * @return list of organization person relationship types that are valid for
	 *         the supplied organization type (may have nothing)
	 * @throws DoesNotExistException
	 *             orgType not found
	 * @throws InvalidParameterException
	 *             invalid orgTypeKey
	 * @throws MissingParameterException
	 *             missing orgTypeKey
	 * @throws OperationFailedException
	 *             unable to complete request
	 */
	public List<OrgPersonRelationTypeInfo> findOrgPersonRelationTypesForOrgType(
			@WebParam(name = "orgTypeKey")
			String orgTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException;

	/**
	 * Validates an organization. Depending on the value of validationType, this
	 * validation could be limited to tests on just the current object and its
	 * directly contained sub-objects or expanded to perform all tests related
	 * to this object. If an identifier is present for the organization (and/or
	 * one of its contained sub-objects) and a record is found for that
	 * identifier, the validation checks if the organization can be shifted to
	 * the new values. If an identifier is not present or a record cannot be
	 * found for the identifier, it is assumed that the record does not exist
	 * and as such, the checks performed will be much shallower, typically
	 * mimicking those performed by setting the validationType to the current
	 * object.
	 * 
	 * @param validationType
	 *            identifier of the extent of validation
	 * @param orgInfo
	 *            organization information to be tested.
	 * @return results from performing the validation
	 * @throws DoesNotExistException
	 *             validationTypeKey not found
	 * @throws InvalidParameterException
	 *             invalid validationTypeKey, orgInfo
	 * @throws MissingParameterException
	 *             missing validationTypeKey, orgInfo
	 * @throws OperationFailedException
	 *             unable to complete request
	 */
	public List<ValidationResult> validateOrg(
			@WebParam(name = "validationType")
			String validationType, @WebParam(name = "orgInfo")
			OrgInfo orgInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException;

	/**
	 * Validates an organization to organization relationship. Depending on the
	 * value of validationType, this validation could be limited to tests on
	 * just the current object and its directly contained sub-objects or
	 * expanded to perform all tests related to this object. If an identifier is
	 * present for the organization organization relationship (and/or one of its
	 * contained sub-objects) and a record is found for that identifier, the
	 * validation checks if the organization organization relationship can be
	 * shifted to the new values. If an identifier is not present or a record
	 * cannot be found for the identifier, it is assumed that the record does
	 * not exist and as such, the checks performed will be much shallower,
	 * typically mimicking those performed by setting the validationType to the
	 * current object.
	 * 
	 * @param validationType
	 *            identifier of the extent of validation
	 * @param orgOrgRelationInfo
	 *            organization organization relationship information to be
	 *            tested.
	 * @return results from performing the validation
	 * @throws DoesNotExistException
	 *             validationTypeKey not found
	 * @throws InvalidParameterException
	 *             invalid validationTypeKey, orgOrgRelationInfo
	 * @throws MissingParameterException
	 *             missing validationTypeKey, orgOrgRelationInfo
	 * @throws OperationFailedException
	 *             unable to complete request
	 */
	public List<ValidationResult> validateOrgOrgRelation(
			@WebParam(name = "validationType")
			String validationType, @WebParam(name = "orgOrgRelationInfo")
			OrgOrgRelationInfo orgOrgRelationInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException;

	/**
	 * Validates a organization to person relationship. Depending on the value
	 * of validationType, this validation could be limited to tests on just the
	 * current object and its directly contained sub-objects or expanded to
	 * perform all tests related to this object. If an identifier is present for
	 * the organization person relationship (and/or one of its contained
	 * sub-objects) and a record is found for that identifier, the validation
	 * checks if the organization person relationship can be shifted to the new
	 * values. If an identifier is not present or a record cannot be found for
	 * the identifier, it is assumed that the record does not exist and as such,
	 * the checks performed will be much shallower, typically mimicking those
	 * performed by setting the validationType to the current object.
	 * 
	 * @param validationType
	 *            identifier of the extent of validation
	 * @param orgPersonRelationInfo
	 *            organization person relationship information to be tested.
	 * @return results from performing the validation
	 * @throws DoesNotExistException
	 *             validationTypeKey not found
	 * @throws InvalidParameterException
	 *             invalid validationTypeKey, orgPersonRelationInfo
	 * @throws MissingParameterException
	 *             missing validationTypeKey, orgPersonRelationInfo
	 * @throws OperationFailedException
	 *             unable to complete request
	 */
	public List<ValidationResult> validateOrgPersonRelation(
			@WebParam(name = "validationType")
			String validationType, @WebParam(name = "orgPersonRelationInfo")
			OrgPersonRelationInfo orgPersonRelationInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException;

	/**
	 * Validates an organization position restriction. Depending on the value of
	 * validationType, this validation could be limited to tests on just the
	 * current object and its directly contained sub-objects or expanded to
	 * perform all tests related to this object. If an identifier is present for
	 * the position restriction (and/or one of its contained sub-objects) and a
	 * record is found for that identifier, the validation checks if the
	 * position restriction can be shifted to the new values. If an identifier
	 * is not present or a record cannot be found for the identifier, it is
	 * assumed that the record does not exist and as such, the checks performed
	 * will be much shallower, typically mimicking those performed by setting
	 * the validationType to the current object.
	 * 
	 * @param validationType
	 *            identifier of the extent of validation
	 * @param orgPositionRestrictionInfo
	 *            organization position restriction information to be tested.
	 * @return results from performing the validation
	 * @throws DoesNotExistException
	 *             validationTypeKey not found
	 * @throws InvalidParameterException
	 *             invalid validationTypeKey, orgPositionRestrictionInfo
	 * @throws MissingParameterException
	 *             missing validationTypeKey, orgPositionRestrictionInfo
	 * @throws OperationFailedException
	 *             unable to complete request
	 */
	public List<ValidationResult> validateOrgPositionRestriction(
			@WebParam(name = "validationType")
			String validationType,
			@WebParam(name = "orgPositionRestrictionInfo")
			OrgPositionRestrictionInfo orgPositionRestrictionInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException;

	/**
	 * Retrieves an existing organization by its identifier.
	 * 
	 * @param orgId
	 *            identifier for org to be retrieved
	 * @return details of the organization for this id
	 * @throws DoesNotExistException
	 *             orgId not found
	 * @throws InvalidParameterException
	 *             invalid orgId
	 * @throws MissingParameterException
	 *             invalid orgId
	 * @throws OperationFailedException
	 *             unable to complete request
	 * @throws PermissionDeniedException
	 *             authorization failure
	 */
	public OrgInfo fetchOrganization(@WebParam(name = "orgId")
	String orgId) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException;

	/**
	 * Retrieves a list of existing organizations from a list of identifiers.
	 * 
	 * @param orgIdList
	 *            identifiers for orgs to be retrieved
	 * @return details of the organizations for these ids
	 * @throws DoesNotExistException
	 *             orgId not found
	 * @throws InvalidParameterException
	 *             invalid orgId
	 * @throws MissingParameterException
	 *             invalid orgId
	 * @throws OperationFailedException
	 *             unable to complete request
	 * @throws PermissionDeniedException
	 *             authorization failure
	 */
	public List<OrgInfo> findOrganizationsByIdList(
			@WebParam(name = "orgIdList")
			List<String> orgIdList) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	/**
	 * Retrieves an existing organization to organization relationship by the
	 * relationship identifier
	 * 
	 * @param orgOrgRelationId
	 *            identifier for org to org relation to be retrieved
	 * @return details of the orgOrgRelation for this id
	 * @throws DoesNotExistException
	 *             orgOrgRelationId not found
	 * @throws InvalidParameterException
	 *             invalid orgOrgRelationId
	 * @throws MissingParameterException
	 *             invalid orgOrgRelationId
	 * @throws OperationFailedException
	 *             unable to complete request
	 * @throws PermissionDeniedException
	 *             authorization failure
	 */
	public OrgOrgRelationInfo fetchOrgOrgRelation(
			@WebParam(name = "orgOrgRelationId")
			String orgOrgRelationId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	/**
	 * Retrieves a list of existing organization to organization relationships
	 * from a list of identifiers
	 * 
	 * @param orgOrgRelationIdList
	 *            identifiers for org org relations to be retrieved
	 * @return details of the organizations for these ids
	 * @throws DoesNotExistException
	 *             orgOrgRelationId not found
	 * @throws InvalidParameterException
	 *             invalid orgOrgRelationId
	 * @throws MissingParameterException
	 *             invalid orgOrgRelationId
	 * @throws OperationFailedException
	 *             unable to complete request
	 * @throws PermissionDeniedException
	 *             authorization failure
	 */
	public List<OrgOrgRelationInfo> findOrgOrgRelationsByIdList(
			@WebParam(name = "orgOrgRelationIdList")
			List<String> orgOrgRelationIdList) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	/**
	 * Retrieves organization to organization relationships for a particular
	 * organization
	 * 
	 * @param orgId
	 *            organization identifier for which organization organization
	 *            relationships are to be found
	 * @return List of org to org relations found for the supplied organization
	 * @throws DoesNotExistException
	 *             orgId not found
	 * @throws InvalidParameterException
	 *             invalid orgId
	 * @throws MissingParameterException
	 *             missing orgId
	 * @throws OperationFailedException
	 *             unable to complete request
	 * @throws PermissionDeniedException
	 *             authorization failure
	 */
	public List<OrgOrgRelationInfo> findOrgOrgRelationsByOrg(
			@WebParam(name = "orgId")
			String orgId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	/**
	 * Retrieves organization to organization relationships that have been made
	 * TO a particular organization. Opposite direction from
	 * findOrgOrgRelationsByOrg.
	 * 
	 * @param relatedOrgId
	 *            organization identifier for which the reverse organization
	 *            organization relationships are to be found
	 * @return List of organization organization relationships found for the
	 *         supplied organization
	 * @throws DoesNotExistException
	 *             orgId not found
	 * @throws InvalidParameterException
	 *             invalid orgId
	 * @throws MissingParameterException
	 *             missing orgId
	 * @throws OperationFailedException
	 *             unable to complete request
	 * @throws PermissionDeniedException
	 *             authorization failure
	 */
	public List<OrgOrgRelationInfo> findOrgOrgRelationsByRelatedOrg(
			@WebParam(name = "relatedOrgId")
			String relatedOrgId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	/**
	 * Tests if a org has a current relationship with a specified organization.
	 * 
	 * @param orgId
	 *            identifier of the organization
	 * @param comparisonOrgId
	 *            identifier of the organization to be compared to
	 * @param orgOrgRelationTypeKey
	 *            type of relationship between the organizations
	 * @return true if a relationship exists
	 * @throws InvalidParameterException
	 *             invalid orgId, comparisonOrgId, orgOrgRelationType
	 * @throws MissingParameterException
	 *             missing orgId, comparisonOrgId, orgOrgRelationType
	 * @throws OperationFailedException
	 *             unable to complete request
	 * @throws PermissionDeniedException
	 *             authorization failure
	 */
	public Boolean hasOrgOrgRelation(@WebParam(name = "orgId")
	String orgId, @WebParam(name = "comparisonOrgId")
	String comparisonOrgId, @WebParam(name = "orgOrgRelationTypeKey")
	String orgOrgRelationTypeKey) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException;

	/**
	 * Tests if a specified organization is a descendant of the other specified
	 * organization in the given organization hierarchy.
	 * 
	 * @param orgId
	 *            identifier of the "ancestor" organization
	 * @param descendantOrgId
	 *            identifier of the organization to be checked if it is a
	 *            descendant
	 * @param orgHierarchy
	 *            identifier of the organization hierarchy to be checked against
	 * @return True if the organization is a descendant of the other
	 *         organization in that hierarchy
	 * @throws InvalidParameterException
	 *             invalid orgId, descendantOrgId, orgHierarchy
	 * @throws MissingParameterException
	 *             missing orgId, descendantOrgId, orgHierarchy
	 * @throws OperationFailedException
	 *             unable to complete request
	 * @throws PermissionDeniedException
	 *             authorization failure
	 */
	public Boolean isDescendant(@WebParam(name = "orgId")
	String orgId, @WebParam(name = "descendantOrgId")
	String descendantOrgId, @WebParam(name = "orgHierarchy")
	String orgHierarchy) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException;

	/**
	 * Retrieves the list of identifiers for the "descendant" organizations of
	 * the specified organization in the given organization hierarchy.
	 * Information about the distance from the original organization is not
	 * passed in this call, so this can be seen as a flattened and de-duplicated
	 * representation.
	 * 
	 * @param orgId
	 *            identifier of the "ancestor" organization
	 * @param orgHierarchy
	 *            identifier of the organization hierarchy to be checked against
	 * @return list of identifiers for the "descendant" organizations for the
	 *         specified organization
	 * @throws InvalidParameterException
	 *             invalid orgId, orgHierarchy
	 * @throws MissingParameterException
	 *             missing orgId, orgHierarchy
	 * @throws OperationFailedException
	 *             unable to complete request
	 * @throws PermissionDeniedException
	 *             authorization failure
	 */
	public List<String> findAllDescendants(@WebParam(name = "orgId")
	String orgId, @WebParam(name = "orgHierarchy")
	String orgHierarchy) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException;

	/**
	 * Retrieves the list of identifiers for the "ancestor" organizations of the
	 * specified organization in the given organization hierarchy. Information
	 * about the distance from the original organization is not passed in this
	 * call, so this can be seen as a flattened and de-duplicated
	 * representation.
	 * 
	 * @param orgId
	 *            identifier of the "descendant" organization
	 * @param orgHierarchy
	 *            identifier of the organization hierarchy to be checked against
	 * @return list of identifiers for the "ancestor" organizations of the
	 *         specified organization
	 * @throws InvalidParameterException
	 *             invalid orgId, orgHierarchy
	 * @throws MissingParameterException
	 *             missing orgId, orgHierarchy
	 * @throws OperationFailedException
	 *             unable to complete request
	 * @throws PermissionDeniedException
	 *             authorization failure
	 */
	public List<String> findAncestors(@WebParam(name = "orgId")
	String orgId, @WebParam(name = "orgHierarchy")
	String orgHierarchy) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException;

	/**
	 * Retrieves an existing org to person relation by the relation ID
	 * 
	 * @param orgPersonRelationId
	 *            identifier for org to person relation to be retrieved
	 * @return details of the orgPersonRelation for this id
	 * @throws DoesNotExistException
	 *             orgPersonRelationId not found
	 * @throws InvalidParameterException
	 *             invalid orgPersonRelationId
	 * @throws MissingParameterException
	 *             invalid orgPersonRelationId
	 * @throws OperationFailedException
	 *             unable to complete request
	 * @throws PermissionDeniedException
	 *             authorization failure
	 */
	public OrgPersonRelationInfo fetchOrgPersonRelation(
			@WebParam(name = "orgPersonRelationId")
			String orgPersonRelationId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	/**
	 * Retrieves a list of existing organization to person relations from a list
	 * of org person relation IDs
	 * 
	 * @param orgPersonRelationIdList
	 *            identifiers for org person relations to be retrieved
	 * @return details of the relations for these ids
	 * @throws DoesNotExistException
	 *             orgPersonRelationId not found
	 * @throws InvalidParameterException
	 *             invalid orgPersonRelationId
	 * @throws MissingParameterException
	 *             invalid orgPersonRelationId
	 * @throws OperationFailedException
	 *             unable to complete request
	 * @throws PermissionDeniedException
	 *             authorization failure
	 */
	public List<OrgPersonRelationInfo> findOrgPersonRelationsByIdList(
			@WebParam(name = "orgPersonRelationIdList")
			List<String> orgPersonRelationIdList) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	/**
	 * Retrieves the list of identifiers for people that have the specified type
	 * of relationship to this organization
	 * 
	 * @param orgId
	 *            identifier of the organization that members are being found
	 *            for
	 * @param orgPersonRelationTypeKey
	 *            type of organization person relationship that is being looked
	 *            for
	 * @return list of person identifiers that match supplied criteria
	 * @throws DoesNotExistException
	 *             orgId, orgPersonRelationType not found
	 * @throws InvalidParameterException
	 *             invalid orgId, orgPersonRelationType
	 * @throws MissingParameterException
	 *             missing orgId, orgPersonRelationType
	 * @throws OperationFailedException
	 *             unable to complete request
	 * @throws PermissionDeniedException
	 *             authorization failure
	 */
	public List<String> findPersonIdsForOrgByRelationType(
			@WebParam(name = "orgId")
			String orgId, @WebParam(name = "orgPersonRelationTypeKey")
			String orgPersonRelationTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	/**
	 * Retrieves the org to person relations for a particular organization
	 * 
	 * @param orgId
	 *            identifier for the organization for which organization person
	 *            relationships are to be found
	 * @return list of organization person relationships found for the supplied
	 *         organization
	 * @throws DoesNotExistException
	 *             orgId not found
	 * @throws InvalidParameterException
	 *             invalid orgId
	 * @throws MissingParameterException
	 *             missing orgId
	 * @throws OperationFailedException
	 *             unable to complete request
	 * @throws PermissionDeniedException
	 *             authorization failure
	 */
	public List<OrgPersonRelationInfo> findOrgPersonRelationsByOrg(
			@WebParam(name = "orgId")
			String orgId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	/**
	 * Retrieves all OrgPersonRelations for a particular Person and Organization
	 * 
	 * @param personId
	 *            person to use to look for relationships
	 * @param orgId
	 *            organization to use to look for relationships
	 * @return list of organization person relationships that exist for the
	 *         supplied person and organization
	 * @throws DoesNotExistException
	 *             personId, orgId not found
	 * @throws InvalidParameterException
	 *             invalid personId, orgId
	 * @throws MissingParameterException
	 *             missing personId, orgId
	 * @throws OperationFailedException
	 *             unable to complete request
	 * @throws PermissionDeniedException
	 *             authorization failure
	 */
	public List<OrgPersonRelationInfo> findOrgPersonRelationsByPerson(
			@WebParam(name = "personId")
			String personId, @WebParam(name = "orgId")
			String orgId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	/**
	 * Retrieves all organization person relationships for a person.
	 * 
	 * @param personId
	 *            person to find all relationships for
	 * @return List of organization person relationships that exist for this
	 *         person
	 * @throws DoesNotExistException
	 *             personId not found
	 * @throws InvalidParameterException
	 *             invalid personId
	 * @throws MissingParameterException
	 *             missing personId
	 * @throws OperationFailedException
	 *             unable to complete request
	 * @throws PermissionDeniedException
	 *             authorization failure
	 */
	public List<OrgPersonRelationInfo> findAllOrgPersonRelationsByPerson(
			@WebParam(name = "personId")
			String personId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	/**
	 * Retrieves all organization person relationships for an organization.
	 * 
	 * @param orgId
	 *            organization to find all relationships for
	 * @return list of organization person relationships that exist for this
	 *         organization
	 * @throws DoesNotExistException
	 *             orgId not found
	 * @throws InvalidParameterException
	 *             invalid orgId
	 * @throws MissingParameterException
	 *             missing orgId
	 * @throws OperationFailedException
	 *             unable to complete request
	 * @throws PermissionDeniedException
	 *             authorization failure
	 */
	public List<OrgPersonRelationInfo> findAllOrgPersonRelationsByOrg(
			@WebParam(name = "orgId")
			String orgId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	/**
	 * Tests if a person has a current relationship with a specified
	 * organization
	 * 
	 * @param orgId
	 *            identifier of the organization
	 * @param personId
	 *            identifier of the person
	 * @param orgPersonRelationTypeKey
	 *            type of relationship between the person and organization
	 * @return true if a relationship exists
	 * @throws InvalidParameterException
	 *             invalid orgId, personId, orgPersonRelationTypeKey
	 * @throws MissingParameterException
	 *             missing orgId, personId, orgPersonRelationTypeKey
	 * @throws OperationFailedException
	 *             unable to complete request
	 * @throws PermissionDeniedException
	 *             authorization failure
	 */
	public Boolean hasOrgPersonRelation(@WebParam(name = "orgId")
	String orgId, @WebParam(name = "personId")
	String personId, @WebParam(name = "orgPersonRelationTypeKey")
	String orgPersonRelationTypeKey) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException;

	/**
	 * Retrieves a list of organization-specific restrictions on relationships
	 * with people for a particular organization.
	 * 
	 * @param orgId
	 *            identifier of the organization
	 * @return list of the organization-specific position restriction
	 *         information
	 * @throws DataValidationErrorException
	 *             One or more values invalid for this operation
	 * @throws DoesNotExistException
	 *             the orgId not found
	 * @throws InvalidParameterException
	 *             invalid orgId
	 * @throws MissingParameterException
	 *             missing orgId
	 * @throws PermissionDeniedException
	 *             authorization failure
	 * @throws OperationFailedException
	 *             unable to complete request
	 */
	public List<OrgPositionRestrictionInfo> findPositionRestrictionsByOrg(
			@WebParam(name = "orgId")
			String orgId) throws DataValidationErrorException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, PermissionDeniedException,
			OperationFailedException;

	/**
	 * Searches for identifiers of organizations by complex criteria and logic.
	 * 
	 * @param orgCriteria
	 *            criteria to be used for search, e.g. by type, by name, by
	 *            number of members
	 * @return list of organization ids that matched the supplied criteria
	 * @throws InvalidParameterException
	 *             invalid organization criteria
	 * @throws MissingParameterException
	 *             missing organization criteria
	 * @throws OperationFailedException
	 *             unable to complete request
	 * @throws PermissionDeniedException
	 *             authorization failure
	 */
	public List<String> searchForOrganizations(@WebParam(name = "orgCriteria")
	OrgCriteria orgCriteria) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException;

	/**
	 * Searches for identifiers of organization to organization relationships by
	 * complex criteria and logic.
	 * 
	 * @param orgOrgRelationCriteria
	 *            criteria to be used for search, e.g. by type, by name, by
	 *            number of members
	 * @return list of organization to organization relationship identifiers
	 *         that matched the supplied criteria
	 * @throws InvalidParameterException
	 *             invalid organization to organization relationship criteria
	 * @throws MissingParameterException
	 *             missing organization to organization relationship criteria
	 * @throws OperationFailedException
	 *             unable to complete request
	 * @throws PermissionDeniedException
	 *             authorization failure
	 */
	public List<String> searchForOrgOrgRelations(
			@WebParam(name = "orgOrgRelationCriteria")
			OrgOrgRelationCriteria orgOrgRelationCriteria)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	/**
	 * Searches for identifiers of organization to person relationships by
	 * complex criteria and logic.
	 * 
	 * @param orgPersonRelationCriteria
	 *            criteria to be used for search, e.g. by type, by name, by
	 *            number of members
	 * @return list of organization to person relationship identifiers that
	 *         matched the supplied criteria
	 * @throws InvalidParameterException
	 *             invalid organization to person relationship criteria
	 * @throws MissingParameterException
	 *             missing organization to person relationship criteria
	 * @throws OperationFailedException
	 *             unable to complete request
	 * @throws PermissionDeniedException
	 *             authorization failure
	 */
	public List<String> searchForOrgPersonRelations(
			@WebParam(name = "orgPersonRelationCriteria")
			OrgPersonRelationCriteria orgPersonRelationCriteria)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	/**
	 * Creates a new organization
	 * 
	 * @param orgTypeKey
	 *            Unique identifier for an organization type.
	 * @param orgInfo
	 *            information about the organization to be created
	 * @return newly created organization information
	 * @throws AlreadyExistsException
	 *             org already exists
	 * @throws DataValidationErrorException
	 *             one or more values invalid for this operation
	 * @throws InvalidParameterException
	 *             invalid orgInfo
	 * @throws MissingParameterException
	 *             missing orgInfo
	 * @throws OperationFailedException
	 *             unable to complete request
	 * @throws PermissionDeniedException
	 *             authorization failure
	 */
	public OrgInfo createOrganization(@WebParam(name = "orgTypeKey")
	String orgTypeKey, @WebParam(name = "orgInfo")
	OrgInfo orgInfo) throws AlreadyExistsException,
			DataValidationErrorException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException;

	/**
	 * Updates an existing organization
	 * 
	 * @param orgId
	 *            identifier for org to be updated
	 * @param orgInfo
	 *            information about the organization to be updated
	 * @return updated organization information
	 * @throws DataValidationErrorException
	 *             one or more values invalid for this operation
	 * @throws DoesNotExistException
	 *             orgId not found
	 * @throws InvalidParameterException
	 *             invalid orgInfo, orgID
	 * @throws MissingParameterException
	 *             missing orgInfo, orgID
	 * @throws OperationFailedException
	 *             unable to complete request
	 * @throws PermissionDeniedException
	 *             authorization failure
	 * @throws VersionMismatchException
	 *             action was attempted on an out of date version.
	 */
	public OrgInfo updateOrganization(@WebParam(name = "orgId")
	String orgId, @WebParam(name = "orgInfo")
	OrgInfo orgInfo) throws DataValidationErrorException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, VersionMismatchException;

	/**
	 * Deletes an existing organization.
	 * 
	 * @param orgId
	 *            identifier for org to be deleted
	 * @return status of the operation (success or failure)
	 * @throws DoesNotExistException
	 *             orgId not found
	 * @throws InvalidParameterException
	 *             invalid orgId
	 * @throws MissingParameterException
	 *             missing orgId
	 * @throws OperationFailedException
	 *             unable to complete request
	 * @throws PermissionDeniedException
	 *             authorization failure
	 */
	public Status deleteOrganization(@WebParam(name = "orgId")
	String orgId) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException;

	/**
	 * Creates an organization to organization relationship between two
	 * organizations of a particular type.
	 * 
	 * @param orgId
	 *            identifier of the organization
	 * @param relatedOrgId
	 *            identifier of the "child" organization
	 * @param orgOrgRelationTypeKey
	 *            identifier of the type of relationship between the
	 *            organizations
	 * @param orgOrgRelationInfo
	 *            organization to organization relationship information to be
	 *            created
	 * @return information about the new organization to organization
	 *         relationship
	 * @throws AlreadyExistsException
	 *             org org relationship already exists
	 * @throws DataValidationErrorException
	 *             one or more values invalid for this operation
	 * @throws DoesNotExistException
	 *             orgId, relatedOrgId, orgOrgRelationTypeKey not found
	 * @throws InvalidParameterException
	 *             invalid orgId, relatedOrgId, orgOrgRelationTypeKey create
	 *             info
	 * @throws MissingParameterException
	 *             missing orgId, relatedOrgId, orgOrgRelationTypeKey create
	 *             info
	 * @throws PermissionDeniedException
	 *             authorization failure
	 * @throws OperationFailedException
	 *             unable to complete request
	 */
	public OrgOrgRelationInfo createOrgOrgRelation(@WebParam(name = "orgId")
	String orgId, @WebParam(name = "relatedOrgId")
	String relatedOrgId, @WebParam(name = "orgOrgRelationTypeKey")
	String orgOrgRelationTypeKey, @WebParam(name = "orgOrgRelationInfo")
	OrgOrgRelationInfo orgOrgRelationInfo) throws AlreadyExistsException,
			DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			PermissionDeniedException, OperationFailedException;

	/**
	 * Updates an organization to organization relationship.
	 * 
	 * @param orgOrgRelationId
	 *            organization to organization relationship identifier
	 * @param orgOrgRelationInfo
	 *            detail information of the organization to organization
	 *            relationship
	 * @return updated organization to organization relationship information
	 * @throws DataValidationErrorException
	 *             one or more values invalid for this operation
	 * @throws DoesNotExistException
	 *             orgOrgRelationId not found
	 * @throws InvalidParameterException
	 *             invalid orgOrgRelationId, orgOrgRelation update info
	 * @throws MissingParameterException
	 *             missing orgOrgRelationId, orgOrgRelation update info
	 * @throws OperationFailedException
	 *             unable to complete request
	 * @throws PermissionDeniedException
	 *             authorization failure
	 * @throws VersionMismatchException
	 *             action was attempted on an out of date version.
	 */
	public OrgOrgRelationInfo updateOrgOrgRelation(
			@WebParam(name = "orgOrgRelationId")
			String orgOrgRelationId, @WebParam(name = "orgOrgRelationInfo")
			OrgOrgRelationInfo orgOrgRelationInfo)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException;

	/**
	 * Removes an organization to organization relationship.
	 * 
	 * @param orgOrgRelationId
	 *            organization to organization relationship identifier
	 * @return status of the operation
	 * @throws DoesNotExistException
	 *             orgOrgRelationId not found
	 * @throws InvalidParameterException
	 *             invalid orgOrgRelationId
	 * @throws MissingParameterException
	 *             missing orgOrgRelationId
	 * @throws OperationFailedException
	 *             unable to complete request
	 * @throws PermissionDeniedException
	 *             authorization failure
	 */
	public Status removeOrgOrgRelation(@WebParam(name = "orgOrgRelationId")
	String orgOrgRelationId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	/**
	 * Creates an organization to person relationship between an organization
	 * and a person with a particular type.
	 * 
	 * @param orgId
	 *            organization
	 * @param personId
	 *            person
	 * @param orgPersonRelationTypeKey
	 *            organization person relationship type
	 * @param orgPersonRelationInfo
	 *            organization person relationship information
	 * @return detail information of the new organization to person relationship
	 * @throws AlreadyExistsException
	 *             organization person relationship already exists
	 * @throws DataValidationErrorException
	 *             one or more values invalid for this operation
	 * @throws DoesNotExistException
	 *             personId, orgId, orgPersonRelationTypeKey not found
	 * @throws InvalidParameterException
	 *             invalid personId, orgId, orgPersonRelationTypeKey,
	 *             orgPersonRelationInfo
	 * @throws MissingParameterException
	 *             missing personId, orgId, orgPersonRelationTypeKey,
	 *             orgPersonRelationInfo
	 * @throws PermissionDeniedException
	 *             authorization failure
	 * @throws OperationFailedException
	 *             unable to complete request
	 */
	public OrgPersonRelationInfo createOrgPersonRelation(
			@WebParam(name = "orgId")
			String orgId, @WebParam(name = "personId")
			String personId, @WebParam(name = "orgPersonRelationTypeKey")
			String orgPersonRelationTypeKey,
			@WebParam(name = "orgPersonRelationInfo")
			OrgPersonRelationInfo orgPersonRelationInfo)
			throws AlreadyExistsException, DataValidationErrorException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, PermissionDeniedException,
			OperationFailedException;

	/**
	 * Updates an organization to person relationship.
	 * 
	 * @param orgPersonRelationId
	 *            organization person relationship identifier
	 * @param orgPersonRelationInfo
	 *            information about the organization to person relationship to
	 *            be updated
	 * @return updated organization to person relationship information
	 * @throws DataValidationErrorException
	 *             one or more values invalid for this operation
	 * @throws DoesNotExistException
	 *             orgPersonRelationId not found
	 * @throws InvalidParameterException
	 *             invalid orgPersonRelationId, orgPersonRelationInfo
	 * @throws MissingParameterException
	 *             missing orgPersonRelationId, orgPersonRelationInfo
	 * @throws OperationFailedException
	 *             unable to complete request
	 * @throws PermissionDeniedException
	 *             authorization failure
	 * @throws VersionMismatchException
	 *             action was attempted on an out of date version.
	 */
	public OrgPersonRelationInfo updateOrgPersonRelation(
			@WebParam(name = "orgPersonRelationId")
			String orgPersonRelationId,
			@WebParam(name = "orgPersonRelationInfo")
			OrgPersonRelationInfo orgPersonRelationInfo)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException;

	/**
	 * Removes an organization to person relationship.
	 * 
	 * @param orgPersonRelationId
	 *            organization person relationship identifier
	 * @return status of the operation
	 * @throws DoesNotExistException
	 *             orgPersonRelationId not found
	 * @throws InvalidParameterException
	 *             invalid orgPersonRelationId
	 * @throws MissingParameterException
	 *             missing orgPersonRelationId
	 * @throws OperationFailedException
	 *             unable to complete request
	 * @throws PermissionDeniedException
	 *             authorization failure
	 */
	public Status removeOrgPersonRelation(
			@WebParam(name = "orgPersonRelationId")
			String orgPersonRelationId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	/**
	 * Adds a description of the organization-specific usage of an organization
	 * person relationship type. This information typically coincides with
	 * constraints, such as how many relationships of a given type may be active
	 * at a particular time, etc.
	 * 
	 * @param orgId
	 *            organization
	 * @param orgPersonRelationTypeKey
	 *            organization person relationship type
	 * @param orgPositionRestrictionInfo
	 *            organization position restriction information
	 * @return information about the newly created organization position
	 *         restriction
	 * @throws AlreadyExistsException
	 *             org position restriction already exists
	 * @throws DataValidationErrorException
	 *             one or more values invalid for this operation
	 * @throws DoesNotExistException
	 *             the orgId, orgPersonRelationTypeKey not found
	 * @throws InvalidParameterException
	 *             invalid orgId, orgPersonRelationTypeKey,
	 *             orgPositionRestrictionInfo
	 * @throws MissingParameterException
	 *             missing orgId, orgPersonRelationTypeKey,
	 *             orgPositionRestrictionInfo
	 * @throws OperationFailedException
	 *             unable to complete request
	 * @throws PermissionDeniedException
	 *             authorization failure
	 */
	public OrgPositionRestrictionInfo addPositionRestrictionToOrg(
			@WebParam(name = "orgId")
			String orgId, @WebParam(name = "orgPersonRelationTypeKey")
			String orgPersonRelationTypeKey,
			@WebParam(name = "orgPositionRestrictionInfo")
			OrgPositionRestrictionInfo orgPositionRestrictionInfo)
			throws AlreadyExistsException, DataValidationErrorException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException;

	/**
	 * Updates a description of the organization-specific usage of an
	 * organization person relationship type.
	 * 
	 * @param orgId
	 *            organization
	 * @param orgPersonRelationTypeKey
	 *            organization person relationship type
	 * @param orgPositionRestrictionInfo
	 *            organization position restriction information
	 * @return information about the updated organization position restriction
	 * @throws DataValidationErrorException
	 *             one or more values invalid for this operation
	 * @throws DoesNotExistException
	 *             orgId, orgPersonRelationTypeKey not found
	 * @throws InvalidParameterException
	 *             invalid orgId, orgPersonRelationTypeKey,
	 *             orgPositionRestrictionInfo
	 * @throws MissingParameterException
	 *             missing orgId, orgPersonRelationTypeKey,
	 *             orgPositionRestrictionInfo
	 * @throws OperationFailedException
	 *             unable to complete request
	 * @throws PermissionDeniedException
	 *             authorization failure
	 * @throws VersionMismatchException
	 *             action was attempted on an out of date version.
	 */
	public OrgPositionRestrictionInfo updatePositionRestrictionForOrg(
			@WebParam(name = "orgId")
			String orgId, @WebParam(name = "orgPersonRelationTypeKey")
			String orgPersonRelationTypeKey,
			@WebParam(name = "orgPositionRestrictionInfo")
			OrgPositionRestrictionInfo orgPositionRestrictionInfo)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException;

	/**
	 * Removes a description of the organization-specific usage of an
	 * organization person relationship type.
	 * 
	 * @param orgId
	 *            organization
	 * @param orgPersonRelationTypeKey
	 *            organization person relationship type
	 * @return status
	 * @throws DoesNotExistException
	 *             the orgId, orgPersonRelationTypeKey not found
	 * @throws InvalidParameterException
	 *             invalid orgId, orgPersonRelationTypeKey
	 * @throws MissingParameterException
	 *             missing orgId, orgPersonRelationTypeKey
	 * @throws OperationFailedException
	 *             unable to complete request
	 * @throws PermissionDeniedException
	 *             authorization failure
	 */
	public Status removePositionRestrictionFromOrg(@WebParam(name = "orgId")
	String orgId, @WebParam(name = "orgPersonRelationTypeKey")
	String orgPersonRelationTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

}
