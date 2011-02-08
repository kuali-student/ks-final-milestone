/**
 * Copyright 2010 The Kuali Foundation Licensed under the Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations under the License.
 */

package org.kuali.student.core.statement.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.kuali.student.core.dictionary.old.dto.FieldDescriptor;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.core.service.impl.BaseAssembler;
import org.kuali.student.core.statement.dao.StatementDao;
import org.kuali.student.core.statement.dto.AbstractStatementInfo;
import org.kuali.student.core.statement.dto.NlUsageTypeInfo;
import org.kuali.student.core.statement.dto.RefStatementRelationInfo;
import org.kuali.student.core.statement.dto.RefStatementRelationTypeInfo;
import org.kuali.student.core.statement.dto.ReqCompFieldInfo;
import org.kuali.student.core.statement.dto.ReqCompFieldTypeInfo;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.statement.dto.ReqComponentTypeInfo;
import org.kuali.student.core.statement.dto.StatementInfo;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.core.statement.dto.StatementTypeInfo;
import org.kuali.student.core.statement.entity.NlUsageType;
import org.kuali.student.core.statement.entity.ObjectSubType;
import org.kuali.student.core.statement.entity.ObjectType;
import org.kuali.student.core.statement.entity.OrderedStatementType;
import org.kuali.student.core.statement.entity.RefStatementRelation;
import org.kuali.student.core.statement.entity.RefStatementRelationAttribute;
import org.kuali.student.core.statement.entity.RefStatementRelationType;
import org.kuali.student.core.statement.entity.ReqComponent;
import org.kuali.student.core.statement.entity.ReqComponentField;
import org.kuali.student.core.statement.entity.ReqComponentFieldType;
import org.kuali.student.core.statement.entity.ReqComponentType;
import org.kuali.student.core.statement.entity.OrderedReqComponentType;
import org.kuali.student.core.statement.entity.Statement;
import org.kuali.student.core.statement.entity.StatementAttribute;
import org.kuali.student.core.statement.entity.StatementRichText;
import org.kuali.student.core.statement.entity.StatementType;
import org.kuali.student.core.statement.naturallanguage.NaturalLanguageTranslator;
import org.springframework.beans.BeanUtils;

public class StatementAssembler extends BaseAssembler {

    private StatementDao statementDao;
    private NaturalLanguageTranslator naturalLanguageTranslator;

    public void setStatementDao(StatementDao dao) {
        this.statementDao = dao;
    }

    public void setNaturalLanguageTranslator(final NaturalLanguageTranslator translator) {
        this.naturalLanguageTranslator = translator;
    }

    public static List<RefStatementRelationInfo> toRefStatementRelationInfos(List<RefStatementRelation> entities) {
        List<RefStatementRelationInfo> list = new ArrayList<RefStatementRelationInfo>();
        for (RefStatementRelation entity : entities) {
            list.add(toRefStatementRelationInfo(entity));
        }
        return list;
    }

    public static RefStatementRelationInfo toRefStatementRelationInfo(RefStatementRelation entity) {
        RefStatementRelationInfo dto = new RefStatementRelationInfo();

        BeanUtils.copyProperties(entity, dto, new String[]{"refStatementRelationType", "statement", "attributes", "metaInfo"});

        // Copy generic attributes
        dto.setAttributes(toAttributeMap(entity.getAttributes()));
        dto.setMetaInfo(toMetaInfo(entity.getMeta(), entity.getVersionNumber()));
        dto.setStatementId(entity.getStatement().getId());
        dto.setType(entity.getRefStatementRelationType().getId());
        // dto.setRefObjectTypeKey(entity.getRefStatementRelationType().getObjectSubTypeList().get(0).g)

        return dto;
    }

    public static List<RefStatementRelationTypeInfo> toRefStatementRelationTypeInfos(List<RefStatementRelationType> entities) {
        List<RefStatementRelationTypeInfo> list = new ArrayList<RefStatementRelationTypeInfo>();
        for (RefStatementRelationType entity : entities) {
            list.add(toRefStatementRelationTypeInfo(entity));
        }
        return list;
    }

    public static RefStatementRelationTypeInfo toRefStatementRelationTypeInfo(RefStatementRelationType entity) {
        RefStatementRelationTypeInfo dto = new RefStatementRelationTypeInfo();

        BeanUtils.copyProperties(entity, dto, new String[]{"attributes", "metaInfo", "objectSubTypeList", "statementTypeList"});

        dto.setAttributes(toAttributeMap(entity.getAttributes()));
        dto.setDesc(entity.getDescr());

        return dto;
    }

