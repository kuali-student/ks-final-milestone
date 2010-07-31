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
import java.util.List;

import org.kuali.student.core.dictionary.dto.FieldDescriptor;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.dto.TimeAmountInfo;
import org.kuali.student.core.entity.RichText;
import org.kuali.student.core.entity.TimeAmount;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.core.service.impl.BaseAssembler;
import org.kuali.student.lum.lu.dao.LuDao;
import org.kuali.student.lum.lu.dto.AccreditationInfo;
import org.kuali.student.lum.lu.dto.AdminOrgInfo;
import org.kuali.student.lum.lu.dto.CluAccountingInfo;
import org.kuali.student.lum.lu.dto.CluCluRelationInfo;
import org.kuali.student.lum.lu.dto.CluCreditInfo;
import org.kuali.student.lum.lu.dto.CluFeeInfo;
import org.kuali.student.lum.lu.dto.CluIdentifierInfo;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.dto.CluInstructorInfo;
import org.kuali.student.lum.lu.dto.CluPublishingInfo;
import org.kuali.student.lum.lu.dto.CluSetInfo;
import org.kuali.student.lum.lu.dto.LrTypeInfo;
import org.kuali.student.lum.lu.dto.LuCodeInfo;
import org.kuali.student.lum.lu.dto.LuDocRelationInfo;
import org.kuali.student.lum.lu.dto.LuDocRelationTypeInfo;
import org.kuali.student.lum.lu.dto.LuLuRelationTypeInfo;
import org.kuali.student.lum.lu.dto.LuStatementInfo;
import org.kuali.student.lum.lu.dto.LuStatementTypeHeaderTemplateInfo;
import org.kuali.student.lum.lu.dto.LuStatementTypeInfo;
import org.kuali.student.lum.lu.dto.LuTypeInfo;
import org.kuali.student.lum.lu.dto.LuiInfo;
import org.kuali.student.lum.lu.dto.LuiLuiRelationInfo;
import org.kuali.student.lum.lu.dto.ReqCompFieldInfo;
import org.kuali.student.lum.lu.dto.ReqCompFieldTypeInfo;
import org.kuali.student.lum.lu.dto.ReqComponentInfo;
import org.kuali.student.lum.lu.dto.ReqComponentTypeInfo;
import org.kuali.student.lum.lu.dto.ReqComponentTypeNLTemplateInfo;
import org.kuali.student.lum.lu.entity.Clu;
import org.kuali.student.lum.lu.entity.CluAcademicSubjectOrg;
import org.kuali.student.lum.lu.entity.CluAccounting;
import org.kuali.student.lum.lu.entity.CluAccreditation;
import org.kuali.student.lum.lu.entity.CluAdminOrg;
import org.kuali.student.lum.lu.entity.CluAtpTypeKey;
import org.kuali.student.lum.lu.entity.CluCampusLocation;
import org.kuali.student.lum.lu.entity.CluCluRelation;
import org.kuali.student.lum.lu.entity.CluCredit;
import org.kuali.student.lum.lu.entity.CluFee;
import org.kuali.student.lum.lu.entity.CluIdentifier;
import org.kuali.student.lum.lu.entity.CluInstructor;
import org.kuali.student.lum.lu.entity.CluOrg;
import org.kuali.student.lum.lu.entity.CluPublishing;
import org.kuali.student.lum.lu.entity.CluSet;
import org.kuali.student.lum.lu.entity.LrType;
import org.kuali.student.lum.lu.entity.LuCode;
import org.kuali.student.lum.lu.entity.LuDocumentRelation;
import org.kuali.student.lum.lu.entity.LuDocumentRelationType;
import org.kuali.student.lum.lu.entity.LuLuRelationType;
import org.kuali.student.lum.lu.entity.LuStatement;
import org.kuali.student.lum.lu.entity.LuStatementAttribute;
import org.kuali.student.lum.lu.entity.LuStatementType;
import org.kuali.student.lum.lu.entity.LuStatementTypeHeaderTemplate;
import org.kuali.student.lum.lu.entity.LuType;
import org.kuali.student.lum.lu.entity.Lui;
import org.kuali.student.lum.lu.entity.LuiAttribute;
import org.kuali.student.lum.lu.entity.LuiLuiRelation;
import org.kuali.student.lum.lu.entity.ReqComponent;
import org.kuali.student.lum.lu.entity.ReqComponentField;
import org.kuali.student.lum.lu.entity.ReqComponentFieldType;
import org.kuali.student.lum.lu.entity.ReqComponentType;
import org.kuali.student.lum.lu.entity.ReqComponentTypeNLTemplate;
import org.kuali.student.lum.nlt.dto.LuNlStatementInfo;
import org.springframework.beans.BeanUtils;

public class LuServiceAssembler extends BaseAssembler {

    public static List<CluCluRelationInfo> toCluCluRelationInfos(
            List<CluCluRelation> entities) {
    	if(entities==null){
    		return null;
    	}
        List<CluCluRelationInfo> dtos = new ArrayList<CluCluRelationInfo>(
                entities.size());
        for (CluCluRelation entity : entities) {
            dtos.add(toCluCluRelationInfo(entity));
        }
        return dtos;

    }

    public static CluCluRelationInfo toCluCluRelationInfo(CluCluRelation entity) {
        if(entity==null){
            return null;
        }
        CluCluRelationInfo dto = new CluCluRelationInfo();
        BeanUtils.copyProperties(entity, dto,
                new String[] { "cluId", "relatedCluId", "cluRelationRequired",
                "attributes", "metaInfo" });

        dto.setIsCluRelationRequired(entity.isCluRelationRequired());
        dto.setCluId(entity.getClu().getId());
        dto.setRelatedCluId(entity.getRelatedClu().getId());
        dto.setType(entity.getLuLuRelationType().getId());
        dto.setAttributes(toAttributeMap(entity.getAttributes()));
        dto.setMetaInfo(toMetaInfo(entity.getMeta(), entity.getVersionInd()));

        return dto;

    }

