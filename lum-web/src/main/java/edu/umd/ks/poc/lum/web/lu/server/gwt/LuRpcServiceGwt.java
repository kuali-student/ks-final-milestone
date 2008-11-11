package edu.umd.ks.poc.lum.web.lu.server.gwt;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.gwt.user.client.rpc.SerializableException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

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
import edu.umd.ks.poc.lum.web.lu.client.service.LuRpcService;
import edu.umd.ks.poc.lum.web.spring.LumServicesContext;

public class LuRpcServiceGwt extends RemoteServiceServlet implements LuRpcService{

	private static final long serialVersionUID = 1L;

	LuRpcService serviceImpl;

	/**
	 * @return the serviceImpl
	 */
	public LuRpcService getServiceImpl() {
		return serviceImpl;
	}

	/**
	 * @param serviceImpl the serviceImpl to set
	 */
	public void setServiceImpl(LuRpcService serviceImpl) {
		this.serviceImpl = serviceImpl;
	}

	/**
	 * @param cluSetId
	 * @param addedCluSetId
	 * @return
	 * @throws DoesNotExistException
	 * @throws CircularReferenceException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#addCluSetToCluSet(java.lang.String, java.lang.String)
	 */
	public Status addCluSetToCluSet(String cluSetId, String addedCluSetId)
		 throws SerializableException {
		return serviceImpl.addCluSetToCluSet(cluSetId, addedCluSetId);
	}

	/**
	 * @param cluId
	 * @param cluSetId
	 * @return
	 * @throws DoesNotExistException
	 * @throws CircularReferenceException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws UnsupportedActionException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#addCluToCluSet(java.lang.String, java.lang.String)
	 */
	public Status addCluToCluSet(String cluId, String cluSetId)
		 throws SerializableException {
		return serviceImpl.addCluToCluSet(cluId, cluSetId);
	}

	/**
	 * @param cluId
	 * @param antireqCluId
	 * @return
	 * @throws DoesNotExistException
	 * @throws AlreadyExistsException
	 * @throws CircularReferenceException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#addSimpleAntirequisite(java.lang.String, java.lang.String)
	 */
	public Status addSimpleAntirequisite(String cluId, String antireqCluId)
		 throws SerializableException {
		return serviceImpl.addSimpleAntirequisite(cluId, antireqCluId);
	}

	/**
	 * @param cluId
	 * @param coreqCluId
	 * @return
	 * @throws DoesNotExistException
	 * @throws AlreadyExistsException
	 * @throws CircularReferenceException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#addSimpleCorequisite(java.lang.String, java.lang.String)
	 */
	public Status addSimpleCorequisite(String cluId, String coreqCluId)
		 throws SerializableException {
		return serviceImpl.addSimpleCorequisite(cluId, coreqCluId);
	}

	/**
	 * @param cluId
	 * @param equivalentCluId
	 * @return
	 * @throws DoesNotExistException
	 * @throws AlreadyExistsException
	 * @throws CircularReferenceException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#addSimpleEquivalency(java.lang.String, java.lang.String)
	 */
	public Status addSimpleEquivalency(String cluId, String equivalentCluId)
		 throws SerializableException {
		return serviceImpl.addSimpleEquivalency(cluId, equivalentCluId);
	}

	/**
	 * @param cluId
	 * @param prereqCluId
	 * @return
	 * @throws DoesNotExistException
	 * @throws AlreadyExistsException
	 * @throws CircularReferenceException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#addSimplePrerequisite(java.lang.String, java.lang.String)
	 */
	public Status addSimplePrerequisite(String cluId, String prereqCluId)
		 throws SerializableException {
		return serviceImpl.addSimplePrerequisite(cluId, prereqCluId);
	}