    public static List<String> toRefObjectSubTypeIds(ObjectType objectType) {
        List<String> ids = new ArrayList<String>();
        for (ObjectSubType objectSubType : objectType.getObjectSubTypes()) {
            ids.add(objectSubType.getId());
        }
        return ids;
    }

    public static NlUsageTypeInfo toNlUsageTypeInfo(NlUsageType entity) throws OperationFailedException {
        NlUsageTypeInfo info = toGenericTypeInfo(NlUsageTypeInfo.class, entity);
        return info;
    }

    public static List<NlUsageTypeInfo> toNlUsageTypeInfos(List<NlUsageType> entities) throws OperationFailedException {
        List<NlUsageTypeInfo> infoList = new ArrayList<NlUsageTypeInfo>();
        for (NlUsageType entity : entities) {
            NlUsageTypeInfo info = toNlUsageTypeInfo(entity);
            infoList.add(info);
        }
        return infoList;
    }

    public ReqComponent toReqComponentRelation(boolean isUpdate, ReqComponentInfo reqCompInfo) throws DoesNotExistException, VersionMismatchException, InvalidParameterException {
        ReqComponent reqComp;
        if (isUpdate) {
            reqComp = this.statementDao.fetch(ReqComponent.class, reqCompInfo.getId());
            if (reqComp == null) {
                throw new DoesNotExistException("ReqComponent does not exist for id: " + reqCompInfo.getId());
            }
            if (!String.valueOf(reqComp.getVersionNumber()).equals(reqCompInfo.getMetaInfo().getVersionInd())) {
                throw new VersionMismatchException("ReqComponent to be updated is not the current version");
            }
            for (ReqComponentField reqCompField : reqComp.getReqComponentFields()) {
                this.statementDao.delete(reqCompField);
            }
        } else {
            reqComp = new ReqComponent();
        }

        BeanUtils.copyProperties(reqCompInfo, reqComp, new String[]{"requiredComponentType", "reqCompField", "metaInfo", "type"});

        // Search for and copy the type
        ReqComponentType reqCompType = this.statementDao.fetch(ReqComponentType.class, reqCompInfo.getType());
        if (reqCompType == null) {
            throw new InvalidParameterException("ReqComponentType does not exist for id: " + reqCompInfo.getType());
        }
        reqComp.setRequiredComponentType(reqCompType);

        // Create and copy ReqCompFields
        List<ReqComponentField> reqCompFieldList = new ArrayList<ReqComponentField>();
        for (ReqCompFieldInfo reqCompFieldInfo : reqCompInfo.getReqCompFields()) {
            ReqComponentField reqCompField = new ReqComponentField();
            // reqCompField.setId(reqCompFieldInfo.getId());
            reqCompField.setType(reqCompFieldInfo.getType());
            reqCompField.setValue(reqCompFieldInfo.getValue());
            reqCompFieldList.add(reqCompField);
        }
        reqComp.setReqComponentFields(reqCompFieldList);

        reqComp.setDescr(toRichText(StatementRichText.class, reqCompInfo.getDesc()));

        return reqComp;
    }

    public List<ReqComponentInfo> toReqComponentInfos(List<ReqComponent> entities, String nlUsageTypeKey, String language) throws DoesNotExistException, OperationFailedException {
        List<ReqComponentInfo> dtos = new ArrayList<ReqComponentInfo>(entities.size());
        for (ReqComponent entity : entities) {
            dtos.add(toReqComponentInfo(entity, nlUsageTypeKey, language));
        }
        return dtos;

    }

    public static ReqComponentInfo toReqComponentInfo(ReqComponent entity) {
        ReqComponentInfo dto = new ReqComponentInfo();

        BeanUtils.copyProperties(entity, dto, new String[]{"requiredComponentType", "reqCompField", "metaInfo"});

        dto.setType(entity.getRequiredComponentType().getId());
        dto.setReqCompFields(toReqCompFieldInfos(entity.getReqComponentFields()));
        // dto.setRequiredComponentType(toReqComponentTypeInfo(entity.getRequiredComponentType()));
        dto.setMetaInfo(toMetaInfo(entity));
        dto.setDesc(toRichTextInfo(entity.getDescr()));
        // if(nlUsageTypeKey != null && language != null) {
        // String nl = this.naturalLanguageTranslator.translateReqComponent(entity, nlUsageTypeKey, language);
        // dto.setNaturalLanguageTranslation(nl);
        // }
        return dto;
    }