    public static List<CluInfo> toCluInfos(List<Clu> entities) {
    	if(entities==null){
    		return null;
    	}
        List<CluInfo> dtos = new ArrayList<CluInfo>(entities.size());
        for (Clu entity : entities) {
            dtos.add(toCluInfo(entity));
        }
        return dtos;

    }

    public static CluInfo toCluInfo(Clu entity) {
        if(entity==null){
            return null;
        }
        CluInfo dto = new CluInfo();

        BeanUtils.copyProperties(entity, dto,
                new String[] { "officialIdentifier", "alternateIdentifiers",
                "desc", "marketingDesc", "participatingOrgs", "primaryInstructor",
                "instructors", "stdDuration", "luCodes", "credit", "publishing",
                "offeredAtpTypes", "fee", "accounting",
                "intensity", "academicSubjectOrgs","campusLocationList", "accreditationList",
                "primaryAdminOrg", "alternateAdminOrgs", 
                "attributes", "metaInfo" });
        dto.setOfficialIdentifier(toCluIdentifierInfo(entity.getOfficialIdentifier()));
        dto.setAlternateIdentifiers(toCluIdentifierInfos(entity.getAlternateIdentifiers()));
        dto.setDesc(toRichTextInfo(entity.getDesc()));
        dto.setMarketingDesc(toRichTextInfo(entity.getMarketingDesc()));
        // accreditingOrg Deprecated in v  1.0-rc2 Replaced by Primary and Alternate admin orgs
        dto.setAccreditingOrg(entity.getAccreditingOrg());
        dto.setAccreditationList(toAccreditationInfos(entity.getAccreditationList()));

        // adminOrg & participatingOrgs deprecated in v  1.0-rc2 Replaced by Primary and Alternate admin orgs
        dto.setAdminOrg(entity.getAdminOrg());
        if(entity.getParticipatingOrgs()!=null){
	        List<String> participatingOrgs = new ArrayList<String>(entity.getParticipatingOrgs().size());
	        for (CluOrg cluOrg : entity.getParticipatingOrgs()) {
	            participatingOrgs.add(cluOrg.getOrgId());
	        }
	        dto.setParticipatingOrgs(participatingOrgs);              
        }
        
        dto.setPrimaryAdminOrg(toAdminOrgInfo(entity.getPrimaryAdminOrg()));
        dto.setAlternateAdminOrgs(toCluAdminOrgInfos(entity.getAlternateAdminOrgs()));
        
        dto.setPrimaryInstructor(toCluInstructorInfo(entity.getPrimaryInstructor()));
        dto.setInstructors(toCluInstructorInfos(entity.getInstructors()));
        dto.setStdDuration(toTimeAmountInfo(entity.getStdDuration()));
        dto.setLuCodes(toLuCodeInfos(entity.getLuCodes()));
        
        dto.setCreditInfo(toCluCreditInfos(entity.getCredit()));
        dto.setPublishingInfo(toCluPublishingInfo(entity.getPublishing()));

        if(entity.getOfferedAtpTypes()!=null){
	        List<String> offeredAtpTypes = new ArrayList<String>(entity.getOfferedAtpTypes().size());
	        for (CluAtpTypeKey key : entity.getOfferedAtpTypes()) {
	            offeredAtpTypes.add(key.getAtpTypeKey());
	        }
	        dto.setOfferedAtpTypes(offeredAtpTypes);
        }
        
        dto.setFeeInfo(toCluFeeInfo(entity.getFee()));
        dto.setAccountingInfo(toCluAccountingInfo(entity.getAccounting()));

        dto.setAttributes(toAttributeMap(entity.getAttributes()));
        dto.setMetaInfo(toMetaInfo(entity.getMeta(), entity.getVersionInd()));

        dto.setType(entity.getLuType().getId());

        if(entity.getAcademicSubjectOrgs()!=null){
	        List<String> academicSubjectOrgs = new ArrayList<String>(entity.getAcademicSubjectOrgs().size());
	        for (CluAcademicSubjectOrg cluOrg : entity.getAcademicSubjectOrgs()) {
	            academicSubjectOrgs.add(cluOrg.getOrgId());
	        }
	        dto.setAcademicSubjectOrgs(academicSubjectOrgs);
        }
        
        if(entity.getCampusLocationList()!=null){
	        List<String> campusLocations = new ArrayList<String>(entity.getCampusLocationList().size());
	        for (CluCampusLocation cluCamp : entity.getCampusLocationList()) {
	            campusLocations.add(cluCamp.getCampusLocation());
	        }
	        dto.setCampusLocationList(campusLocations);
        }
        
        dto.setIntensity(toTimeAmountInfo(entity.getIntensity()));

        return dto;

    }

    public static List<CluSetInfo> toCluSetInfos(List<CluSet> entities) {
    	if(entities==null){
    		return null;
    	}
        List<CluSetInfo> dtos = new ArrayList<CluSetInfo>(entities.size());
        for (CluSet entity : entities) {
            dtos.add(toCluSetInfo(entity));
        }
        return dtos;

    }