	/**
	 * @param cluId
	 * @param relatedCluId
	 * @param luRelationType
	 * @param cluRelationAssignInfo
	 * @return
	 * @throws AlreadyExistsException
	 * @throws CircularReferenceException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#assignCluRelation(java.lang.String, java.lang.String, java.lang.String, edu.umd.ks.poc.lum.lu.dto.CluRelationAssignInfo)
	 */
	public Status assignCluRelation(String cluId, String relatedCluId,
			String luRelationType, CluRelationAssignInfo cluRelationAssignInfo)
		 throws SerializableException {
		return serviceImpl.assignCluRelation(cluId, relatedCluId,
				luRelationType, cluRelationAssignInfo);
	}

	/**
	 * @param luiId
	 * @param relatedLuiId
	 * @param luRelationTypeId
	 * @param luiRelationAssignInfo
	 * @return
	 * @throws AlreadyExistsException
	 * @throws CircularReferenceException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#assignLuiRelation(java.lang.String, java.lang.String, java.lang.String, edu.umd.ks.poc.lum.lu.dto.LuiRelationAssignInfo)
	 */
	public Status assignLuiRelation(String luiId, String relatedLuiId,
			String luRelationTypeId, LuiRelationAssignInfo luiRelationAssignInfo)
		 throws SerializableException {
		return serviceImpl.assignLuiRelation(luiId, relatedLuiId,
				luRelationTypeId, luiRelationAssignInfo);
	}

	/**
	 * @param luTypeKey
	 * @param cluCreateInfo
	 * @return
	 * @throws AlreadyExistsException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#createClu(java.lang.String, edu.umd.ks.poc.lum.lu.dto.CluCreateInfo)
	 */
	public String createClu(String luTypeKey, CluCreateInfo cluCreateInfo)
		 throws SerializableException {
		return serviceImpl.createClu(luTypeKey, cluCreateInfo);
	}

	/**
	 * @param cluSetName
	 * @param cluSetCreateInfo
	 * @param cluCriteria
	 * @return
	 * @throws AlreadyExistsException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#createDynamicCluSet(java.lang.String, edu.umd.ks.poc.lum.lu.dto.CluSetCreateInfo, edu.umd.ks.poc.lum.lu.dto.CluCriteria)
	 */
	public String createDynamicCluSet(String cluSetName,
			CluSetCreateInfo cluSetCreateInfo, CluCriteria cluCriteria)
		 throws SerializableException {
		return serviceImpl.createDynamicCluSet(cluSetName, cluSetCreateInfo,
				cluCriteria);
	}

	/**
	 * @param cluSetName
	 * @param cluSetCreateInfo
	 * @return
	 * @throws AlreadyExistsException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#createEnumeratedCluSet(java.lang.String, edu.umd.ks.poc.lum.lu.dto.CluSetCreateInfo)
	 */
	public String createEnumeratedCluSet(String cluSetName,
			CluSetCreateInfo cluSetCreateInfo) throws SerializableException {
		return serviceImpl.createEnumeratedCluSet(cluSetName, cluSetCreateInfo);
	}

	/**
	 * @param luAttributeTypeInfo
	 * @return
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#createLuAttributeTypeInfo(edu.umd.ks.poc.lum.lu.dto.LuAttributeTypeInfo)
	 */
	public String createLuAttributeTypeInfo(
			LuAttributeTypeInfo luAttributeTypeInfo) {
		return serviceImpl.createLuAttributeTypeInfo(luAttributeTypeInfo);
	}

	/**
	 * @param cluId
	 * @param atpId
	 * @param luiCreateInfo
	 * @return
	 * @throws AlreadyExistsException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#createLui(java.lang.String, java.lang.String, edu.umd.ks.poc.lum.lu.dto.LuiCreateInfo)
	 */
	public String createLui(String cluId, String atpId,
			LuiCreateInfo luiCreateInfo) throws SerializableException {
		return serviceImpl.createLui(cluId, atpId, luiCreateInfo);
	}

	/**
	 * @param luTypeInfo
	 * @return
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#createLuTypeInfo(edu.umd.ks.poc.lum.lu.dto.LuTypeInfo)
	 */
	public String createLuTypeInfo(LuTypeInfo luTypeInfo) {
		return serviceImpl.createLuTypeInfo(luTypeInfo);
	}

