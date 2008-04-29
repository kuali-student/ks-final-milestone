package org.kuali.student.poc.learningunit.lu.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jws.WebService;

import org.kuali.student.poc.common.ws.exceptions.AlreadyExistsException;
import org.kuali.student.poc.common.ws.exceptions.CircularReferenceException;
import org.kuali.student.poc.common.ws.exceptions.DependentObjectsExistException;
import org.kuali.student.poc.common.ws.exceptions.DoesNotExistException;
import org.kuali.student.poc.common.ws.exceptions.InvalidParameterException;
import org.kuali.student.poc.common.ws.exceptions.MissingParameterException;
import org.kuali.student.poc.common.ws.exceptions.OperationFailedException;
import org.kuali.student.poc.common.ws.exceptions.PermissionDeniedException;
import org.kuali.student.poc.common.ws.exceptions.UnsupportedActionException;
import org.kuali.student.poc.learningunit.lu.dao.LuDao;
import org.kuali.student.poc.learningunit.lu.entity.Clu;
import org.kuali.student.poc.learningunit.lu.entity.CluRelation;
import org.kuali.student.poc.learningunit.lu.entity.CluSet;
import org.kuali.student.poc.learningunit.lu.entity.LuRelationType;
import org.kuali.student.poc.learningunit.lu.entity.LuType;
import org.kuali.student.poc.learningunit.lu.entity.Lui;
import org.kuali.student.poc.learningunit.lu.entity.LuiRelation;
import org.kuali.student.poc.wsdl.learningunit.lu.LuService;
import org.kuali.student.poc.xsd.learningunit.lu.dto.CluCreateInfo;
import org.kuali.student.poc.xsd.learningunit.lu.dto.CluCriteria;
import org.kuali.student.poc.xsd.learningunit.lu.dto.CluDisplay;
import org.kuali.student.poc.xsd.learningunit.lu.dto.CluInfo;
import org.kuali.student.poc.xsd.learningunit.lu.dto.CluRelationAssignInfo;
import org.kuali.student.poc.xsd.learningunit.lu.dto.CluRelationCriteria;
import org.kuali.student.poc.xsd.learningunit.lu.dto.CluRelationDisplay;
import org.kuali.student.poc.xsd.learningunit.lu.dto.CluRelationInfo;
import org.kuali.student.poc.xsd.learningunit.lu.dto.CluRelationUpdateInfo;
import org.kuali.student.poc.xsd.learningunit.lu.dto.CluSetCreateInfo;
import org.kuali.student.poc.xsd.learningunit.lu.dto.CluSetInfo;
import org.kuali.student.poc.xsd.learningunit.lu.dto.CluSetUpdateInfo;
import org.kuali.student.poc.xsd.learningunit.lu.dto.CluUpdateInfo;
import org.kuali.student.poc.xsd.learningunit.lu.dto.LuTypeInfo;
import org.kuali.student.poc.xsd.learningunit.lu.dto.LuiCreateInfo;
import org.kuali.student.poc.xsd.learningunit.lu.dto.LuiCriteria;
import org.kuali.student.poc.xsd.learningunit.lu.dto.LuiDisplay;
import org.kuali.student.poc.xsd.learningunit.lu.dto.LuiInfo;
import org.kuali.student.poc.xsd.learningunit.lu.dto.LuiRelationAssignInfo;
import org.kuali.student.poc.xsd.learningunit.lu.dto.LuiRelationCriteria;
import org.kuali.student.poc.xsd.learningunit.lu.dto.LuiRelationDisplay;
import org.kuali.student.poc.xsd.learningunit.lu.dto.LuiRelationInfo;
import org.kuali.student.poc.xsd.learningunit.lu.dto.LuiRelationUpdateInfo;
import org.kuali.student.poc.xsd.learningunit.lu.dto.LuiUpdateInfo;
import org.kuali.student.poc.xsd.learningunit.lu.dto.Status;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

@WebService(endpointInterface = "org.kuali.student.poc.wsdl.learningunit.lu.LuService", serviceName = "LuService", portName = "LuService", targetNamespace = "http://student.kuali.org/poc/wsdl/learningunit/lu")
@Transactional
public class LuServiceImpl implements LuService {
	private LuDao dao;

