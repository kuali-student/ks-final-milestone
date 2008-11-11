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

public interface LuRpcServiceAsync {
//	public void createClu(String luTypeKey, CluCreateInfo cluCreateInfo,
//			AsyncCallback<String> callback, AsyncCallback<interface> callback) throws SerializableException;
//
//	public void createLuAttributeTypeInfo(
//			LuAttributeTypeInfo luAttributeTypeInfo,
//			AsyncCallback<String> callback, AsyncCallback<void> callback);
//
//	public void createLui(String cluId, String atpId,
//			LuiCreateInfo luiCreateInfo, AsyncCallback<String> callback, AsyncCallback<void> callback)
//		 throws SerializableException;
//
//	public void createLuTypeInfo(LuTypeInfo luTypeInfo,
//			AsyncCallback<String> callback, AsyncCallback<void> callback);
//
//	public void deleteClu(String cluId, AsyncCallback<Status> callback, AsyncCallback<void> callback)
//		 throws SerializableException;
//
//	public void deleteLui(String luiId, AsyncCallback<Status> callback, AsyncCallback<void> callback)
//		 throws SerializableException;
//
//	public void removeLuAttributeTypeInfo(String luAttributeTypeInfoId,
//			AsyncCallback<Status> callback, AsyncCallback<void> callback);
//
//	public void removeLuTypeInfo(String luTypeInfoId,
//			AsyncCallback<Status> callback, AsyncCallback<void> callback);
//
//	public void fetchClu(String cluId, AsyncCallback<CluInfo> callback, AsyncCallback<void> callback)
//		 throws SerializableException;
//
//	public void fetchLuAttributeType(String luAttributeTypeId,
//			AsyncCallback<LuAttributeTypeInfo> callback, AsyncCallback<void> callback);
//
//	public void fetchLui(String luiId, AsyncCallback<LuiInfo> callback, AsyncCallback<void> callback)
//		 throws SerializableException;
//
//	public void fetchLuType(String luTypeKey, AsyncCallback<LuTypeInfo> callback, AsyncCallback<void> callback)
//		 throws SerializableException;
//
//	public void findCluIdsForLuType(String luTypeKey,
//			AsyncCallback<List<String>> callback, AsyncCallback<void> callback) throws SerializableException;
//
//	public void findLuAttributeTypes(
//			AsyncCallback<List<LuAttributeTypeInfo>> callback, AsyncCallback<void> callback);
//
//	public void findLuAttributeTypesForLuTypeId(String luTypeId,
//			AsyncCallback<List<LuAttributeTypeInfo>> callback, AsyncCallback<void> callback);
//
//	public void findLuiIdsForClu(String cluId, String atpId,
//			AsyncCallback<List<String>> callback, AsyncCallback<void> callback) throws SerializableException;
//
//	public void findLuTypes(AsyncCallback<List<LuTypeInfo>> callback, AsyncCallback<void> callback)
//		 throws SerializableException;
//
//	public void updateLuAttributeTypeInfo(
//			LuAttributeTypeInfo luAttributeTypeInfo,
//			AsyncCallback<String> callback, AsyncCallback<void> callback);
//
//	public void updateClu(String cluId, CluUpdateInfo cluUpdateInfo,
//			AsyncCallback<Status> callback, AsyncCallback<void> callback) throws SerializableException;
//
//	public void updateLui(String luiId, LuiUpdateInfo cluUpdateInfo,
//			AsyncCallback<Status> callback, AsyncCallback<void> callback) throws SerializableException;
//
//	public void updateLuTypeInfo(LuTypeInfo luTypeInfo,
//			AsyncCallback<String> callback, AsyncCallback<void> callback);
	
	public void createAndRouteClu(String userId, String luTypeKey, CluCreateInfo cluCreateInfo,String createComment, AsyncCallback<String> callback);
	public void approveClu(String userId, String cluDocId, String cluId, String comment, AsyncCallback<Status> callback);
	public void disapproveClu(String userId, String cluDocId, String cluId, String comment, AsyncCallback<Status> callback);
	