    public static CluSetInfo toCluSetInfo(CluSet entity) {
        if(entity==null){
            return null;
        }
        CluSetInfo dto = new CluSetInfo();

        BeanUtils.copyProperties(entity, dto, new String[] { "desc",
                "cluCriteria", "cluSets", "clus", "attributes", "metaInfo" });

        dto.setDesc(toRichTextInfo(entity.getDesc()));
        // TODO dto.setCluCriteria()
        List<String> cluSetIds = new ArrayList<String>(entity.getCluSets()
                .size());
        for (CluSet id : entity.getCluSets()) {
            cluSetIds.add(id.getId());
        }
        dto.setCluSetIds(cluSetIds);

        List<String> cluIds = new ArrayList<String>(entity.getClus().size());
        for (Clu id : entity.getClus()) {
            cluIds.add(id.getId());
        }
        dto.setCluIds(cluIds);

        dto.setAttributes(toAttributeMap(entity.getAttributes()));
        dto.setMetaInfo(toMetaInfo(entity.getMeta(), entity.getVersionInd()));

        return dto;

    }

    public static List<LrTypeInfo> toLrTypeInfos(List<LrType> entities) {
    	if(entities==null){
    		return null;
    	}
        List<LrTypeInfo> dtos = new ArrayList<LrTypeInfo>(entities.size());
        for (LrType entity : entities) {
            dtos.add(toLrTypeInfo(entity));
        }
        return dtos;

    }

    public static LrTypeInfo toLrTypeInfo(LrType entity) {
        if(entity==null){
            return null;
        }
        LrTypeInfo dto = new LrTypeInfo();

        BeanUtils.copyProperties(entity, dto,
                new String[] {"attributes" });

        dto.setAttributes(toAttributeMap(entity.getAttributes()));

        return dto;

    }

    public static List<LuDocRelationTypeInfo> toLuDocRelationType(
            List<LuDocumentRelationType> entities) {
    	if(entities==null){
    		return null;
    	}
        List<LuDocRelationTypeInfo> dtos = new ArrayList<LuDocRelationTypeInfo>(
                entities.size());
        for (LuDocumentRelationType entity : entities) {
            dtos.add(toLuDocRelationTypeInfo(entity));
        }
        return dtos;

    }

    public static LuDocRelationInfo toLuDocRelationInfo(
            LuDocumentRelation entity) {
        if(entity==null){
            return null;
        }
        LuDocRelationInfo dto = new LuDocRelationInfo();

        BeanUtils.copyProperties(entity, dto, new String[] { "desc",
                "luDocumentRelationType", "attributes", "metInfo" });

        dto.setCluId(entity.getClu().getId());
        dto.setDesc(toRichTextInfo(entity.getDesc()));
        dto.setType(entity.getLuDocumentRelationType().getId());
        dto.setAttributes(toAttributeMap(entity.getAttributes()));
        dto.setMetaInfo(toMetaInfo(entity.getMeta(), entity.getVersionInd()));

        return dto;
    }

    public static List<LuDocRelationInfo> toLuDocRelationInfos(
            List<LuDocumentRelation> entities) {
    	if(entities==null){
    		return null;
    	}
        List<LuDocRelationInfo> dtos = new ArrayList<LuDocRelationInfo>(
                entities.size());
        for (LuDocumentRelation entity : entities) {
            dtos.add(toLuDocRelationInfo(entity));
        }
        return dtos;
    }

    public static List<LuDocRelationTypeInfo> toLuDocRelationTypeInfos(
            List<LuDocumentRelationType> entities) {
    	if(entities==null){
    		return null;
    	}
        List<LuDocRelationTypeInfo> dtos = new ArrayList<LuDocRelationTypeInfo>(
                entities.size());
        for (LuDocumentRelationType entity : entities) {
            dtos.add(toLuDocRelationTypeInfo(entity));
        }
        return dtos;

    }

    public static List<LuDocRelationTypeInfo> toLuDocumentRelationTypes(
            List<LuDocumentRelationType> entities) {
    	if(entities==null){
    		return null;
    	}
        List<LuDocRelationTypeInfo> dtos = new ArrayList<LuDocRelationTypeInfo>(
                entities.size());
        for (LuDocumentRelationType entity : entities) {
            dtos.add(toLuDocRelationTypeInfo(entity));
        }
        return dtos;
    }

    public static LuDocRelationTypeInfo toLuDocRelationTypeInfo(
            LuDocumentRelationType entity) {
        if(entity==null){
            return null;
        }
        LuDocRelationTypeInfo dto = new LuDocRelationTypeInfo();

        BeanUtils.copyProperties(entity, dto,
                new String[] { "attributes" });

        dto.setAttributes(toAttributeMap(entity.getAttributes()));

        return dto;
    }

    public static List<LuLuRelationTypeInfo> toLuLuRelationTypeInfos(
            List<LuLuRelationType> entities) {
        List<LuLuRelationTypeInfo> dtos = new ArrayList<LuLuRelationTypeInfo>(
                entities.size());
        for (LuLuRelationType entity : entities) {
            dtos.add(toLuLuRelationTypeInfo(entity));
        }
        return dtos;

    }

    public static LuLuRelationTypeInfo toLuLuRelationTypeInfo(
            LuLuRelationType entity) {
        if(entity==null){
            return null;
        }
        LuLuRelationTypeInfo dto = new LuLuRelationTypeInfo();

        BeanUtils.copyProperties(entity, dto,
                new String[] { "attributes" });

        dto.setAttributes(toAttributeMap(entity.getAttributes()));

        return dto;
    }

    public static List<LuStatementInfo> toLuStatementInfos(
            List<LuStatement> entities) {
        List<LuStatementInfo> dtos = new ArrayList<LuStatementInfo>(entities
                .size());
        for (LuStatement entity : entities) {
            dtos.add(toLuStatementInfo(entity));
        }
        return dtos;

    }