	@Override
	public Status addCluSetToCluSet(String cluSetId, String addedCluSetId)
			throws DoesNotExistException, CircularReferenceException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		CluSet cluSetChild = dao.fetchCluSet(cluSetId);
		CluSet cluParentSet = dao.fetchCluSet(addedCluSetId);
		// Check if the sets do not exist
		if (cluSetChild == null || cluParentSet == null) {
			throw new DoesNotExistException();
		}
		// Check if trying to add a set to itself
		if (cluSetId.equals(addedCluSetId)) {
			throw new InvalidParameterException(
					"Can not add a Clu set to itself for CluSet id:" + cluSetId);
		}
		// Check for CircularReference -
		// We can't add a child to a parent if that child already contains said
		// parent
		if (flattenCluSet(cluSetChild).contains(cluParentSet)) {
			throw new CircularReferenceException(
					"Can not create a circular reference to a Clu Set for CluChildSet id:"
							+ cluSetId + " CluParentSet id:" + addedCluSetId);
		}
		cluParentSet.getCluSetList().add(cluSetChild);
		dao.updateCluSet(cluParentSet);
		Status status = new Status();
		status.setSuccess(true);
		return status;
	}

	@Override
	public Status addCluToCluSet(String cluId, String cluSetId)
			throws DoesNotExistException, CircularReferenceException,
			InvalidParameterException, MissingParameterException,
			UnsupportedActionException, OperationFailedException,
			PermissionDeniedException {
		Clu clu = dao.fetchClu(cluId);
		CluSet cluSet = dao.fetchCluSet(cluSetId);
		if (!cluSet.getCluList().contains(clu)) {
			cluSet.getCluList().add(clu);
		}
		Status status = new Status();
		status.setSuccess(true);
		return status;
	}

	private Set<CluSet> flattenCluSet(CluSet cluSet) {
		Set<CluSet> flattenedSet = new HashSet<CluSet>();
		flattenedSet.add(cluSet);
		for (CluSet subSet : cluSet.getCluSetList()) {
			flattenedSet.addAll(flattenCluSet(subSet));
		}
		return flattenedSet;
	}

	private Set<Clu> extractClusFromCluSets(Set<CluSet> cluSets) {
		Set<Clu> clus = new HashSet<Clu>();
		for (CluSet subSet : cluSets) {
			clus.addAll(subSet.getCluList());
		}
		return clus;
	}

	@Override
	public Status addSimpleAntirequisite(String cluId, String antireqCluId)
			throws DoesNotExistException, AlreadyExistsException,
			CircularReferenceException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Status addSimpleCorequisite(String cluId, String coreqCluId)
			throws DoesNotExistException, AlreadyExistsException,
			CircularReferenceException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Status addSimpleEquivalency(String cluId, String equivalentCluId)
			throws DoesNotExistException, AlreadyExistsException,
			CircularReferenceException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Status addSimplePrerequisite(String cluId, String prereqCluId)
			throws DoesNotExistException, AlreadyExistsException,
			CircularReferenceException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Status assignCluRelation(String cluId, String relatedCluId,
			String luRelationType, CluRelationAssignInfo cluRelationAssignInfo)
			throws AlreadyExistsException, CircularReferenceException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Status assignLuiRelation(String luiId, String relatedLuiId,
			String luRelationTypeId, LuiRelationAssignInfo luiRelationAssignInfo)
			throws AlreadyExistsException, CircularReferenceException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createClu(String luTypeId, CluCreateInfo cluCreateInfo)
			throws AlreadyExistsException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {

		Clu clu = Assembler.createClu(luTypeId, cluCreateInfo, dao);
		dao.createClu(clu);
		return clu.getCluId();
	}

	@Override
	public String createDynamicCluSet(String cluSetName,
			CluSetCreateInfo cluSetCreateInfo, CluCriteria cluCriteria)
			throws AlreadyExistsException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {

		CluSet cluSet = Assembler.createCluSet(cluSetName, cluSetCreateInfo,
				cluCriteria, dao);
		dao.createCluSet(cluSet);
		return cluSet.getCluSetId();
	}

	@Override
	public String createEnumeratedCluSet(String cluSetName,
			CluSetCreateInfo cluSetCreateInfo) throws AlreadyExistsException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {

		CluSet cluSet = Assembler.createCluSet(cluSetName, cluSetCreateInfo,
				null, dao);
		dao.createCluSet(cluSet);
		return cluSet.getCluSetId();
	}

	@Override
	public String createLui(String cluId, String atpId,
			LuiCreateInfo luiCreateInfo) throws AlreadyExistsException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		Lui lui = Assembler.createLui(cluId, atpId, luiCreateInfo, dao);
		dao.createLui(lui);
		return lui.getLuiId();
	}

	@Override
	public Status deleteClu(String cluId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			DependentObjectsExistException, OperationFailedException,
			PermissionDeniedException {
		Status status = new Status();
		status.setSuccess(dao.deleteClu(dao.fetchClu(cluId)));
		return status;
	}

	@Override
	public Status deleteCluRelation(String cluId, String relatedCluId,
			String luRelationTypeId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		return null;
	}

	@Override
	public Status deleteCluSet(String cluSetId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		Status status = new Status();
		status.setSuccess(dao.deleteCluSet(dao.fetchCluSet(cluSetId)));
		return status;
	}

	@Override
	public Status deleteLui(String luiId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			DependentObjectsExistException, OperationFailedException,
			PermissionDeniedException {

		Lui lui = dao.fetchLui(luiId);
		Status status = new Status();
		status.setSuccess(dao.deleteLui(lui));
		return status;
	}

	@Override
	public Status deleteLuiRelation(String luiId, String relatedLuiId,
			String luRelationTypeId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {

		LuiRelation luiRelation = dao.fetchLuiRelation(luiId, relatedLuiId,
				luRelationTypeId);
		Status status = new Status();
		status.setSuccess(dao.deleteLuiRelation(luiRelation));
		return status;
	}

	@Override
	public CluInfo fetchClu(String cluId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		Clu clu = dao.fetchClu(cluId);
		return Assembler.createCluInfo(clu);

	}

	@Override
	public CluRelationInfo fetchCluRelation(String cluId, String relatedCluId,
			String luRelationTypeId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		CluRelation cluRelation = dao.fetchCluRelation(cluId, relatedCluId,
				luRelationTypeId);
		return Assembler.createCluRelationInfo(cluRelation);
	}

	@Override
	public CluSetInfo fetchCluSetInfo(String cluSetId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		CluSet cluSet = dao.fetchCluSet(cluSetId);
		return Assembler.createCluSetInfo(cluSet);
	}

	@Override
	public LuTypeInfo fetchLuType(String luTypeId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		LuType luType = dao.fetchLuType(luTypeId);
		if (luType == null) {
			throw new DoesNotExistException("Lu Type with id:" + luTypeId
					+ " does not exist.");
		}
		return Assembler.createLuTypeInfo(luType);
	}

	@Override
	public LuiInfo fetchLui(String luiId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return Assembler.createLuiInfo(dao.fetchLui(luiId));
	}

	@Override
	public LuiDisplay fetchLuiDisplay(String luiId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return Assembler.createLuiDisplay(dao.fetchLui(luiId));
	}

	@Override
	public LuiRelationInfo fetchLuiRelation(String luiId, String relatedLuiId,
			String luRelationTypeId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {

		LuiRelation luiRelation = dao.fetchLuiRelation(luiId, relatedLuiId,
				luRelationTypeId);
		return Assembler.createLuiRelationInfo(luiRelation);
	}

	@Override
	public List<String> findAllowedLuLuRelationTypesForLuType(String luTypeId,
			String relatedLuTypeId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		Set<LuRelationType> luRelationTypes = dao
				.findAllowedLuLuRelationTypesForLuType(luTypeId,
						relatedLuTypeId);
		List<String> result = new ArrayList<String>();
		for (LuRelationType luRelationType : luRelationTypes) {
			result.add(luRelationType.getId());
		}
		return result;
	}

	@Override
	public List<String> findAllowedLuRelationTypesForClu(String cluId,
			String relatedCluId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		List<String> results = new ArrayList<String>();
		Set<LuRelationType> luRelationTypes = dao
				.findAllowedLuRelationTypesForClu(cluId, relatedCluId);
		for (LuRelationType luRelationType : luRelationTypes) {
			results.add(luRelationType.getId());
		}
		return results;
	}

	@Override
	public List<String> findAllowedLuRelationTypesForLui(String luiId,
			String relatedLuiId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		List<String> results = new ArrayList<String>();
		Set<LuRelationType> luRelationTypes = dao
				.findAllowedLuRelationTypesForLui(luiId, relatedLuiId);
		for (LuRelationType luRelationType : luRelationTypes) {
			results.add(luRelationType.getId());
		}
		return results;
	}

	@Override
	public String findAntirequisitesForDisplay(String cluId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> findCluIdsByRelation(String cluId,
			String luRelationTypeId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> findCluIdsForLuType(String luTypeId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> findCluIdsFromCluSet(String cluSetId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		List<String> cluIds = new ArrayList<String>();
		CluSet cluSet = dao.fetchCluSet(cluSetId);
		for (Clu clu : extractClusFromCluSets(flattenCluSet(cluSet))) {
			cluIds.add(clu.getCluId());
		}
		return cluIds;
	}

	@Override
	public List<CluRelationDisplay> findCluRelations(String cluId,
			String luRelationType) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> findCluSetIdsFromCluSet(String cluSetId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		List<String> cluSetIds = new ArrayList<String>();
		CluSet cluSet = dao.fetchCluSet(cluSetId);
		for (CluSet currentCluSet : flattenCluSet(cluSet)) {
			if (!cluSetId.equals(currentCluSet.getCluSetId())) {
				cluSetIds.add(currentCluSet.getCluSetId());
			}
		}
		return cluSetIds;
	}

	@Override
	public List<CluDisplay> findClusByRelation(String cluId,
			String luRelationTypeId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CluDisplay> findClusForLuType(String luTypeId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CluDisplay> findClusFromCluSet(String cluSetId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String findCorequisitesForDisplay(String cluId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> findEnumeratedCluIdsInCluSet(String cluSetId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CluDisplay> findEnumeratedClusInCluSet(String cluSetId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String findEquivalenciesForDisplay(String cluId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> findLuRelationTypes() throws OperationFailedException {
		List<LuRelationType> luRelationTypes = dao.findLuRelationTypes();
		List<String> result = new ArrayList<String>();
		for (LuRelationType luRelationType : luRelationTypes) {
			result.add(luRelationType.getId());
		}
		return result;
	}

	@Override
	public List<LuTypeInfo> findLuTypes() throws OperationFailedException {
		List<LuType> luTypes = dao.findLuTypes();
		List<LuTypeInfo> result = new ArrayList<LuTypeInfo>();
		for (LuType luType : luTypes) {
			LuTypeInfo luTypeInfo = new LuTypeInfo();
			BeanUtils.copyProperties(luType, luTypeInfo);
			luTypeInfo.setLuTypeKey(luType.getLuTypeId());
			result.add(luTypeInfo);
		}
		return result;
	}

	@Override
	public List<String> findLuiIdsByRelation(String luiId,
			String luRelationTypeId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> findLuiIdsForClu(String cluId, String atpId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {

		List<String> result = new ArrayList<String>();
		for (Lui lui : dao.findLuisForClu(cluId, atpId)) {
			result.add(lui.getLuiId());
		}
		return result;
	}

	@Override
	public List<LuiRelationDisplay> findLuiRelations(String luiId,
			String luRelationTypeId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LuiDisplay> findLuisByRelation(String luiId,
			String luRelationTypeId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LuiDisplay> findLuisForClu(String cluId, String atpId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		List<LuiDisplay> result = new ArrayList<LuiDisplay>();
		List<Lui> luis = dao.findLuisForClu(cluId, atpId);
		for (Lui lui : luis) {
			result.add(Assembler.createLuiDisplay(lui));
		}
		return result;
	}

	@Override
	public String findPrerequisitesForDisplay(String cluId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> findRelatedCluIdsForCluId(String cluId,
			String luRelationTypeId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CluDisplay> findRelatedClusForCluId(String cluId,
			String luRelationTypeId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> findRelatedLuiIdsForLuiId(String luiId,
			String luRelationTypeId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CluDisplay> findRelatedLuisForLuiId(String luiId,
			String luRelationTypeId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CluDisplay> findSimpleAntirequisites(String cluId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CluDisplay> findSimpleCorequisites(String cluId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CluDisplay> findSimpleEquivalencies(String cluId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CluDisplay> findSimplePrerequisites(String cluId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isCluInCluSet(String cluId, String cluSetId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {

		CluSet cluSet = dao.fetchCluSet(cluSetId);
		Clu clu = dao.fetchClu(cluId);
		return extractClusFromCluSets(flattenCluSet(cluSet)).contains(clu);
	}

	@Override
	public Status removeCluFromCluSet(String cluId, String cluSetId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, UnsupportedActionException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Status removeCluSetFromCluSet(String cluSetId, String removedCluSetId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Status removeSimpleAntirequisite(String cluId, String antireqCluId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Status removeSimpleCorequisite(String cluId, String coreqCluId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Status removeSimpleEquivalency(String cluId, String equivalentCluId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Status removeSimplePrerequisite(String cluId, String prereqCluId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> searchForCluIds(CluCriteria cluCriteria)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CluRelationDisplay> searchForCluRelations(
			CluRelationCriteria cluRelationCriteria)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CluDisplay> searchForClus(CluCriteria cluCriteria)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> searchForLuiIds(LuiCriteria luiCriteria)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LuiRelationDisplay> searchForLuiRelations(
			LuiRelationCriteria luiRelationCriteria)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LuiDisplay> searchForLuis(LuiCriteria luiCriteria)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Status updateClu(String cluId, CluUpdateInfo cluUpdateInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {

		Clu clu = dao.fetchClu(cluId);
		Assembler.updateClu(cluUpdateInfo, clu, dao);
		dao.updateClu(clu);
		Status status = new Status();
		status.setSuccess(true);
		return status;
	}

	@Override
	public Status updateCluRelation(String cluId, String relatedCluId,
			String luRelationTypeId, CluRelationUpdateInfo cluRelationUpdateInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {

		CluRelation cluRelation = dao.fetchCluRelation(cluId, relatedCluId,
				luRelationTypeId);
		Assembler.updateCluRelation(cluRelationUpdateInfo, cluRelation);
		dao.updateCluRelation(cluRelation);
		Status status = new Status();
		status.setSuccess(true);
		return status;
	}

	@Override
	public Status updateCluSet(String cluSetId,
			CluSetUpdateInfo cluSetUpdateInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {

		CluSet cluSet = dao.fetchCluSet(cluSetId);
		Assembler.updateCluSet(cluSet, cluSetUpdateInfo, dao);
		dao.updateCluSet(cluSet);
		Status status = new Status();
		status.setSuccess(true);
		return status;
	}

	@Override
	public Status updateLui(String luiId, LuiUpdateInfo luiUpdateInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		Lui lui = dao.fetchLui(luiId);
		Assembler.updateLui(luiUpdateInfo, lui);
		dao.updateLui(lui);
		Status status = new Status();
		status.setSuccess(true);
		return status;
	}

	@Override
	public Status updateLuiRelation(String luiId, String relatedLuiId,
			String luRelationTypeId, LuiRelationUpdateInfo luiRelationUpdateInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		LuiRelation luiRelation = dao.fetchLuiRelation(luiId, relatedLuiId,
				luRelationTypeId);
		Assembler.updateLuiRelation(luiRelationUpdateInfo, luiRelation);
		dao.updateLuiRelation(luiRelation);
		Status status = new Status();
		status.setSuccess(true);
		return status;
	}

	/**
	 * @return the dao
	 */
	public LuDao getDao() {
		return dao;
	}

	/**
	 * @param dao
	 *            the dao to set
	 */
	public void setDao(LuDao dao) {
		this.dao = dao;
	}

}
