package edu.umd.ks.poc.lum.web.lu.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.SerializableException;

import edu.umd.ks.poc.lum.lu.dto.CluCreateInfo;
import edu.umd.ks.poc.lum.lu.dto.CluCriteria;
import edu.umd.ks.poc.lum.lu.dto.CluDisplay;
import edu.umd.ks.poc.lum.lu.dto.CluInfo;
import edu.umd.ks.poc.lum.lu.dto.CluRelationAssignInfo;
import edu.umd.ks.poc.lum.lu.dto.CluRelationCriteria;
import edu.umd.ks.poc.lum.lu.dto.CluRelationDisplay;
import edu.umd.ks.poc.lum.lu.dto.CluRelationInfo;
import edu.umd.ks.poc.lum.lu.dto.CluRelationUpdateInfo;
import edu.umd.ks.poc.lum.lu.dto.CluSetCreateInfo;
import edu.umd.ks.poc.lum.lu.dto.CluSetInfo;
import edu.umd.ks.poc.lum.lu.dto.CluSetUpdateInfo;
import edu.umd.ks.poc.lum.lu.dto.CluUpdateInfo;
import edu.umd.ks.poc.lum.lu.dto.LuAttributeTypeInfo;
import edu.umd.ks.poc.lum.lu.dto.LuTypeInfo;
import edu.umd.ks.poc.lum.lu.dto.LuiCreateInfo;
import edu.umd.ks.poc.lum.lu.dto.LuiCriteria;
import edu.umd.ks.poc.lum.lu.dto.LuiDisplay;
import edu.umd.ks.poc.lum.lu.dto.LuiInfo;
import edu.umd.ks.poc.lum.lu.dto.LuiRelationAssignInfo;
import edu.umd.ks.poc.lum.lu.dto.LuiRelationCriteria;
import edu.umd.ks.poc.lum.lu.dto.LuiRelationDisplay;
import edu.umd.ks.poc.lum.lu.dto.LuiRelationInfo;
import edu.umd.ks.poc.lum.lu.dto.LuiRelationUpdateInfo;
import edu.umd.ks.poc.lum.lu.dto.LuiUpdateInfo;
import edu.umd.ks.poc.lum.lu.dto.Status;

public interface LuService {
	/* Setup */
	/**
	 * Retrieves the list of LU types
	 * 
	 * @return list of LU types
	 * @throws OperationFailedException
	 */

	public List<LuTypeInfo> findLuTypes() throws SerializableException;

	/**
	 * Retrieves the list of LU to LU relation types
	 * 
	 * @return list of LU to LU relation types
	 * @throws OperationFailedException
	 */

	public List<String> findLuRelationTypes() throws SerializableException;

	/**
	 * Retrieves information about a LU Type
	 * 
	 * @param luTypeKey
	 * @return information about a LU Type
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */

	public LuTypeInfo fetchLuType(String luTypeKey)
			throws SerializableException;

	/**
	 * Retrieves the list of allowed relation types between the two specified LU
	 * Types
	 * 
	 * @param luTypeKey
	 * @param relatedLuTypeKey
	 * @return list of LU to LU relation types
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */

	public List<String> findAllowedLuLuRelationTypesForLuType(

	String luTypeKey, String relatedLuTypeKey) throws SerializableException;

	/* Read */
	/**
	 * Retrieves information about a CLU
	 * 
	 * @param cluId
	 * @return information about a CLU
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */

	public CluInfo fetchClu(String cluId) throws SerializableException;

	/**
	 * Retrieves information about a LUI
	 * 
	 * @param luiId
	 * @return information about a LUI
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */

	public LuiInfo fetchLui(String luiId) throws SerializableException;

	/**
	 * Retrieves Display information about a LUI
	 * 
	 * @param luiId
	 * @return Display information about a LUI
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */

	public LuiDisplay fetchLuiDisplay(String luiId)
			throws SerializableException;

	/**
	 * Retrieves the list of CLUs for the specified LU Type
	 * 
	 * @param luTypeKey
	 * @return list of CLU information
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */

	public List<CluDisplay> findClusForLuType(String luTypeKey)
			throws SerializableException;

	/**
	 * Retrieves the list of CLU ids for the specified LU Type
	 * 
	 * @param luTypeKey
	 * @return list of CLU identifiers
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */

	public List<String> findCluIdsForLuType(String luTypeKey)
			throws SerializableException;

	/**
	 * Retrieves the list of LUIs for the specified CLU
	 * 
	 * @param cluId
	 * @param atpId
	 * @return list of LUI information
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */

	public List<LuiDisplay> findLuisForClu(String cluId, String atpId)
			throws SerializableException;

	/**
	 * Retrieves the list of LUI ids for the specified CLU
	 * 
	 * @param cluId
	 * @param atpId
	 * @return list of LUI identifiers
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */

	public List<String> findLuiIdsForClu(String cluId, String atpId)
			throws SerializableException;

	/**
	 * Retrieves the list of allowed relation types between the two specified
	 * CLUs
	 * 
	 * @param cluId
	 * @param relatedCluId
	 * @return list of LU to LU relation types
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */

	public List<String> findAllowedLuRelationTypesForClu(

	String cluId, String relatedCluId) throws SerializableException;

	/**
	 * Retrieves the list of allowed relation types between the two specified
	 * LUIs
	 * 
	 * @param luiId
	 * @param relatedLuiId
	 * @return list of LU to LU relation types
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */

	public List<String> findAllowedLuRelationTypesForLui(

	String luiId, String relatedLuiId) throws SerializableException;

	/**
	 * Retrieves the list of CLU information for the specified related CLU Id
	 * and LU to LU relation type (findRelatedClusForCluId from the other
	 * direction)
	 * 
	 * @param cluId
	 * @param luRelationType
	 * @return list of CLU information
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */

	public List<CluDisplay> findClusByRelation(String cluId,
			String luRelationTypeId) throws SerializableException;

	/**
	 * Retrieves the list of CLU Ids for the specified related CLU Id and LU to
	 * LU relation type (findRelatedCluIdsForCluId from the other direction)
	 * 
	 * @param cluId
	 * @param luRelationTypeId
	 * @return list of CLU identifiers
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */

	public List<String> findCluIdsByRelation(String cluId,
			String luRelationTypeId) throws SerializableException;

	/**
	 * Retrieves the list of LUI information for the specified related LUI Id
	 * and LU to LU relation type (findRelatedLuisForLuiId from the other
	 * direction)
	 * 
	 * @param luiId
	 * @param luRelationTypeId
	 * @return list of LUI information
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */

	public List<LuiDisplay> findLuisByRelation(String luiId,
			String luRelationTypeId) throws SerializableException;

	/**
	 * Retrieves the list of LUI Ids for the specified related LUI Id and LU to
	 * LU relation type (findRelatedLuiIdsForLuiId from the other direction)
	 * 
	 * @param luiId
	 * @param luRelationTypeId
	 * @return list of LUI identifiers
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */

	public List<String> findLuiIdsByRelation(String luiId,
			String luRelationTypeId) throws SerializableException;

	/**
	 * Retrieves the list of related CLU information for the specified LUI Id
	 * and LU to LU relation type (findClusByRelation from the other direction)
	 * 
	 * @param cluId
	 * @param luRelationTypeId
	 * @return list of CLU information
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */

	public List<CluDisplay> findRelatedClusForCluId(String cluId,
			String luRelationTypeId) throws SerializableException;

	/**
	 * Retrieves the list of related CLU Ids for the specified LUI Id and LU to
	 * LU relation type (findCluIdsByRelation from the other direction)
	 * 
	 * @param cluId
	 * @param luRelationTypeId
	 * @return list of CLU identifiers
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */

	public List<String> findRelatedCluIdsForCluId(String cluId,
			String luRelationTypeId) throws SerializableException;

