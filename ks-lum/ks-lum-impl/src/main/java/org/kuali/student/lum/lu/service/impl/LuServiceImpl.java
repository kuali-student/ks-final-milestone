/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

import org.apache.log4j.Logger;
import org.kuali.student.common.conversion.util.R1R2ConverterUtil;
import org.kuali.student.lum.lu.dao.LuDao;
import org.kuali.student.lum.lu.entity.*;
import org.kuali.student.r1.common.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.r1.common.dictionary.service.DictionaryService;
import org.kuali.student.r1.common.entity.Amount;
import org.kuali.student.r1.common.entity.TimeAmount;
import org.kuali.student.r1.common.entity.Version;
import org.kuali.student.r1.common.entity.VersionEntity;
import org.kuali.student.r1.common.search.dto.*;
import org.kuali.student.r1.common.search.service.SearchDispatcher;
import org.kuali.student.r1.common.search.service.SearchManager;
import org.kuali.student.r2.common.dto.*;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.validator.Validator;
import org.kuali.student.r2.common.validator.ValidatorFactory;
import org.kuali.student.r2.core.versionmanagement.dto.VersionDisplayInfo;
import org.kuali.student.r2.lum.clu.dto.*;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.WebService;
import javax.persistence.NoResultException;
import java.util.*;
import java.util.Map.Entry;