	/**
	 * @param cluId
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws DependentObjectsExistException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#deleteClu(java.lang.String)
	 */
	public Status deleteClu(String cluId) throws SerializableException {
		return serviceImpl.deleteClu(cluId);
	}

	/**
	 * @param cluId
	 * @param relatedCluId
	 * @param luRelationTypeId
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#deleteCluRelation(java.lang.String, java.lang.String, java.lang.String)
	 */
	public Status deleteCluRelation(String cluId, String relatedCluId,
			String luRelationTypeId) throws SerializableException {
		return serviceImpl.deleteCluRelation(cluId, relatedCluId,
				luRelationTypeId);
	}

	/**
	 * @param cluSetId
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#deleteCluSet(java.lang.String)
	 */
	public Status deleteCluSet(String cluSetId) throws SerializableException {
		return serviceImpl.deleteCluSet(cluSetId);
	}

	/**
	 * @param luiId
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws DependentObjectsExistException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#deleteLui(java.lang.String)
	 */
	public Status deleteLui(String luiId) throws SerializableException {
		return serviceImpl.deleteLui(luiId);
	}

	/**
	 * @param luiId
	 * @param relatedLuiId
	 * @param luRelationTypeId
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#deleteLuiRelation(java.lang.String, java.lang.String, java.lang.String)
	 */
	public Status deleteLuiRelation(String luiId, String relatedLuiId,
			String luRelationTypeId) throws SerializableException {
		return serviceImpl.deleteLuiRelation(luiId, relatedLuiId,
				luRelationTypeId);
	}

	/**
	 * @param cluId
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#fetchClu(java.lang.String)
	 */
	public CluInfo fetchClu(String cluId) throws SerializableException {
		return serviceImpl.fetchClu(cluId);
	}

	/**
	 * @param cluId
	 * @param relatedCluId
	 * @param luRelationTypeId
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#fetchCluRelation(java.lang.String, java.lang.String, java.lang.String)
	 */
	public CluRelationInfo fetchCluRelation(String cluId, String relatedCluId,
			String luRelationTypeId) throws SerializableException {
		return serviceImpl.fetchCluRelation(cluId, relatedCluId,
				luRelationTypeId);
	}

	/**
	 * @param cluSetId
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#fetchCluSetInfo(java.lang.String)
	 */
	public CluSetInfo fetchCluSetInfo(String cluSetId)
		 throws SerializableException {
		return serviceImpl.fetchCluSetInfo(cluSetId);
	}

	/**
	 * @param luAttributeTypeId
	 * @return
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#fetchLuAttributeType(java.lang.String)
	 */
	public LuAttributeTypeInfo fetchLuAttributeType(String luAttributeTypeId) {
		return serviceImpl.fetchLuAttributeType(luAttributeTypeId);
	}

	/**
	 * @param luiId
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#fetchLui(java.lang.String)
	 */
	public LuiInfo fetchLui(String luiId) throws SerializableException {
		return serviceImpl.fetchLui(luiId);
	}

	/**
	 * @param luiId
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#fetchLuiDisplay(java.lang.String)
	 */
	public LuiDisplay fetchLuiDisplay(String luiId)
		 throws SerializableException {
		return serviceImpl.fetchLuiDisplay(luiId);
	}

	/**
	 * @param luiId
	 * @param relatedLuiId
	 * @param luRelationTypeId
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#fetchLuiRelation(java.lang.String, java.lang.String, java.lang.String)
	 */
	public LuiRelationInfo fetchLuiRelation(String luiId, String relatedLuiId,
			String luRelationTypeId) throws SerializableException {
		return serviceImpl.fetchLuiRelation(luiId, relatedLuiId,
				luRelationTypeId);
	}

	/**
	 * @param luTypeKey
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#fetchLuType(java.lang.String)
	 */
	public LuTypeInfo fetchLuType(String luTypeKey)
		 throws SerializableException {
		return serviceImpl.fetchLuType(luTypeKey);
	}