    public ReqComponentInfo toReqComponentInfo(ReqComponent entity, String nlUsageTypeKey, String language) throws DoesNotExistException, OperationFailedException {
        ReqComponentInfo dto = toReqComponentInfo(entity);
        if (nlUsageTypeKey != null && language != null) {
            String nl = this.naturalLanguageTranslator.translateReqComponent(entity, nlUsageTypeKey, language);
            dto.setNaturalLanguageTranslation(nl);
        }
        return dto;
    }

    public static List<ReqComponentTypeInfo> toReqComponentTypeInfos(List<ReqComponentType> entities) {
        List<ReqComponentTypeInfo> dtos = new ArrayList<ReqComponentTypeInfo>(entities.size());
        for (ReqComponentType entity : entities) {
            dtos.add(toReqComponentTypeInfo(entity));
        }
        return dtos;

    }

    public static List<ReqComponentTypeInfo> toReqComponentTypeInfosOrdered(List<OrderedReqComponentType> entities) {
        List<ReqComponentTypeInfo> dtos = new ArrayList<ReqComponentTypeInfo>(entities.size());
        for (OrderedReqComponentType entity : entities) {
            dtos.add(toReqComponentTypeInfo(entity.getReqComponentType()));
        }
        return dtos;

    }

    public static ReqComponentTypeInfo toReqComponentTypeInfo(ReqComponentType entity) {
        ReqComponentTypeInfo dto = toGenericTypeInfo(ReqComponentTypeInfo.class, entity);
        dto.setReqCompFieldTypeInfos(toReqCompFieldTypeInfos(entity.getReqCompFieldTypes()));
        dto.setDescr(entity.getDescr());
        return dto;
    }

    public static List<ReqCompFieldTypeInfo> toReqCompFieldTypeInfos(List<ReqComponentFieldType> entities) {
        List<ReqCompFieldTypeInfo> dtos = new ArrayList<ReqCompFieldTypeInfo>(entities.size());
        for (ReqComponentFieldType entity : entities) {
            dtos.add(toReqCompFieldTypeInfo(entity));
        }
        return dtos;
    }

    public static ReqCompFieldTypeInfo toReqCompFieldTypeInfo(ReqComponentFieldType entity) {
        ReqCompFieldTypeInfo dto = new ReqCompFieldTypeInfo();

        BeanUtils.copyProperties(entity, dto, new String[]{"fieldDescriptor"});

        FieldDescriptor fDTO = new FieldDescriptor();
        BeanUtils.copyProperties(entity.getFieldDescriptor(), fDTO);
        fDTO.setDesc(entity.getFieldDescriptor().getDescr());
        dto.setFieldDescriptor(fDTO);

        return dto;
    }

    public static List<ReqCompFieldInfo> toReqCompFieldInfos(List<ReqComponentField> entities) {
        if (entities == null) {
            return null;
        }
        List<ReqCompFieldInfo> dtos = new ArrayList<ReqCompFieldInfo>(entities.size());
        for (ReqComponentField entity : entities) {
            dtos.add(toReqCompFieldInfo(entity));
        }
        return dtos;
    }

    public static ReqCompFieldInfo toReqCompFieldInfo(ReqComponentField entity) {
        if (null == entity) {
            return null;
        }

        ReqCompFieldInfo dto = new ReqCompFieldInfo();
        dto.setId(entity.getId());
        dto.setType(entity.getType());
        dto.setValue(entity.getValue());
        return dto;
    }