	/**
	 * Retrieves the list of LU types
	 * 
	 * @return list of LU types
	 * @throws OperationFailedException
	 */

	public void findLuTypes(AsyncCallback<List<LuTypeInfo>> callback) throws SerializableException;

	/**
	 * Retrieves the list of LU to LU relation types
	 * 
	 * @return list of LU to LU relation types
	 * @throws OperationFailedException
	 */

	public void findLuRelationTypes(AsyncCallback<List<String>> callback) throws SerializableException;

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

	public void fetchLuType(String luTypeKey, AsyncCallback<LuTypeInfo> callback)
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

	public void findAllowedLuLuRelationTypesForLuType(

	String luTypeKey, String relatedLuTypeKey, AsyncCallback<List<String>> callback) throws SerializableException;

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

	public void fetchClu(String cluId, AsyncCallback<CluInfo> callback) throws SerializableException;

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

	public void fetchLui(String luiId, AsyncCallback<LuiInfo> callback) throws SerializableException;

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

	public void fetchLuiDisplay(String luiId, AsyncCallback<LuiDisplay> callback)
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

	public void findClusForLuType(String luTypeKey, AsyncCallback<List<CluDisplay>> callback)
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

	public void findCluIdsForLuType(String luTypeKey, AsyncCallback<List<String>> callback)
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

	public void findLuisForClu(String cluId, String atpId, AsyncCallback<List<LuiDisplay>> callback)
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

	public void findLuiIdsForClu(String cluId, String atpId, AsyncCallback<List<String>> callback)
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

	public void findAllowedLuRelationTypesForClu(

	String cluId, String relatedCluId, AsyncCallback<List<String>> callback) throws SerializableException;

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

	public void findAllowedLuRelationTypesForLui(

	String luiId, String relatedLuiId, AsyncCallback<List<String>> callback) throws SerializableException;

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

	public void findClusByRelation(String cluId,
			String luRelationTypeId, AsyncCallback<List<CluDisplay>> callback) throws SerializableException;

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

	public void findCluIdsByRelation(String cluId,
			String luRelationTypeId, AsyncCallback<List<String>> callback) throws SerializableException;

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

	public void findLuisByRelation(String luiId,
			String luRelationTypeId, AsyncCallback<List<LuiDisplay>> callback) throws SerializableException;

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

	public void findLuiIdsByRelation(String luiId,
			String luRelationTypeId, AsyncCallback<List<String>> callback) throws SerializableException;

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

	public void findRelatedClusForCluId(String cluId,
			String luRelationTypeId, AsyncCallback<List<CluDisplay>> callback) throws SerializableException;

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

	public void findRelatedCluIdsForCluId(String cluId,
			String luRelationTypeId, AsyncCallback<List<String>> callback) throws SerializableException;

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

	public void findRelatedLuisForLuiId(String luiId,
			String luRelationTypeId, AsyncCallback<List<CluDisplay>> callback) throws SerializableException;

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

	public void findRelatedLuiIdsForLuiId(String luiId,
			String luRelationTypeId, AsyncCallback<List<String>> callback) throws SerializableException;

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

	public void fetchCluRelation(String cluId, String relatedCluId,
			String luRelationTypeId, AsyncCallback<CluRelationInfo> callback) throws SerializableException;

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

	public void fetchLuiRelation(String luiId, String relatedLuiId,
			String luRelationTypeId, AsyncCallback<LuiRelationInfo> callback) throws SerializableException;

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

	public void findCluRelations(String cluId,
			String luRelationType, AsyncCallback<List<CluRelationDisplay>> callback) throws SerializableException;

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

	public void findLuiRelations(String luiId,
			String luRelationTypeId, AsyncCallback<List<LuiRelationDisplay>> callback) throws SerializableException;

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