	/**
	 * @param luTypeKey
	 * @param relatedLuTypeKey
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#findAllowedLuLuRelationTypesForLuType(java.lang.String, java.lang.String)
	 */
	public List<String> findAllowedLuLuRelationTypesForLuType(String luTypeKey,
			String relatedLuTypeKey) throws SerializableException {
		return serviceImpl.findAllowedLuLuRelationTypesForLuType(luTypeKey,
				relatedLuTypeKey);
	}

	/**
	 * @param cluId
	 * @param relatedCluId
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#findAllowedLuRelationTypesForClu(java.lang.String, java.lang.String)
	 */
	public List<String> findAllowedLuRelationTypesForClu(String cluId,
			String relatedCluId) throws SerializableException {
		return serviceImpl
				.findAllowedLuRelationTypesForClu(cluId, relatedCluId);
	}

	/**
	 * @param luiId
	 * @param relatedLuiId
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#findAllowedLuRelationTypesForLui(java.lang.String, java.lang.String)
	 */
	public List<String> findAllowedLuRelationTypesForLui(String luiId,
			String relatedLuiId) throws SerializableException {
		return serviceImpl
				.findAllowedLuRelationTypesForLui(luiId, relatedLuiId);
	}

	/**
	 * @param cluId
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#findAntirequisitesForDisplay(java.lang.String)
	 */
	public String findAntirequisitesForDisplay(String cluId)
		 throws SerializableException {
		return serviceImpl.findAntirequisitesForDisplay(cluId);
	}

	/**
	 * @param cluId
	 * @param luRelationTypeId
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#findCluIdsByRelation(java.lang.String, java.lang.String)
	 */
	public List<String> findCluIdsByRelation(String cluId,
			String luRelationTypeId) throws SerializableException {
		return serviceImpl.findCluIdsByRelation(cluId, luRelationTypeId);
	}

	/**
	 * @param luTypeKey
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#findCluIdsForLuType(java.lang.String)
	 */
	public List<String> findCluIdsForLuType(String luTypeKey)
		 throws SerializableException {
		return serviceImpl.findCluIdsForLuType(luTypeKey);
	}

	/**
	 * @param cluSetId
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#findCluIdsFromCluSet(java.lang.String)
	 */
	public List<String> findCluIdsFromCluSet(String cluSetId)
		 throws SerializableException {
		return serviceImpl.findCluIdsFromCluSet(cluSetId);
	}

	/**
	 * @param cluId
	 * @param luRelationType
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#findCluRelations(java.lang.String, java.lang.String)
	 */
	public List<CluRelationDisplay> findCluRelations(String cluId,
			String luRelationType) throws SerializableException {
		return serviceImpl.findCluRelations(cluId, luRelationType);
	}

	/**
	 * @param cluId
	 * @param luRelationTypeId
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#findClusByRelation(java.lang.String, java.lang.String)
	 */
	public List<CluDisplay> findClusByRelation(String cluId,
			String luRelationTypeId) throws SerializableException {
		return serviceImpl.findClusByRelation(cluId, luRelationTypeId);
	}

	/**
	 * @param cluSetId
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#findCluSetIdsFromCluSet(java.lang.String)
	 */
	public List<String> findCluSetIdsFromCluSet(String cluSetId)
		 throws SerializableException {
		return serviceImpl.findCluSetIdsFromCluSet(cluSetId);
	}

	/**
	 * @param luTypeKey
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#findClusForLuType(java.lang.String)
	 */
	public List<CluDisplay> findClusForLuType(String luTypeKey)
		 throws SerializableException {
		return serviceImpl.findClusForLuType(luTypeKey);
	}

	/**
	 * @param cluSetId
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#findClusFromCluSet(java.lang.String)
	 */
	public List<CluDisplay> findClusFromCluSet(String cluSetId)
		 throws SerializableException {
		return serviceImpl.findClusFromCluSet(cluSetId);
	}