    public RefStatementRelation toRefStatementRelation(boolean isUpdate, RefStatementRelationInfo refStatementRelationInfo) throws DoesNotExistException, VersionMismatchException, InvalidParameterException {
        RefStatementRelation refStatement;
        if (isUpdate) {
            refStatement = this.statementDao.fetch(RefStatementRelation.class, refStatementRelationInfo.getId());
            if (refStatement == null) {
                throw new DoesNotExistException("RefStatementRelation does not exist for id: " + refStatementRelationInfo.getId());
            }
            if (!String.valueOf(refStatement.getVersionNumber()).equals(refStatementRelationInfo.getMetaInfo().getVersionInd())) {
                throw new VersionMismatchException("RefStatementRelation to be updated is not the current version");
            }
        } else {
            refStatement = new RefStatementRelation();
        }

        BeanUtils.copyProperties(refStatementRelationInfo, refStatement, new String[]{"attributes", "metaInfo", "type", "statementId"});

        // make sure refObjectType exist
        this.statementDao.fetch(ObjectType.class, refStatementRelationInfo.getRefObjectTypeKey());

        // Copy generic attributes
        refStatement.setAttributes(toGenericAttributes(RefStatementRelationAttribute.class, refStatementRelationInfo.getAttributes(), refStatement, this.statementDao));
        RefStatementRelationType type = this.statementDao.fetch(RefStatementRelationType.class, refStatementRelationInfo.getType());

        refStatement.setRefStatementRelationType(type);
        Statement statement = this.statementDao.fetch(Statement.class, refStatementRelationInfo.getStatementId());
        refStatement.setStatement(statement);

        return refStatement;
    }

    public Statement toStatementFromTree(Statement stmt, StatementTreeViewInfo treeView, Set<String> statementIdsToDelete, List<Statement> statementsToUpdate, List<ReqComponent> reqCompsToCreate) throws DoesNotExistException, VersionMismatchException, InvalidParameterException {

        // copy any non-transactional fields from treeview to statement, ignoring the properties in the array parameter
        BeanUtils.copyProperties(treeView, stmt, new String[]{"cluIds", "statementIds", "reqComponentIds", 
                "attributes", "metaInfo", "type", "parent", "children", "requiredComponents", "statementType", "id"});

        // Copy generic attributes
        stmt.setAttributes(toGenericAttributes(StatementAttribute.class, treeView.getAttributes(), stmt, this.statementDao));

        // Search for and copy the type
        StatementType stmtType = this.statementDao.fetch(StatementType.class, treeView.getType());
        if (stmtType == null) {
            throw new InvalidParameterException("StatementType does not exist for id: " + treeView.getType());
        }
        stmt.setStatementType(stmtType);

        // Copy nested requirements
        List<ReqComponent> reqCompList = new ArrayList<ReqComponent>();
        for (ReqComponentInfo reqCompInfo : treeView.getReqComponents()) {

            boolean isUpdate = (reqCompInfo.getId() != null);
            
            ReqComponent reqComp = toReqComponentRelation(isUpdate, reqCompInfo);
            
            if (!isUpdate) {
                reqCompsToCreate.add(reqComp);
            }

            reqCompList.add(reqComp);
        }

        stmt.setRequiredComponents(reqCompList);

        stmt.setDescr(toRichText(StatementRichText.class, treeView.getDesc()));

        Map<String, Statement> stmtsToDelete = new HashMap<String, Statement>();
        if (stmt.getChildren() != null) {
            for (Statement childStmt : stmt.getChildren()) {
                stmtsToDelete.put(childStmt.getId(), childStmt);
            }
            stmt.getChildren().clear();
        } else {
            stmt.setChildren(new ArrayList<Statement>());
        }
        for (StatementTreeViewInfo childTreeView : treeView.getStatements()) {
            Statement childStmt;
            if (childTreeView.getId() != null && stmtsToDelete.containsKey(childTreeView.getId())) {
                childStmt = stmtsToDelete.remove(childTreeView.getId());
            } else {
                childStmt = new Statement();
            }
            childStmt = toStatementFromTree(childStmt, childTreeView, statementIdsToDelete, statementsToUpdate, reqCompsToCreate);
            stmt.getChildren().add(childStmt);
        }
        for (Statement statementToDelete : stmtsToDelete.values()) {
            deleteStatementsRecursively(statementToDelete, statementIdsToDelete, statementsToUpdate);
        }
        statementIdsToDelete.addAll(stmtsToDelete.keySet());
        return stmt;
    }

    // Recursively parse through a statement tree, clear all children and if the children were cleared add to the list of
    // statements to be updated
    // Then add the statement id to the list of statements to be deleted so they can be updated and deleted separately.
    private void deleteStatementsRecursively(Statement statementToDelete, Set<String> statementIdsToDelete, List<Statement> statementsToUpdate) {
        if (statementToDelete.getChildren() != null) {
            for (Statement childStatement : statementToDelete.getChildren()) {
                deleteStatementsRecursively(childStatement, statementIdsToDelete, statementsToUpdate);
            }
            statementToDelete.getChildren().clear();
            statementsToUpdate.add(statementToDelete);
        }
        statementIdsToDelete.add(statementToDelete.getId());
    }