	public void findPrerequisitesForDisplay(String cluId, AsyncCallback<String> callback)
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

	public void findSimplePrerequisites(String cluId, AsyncCallback<List<CluDisplay>> callback)
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

	public void findCorequisitesForDisplay(String cluId, AsyncCallback<String> callback)
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

	public void findSimpleCorequisites(String cluId, AsyncCallback<List<CluDisplay>> callback)
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

	public void findAntirequisitesForDisplay(String cluId, AsyncCallback<String> callback)
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

	public void findSimpleAntirequisites(String cluId, AsyncCallback<List<CluDisplay>> callback)
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

	public void findEquivalenciesForDisplay(String cluId, AsyncCallback<String> callback)
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

	public void findSimpleEquivalencies(String cluId, AsyncCallback<List<CluDisplay>> callback)
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

	public void fetchCluSetInfo(String cluSetId, AsyncCallback<CluSetInfo> callback)
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

	public void findClusFromCluSet(String cluSetId, AsyncCallback<List<CluDisplay>> callback)
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

	public void findCluIdsFromCluSet(String cluSetId, AsyncCallback<List<String>> callback)
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

	public void findCluSetIdsFromCluSet(String cluSetId, AsyncCallback<List<String>> callback)
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

	public void findEnumeratedClusInCluSet(

	String cluSetId, AsyncCallback<List<CluDisplay>> callback) throws SerializableException;

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

	public void findEnumeratedCluIdsInCluSet(

	String cluSetId, AsyncCallback<List<String>> callback) throws SerializableException;

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

	public void isCluInCluSet(String cluId, String cluSetId, AsyncCallback<Boolean> callback)
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

	public void searchForClus(CluCriteria cluCriteria, AsyncCallback<List<CluDisplay>> callback)
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

	public void searchForLuis(LuiCriteria luiCriteria, AsyncCallback<List<LuiDisplay>> callback)
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

	public void searchForCluIds(CluCriteria cluCriteria, AsyncCallback<List<String>> callback)
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

	public void searchForLuiIds(LuiCriteria luiCriteria, AsyncCallback<List<String>> callback)
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

	public void searchForCluRelations(

	CluRelationCriteria cluRelationCriteria, AsyncCallback<List<CluRelationDisplay>> callback) throws SerializableException;

	/**
	 * Retrieves Lui to Lui relations by criteria
	 * 
	 * @param luiRelationCriteria
	 * @return list of LUI to LUI relations that match the supplied criteria
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */

	public void searchForLuiRelations(

	LuiRelationCriteria luiRelationCriteria, AsyncCallback<List<LuiRelationDisplay>> callback) throws SerializableException;

	/* Maintenance */
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

	public void createClu(String luTypeKey, CluCreateInfo cluCreateInfo, AsyncCallback<String> callback)
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

	public void createLui(String cluId, String atpId,
			LuiCreateInfo luiCreateInfo, AsyncCallback<String> callback) throws SerializableException;

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

	public void updateClu(String cluId, CluUpdateInfo cluUpdateInfo, AsyncCallback<Status> callback)
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

	public void updateLui(String luiId, LuiUpdateInfo cluUpdateInfo, AsyncCallback<Status> callback)
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

	public void deleteClu(String cluId, AsyncCallback<Status> callback) throws SerializableException;

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

	public void deleteLui(String luiId, AsyncCallback<Status> callback) throws SerializableException;

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

	public void assignCluRelation(String cluId, String relatedCluId,
			String luRelationType, CluRelationAssignInfo cluRelationAssignInfo, AsyncCallback<Status> callback)
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

	public void assignLuiRelation(String luiId, String relatedLuiId,
			String luRelationTypeId, LuiRelationAssignInfo luiRelationAssignInfo, AsyncCallback<Status> callback)
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

