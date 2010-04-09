/*
 * Copyright 2009 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.lum.lu.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.jws.WebService;

import org.apache.log4j.Logger;
import org.kuali.student.common.validator.Validator;
import org.kuali.student.core.dictionary.dto.ObjectStructure;
import org.kuali.student.core.dictionary.service.DictionaryService;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.entity.Amount;
import org.kuali.student.core.entity.TimeAmount;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.CircularRelationshipException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DependentObjectsExistException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.UnsupportedActionException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.core.search.dto.SearchCriteriaTypeInfo;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;
import org.kuali.student.core.search.dto.SearchResultCell;
import org.kuali.student.core.search.dto.SearchResultRow;
import org.kuali.student.core.search.dto.SearchResultTypeInfo;
import org.kuali.student.core.search.dto.SearchTypeInfo;
import org.kuali.student.core.search.service.impl.SearchManager;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.lum.lu.dao.LuDao;
import org.kuali.student.lum.lu.dto.AcademicSubjectOrgInfo;
import org.kuali.student.lum.lu.dto.AccreditationInfo;
import org.kuali.student.lum.lu.dto.AdminOrgInfo;
import org.kuali.student.lum.lu.dto.CluCluRelationInfo;
import org.kuali.student.lum.lu.dto.CluIdentifierInfo;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.dto.CluInstructorInfo;
import org.kuali.student.lum.lu.dto.CluLoRelationInfo;
import org.kuali.student.lum.lu.dto.CluLoRelationTypeInfo;
import org.kuali.student.lum.lu.dto.CluPublicationInfo;
import org.kuali.student.lum.lu.dto.CluResultInfo;
import org.kuali.student.lum.lu.dto.CluResultTypeInfo;
import org.kuali.student.lum.lu.dto.CluSetInfo;
import org.kuali.student.lum.lu.dto.CluSetTypeInfo;
import org.kuali.student.lum.lu.dto.DeliveryMethodTypeInfo;
import org.kuali.student.lum.lu.dto.InstructionalFormatTypeInfo;
import org.kuali.student.lum.lu.dto.LuCodeInfo;
import org.kuali.student.lum.lu.dto.LuCodeTypeInfo;
import org.kuali.student.lum.lu.dto.LuLuRelationTypeInfo;
import org.kuali.student.lum.lu.dto.LuPublicationTypeInfo;
import org.kuali.student.lum.lu.dto.LuTypeInfo;
import org.kuali.student.lum.lu.dto.LuiInfo;
import org.kuali.student.lum.lu.dto.LuiLuiRelationInfo;
import org.kuali.student.lum.lu.dto.MembershipQueryInfo;
import org.kuali.student.lum.lu.dto.ResultOptionInfo;
import org.kuali.student.lum.lu.dto.ResultUsageTypeInfo;
import org.kuali.student.lum.lu.entity.Clu;
import org.kuali.student.lum.lu.entity.CluAcademicSubjectOrg;
import org.kuali.student.lum.lu.entity.CluAccounting;
import org.kuali.student.lum.lu.entity.CluAccountingAttribute;
import org.kuali.student.lum.lu.entity.CluAccreditation;
import org.kuali.student.lum.lu.entity.CluAccreditationAttribute;
import org.kuali.student.lum.lu.entity.CluAdminOrg;
import org.kuali.student.lum.lu.entity.CluAdminOrgAttribute;
import org.kuali.student.lum.lu.entity.CluAtpTypeKey;
import org.kuali.student.lum.lu.entity.CluAttribute;
import org.kuali.student.lum.lu.entity.CluCampusLocation;
import org.kuali.student.lum.lu.entity.CluCluRelation;
import org.kuali.student.lum.lu.entity.CluCluRelationAttribute;
import org.kuali.student.lum.lu.entity.CluFee;
import org.kuali.student.lum.lu.entity.CluIdentifier;
import org.kuali.student.lum.lu.entity.CluInstructor;
import org.kuali.student.lum.lu.entity.CluInstructorAttribute;
import org.kuali.student.lum.lu.entity.CluLoRelation;
import org.kuali.student.lum.lu.entity.CluLoRelationAttribute;
import org.kuali.student.lum.lu.entity.CluLoRelationType;
import org.kuali.student.lum.lu.entity.CluResult;
import org.kuali.student.lum.lu.entity.CluResultType;
import org.kuali.student.lum.lu.entity.CluSet;
import org.kuali.student.lum.lu.entity.CluSetAttribute;
import org.kuali.student.lum.lu.entity.CluSetType;
import org.kuali.student.lum.lu.entity.DeliveryMethodType;
import org.kuali.student.lum.lu.entity.InstructionalFormatType;
import org.kuali.student.lum.lu.entity.LuCode;
import org.kuali.student.lum.lu.entity.LuCodeAttribute;
import org.kuali.student.lum.lu.entity.LuCodeType;
import org.kuali.student.lum.lu.entity.LuLuRelationType;
import org.kuali.student.lum.lu.entity.LuPublicationType;
import org.kuali.student.lum.lu.entity.LuRichText;
import org.kuali.student.lum.lu.entity.LuType;
import org.kuali.student.lum.lu.entity.Lui;
import org.kuali.student.lum.lu.entity.LuiAttribute;
import org.kuali.student.lum.lu.entity.LuiLuiRelation;
import org.kuali.student.lum.lu.entity.LuiLuiRelationAttribute;
import org.kuali.student.lum.lu.entity.MembershipQuery;
import org.kuali.student.lum.lu.entity.ResultOption;
import org.kuali.student.lum.lu.entity.ResultUsageType;
import org.kuali.student.lum.lu.service.LuService;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

@WebService(endpointInterface = "org.kuali.student.lum.lu.service.LuService", serviceName = "LuService", portName = "LuService", targetNamespace = "http://student.kuali.org/wsdl/lu")
@Transactional(rollbackFor = { Throwable.class })
public class LuServiceImpl implements LuService {

	final Logger logger = Logger.getLogger(LuServiceImpl.class);

	private LuDao luDao;
	private SearchManager searchManager;
	private DictionaryService dictionaryServiceDelegate;
	private Validator validator;

	public void setSearchManager(SearchManager searchManager) {
		this.searchManager = searchManager;
	}

	public void setDictionaryServiceDelegate(
			DictionaryService dictionaryServiceDelegate) {
		this.dictionaryServiceDelegate = dictionaryServiceDelegate;
	}

	public DictionaryService getDictionaryServiceDelegate() {
		return dictionaryServiceDelegate;
	}

	public Validator getValidator() {
		return validator;
	}

	public void setValidator(Validator validator) {
		this.validator = validator;
	}

	/**************************************************************************
	 * SETUP OPERATION *
	 **************************************************************************/

	@Override
	public List<DeliveryMethodTypeInfo> getDeliveryMethodTypes()
			throws OperationFailedException {
		return LuServiceAssembler.toDeliveryMethodTypeInfos(luDao
				.find(DeliveryMethodType.class));
	}

	@Override
	public DeliveryMethodTypeInfo getDeliveryMethodType(
			String deliveryMethodTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {

		checkForMissingParameter(deliveryMethodTypeKey, "deliveryMethodTypeKey");

		return LuServiceAssembler.toDeliveryMethodTypeInfo(luDao.fetch(
				DeliveryMethodType.class, deliveryMethodTypeKey));
	}

	@Override
	public List<InstructionalFormatTypeInfo> getInstructionalFormatTypes()
			throws OperationFailedException {
		return LuServiceAssembler.toInstructionalFormatTypeInfos(luDao
				.find(InstructionalFormatType.class));
	}

	@Override
	public InstructionalFormatTypeInfo getInstructionalFormatType(
			String instructionalFormatTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		checkForMissingParameter(instructionalFormatTypeKey,
				"instructionalFormatTypeKey");

		return LuServiceAssembler.toInstructionalFormatTypeInfo(luDao.fetch(
				InstructionalFormatType.class, instructionalFormatTypeKey));
	}

	@Override
	public List<LuTypeInfo> getLuTypes() throws OperationFailedException {
		return LuServiceAssembler.toLuTypeInfos(luDao.find(LuType.class));
	}

	@Override
	public LuTypeInfo getLuType(String luTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		checkForMissingParameter(luTypeKey, "luTypeKey");

		return LuServiceAssembler.toLuTypeInfo(luDao.fetch(LuType.class,
				luTypeKey));
	}

	@Override
	public LuCodeTypeInfo getLuCodeType(String luCodeTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		checkForMissingParameter(luCodeTypeKey, "luCodeTypeKey");
		return LuServiceAssembler.toLuCodeTypeInfo(luDao.fetch(
				LuCodeType.class, luCodeTypeKey));
	}

	@Override
	public List<LuCodeTypeInfo> getLuCodeTypes()
			throws OperationFailedException {
		return LuServiceAssembler.toLuCodeTypeInfos(luDao
				.find(LuCodeType.class));
	}

	@Override
	public List<LuLuRelationTypeInfo> getLuLuRelationTypes()
			throws OperationFailedException {
		return LuServiceAssembler.toLuLuRelationTypeInfos(luDao
				.find(LuLuRelationType.class));
	}

	@Override
	public LuLuRelationTypeInfo getLuLuRelationType(String luLuRelationTypeKey)
			throws OperationFailedException, MissingParameterException,
			DoesNotExistException {
		checkForMissingParameter(luLuRelationTypeKey, "luLuRelationTypeKey");

		LuLuRelationType luLuRelationType = luDao.fetch(LuLuRelationType.class,
				luLuRelationTypeKey);
		return LuServiceAssembler.toLuLuRelationTypeInfo(luLuRelationType);
	}

	@Override
	public List<String> getAllowedLuLuRelationTypesForLuType(String luTypeKey,
			String relatedLuTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		checkForMissingParameter(luTypeKey, "luTypeKey");
		checkForMissingParameter(relatedLuTypeKey, "relatedLuTypeKey");

		return luDao.getAllowedLuLuRelationTypesForLuType(luTypeKey,
				relatedLuTypeKey);
	}

	@Override
	public List<LuPublicationTypeInfo> getLuPublicationTypes()
			throws OperationFailedException {
		return LuServiceAssembler.toLuPublicationTypeInfos(luDao
				.find(LuPublicationType.class));
	}

	@Override
	public LuPublicationTypeInfo getLuPublicationType(
			String luPublicationTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		checkForMissingParameter(luPublicationTypeKey, "luPublicationTypeKey");

		return LuServiceAssembler.toLuPublicationTypeInfo(luDao.fetch(
				LuPublicationType.class, luPublicationTypeKey));
	}

	@Override
	public List<String> getLuPublicationTypesForLuType(String luTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CluResultTypeInfo> getCluResultTypes()
			throws OperationFailedException {
		return LuServiceAssembler.toCluResultTypeInfos(luDao
				.find(CluResultType.class));
	}

	@Override
	public CluResultTypeInfo getCluResultType(String cluResultTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return LuServiceAssembler.toCluResultTypeInfo(luDao.fetch(
				CluResultType.class, cluResultTypeKey));
	}

	@Override
	public List<CluResultTypeInfo> getCluResultTypesForLuType(String luTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		checkForMissingParameter(luTypeKey, "luTypeKey");
		return LuServiceAssembler.toCluResultTypeInfos((luDao
				.getAllowedCluResultTypesForLuType(luTypeKey)));
	}

	@Override
	public List<ResultUsageTypeInfo> getResultUsageTypes()
			throws OperationFailedException {
		return LuServiceAssembler.toResultUsageTypeInfos(luDao
				.find(ResultUsageType.class));
	}

	@Override
	public ResultUsageTypeInfo getResultUsageType(String resultUsageTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		checkForMissingParameter(resultUsageTypeKey, "resultUsageTypeKey");
		return LuServiceAssembler.toResultUsageTypeInfo(luDao.fetch(
				ResultUsageType.class, resultUsageTypeKey));
	}

	@Override
	public List<String> getAllowedResultUsageTypesForLuType(String luTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		checkForMissingParameter(luTypeKey, "luTypeKey");

		return luDao.getAllowedResultUsageTypesForLuType(luTypeKey);
	}

	@Override
	public List<String> getAllowedResultComponentTypesForResultUsageType(
			String resultUsageTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {

		checkForMissingParameter(resultUsageTypeKey, "resultUsageTypeKey");

		return luDao
				.getAllowedResultComponentTypesForResultUsageType(resultUsageTypeKey);
	}

	@Override
	public CluLoRelationTypeInfo getCluLoRelationType(
			String cluLoRelationTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		checkForMissingParameter(cluLoRelationTypeKey, "cluLoRelationTypeKey");

		CluLoRelationType cluLoRelationType = luDao.fetch(
				CluLoRelationType.class, cluLoRelationTypeKey);
		return LuServiceAssembler.toCluLoRelationTypeInfo(cluLoRelationType);
	}

	@Override
	public List<CluLoRelationTypeInfo> getCluLoRelationTypes()
			throws OperationFailedException {
		return LuServiceAssembler.toCluLoRelationTypeInfos(luDao
				.find(CluLoRelationType.class));
	}

	@Override
	public List<String> getAllowedCluLoRelationTypesForLuType(String luTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {

		checkForMissingParameter(luTypeKey, luTypeKey);

		return luDao.getAllowedCluLoRelationTypesForLuType(luTypeKey);
	}

	@Override
	public List<CluSetTypeInfo> getCluSetTypes()
			throws OperationFailedException {
		return LuServiceAssembler.toCluSetTypeInfos(luDao
				.find(CluSetType.class));
	}

	@Override
	public CluSetTypeInfo getCluSetType(String cluSetTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		checkForMissingParameter(cluSetTypeKey, "cluSetTypeKey");
		return LuServiceAssembler.toCluSetTypeInfo(luDao.fetch(
				CluSetType.class, cluSetTypeKey));
	}

	/**************************************************************************
	 * READ OPERATION *
	 **************************************************************************/

	// **** Core **********
	@Override
	public CluInfo getClu(String cluId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {

		checkForMissingParameter(cluId, "cluId");

		Clu clu = luDao.fetch(Clu.class, cluId);
		return LuServiceAssembler.toCluInfo(clu);
	}

	@Override
	public List<CluInfo> getClusByIdList(List<String> cluIdList)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		checkForMissingParameter(cluIdList, "cluIdList");
		checkForEmptyList(cluIdList, "cluIdList");
		List<Clu> clus = luDao.getClusByIdList(cluIdList);
		return LuServiceAssembler.toCluInfos(clus);
	}

	@Override
	public List<CluInfo> getClusByLuType(String luTypeKey, String luState)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		checkForMissingParameter(luTypeKey, "luTypeKey");
		checkForMissingParameter(luState, "lustate");
		List<Clu> clus = luDao.getClusByLuType(luTypeKey, luState);
		return LuServiceAssembler.toCluInfos(clus);
	}

	@Override
	public List<String> getCluIdsByLuType(String luTypeKey, String luState)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		checkForMissingParameter(luTypeKey, "luTypeKey");
		checkForMissingParameter(luState, "luState");
		List<Clu> clus = luDao.getClusByLuType(luTypeKey, luState);
		List<String> ids = new ArrayList<String>(clus.size());
		for (Clu clu : clus) {
			ids.add(clu.getId());
		}
		return ids;
	}

	// ****** Relations

	@Override
	public List<String> getAllowedLuLuRelationTypesByCluId(String cluId,
			String relatedCluId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		checkForMissingParameter(cluId, "cluId");
		checkForMissingParameter(relatedCluId, "relatedCluId");

		return luDao.getAllowedLuLuRelationTypesByCluId(cluId, relatedCluId);
	}

	@Override
	public List<CluInfo> getClusByRelation(String relatedCluId,
			String luLuRelationTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		checkForMissingParameter(relatedCluId, "relatedCluId");
		checkForMissingParameter(luLuRelationTypeKey, "luLuRelationTypeKey");

		List<Clu> clus = luDao.getClusByRelation(relatedCluId,
				luLuRelationTypeKey);
		List<CluInfo> result = LuServiceAssembler.toCluInfos(clus);
		return result;

	}

	@Override
	public List<String> getCluIdsByRelation(String relatedCluId,
			String luLuRelationTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		checkForMissingParameter(relatedCluId, "relatedCluId");
		checkForMissingParameter(luLuRelationTypeKey, "luLuRelationTypeKey");

		List<Clu> clus = luDao.getClusByRelation(relatedCluId,
				luLuRelationTypeKey);
		List<String> ids = new ArrayList<String>(clus.size());
		for (Clu clu : clus) {
			ids.add(clu.getId());
		}
		return ids;
	}

	@Override
	public List<CluInfo> getRelatedClusByCluId(String cluId,
			String luLuRelationTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		checkForMissingParameter(cluId, "cluId");
		checkForMissingParameter(luLuRelationTypeKey, "luLuRelationTypeKey");
		List<Clu> relatedClus = luDao.getRelatedClusByCluId(cluId,
				luLuRelationTypeKey);
		return LuServiceAssembler.toCluInfos(relatedClus);
	}

	@Override
	public List<String> getRelatedCluIdsByCluId(String cluId,
			String luLuRelationTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		checkForMissingParameter(cluId, "cluId");
		checkForMissingParameter(luLuRelationTypeKey, "luLuRelationTypeKey");
		List<String> relatedCluIds = luDao.getRelatedCluIdsByCluId(cluId,
				luLuRelationTypeKey);
		return relatedCluIds;
	}

	@Override
	public CluCluRelationInfo getCluCluRelation(String cluCluRelationId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		checkForMissingParameter(cluCluRelationId, "cluCluRelationId");
		return LuServiceAssembler.toCluCluRelationInfo(luDao.fetch(
				CluCluRelation.class, cluCluRelationId));
	}

	@Override
	public List<CluCluRelationInfo> getCluCluRelationsByClu(String cluId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		checkForMissingParameter(cluId, "cluId");
		List<CluCluRelation> cluCluRelations = luDao
				.getCluCluRelationsByClu(cluId);
		return LuServiceAssembler.toCluCluRelationInfos(cluCluRelations);
	}

	// **** Publication

	// TODO: Add meat to all publication methods
	@Override
	public List<CluPublicationInfo> getCluPublicationsByCluId(String cluId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CluPublicationInfo> getCluPublicationsByType(
			String luPublicationTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CluPublicationInfo getCluPublication(String cluPublicationId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	// **** Results

	@Override
	public CluResultInfo getCluResult(String cluResultId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {

		checkForMissingParameter(cluResultId, "cluResultId");

		CluResult cluResult = luDao.fetch(CluResult.class, cluResultId);
		return LuServiceAssembler.toCluResultInfo(cluResult);
	}

	@Override
	public List<CluResultInfo> getCluResultByClu(String cluId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {

		checkForMissingParameter(cluId, "cluId");

		return LuServiceAssembler.toCluResultInfos(luDao
				.getCluResultByClu(cluId));
	}

	@Override
	public List<String> getCluIdsByResultUsageType(String resultUsageTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return luDao.getCluIdsByResultUsageType(resultUsageTypeKey);
	}

	@Override
	public List<String> getCluIdsByResultComponent(String resultComponentId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return luDao.getCluIdsByResultComponentId(resultComponentId);
	}

	// **** Learning Objectives

	@Override
	public CluLoRelationInfo getCluLoRelation(String cluLoRelationId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {

		checkForMissingParameter(cluLoRelationId, "cluLoRelationId");

		CluLoRelation reltn = luDao.fetch(CluLoRelation.class, cluLoRelationId);
		return LuServiceAssembler.toCluLoRelationInfo(reltn);

	}

	@Override
	public List<CluLoRelationInfo> getCluLoRelationsByClu(String cluId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {

		checkForMissingParameter(cluId, "cluId");
		List<CluLoRelation> cluLoRelations = luDao
				.getCluLoRelationsByClu(cluId);
		return LuServiceAssembler.toCluLoRelationInfos(cluLoRelations);

	}

	@Override
	public List<CluLoRelationInfo> getCluLoRelationsByLo(String loId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		checkForMissingParameter(loId, "loId");
		List<CluLoRelation> cluLoRelations = luDao.getCluLoRelationsByLo(loId);
		return LuServiceAssembler.toCluLoRelationInfos(cluLoRelations);
	}

	// *** Resources

	@Override
	public List<String> getResourceRequirementsForCluId(String cluId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	// *** Sets

	@Override
	public CluSetInfo getCluSetInfo(String cluSetId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		checkForMissingParameter(cluSetId, "cluSetId");
		CluSet cluSet = luDao.fetch(CluSet.class, cluSetId);
		CluSetInfo cluSetInfo = LuServiceAssembler.toCluSetInfo(cluSet);
		setMembershipQuerySearchResult(cluSetInfo);
		return cluSetInfo;
	}

	@Override
	public List<CluSetInfo> getCluSetInfoByIdList(List<String> cluSetIdList)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		checkForMissingParameter(cluSetIdList, "cluSetIdList");
		checkForEmptyList(cluSetIdList, "cluSetIdList");
		List<CluSet> cluSets = luDao.getCluSetInfoByIdList(cluSetIdList);
		return LuServiceAssembler.toCluSetInfos(cluSets);
	}

	@Override
	public List<String> getCluSetIdsFromCluSet(String cluSetId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		checkForMissingParameter(cluSetId, "cluSetId");
		CluSet cluSet = luDao.fetch(CluSet.class, cluSetId);
		List<String> ids = new ArrayList<String>(cluSet.getClus().size());
		for (CluSet cluSet2 : cluSet.getCluSets()) {
			ids.add(cluSet2.getId());
		}
		return ids;
	}

	@Override
	public List<CluInfo> getClusFromCluSet(String cluSetId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		checkForMissingParameter(cluSetId, "cluSetId");
		CluSet cluSet = luDao.fetch(CluSet.class, cluSetId);
		List<CluInfo> clus = new ArrayList<CluInfo>(cluSet.getClus().size());
		for (Clu clu : cluSet.getClus()) {
			clus.add(LuServiceAssembler.toCluInfo(clu));
		}
		return clus;
	}

	@Override
	public List<String> getCluIdsFromCluSet(String cluSetId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		checkForMissingParameter(cluSetId, "cluSetId");
		CluSet cluSet = luDao.fetch(CluSet.class, cluSetId);
		List<String> ids = new ArrayList<String>(cluSet.getClus().size());
		for (Clu clu : cluSet.getClus()) {
			ids.add(clu.getId());
		}
		return ids;
	}

	@Override
	public List<CluInfo> getAllClusInCluSet(String cluSetId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		checkForMissingParameter(cluSetId, "cluSetId");
		List<Clu> clus = new ArrayList<Clu>();
		CluSet cluSet = luDao.fetch(CluSet.class, cluSetId);
		findClusInCluSet(clus, cluSet);
		List<CluInfo> infos = new ArrayList<CluInfo>(clus.size());
		for (Clu clu : clus) {
			infos.add(LuServiceAssembler.toCluInfo(clu));
		}
		return infos;
	}

	@Override
	public List<String> getAllCluIdsInCluSet(String cluSetId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		checkForMissingParameter(cluSetId, "cluSetId");
		List<Clu> clus = new ArrayList<Clu>();
		CluSet cluSet = luDao.fetch(CluSet.class, cluSetId);
		findClusInCluSet(clus, cluSet);
		List<String> ids = new ArrayList<String>(clus.size());
		for (Clu clu : clus) {
			ids.add(clu.getId());
		}
		return ids;
	}

	@Override
	public Boolean isCluInCluSet(String cluId, String cluSetId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		checkForMissingParameter(cluId, "cluId");
		checkForMissingParameter(cluSetId, "cluSetId");
		return luDao.isCluInCluSet(cluId, cluSetId);
	}

	// ******** LUI OPERATIONS
	// *** Core

	@Override
	public LuiInfo getLui(String luiId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {

		checkForMissingParameter(luiId, "luiId");

		Lui lui = luDao.fetch(Lui.class, luiId);
		return LuServiceAssembler.toLuiInfo(lui);
	}

	@Override
	public List<LuiInfo> getLuisByIdList(List<String> luiIdList)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		checkForMissingParameter(luiIdList, "luiIdList");
		checkForEmptyList(luiIdList, "luiIdList");
		List<Lui> luis = luDao.getLuisByIdList(luiIdList);
		return LuServiceAssembler.toLuiInfos(luis);
	}

	@Override
	public List<LuiInfo> getLuisInAtpByCluId(String cluId, String atpKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getLuiIdsByCluId(String cluId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {

		checkForMissingParameter(cluId, "cluId");

		return luDao.getLuiIdsByCluId(cluId);
	}

	@Override
	public List<String> getLuiIdsInAtpByCluId(String cluId, String atpKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {

		checkForMissingParameter(cluId, "cluId");
		checkForMissingParameter(atpKey, "atpKey");
		return luDao.getLuiIdsInAtpByCluId(cluId, atpKey);
	}

	// *** Relations

	@Override
	public List<String> getAllowedLuLuRelationTypesByLuiId(String luiId,
			String relatedLuiId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {

		checkForMissingParameter(luiId, "luiId");
		checkForMissingParameter(relatedLuiId, "relatedLuiId");

		return luDao.getAllowedLuLuRelationTypesByLuiId(luiId, relatedLuiId);
	}

	@Override
	public List<LuiInfo> getLuisByRelation(String luiId,
			String luLuRelationTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		checkForMissingParameter(luiId, "luiId");
		checkForMissingParameter(luLuRelationTypeKey, "luLuRelationTypeKey");

		return LuServiceAssembler.toLuiInfos(luDao.getLuisByRelationType(luiId,
				luLuRelationTypeKey));
	}

	@Override
	public List<String> getLuiIdsByRelation(String luiId,
			String luLuRelationTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		checkForMissingParameter(luiId, "luiId");
		checkForMissingParameter(luLuRelationTypeKey, "luLuRelationTypeKey");

		return luDao.getLuiIdsByRelationType(luiId, luLuRelationTypeKey);
	}

	@Override
	public List<LuiInfo> getRelatedLuisByLuiId(String luiId,
			String luLuRelationTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		checkForMissingParameter(luiId, "luiId");
		checkForMissingParameter(luLuRelationTypeKey, "luLuRelationTypeKey");
		List<Lui> relatedLuis = luDao.getRelatedLuisByLuiId(luiId,
				luLuRelationTypeKey);
		return LuServiceAssembler.toLuiInfos(relatedLuis);
	}

	@Override
	public List<String> getRelatedLuiIdsByLuiId(String luiId,
			String luLuRelationTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		checkForMissingParameter(luiId, "luiId");
		checkForMissingParameter(luLuRelationTypeKey, "luLuRelationTypeKey");
		List<String> relatedLuiIds = luDao.getRelatedLuiIdsByLuiId(luiId,
				luLuRelationTypeKey);
		return relatedLuiIds;
	}

	@Override
	public LuiLuiRelationInfo getLuiLuiRelation(String luiLuiRelationId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		checkForMissingParameter(luiLuiRelationId, "luiLuiRelationId");
		LuiLuiRelation luiLuiRelation = luDao.fetch(LuiLuiRelation.class,
				luiLuiRelationId);
		return LuServiceAssembler.toLuiLuiRelationInfo(luiLuiRelation);
	}

	@Override
	public List<LuiLuiRelationInfo> getLuiLuiRelationsByLui(String luiId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		checkForMissingParameter(luiId, "luiId");
		List<LuiLuiRelation> entities = luDao.getLuiLuiRelations(luiId);
		return LuServiceAssembler.toLuiLuiRelationInfos(entities);
	}

	/**************************************************************************
	 * MAINTENANCE OPERATIONS *
	 **************************************************************************/

	@Override
	public List<ValidationResultInfo> validateClu(String validationType,
			CluInfo cluInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		checkForMissingParameter(validationType, "validationType");
		checkForMissingParameter(cluInfo, "cluInfo");

		return validator.validateTypeStateObject(cluInfo, getObjectStructure("org.kuali.student.lum.lu.dto.CluInfo"));
	}

	@Override
	public CluInfo createClu(String luTypeKey, CluInfo cluInfo)
			throws AlreadyExistsException, DataValidationErrorException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		checkForMissingParameter(luTypeKey, "luTypeKey");
		checkForMissingParameter(cluInfo, "cluInfo");

		// Validate CLU
		List<ValidationResultInfo> val = validateClu("SYSTEM", cluInfo);
		if(null != val && val.size() > 0) {
			throw new DataValidationErrorException("Validation error!");
		}

		Clu clu = new Clu();

		LuType luType = luDao.fetch(LuType.class, luTypeKey);
		clu.setLuType(luType);

		if (cluInfo.getOfficialIdentifier() != null) {
			CluIdentifier officialIdentifier = new CluIdentifier();
			BeanUtils.copyProperties(cluInfo.getOfficialIdentifier(),
					officialIdentifier, new String[] { "code" });

			// FIXME: This will be in orchestration somewhere but
			// for now put it here
			officialIdentifier
					.setCode(new StringBuilder().append(
							cluInfo.getOfficialIdentifier().getDivision())
							.append(
									cluInfo.getOfficialIdentifier()
											.getSuffixCode()).toString());

			clu.setOfficialIdentifier(officialIdentifier);
		}

		if (clu.getAlternateIdentifiers() == null) {
			clu.setAlternateIdentifiers(new ArrayList<CluIdentifier>(0));
		}
		List<CluIdentifier> alternateIdentifiers = clu
				.getAlternateIdentifiers();

		for (CluIdentifierInfo cluIdInfo : cluInfo.getAlternateIdentifiers()) {
			CluIdentifier identifier = new CluIdentifier();
			BeanUtils.copyProperties(cluIdInfo, identifier,
					new String[] { "code" });

			// FIXME: This will be in orchestration somewhere but
			// for now put it here
			identifier.setCode(new StringBuilder().append(
					cluIdInfo.getDivision()).append(cluIdInfo.getSuffixCode())
					.toString());
			alternateIdentifiers.add(identifier);
		}

		if (cluInfo.getDescr() != null) {
		    LuRichText descr = LuServiceAssembler.toRichText(LuRichText.class, cluInfo.getDescr());
		    if (descr.getPlain() != null || descr.getFormatted() != null) {
		        clu.setDescr(descr);
		    }
		}

		if (cluInfo.getPrimaryAdminOrg() != null) {
			CluAdminOrg primaryAdminOrg = new CluAdminOrg();
			BeanUtils.copyProperties(cluInfo.getPrimaryAdminOrg(),
					primaryAdminOrg, new String[] { "attributes" });
			primaryAdminOrg.setAttributes(LuServiceAssembler
					.toGenericAttributes(CluAdminOrgAttribute.class, cluInfo
							.getPrimaryAdminOrg().getAttributes(),
							primaryAdminOrg, luDao));
			clu.setPrimaryAdminOrg(primaryAdminOrg);
		}

		if (clu.getAlternateAdminOrgs() == null) {
			clu.setAlternateAdminOrgs(new ArrayList<CluAdminOrg>(0));
		}
		List<CluAdminOrg> alternateOrgs = clu.getAlternateAdminOrgs();
		for (AdminOrgInfo orgInfo : cluInfo.getAlternateAdminOrgs()) {
			CluAdminOrg instructor = new CluAdminOrg();
			BeanUtils.copyProperties(orgInfo, instructor,
					new String[] { "attributes" });
			instructor.setAttributes(LuServiceAssembler.toGenericAttributes(
					CluAdminOrgAttribute.class, orgInfo.getAttributes(),
					instructor, luDao));
			alternateOrgs.add(instructor);
		}

		if (cluInfo.getPrimaryInstructor() != null) {
			CluInstructor primaryInstructor = new CluInstructor();
			BeanUtils.copyProperties(cluInfo.getPrimaryInstructor(),
					primaryInstructor, new String[] { "attributes" });
			primaryInstructor.setAttributes(LuServiceAssembler
					.toGenericAttributes(CluInstructorAttribute.class, cluInfo
							.getPrimaryInstructor().getAttributes(),
							primaryInstructor, luDao));
			clu.setPrimaryInstructor(primaryInstructor);
		}

		if (clu.getInstructors() == null) {
			clu.setInstructors(new ArrayList<CluInstructor>(0));
		}
		List<CluInstructor> instructors = clu.getInstructors();
		for (CluInstructorInfo instructorInfo : cluInfo.getInstructors()) {
			CluInstructor instructor = new CluInstructor();
			BeanUtils.copyProperties(instructorInfo, instructor,
					new String[] { "attributes" });
			instructor.setAttributes(LuServiceAssembler.toGenericAttributes(
					CluInstructorAttribute.class, instructorInfo
							.getAttributes(), instructor, luDao));
			instructors.add(instructor);
		}

		if (cluInfo.getStdDuration() != null) {
			clu.setStdDuration(LuServiceAssembler.toTimeAmount(cluInfo
					.getStdDuration()));
		}

		if (clu.getLuCodes() == null) {
			clu.setLuCodes(new ArrayList<LuCode>(0));
		}
		List<LuCode> luCodes = clu.getLuCodes();
		for (LuCodeInfo luCodeInfo : cluInfo.getLuCodes()) {
			LuCode luCode = new LuCode();
			luCode.setAttributes(LuServiceAssembler.toGenericAttributes(
					LuCodeAttribute.class, luCodeInfo.getAttributes(), luCode,
					luDao));
			BeanUtils.copyProperties(luCodeInfo, luCode, new String[] {
					"attributes", "metaInfo" });
			luCode.setDescr(luCodeInfo.getDescr());
			luCode.setClu(clu);
			luCodes.add(luCode);
		}

		if (clu.getOfferedAtpTypes() == null) {
			clu.setOfferedAtpTypes(new ArrayList<CluAtpTypeKey>(0));
		}
		List<CluAtpTypeKey> offeredAtpTypes = clu.getOfferedAtpTypes();
		for (String atpTypeKey : cluInfo.getOfferedAtpTypes()) {
			CluAtpTypeKey cluAtpTypeKey = new CluAtpTypeKey();
			cluAtpTypeKey.setAtpTypeKey(atpTypeKey);
			cluAtpTypeKey.setClu(clu);
			offeredAtpTypes.add(cluAtpTypeKey);
		}

		// FEE INFO
		if (cluInfo.getFeeInfo() != null) {
			CluFee cluFee = null;
			try {
				cluFee = LuServiceAssembler.toCluFee(false, cluInfo
						.getFeeInfo(), luDao);
			} catch (VersionMismatchException e) {
				// Version Mismatch Should Happen only for updates
			}
			clu.setFee(cluFee);
		}

		if (cluInfo.getAccountingInfo() != null) {
			CluAccounting cluAccounting = new CluAccounting();
			cluAccounting.setAttributes(LuServiceAssembler.toGenericAttributes(
					CluAccountingAttribute.class, cluInfo.getAccountingInfo()
							.getAttributes(), cluAccounting, luDao));
			cluAccounting.setAffiliatedOrgs(LuServiceAssembler
					.toAffiliatedOrgs(false, cluAccounting.getAffiliatedOrgs(),
							cluInfo.getAccountingInfo().getAffiliatedOrgs(),
							luDao));
			clu.setAccounting(cluAccounting);
		}

		clu.setAttributes(LuServiceAssembler.toGenericAttributes(
				CluAttribute.class, cluInfo.getAttributes(), clu, luDao));

		if (clu.getAcademicSubjectOrgs() == null) {
			clu.setAcademicSubjectOrgs(new ArrayList<CluAcademicSubjectOrg>());
		}
		List<CluAcademicSubjectOrg> subjectOrgs = clu.getAcademicSubjectOrgs();
		for (AcademicSubjectOrgInfo org : cluInfo.getAcademicSubjectOrgs()) {
            if (org.getOrgId() != null && !org.getOrgId().isEmpty()) {
                CluAcademicSubjectOrg subjOrg = new CluAcademicSubjectOrg();
                subjOrg.setOrgId(org.getOrgId());
                subjOrg.setClu(clu);
                subjectOrgs.add(subjOrg);
            }
		}

		if (cluInfo.getIntensity() != null) {
			clu.setIntensity(LuServiceAssembler
					.toAmount(cluInfo.getIntensity()));
		}

		if (clu.getCampusLocations() == null) {
			clu.setCampusLocations(new ArrayList<CluCampusLocation>(0));
		}
		List<CluCampusLocation> locations = clu.getCampusLocations();
		for (String locationName : cluInfo.getCampusLocations()) {
			CluCampusLocation location = new CluCampusLocation();
			location.setCampusLocation(locationName);
			location.setClu(clu);
			locations.add(location);
		}

		if (clu.getAccreditations() == null) {
			clu.setAccreditations(new ArrayList<CluAccreditation>(0));
		}
		List<CluAccreditation> accreditations = clu.getAccreditations();
		for (AccreditationInfo accreditationInfo : cluInfo.getAccreditations()) {
			CluAccreditation accreditation = new CluAccreditation();
			BeanUtils.copyProperties(accreditationInfo, accreditation,
					new String[] { "attributes" });
			accreditation.setAttributes(LuServiceAssembler.toGenericAttributes(
					CluAccreditationAttribute.class, accreditationInfo
							.getAttributes(), accreditation, luDao));
			accreditations.add(accreditation);
		}

		// Now copy all not standard properties
		BeanUtils.copyProperties(cluInfo, clu, new String[] { "luType",
				"officialIdentifier", "alternateIdentifiers", "descr",
				"luCodes", "primaryInstructor", "instructors", "stdDuration",
				"offeredAtpTypes", "feeInfo", "accountingInfo", "attributes",
				"metaInfo", "academicSubjectOrgs", "intensity",
				"campusLocations", "accreditations", "primaryAdminOrg",
				"alternateAdminOrgs" });

		luDao.create(clu);

		return LuServiceAssembler.toCluInfo(clu);
	}

	@Override
	public CluInfo updateClu(String cluId, CluInfo cluInfo)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {

		checkForMissingParameter(cluId, "cluId");
		checkForMissingParameter(cluInfo, "cluInfo");

		// Validate CLU
		List<ValidationResultInfo> val = validateClu("SYSTEM", cluInfo);
		if(null != val && val.size() > 0) {
			throw new DataValidationErrorException("Validation error!");
		}

		Clu clu = luDao.fetch(Clu.class, cluId);

		if (!String.valueOf(clu.getVersionInd()).equals(
				cluInfo.getMetaInfo().getVersionInd())) {
			throw new VersionMismatchException(
					"Clu to be updated is not the current version");
		}

		LuType luType = luDao.fetch(LuType.class, cluInfo.getType());
		clu.setLuType(luType);

		if (cluInfo.getOfficialIdentifier() != null) {
			if (clu.getOfficialIdentifier() == null) {
				clu.setOfficialIdentifier(new CluIdentifier());
			}
			BeanUtils.copyProperties(cluInfo.getOfficialIdentifier(), clu
					.getOfficialIdentifier(), new String[] { "id", "code" });

			// FIXME: This will be in orchestration somewhere but
			// for now put it here
			clu.getOfficialIdentifier().setCode(
					new StringBuilder().append(
							cluInfo.getOfficialIdentifier().getDivision())
							.append(
									cluInfo.getOfficialIdentifier()
											.getSuffixCode()).toString());

		} else if (clu.getOfficialIdentifier() != null) {
			luDao.delete(clu.getOfficialIdentifier());
		}

		// Update the list of Alternate Identifiers
		// Get a map of Id->object of all the currently persisted objects in the
		// list
		Map<String, CluIdentifier> oldAltIdMap = new HashMap<String, CluIdentifier>();
		for (CluIdentifier altIdentifier : clu.getAlternateIdentifiers()) {
			oldAltIdMap.put(altIdentifier.getId(), altIdentifier);
		}
		clu.getAlternateIdentifiers().clear();

		// Loop through the new list, if the item exists already update and
		// remove from the list
		// otherwise create a new entry
		for (CluIdentifierInfo cluIdInfo : cluInfo.getAlternateIdentifiers()) {
			CluIdentifier identifier = oldAltIdMap.remove(cluIdInfo.getId());
			if (identifier == null) {
				identifier = new CluIdentifier();
			}
			// Do Copy
			BeanUtils.copyProperties(cluIdInfo, identifier,
					new String[] { "code" });
			// FIXME: This will be in orchestration somewhere but
			// for now put it here
			identifier.setCode(new StringBuilder().append(
					cluIdInfo.getDivision()).append(cluIdInfo.getSuffixCode())
					.toString());
			clu.getAlternateIdentifiers().add(identifier);
		}

		// Now delete anything left over
		for (Entry<String, CluIdentifier> entry : oldAltIdMap.entrySet()) {
			luDao.delete(entry.getValue());
		}

		if (cluInfo.getDescr() != null && (cluInfo.getDescr().getPlain() != null || cluInfo.getDescr().getFormatted() != null)) {
			if (clu.getDescr() == null) {
				clu.setDescr(new LuRichText());
			}
			BeanUtils.copyProperties(cluInfo.getDescr(), clu.getDescr());
		} else if (clu.getDescr() != null) {
			luDao.delete(clu.getDescr());
		}

		if (cluInfo.getPrimaryInstructor() != null) {
			if (clu.getPrimaryInstructor() == null) {
				clu.setPrimaryInstructor(new CluInstructor());
			}
			BeanUtils.copyProperties(cluInfo.getPrimaryInstructor(), clu
					.getPrimaryInstructor(), new String[] { "attributes" });
			clu.getPrimaryInstructor().setAttributes(
					LuServiceAssembler.toGenericAttributes(
							CluInstructorAttribute.class, cluInfo
									.getPrimaryInstructor().getAttributes(),
							clu.getPrimaryInstructor(), luDao));
		} else if (clu.getPrimaryInstructor() != null) {
			luDao.delete(clu.getPrimaryInstructor());
		}

		// Update the List of instructors
		// Get a map of Id->object of all the currently persisted objects in the
		// list
		Map<String, CluInstructor> oldInstructorMap = new HashMap<String, CluInstructor>();
		for (CluInstructor cluInstructor : clu.getInstructors()) {
			oldInstructorMap.put(cluInstructor.getOrgId() + "_"
					+ cluInstructor.getPersonId(), cluInstructor);
		}
		clu.getInstructors().clear();

		// Loop through the new list, if the item exists already update and
		// remove from the list
		// otherwise create a new entry
		for (CluInstructorInfo instructorInfo : cluInfo.getInstructors()) {
			CluInstructor cluInstructor = oldInstructorMap
					.remove(instructorInfo.getOrgId() + "_"
							+ instructorInfo.getPersonId());
			if (cluInstructor == null) {
				cluInstructor = new CluInstructor();
			}
			// Do Copy
			BeanUtils.copyProperties(instructorInfo, cluInstructor,
					new String[] { "attributes" });
			cluInstructor.setAttributes(LuServiceAssembler.toGenericAttributes(
					CluInstructorAttribute.class, instructorInfo
							.getAttributes(), cluInstructor, luDao));
			clu.getInstructors().add(cluInstructor);
		}

		// Now delete anything left over
		for (Entry<String, CluInstructor> entry : oldInstructorMap.entrySet()) {
			luDao.delete(entry.getValue());
		}

		if (cluInfo.getStdDuration() != null) {
			if (clu.getStdDuration() == null) {
				clu.setStdDuration(new TimeAmount());
			}
			BeanUtils.copyProperties(cluInfo.getStdDuration(), clu
					.getStdDuration());
		} else if (clu.getStdDuration() != null) {
			luDao.delete(clu.getStdDuration());
		}

		// Update the LuCodes
		// Get a map of Id->object of all the currently persisted objects in the
		// list
		Map<String, LuCode> oldLuCodeMap = new HashMap<String, LuCode>();
		for (LuCode luCode : clu.getLuCodes()) {
			oldLuCodeMap.put(luCode.getId(), luCode);
		}
		clu.getLuCodes().clear();

		// Loop through the new list, if the item exists already update and
		// remove from the list
		// otherwise create a new entry
		for (LuCodeInfo luCodeInfo : cluInfo.getLuCodes()) {
			LuCode luCode = oldLuCodeMap.remove(luCodeInfo.getId());
			if (luCode == null) {
				luCode = new LuCode();
			} else {
				if (!String.valueOf(luCode.getVersionInd()).equals(
						luCodeInfo.getMetaInfo().getVersionInd())) {
					throw new VersionMismatchException(
							"LuCode to be updated is not the current version");
				}
			}
			// Do Copy
			luCode.setAttributes(LuServiceAssembler.toGenericAttributes(
					LuCodeAttribute.class, luCodeInfo.getAttributes(), luCode,
					luDao));
			BeanUtils.copyProperties(luCodeInfo, luCode, new String[] {
					"attributes", "metaInfo" });
			luCode.setDescr(luCodeInfo.getDescr());
			luCode.setClu(clu);
			clu.getLuCodes().add(luCode);
		}

		// Now delete anything left over
		for (Entry<String, LuCode> entry : oldLuCodeMap.entrySet()) {
			luDao.delete(entry.getValue());
		}

		// Update the list of AtpTypeKeys
		// Get a map of Id->object of all the currently persisted objects in the
		// list
		Map<String, CluAtpTypeKey> oldOfferedAtpTypesMap = new HashMap<String, CluAtpTypeKey>();
		for (CluAtpTypeKey cluAtpTypeKey : clu.getOfferedAtpTypes()) {
			oldOfferedAtpTypesMap.put(cluAtpTypeKey.getAtpTypeKey(),
					cluAtpTypeKey);
		}
		clu.getOfferedAtpTypes().clear();

		// Loop through the new list, if the item exists already update and
		// remove from the list
		// otherwise create a new entry
		for (String atpTypeKey : cluInfo.getOfferedAtpTypes()) {
			CluAtpTypeKey cluAtpTypeKey = oldOfferedAtpTypesMap
					.remove(atpTypeKey);
			if (cluAtpTypeKey == null) {
				cluAtpTypeKey = new CluAtpTypeKey();
			}
			// Do Copy
			cluAtpTypeKey.setAtpTypeKey(atpTypeKey);
			cluAtpTypeKey.setClu(clu);
			clu.getOfferedAtpTypes().add(cluAtpTypeKey);
		}

		// Now delete anything left over
		for (Entry<String, CluAtpTypeKey> entry : oldOfferedAtpTypesMap
				.entrySet()) {
			luDao.delete(entry.getValue());
		}

		if (cluInfo.getFeeInfo() != null) {
			if (clu.getFee() == null) {
				clu.setFee(LuServiceAssembler.toCluFee(false, cluInfo
						.getFeeInfo(), luDao));
			} else {
				clu.setFee(LuServiceAssembler.toCluFee(true, cluInfo
						.getFeeInfo(), luDao));
			}
		} else if (clu.getFee() != null) {
			luDao.delete(clu.getFee());
		}

		if (cluInfo.getAccountingInfo() != null) {
			if (clu.getAccounting() == null) {
				clu.setAccounting(new CluAccounting());
			}
			clu.getAccounting().setAttributes(
					LuServiceAssembler.toGenericAttributes(
							CluAccountingAttribute.class, cluInfo
									.getAccountingInfo().getAttributes(), clu
									.getAccounting(), luDao));
			clu.getAccounting().setAffiliatedOrgs(LuServiceAssembler
					.toAffiliatedOrgs(true, clu.getAccounting().getAffiliatedOrgs(),
							cluInfo.getAccountingInfo().getAffiliatedOrgs(),
							luDao));

		} else if (clu.getAccounting() != null) {
			luDao.delete(clu.getAccounting());
		}

		clu.setAttributes(LuServiceAssembler.toGenericAttributes(
				CluAttribute.class, cluInfo.getAttributes(), clu, luDao));

		if (cluInfo.getIntensity() != null) {
			if (clu.getIntensity() == null) {
				clu.setIntensity(new Amount());
			}
			BeanUtils
					.copyProperties(cluInfo.getIntensity(), clu.getIntensity());
		} else if (clu.getIntensity() != null) {
			luDao.delete(clu.getIntensity());
		}

		// Update the list of academicSubjectOrgs
		// Get a map of Id->object of all the currently persisted objects in the
		// list
		Map<String, CluAcademicSubjectOrg> oldOrgMap = new HashMap<String, CluAcademicSubjectOrg>();
		for (CluAcademicSubjectOrg subjOrg : clu.getAcademicSubjectOrgs()) {
			oldOrgMap.put(subjOrg.getOrgId(), subjOrg);
		}
		clu.getAcademicSubjectOrgs().clear();

		// Loop through the new list, if the item exists already update and
		// remove from the list
		// otherwise create a new entry
		for (AcademicSubjectOrgInfo org : cluInfo.getAcademicSubjectOrgs()) {
			CluAcademicSubjectOrg subjOrg = oldOrgMap.remove(org.getOrgId());
			if (subjOrg == null) {
				subjOrg = new CluAcademicSubjectOrg();
			}
			// Do Copy
			subjOrg.setOrgId(org.getOrgId());
			subjOrg.setClu(clu);
			clu.getAcademicSubjectOrgs().add(subjOrg);
		}

		// Now delete anything left over
		for (Entry<String, CluAcademicSubjectOrg> entry : oldOrgMap.entrySet()) {
			luDao.delete(entry.getValue());
		}

		// Update the list of campusLocations
		// Get a map of Id->object of all the currently persisted objects in the
		// list
		Map<String, CluCampusLocation> oldLocationsMap = new HashMap<String, CluCampusLocation>();
		for (CluCampusLocation campus : clu.getCampusLocations()) {
			oldLocationsMap.put(campus.getCampusLocation(), campus);
		}
		clu.getCampusLocations().clear();

		// Loop through the new list, if the item exists already update and
		// remove from the list
		// otherwise create a new entry
		for (String locationName : cluInfo.getCampusLocations()) {
			CluCampusLocation location = oldLocationsMap.remove(locationName);
			if (location == null) {
				location = new CluCampusLocation();
			}
			// Do Copy
			location.setCampusLocation(locationName);
			location.setClu(clu);
			clu.getCampusLocations().add(location);
		}

		// Now delete anything left over
		for (Entry<String, CluCampusLocation> entry : oldLocationsMap
				.entrySet()) {
			luDao.delete(entry.getValue());
		}

		// Update the List of accreditations
		// Get a map of Id->object of all the currently persisted objects in the
		// list
		Map<String, CluAccreditation> oldAccreditationMap = new HashMap<String, CluAccreditation>();
		for (CluAccreditation cluAccreditation : clu.getAccreditations()) {
			oldAccreditationMap.put(cluAccreditation.getOrgId(),
					cluAccreditation);
		}
		clu.getAccreditations().clear();

		// Loop through the new list, if the item exists already update and
		// remove from the list
		// otherwise create a new entry
		for (AccreditationInfo accreditationInfo : cluInfo.getAccreditations()) {
			CluAccreditation cluAccreditation = oldAccreditationMap
					.remove(accreditationInfo.getOrgId());
			if (cluAccreditation == null) {
				cluAccreditation = new CluAccreditation();
			}
			// Do Copy
			BeanUtils.copyProperties(accreditationInfo, cluAccreditation,
					new String[] { "attributes" });
			cluAccreditation.setAttributes(LuServiceAssembler
					.toGenericAttributes(CluAccreditationAttribute.class,
							accreditationInfo.getAttributes(),
							cluAccreditation, luDao));
			clu.getAccreditations().add(cluAccreditation);
		}

		// Now delete anything left over
		for (Entry<String, CluAccreditation> entry : oldAccreditationMap
				.entrySet()) {
			luDao.delete(entry.getValue());
		}

		// Update the primary admin org
		if (cluInfo.getPrimaryAdminOrg() != null) {
			if (clu.getPrimaryAdminOrg() == null) {
				clu.setPrimaryAdminOrg(new CluAdminOrg());
			}
			BeanUtils.copyProperties(cluInfo.getPrimaryAdminOrg(), clu
					.getPrimaryAdminOrg(), new String[] { "attributes" });
			clu.getPrimaryAdminOrg().setAttributes(
					LuServiceAssembler.toGenericAttributes(
							CluAdminOrgAttribute.class, cluInfo
									.getPrimaryAdminOrg().getAttributes(), clu
									.getPrimaryAdminOrg(), luDao));
		} else if (clu.getPrimaryAdminOrg() != null) {
			luDao.delete(clu.getPrimaryAdminOrg());
		}

		// Update the List of alternate admin orgs
		// Get a map of Id->object of all the currently persisted objects in the
		// list
		Map<String, CluAdminOrg> oldAdminOrgsMap = new HashMap<String, CluAdminOrg>();
		for (CluAdminOrg cluOrg : clu.getAlternateAdminOrgs()) {
			oldAdminOrgsMap.put(cluOrg.getOrgId(), cluOrg);
		}
		clu.getAlternateAdminOrgs().clear();

		// Loop through the new list, if the item exists already update and
		// remove from the list
		// otherwise create a new entry
		for (AdminOrgInfo orgInfo : cluInfo.getAlternateAdminOrgs()) {
			CluAdminOrg cluOrg = oldAdminOrgsMap.remove(orgInfo.getOrgId());
			if (cluOrg == null) {
				cluOrg = new CluAdminOrg();
			}
			// Do Copy
			BeanUtils.copyProperties(orgInfo, cluOrg,
					new String[] { "attributes" });
			cluOrg.setAttributes(LuServiceAssembler.toGenericAttributes(
					CluAdminOrgAttribute.class, orgInfo.getAttributes(),
					cluOrg, luDao));
			clu.getAlternateAdminOrgs().add(cluOrg);
		}

		// Now delete anything left over
		for (Entry<String, CluAdminOrg> entry : oldAdminOrgsMap.entrySet()) {
			luDao.delete(entry.getValue());
		}

		// Now copy all not standard properties
		BeanUtils.copyProperties(cluInfo, clu, new String[] { "luType",
				"officialIdentifier", "alternateIdentifiers", "descr",
				"luCodes", "primaryInstructor", "instructors", "stdDuration",
				"offeredAtpTypes", "feeInfo", "accountingInfo", "attributes",
				"metaInfo", "academicSubjectOrgs", "intensity",
				"campusLocations", "accreditations", "primaryAdminOrg",
				"alternateAdminOrgs" });
		Clu updated = null;
		try {
			updated = luDao.update(clu);
		} catch (Exception e) {
			logger.error("Exception occured: ", e);
		}
		return LuServiceAssembler.toCluInfo(updated);
	}

	@Override
	public StatusInfo deleteClu(String cluId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			DependentObjectsExistException, OperationFailedException,
			PermissionDeniedException {
		checkForMissingParameter(cluId, "cluId");

		luDao.delete(Clu.class, cluId);

		StatusInfo statusInfo = new StatusInfo();
		statusInfo.setSuccess(true);

		return statusInfo;
	}

	@Override
	public CluInfo updateCluState(String cluId, String luState)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// Check Missing params
		checkForMissingParameter(cluId, "cluId");
		checkForMissingParameter(luState, "luState");
		Clu clu = luDao.fetch(Clu.class, cluId);
		clu.setState(luState);
		Clu updated = luDao.update(clu);
		return LuServiceAssembler.toCluInfo(updated);
	}

	@Override
	public List<ValidationResultInfo> validateCluCluRelation(
			String validationType, CluCluRelationInfo cluCluRelationInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		checkForMissingParameter(validationType, "validationType");
		checkForMissingParameter(cluCluRelationInfo, "cluCluRelationInfo");

		return validator.validateTypeStateObject(cluCluRelationInfo, getObjectStructure("org.kuali.student.lum.lu.dto.CluCluRelationInfo"));
	}

	@Override
	public CluCluRelationInfo createCluCluRelation(String cluId,
			String relatedCluId, String luLuRelationTypeKey,
			CluCluRelationInfo cluCluRelationInfo)
			throws AlreadyExistsException, DataValidationErrorException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, CircularRelationshipException {
		checkForMissingParameter(cluId, "cluId");
		checkForMissingParameter(relatedCluId, "relatedCluId");
		checkForMissingParameter(luLuRelationTypeKey, "luLuRelationTypeKey");
		checkForMissingParameter(cluCluRelationInfo, "cluCluRelationInfo");

		if (cluId.equals(relatedCluId)) {
			throw new CircularRelationshipException(
					"Can not relate a Clu to itself");
		}

		// Validate CluCluRelationInfo
		List<ValidationResultInfo> val = validateCluCluRelation("SYSTEM", cluCluRelationInfo);
		if(null != val && val.size() > 0) {
			throw new DataValidationErrorException("Validation error!");
		}


		Clu clu = luDao.fetch(Clu.class, cluId);
		Clu relatedClu = luDao.fetch(Clu.class, relatedCluId);

		CluCluRelation cluCluRelation = new CluCluRelation();
		BeanUtils.copyProperties(cluCluRelationInfo, cluCluRelation,
				new String[] { "cluId", "relatedCluId",
						"isCluRelationRequired", "attributes", "metaInfo" });

		cluCluRelation.setClu(clu);
		cluCluRelation.setRelatedClu(relatedClu);
		cluCluRelation.setCluRelationRequired(cluCluRelationInfo
				.getIsCluRelationRequired() == null ? true : cluCluRelationInfo
				.getIsCluRelationRequired()); // TODO maybe this is unnecessary,
		// contract specifies not null
		cluCluRelation.setAttributes(LuServiceAssembler.toGenericAttributes(
				CluCluRelationAttribute.class, cluCluRelationInfo
						.getAttributes(), cluCluRelation, luDao));

		LuLuRelationType luLuRelationType = luDao.fetch(LuLuRelationType.class,
				luLuRelationTypeKey);

		cluCluRelation.setLuLuRelationType(luLuRelationType);

		luDao.create(cluCluRelation);

		return LuServiceAssembler.toCluCluRelationInfo(cluCluRelation);
	}

	@Override
	public CluCluRelationInfo updateCluCluRelation(
			final String cluCluRelationId,
			final CluCluRelationInfo cluCluRelationInfo)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
		checkForMissingParameter(cluCluRelationId, "cluCluRelationId");
		checkForMissingParameter(cluCluRelationInfo, "cluCluRelationInfo");

		// Validate CluCluRelationInfo
		List<ValidationResultInfo> val = validateCluCluRelation("SYSTEM", cluCluRelationInfo);
		if(null != val && val.size() > 0) {
			throw new DataValidationErrorException("Validation error!");
		}

		final CluCluRelation cluCluRelation = luDao.fetch(CluCluRelation.class,
				cluCluRelationId);
		BeanUtils.copyProperties(cluCluRelationInfo, cluCluRelation,
				new String[] { "cluId", "relatedCluId",
						"isCluRelationRequired", "attributes", "metaInfo" });

		cluCluRelation.setClu(luDao.fetch(Clu.class, cluCluRelationInfo
				.getCluId()));
		cluCluRelation.setRelatedClu(luDao.fetch(Clu.class, cluCluRelationInfo
				.getRelatedCluId()));
		cluCluRelation.setCluRelationRequired(cluCluRelationInfo
				.getIsCluRelationRequired() == null ? true : cluCluRelationInfo
				.getIsCluRelationRequired()); // TODO maybe this is unnecessary,
		// contract specifies not null
		cluCluRelation.setAttributes(LuServiceAssembler.toGenericAttributes(
				CluCluRelationAttribute.class, cluCluRelationInfo
						.getAttributes(), cluCluRelation, luDao));

		cluCluRelation.setLuLuRelationType(luDao.fetch(LuLuRelationType.class,
				cluCluRelationInfo.getType()));

		final CluCluRelation update = luDao.update(cluCluRelation);

		return LuServiceAssembler.toCluCluRelationInfo(update);
	}

	@Override
	public StatusInfo deleteCluCluRelation(String cluCluRelationId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		checkForMissingParameter(cluCluRelationId, "cluCluRelationId");

		luDao.delete(CluCluRelation.class, cluCluRelationId);

		StatusInfo statusInfo = new StatusInfo();
		statusInfo.setSuccess(true);

		return statusInfo;
	}

	@Override
	public List<ValidationResultInfo> validateCluPublication(
			String validationType, CluPublicationInfo cluPublicationInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {

		checkForMissingParameter(validationType, "validationType");
		checkForMissingParameter(cluPublicationInfo, "cluPublicationInfo");

		return validator.validateTypeStateObject(cluPublicationInfo, getObjectStructure("cluPlublicationInfo"));
	}

	@Override
	public CluPublicationInfo createCluPublication(String cluId,
			String luPublicationType, CluPublicationInfo cluPublicationInfo)
			throws AlreadyExistsException, DataValidationErrorException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CluPublicationInfo updateCluPublication(String cluPublicationId,
			CluPublicationInfo cluPublicationInfo)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusInfo deleteCluPublication(String cluPublicationId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, DependentObjectsExistException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ValidationResultInfo> validateCluResult(String validationType,
			CluResultInfo cluResultInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		checkForMissingParameter(validationType, "validationType");
		checkForMissingParameter(cluResultInfo, "cluResultInfo");

		return validator.validateTypeStateObject(cluResultInfo, getObjectStructure("org.kuali.student.lum.lu.dto.CluResultInfo"));
	}

	@Override
	public CluResultInfo createCluResult(String cluId, String cluResultTypeKey,
			CluResultInfo cluResultInfo) throws AlreadyExistsException,
			DataValidationErrorException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, DoesNotExistException {

		checkForMissingParameter(cluId, "cluId");
		checkForMissingParameter(cluResultTypeKey, "cluResultTypeKey");
		checkForMissingParameter(cluResultInfo, "cluResultInfo");

		// Validate CluResult
		List<ValidationResultInfo> val = validateCluResult("SYSTEM", cluResultInfo);
		if(null != val && val.size() > 0) {
			throw new DataValidationErrorException("Validation error!");
		}
		
		cluResultInfo.setType(cluResultTypeKey);
		cluResultInfo.setCluId(cluId);

		List<ResultOption> resOptList = new ArrayList<ResultOption>();
		for (ResultOptionInfo resOptInfo : cluResultInfo.getResultOptions()) {
			ResultOption resOpt = new ResultOption();
			BeanUtils.copyProperties(resOptInfo, resOpt, new String[] { "id",
					"metaInfo", "resultUsageType", "desc" });

			ResultUsageType resUsageType = luDao.fetch(ResultUsageType.class,
					resOptInfo.getResultUsageTypeKey());
			resOpt.setResultUsageType(resUsageType);
			resOpt.setDesc(LuServiceAssembler.toRichText(LuRichText.class, resOptInfo.getDesc()));
			luDao.create(resOpt);
			resOptList.add(resOpt);
		}

		CluResult cluResult = new CluResult();
		BeanUtils.copyProperties(cluResultInfo, cluResult, new String[] { "id",
				"desc", "resultOptions", "metaInfo" });

		cluResult.setDesc(LuServiceAssembler
				.toRichText(LuRichText.class, cluResultInfo.getDesc()));
		cluResult.setResultOptions(resOptList);
		
		Clu clu = luDao.fetch(Clu.class, cluId);
		cluResult.setClu(clu);
		
		CluResultType type = luDao.fetch(CluResultType.class, cluResultTypeKey);
		cluResult.setCluResultType(type);

		luDao.create(cluResult);

		return LuServiceAssembler.toCluResultInfo(cluResult);
	}

	@Override
	public CluResultInfo updateCluResult(String cluResultId,
			CluResultInfo cluResultInfo) throws DataValidationErrorException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, VersionMismatchException {

		checkForMissingParameter(cluResultId, "cluResultId");
		checkForMissingParameter(cluResultInfo, "cluResultInfo");

		// Validate CluResult
		List<ValidationResultInfo> val = validateCluResult("SYSTEM", cluResultInfo);
		if(null != val && val.size() > 0) {
			throw new DataValidationErrorException("Validation error!");
		}

		CluResult result = luDao.fetch(CluResult.class, cluResultId);
		if (!String.valueOf(result.getVersionInd()).equals(
				cluResultInfo.getMetaInfo().getVersionInd())) {
			throw new VersionMismatchException(
					"CluResult to be updated is not the current version");
		}

		// Update the list of resultoptions
		// Get a map of Id->object of all the currently persisted objects in the
		// list
		Map<String, ResultOption> oldResultOptionMap = new HashMap<String, ResultOption>();
		for (ResultOption opt : result.getResultOptions()) {
			oldResultOptionMap.put(opt.getId(), opt);
		}
		result.getResultOptions().clear();

		// Loop through the new list, if the item exists already update and
		// remove from the list otherwise create a new entry
		for (ResultOptionInfo resOptInfo : cluResultInfo.getResultOptions()) {
			ResultOption opt = oldResultOptionMap.remove(resOptInfo.getId());
			if (opt == null) {
				opt = new ResultOption();
			}
			// Do Copy
			BeanUtils.copyProperties(resOptInfo, opt, new String[] {
					"resultUsageType", "desc" });

			ResultUsageType resUsageType = luDao.fetch(ResultUsageType.class,
					resOptInfo.getResultUsageTypeKey());
			opt.setResultUsageType(resUsageType);
			opt.setDesc(LuServiceAssembler.toRichText(LuRichText.class, resOptInfo.getDesc()));
			result.getResultOptions().add(opt);
		}

		// Now delete anything left over
		for (Entry<String, ResultOption> entry : oldResultOptionMap.entrySet()) {
			luDao.delete(entry.getValue());
		}

		BeanUtils.copyProperties(cluResultInfo, result, new String[] { "id",
				"desc", "resultOptions" });

		result.setDesc(LuServiceAssembler.toRichText(LuRichText.class, cluResultInfo.getDesc()));

		CluResult updated = luDao.update(result);

		return LuServiceAssembler.toCluResultInfo(updated);
	}

	@Override
	public StatusInfo deleteCluResult(String cluResultId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, DependentObjectsExistException,
			OperationFailedException, PermissionDeniedException {

		checkForMissingParameter(cluResultId, "cluResultId");

		luDao.delete(CluResult.class, cluResultId);

		StatusInfo statusInfo = new StatusInfo();
		statusInfo.setSuccess(true);

		return statusInfo;
	}

	@Override
	public List<ValidationResultInfo> validateCluLoRelation(
			String validationType, CluLoRelationInfo cluLoRelationInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {

		checkForMissingParameter(validationType, "validationType");
		checkForMissingParameter(cluLoRelationInfo, "cluLoRelationInfo");

		return validator.validateTypeStateObject(cluLoRelationInfo, getObjectStructure("org.kuali.student.lum.lu.dto.CluLoRelationInfo"));
	}

	@Override
	public CluLoRelationInfo createCluLoRelation(String cluId, String loId,
			String cluLoRelationType, CluLoRelationInfo cluLoRelationInfo)
			throws AlreadyExistsException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException, DataValidationErrorException {
		checkForMissingParameter(loId, "loId");
		checkForMissingParameter(cluId, "cluId");
		checkForEmptyList(cluLoRelationType, "cluLoRelationType");
		checkForEmptyList(cluLoRelationInfo, "cluLoRelationInfo");

		// Validate CluLoRelation
		List<ValidationResultInfo> val = validateCluLoRelation("SYSTEM", cluLoRelationInfo);
		if(null != val && val.size() > 0) {
			throw new DataValidationErrorException("Validation error!");
		}

		Clu clu = luDao.fetch(Clu.class, cluId);
		if (clu == null) {
			throw new DoesNotExistException("Clu does not exist for id: "
					+ cluId);
		}

		// Check to see if this relation already exists
		List<CluLoRelation> reltns = luDao.getCluLoRelationsByCludIdAndLoId(
				cluId, loId);
		if (reltns.size() > 0) {
			throw new AlreadyExistsException(
					"Relation already exists for cluId:" + cluId + " and Lo:"
							+ loId);
		}

		CluLoRelation cluLoRelation = new CluLoRelation();
		BeanUtils.copyProperties(cluLoRelationInfo, cluLoRelation,
				new String[] { "cluId", "attributes", "metaInfo" });

		cluLoRelation.setClu(clu);
		cluLoRelation.setAttributes(LuServiceAssembler.toGenericAttributes(
				CluLoRelationAttribute.class,
				cluLoRelationInfo.getAttributes(), cluLoRelation, luDao));

		luDao.create(cluLoRelation);

		return LuServiceAssembler.toCluLoRelationInfo(cluLoRelation);
	}

	@Override
	public CluLoRelationInfo updateCluLoRelation(String cluLoRelationId,
			CluLoRelationInfo cluLoRelationInfo)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
		checkForMissingParameter(cluLoRelationId, "cluLoRelationId");
		checkForMissingParameter(cluLoRelationInfo, "cluLoRelationInfo");

		// Validate CluLoRelation
		List<ValidationResultInfo> val = validateCluLoRelation("SYSTEM", cluLoRelationInfo);
		if(null != val && val.size() > 0) {
			throw new DataValidationErrorException("Validation error!");
		}

		CluLoRelation reltn = luDao.fetch(CluLoRelation.class, cluLoRelationId);

		if (!String.valueOf(reltn.getVersionInd()).equals(
				cluLoRelationInfo.getMetaInfo().getVersionInd())) {
			throw new VersionMismatchException(
					"CluLoRelation to be updated is not the current version");
		}

		Clu clu = luDao.fetch(Clu.class, cluLoRelationInfo.getCluId());
		if (clu == null) {
			throw new DoesNotExistException("Clu does not exist for id: "
					+ cluLoRelationInfo.getCluId());
		}

		BeanUtils.copyProperties(cluLoRelationInfo, reltn, new String[] {
				"cluId", "attributes", "metaInfo" });

		reltn.setClu(clu);
		reltn.setAttributes(LuServiceAssembler.toGenericAttributes(
				CluLoRelationAttribute.class,
				cluLoRelationInfo.getAttributes(), reltn, luDao));

		CluLoRelation updated = luDao.update(reltn);

		return LuServiceAssembler.toCluLoRelationInfo(updated);
	}

	@Override
	public StatusInfo deleteCluLoRelation(String cluLoRelationId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		checkForMissingParameter(cluLoRelationId, "cluLoRelationId");

		CluLoRelation reltn = luDao.fetch(CluLoRelation.class, cluLoRelationId);
		if (reltn == null) {
			throw new DoesNotExistException(
					"CluLoRelation does not exist for id: " + cluLoRelationId);
		}

		luDao.delete(CluLoRelation.class, cluLoRelationId);

		StatusInfo statusInfo = new StatusInfo();
		statusInfo.setSuccess(true);

		return statusInfo;
	}

	@Override
	public StatusInfo addCluResourceRequirement(String resourceTypeKey,
			String cluId) throws AlreadyExistsException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusInfo removeCluResourceRequirement(String resourceTypeKey,
			String cluId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ValidationResultInfo> validateCluSet(String validationType,
			CluSetInfo cluSetInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		checkForMissingParameter(validationType, "validationType");
		checkForMissingParameter(cluSetInfo, "cluSetInfo");

		return validator.validateTypeStateObject(cluSetInfo, getObjectStructure("org.kuali.student.lum.lu.dto.CluSetInfo"));
	}

	@Override
	public CluSetInfo createCluSet(String cluSetType, CluSetInfo cluSetInfo)
			throws AlreadyExistsException, DataValidationErrorException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			UnsupportedActionException {

		checkForMissingParameter(cluSetType, "cluSetType");
		checkForMissingParameter(cluSetInfo, "cluSetInfo");

		cluSetInfo.setType(cluSetType);

		validateCluSet(cluSetInfo);
		
		// Validate CluSet
		List<ValidationResultInfo> val;
		try {
			val = validateCluSet("SYSTEM", cluSetInfo);
		} catch (DoesNotExistException e) {
			throw new DataValidationErrorException("Validation error! " + e.getMessage());
		}
		if(null != val && val.size() > 0) {
			throw new DataValidationErrorException("Validation error!");
		}

		CluSet cluSet = null;
		try {
			cluSet = LuServiceAssembler.toCluSetEntity(cluSetInfo, this.luDao);
		} catch (DoesNotExistException e) {
			throw new DataValidationErrorException("Creating CluSet entity failed. Clu or CluSet does not exist: " + e.getMessage());
		}
		cluSet = luDao.create(cluSet);

		CluSetInfo newCluSetInfo = LuServiceAssembler.toCluSetInfo(cluSet);

		setMembershipQuerySearchResult(newCluSetInfo);

		return newCluSetInfo;
	}

	private void setMembershipQuerySearchResult(CluSetInfo cluSetInfo) throws MissingParameterException {
		if(cluSetInfo.getMembershipQuery() == null) {
			return;
		}
		List<String> cluIds = getMembershipQuerySearchResult(cluSetInfo.getMembershipQuery());
		cluSetInfo.getCluIds().addAll(cluIds);
	}

	private List<String> getMembershipQuerySearchResult(MembershipQueryInfo query) throws MissingParameterException {
		SearchRequest sr = new SearchRequest();
		sr.setSearchKey(query.getSearchTypeKey());
		sr.setParams(query.getQueryParamValueList());

		SearchResult result = search(sr);
		
		List<String> cluIds = new ArrayList<String>();
		List<SearchResultRow> rows = result.getRows();
		for(SearchResultRow row : rows) {
			List<SearchResultCell> cells = row.getCells();
			for(SearchResultCell cell : cells) {
				if(cell.getKey().equals("lu.resultColumn.cluId")) {
					cluIds.add(cell.getValue());
				}
			}
		}
		return cluIds;
	}
	
	private void validateCluSet(CluSetInfo cluSetInfo) throws UnsupportedActionException {
		MembershipQueryInfo mqInfo = cluSetInfo.getMembershipQuery();

		if (cluSetInfo.getType() == null) {
			throw new UnsupportedActionException("CluSet type cannot be null. CluSet id="+cluSetInfo.getId());
		}
		else if(mqInfo != null && mqInfo.getSearchTypeKey() != null && !mqInfo.getSearchTypeKey().isEmpty() && 
				(cluSetInfo.getCluIds().size() > 0 || cluSetInfo.getCluSetIds().size() > 0)) {
			throw new UnsupportedActionException("Dynamic CluSet cannot contain Clus or CluSets. CluSet id="+cluSetInfo.getId());
		}
		else if (cluSetInfo.getCluIds().size() > 0 && cluSetInfo.getCluSetIds().size() > 0) {
			throw new UnsupportedActionException("CluSet cannot contain both Clus and CluSets. CluSet id="+cluSetInfo.getId());
		}
	}
	
	@Override
	// TODO: Fix return CluSetInfo's meta version, version doesn't seem to 
	// increment until method getCluSetInfo is called
	public CluSetInfo updateCluSet(String cluSetId, CluSetInfo cluSetInfo)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException, CircularRelationshipException,
			UnsupportedActionException {
		// Check Missing params
		checkForMissingParameter(cluSetId, "cluSetId");
		checkForMissingParameter(cluSetInfo, "cluSetInfo");

		// Validate CluSet
		List<ValidationResultInfo> val = validateCluSet("SYSTEM", cluSetInfo);
		if(null != val && val.size() > 0) {
			throw new DataValidationErrorException("Validation error!");
		}

		cluSetInfo.setId(cluSetId);
		
		validateCluSet(cluSetInfo);
		
		CluSet cluSet = luDao.fetch(CluSet.class, cluSetId);

		if (!cluSetInfo.getType().equals(cluSet.getType())) {
			throw new UnsupportedActionException("CluSet type is set at creation time and cannot be updated. CluSet id="+cluSetId);
		}

		if (!String.valueOf(cluSet.getVersionInd()).equals(
				cluSetInfo.getMetaInfo().getVersionInd())) {
			throw new VersionMismatchException(
					"CluSet to be updated is not the current version. CluSet id="+cluSetId);
		}

		// update the cluIds
		if(!cluSetInfo.getCluIds().isEmpty()) {
			Set<String> newCluIds = new HashSet<String>(cluSetInfo.getCluIds());
			for (Iterator<Clu> i = cluSet.getClus().iterator(); i.hasNext();) {
				if (!newCluIds.remove(i.next().getId())) {
					i.remove();
				}
			}
			this.addClusToCluSet(new ArrayList<String>(newCluIds), cluSet.getId());
		}
		// update the cluSetIds
		else if(!cluSetInfo.getCluSetIds().isEmpty()) {
			Set<String> newCluSetIds = new HashSet<String>(cluSetInfo.getCluSetIds());
			for (Iterator<CluSet> i = cluSet.getCluSets().iterator(); i.hasNext();) {
				if (!newCluSetIds.remove(i.next().getId())) {
					i.remove();
				}
			}
			this.addCluSetsToCluSet(cluSet.getId(), new ArrayList<String>(newCluSetIds));
		}

		BeanUtils.copyProperties(cluSetInfo, cluSet, new String[] { "descr",
				"attributes", "metaInfo", "membershipQuery" });
		cluSet.setAttributes(LuServiceAssembler.toGenericAttributes(
				CluSetAttribute.class, cluSetInfo.getAttributes(), cluSet,
				luDao));
		cluSet.setDescr(LuServiceAssembler.toRichText(LuRichText.class, cluSetInfo.getDescr()));
		
		MembershipQuery mq = LuServiceAssembler.toMembershipQueryEntity(cluSetInfo.getMembershipQuery());
		cluSet.setMembershipQuery(mq);
		
		CluSet updated = luDao.update(cluSet);

		CluSetInfo updatedCluSetInfo = LuServiceAssembler.toCluSetInfo(updated);

		setMembershipQuerySearchResult(updatedCluSetInfo);

		return updatedCluSetInfo;
	}

	@Override
	public StatusInfo deleteCluSet(String cluSetId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {

		checkForMissingParameter(cluSetId, "cluSetId");

		luDao.delete(CluSet.class, cluSetId);

		StatusInfo statusInfo = new StatusInfo();
		statusInfo.setSuccess(true);

		return statusInfo;
	}

	@Override
	public StatusInfo addCluSetToCluSet(String cluSetId, String addedCluSetId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, UnsupportedActionException,
			CircularRelationshipException {
		checkForMissingParameter(cluSetId, "cluSetId");
		checkForMissingParameter(addedCluSetId, "addedCluSetId");

		CluSet cluSet = luDao.fetch(CluSet.class, cluSetId);

		checkCluSetAlreadyAdded(cluSet, addedCluSetId);
	
		CluSet addedCluSet = luDao.fetch(CluSet.class, addedCluSetId);

		checkCluSetCircularReference(addedCluSet, cluSetId);

		cluSet.getCluSets().add(addedCluSet);

		luDao.update(cluSet);

		StatusInfo statusInfo = new StatusInfo();
		statusInfo.setSuccess(true);

		return statusInfo;
	}

	@Override
	public StatusInfo removeCluSetFromCluSet(String cluSetId,
			String removedCluSetId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			UnsupportedActionException {

		checkForMissingParameter(cluSetId, "cluSetId");
		checkForMissingParameter(removedCluSetId, "removedCluSetId");

		CluSet cluSet = luDao.fetch(CluSet.class, cluSetId);

		for (Iterator<CluSet> i = cluSet.getCluSets().iterator(); i.hasNext();) {
			CluSet childCluSet = i.next();
			if (childCluSet.getId().equals(removedCluSetId)) {
				i.remove();
				luDao.update(cluSet);
				StatusInfo statusInfo = new StatusInfo();
				statusInfo.setSuccess(true);

				return statusInfo;
			}
		}

		StatusInfo statusInfo = new StatusInfo();
		statusInfo.setSuccess(false);
		statusInfo.setMessage("CluSet does not contain CluSet:"
				+ removedCluSetId);

		return statusInfo;
	}

	@Override
	public StatusInfo addCluToCluSet(String cluId, String cluSetId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, UnsupportedActionException {

		checkForMissingParameter(cluId, "cluId");
		checkForMissingParameter(cluSetId, "cluSetId");

		CluSet cluSet = luDao.fetch(CluSet.class, cluSetId);

		Clu clu = luDao.fetch(Clu.class, cluId);

		checkCluAlreadyAdded(cluSet, cluId);
		
		cluSet.getClus().add(clu);

		luDao.update(cluSet);

		StatusInfo statusInfo = new StatusInfo();
		statusInfo.setSuccess(true);

		return statusInfo;
	}

	@Override
	public StatusInfo removeCluFromCluSet(String cluId, String cluSetId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, UnsupportedActionException {

		checkForMissingParameter(cluId, "cluId");
		checkForMissingParameter(cluSetId, "cluSetId");

		CluSet cluSet = luDao.fetch(CluSet.class, cluSetId);

		for (Iterator<Clu> i = cluSet.getClus().iterator(); i.hasNext();) {
			Clu clu = i.next();
			if (clu.getId().equals(cluId)) {
				i.remove();
				luDao.update(cluSet);
				StatusInfo statusInfo = new StatusInfo();
				statusInfo.setSuccess(true);

				return statusInfo;
			}
		}

		StatusInfo statusInfo = new StatusInfo();
		statusInfo.setSuccess(false);
		statusInfo.setMessage("Clu set does not contain Clu:" + cluId);

		return statusInfo;
	}

	@Override
	public List<ValidationResultInfo> validateLui(String validationType,
			LuiInfo luiInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		checkForMissingParameter(validationType, "validationType");
		checkForMissingParameter(luiInfo, "luiInfo");

		return validator.validateTypeStateObject(luiInfo, getObjectStructure("luiInfo"));
	}

	@Override
	public LuiInfo createLui(String cluId, String atpKey, LuiInfo luiInfo)
			throws AlreadyExistsException, DataValidationErrorException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		checkForMissingParameter(cluId, "cludId");
		checkForMissingParameter(atpKey, "atpKey");
		checkForMissingParameter(luiInfo, "luiInfo");

		// Validate Lui
		List<ValidationResultInfo> val = validateLui("SYSTEM", luiInfo);
		if(null != val && val.size() > 0) {
			throw new DataValidationErrorException("Validation error!");
		}

		Lui lui = new Lui();
		luiInfo.setCluId(cluId);
		luiInfo.setAtpId(atpKey);

		try {
			lui = LuServiceAssembler.toLui(false, luiInfo, luDao);
		} catch (VersionMismatchException vme) {
		}

		luDao.create(lui);

		return LuServiceAssembler.toLuiInfo(lui);
	}

	@Override
	public LuiInfo updateLui(String luiId, LuiInfo luiInfo)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {

		checkForMissingParameter(luiId, "luiId");
		checkForMissingParameter(luiInfo, "luiInfo");

		// Validate Lui
		List<ValidationResultInfo> val = validateLui("SYSTEM", luiInfo);
		if(null != val && val.size() > 0) {
			throw new DataValidationErrorException("Validation error!");
		}

		Lui lui = luDao.fetch(Lui.class, luiId);

		if (!String.valueOf(lui.getVersionInd()).equals(
				luiInfo.getMetaInfo().getVersionInd())) {
			throw new VersionMismatchException(
					"Lui to be updated is not the current version");
		}

		Clu clu = luDao.fetch(Clu.class, luiInfo.getCluId());
		lui.setClu(clu);

		lui.setAttributes(LuServiceAssembler.toGenericAttributes(
				LuiAttribute.class, luiInfo.getAttributes(), lui, luDao));

		// Now copy standard properties
		BeanUtils.copyProperties(luiInfo, lui, new String[] { "cluId",
				"attributes" });

		Lui updated = luDao.update(lui);

		return LuServiceAssembler.toLuiInfo(updated);
	}

	@Override
	public StatusInfo deleteLui(String luiId)
			throws DependentObjectsExistException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {

		checkForMissingParameter(luiId, "luiId");

		luDao.delete(Lui.class, luiId);

		StatusInfo statusInfo = new StatusInfo();
		statusInfo.setSuccess(true);

		return statusInfo;
	}

	@Override
	public LuiInfo updateLuiState(String luiId, String luiState)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {

		// check for missing params
		checkForMissingParameter(luiId, "luiId");
		checkForMissingParameter(luiState, "luiState");
		Lui lui = luDao.fetch(Lui.class, luiId);
		lui.setState(luiState);
		Lui updated = luDao.update(lui);
		return LuServiceAssembler.toLuiInfo(updated);
	}

	@Override
	public List<ValidationResultInfo> validateLuiLuiRelation(
			String validationType, LuiLuiRelationInfo luiLuiRelationInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		checkForMissingParameter(validationType, "validationType");
		checkForMissingParameter(luiLuiRelationInfo, "luiLuiRelationInfo");

		return validator.validateTypeStateObject(luiLuiRelationInfo, getObjectStructure("luiLuiRelationInfo"));
	}

	@Override
	public LuiLuiRelationInfo createLuiLuiRelation(String luiId,
			String relatedLuiId, String luLuRelationTypeKey,
			LuiLuiRelationInfo luiLuiRelationInfo)
			throws AlreadyExistsException, CircularRelationshipException,
			DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		checkForMissingParameter(luiId, "luiId");
		checkForMissingParameter(relatedLuiId, "relatedLuiId");
		checkForMissingParameter(luLuRelationTypeKey, "luLuRelationTypeKey");
		checkForMissingParameter(luiLuiRelationInfo, "luiLuiRelationInfo");

		// Validate LuiLuiRelation
		List<ValidationResultInfo> val = validateLuiLuiRelation("SYSTEM", luiLuiRelationInfo);
		if(null != val && val.size() > 0) {
			throw new DataValidationErrorException("Validation error!");
		}

		if (luiId.equals(relatedLuiId)) {
			throw new CircularRelationshipException(
					"Can not relate a Lui to itself");
		}

		Lui lui = luDao.fetch(Lui.class, luiId);
		Lui relatedLui = luDao.fetch(Lui.class, relatedLuiId);

		LuiLuiRelation luiLuiRelation = new LuiLuiRelation();
		BeanUtils.copyProperties(luiLuiRelationInfo, luiLuiRelation,
				new String[] { "luiId", "relatedLuiId", "attributes",
						"metaInfo" });

		luiLuiRelation.setLui(lui);
		luiLuiRelation.setRelatedLui(relatedLui);
		luiLuiRelation.setAttributes(LuServiceAssembler.toGenericAttributes(
				LuiLuiRelationAttribute.class, luiLuiRelationInfo
						.getAttributes(), luiLuiRelation, luDao));

		LuLuRelationType luLuRelationType = luDao.fetch(LuLuRelationType.class,
				luLuRelationTypeKey);

		luiLuiRelation.setLuLuRelationType(luLuRelationType);

		luDao.create(luiLuiRelation);

		return LuServiceAssembler.toLuiLuiRelationInfo(luiLuiRelation);
	}

	@Override
	public LuiLuiRelationInfo updateLuiLuiRelation(String luiLuiRelationId,
			LuiLuiRelationInfo luiLuiRelationInfo)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {

		checkForMissingParameter(luiLuiRelationId, "luiLuiRelationId");
		checkForMissingParameter(luiLuiRelationInfo, "luiLuiRelationInfo");

		// Validate LuiLuiRelation
		List<ValidationResultInfo> val = validateLuiLuiRelation("SYSTEM", luiLuiRelationInfo);
		if(null != val && val.size() > 0) {
			throw new DataValidationErrorException("Validation error!");
		}

		LuiLuiRelation luiLuiRelation = luDao.fetch(LuiLuiRelation.class,
				luiLuiRelationId);

		if (!String.valueOf(luiLuiRelation.getVersionInd()).equals(
				luiLuiRelationInfo.getMetaInfo().getVersionInd())) {
			throw new VersionMismatchException(
					"LuiLuiRelation to be updated is not the current version");
		}

		BeanUtils.copyProperties(luiLuiRelationInfo, luiLuiRelation,
				new String[] { "luiId", "relatedLuiId", "attributes",
						"metaInfo" });

		if (!luiLuiRelationInfo.getLuiId().equals(
				luiLuiRelation.getLui().getId())) {
			luiLuiRelation.setLui(luDao.fetch(Lui.class, luiLuiRelationInfo
					.getLuiId()));
		}

		if (!luiLuiRelationInfo.getRelatedLuiId().equals(
				luiLuiRelation.getRelatedLui().getId())) {
			luiLuiRelation.setRelatedLui(luDao.fetch(Lui.class,
					luiLuiRelationInfo.getRelatedLuiId()));
		}

		luiLuiRelation.setAttributes(LuServiceAssembler.toGenericAttributes(
				LuiLuiRelationAttribute.class, luiLuiRelationInfo
						.getAttributes(), luiLuiRelation, luDao));

		if (!luiLuiRelationInfo.getType().equals(
				luiLuiRelation.getLuLuRelationType().getId())) {
			luiLuiRelation.setLuLuRelationType(luDao.fetch(
					LuLuRelationType.class, luiLuiRelationInfo.getType()));
		}

		LuiLuiRelation updated = luDao.update(luiLuiRelation);

		return LuServiceAssembler.toLuiLuiRelationInfo(updated);
	}

	@Override
	public StatusInfo deleteLuiLuiRelation(String luiLuiRelationId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {

		checkForMissingParameter(luiLuiRelationId, "luiLuiRelationId");

		luDao.delete(LuiLuiRelation.class, luiLuiRelationId);

		StatusInfo statusInfo = new StatusInfo();
		statusInfo.setSuccess(true);

		return statusInfo;
	}

	/**************************************************************************
	 * SEARCH OPERATIONS *
	 **************************************************************************/

	@Override
	public SearchCriteriaTypeInfo getSearchCriteriaType(
			String searchCriteriaTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {

		return searchManager.getSearchCriteriaType(searchCriteriaTypeKey);
	}

	@Override
	public List<SearchCriteriaTypeInfo> getSearchCriteriaTypes()
			throws OperationFailedException {
		return searchManager.getSearchCriteriaTypes();
	}

	@Override
	public SearchResultTypeInfo getSearchResultType(String searchResultTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		checkForMissingParameter(searchResultTypeKey, "searchResultTypeKey");
		return searchManager.getSearchResultType(searchResultTypeKey);
	}

	@Override
	public List<SearchResultTypeInfo> getSearchResultTypes()
			throws OperationFailedException {
		return searchManager.getSearchResultTypes();
	}

	@Override
	public SearchTypeInfo getSearchType(String searchTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		checkForMissingParameter(searchTypeKey, "searchTypeKey");
		return searchManager.getSearchType(searchTypeKey);
	}

	@Override
	public List<SearchTypeInfo> getSearchTypes()
			throws OperationFailedException {
		return searchManager.getSearchTypes();
	}

	@Override
	public List<SearchTypeInfo> getSearchTypesByCriteria(
			String searchCriteriaTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		checkForMissingParameter(searchCriteriaTypeKey, "searchCriteriaTypeKey");
		return searchManager.getSearchTypesByCriteria(searchCriteriaTypeKey);
	}

	@Override
	public List<SearchTypeInfo> getSearchTypesByResult(
			String searchResultTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		checkForMissingParameter(searchResultTypeKey, "searchResultTypeKey");
		return searchManager.getSearchTypesByResult(searchResultTypeKey);
	}

	private void checkCluAlreadyAdded(CluSet cluSet, String cluId)
			throws OperationFailedException {
		for (Clu childClu : cluSet.getClus()) {
			if (childClu.getId().equals(cluId)) {
				throw new OperationFailedException("CluSet already contains Clu (id='" + cluId + "')");
			}
		}
	}

	private void checkCluSetAlreadyAdded(CluSet cluSet, String cluSetIdToAdd) 
			throws OperationFailedException {
		for (CluSet childCluSet : cluSet.getCluSets()) {
			if (childCluSet.getId().equals(cluSetIdToAdd)) {
				throw new OperationFailedException("CluSet (id=" + cluSet.getId() + 
						") already contains CluSet (id='" + cluSetIdToAdd + "')");
			}
		}
	}
	
	private void checkCluSetCircularReference(CluSet addedCluSet, String hostCluSetId) 
			throws CircularRelationshipException {
		if (addedCluSet.getId().equals(hostCluSetId)) {
			throw new CircularRelationshipException(
					"Cannot add a CluSet (id=" + hostCluSetId + ") to ifself");
		}
		for (CluSet childSet : addedCluSet.getCluSets()) {
			if (childSet.getId().equals(hostCluSetId)) {
				throw new CircularRelationshipException(
						"CluSet (id=" + hostCluSetId +
						") already contains this CluSet (id=" + 
						childSet.getId() + ")");
			}
			checkCluSetCircularReference(childSet, hostCluSetId);
		}
	}

	private void findClusInCluSet(List<Clu> clus, CluSet parentCluSet)
			throws DoesNotExistException {
		for (Clu clu : parentCluSet.getClus()) {
			if (!clus.contains(clu)) {
				clus.add(clu);
			}
		}
		// Recursion possible problem? Stack overflow
		for (CluSet cluSet : parentCluSet.getCluSets()) {
			findClusInCluSet(clus, cluSet);
		}
	}

	private Validator createValidator() {
		// Validator validator = new Validator();
		// validator.setDateParser(new ServerDateParser());
		// // validator.addMessages(null); //TODO this needs to be loaded
		// somehow
		// return validator;
		return null;
	}

	@Override
	public ObjectStructure getObjectStructure(String objectTypeKey) {
		return dictionaryServiceDelegate.getObjectStructure(objectTypeKey);
	}

	@Override
	public List<String> getObjectTypes() {
		return dictionaryServiceDelegate.getObjectTypes();
	}

	@Override
	public boolean validateObject(String objectTypeKey, String stateKey,
			String info) {
		return dictionaryServiceDelegate.validateObject(objectTypeKey,
				stateKey, info);
	}

	@Override
	public boolean validateStructureData(String objectTypeKey, String stateKey,
			String info) {
		return dictionaryServiceDelegate.validateStructureData(objectTypeKey,
				stateKey, info);
	}

	public LuDao getLuDao() {
		return luDao;
	}

	public void setLuDao(LuDao luDao) {
		this.luDao = luDao;
	}

	@Override
	public SearchResult search(SearchRequest searchRequest) throws MissingParameterException {
        checkForMissingParameter(searchRequest, "searchRequest");
        return searchManager.search(searchRequest, luDao);
	}

	/**
	 * Check for missing parameter and throw localized exception if missing
	 *
	 * @param param
	 * @param parameter
	 *            name
	 * @throws MissingParameterException
	 */
	private void checkForMissingParameter(Object param, String paramName)
			throws MissingParameterException {
		if (param == null) {
			throw new MissingParameterException(paramName + " can not be null");
		}
	}

	/**
	 * @param param
	 * @param paramName
	 * @throws MissingParameterException
	 */
	private void checkForEmptyList(Object param, String paramName)
			throws MissingParameterException {
		if (param != null && param instanceof List<?>
				&& ((List<?>) param).size() == 0) {
			throw new MissingParameterException(paramName
					+ " can not be an empty list");
		}
	}

	@Override
	public StatusInfo addCluSetsToCluSet(String cluSetId, List<String> cluSetIdList) 
		throws CircularRelationshipException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, UnsupportedActionException {
		
		checkForMissingParameter(cluSetId, "cluSetId");
		checkForMissingParameter(cluSetIdList, "cluSetIdList");

		// Check that CluSet exists
		luDao.fetch(CluSet.class, cluSetId);

		for(String cluSetIdToAdd : cluSetIdList) {
			StatusInfo status = addCluSetToCluSet(cluSetId, cluSetIdToAdd);
			if (!status.getSuccess()) {
				return status;
			}
		}

		StatusInfo statusInfo = new StatusInfo();
		statusInfo.setSuccess(true);

		return statusInfo;
	}

	@Override
	public StatusInfo addClusToCluSet(List<String> cluIdList, String cluSetId) 
		throws DoesNotExistException, InvalidParameterException, 
			MissingParameterException, OperationFailedException, 
			PermissionDeniedException, UnsupportedActionException {

		checkForMissingParameter(cluIdList, "cluIdList");
		checkForMissingParameter(cluSetId, "cluSetId");

		for(String cluId : cluIdList) {
			StatusInfo status = addCluToCluSet(cluId, cluSetId);
			if (!status.getSuccess()) {
				return status;
			}
		}

		StatusInfo statusInfo = new StatusInfo();
		statusInfo.setSuccess(true);

		return statusInfo;
	}
}