	/**
	 * @param cluId
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#findCorequisitesForDisplay(java.lang.String)
	 */
	public String findCorequisitesForDisplay(String cluId)
		 throws SerializableException {
		return serviceImpl.findCorequisitesForDisplay(cluId);
	}

	/**
	 * @param cluSetId
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#findEnumeratedCluIdsInCluSet(java.lang.String)
	 */
	public List<String> findEnumeratedCluIdsInCluSet(String cluSetId)
		 throws SerializableException {
		return serviceImpl.findEnumeratedCluIdsInCluSet(cluSetId);
	}

	/**
	 * @param cluSetId
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#findEnumeratedClusInCluSet(java.lang.String)
	 */
	public List<CluDisplay> findEnumeratedClusInCluSet(String cluSetId)
		 throws SerializableException {
		return serviceImpl.findEnumeratedClusInCluSet(cluSetId);
	}

	/**
	 * @param cluId
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#findEquivalenciesForDisplay(java.lang.String)
	 */
	public String findEquivalenciesForDisplay(String cluId)
		 throws SerializableException {
		return serviceImpl.findEquivalenciesForDisplay(cluId);
	}

	/**
	 * @return
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#findLuAttributeTypes()
	 */
	public List<LuAttributeTypeInfo> findLuAttributeTypes() {
		return serviceImpl.findLuAttributeTypes();
	}

	/**
	 * @param luTypeId
	 * @return
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#findLuAttributeTypesForLuTypeId(java.lang.String)
	 */
	public List<LuAttributeTypeInfo> findLuAttributeTypesForLuTypeId(
			String luTypeId) {
		return serviceImpl.findLuAttributeTypesForLuTypeId(luTypeId);
	}

	/**
	 * @param luiId
	 * @param luRelationTypeId
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#findLuiIdsByRelation(java.lang.String, java.lang.String)
	 */
	public List<String> findLuiIdsByRelation(String luiId,
			String luRelationTypeId) throws SerializableException {
		return serviceImpl.findLuiIdsByRelation(luiId, luRelationTypeId);
	}

	/**
	 * @param cluId
	 * @param atpId
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#findLuiIdsForClu(java.lang.String, java.lang.String)
	 */
	public List<String> findLuiIdsForClu(String cluId, String atpId)
		 throws SerializableException {
		return serviceImpl.findLuiIdsForClu(cluId, atpId);
	}

	/**
	 * @param luiId
	 * @param luRelationTypeId
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#findLuiRelations(java.lang.String, java.lang.String)
	 */
	public List<LuiRelationDisplay> findLuiRelations(String luiId,
			String luRelationTypeId) throws SerializableException {
		return serviceImpl.findLuiRelations(luiId, luRelationTypeId);
	}

	/**
	 * @param luiId
	 * @param luRelationTypeId
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#findLuisByRelation(java.lang.String, java.lang.String)
	 */
	public List<LuiDisplay> findLuisByRelation(String luiId,
			String luRelationTypeId) throws SerializableException {
		return serviceImpl.findLuisByRelation(luiId, luRelationTypeId);
	}

	/**
	 * @param cluId
	 * @param atpId
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#findLuisForClu(java.lang.String, java.lang.String)
	 */
	public List<LuiDisplay> findLuisForClu(String cluId, String atpId)
		 throws SerializableException {
		return serviceImpl.findLuisForClu(cluId, atpId);
	}

	/**
	 * @return
	 * @throws OperationFailedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#findLuRelationTypes()
	 */
	public List<String> findLuRelationTypes() throws SerializableException {
		return serviceImpl.findLuRelationTypes();
	}

	/**
	 * @return
	 * @throws OperationFailedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#findLuTypes()
	 */
	public List<LuTypeInfo> findLuTypes() throws SerializableException {
		return serviceImpl.findLuTypes();
	}

	/**
	 * @param cluId
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#findPrerequisitesForDisplay(java.lang.String)
	 */
	public String findPrerequisitesForDisplay(String cluId)
		 throws SerializableException {
		return serviceImpl.findPrerequisitesForDisplay(cluId);
	}