    public Statement toStatementRelation(boolean isUpdate, StatementInfo stmtInfo) throws DoesNotExistException, VersionMismatchException, InvalidParameterException, OperationFailedException {
        Statement stmt;
        if (isUpdate) {
            stmt = this.statementDao.fetch(Statement.class, stmtInfo.getId());
            if (stmt == null) {
                throw new DoesNotExistException("Statement does not exist for id: " + stmtInfo.getId());
            }
            if (!String.valueOf(stmt.getVersionNumber()).equals(stmtInfo.getMetaInfo().getVersionInd())) {
                throw new VersionMismatchException("Statement to be updated is not the current version");
            }
        } else {
            stmt = new Statement();
        }

        BeanUtils.copyProperties(stmtInfo, stmt, new String[]{"cluIds", "statementIds", "reqComponentIds", "attributes", "metaInfo", "type", "parent", "children", "requiredComponents", "statementType"});

        // Copy generic attributes
        stmt.setAttributes(toGenericAttributes(StatementAttribute.class, stmtInfo.getAttributes(), stmt, this.statementDao));

        // Search for and copy the type
        StatementType stmtType = this.statementDao.fetch(StatementType.class, stmtInfo.getType());
        if (stmtType == null) {
            throw new InvalidParameterException("StatementType does not exist for id: " + stmtInfo.getType());
        }
        stmt.setStatementType(stmtType);

        // Copy nested statements

        List<Statement> stmtList = new ArrayList<Statement>();
        for (String stmtId : stmtInfo.getStatementIds()) {
            if (stmtId.equals(stmtInfo.getId())) {
                throw new OperationFailedException("Statement nested within itself. Statement Id: " + stmtInfo.getId());
            }

            Statement nestedStmt = this.statementDao.fetch(Statement.class, stmtId);
            if (null == nestedStmt) {
                throw new DoesNotExistException("Nested Statement does not exist for id: " + stmtId + ". Parent Statement: " + stmtInfo.getId());
            }

            stmtList.add(nestedStmt);
        }
        stmt.setChildren(stmtList);

        // Copy nested requirements
        List<ReqComponent> reqCompList = new ArrayList<ReqComponent>();
        for (String reqId : stmtInfo.getReqComponentIds()) {
            ReqComponent reqComp = this.statementDao.fetch(ReqComponent.class, reqId);

            if (null == reqComp) {
                throw new DoesNotExistException("Nested Requirement does not exist for id: " + reqId + ". Parent Statement Id: " + stmtInfo.getId());
            }

            reqCompList.add(reqComp);
        }
        stmt.setRequiredComponents(reqCompList);

        stmt.setDescr(toRichText(StatementRichText.class, stmtInfo.getDesc()));

        return stmt;
    }

    public static StatementInfo toStatementInfo(Statement entity) {
        if (entity == null) {
            return null;
        }
        StatementInfo dto = new StatementInfo();

        BeanUtils.copyProperties(entity, dto, new String[]{"parent", "children", "requiredComponents", "statementType", "attributes", "metaInfo"});

        List<String> statementIds = new ArrayList<String>(entity.getChildren().size());
        for (Statement statement : entity.getChildren()) {
            statementIds.add(statement.getId());
        }
        dto.setStatementIds(statementIds);

        List<String> componentIds = new ArrayList<String>(entity.getRequiredComponents().size());
        for (ReqComponent reqComponent : entity.getRequiredComponents()) {
            componentIds.add(reqComponent.getId());
        }
        dto.setReqComponentIds(componentIds);
        dto.setType(entity.getStatementType().getId());
        dto.setAttributes(toAttributeMap(entity.getAttributes()));
        dto.setMetaInfo(toMetaInfo(entity.getMeta(), entity.getVersionNumber()));
        dto.setName(entity.getName());
        dto.setOperator(entity.getOperator());
        dto.setDesc(toRichTextInfo(entity.getDescr()));
        return dto;
    }

    public static List<StatementInfo> toStatementInfos(List<Statement> entities) {
        List<StatementInfo> dtos = new ArrayList<StatementInfo>(entities.size());
        for (Statement entity : entities) {
            dtos.add(toStatementInfo(entity));
        }
        return dtos;

    }