	/**
	 * Retrieves the list of related LUI information for the specified LUI Id
	 * and LU to LU relation type (findLuisByRelation from the other direction)
	 * 
	 * @param luiId
	 * @param luRelationTypeId
	 * @return list of LUI information
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */

	public List<CluDisplay> findRelatedLuisForLuiId(String luiId,
			String luRelationTypeId) throws SerializableException;

	/**
	 * Retrieves the list of related LUI Ids for the specified LUI Id and LU to
	 * LU relation type. (findLuiIdsByRelation from the other direction)
	 * 
	 * @param luiId
	 * @param luRelationTypeId
	 * @return list of LUI identifiers
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */

	public List<String> findRelatedLuiIdsForLuiId(String luiId,
			String luRelationTypeId) throws SerializableException;

	/**
	 * Retrieves the relationship information between the specified CLUs
	 * 
	 * @param cluId
	 * @param relatedCluId
	 * @param luRelationTypeId
	 * @return information on the relation between two CLUs
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */

	public CluRelationInfo fetchCluRelation(String cluId, String relatedCluId,
			String luRelationTypeId) throws SerializableException;

	/**
	 * Retrieves the relationship information between the specified LUIs
	 * 
	 * @param luiId
	 * @param relatedLuiId
	 * @param luRelationTypeId
	 * @return information on the relation between two LUIs
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */

	public LuiRelationInfo fetchLuiRelation(String luiId, String relatedLuiId,
			String luRelationTypeId) throws SerializableException;

	/**
	 * Retrieves the list of relationship information for the specified CLU
	 * 
	 * @param cluId
	 * @param luRelationType
	 * @return list of relation information
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */

	public List<CluRelationDisplay> findCluRelations(String cluId,
			String luRelationType) throws SerializableException;

	/**
	 * Retrieves the list of relationship information for the specified LUI
	 * 
	 * @param luiId
	 * @param luRelationTypeId
	 * @return list of relation information
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */

	public List<LuiRelationDisplay> findLuiRelations(String luiId,
			String luRelationTypeId) throws SerializableException;

	/**
	 * Retrieves an "English" translation of the prerequisites for the target
	 * CLU.
	 * 
	 * @param cluId
	 * @return "English" description of prerequisites
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */

	public String findPrerequisitesForDisplay(String cluId)
			throws SerializableException;

	/**
	 * Retrieves list of simple prerequisite(s) for the target CLU.
	 * 
	 * @param cluId
	 * @return list of simple prerequisite(s), separated by commas with and
	 *         before last one
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */

	public List<CluDisplay> findSimplePrerequisites(String cluId)
			throws SerializableException;

	/**
	 * Retrieves an "English" translation of the corequisites for the target
	 * CLU.
	 * 
	 * @param cluId
	 * @return "English" description of corequisites
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */

	public String findCorequisitesForDisplay(String cluId)
			throws SerializableException;

	/**
	 * Retrieves list of simple corequisites for the target CLU.
	 * 
	 * @param cluId
	 * @return list of simple corequisites
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */

	public List<CluDisplay> findSimpleCorequisites(String cluId)
			throws SerializableException;

	/**
	 * Retrieves an "English" translation of the antirequisites for the target
	 * CLU.
	 * 
	 * @param cluId
	 * @return "English" description of antirequisites
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */

	public String findAntirequisitesForDisplay(String cluId)
			throws SerializableException;

	/**
	 * Retrieves list of simple antirequisites for the target CLU.
	 * 
	 * @param cluId
	 * @return list of simple antirequisites
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */

	public List<CluDisplay> findSimpleAntirequisites(String cluId)
			throws SerializableException;

	/**
	 * Retrieves an "English" translation of the equivalencies for the target
	 * CLU.
	 * 
	 * @param cluId
	 * @return "English" description of equivalencies
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */

	public String findEquivalenciesForDisplay(String cluId)
			throws SerializableException;

	/**
	 * Retrieves list of simple equivalencies for the target CLU.
	 * 
	 * @param cluId
	 * @return list of simple equivalencies
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */

	public List<CluDisplay> findSimpleEquivalencies(String cluId)
			throws SerializableException;

	/**
	 * 
	 * Retrieve information on a CLU set. This information should be about the
	 * set itself, and in the case of a dynamic CLU set, should include the
	 * criteria used to generate the set.
	 * 
	 * @param cluSetId
	 * @return The retrieved CLU set information
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */

	public CluSetInfo fetchCluSetInfo(String cluSetId)
			throws SerializableException;

	/**
	 * Retrieves the list of CLUs in a CLU set. This list will be flattened and
	 * de-duplicated in the case of the CLU set containing additional CLU sets.
	 * 
	 * @param cluSetId
	 * @return The retrieved list of information on the CLUs within the CLU set
	 *         (flattened and de-duped)
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */

	public List<CluDisplay> findClusFromCluSet(String cluSetId)
			throws SerializableException;

	/**
	 * Retrieves the list of CLU Identifiers within a CLU Set. This list will be
	 * flattened and de-duplicated in the case of the CLU set containing
	 * additional CLU sets.
	 * 
	 * @param cluSetId
	 * @return The retrieved list of CLU Ids within the specified CLU set
	 *         (flattened and de-duped)
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */

	public List<String> findCluIdsFromCluSet(String cluSetId)
			throws SerializableException;

	/**
	 * Retrieve the list of CLU Set Ids within a CLU Set
	 * 
	 * @param cluSetId
	 * @return The retrieved list of CLU Set Ids within the specified CLU set
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */

	public List<String> findCluSetIdsFromCluSet(String cluSetId)
			throws SerializableException;

	/**
	 * Retrieves the full list of CLUs in this CLU set. Duplicate CLUs resulting
	 * from additional CLU set members will be included in the results.
	 * 
	 * @param cluSetId
	 * @return The retrieved list of information on the CLUs within the CLU set
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */

	public List<CluDisplay> findEnumeratedClusInCluSet(

	String cluSetId) throws SerializableException;

	/**
	 * Retrieves the list of CLU Identifiers within a CLU Set. Duplicate CLU Ids
	 * resulting from additional CLU set members will be included in the
	 * results.
	 * 
	 * @param cluSetId
	 * @return The retrieved list of CLU Ids within the specified CLU set
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */

	public List<String> findEnumeratedCluIdsInCluSet(

	String cluSetId) throws SerializableException;

	/**
	 * Checks if a CLU is a member of a CLU set or any contained CLU set
	 * 
	 * @param cluId
	 * @param cluSetId
	 * @return True if the CLU is a member of the CLU Set
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */

	public boolean isCluInCluSet(String cluId, String cluSetId)
			throws SerializableException;

	/* Search */
	/**
	 * Retrieves CLU information by criteria
	 * 
	 * @param cluCriteria
	 * @return criteria to be used for retrieval of multiple CLUs
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */

	public List<CluDisplay> searchForClus(CluCriteria cluCriteria)
			throws SerializableException;

	/**
	 * Retrieves LUI information by criteria
	 * 
	 * @param luiCriteria
	 * @return list of information about LUIs that match the supplied criteria
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */

	public List<LuiDisplay> searchForLuis(LuiCriteria luiCriteria)
			throws SerializableException;

	/**
	 * Retrieves CLU ids by criteria
	 * 
	 * @param cluCriteria
	 * @return list of CLU identifiers that match the supplied criteria
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */

	public List<String> searchForCluIds(CluCriteria cluCriteria)
			throws SerializableException;

	/**
	 * Retrieves LUI ids by criteria
	 * 
	 * @param luiCriteria
	 * @return list of LUI identifiers that match the supplied criteria
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */

	public List<String> searchForLuiIds(LuiCriteria luiCriteria)
			throws SerializableException;

	/**
	 * Retrieves CLU to CLU relations by criteria
	 * 
	 * @param cluRelationCriteria
	 * @return list of CLU to CLU relations that match the supplied criteria
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */

	public List<CluRelationDisplay> searchForCluRelations(

	CluRelationCriteria cluRelationCriteria) throws SerializableException;

	/**
	 * Retrieves Lui to Lui relations by criteria
	 * 
	 * @param luiRelationCriteria
	 * @return list of LUI to LUI relations that match the supplied criteria
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */

	public List<LuiRelationDisplay> searchForLuiRelations(

	LuiRelationCriteria luiRelationCriteria) throws SerializableException;

	/* Maintenance */
	
	public String createAndRouteClu(String userId, String luTypeKey, CluCreateInfo cluCreateInfo, String createComment);
	public Status approveClu(String userId, String cluDocId, String cluId, String comment);
	public Status disapproveClu(String userId, String cluDocId, String cluId, String comment);
	
	/**
	 * Creates a CLU record
	 * 
	 * @param luTypeKey
	 * @param cluCreateInfo
	 * @return identifier for the newly created CLU
	 * @throws AlreadyExistsException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */

	public String createClu(String luTypeKey, CluCreateInfo cluCreateInfo)
			throws SerializableException;

	/**
	 * Creates a LUI record
	 * 
	 * @param cluId
	 * @param atpId
	 * @param luiCreateInfo
	 * @return identifier for the newly created LUI
	 * @throws AlreadyExistsException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */

	public String createLui(String cluId, String atpId,
			LuiCreateInfo luiCreateInfo) throws SerializableException;

	/**
	 * Updates a CLU record
	 * 
	 * @param cluId
	 * @param cluUpdateInfo
	 * @return status of the operation
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */

	public Status updateClu(String cluId, CluUpdateInfo cluUpdateInfo)
			throws SerializableException;

	/**
	 * Updates a LUI record
	 * 
	 * @param luiId
	 * @param cluUpdateInfo
	 * @return status of the operation
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */

	public Status updateLui(String luiId, LuiUpdateInfo cluUpdateInfo)
			throws SerializableException;

	/**
	 * Deletes a CLU record
	 * 
	 * @param cluId
	 * @return status of the operation
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws DependentObjectsExistException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */

	public Status deleteClu(String cluId) throws SerializableException;

	/**
	 * Deletes a LUI record
	 * 
	 * @param luiId
	 * @return status of the operation
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws DependentObjectsExistException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */

	public Status deleteLui(String luiId) throws SerializableException;

	/**
	 * Assigns a relationship between two CLUs
	 * 
	 * @param cluId
	 * @param relatedCluId
	 * @param luRelationType
	 * @param cluRelationAssignInfo
	 * @return status of the operation (success or failure)
	 * @throws AlreadyExistsException
	 * @throws CircularReferenceException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */

	public Status assignCluRelation(String cluId, String relatedCluId,
			String luRelationType, CluRelationAssignInfo cluRelationAssignInfo)
			throws SerializableException;

	/**
	 * Assigns a relationship between two LUIs
	 * 
	 * @param luiId
	 * @param relatedLuiId
	 * @param luRelationTypeId
	 * @param luiRelationAssignInfo
	 * @return status of the operation (success or failure)
	 * @throws AlreadyExistsException
	 * @throws CircularReferenceException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */

	public Status assignLuiRelation(String luiId, String relatedLuiId,
			String luRelationTypeId, LuiRelationAssignInfo luiRelationAssignInfo)
			throws SerializableException;

	/**
	 * Updates a relationship between two CLUs
	 * 
	 * @param cluId
	 * @param relatedCluId
	 * @param luRelationTypeId
	 * @param cluRelationUpdateInfo
	 * @return status of the operation (success or failure)
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */

	public Status updateCluRelation(String cluId, String relatedCluId,
			String luRelationTypeId, CluRelationUpdateInfo cluRelationUpdateInfo)
			throws SerializableException;

	/**
	 * Updates a relationship between two LUIs
	 * 
	 * @param luiId
	 * @param relatedLuiId
	 * @param luRelationType
	 * @param luiRelationUpdateInfo
	 * @return status of the operation (success or failure)
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */

