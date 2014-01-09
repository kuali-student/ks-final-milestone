/*
 * Copyright 2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Lic+ense is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.r2.lum.lu.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.CircularRelationshipException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.IllegalVersionSequencingException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.UnsupportedActionException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.service.SearchService;
import org.kuali.student.r2.core.versionmanagement.dto.VersionDisplayInfo;
import org.kuali.student.r2.core.versionmanagement.dto.VersionInfo;
import org.kuali.student.r2.lum.clu.dto.CluCluRelationInfo;
import org.kuali.student.r2.lum.clu.dto.CluInfo;
import org.kuali.student.r2.lum.clu.dto.CluLoRelationInfo;
import org.kuali.student.r2.lum.clu.dto.CluPublicationInfo;
import org.kuali.student.r2.lum.clu.dto.CluResultInfo;
import org.kuali.student.r2.lum.clu.dto.CluSetInfo;
import org.kuali.student.r2.lum.clu.dto.CluSetTreeViewInfo;
import org.kuali.student.r2.lum.clu.dto.ResultOptionInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;

public class CluServiceMockImpl implements CluService {
    // cache variable 
    // The LinkedHashMap is just so the values come back in a predictable order

    private SearchService searchService;

    private Map<String, TypeInfo> typeMap = new LinkedHashMap<String, TypeInfo>();
    private Map<String, CluInfo> cluMap = new LinkedHashMap<String, CluInfo>();
    private Map<String, CluCluRelationInfo> cluCluRelationMap = new LinkedHashMap<String, CluCluRelationInfo>();
    private Map<String, CluPublicationInfo> cluPublicationMap = new LinkedHashMap<String, CluPublicationInfo>();
    private Map<String, CluResultInfo> cluResultMap = new LinkedHashMap<String, CluResultInfo>();
    private Map<String, CluLoRelationInfo> cluLoRelationMap = new LinkedHashMap<String, CluLoRelationInfo>();
    private Map<String, CluSetInfo> cluSetMap = new LinkedHashMap<String, CluSetInfo>();
    private Map<String, CluSetTreeViewInfo> cluSetTreeViewMap = new LinkedHashMap<String, CluSetTreeViewInfo>();
    private Map<String, VersionInfo> cluVersionMap = new LinkedHashMap<String, VersionInfo>();

    public void clear() {
        this.typeMap.clear();
        this.cluMap.clear();
        this.cluCluRelationMap.clear();
        this.cluPublicationMap.clear();
        this.cluResultMap.clear();
        this.cluLoRelationMap.clear();
        this.cluSetMap.clear();
        this.cluSetTreeViewMap.clear();
        this.cluVersionMap.clear();
    }

    @Override
    public List<TypeInfo> getDeliveryMethodTypes(ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // GET_TYPES
        return new ArrayList<TypeInfo>(typeMap.values());
    }

    @Override
    public TypeInfo getDeliveryMethodType(String deliveryMethodTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // GET_TYPE
        if (!this.typeMap.containsKey(deliveryMethodTypeKey)) {
            throw new OperationFailedException(deliveryMethodTypeKey);
        }
        return this.typeMap.get(deliveryMethodTypeKey);
    }

    @Override
    public List<TypeInfo> getInstructionalFormatTypes(ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // GET_TYPES
        return new ArrayList<TypeInfo>(typeMap.values());
    }

    @Override
    public TypeInfo getInstructionalFormatType(String instructionalFormatTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // GET_TYPE
        if (!this.typeMap.containsKey(instructionalFormatTypeKey)) {
            throw new OperationFailedException(instructionalFormatTypeKey);
        }
        return this.typeMap.get(instructionalFormatTypeKey);
    }

    @Override
    public List<TypeInfo> getLuTypes(ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // GET_TYPES
        return new ArrayList<TypeInfo>(typeMap.values());
    }

    @Override
    public TypeInfo getLuType(String luTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // GET_TYPE
        if (!this.typeMap.containsKey(luTypeKey)) {
            throw new OperationFailedException(luTypeKey);
        }
        return this.typeMap.get(luTypeKey);
    }

    @Override
    public List<TypeInfo> getLuCodeTypes(ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // GET_TYPES
        return new ArrayList<TypeInfo>(typeMap.values());
    }

    @Override
    public TypeInfo getLuCodeType(String luCodeTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // GET_TYPE
        if (!this.typeMap.containsKey(luCodeTypeKey)) {
            throw new OperationFailedException(luCodeTypeKey);
        }
        return this.typeMap.get(luCodeTypeKey);
    }

    @Override
    public List<TypeInfo> getCluCluRelationTypes(ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // GET_TYPES
        return new ArrayList<TypeInfo>(typeMap.values());
    }

    @Override
    public TypeInfo getLuLuRelationType(String cluCluRelationTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // GET_TYPE
        if (!this.typeMap.containsKey(cluCluRelationTypeKey)) {
            throw new OperationFailedException(cluCluRelationTypeKey);
        }
        return this.typeMap.get(cluCluRelationTypeKey);
    }

    @Override
    public List<String> getAllowedLuLuRelationTypesForLuType(String luTypeKey, String relatedLuTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("getAllowedLuLuRelationTypesForLuType has not been implemented");
    }

    @Override
    public List<TypeInfo> getLuPublicationTypes(ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // GET_TYPES
        return new ArrayList<TypeInfo>(typeMap.values());
    }

    @Override
    public TypeInfo getLuPublicationType(String luPublicationTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // GET_TYPE
        if (!this.typeMap.containsKey(luPublicationTypeKey)) {
            throw new OperationFailedException(luPublicationTypeKey);
        }
        return this.typeMap.get(luPublicationTypeKey);
    }

    @Override
    public List<String> getLuPublicationTypesForLuType(String luTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("getLuPublicationTypesForLuType has not been implemented");
    }

    @Override
    public List<TypeInfo> getCluResultTypes(ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // GET_TYPES
        return new ArrayList<TypeInfo>(typeMap.values());
    }

    @Override
    public TypeInfo getCluResultType(String cluResultTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // GET_TYPE
        if (!this.typeMap.containsKey(cluResultTypeKey)) {
            throw new OperationFailedException(cluResultTypeKey);
        }
        return this.typeMap.get(cluResultTypeKey);
    }

    @Override
    public List<TypeInfo> getCluResultTypesForLuType(String luTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // GET_TYPES
        return new ArrayList<TypeInfo>(typeMap.values());
    }

    @Override
    public List<TypeInfo> getResultUsageTypes(ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // GET_TYPES
        return new ArrayList<TypeInfo>(typeMap.values());
    }

    @Override
    public TypeInfo getResultUsageType(String resultUsageTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // GET_TYPE
        if (!this.typeMap.containsKey(resultUsageTypeKey)) {
            throw new OperationFailedException(resultUsageTypeKey);
        }
        return this.typeMap.get(resultUsageTypeKey);
    }

    @Override
    public List<String> getAllowedResultUsageTypesForLuType(String luTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("getAllowedResultUsageTypesForLuType has not been implemented");
    }

    @Override
    public List<String> getAllowedResultComponentTypesForResultUsageType(String resultUsageTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("getAllowedResultComponentTypesForResultUsageType has not been implemented");
    }

    @Override
    public List<TypeInfo> getCluLoRelationTypes(ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // GET_TYPES
        return new ArrayList<TypeInfo>(typeMap.values());
    }

    @Override
    public TypeInfo getCluLoRelationType(String cluLoRelationTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // GET_TYPE
        if (!this.typeMap.containsKey(cluLoRelationTypeKey)) {
            throw new OperationFailedException(cluLoRelationTypeKey);
        }
        return this.typeMap.get(cluLoRelationTypeKey);
    }

    @Override
    public List<String> getAllowedCluLoRelationTypesForLuType(String luTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("getAllowedCluLoRelationTypesForLuType has not been implemented");
    }

    @Override
    public List<TypeInfo> getCluSetTypes(ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // GET_TYPES
        return new ArrayList<TypeInfo>(typeMap.values());
    }

    @Override
    public TypeInfo getCluSetType(String cluSetTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // GET_TYPE
        if (!this.typeMap.containsKey(cluSetTypeKey)) {
            throw new OperationFailedException(cluSetTypeKey);
        }
        return this.typeMap.get(cluSetTypeKey);
    }

    @Override
    public CluInfo getClu(String cluId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // GET_BY_ID
        if (!this.cluMap.containsKey(cluId)) {
            throw new DoesNotExistException(cluId);
        }
        return new CluInfo(this.cluMap.get(cluId));
    }

    @Override
    public List<CluInfo> getClusByIds(List<String> cluIds, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // GET_BY_IDS
        List<CluInfo> list = new ArrayList<CluInfo>();
        for (String id : cluIds) {
            list.add(this.getClu(id, contextInfo));
        }
        return list;
    }

    @Override
    public List<CluInfo> getClusByLuType(String luTypeKey, String luState, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // GET_INFOS_BY_OTHER
        List<CluInfo> list = new ArrayList<CluInfo>();
        for (CluInfo info : cluMap.values()) {
            if (luTypeKey.equals(info.getTypeKey())) {
                if (luState.equals(info.getStateKey())) {
                    list.add(new CluInfo(info));
                }
            }
        }
        return list;
    }

    @Override
    public List<String> getCluIdsByLuType(String luTypeKey, String luState, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // GET_IDS_BY_OTHER
        List<String> list = new ArrayList<String>();
        for (CluInfo info : cluMap.values()) {
            if (luTypeKey.equals(info.getTypeKey())) {
                if (luState.equals(info.getStateKey())) {
                    list.add(info.getId());
                }
            }
        }
        return list;
    }

    @Override
    public List<String> getAllowedCluCluRelationTypesByClu(String cluId, String relatedCluId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> list = new ArrayList<String>();
        return list;
    }

    @Override
    public List<CluInfo> getClusByRelatedCluAndRelationType(String relatedCluId, String cluCLuRelationTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // GET_INFOS_BY_OTHER
        List<CluInfo> list = new ArrayList<CluInfo>();
        for (String cluId : this.getCluIdsByRelatedCluAndRelationType(relatedCluId, cluCLuRelationTypeKey, contextInfo)) {
            list.add(this.getClu(cluId, contextInfo));
        }
        return list;
    }

    @Override
    public List<String> getCluIdsByRelatedCluAndRelationType(String relatedCluId, String cluCluRelationTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // GET_IDS_BY_OTHER
        List<String> list = new ArrayList<String>();
        for (CluCluRelationInfo info : cluCluRelationMap.values()) {
            if (relatedCluId.equals(info.getRelatedCluId())) {
                if (cluCluRelationTypeKey.equals(info.getTypeKey())) {
                    list.add(info.getCluId());
                }
            }
        }
        return list;
    }

    @Override
    public List<CluInfo> getRelatedClusByCluAndRelationType(String cluId, String cluCluRelationTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // GET_INFOS_BY_OTHER
        List<CluInfo> list = new ArrayList<CluInfo>();
        for (String relatedCluId : this.getRelatedCluIdsByCluAndRelationType(cluId, cluCluRelationTypeKey, contextInfo)) {
            list.add(this.getClu(relatedCluId, contextInfo));
        }
        return list;
    }

    @Override
    public List<String> getRelatedCluIdsByCluAndRelationType(String cluId, String cluCluRelationTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // GET_IDS_BY_OTHER
        List<String> list = new ArrayList<String>();
        for (CluCluRelationInfo info : cluCluRelationMap.values()) {
            if (cluId.equals(info.getCluId())) {
                if (cluCluRelationTypeKey.equals(info.getTypeKey())) {
                    list.add(info.getRelatedCluId());
                }
            }
        }
        return list;
    }

    @Override
    public CluCluRelationInfo getCluCluRelation(String cluCluRelationId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // GET_BY_ID
        if (!this.cluCluRelationMap.containsKey(cluCluRelationId)) {
            throw new OperationFailedException(cluCluRelationId);
        }
        return new CluCluRelationInfo(this.cluCluRelationMap.get(cluCluRelationId));
    }

    @Override
    public List<CluCluRelationInfo> getCluCluRelationsByClu(String cluId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // GET_INFOS_BY_OTHER
        List<CluCluRelationInfo> list = new ArrayList<CluCluRelationInfo>();
        for (CluCluRelationInfo info : cluCluRelationMap.values()) {
            if (cluId.equals(info.getCluId())) {
                list.add(new CluCluRelationInfo(info));
            }
        }
        return list;
    }

    @Override
    public List<CluPublicationInfo> getCluPublicationsByClu(String cluId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // GET_INFOS_BY_OTHER
        List<CluPublicationInfo> list = new ArrayList<CluPublicationInfo>();
        for (CluPublicationInfo info : cluPublicationMap.values()) {
            if (cluId.equals(info.getCluId())) {
                list.add(new CluPublicationInfo(info));
            }
        }
        return list;
    }

    @Override
    public List<CluPublicationInfo> getCluPublicationsByType(String luPublicationTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // GET_IDS_BY_TYPE
        List<CluPublicationInfo> list = new ArrayList<CluPublicationInfo>();
        for (CluPublicationInfo info : cluPublicationMap.values()) {
            if (luPublicationTypeKey.equals(info.getTypeKey())) {
                list.add(new CluPublicationInfo(info));
            }
        }
        return list;
    }

    @Override
    public CluPublicationInfo getCluPublication(String cluPublicationId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // GET_BY_ID
        if (!this.cluPublicationMap.containsKey(cluPublicationId)) {
            throw new OperationFailedException(cluPublicationId);
        }
        return new CluPublicationInfo(this.cluPublicationMap.get(cluPublicationId));
    }

    @Override
    public CluResultInfo getCluResult(String cluResultId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // GET_BY_ID
        if (!this.cluResultMap.containsKey(cluResultId)) {
            throw new OperationFailedException(cluResultId);
        }
        return new CluResultInfo(this.cluResultMap.get(cluResultId));
    }

    @Override
    public List<CluResultInfo> getCluResultByClu(String cluId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // GET_INFOS_BY_OTHER
        List<CluResultInfo> list = new ArrayList<CluResultInfo>();
        for (CluResultInfo info : cluResultMap.values()) {
            if (cluId.equals(info.getCluId())) {
                list.add(new CluResultInfo(info));
            }
        }
        return list;
    }

    @Override
    public List<CluResultInfo> getCluResultsByClus(List<String> cluIds, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // GET_INFOS_BY_OTHER
        List<CluResultInfo> list = new ArrayList<CluResultInfo>();
        for (CluResultInfo info : cluResultMap.values()) {
            if (cluIds.contains(info.getCluId())) {
                list.add(new CluResultInfo(info));
            }
        }
        return list;
    }

    @Override
    public List<String> getCluIdsByResultUsageType(String resultUsageTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // GET_IDS_BY_OTHER
        Set<String> set = new LinkedHashSet<String>();
        for (CluResultInfo info : cluResultMap.values()) {
            for (ResultOptionInfo optInfo : info.getResultOptions()) {
                if (resultUsageTypeKey.equals(optInfo.getResultUsageTypeKey())) {
                    set.add(info.getCluId());
                }
            }
        }
        return new ArrayList(set);
    }

    @Override
    public List<String> getCluIdsByResultComponent(String resultComponentId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // GET_IDS_BY_OTHER
        Set<String> set = new LinkedHashSet<String>();
        for (CluResultInfo info : cluResultMap.values()) {
            for (ResultOptionInfo optInfo : info.getResultOptions()) {
                if (resultComponentId.equals(optInfo.getResultComponentId())) {
                    set.add(info.getCluId());
                }
            }
        }
        return new ArrayList(set);
    }

    @Override
    public CluLoRelationInfo getCluLoRelation(String cluLoRelationId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // GET_BY_ID
        if (!this.cluLoRelationMap.containsKey(cluLoRelationId)) {
            throw new OperationFailedException(cluLoRelationId);
        }
        return new CluLoRelationInfo(this.cluLoRelationMap.get(cluLoRelationId));
    }

    @Override
    public List<CluLoRelationInfo> getCluLoRelationsByClu(String cluId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // GET_INFOS_BY_OTHER
        List<CluLoRelationInfo> list = new ArrayList<CluLoRelationInfo>();
        for (CluLoRelationInfo info : cluLoRelationMap.values()) {
            if (cluId.equals(info.getCluId())) {
                list.add(new CluLoRelationInfo(info));
            }
        }
        return list;
    }

    @Override
    public List<CluLoRelationInfo> getCluLoRelationsByLo(String loId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // GET_INFOS_BY_OTHER
        List<CluLoRelationInfo> list = new ArrayList<CluLoRelationInfo>();
        for (CluLoRelationInfo info : cluLoRelationMap.values()) {
            if (loId.equals(info.getLoId())) {
                list.add(new CluLoRelationInfo(info));
            }
        }
        return list;
    }

    @Override
    public List<String> getResourceRequirementsForClu(String cluId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("getResourceRequirementsForClu has not been implemented");
    }

    @Override
    public CluSetInfo getCluSet(String cluSetId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // GET_BY_ID
        if (!this.cluSetMap.containsKey(cluSetId)) {
            throw new DoesNotExistException(cluSetId);
        }
        return new CluSetInfo(this.cluSetMap.get(cluSetId));
    }

    @Override
    public CluSetTreeViewInfo getCluSetTreeView(String cluSetId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // GET_BY_ID
        if (!this.cluSetTreeViewMap.containsKey(cluSetId)) {
            throw new OperationFailedException(cluSetId);
        }
        return new CluSetTreeViewInfo(this.cluSetTreeViewMap.get(cluSetId));
    }

    @Override
    public List<CluSetInfo> getCluSetsByIds(List<String> cluSetIds, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // GET_BY_IDS
        List<CluSetInfo> list = new ArrayList<CluSetInfo>();
        for (String id : cluSetIds) {
            list.add(this.getCluSet(id, contextInfo));
        }
        return list;
    }

    @Override
    public List<String> getCluSetIdsFromCluSet(String cluSetId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("getCluSetIdsFromCluSet has not been implemented");
    }

    @Override
    public Boolean isCluSetDynamic(String cluSetId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("isCluSetDynamic has not been implemented");
    }

    @Override
    public List<CluInfo> getClusFromCluSet(String cluSetId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("getClusFromCluSet has not been implemented");
    }

    @Override
    public List<String> getCluIdsFromCluSet(String cluSetId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("getCluIdsFromCluSet has not been implemented");
    }

    @Override
    public List<CluInfo> getAllClusInCluSet(String cluSetId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("getAllClusInCluSet has not been implemented");
    }

    @Override
    public List<String> getAllCluIdsInCluSet(String cluSetId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        CluSetInfo cluSetInfo = cluSetMap.get(cluSetId);
        return cluSetInfo.getCluIds();
    }

    @Override
    public Boolean isCluInCluSet(String cluId, String cluSetId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("isCluInCluSet has not been implemented");
    }

    @Override
    public List<ValidationResultInfo> validateClu(String validationTypeKey, CluInfo cluInfo, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // VALIDATE
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public CluInfo createClu(String luTypeKey, CluInfo cluInfo, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        // CREATE
        if (!luTypeKey.equals(cluInfo.getTypeKey())) {
            throw new InvalidParameterException("The type parameter does not match the type on the info object");
        }
        CluInfo copy = new CluInfo(cluInfo);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }    
        copy.setMeta(newMeta(contextInfo));
        VersionInfo versionInfo = new VersionInfo ();
        versionInfo.setVersionIndId(UUIDHelper.genStringUUID());
        versionInfo.setVersionComment("Initial Create");
        versionInfo.setCurrentVersionStart(contextInfo.getCurrentDate());
        if (versionInfo.getCurrentVersionStart() == null) {
            versionInfo.setCurrentVersionStart(new Date ());
        }
        versionInfo.setSequenceNumber(0l);
        versionInfo.setVersionedFromId(copy.getId());
        copy.setVersion(versionInfo);
        cluMap.put(copy.getId(), copy);
        return new CluInfo(copy);
    }

    @Override
    public CluInfo updateClu(String cluId, CluInfo cluInfo, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        // UPDATE
        if (!cluId.equals(cluInfo.getId())) {
            throw new InvalidParameterException("The id parameter does not match the id on the info object");
        }
        CluInfo copy = new CluInfo(cluInfo);
        CluInfo old = this.getClu(cluInfo.getId(), contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        this.cluMap.put(cluInfo.getId(), copy);
        return new CluInfo(copy);
    }

    @Override
    public StatusInfo deleteClu(String cluId, ContextInfo contextInfo)
            throws DependentObjectsExistException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // DELETE
        if (this.cluMap.remove(cluId) == null) {
            throw new OperationFailedException(cluId);
        }
        return newStatus();
    }

    @Override
    public CluInfo createNewCluVersion(String cluId, String versionComment, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        // CREATE
        throw new OperationFailedException("createNewCluVersion has not been implemented");
    }

    @Override
    public StatusInfo setCurrentCluVersion(String cluVersionId, Date currentVersionStart, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, IllegalVersionSequencingException, OperationFailedException, PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("setCurrentCluVersion has not been implemented");
    }

    @Override
    public CluInfo updateCluState(String cluId, String luState, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        // UPDATE
        throw new OperationFailedException("updateCluState has not been implemented");
    }

    @Override
    public List<ValidationResultInfo> validateCluCluRelation(String validationTypeKey, String cluId, String relatedCluId, String cluCluRelationTypeKey, CluCluRelationInfo cluCluRelationInfo, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // VALIDATE
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public CluCluRelationInfo createCluCluRelation(String cluId, String relatedCluId, String cluCluRelationTypeKey, CluCluRelationInfo cluCluRelationInfo, ContextInfo contextInfo)
            throws CircularRelationshipException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        // CREATE
        if (!cluCluRelationTypeKey.equals(cluCluRelationInfo.getTypeKey())) {
            throw new InvalidParameterException("The type parameter does not match the type on the info object");
        }
        // TODO: check the rest of the readonly fields that are specified on the create to make sure they match the info object
        CluCluRelationInfo copy = new CluCluRelationInfo(cluCluRelationInfo);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        copy.setMeta(newMeta(contextInfo));
        cluCluRelationMap.put(copy.getId(), copy);
        return new CluCluRelationInfo(copy);
    }

    @Override
    public CluCluRelationInfo updateCluCluRelation(String cluCluRelationId, CluCluRelationInfo cluCluRelationInfo, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        // UPDATE
        if (!cluCluRelationId.equals(cluCluRelationInfo.getId())) {
            throw new InvalidParameterException("The id parameter does not match the id on the info object");
        }
        CluCluRelationInfo copy = new CluCluRelationInfo(cluCluRelationInfo);
        CluCluRelationInfo old = this.getCluCluRelation(cluCluRelationInfo.getId(), contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        this.cluCluRelationMap.put(cluCluRelationInfo.getId(), copy);
        return new CluCluRelationInfo(copy);
    }

    @Override
    public StatusInfo deleteCluCluRelation(String cluCluRelationId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // DELETE
        if (this.cluCluRelationMap.remove(cluCluRelationId) == null) {
            throw new OperationFailedException(cluCluRelationId);
        }
        return newStatus();
    }

    @Override
    public List<ValidationResultInfo> validateCluPublication(String validationTypeKey, String cluId, String luPublicationTypeKey, CluPublicationInfo cluPublicationInfo, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // VALIDATE
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public CluPublicationInfo createCluPublication(String cluId, String luPublicationTypeKey, CluPublicationInfo cluPublicationInfo, ContextInfo contextInfo)
            throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        // CREATE
        if (!luPublicationTypeKey.equals(cluPublicationInfo.getTypeKey())) {
            throw new InvalidParameterException("The type parameter does not match the type on the info object");
        }
        // TODO: check the rest of the readonly fields that are specified on the create to make sure they match the info object
        CluPublicationInfo copy = new CluPublicationInfo(cluPublicationInfo);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        copy.setMeta(newMeta(contextInfo));
        cluPublicationMap.put(copy.getId(), copy);
        return new CluPublicationInfo(copy);
    }

    @Override
    public CluPublicationInfo updateCluPublication(String cluPublicationId, CluPublicationInfo cluPublicationInfo, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        // UPDATE
        if (!cluPublicationId.equals(cluPublicationInfo.getId())) {
            throw new InvalidParameterException("The id parameter does not match the id on the info object");
        }
        CluPublicationInfo copy = new CluPublicationInfo(cluPublicationInfo);
        CluPublicationInfo old = this.getCluPublication(cluPublicationInfo.getId(), contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        this.cluPublicationMap.put(cluPublicationInfo.getId(), copy);
        return new CluPublicationInfo(copy);
    }

    @Override
    public StatusInfo deleteCluPublication(String cluPublicationId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, DependentObjectsExistException, OperationFailedException, PermissionDeniedException {
        // DELETE
        if (this.cluPublicationMap.remove(cluPublicationId) == null) {
            throw new OperationFailedException(cluPublicationId);
        }
        return newStatus();
    }

    @Override
    public List<ValidationResultInfo> validateCluResult(String validationTypeKey, String cluId, String cluResultTypeKey, CluResultInfo cluResultInfo, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // VALIDATE
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public CluResultInfo createCluResult(String cluId, String cluResultTypeKey, CluResultInfo cluResultInfo, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        // CREATE
        if (!cluResultTypeKey.equals(cluResultInfo.getTypeKey())) {
            throw new InvalidParameterException("The type parameter does not match the type on the info object");
        }
        // TODO: check the rest of the readonly fields that are specified on the create to make sure they match the info object
        CluResultInfo copy = new CluResultInfo(cluResultInfo);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        copy.setMeta(newMeta(contextInfo));
        cluResultMap.put(copy.getId(), copy);
        return new CluResultInfo(copy);
    }

    @Override
    public CluResultInfo updateCluResult(String cluResultId, CluResultInfo cluResultInfo, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        // UPDATE
        if (!cluResultId.equals(cluResultInfo.getId())) {
            throw new InvalidParameterException("The id parameter does not match the id on the info object");
        }
        CluResultInfo copy = new CluResultInfo(cluResultInfo);
        CluResultInfo old = this.getCluResult(cluResultInfo.getId(), contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        this.cluResultMap.put(cluResultInfo.getId(), copy);
        return new CluResultInfo(copy);
    }

    @Override
    public StatusInfo deleteCluResult(String cluResultId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, DependentObjectsExistException, OperationFailedException, PermissionDeniedException {
        // DELETE
        if (this.cluResultMap.remove(cluResultId) == null) {
            throw new OperationFailedException(cluResultId);
        }
        return newStatus();
    }

    @Override
    public List<ValidationResultInfo> validateCluLoRelation(String validationTypeKey, String cluId, String loId, String cluLoRelationTypeKey, CluLoRelationInfo cluLoRelationInfo, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // VALIDATE
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public CluLoRelationInfo createCluLoRelation(String cluId, String loId, String cluLoRelationTypeKey, CluLoRelationInfo cluLoRelationInfo, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        // CREATE
        if (!cluLoRelationTypeKey.equals(cluLoRelationInfo.getTypeKey())) {
            throw new InvalidParameterException("The type parameter does not match the type on the info object");
        }
        // TODO: check the rest of the readonly fields that are specified on the create to make sure they match the info object
        CluLoRelationInfo copy = new CluLoRelationInfo(cluLoRelationInfo);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        copy.setMeta(newMeta(contextInfo));
        cluLoRelationMap.put(copy.getId(), copy);
        return new CluLoRelationInfo(copy);
    }

    @Override
    public CluLoRelationInfo updateCluLoRelation(String cluLoRelationId, CluLoRelationInfo cluLoRelationInfo, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        // UPDATE
        if (!cluLoRelationId.equals(cluLoRelationInfo.getId())) {
            throw new InvalidParameterException("The id parameter does not match the id on the info object");
        }
        CluLoRelationInfo copy = new CluLoRelationInfo(cluLoRelationInfo);
        CluLoRelationInfo old = this.getCluLoRelation(cluLoRelationInfo.getId(), contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        this.cluLoRelationMap.put(cluLoRelationInfo.getId(), copy);
        return new CluLoRelationInfo(copy);
    }

    @Override
    public StatusInfo deleteCluLoRelation(String cluLoRelationId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // DELETE
        if (this.cluLoRelationMap.remove(cluLoRelationId) == null) {
            throw new OperationFailedException(cluLoRelationId);
        }
        return newStatus();
    }

    @Override
    public StatusInfo addCluResourceRequirement(String resourceTypeKey, String cluId, ContextInfo contextInfo)
            throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("addCluResourceRequirement has not been implemented");
    }

    @Override
    public StatusInfo removeCluResourceRequirement(String resourceTypeKey, String cluId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("addCluResourceRequirement has not been implemented");
    }

    @Override
    public List<ValidationResultInfo> validateCluSet(String validationTypeKey, String cluSetTypeKey, CluSetInfo cluSetInfo, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // VALIDATE
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public CluSetInfo createCluSet(String cluSetTypeKey, CluSetInfo cluSetInfo, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, UnsupportedActionException {
        // CREATE
        if (!cluSetTypeKey.equals(cluSetInfo.getTypeKey())) {
            throw new InvalidParameterException("The type parameter does not match the type on the info object");
        }
        CluSetInfo copy = new CluSetInfo(cluSetInfo);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        copy.setMeta(newMeta(contextInfo));
        cluSetMap.put(copy.getId(), copy);
        return new CluSetInfo(copy);
    }

    @Override
    public CluSetInfo updateCluSet(String cluSetId, CluSetInfo cluSetInfo, ContextInfo contextInfo)
            throws CircularRelationshipException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, UnsupportedActionException, VersionMismatchException {
        // UPDATE
        if (!cluSetId.equals(cluSetInfo.getId())) {
            throw new InvalidParameterException("The id parameter does not match the id on the info object");
        }
        CluSetInfo copy = new CluSetInfo(cluSetInfo);
        CluSetInfo old = this.getCluSet(cluSetInfo.getId(), contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        this.cluSetMap.put(cluSetInfo.getId(), copy);
        return new CluSetInfo(copy);
    }

    @Override
    public StatusInfo deleteCluSet(String cluSetId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // DELETE
        if (this.cluSetMap.remove(cluSetId) == null) {
            throw new OperationFailedException(cluSetId);
        }
        return newStatus();
    }

    @Override
    public StatusInfo addCluSetToCluSet(String cluSetId, String addedCluSetId, ContextInfo contextInfo)
            throws CircularRelationshipException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, UnsupportedActionException {
        throw new OperationFailedException("addCluSetToCluSet has not been implemented");
    }

    @Override
    public StatusInfo addCluSetsToCluSet(String cluSetId, List<String> addedCluSetIds, ContextInfo contextInfo)
            throws CircularRelationshipException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, UnsupportedActionException {
                throw new OperationFailedException("addCluSetsToCluSet has not been implemented");
    }

    @Override
    public StatusInfo removeCluSetFromCluSet(String cluSetId, String removedCluSetId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, UnsupportedActionException {
        throw new OperationFailedException("removeCluSetFromCluSet has not been implemented");
    }

    @Override
    public StatusInfo addCluToCluSet(String cluId, String cluSetId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, UnsupportedActionException {
        // ADD
        throw new OperationFailedException("addCluToCluSet has not been implemented");
    }

    @Override
    public StatusInfo addClusToCluSet(List<String> cluSetIds, String cluSetId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, UnsupportedActionException {
      throw new OperationFailedException("addClusToCluSet has not been implemented");
    }

    @Override
    public StatusInfo removeCluFromCluSet(String cluId, String cluSetId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, UnsupportedActionException {
        throw new OperationFailedException("removeCluFromCluSet has not been implemented");
    }

    @Override
    public List<CluInfo> searchForClus(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("searchForClus has not been implemented");
    }

    @Override
    public List<String> searchForCluIds(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("searchForCluIds has not been implemented");
    }

    @Override
    public List<CluCluRelationInfo> searchForCluCluRelations(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("searchForCluCluRelations has not been implemented");
    }

    @Override
    public List<String> searchForCluCluRelationIds(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("searchForCluCluRelationIds has not been implemented");
    }

    @Override
    public List<CluLoRelationInfo> searchForCluLoRelations(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("searchForCluLoRelations has not been implemented");
    }

    @Override
    public List<String> searchForCluLoRelationIds(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("searchForCluLoRelationIds has not been implemented");
    }

    @Override
    public List<CluPublicationInfo> searchForCluPublications(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("searchForCluPublications has not been implemented");
    }

    @Override
    public List<String> searchForCluPublicationIds(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("searchForCluPublicationIds has not been implemented");
    }

    @Override
    public List<CluResultInfo> searchForCluResults(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("searchForCluResults has not been implemented");
    }

    @Override
    public List<String> searchForCluResultIds(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("searchForCluResultIds has not been implemented");
    }

    @Override
    public List<VersionDisplayInfo> getVersions(String refObjectTypeURI, String refObjectId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<VersionDisplayInfo> versionInfos = new ArrayList<VersionDisplayInfo>();
        if (CluServiceConstants.CLU_NAMESPACE_URI.equals(refObjectTypeURI)) {
            for (CluInfo cluInfo : cluMap.values()) {
                VersionInfo version = cluInfo.getVersion();
                if (version.getVersionIndId().equals(refObjectId)) {
                    versionInfos.add(new VersionDisplayInfo(cluInfo.getId(), version.getVersionIndId(), version.getSequenceNumber(), version.getCurrentVersionStart(), version.getCurrentVersionEnd(), version.getVersionComment(), version.getVersionedFromId()));
                }
            }
        } else {
            throw new UnsupportedOperationException("This method does not know how to handle object type:" + refObjectTypeURI);
        }
        return versionInfos;
    }

    @Override
    public VersionDisplayInfo getFirstVersion(String refObjectUri, String refObjectId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public VersionDisplayInfo getLatestVersion(String refObjectUri, String refObjectId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public VersionDisplayInfo getCurrentVersion(String refObjectUri, String refObjectId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public VersionDisplayInfo getVersionBySequenceNumber(String refObjectUri, String refObjectId, Long sequence, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public VersionDisplayInfo getCurrentVersionOnDate(String refObjectUri, String refObjectId, Date date, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<VersionDisplayInfo> getVersionsInDateRange(String refObjectUri, String refObjectId, Date from, Date to, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TypeInfo> getSearchTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TypeInfo getSearchType(String searchTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SearchResultInfo search(SearchRequestInfo searchRequestInfo, ContextInfo contextInfo) throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException {
        return getSearchService().search(searchRequestInfo, contextInfo);
    }
    
    private StatusInfo newStatus() {
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    private MetaInfo newMeta(ContextInfo context) {
        MetaInfo meta = new MetaInfo();
        meta.setCreateId(context.getPrincipalId());
        meta.setCreateTime(new Date());
        meta.setUpdateId(context.getPrincipalId());
        meta.setUpdateTime(meta.getCreateTime());
        meta.setVersionInd("0");
        return meta;
    }

    private MetaInfo updateMeta(MetaInfo old, ContextInfo context) {
        MetaInfo meta = new MetaInfo(old);
        meta.setUpdateId(context.getPrincipalId());
        meta.setUpdateTime(new Date());
        meta.setVersionInd((Integer.parseInt(meta.getVersionInd()) + 1) + "");
        return meta;
    }

    public void setSearchService(SearchService searchService) {
        this.searchService = searchService;
    }

    public SearchService getSearchService() {
        return this.searchService;
    }

	@Override
	public List<CluCluRelationInfo> getCluCluRelationsByIds(
			List<String> cluCluRelationIds, ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public List<CluPublicationInfo> getCluPublicationsByIds(
			List<String> cluPublicationIds, ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public List<CluResultInfo> getCluResultsByIds(List<String> cluResultIds,
			ContextInfo contextInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public List<CluLoRelationInfo> getCluLoRelationsByIds(
			List<String> cluLoRelationIds, ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		throw new UnsupportedOperationException("not implemented");
	}
    
    
}