	/**
	 * @param cluId
	 * @param luRelationTypeId
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#findRelatedCluIdsForCluId(java.lang.String, java.lang.String)
	 */
	public List<String> findRelatedCluIdsForCluId(String cluId,
			String luRelationTypeId) throws SerializableException {
		return serviceImpl.findRelatedCluIdsForCluId(cluId, luRelationTypeId);
	}

	/**
	 * @param cluId
	 * @param luRelationTypeId
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#findRelatedClusForCluId(java.lang.String, java.lang.String)
	 */
	public List<CluDisplay> findRelatedClusForCluId(String cluId,
			String luRelationTypeId) throws SerializableException {
		return serviceImpl.findRelatedClusForCluId(cluId, luRelationTypeId);
	}

	/**
	 * @param luiId
	 * @param luRelationTypeId
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#findRelatedLuiIdsForLuiId(java.lang.String, java.lang.String)
	 */
	public List<String> findRelatedLuiIdsForLuiId(String luiId,
			String luRelationTypeId) throws SerializableException {
		return serviceImpl.findRelatedLuiIdsForLuiId(luiId, luRelationTypeId);
	}

	/**
	 * @param luiId
	 * @param luRelationTypeId
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#findRelatedLuisForLuiId(java.lang.String, java.lang.String)
	 */
	public List<CluDisplay> findRelatedLuisForLuiId(String luiId,
			String luRelationTypeId) throws SerializableException {
		return serviceImpl.findRelatedLuisForLuiId(luiId, luRelationTypeId);
	}

	/**
	 * @param cluId
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#findSimpleAntirequisites(java.lang.String)
	 */
	public List<CluDisplay> findSimpleAntirequisites(String cluId)
		 throws SerializableException {
		return serviceImpl.findSimpleAntirequisites(cluId);
	}

	/**
	 * @param cluId
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#findSimpleCorequisites(java.lang.String)
	 */
	public List<CluDisplay> findSimpleCorequisites(String cluId)
		 throws SerializableException {
		return serviceImpl.findSimpleCorequisites(cluId);
	}

	/**
	 * @param cluId
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#findSimpleEquivalencies(java.lang.String)
	 */
	public List<CluDisplay> findSimpleEquivalencies(String cluId)
		 throws SerializableException {
		return serviceImpl.findSimpleEquivalencies(cluId);
	}

	/**
	 * @param cluId
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#findSimplePrerequisites(java.lang.String)
	 */
	public List<CluDisplay> findSimplePrerequisites(String cluId)
		 throws SerializableException {
		return serviceImpl.findSimplePrerequisites(cluId);
	}

	/**
	 * @param cluId
	 * @param cluSetId
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#isCluInCluSet(java.lang.String, java.lang.String)
	 */
	public boolean isCluInCluSet(String cluId, String cluSetId)
		 throws SerializableException {
		return serviceImpl.isCluInCluSet(cluId, cluSetId);
	}

	/**
	 * @param cluId
	 * @param cluSetId
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws UnsupportedActionException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#removeCluFromCluSet(java.lang.String, java.lang.String)
	 */
	public Status removeCluFromCluSet(String cluId, String cluSetId)
		 throws SerializableException {
		return serviceImpl.removeCluFromCluSet(cluId, cluSetId);
	}

	/**
	 * @param cluSetId
	 * @param removedCluSetId
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#removeCluSetFromCluSet(java.lang.String, java.lang.String)
	 */
	public Status removeCluSetFromCluSet(String cluSetId, String removedCluSetId)
		 throws SerializableException {
		return serviceImpl.removeCluSetFromCluSet(cluSetId, removedCluSetId);
	}

	/**
	 * @param luAttributeTypeInfoId
	 * @return
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#removeLuAttributeTypeInfo(java.lang.String)
	 */
	public Status removeLuAttributeTypeInfo(String luAttributeTypeInfoId) {
		return serviceImpl.removeLuAttributeTypeInfo(luAttributeTypeInfoId);
	}

