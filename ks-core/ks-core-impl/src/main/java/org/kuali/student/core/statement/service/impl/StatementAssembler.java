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

package org.kuali.student.core.statement.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.core.statement.dao.StatementDao;
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
import org.kuali.student.core.statement.entity.RefStatementRelation;
import org.kuali.student.core.statement.entity.RefStatementRelationAttribute;
import org.kuali.student.core.statement.entity.RefStatementRelationType;
import org.kuali.student.core.statement.entity.ReqComponent;
import org.kuali.student.core.statement.entity.ReqComponentField;
import org.kuali.student.core.statement.entity.ReqComponentFieldType;
import org.kuali.student.core.statement.entity.ReqComponentType;
import org.kuali.student.core.statement.entity.Statement;
import org.kuali.student.core.statement.entity.StatementAttribute;
import org.kuali.student.core.statement.entity.StatementRichText;
import org.kuali.student.core.statement.entity.StatementType;
import org.kuali.student.core.dictionary.old.dto.FieldDescriptor;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.core.service.impl.BaseAssembler;
import org.springframework.beans.BeanUtils;

public class StatementAssembler extends BaseAssembler {

	private StatementDao statementDao;
	
	public void setStatementDao(StatementDao dao) {
		this.statementDao = dao;
	}

	public List<RefStatementRelationInfo> toRefStatementRelationInfos(List<RefStatementRelation> entities) {
		List<RefStatementRelationInfo> list = new ArrayList<RefStatementRelationInfo>();
		for(RefStatementRelation entity : entities) {
			list.add(toRefStatementRelationInfo(entity));
		}
		return list;
	}
	
	public RefStatementRelationInfo toRefStatementRelationInfo(RefStatementRelation entity) {
		RefStatementRelationInfo dto = new RefStatementRelationInfo();
		
        BeanUtils.copyProperties(entity, dto, new String[]{
        		"refStatementRelationType", "statement", "attributes", "metaInfo"});

        // Copy generic attributes
        dto.setAttributes(toAttributeMap(entity.getAttributes()));
        dto.setMetaInfo(toMetaInfo(entity.getMeta(), entity.getVersionInd()));
        dto.setStatementId(entity.getStatement().getId());
        dto.setType(entity.getRefStatementRelationType().getId());
        //dto.setRefObjectTypeKey(entity.getRefStatementRelationType().getObjectSubTypeList().get(0).g)
        
        return dto;
	}

	public List<RefStatementRelationTypeInfo> toRefStatementRelationTypeInfos(List<RefStatementRelationType> entities) {
		List<RefStatementRelationTypeInfo> list = new ArrayList<RefStatementRelationTypeInfo>();
		for(RefStatementRelationType entity : entities) {
			list.add(toRefStatementRelationTypeInfo(entity));
		}
		return list;
	}
	
	public RefStatementRelationTypeInfo toRefStatementRelationTypeInfo(RefStatementRelationType entity) {
		RefStatementRelationTypeInfo dto = new RefStatementRelationTypeInfo();
		
        BeanUtils.copyProperties(entity, dto, new String[]{
        		"attributes", "metaInfo", "objectSubTypeList", "statementTypeList"});
        
        dto.setAttributes(toAttributeMap(entity.getAttributes()));
        dto.setDesc(entity.getDescr());
        
        return dto;
	}
	
	public List<String> toRefObjectSubTypeIds(ObjectType objectType) {
		List<String> ids = new ArrayList<String>();
		for(ObjectSubType objectSubType : objectType.getObjectSubTypes()) {
			ids.add(objectSubType.getId());
		}
		return ids;
	}
	
	public NlUsageTypeInfo toNlUsageTypeInfo(NlUsageType entity) throws OperationFailedException {
		NlUsageTypeInfo info = toGenericTypeInfo(NlUsageTypeInfo.class, entity);
		return info;
	}
	
	public List<NlUsageTypeInfo> toNlUsageTypeInfos(List<NlUsageType> entities) throws OperationFailedException {
		List<NlUsageTypeInfo> infoList = new ArrayList<NlUsageTypeInfo>();
		for(NlUsageType entity : entities) {
			NlUsageTypeInfo info = toNlUsageTypeInfo(entity);
			infoList.add(info);
		}
		return infoList;
	}	
	