	public Status updateLuiRelation(String luiId, String relatedLuiId,
			String luRelationType, LuiRelationUpdateInfo luiRelationUpdateInfo)
			throws SerializableException;

	/**
	 * Deletes a relationship between two CLUs
	 * 
	 * @param cluId
	 * @param relatedCluId
	 * @param luRelationTypeId
	 * @return status of the operation (success or failure)
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */

	public Status deleteCluRelation(String cluId, String relatedCluId,
			String luRelationTypeId) throws SerializableException;

	/**
	 * Deletes a relationship between two LUIs
	 * 
	 * @param luiId
	 * @param relatedLuiId
	 * @param luRelationTypeId
	 * @return status of the operation (success or failure)
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */

	public Status deleteLuiRelation(String luiId, String relatedLuiId,
			String luRelationTypeId) throws SerializableException;

	/**
	 * Adds a CLU as the prerequisite of another CLU. If there are two CLUs that
	 * must be completed as part of prereq, in no particular order, then this
	 * method would be used twice, once to add first prereq and then again to
	 * add the second. Logic is limited to "and"s between multiple prereq CLUs.
	 * 
	 * @param cluId
	 * @param prereqCluId
	 * @return status of the operation (success or failure)
	 * @throws DoesNotExistException
	 * @throws AlreadyExistsException
	 * @throws CircularReferenceException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */

	public Status addSimplePrerequisite(String cluId, String prereqCluId)
			throws SerializableException;

	/**
	 * Remove CLU as the prerequisite of another CLU
	 * 
	 * @param cluId
	 * @param prereqCluId
	 * @return status of the operation (success or failure)
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */

	public Status removeSimplePrerequisite(String cluId, String prereqCluId)
			throws SerializableException;

	/**
	 * Adds a CLU as the corequisite of another CLU. If there are two CLUs that
	 * must be completed as part of the coreq, in no particular order, then this
	 * method would be used twice, once to add first coreq and then again to add
	 * the second. Logic is limited to "and"s between multiple coreq CLUs.
	 * 
	 * @param cluId
	 * @param coreqCluId
	 * @return status of the operation (success or failure)
	 * @throws DoesNotExistException
	 * @throws AlreadyExistsException
	 * @throws CircularReferenceException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */

	public Status addSimpleCorequisite(String cluId, String coreqCluId)
			throws SerializableException;

	/**
	 * Remove CLU as the corequisite of another CLU
	 * 
	 * @param cluId
	 * @param coreqCluId
	 * @return status of the operation (success or failure)
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */

	public Status removeSimpleCorequisite(String cluId, String coreqCluId)
			throws SerializableException;

	/**
	 * Adds a CLU as the antirequisite of another CLU. If there are multiple
	 * CLUs that are antireqs, then this method would be used once for each
	 * antireq CLU. Logic is limited to "and"s between multiple antireq CLUs.
	 * 
	 * @param cluId
	 * @param antireqCluId
	 * @return status of the operation (success or failure)
	 * @throws DoesNotExistException
	 * @throws AlreadyExistsException
	 * @throws CircularReferenceException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */

	public Status addSimpleAntirequisite(String cluId, String antireqCluId)
			throws SerializableException;

	/**
	 * Remove CLU as the antirequisite of another CLU
	 * 
	 * @param cluId
	 * @param antireqCluId
	 * @return status of the operation (success or failure)
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */

	public Status removeSimpleAntirequisite(String cluId, String antireqCluId)
			throws SerializableException;

	/**
	 * Adds a CLU as an equivalent of another CLU. If there are multiple CLUs
	 * that are equivalent, then this method would be used once for each
	 * equivalent CLU. Logic is limited to "and"s between multiple equivalent
	 * CLUs.
	 * 
	 * @param cluId
	 * @param equivalentCluId
	 * @return status of the operation (success or failure)
	 * @throws DoesNotExistException
	 * @throws AlreadyExistsException
	 * @throws CircularReferenceException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */

	public Status addSimpleEquivalency(String cluId, String equivalentCluId)
			throws SerializableException;