    public static LuStatementInfo toLuStatementInfo(LuStatement entity) {
        if(entity==null){
            return null;
        }
        LuStatementInfo dto = new LuStatementInfo();

        BeanUtils.copyProperties(entity, dto, new String[] { "parent", "children",
                "requiredComponents", "luStatementType", "attributes", "metaInfo" });

		if(entity.getParent() != null) {
			dto.setParentId(entity.getParent().getId());
		}
        List<String> statementIds = new ArrayList<String>(entity.getChildren().size());
        for (LuStatement statement : entity.getChildren()) {
            statementIds.add(statement.getId());
        }
        dto.setLuStatementIds(statementIds);

        List<String> componentIds = new ArrayList<String>(entity.getRequiredComponents().size());
        for (ReqComponent reqComponent : entity.getRequiredComponents()) {
            componentIds.add(reqComponent.getId());
        }
        dto.setReqComponentIds(componentIds);
        dto.setType(entity.getLuStatementType().getId());
        dto.setAttributes(toAttributeMap(entity.getAttributes()));
        dto.setMetaInfo(toMetaInfo(entity.getMeta(), entity.getVersionInd()));

        dto.setLuStatementType(toLuStatementTypeInfo(entity.getLuStatementType()));
        dto.setName(entity.getName());
        dto.setOperator(entity.getOperator());
        
        return dto;
    }

    public static List<LuStatementTypeInfo> toLuStatementTypeInfos(List<LuStatementType> entities) {
        List<LuStatementTypeInfo> dtos = new ArrayList<LuStatementTypeInfo>(entities.size());
        for (LuStatementType entity : entities) {
            dtos.add(toLuStatementTypeInfo(entity));
        }
        return dtos;

    }

    public static LuStatement toLuStatementRelation(boolean isUpdate, LuStatementInfo stmtInfo, LuDao dao) throws DoesNotExistException, VersionMismatchException, InvalidParameterException, OperationFailedException {
        LuStatement stmt;
        if (isUpdate) {
            stmt = dao.fetch(LuStatement.class, stmtInfo.getId());
            if (stmt == null) {
                throw new DoesNotExistException("LuStatement does not exist for id: " + stmtInfo.getId());
            }
            if (!String.valueOf(stmt.getVersionInd()).equals(stmtInfo.getMetaInfo().getVersionInd())) {
                throw new VersionMismatchException("LuStatement to be updated is not the current version");
            }
        } else {
            stmt = new LuStatement();
        }

        BeanUtils.copyProperties(stmtInfo, stmt, new String[]{"luStatementIds", 
        		"reqComponentIds", "attributes", "metaInfo", "type", 
        		"parent", "children", "requiredComponents", "luStatementType"});

        // Copy generic attributes
        stmt.setAttributes(toGenericAttributes(LuStatementAttribute.class, stmtInfo.getAttributes(), stmt, dao));

        // Search for and copy the type
        LuStatementType stmtType = dao.fetch(LuStatementType.class, stmtInfo.getType());
        if (stmtType == null) {
            throw new InvalidParameterException(
                    "LuStatementType does not exist for id: " + stmtInfo.getType());
        }
        stmt.setLuStatementType(stmtType);

        // Copy nested statements
        List<LuStatement> stmtList = new ArrayList<LuStatement>();
        for(String stmtId : stmtInfo.getLuStatementIds()) {
            if(stmtId == stmtInfo.getId()) {
                throw new OperationFailedException("LuStatement nested within itself. LuStatement Id: " + stmtInfo.getId());
            }

            LuStatement nestedStmt = dao.fetch(LuStatement.class, stmtId);
            if (null == nestedStmt) {
                throw new DoesNotExistException("Nested LuStatement does not exist for id: " + stmtId + ". Parent LuStatement: " + stmtInfo.getId());
            }

            stmtList.add(nestedStmt);
        }
        stmt.setChildren(stmtList);

        // Copy nested requirements
        List<ReqComponent> reqCompList = new ArrayList<ReqComponent>();
        for(String reqId: stmtInfo.getReqComponentIds()) {
            ReqComponent reqComp = dao.fetch(ReqComponent.class, reqId);

            if(null == reqComp) {
                throw new DoesNotExistException("Nested Requirement does not exist for id: " + reqId + ". Parent LuStatement Id: " + stmtInfo.getId());
            }

            reqCompList.add(reqComp);
        }
        stmt.setRequiredComponents(reqCompList);
        
        if(stmtInfo.getParentId() != null) {
	        LuStatement parent = dao.fetch(LuStatement.class, stmtInfo.getParentId());
	        stmt.setParent(parent);
        }

        return stmt;
    }

    public static LuStatement toLuStatementRelation(LuNlStatementInfo stmtInfo, LuDao luDao) throws DoesNotExistException, VersionMismatchException, InvalidParameterException, OperationFailedException {
        LuStatement stmt = new LuStatement();
        stmt.setName(stmtInfo.getName());
        stmt.setOperator(stmtInfo.getOperator());

        LuStatementType stmtType = luDao.fetch(LuStatementType.class, stmtInfo.getStatementTypeId());
        stmt.setLuStatementType(stmtType);

        if(stmtInfo.getChildren() == null || stmtInfo.getChildren().isEmpty()) {
            List<ReqComponent> reqCompList = new ArrayList<ReqComponent>();
            for(ReqComponentInfo rc : stmtInfo.getRequiredComponents()) {
                ReqComponent reqComp = toReqComponentRelation(false, rc, luDao);
                reqCompList.add(reqComp);
            }
            stmt.setRequiredComponents(reqCompList);
        } else {
        	createLuStatement(stmtInfo, stmt, luDao);
        }

        return stmt;
    }