    public ReqComponent toReqComponentRelation(boolean isUpdate, ReqComponentInfo reqCompInfo) 
    		throws DoesNotExistException, VersionMismatchException, InvalidParameterException {
        ReqComponent reqComp;
        if (isUpdate) {
            reqComp = this.statementDao.fetch(ReqComponent.class, reqCompInfo.getId());
            if (reqComp == null) {
                throw new DoesNotExistException("ReqComponent does not exist for id: " + reqCompInfo.getId());
            }
            if (!String.valueOf(reqComp.getVersionInd()).equals(reqCompInfo.getMetaInfo().getVersionInd())) {
                throw new VersionMismatchException("ReqComponent to be updated is not the current version");
            }
            for(ReqComponentField reqCompField : reqComp.getReqComponentFields()) {
            	this.statementDao.delete(reqCompField);
            }
        } else {
            reqComp = new ReqComponent();
        }

        BeanUtils.copyProperties(reqCompInfo, reqComp, new String[]{"requiredComponentType", "reqCompField", "metaInfo", "type"});

        // Search for and copy the type
        ReqComponentType reqCompType = this.statementDao.fetch(ReqComponentType.class, reqCompInfo.getType());
        if (reqCompType == null) {
            throw new InvalidParameterException(
                    "ReqComponentType does not exist for id: " + reqCompInfo.getType());
        }
        reqComp.setRequiredComponentType(reqCompType);

        // Create and copy ReqCompFields
        List<ReqComponentField> reqCompFieldList = new ArrayList<ReqComponentField>();
        for(ReqCompFieldInfo reqCompFieldInfo : reqCompInfo.getReqCompFields()) {
            ReqComponentField reqCompField = new ReqComponentField();
            reqCompField.setKey(reqCompFieldInfo.getId());
            reqCompField.setValue(reqCompFieldInfo.getValue());
            reqCompFieldList.add(reqCompField);
        }
        reqComp.setReqComponentFields(reqCompFieldList);
        
        reqComp.setDescr(toRichText(StatementRichText.class, reqCompInfo.getDesc()));
        
        return reqComp;
    }
    
    public List<ReqComponentInfo> toReqComponentInfos(
            List<ReqComponent> entities) {
        List<ReqComponentInfo> dtos = new ArrayList<ReqComponentInfo>(
                entities.size());
        for (ReqComponent entity : entities) {
            dtos.add(toReqComponentInfo(entity));
        }
        return dtos;

    }

    public ReqComponentInfo toReqComponentInfo(ReqComponent entity) {
        ReqComponentInfo dto = new ReqComponentInfo();

        BeanUtils.copyProperties(entity, dto, new String[] {
                "requiredComponentType", "reqCompField", "metaInfo" });

        dto.setType(entity.getRequiredComponentType().getId());
        dto.setReqCompFields(toReqCompFieldInfos(entity.getReqComponentFields()));
        dto.setRequiredComponentType(toReqComponentTypeInfo(entity.getRequiredComponentType()));
        dto.setMetaInfo(toMetaInfo(entity.getMeta(), entity.getVersionInd()));
        dto.setDesc(toRichTextInfo(entity.getDescr()));
        return dto;
    }
    
    public List<ReqComponentTypeInfo> toReqComponentTypeInfos(List<ReqComponentType> entities) {
        List<ReqComponentTypeInfo> dtos = new ArrayList<ReqComponentTypeInfo>(entities.size());
        for (ReqComponentType entity : entities) {
            dtos.add(toReqComponentTypeInfo(entity));
        }
        return dtos;

    }

    public ReqComponentTypeInfo toReqComponentTypeInfo(ReqComponentType entity) {
        ReqComponentTypeInfo dto = toGenericTypeInfo(ReqComponentTypeInfo.class, entity);
        dto.setReqCompFieldTypeInfos(toReqCompFieldTypeInfos(entity.getReqCompFieldTypes()));
        dto.setDescr(entity.getDescr());
        return dto;
    }
    
    public List<ReqCompFieldTypeInfo> toReqCompFieldTypeInfos(
            List<ReqComponentFieldType> entities) {
        List<ReqCompFieldTypeInfo> dtos = new ArrayList<ReqCompFieldTypeInfo>(
                entities.size());
        for (ReqComponentFieldType entity : entities) {
            dtos.add(toReqCompFieldTypeInfo(entity));
        }
        return dtos;
    }

