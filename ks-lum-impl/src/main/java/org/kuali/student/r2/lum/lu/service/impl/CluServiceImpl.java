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
package org.kuali.student.r2.lum.lu.service.impl;

import org.apache.log4j.Logger;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r1.common.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.r1.common.dictionary.service.DictionaryService;
import org.kuali.student.r1.common.entity.Amount;
import org.kuali.student.r1.common.entity.TimeAmount;
import org.kuali.student.r1.common.entity.Version;
import org.kuali.student.r1.common.entity.VersionEntity;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.DtoConstants;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.search.dto.*;
import org.kuali.student.r2.core.search.service.SearchManager;
import org.kuali.student.r2.core.search.service.SearchService;
import org.kuali.student.r2.common.validator.Validator;
import org.kuali.student.r2.common.validator.ValidatorFactory;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.versionmanagement.dto.VersionDisplayInfo;
import org.kuali.student.r2.lum.clu.dto.*;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.lu.dao.LuDao;
import org.kuali.student.r2.lum.lu.entity.*;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.persistence.NoResultException;
import java.util.*;
import java.util.Map.Entry;

@WebService(endpointInterface = "org.kuali.student.r2.lum.clu.service.CluService", serviceName = "CluService", portName = "CluService", targetNamespace = "http://student.kuali.org/wsdl/lu")
@Transactional(readOnly=true,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
public class CluServiceImpl implements CluService {

    private static final String SEARCH_KEY_DEPENDENCY_ANALYSIS = "lu.search.dependencyAnalysis";
    private static final String SEARCH_KEY_BROWSE_PROGRAM = "lu.search.browseProgram";
    private static final String SEARCH_KEY_BROWSE_VARIATIONS = "lu.search.browseVariations";
    private static final String SEARCH_KEY_RESULT_COMPONENT = "lrc.search.resultComponent";
    private static final String SEARCH_KEY_PROPOSALS_BY_COURSE_CODE = "lu.search.proposalsByCourseCode";
    private static final String SEARCH_KEY_BROWSE_VERSIONS = "lu.search.clu.versions";
    private static final String SEARCH_KEY_LU_RESULT_COMPONENTS = "lu.search.resultComponents";
    private static final String SEARCH_KEY_CLUSET_SEARCH_GENERIC = "cluset.search.generic";
    private static final String SEARCH_KEY_CLUSET_SEARCH_GENERICWITHCLUS = "cluset.search.genericWithClus";

    final Logger logger = Logger.getLogger(CluServiceImpl.class);
    private LuDao luDao;
    private ValidatorFactory validatorFactory;
    private DictionaryService dictionaryServiceDelegate;
    private SearchService searchDispatcher;
    private SearchManager searchManager;

    public void setDictionaryServiceDelegate(
            DictionaryService dictionaryServiceDelegate) {
        this.dictionaryServiceDelegate = dictionaryServiceDelegate;
    }

    public DictionaryService getDictionaryServiceDelegate() {
        return dictionaryServiceDelegate;
    }

    @Override
    public List<TypeInfo> getSearchTypes( ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        return searchManager.getSearchTypes(contextInfo);
    }

    @Override
    public TypeInfo getSearchType(String searchTypeKey,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(searchTypeKey, "searchTypeKey");
        return searchManager.getSearchType(searchTypeKey, contextInfo);
    }

    @Override
    public List<TypeInfo> getSearchTypesByResult(String searchResultTypeKey,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(searchResultTypeKey, "searchResultTypeKey");
        return searchManager.getSearchTypesByResult(searchResultTypeKey, contextInfo);
    }

    @Override
    public List<TypeInfo> getSearchTypesByCriteria(String searchCriteriaTypeKey,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(searchCriteriaTypeKey, "searchCriteriaTypeKey");
        return searchManager.getSearchTypesByCriteria(searchCriteriaTypeKey, contextInfo);
    }

    @Override
    public List<TypeInfo> getSearchResultTypes( ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        return searchManager.getSearchResultTypes(contextInfo);
    }

    @Override
    public List<TypeInfo> getSearchCriteriaTypes( ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        return searchManager.getSearchCriteriaTypes(contextInfo);
    }

    public SearchManager getSearchManager() {
        return searchManager;
    }

    public void setSearchManager(SearchManager searchManager) {
        this.searchManager = searchManager;
    }

    /**************************************************************************
     * SETUP OPERATION *
     **************************************************************************/
    @Override
    public List<TypeInfo> getDeliveryMethodTypes(ContextInfo context)
            throws OperationFailedException {
        return CluServiceAssembler.toGenericTypeInfoList(luDao.find(DeliveryMethodType.class));
    }

    @Override
    public TypeInfo getDeliveryMethodType(
            String deliveryMethodTypeKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {

        checkForMissingParameter(deliveryMethodTypeKey, "deliveryMethodTypeKey");
        try {
            return CluServiceAssembler.toGenericTypeInfo(luDao.fetch(
                    DeliveryMethodType.class, deliveryMethodTypeKey));
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException ex) {
            throw new DoesNotExistException(deliveryMethodTypeKey, ex);
        }
    }

    @Override
    public List<TypeInfo> getInstructionalFormatTypes(ContextInfo context)
            throws OperationFailedException {
        return CluServiceAssembler.toGenericTypeInfoList(luDao.find(InstructionalFormatType.class));
    }

    @Override
    public TypeInfo getInstructionalFormatType(
            String instructionalFormatTypeKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {
        checkForMissingParameter(instructionalFormatTypeKey,
                "instructionalFormatTypeKey");
        try {
            return CluServiceAssembler.toGenericTypeInfo(luDao.fetch(
                    InstructionalFormatType.class, instructionalFormatTypeKey));
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException ex) {
            throw new DoesNotExistException(instructionalFormatTypeKey, ex);
        }
    }

    @Override
    public List<TypeInfo> getLuTypes(ContextInfo context) throws OperationFailedException {
        return CluServiceAssembler.toGenericTypeInfoList(luDao.find(LuType.class));
    }

    @Override
    public TypeInfo getLuType(String luTypeKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {
        checkForMissingParameter(luTypeKey, "luTypeKey");
        try {
            return CluServiceAssembler.toGenericTypeInfo(luDao.fetch(LuType.class,
                    luTypeKey));
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException ex) {
            throw new DoesNotExistException(luTypeKey,ex);
        }
    }

    @Override
    public TypeInfo getLuCodeType(String luCodeTypeKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        try {
            checkForMissingParameter(luCodeTypeKey, "luCodeTypeKey");
            return CluServiceAssembler.toGenericTypeInfo(luDao.fetch(
                    LuCodeType.class, luCodeTypeKey));
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException ex) {
            throw new DoesNotExistException(luCodeTypeKey, ex);
        }
    }

    @Override
    public List<TypeInfo> getLuCodeTypes(ContextInfo context)
            throws OperationFailedException {
        return CluServiceAssembler.toGenericTypeInfoList(luDao.find(LuCodeType.class));
    }

    @Override
    public List<TypeInfo> getCluCluRelationTypes(ContextInfo context)
            throws OperationFailedException {
        return CluServiceAssembler.toLuLuRelationTypeInfos(luDao.find(LuLuRelationType.class));
    }

    @Override
    public TypeInfo getLuLuRelationType(String luLuRelationTypeKey, ContextInfo context)
            throws OperationFailedException, MissingParameterException,
            DoesNotExistException {
        checkForMissingParameter(luLuRelationTypeKey, "luLuRelationTypeKey");

        LuLuRelationType luLuRelationType;
        try {
            luLuRelationType = luDao.fetch(LuLuRelationType.class,
                    luLuRelationTypeKey);
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException ex) {
            throw new DoesNotExistException(luLuRelationTypeKey, ex);
        }
        return CluServiceAssembler.toLuLuRelationTypeInfo(luLuRelationType);
    }

    @Override
    public List<String> getAllowedLuLuRelationTypesForLuType(String luTypeKey,
                                                             String relatedLuTypeKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {
        checkForMissingParameter(luTypeKey, "luTypeKey");
        checkForMissingParameter(relatedLuTypeKey, "relatedLuTypeKey");

        return luDao.getAllowedLuLuRelationTypesForLuType(luTypeKey,
                relatedLuTypeKey);
    }

    @Override
    public List<TypeInfo> getLuPublicationTypes(ContextInfo context)
            throws OperationFailedException {
        return CluServiceAssembler.toGenericTypeInfoList(luDao.find(LuPublicationType.class));
    }

    @Override
    public TypeInfo getLuPublicationType(
            String luPublicationTypeKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {
        checkForMissingParameter(luPublicationTypeKey, "luPublicationTypeKey");
        try {
            return CluServiceAssembler.toGenericTypeInfo(luDao.fetch(
                    LuPublicationType.class, luPublicationTypeKey));
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException ex) {
            throw new DoesNotExistException(luPublicationTypeKey, ex);
        }
    }

    @Override
    public List<String> getLuPublicationTypesForLuType(String luTypeKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("getLuPublicationTypesForLuType");
    }

    @Override
    public List<TypeInfo> getCluResultTypes(ContextInfo context)
            throws OperationFailedException {
        return CluServiceAssembler.toGenericTypeInfoList(luDao.find(CluResultType.class));
    }

    @Override
    public TypeInfo getCluResultType(String cluResultTypeKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        try {
            return CluServiceAssembler.toGenericTypeInfo(luDao.fetch(
                    CluResultType.class, cluResultTypeKey));
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException ex) {
            throw new DoesNotExistException(cluResultTypeKey, ex);
        }
    }

    @Override
    public List<TypeInfo> getCluResultTypesForLuType(String luTypeKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        checkForMissingParameter(luTypeKey, "luTypeKey");
        return CluServiceAssembler.toGenericTypeInfoList(luDao.getAllowedCluResultTypesForLuType(luTypeKey));
    }

    @Override
    public List<TypeInfo> getResultUsageTypes(ContextInfo context)
            throws OperationFailedException {
        return CluServiceAssembler.toGenericTypeInfoList(luDao.find(ResultUsageType.class));
    }

    @Override
    public TypeInfo getResultUsageType(String resultUsageTypeKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        checkForMissingParameter(resultUsageTypeKey, "resultUsageTypeKey");
        try {
            return CluServiceAssembler.toGenericTypeInfo(luDao.fetch(
                    ResultUsageType.class, resultUsageTypeKey));
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException ex) {
            throw new DoesNotExistException(resultUsageTypeKey, ex);
        }
    }

    @Override
    public List<String> getAllowedResultUsageTypesForLuType(String luTypeKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        checkForMissingParameter(luTypeKey, "luTypeKey");

        return luDao.getAllowedResultUsageTypesForLuType(luTypeKey);
    }

    @Override
    public List<String> getAllowedResultComponentTypesForResultUsageType(
            String resultUsageTypeKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {

        checkForMissingParameter(resultUsageTypeKey, "resultUsageTypeKey");

        return luDao.getAllowedResultComponentTypesForResultUsageType(resultUsageTypeKey);
    }

    @Override
    public TypeInfo getCluLoRelationType(
            String cluLoRelationTypeKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {
        checkForMissingParameter(cluLoRelationTypeKey, "cluLoRelationTypeKey");

        CluLoRelationType cluLoRelationType;
        try {
            cluLoRelationType = luDao.fetch(
                    CluLoRelationType.class, cluLoRelationTypeKey);
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException ex) {
            throw new DoesNotExistException(cluLoRelationTypeKey, ex);
        }
        return CluServiceAssembler.toGenericTypeInfo(cluLoRelationType);
    }

    @Override
    public List<TypeInfo> getCluLoRelationTypes(ContextInfo context)
            throws OperationFailedException {
        return CluServiceAssembler.toGenericTypeInfoList(luDao.find(CluLoRelationType.class));
    }

    @Override
    public List<String> getAllowedCluLoRelationTypesForLuType(String luTypeKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {

        checkForMissingParameter(luTypeKey, luTypeKey);

        return luDao.getAllowedCluLoRelationTypesForLuType(luTypeKey);
    }

    @Override
    public List<TypeInfo> getCluSetTypes(ContextInfo context)
            throws OperationFailedException {
        return CluServiceAssembler.toGenericTypeInfoList(luDao.find(CluSetType.class));
    }

    @Override
    public TypeInfo getCluSetType(String cluSetTypeKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        checkForMissingParameter(cluSetTypeKey, "cluSetTypeKey");
        try {
            return CluServiceAssembler.toGenericTypeInfo(luDao.fetch(
                    CluSetType.class, cluSetTypeKey));
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException ex) {
            throw new DoesNotExistException(cluSetTypeKey, ex);
        }
    }

    /**************************************************************************
     * READ OPERATION *
     **************************************************************************/
    // **** Core **********
    @Override
    public CluInfo getClu(String cluId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {

        checkForMissingParameter(cluId, "cluId");

        Clu clu;
        try {
            clu = luDao.fetch(Clu.class, cluId);
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException ex) {
            throw new DoesNotExistException(cluId, ex);
        }
        return CluServiceAssembler.toCluInfo(clu);
    }

    @Override
    public List<CluInfo> getClusByIds(List<String> cluIds, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        checkForMissingParameter(cluIds, "cluIds");
        checkForEmptyList(cluIds, "cluIds");
        List<Clu> clus = luDao.getClusByIdList(cluIds);
        return CluServiceAssembler.toCluInfos(clus);
    }

    @Override
    public List<CluInfo> getClusByLuType(String luTypeKey, String luState, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        checkForMissingParameter(luTypeKey, "luTypeKey");
        checkForMissingParameter(luState, "lustate");
        List<Clu> clus = luDao.getClusByLuType(luTypeKey, luState);
        return CluServiceAssembler.toCluInfos(clus);
    }

    @Override
    public List<String> getCluIdsByLuType(String luTypeKey, String luState, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        checkForMissingParameter(luTypeKey, "luTypeKey");
        checkForMissingParameter(luState, "luState");
        List<Clu> clus = luDao.getClusByLuType(luTypeKey, luState);
        List<String> Ids = new ArrayList<String>(clus.size());
        for (Clu clu : clus) {
            Ids.add(clu.getId());
        }
        return Ids;
    }

    // ****** Relations
    @Override
    public List<String> getAllowedCluCluRelationTypesByClu(String cluId,
                                                           String relatedCluId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {
        checkForMissingParameter(cluId, "cluId");
        checkForMissingParameter(relatedCluId, "relatedCluId");

        return luDao.getAllowedLuLuRelationTypesByCluId(cluId, relatedCluId);
    }

    @Override
    public List<CluInfo> getClusByRelatedCluAndRelationType(String relatedCluId,
                                                            String luLuRelationTypeKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {
        checkForMissingParameter(relatedCluId, "relatedCluId");
        checkForMissingParameter(luLuRelationTypeKey, "luLuRelationTypeKey");

        List<Clu> clus = luDao.getClusByRelation(relatedCluId,
                luLuRelationTypeKey);
        List<CluInfo> result = CluServiceAssembler.toCluInfos(clus);
        return result;

    }

    @Override
    public List<String> getCluIdsByRelatedCluAndRelationType(String relatedCluId,
                                                             String cluCluRelationTypeKey,
                                                             ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {

        checkForMissingParameter(relatedCluId, "relatedCluId");
        checkForMissingParameter(cluCluRelationTypeKey, "cluCluRelationTypeKey");

        List<String> cluIds = luDao.getCluIdsByRelatedCluId(relatedCluId, cluCluRelationTypeKey);
        return cluIds;
    }

    @Override
    public List<CluInfo> getRelatedClusByCluAndRelationType(String relatedCluId,
                                                            String cluCLuRelationTypeKey,
                                                            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        checkForMissingParameter(relatedCluId, "cluId");
        checkForMissingParameter(cluCLuRelationTypeKey, "cluCLuRelationTypeKey");
        List<Clu> relatedClus = luDao.getRelatedClusByCluId(relatedCluId,
                cluCLuRelationTypeKey);
        return CluServiceAssembler.toCluInfos(relatedClus);
    }

    @Override
    public List<String> getRelatedCluIdsByCluAndRelationType(String cluId,
                                                             String luLuRelationTypeKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {
        checkForMissingParameter(cluId, "cluId");
        checkForMissingParameter(luLuRelationTypeKey, "luLuRelationTypeKey");
        List<String> relatedCluIds = luDao.getRelatedCluIdsByCluId(cluId,
                luLuRelationTypeKey);
        return relatedCluIds;
    }

    @Override
    public CluCluRelationInfo getCluCluRelation(String cluCluRelationId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        checkForMissingParameter(cluCluRelationId, "cluCluRelationId");
        try {
            return CluServiceAssembler.toCluCluRelationInfo(luDao.fetch(
                    CluCluRelation.class, cluCluRelationId));
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException ex) {
            throw new DoesNotExistException(cluCluRelationId, ex);
        }
    }

    @Override
    public List<CluCluRelationInfo> getCluCluRelationsByClu(String cluId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        checkForMissingParameter(cluId, "cluId");
        List<CluCluRelation> cluCluRelations = luDao.getCluCluRelationsByClu(cluId);
        return CluServiceAssembler.toCluCluRelationInfos(cluCluRelations);
    }

    // **** Publication
    @Override
    public List<CluPublicationInfo> getCluPublicationsByClu(String cluId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        checkForMissingParameter(cluId, "cluId");
        List<CluPublication> cluPublications = luDao.getCluPublicationsByCluId(cluId);
        return CluServiceAssembler.toCluPublicationInfos(cluPublications);
    }

    @Override
    public List<CluPublicationInfo> getCluPublicationsByType(
            String luPublicationTypeKey, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {
        checkForMissingParameter(luPublicationTypeKey, "luPublicationTypeKey");
        List<CluPublication> cluPublications = luDao.getCluPublicationsByType(luPublicationTypeKey);
        return CluServiceAssembler.toCluPublicationInfos(cluPublications);
    }

    @Override
    public CluPublicationInfo getCluPublication(String cluPublicationId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        checkForMissingParameter(cluPublicationId, "cluPublicationId");
        CluPublication cluPublication;
        try {
            cluPublication = luDao.fetch(CluPublication.class, cluPublicationId);
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException ex) {
            throw new DoesNotExistException(cluPublicationId, ex);
        }
        return CluServiceAssembler.toCluPublicationInfo(cluPublication);
    }

    // **** Results
    @Override
    public CluResultInfo getCluResult(String cluResultId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {

        checkForMissingParameter(cluResultId, "cluResultId");

        CluResult cluResult;
        try {
            cluResult = luDao.fetch(CluResult.class, cluResultId);
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException ex) {
            throw new DoesNotExistException(cluResultId, ex);
        }
        return CluServiceAssembler.toCluResultInfo(cluResult);
    }

    @Override
    public List<CluResultInfo> getCluResultByClu(String cluId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {

        checkForMissingParameter(cluId, "cluId");

        return CluServiceAssembler.toCluResultInfos(luDao.getCluResultByClu(cluId));
    }

    @Override
    public List<String> getCluIdsByResultUsageType(String resultUsageTypeKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        return luDao.getCluIdsByResultUsageType(resultUsageTypeKey);
    }

    @Override
    public List<String> getCluIdsByResultComponent(String resultComponentId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        return luDao.getCluIdsByResultComponentId(resultComponentId);
    }

    // **** Learning Objectives
    @Override
    public CluLoRelationInfo getCluLoRelation(String cluLoRelationId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        checkForMissingParameter(cluLoRelationId, "cluLoRelationId");

        CluLoRelation reltn;
        try {
            reltn = luDao.fetch(CluLoRelation.class, cluLoRelationId);
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException ex) {
            throw new DoesNotExistException(cluLoRelationId, ex);
        }
        return CluServiceAssembler.toCluLoRelationInfo(reltn);

    }

    @Override
    public List<CluLoRelationInfo> getCluLoRelationsByClu(String cluId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {

        checkForMissingParameter(cluId, "cluId");
        List<CluLoRelation> cluLoRelations = luDao.getCluLoRelationsByClu(cluId);
        return CluServiceAssembler.toCluLoRelationInfos(cluLoRelations);

    }

    @Override
    public List<CluLoRelationInfo> getCluLoRelationsByLo(String loId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        checkForMissingParameter(loId, "loId");
        List<CluLoRelation> cluLoRelations = luDao.getCluLoRelationsByLo(loId);
        return CluServiceAssembler.toCluLoRelationInfos(cluLoRelations);
    }

    // *** Resources
    @Override
    public List<String> getResourceRequirementsForClu(String cluId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        return new ArrayList<String>();
    }

    // *** Sets
    @Override
    public CluSetInfo getCluSet(String cluSetId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        checkForMissingParameter(cluSetId, "cluSetId");
        CluSet cluSet;
        try {
            cluSet = luDao.fetch(CluSet.class, cluSetId);
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException ex) {
            throw new DoesNotExistException(cluSetId, ex);
        }
        CluSetInfo cluSetInfo = CluServiceAssembler.toCluSetInfo(cluSet);
        setMembershipQuerySearchResult(cluSetInfo, contextInfo);
        return cluSetInfo;
    }

    @Override
    public CluSetTreeViewInfo getCluSetTreeView(String cluSetId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        checkForMissingParameter(cluSetId, "cluSetId");
        CluSetInfo cluSet = getCluSet(cluSetId, context);
        if (cluSet == null) {
            return null;
        }

        CluSetTreeViewInfo cluSetTreeView = new CluSetTreeViewInfo();
        getCluSetTreeViewHelper(cluSet, cluSetTreeView, context);
        return cluSetTreeView;
    }

    /**
     * Go through the list of CluSets and retrieve all the information regarding child
     * Clu Sets and associated Clus
     *
     * @param cluSetInfo
     * @param cluSetTreeViewInfo
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    private void getCluSetTreeViewHelper(CluSetInfo cluSetInfo,
                                         CluSetTreeViewInfo cluSetTreeViewInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
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
                CluSetInfo subCluSet = getCluSet(cluSetId, context);
                List<CluSetTreeViewInfo> cluSets =
                        cluSetTreeViewInfo.getCluSets() == null
                                ? new ArrayList<CluSetTreeViewInfo>(0) : cluSetTreeViewInfo.getCluSets();

                CluSetTreeViewInfo subCluSetTreeViewInfo = new CluSetTreeViewInfo();
                getCluSetTreeViewHelper(subCluSet, subCluSetTreeViewInfo, context);
                cluSets.add(subCluSetTreeViewInfo);

                cluSetTreeViewInfo.setCluSets(cluSets);
            }
        }
        List<CluInfo> clus = new ArrayList<CluInfo>();
        for (String cluId : cluSetInfo.getCluIds()) {
            if (cluId != null) {
                //Optimized version of clu translation. It seems like for now we only need the following information.
                //If more information is needed, then appropriate method in assembler has to be used.
                logger.info("CluID: " + cluId);
                Clu clu = luDao.getCurrentCluVersion(cluId);
                CluInfo cluInfo = new CluInfo();
                cluInfo.setId(clu.getId());
                cluInfo.setTypeKey(clu.getLuType().getId());
                cluInfo.setOfficialIdentifier(CluServiceAssembler.toCluIdentifierInfo(clu.getOfficialIdentifier()));
                clus.add(cluInfo);
            }
        }
        cluSetTreeViewInfo.setClus(clus);
    }

    @Override
    public List<CluSetInfo> getCluSetsByIds(List<String> cluSetIds, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        checkForMissingParameter(cluSetIds, "cluSetIds");
        checkForEmptyList(cluSetIds, "cluSetIds");
        List<CluSet> cluSets = luDao.getCluSetInfoByIdList(cluSetIds);
        return CluServiceAssembler.toCluSetInfos(cluSets);
    }

    @Override
    public List<String> getCluSetIdsFromCluSet(String cluSetId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        checkForMissingParameter(cluSetId, "cluSetId");
        CluSet cluSet;
        try {
            cluSet = luDao.fetch(CluSet.class, cluSetId);
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException ex) {
            throw new DoesNotExistException(cluSetId, ex);
        }
        List<String> Ids = new ArrayList<String>(cluSet.getCluVerIndIds().size());
        if (cluSet.getCluSets() != null) {
            for (CluSet cluSet2 : cluSet.getCluSets()) {
                Ids.add(cluSet2.getId());
            }
        }
        return Ids;
    }

    @Override
    public Boolean isCluSetDynamic(String cluSetId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return null;
    }

    @Override
    public List<CluInfo> getClusFromCluSet(String cluSetId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        checkForMissingParameter(cluSetId, "cluSetId");
        CluSet cluSet;
        try {
            cluSet = luDao.fetch(CluSet.class, cluSetId);
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException ex) {
            throw new DoesNotExistException(cluSetId, ex);
        }
        List<CluInfo> clus = new ArrayList<CluInfo>(cluSet.getCluVerIndIds().size());
        for (CluSetJoinVersionIndClu cluSetJnClu : cluSet.getCluVerIndIds()) {
            clus.add(CluServiceAssembler.toCluInfo(luDao.getCurrentCluVersion(cluSetJnClu.getCluVersionIndId())));
        }
        return clus;
    }

    @Override
    public List<String> getCluIdsFromCluSet(String cluSetId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        checkForMissingParameter(cluSetId, "cluSetId");
        CluSet cluSet;
        try {
            cluSet = luDao.fetch(CluSet.class, cluSetId);
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException ex) {
            throw new DoesNotExistException(cluSetId, ex);
        }
        List<String> Ids = new ArrayList<String>(cluSet.getCluVerIndIds().size());
        for (CluSetJoinVersionIndClu cluSetJnClu : cluSet.getCluVerIndIds()) {
            Ids.add(cluSetJnClu.getCluVersionIndId());
        }
        return Ids;
    }

    @Override
    public List<CluInfo> getAllClusInCluSet(String cluSetId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        checkForMissingParameter(cluSetId, "cluSetId");
        List<String> cluIndIds = new ArrayList<String>();
        CluSet cluSet;
        try {
            cluSet = luDao.fetch(CluSet.class, cluSetId);
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException ex) {
            throw new DoesNotExistException(cluSetId, ex);
        }
        findClusInCluSet(cluIndIds, cluSet);
        List<CluInfo> infos = new ArrayList<CluInfo>(cluIndIds.size());
        for (String cluIndId : cluIndIds) {
            infos.add(CluServiceAssembler.toCluInfo(luDao.getCurrentCluVersion(cluIndId)));
        }
        return infos;
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
                // This condition avoIds infinite recursion problem
                if (!processedCluSetIds.contains(cluSet.getId())) {
                    processedCluSetIds.add(cluSet.getId());
                    doFindClusInCluSet(processedCluSetIds, clus, cluSet);
                }
            }
        }
    }

    @Override
    public List<String> getAllCluIdsInCluSet(String cluSetId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        checkForMissingParameter(cluSetId, "cluSetId");
        List<String> Ids = new ArrayList<String>();
        CluSet cluSet;
        try {
            cluSet = luDao.fetch(CluSet.class, cluSetId);
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException ex) {
            throw new DoesNotExistException(cluSetId, ex);
        }
        findClusInCluSet(Ids, cluSet);
        return Ids;
    }

    @Override
    public Boolean isCluInCluSet(String cluId, String cluSetId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        checkForMissingParameter(cluId, "cluId");
        checkForMissingParameter(cluSetId, "cluSetId");
        return luDao.isCluInCluSet(cluId, cluSetId);
    }

    /**************************************************************************
     * MAINTENANCE OPERATIONS *
     **************************************************************************/
    @Override
    public List<ValidationResultInfo> validateClu(String validationType,
                                                  CluInfo cluInfo, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {
        checkForMissingParameter(validationType, "validationType");
        checkForMissingParameter(cluInfo, "cluInfo");

        ObjectStructureDefinition objStructure = this.getObjectStructure(CluInfo.class.getName());
        Validator defaultValidator = validatorFactory.getValidator();
        List<org.kuali.student.r2.common.dto.ValidationResultInfo> vris = defaultValidator.validateObject(cluInfo, objStructure, context);
        return vris;
    }

    @Override
    @Transactional(readOnly = false)
    public CluInfo createClu(String luTypeKey, CluInfo cluInfo, ContextInfo context)
            throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        Clu clu;
        try {
            clu = toCluForCreate(luTypeKey, cluInfo, context);
        } catch (AlreadyExistsException ex) {
            throw new OperationFailedException(ex.getMessage(), ex);
        }
        //Set current (since this is brand new and every verIndId needs one current)
        if (clu.getVersion() == null) {
            clu.setVersion(new Version());
        }
        clu.getVersion().setCurrentVersionStart(new Date());
        luDao.create(clu);
        return CluServiceAssembler.toCluInfo(clu);
    }

    public Clu toCluForCreate(String luTypeKey, CluInfo cluInfo, ContextInfo context)
            throws AlreadyExistsException, DataValidationErrorException,
            DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        checkForMissingParameter(luTypeKey, "luTypeKey");
        checkForMissingParameter(cluInfo, "cluInfo");

        // Validate CLU
        List<ValidationResultInfo> val = validateClu("SYSTEM", cluInfo, context);
        if (null != val && val.size() > 0) {
            throw new DataValidationErrorException("Validation error!", val);
        }

        Clu clu = new Clu();

        LuType luType;
        try {
            luType = luDao.fetch(LuType.class, luTypeKey);
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException ex) {
            throw new DoesNotExistException(luTypeKey, ex);
        }
        clu.setLuType(luType);

        if (cluInfo.getOfficialIdentifier() != null) {
            clu.setOfficialIdentifier(CluServiceAssembler.createOfficialIdentifier(cluInfo, luDao));
        }
        clu.setAlternateIdentifiers(CluServiceAssembler.createAlternateIdentifiers(cluInfo, luDao));
        if (cluInfo.getDescr() != null) {
            LuRichText descr = CluServiceAssembler.toRichText(LuRichText.class, cluInfo.getDescr());
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
            instructor.setAttributes(CluServiceAssembler.toGenericAttributes(
                    CluAdminOrgAttribute.class, orgInfo.getAttributes(),
                    instructor, luDao));
            instructor.setClu(clu);
            adminOrgs.add(instructor);
        }

        if (cluInfo.getPrimaryInstructor() != null) {
            CluInstructor primaryInstructor = new CluInstructor();
            BeanUtils.copyProperties(cluInfo.getPrimaryInstructor(),
                    primaryInstructor, new String[]{"id", "attributes"});
            primaryInstructor.setAttributes(CluServiceAssembler.toGenericAttributes(CluInstructorAttribute.class, cluInfo.getPrimaryInstructor().getAttributes(),
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
                    new String[]{"id", "attributes"});
            instructor.setAttributes(CluServiceAssembler.toGenericAttributes(
                    CluInstructorAttribute.class, instructorInfo.getAttributes(), instructor, luDao));
            instructors.add(instructor);
        }

        if (cluInfo.getStdDuration() != null) {
            clu.setStdDuration(CluServiceAssembler.toTimeAmount(cluInfo.getStdDuration()));
        }

        if (clu.getLuCodes() == null) {
            clu.setLuCodes(new ArrayList<LuCode>(0));
        }
        List<LuCode> luCodes = clu.getLuCodes();
        for (LuCodeInfo luCodeInfo : cluInfo.getLuCodes()) {
            LuCode luCode = new LuCode();
            luCode.setAttributes(CluServiceAssembler.toGenericAttributes(
                    LuCodeAttribute.class, luCodeInfo.getAttributes(), luCode,
                    luDao));
            BeanUtils.copyProperties(luCodeInfo, luCode, new String[]{
                    "attributes", "meta", "descr", "typeKey"});
            if (luCodeInfo.getDescr() != null) {
                luCode.setDescr(luCodeInfo.getDescr().getPlain());
            }
            luCode.setType(luCodeInfo.getTypeKey());
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
                cluFee = CluServiceAssembler.toCluFee(clu, false, cluInfo.getFeeInfo(), luDao);
            } catch (VersionMismatchException e) {
                // Version Mismatch Should Happen only for updates
            }
            clu.setFee(cluFee);
        }

        if (cluInfo.getAccountingInfo() != null) {
            CluAccounting cluAccounting = new CluAccounting();
            cluAccounting.setAttributes(CluServiceAssembler.toGenericAttributes(
                    CluAccountingAttribute.class, cluInfo.getAccountingInfo().getAttributes(), cluAccounting, luDao));
            cluAccounting.setAffiliatedOrgs(CluServiceAssembler.toAffiliatedOrgs(false, cluAccounting.getAffiliatedOrgs(),
                    cluInfo.getAccountingInfo().getAffiliatedOrgs(),
                    luDao));
            clu.setAccounting(cluAccounting);
        }

        clu.setAttributes(CluServiceAssembler.toGenericAttributes(
                CluAttribute.class, cluInfo.getAttributes(), clu, luDao));


        if (cluInfo.getIntensity() != null) {
            clu.setIntensity(CluServiceAssembler.toAmount(cluInfo.getIntensity()));
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
                    new String[]{"attributes", "meta"});
            accreditation.setAttributes(CluServiceAssembler.toGenericAttributes(
                    CluAccreditationAttribute.class, accreditationInfo.getAttributes(), accreditation, luDao));
            accreditations.add(accreditation);
        }


        // TODO: the following should be done via copyProperties
        // dictionary needs work
        // required field name allignment between CluInfo and Clu
                if (cluInfo.getCanCreateLui() != null) {
                    clu.setCanCreateLui(cluInfo.getCanCreateLui());
                }

                if (cluInfo.getIsEnrollable() != null) {
                    clu.setEnrollable(cluInfo.getIsEnrollable());
                }

                if (cluInfo.getIsHasEarlyDropDeadline() != null) {
                    clu.setHasEarlyDropDeadline(cluInfo.getIsHasEarlyDropDeadline());
                }

                if (cluInfo.getIsHazardousForDisabledStudents() != null) {
                    clu.setHazardousForDisabledStudents(cluInfo.getIsHazardousForDisabledStudents());
                }


         // Now copy all not standard properties
        BeanUtils.copyProperties(cluInfo, clu, new String[]{"luType",
                "officialIdentifier", "alternateIdentifiers", "descr",
                "luCodes", "primaryInstructor", "instructors", "stdDuration",
                "offeredAtpTypes", "feeInfo", "accountingInfo", "attributes",
                "meta", "version", "intensity",
                "campusLocations", "accreditations",
                "adminOrgs", "canCreateLui", "hasEarlyDropDeadline", ""});

        return clu;
    }

    @Override
    @Transactional(readOnly = false)
    public CluInfo updateClu(String cluId, CluInfo cluInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException,
            VersionMismatchException {

        checkForMissingParameter(cluId, "cluId");
        checkForMissingParameter(cluInfo, "cluInfo");

        // Validate CLU
        List<ValidationResultInfo> val = validateClu("SYSTEM", cluInfo, context);
        if (null != val && val.size() > 0) {
            throw new DataValidationErrorException("Validation error!", val);
        }

        Clu clu;
        try {
            clu = luDao.fetch(Clu.class, cluId);
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException ex) {
            throw new DoesNotExistException(cluId, ex);
        }

        if (!String.valueOf(clu.getVersionNumber()).equals(
                cluInfo.getMeta().getVersionInd())) {
            throw new VersionMismatchException(
                    "Clu to be updated is not the current version");
        }

        LuType luType;
        try {
            luType = luDao.fetch(LuType.class, cluInfo.getTypeKey());
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException ex) {
            throw new DoesNotExistException(cluInfo.getTypeKey(), ex);
        }
        clu.setLuType(luType);

        if (cluInfo.getOfficialIdentifier() != null) {
            CluServiceAssembler.updateOfficialIdentifier(clu, cluInfo, luDao);
        } else if (clu.getOfficialIdentifier() != null) {
            luDao.delete(clu.getOfficialIdentifier());
        }

        // Update the list of Alternate Identifiers
        // Get a map of Id->object of all the currently persisted objects in the
        // list
        Map<String, CluIdentifier> oldAltIdMap = new HashMap<String, CluIdentifier>();
        CluServiceAssembler.updateAlternateIdentifier(oldAltIdMap, clu, cluInfo, luDao);
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
            clu.setDescr(null);//TODO is the is the best method of doing this? what if the user passes in a new made up id, does that mean we have orphaned richtexts?
        }

        if (cluInfo.getPrimaryInstructor() != null) {
            if (clu.getPrimaryInstructor() == null) {
                clu.setPrimaryInstructor(new CluInstructor());
            }
            BeanUtils.copyProperties(cluInfo.getPrimaryInstructor(), clu.getPrimaryInstructor(), new String[]{"attributes"});
            clu.getPrimaryInstructor().setAttributes(
                    CluServiceAssembler.toGenericAttributes(
                            CluInstructorAttribute.class, cluInfo.getPrimaryInstructor().getAttributes(),
                            clu.getPrimaryInstructor(), luDao));
        } else if (clu.getPrimaryInstructor() != null) {
            luDao.delete(clu.getPrimaryInstructor());
        }

        // Update the List of instructors
        // Get a map of Id->object of all the currently persisted objects in the
        // list
        Map<String, CluInstructor> oldInstructorMap = new HashMap<String, CluInstructor>();
        for (CluInstructor cluInstructor : clu.getInstructors()) {
            oldInstructorMap.put(cluInstructor.getId(), cluInstructor);
        }
        clu.getInstructors().clear();

        // Loop through the new list, if the item exists already update and
        // remove from the list
        // otherwise create a new entry
        for (CluInstructorInfo instructorInfo : cluInfo.getInstructors()) {
            CluInstructor cluInstructor = oldInstructorMap.remove(instructorInfo.getId());
            if (cluInstructor == null) {
                cluInstructor = new CluInstructor();
            }
            // Do Copy
            BeanUtils.copyProperties(instructorInfo, cluInstructor,
                    new String[]{"attributes"});
            cluInstructor.setAttributes(CluServiceAssembler.toGenericAttributes(
                    CluInstructorAttribute.class, instructorInfo.getAttributes(), cluInstructor, luDao));
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
            BeanUtils.copyProperties(cluInfo.getStdDuration(), clu.getStdDuration());
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
            luCode.setAttributes(CluServiceAssembler.toGenericAttributes(
                    LuCodeAttribute.class, luCodeInfo.getAttributes(), luCode,
                    luDao));

            luCode.setValue(luCodeInfo.getValue());
            if (luCodeInfo.getDescr() != null) {
                luCode.setDescr(luCodeInfo.getDescr().getPlain());
            }
            luCode.setType(luCodeInfo.getTypeKey());
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
            CluAtpTypeKey cluAtpTypeKey = oldOfferedAtpTypesMap.remove(atpTypeKey);
            if (cluAtpTypeKey == null) {
                cluAtpTypeKey = new CluAtpTypeKey();
            }
            // Do Copy
            cluAtpTypeKey.setAtpTypeKey(atpTypeKey);
            cluAtpTypeKey.setClu(clu);
            clu.getOfferedAtpTypes().add(cluAtpTypeKey);
        }

        // Now delete anything left over
        for (Entry<String, CluAtpTypeKey> entry : oldOfferedAtpTypesMap.entrySet()) {
            luDao.delete(entry.getValue());
        }

        if (cluInfo.getFeeInfo() != null) {
            if (clu.getFee() == null) {
                clu.setFee(CluServiceAssembler.toCluFee(clu, false, cluInfo.getFeeInfo(), luDao));
            } else {
                clu.setFee(CluServiceAssembler.toCluFee(clu, true, cluInfo.getFeeInfo(), luDao));
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
                    CluServiceAssembler.toGenericAttributes(
                            CluAccountingAttribute.class, cluInfo.getAccountingInfo().getAttributes(), clu.getAccounting(), luDao));
            clu.getAccounting().setAffiliatedOrgs(CluServiceAssembler.toAffiliatedOrgs(true, clu.getAccounting().getAffiliatedOrgs(),
                    cluInfo.getAccountingInfo().getAffiliatedOrgs(),
                    luDao));

        } else if (clu.getAccounting() != null) {
            clu.setAccounting(null);
        }

        clu.setAttributes(CluServiceAssembler.toGenericAttributes(
                CluAttribute.class, cluInfo.getAttributes(), clu, luDao));

        if (cluInfo.getIntensity() != null) {
            if (clu.getIntensity() == null) {
                clu.setIntensity(new Amount());
            }
            clu.getIntensity().setUnitQuantity(cluInfo.getIntensity().getUnitQuantity());
            clu.getIntensity().setUnitType(cluInfo.getIntensity().getUnitTypeKey());
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
        for (Entry<String, CluCampusLocation> entry : oldLocationsMap.entrySet()) {
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
                    new String[]{"attributes", "meta"});
            cluAccreditation.setAttributes(CluServiceAssembler.toGenericAttributes(CluAccreditationAttribute.class,
                    accreditationInfo.getAttributes(),
                    cluAccreditation, luDao));
            clu.getAccreditations().add(cluAccreditation);
        }

        // Now delete anything left over
        for (Entry<String, CluAccreditation> entry : oldAccreditationMap.entrySet()) {
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
            cluOrg.setAttributes(CluServiceAssembler.toGenericAttributes(
                    CluAdminOrgAttribute.class, orgInfo.getAttributes(),
                    cluOrg, luDao));
            cluOrg.setClu(clu);
            clu.getAdminOrgs().add(cluOrg);
        }

        for (Entry<String, CluAdminOrg> entry : oldAdminOrgsMap.entrySet()) {
            luDao.delete(entry.getValue());
        }

        clu.setHasEarlyDropDeadline(cluInfo.getIsHasEarlyDropDeadline());
        clu.setHazardousForDisabledStudents(cluInfo.getIsHazardousForDisabledStudents());
        clu.setEnrollable(cluInfo.getIsEnrollable());
        clu.setCanCreateLui(cluInfo.getCanCreateLui());

        // Now copy all not standard properties
        BeanUtils.copyProperties(cluInfo, clu, new String[]{"luType",
                "officialIdentifier", "alternateIdentifiers", "descr",
                "luCodes", "primaryInstructor", "instructors", "stdDuration",
                "offeredAtpTypes", "feeInfo", "accountingInfo", "attributes",
                "meta", "version", "intensity",
                "campusLocations", "accreditations",
                "adminOrgs", "canCreateLui", "hasEarlyDropDeadline",
                "hazardousForDisabledStudents", "enrollable", ""});
        Clu updated = null;
        try {
            updated = luDao.update(clu);
        } catch (Exception e) {
            logger.error("Exception occured: ", e);
        }
        return CluServiceAssembler.toCluInfo(updated);
    }

    @Override
    @Transactional(readOnly = false)
    public StatusInfo deleteClu(String cluId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            DependentObjectsExistException, OperationFailedException,
            PermissionDeniedException {
        checkForMissingParameter(cluId, "cluId");
        try {
            luDao.delete(Clu.class, cluId);
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException ex) {
            throw new DoesNotExistException(cluId, ex);
        }

        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setSuccess(true);

        return statusInfo;
    }

    @Override
    @Transactional(readOnly = false)
    public CluInfo updateCluState(String cluId, String luState, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // Check Missing params
        checkForMissingParameter(cluId, "cluId");
        checkForMissingParameter(luState, "luState");
        Clu clu;
        try {
            clu = luDao.fetch(Clu.class, cluId);
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException ex) {
            throw new DoesNotExistException(cluId, ex);
        }
        clu.setState(luState);
        Clu updated = luDao.update(clu);
        return CluServiceAssembler.toCluInfo(updated);
    }

    @Override
    public List<ValidationResultInfo> validateCluCluRelation(String validationTypeKey,
                                                             String cluId, String relatedCluId,
                                                             String cluCluRelationTypeKey,
                                                             CluCluRelationInfo cluCluRelationInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        checkForMissingParameter(validationTypeKey, "validationTypeKey");
        checkForMissingParameter(cluCluRelationInfo, "cluCluRelationInfo");

        ObjectStructureDefinition objStructure = this.getObjectStructure(CluCluRelationInfo.class.getName());
        Validator defaultValidator = validatorFactory.getValidator();

        List<org.kuali.student.r2.common.dto.ValidationResultInfo> vris = defaultValidator.validateObject(cluCluRelationInfo, objStructure, null);
        return vris;
    }

    @Override
    @Transactional(readOnly = false)
    public CluCluRelationInfo createCluCluRelation(String cluId,
                                                   String relatedCluId, String luLuRelationTypeKey,
                                                   CluCluRelationInfo cluCluRelationInfo, ContextInfo context)
            throws DataValidationErrorException,
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
        List<ValidationResultInfo> val =
                validateCluCluRelation("SYSTEM", cluId, relatedCluId, luLuRelationTypeKey, cluCluRelationInfo, context);
        if (null != val && val.size() > 0) {
            throw new DataValidationErrorException("Validation error!", val);
        }


        Clu clu;
        try {
            clu = luDao.fetch(Clu.class, cluId);
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException ex) {
            throw new DoesNotExistException(cluId, ex);
        }
        Clu relatedClu;
        try {
            relatedClu = luDao.fetch(Clu.class, relatedCluId);
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException ex) {
            throw new DoesNotExistException(relatedCluId, ex);
        }

        CluCluRelation cluCluRelation = new CluCluRelation();
        BeanUtils.copyProperties(cluCluRelationInfo, cluCluRelation,
                new String[]{"cluId", "relatedCluId",
                        "isCluRelationRequired", "attributes", "meta"});

        cluCluRelation.setClu(clu);
        cluCluRelation.setRelatedClu(relatedClu);
        cluCluRelation.setCluRelationRequired(cluCluRelationInfo.getIsCluRelationRequired() == null ? true : cluCluRelationInfo.getIsCluRelationRequired()); // TODO maybe this is unnecessary,
        // contract specifies not null
        cluCluRelation.setAttributes(CluServiceAssembler.toGenericAttributes(
                CluCluRelationAttribute.class, cluCluRelationInfo.getAttributes(), cluCluRelation, luDao));

        LuLuRelationType luLuRelationType;
        try {
            luLuRelationType = luDao.fetch(LuLuRelationType.class,
                    luLuRelationTypeKey);
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException ex) {
            throw new DoesNotExistException(luLuRelationTypeKey, ex);
        }

        cluCluRelation.setLuLuRelationType(luLuRelationType);

        luDao.create(cluCluRelation);

        return CluServiceAssembler.toCluCluRelationInfo(cluCluRelation);
    }

    @Override
    @Transactional(readOnly = false)
    public CluCluRelationInfo updateCluCluRelation(
            final String cluCluRelationId,
            final CluCluRelationInfo cluCluRelationInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException,
            VersionMismatchException {
        checkForMissingParameter(cluCluRelationId, "cluCluRelationId");
        checkForMissingParameter(cluCluRelationInfo, "cluCluRelationInfo");

        // Validate CluCluRelationInfo
        List<ValidationResultInfo> val =
                validateCluCluRelation("SYSTEM",
                        cluCluRelationInfo.getCluId(),
                        cluCluRelationInfo.getRelatedCluId(),
                        cluCluRelationInfo.getTypeKey(),
                        cluCluRelationInfo,
                        context);
        if (null != val && val.size() > 0) {
            throw new DataValidationErrorException("Validation error!", val);
        }

        final CluCluRelation cluCluRelation;
        try {
            cluCluRelation = luDao.fetch(CluCluRelation.class,
                    cluCluRelationId);
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException ex) {
            throw new DoesNotExistException(cluCluRelationId, ex);
        }
        BeanUtils.copyProperties(cluCluRelationInfo, cluCluRelation,
                new String[]{"cluId", "relatedCluId",
                        "isCluRelationRequired", "attributes", "meta"});
        try {
            cluCluRelation.setClu(luDao.fetch(Clu.class, cluCluRelationInfo.getCluId()));
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException ex) {
            throw new DoesNotExistException(cluCluRelationInfo.getCluId(), ex);
        }
        try {
            cluCluRelation.setRelatedClu(luDao.fetch(Clu.class, cluCluRelationInfo.getRelatedCluId()));
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException ex) {
            throw new DoesNotExistException(cluCluRelationInfo.getRelatedCluId(), ex);
        }
        cluCluRelation.setCluRelationRequired(cluCluRelationInfo.getIsCluRelationRequired() == null ? true : cluCluRelationInfo.getIsCluRelationRequired()); // TODO maybe this is unnecessary,
        // contract specifies not null
        cluCluRelation.setAttributes(CluServiceAssembler.toGenericAttributes(
                CluCluRelationAttribute.class, cluCluRelationInfo.getAttributes(), cluCluRelation, luDao));
        try {
            cluCluRelation.setLuLuRelationType(luDao.fetch(LuLuRelationType.class,
                    cluCluRelationInfo.getTypeKey()));
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException ex) {
            throw new DoesNotExistException(cluCluRelationInfo.getTypeKey(), ex);
        }

        final CluCluRelation update = luDao.update(cluCluRelation);

        return CluServiceAssembler.toCluCluRelationInfo(update);
    }

    @Override
    @Transactional(readOnly = false)
    public StatusInfo deleteCluCluRelation(String cluCluRelationId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        checkForMissingParameter(cluCluRelationId, "cluCluRelationId");
        try {
            luDao.delete(CluCluRelation.class, cluCluRelationId);
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException ex) {
            throw new DoesNotExistException(cluCluRelationId, ex);
        }

        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setSuccess(true);

        return statusInfo;
    }

    @Override
    public List<ValidationResultInfo> validateCluPublication(
            String validationType, String cluId, String luPublicationTypeKey,
            CluPublicationInfo cluPublicationInfo, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {

        checkForMissingParameter(validationType, "validationType");
        checkForMissingParameter(cluPublicationInfo, "cluPublicationInfo");

        ObjectStructureDefinition objStructure = this.getObjectStructure(CluPublicationInfo.class.getName());
        Validator defaultValidator = validatorFactory.getValidator();
        List<org.kuali.student.r2.common.dto.ValidationResultInfo> vris =
                defaultValidator.validateObject(cluPublicationInfo, objStructure, null);
        return vris;
    }

    @Override
    @Transactional(readOnly = false)
    public CluPublicationInfo createCluPublication(String cluId,
                                                   String luPublicationType, CluPublicationInfo cluPublicationInfo, ContextInfo context)
            throws DataValidationErrorException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(cluId, "cluId");
        checkForMissingParameter(luPublicationType, "luPublicationType");
        checkForMissingParameter(cluPublicationInfo, "cluPublicationInfo");

        // Validate CLU
        List<ValidationResultInfo> val;
        try {
            val = validateCluPublication("SYSTEM", cluId, luPublicationType, cluPublicationInfo, context);
            if (null != val && val.size() > 0) {
                throw new DataValidationErrorException("Validation error!", val);
            }
        } catch (DoesNotExistException e) {
            throw new OperationFailedException("Error creating clu", e);
        }


        CluPublication cluPub = new CluPublication();
        Clu clu;
        try {
            clu = luDao.fetch(Clu.class, cluId);
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException e) {
            throw new InvalidParameterException("Clu does not exist for id:" + cluId);
        }

        CluPublicationType type;
        try {
            type = luDao.fetch(CluPublicationType.class, luPublicationType);
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException e) {
            throw new InvalidParameterException("CluPublication Type does not exist for id:" + luPublicationType);
        }

        cluPub.setClu(clu);
        cluPub.setId(cluPublicationInfo.getId());
        cluPub.setEndCycle(cluPublicationInfo.getEndCycle());
        cluPub.setStartCycle(cluPublicationInfo.getStartCycle());
        cluPub.setEffectiveDate(cluPublicationInfo.getEffectiveDate());
        cluPub.setExpirationDate(cluPublicationInfo.getExpirationDate());
        cluPub.setState(cluPublicationInfo.getStateKey());
        cluPub.setType(type);
        cluPub.setAttributes(CluServiceAssembler.toGenericAttributes(CluPublicationAttribute.class, cluPublicationInfo.getAttributes(), cluPub, luDao));
        cluPub.setVariants(CluServiceAssembler.toCluPublicationVariants(cluPublicationInfo.getVariants(), cluPub, luDao));

        luDao.create(cluPub);

        return CluServiceAssembler.toCluPublicationInfo(cluPub);
    }

    @Override
    @Transactional(readOnly = false)
    public CluPublicationInfo updateCluPublication(String cluPublicationId,
                                                   CluPublicationInfo cluPublicationInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException,
            VersionMismatchException {
        checkForMissingParameter(cluPublicationId, "cluPublicationId");
        checkForMissingParameter(cluPublicationInfo, "cluPublicationInfo");

        // Validate CLU
        List<ValidationResultInfo> val;

        val = validateCluPublication("SYSTEM",
                cluPublicationInfo.getCluId(),
                cluPublicationInfo.getTypeKey(),
                cluPublicationInfo, context);
        if (null != val && val.size() > 0) {
            throw new DataValidationErrorException("Validation error!", val);
        }

        CluPublication cluPub;
        try {
            cluPub = luDao.fetch(CluPublication.class, cluPublicationId);
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException ex) {
            throw new DoesNotExistException(ex.getMessage(), ex);
        }

        if (!String.valueOf(cluPub.getVersionNumber()).equals(
                cluPublicationInfo.getMeta().getVersionInd())) {
            throw new VersionMismatchException(
                    "CluPublication to be updated is not the current version");
        }

        Clu clu;
        try {
            clu = luDao.fetch(Clu.class, cluPublicationInfo.getCluId());
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException e) {
            throw new InvalidParameterException("Clu does not exist for id:" + cluPublicationInfo.getCluId());
        }

        CluPublicationType type;
        try {
            type = luDao.fetch(CluPublicationType.class, cluPublicationInfo.getTypeKey());
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException e) {
            throw new InvalidParameterException("CluPublication Type does not exist for id:" + cluPublicationInfo.getTypeKey());
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
        cluPub.setAttributes(CluServiceAssembler.toGenericAttributes(CluPublicationAttribute.class, cluPublicationInfo.getAttributes(), cluPub, luDao));

        CluPublication updated = luDao.update(cluPub);

        return CluServiceAssembler.toCluPublicationInfo(updated);
    }

    @Override
    @Transactional(readOnly = false)
    public StatusInfo deleteCluPublication(String cluPublicationId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, DependentObjectsExistException,
            OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(cluPublicationId, "cluPublicationId");
        try {
            luDao.delete(CluPublication.class, cluPublicationId);
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException ex) {
            throw new DoesNotExistException(cluPublicationId, ex);
        }

        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setSuccess(true);

        return statusInfo;
    }

    @Override
    public List<ValidationResultInfo> validateCluResult(String validationType,
                                                        String cluId, String cluResultTypeKey,
                                                        CluResultInfo cluResultInfo, ContextInfo context)
            throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {
        checkForMissingParameter(validationType, "validationType");
        checkForMissingParameter(cluResultInfo, "cluResultInfo");

        ObjectStructureDefinition objStructure = this.getObjectStructure(CluResultInfo.class.getName());
        Validator defaultValidator = validatorFactory.getValidator();
        List<org.kuali.student.r2.common.dto.ValidationResultInfo> vris = defaultValidator.validateObject(cluResultInfo, objStructure, null);
        return vris;
    }

    @Override
    @Transactional(readOnly = false)
    public CluResultInfo createCluResult(String cluId, String cluResultTypeKey,
                                         CluResultInfo cluResultInfo, ContextInfo context) throws
            DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException, DoesNotExistException {

        checkForMissingParameter(cluId, "cluId");
        checkForMissingParameter(cluResultTypeKey, "cluResultTypeKey");
        checkForMissingParameter(cluResultInfo, "cluResultInfo");

        // Validate CluResult
        List<ValidationResultInfo> val = validateCluResult("SYSTEM",
                cluResultInfo.getCluId(),
                cluResultInfo.getTypeKey(),
                cluResultInfo, context);
        if (null != val && val.size() > 0) {
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
                ResultUsageType resUsageType;
                try {
                    resUsageType = luDao.fetch(ResultUsageType.class,
                            resOptInfo.getResultUsageTypeKey());
                } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException ex) {
                    throw new DoesNotExistException(resOptInfo.getResultUsageTypeKey(), ex);
                }
                resOpt.setResultUsageType(resUsageType);
            }
            resOpt.setDesc(CluServiceAssembler.toRichText(LuRichText.class, resOptInfo.getDescr()));
            resOpt.setCreateId(context.getPrincipalId());
            resOpt.setCreateTime(context.getCurrentDate());
            luDao.create(resOpt);
            resOptList.add(resOpt);
        }

        CluResult cluResult = new CluResult();
        BeanUtils.copyProperties(cluResultInfo, cluResult, new String[]{"id",
                "desc", "resultOptions", "meta"});

        cluResult.setDesc(CluServiceAssembler.toRichText(LuRichText.class, cluResultInfo.getDescr()));
        cluResult.setResultOptions(resOptList);

        Clu clu;
        try {
            clu = luDao.fetch(Clu.class, cluId);
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException ex) {
            throw new DoesNotExistException(cluId, ex);
        }
        cluResult.setClu(clu);

        CluResultType type;
        try {
            type = luDao.fetch(CluResultType.class, cluResultTypeKey);
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException ex) {
            throw new DoesNotExistException(cluResultTypeKey, ex);
        }
        cluResult.setCluResultType(type);
        cluResult.setCreateId(context.getPrincipalId());
        cluResult.setCreateTime(context.getCurrentDate());

        luDao.create(cluResult);

        return CluServiceAssembler.toCluResultInfo(cluResult);
    }

    @Override
    @Transactional(readOnly = false)
    public CluResultInfo updateCluResult(String cluResultId,
                                         CluResultInfo cluResultInfo, ContextInfo context) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException, VersionMismatchException {

        checkForMissingParameter(cluResultId, "cluResultId");
        checkForMissingParameter(cluResultInfo, "cluResultInfo");

        // Validate CluResult
        List<ValidationResultInfo> val = validateCluResult("SYSTEM",
                cluResultInfo.getCluId(),
                cluResultInfo.getTypeKey(),
                cluResultInfo,
                context);
        if (null != val && val.size() > 0) {
            throw new DataValidationErrorException("Validation error!", val);
        }

        CluResult result;
        try {
            result = luDao.fetch(CluResult.class, cluResultId);
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException ex) {
            throw new DoesNotExistException(cluResultId,ex);
        }
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
                opt.setCreateId(context.getPrincipalId());
                opt.setCreateTime(context.getCurrentDate());
            } else {
                try {
                    // Get existing result option
                    opt = luDao.fetch(ResultOption.class, resOptInfo.getId());
                } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException ex) {
                    throw new DoesNotExistException(resOptInfo.getId(), ex);
                }
                // Copy properties
                BeanUtils.copyProperties(resOptInfo, opt, new String[]{
                        "id", "resultUsageType", "desc"});
            }
            if (resOptInfo.getResultUsageTypeKey() != null && !resOptInfo.getResultUsageTypeKey().isEmpty()) {
                ResultUsageType resUsageType;
                try {
                    resUsageType = luDao.fetch(ResultUsageType.class,
                            resOptInfo.getResultUsageTypeKey());
                } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException ex) {
                    throw new DoesNotExistException(resOptInfo.getResultUsageTypeKey(), ex);
                }
                opt.setResultUsageType(resUsageType);
            }
            opt.setDesc(CluServiceAssembler.toRichText(LuRichText.class, resOptInfo.getDescr()));
            result.getResultOptions().add(opt);
        }

        // Now delete anything left over
        for (Entry<String, ResultOption> entry : oldResultOptionMap.entrySet()) {
            luDao.delete(entry.getValue());
        }

        BeanUtils.copyProperties(cluResultInfo, result, new String[]{"id",
                "desc", "resultOptions"});

        result.setDesc(CluServiceAssembler.toRichText(LuRichText.class, cluResultInfo.getDescr()));
        CluResultType type;
        try {
            type = luDao.fetch(CluResultType.class, cluResultInfo.getTypeKey());
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException ex) {
            throw new DoesNotExistException(cluResultInfo.getTypeKey(), ex);
        }
        result.setCluResultType(type);

        CluResult updated = luDao.update(result);

        return CluServiceAssembler.toCluResultInfo(updated);
    }

    @Override
    @Transactional(readOnly = false)
    public StatusInfo deleteCluResult(String cluResultId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, DependentObjectsExistException,
            OperationFailedException, PermissionDeniedException {

        checkForMissingParameter(cluResultId, "cluResultId");
        try {
            luDao.delete(CluResult.class, cluResultId);
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException ex) {
            throw new DoesNotExistException(cluResultId, ex);
        }

        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setSuccess(true);

        return statusInfo;
    }

    @Override
    public List<ValidationResultInfo> validateCluLoRelation(
            String validationType,
            String cluId,
            String loId,
            String cluLoRelationType,
            CluLoRelationInfo cluLoRelationInfo, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {

        checkForMissingParameter(validationType, "validationType");
        checkForMissingParameter(cluLoRelationInfo, "cluLoRelationInfo");

        ObjectStructureDefinition objStructure = this.getObjectStructure(CluLoRelation.class.getName());
        Validator defaultValidator = validatorFactory.getValidator();
        List<org.kuali.student.r2.common.dto.ValidationResultInfo> vris = defaultValidator.validateObject(cluLoRelationInfo, objStructure, null);
        return vris;
    }

    @Override
    @Transactional(readOnly = false)
    public CluLoRelationInfo createCluLoRelation(String cluId, String loId,
                                                 String cluLoRelationType, CluLoRelationInfo cluLoRelationInfo, ContextInfo context)
            throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, DataValidationErrorException {
        checkForMissingParameter(loId, "loId");
        checkForMissingParameter(cluId, "cluId");
        checkForEmptyList(cluLoRelationType, "cluLoRelationType");
        checkForEmptyList(cluLoRelationInfo, "cluLoRelationInfo");

        // Validate CluLoRelation
        List<ValidationResultInfo> val = validateCluLoRelation("SYSTEM", cluId, loId, cluLoRelationType, cluLoRelationInfo, context);
        if (null != val && val.size() > 0) {
            throw new DataValidationErrorException("Validation error!", val);
        }

        Clu clu;
        try {
            clu = luDao.fetch(Clu.class, cluId);
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException ex) {
            throw new DoesNotExistException(cluId, ex);
        }
        if (clu == null) {
            throw new DoesNotExistException("Clu does not exist for id: "
                    + cluId);
        }

        CluLoRelationType cluLoRelationTypeEntity;
        try {
            cluLoRelationTypeEntity = luDao.fetch(CluLoRelationType.class, cluLoRelationType);
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException ex) {
            throw new DoesNotExistException(cluLoRelationType, ex);
        }
        if (cluLoRelationTypeEntity == null) {
            throw new DoesNotExistException("CluLoRelationType does not exist for id: "
                    + cluLoRelationType);
        }

        // Check to see if this relation already exists
        List<CluLoRelation> reltns = luDao.getCluLoRelationsByCludIdAndLoId(
                cluId, loId);
        if (reltns.size() > 0) {
            // TODO: take this check out? R1 had this throwing an aleady exists exception but it is possible 
            // though unlikely for there to be two different relations between the same clu and Lo
            // So we took out the AlreadyExistException in R2 BUT something might be depending on this
            // So I kept it throwing an exception but changed it to throw an OperationFailed instead
            throw new OperationFailedException(
                    "Relation already exists for cluId:" + cluId + " and Lo:"
                            + loId);
        }

        CluLoRelation cluLoRelation = new CluLoRelation();
        BeanUtils.copyProperties(cluLoRelationInfo, cluLoRelation,
                new String[]{"cluId", "attributes", "meta", "type"});

        cluLoRelation.setClu(clu);
        cluLoRelation.setAttributes(CluServiceAssembler.toGenericAttributes(
                CluLoRelationAttribute.class,
                cluLoRelationInfo.getAttributes(), cluLoRelation, luDao));
        cluLoRelation.setType(cluLoRelationTypeEntity);

        luDao.create(cluLoRelation);

        return CluServiceAssembler.toCluLoRelationInfo(cluLoRelation);
    }

    @Override
    @Transactional(readOnly = false)
    public CluLoRelationInfo updateCluLoRelation(String cluLoRelationId,
                                                 CluLoRelationInfo cluLoRelationInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException,
            VersionMismatchException {
        checkForMissingParameter(cluLoRelationId, "cluLoRelationId");
        checkForMissingParameter(cluLoRelationInfo, "cluLoRelationInfo");

        // Validate CluLoRelation
        List<ValidationResultInfo> val = validateCluLoRelation("SYSTEM",
                cluLoRelationInfo.getCluId(),
                cluLoRelationInfo.getLoId(),
                cluLoRelationInfo.getTypeKey(),
                cluLoRelationInfo, context);
        if (null != val && val.size() > 0) {
            throw new DataValidationErrorException("Validation error!", val);
        }

        CluLoRelation reltn;
        try {
            reltn = luDao.fetch(CluLoRelation.class, cluLoRelationId);
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException ex) {
            throw new DoesNotExistException(cluLoRelationId, ex);
        }

        if (!String.valueOf(reltn.getVersionNumber()).equals(
                cluLoRelationInfo.getMeta().getVersionInd())) {
            throw new VersionMismatchException(
                    "CluLoRelation to be updated is not the current version");
        }

        Clu clu;
        try {
            clu = luDao.fetch(Clu.class, cluLoRelationInfo.getCluId());
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException ex) {
            throw new DoesNotExistException(cluLoRelationInfo.getCluId(), ex);
        }
        if (clu == null) {
            throw new DoesNotExistException("Clu does not exist for id: "
                    + cluLoRelationInfo.getCluId());
        }

        CluLoRelationType cluLoRelationTypeEntity;
        try {
            cluLoRelationTypeEntity = luDao.fetch(CluLoRelationType.class, cluLoRelationInfo.getTypeKey());
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException ex) {
            throw new DoesNotExistException(cluLoRelationInfo.getTypeKey(), ex);
        }
        if (cluLoRelationTypeEntity == null) {
            throw new DoesNotExistException("CluLoRelationType does not exist for id: "
                    + cluLoRelationInfo.getTypeKey());
        }

        BeanUtils.copyProperties(cluLoRelationInfo, reltn, new String[]{
                "cluId", "attributes", "meta", "type"});

        reltn.setClu(clu);
        reltn.setAttributes(CluServiceAssembler.toGenericAttributes(
                CluLoRelationAttribute.class,
                cluLoRelationInfo.getAttributes(), reltn, luDao));
        reltn.setType(cluLoRelationTypeEntity);
        CluLoRelation updated = luDao.update(reltn);

        return CluServiceAssembler.toCluLoRelationInfo(updated);
    }

    @Override
    @Transactional(readOnly = false)
    public StatusInfo deleteCluLoRelation(String cluLoRelationId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        checkForMissingParameter(cluLoRelationId, "cluLoRelationId");

        CluLoRelation reltn;
        try {
            reltn = luDao.fetch(CluLoRelation.class, cluLoRelationId);
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException ex) {
            throw new DoesNotExistException(cluLoRelationId);
        }
        if (reltn == null) {
            throw new DoesNotExistException(
                    "CluLoRelation does not exist for id: " + cluLoRelationId);
        }
        try {
            luDao.delete(CluLoRelation.class, cluLoRelationId);
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException ex) {
            throw new DoesNotExistException(cluLoRelationId, ex);
        }

        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setSuccess(true);

        return statusInfo;
    }

    @Override
    @Transactional(readOnly = false)
    public StatusInfo addCluResourceRequirement(String resourceTypeKey,
                                                String cluId, ContextInfo context) throws AlreadyExistsException, DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("not yet implemented");
    }

    @Override
    @Transactional(readOnly = false)
    public StatusInfo removeCluResourceRequirement(String resourceTypeKey,
                                                   String cluId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public List<ValidationResultInfo> validateCluSet(String validationType,
                                                     String cluSetType,
                                                     CluSetInfo cluSetInfo, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {
        checkForMissingParameter(validationType, "validationType");
        checkForMissingParameter(cluSetInfo, "cluSetInfo");

        ObjectStructureDefinition objStructure = this.getObjectStructure(CluSetInfo.class.getName());
        Validator defaultValidator = validatorFactory.getValidator();
        List<org.kuali.student.r2.common.dto.ValidationResultInfo> vris = defaultValidator.validateObject(cluSetInfo, objStructure, null);
        return vris;
    }

    @Override
    @Transactional(readOnly = false)
    public CluSetInfo createCluSet(String cluSetType, CluSetInfo cluSetInfo, ContextInfo contextInfo)
            throws DataValidationErrorException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException,
            UnsupportedActionException {

        checkForMissingParameter(cluSetType, "cluSetType");
        checkForMissingParameter(cluSetInfo, "cluSetInfo");

        cluSetInfo.setTypeKey(cluSetType);

        validateCluSet(cluSetInfo);

        // Validate CluSet
        List<ValidationResultInfo> val;
        try {
            val = validateCluSet("SYSTEM",
                    cluSetInfo.getTypeKey(),
                    cluSetInfo, contextInfo);
        } catch (DoesNotExistException e) {
            throw new DataValidationErrorException("Validation error! " + e.getMessage(), e);
        }
        if (null != val && val.size() > 0) {
            throw new DataValidationErrorException("Validation error!", val);
        }

        List<String> cluIds = getMembershipQuerySearchResult(cluSetInfo.getMembershipQuery(), contextInfo);

        CluSet cluSet = null;
        try {
            cluSet = CluServiceAssembler.toCluSetEntity(cluSetInfo, this.luDao);
        } catch (DoesNotExistException e) {
            throw new DataValidationErrorException("Creating CluSet entity failed. Clu or CluSet does not exist: " + e.getMessage(), e);
        }

        cluSet = luDao.create(cluSet);

        CluSetInfo newCluSetInfo = CluServiceAssembler.toCluSetInfo(cluSet);

        if (cluIds != null) {
            newCluSetInfo.getCluIds().addAll(cluIds);
        }

        return newCluSetInfo;
    }

    private void setMembershipQuerySearchResult(CluSetInfo cluSetInfo, ContextInfo contextInfo) throws MissingParameterException, PermissionDeniedException, OperationFailedException {
        if (cluSetInfo.getMembershipQuery() == null) {
            return;
        }
        List<String> cluIds = getMembershipQuerySearchResult(cluSetInfo.getMembershipQuery(), contextInfo);
        cluSetInfo.getCluIds().addAll(cluIds);
    }

    private List<String> getMembershipQuerySearchResult(MembershipQueryInfo query, ContextInfo contextInfo) throws MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (query == null) {
            return null;
        }

        SearchRequestInfo request = new SearchRequestInfo();
        request.setSearchKey(query.getSearchTypeKey());
        request.setParams(query.getQueryParamValues());

        SearchResultInfo result = search(request, contextInfo);

        Set<String> cluIds = new HashSet<String>();
        List<SearchResultRowInfo> rows = result.getRows();
        for (SearchResultRowInfo row : rows) {
            List<SearchResultCellInfo> cells = row.getCells();
            for (SearchResultCellInfo cell : cells) {
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
        } else if (mqInfo != null && mqInfo.getSearchTypeKey() != null && !mqInfo.getSearchTypeKey().isEmpty()
                && (cluSetInfo.getCluIds().size() > 0 || cluSetInfo.getCluSetIds().size() > 0)) {
            throw new UnsupportedActionException("Dynamic CluSet cannot contain Clus and/or CluSets. CluSet id=" + cluSetInfo.getId());
        } else if (cluSetInfo.getCluIds().size() > 0 && cluSetInfo.getCluSetIds().size() > 0) {
            throw new UnsupportedActionException("CluSet cannot contain both Clus and CluSets. CluSet id=" + cluSetInfo.getId());
        }
    }

    @Override
    @Transactional(readOnly = false)
    public CluSetInfo updateCluSet(String cluSetId, CluSetInfo cluSetInfo, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException,
            VersionMismatchException, CircularRelationshipException,
            UnsupportedActionException {

        // Check Missing params
        checkForMissingParameter(cluSetId, "cluSetId");
        checkForMissingParameter(cluSetInfo, "cluSetInfo");

        // Validate CluSet
        List<ValidationResultInfo> val = validateCluSet("SYSTEM",
                cluSetInfo.getTypeKey(),
                cluSetInfo,
                contextInfo);
        if (null != val && val.size() > 0) {
            throw new DataValidationErrorException("Validation error!", val);
        }

        cluSetInfo.setId(cluSetId);

        validateCluSet(cluSetInfo);

        List<String> cluIds = getMembershipQuerySearchResult(cluSetInfo.getMembershipQuery(), contextInfo);

        CluSet cluSet;
        try {
            cluSet = luDao.fetch(CluSet.class, cluSetId);
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException ex) {
            throw new DoesNotExistException(cluSetId, ex);
        }

        if (!cluSetInfo.getTypeKey().equals(cluSet.getType())) {
            throw new UnsupportedActionException("CluSet type is set at creation time and cannot be updated. CluSet id=" + cluSetId);
        }

        if (!String.valueOf(cluSet.getVersionNumber()).equals(
                cluSetInfo.getMeta().getVersionInd())) {
            throw new VersionMismatchException(
                    "CluSet (id=" + cluSetId
                            + ") to be updated is not the current version "
                            + "(version=" + cluSetInfo.getMeta().getVersionInd()
                            + "), current version=" + cluSet.getVersionNumber());
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
        cluSet.setAttributes(CluServiceAssembler.toGenericAttributes(
                CluSetAttribute.class, cluSetInfo.getAttributes(), cluSet, luDao));
        cluSet.setDescr(CluServiceAssembler.toRichText(LuRichText.class, cluSetInfo.getDescr()));

        MembershipQuery mq = CluServiceAssembler.toMembershipQueryEntity(cluSetInfo.getMembershipQuery());
        cluSet.setMembershipQuery(mq);

        CluSet updated = luDao.update(cluSet);

        CluSetInfo updatedCluSetInfo = CluServiceAssembler.toCluSetInfo(updated);

        if (cluIds != null) {
            updatedCluSetInfo.getCluIds().addAll(cluIds);
        }

        return updatedCluSetInfo;
    }

    @Override
    @Transactional(readOnly = false)
    public StatusInfo deleteCluSet(String cluSetId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        checkForMissingParameter(cluSetId, "cluSetId");
        try {
            luDao.delete(CluSet.class, cluSetId);
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException ex) {
            throw new DoesNotExistException(cluSetId, ex);
        }

        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setSuccess(true);

        return statusInfo;
    }

    @Override
    @Transactional(readOnly = false)
    public StatusInfo addCluSetToCluSet(String cluSetId, String addedCluSetId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException, UnsupportedActionException,
            CircularRelationshipException {
        checkForMissingParameter(cluSetId, "cluSetId");
        checkForMissingParameter(addedCluSetId, "addedCluSetId");

        CluSet cluSet;
        try {
            cluSet = luDao.fetch(CluSet.class, cluSetId);
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException ex) {
            throw new DoesNotExistException(cluSetId, ex);
        }

        checkCluSetAlreadyAdded(cluSet, addedCluSetId);

        CluSet addedCluSet;
        try {
            addedCluSet = luDao.fetch(CluSet.class, addedCluSetId);
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException ex) {
            throw new DoesNotExistException(addedCluSetId, ex);
        }

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

    private void checkCluSetAlreadyAdded(CluSet cluSet, String cluSetIdToAdd)
            throws OperationFailedException {
        if (cluSet.getCluSets() != null) {
            for (CluSet childCluSet : cluSet.getCluSets()) {
                if (childCluSet.getId().equals(cluSetIdToAdd)) {
                    throw new OperationFailedException("CluSet (id=" + cluSet.getId()
                            + ") already contains CluSet (id='" + cluSetIdToAdd + "')");
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
                            "CluSet (id=" + hostCluSetId
                                    + ") already contains this CluSet (id="
                                    + childSet.getId() + ")");
                }
                checkCluSetCircularReference(childSet, hostCluSetId);
            }
        }
    }

    @Override
    @Transactional(readOnly = false)
    public StatusInfo removeCluSetFromCluSet(String cluSetId,
                                             String removedCluSetId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException,
            UnsupportedActionException {

        checkForMissingParameter(cluSetId, "cluSetId");
        checkForMissingParameter(removedCluSetId, "removedCluSetId");

        CluSet cluSet;
        try {
            cluSet = luDao.fetch(CluSet.class, cluSetId);
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException ex) {
            throw new DoesNotExistException(cluSetId, ex);
        }
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
    public StatusInfo addCluToCluSet(String cluId, String cluSetId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException, UnsupportedActionException {

        checkForMissingParameter(cluId, "cluId");
        checkForMissingParameter(cluSetId, "cluSetId");

        StatusInfo statusInfo = new StatusInfo();

        CluSet cluSet;
        try {
            cluSet = luDao.fetch(CluSet.class, cluSetId);
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException ex) {
            throw new DoesNotExistException(cluSetId, ex);
        }

        if(!checkCluAlreadyAdded(cluSet, cluId)) {
            statusInfo.setSuccess(Boolean.FALSE);
            statusInfo.setMessage("CluSet already contains Clu (id='" + cluId + "')");
        }else {
            try {
                luDao.getCurrentCluVersionInfo(cluId, CluServiceConstants.CLU_NAMESPACE_URI);
            } catch (NoResultException e) {
                throw new DoesNotExistException("Could not get current clu version info by cluId", e);
            }

            CluSetJoinVersionIndClu join = new CluSetJoinVersionIndClu();
            join.setCluSet(cluSet);
            join.setCluVersionIndId(cluId);

            cluSet.getCluVerIndIds().add(join);

            luDao.update(cluSet);


            statusInfo.setSuccess(true);
        }

        return statusInfo;
    }

    private boolean checkCluAlreadyAdded(CluSet cluSet, String cluId)
            throws OperationFailedException {
        for (CluSetJoinVersionIndClu join : cluSet.getCluVerIndIds()) {
            if (join.getCluVersionIndId().equals(cluId)) {
                return false;
            }
        }
        return true;
    }

    @Override
    @Transactional(readOnly = false)
    public StatusInfo removeCluFromCluSet(String cluId, String cluSetId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException, UnsupportedActionException {

        checkForMissingParameter(cluId, "cluId");
        checkForMissingParameter(cluSetId, "cluSetId");

        CluSet cluSet;
        try {
            cluSet = luDao.fetch(CluSet.class, cluSetId);
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException ex) {
            throw new DoesNotExistException(cluSetId, ex);
        }

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

    public LuDao getLuDao() {
        return luDao;
    }

    public void setLuDao(LuDao luDao) {
        this.luDao = luDao;
    }

    /**
     * Check for missing parameter and throw localized exception if missing
     *
     * @param param
     * @param paramName
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
    public StatusInfo addCluSetsToCluSet(String cluSetId, List<String> cluSetIds, ContextInfo context)
            throws CircularRelationshipException,
            DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException, UnsupportedActionException {

        checkForMissingParameter(cluSetId, "cluSetId");
        checkForMissingParameter(cluSetIds, "cluSetIds");
        try {
            // Check that CluSet exists
            luDao.fetch(CluSet.class, cluSetId);
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException ex) {
            throw new DoesNotExistException(cluSetId, ex);
        }

        for (String cluSetIdToAdd : cluSetIds) {
            StatusInfo status = addCluSetToCluSet(cluSetId, cluSetIdToAdd, context);
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
    public StatusInfo addClusToCluSet(List<String> cluIds, String cluSetId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException, UnsupportedActionException {
        StatusInfo statusInfo = new StatusInfo();

        checkForMissingParameter(cluIds, "cluIds");
        checkForMissingParameter(cluSetId, "cluSetId");

        for (String cluId : cluIds) {
            StatusInfo status = addCluToCluSet(cluId, cluSetId, context);
            if (!status.getIsSuccess()) {
                if(statusInfo.getMessage().isEmpty()){
                    statusInfo.setMessage(status.getMessage());
                }else{
                    statusInfo.setMessage(statusInfo.getMessage()+"\n"+status.getMessage());
                }
            }
        }

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
    public CluInfo createNewCluVersion(String versionIndCluId,
                                       String versionComment,
                                       ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        Clu latestClu;
        Clu currentClu;
        try {
            latestClu = luDao.getLatestCluVersion(versionIndCluId);
        } catch (NoResultException e) {
            throw new DoesNotExistException("There are no matching versions of this clu", e);
        }
        try {
            currentClu = luDao.getCurrentCluVersion(versionIndCluId);
        } catch (NoResultException e) {
            throw new DoesNotExistException("There is no current version of this clu. Only current clus can be versioned. Use setCurrentCluVersion to make a clu current.", e);
        }

        CluInfo cluInfo = CluServiceAssembler.toCluInfo(currentClu);

        // Reset the Clu
        clearCluIds(cluInfo);

        // Create the new Clu Version	    
        CluInfo newClu = null;

        try {
            Clu clu = toCluForCreate(cluInfo.getTypeKey(), cluInfo, context);
            //Set the Version data
            Version version = new Version();
            version.setSequenceNumber(latestClu.getVersion().getSequenceNumber() + 1);
            version.setVersionIndId(versionIndCluId);
            version.setCurrentVersionStart(null);
            version.setCurrentVersionEnd(null);
            version.setVersionComment(versionComment);
            version.setVersionedFromId(currentClu.getId());
            clu.setVersion(version);
            luDao.create(clu);
            newClu = CluServiceAssembler.toCluInfo(clu);
        } catch (AlreadyExistsException e) {
            throw new OperationFailedException("Error creating a new clu version", e);
        }

        return newClu;
    }

    private void clearCluIds(CluInfo clu) {
        // Clear out all Ids so a copy can be made
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
     * This method sets the CLU with ID of cluVersionId as the current version and will set the version end date of the previously current version to currentVersionStart or now() if null.  This will NOT update state of the current or previously current CLU.  All state changes must be handled either by the business service or from the atpService application.
     *
     * @param currentVersionStart if set to null, will default the current version start to the time when this method is called.
     * You can set this to a future date as well. 
     */
    @Override
    @Transactional(readOnly = false)
    public StatusInfo setCurrentCluVersion(String cluVersionId, Date currentVersionStart, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, IllegalVersionSequencingException, OperationFailedException, PermissionDeniedException {
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
        Clu clu;
        try {
            clu = luDao.fetch(Clu.class, cluVersionId);
        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException ex) {
            throw new DoesNotExistException(cluVersionId, ex);
        }
        String versionIndId = clu.getVersion().getVersionIndId();

        Clu oldClu = null;
        try {
            oldClu = luDao.getCurrentCluVersion(versionIndId);
        } catch (NoResultException e) {
        }

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
        List<VersionDisplayInfo> versionsInFuture = luDao.getVersionsInDateRange(versionIndId, null, currentDbDate, null);
        for (VersionDisplayInfo versionInFuture : versionsInFuture) {
            if (oldClu == null || !versionInFuture.getId().equals(oldClu.getId())) {
                VersionEntity futureClu;
                try {
                    futureClu = luDao.fetch(Clu.class, versionInFuture.getId());
                } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException ex) {
                    throw new DoesNotExistException(versionInFuture.getId(), ex);
                }
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

    private SearchResultInfo doBrowseProgramSearch(ContextInfo contextInfo) throws MissingParameterException, PermissionDeniedException, OperationFailedException {
        //This is our main result
        SearchResultInfo programSearchResults = searchManager.search(new SearchRequestInfo(SEARCH_KEY_BROWSE_PROGRAM), contextInfo);

        //These variations need to be mapped back to the program search results
        SearchResultInfo variationSearchResults = searchManager.search(new SearchRequestInfo(SEARCH_KEY_BROWSE_VARIATIONS),
                contextInfo);

        //Get a mapping of program id to variation long name mapping:
        Map<String, List<String>> variationMapping = new HashMap<String, List<String>>();
        for (SearchResultRowInfo row : variationSearchResults.getRows()) {
            String programId = null;
            String variationLongName = null;
            for (SearchResultCellInfo cell : row.getCells()) {
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
        SearchRequestInfo resultComponentSearchRequest = new SearchRequestInfo(SEARCH_KEY_RESULT_COMPONENT);
        resultComponentSearchRequest.addParam("lrc.queryParam.resultComponent.type",
                "kuali.result.values.group.type.fixed");
        resultComponentSearchRequest.addParam("lrc.queryParam.resultComponent.resultScaleId",
                "kuali.result.scale.degree");

        SearchResultInfo resultComponentSearchResults = searchDispatcher.search(resultComponentSearchRequest, contextInfo);

        //Get a mapping of result type id to result type name:
        Map<String, String> resultComponentMapping = new HashMap<String, String>();
        for (SearchResultRowInfo row : resultComponentSearchResults.getRows()) {
            String resultComponentTypeId = null;
            String resultComponentTypeName = null;
            for (SearchResultCellInfo cell : row.getCells()) {
                if ("lrc.resultColumn.resultComponent.id".equals(cell.getKey())) {
                    resultComponentTypeId = cell.getValue();
                } else if ("lrc.resultColumn.resultComponent.name".equals(cell.getKey())) {
                    resultComponentTypeName = cell.getValue();
                }
            }
            resultComponentMapping.put(resultComponentTypeId, resultComponentTypeName);
        }

        Map<String, Set<SearchResultCellInfo>> orgIdToCellMapping = new HashMap<String, Set<SearchResultCellInfo>>();
        Map<String, Set<SearchResultCellInfo>> resultComponentToCellMapping = new HashMap<String, Set<SearchResultCellInfo>>();
        Map<String, Set<SearchResultCellInfo>> campusToCellMapping = new HashMap<String, Set<SearchResultCellInfo>>();
        Map<String, SearchResultCellInfo> progIdToOrgCellMapping = new HashMap<String, SearchResultCellInfo>();
        Map<String, SearchResultCellInfo> progIdToResultComponentCellMapping = new HashMap<String, SearchResultCellInfo>();
        Map<String, SearchResultCellInfo> progIdToCampusCellMapping = new HashMap<String, SearchResultCellInfo>();

        //We need to reduce the programSearchResults, translating variations, result options, etc and creating a mapping for org id translation
        for (Iterator<SearchResultRowInfo> rowIter = programSearchResults.getRows().iterator(); rowIter.hasNext();) {
            SearchResultRowInfo row = rowIter.next();
            String programId = null;
            String orgId = null;
            String resultComponentName = null;
            String campusCode = null;
            SearchResultCellInfo orgCell = null;
            SearchResultCellInfo resultComponentCell = null;
            SearchResultCellInfo variationCell = null;
            SearchResultCellInfo campusLocationCell = null;

            for (SearchResultCellInfo cell : row.getCells()) {
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
                Set<SearchResultCellInfo> orgCells = orgIdToCellMapping.get(orgId);
                if (orgCells == null) {
                    orgCells = new HashSet<SearchResultCellInfo>();
                    orgIdToCellMapping.put(orgId, orgCells);
                }
                orgCells.add(orgCell);
                orgCell.setValue(null);

                //Add this to the map
                Set<SearchResultCellInfo> campusCells = campusToCellMapping.get(campusCode);
                if (campusCells == null) {
                    campusCells = new HashSet<SearchResultCellInfo>();
                    campusToCellMapping.put(campusCode, campusCells);
                }
                campusCells.add(campusLocationCell);
                campusLocationCell.setValue(null);

                //Add this to the map
                Set<SearchResultCellInfo> resultCells = resultComponentToCellMapping.get(resultComponentName);
                if (resultCells == null) {
                    resultCells = new HashSet<SearchResultCellInfo>();
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
                Set<SearchResultCellInfo> resultCells = resultComponentToCellMapping.get(resultComponentName);
                if (resultCells == null) {
                    resultCells = new HashSet<SearchResultCellInfo>();
                    resultComponentToCellMapping.put(resultComponentName, resultCells);
                }
                resultCells.add(progIdToResultComponentCellMapping.get(programId));

                //Add a new mapping to the org cell for this org id
                Set<SearchResultCellInfo> orgCells = orgIdToCellMapping.get(orgId);
                if (orgCells == null) {
                    orgCells = new HashSet<SearchResultCellInfo>();
                    orgIdToCellMapping.put(orgId, orgCells);
                }
                orgCells.add(progIdToOrgCellMapping.get(programId));

                //Concatenate the campus location
                Set<SearchResultCellInfo> campusCells = campusToCellMapping.get(campusCode);
                if (campusCells == null) {
                    campusCells = new HashSet<SearchResultCellInfo>();
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
                Set<SearchResultCellInfo> cells = resultComponentToCellMapping.get(resultComponentName);
                if (cells != null) {
                    for (SearchResultCellInfo cell : cells) {
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
                Set<SearchResultCellInfo> cells = campusToCellMapping.get(campusCode);
                if (cells != null) {
                    for (SearchResultCellInfo cell : cells) {
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
            SearchRequestInfo orgIdTranslationSearchRequest = new SearchRequestInfo("org.search.generic");
            orgIdTranslationSearchRequest.addParam("org.queryParam.orgOptionalIds", new ArrayList<String>(
                    orgIdToCellMapping.keySet()));
            orgIdTranslationSearchRequest.setSortColumn("org.resultColumn.orgShortName");
            SearchResultInfo orgIdTranslationSearchResult = searchDispatcher.search(orgIdTranslationSearchRequest, contextInfo);

            //For each translation, update the result cell with the translated org name
            for (SearchResultRowInfo row : orgIdTranslationSearchResult.getRows()) {

                //Get Params
                String orgId = "";
                String orgName = "";
                for (SearchResultCellInfo cell : row.getCells()) {
                    if ("org.resultColumn.orgId".equals(cell.getKey())) {
                        orgId = cell.getValue();
                        continue;
                    } else if ("org.resultColumn.orgShortName".equals(cell.getKey())) {
                        orgName = cell.getValue();
                    }
                }

                //Concatenate org names in the holder cells
                Set<SearchResultCellInfo> cells = orgIdToCellMapping.get(orgId);
                if (cells != null) {
                    for (SearchResultCellInfo cell : cells) {
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

    private SearchResultInfo doDependencyAnalysisSearch(String cluId, ContextInfo contextInfo) throws MissingParameterException,
            DoesNotExistException, PermissionDeniedException, OperationFailedException {

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
                MembershipQueryInfo queryInfo = CluServiceAssembler.toMembershipQueryInfo(cluSet.getMembershipQuery());
                List<String> memberCluVersionIndIds = getMembershipQuerySearchResult(queryInfo, contextInfo);
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
        SearchRequestInfo statementSearchRequest = new SearchRequestInfo("stmt.search.dependencyAnalysis");

        statementSearchRequest.addParam("stmt.queryParam.cluSetIds", new ArrayList<String>(cluSetMap.keySet()));
        statementSearchRequest.addParam("stmt.queryParam.cluVersionIndIds", cluVersionIndIds);

        SearchResultInfo statementSearchResult = searchDispatcher.search(statementSearchRequest, contextInfo);

        //Create a search result for the return value
        SearchResultInfo searchResult = new SearchResultInfo();

        Map<String, List<SearchResultCellInfo>> orgIdToCellMapping = new HashMap<String, List<SearchResultCellInfo>>();

        //Now we need to take the statement ids and find the clus that relate to them
        //We will also transform the search result from the statement search result to
        //the dependency analysis search result
        Set<String> processed = new HashSet<String>();
        for (SearchResultRowInfo stmtRow : statementSearchResult.getRows()) {

            //Determine result column values
            String refObjId = null;
            String statementType = null;
            String statementTypeName = null;
            String rootId = null;
            String requirementComponentIds = null;

            for (SearchResultCellInfo stmtCell : stmtRow.getCells()) {
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

                SearchResultRowInfo resultRow = new SearchResultRowInfo();

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
                SearchResultCellInfo orgIdsCell = new SearchResultCellInfo("lu.resultColumn.luOptionalOversightCommitteeIds",
                        null);
                resultRow.getCells().add(orgIdsCell);

                //Make a holder cell for the org ids, to be populated later
                SearchResultCellInfo orgNamesCell = new SearchResultCellInfo(
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
                        List<SearchResultCellInfo> cells = orgIdToCellMapping.get(adminOrg.getOrgId());
                        if (cells == null) {
                            cells = new ArrayList<SearchResultCellInfo>();
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
            SearchRequestInfo orgIdTranslationSearchRequest = new SearchRequestInfo("org.search.generic");
            orgIdTranslationSearchRequest.addParam("org.queryParam.orgOptionalIds", new ArrayList<String>(
                    orgIdToCellMapping.keySet()));
            SearchResultInfo orgIdTranslationSearchResult = searchDispatcher.search(orgIdTranslationSearchRequest, contextInfo);

            //For each translation, update the result cell with the translated org name
            for (SearchResultRowInfo row : orgIdTranslationSearchResult.getRows()) {

                //Get Params
                String orgId = "";
                String orgName = "";
                for (SearchResultCellInfo cell : row.getCells()) {
                    if ("org.resultColumn.orgId".equals(cell.getKey())) {
                        orgId = cell.getValue();
                        continue;
                    } else if ("org.resultColumn.orgShortName".equals(cell.getKey())) {
                        orgName = cell.getValue();
                    }
                }

                //Concatenate org names in the holder cells
                List<SearchResultCellInfo> cells = orgIdToCellMapping.get(orgId);
                if (cells != null) {
                    for (SearchResultCellInfo cell : cells) {
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

                SearchResultRowInfo resultRow = new SearchResultRowInfo();

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

                SearchResultRowInfo resultRow = new SearchResultRowInfo();

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
                SearchResultRowInfo resultRow = new SearchResultRowInfo();

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

    public class SearchResultRowComparator implements Comparator<SearchResultRowInfo> {
        private String sortColumn;

        SearchResultRowComparator(String sortColumn) {
            super();
            this.sortColumn = sortColumn;
        }

        @Override
        public int compare(SearchResultRowInfo o1, SearchResultRowInfo o2) {
            String o1SortValue = null;
            String o2SortValue = null;
            for (SearchResultCellInfo cell : o1.getCells()) {
                if (sortColumn.equals(cell.getKey())) {
                    o1SortValue = cell.getValue();
                    break;
                }
            }
            for (SearchResultCellInfo cell : o2.getCells()) {
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

    private SearchResultInfo doSearchProposalsByCourseCode(String courseCode, ContextInfo contextInfo) throws MissingParameterException, PermissionDeniedException, OperationFailedException {
        if(courseCode==null||courseCode.isEmpty()){
            return new SearchResultInfo();
        }
        //First do a search of courses with said code
        SearchRequestInfo sr = new SearchRequestInfo("lu.search.mostCurrent.union");
        sr.addParam("lu.queryParam.luOptionalCode", courseCode);
        sr.addParam("lu.queryParam.luOptionalType","kuali.lu.type.CreditCourse");
        SearchResultInfo results = search(sr, contextInfo);
        Map<String,String> cluIdToCodeMap = new HashMap<String,String>();
        for(SearchResultRowInfo row:results.getRows()){
            String cluId = null;
            String code = null;
            for(SearchResultCellInfo cell:row.getCells()){
                if("lu.resultColumn.cluId".equals(cell.getKey())){
                    cluId = cell.getValue();
                }else if("lu.resultColumn.luOptionalCode".equals(cell.getKey())){
                    code = cell.getValue();
                }
            }
            //Create a mapping of Clu Id to code to dereference later
            if(code!=null&&cluId!=null){
                cluIdToCodeMap.put(cluId, code);
            }
        }

        //Do a search for proposals that refer to the clu ids we found
        sr = new SearchRequestInfo("proposal.search.proposalsForReferenceIds");
        sr.addParam("proposal.queryParam.proposalOptionalReferenceIds", new ArrayList<String>(cluIdToCodeMap.keySet()));
        results = searchDispatcher.search(sr, contextInfo);
        for(SearchResultRowInfo row:results.getRows()){
            String cluId = null;
            SearchResultCellInfo proposalNameCell = null;

            for(SearchResultCellInfo cell:row.getCells()){
                if("proposal.resultColumn.proposalOptionalName".equals(cell.getKey())){
                    proposalNameCell = cell;
                    cell.setKey("lu.resultColumn.proposalOptionalName");
                }else if("proposal.resultColumn.proposalOptionalReferenceId".equals(cell.getKey())){
                    cluId = cell.getValue();
                    cell.setKey("lu.resultColumn.proposalOptionalReferenceId");
                }else if("proposal.resultColumn.proposalId".equals(cell.getKey())){
                    cell.setKey("lu.resultColumn.proposalId");
                }
            }
            //update the name of the proposal to reflect the course number
            proposalNameCell.setValue(cluIdToCodeMap.get(cluId)+" ("+proposalNameCell.getValue()+")");
        }

        return results;
    }

    /**
     * Looks up Atp descriptions and adds to search results
     * @param searchRequest
     * @return
     * @throws MissingParameterException
     */
    private SearchResultInfo doBrowseVersionsSearch(SearchRequestInfo searchRequest, ContextInfo contextInfo) throws MissingParameterException, PermissionDeniedException, OperationFailedException {
        SearchResultInfo searchResult = searchManager.search(searchRequest, contextInfo);

        Map<String,List<SearchResultCellInfo>> atpIdToCellMapping = new HashMap<String,List<SearchResultCellInfo>>();

        for(SearchResultRowInfo row:searchResult.getRows()){
            for(SearchResultCellInfo cell:row.getCells()){
                if(cell.getValue()!=null &&
                        ("lu.resultColumn.luOptionalExpFirstAtpDisplay".equals(cell.getKey()) ||
                                "lu.resultColumn.luOptionalLastAtpDisplay".equals(cell.getKey()))) {
                    List<SearchResultCellInfo> cells = atpIdToCellMapping.get(cell.getValue());
                    if(cells==null){
                        cells = new ArrayList<SearchResultCellInfo>();
                        atpIdToCellMapping.put(cell.getValue(), cells);
                    }
                    cells.add(cell);
                }
            }
        }
        //Now do an atp search to translate ids to names

        SearchRequestInfo atpSearchRequest = new SearchRequestInfo("atp.search.advancedAtpSearch");
        atpSearchRequest.addParam("atp.advancedAtpSearchParam.optionalAtpIds", new ArrayList<String>(atpIdToCellMapping.keySet()));
        SearchResultInfo atpSearchResults = searchDispatcher.search(atpSearchRequest, contextInfo);
        for(SearchResultRowInfo row:atpSearchResults.getRows()){
            String atpId = null;
            String atpName = null;
            for(SearchResultCellInfo cell:row.getCells()){
                if("atp.resultColumn.atpId".equals(cell.getKey())){
                    atpId = cell.getValue();
                }else if("atp.resultColumn.atpShortName".equals(cell.getKey())){
                    atpName = cell.getValue();
                }
            }
            if(atpId!=null && atpIdToCellMapping.get(atpId)!=null){
                for(SearchResultCellInfo cell : atpIdToCellMapping.get(atpId)){
                    cell.setValue(atpName);
                }
            }
        }

        return searchResult;
    }

    /**
     * Does a cross search to first get result componets from the lu search and then use an LRC search to get the result component names
     * @param cluSearchRequest
     * @return
     * @throws MissingParameterException
     */
    private SearchResultInfo doResultComponentTypesForCluSearch(SearchRequestInfo cluSearchRequest, ContextInfo contextInfo) throws MissingParameterException, PermissionDeniedException, OperationFailedException {

        SearchResultInfo searchResult = searchManager.search(cluSearchRequest, contextInfo);

        //Get the result Component Ids using a search
        Map<String,List<SearchResultRowInfo>> rcIdToRowMapping = new HashMap<String,List<SearchResultRowInfo>>();

        //Get a mapping of ids to translate
        for(SearchResultRowInfo row:searchResult.getRows()){
            for(SearchResultCellInfo cell:row.getCells()){
                if(cell.getValue()!=null &&
                        "lu.resultColumn.resultComponentId".equals(cell.getKey())) {
                    List<SearchResultRowInfo> rows = rcIdToRowMapping.get(cell.getValue());
                    if(rows==null){
                        rows = new ArrayList<SearchResultRowInfo>();
                        rcIdToRowMapping.put(cell.getValue(), rows);
                    }
                    rows.add(row);
                }
            }
        }

        //Get the LRC names to match the ids
        SearchRequestInfo lrcSearchRequest = new SearchRequestInfo(SEARCH_KEY_RESULT_COMPONENT);
        lrcSearchRequest.addParam("lrc.queryParam.resultComponent.idRestrictionList", new ArrayList<String>(rcIdToRowMapping.keySet()));
        SearchResultInfo lrcSearchResults = searchDispatcher.search(lrcSearchRequest, contextInfo);

        //map the names back to the original search results
        for(SearchResultRowInfo row:lrcSearchResults.getRows()){
            String lrcId = null;
            String lrcName = null;
            for(SearchResultCellInfo cell:row.getCells()){
                if("lrc.resultColumn.resultComponent.id".equals(cell.getKey())){
                    lrcId = cell.getValue();
                }else if("lrc.resultColumn.resultComponent.name".equals(cell.getKey())){
                    lrcName = cell.getValue();
                }
            }
            if(lrcId!=null && rcIdToRowMapping.get(lrcId)!=null){
                for(SearchResultRowInfo resultRow : rcIdToRowMapping.get(lrcId)){
                    resultRow.addCell("lu.resultColumn.resultComponentName",lrcName);
                }
            }
        }

        return searchResult;
    }

    @Override
    @Transactional(readOnly=true)
    public SearchResultInfo search(SearchRequestInfo searchRequest, ContextInfo contextInfo) throws MissingParameterException, PermissionDeniedException, OperationFailedException {
        checkForMissingParameter(searchRequest, "searchRequest");

        if (SEARCH_KEY_DEPENDENCY_ANALYSIS.equals(searchRequest.getSearchKey())) {
            String cluId = null;
            for (SearchParamInfo param : searchRequest.getParams()) {
                if ("lu.queryParam.luOptionalCluId".equals(param.getKey())) {
                    cluId = (String) param.getValues().get(0);
                    break;
                }
            }
            try {
                return doDependencyAnalysisSearch(cluId, contextInfo);
            } catch (DoesNotExistException e) {
                throw new RuntimeException("Error performing search");//FIXME should be more checked service exceptions thrown
            }
        } else if (SEARCH_KEY_BROWSE_PROGRAM.equals(searchRequest.getSearchKey())) {
            return doBrowseProgramSearch(contextInfo);
        }else if(SEARCH_KEY_PROPOSALS_BY_COURSE_CODE.equals(searchRequest.getSearchKey())){
            String courseCode = null;
            for(SearchParamInfo param:searchRequest.getParams()){
                if("lu.queryParam.luOptionalCode".equals(param.getKey())){
                    courseCode = (String)param.getValues().get(0);
                    break;
                }
            }
            return doSearchProposalsByCourseCode(courseCode, contextInfo);
        }else if(SEARCH_KEY_BROWSE_VERSIONS.equals(searchRequest.getSearchKey())){
            return doBrowseVersionsSearch(searchRequest, contextInfo);
        }else if(SEARCH_KEY_LU_RESULT_COMPONENTS.equals(searchRequest.getSearchKey())){
            return doResultComponentTypesForCluSearch(searchRequest, contextInfo);
        }else if(SEARCH_KEY_CLUSET_SEARCH_GENERIC.equals(searchRequest.getSearchKey())){
            //If any clu specific params are set, use a search key that has the clu defined in the JPQL
            for(SearchParamInfo param:searchRequest.getParams()){
                if(param.getKey().contains("queryParam.luOptional")){
                    searchRequest.setSearchKey(SEARCH_KEY_CLUSET_SEARCH_GENERICWITHCLUS);
                    break;
                }
            }
        }
        return searchManager.search(searchRequest, contextInfo);


    }

    @Override
    public VersionDisplayInfo getLatestVersion(String refObjectTypeURI, String refObjectId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        VersionDisplayInfo versionInfo = null;
        if (CluServiceConstants.CLU_NAMESPACE_URI.equals(refObjectTypeURI)) {
            try {
                versionInfo = this.getLatestVersion(refObjectId, refObjectTypeURI, context);
            } catch (NoResultException e) {
                throw new DoesNotExistException("getLatestVersion returned no result", e);
            }
        } else {
            throw new UnsupportedOperationException("This method does not know how to handle object type:" + refObjectTypeURI);
        }
        return versionInfo;
    }

    @Override
    public VersionDisplayInfo getCurrentVersion(String refObjectTypeURI, String refObjectId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        VersionDisplayInfo versionInfo = null;
        if (CluServiceConstants.CLU_NAMESPACE_URI.equals(refObjectTypeURI)) {
            try {
                versionInfo = luDao.getCurrentCluVersionInfo(refObjectId, refObjectTypeURI);
            } catch (NoResultException e) {
                throw new DoesNotExistException("getCurrentCluVersionInfo could not get current CLU version info", e);
            }
        } else {
            throw new UnsupportedOperationException("This method does not know how to handle object type:" + refObjectTypeURI);
        }
        return versionInfo;
    }

    @Override
    public VersionDisplayInfo getCurrentVersionOnDate(String refObjectTypeURI, String refObjectId, Date date, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        VersionDisplayInfo versionInfo = null;
        if (CluServiceConstants.CLU_NAMESPACE_URI.equals(refObjectTypeURI)) {
            try {
                versionInfo = luDao.getCurrentVersionOnDate(refObjectId, refObjectTypeURI, date);
            } catch (NoResultException e) {
                throw new DoesNotExistException("getCurrentCluVersionInfo could not get current CLU version info", e);
            }
        } else {
            throw new UnsupportedOperationException("This method does not know how to handle object type:" + refObjectTypeURI);
        }
        return versionInfo;
    }

    @Override
    public VersionDisplayInfo getFirstVersion(String refObjectTypeURI, String refObjectId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        VersionDisplayInfo versionInfo = null;
        if (CluServiceConstants.CLU_NAMESPACE_URI.equals(refObjectTypeURI)) {
            try {
                versionInfo = luDao.getFirstVersion(refObjectId, refObjectTypeURI);
            } catch (NoResultException e) {
                throw new DoesNotExistException("getFirstVersion could not get first version", e);
            }
        } else {
            throw new UnsupportedOperationException("This method does not know how to handle object type:" + refObjectTypeURI);
        }
        return versionInfo;
    }

    @Override
    public VersionDisplayInfo getVersionBySequenceNumber(String refObjectTypeURI, String refObjectId,
                                                         Long sequence, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        VersionDisplayInfo versionInfo = null;
        if (CluServiceConstants.CLU_NAMESPACE_URI.equals(refObjectTypeURI)) {
            try {
                versionInfo = luDao.getVersionBySequenceNumber(refObjectId, refObjectTypeURI, sequence);
            } catch (NoResultException e) {
                throw new DoesNotExistException("getVersionBySequenceNumber", e);
            }
        } else {
            throw new UnsupportedOperationException("This method does not know how to handle object type:" + refObjectTypeURI);
        }
        return versionInfo;
    }

    @Override
    public List<VersionDisplayInfo> getVersions(String refObjectTypeURI, String refObjectId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<VersionDisplayInfo> versionInfos = null;
        if (CluServiceConstants.CLU_NAMESPACE_URI.equals(refObjectTypeURI)) {
            versionInfos = luDao.getVersions(refObjectId, refObjectTypeURI);
            if (versionInfos == null) {
                versionInfos = Collections.<VersionDisplayInfo>emptyList();
            }
        } else {
            throw new UnsupportedOperationException("This method does not know how to handle object type:" + refObjectTypeURI);
        }
        return versionInfos;
    }

    @Override
    public List<VersionDisplayInfo> getVersionsInDateRange(String refObjectTypeURI,
                                                           String refObjectId,
                                                           Date from,
                                                           Date to,
                                                           ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<VersionDisplayInfo> versionInfos = null;
        if (CluServiceConstants.CLU_NAMESPACE_URI.equals(refObjectTypeURI)) {
            versionInfos = luDao.getVersionsInDateRange(refObjectId, refObjectTypeURI, from, to);
            if (versionInfos == null) {
                versionInfos = Collections.<VersionDisplayInfo>emptyList();
            }
        } else {
            throw new UnsupportedOperationException("This method does not know how to handle object type:" + refObjectTypeURI);
        }
        return versionInfos;
    }

    public void setSearchDispatcher(SearchService searchDispatcher) {
        this.searchDispatcher = searchDispatcher;
    }

    private ObjectStructureDefinition getObjectStructure(String objectTypeKey) {
        return dictionaryServiceDelegate.getObjectStructure(objectTypeKey);
    }

    /* (non-Javadoc)
     * @see org.kuali.student.r2.lum.clu.service.CluService#getCluResultsByClus(java.util.List, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public List<CluResultInfo> getCluResultsByClus(
             List<String> cluIds,
             ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        checkForMissingParameter(cluIds, "cluIds");
        checkForEmptyList(cluIds, "cluIds");
        return CluServiceAssembler.toCluResultInfos(luDao.getCluResultsByClus(cluIds));

    }

    /* (non-Javadoc)
     * @see org.kuali.student.r2.lum.clu.service.CluService#searchForClus(org.kuali.rice.core.api.criteria.QueryByCriteria, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public List<CluInfo> searchForClus(
             QueryByCriteria criteria,
             ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("not implemented");
    }

    /* (non-Javadoc)
     * @see org.kuali.student.r2.lum.clu.service.CluService#searchForCluIds(org.kuali.rice.core.api.criteria.QueryByCriteria, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public List<String> searchForCluIds(
             QueryByCriteria criteria,
             ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("not implemented");
    }

    /* (non-Javadoc)
     * @see org.kuali.student.r2.lum.clu.service.CluService#searchForCluCluRelations(org.kuali.rice.core.api.criteria.QueryByCriteria, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public List<CluCluRelationInfo> searchForCluCluRelations(
             QueryByCriteria criteria,
             ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("not implemented");
    }

    /* (non-Javadoc)
     * @see org.kuali.student.r2.lum.clu.service.CluService#searchForCluCluRelationIds(org.kuali.rice.core.api.criteria.QueryByCriteria, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public List<String> searchForCluCluRelationIds(
             QueryByCriteria criteria,
             ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("not implemented");
    }

    /* (non-Javadoc)
     * @see org.kuali.student.r2.lum.clu.service.CluService#searchForCluLoRelations(org.kuali.rice.core.api.criteria.QueryByCriteria, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public List<CluLoRelationInfo> searchForCluLoRelations(
             QueryByCriteria criteria,
             ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("not implemented");
    }

    /* (non-Javadoc)
     * @see org.kuali.student.r2.lum.clu.service.CluService#searchForCluLoRelationIds(org.kuali.rice.core.api.criteria.QueryByCriteria, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public List<String> searchForCluLoRelationIds(
             QueryByCriteria criteria,
             ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("not implemented");
    }

    /* (non-Javadoc)
     * @see org.kuali.student.r2.lum.clu.service.CluService#searchForCluPublications(org.kuali.rice.core.api.criteria.QueryByCriteria, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public List<CluPublicationInfo> searchForCluPublications(
             QueryByCriteria criteria,
             ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("not implemented");
    }

    /* (non-Javadoc)
     * @see org.kuali.student.r2.lum.clu.service.CluService#searchForCluPublicationIds(org.kuali.rice.core.api.criteria.QueryByCriteria, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public List<String> searchForCluPublicationIds(
             QueryByCriteria criteria,
             ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("not implemented");
    }

    /* (non-Javadoc)
     * @see org.kuali.student.r2.lum.clu.service.CluService#searchForCluResults(org.kuali.rice.core.api.criteria.QueryByCriteria, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public List<CluResultInfo> searchForCluResults(
             QueryByCriteria criteria,
             ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("not implemented");
    }

    /* (non-Javadoc)
     * @see org.kuali.student.r2.lum.clu.service.CluService#searchForCluResultIds(org.kuali.rice.core.api.criteria.QueryByCriteria, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public List<String> searchForCluResultIds(
             QueryByCriteria criteria,
             ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("not implemented");
    }
    
    
}