    private static void createLuStatement(LuNlStatementInfo stmtInfo, LuStatement rootLuStatement, LuDao luDao) throws DoesNotExistException, VersionMismatchException, InvalidParameterException {
    	for(LuNlStatementInfo luNlStmt : stmtInfo.getChildren()) {
            LuStatement stmt = new LuStatement();
            stmt.setName(luNlStmt.getName());
            stmt.setParent(rootLuStatement);
            stmt.setOperator(luNlStmt.getOperator());
	        if(rootLuStatement.getChildren() == null) {
	        	rootLuStatement.setChildren(new ArrayList<LuStatement>());
	        }
            rootLuStatement.getChildren().add(stmt);
            if (luNlStmt.getChildren() == null || luNlStmt.getChildren().isEmpty()) {
                List<ReqComponent> children = getReqComponents(luNlStmt.getRequiredComponents(), luDao);
                stmt.setRequiredComponents(children);
            } else {
            	createLuStatement(luNlStmt, stmt, luDao);
            }
        }
    }

    private static List<ReqComponent> getReqComponents(List<ReqComponentInfo> reqComponentInfoList, LuDao luDao) throws DoesNotExistException, VersionMismatchException, InvalidParameterException {
        List<ReqComponent> list = new ArrayList<ReqComponent>();
        for(ReqComponentInfo reqCompInfo : reqComponentInfoList) {
            ReqComponent reqComp = toReqComponentRelation(false, reqCompInfo, luDao);
            list.add(reqComp);
        }
        return list;
    }
    
    public static LuStatementTypeInfo toLuStatementTypeInfo(LuStatementType entity) {
    	if(entity==null){
    		return null;
    	}
        LuStatementTypeInfo stmtTypeInfo = toGenericTypeInfo(LuStatementTypeInfo.class, entity);

        // Copy allowed RequiredComponent Types
        List<String> reqTypeIds = new ArrayList<String>(entity.getAllowedReqComponentTypes().size());
        for (ReqComponentType reqComponentType : entity.getAllowedReqComponentTypes()) {
            reqTypeIds.add(reqComponentType.getId());
        }
        stmtTypeInfo.setAllowedReqComponentTypes(reqTypeIds);

        // Copy allowed LuStatement Types
        List<String> luStmtIds = new ArrayList<String>(entity.getAllowedLuStatementTypes().size());
        for (LuStatementType stmtType : entity.getAllowedLuStatementTypes()) {
            luStmtIds.add(stmtType.getId());
        }
        stmtTypeInfo.setAllowedLuStatementTypes(luStmtIds);
        
        stmtTypeInfo.setHeaders(toLuStatementTypeHeaderTemplateInfos(entity.getHeaders()));

        return stmtTypeInfo;
    }
    
    public static LuStatementTypeHeaderTemplateInfo toLuStatementTypeHeaderTemplateInfo(LuStatementTypeHeaderTemplate template) {
    	LuStatementTypeHeaderTemplateInfo templateInfo = new LuStatementTypeHeaderTemplateInfo();
    	templateInfo.setLanguage(template.getLanguage());
    	templateInfo.setNlUsageTypeKey(template.getNlUsageTypeKey());
    	templateInfo.setTemplate(template.getTemplate());
    	
    	return templateInfo;
    }

    public static List<LuStatementTypeHeaderTemplateInfo> toLuStatementTypeHeaderTemplateInfos(List<LuStatementTypeHeaderTemplate> templateList) {
    	List<LuStatementTypeHeaderTemplateInfo> list = new ArrayList<LuStatementTypeHeaderTemplateInfo>(templateList.size());
    	for(LuStatementTypeHeaderTemplate template : templateList) {
    		list.add(toLuStatementTypeHeaderTemplateInfo(template));
    	}
    	return list;
    }
    
    public static List<LuTypeInfo> toLuTypeInfos(List<LuType> entities) {
    	if(entities==null){
    		return null;
    	}
        List<LuTypeInfo> dtos = new ArrayList<LuTypeInfo>(entities.size());
        for (LuType entity : entities) {
            dtos.add(toLuTypeInfo(entity));
        }
        return dtos;
    }

    public static LuTypeInfo toLuTypeInfo(LuType entity) {
        if(entity==null){
            return null;
        }
        LuTypeInfo dto = new LuTypeInfo();

        BeanUtils.copyProperties(entity, dto,
                new String[] { "attributes" });

        dto.setAttributes(toAttributeMap(entity.getAttributes()));

        return dto;
    }

    public static List<LuiInfo> toLuiInfos(List<Lui> entities) {
    	if(entities==null){
    		return null;
    	}
        List<LuiInfo> dtos = new ArrayList<LuiInfo>(entities.size());
        for (Lui entity : entities) {
            dtos.add(toLuiInfo(entity));
        }
        return dtos;

    }

    public static LuiInfo toLuiInfo(Lui entity) {
        if(entity==null){
            return null;
        }
        LuiInfo luiInfo = new LuiInfo();

        BeanUtils.copyProperties(entity, luiInfo, new String[] { "clu", "metaInfo", "attributes" });

        luiInfo.setCluId(entity.getClu().getId());

        luiInfo.setMetaInfo(toMetaInfo(entity.getMeta(), entity.getVersionInd()));

        luiInfo.setAttributes(toAttributeMap(entity.getAttributes()));

        return luiInfo;
    }