    public static List<StatementTypeInfo> toStatementTypeInfos(List<StatementType> entities) {
        List<StatementTypeInfo> list = new ArrayList<StatementTypeInfo>();
        for (StatementType type : entities) {
            StatementTypeInfo dto = toStatementTypeInfo(type);
            list.add(dto);
        }
        return list;
    }

    public static StatementTypeInfo toStatementTypeInfo(StatementType entity) {
        if (entity == null) {
            return null;
        }
        StatementTypeInfo stmtTypeInfo = toGenericTypeInfo(StatementTypeInfo.class, entity);

        // Copy allowed RequiredComponent Types
        List<String> reqTypeIds = new ArrayList<String>(entity.getAllowedReqComponentTypes().size());
        for (OrderedReqComponentType reqComponentTypeOrder : entity.getAllowedReqComponentTypes()) {
            reqTypeIds.add(reqComponentTypeOrder.getReqComponentType().getId());
        }
        stmtTypeInfo.setAllowedReqComponentTypes(reqTypeIds);

        // Copy allowed Statement Types
        List<String> stmtIds = new ArrayList<String>(entity.getAllowedStatementTypes().size());
        for (OrderedStatementType stmtType : entity.getAllowedStatementTypes()) {
            stmtIds.add(stmtType.getChildStatementType().getId());
        }
        stmtTypeInfo.setAllowedStatementTypes(stmtIds);

        // statement type header is no longer defined in specification
        // stmtTypeInfo.setHeaders(toStatementTypeHeaderTemplateInfos(entity.getHeaders()));

        stmtTypeInfo.setDescr(entity.getDescr());

        return stmtTypeInfo;
    }

    public StatementInfo toStatementInfo(final StatementTreeViewInfo statementTreeViewInfo) {
        StatementInfo statementInfo = null;
        if (statementTreeViewInfo == null)
            return null;
        statementInfo = new StatementInfo();
        copyValues(statementInfo, statementTreeViewInfo);
        // goes through the list of reqComponents in statementTreeViewInfo and extract the reqComponent ids
        if (statementTreeViewInfo.getReqComponents() != null) {
            List<String> reqCompIds = new ArrayList<String>(7);
            for (ReqComponentInfo reqComponentInfo : statementTreeViewInfo.getReqComponents()) {
                reqCompIds.add(reqComponentInfo.getId());
            }
            statementInfo.setReqComponentIds(reqCompIds);
        }
        statementInfo.setState(statementTreeViewInfo.getState());
        // TODO goes through the list of statements in statementTreeViewInfo and extract the statement ids
        if (statementTreeViewInfo.getStatements() != null) {
            List<String> statementIds = new ArrayList<String>(7);
            for (StatementTreeViewInfo subStatementTreeViewInfo : statementTreeViewInfo.getStatements()) {
                statementIds.add(subStatementTreeViewInfo.getId());
            }
            statementInfo.setStatementIds(statementIds);
        }
        statementInfo.setType(statementTreeViewInfo.getType());
        return statementInfo;
    }

    /**
     * copies the values in statementInfo into statementTreeViewInfo. Only the values of the root statement will be affected.
     * 
     * @param toStatementInfo
     * @param fromStatementInfo
     */
    public void copyValues(final AbstractStatementInfo toStatementInfo, AbstractStatementInfo fromStatementInfo) {
        toStatementInfo.setAttributes(fromStatementInfo.getAttributes());
        toStatementInfo.setDesc(fromStatementInfo.getDesc());
        toStatementInfo.setId(fromStatementInfo.getId());
        toStatementInfo.setMetaInfo(fromStatementInfo.getMetaInfo());
        toStatementInfo.setName(fromStatementInfo.getName());
        toStatementInfo.setOperator(fromStatementInfo.getOperator());
        toStatementInfo.setState(fromStatementInfo.getState());
        toStatementInfo.setType(fromStatementInfo.getType());
    }