@WebService(endpointInterface = "org.kuali.student.r2.lum.clu.service.CluService", serviceName = "CluService", portName = "CluService", targetNamespace = "http://student.kuali.org/wsdl/clu")
@Transactional(readOnly = true, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
public class LuServiceImpl implements CluService {

    private static final String SEARCH_KEY_DEPENDENCY_ANALYSIS = "lu.search.dependencyAnalysis";
    private static final String SEARCH_KEY_BROWSE_PROGRAM = "lu.search.browseProgram";
    private static final String SEARCH_KEY_BROWSE_VARIATIONS = "lu.search.browseVariations";
    private static final String SEARCH_KEY_RESULT_COMPONENT = "lrc.search.resultComponent";

    final Logger logger = Logger.getLogger(LuServiceImpl.class);

    private LuDao luDao;
    private SearchManager searchManager;
    private SearchDispatcher searchDispatcher;
    private DictionaryService dictionaryServiceDelegate;
    private ValidatorFactory validatorFactory;

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

    /**************************************************************************
     * SETUP OPERATION *
     **************************************************************************/

    /* TODO KSCM-422
    @Override
    public List<DeliveryMethodTypeInfo> getDeliveryMethodTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
    	return LuServiceAssembler.toDeliveryMethodTypeInfos(luDao
    			.find(DeliveryMethodType.class));
    }

    @Override
    public DeliveryMethodTypeInfo getDeliveryMethodType(String deliveryMethodTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

    	checkForMissingParameter(deliveryMethodTypeKey, "deliveryMethodTypeKey");

    	return LuServiceAssembler.toDeliveryMethodTypeInfo(luDao.fetch(
    			DeliveryMethodType.class, deliveryMethodTypeKey));
    }

    @Override
    public List<InstructionalFormatTypeInfo> getInstructionalFormatTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
    	return LuServiceAssembler.toInstructionalFormatTypeInfos(luDao
    			.find(InstructionalFormatType.class));
    }

    @Override
    public InstructionalFormatTypeInfo getInstructionalFormatType(String instructionalFormatTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
    	checkForMissingParameter(instructionalFormatTypeKey,
    			"instructionalFormatTypeKey");

    	return LuServiceAssembler.toInstructionalFormatTypeInfo(luDao.fetch(
    			InstructionalFormatType.class, instructionalFormatTypeKey));
    }

    @Override
    public List<LuTypeInfo> getLuTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
    	return LuServiceAssembler.toLuTypeInfos(luDao.find(LuType.class));
    }

    @Override
    public LuTypeInfo getLuType(String luTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
    	checkForMissingParameter(luTypeKey, "luTypeKey");

    	return LuServiceAssembler.toLuTypeInfo(luDao.fetch(LuType.class,
    			luTypeKey));
    }

    @Override
    public LuCodeTypeInfo getLuCodeType(String luCodeTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
    	checkForMissingParameter(luCodeTypeKey, "luCodeTypeKey");
    	return LuServiceAssembler.toLuCodeTypeInfo(luDao.fetch(
    			LuCodeType.class, luCodeTypeKey));
    }

    @Override
    public List<LuCodeTypeInfo> getLuCodeTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
    	return LuServiceAssembler.toLuCodeTypeInfos(luDao
    			.find(LuCodeType.class));
    }

    @Override
    public List<LuLuRelationTypeInfo> getLuLuRelationTypes(ContextInfo contextInfo) throws OperationFailedException, UnsupportedOperationException {
    	return LuServiceAssembler.toLuLuRelationTypeInfos(luDao
    			.find(LuLuRelationType.class));
    }

    @Override
    public LuLuRelationTypeInfo getLuLuRelationType(String cluCluRelationTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
    	checkForMissingParameter(cluCluRelationTypeKey, "luLuRelationTypeKey");

    	LuLuRelationType luLuRelationType = luDao.fetch(LuLuRelationType.class,
    	        cluCluRelationTypeKey);
    	return LuServiceAssembler.toLuLuRelationTypeInfo(luLuRelationType);
    }*/

    @Override
    public List<String> getAllowedLuLuRelationTypesForLuType(String luTypeKey, String relatedLuTypeKey,
            ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(luTypeKey, "luTypeKey");
        checkForMissingParameter(relatedLuTypeKey, "relatedLuTypeKey");

        return luDao.getAllowedLuLuRelationTypesForLuType(luTypeKey,
                relatedLuTypeKey);
    }

    /* TODO KSCM-422
    @Override
    public List<LuPublicationTypeInfo> getLuPublicationTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
    	return LuServiceAssembler.toLuPublicationTypeInfos(luDao
    			.find(LuPublicationType.class));
    }

    @Override
    public LuPublicationTypeInfo getLuPublicationType(String luPublicationTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
    	checkForMissingParameter(luPublicationTypeKey, "luPublicationTypeKey");

    	return LuServiceAssembler.toLuPublicationTypeInfo(luDao.fetch(
    			LuPublicationType.class, luPublicationTypeKey));
    }*/

    @Override
    public List<String> getLuPublicationTypesForLuType(String luTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("getLuPublicationTypesForLuType");
    }

    /* TODO KSCM-422
    @Override
    public List<CluResultTypeInfo> getCluResultTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
    	return LuServiceAssembler.toCluResultTypeInfos(luDao
    			.find(CluResultType.class));
    }

    @Override
    public CluResultTypeInfo getCluResultType(String cluResultTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
    	return LuServiceAssembler.toCluResultTypeInfo(luDao.fetch(
    			CluResultType.class, cluResultTypeKey));
    }

    @Override
    public List<CluResultTypeInfo> getCluResultTypesForLuType(String luTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
    	checkForMissingParameter(luTypeKey, "luTypeKey");
    	return LuServiceAssembler.toCluResultTypeInfos((luDao
    			.getAllowedCluResultTypesForLuType(luTypeKey)));
    }

    @Override
    public List<ResultUsageTypeInfo> getResultUsageTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
    	return LuServiceAssembler.toResultUsageTypeInfos(luDao
    			.find(ResultUsageType.class));
    }

    @Override
    public ResultUsageTypeInfo getResultUsageType(String resultUsageTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
    	checkForMissingParameter(resultUsageTypeKey, "resultUsageTypeKey");
    	return LuServiceAssembler.toResultUsageTypeInfo(luDao.fetch(
    			ResultUsageType.class, resultUsageTypeKey));
    }*/

    @Override
    public List<String> getAllowedResultUsageTypesForLuType(String luTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(luTypeKey, "luTypeKey");

        return luDao.getAllowedResultUsageTypesForLuType(luTypeKey);
    }

    @Override
    public List<String> getAllowedResultComponentTypesForResultUsageType(String resultUsageTypeKey,
            ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {

        checkForMissingParameter(resultUsageTypeKey, "resultUsageTypeKey");

        return luDao
                .getAllowedResultComponentTypesForResultUsageType(resultUsageTypeKey);
    }

    /* TODO KSCM-422
    @Override
    public CluLoRelationTypeInfo getCluLoRelationType(String cluLoRelationTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
    	checkForMissingParameter(cluLoRelationTypeKey, "cluLoRelationTypeKey");

    	CluLoRelationType cluLoRelationType = luDao.fetch(
    			CluLoRelationType.class, cluLoRelationTypeKey);
    	return LuServiceAssembler.toCluLoRelationTypeInfo(cluLoRelationType);
    }*/

    /* TODO KSCM-422
    @Override
    public List<CluLoRelationTypeInfo> getCluLoRelationTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
    	return LuServiceAssembler.toCluLoRelationTypeInfos(luDao
    			.find(CluLoRelationType.class));
    }*/

    @Override
    public List<String> getAllowedCluLoRelationTypesForLuType(String luTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        checkForMissingParameter(luTypeKey, luTypeKey);

        return luDao.getAllowedCluLoRelationTypesForLuType(luTypeKey);
    }

    /* TODO KSCM-422
    @Override
    public List<CluSetTypeInfo> getCluSetTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
    	return LuServiceAssembler.toCluSetTypeInfos(luDao
    			.find(CluSetType.class));
    }*/

    /* TODO KSCM-422
    @Override
    public CluSetTypeInfo getCluSetType(String cluSetTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
    	checkForMissingParameter(cluSetTypeKey, "cluSetTypeKey");
    	return LuServiceAssembler.toCluSetTypeInfo(luDao.fetch(
    			CluSetType.class, cluSetTypeKey));
    }*/

    /**************************************************************************
     * READ OPERATION *
     **************************************************************************/

    // **** Core **********
    @Override
    public CluInfo getClu(String cluId, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        checkForMissingParameter(cluId, "cluId");

        Clu clu = luDao.fetch(Clu.class, cluId);
        return R1R2ConverterUtil.convert(LuServiceAssembler.toCluInfo(clu), CluInfo.class);
    }

    @Override
    public List<CluInfo> getClusByIds(List<String> cluIds, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(cluIds, "cluIdList");
        checkForEmptyList(cluIds, "cluIdList");
        List<Clu> clus = luDao.getClusByIdList(cluIds);
        return R1R2ConverterUtil.convertLists(LuServiceAssembler.toCluInfos(clus), CluInfo.class);
    }

    @Override
    public List<CluInfo> getClusByLuType(String luTypeKey, String luState, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(luTypeKey, "luTypeKey");
        checkForMissingParameter(luState, "lustate");
        List<Clu> clus = luDao.getClusByLuType(luTypeKey, luState);
        return R1R2ConverterUtil.convertLists(LuServiceAssembler.toCluInfos(clus), CluInfo.class);
    }

    @Override
    public List<String> getCluIdsByLuType(String luTypeKey, String luState, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
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
    public List<String> getAllowedCluCluRelationTypesByClu(String cluId, String relatedCluId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(cluId, "cluId");
        checkForMissingParameter(relatedCluId, "relatedCluId");

        return luDao.getAllowedLuLuRelationTypesByCluId(cluId, relatedCluId);
    }

    @Override
    public List<CluInfo> getClusByRelatedCluAndRelationType(String relatedCluId, String cluCLuRelationTypeKey,
            ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(relatedCluId, "relatedCluId");
        checkForMissingParameter(cluCLuRelationTypeKey, "luLuRelationTypeKey");

        List<Clu> clus = luDao.getClusByRelation(relatedCluId,
                cluCLuRelationTypeKey);
        List<CluInfo> result = R1R2ConverterUtil.convertLists(LuServiceAssembler.toCluInfos(clus), CluInfo.class);
        return result;

    }

    @Override
    public List<String> getCluIdsByRelatedCluAndRelationType(String relatedCluId, String cluCluRelationTypeKey,
            ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(relatedCluId, "relatedCluId");
        checkForMissingParameter(cluCluRelationTypeKey, "luLuRelationTypeKey");

        List<String> cluIds = luDao.getCluIdsByRelatedCluId(relatedCluId, cluCluRelationTypeKey);
        return cluIds;
    }

    @Override
    public List<CluInfo> getRelatedClusByCluAndRelationType(String cluId, String cluCluRelationTypeKey,
            ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(cluId, "cluId");
        checkForMissingParameter(cluCluRelationTypeKey, "luLuRelationTypeKey");
        List<Clu> relatedClus = luDao.getRelatedClusByCluId(cluId,
                cluCluRelationTypeKey);
        return R1R2ConverterUtil.convertLists(LuServiceAssembler.toCluInfos(relatedClus), CluInfo.class);
    }

    @Override
    public List<String> getRelatedCluIdsByCluAndRelationType(String cluId, String cluCluRelationTypeKey,
            ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(cluId, "cluId");
        checkForMissingParameter(cluCluRelationTypeKey, "luLuRelationTypeKey");
        List<String> relatedCluIds = luDao.getRelatedCluIdsByCluId(cluId,
                cluCluRelationTypeKey);
        return relatedCluIds;
    }

    @Override
    public CluCluRelationInfo getCluCluRelation(String cluCluRelationId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(cluCluRelationId, "cluCluRelationId");
        return R1R2ConverterUtil.convert(LuServiceAssembler.toCluCluRelationInfo(luDao.fetch(
                CluCluRelation.class, cluCluRelationId)), CluCluRelationInfo.class);
    }

    @Override
    public List<CluCluRelationInfo> getCluCluRelationsByClu(String cluId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(cluId, "cluId");
        List<CluCluRelation> cluCluRelations = luDao
                .getCluCluRelationsByClu(cluId);
        return R1R2ConverterUtil.convertLists(LuServiceAssembler.toCluCluRelationInfos(cluCluRelations), CluCluRelationInfo.class);
    }

    // **** Publication
    @Override
    public List<CluPublicationInfo> getCluPublicationsByClu(String cluId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(cluId, "cluId");
        List<CluPublication> cluPublications = luDao.getCluPublicationsByCluId(cluId);
        return R1R2ConverterUtil.convertLists(LuServiceAssembler.toCluPublicationInfos(cluPublications), CluPublicationInfo.class);
    }

    @Override
    public List<CluPublicationInfo> getCluPublicationsByType(String luPublicationTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(luPublicationTypeKey, "luPublicationTypeKey");
        List<CluPublication> cluPublications = luDao.getCluPublicationsByType(luPublicationTypeKey);
        return R1R2ConverterUtil.convertLists(LuServiceAssembler.toCluPublicationInfos(cluPublications), CluPublicationInfo.class);
    }

    @Override
    public CluPublicationInfo getCluPublication(String cluPublicationId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(cluPublicationId, "cluPublicationId");
        CluPublication cluPublication = luDao.fetch(CluPublication.class, cluPublicationId);
        return R1R2ConverterUtil.convert(LuServiceAssembler.toCluPublicationInfo(cluPublication), CluPublicationInfo.class);
    }

    // **** Results

    @Override
    public CluResultInfo getCluResult(String cluResultId, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        checkForMissingParameter(cluResultId, "cluResultId");

        CluResult cluResult = luDao.fetch(CluResult.class, cluResultId);
        return R1R2ConverterUtil.convert(LuServiceAssembler.toCluResultInfo(cluResult), CluResultInfo.class);
    }

    @Override
    public List<CluResultInfo> getCluResultByClu(String cluId, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {

        checkForMissingParameter(cluId, "cluId");

        return R1R2ConverterUtil.convertLists(LuServiceAssembler.toCluResultInfos(luDao
                .getCluResultByClu(cluId)), CluResultInfo.class);
    }

    @Override
    public List<String> getCluIdsByResultUsageType(String resultUsageTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        return luDao.getCluIdsByResultUsageType(resultUsageTypeKey);
    }

    @Override
    public List<String> getCluIdsByResultComponent(String resultComponentId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        return luDao.getCluIdsByResultComponentId(resultComponentId);
    }

    // **** Learning Objectives

    @Override
    public CluLoRelationInfo getCluLoRelation(String cluLoRelationId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        checkForMissingParameter(cluLoRelationId, "cluLoRelationId");

        CluLoRelation reltn = luDao.fetch(CluLoRelation.class, cluLoRelationId);
        return R1R2ConverterUtil.convert(LuServiceAssembler.toCluLoRelationInfo(reltn), CluLoRelationInfo.class);

    }

    @Override
    public List<CluLoRelationInfo> getCluLoRelationsByClu(String cluId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        checkForMissingParameter(cluId, "cluId");
        List<CluLoRelation> cluLoRelations = luDao
                .getCluLoRelationsByClu(cluId);
        return R1R2ConverterUtil.convertLists(LuServiceAssembler.toCluLoRelationInfos(cluLoRelations), CluLoRelationInfo.class);

    }

    @Override
    public List<CluLoRelationInfo> getCluLoRelationsByLo(String loId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(loId, "loId");
        List<CluLoRelation> cluLoRelations = luDao.getCluLoRelationsByLo(loId);
        return R1R2ConverterUtil.convertLists(LuServiceAssembler.toCluLoRelationInfos(cluLoRelations), CluLoRelationInfo.class);
    }

    // *** Resources

    @Override
    public List<String> getResourceRequirementsForClu(String cluId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not yet implemented!");
    }

    // *** Sets

    @Override
    public CluSetInfo getCluSet(String cluSetId, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(cluSetId, "cluSetId");
        CluSet cluSet = luDao.fetch(CluSet.class, cluSetId);
        CluSetInfo cluSetInfo = R1R2ConverterUtil.convert(LuServiceAssembler.toCluSetInfo(cluSet), CluSetInfo.class);
        setMembershipQuerySearchResult(cluSetInfo);
        return cluSetInfo;
    }

    @Override
    public CluSetTreeViewInfo getCluSetTreeView(String cluSetId, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        checkForMissingParameter(cluSetId, "cluSetId");
        CluSetInfo cluSet = getCluSet(cluSetId, contextInfo);
        if (cluSet == null) {
            return null;
        }

        CluSetTreeViewInfo cluSetTreeView = new CluSetTreeViewInfo();
        getCluSetTreeViewHelper(cluSet, cluSetTreeView, contextInfo);
        return cluSetTreeView;
    }

    /**
     * Go through the list of CluSets and retrieve all the information regarding child Clu
     * Sets and associated Clus
     * 
     * @param cluSetInfo
     * @param cluSetTreeViewInfo
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    private void getCluSetTreeViewHelper(CluSetInfo cluSetInfo, CluSetTreeViewInfo cluSetTreeViewInfo,
            ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        cluSetTreeViewInfo.setName(cluSetInfo.getName());
        cluSetTreeViewInfo.setDescr(cluSetInfo.getDescr());
        cluSetTreeViewInfo.setEffectiveDate(cluSetInfo.getEffectiveDate());
        cluSetTreeViewInfo.setExpirationDate(cluSetInfo.getExpirationDate());
        cluSetTreeViewInfo.setAdminOrg(cluSetInfo.getAdminOrg());
        cluSetTreeViewInfo.setIsReusable(cluSetInfo.getIsReusable());
        cluSetTreeViewInfo.setIsReferenceable(cluSetInfo.getIsReferenceable());
        cluSetTreeViewInfo.setMeta(cluSetInfo.getMeta());
        cluSetTreeViewInfo.setAttributes(cluSetInfo.getAttributes());
        cluSetTreeViewInfo.setTypeKey(cluSetInfo.getTypeKey());
        cluSetTreeViewInfo.setStateKey(cluSetInfo.getStateKey());
        cluSetTreeViewInfo.setId(cluSetInfo.getId());

        if (!cluSetInfo.getCluSetIds().isEmpty()) {
            for (String cluSetId : cluSetInfo.getCluSetIds()) {
                CluSetInfo subCluSet = getCluSet(cluSetId, contextInfo);
                List<CluSetTreeViewInfo> cluSets =
                        cluSetTreeViewInfo.getCluSets() == null ?
                                new ArrayList<CluSetTreeViewInfo>(0) : cluSetTreeViewInfo.getCluSets();

                CluSetTreeViewInfo subCluSetTreeViewInfo = new CluSetTreeViewInfo();
                getCluSetTreeViewHelper(subCluSet, subCluSetTreeViewInfo, contextInfo);
                cluSets.add(subCluSetTreeViewInfo);

                cluSetTreeViewInfo.setCluSets(cluSets);
            }
        }
        List<CluInfo> clus = new ArrayList<CluInfo>();
        for (String cluId : cluSetInfo.getCluIds()) {
            if (cluId != null) {
                //Optimized version of clu translation. It seems like for now we only need the following information.
                //If more information is needed, then appropriate method in assembler has to be used.
                Clu clu = luDao.getCurrentCluVersion(cluId);
                CluInfo cluInfo = new CluInfo();
                cluInfo.setId(clu.getId());
                cluInfo.setTypeKey(clu.getLuType().getId());
                cluInfo.setOfficialIdentifier(R1R2ConverterUtil.convert(LuServiceAssembler.toCluIdentifierInfo(clu.getOfficialIdentifier()), CluIdentifierInfo.class));
                clus.add(cluInfo);
            }
        }
        cluSetTreeViewInfo.setClus(clus);
    }

    @Override
    public List<CluSetInfo> getCluSetsByIds(List<String> cluSetIds, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(cluSetIds, "cluSetIdList");
        checkForEmptyList(cluSetIds, "cluSetIdList");
        List<CluSet> cluSets = luDao.getCluSetInfoByIdList(cluSetIds);
        return R1R2ConverterUtil.convertLists(LuServiceAssembler.toCluSetInfos(cluSets), CluSetInfo.class);
    }

    @Override
    public List<String> getCluSetIdsFromCluSet(String cluSetId, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(cluSetId, "cluSetId");
        CluSet cluSet = luDao.fetch(CluSet.class, cluSetId);
        List<String> ids = new ArrayList<String>(cluSet.getCluVerIndIds().size());
        if (cluSet.getCluSets() != null) {
            for (CluSet cluSet2 : cluSet.getCluSets()) {
                ids.add(cluSet2.getId());
            }
        }
        return ids;
    }

    @Override
    public Boolean isCluSetDynamic(String cluSetId, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not yet implemented!");
    }

    @Override
    public List<CluInfo> getClusFromCluSet(String cluSetId, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(cluSetId, "cluSetId");
        CluSet cluSet = luDao.fetch(CluSet.class, cluSetId);
        List<CluInfo> clus = new ArrayList<CluInfo>(cluSet.getCluVerIndIds().size());
        for (CluSetJoinVersionIndClu cluSetJnClu : cluSet.getCluVerIndIds()) {
            clus.add(R1R2ConverterUtil.convert(LuServiceAssembler.toCluInfo(luDao.getCurrentCluVersion(cluSetJnClu.getCluVersionIndId())), CluInfo.class));
        }
        return clus;
    }

    @Override
    public List<String> getCluIdsFromCluSet(String cluSetId, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(cluSetId, "cluSetId");
        CluSet cluSet = luDao.fetch(CluSet.class, cluSetId);
        List<String> ids = new ArrayList<String>(cluSet.getCluVerIndIds().size());
        for (CluSetJoinVersionIndClu cluSetJnClu : cluSet.getCluVerIndIds()) {
            ids.add(cluSetJnClu.getCluVersionIndId());
        }
        return ids;
    }

    @Override
    public List<CluInfo> getAllClusInCluSet(String cluSetId, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(cluSetId, "cluSetId");
        List<String> cluIndIds = new ArrayList<String>();
        CluSet cluSet = luDao.fetch(CluSet.class, cluSetId);
        findClusInCluSet(cluIndIds, cluSet);
        List<CluInfo> infos = new ArrayList<CluInfo>(cluIndIds.size());
        for (String cluIndId : cluIndIds) {
            infos.add(R1R2ConverterUtil.convert(LuServiceAssembler.toCluInfo(luDao.getCurrentCluVersion(cluIndId)), CluInfo.class));
        }
        return infos;
    }

    @Override
    public List<String> getAllCluIdsInCluSet(String cluSetId, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(cluSetId, "cluSetId");
        List<String> ids = new ArrayList<String>();
        CluSet cluSet = luDao.fetch(CluSet.class, cluSetId);
        findClusInCluSet(ids, cluSet);
        return ids;
    }

    @Override
    public Boolean isCluInCluSet(String cluId, String cluSetId, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(cluId, "cluId");
        checkForMissingParameter(cluSetId, "cluSetId");
        return luDao.isCluInCluSet(cluId, cluSetId);
    }

    /**************************************************************************
     * MAINTENANCE OPERATIONS *
     **************************************************************************/

    @Override
    public List<ValidationResultInfo> validateClu(String validationTypeKey, CluInfo cluInfo, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(validationTypeKey, "validationType");
        checkForMissingParameter(cluInfo, "cluInfo");

        ObjectStructureDefinition objStructure = this.getObjectStructure(CluInfo.class.getName(), contextInfo);
        Validator defaultValidator = validatorFactory.getValidator();
        List<ValidationResultInfo> validationResults = defaultValidator.validateObject(cluInfo, objStructure,
                contextInfo);

        return validationResults;
    }

    @Override
    @Transactional(readOnly = false)
    public CluInfo createClu(String luTypeKey, CluInfo cluInfo, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException,
            AlreadyExistsException {
        Clu clu = toCluForCreate(luTypeKey, cluInfo, contextInfo);
        //Set current (since this is brand new and every verIndId needs one current)
        if (clu.getVersion() == null) {
            clu.setVersion(new Version());
        }
        clu.getVersion().setCurrentVersionStart(new Date());
        luDao.create(clu);
        return R1R2ConverterUtil.convert(LuServiceAssembler.toCluInfo(clu), CluInfo.class);
    }

    public Clu toCluForCreate(String luTypeKey, CluInfo cluInfo, ContextInfo contextInfo)
            throws AlreadyExistsException, DataValidationErrorException,
            DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        checkForMissingParameter(luTypeKey, "luTypeKey");
        checkForMissingParameter(cluInfo, "cluInfo");

        // Validate CLU
        List<ValidationResultInfo> val = validateClu("SYSTEM", cluInfo, contextInfo);
        if (null != val && val.size() > 0) {
            throw new DataValidationErrorException("Validation error!", val);
        }

        Clu clu = new Clu();

        LuType luType = luDao.fetch(LuType.class, luTypeKey);
        clu.setLuType(luType);

        if (cluInfo.getOfficialIdentifier() != null) {
            clu.setOfficialIdentifier(LuServiceAssembler.createOfficialIdentifier(R1R2ConverterUtil.convert(cluInfo, org.kuali.student.r1.lum.lu.dto.CluInfo.class), luDao));
        }
        clu.setAlternateIdentifiers(LuServiceAssembler.createAlternateIdentifiers(R1R2ConverterUtil.convert(cluInfo, org.kuali.student.r1.lum.lu.dto.CluInfo.class), luDao));
        if (cluInfo.getDescr() != null) {
            LuRichText descr = LuServiceAssembler.toRichText(LuRichText.class, R1R2ConverterUtil.convert(cluInfo.getDescr(), org.kuali.student.r1.common.dto.RichTextInfo.class));
            if (descr.getPlain() != null || descr.getFormatted() != null) {
                clu.setDescr(descr);
            }
        }

        if (clu.getAdminOrgs() == null) {
            clu.setAdminOrgs(new ArrayList<CluAdminOrg>(0));
        }
        List<CluAdminOrg> adminOrgs = clu.getAdminOrgs();
        for (AdminOrgInfo orgInfo : cluInfo.getAdminOrgs()) {
            CluAdminOrg instructor = new CluAdminOrg();
            BeanUtils.copyProperties(orgInfo, instructor,
                    new String[]{"attributes"});
            instructor.setAttributes(LuServiceAssembler.toGenericAttributes(
            		CluAdminOrgAttribute.class, R1R2ConverterUtil.convert(orgInfo, org.kuali.student.r1.lum.lu.dto.AdminOrgInfo.class).getAttributes(),
            		instructor, luDao));
            instructor.setClu(clu);
            adminOrgs.add(instructor);
        }

        if (cluInfo.getPrimaryInstructor() != null) {
            CluInstructor primaryInstructor = new CluInstructor();
            BeanUtils.copyProperties(cluInfo.getPrimaryInstructor(),
                    primaryInstructor, new String[]{"attributes"});

            primaryInstructor.setAttributes(LuServiceAssembler
            		.toGenericAttributes(CluInstructorAttribute.class, R1R2ConverterUtil.convert(cluInfo, org.kuali.student.r1.lum.lu.dto.CluInfo.class)
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
                    new String[]{"attributes"});

            instructor.setAttributes(LuServiceAssembler.toGenericAttributes(
            		CluInstructorAttribute.class, R1R2ConverterUtil.convert(instructorInfo, org.kuali.student.r1.lum.lu.dto.CluInstructorInfo.class)
            				.getAttributes(), instructor, luDao));
            instructors.add(instructor);
        }

        if (cluInfo.getStdDuration() != null) {
            clu.setStdDuration(LuServiceAssembler.toTimeAmount(R1R2ConverterUtil.convert(cluInfo.getStdDuration(), org.kuali.student.r1.common.dto.TimeAmountInfo.class)));
        }

        if (clu.getLuCodes() == null) {
            clu.setLuCodes(new ArrayList<LuCode>(0));
        }
        List<LuCode> luCodes = clu.getLuCodes();
        for (LuCodeInfo luCodeInfo : cluInfo.getLuCodes()) {
            LuCode luCode = new LuCode();
            luCode.setAttributes(LuServiceAssembler.toGenericAttributes(
            		LuCodeAttribute.class, R1R2ConverterUtil.convert(luCodeInfo, org.kuali.student.r1.lum.lu.dto.LuCodeInfo.class).getAttributes(), luCode,
            		luDao));
            BeanUtils.copyProperties(luCodeInfo, luCode, new String[]{
                    "attributes", "meta", "descr"});
            luCode.setDescr(luCodeInfo.getDescr().getPlain());
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
                cluFee = LuServiceAssembler.toCluFee(clu, false, R1R2ConverterUtil.convert(cluInfo, org.kuali.student.r1.lum.lu.dto.CluInfo.class)
                        .getFeeInfo(), luDao);
            } catch (VersionMismatchException e) {
                // Version Mismatch Should Happen only for updates
            }
            clu.setFee(cluFee);
        }

        if (cluInfo.getAccountingInfo() != null) {
            CluAccounting cluAccounting = new CluAccounting();
            cluAccounting.setAttributes(LuServiceAssembler.toGenericAttributes(
            		CluAccountingAttribute.class, R1R2ConverterUtil.convert(cluInfo, org.kuali.student.r1.lum.lu.dto.CluInfo.class).getAccountingInfo()
            				.getAttributes(), cluAccounting, luDao));
            cluAccounting.setAffiliatedOrgs(LuServiceAssembler
                    .toAffiliatedOrgs(false, cluAccounting.getAffiliatedOrgs(),
                            R1R2ConverterUtil.convert(cluInfo, org.kuali.student.r1.lum.lu.dto.CluInfo.class).getAccountingInfo().getAffiliatedOrgs(),
                            luDao));
            clu.setAccounting(cluAccounting);
        }

        clu.setAttributes(LuServiceAssembler.toGenericAttributes(
        		CluAttribute.class, R1R2ConverterUtil.convert(cluInfo, org.kuali.student.r1.lum.lu.dto.CluInfo.class).getAttributes(), clu, luDao));

        if (cluInfo.getIntensity() != null) {
            clu.setIntensity(LuServiceAssembler
                    .toAmount(R1R2ConverterUtil.convert(cluInfo, org.kuali.student.r1.lum.lu.dto.CluInfo.class).getIntensity()));
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
                    new String[]{"attributes"});
            accreditation.setAttributes(LuServiceAssembler.toGenericAttributes(
            		CluAccreditationAttribute.class, R1R2ConverterUtil.convert(accreditationInfo, org.kuali.student.r1.lum.lu.dto.AccreditationInfo.class)
            				.getAttributes(), accreditation, luDao));
            accreditations.add(accreditation);
        }

        // Now copy all not standard properties
        BeanUtils.copyProperties(cluInfo, clu, new String[]{"luType",
                "officialIdentifier", "alternateIdentifiers", "descr",
                "luCodes", "primaryInstructor", "instructors", "stdDuration",
                "offeredAtpTypes", "feeInfo", "accountingInfo", "attributes",
                "meta", "versionInfo", "intensity",
                "campusLocations", "accreditations",
                "adminOrgs"});

        return clu;
    }

    @Override
    @Transactional(readOnly = false)
    public CluInfo updateClu(String cluId, CluInfo cluInfo, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException,
            VersionMismatchException {

        checkForMissingParameter(cluId, "cluId");
        checkForMissingParameter(cluInfo, "cluInfo");

        // Validate CLU
        List<ValidationResultInfo> val = validateClu("SYSTEM", cluInfo, contextInfo);
        if (null != val && val.size() > 0) {
            throw new DataValidationErrorException("Validation error!", val);
        }

        Clu clu = luDao.fetch(Clu.class, cluId);

        if (!String.valueOf(clu.getVersionNumber()).equals(
                cluInfo.getMeta().getVersionInd())) {
            throw new VersionMismatchException(
                    "Clu to be updated is not the current version");
        }

        LuType luType = luDao.fetch(LuType.class, cluInfo.getTypeKey());
        clu.setLuType(luType);

        if (cluInfo.getOfficialIdentifier() != null) {
            LuServiceAssembler.updateOfficialIdentifier(clu, R1R2ConverterUtil.convert(cluInfo, org.kuali.student.r1.lum.lu.dto.CluInfo.class), luDao);
        } else if (clu.getOfficialIdentifier() != null) {
            luDao.delete(clu.getOfficialIdentifier());
        }

        // Update the list of Alternate Identifiers
        // Get a map of Id->object of all the currently persisted objects in the
        // list
        Map<String, CluIdentifier> oldAltIdMap = new HashMap<String, CluIdentifier>();
        LuServiceAssembler.updateAlternateIdentifier(oldAltIdMap, clu, R1R2ConverterUtil.convert(cluInfo, org.kuali.student.r1.lum.lu.dto.CluInfo.class), luDao);
        // Now delete anything left over
        for (Entry<String, CluIdentifier> entry : oldAltIdMap.entrySet()) {
            luDao.delete(entry.getValue());
        }

        if (cluInfo.getDescr() != null
                && (cluInfo.getDescr().getPlain() != null || cluInfo.getDescr().getFormatted() != null)) {
            if (clu.getDescr() == null) {
                clu.setDescr(new LuRichText());
            }
            BeanUtils.copyProperties(cluInfo.getDescr(), clu.getDescr());
        } else if (clu.getDescr() != null) {
            luDao.delete(clu.getDescr());
            clu.setDescr(null);//TODO is the is the best method of doing this? what if the user passes in a new made up id, does that mean we have orphaned richtexts?
        }

        if (cluInfo.getPrimaryInstructor() != null) {
            if (clu.getPrimaryInstructor() == null) {
                clu.setPrimaryInstructor(new CluInstructor());
            }
            BeanUtils.copyProperties(cluInfo.getPrimaryInstructor(), clu
                    .getPrimaryInstructor(), new String[]{"attributes"});
            clu.getPrimaryInstructor().setAttributes(
            		LuServiceAssembler.toGenericAttributes(
            				CluInstructorAttribute.class, R1R2ConverterUtil.convert(cluInfo, org.kuali.student.r1.lum.lu.dto.CluInfo.class)
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
                    new String[]{"attributes"});
            cluInstructor.setAttributes(LuServiceAssembler.toGenericAttributes(
            		CluInstructorAttribute.class, R1R2ConverterUtil.convert(instructorInfo, org.kuali.student.r1.lum.lu.dto.CluInstructorInfo.class)
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
                if (!String.valueOf(luCode.getVersionNumber()).equals(
                        luCodeInfo.getMeta().getVersionInd())) {
                    throw new VersionMismatchException(
                            "LuCode to be updated is not the current version");
                }
            }
            // Do Copy 
            luCode.setAttributes(LuServiceAssembler.toGenericAttributes(
            		LuCodeAttribute.class, R1R2ConverterUtil.convert(luCodeInfo, org.kuali.student.r1.lum.lu.dto.LuCodeInfo.class).getAttributes(), luCode,
            		luDao));
            BeanUtils.copyProperties(luCodeInfo, luCode, new String[]{
                    "attributes", "meta"});
            luCode.setDescr(luCodeInfo.getDescr().getPlain());
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
                clu.setFee(LuServiceAssembler.toCluFee(clu, false, R1R2ConverterUtil.convert(cluInfo, org.kuali.student.r1.lum.lu.dto.CluInfo.class)
                        .getFeeInfo(), luDao));
            } else {
                clu.setFee(LuServiceAssembler.toCluFee(clu, true, R1R2ConverterUtil.convert(cluInfo, org.kuali.student.r1.lum.lu.dto.CluInfo.class)
                        .getFeeInfo(), luDao));
            }
        } else if (clu.getFee() != null) {
            luDao.delete(clu.getFee());
            clu.setFee(null);
        }

        if (cluInfo.getAccountingInfo() != null) {
            if (clu.getAccounting() == null) {
                clu.setAccounting(new CluAccounting());
            }
            clu.getAccounting().setAttributes(
            		LuServiceAssembler.toGenericAttributes(
            				CluAccountingAttribute.class, R1R2ConverterUtil.convert(cluInfo, org.kuali.student.r1.lum.lu.dto.CluInfo.class)
            						.getAccountingInfo().getAttributes(), clu
            						.getAccounting(), luDao));
            clu.getAccounting().setAffiliatedOrgs(LuServiceAssembler
                    .toAffiliatedOrgs(true, clu.getAccounting().getAffiliatedOrgs(),
                            R1R2ConverterUtil.convert(cluInfo, org.kuali.student.r1.lum.lu.dto.CluInfo.class).getAccountingInfo().getAffiliatedOrgs(),
                            luDao));

        } else if (clu.getAccounting() != null) {
            clu.setAccounting(null);
        }

        clu.setAttributes(LuServiceAssembler.toGenericAttributes(
        		CluAttribute.class, R1R2ConverterUtil.convert(cluInfo, org.kuali.student.r1.lum.lu.dto.CluInfo.class).getAttributes(), clu, luDao));

        if (cluInfo.getIntensity() != null) {
            if (clu.getIntensity() == null) {
                clu.setIntensity(new Amount());
            }
            BeanUtils
                    .copyProperties(cluInfo.getIntensity(), clu.getIntensity());
        } else if (clu.getIntensity() != null) {
            luDao.delete(clu.getIntensity());
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
            oldAccreditationMap.put(cluAccreditation.getId(),
                    cluAccreditation);
        }
        clu.getAccreditations().clear();

        // Loop through the new list, if the item exists already update and
        // remove from the list
        // otherwise create a new entry
        for (AccreditationInfo accreditationInfo : cluInfo.getAccreditations()) {
            CluAccreditation cluAccreditation = null;
            if (accreditationInfo.getId() != null) {
                cluAccreditation = oldAccreditationMap.remove(accreditationInfo.getId());
            }

            if (cluAccreditation == null) {
                cluAccreditation = new CluAccreditation();
            }
            // Do Copy
            BeanUtils.copyProperties(accreditationInfo, cluAccreditation,
                    new String[]{"attributes"});
            cluAccreditation.setAttributes(LuServiceAssembler
            		.toGenericAttributes(CluAccreditationAttribute.class,
            		        R1R2ConverterUtil.convert(accreditationInfo, org.kuali.student.r1.lum.lu.dto.AccreditationInfo.class).getAttributes(),
            				cluAccreditation, luDao));
            clu.getAccreditations().add(cluAccreditation);
        }

        // Now delete anything left over
        for (Entry<String, CluAccreditation> entry : oldAccreditationMap
                .entrySet()) {
            luDao.delete(entry.getValue());
        }

        // Update the List of alternate admin orgs
        // Get a map of Id->object of all the currently persisted objects in the
        // list
        Map<String, CluAdminOrg> oldAdminOrgsMap = new HashMap<String, CluAdminOrg>();
        if (clu.getAdminOrgs() != null) {
            for (CluAdminOrg cluOrg : clu.getAdminOrgs()) {
                oldAdminOrgsMap.put(cluOrg.getId(), cluOrg);
            }
        }
        clu.setAdminOrgs(new ArrayList<CluAdminOrg>());

        // Loop through the new list, if the item exists already update and
        // remove from the list
        // otherwise create a new entry
        for (AdminOrgInfo orgInfo : cluInfo.getAdminOrgs()) {
            CluAdminOrg cluOrg = null;
            if (orgInfo.getId() != null) {
                cluOrg = oldAdminOrgsMap.remove(orgInfo.getId());
            }

            if (cluOrg == null) {
                cluOrg = new CluAdminOrg();
            }

            // Do Copy
            BeanUtils.copyProperties(orgInfo, cluOrg,
                    new String[]{"attributes", "id"});
            cluOrg.setAttributes(LuServiceAssembler.toGenericAttributes(
            		CluAdminOrgAttribute.class, R1R2ConverterUtil.convert(orgInfo, org.kuali.student.r1.lum.lu.dto.AdminOrgInfo.class).getAttributes(),
            		cluOrg, luDao));
            cluOrg.setClu(clu);
            clu.getAdminOrgs().add(cluOrg);
        }

        for (Entry<String, CluAdminOrg> entry : oldAdminOrgsMap.entrySet()) {
            luDao.delete(entry.getValue());
        }

        // Now copy all not standard properties
        BeanUtils.copyProperties(cluInfo, clu, new String[]{"luType",
                "officialIdentifier", "alternateIdentifiers", "descr",
                "luCodes", "primaryInstructor", "instructors", "stdDuration",
                "offeredAtpTypes", "feeInfo", "accountingInfo", "attributes",
                "meta", "intensity",
                "campusLocations", "accreditations",
                "adminOrgs"});
        Clu updated = null;
        try {
            updated = luDao.update(clu);
        } catch (Exception e) {
            logger.error("Exception occured: ", e);
        }
        return R1R2ConverterUtil.convert(LuServiceAssembler.toCluInfo(updated), CluInfo.class);
    }

    @Override
    @Transactional(readOnly = false)
    public StatusInfo deleteClu(String cluId, ContextInfo contextInfo) throws DependentObjectsExistException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        checkForMissingParameter(cluId, "cluId");

        luDao.delete(Clu.class, cluId);

        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setSuccess(true);

        return statusInfo;
    }

    @Override
    @Transactional(readOnly = false)
    public CluInfo updateCluState(String cluId, String luState, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException,
            VersionMismatchException {
        // Check Missing params
        checkForMissingParameter(cluId, "cluId");
        checkForMissingParameter(luState, "luState");
        Clu clu = luDao.fetch(Clu.class, cluId);
        clu.setState(luState);
        Clu updated = luDao.update(clu);
        return R1R2ConverterUtil.convert(LuServiceAssembler.toCluInfo(updated), CluInfo.class);
    }

    @Override
    public List<ValidationResultInfo> validateCluCluRelation(String validationTypeKey, String cluId,
            String relatedCluId, String cluCluRelationTypeKey, CluCluRelationInfo cluCluRelationInfo,
            ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(validationTypeKey, "validationType");
        checkForMissingParameter(cluCluRelationInfo, "cluCluRelationInfo");

        ObjectStructureDefinition objStructure = this.getObjectStructure(CluCluRelationInfo.class.getName(),
                contextInfo);
        Validator defaultValidator = validatorFactory.getValidator();
        List<ValidationResultInfo> validationResults = defaultValidator.validateObject(cluCluRelationInfo,
                objStructure, contextInfo);

        return validationResults;
    }

    @Override
    @Transactional(readOnly = false)
    public CluCluRelationInfo createCluCluRelation(String cluId, String relatedCluId, String cluCluRelationTypeKey,
            CluCluRelationInfo cluCluRelationInfo, ContextInfo contextInfo) throws CircularRelationshipException,
            DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException, AlreadyExistsException {
        checkForMissingParameter(cluId, "cluId");
        checkForMissingParameter(relatedCluId, "relatedCluId");
        checkForMissingParameter(cluCluRelationTypeKey, "luLuRelationTypeKey");
        checkForMissingParameter(cluCluRelationInfo, "cluCluRelationInfo");

        if (cluId.equals(relatedCluId)) {
            throw new CircularRelationshipException(
                    "Can not relate a Clu to itself");
        }

        // Validate CluCluRelationInfo
          List<ValidationResultInfo> val = validateCluCluRelation("SYSTEM",cluId, relatedCluId, cluCluRelationTypeKey, cluCluRelationInfo, contextInfo);
       
        if(null != val && val.size() > 0) {
        	throw new DataValidationErrorException("Validation error!", val);
        }

        Clu clu = luDao.fetch(Clu.class, cluId);
        Clu relatedClu = luDao.fetch(Clu.class, relatedCluId);

        CluCluRelation cluCluRelation = new CluCluRelation();
        BeanUtils.copyProperties(R1R2ConverterUtil.convert(cluCluRelationInfo, org.kuali.student.r1.lum.lu.dto.CluCluRelationInfo.class), cluCluRelation,
                new String[]{"cluId", "relatedCluId",
                        "isCluRelationRequired", "attributes", "meta"});

        cluCluRelation.setClu(clu);
        cluCluRelation.setRelatedClu(relatedClu);
        cluCluRelation.setCluRelationRequired(cluCluRelationInfo
                .getIsCluRelationRequired() == null ? true : cluCluRelationInfo
                .getIsCluRelationRequired()); // TODO maybe this is unnecessary,
        // contract specifies not null
        cluCluRelation.setAttributes(LuServiceAssembler.toGenericAttributes(
        		CluCluRelationAttribute.class, R1R2ConverterUtil.convert(cluCluRelationInfo, org.kuali.student.r1.lum.lu.dto.CluCluRelationInfo.class) 
        				.getAttributes(), cluCluRelation, luDao));

        LuLuRelationType luLuRelationType = luDao.fetch(LuLuRelationType.class,
                cluCluRelationTypeKey);

        cluCluRelation.setLuLuRelationType(luLuRelationType);

        luDao.create(cluCluRelation);

        return R1R2ConverterUtil.convert(LuServiceAssembler.toCluCluRelationInfo(cluCluRelation), CluCluRelationInfo.class);
    }

    @Override
    @Transactional(readOnly = false)
    public CluCluRelationInfo updateCluCluRelation(String cluCluRelationId, CluCluRelationInfo cluCluRelationInfo,
            ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException,
            ReadOnlyException, VersionMismatchException {
        checkForMissingParameter(cluCluRelationId, "cluCluRelationId");
        checkForMissingParameter(cluCluRelationInfo, "cluCluRelationInfo");

        // Validate CluCluRelationInfo
        List<ValidationResultInfo> val = validateCluCluRelation("SYSTEM",cluCluRelationInfo.getCluId(), cluCluRelationInfo.getRelatedCluId(), cluCluRelationInfo.getTypeKey(), cluCluRelationInfo, contextInfo);
        if(null != val && val.size() > 0) {
        	throw new DataValidationErrorException("Validation error!", val);
        }

        final CluCluRelation cluCluRelation = luDao.fetch(CluCluRelation.class,
                cluCluRelationId);
        BeanUtils.copyProperties(cluCluRelationInfo, cluCluRelation,
                new String[]{"cluId", "relatedCluId",
                        "isCluRelationRequired", "attributes", "meta"});

        cluCluRelation.setClu(luDao.fetch(Clu.class, cluCluRelationInfo
                .getCluId()));
        cluCluRelation.setRelatedClu(luDao.fetch(Clu.class, cluCluRelationInfo
                .getRelatedCluId()));
        cluCluRelation.setCluRelationRequired(cluCluRelationInfo
                .getIsCluRelationRequired() == null ? true : cluCluRelationInfo
                .getIsCluRelationRequired()); // TODO maybe this is unnecessary,
        // contract specifies not null
        cluCluRelation.setAttributes(LuServiceAssembler.toGenericAttributes(
        		CluCluRelationAttribute.class, R1R2ConverterUtil.convert(cluCluRelationInfo, org.kuali.student.r1.lum.lu.dto.CluCluRelationInfo.class)
        				.getAttributes(), cluCluRelation, luDao));

        cluCluRelation.setLuLuRelationType(luDao.fetch(LuLuRelationType.class,
                cluCluRelationInfo.getTypeKey()));

        final CluCluRelation update = luDao.update(cluCluRelation);

        return R1R2ConverterUtil.convert(LuServiceAssembler.toCluCluRelationInfo(update), CluCluRelationInfo.class);
    }

    @Override
    @Transactional(readOnly = false)
    public StatusInfo deleteCluCluRelation(String cluCluRelationId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(cluCluRelationId, "cluCluRelationId");

        luDao.delete(CluCluRelation.class, cluCluRelationId);

        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setSuccess(true);

        return statusInfo;
    }

    @Override
    public List<ValidationResultInfo> validateCluPublication(String validationTypeKey, String cluId,
            String luPublicationTypeKey, CluPublicationInfo cluPublicationInfo, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        checkForMissingParameter(validationTypeKey, "validationType");
        checkForMissingParameter(cluPublicationInfo, "cluPublicationInfo");

        ObjectStructureDefinition objStructure = this.getObjectStructure(CluPublicationInfo.class.getName(),
                contextInfo);
        Validator defaultValidator = validatorFactory.getValidator();
        List<ValidationResultInfo> validationResults = defaultValidator.validateObject(cluPublicationInfo,
                objStructure, contextInfo);
        return validationResults;
    }

    @Override
    @Transactional(readOnly = false)
    public CluPublicationInfo createCluPublication(String cluId, String luPublicationTypeKey,
            CluPublicationInfo cluPublicationInfo, ContextInfo contextInfo) throws DataValidationErrorException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException,
            ReadOnlyException, AlreadyExistsException, DoesNotExistException {
        checkForMissingParameter(cluId, "cluId");
        checkForMissingParameter(luPublicationTypeKey, "luPublicationType");
        checkForMissingParameter(cluPublicationInfo, "cluPublicationInfo");

        // Validate CLU
        List<ValidationResultInfo> val;
        
        try { 	
        	val = validateCluPublication("SYSTEM",cluId,luPublicationTypeKey, cluPublicationInfo, contextInfo);
        	if(null != val && val.size() > 0) {
        		throw new DataValidationErrorException("Validation error!", val);
        	}
        } catch (DoesNotExistException e) {
        	throw new OperationFailedException("Error creating clu",e);
        }

        CluPublication cluPub = new CluPublication();
        Clu clu;
        try {
            clu = luDao.fetch(Clu.class, cluId);
        } catch (DoesNotExistException e) {
            throw new InvalidParameterException("Clu does not exist for id:" + cluId);
        }

        CluPublicationType type;
        try {
            type = luDao.fetch(CluPublicationType.class, luPublicationTypeKey);
        } catch (DoesNotExistException e) {
            throw new InvalidParameterException("CluPublication Type does not exist for id:" + luPublicationTypeKey);
        }

        cluPub.setClu(clu);
        cluPub.setId(cluPublicationInfo.getId());
        cluPub.setEndCycle(cluPublicationInfo.getEndCycle());
        cluPub.setStartCycle(cluPublicationInfo.getStartCycle());
        cluPub.setEffectiveDate(cluPublicationInfo.getEffectiveDate());
        cluPub.setExpirationDate(cluPublicationInfo.getExpirationDate());
        cluPub.setState(cluPublicationInfo.getStateKey());
        cluPub.setType(type);
        cluPub.setAttributes(LuServiceAssembler.toGenericAttributes(CluPublicationAttribute.class, R1R2ConverterUtil.convert(cluPublicationInfo,  org.kuali.student.r1.lum.lu.dto.CluPublicationInfo.class).getAttributes(), cluPub, luDao));
        cluPub.setVariants(LuServiceAssembler.toCluPublicationVariants(R1R2ConverterUtil.convertLists(cluPublicationInfo.getVariants(), org.kuali.student.r1.lum.lu.dto.FieldInfo.class), cluPub, luDao));

        luDao.create(cluPub);

        return R1R2ConverterUtil.convert(LuServiceAssembler.toCluPublicationInfo(cluPub), CluPublicationInfo.class);
    }

    @Override
    @Transactional(readOnly = false)
    public CluPublicationInfo updateCluPublication(String cluPublicationId, CluPublicationInfo cluPublicationInfo,
            ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException,
            ReadOnlyException, VersionMismatchException {
        checkForMissingParameter(cluPublicationId, "cluPublicationId");
        checkForMissingParameter(cluPublicationInfo, "cluPublicationInfo");

        // Validate CLU
        List<ValidationResultInfo> val;
        
        try {
        	val = validateCluPublication("SYSTEM",cluPublicationInfo.getCluId(),cluPublicationInfo.getTypeKey(), cluPublicationInfo, contextInfo);
        	if(null != val && val.size() > 0) {
        		throw new DataValidationErrorException("Validation error!", val);
        	}
        } catch (DoesNotExistException e) {
        	throw new OperationFailedException("Error creating clu",e);
        }

        CluPublication cluPub = luDao.fetch(CluPublication.class, cluPublicationId);

        if (!String.valueOf(cluPub.getVersionNumber()).equals(
                cluPublicationInfo.getMeta().getVersionInd())) {
            throw new VersionMismatchException(
                    "CluPublication to be updated is not the current version");
        }

        Clu clu;
        try {
            clu = luDao.fetch(Clu.class, cluPublicationInfo.getCluId());
        } catch (DoesNotExistException e) {
            throw new InvalidParameterException("Clu does not exist for id:" + cluPublicationInfo.getCluId());
        }

        CluPublicationType type;
        try {
            type = luDao.fetch(CluPublicationType.class, cluPublicationInfo.getTypeKey());
        } catch (DoesNotExistException e) {
            throw new InvalidParameterException("CluPublication Type does not exist for id:"
                    + cluPublicationInfo.getTypeKey());
        }

        // Update the list of variants
        // Get a map of Id->object of all the currently persisted objects in the
        // list
        Map<String, CluPublicationVariant> oldVariantMap = new HashMap<String, CluPublicationVariant>();
        for (CluPublicationVariant variant : cluPub.getVariants()) {
            oldVariantMap.put(variant.getKey(), variant);
        }
        cluPub.getVariants().clear();

        // Loop through the new list, if the item exists already update and
        // remove from the list otherwise create a new entry
        CluPublicationVariant variant = null;
        for (FieldInfo fieldInfo : cluPublicationInfo.getVariants()) {
            if (!oldVariantMap.containsKey(fieldInfo.getId())) {
                // New variant key
                variant = new CluPublicationVariant();
                variant.setKey(fieldInfo.getId());
                variant.setValue(fieldInfo.getValue());
            } else {
                // Update existing variant
                variant = oldVariantMap.get(fieldInfo.getId());
                variant.setValue(fieldInfo.getValue());
                oldVariantMap.remove(fieldInfo.getId());
            }

            cluPub.getVariants().add(variant);
        }

        // Now delete anything left over
        for (Entry<String, CluPublicationVariant> entry : oldVariantMap.entrySet()) {
            luDao.delete(entry.getValue());
        }

        cluPub.setClu(clu);
        cluPub.setEndCycle(cluPublicationInfo.getEndCycle());
        cluPub.setStartCycle(cluPublicationInfo.getStartCycle());
        cluPub.setEffectiveDate(cluPublicationInfo.getEffectiveDate());
        cluPub.setExpirationDate(cluPublicationInfo.getExpirationDate());
        cluPub.setState(cluPublicationInfo.getStateKey());
        cluPub.setType(type);
        cluPub.setAttributes(LuServiceAssembler.toGenericAttributes(CluPublicationAttribute.class, R1R2ConverterUtil.convert(cluPublicationInfo, org.kuali.student.r1.lum.lu.dto.CluPublicationInfo.class).getAttributes(), cluPub, luDao));

        CluPublication updated = luDao.update(cluPub);

        return R1R2ConverterUtil.convert(LuServiceAssembler.toCluPublicationInfo(updated), CluPublicationInfo.class);
    }

    @Override
    @Transactional(readOnly = false)
    public StatusInfo deleteCluPublication(String cluPublicationId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            DependentObjectsExistException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(cluPublicationId, "cluPublicationId");

        luDao.delete(CluPublication.class, cluPublicationId);

        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setSuccess(true);

        return statusInfo;
    }

    @Override
    public List<ValidationResultInfo> validateCluResult(String validationTypeKey, String cluId,
            String cluResultTypeKey, CluResultInfo cluResultInfo, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(validationTypeKey, "validationType");
        checkForMissingParameter(cluResultInfo, "cluResultInfo");

        ObjectStructureDefinition objStructure = this.getObjectStructure(CluResultInfo.class.getName(), contextInfo);
        Validator defaultValidator = validatorFactory.getValidator();
        List<ValidationResultInfo> validationResults = defaultValidator.validateObject(cluResultInfo, objStructure,
                contextInfo);
        return validationResults;
    }

    @Override
    @Transactional(readOnly = false)
    public CluResultInfo createCluResult(String cluId, String cluResultTypeKey, CluResultInfo cluResultInfo,
            ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException,
            ReadOnlyException, AlreadyExistsException {

        checkForMissingParameter(cluId, "cluId");
        checkForMissingParameter(cluResultTypeKey, "cluResultTypeKey");
        checkForMissingParameter(cluResultInfo, "cluResultInfo");

        // Validate CluResult
         List<ValidationResultInfo> val = validateCluResult("SYSTEM",cluId,cluResultTypeKey,cluResultInfo, contextInfo);
        if(null != val && val.size() > 0) {
        	throw new DataValidationErrorException("Validation error!", val);
        }

        cluResultInfo.setTypeKey(cluResultTypeKey);
        cluResultInfo.setCluId(cluId);

        List<ResultOption> resOptList = new ArrayList<ResultOption>();
        for (ResultOptionInfo resOptInfo : cluResultInfo.getResultOptions()) {
            ResultOption resOpt = new ResultOption();
            BeanUtils.copyProperties(resOptInfo, resOpt, new String[]{"id",
                    "meta", "resultUsageType", "desc"});

            if (resOptInfo.getResultUsageTypeKey() != null) {
                ResultUsageType resUsageType = luDao.fetch(ResultUsageType.class,
                        resOptInfo.getResultUsageTypeKey());
                resOpt.setResultUsageType(resUsageType);
            } 
            resOpt.setDesc(LuServiceAssembler.toRichText(LuRichText.class, R1R2ConverterUtil.convert(resOptInfo, org.kuali.student.r1.lum.lu.dto.ResultOptionInfo.class).getDesc()));
            luDao.create(resOpt);
            resOptList.add(resOpt);
        }

        CluResult cluResult = new CluResult();
        BeanUtils.copyProperties(cluResultInfo, cluResult, new String[]{"id",
                "desc", "resultOptions", "meta"});

        cluResult.setDesc(LuServiceAssembler
        		.toRichText(LuRichText.class, R1R2ConverterUtil.convert(cluResultInfo, org.kuali.student.r1.lum.lu.dto.CluResultInfo.class).getDesc()));
        cluResult.setResultOptions(resOptList);

        Clu clu = luDao.fetch(Clu.class, cluId);
        cluResult.setClu(clu);

        CluResultType type = luDao.fetch(CluResultType.class, cluResultTypeKey);
        cluResult.setCluResultType(type);

        luDao.create(cluResult);

        return R1R2ConverterUtil.convert(LuServiceAssembler.toCluResultInfo(cluResult), CluResultInfo.class);
    }

    @Override
    @Transactional(readOnly = false)
    public CluResultInfo updateCluResult(String cluResultId, CluResultInfo cluResultInfo, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException,
            VersionMismatchException {

        checkForMissingParameter(cluResultId, "cluResultId");
        checkForMissingParameter(cluResultInfo, "cluResultInfo");

        // Validate CluResult
          List<ValidationResultInfo> val = validateCluResult("SYSTEM",cluResultInfo.getCluId(), cluResultInfo.getTypeKey(),cluResultInfo,contextInfo);
        if(null != val && val.size() > 0) {
        	throw new DataValidationErrorException("Validation error!", val);
        }

        CluResult result = luDao.fetch(CluResult.class, cluResultId);
        if (!String.valueOf(result.getVersionNumber()).equals(
                cluResultInfo.getMeta().getVersionInd())) {
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
                // New result option
                opt = new ResultOption();
                // Copy properties
                BeanUtils.copyProperties(resOptInfo, opt, new String[]{
                        "resultUsageType", "desc"});
            } else {
                // Get existing result option
                opt = luDao.fetch(ResultOption.class, resOptInfo.getId());
                // Copy properties
                BeanUtils.copyProperties(resOptInfo, opt, new String[]{
                        "id", "resultUsageType", "desc"});
            }
            if (resOptInfo.getResultUsageTypeKey() != null && !resOptInfo.getResultUsageTypeKey().isEmpty()) {
                ResultUsageType resUsageType = luDao.fetch(ResultUsageType.class,
                        resOptInfo.getResultUsageTypeKey());
                opt.setResultUsageType(resUsageType);
            }
            opt.setDesc(LuServiceAssembler.toRichText(LuRichText.class, R1R2ConverterUtil.convert(resOptInfo, org.kuali.student.r1.lum.lu.dto.ResultOptionInfo.class).getDesc()));
            result.getResultOptions().add(opt);
        }

        // Now delete anything left over
        for (Entry<String, ResultOption> entry : oldResultOptionMap.entrySet()) {
            luDao.delete(entry.getValue());
        }

        BeanUtils.copyProperties(cluResultInfo, result, new String[]{"id",
                "desc", "resultOptions"});
 
        result.setDesc(LuServiceAssembler.toRichText(LuRichText.class, R1R2ConverterUtil.convert(cluResultInfo, org.kuali.student.r1.lum.lu.dto.CluResultInfo.class).getDesc()));
        CluResultType type = luDao.fetch(CluResultType.class, cluResultInfo.getTypeKey());
        result.setCluResultType(type);

        CluResult updated = luDao.update(result);

        return R1R2ConverterUtil.convert(LuServiceAssembler.toCluResultInfo(updated), CluResultInfo.class);
    }

    @Override
    @Transactional(readOnly = false)
    public StatusInfo deleteCluResult(String cluResultId, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, DependentObjectsExistException,
            OperationFailedException, PermissionDeniedException {

        checkForMissingParameter(cluResultId, "cluResultId");

        luDao.delete(CluResult.class, cluResultId);

        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setSuccess(true);

        return statusInfo;
    }

    @Override
    public List<ValidationResultInfo> validateCluLoRelation(String validationTypeKey, String cluId, String loId,
            String cluLoRelationTypeKey, CluLoRelationInfo cluLoRelationInfo, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        checkForMissingParameter(validationTypeKey, "validationType");
        checkForMissingParameter(cluLoRelationInfo, "cluLoRelationInfo");

        ObjectStructureDefinition objStructure = this.getObjectStructure(CluLoRelation.class.getName(), contextInfo);
        Validator defaultValidator = validatorFactory.getValidator();
        List<ValidationResultInfo> validationResults = defaultValidator.validateObject(cluLoRelationInfo, objStructure,
                contextInfo);
        return validationResults;
    }

    @Override
    @Transactional(readOnly = false)
    public CluLoRelationInfo createCluLoRelation(String cluId, String loId, String cluLoRelationTypeKey,
            CluLoRelationInfo cluLoRelationInfo, ContextInfo contextInfo) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, ReadOnlyException, AlreadyExistsException {
        checkForMissingParameter(loId, "loId");
        checkForMissingParameter(cluId, "cluId");
        checkForEmptyList(cluLoRelationTypeKey, "cluLoRelationType");
        checkForEmptyList(cluLoRelationInfo, "cluLoRelationInfo");

        // Validate CluLoRelation
       List<ValidationResultInfo> val = validateCluLoRelation("SYSTEM",cluId, loId,cluLoRelationTypeKey,cluLoRelationInfo, contextInfo);
        if(null != val && val.size() > 0) {
        	throw new DataValidationErrorException("Validation error!", val);
        }

        Clu clu = luDao.fetch(Clu.class, cluId);
        if (clu == null) {
            throw new DoesNotExistException("Clu does not exist for id: "
                    + cluId);
        }

        CluLoRelationType cluLoRelationTypeEntity = luDao.fetch(CluLoRelationType.class, cluLoRelationTypeKey);
        if (cluLoRelationTypeEntity == null) {
            throw new DoesNotExistException("CluLoRelationType does not exist for id: "
                    + cluLoRelationTypeKey);
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
                new String[]{"cluId", "attributes", "meta", "type"});

        cluLoRelation.setClu(clu); 
        cluLoRelation.setAttributes(LuServiceAssembler.toGenericAttributes(
        		CluLoRelationAttribute.class,
        		R1R2ConverterUtil.convert(cluLoRelationInfo, org.kuali.student.r1.lum.lu.dto.CluLoRelationInfo.class).getAttributes(), cluLoRelation, luDao));
        cluLoRelation.setType(cluLoRelationTypeEntity);

        luDao.create(cluLoRelation);

        return R1R2ConverterUtil.convert(LuServiceAssembler.toCluLoRelationInfo(cluLoRelation), CluLoRelationInfo.class);
    }

    @Override
    @Transactional(readOnly = false)
    public CluLoRelationInfo updateCluLoRelation(String cluLoRelationId, CluLoRelationInfo cluLoRelationInfo,
            ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException,
            ReadOnlyException, VersionMismatchException {
        checkForMissingParameter(cluLoRelationId, "cluLoRelationId");
        checkForMissingParameter(cluLoRelationInfo, "cluLoRelationInfo");

        // Validate CluLoRelation
         List<ValidationResultInfo> val = validateCluLoRelation("SYSTEM",cluLoRelationInfo.getCluId(), cluLoRelationInfo.getLoId(),cluLoRelationInfo.getTypeKey(),cluLoRelationInfo, contextInfo);
        if(null != val && val.size() > 0) {
        	throw new DataValidationErrorException("Validation error!", val);
        } 

        CluLoRelation reltn = luDao.fetch(CluLoRelation.class, cluLoRelationId);

        if (!String.valueOf(reltn.getVersionNumber()).equals(
                cluLoRelationInfo.getMeta().getVersionInd())) {
            throw new VersionMismatchException(
                    "CluLoRelation to be updated is not the current version");
        }

        Clu clu = luDao.fetch(Clu.class, cluLoRelationInfo.getCluId());
        if (clu == null) {
            throw new DoesNotExistException("Clu does not exist for id: "
                    + cluLoRelationInfo.getCluId());
        }

        CluLoRelationType cluLoRelationTypeEntity = luDao.fetch(CluLoRelationType.class, cluLoRelationInfo.getTypeKey());
        if (cluLoRelationTypeEntity == null) {
            throw new DoesNotExistException("CluLoRelationType does not exist for id: "
                    + cluLoRelationInfo.getTypeKey());
        }

        BeanUtils.copyProperties(cluLoRelationInfo, reltn, new String[]{
                "cluId", "attributes", "meta", "type"});

        reltn.setClu(clu); 
        reltn.setAttributes(LuServiceAssembler.toGenericAttributes(
        		CluLoRelationAttribute.class,
        		R1R2ConverterUtil.convert(cluLoRelationInfo, org.kuali.student.r1.lum.lu.dto.CluLoRelationInfo.class).getAttributes(), reltn, luDao));
        reltn.setType(cluLoRelationTypeEntity);
        CluLoRelation updated = luDao.update(reltn);

        return R1R2ConverterUtil.convert(LuServiceAssembler.toCluLoRelationInfo(updated), CluLoRelationInfo.class);
    }

    @Override
    @Transactional(readOnly = false)
    public StatusInfo deleteCluLoRelation(String cluLoRelationId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
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
    @Transactional(readOnly = false)
    public StatusInfo addCluResourceRequirement(String resourceTypeKey, String cluId, ContextInfo contextInfo)
            throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not yet implemented!");
    }

    @Override
    @Transactional(readOnly = false)
    public StatusInfo removeCluResourceRequirement(String resourceTypeKey, String cluId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ValidationResultInfo> validateCluSet(String validationTypeKey, String cluSetTypeKey,
            CluSetInfo cluSetInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(validationTypeKey, "validationType");
        checkForMissingParameter(cluSetInfo, "cluSetInfo");

        ObjectStructureDefinition objStructure = this.getObjectStructure(CluSetInfo.class.getName(), contextInfo);
        Validator defaultValidator = validatorFactory.getValidator();
        List<ValidationResultInfo> validationResults = defaultValidator.validateObject(cluSetInfo, objStructure,
                contextInfo);
        return validationResults;
    }

    @Override
    @Transactional(readOnly = false)
    public CluSetInfo createCluSet(String cluSetTypeKey, CluSetInfo cluSetInfo, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException,
            UnsupportedActionException, AlreadyExistsException {

        checkForMissingParameter(cluSetTypeKey, "cluSetType");
        checkForMissingParameter(cluSetInfo, "cluSetInfo");

        cluSetInfo.setTypeKey(cluSetTypeKey);

        validateCluSet(cluSetInfo);

        // Validate CluSet
        List<ValidationResultInfo> val;

        try {
        	val = validateCluSet("SYSTEM", cluSetTypeKey,cluSetInfo,contextInfo);
        } catch (DoesNotExistException e) {
        	throw new DataValidationErrorException("Validation error! " + e.getMessage());
        }
        if(null != val && val.size() > 0) {
        	throw new DataValidationErrorException("Validation error!", val);
        }

        List<String> cluIdList = getMembershipQuerySearchResult(cluSetInfo.getMembershipQuery());

        CluSet cluSet = null;
        try {
            cluSet = LuServiceAssembler.toCluSetEntity(R1R2ConverterUtil.convert(cluSetInfo, org.kuali.student.r1.lum.lu.dto.CluSetInfo.class), this.luDao);
        } catch (DoesNotExistException e) {
            throw new DataValidationErrorException("Creating CluSet entity failed. Clu or CluSet does not exist: "
                    + e.getMessage());
        }

        cluSet = luDao.create(cluSet);

        CluSetInfo newCluSetInfo = R1R2ConverterUtil.convert(LuServiceAssembler.toCluSetInfo(cluSet), CluSetInfo.class);

        if (cluIdList != null) {
            newCluSetInfo.getCluIds().addAll(cluIdList);
        }

        return newCluSetInfo;
    }

    private void setMembershipQuerySearchResult(CluSetInfo cluSetInfo) throws MissingParameterException {
        if (cluSetInfo.getMembershipQuery() == null) {
            return;
        }
        List<String> cluIds = getMembershipQuerySearchResult(cluSetInfo.getMembershipQuery());
        cluSetInfo.getCluIds().addAll(cluIds);
    }

    private List<String> getMembershipQuerySearchResult(MembershipQueryInfo query) throws MissingParameterException {
        if (query == null) {
            return null;
        }
        SearchRequest sr = new SearchRequest();
        sr.setSearchKey(query.getSearchTypeKey());
        sr.setParams(R1R2ConverterUtil.convertLists(query.getQueryParamValues(), org.kuali.student.r1.common.search.dto.SearchParam.class));

        SearchResult result = search(sr);

        Set<String> cluIds = new HashSet<String>();
        List<SearchResultRow> rows = result.getRows();
        for (SearchResultRow row : rows) {
            List<SearchResultCell> cells = row.getCells();
            for (SearchResultCell cell : cells) {
                if (cell.getKey().equals("lu.resultColumn.luOptionalVersionIndId") && cell.getValue() != null) {
                    cluIds.add(cell.getValue());
                }
            }
        }
        return new ArrayList<String>(cluIds);
    }

    private void validateCluSet(CluSetInfo cluSetInfo) throws UnsupportedActionException {
        MembershipQueryInfo mqInfo = cluSetInfo.getMembershipQuery();

        if (cluSetInfo.getTypeKey() == null) {
            throw new UnsupportedActionException("CluSet type cannot be null. CluSet id=" + cluSetInfo.getId());
        } else if (mqInfo != null && mqInfo.getSearchTypeKey() != null && !mqInfo.getSearchTypeKey().isEmpty() &&
                (cluSetInfo.getCluIds().size() > 0 || cluSetInfo.getCluSetIds().size() > 0)) {
            throw new UnsupportedActionException("Dynamic CluSet cannot contain Clus and/or CluSets. CluSet id="
                    + cluSetInfo.getId());
        } else if (cluSetInfo.getCluIds().size() > 0 && cluSetInfo.getCluSetIds().size() > 0) {
            throw new UnsupportedActionException("CluSet cannot contain both Clus and CluSets. CluSet id="
                    + cluSetInfo.getId());
        }
    }

    @Override
    @Transactional(readOnly = false)
    public CluSetInfo updateCluSet(String cluSetId, CluSetInfo cluSetInfo, ContextInfo contextInfo)
            throws CircularRelationshipException, DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException,
            ReadOnlyException, UnsupportedActionException, VersionMismatchException {

        // Check Missing params
        checkForMissingParameter(cluSetId, "cluSetId");
        checkForMissingParameter(cluSetInfo, "cluSetInfo");

        // Validate CluSet
         List<ValidationResultInfo> val = validateCluSet("SYSTEM", cluSetInfo.getTypeKey(),cluSetInfo,contextInfo);
        if(null != val && val.size() > 0) {
        	throw new DataValidationErrorException("Validation error!", val);
        } 

        cluSetInfo.setId(cluSetId);

        validateCluSet(cluSetInfo);

        List<String> cluIdList = getMembershipQuerySearchResult(cluSetInfo.getMembershipQuery());

        CluSet cluSet = luDao.fetch(CluSet.class, cluSetId);

        if (!cluSetInfo.getTypeKey().equals(cluSet.getType())) {
            throw new UnsupportedActionException(
                    "CluSet type is set at creation time and cannot be updated. CluSet id=" + cluSetId);
        }

        if (!String.valueOf(cluSet.getVersionNumber()).equals(
                cluSetInfo.getMeta().getVersionInd())) {
            throw new VersionMismatchException(
                    "CluSet (id=" + cluSetId +
                            ") to be updated is not the current version " +
                            "(version=" + cluSetInfo.getMeta().getVersionInd() +
                            "), current version=" + cluSet.getVersionNumber());
        }

        // update the cluIds
        Map<String, CluSetJoinVersionIndClu> oldClus = new HashMap<String, CluSetJoinVersionIndClu>();
        for (CluSetJoinVersionIndClu join : cluSet.getCluVerIndIds()) {
            oldClus.put(join.getCluVersionIndId(), join);
        }

        cluSet.getCluVerIndIds().clear();
        // Loop through the new list, if the item exists already update and remove from the list otherwise create a new entry
        for (String newCluId : cluSetInfo.getCluIds()) {
            CluSetJoinVersionIndClu join = oldClus.remove(newCluId);
            if (join == null) {
                join = new CluSetJoinVersionIndClu();
                join.setCluSet(cluSet);
                join.setCluVersionIndId(newCluId);
            }
            cluSet.getCluVerIndIds().add(join);
        }

        // Now delete anything left over
        for (Entry<String, CluSetJoinVersionIndClu> entry : oldClus.entrySet()) {
            luDao.delete(entry.getValue());
        }

        // clean up existing wrappers if any
        if (cluSetInfo.getId() != null) {
            CluSetInfo originalCluSet = getCluSet(cluSetInfo.getId(), contextInfo);
            List<CluSetInfo> origSubCSs = null;
            List<String> origSubCSIds = originalCluSet.getCluSetIds();
            if (origSubCSIds != null && !origSubCSIds.isEmpty()) {
            	origSubCSs = getCluSetsByIds(origSubCSIds, contextInfo);
            }
            if (origSubCSs != null) {
                for (CluSetInfo origSubCS : origSubCSs) {
                    if (!origSubCS.getIsReusable()) {
                        deleteCluSet(origSubCS.getId(), contextInfo);
                    }
                }
            }
        }

        // update the cluSetIds
        if (cluSet.getCluSets() == null) {
            cluSet.setCluSets(new ArrayList<CluSet>());
        }
        cluSet.setCluSets(null);
        if (!cluSetInfo.getCluSetIds().isEmpty()) {
            Set<String> newCluSetIds = new HashSet<String>(cluSetInfo.getCluSetIds());
            if (cluSet.getCluSets() != null) {
                for (Iterator<CluSet> i = cluSet.getCluSets().iterator(); i.hasNext();) {
                    if (!newCluSetIds.remove(i.next().getId())) {
                        i.remove();
                    }
                }
            }
            List<CluSet> cluSetList = luDao.getCluSetInfoByIdList(new ArrayList<String>(newCluSetIds));
            cluSet.setCluSets(cluSetList);
        }

        BeanUtils.copyProperties(cluSetInfo, cluSet, new String[]{"descr",
                "attributes", "meta", "membershipQuery"});
        cluSet.setAttributes(LuServiceAssembler.toGenericAttributes(
        		CluSetAttribute.class, R1R2ConverterUtil.convert(cluSetInfo, org.kuali.student.r1.lum.lu.dto.CluSetInfo.class).getAttributes(), cluSet, luDao)); 
        cluSet.setDescr(LuServiceAssembler.toRichText(LuRichText.class, R1R2ConverterUtil.convert(cluSetInfo, org.kuali.student.r1.lum.lu.dto.CluSetInfo.class).getDescr()));

        MembershipQuery mq = LuServiceAssembler.toMembershipQueryEntity(R1R2ConverterUtil.convert(cluSetInfo, org.kuali.student.r1.lum.lu.dto.CluSetInfo.class).getMembershipQuery());
        cluSet.setMembershipQuery(mq);

        CluSet updated = luDao.update(cluSet);

        CluSetInfo updatedCluSetInfo = R1R2ConverterUtil.convert(LuServiceAssembler.toCluSetInfo(updated), CluSetInfo.class);

        if (cluIdList != null) {
            updatedCluSetInfo.getCluIds().addAll(cluIdList);
        }

        return updatedCluSetInfo;
    }

    @Override
    @Transactional(readOnly = false)
    public StatusInfo deleteCluSet(String cluSetId, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        checkForMissingParameter(cluSetId, "cluSetId");

        luDao.delete(CluSet.class, cluSetId);

        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setSuccess(true);

        return statusInfo;
    }

    @Override
    @Transactional(readOnly = false)
    public StatusInfo addCluSetToCluSet(String cluSetId, String addedCluSetId, ContextInfo contextInfo)
            throws CircularRelationshipException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, UnsupportedActionException {
        checkForMissingParameter(cluSetId, "cluSetId");
        checkForMissingParameter(addedCluSetId, "addedCluSetId");

        CluSet cluSet = luDao.fetch(CluSet.class, cluSetId);

        checkCluSetAlreadyAdded(cluSet, addedCluSetId);

        CluSet addedCluSet = luDao.fetch(CluSet.class, addedCluSetId);

        checkCluSetCircularReference(addedCluSet, cluSetId);

        if (cluSet.getCluSets() == null) {
            cluSet.setCluSets(new ArrayList<CluSet>());
        }
        cluSet.getCluSets().add(addedCluSet);

        luDao.update(cluSet);

        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setSuccess(true);

        return statusInfo;
    }

    @Override
    @Transactional(readOnly = false)
    public StatusInfo removeCluSetFromCluSet(String cluSetId, String removedCluSetId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, UnsupportedActionException {

        checkForMissingParameter(cluSetId, "cluSetId");
        checkForMissingParameter(removedCluSetId, "removedCluSetId");

        CluSet cluSet = luDao.fetch(CluSet.class, cluSetId);
        if (cluSet.getCluSets() != null) {
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
        }

        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setSuccess(false);
        statusInfo.setMessage("CluSet does not contain CluSet:"
                + removedCluSetId);

        return statusInfo;
    }

    @Override
    @Transactional(readOnly = false)
    public StatusInfo addCluToCluSet(String cluId, String cluSetId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, UnsupportedActionException {

        checkForMissingParameter(cluId, "cluId");
        checkForMissingParameter(cluSetId, "cluSetId");

        CluSet cluSet = luDao.fetch(CluSet.class, cluSetId);

        checkCluAlreadyAdded(cluSet, cluId);

        try {
            luDao.getCurrentCluVersionInfo(cluId, CluServiceConstants.CLU_NAMESPACE_URI);
        } catch (NoResultException e) {
            throw new DoesNotExistException();
        }

        CluSetJoinVersionIndClu join = new CluSetJoinVersionIndClu();
        join.setCluSet(cluSet);
        join.setCluVersionIndId(cluId);

        cluSet.getCluVerIndIds().add(join);

        luDao.update(cluSet);

        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setSuccess(true);

        return statusInfo;
    }

    @Override
    @Transactional(readOnly = false)
    public StatusInfo removeCluFromCluSet(String cluId, String cluSetId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, UnsupportedActionException {

        checkForMissingParameter(cluId, "cluId");
        checkForMissingParameter(cluSetId, "cluSetId");

        CluSet cluSet = luDao.fetch(CluSet.class, cluSetId);

        for (Iterator<CluSetJoinVersionIndClu> i = cluSet.getCluVerIndIds().iterator(); i.hasNext();) {
            CluSetJoinVersionIndClu join = i.next();
            if (join.getCluVersionIndId().equals(cluId)) {
                i.remove();
                luDao.delete(join);
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

    @Override
    public List<org.kuali.student.r2.lum.clu.dto.CluInfo> getRelatedClusByCluId(String id, String courseActivityRelationType) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {
        //To change body of implemented methods use File | Settings | File Templates.

        checkForMissingParameter(id, "cluId");
        checkForMissingParameter(courseActivityRelationType, "luLuRelationTypeKey");
        List<Clu> relatedClus = luDao.getRelatedClusByCluId(id,
                courseActivityRelationType);
        return R1R2ConverterUtil.convertLists( LuServiceAssembler.toCluInfos(relatedClus), org.kuali.student.r2.lum.clu.dto.CluInfo.class);
       // return null;
    }

    @Override
    public CluSetInfo getCluSetInfo(String cluSetId, ContextInfo contextInfo)throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        checkForMissingParameter(cluSetId, "cluSetId");
        CluSet cluSet = luDao.fetch(CluSet.class, cluSetId);
        CluSetInfo cluSetInfo = R1R2ConverterUtil.convert(LuServiceAssembler.toCluSetInfo(cluSet),CluSetInfo.class);
        setMembershipQuerySearchResult(cluSetInfo);
        return cluSetInfo;
    }

    private void checkCluAlreadyAdded(CluSet cluSet, String cluId)
            throws OperationFailedException {
        for (CluSetJoinVersionIndClu join : cluSet.getCluVerIndIds()) {
            if (join.getCluVersionIndId().equals(cluId)) {
                throw new OperationFailedException("CluSet already contains Clu (id='" + cluId + "')");
            }
        }
    }

    private void checkCluSetAlreadyAdded(CluSet cluSet, String cluSetIdToAdd)
            throws OperationFailedException {
        if (cluSet.getCluSets() != null) {
            for (CluSet childCluSet : cluSet.getCluSets()) {
                if (childCluSet.getId().equals(cluSetIdToAdd)) {
                    throw new OperationFailedException("CluSet (id=" + cluSet.getId() +
                            ") already contains CluSet (id='" + cluSetIdToAdd + "')");
                }
            }
        }
    }

    private void checkCluSetCircularReference(CluSet addedCluSet, String hostCluSetId)
            throws CircularRelationshipException {
        if (addedCluSet.getId().equals(hostCluSetId)) {
            throw new CircularRelationshipException(
                    "Cannot add a CluSet (id=" + hostCluSetId + ") to ifself");
        }
        if (addedCluSet.getCluSets() != null) {
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
    }

    private void findClusInCluSet(List<String> clus, CluSet parentCluSet)
            throws DoesNotExistException {
        List<String> processedCluSetIds = new ArrayList<String>();
        doFindClusInCluSet(processedCluSetIds, clus, parentCluSet);
    }

    private void doFindClusInCluSet(List<String> processedCluSetIds,
            List<String> clus, CluSet parentCluSet) {
        for (CluSetJoinVersionIndClu join : parentCluSet.getCluVerIndIds()) {
            if (!clus.contains(join.getCluVersionIndId())) {
                clus.add(join.getCluVersionIndId());
            }
        }
        if (parentCluSet.getCluSets() != null) {
            for (CluSet cluSet : parentCluSet.getCluSets()) {
                // This condition avoids infinite recursion problem
                if (!processedCluSetIds.contains(cluSet.getId())) {
                    processedCluSetIds.add(cluSet.getId());
                    doFindClusInCluSet(processedCluSetIds, clus, cluSet);
                }
            }
        }
    }

    @Override
    public ObjectStructureDefinition getObjectStructure(String objectTypeKey, ContextInfo contextInfo)
            throws UnsupportedOperationException {
        return dictionaryServiceDelegate.getObjectStructure(objectTypeKey);
    }

    @Override
    public List<String> getObjectTypes(ContextInfo contextInfo) throws UnsupportedOperationException {
        return dictionaryServiceDelegate.getObjectTypes();
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

        if (SEARCH_KEY_DEPENDENCY_ANALYSIS.equals(searchRequest.getSearchKey())) {
            String cluId = null;
            for (SearchParam param : searchRequest.getParams()) {
                if ("lu.queryParam.luOptionalCluId".equals(param.getKey())) {
                    cluId = (String) param.getValue();
                    break;
                }
            }
            try {
                return doDependencyAnalysisSearch(cluId);
            } catch (DoesNotExistException e) {
                throw new RuntimeException("Error performing search");//FIXME should be more checked service exceptions thrown
            }
        } else if (SEARCH_KEY_BROWSE_PROGRAM.equals(searchRequest.getSearchKey())) {
            return doBrowseProgramSearch();
        }
        return searchManager.search(searchRequest, luDao);
    }

    private SearchResult doBrowseProgramSearch() throws MissingParameterException {
        //This is our main result
        SearchResult programSearchResults = searchManager.search(new SearchRequest(SEARCH_KEY_BROWSE_PROGRAM), luDao);

        //These variations need to be mapped back to the program search results
        SearchResult variationSearchResults = searchManager.search(new SearchRequest(SEARCH_KEY_BROWSE_VARIATIONS),
                luDao);

        //Get a mapping of program id to variation long name mapping:
        Map<String, List<String>> variationMapping = new HashMap<String, List<String>>();
        for (SearchResultRow row : variationSearchResults.getRows()) {
            String programId = null;
            String variationLongName = null;
            for (SearchResultCell cell : row.getCells()) {
                if ("lu.resultColumn.cluId".equals(cell.getKey())) {
                    programId = cell.getValue();
                } else if ("lu.resultColumn.luOptionalLongName".equals(cell.getKey())) {
                    variationLongName = cell.getValue();
                }
            }
            List<String> variationLongNames = variationMapping.get(programId);
            if (variationLongNames == null) {
                variationLongNames = new ArrayList<String>();
                variationMapping.put(programId, variationLongNames);
            }
            variationLongNames.add(variationLongName);
        }

        //The result component types need to be mapped back as well
        SearchRequest resultComponentSearchRequest = new SearchRequest(SEARCH_KEY_RESULT_COMPONENT);
        resultComponentSearchRequest
                .addParam("lrc.queryParam.resultComponent.type", "kuali.resultComponentType.degree");
        SearchResult resultComponentSearchResults = searchDispatcher.dispatchSearch(resultComponentSearchRequest);

        //Get a mapping of result type id to result type name:
        Map<String, String> resultComponentMapping = new HashMap<String, String>();
        for (SearchResultRow row : resultComponentSearchResults.getRows()) {
            String resultComponentTypeId = null;
            String resultComponentTypeName = null;
            for (SearchResultCell cell : row.getCells()) {
                if ("lrc.resultColumn.resultComponent.id".equals(cell.getKey())) {
                    resultComponentTypeId = cell.getValue();
                } else if ("lrc.resultColumn.resultComponent.name".equals(cell.getKey())) {
                    resultComponentTypeName = cell.getValue();
                }
            }
            resultComponentMapping.put(resultComponentTypeId, resultComponentTypeName);
        }

        Map<String, Set<SearchResultCell>> orgIdToCellMapping = new HashMap<String, Set<SearchResultCell>>();
        Map<String, Set<SearchResultCell>> resultComponentToCellMapping = new HashMap<String, Set<SearchResultCell>>();
        Map<String, Set<SearchResultCell>> campusToCellMapping = new HashMap<String, Set<SearchResultCell>>();
        Map<String, SearchResultCell> progIdToOrgCellMapping = new HashMap<String, SearchResultCell>();
        Map<String, SearchResultCell> progIdToResultComponentCellMapping = new HashMap<String, SearchResultCell>();
        Map<String, SearchResultCell> progIdToCampusCellMapping = new HashMap<String, SearchResultCell>();

        //We need to reduce the programSearchResults, translating variations, result options, etc and creating a mapping for org id translation
        for (Iterator<SearchResultRow> rowIter = programSearchResults.getRows().iterator(); rowIter.hasNext();) {
            SearchResultRow row = rowIter.next();
            String programId = null;
            String orgId = null;
            String resultComponentName = null;
            String campusCode = null;
            SearchResultCell orgCell = null;
            SearchResultCell resultComponentCell = null;
            SearchResultCell variationCell = null;
            SearchResultCell campusLocationCell = null;

            for (SearchResultCell cell : row.getCells()) {
                if ("lu.resultColumn.cluId".equals(cell.getKey())) {
                    programId = cell.getValue();
                } else if ("lu.resultColumn.luOptionalAdminOrg".equals(cell.getKey())) {
                    orgId = cell.getValue();
                    orgCell = cell;
                } else if ("lu.resultColumn.resultComponentId".equals(cell.getKey())) {
                    resultComponentName = resultComponentMapping.get(cell.getValue());
                    resultComponentCell = cell;
                } else if ("lu.resultColumn.variationId".equals(cell.getKey())) {
                    variationCell = cell;
                } else if ("lu.resultColumn.luOptionalCampusLocation".equals(cell.getKey())) {
                    campusLocationCell = cell;
                    campusCode = cell.getValue();
                }
            }
            if (!progIdToOrgCellMapping.containsKey(programId)) {
                //Add in the Variations
                List<String> variations = variationMapping.get(programId);
                variationCell.setValue("");
                if (variations != null) {
                    for (Iterator<String> variationIter = variations.iterator(); variationIter.hasNext();) {
                        String variation = variationIter.next();
                        if (variationIter.hasNext()) {
                            variation += "<br/>";
                        }
                        variationCell.setValue(variationCell.getValue() + variation);
                    }
                }

                //Add the cell to the org id mapping
                Set<SearchResultCell> orgCells = orgIdToCellMapping.get(orgId);
                if (orgCells == null) {
                    orgCells = new HashSet<SearchResultCell>();
                    orgIdToCellMapping.put(orgId, orgCells);
                }
                orgCells.add(orgCell);
                orgCell.setValue(null);

                //Add this to the map
                Set<SearchResultCell> campusCells = campusToCellMapping.get(campusCode);
                if (campusCells == null) {
                    campusCells = new HashSet<SearchResultCell>();
                    campusToCellMapping.put(campusCode, campusCells);
                }
                campusCells.add(campusLocationCell);
                campusLocationCell.setValue(null);

                //Add this to the map
                Set<SearchResultCell> resultCells = resultComponentToCellMapping.get(resultComponentName);
                if (resultCells == null) {
                    resultCells = new HashSet<SearchResultCell>();
                    resultComponentToCellMapping.put(resultComponentName, resultCells);
                }
                resultCells.add(resultComponentCell);
                resultComponentCell.setValue(null);

                progIdToOrgCellMapping.put(programId, orgCell);
                progIdToResultComponentCellMapping.put(programId, resultComponentCell);
                progIdToCampusCellMapping.put(programId, campusLocationCell);
            } else {
                //this row already exists so we need to concatenate the result component and add the org id
                //Get the result component row
                Set<SearchResultCell> resultCells = resultComponentToCellMapping.get(resultComponentName);
                if (resultCells == null) {
                    resultCells = new HashSet<SearchResultCell>();
                    resultComponentToCellMapping.put(resultComponentName, resultCells);
                }
                resultCells.add(progIdToResultComponentCellMapping.get(programId));

                //Add a new mapping to the org cell for this org id
                Set<SearchResultCell> orgCells = orgIdToCellMapping.get(orgId);
                if (orgCells == null) {
                    orgCells = new HashSet<SearchResultCell>();
                    orgIdToCellMapping.put(orgId, orgCells);
                }
                orgCells.add(progIdToOrgCellMapping.get(programId));

                //Concatenate the campus location
                Set<SearchResultCell> campusCells = campusToCellMapping.get(campusCode);
                if (campusCells == null) {
                    campusCells = new HashSet<SearchResultCell>();
                    campusToCellMapping.put(campusCode, campusCells);
                }
                campusCells.add(progIdToCampusCellMapping.get(programId));

                //Remove this row from results
                rowIter.remove();
            }
        }

        if (!resultComponentToCellMapping.isEmpty()) {
            List<String> resultComponentNames = new ArrayList<String>(resultComponentToCellMapping.keySet());
            Collections.sort(resultComponentNames);
            for (String resultComponentName : resultComponentNames) {
                //Concatenate resultComponent names in the holder cells
                Set<SearchResultCell> cells = resultComponentToCellMapping.get(resultComponentName);
                if (cells != null) {
                    for (SearchResultCell cell : cells) {
                        if (cell.getValue() == null) {
                            cell.setValue(resultComponentName);
                        } else {
                            cell.setValue(cell.getValue() + "<br/>" + resultComponentName);
                        }
                    }
                }
            }
        }

        if (!campusToCellMapping.isEmpty()) {
            List<String> campusCodes = new ArrayList<String>(campusToCellMapping.keySet());
            Collections.sort(campusCodes);
            for (String campusCode : campusCodes) {
                //Concatenate campus code names in the holder cells
                Set<SearchResultCell> cells = campusToCellMapping.get(campusCode);
                if (cells != null) {
                    for (SearchResultCell cell : cells) {
                        if (cell.getValue() == null) {
                            cell.setValue(campusCode);
                        } else {
                            cell.setValue(cell.getValue() + "<br/>" + campusCode);
                        }
                    }
                }
            }
        }

        //Use the org search to Translate the orgIds into Org names and update the holder cells
        if (!orgIdToCellMapping.isEmpty()) {
            //Perform the Org search
            SearchRequest orgIdTranslationSearchRequest = new SearchRequest("org.search.generic");
            orgIdTranslationSearchRequest.addParam("org.queryParam.orgOptionalIds", new ArrayList<String>(
                    orgIdToCellMapping.keySet()));
            orgIdTranslationSearchRequest.setSortColumn("org.resultColumn.orgShortName");
            SearchResult orgIdTranslationSearchResult = searchDispatcher.dispatchSearch(orgIdTranslationSearchRequest);

            //For each translation, update the result cell with the translated org name
            for (SearchResultRow row : orgIdTranslationSearchResult.getRows()) {

                //Get Params
                String orgId = "";
                String orgName = "";
                for (SearchResultCell cell : row.getCells()) {
                    if ("org.resultColumn.orgId".equals(cell.getKey())) {
                        orgId = cell.getValue();
                        continue;
                    } else if ("org.resultColumn.orgShortName".equals(cell.getKey())) {
                        orgName = cell.getValue();
                    }
                }

                //Concatenate org names in the holder cells
                Set<SearchResultCell> cells = orgIdToCellMapping.get(orgId);
                if (cells != null) {
                    for (SearchResultCell cell : cells) {
                        if (cell.getValue() == null) {
                            cell.setValue(orgName);
                        } else {
                            cell.setValue(cell.getValue() + "<br/>" + orgName);
                        }
                    }
                }
            }
        }

        return programSearchResults;
    }

    private SearchResult doDependencyAnalysisSearch(String cluId) throws MissingParameterException,
            DoesNotExistException {

        checkForMissingParameter(cluId, "cluId");

        Clu triggerClu = luDao.fetch(Clu.class, cluId);

        List<String> cluVersionIndIds = new ArrayList<String>();
        cluVersionIndIds.add(triggerClu.getVersion().getVersionIndId());

        //Find all clusets that contain this course
        List<CluSet> cluSets = luDao.getCluSetsByCluVersionIndId(cluVersionIndIds);

        //Get a mapping of clusetId to cluset for easy referencing
        Map<String, CluSet> cluSetMap = new HashMap<String, CluSet>();
        if (cluSets != null) {
            for (CluSet cluSet : cluSets) {
                cluSetMap.put(cluSet.getId(), cluSet);
            }
        }

        //Execute all dynamic queries to see if the target clu is in the cluset and add those clusets
        List<CluSet> dynamicCluSets = luDao.getAllDynamicCluSets();
        if (dynamicCluSets != null) {
            for (CluSet cluSet : dynamicCluSets) {
                MembershipQueryInfo queryInfo = R1R2ConverterUtil.convert(LuServiceAssembler.toMembershipQueryInfo(cluSet.getMembershipQuery()), MembershipQueryInfo.class);
                List<String> memberCluVersionIndIds = getMembershipQuerySearchResult(queryInfo);
                if (memberCluVersionIndIds != null) {
                    for (String cluVersionIndId : cluVersionIndIds) {
                        if (memberCluVersionIndIds.contains(cluVersionIndId)) {
                            cluSetMap.put(cluSet.getId(), cluSet);
                            break;
                        }
                    }
                }
            }
        }
        //TODO Is it possible we need to search up the cluset hierarchies?
        //	If Cluset A contains clu 1 and cluset B contains cluset A, do we also return cluset B as a dependency?

        //Now we have the clu id and the list of clusets that the id appears in,
        //We need to do a statement service search to see what statements use these as 
        //dependencies
        SearchRequest statementSearchRequest = new SearchRequest("stmt.search.dependencyAnalysis");

        statementSearchRequest.addParam("stmt.queryParam.cluSetIds", new ArrayList<String>(cluSetMap.keySet()));
        statementSearchRequest.addParam("stmt.queryParam.cluVersionIndIds", cluVersionIndIds);

        SearchResult statementSearchResult = searchDispatcher.dispatchSearch(statementSearchRequest);

        //Create a search result for the return value
        SearchResult searchResult = new SearchResult();

        Map<String, List<SearchResultCell>> orgIdToCellMapping = new HashMap<String, List<SearchResultCell>>();

        //Now we need to take the statement ids and find the clus that relate to them
        //We will also transform the search result from the statement search result to 
        //the dependency analysis search result
        Set<String> processed = new HashSet<String>();
        for (SearchResultRow stmtRow : statementSearchResult.getRows()) {

            //Determine result column values
            String refObjId = null;
            String statementType = null;
            String statementTypeName = null;
            String rootId = null;
            String requirementComponentIds = null;

            for (SearchResultCell stmtCell : stmtRow.getCells()) {
                if ("stmt.resultColumn.refObjId".equals(stmtCell.getKey())) {
                    refObjId = stmtCell.getValue();
                    continue;
                } else if ("stmt.resultColumn.statementTypeId".equals(stmtCell.getKey())) {
                    statementType = stmtCell.getValue();
                    continue;
                } else if ("stmt.resultColumn.statementTypeName".equals(stmtCell.getKey())) {
                    statementTypeName = stmtCell.getValue();
                    continue;
                } else if ("stmt.resultColumn.rootId".equals(stmtCell.getKey())) {
                    rootId = stmtCell.getValue();
                    continue;
                } else if ("stmt.resultColumn.requirementComponentIds".equals(stmtCell.getKey())) {
                    requirementComponentIds = stmtCell.getValue();
                }
            }

            //Find the clu
            Clu clu = luDao.fetch(Clu.class, refObjId);

            //Program statements are attached to dummy clus, so look up the parent program
            if ("kuali.lu.type.Requirement".equals(clu.getLuType().getId())) {

                List<Clu> clus = luDao.getClusByRelatedCluId(clu.getId(),
                        "kuali.lu.lu.relation.type.hasProgramRequirement");

                rootId = clu.getId();

                if (clus == null || clus.size() == 0) {
                    throw new RuntimeException("Statement Dependency clu found, but no parent Program exists");
                } else if (clus.size() > 1) {
                    throw new RuntimeException("Statement Dependency clu can only have one parent Program relation");
                }
                clu = clus.get(0);
            }

            //Only process clus that are not active and that we have not already processed
            String rowId = clu.getId() + "|" + statementType + "|" + rootId;

            if ("Active".equals(clu.getState()) && !processed.contains(rowId)) {

                processed.add(rowId);

                SearchResultRow resultRow = new SearchResultRow();

                //Map the result cells
                resultRow.addCell("lu.resultColumn.cluId", clu.getId());
                resultRow.addCell("lu.resultColumn.cluType", clu.getLuType().getId());
                resultRow.addCell("lu.resultColumn.luOptionalCode", clu.getOfficialIdentifier().getCode());
                resultRow.addCell("lu.resultColumn.luOptionalShortName", clu.getOfficialIdentifier().getShortName());
                resultRow.addCell("lu.resultColumn.luOptionalLongName", clu.getOfficialIdentifier().getLongName());
                resultRow.addCell("lu.resultColumn.luOptionalDependencyType", statementType);
                resultRow.addCell("lu.resultColumn.luOptionalDependencyTypeName", statementTypeName);
                resultRow.addCell("lu.resultColumn.luOptionalDependencyRootId", rootId);
                resultRow.addCell("lu.resultColumn.luOptionalDependencyRequirementComponentIds",
                        requirementComponentIds);

                //Make a holder cell for the org names, to be populated later
                SearchResultCell orgIdsCell = new SearchResultCell("lu.resultColumn.luOptionalOversightCommitteeIds",
                        null);
                resultRow.getCells().add(orgIdsCell);

                //Make a holder cell for the org ids, to be populated later
                SearchResultCell orgNamesCell = new SearchResultCell(
                        "lu.resultColumn.luOptionalOversightCommitteeNames", null);
                resultRow.getCells().add(orgNamesCell);

                //For each curriculum oversight committee we want to look up the Org Name
                //We're going to save a mapping of the org id to a holder cell so we can make just one org 
                //service call with all the org ids, and update the holder cells later.
                boolean differentAdminOrg = true;
                for (CluAdminOrg adminOrg : clu.getAdminOrgs()) {
                    if ("kuali.adminOrg.type.CurriculumOversight".equals(adminOrg.getType()) ||
                            "kuali.adminOrg.type.CurriculumOversightUnit".equals(adminOrg.getType())) {

                        //Add the cell to the mapping for that perticular org id
                        List<SearchResultCell> cells = orgIdToCellMapping.get(adminOrg.getOrgId());
                        if (cells == null) {
                            cells = new ArrayList<SearchResultCell>();
                            orgIdToCellMapping.put(adminOrg.getOrgId(), cells);
                        }
                        cells.add(orgNamesCell);

                        //Add the orgid to the orgIds cell so there is a comma delimited list of org ids
                        if (orgIdsCell.getValue() == null) {
                            orgIdsCell.setValue(adminOrg.getId());
                        } else {
                            orgIdsCell.setValue(orgIdsCell.getValue() + "," + adminOrg.getId());
                        }

                        for (CluAdminOrg triggerAdminOrg : triggerClu.getAdminOrgs()) {
                            if (triggerAdminOrg.getOrgId().equals(adminOrg.getOrgId())) {
                                differentAdminOrg = false;
                            }
                        }
                    }
                }
                resultRow.addCell("lu.resultColumn.luOptionalDependencyRequirementDifferentAdminOrg",
                        String.valueOf(differentAdminOrg));

                //Add the result row
                searchResult.getRows().add(resultRow);
            }
        }

        //Use the org search to Translate the orgIds into Org names and update the holder cells
        if (!orgIdToCellMapping.isEmpty()) {
            //Perform the Org search
            SearchRequest orgIdTranslationSearchRequest = new SearchRequest("org.search.generic");
            orgIdTranslationSearchRequest.addParam("org.queryParam.orgOptionalIds", new ArrayList<String>(
                    orgIdToCellMapping.keySet()));
            SearchResult orgIdTranslationSearchResult = searchDispatcher.dispatchSearch(orgIdTranslationSearchRequest);

            //For each translation, update the result cell with the translated org name
            for (SearchResultRow row : orgIdTranslationSearchResult.getRows()) {

                //Get Params
                String orgId = "";
                String orgName = "";
                for (SearchResultCell cell : row.getCells()) {
                    if ("org.resultColumn.orgId".equals(cell.getKey())) {
                        orgId = cell.getValue();
                        continue;
                    } else if ("org.resultColumn.orgShortName".equals(cell.getKey())) {
                        orgName = cell.getValue();
                    }
                }

                //Concatenate org names in the holder cells
                List<SearchResultCell> cells = orgIdToCellMapping.get(orgId);
                if (cells != null) {
                    for (SearchResultCell cell : cells) {
                        if (cell.getValue() == null) {
                            cell.setValue(orgName);
                        } else {
                            cell.setValue(cell.getValue() + ", " + orgName);
                        }
                    }
                }
            }
        }

        //Add in CluSets and ignore ones named AdHoc
        for (CluSet cluSet : cluSetMap.values()) {
            if (!"AdHock".equals(cluSet.getName())) {

                SearchResultRow resultRow = new SearchResultRow();

                resultRow.addCell("lu.resultColumn.cluId", cluSet.getId());
                resultRow.addCell("lu.resultColumn.luOptionalShortName", cluSet.getName());
                resultRow.addCell("lu.resultColumn.luOptionalLongName", cluSet.getName());
                resultRow.addCell("lu.resultColumn.luOptionalDependencyType", "cluSet");
                resultRow.addCell("lu.resultColumn.luOptionalDependencyTypeName", "Course Set");

                searchResult.getRows().add(resultRow);
            }
        }

        //Get any joints here and add them into the results
        List<Clu> joints = luDao.getClusByRelation(cluId, "kuali.lu.relation.type.co-located");
        if (joints != null) {
            for (Clu clu : joints) {

                SearchResultRow resultRow = new SearchResultRow();

                resultRow.addCell("lu.resultColumn.cluId", clu.getId());
                resultRow.addCell("lu.resultColumn.luOptionalCode", clu.getOfficialIdentifier().getCode());
                resultRow.addCell("lu.resultColumn.luOptionalShortName", clu.getOfficialIdentifier().getShortName());
                resultRow.addCell("lu.resultColumn.luOptionalLongName", clu.getOfficialIdentifier().getLongName());
                resultRow.addCell("lu.resultColumn.luOptionalDependencyType", "joint");
                resultRow.addCell("lu.resultColumn.luOptionalDependencyTypeName", "jointly offered");

                searchResult.getRows().add(resultRow);
            }
        }

        //Lookup cross-listings and add to the results
        for (CluIdentifier altId : triggerClu.getAlternateIdentifiers()) {
            if ("kuali.lu.type.CreditCourse.identifier.crosslisting".equals(altId.getType())) {
                SearchResultRow resultRow = new SearchResultRow();

                resultRow.addCell("lu.resultColumn.luOptionalCode", altId.getCode());
                resultRow.addCell("lu.resultColumn.luOptionalShortName", altId.getShortName());
                resultRow.addCell("lu.resultColumn.luOptionalLongName", altId.getLongName());
                resultRow.addCell("lu.resultColumn.luOptionalDependencyType", "crossListed");
                resultRow.addCell("lu.resultColumn.luOptionalDependencyTypeName", "cross-listed");

                searchResult.getRows().add(resultRow);
            }
        }

        //Sort results by Code
        Collections.sort(searchResult.getRows(), new SearchResultRowComparator("lu.resultColumn.luOptionalCode"));

        return searchResult;
    }

    public class SearchResultRowComparator implements Comparator<SearchResultRow> {
        private String sortColumn;

        SearchResultRowComparator(String sortColumn) {
            super();
            this.sortColumn = sortColumn;
        }

        @Override
        public int compare(SearchResultRow o1, SearchResultRow o2) {
            String o1SortValue = null;
            String o2SortValue = null;
            for (SearchResultCell cell : o1.getCells()) {
                if (sortColumn.equals(cell.getKey())) {
                    o1SortValue = cell.getValue();
                    break;
                }
            }
            for (SearchResultCell cell : o2.getCells()) {
                if (sortColumn.equals(cell.getKey())) {
                    o2SortValue = cell.getValue();
                    break;
                }
            }
            if (o1SortValue != null) {
                if (o2SortValue == null) {
                    return 1;
                }
                return o1SortValue.compareTo(o2SortValue);
            }
            if (o2SortValue == null) {
                return 0;
            }
            return -1;
        }

    }

    /**
     * Check for missing parameter and throw localized exception if missing
     * 
     * @param param
     * @param paramName name
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
    @Transactional(readOnly = false)
    public StatusInfo addCluSetsToCluSet(String cluSetId, List<String> addedCluSetIds, ContextInfo contextInfo)
            throws CircularRelationshipException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, UnsupportedActionException {

        checkForMissingParameter(cluSetId, "cluSetId");
        checkForMissingParameter(addedCluSetIds, "cluSetIdList");

        // Check that CluSet exists
        luDao.fetch(CluSet.class, cluSetId);

        for (String cluSetIdToAdd : addedCluSetIds) {
            StatusInfo status = addCluSetToCluSet(cluSetId, cluSetIdToAdd, contextInfo);
            if (!status.getIsSuccess()) {
                return status;
            }
        }

        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setSuccess(true);

        return statusInfo;
    }

    @Override
    @Transactional(readOnly = false)
    public StatusInfo addClusToCluSet(List<String> cluSetIds, String cluSetId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, UnsupportedActionException {

        checkForMissingParameter(cluSetIds, "cluIdList");
        checkForMissingParameter(cluSetId, "cluSetId");

        for (String cluId : cluSetIds) {
            StatusInfo status = addCluToCluSet(cluId, cluSetId, contextInfo);
            if (!status.getIsSuccess()) {
                return status;
            }
        }

        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setSuccess(true);

        return statusInfo;
    }

    public ValidatorFactory getValidatorFactory() {
        return validatorFactory;
    }

    public void setValidatorFactory(ValidatorFactory validatorFactory) {
        this.validatorFactory = validatorFactory;
    }

    /********* Versioning Methods ***************************/

    @Override
    @Transactional(readOnly = false)
    public CluInfo createNewCluVersion(String cluId, String versionComment, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException,
            VersionMismatchException {
        Clu latestClu;
        Clu currentClu;
        try {
            latestClu = luDao.getLatestCluVersion(cluId); 
        } catch (NoResultException e) {
            throw new DoesNotExistException("There are no matching versions of this clu", e);
        }
        try {
            currentClu = luDao.getCurrentCluVersion(cluId); 
        } catch (NoResultException e) {
            throw new DoesNotExistException(
                    "There is no current version of this clu. Only current clus can be versioned. Use setCurrentCluVersion to make a clu current.",
                    e);
        }

        CluInfo cluInfo = R1R2ConverterUtil.convert(LuServiceAssembler.toCluInfo(currentClu), CluInfo.class);

        // Reset the Clu
        clearCluIds(cluInfo);

        // Create the new Clu Version	    
        CluInfo newClu = null;

        try {
            Clu clu = toCluForCreate(cluInfo.getTypeKey(), cluInfo, contextInfo);
            //Set the Version data
            Version version = new Version();
            version.setSequenceNumber(latestClu.getVersion().getSequenceNumber() + 1);
            version.setVersionIndId(cluId);
            version.setCurrentVersionStart(null);
            version.setCurrentVersionEnd(null);
            version.setVersionComment(versionComment);
            version.setVersionedFromId(currentClu.getId());
            clu.setVersion(version);
            luDao.create(clu);
            newClu = R1R2ConverterUtil.convert(LuServiceAssembler.toCluInfo(clu), CluInfo.class);
        } catch (AlreadyExistsException e) {
            throw new OperationFailedException("Error creating a new clu version", e);
        }

        return newClu;
    }

    private void clearCluIds(CluInfo clu) {
        // Clear out all ids so a copy can be made
        clu.setStateKey(DtoConstants.STATE_DRAFT);// TODO check if this should be set from outside
        clu.setId(null);

        if (clu.getAccountingInfo() != null) {
            clu.getAccountingInfo().setId(null);

            for (AffiliatedOrgInfo affiliatedOrg : clu.getAccountingInfo().getAffiliatedOrgs()) {
                affiliatedOrg.setId(null);
            }
        }
        for (AccreditationInfo accredation : clu.getAccreditations()) {
            accredation.setId(null);
        }
        for (AdminOrgInfo adminOrg : clu.getAdminOrgs()) {
            adminOrg.setId(null);
        }
        for (CluIdentifierInfo alternateIdentifier : clu.getAlternateIdentifiers()) {
            alternateIdentifier.setId(null);
        }
        if (clu.getFeeInfo() != null) {
            clu.getFeeInfo().setId(null);
            for (CluFeeRecordInfo cluFeeRecord : clu.getFeeInfo().getCluFeeRecords()) {
                cluFeeRecord.setId(null);
                for (AffiliatedOrgInfo affiliatedOrg : cluFeeRecord.getAffiliatedOrgs()) {
                    affiliatedOrg.setId(null);
                }
                for (CurrencyAmountInfo feeAmount : cluFeeRecord.getFeeAmounts()) {
                    feeAmount.setId(null);
                }
            }
        }
        for (LuCodeInfo luCode : clu.getLuCodes()) {
            luCode.setId(null);
        }
        if (clu.getOfficialIdentifier() != null) {
            clu.getOfficialIdentifier().setId(null);
        }
    }

    /**
     * This method sets the CLU with ID of cluVersionId as the current version and will
     * set the version end date of the previously current version to currentVersionStart
     * or now() if null. This will NOT update state of the current or previously current
     * CLU. All state changes must be handled either by the business service or from the
     * client application.
     * 
     * @param currentVersionStart if set to null, will default the current version start
     * to the time when this method is called. You can set this to a future date as well.
     */
    @Override
    @Transactional(readOnly = false)
    public StatusInfo setCurrentCluVersion(String cluVersionId, Date currentVersionStart, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, IllegalVersionSequencingException, OperationFailedException,
            PermissionDeniedException {
        //Check params
        Date currentDbDate = new Date();//FIXME, this should be DB time
        if (currentVersionStart != null && currentVersionStart.compareTo(currentDbDate) < 0) {
            throw new InvalidParameterException("currentVersionStart must be in the future.");
        }
        //Default the currentVersionStart to the current date
        if (currentVersionStart == null) {
            currentVersionStart = currentDbDate;
        }

        //get the clu we are setting as current 
        Clu clu = luDao.fetch(Clu.class, cluVersionId);
        String versionIndId = clu.getVersion().getVersionIndId();

        Clu oldClu = null;
        try {
            oldClu = luDao.getCurrentCluVersion(versionIndId);
        } catch (NoResultException e) {}

        //Check that the clu you are trying to version has a sequence number greater than the current clu
        if (oldClu != null) {
            if (clu.getVersion().getSequenceNumber() <= oldClu.getVersion().getSequenceNumber()) {
                throw new OperationFailedException("Clu to make current must have been versioned from the current Clu");
            }
        } else {
            //Ignore the start date set if this is the first version (it will be set to the current time to avoid weird time problems)
            currentVersionStart = currentDbDate;
        }

        //Get any clus that are set to become current in the future, and clear their current dates
        List<VersionDisplayInfo> versionsInFuture = R1R2ConverterUtil.convertLists(luDao.getVersionsInDateRange(versionIndId, null, currentDbDate, null), VersionDisplayInfo.class);
        		for(VersionDisplayInfo versionInFuture:versionsInFuture){
        			if(oldClu==null || !versionInFuture.getId().equals(oldClu.getId())){
        				VersionEntity futureClu = luDao.fetch(Clu.class, versionInFuture.getId());
        				futureClu.getVersion().setCurrentVersionStart(null);
        				futureClu.getVersion().setCurrentVersionEnd(null);
        				futureClu = luDao.update(futureClu);
        			}
        		}

        //If there is a current clu, set its end date to the new clu's start date
        if (oldClu != null) {
            oldClu.getVersion().setCurrentVersionEnd(currentVersionStart);
            oldClu = luDao.update(oldClu);
        }

        //Set the startdate of the new current clu
        clu.getVersion().setCurrentVersionStart(currentVersionStart);
        clu.getVersion().setCurrentVersionEnd(null);
        clu = luDao.update(clu);

        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setSuccess(true);
        return statusInfo;
    }

    @Override
    public VersionDisplayInfo getLatestVersion(String refObjectUri, String refObjectId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        VersionDisplayInfo versionInfo = null;
        if (CluServiceConstants.CLU_NAMESPACE_URI.equals(refObjectUri)) {
            try {
                versionInfo = R1R2ConverterUtil.convert(luDao.getLatestVersion(refObjectId, refObjectUri), VersionDisplayInfo.class);
            } catch (NoResultException e) {
                throw new DoesNotExistException();
            }
        } else {
            throw new UnsupportedOperationException("This method does not know how to handle object type:"
                    + refObjectUri);
        }
        return versionInfo;
    }

    @Override
    public VersionDisplayInfo getCurrentVersion(String refObjectUri, String refObjectId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        VersionDisplayInfo versionInfo = null;
        if (CluServiceConstants.CLU_NAMESPACE_URI.equals(refObjectUri)) {
            try {
 
                versionInfo = R1R2ConverterUtil.convert(luDao.getCurrentCluVersionInfo(refObjectId, refObjectUri), VersionDisplayInfo.class);
            } catch (NoResultException e) {
                throw new DoesNotExistException();
            }
        } else {
            throw new UnsupportedOperationException("This method does not know how to handle object type:"
                    + refObjectUri);
        }
        return versionInfo;
    }

    @Override
    public VersionDisplayInfo getCurrentVersionOnDate(String refObjectUri, String refObjectId, Date date,
            ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        VersionDisplayInfo versionInfo = null;
        if (CluServiceConstants.CLU_NAMESPACE_URI.equals(refObjectUri)) {
            try {
                versionInfo = R1R2ConverterUtil.convert(luDao.getCurrentVersionOnDate(refObjectId, refObjectUri, date), VersionDisplayInfo.class);
            } catch (NoResultException e) {
                throw new DoesNotExistException();
            }
        } else {
            throw new UnsupportedOperationException("This method does not know how to handle object type:"
                    + refObjectUri);
        }
        return versionInfo;
    }

    @Override
    public VersionDisplayInfo getFirstVersion(String refObjectUri, String refObjectId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        VersionDisplayInfo versionInfo = null;
        if (CluServiceConstants.CLU_NAMESPACE_URI.equals(refObjectUri)) {
            try { 
                versionInfo = R1R2ConverterUtil.convert(luDao.getFirstVersion(refObjectId, refObjectUri), VersionDisplayInfo.class);
            } catch (NoResultException e) {
                throw new DoesNotExistException();
            }
        } else {
            throw new UnsupportedOperationException("This method does not know how to handle object type:"
                    + refObjectUri);
        }
        return versionInfo;
    }

    @Override
    public VersionDisplayInfo getVersionBySequenceNumber(String refObjectUri, String refObjectId, Long sequence,
            ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        VersionDisplayInfo versionInfo = null;
        if (CluServiceConstants.CLU_NAMESPACE_URI.equals(refObjectUri)) {
            try {
                versionInfo = R1R2ConverterUtil.convert(luDao.getVersionBySequenceNumber(refObjectId, refObjectUri, sequence), VersionDisplayInfo.class);
            } catch (NoResultException e) {
                throw new DoesNotExistException();
            }
        } else {
            throw new UnsupportedOperationException("This method does not know how to handle object type:"
                    + refObjectUri);
        }
        return versionInfo;
    }

    @Override
    public List<VersionDisplayInfo> getVersions(String refObjectUri, String refObjectId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        List<VersionDisplayInfo> versionInfos = null;
        if (CluServiceConstants.CLU_NAMESPACE_URI.equals(refObjectUri)) {
            versionInfos = R1R2ConverterUtil.convertLists(luDao.getVersions(refObjectId, refObjectUri), VersionDisplayInfo.class);
            if (versionInfos == null) {
                versionInfos = Collections.<VersionDisplayInfo> emptyList();
            }
        } else {
            throw new UnsupportedOperationException("This method does not know how to handle object type:"
                    + refObjectUri);
        }
        return versionInfos;
    }

    @Override
    public List<VersionDisplayInfo> getVersionsInDateRange(String refObjectUri, String refObjectId, Date from, Date to,
            ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<VersionDisplayInfo> versionInfos = null;
        if (CluServiceConstants.CLU_NAMESPACE_URI.equals(refObjectUri)) {
            if (versionInfos == null) {
                versionInfos = Collections.<VersionDisplayInfo> emptyList();
            }
        } else {
            throw new UnsupportedOperationException("This method does not know how to handle object type:"
                    + refObjectUri);
        }
        return versionInfos;
    }

    public void setSearchDispatcher(SearchDispatcher searchDispatcher) {
        this.searchDispatcher = searchDispatcher;
    }

    @Override
    public List<TypeInfo> getCluCluRelationTypes(ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
    	throw new UnsupportedOperationException("getCluCluRelationTypes");
    }

    @Override
    public List<TypeInfo> getDeliveryMethodTypes(ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
    	throw new UnsupportedOperationException("getDeliveryMethodTypes");
    }

    @Override
    public TypeInfo getDeliveryMethodType(String deliveryMethodTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
    	throw new UnsupportedOperationException("getDeliveryMethodType");
    }

    @Override
    public List<TypeInfo> getInstructionalFormatTypes(ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
    	throw new UnsupportedOperationException("getInstructionalFormatTypes");
    }

    @Override
    public TypeInfo getInstructionalFormatType(String instructionalFormatTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
    	throw new UnsupportedOperationException("getInstructionalFormatType");
    }

    @Override
    public List<TypeInfo> getLuTypes(ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
    	throw new UnsupportedOperationException("getLuTypes");
    }

    @Override
    public TypeInfo getLuType(String luTypeKey, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
    	throw new UnsupportedOperationException("getLuType");
    }

    @Override
    public List<TypeInfo> getLuCodeTypes(ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
    	throw new UnsupportedOperationException("getLuCodeTypes");
    }

    @Override
    public TypeInfo getLuCodeType(String luCodeTypeKey, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
    	throw new UnsupportedOperationException("getLuCodeType");
    }

    @Override
    public TypeInfo getLuLuRelationType(String cluCluRelationTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
    	throw new UnsupportedOperationException("getLuLuRelationType");
    }

    @Override
    public List<TypeInfo> getLuPublicationTypes(ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
    	throw new UnsupportedOperationException("getLuPublicationTypes");
    }

    @Override
    public TypeInfo getLuPublicationType(String luPublicationTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
    	throw new UnsupportedOperationException("getLuPublicationType");
    }

    @Override
    public List<TypeInfo> getCluResultTypes(ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
    	throw new UnsupportedOperationException("getCluResultTypes");
    }

    @Override
    public TypeInfo getCluResultType(String cluResultTypeKey, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
    	throw new UnsupportedOperationException("getCluResultType");
    }

    @Override
    public List<TypeInfo> getCluResultTypesForLuType(String luTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
    	throw new UnsupportedOperationException("getCluResultTypesForLuType");
    }

    @Override
    public List<TypeInfo> getResultUsageTypes(ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
    	throw new UnsupportedOperationException("getResultUsageTypes");
    }

    @Override
    public TypeInfo getResultUsageType(String resultUsageTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
    	throw new UnsupportedOperationException("getResultUsageType");
    }

    @Override
    public List<TypeInfo> getCluLoRelationTypes(ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
    	throw new UnsupportedOperationException("getCluLoRelationTypes");
    }

    @Override
    public TypeInfo getCluLoRelationType(String cluLoRelationTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
    	throw new UnsupportedOperationException("getCluLoRelationType");
    }

    @Override
    public List<TypeInfo> getCluSetTypes(ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
    	throw new UnsupportedOperationException("getCluSetTypes");
    }

    @Override
    public TypeInfo getCluSetType(String cluSetTypeKey, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
    	throw new UnsupportedOperationException("getCluSetType");
    }

    @Override
    public List<TypeInfo> getLuLuRelationTypes(ContextInfo contextInfo) throws OperationFailedException,
            UnsupportedOperationException {
    	throw new UnsupportedOperationException("getLuLuRelationTypes");
    }

    //    @Override
    //    public TypeInfo getSearchCriteriaType(String searchCriteriaTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, UnsupportedOperationException {
    //        // TODO KSCM-422 
    //        return null;
    //    }
    //
    //    @Override
    //    public List<TypeInfo> getSearchCriteriaTypes() throws OperationFailedException, UnsupportedOperationException {
    //        // TODO KSCM-422 
    //        return null;
    //    }
    //
    //    @Override
    //    public TypeInfo getSearchResultType(String searchResultTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, UnsupportedOperationException {
    //        // TODO KSCM-422 
    //        return null;
    //    }
    //
    //    @Override
    //    public List<TypeInfo> getSearchResultTypes() throws OperationFailedException, UnsupportedOperationException {
    //        // TODO KSCM-422 
    //        return null;
    //    }
    //
    //    @Override
    //    public TypeInfo getSearchType(String searchTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, UnsupportedOperationException {
    //        // TODO KSCM-422 
    //        return null;
    //    }
    //
    //    @Override
    //    public List<TypeInfo> getSearchTypes() throws OperationFailedException, UnsupportedOperationException {
    //        // TODO KSCM-422 
    //        return null;
    //    }
    //
    //    @Override
    //    public List<TypeInfo> getSearchTypesByCriteria(String searchCriteriaTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, UnsupportedOperationException {
    //        // TODO KSCM-422 
    //        return null;
    //    }
    //
    //    @Override
    //    public List<TypeInfo> getSearchTypesByResult(String searchResultTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, UnsupportedOperationException {
    //        // TODO KSCM-422 
    //        return null;
    //    }
}