    public static Lui toLui(boolean isUpdate, LuiInfo luiInfo, LuDao dao) throws DoesNotExistException, VersionMismatchException, InvalidParameterException {
        if(luiInfo==null){
            return null;
        }
        Lui lui;

        if (isUpdate) {
            lui = (Lui) dao.fetch(Lui.class, luiInfo.getId());
            if(null == lui) {
                throw new DoesNotExistException((new StringBuilder()).append("Lui does not exist for id: ").append(luiInfo.getId()).toString());
            }
            if(!String.valueOf(lui.getVersionInd()).equals(luiInfo.getMetaInfo().getVersionInd())) {
                throw new VersionMismatchException("Lui to be updated is not the current version");
            }
        } else
        {
            lui = new Lui();
        }

        BeanUtils.copyProperties(luiInfo, lui, new String[] { "cluId", "attributes", "metaInfo" });

        lui.setAttributes(toGenericAttributes(LuiAttribute.class, luiInfo.getAttributes(), lui, dao));

        Clu clu = (Clu) dao.fetch(Clu.class, luiInfo.getCluId());
        if(null == clu) {
            throw new InvalidParameterException((new StringBuilder()).append("Clu does not exist for id: ").append(luiInfo.getCluId()).toString());
        }
        lui.setClu(clu);
        return lui;
    }


    public static List<LuiLuiRelationInfo> toLuiLuiRelationInfos(
            List<LuiLuiRelation> entities) {
    	if(entities==null){
    		return null;
    	}
        List<LuiLuiRelationInfo> dtos = new ArrayList<LuiLuiRelationInfo>(
                entities.size());
        for (LuiLuiRelation entity : entities) {
            dtos.add(toLuiLuiRelationInfo(entity));
        }
        return dtos;

    }

    public static LuiLuiRelationInfo toLuiLuiRelationInfo(LuiLuiRelation entity) {
        if(entity==null){
            return null;
        }
        LuiLuiRelationInfo dto = new LuiLuiRelationInfo();

        BeanUtils.copyProperties(entity, dto, new String[] { "lui",
                "relatedLui", "attributes" });

        dto.setLuiId(entity.getLui().getId());
        dto.setRelatedLuiId(entity.getRelatedLui().getId());
        dto.setType(entity.getLuLuRelationType().getId());
        dto.setAttributes(toAttributeMap(entity.getAttributes()));
        dto.setMetaInfo(toMetaInfo(entity.getMeta(), entity.getVersionInd()));
        return dto;
    }

    public static List<ReqComponentInfo> toReqComponentInfos(
            List<ReqComponent> entities) {
        List<ReqComponentInfo> dtos = new ArrayList<ReqComponentInfo>(
                entities.size());
        for (ReqComponent entity : entities) {
            dtos.add(toReqComponentInfo(entity));
        }
        return dtos;

    }

    public static ReqComponentInfo toReqComponentInfo(ReqComponent entity) {
        ReqComponentInfo dto = new ReqComponentInfo();

        BeanUtils.copyProperties(entity, dto, new String[] {
                "requiredComponentType", "reqCompField", "metaInfo" });

        dto.setType(entity.getRequiredComponentType().getId());
        dto.setReqCompFields(toReqCompFieldInfos(entity.getReqCompField()));
        dto.setRequiredComponentType(toReqComponentTypeInfo(entity.getRequiredComponentType()));
        dto.setMetaInfo(toMetaInfo(entity.getMeta(), entity.getVersionInd()));

        return dto;
    }

    public static ReqComponent toReqComponentRelation(boolean isUpdate,
            ReqComponentInfo reqCompInfo, LuDao dao) throws DoesNotExistException, VersionMismatchException, InvalidParameterException {
        ReqComponent reqComp;
        if (isUpdate) {
            reqComp = dao.fetch(ReqComponent.class, reqCompInfo.getId());
            if (reqComp == null) {
                throw new DoesNotExistException("ReqComponent does not exist for id: " + reqCompInfo.getId());
            }
            if (!String.valueOf(reqComp.getVersionInd()).equals(reqCompInfo.getMetaInfo().getVersionInd())) {
                throw new VersionMismatchException("ReqComponent to be updated is not the current version");
            }
            for(ReqComponentField reqCompField : reqComp.getReqCompField()) {
                dao.delete(reqCompField);
            }
        } else {
            reqComp = new ReqComponent();
        }

        BeanUtils.copyProperties(reqCompInfo, reqComp, new String[]{"requiredComponentType", "reqCompField", "metaInfo", "type"});

        // Search for and copy the type
        ReqComponentType reqCompType = dao.fetch(ReqComponentType.class, reqCompInfo.getType());
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
        reqComp.setReqCompField(reqCompFieldList);

        return reqComp;
    }

    public static List<ReqComponentTypeInfo> toReqComponentTypeInfos(List<ReqComponentType> entities) {
        List<ReqComponentTypeInfo> dtos = new ArrayList<ReqComponentTypeInfo>(entities.size());
        for (ReqComponentType entity : entities) {
            dtos.add(toReqComponentTypeInfo(entity));
        }
        return dtos;

    }

    public static ReqComponentTypeInfo toReqComponentTypeInfo(ReqComponentType entity) {
        ReqComponentTypeInfo dto = toGenericTypeInfo(ReqComponentTypeInfo.class, entity);
        dto.setReqCompFieldTypeInfos(toReqCompFieldTypeInfos(entity.getReqCompFieldTypes()));
        dto.setNlUsageTemplates(toReqComponentTypeNLTemplateInfos(entity.getNlUsageTemplates()));
        return dto;
    }
    
    public static ReqComponentTypeNLTemplateInfo toReqComponentTypeNLTemplateInfo(ReqComponentTypeNLTemplate template) {
    	ReqComponentTypeNLTemplateInfo templateInfo = new ReqComponentTypeNLTemplateInfo();
    	templateInfo.setLanguage(template.getLanguage());
    	templateInfo.setNlUsageTypeKey(template.getNlUsageTypeKey());
    	templateInfo.setTemplate(template.getTemplate());
    	return templateInfo;
    }