	public void updateCluRelation(String cluId, String relatedCluId,
			String luRelationTypeId, CluRelationUpdateInfo cluRelationUpdateInfo, AsyncCallback<Status> callback)
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

	public void updateLuiRelation(String luiId, String relatedLuiId,
			String luRelationType, LuiRelationUpdateInfo luiRelationUpdateInfo, AsyncCallback<Status> callback)
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

	public void deleteCluRelation(String cluId, String relatedCluId,
			String luRelationTypeId, AsyncCallback<Status> callback) throws SerializableException;

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

	public void deleteLuiRelation(String luiId, String relatedLuiId,
			String luRelationTypeId, AsyncCallback<Status> callback) throws SerializableException;

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

	public void addSimplePrerequisite(String cluId, String prereqCluId, AsyncCallback<Status> callback)
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

	public void removeSimplePrerequisite(String cluId, String prereqCluId, AsyncCallback<Status> callback)
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

	public void addSimpleCorequisite(String cluId, String coreqCluId, AsyncCallback<Status> callback)
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

	public void removeSimpleCorequisite(String cluId, String coreqCluId, AsyncCallback<Status> callback)
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

	public void addSimpleAntirequisite(String cluId, String antireqCluId, AsyncCallback<Status> callback)
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

	public void removeSimpleAntirequisite(String cluId, String antireqCluId, AsyncCallback<Status> callback)
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

	public void addSimpleEquivalency(String cluId, String equivalentCluId, AsyncCallback<Status> callback)
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

	public void removeSimpleEquivalency(String cluId, String equivalentCluId, AsyncCallback<Status> callback)
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

	public void createEnumeratedCluSet(String cluSetName,
			CluSetCreateInfo cluSetCreateInfo, AsyncCallback<String> callback) throws SerializableException;

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

	public void createDynamicCluSet(String cluSetName,
			CluSetCreateInfo cluSetCreateInfo, CluCriteria cluCriteria, AsyncCallback<String> callback)
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

	public void updateCluSet(String cluSetId,
			CluSetUpdateInfo cluSetUpdateInfo, AsyncCallback<Status> callback) throws SerializableException;

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

	public void deleteCluSet(String cluSetId, AsyncCallback<Status> callback) throws SerializableException;

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

	public void addCluSetToCluSet(String cluSetId, String addedCluSetId, AsyncCallback<Status> callback)
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

	public void removeCluSetFromCluSet(String cluSetId, String removedCluSetId, AsyncCallback<Status> callback)
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

	public void addCluToCluSet(String cluId, String cluSetId, AsyncCallback<Status> callback)
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

	public void removeCluFromCluSet(String cluId, String cluSetId, AsyncCallback<Status> callback)
			throws SerializableException;

	public void createLuTypeInfo(LuTypeInfo luTypeInfo, AsyncCallback<String> callback);

	public void updateLuTypeInfo(LuTypeInfo luTypeInfo, AsyncCallback<String> callback);

	public void removeLuTypeInfo(String luTypeInfoId, AsyncCallback<Status> callback);

	public void createLuAttributeTypeInfo(

	LuAttributeTypeInfo luAttributeTypeInfo, AsyncCallback<String> callback);

	public void updateLuAttributeTypeInfo(
			LuAttributeTypeInfo luAttributeTypeInfo, AsyncCallback<String> callback);

	public void removeLuAttributeTypeInfo(

	String luAttributeTypeInfoId, AsyncCallback<Status> callback);

	public void findLuAttributeTypes(AsyncCallback<List<LuAttributeTypeInfo>> callback);

	public void findLuAttributeTypesForLuTypeId(

	String luTypeId, AsyncCallback<List<LuAttributeTypeInfo>> callback);

	public void fetchLuAttributeType(

	String luAttributeTypeId, AsyncCallback<LuAttributeTypeInfo> callback);
	
	public void searchForLuTypesByDescription(String descriptionSearchString, AsyncCallback<List<LuTypeInfo>> callback);
}
