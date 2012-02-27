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

package org.kuali.student.lum.ui.requirements.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebParam;

import org.kuali.student.common.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.CircularReferenceException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r1.common.search.dto.SearchCriteriaTypeInfo;
import org.kuali.student.r1.common.search.dto.SearchRequest;
import org.kuali.student.r1.common.search.dto.SearchResult;
import org.kuali.student.r1.common.search.dto.SearchResultTypeInfo;
import org.kuali.student.r1.common.search.dto.SearchTypeInfo;
import org.kuali.student.common.validation.dto.ValidationResultInfo;
import org.kuali.student.core.statement.dto.NlUsageTypeInfo;
import org.kuali.student.core.statement.dto.RefStatementRelationInfo;
import org.kuali.student.core.statement.dto.RefStatementRelationTypeInfo;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.statement.dto.ReqComponentTypeInfo;
import org.kuali.student.core.statement.dto.StatementInfo;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.core.statement.dto.StatementTypeInfo;
import org.kuali.student.core.statement.service.StatementService;

public class StatementServiceMock implements StatementService {

    private Map<String, Object> map = new HashMap<String, Object>();

    public void addObject(String key, Object value) {
        this.map.put(key, value);
    }

    public void addAll(Map<String, Object> map) {
        this.map.putAll(map);
    }

    public void clear() {
        this.map.clear();
    }

    @Override
    public List<TypeInfo> getRefObjectTypes(ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public List<TypeInfo> getRefObjectSubTypes(String refObjectTypeKey, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return null;
    }

    @Override
    public RefStatementRelationInfo getRefStatementRelation(String refStatementRelationId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public List<RefStatementRelationInfo> getRefStatementRelationsByIds(List<String> refStatementRelationIds,
            ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public List<RefStatementRelationInfo> getRefStatementRelationsByType(String refStatementRelationTypeKey,
            ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException,
            OperationFailedException {
        return null;
    }

    @Override
    public List<RefStatementRelationInfo> getRefStatementRelationsByStatement(String statementId,
            ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public List<org.kuali.student.common.dto.ValidationResultInfo> validateRefStatementRelation(
            String validationTypeKey, String statementId, String refStatementRelationTypeKey,
            RefStatementRelationInfo refStatementRelationInfo, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public RefStatementRelationInfo createRefStatementRelation(String refObjectId, String statementId,
            String refObjectTypeKey, RefStatementRelationInfo refStatementRelationInfo, ContextInfo contextInfo)
            throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException,
            ReadOnlyException {
        return null;
    }

    @Override
    public RefStatementRelationInfo updateRefStatementRelation(String refStatementRelationId,
            RefStatementRelationInfo refStatementRelationInfo, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException,
            VersionMismatchException {
        return null;
    }

    @Override
    public StatusInfo deleteRefStatementRelation(String refStatementRelationId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public TypeInfo getNlUsageByType(String nlUsageTypeKey, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public String getNlByStatementId(String statementId, String nlUsageTypeKey, String language, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public String getNlByRefStatementRelation(String refStatementRelationId, String nlUsageTypeKey, String language,
            ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public String getNlByReqComponent(String reqComponentId, String nlUsageTypeKey, String language,
            ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public String translateStatementTreeViewToNL(StatementTreeViewInfo statementTreeViewInfo, String nlUsageTypeKey,
            String language, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public String translateReqComponentToNL(ReqComponentInfo reqComponentInfo, String nlUsageTypeKey, String language,
            ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public StatementInfo getStatement(String statementId, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public List<StatementInfo> getStatementsByReqComponentId(String reqComponentId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public List<StatementInfo> getStatementsByType(String statementTypeKey, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return null;
    }

    @Override
    public List<StatementInfo> getStatementsForStatement(String statementId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public List<TypeInfo> getStatementTypes(ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public List<TypeInfo> getStatementTypesForStatementType(String statementTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public List<TypeInfo> getStatementTypesForRefStatementRelationType(String refStatementRelationTypeKey,
            ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public List<TypeInfo> getRefStatementRelationTypesForRefObjectSubType(String refSubTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public List<org.kuali.student.common.dto.ValidationResultInfo> validateStatement(String validationTypeKey,
            String statementTypeKey, StatementInfo statementInfo, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public StatementInfo createStatement(String statementTypeKey, StatementInfo statementInfo, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return null;
    }

    @Override
    public StatementInfo updateStatement(String statementId, StatementInfo statementInfo, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException,
            VersionMismatchException {
        return null;
    }

    @Override
    public StatusInfo deleteStatement(String statementId, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public List<TypeInfo> getReqCompFieldTypesForReqComponentType(String reqComponentTypeKey, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return null;
    }

    @Override
    public ReqComponentInfo getReqComponent(String reqComponentId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public List<ReqComponentInfo> getReqComponentsByType(String reqComponentTypeKey, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return null;
    }

    @Override
    public TypeInfo getReqComponentType(String reqComponentTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public List<TypeInfo> getReqComponentTypes(ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public List<TypeInfo> getReqComponentTypesForStatementType(String statementTypeKey)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return null;
    }

    @Override
    public List<org.kuali.student.common.dto.ValidationResultInfo> validateReqComponent(String validationTypeKey,
            String reqComponentTypeKey, ReqComponentInfo reqComponentInfo, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public ReqComponentInfo createReqComponent(String reqComponentTypeKey, ReqComponentInfo reqComponentInfo,
            ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException,
            ReadOnlyException {
        return null;
    }

    @Override
    public ReqComponentInfo updateReqComponent(String reqComponentId, ReqComponentInfo reqComponentInfo,
            ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException,
            VersionMismatchException {
        return null;
    }

    @Override
    public StatusInfo deleteReqComponent(String reqComponentId, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public StatementTreeViewInfo getStatementTreeView(String statementId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public StatementTreeViewInfo getStatementTreeViewForNlUsageType(String statementId, String nlUsageTypeKey,
            String language, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public StatementTreeViewInfo createStatementTreeView(StatementTreeViewInfo statementTreeViewInfo,
            ContextInfo contextInfo) throws DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return null;
    }

    @Override
    public StatementTreeViewInfo updateStatementTreeView(String statementId,
            StatementTreeViewInfo statementTreeViewInfo, ContextInfo contextInfo) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return null;
    }

    @Override
    public StatusInfo deleteStatementTreeView(String statementId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public List<RefStatementRelationInfo> getRefStatementRelationsByRef(String refObjectTypeKey, String refObjectId,
            ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        return null;
    }

    @Override
    public StatementTypeInfo getStatementType(String topStatementType) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;
    }

    @Override
    public String getNaturalLanguageForReqComponent(String reqComponentId, String string, String string2)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException {
        return null;
    }

    //from before the service contract change:

    // getLuStatement
    // return (LuStatementInfo) map.get(luStatementId);

    // getLuStatements
    /*
     * List<LuStatementInfo> list = new ArrayList<LuStatementInfo>(); for(String id : luStatementIdList) { LuStatementInfo
     * stmt = (LuStatementInfo) map.get(id); list.add(stmt); } return list;
     */

    // getLuStatementType
    // return (LuStatementTypeInfo) map.get(luStatementTypeKey);
    
    // getReqComponent
    // return (ReqComponentInfo) map.get(reqComponentId);
    
    // getReqComponents
    /*
     * List<ReqComponentInfo> list = new ArrayList<ReqComponentInfo>(); for(String id : reqComponentIdList) {
     * ReqComponentInfo req = (ReqComponentInfo) map.get(id); list.add(req); } return list;
     */

}