    public static List<ReqComponentTypeNLTemplateInfo> toReqComponentTypeNLTemplateInfos(List<ReqComponentTypeNLTemplate> templateList) {
    	List<ReqComponentTypeNLTemplateInfo> list = new ArrayList<ReqComponentTypeNLTemplateInfo>(templateList.size());
    	for(ReqComponentTypeNLTemplate template : templateList) {
    		list.add(toReqComponentTypeNLTemplateInfo(template));
    	}
    	return list;
    }
    
    public static RichTextInfo toRichTextInfo(RichText entity) {
        if(entity==null){
            return null;
        }

        RichTextInfo dto = new RichTextInfo();

        BeanUtils.copyProperties(entity, dto, new String[] { "id" });

        return dto;

    }

    public static List<ReqCompFieldInfo> toReqCompFieldInfos(
            List<ReqComponentField> entities) {
        List<ReqCompFieldInfo> dtos = new ArrayList<ReqCompFieldInfo>(
                entities.size());
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
        dto.setId(entity.getKey());
        dto.setValue(entity.getValue());
        return dto;
    }

    public static List<ReqCompFieldTypeInfo> toReqCompFieldTypeInfos(
            List<ReqComponentFieldType> entities) {
        List<ReqCompFieldTypeInfo> dtos = new ArrayList<ReqCompFieldTypeInfo>(
                entities.size());
        for (ReqComponentFieldType entity : entities) {
            dtos.add(toReqCompFieldTypeInfo(entity));
        }
        return dtos;
    }


    public static ReqCompFieldTypeInfo toReqCompFieldTypeInfo(
            ReqComponentFieldType entity) {
        ReqCompFieldTypeInfo dto = new ReqCompFieldTypeInfo();

        BeanUtils.copyProperties(entity, dto, new String[] { "fieldDescriptor" });

        FieldDescriptor fDTO = new FieldDescriptor();
        BeanUtils.copyProperties(entity.getFieldDescriptor(), fDTO);

        dto.setFieldDescriptor(fDTO);

        return dto;
    }

    public static List<CluIdentifierInfo> toCluIdentifierInfos(List<CluIdentifier> entities) {
        List<CluIdentifierInfo> dtos = new ArrayList<CluIdentifierInfo>(entities.size());
        for (CluIdentifier entity : entities) {
            dtos.add(toCluIdentifierInfo(entity));
        }
        return dtos;
    }

    public static CluIdentifierInfo toCluIdentifierInfo(CluIdentifier entity) {
        if(entity==null){
            return null;
        }

        CluIdentifierInfo dto = new CluIdentifierInfo();

        BeanUtils.copyProperties(entity, dto);

        return dto;
    }

    public static List<CluInstructorInfo> toCluInstructorInfos(List<CluInstructor> entities) {
        List<CluInstructorInfo> dtos = new ArrayList<CluInstructorInfo>(entities.size());
        for (CluInstructor entity : entities) {
            dtos.add(toCluInstructorInfo(entity));
        }
        return dtos;
    }

    public static CluInstructorInfo toCluInstructorInfo(CluInstructor entity) {
        if(entity==null){
            return null;
        }
        CluInstructorInfo dto = new CluInstructorInfo();

        BeanUtils.copyProperties(entity, dto, new String[] { "id", "attributes" });
        dto.setAttributes(toAttributeMap(entity.getAttributes()));

        return dto;
    }

    public static TimeAmountInfo toTimeAmountInfo(TimeAmount entity) {
        if(entity==null){
            return null;
        }
        TimeAmountInfo dto = new TimeAmountInfo();

        BeanUtils.copyProperties(entity, dto);

        return dto;
    }

    public static List<LuCodeInfo> toLuCodeInfos(List<LuCode> entities) {
        List<LuCodeInfo> dtos = new ArrayList<LuCodeInfo>(entities.size());
        for (LuCode entity : entities) {
            dtos.add(toLuCodeInfo(entity));
        }
        return dtos;
    }

    public static LuCodeInfo toLuCodeInfo(LuCode entity) {
        if(entity==null){
            return null;
        }
        LuCodeInfo dto = new LuCodeInfo();

        BeanUtils.copyProperties(entity, dto, new String[] { "attributes", "metInfo" });

        dto.setAttributes(toAttributeMap(entity.getAttributes()));
        dto.setMetaInfo(toMetaInfo(entity.getMeta(), entity.getVersionInd()));

        return dto;
    }

    public static CluCreditInfo toCluCreditInfos(CluCredit entity) {
        if(entity==null){
            return null;
        }
        CluCreditInfo dto = new CluCreditInfo();

        BeanUtils.copyProperties(entity, dto, new String[] { "id", "repeatTime" , "minTimeToComplete", "maxTimeToComplete", "maxAllowableInactivity", "maxTimeResultsRecognized"});
        dto.setRepeatTime(toTimeAmountInfo(entity.getRepeatTime()));
        dto.setMinTimeToComplete(toTimeAmountInfo(entity.getMinTimeToComplete()));
        dto.setMaxTimeToComplete(toTimeAmountInfo(entity.getMaxTimeToComplete()));
        dto.setMaxAllowableInactivity(toTimeAmountInfo(entity.getMaxAllowableInactivity()));
        dto.setMaxTimeResultsRecognized(toTimeAmountInfo(entity.getMaxTimeResultsRecognized()));

        return dto;
    }