	/**
	 * @param luTypeInfoId
	 * @return
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#removeLuTypeInfo(java.lang.String)
	 */
	public Status removeLuTypeInfo(String luTypeInfoId) {
		return serviceImpl.removeLuTypeInfo(luTypeInfoId);
	}

	/**
	 * @param cluId
	 * @param antireqCluId
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#removeSimpleAntirequisite(java.lang.String, java.lang.String)
	 */
	public Status removeSimpleAntirequisite(String cluId, String antireqCluId)
		 throws SerializableException {
		return serviceImpl.removeSimpleAntirequisite(cluId, antireqCluId);
	}

	/**
	 * @param cluId
	 * @param coreqCluId
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#removeSimpleCorequisite(java.lang.String, java.lang.String)
	 */
	public Status removeSimpleCorequisite(String cluId, String coreqCluId)
		 throws SerializableException {
		return serviceImpl.removeSimpleCorequisite(cluId, coreqCluId);
	}

	/**
	 * @param cluId
	 * @param equivalentCluId
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#removeSimpleEquivalency(java.lang.String, java.lang.String)
	 */
	public Status removeSimpleEquivalency(String cluId, String equivalentCluId)
		 throws SerializableException {
		return serviceImpl.removeSimpleEquivalency(cluId, equivalentCluId);
	}

	/**
	 * @param cluId
	 * @param prereqCluId
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#removeSimplePrerequisite(java.lang.String, java.lang.String)
	 */
	public Status removeSimplePrerequisite(String cluId, String prereqCluId)
		 throws SerializableException {
		return serviceImpl.removeSimplePrerequisite(cluId, prereqCluId);
	}

	/**
	 * @param cluCriteria
	 * @return
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#searchForCluIds(edu.umd.ks.poc.lum.lu.dto.CluCriteria)
	 */
	public List<String> searchForCluIds(CluCriteria cluCriteria)
		 throws SerializableException {
		return serviceImpl.searchForCluIds(cluCriteria);
	}

	/**
	 * @param cluRelationCriteria
	 * @return
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#searchForCluRelations(edu.umd.ks.poc.lum.lu.dto.CluRelationCriteria)
	 */
	public List<CluRelationDisplay> searchForCluRelations(
			CluRelationCriteria cluRelationCriteria)
		 throws SerializableException {
		return serviceImpl.searchForCluRelations(cluRelationCriteria);
	}

	/**
	 * @param cluCriteria
	 * @return
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#searchForClus(edu.umd.ks.poc.lum.lu.dto.CluCriteria)
	 */
	public List<CluDisplay> searchForClus(CluCriteria cluCriteria)
		 throws SerializableException {
		return serviceImpl.searchForClus(cluCriteria);
	}

	/**
	 * @param luiCriteria
	 * @return
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#searchForLuiIds(edu.umd.ks.poc.lum.lu.dto.LuiCriteria)
	 */
	public List<String> searchForLuiIds(LuiCriteria luiCriteria)
		 throws SerializableException {
		return serviceImpl.searchForLuiIds(luiCriteria);
	}

	/**
	 * @param luiRelationCriteria
	 * @return
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#searchForLuiRelations(edu.umd.ks.poc.lum.lu.dto.LuiRelationCriteria)
	 */
	public List<LuiRelationDisplay> searchForLuiRelations(
			LuiRelationCriteria luiRelationCriteria)
		 throws SerializableException {
		return serviceImpl.searchForLuiRelations(luiRelationCriteria);
	}

	/**
	 * @param luiCriteria
	 * @return
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#searchForLuis(edu.umd.ks.poc.lum.lu.dto.LuiCriteria)
	 */
	public List<LuiDisplay> searchForLuis(LuiCriteria luiCriteria)
		 throws SerializableException {
		return serviceImpl.searchForLuis(luiCriteria);
	}

