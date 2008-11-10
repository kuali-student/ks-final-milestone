package edu.umd.ks.poc.lum.web.lu.server.impl;

import java.io.StringWriter;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.kuali.rice.kew.webservice.DocumentResponse;
import org.kuali.rice.kew.webservice.SimpleDocumentActionsWebService;
import org.kuali.rice.kew.webservice.StandardResponse;
import org.kuali.student.poc.common.ws.exceptions.AlreadyExistsException;
import org.kuali.student.poc.common.ws.exceptions.CircularReferenceException;
import org.kuali.student.poc.common.ws.exceptions.DependentObjectsExistException;
import org.kuali.student.poc.common.ws.exceptions.DoesNotExistException;
import org.kuali.student.poc.common.ws.exceptions.InvalidParameterException;
import org.kuali.student.poc.common.ws.exceptions.MissingParameterException;
import org.kuali.student.poc.common.ws.exceptions.OperationFailedException;
import org.kuali.student.poc.common.ws.exceptions.PermissionDeniedException;
import org.kuali.student.poc.common.ws.exceptions.UnsupportedActionException;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

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
import edu.umd.ks.poc.lum.lu.service.LuService;
import edu.umd.ks.poc.lum.web.core.client.Authorization;
import edu.umd.ks.poc.lum.web.lu.client.service.LuRpcService;

public class LuRpcServiceImpl implements LuRpcService {
	private LuService service = null;
	private SimpleDocumentActionsWebService wfService = null;
	static int createLuTypeInfoCount = 0;

	/**
	 * @return the service
	 */
	public LuService getService() {
		return service;
	}

	/**
	 * @param service
	 *            the service to set
	 */
	public void setService(LuService service) {
		this.service = service;
	}