    public Statement toStatement(final StatementTreeViewInfo statementTreeViewInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, VersionMismatchException {
        return toCustomLuStatementInfo(statementTreeViewInfo);
    }

    /**
     * <p>
     * Converts a {@link LuNlStatementInfo} into a {@link CustomReqComponentInfo}.
     * </p>
     * <p>
     * Note: LuNlStatementInfo has no id since it is only used for on-the-fly translations and is not persisted.
     * </p>
     * 
     * @param statementInfo
     *            LuNlStatementInfo to convert
     * @return A custom LU statement
     * @throws VersionMismatchException
     * @throws Exception
     *             If conversion fails
     */
    public Statement toCustomLuStatementInfo(final StatementTreeViewInfo statementInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, VersionMismatchException {
        Statement stmt = new Statement();
        stmt.setName(statementInfo.getName());
        stmt.setOperator(statementInfo.getOperator());
        // TODO: Fix with LuService RC1.4 changes
        // LuStatementTypeInfo stmtType = this.luService.getLuStatementType(statementInfo.getStatementTypeId());
        // stmt.setLuStatementType(stmtType);
        StatementType type = this.statementDao.fetch(StatementType.class, statementInfo.getType());
        stmt.setStatementType(type);

        if (statementInfo.getStatements() == null || statementInfo.getStatements().isEmpty()) {
            // setReqComponentType(statementInfo.getReqComponents());
            List<ReqComponent> customReqList = toReqComponents(statementInfo.getReqComponents());
            stmt.setRequiredComponents(customReqList);
        } else {
            createStatement(statementInfo, stmt);
        }

        return stmt;
    }

    /*
     * private void setReqComponentType(List<ReqComponentInfo> reqList) throws DoesNotExistException,
     * InvalidParameterException, MissingParameterException, OperationFailedException { for(ReqComponentInfo req : reqList) {
     * setReqComponentType(req); } } private void setReqComponentType(ReqComponentInfo req) throws DoesNotExistException,
     * InvalidParameterException, MissingParameterException, OperationFailedException { if(req.getRequiredComponentType() ==
     * null) { //TODO: Fix with LuService RC1.4 changes ReqComponentType type =
     * this.statementDao.fetch(ReqComponentType.class, req.getType());
     * req.setRequiredComponentType(toReqComponentTypeInfo(type)); } }
     */

    private void createStatement(final StatementTreeViewInfo stmtInfo, Statement rootLuStatement) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, VersionMismatchException {
        for (StatementTreeViewInfo luNlStmt : stmtInfo.getStatements()) {
            Statement stmt = new Statement();
            stmt.setName(luNlStmt.getName());
            stmt.setOperator(luNlStmt.getOperator());
            if (luNlStmt.getType() != null) {
                // TODO: Fix with LuService RC1.4 changes
                // stmt.setLuStatementType(this.luService.getLuStatementType(luNlStmt.getStatementTypeId()));
                StatementType type = this.statementDao.fetch(StatementType.class, luNlStmt.getType());
                stmt.setStatementType(type);
            }

            if (rootLuStatement.getChildren() == null) {
                rootLuStatement.setChildren(new ArrayList<Statement>());
            }
            rootLuStatement.getChildren().add(stmt);
            if (luNlStmt.getStatements() == null || luNlStmt.getStatements().isEmpty()) {
                List<ReqComponentInfo> children = luNlStmt.getReqComponents();
                // setReqComponentType(children);
                List<ReqComponent> customReqList = toReqComponents(children);
                stmt.setRequiredComponents(customReqList);
            } else {
                createStatement(luNlStmt, stmt);
            }
        }
    }

    private List<ReqComponent> toReqComponents(List<ReqComponentInfo> reqComponentInfoList) throws DoesNotExistException, VersionMismatchException, InvalidParameterException {
        List<ReqComponent> list = new ArrayList<ReqComponent>();
        for (ReqComponentInfo info : reqComponentInfoList) {
            ReqComponent req = toReqComponentRelation(false, info);
            list.add(req);
        }
        return list;
    }

    public StatementTreeViewInfo toStatementTreeViewInfo(Statement stmt) {
        StatementTreeViewInfo treeView = new StatementTreeViewInfo();
        treeView.setAttributes(toAttributeMap(stmt.getAttributes()));
        treeView.setDesc(toRichTextInfo(stmt.getDescr()));
        treeView.setId(stmt.getId());
        treeView.setMetaInfo(toMetaInfo(stmt.getMeta(), stmt.getVersionNumber()));
        treeView.setName(stmt.getName());
        treeView.setType(stmt.getStatementType().getId());
        treeView.setState(stmt.getState());
        for (ReqComponent reqComp : stmt.getRequiredComponents()) {
            treeView.getReqComponents().add(toReqComponentInfo(reqComp));
        }
        treeView.setOperator(stmt.getOperator());
        for (Statement childStmt : stmt.getChildren()) {
            treeView.getStatements().add(toStatementTreeViewInfo(childStmt));
        }

        return treeView;
    }
}