	/**
	 * @param cluId
	 * @param cluUpdateInfo
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#updateClu(java.lang.String, edu.umd.ks.poc.lum.lu.dto.CluUpdateInfo)
	 */
	public Status updateClu(String cluId, CluUpdateInfo cluUpdateInfo)
		 throws SerializableException {
		return serviceImpl.updateClu(cluId, cluUpdateInfo);
	}

	/**
	 * @param cluId
	 * @param relatedCluId
	 * @param luRelationTypeId
	 * @param cluRelationUpdateInfo
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#updateCluRelation(java.lang.String, java.lang.String, java.lang.String, edu.umd.ks.poc.lum.lu.dto.CluRelationUpdateInfo)
	 */
	public Status updateCluRelation(String cluId, String relatedCluId,
			String luRelationTypeId, CluRelationUpdateInfo cluRelationUpdateInfo)
		 throws SerializableException {
		return serviceImpl.updateCluRelation(cluId, relatedCluId,
				luRelationTypeId, cluRelationUpdateInfo);
	}

	/**
	 * @param cluSetId
	 * @param cluSetUpdateInfo
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#updateCluSet(java.lang.String, edu.umd.ks.poc.lum.lu.dto.CluSetUpdateInfo)
	 */
	public Status updateCluSet(String cluSetId,
			CluSetUpdateInfo cluSetUpdateInfo) throws SerializableException {
		return serviceImpl.updateCluSet(cluSetId, cluSetUpdateInfo);
	}

	/**
	 * @param luAttributeTypeInfo
	 * @return
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#updateLuAttributeTypeInfo(edu.umd.ks.poc.lum.lu.dto.LuAttributeTypeInfo)
	 */
	public String updateLuAttributeTypeInfo(
			LuAttributeTypeInfo luAttributeTypeInfo) {
		return serviceImpl.updateLuAttributeTypeInfo(luAttributeTypeInfo);
	}

	/**
	 * @param luiId
	 * @param cluUpdateInfo
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#updateLui(java.lang.String, edu.umd.ks.poc.lum.lu.dto.LuiUpdateInfo)
	 */
	public Status updateLui(String luiId, LuiUpdateInfo cluUpdateInfo)
		 throws SerializableException {
		return serviceImpl.updateLui(luiId, cluUpdateInfo);
	}

	/**
	 * @param luiId
	 * @param relatedLuiId
	 * @param luRelationType
	 * @param luiRelationUpdateInfo
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#updateLuiRelation(java.lang.String, java.lang.String, java.lang.String, edu.umd.ks.poc.lum.lu.dto.LuiRelationUpdateInfo)
	 */
	public Status updateLuiRelation(String luiId, String relatedLuiId,
			String luRelationType, LuiRelationUpdateInfo luiRelationUpdateInfo)
		 throws SerializableException {
		return serviceImpl.updateLuiRelation(luiId, relatedLuiId,
				luRelationType, luiRelationUpdateInfo);
	}

	/**
	 * @param luTypeInfo
	 * @return
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#updateLuTypeInfo(edu.umd.ks.poc.lum.lu.dto.LuTypeInfo)
	 */
	public String updateLuTypeInfo(LuTypeInfo luTypeInfo) {
		return serviceImpl.updateLuTypeInfo(luTypeInfo);
	}

	@Override
	public List<LuTypeInfo> searchForLuTypesByDescription(
			String descriptionSearchString) {
		return serviceImpl.searchForLuTypesByDescription(descriptionSearchString);
	}

	@Override
	public String createAndRouteClu(String userId, String luTypeKey,
			CluCreateInfo cluCreateInfo, String createComment) {
		return serviceImpl.createAndRouteClu(userId, luTypeKey, cluCreateInfo, createComment);
	}

	@Override
	public Status approveClu(String userId, String cluDocId, String cluId, String comment) {
		return serviceImpl.approveClu(userId, cluDocId, cluId, comment);
	}

	@Override
	public Status disapproveClu(String userId, String cluDocId, String cluId, String comment) {
		return serviceImpl.disapproveClu(userId, cluDocId, cluId, comment);
	}


	
	
}