	/**
	 * @param cluSetId
	 * @param addedCluSetId
	 * @return
	 * @throws SerializableException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#addCluSetToCluSet(java.lang.String,
	 *      java.lang.String)
	 */
	public Status addCluSetToCluSet(String cluSetId, String addedCluSetId)
			throws SerializableException {
		try {
			return service.addCluSetToCluSet(cluSetId, addedCluSetId);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
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
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#addCluToCluSet(java.lang.String,
	 *      java.lang.String)
	 */
	public Status addCluToCluSet(String cluId, String cluSetId)
			throws SerializableException {
		try {
			try {
				return service.addCluToCluSet(cluId, cluSetId);
			} catch (Exception e) {
				throw new SerializableException(e.getMessage());
			}
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
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
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#addSimpleAntirequisite(java.lang.String,
	 *      java.lang.String)
	 */
	public Status addSimpleAntirequisite(String cluId, String antireqCluId)
			throws SerializableException {
		try {
			return service.addSimpleAntirequisite(cluId, antireqCluId);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
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
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#addSimpleCorequisite(java.lang.String,
	 *      java.lang.String)
	 */
	public Status addSimpleCorequisite(String cluId, String coreqCluId)
			throws SerializableException {
		try {
			return service.addSimpleCorequisite(cluId, coreqCluId);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
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
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#addSimpleEquivalency(java.lang.String,
	 *      java.lang.String)
	 */
	public Status addSimpleEquivalency(String cluId, String equivalentCluId)
			throws SerializableException {
		try {
			return service.addSimpleEquivalency(cluId, equivalentCluId);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
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
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#addSimplePrerequisite(java.lang.String,
	 *      java.lang.String)
	 */
	public Status addSimplePrerequisite(String cluId, String prereqCluId)
			throws SerializableException {
		try {
			return service.addSimplePrerequisite(cluId, prereqCluId);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
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
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#assignCluRelation(java.lang.String,
	 *      java.lang.String, java.lang.String,
	 *      edu.umd.ks.poc.lum.lu.dto.CluRelationAssignInfo)
	 */
	public Status assignCluRelation(String cluId, String relatedCluId,
			String luRelationType, CluRelationAssignInfo cluRelationAssignInfo)
			throws SerializableException {
		try {
			return service.assignCluRelation(cluId, relatedCluId,
					luRelationType, cluRelationAssignInfo);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
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
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#assignLuiRelation(java.lang.String,
	 *      java.lang.String, java.lang.String,
	 *      edu.umd.ks.poc.lum.lu.dto.LuiRelationAssignInfo)
	 */
	public Status assignLuiRelation(String luiId, String relatedLuiId,
			String luRelationTypeId, LuiRelationAssignInfo luiRelationAssignInfo)
			throws SerializableException {
		try {
			return service.assignLuiRelation(luiId, relatedLuiId,
					luRelationTypeId, luiRelationAssignInfo);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
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
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#createClu(java.lang.String,
	 *      edu.umd.ks.poc.lum.lu.dto.CluCreateInfo)
	 */
	public String createClu(String luTypeKey, CluCreateInfo cluCreateInfo)
			throws SerializableException {
		try {
			return service.createClu(luTypeKey, cluCreateInfo);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
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
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#createDynamicCluSet(java.lang.String,
	 *      edu.umd.ks.poc.lum.lu.dto.CluSetCreateInfo,
	 *      edu.umd.ks.poc.lum.lu.dto.CluCriteria)
	 */
	public String createDynamicCluSet(String cluSetName,
			CluSetCreateInfo cluSetCreateInfo, CluCriteria cluCriteria)
			throws SerializableException {
		try {
			return service.createDynamicCluSet(cluSetName, cluSetCreateInfo,
					cluCriteria);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
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
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#createEnumeratedCluSet(java.lang.String,
	 *      edu.umd.ks.poc.lum.lu.dto.CluSetCreateInfo)
	 */
	public String createEnumeratedCluSet(String cluSetName,
			CluSetCreateInfo cluSetCreateInfo) throws SerializableException {
		try {
			return service.createEnumeratedCluSet(cluSetName, cluSetCreateInfo);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
	}

	/**
	 * @param luAttributeTypeInfo
	 * @return
	 * @throws SerializableException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#createLuAttributeTypeInfo(edu.umd.ks.poc.lum.lu.dto.LuAttributeTypeInfo)
	 */
	public String createLuAttributeTypeInfo(
			LuAttributeTypeInfo luAttributeTypeInfo) {
		try {
			return service.createLuAttributeTypeInfo(luAttributeTypeInfo);
		} catch (Exception e) {
			return null;
			// throw new SerializableException(e.getMessage());
		}
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
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#createLui(java.lang.String,
	 *      java.lang.String, edu.umd.ks.poc.lum.lu.dto.LuiCreateInfo)
	 */
	public String createLui(String cluId, String atpId,
			LuiCreateInfo luiCreateInfo) throws SerializableException {
		try {
			return service.createLui(cluId, atpId, luiCreateInfo);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
	}

	/**
	 * @param luTypeInfo
	 * @return
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#createLuTypeInfo(edu.umd.ks.poc.lum.lu.dto.LuTypeInfo)
	 */
	public String createLuTypeInfo(LuTypeInfo luTypeInfo) {
		try {
		    ++createLuTypeInfoCount;
			return service.createLuTypeInfo(luTypeInfo);
		} catch (Exception e) {
			return null;
			// throw new SerializableException(e.getMessage());
		}
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
		try {
			return service.deleteClu(cluId);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
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
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#deleteCluRelation(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public Status deleteCluRelation(String cluId, String relatedCluId,
			String luRelationTypeId) throws SerializableException {
		try {
			return service.deleteCluRelation(cluId, relatedCluId,
					luRelationTypeId);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
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
		try {
			return service.deleteCluSet(cluSetId);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
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
		try {
			return service.deleteLui(luiId);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
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
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#deleteLuiRelation(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public Status deleteLuiRelation(String luiId, String relatedLuiId,
			String luRelationTypeId) throws SerializableException {
		try {
			return service.deleteLuiRelation(luiId, relatedLuiId,
					luRelationTypeId);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
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
		try {
			return service.fetchClu(cluId);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
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
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#fetchCluRelation(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public CluRelationInfo fetchCluRelation(String cluId, String relatedCluId,
			String luRelationTypeId) throws SerializableException {
		try {
			return service.fetchCluRelation(cluId, relatedCluId,
					luRelationTypeId);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
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
		try {
			return service.fetchCluSetInfo(cluSetId);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
	}

	/**
	 * @param luAttributeTypeId
	 * @return
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#fetchLuAttributeType(java.lang.String)
	 */
	public LuAttributeTypeInfo fetchLuAttributeType(String luAttributeTypeId) {
		try {
			return service.fetchLuAttributeType(luAttributeTypeId);
		} catch (Exception e) {
			return null;
			// throw new SerializableException(e.getMessage());
		}
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
		try {
			return service.fetchLui(luiId);
		} catch (Exception e) {
			return null;
			// throw new SerializableException(e.getMessage());
		}
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
		try {
			return service.fetchLuiDisplay(luiId);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
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
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#fetchLuiRelation(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public LuiRelationInfo fetchLuiRelation(String luiId, String relatedLuiId,
			String luRelationTypeId) throws SerializableException {
		try {
			return service.fetchLuiRelation(luiId, relatedLuiId,
					luRelationTypeId);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
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
		try {
			return service.fetchLuType(luTypeKey);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
	}

	/**
	 * @param luTypeKey
	 * @param relatedLuTypeKey
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#findAllowedLuLuRelationTypesForLuType(java.lang.String,
	 *      java.lang.String)
	 */
	public List<String> findAllowedLuLuRelationTypesForLuType(String luTypeKey,
			String relatedLuTypeKey) throws SerializableException {
		try {
			return service.findAllowedLuLuRelationTypesForLuType(luTypeKey,
					relatedLuTypeKey);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
	}

	/**
	 * @param cluId
	 * @param relatedCluId
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#findAllowedLuRelationTypesForClu(java.lang.String,
	 *      java.lang.String)
	 */
	public List<String> findAllowedLuRelationTypesForClu(String cluId,
			String relatedCluId) throws SerializableException {
		try {
			return service
					.findAllowedLuRelationTypesForClu(cluId, relatedCluId);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
	}

	/**
	 * @param luiId
	 * @param relatedLuiId
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#findAllowedLuRelationTypesForLui(java.lang.String,
	 *      java.lang.String)
	 */
	public List<String> findAllowedLuRelationTypesForLui(String luiId,
			String relatedLuiId) throws SerializableException {
		try {
			return service
					.findAllowedLuRelationTypesForLui(luiId, relatedLuiId);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
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
		try {
			return service.findAntirequisitesForDisplay(cluId);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
	}

	/**
	 * @param cluId
	 * @param luRelationTypeId
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#findCluIdsByRelation(java.lang.String,
	 *      java.lang.String)
	 */
	public List<String> findCluIdsByRelation(String cluId,
			String luRelationTypeId) throws SerializableException {
		try {
			return service.findCluIdsByRelation(cluId, luRelationTypeId);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
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
		try {
			return service.findCluIdsForLuType(luTypeKey);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
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
		try {
			return service.findCluIdsFromCluSet(cluSetId);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
	}

	/**
	 * @param cluId
	 * @param luRelationType
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#findCluRelations(java.lang.String,
	 *      java.lang.String)
	 */
	public List<CluRelationDisplay> findCluRelations(String cluId,
			String luRelationType) throws SerializableException {
		try {
			return service.findCluRelations(cluId, luRelationType);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
	}

	/**
	 * @param cluId
	 * @param luRelationTypeId
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#findClusByRelation(java.lang.String,
	 *      java.lang.String)
	 */
	public List<CluDisplay> findClusByRelation(String cluId,
			String luRelationTypeId) throws SerializableException {
		try {
			return service.findClusByRelation(cluId, luRelationTypeId);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
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
		try {
			return service.findCluSetIdsFromCluSet(cluSetId);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
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
		try {
			return service.findClusForLuType(luTypeKey);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
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
		try {
			return service.findClusFromCluSet(cluSetId);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
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
		try {
			return service.findCorequisitesForDisplay(cluId);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
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
		try {
			return service.findEnumeratedCluIdsInCluSet(cluSetId);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
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
		try {
			return service.findEnumeratedClusInCluSet(cluSetId);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
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
		try {
			return service.findEquivalenciesForDisplay(cluId);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
	}

	/**
	 * @return
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#findLuAttributeTypes()
	 */
	public List<LuAttributeTypeInfo> findLuAttributeTypes() {
		try {
			return service.findLuAttributeTypes();
		} catch (Exception e) {
			return null;
			// throw new SerializableException(e.getMessage());
		}
	}

	/**
	 * @param luTypeId
	 * @return
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#findLuAttributeTypesForLuTypeId(java.lang.String)
	 */
	public List<LuAttributeTypeInfo> findLuAttributeTypesForLuTypeId(
			String luTypeId) {
		try {
			return service.findLuAttributeTypesForLuTypeId(luTypeId);
		} catch (Exception e) {
			return null;
			// throw new SerializableException(e.getMessage());
		}
	}

	/**
	 * @param luiId
	 * @param luRelationTypeId
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#findLuiIdsByRelation(java.lang.String,
	 *      java.lang.String)
	 */
	public List<String> findLuiIdsByRelation(String luiId,
			String luRelationTypeId) throws SerializableException {
		try {
			return service.findLuiIdsByRelation(luiId, luRelationTypeId);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
	}

	/**
	 * @param cluId
	 * @param atpId
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#findLuiIdsForClu(java.lang.String,
	 *      java.lang.String)
	 */
	public List<String> findLuiIdsForClu(String cluId, String atpId)
			throws SerializableException {
		try {
			return service.findLuiIdsForClu(cluId, atpId);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
	}

	/**
	 * @param luiId
	 * @param luRelationTypeId
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#findLuiRelations(java.lang.String,
	 *      java.lang.String)
	 */
	public List<LuiRelationDisplay> findLuiRelations(String luiId,
			String luRelationTypeId) throws SerializableException {
		try {
			return service.findLuiRelations(luiId, luRelationTypeId);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
	}

	/**
	 * @param luiId
	 * @param luRelationTypeId
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#findLuisByRelation(java.lang.String,
	 *      java.lang.String)
	 */
	public List<LuiDisplay> findLuisByRelation(String luiId,
			String luRelationTypeId) throws SerializableException {
		try {
			return service.findLuisByRelation(luiId, luRelationTypeId);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
	}

	/**
	 * @param cluId
	 * @param atpId
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#findLuisForClu(java.lang.String,
	 *      java.lang.String)
	 */
	public List<LuiDisplay> findLuisForClu(String cluId, String atpId)
			throws SerializableException {
		try {
			return service.findLuisForClu(cluId, atpId);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
	}

	/**
	 * @return
	 * @throws OperationFailedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#findLuRelationTypes()
	 */
	public List<String> findLuRelationTypes() throws SerializableException {
		try {
			return service.findLuRelationTypes();
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
	}

	/**
	 * @return
	 * @throws OperationFailedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#findLuTypes()
	 */
	public List<LuTypeInfo> findLuTypes() throws SerializableException {
		try {
			return service.findLuTypes();
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
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
		try {
			return service.findPrerequisitesForDisplay(cluId);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
	}

	/**
	 * @param cluId
	 * @param luRelationTypeId
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#findRelatedCluIdsForCluId(java.lang.String,
	 *      java.lang.String)
	 */
	public List<String> findRelatedCluIdsForCluId(String cluId,
			String luRelationTypeId) throws SerializableException {
		try {
			return service.findRelatedCluIdsForCluId(cluId, luRelationTypeId);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
	}

	/**
	 * @param cluId
	 * @param luRelationTypeId
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#findRelatedClusForCluId(java.lang.String,
	 *      java.lang.String)
	 */
	public List<CluDisplay> findRelatedClusForCluId(String cluId,
			String luRelationTypeId) throws SerializableException {
		try {
			return service.findRelatedClusForCluId(cluId, luRelationTypeId);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
	}

	/**
	 * @param luiId
	 * @param luRelationTypeId
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#findRelatedLuiIdsForLuiId(java.lang.String,
	 *      java.lang.String)
	 */
	public List<String> findRelatedLuiIdsForLuiId(String luiId,
			String luRelationTypeId) throws SerializableException {
		try {
			return service.findRelatedLuiIdsForLuiId(luiId, luRelationTypeId);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
	}

	/**
	 * @param luiId
	 * @param luRelationTypeId
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#findRelatedLuisForLuiId(java.lang.String,
	 *      java.lang.String)
	 */
	public List<CluDisplay> findRelatedLuisForLuiId(String luiId,
			String luRelationTypeId) throws SerializableException {
		try {
			return service.findRelatedLuisForLuiId(luiId, luRelationTypeId);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
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
		try {
			return service.findSimpleAntirequisites(cluId);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
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
		try {
			return service.findSimpleCorequisites(cluId);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
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
		try {
			return service.findSimpleEquivalencies(cluId);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
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
		try {
			return service.findSimplePrerequisites(cluId);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
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
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#isCluInCluSet(java.lang.String,
	 *      java.lang.String)
	 */
	public boolean isCluInCluSet(String cluId, String cluSetId)
			throws SerializableException {
		try {
			return service.isCluInCluSet(cluId, cluSetId);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
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
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#removeCluFromCluSet(java.lang.String,
	 *      java.lang.String)
	 */
	public Status removeCluFromCluSet(String cluId, String cluSetId)
			throws SerializableException {
		try {
			return service.removeCluFromCluSet(cluId, cluSetId);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
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
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#removeCluSetFromCluSet(java.lang.String,
	 *      java.lang.String)
	 */
	public Status removeCluSetFromCluSet(String cluSetId, String removedCluSetId)
			throws SerializableException {
		try {
			return service.removeCluSetFromCluSet(cluSetId, removedCluSetId);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
	}

	/**
	 * @param luAttributeTypeInfoId
	 * @return
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#removeLuAttributeTypeInfo(java.lang.String)
	 */
	public Status removeLuAttributeTypeInfo(String luAttributeTypeInfoId) {
		try {
			return service.removeLuAttributeTypeInfo(luAttributeTypeInfoId);
		} catch (Exception e) {
			return null;
			// throw new SerializableException(e.getMessage());
		}
	}

	/**
	 * @param luTypeInfoId
	 * @return
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#removeLuTypeInfo(java.lang.String)
	 */
	public Status removeLuTypeInfo(String luTypeInfoId) {
		try {
			return service.removeLuTypeInfo(luTypeInfoId);
		} catch (Exception e) {
			return null;
			// throw new SerializableException(e.getMessage());
		}
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
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#removeSimpleAntirequisite(java.lang.String,
	 *      java.lang.String)
	 */
	public Status removeSimpleAntirequisite(String cluId, String antireqCluId)
			throws SerializableException {
		try {
			return service.removeSimpleAntirequisite(cluId, antireqCluId);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
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
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#removeSimpleCorequisite(java.lang.String,
	 *      java.lang.String)
	 */
	public Status removeSimpleCorequisite(String cluId, String coreqCluId)
			throws SerializableException {
		try {
			return service.removeSimpleCorequisite(cluId, coreqCluId);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
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
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#removeSimpleEquivalency(java.lang.String,
	 *      java.lang.String)
	 */
	public Status removeSimpleEquivalency(String cluId, String equivalentCluId)
			throws SerializableException {
		try {
			return service.removeSimpleEquivalency(cluId, equivalentCluId);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
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
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#removeSimplePrerequisite(java.lang.String,
	 *      java.lang.String)
	 */
	public Status removeSimplePrerequisite(String cluId, String prereqCluId)
			throws SerializableException {
		try {
			return service.removeSimplePrerequisite(cluId, prereqCluId);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
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
		try {
			return service.searchForCluIds(cluCriteria);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
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
		try {
			return service.searchForCluRelations(cluRelationCriteria);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
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
		try {
			return service.searchForClus(cluCriteria);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
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
		try {
			return service.searchForLuiIds(luiCriteria);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
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
		try {
			return service.searchForLuiRelations(luiRelationCriteria);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
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
		try {
			return service.searchForLuis(luiCriteria);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
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
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#updateClu(java.lang.String,
	 *      edu.umd.ks.poc.lum.lu.dto.CluUpdateInfo)
	 */
	public Status updateClu(String cluId, CluUpdateInfo cluUpdateInfo)
			throws SerializableException {
		try {
			return service.updateClu(cluId, cluUpdateInfo);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
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
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#updateCluRelation(java.lang.String,
	 *      java.lang.String, java.lang.String,
	 *      edu.umd.ks.poc.lum.lu.dto.CluRelationUpdateInfo)
	 */
	public Status updateCluRelation(String cluId, String relatedCluId,
			String luRelationTypeId, CluRelationUpdateInfo cluRelationUpdateInfo)
			throws SerializableException {
		try {
			return service.updateCluRelation(cluId, relatedCluId,
					luRelationTypeId, cluRelationUpdateInfo);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
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
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#updateCluSet(java.lang.String,
	 *      edu.umd.ks.poc.lum.lu.dto.CluSetUpdateInfo)
	 */
	public Status updateCluSet(String cluSetId,
			CluSetUpdateInfo cluSetUpdateInfo) throws SerializableException {
		try {
			return service.updateCluSet(cluSetId, cluSetUpdateInfo);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
	}

	/**
	 * @param luAttributeTypeInfo
	 * @return
	 * @throws SerializableException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#updateLuAttributeTypeInfo(edu.umd.ks.poc.lum.lu.dto.LuAttributeTypeInfo)
	 */
	public String updateLuAttributeTypeInfo(
			LuAttributeTypeInfo luAttributeTypeInfo) {
		try {
			return service.updateLuAttributeTypeInfo(luAttributeTypeInfo);
		} catch (Exception e) {
			return null;
			// throw new SerializableException(e.getMessage());
		}
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
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#updateLui(java.lang.String,
	 *      edu.umd.ks.poc.lum.lu.dto.LuiUpdateInfo)
	 */
	public Status updateLui(String luiId, LuiUpdateInfo cluUpdateInfo)
			throws SerializableException {
		try {
			return service.updateLui(luiId, cluUpdateInfo);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
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
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#updateLuiRelation(java.lang.String,
	 *      java.lang.String, java.lang.String,
	 *      edu.umd.ks.poc.lum.lu.dto.LuiRelationUpdateInfo)
	 */
	public Status updateLuiRelation(String luiId, String relatedLuiId,
			String luRelationType, LuiRelationUpdateInfo luiRelationUpdateInfo)
			throws SerializableException {
		try {
			return service.updateLuiRelation(luiId, relatedLuiId,
					luRelationType, luiRelationUpdateInfo);
		} catch (Exception e) {
			throw new SerializableException(e.getMessage());
		}
	}

	/**
	 * @param luTypeInfo
	 * @return
	 * @throws SerializableException
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#updateLuTypeInfo(edu.umd.ks.poc.lum.lu.dto.LuTypeInfo)
	 */
	public String updateLuTypeInfo(LuTypeInfo luTypeInfo) {
		try {
			return service.updateLuTypeInfo(luTypeInfo);
		} catch (Exception e) {
			return null;
			// throw new SerializableException(e.getMessage());
		}
	}

	/**
	 * @param descriptionSearchString
	 * @return
	 * @see edu.umd.ks.poc.lum.lu.service.LuService#searchForLuTypesByDescription(java.lang.String)
	 */
	public List<LuTypeInfo> searchForLuTypesByDescription(
			String descriptionSearchString) {
		try {
			return service.searchForLuTypesByDescription(descriptionSearchString);
		} catch (Exception e) {
			return null;
			// throw new SerializableException(e.getMessage());
		}
	}

	@Override
	public String createAndRouteClu(String userId, String luTypeKey,
			CluCreateInfo cluCreateInfo, String createComment) {
		try {
			cluCreateInfo.setStatus("R");
			String cluId = service.createClu(luTypeKey, cluCreateInfo);
			DocumentResponse docResp = wfService.create(userId, "1234", "CluDocument", cluCreateInfo.getCluShortName());
			
			
			DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            Element root = doc.createElement("cluId");
            root.setAttribute("DeptCode", cluCreateInfo.getAdminDepartment());
            doc.appendChild(root);
            Text text = doc.createTextNode(cluId);
            root.appendChild(text);
            
            DOMSource domSource = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);
            
            
			String docContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><cluId DeptCode=\""+cluCreateInfo.getAdminDepartment()+"\">"+cluId+"</cluId>";
			System.out.println("## DocContent:"+docContent);
			System.out.println("## DocContentWriter:"+writer.toString());
			wfService.route(docResp.getDocId(), userId, cluCreateInfo.getCluShortName(), writer.toString(), createComment);
			
//			DocumentResponse afterRoutingDocResp = wfService.getDocument(docResp.getDocId(), userId);
//			//Update the Clu with the status code and routeId
//			CluUpdateInfo cluUpdateInfo = new CluUpdateInfo();
//			cluUpdateInfo.setCluShortName(cluCreateInfo.getCluShortName());
//			cluUpdateInfo.setDescription(cluCreateInfo.getDescription());
//			cluUpdateInfo.setEffectiveEndCycle(cluCreateInfo.getEffectiveEndCycle());
//			cluUpdateInfo.setEffectiveStartCycle(cluCreateInfo.getEffectiveStartCycle());
//			cluUpdateInfo.setEffectiveEndDate(cluCreateInfo.getEffectiveEndDate());
//			cluUpdateInfo.setEffectiveStartDate(cluCreateInfo.getEffectiveStartDate());
//			cluUpdateInfo.setAdminDepartment(cluCreateInfo.getAdminDepartment());
//			//cluUpdateInfo.setStatus(afterRoutingDocResp.getDocStatus()+docResp.getDocId());
//			if(Authorization.User.admin.toString().equals(userId)){
//				cluUpdateInfo.setStatus("F"+docResp.getDocId());
//			}else{
//				cluUpdateInfo.setStatus("R"+docResp.getDocId());
//			}
//			cluUpdateInfo.getAttributes().putAll(cluCreateInfo.getAttributes());
//
//			service.updateClu(cluId, cluUpdateInfo);
			
			return cluId;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Status approveClu(String userId, String cluDocId, String cluId, String comment) {

		try {
			//Get the Clu 
			CluInfo clu = service.fetchClu(cluId);

			//Do the approval
			String docContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><cluId DeptCode=\""+clu.getAdminDepartment()+"\">"+cluId+"</cluId>";
			StandardResponse stdResp = wfService.approve(cluDocId, userId, clu.getCluShortName(), docContent, comment);
//			DocumentResponse afterRoutingDocResp = wfService.getDocument(cluDocId, userId);
//			
//			//Update the Clu with the status code and routeId
//			CluUpdateInfo cluUpdateInfo = new CluUpdateInfo();
//			cluUpdateInfo.setCluShortName(clu.getCluShortName());
//			cluUpdateInfo.setDescription(clu.getDescription());
//			cluUpdateInfo.setEffectiveEndCycle(clu.getEffectiveEndCycle());
//			cluUpdateInfo.setEffectiveStartCycle(clu.getEffectiveStartCycle());
//			cluUpdateInfo.setEffectiveEndDate(clu.getEffectiveEndDate());
//			cluUpdateInfo.setEffectiveStartDate(clu.getEffectiveStartDate());
//			cluUpdateInfo.setAdminDepartment(clu.getAdminDepartment());
//			cluUpdateInfo.setStatus("F"+cluDocId);
////			cluUpdateInfo.setStatus(afterRoutingDocResp.getDocStatus()+cluDocId);
//			cluUpdateInfo.getAttributes().putAll(clu.getAttributes());
//			service.updateClu(cluId, cluUpdateInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		Status status = new Status();
		status.setSuccess(true);
		return status;
	}

	@Override
	public Status disapproveClu(String userId, String cluDocId, String cluId, String comment) {
		StandardResponse stdResp = wfService.disapprove(cluDocId, userId, comment);
		DocumentResponse afterRoutingDocResp = wfService.getDocument(cluDocId, userId);
		//Get the Clu and update it's status
		try {
			CluInfo clu = service.fetchClu(cluId);
			//Update the Clu with the status code and routeId
			CluUpdateInfo cluUpdateInfo = new CluUpdateInfo();
			cluUpdateInfo.setCluShortName(clu.getCluShortName());
			cluUpdateInfo.setDescription(clu.getDescription());
			cluUpdateInfo.setEffectiveEndCycle(clu.getEffectiveEndCycle());
			cluUpdateInfo.setEffectiveStartCycle(clu.getEffectiveStartCycle());
			cluUpdateInfo.setEffectiveEndDate(clu.getEffectiveEndDate());
			cluUpdateInfo.setEffectiveStartDate(clu.getEffectiveStartDate());
			cluUpdateInfo.setAdminDepartment(clu.getAdminDepartment());
			//cluUpdateInfo.setStatus(afterRoutingDocResp.getDocStatus()+cluDocId);
			cluUpdateInfo.setStatus("D"+cluDocId);
			cluUpdateInfo.getAttributes().putAll(clu.getAttributes());
			service.updateClu(cluId, cluUpdateInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Status status = new Status();
		status.setSuccess(true);
		return status;
	}

	/**
	 * @return the wfService
	 */
	public SimpleDocumentActionsWebService getWfService() {
		return wfService;
	}

	/**
	 * @param wfService the wfService to set
	 */
	public void setWfService(SimpleDocumentActionsWebService wfService) {
		this.wfService = wfService;
	}


}