    public static CluPublishingInfo toCluPublishingInfo(CluPublishing entity) {
        if(entity==null){
            return null;
        }
        CluPublishingInfo dto = new CluPublishingInfo();

        BeanUtils.copyProperties(entity, dto, new String[] { "primaryInstructor", "instructors",
        "attributes" });
        dto.setPrimaryInstructor(toCluInstructorInfo(entity.getPrimaryInstructor()));
        dto.setInstructors(toCluInstructorInfos(entity.getInstructors()));
        dto.setAttributes(toAttributeMap(entity.getAttributes()));

        return dto;
    }

    public static CluFeeInfo toCluFeeInfo(CluFee entity) {
        if(entity==null){
            return null;
        }
        CluFeeInfo dto = new CluFeeInfo();

        dto.setAttributes(toAttributeMap(entity.getAttributes()));

        return dto;
    }

    public static CluAccountingInfo toCluAccountingInfo(CluAccounting entity) {
        if(entity==null){
            return null;
        }
        CluAccountingInfo dto = new CluAccountingInfo();

        dto.setAttributes(toAttributeMap(entity.getAttributes()));

        return dto;
    }

    public static TimeAmount toTimeAmount(TimeAmountInfo timeAmountInfo) {
        if(timeAmountInfo==null){
            return null;
        }
        TimeAmount timeAmount = new TimeAmount();
        BeanUtils.copyProperties(timeAmountInfo, timeAmount);
        return timeAmount;
    }

    public static CluCredit toCluCredit(CluCreditInfo cluCreditInfo) {
        if(cluCreditInfo==null){
            return null;
        }
        CluCredit cluCredit = new CluCredit();

        cluCredit.setMaxAllowableInactivity(LuServiceAssembler.toTimeAmount(cluCreditInfo.getMaxAllowableInactivity()));
        cluCredit.setMaxTimeResultsRecognized(LuServiceAssembler.toTimeAmount(cluCreditInfo.getMaxTimeResultsRecognized()));
        cluCredit.setMaxTimeToComplete(LuServiceAssembler.toTimeAmount(cluCreditInfo.getMaxTimeToComplete()));
        cluCredit.setMinTimeToComplete(LuServiceAssembler.toTimeAmount(cluCreditInfo.getMinTimeToComplete()));
        cluCredit.setRepeatTime(LuServiceAssembler.toTimeAmount(cluCreditInfo.getRepeatTime()));

        BeanUtils.copyProperties(cluCreditInfo,cluCredit,new String[]{"repeatTime","minTimeToComplete","maxTimeToComplete","maxAllowableInactivity","maxTimeResultsRecognized"});

        return cluCredit;
    }

    public static void copyCluCredit(CluCreditInfo cluCreditInfo, CluCredit entity) {
    	if(entity==null){
    		return;
    	}
    	if(entity.getMaxAllowableInactivity()==null){
            entity.setMaxAllowableInactivity(new TimeAmount());
        }
        BeanUtils.copyProperties(cluCreditInfo.getMaxAllowableInactivity(),entity.getMaxAllowableInactivity());

        if(entity.getMaxTimeResultsRecognized()==null){
            entity.setMaxTimeResultsRecognized(new TimeAmount());
        }
        BeanUtils.copyProperties(cluCreditInfo.getMaxTimeResultsRecognized(),entity.getMaxTimeResultsRecognized());

        if(entity.getMaxTimeToComplete()==null){
            entity.setMaxTimeToComplete(new TimeAmount());
        }
        BeanUtils.copyProperties(cluCreditInfo.getMaxTimeToComplete(),entity.getMaxTimeToComplete());

        if(entity.getMinTimeToComplete()==null){
            entity.setMinTimeToComplete(new TimeAmount());
        }
        BeanUtils.copyProperties(cluCreditInfo.getMinTimeToComplete(),entity.getMinTimeToComplete());

        if(entity.getRepeatTime()==null){
            entity.setRepeatTime(new TimeAmount());
        }
        BeanUtils.copyProperties(cluCreditInfo.getRepeatTime(),entity.getRepeatTime());

        BeanUtils.copyProperties(cluCreditInfo,entity,new String[]{"repeatTime","minTimeToComplete","maxTimeToComplete","maxAllowableInactivity","maxTimeResultsRecognized"});

    }
    
    public static List<AccreditationInfo> toAccreditationInfos(List<CluAccreditation> entities) {
        if(entities==null){
        	return null;
        }
    	List<AccreditationInfo> dtos = new ArrayList<AccreditationInfo>(entities.size());
        for (CluAccreditation entity : entities) {
            dtos.add(toAccreditationInfo(entity));
        }
        return dtos;

    }

    public static AccreditationInfo toAccreditationInfo(CluAccreditation entity) {
        if(entity==null){
            return null;
        }
        AccreditationInfo dto = new AccreditationInfo();

        BeanUtils.copyProperties(entity, dto,  new String[] {"attributes" });

        dto.setAttributes(toAttributeMap(entity.getAttributes()));

        return dto;

    }
    
    public static List<AdminOrgInfo> toCluAdminOrgInfos(List<CluAdminOrg> entities) {
        if(entities==null){
        	return null;
        }
    	List<AdminOrgInfo> dtos = new ArrayList<AdminOrgInfo>(entities.size());
        for (CluAdminOrg entity : entities) {
            dtos.add(toAdminOrgInfo(entity));
        }
        return dtos;
    }

    public static AdminOrgInfo toAdminOrgInfo(CluAdminOrg entity) {
        if(entity==null){
            return null;
        }
        AdminOrgInfo dto = new AdminOrgInfo();
        BeanUtils.copyProperties(entity, dto,  new String[] {"attributes" });
        dto.setAttributes(toAttributeMap(entity.getAttributes()));

        return dto;
    }

}