    public ReqCompFieldTypeInfo toReqCompFieldTypeInfo(
            ReqComponentFieldType entity) {
        ReqCompFieldTypeInfo dto = new ReqCompFieldTypeInfo();

        BeanUtils.copyProperties(entity, dto, new String[] { "fieldDescriptor" });

        FieldDescriptor fDTO = new FieldDescriptor();
        BeanUtils.copyProperties(entity.getFieldDescriptor(), fDTO);
        fDTO.setDesc(entity.getFieldDescriptor().getDescr());
        dto.setFieldDescriptor(fDTO);

        return dto;
    }

    public List<ReqCompFieldInfo> toReqCompFieldInfos(
            List<ReqComponentField> entities) {
        List<ReqCompFieldInfo> dtos = new ArrayList<ReqCompFieldInfo>(
                entities.size());
        for (ReqComponentField entity : entities) {
            dtos.add(toReqCompFieldInfo(entity));
        }
        return dtos;
    }

    public ReqCompFieldInfo toReqCompFieldInfo(ReqComponentField entity) {
        if (null == entity) {
            return null;
        }

        ReqCompFieldInfo dto = new ReqCompFieldInfo();
        dto.setId(entity.getKey());
        dto.setValue(entity.getValue());
        return dto;
    }

    public RefStatementRelation toRefStatementRelation(boolean isUpdate, RefStatementRelationInfo refStatementRelationInfo) 
    		throws DoesNotExistException, VersionMismatchException, InvalidParameterException {
    	RefStatementRelation refStatement;
        if (isUpdate) {
            refStatement = this.statementDao.fetch(RefStatementRelation.class, refStatementRelationInfo.getId());
            if (refStatement == null) {
                throw new DoesNotExistException("RefStatementRelation does not exist for id: " + refStatementRelationInfo.getId());
            }
            if (!String.valueOf(refStatement.getVersionInd()).equals(refStatementRelationInfo.getMetaInfo().getVersionInd())) {
                throw new VersionMismatchException("RefStatementRelation to be updated is not the current version");
            }
        } else {
            refStatement = new RefStatementRelation();
        }
    	
        BeanUtils.copyProperties(refStatementRelationInfo, refStatement, new String[]{
        		"attributes", "metaInfo", "type", "statementId"});

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

    public Statement toStatementRelation(boolean isUpdate, StatementInfo stmtInfo) throws DoesNotExistException, VersionMismatchException, InvalidParameterException, OperationFailedException {
        Statement stmt;
        if (isUpdate) {
            stmt = this.statementDao.fetch(Statement.class, stmtInfo.getId());
            if (stmt == null) {
                throw new DoesNotExistException("Statement does not exist for id: " + stmtInfo.getId());
            }
            if (!String.valueOf(stmt.getVersionInd()).equals(stmtInfo.getMetaInfo().getVersionInd())) {
                throw new VersionMismatchException("Statement to be updated is not the current version");
            }
        } else {
            stmt = new Statement();
        }

        BeanUtils.copyProperties(stmtInfo, stmt, new String[]{"cluIds", "statementIds", 
                "reqComponentIds", "attributes", "metaInfo", "type", 
                "parent", "children", "requiredComponents", "statementType"});

        // Copy generic attributes
        stmt.setAttributes(toGenericAttributes(StatementAttribute.class, stmtInfo.getAttributes(), stmt, this.statementDao));

        // Search for and copy the type
        StatementType stmtType = this.statementDao.fetch(StatementType.class, stmtInfo.getType());
        if (stmtType == null) {
            throw new InvalidParameterException(
                    "StatementType does not exist for id: " + stmtInfo.getType());
        }
        stmt.setStatementType(stmtType);

        // Copy nested statements
        List<Statement> stmtList = new ArrayList<Statement>();
        for(String stmtId : stmtInfo.getStatementIds()) {
            if(stmtId.equals(stmtInfo.getId())) {
                throw new OperationFailedException("Statement nested within itself. Statement Id: " + stmtInfo.getId());
            }

            Statement nestedStmt = this.statementDao.fetch(Statement.class, stmtId);
            if (null == nestedStmt) {
                throw new DoesNotExistException("Nested Statement does not exist for id: " + stmtId + ". Parent Statement: " + stmtInfo.getId());
            }

            stmtList.add(nestedStmt);
        }
        stmt.setChildren(stmtList);
        if (stmtInfo.getParentId() != null) {
            Statement parentStatement = this.statementDao.fetch(Statement.class, stmtInfo.getParentId());
            stmt.setParent(parentStatement);
        }

        // Copy nested requirements
        List<ReqComponent> reqCompList = new ArrayList<ReqComponent>();
        for(String reqId: stmtInfo.getReqComponentIds()) {
            ReqComponent reqComp = this.statementDao.fetch(ReqComponent.class, reqId);

            if(null == reqComp) {
                throw new DoesNotExistException("Nested Requirement does not exist for id: " + reqId + ". Parent Statement Id: " + stmtInfo.getId());
            }

            reqCompList.add(reqComp);
        }
        stmt.setRequiredComponents(reqCompList);
        
        // TODO populate the parent of the Statement here
//        if(stmtInfo.getParentId() != null) {
//            Statement parent = dao.fetch(Statement.class, stmtInfo.getParentId());
//            stmt.setParent(parent);
//        }
        
        stmt.setDescr(toRichText(StatementRichText.class, stmtInfo.getDesc()));
        
        return stmt;
    }

    public StatementInfo toStatementInfo(Statement entity) {
        if(entity==null){
            return null;
        }
        StatementInfo dto = new StatementInfo();

        BeanUtils.copyProperties(entity, dto, new String[] { "parent", "children",
                "requiredComponents", "statementType", "attributes", "metaInfo" });

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
        dto.setMetaInfo(toMetaInfo(entity.getMeta(), entity.getVersionInd()));
        dto.setName(entity.getName());
        dto.setOperator(entity.getOperator());
        dto.setDesc(toRichTextInfo(entity.getDescr()));
        return dto;
    }
    
    public List<StatementInfo> toStatementInfos(
            List<Statement> entities) {
        List<StatementInfo> dtos = new ArrayList<StatementInfo>(entities
                .size());
        for (Statement entity : entities) {
            dtos.add(toStatementInfo(entity));
        }
        return dtos;

    }

    public List<StatementTypeInfo> toStatementTypeInfos(List<StatementType> entities) {
    	List<StatementTypeInfo> list = new ArrayList<StatementTypeInfo>();
    	for(StatementType type : entities) {
    		StatementTypeInfo dto = toStatementTypeInfo(type);
    		list.add(dto);
    	}
    	return list;
    }
    
    public StatementTypeInfo toStatementTypeInfo(StatementType entity) {
        if(entity==null){
            return null;
        }
        StatementTypeInfo stmtTypeInfo = toGenericTypeInfo(StatementTypeInfo.class, entity);

        // Copy allowed RequiredComponent Types
        List<String> reqTypeIds = new ArrayList<String>(entity.getAllowedReqComponentTypes().size());
        for (ReqComponentType reqComponentType : entity.getAllowedReqComponentTypes()) {
            reqTypeIds.add(reqComponentType.getId());
        }
        stmtTypeInfo.setAllowedReqComponentTypes(reqTypeIds);

        // Copy allowed Statement Types
        List<String> stmtIds = new ArrayList<String>(entity.getAllowedStatementTypes().size());
        for (StatementType stmtType : entity.getAllowedStatementTypes()) {
            stmtIds.add(stmtType.getId());
        }
        stmtTypeInfo.setAllowedStatementTypes(stmtIds);
        
        // statement type header is no longer defined in specification
//        stmtTypeInfo.setHeaders(toStatementTypeHeaderTemplateInfos(entity.getHeaders()));
        
        stmtTypeInfo.setDescr(entity.getDescr());
        
        return stmtTypeInfo;
    }
    
    public StatementInfo toStatementInfo(final StatementTreeViewInfo statementTreeViewInfo) {
        StatementInfo statementInfo = null;
        if (statementTreeViewInfo == null) return null;
        statementInfo = new StatementInfo();
        statementInfo.setAttributes(statementTreeViewInfo.getAttributes());
        statementInfo.setDesc(statementTreeViewInfo.getDesc());
        statementInfo.setId(statementTreeViewInfo.getId());
        statementInfo.setMetaInfo(statementTreeViewInfo.getMetaInfo());
        statementInfo.setName(statementTreeViewInfo.getName());
        statementInfo.setOperator(statementTreeViewInfo.getOperator());
        statementInfo.setParentId(statementTreeViewInfo.getParentId());
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
     * copies the values in statementInfo into statementTreeViewInfo.  Only the values of the root statement will
     * be affected.
     * @param statementTreeViewInfo
     * @param statementInfo
     */
    public void copyValues(final StatementTreeViewInfo statementTreeViewInfo, StatementInfo statementInfo) {
        statementTreeViewInfo.setAttributes(statementInfo.getAttributes());
        statementTreeViewInfo.setDesc(statementInfo.getDesc());
        statementTreeViewInfo.setId(statementInfo.getId());
        statementTreeViewInfo.setMetaInfo(statementInfo.getMetaInfo());
        statementTreeViewInfo.setName(statementInfo.getName());
        statementTreeViewInfo.setOperator(statementInfo.getOperator());
        statementTreeViewInfo.setState(statementInfo.getState());
        statementTreeViewInfo.setType(statementInfo.getType());
    }
    
    public Statement toStatement(final StatementTreeViewInfo statementTreeViewInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, VersionMismatchException {
    	return toCustomLuStatementInfo(statementTreeViewInfo);
    }

	/**
	 * <p>Converts a {@link LuNlStatementInfo} into a {@link CustomReqComponentInfo}.</p>
	 * <p>Note: LuNlStatementInfo has no id since it is only used for 
	 * on-the-fly translations and is not persisted.</p>
	 * 
	 * @param statementInfo LuNlStatementInfo to convert
	 * @return A custom LU statement
	 * @throws VersionMismatchException 
	 * @throws Exception If conversion fails
	 */
	public Statement toCustomLuStatementInfo(final StatementTreeViewInfo statementInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, VersionMismatchException {
		Statement stmt = new Statement();
        stmt.setName(statementInfo.getName());
        stmt.setOperator(statementInfo.getOperator());
		//TODO: Fix with LuService RC1.4 changes
        //LuStatementTypeInfo stmtType = this.luService.getLuStatementType(statementInfo.getStatementTypeId());
        //stmt.setLuStatementType(stmtType);
		StatementType type = this.statementDao.fetch(StatementType.class, statementInfo.getType());
		stmt.setStatementType(type);
        
        if(statementInfo.getStatements() == null || statementInfo.getStatements().isEmpty()) {
        	setReqComponentType(statementInfo.getReqComponents());
        	List<ReqComponent> customReqList = toReqComponents(statementInfo.getReqComponents());
        	stmt.setRequiredComponents(customReqList);
        } else {
            createStatement(statementInfo, stmt);
        }

        return stmt;
	}

	private void setReqComponentType(List<ReqComponentInfo> reqList) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		for(ReqComponentInfo req : reqList) {
			setReqComponentType(req);
		}
	}

	private void setReqComponentType(ReqComponentInfo req) 
			throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		if(req.getRequiredComponentType() == null) {
			//TODO: Fix with LuService RC1.4 changes			
			ReqComponentType type = this.statementDao.fetch(ReqComponentType.class, req.getType());
			req.setRequiredComponentType(toReqComponentTypeInfo(type));
		}
	}
	
	private void createStatement(final StatementTreeViewInfo stmtInfo, Statement rootLuStatement) 
			throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, VersionMismatchException {
		for(StatementTreeViewInfo luNlStmt : stmtInfo.getStatements()) {
			Statement stmt = new Statement();
	        stmt.setName(luNlStmt.getName());
	        stmt.setParent(rootLuStatement);
	        stmt.setOperator(luNlStmt.getOperator());
			if(luNlStmt.getType() != null) {
				//TODO: Fix with LuService RC1.4 changes				
		        //stmt.setLuStatementType(this.luService.getLuStatementType(luNlStmt.getStatementTypeId()));
				StatementType type = this.statementDao.fetch(StatementType.class, luNlStmt.getType());
				stmt.setStatementType(type);
			}

	        if(rootLuStatement.getChildren() == null) {
	        	rootLuStatement.setChildren(new ArrayList<Statement>());
	        }
	        rootLuStatement.getChildren().add(stmt);
	        if (luNlStmt.getStatements() == null || luNlStmt.getStatements().isEmpty()) {
	            List<ReqComponentInfo> children = luNlStmt.getReqComponents();
	        	//setReqComponentType(children);
	        	List<ReqComponent> customReqList = toReqComponents(children);
	            stmt.setRequiredComponents(customReqList);
	        } else {
	            createStatement(luNlStmt, stmt);
	        }
	    }
	}
	
	private List<ReqComponent> toReqComponents(List<ReqComponentInfo> reqComponentInfoList) throws DoesNotExistException, VersionMismatchException, InvalidParameterException {
		List<ReqComponent> list = new ArrayList<ReqComponent>();
		for(ReqComponentInfo info : reqComponentInfoList) {
			ReqComponent req = toReqComponentRelation(false, info);
			list.add(req);
		}
		return list;
	}
}