	/**
	 * Remove CLU as the equivalent of another CLU
	 * 
	 * @param cluId
	 * @param equivalentCluId
	 * @return status of the operation (success or failure)
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */

	public Status removeSimpleEquivalency(String cluId, String equivalentCluId)
			throws SerializableException;

	/**
	 * Creates a CLU set with manually maintained membership. Sets created in
	 * this manner can contain other sets.
	 * 
	 * @param cluSetName
	 * @param cluSetCreateInfo
	 * @return the identifier of the created CLU set
	 * @throws AlreadyExistsException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */

	public String createEnumeratedCluSet(String cluSetName,
			CluSetCreateInfo cluSetCreateInfo) throws SerializableException;

	/**
	 * Creates a CLU set with membership determined via a search criteria based
	 * query. Sets created in this manner cannot have their membership managed
	 * manually and cannot contain other sets.
	 * 
	 * @param cluSetName
	 * @param cluSetCreateInfo
	 * @param cluCriteria
	 * @return the identifier of the created CLU set
	 * @throws AlreadyExistsException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */

	public String createDynamicCluSet(String cluSetName,
			CluSetCreateInfo cluSetCreateInfo, CluCriteria cluCriteria)
			throws SerializableException;

	/**
	 * Update the information for a CLU set
	 * 
	 * @param cluSetId
	 * @param cluSetUpdateInfo
	 * @return status of the operation (success or failure)
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */

	public Status updateCluSet(String cluSetId,
			CluSetUpdateInfo cluSetUpdateInfo) throws SerializableException;

	/**
	 * Delete a CLU set
	 * 
	 * @param cluSetId
	 * @return status of the operation (success or failure)
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */

	public Status deleteCluSet(String cluSetId) throws SerializableException;

	/**
	 * Adds one CLU set to another
	 * 
	 * @param cluSetId
	 * @param addedCluSetId
	 * @return status of the operation (success or failure)
	 * @throws DoesNotExistException
	 * @throws CircularReferenceException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */

	public Status addCluSetToCluSet(String cluSetId, String addedCluSetId)
			throws SerializableException;

	/**
	 * Removes one CLU set from another
	 * 
	 * @param cluSetId
	 * @param removedCluSetId
	 * @return status of the operation (success or failure)
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */

	public Status removeCluSetFromCluSet(String cluSetId, String removedCluSetId)
			throws SerializableException;

	/**
	 * Add a CLU to a CLU set
	 * 
	 * @param cluId
	 * @param cluSetId
	 * @return status of the operation (success or failure)
	 * @throws DoesNotExistException
	 * @throws CircularReferenceException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws UnsupportedActionException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */

	public Status addCluToCluSet(String cluId, String cluSetId)
			throws SerializableException;

	/**
	 * Remove a CLU from a CLU set
	 * 
	 * @param cluId
	 * @param cluSetId
	 * @return status of the operation (success or failure)
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws UnsupportedActionException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */

	public Status removeCluFromCluSet(String cluId, String cluSetId)
			throws SerializableException;

	public String createLuTypeInfo(LuTypeInfo luTypeInfo);

	public String updateLuTypeInfo(LuTypeInfo luTypeInfo);

	public Status removeLuTypeInfo(String luTypeInfoId);

	public String createLuAttributeTypeInfo(

	LuAttributeTypeInfo luAttributeTypeInfo);

	public String updateLuAttributeTypeInfo(
			LuAttributeTypeInfo luAttributeTypeInfo);

	public Status removeLuAttributeTypeInfo(

	String luAttributeTypeInfoId);

	public List<LuAttributeTypeInfo> findLuAttributeTypes();

	public List<LuAttributeTypeInfo> findLuAttributeTypesForLuTypeId(

	String luTypeId);

	public LuAttributeTypeInfo fetchLuAttributeType(

	String luAttributeTypeId);
	
	public List<LuTypeInfo> searchForLuTypesByDescription(String descriptionSearchString);
}
