package edu.umd.ks.poc.lum.lu.service.impl;

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
import edu.umd.ks.poc.lum.lu.dao.LuDao;
import edu.umd.ks.poc.lum.lu.entity.Clu;
import edu.umd.ks.poc.lum.lu.entity.CluRelation;
import edu.umd.ks.poc.lum.lu.entity.CluSet;
import edu.umd.ks.poc.lum.lu.entity.LuAttributeType;
import edu.umd.ks.poc.lum.lu.entity.LuRelationType;
import edu.umd.ks.poc.lum.lu.entity.LuType;
import edu.umd.ks.poc.lum.lu.entity.Lui;
import edu.umd.ks.poc.lum.lu.entity.LuiRelation;
import edu.umd.ks.poc.lum.lu.service.LuService;
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
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

@WebService(endpointInterface = "edu.umd.ks.poc.lum.lu.service.LuService", serviceName = "LuService", portName = "LuService", targetNamespace = "http://edu.umd.ks/poc/lum/lu")
@Transactional
public class LuServiceImpl implements LuService {
	private LuDao dao;


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


	public Status addSimpleAntirequisite(String cluId, String antireqCluId)
			throws DoesNotExistException, AlreadyExistsException,
			CircularReferenceException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}


	public Status addSimpleCorequisite(String cluId, String coreqCluId)
			throws DoesNotExistException, AlreadyExistsException,
			CircularReferenceException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}


	public Status addSimpleEquivalency(String cluId, String equivalentCluId)
			throws DoesNotExistException, AlreadyExistsException,
			CircularReferenceException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}


	public Status addSimplePrerequisite(String cluId, String prereqCluId)
			throws DoesNotExistException, AlreadyExistsException,
			CircularReferenceException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}


	public Status assignCluRelation(String cluId, String relatedCluId,
			String luRelationType, CluRelationAssignInfo cluRelationAssignInfo)
			throws AlreadyExistsException, CircularReferenceException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}


	public Status assignLuiRelation(String luiId, String relatedLuiId,
			String luRelationTypeId, LuiRelationAssignInfo luiRelationAssignInfo)
			throws AlreadyExistsException, CircularReferenceException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}


	public String createClu(String luTypeId, CluCreateInfo cluCreateInfo)
			throws AlreadyExistsException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {

		Clu clu = Assembler.createClu(luTypeId, cluCreateInfo, dao);
		dao.createClu(clu);
		return clu.getCluId();
	}


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


	public String createEnumeratedCluSet(String cluSetName,
			CluSetCreateInfo cluSetCreateInfo) throws AlreadyExistsException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {

		CluSet cluSet = Assembler.createCluSet(cluSetName, cluSetCreateInfo,
				null, dao);
		dao.createCluSet(cluSet);
		return cluSet.getCluSetId();
	}


	public String createLui(String cluId, String atpId,
			LuiCreateInfo luiCreateInfo) throws AlreadyExistsException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		Lui lui = Assembler.createLui(cluId, atpId, luiCreateInfo, dao);
		dao.createLui(lui);
		return lui.getLuiId();
	}


	public Status deleteClu(String cluId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			DependentObjectsExistException, OperationFailedException,
			PermissionDeniedException {
		Status status = new Status();
		status.setSuccess(dao.deleteClu(dao.fetchClu(cluId)));
		return status;
	}


	public Status deleteCluRelation(String cluId, String relatedCluId,
			String luRelationTypeId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		return null;
	}


	public Status deleteCluSet(String cluSetId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		Status status = new Status();
		status.setSuccess(dao.deleteCluSet(dao.fetchCluSet(cluSetId)));
		return status;
	}


	public Status deleteLui(String luiId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			DependentObjectsExistException, OperationFailedException,
			PermissionDeniedException {

		Lui lui = dao.fetchLui(luiId);
		Status status = new Status();
		status.setSuccess(dao.deleteLui(lui));
		return status;
	}


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


	public CluInfo fetchClu(String cluId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		Clu clu = dao.fetchClu(cluId);
		return Assembler.createCluInfo(clu);

	}


	public CluRelationInfo fetchCluRelation(String cluId, String relatedCluId,
			String luRelationTypeId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		CluRelation cluRelation = dao.fetchCluRelation(cluId, relatedCluId,
				luRelationTypeId);
		return Assembler.createCluRelationInfo(cluRelation);
	}


	public CluSetInfo fetchCluSetInfo(String cluSetId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		CluSet cluSet = dao.fetchCluSet(cluSetId);
		return Assembler.createCluSetInfo(cluSet);
	}


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


	public LuiInfo fetchLui(String luiId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return Assembler.createLuiInfo(dao.fetchLui(luiId));
	}


	public LuiDisplay fetchLuiDisplay(String luiId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return Assembler.createLuiDisplay(dao.fetchLui(luiId));
	}


	public LuiRelationInfo fetchLuiRelation(String luiId, String relatedLuiId,
			String luRelationTypeId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {

		LuiRelation luiRelation = dao.fetchLuiRelation(luiId, relatedLuiId,
				luRelationTypeId);
		return Assembler.createLuiRelationInfo(luiRelation);
	}


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


	public String findAntirequisitesForDisplay(String cluId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}


	public List<String> findCluIdsByRelation(String cluId,
			String luRelationTypeId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}


	public List<String> findCluIdsForLuType(String luTypeId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		List<String> cluIds = new ArrayList<String>();
		List<Clu> clus = dao.findClusForLuType(luTypeId);
		for(Clu clu:clus){
			cluIds.add(clu.getCluId());
		}
		return cluIds;
	}


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


	public List<CluRelationDisplay> findCluRelations(String cluId,
			String luRelationType) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}


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


	public List<CluDisplay> findClusByRelation(String cluId,
			String luRelationTypeId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}


	public List<CluDisplay> findClusForLuType(String luTypeId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		List<CluDisplay> cluDisplays = new ArrayList<CluDisplay>();
		List<Clu> clus = dao.findClusForLuType(luTypeId);
		for(Clu clu:clus){
			cluDisplays.add(Assembler.createCluDisplay(clu));
		}
		return cluDisplays;
	}


	public List<CluDisplay> findClusFromCluSet(String cluSetId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}


	public String findCorequisitesForDisplay(String cluId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}


	public List<String> findEnumeratedCluIdsInCluSet(String cluSetId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}


	public List<CluDisplay> findEnumeratedClusInCluSet(String cluSetId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}


	public String findEquivalenciesForDisplay(String cluId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}


	public List<String> findLuRelationTypes() throws OperationFailedException {
		List<LuRelationType> luRelationTypes = dao.findLuRelationTypes();
		List<String> result = new ArrayList<String>();
		for (LuRelationType luRelationType : luRelationTypes) {
			result.add(luRelationType.getId());
		}
		return result;
	}


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


	public List<String> findLuiIdsByRelation(String luiId,
			String luRelationTypeId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}


	public List<String> findLuiIdsForClu(String cluId, String atpId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {

		List<String> result = new ArrayList<String>();
		for (Lui lui : dao.findLuisForClu(cluId, atpId)) {
			result.add(lui.getLuiId());
		}
		return result;
	}


	public List<LuiRelationDisplay> findLuiRelations(String luiId,
			String luRelationTypeId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}


	public List<LuiDisplay> findLuisByRelation(String luiId,
			String luRelationTypeId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}


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


	public String findPrerequisitesForDisplay(String cluId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}


	public List<String> findRelatedCluIdsForCluId(String cluId,
			String luRelationTypeId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}


	public List<CluDisplay> findRelatedClusForCluId(String cluId,
			String luRelationTypeId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}


	public List<String> findRelatedLuiIdsForLuiId(String luiId,
			String luRelationTypeId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}


	public List<CluDisplay> findRelatedLuisForLuiId(String luiId,
			String luRelationTypeId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}


	public List<CluDisplay> findSimpleAntirequisites(String cluId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}


	public List<CluDisplay> findSimpleCorequisites(String cluId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}


	public List<CluDisplay> findSimpleEquivalencies(String cluId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}


	public List<CluDisplay> findSimplePrerequisites(String cluId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}


	public boolean isCluInCluSet(String cluId, String cluSetId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {

		CluSet cluSet = dao.fetchCluSet(cluSetId);
		Clu clu = dao.fetchClu(cluId);
		return extractClusFromCluSets(flattenCluSet(cluSet)).contains(clu);
	}


	public Status removeCluFromCluSet(String cluId, String cluSetId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, UnsupportedActionException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}


	public Status removeCluSetFromCluSet(String cluSetId, String removedCluSetId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}


	public Status removeSimpleAntirequisite(String cluId, String antireqCluId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}


	public Status removeSimpleCorequisite(String cluId, String coreqCluId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}


	public Status removeSimpleEquivalency(String cluId, String equivalentCluId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}


	public Status removeSimplePrerequisite(String cluId, String prereqCluId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}


	public List<String> searchForCluIds(CluCriteria cluCriteria)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}


	public List<CluRelationDisplay> searchForCluRelations(
			CluRelationCriteria cluRelationCriteria)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}


	public List<CluDisplay> searchForClus(CluCriteria cluCriteria)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}


	public List<String> searchForLuiIds(LuiCriteria luiCriteria)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}


	public List<LuiRelationDisplay> searchForLuiRelations(
			LuiRelationCriteria luiRelationCriteria)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}


	public List<LuiDisplay> searchForLuis(LuiCriteria luiCriteria)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
		List<LuiDisplay> results = new ArrayList<LuiDisplay>();
		for(Lui lui:dao.searchForLuis(luiCriteria)){
			results.add(Assembler.createLuiDisplay(lui));
		}
		return results;
	}


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


	@Override
	public String createLuAttributeTypeInfo(
			LuAttributeTypeInfo luAttributeTypeInfo) {
		LuAttributeType luAttrType = Assembler.createLuAttributeType(luAttributeTypeInfo);
		dao.createLuAttributeType(luAttrType);
		return luAttrType.getId();
	}


	@Override
	public String createLuTypeInfo(LuTypeInfo luTypeInfo) {
		LuType luType = Assembler.createLuType(luTypeInfo, dao);
		dao.createLuType(luType);
		return luType.getLuTypeId();
	}


	@Override
	public LuAttributeTypeInfo fetchLuAttributeType(String luAttributeTypeId) {
		LuAttributeType luAttrType = dao.fetchLuAttributeType(luAttributeTypeId);
		return Assembler.createLuAttributeTypeInfo(luAttrType);
	}


	@Override
	public List<LuAttributeTypeInfo> findLuAttributeTypes() {
		List<LuAttributeTypeInfo> results = new ArrayList<LuAttributeTypeInfo>();
		List<LuAttributeType> luAttrTypes = dao.findLuAttributeTypes();
		for(LuAttributeType luAttrType:luAttrTypes){
			results.add(Assembler.createLuAttributeTypeInfo(luAttrType));
		}
		return results;
	}


	@Override
	public List<LuAttributeTypeInfo> findLuAttributeTypesForLuTypeId(
			String luTypeId) {
		List<LuAttributeTypeInfo> results = new ArrayList<LuAttributeTypeInfo>();
		List<LuAttributeType> luAttrTypes = dao.findLuAttributeTypesForLuTypeId(luTypeId);
		for(LuAttributeType luAttrType:luAttrTypes){
			results.add(Assembler.createLuAttributeTypeInfo(luAttrType));
		}
		return results;
	}


	@Override
	public Status removeLuAttributeTypeInfo(String luAttributeTypeInfoId) {
		Status status = new Status();
		status.setSuccess(dao.deleteLuAttributeType(dao.fetchLuAttributeType(luAttributeTypeInfoId)));
		return status;
	}


	@Override
	public Status removeLuTypeInfo(String luTypeInfoId) {
		Status status = new Status();
		status.setSuccess(dao.deleteLuType(dao.fetchLuType(luTypeInfoId)));
		return status;
	}


	@Override
	public String updateLuAttributeTypeInfo(
			LuAttributeTypeInfo luAttributeTypeInfo) {
		LuAttributeType luAttrType = dao.fetchLuAttributeType(luAttributeTypeInfo.getId());
		Assembler.updateLuAttributeType(luAttributeTypeInfo, luAttrType);
		dao.updateLuAttributeType(luAttrType);
		return luAttributeTypeInfo.getId();
	}


	@Override
	public String updateLuTypeInfo(LuTypeInfo luTypeInfo) {
		LuType luType = dao.fetchLuType(luTypeInfo.getLuTypeKey());
		Assembler.updateLuType(luTypeInfo, luType, dao);
		dao.updateLuType(luType);
		return luTypeInfo.getLuTypeKey();
	}


	@Override
	public List<LuTypeInfo> searchForLuTypesByDescription(
			String descriptionSearchString) {
		List<LuTypeInfo> results = new ArrayList<LuTypeInfo>();
		for(LuType luType:dao.searchForLuTypesByDescription(descriptionSearchString)){
			results.add(Assembler.createLuTypeInfo(luType));
		}
		return results;
	}

}
