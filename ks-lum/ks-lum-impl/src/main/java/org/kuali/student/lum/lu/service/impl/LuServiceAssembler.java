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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.kuali.student.core.dto.AmountInfo;
import org.kuali.student.core.dto.CurrencyAmountInfo;
import org.kuali.student.core.dto.TimeAmountInfo;
import org.kuali.student.core.entity.Amount;
import org.kuali.student.core.entity.CurrencyAmount;
import org.kuali.student.core.entity.TimeAmount;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.core.search.dto.SearchParam;
import org.kuali.student.core.service.impl.BaseAssembler;
import org.kuali.student.lum.lrc.dto.ResultComponentTypeInfo;
import org.kuali.student.lum.lu.dao.LuDao;
import org.kuali.student.lum.lu.dto.AcademicSubjectOrgInfo;
import org.kuali.student.lum.lu.dto.AccreditationInfo;
import org.kuali.student.lum.lu.dto.AdminOrgInfo;
import org.kuali.student.lum.lu.dto.AffiliatedOrgInfo;
import org.kuali.student.lum.lu.dto.CluAccountingInfo;
import org.kuali.student.lum.lu.dto.CluCluRelationInfo;
import org.kuali.student.lum.lu.dto.CluCreditInfo;
import org.kuali.student.lum.lu.dto.CluFeeInfo;
import org.kuali.student.lum.lu.dto.CluFeeRecordInfo;
import org.kuali.student.lum.lu.dto.CluIdentifierInfo;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.dto.CluInstructorInfo;
import org.kuali.student.lum.lu.dto.CluLoRelationInfo;
import org.kuali.student.lum.lu.dto.CluLoRelationTypeInfo;
import org.kuali.student.lum.lu.dto.CluResultInfo;
import org.kuali.student.lum.lu.dto.CluResultTypeInfo;
import org.kuali.student.lum.lu.dto.CluSetInfo;
import org.kuali.student.lum.lu.dto.CluSetTypeInfo;
import org.kuali.student.lum.lu.dto.DeliveryMethodTypeInfo;
import org.kuali.student.lum.lu.dto.InstructionalFormatTypeInfo;
import org.kuali.student.lum.lu.dto.LuCodeInfo;
import org.kuali.student.lum.lu.dto.LuCodeTypeInfo;
import org.kuali.student.lum.lu.dto.LuDocRelationInfo;
import org.kuali.student.lum.lu.dto.LuDocRelationTypeInfo;
import org.kuali.student.lum.lu.dto.LuLuRelationTypeInfo;
import org.kuali.student.lum.lu.dto.LuPublicationTypeInfo;
import org.kuali.student.lum.lu.dto.LuTypeInfo;
import org.kuali.student.lum.lu.dto.LuiInfo;
import org.kuali.student.lum.lu.dto.LuiLuiRelationInfo;
import org.kuali.student.lum.lu.dto.MembershipQueryInfo;
import org.kuali.student.lum.lu.dto.ResultOptionInfo;
import org.kuali.student.lum.lu.dto.ResultUsageTypeInfo;
import org.kuali.student.lum.lu.entity.AffiliatedOrg;
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
import org.kuali.student.lum.lu.entity.CluFeeAmount;
import org.kuali.student.lum.lu.entity.CluFeeAttribute;
import org.kuali.student.lum.lu.entity.CluFeeRecord;
import org.kuali.student.lum.lu.entity.CluFeeRecordAttribute;
import org.kuali.student.lum.lu.entity.CluIdentifier;
import org.kuali.student.lum.lu.entity.CluInstructor;
import org.kuali.student.lum.lu.entity.CluLoRelation;
import org.kuali.student.lum.lu.entity.CluLoRelationType;
import org.kuali.student.lum.lu.entity.CluResult;
import org.kuali.student.lum.lu.entity.CluResultType;
import org.kuali.student.lum.lu.entity.CluSet;
import org.kuali.student.lum.lu.entity.CluSetAttribute;
import org.kuali.student.lum.lu.entity.CluSetType;
import org.kuali.student.lum.lu.entity.DeliveryMethodType;
import org.kuali.student.lum.lu.entity.InstructionalFormatType;
import org.kuali.student.lum.lu.entity.LuCode;
import org.kuali.student.lum.lu.entity.LuCodeType;
import org.kuali.student.lum.lu.entity.LuDocumentRelation;
import org.kuali.student.lum.lu.entity.LuDocumentRelationType;
import org.kuali.student.lum.lu.entity.LuLuRelationType;
import org.kuali.student.lum.lu.entity.LuPublicationType;
import org.kuali.student.lum.lu.entity.LuRichText;
import org.kuali.student.lum.lu.entity.LuType;
import org.kuali.student.lum.lu.entity.Lui;
import org.kuali.student.lum.lu.entity.LuiAttribute;
import org.kuali.student.lum.lu.entity.LuiLuiRelation;
import org.kuali.student.lum.lu.entity.MembershipQuery;
import org.kuali.student.lum.lu.entity.ResultOption;
import org.kuali.student.lum.lu.entity.ResultUsageType;
import org.kuali.student.lum.lu.entity.SearchParameter;
import org.kuali.student.lum.lu.entity.SearchParameterValue;
import org.springframework.beans.BeanUtils;

public class LuServiceAssembler extends BaseAssembler {

	public static List<CluLoRelationTypeInfo> toCluLoRelationTypeInfos(
			List<CluLoRelationType> entities) {
		return toGenericTypeInfoList(CluLoRelationTypeInfo.class, entities);
	}

	public static CluLoRelationTypeInfo toCluLoRelationTypeInfo(
			CluLoRelationType entity) {
		return toGenericTypeInfo(CluLoRelationTypeInfo.class, entity);
	}

	public static List<CluCluRelationInfo> toCluCluRelationInfos(
			List<CluCluRelation> entities) {
		List<CluCluRelationInfo> dtos = new ArrayList<CluCluRelationInfo>(
				entities.size());
		if (entities != null) {
			for (CluCluRelation entity : entities) {
				dtos.add(toCluCluRelationInfo(entity));
			}
		}
		return dtos;

	}

	public static CluCluRelationInfo toCluCluRelationInfo(CluCluRelation entity) {
		if (entity == null) {
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

	public static List<CluLoRelationInfo> toCluLoRelationInfos(
			List<CluLoRelation> entities) {
		
		List<CluLoRelationInfo> dtos = new ArrayList<CluLoRelationInfo>(
				entities.size());
		if (entities != null) {
			for (CluLoRelation entity : entities) {
				dtos.add(toCluLoRelationInfo(entity));
			}
		}
		return dtos;
	}

	public static CluLoRelationInfo toCluLoRelationInfo(CluLoRelation entity) {
		if (entity == null) {
			return null;
		}
		CluLoRelationInfo dto = new CluLoRelationInfo();
		BeanUtils.copyProperties(entity, dto, new String[] { "cluId",
				"attributes", "metaInfo" });

		dto.setCluId(entity.getClu().getId());
		dto.setAttributes(toAttributeMap(entity.getAttributes()));
		dto.setMetaInfo(toMetaInfo(entity.getMeta(), entity.getVersionInd()));

		return dto;
	}

	public static List<CluInfo> toCluInfos(List<Clu> entities) {

		// return an empty list (Effective Java 2ndEd, item #43)
		if (entities == null) {
			return  new ArrayList<CluInfo>(0);
		}

		List<CluInfo> dtos = new ArrayList<CluInfo>(entities.size());
		for (Clu entity : entities) {
			dtos.add(toCluInfo(entity));
		}
		return dtos;

	}

	public static CluInfo toCluInfo(Clu entity) {
		if (entity == null) {
			return null;
		}
		CluInfo dto = new CluInfo();

		// copy all simple fields - exclude complex data types
		BeanUtils.copyProperties(entity, dto, new String[] {
				"officialIdentifier", "alternateIdentifiers", "descr",
				"participatingOrgs", "primaryInstructor", "instructors",
				"stdDuration", "luCodes", "credit", "offeredAtpTypes", "fee",
				"accounting", "intensity", "academicSubjectOrgs",
				"campusLocationList", "accreditationList", "primaryAdminOrg",
				"alternateAdminOrgs", "attributes", "metaInfo" });
		dto.setOfficialIdentifier(toCluIdentifierInfo(entity
				.getOfficialIdentifier()));
		dto.setAlternateIdentifiers(toCluIdentifierInfos(entity
				.getAlternateIdentifiers()));
		dto.setDescr(toRichTextInfo(entity.getDescr()));

		// accreditingOrg Deprecated in v 1.0-rc2 Replaced by Primary and
		// Alternate admin orgs
		dto.setAccreditations(toAccreditationInfos(entity.getAccreditations()));

		dto.setPrimaryAdminOrg(toAdminOrgInfo(entity.getPrimaryAdminOrg()));
		dto.setAlternateAdminOrgs(toCluAdminOrgInfos(entity
				.getAlternateAdminOrgs()));

		dto.setPrimaryInstructor(toCluInstructorInfo(entity
				.getPrimaryInstructor()));
		dto.setInstructors(toCluInstructorInfos(entity.getInstructors()));
		dto.setStdDuration(toTimeAmountInfo(entity.getStdDuration()));
		dto.setLuCodes(toLuCodeInfos(entity.getLuCodes()));

		if (entity.getOfferedAtpTypes() != null) {
			List<String> offeredAtpTypes = new ArrayList<String>(entity
					.getOfferedAtpTypes().size());
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

		if (entity.getAcademicSubjectOrgs() != null) {
			List<AcademicSubjectOrgInfo> academicSubjectOrgs = new ArrayList<AcademicSubjectOrgInfo>(
					entity.getAcademicSubjectOrgs().size());
			for (CluAcademicSubjectOrg cluOrg : entity.getAcademicSubjectOrgs()) {
				AcademicSubjectOrgInfo sOrg = new AcademicSubjectOrgInfo();
				sOrg.setOrgId(cluOrg.getOrgId());
				academicSubjectOrgs.add(sOrg);
			}
			dto.setAcademicSubjectOrgs(academicSubjectOrgs);
		}

		if (entity.getCampusLocations() != null) {
			List<String> campusLocations = new ArrayList<String>(entity
					.getCampusLocations().size());
			for (CluCampusLocation cluCamp : entity.getCampusLocations()) {
				campusLocations.add(cluCamp.getCampusLocation());
			}
			dto.setCampusLocations(campusLocations);
		}

		dto.setIntensity(toAmountInfo(entity.getIntensity()));

		return dto;

	}

	public static List<CluSetInfo> toCluSetInfos(List<CluSet> entities) {
		List<CluSetInfo> dtos = new ArrayList<CluSetInfo>(entities.size());
		if (entities != null) {
			for (CluSet entity : entities) {
				dtos.add(toCluSetInfo(entity));
			}
		}
		return dtos;
	}

	public static CluSet toCluSetEntity(CluSetInfo cluSetInfo, LuDao luDao) throws InvalidParameterException, DoesNotExistException {
		CluSet cluSet = new CluSet();

		BeanUtils.copyProperties(cluSetInfo, cluSet, new String[] { "id",
				"descr", "attributes", "metaInfo", "membershipQuery" });
		cluSet.setAttributes(toGenericAttributes(
				CluSetAttribute.class, cluSetInfo.getAttributes(), cluSet, luDao));
		cluSet.setType(cluSetInfo.getType());
		cluSet.setDescr(toRichText(LuRichText.class, cluSetInfo.getDescr()));

		for (String cluId : cluSetInfo.getCluIds()) {
			cluSet.getClus().add(luDao.fetch(Clu.class, cluId));
		}
		for (String cluSetId : cluSetInfo.getCluSetIds()) {
			CluSet c = luDao.fetch(CluSet.class, cluSetId);
			cluSet.getCluSets().add(c);
		}
		cluSet.setMembershipQuery(toMembershipQueryEntity(cluSetInfo.getMembershipQuery()));

		return cluSet;
	}

	public static CluSetInfo toCluSetInfo(CluSet entity) {
		if (entity == null) {
			return null;
		}
		CluSetInfo dto = new CluSetInfo();

		BeanUtils.copyProperties(entity, dto, new String[] { "descr",
				"cluCriteria", "cluSets", "clus", "attributes", "metaInfo", "membershipQuery" });

		dto.setDescr(toRichTextInfo(entity.getDescr()));
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

		MembershipQueryInfo mqInfo = toMembershipQueryInfo(entity.getMembershipQuery());
		dto.setMembershipQuery(mqInfo);
		dto.setIsReusable(entity.getIsReusable());
		dto.setIsReferenceable(entity.getIsReferenceable());

		return dto;
	}

	public static MembershipQueryInfo toMembershipQueryInfo(MembershipQuery entity) {
		if(entity == null) {
			return null;
		}
		MembershipQueryInfo dto = new MembershipQueryInfo();
		dto.setId(entity.getId());
		dto.setSearchTypeKey(entity.getSearchTypeKey());
		List<SearchParam> list = new ArrayList<SearchParam>();
		for(SearchParameter param : entity.getSearchParameters()) {
			SearchParam sp = toSearchParam(param);
			list.add(sp);
		}
		dto.setQueryParamValueList(list);

		return dto;
	}

	public static SearchParam toSearchParam(SearchParameter entity) {
		SearchParam dto = new SearchParam();
		dto.setKey(entity.getKey());
		List<String> values = new ArrayList<String>();
		for(SearchParameterValue value : entity.getValues()) {
			values.add(value.getValue());
		}
		dto.setValue(values);
		if(entity.getValues().size() == 1) {
			dto.setValue(entity.getValues().get(0).getValue());
		}
		return dto;
	}

	public static MembershipQuery toMembershipQueryEntity(MembershipQueryInfo dto) {
		if(dto == null) {
			return null;
		}
		MembershipQuery entity = new MembershipQuery();
		entity.setSearchTypeKey(dto.getSearchTypeKey());
		List<SearchParameter> list = new ArrayList<SearchParameter>();
		for(SearchParam param : dto.getQueryParamValueList()) {
			SearchParameter sp = toSearchParameterEntity(param);
			list.add(sp);
		}
		entity.setSearchParameters(list);

		return entity;
	}

	public static SearchParameter toSearchParameterEntity(SearchParam dto) {
		if(dto == null) {
			return null;
		}
		SearchParameter entity = new SearchParameter();
		entity.setKey(dto.getKey());
		List<SearchParameterValue> values = new ArrayList<SearchParameterValue>();
		if(dto.getValue() instanceof String) {
			SearchParameterValue value = new SearchParameterValue();
			value.setValue((String) dto.getValue());
			values.add(value);
		} else if(dto.getValue() instanceof List<?>) {
			List<String> stringList = (List<String>)dto.getValue();
			for(String s : stringList) {
				SearchParameterValue value = new SearchParameterValue();
				value.setValue(s);
				values.add(value);
			}
		}
		entity.setValues(values);
		return entity;
	}

	public static List<ResultUsageTypeInfo> toResultUsageTypeInfos(
			List<ResultUsageType> entities) {
		return toGenericTypeInfoList(ResultUsageTypeInfo.class, entities);
	}

	public static ResultUsageTypeInfo toResultUsageTypeInfo(
			ResultUsageType entity) {
		return toGenericTypeInfo(ResultUsageTypeInfo.class, entity);
	}

	public static List<DeliveryMethodTypeInfo> toDeliveryMethodTypeInfos(
			List<DeliveryMethodType> entities) {
		return toGenericTypeInfoList(DeliveryMethodTypeInfo.class, entities);
	}

	public static DeliveryMethodTypeInfo toDeliveryMethodTypeInfo(
			DeliveryMethodType entity) {
		return toGenericTypeInfo(DeliveryMethodTypeInfo.class, entity);
	}

	public static List<InstructionalFormatTypeInfo> toInstructionalFormatTypeInfos(
			List<InstructionalFormatType> entities) {
		return toGenericTypeInfoList(InstructionalFormatTypeInfo.class,
				entities);
	}

	public static InstructionalFormatTypeInfo toInstructionalFormatTypeInfo(
			InstructionalFormatType entity) {
		return toGenericTypeInfo(InstructionalFormatTypeInfo.class, entity);
	}

	public static List<LuCodeTypeInfo> toLuCodeTypeInfos(
			List<LuCodeType> entities) {
		return toGenericTypeInfoList(LuCodeTypeInfo.class, entities);
	}

	public static LuCodeTypeInfo toLuCodeTypeInfo(LuCodeType entity) {
		return toGenericTypeInfo(LuCodeTypeInfo.class, entity);
	}

	public static List<CluResultTypeInfo> toCluResultTypeInfos(
			List<CluResultType> entities) {
		return toGenericTypeInfoList(CluResultTypeInfo.class, entities);
	}

	public static CluResultTypeInfo toCluResultTypeInfo(CluResultType entity) {
		return toGenericTypeInfo(CluResultTypeInfo.class, entity);
	}

	public static CluSetTypeInfo toCluSetTypeInfo(CluSetType entity) {
		return toGenericTypeInfo(CluSetTypeInfo.class, entity);
	}

	public static List<CluSetTypeInfo> toCluSetTypeInfos(
			List<CluSetType> entities) {
		return toGenericTypeInfoList(CluSetTypeInfo.class, entities);
	}

	public static CluResultInfo toCluResultInfo(CluResult entity) {
		if (entity == null) {
			return null;
		}

		CluResultInfo dto = new CluResultInfo();

		BeanUtils.copyProperties(entity, dto, new String[] { "resultOptions", "desc" });

		List<ResultOptionInfo> resultOptions = toResultOptionInfos(entity.getResultOptions());
		dto.setResultOptions(resultOptions);
		dto.setDesc(toRichTextInfo(entity.getDesc()));
		dto.setCluId(entity.getClu().getId());
		CluResultTypeInfo type = toCluResultTypeInfo(entity.getCluResultType());
		dto.setType(type.getId());
		dto.setMetaInfo(toMetaInfo(entity.getMeta(), entity.getVersionInd()));

		return dto;
	}

	public static List<CluResultInfo> toCluResultInfos(List<CluResult> entities) {
		List<CluResultInfo> dtos = new ArrayList<CluResultInfo>();
		if (entities != null) {
			for (CluResult entity : entities) {
				dtos.add(toCluResultInfo(entity));
			}
		}
		return dtos;
	}

	public static List<ResultOptionInfo> toResultOptionInfos(
			List<ResultOption> entities) {
		List<ResultOptionInfo> dtos = new ArrayList<ResultOptionInfo>();
		if (entities != null) {
			for (ResultOption entity : entities) {
				dtos.add(toResultOptionInfo(entity));
			}
		}
		return dtos;
	}

	public static ResultOptionInfo toResultOptionInfo(ResultOption entity) {
		if (entity == null) {
			return null;
		}

		ResultOptionInfo dto = new ResultOptionInfo();

		BeanUtils.copyProperties(entity, dto, new String[] { "resultUsageType", "desc" });
		if(entity.getResultUsageType() != null) {
			dto.setResultUsageTypeKey(entity.getResultUsageType().getId());
		}
		dto.setDesc(toRichTextInfo(entity.getDesc()));
		dto.setMetaInfo(toMetaInfo(entity.getMeta(), entity.getVersionInd()));

		return dto;
	}

	public static List<LuDocRelationTypeInfo> toLuDocRelationType(
			List<LuDocumentRelationType> entities) {
		List<LuDocRelationTypeInfo> dtos = new ArrayList<LuDocRelationTypeInfo>(
				entities.size());
		if (entities != null) {
			for (LuDocumentRelationType entity : entities) {
				dtos.add(toLuDocRelationTypeInfo(entity));
			}
		}
		return dtos;

	}

	public static LuDocRelationInfo toLuDocRelationInfo(
			LuDocumentRelation entity) {
		if (entity != null) {
			return null;
		}
		LuDocRelationInfo dto = new LuDocRelationInfo();

		BeanUtils.copyProperties(entity, dto, new String[] { "desc",
				"luDocumentRelationType", "attributes", "metInfo" });

		dto.setCluId(entity.getClu().getId());
		dto.setDesc(toRichTextInfo(entity.getDescr()));
		dto.setType(entity.getLuDocumentRelationType().getId());
		dto.setAttributes(toAttributeMap(entity.getAttributes()));
		dto.setMetaInfo(toMetaInfo(entity.getMeta(), entity.getVersionInd()));

		return dto;
	}

	public static List<LuDocRelationInfo> toLuDocRelationInfos(
			List<LuDocumentRelation> entities) {
		List<LuDocRelationInfo> dtos = new ArrayList<LuDocRelationInfo>(
				entities.size());
		if (entities != null) {
			for (LuDocumentRelation entity : entities) {
				dtos.add(toLuDocRelationInfo(entity));
			}
		}
		return dtos;
	}

	public static List<LuDocRelationTypeInfo> toLuDocRelationTypeInfos(
			List<LuDocumentRelationType> entities) {
		List<LuDocRelationTypeInfo> dtos = new ArrayList<LuDocRelationTypeInfo>(
				entities.size());
		if (entities != null) {
			for (LuDocumentRelationType entity : entities) {
				dtos.add(toLuDocRelationTypeInfo(entity));
			}
		}
		return dtos;

	}

	public static List<LuDocRelationTypeInfo> toLuDocumentRelationTypes(
			List<LuDocumentRelationType> entities) {
		List<LuDocRelationTypeInfo> dtos = new ArrayList<LuDocRelationTypeInfo>(
				entities.size());
		if (entities != null) {
			for (LuDocumentRelationType entity : entities) {
				dtos.add(toLuDocRelationTypeInfo(entity));
			}
		}
		return dtos;
	}

	public static LuDocRelationTypeInfo toLuDocRelationTypeInfo(
			LuDocumentRelationType entity) {
		if (entity == null) {
			return null;
		}
		LuDocRelationTypeInfo dto = new LuDocRelationTypeInfo();

		BeanUtils.copyProperties(entity, dto, new String[] { "attributes" });

		dto.setAttributes(toAttributeMap(entity.getAttributes()));

		return dto;
	}

	public static List<LuLuRelationTypeInfo> toLuLuRelationTypeInfos(
			List<LuLuRelationType> entities) {
		List<LuLuRelationTypeInfo> dtos = new ArrayList<LuLuRelationTypeInfo>(
				entities.size());
		if(entities!=null){
			for (LuLuRelationType entity : entities) {
				dtos.add(toLuLuRelationTypeInfo(entity));
			}
		}
		return dtos;

	}

	public static LuLuRelationTypeInfo toLuLuRelationTypeInfo(
			LuLuRelationType entity) {
		if (entity == null) {
			return null;
		}
		LuLuRelationTypeInfo dto = new LuLuRelationTypeInfo();

		BeanUtils.copyProperties(entity, dto, new String[] { "attributes" });
		dto.setAttributes(toAttributeMap(entity.getAttributes()));

		return dto;
	}

	public static List<LuTypeInfo> toLuTypeInfos(List<LuType> entities) {
		return toGenericTypeInfoList(LuTypeInfo.class, entities);
	}

	public static LuTypeInfo toLuTypeInfo(LuType entity) {
		LuTypeInfo typeInfo = toGenericTypeInfo(LuTypeInfo.class, entity);
		typeInfo.setDeliveryMethod(entity.getDeliveryMethod());
		typeInfo.setInstructionalFormat(entity.getInstructionalFormat());

		return typeInfo;
	}

	public static List<LuiInfo> toLuiInfos(List<Lui> entities) {
		List<LuiInfo> dtos = new ArrayList<LuiInfo>(entities.size());
		if (entities != null) {
			for (Lui entity : entities) {
				dtos.add(toLuiInfo(entity));
			}
		}
		return dtos;

	}

	public static LuiInfo toLuiInfo(Lui entity) {
		if (entity == null) {
			return null;
		}
		LuiInfo luiInfo = new LuiInfo();

		BeanUtils.copyProperties(entity, luiInfo, new String[] { "clu",
				"metaInfo", "attributes" });

		luiInfo.setCluId(entity.getClu().getId());

		luiInfo
				.setMetaInfo(toMetaInfo(entity.getMeta(), entity
						.getVersionInd()));

		luiInfo.setAttributes(toAttributeMap(entity.getAttributes()));

		return luiInfo;
	}

	public static Lui toLui(boolean isUpdate, LuiInfo luiInfo, LuDao dao)
			throws DoesNotExistException, VersionMismatchException,
			InvalidParameterException {
		if (luiInfo == null) {
			return null;
		}
		Lui lui;

		if (isUpdate) {
			lui = (Lui) dao.fetch(Lui.class, luiInfo.getId());
			if (null == lui) {
				throw new DoesNotExistException((new StringBuilder()).append(
						"Lui does not exist for id: ").append(luiInfo.getId())
						.toString());
			}
			if (!String.valueOf(lui.getVersionInd()).equals(
					luiInfo.getMetaInfo().getVersionInd())) {
				throw new VersionMismatchException(
						"Lui to be updated is not the current version");
			}
		} else {
			lui = new Lui();
		}

		BeanUtils.copyProperties(luiInfo, lui, new String[] { "cluId",
				"attributes", "metaInfo" });

		lui.setAttributes(toGenericAttributes(LuiAttribute.class, luiInfo
				.getAttributes(), lui, dao));

		Clu clu = (Clu) dao.fetch(Clu.class, luiInfo.getCluId());
		if (null == clu) {
			throw new InvalidParameterException((new StringBuilder()).append(
					"Clu does not exist for id: ").append(luiInfo.getCluId())
					.toString());
		}
		lui.setClu(clu);
		return lui;
	}

	public static List<LuiLuiRelationInfo> toLuiLuiRelationInfos(
			List<LuiLuiRelation> entities) {
		List<LuiLuiRelationInfo> dtos = new ArrayList<LuiLuiRelationInfo>(
				entities.size());
		if (entities != null) {
			for (LuiLuiRelation entity : entities) {
				dtos.add(toLuiLuiRelationInfo(entity));
			}
		}
		return dtos;
	}

	public static LuiLuiRelationInfo toLuiLuiRelationInfo(LuiLuiRelation entity) {
		if (entity == null) {
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

	public static List<CluIdentifierInfo> toCluIdentifierInfos(
			List<CluIdentifier> entities) {
		List<CluIdentifierInfo> dtos = new ArrayList<CluIdentifierInfo>(
				entities.size());
		if(entities!=null){
			for (CluIdentifier entity : entities) {
				dtos.add(toCluIdentifierInfo(entity));
			}
		}
		return dtos;
	}

	public static CluIdentifierInfo toCluIdentifierInfo(CluIdentifier entity) {
		if (entity == null) {
			return null;
		}

		CluIdentifierInfo dto = new CluIdentifierInfo();

		BeanUtils.copyProperties(entity, dto);

		return dto;
	}

	public static List<CluInstructorInfo> toCluInstructorInfos(
			List<CluInstructor> entities) {
		List<CluInstructorInfo> dtos = new ArrayList<CluInstructorInfo>(
				entities.size());
		if(entities!=null){
			for (CluInstructor entity : entities) {
				dtos.add(toCluInstructorInfo(entity));
			}
		}
		return dtos;
	}

	public static CluInstructorInfo toCluInstructorInfo(CluInstructor entity) {
		if (entity == null) {
			return null;
		}
		CluInstructorInfo dto = new CluInstructorInfo();

		BeanUtils.copyProperties(entity, dto,
				new String[] { "id", "attributes" });
		dto.setAttributes(toAttributeMap(entity.getAttributes()));

		return dto;
	}

	public static AmountInfo toAmountInfo(Amount entity) {
		if (entity == null) {
			return null;
		}
		AmountInfo dto = new AmountInfo();

		BeanUtils.copyProperties(entity, dto);

		return dto;
	}

	public static TimeAmountInfo toTimeAmountInfo(TimeAmount entity) {
		if (entity == null) {
			return null;
		}
		TimeAmountInfo dto = new TimeAmountInfo();

		BeanUtils.copyProperties(entity, dto);

		return dto;
	}

	public static List<LuCodeInfo> toLuCodeInfos(List<LuCode> entities) {
		List<LuCodeInfo> dtos = new ArrayList<LuCodeInfo>(entities.size());
		if(entities!=null){
			for (LuCode entity : entities) {
				dtos.add(toLuCodeInfo(entity));
			}
		}
		return dtos;
	}

	public static LuCodeInfo toLuCodeInfo(LuCode entity) {
		if (entity == null) {
			return null;
		}
		LuCodeInfo dto = new LuCodeInfo();

		BeanUtils.copyProperties(entity, dto, new String[] { "attributes",
				"metInfo" });

		dto.setAttributes(toAttributeMap(entity.getAttributes()));
		dto.setMetaInfo(toMetaInfo(entity.getMeta(), entity.getVersionInd()));

		return dto;
	}

	public static CluCreditInfo toCluCreditInfos(CluCredit entity) {
		if (entity == null) {
			return null;
		}
		CluCreditInfo dto = new CluCreditInfo();

		BeanUtils.copyProperties(entity, dto, new String[] { "id",
				"repeatTime", "minTimeToComplete", "maxTimeToComplete",
				"maxAllowableInactivity", "maxTimeResultsRecognized" });
		dto.setRepeatTime(toTimeAmountInfo(entity.getRepeatTime()));
		dto
				.setMinTimeToComplete(toTimeAmountInfo(entity
						.getMinTimeToComplete()));
		dto
				.setMaxTimeToComplete(toTimeAmountInfo(entity
						.getMaxTimeToComplete()));
		dto.setMaxAllowableInactivity(toTimeAmountInfo(entity
				.getMaxAllowableInactivity()));
		dto.setMaxTimeResultsRecognized(toTimeAmountInfo(entity
				.getMaxTimeResultsRecognized()));

		return dto;
	}

	public static List<ResultComponentTypeInfo> toResultComponentTypeInfo(
			List<String> componentIds) {
		List<ResultComponentTypeInfo> dtos = new ArrayList<ResultComponentTypeInfo>();
		if(componentIds!=null){
			for (String id : componentIds) {
				ResultComponentTypeInfo comp = new ResultComponentTypeInfo();
				comp.setId(id);
				dtos.add(comp);
			}
		}
		return dtos;
	}

	public static CluFeeInfo toCluFeeInfo(CluFee entity) {
		if (entity == null) {
			return null;
		}
		CluFeeInfo dto = new CluFeeInfo();

		dto.setCluFeeRecords(toCluFeeRecordInfos(entity.getCluFeeRecords()));
		dto.setId(entity.getId());
		dto.setAttributes(toAttributeMap(entity.getAttributes()));
		dto.setDescr(toRichTextInfo(entity.getDescr()));
		dto.setMetaInfo(toMetaInfo(entity.getMeta(), entity.getVersionInd()));

		return dto;
	}

	private static List<CluFeeRecordInfo> toCluFeeRecordInfos(
			List<CluFeeRecord> entities) {
		List<CluFeeRecordInfo> dtos = new ArrayList<CluFeeRecordInfo>();
		if (entities != null) {
			for (CluFeeRecord record : entities) {
				dtos.add(toCluFeeRecordInfo(record));
			}
		}
		return dtos;
	}

	private static CluFeeRecordInfo toCluFeeRecordInfo(CluFeeRecord entity) {
		if (entity == null) {
			return null;
		}

		CluFeeRecordInfo dto = new CluFeeRecordInfo();

		BeanUtils.copyProperties(entity, dto,
				new String[] { "affiliatedOrgs", "currencyAmount","attributes","descr" });

		dto.setAffiliatedOrgs(toAffiliatedOrgInfos(entity.getAffiliatedOrgs()));
		dto.setFeeAmounts(toFeeAmounts(entity.getFeeAmounts()));
		dto.setDescr(toRichTextInfo(entity.getDescr()));
		dto.setAttributes(toAttributeMap(entity.getAttributes()));

		return dto;
	}

	private static List<CurrencyAmountInfo> toFeeAmounts(List<CluFeeAmount> cluFees) {
		List<CurrencyAmountInfo> feeAmounts = new ArrayList<CurrencyAmountInfo>();
		
		if (cluFees != null){
			for (CluFeeAmount cluFeeAmount:cluFees){
				CurrencyAmountInfo dto = new CurrencyAmountInfo();
				CurrencyAmount ca = cluFeeAmount.getCurrencyAmount();
				if(ca!=null){
					dto.setCurrencyQuantity(ca.getCurrencyQuantity());
					dto.setCurrencyTypeKey(ca.getCurrencyTypeKey());
				}
				feeAmounts.add(dto);
			}
		}
		
		return feeAmounts;
	}

	private static List<AffiliatedOrgInfo> toAffiliatedOrgInfos(
			List<AffiliatedOrg> entities) {
		List<AffiliatedOrgInfo> dtos = new ArrayList<AffiliatedOrgInfo>();
		if (entities != null) {
			for (AffiliatedOrg record : entities) {
				dtos.add(toAffiliatedOrgInfo(record));
			}
		}
		return dtos;
	}

	private static AffiliatedOrgInfo toAffiliatedOrgInfo(AffiliatedOrg entity) {
		if (entity == null) {
			return null;
		}

		AffiliatedOrgInfo dto = new AffiliatedOrgInfo();

		BeanUtils.copyProperties(entity, dto);
		return dto;
	}

	public static CluAccountingInfo toCluAccountingInfo(CluAccounting entity) {
		if (entity == null) {
			return null;
		}
		CluAccountingInfo dto = new CluAccountingInfo();
		dto.setId(entity.getId());
		dto.setAffiliatedOrgs(toAffiliatedOrgInfos(entity.getAffiliatedOrgs()));
		dto.setAttributes(toAttributeMap(entity.getAttributes()));
		return dto;
	}

	public static Amount toAmount(AmountInfo amountInfo) {
		if (amountInfo == null) {
			return null;
		}
		Amount amount = new Amount();
		BeanUtils.copyProperties(amountInfo, amount);
		return amount;
	}

	public static TimeAmount toTimeAmount(TimeAmountInfo timeAmountInfo) {
		if (timeAmountInfo == null) {
			return null;
		}
		TimeAmount timeAmount = new TimeAmount();
		BeanUtils.copyProperties(timeAmountInfo, timeAmount);
		return timeAmount;
	}

	public static CluCredit toCluCredit(CluCreditInfo cluCreditInfo) {
		if (cluCreditInfo == null) {
			return null;
		}
		CluCredit cluCredit = new CluCredit();

		cluCredit.setMaxAllowableInactivity(LuServiceAssembler
				.toTimeAmount(cluCreditInfo.getMaxAllowableInactivity()));
		cluCredit.setMaxTimeResultsRecognized(LuServiceAssembler
				.toTimeAmount(cluCreditInfo.getMaxTimeResultsRecognized()));
		cluCredit.setMaxTimeToComplete(LuServiceAssembler
				.toTimeAmount(cluCreditInfo.getMaxTimeToComplete()));
		cluCredit.setMinTimeToComplete(LuServiceAssembler
				.toTimeAmount(cluCreditInfo.getMinTimeToComplete()));
		cluCredit.setRepeatTime(LuServiceAssembler.toTimeAmount(cluCreditInfo
				.getRepeatTime()));

		BeanUtils.copyProperties(cluCreditInfo, cluCredit, new String[] {
				"repeatTime", "minTimeToComplete", "maxTimeToComplete",
				"maxAllowableInactivity", "maxTimeResultsRecognized" });

		return cluCredit;
	}

	public static void copyCluCredit(CluCreditInfo cluCreditInfo,
			CluCredit entity) {
		if (entity == null) {
			return;
		}
		if (entity.getMaxAllowableInactivity() == null) {
			entity.setMaxAllowableInactivity(new TimeAmount());
		}
		BeanUtils.copyProperties(cluCreditInfo.getMaxAllowableInactivity(),
				entity.getMaxAllowableInactivity());

		if (entity.getMaxTimeResultsRecognized() == null) {
			entity.setMaxTimeResultsRecognized(new TimeAmount());
		}
		BeanUtils.copyProperties(cluCreditInfo.getMaxTimeResultsRecognized(),
				entity.getMaxTimeResultsRecognized());

		if (entity.getMaxTimeToComplete() == null) {
			entity.setMaxTimeToComplete(new TimeAmount());
		}
		BeanUtils.copyProperties(cluCreditInfo.getMaxTimeToComplete(), entity
				.getMaxTimeToComplete());

		if (entity.getMinTimeToComplete() == null) {
			entity.setMinTimeToComplete(new TimeAmount());
		}
		BeanUtils.copyProperties(cluCreditInfo.getMinTimeToComplete(), entity
				.getMinTimeToComplete());

		if (entity.getRepeatTime() == null) {
			entity.setRepeatTime(new TimeAmount());
		}
		BeanUtils.copyProperties(cluCreditInfo.getRepeatTime(), entity
				.getRepeatTime());

		BeanUtils.copyProperties(cluCreditInfo, entity, new String[] {
				"repeatTime", "minTimeToComplete", "maxTimeToComplete",
				"maxAllowableInactivity", "maxTimeResultsRecognized" });

	}

	public static List<AccreditationInfo> toAccreditationInfos(
			List<CluAccreditation> entities) {
		List<AccreditationInfo> dtos = new ArrayList<AccreditationInfo>(entities.size());

		if (entities != null) {
			for (CluAccreditation entity : entities) {
				dtos.add(toAccreditationInfo(entity));
			}
		}
		return dtos;
	}

	public static AccreditationInfo toAccreditationInfo(CluAccreditation entity) {
		if (entity == null) {
			return null;
		}
		AccreditationInfo dto = new AccreditationInfo();

		BeanUtils.copyProperties(entity, dto, new String[] { "attributes" });

		dto.setAttributes(toAttributeMap(entity.getAttributes()));

		return dto;

	}

	public static List<AdminOrgInfo> toCluAdminOrgInfos(
			List<CluAdminOrg> entities) {
		List<AdminOrgInfo> dtos = new ArrayList<AdminOrgInfo>(entities.size());
		if (entities != null) {
			for (CluAdminOrg entity : entities) {
				dtos.add(toAdminOrgInfo(entity));
			}
		}
		return dtos;
	}

	public static AdminOrgInfo toAdminOrgInfo(CluAdminOrg entity) {
		if (entity == null) {
			return null;
		}
		AdminOrgInfo dto = new AdminOrgInfo();
		BeanUtils.copyProperties(entity, dto, new String[] { "attributes" });
		dto.setAttributes(toAttributeMap(entity.getAttributes()));

		return dto;
	}

	public static List<LuPublicationTypeInfo> toLuPublicationTypeInfos(
			List<LuPublicationType> entities) {
		return toGenericTypeInfoList(LuPublicationTypeInfo.class, entities);
	}

	public static LuPublicationTypeInfo toLuPublicationTypeInfo(
			LuPublicationType entity) {
		return toGenericTypeInfo(LuPublicationTypeInfo.class, entity);
	}

	public static CluFee toCluFee(boolean isUpdate, CluFeeInfo feeInfo,
			LuDao dao) throws DoesNotExistException, VersionMismatchException,
			InvalidParameterException {
		if (feeInfo == null) {
			return null;
		}

		CluFee fee;

		if (isUpdate) {
			fee = (CluFee) dao.fetch(CluFee.class, feeInfo.getId());
			if (null == fee) {
				throw new DoesNotExistException((new StringBuilder()).append(
						"CluFee does not exist for id: ").append(
						feeInfo.getId()).toString());
			}
			if (!String.valueOf(fee.getVersionInd()).equals(
					feeInfo.getMetaInfo().getVersionInd())) {
				throw new VersionMismatchException(
						"CluFee to be updated is not the current version");
			}
		} else {
			fee = new CluFee();
		}

		if (feeInfo.getDescr() != null) {
		    LuRichText descr = LuServiceAssembler.toRichText(LuRichText.class, feeInfo.getDescr());
		    if (descr.getPlain() != null || descr.getFormatted() != null) {
		        fee.setDescr(descr);
		    }
		}

		fee.setAttributes(LuServiceAssembler.toGenericAttributes(
				CluFeeAttribute.class, feeInfo.getAttributes(), fee, dao));
		toCluFeeRecords(isUpdate, fee, feeInfo.getCluFeeRecords(), dao);

		return fee;
	}

	public static void toCluFeeRecords(boolean isUpdate,
			CluFee cluFee, List<CluFeeRecordInfo> cluFeeRecords, LuDao dao) throws InvalidParameterException {

		if (cluFeeRecords == null) {
			return;
		}

		if (!isUpdate) {

			for (CluFeeRecordInfo feeRecordInfo : cluFeeRecords) {
				CluFeeRecord feeRec = new CluFeeRecord();
				feeRec.setAffiliatedOrgs(toAffiliatedOrgs(isUpdate, feeRec.getAffiliatedOrgs(), feeRecordInfo.getAffiliatedOrgs(), dao));
				feeRec.setFeeType(feeRecordInfo.getFeeType());
				feeRec.setRateType(feeRecordInfo.getRateType());
				feeRec.setDescr(toRichText(LuRichText.class, feeRecordInfo.getDescr()));
				feeRec.setFeeAmounts(toFeeAmounts(isUpdate, feeRec.getFeeAmounts(), feeRecordInfo.getFeeAmounts(), dao));
				feeRec.setAttributes(LuServiceAssembler.toGenericAttributes(
						CluFeeRecordAttribute.class, feeRecordInfo
								.getAttributes(), feeRec, dao));
				cluFee.getCluFeeRecords().add(feeRec);
			}
		} else {
			Map<String, CluFeeRecord> oldFeeRecMap = new HashMap<String, CluFeeRecord>();
			for (CluFeeRecord feeRec : cluFee.getCluFeeRecords()) {
				oldFeeRecMap.put(feeRec.getId(), feeRec);
			}
			cluFee.getCluFeeRecords().clear();

			// Loop through the new list, if the item exists already update and
			// remove from the list
			// otherwise create a new entry
			for (CluFeeRecordInfo feeRecordInfo : cluFeeRecords) {
				CluFeeRecord feeRec = oldFeeRecMap.remove(feeRecordInfo.getId());
				if (feeRec == null) {
					feeRec = new CluFeeRecord();
				}

				feeRec.setAffiliatedOrgs(toAffiliatedOrgs(isUpdate, feeRec.getAffiliatedOrgs(), feeRecordInfo.getAffiliatedOrgs(),dao));
				feeRec.setFeeType(feeRecordInfo.getFeeType());
				feeRec.setRateType(feeRecordInfo.getRateType());
				feeRec.setDescr(toRichText(LuRichText.class, feeRecordInfo.getDescr()));
				feeRec.setFeeAmounts(toFeeAmounts(isUpdate, feeRec.getFeeAmounts(), feeRecordInfo.getFeeAmounts(),dao));
				feeRec.setAttributes(LuServiceAssembler.toGenericAttributes(
						CluFeeRecordAttribute.class, feeRecordInfo
								.getAttributes(), feeRec, dao));

				cluFee.getCluFeeRecords().add(feeRec);
			}

			// Now delete anything left over
			for (Entry<String, CluFeeRecord> entry : oldFeeRecMap.entrySet()) {
				dao.delete(entry.getValue());
			}
		}
	}

	public static List<AffiliatedOrg> toAffiliatedOrgs(boolean isUpdate, List<AffiliatedOrg> orgList, List<AffiliatedOrgInfo> affiliatedOrgInfoList, LuDao dao) {
		if(null == affiliatedOrgInfoList) {
			return orgList;
		}

		if (!isUpdate) {

			for (AffiliatedOrgInfo orgInfo : affiliatedOrgInfoList) {
				AffiliatedOrg org = new AffiliatedOrg();
				BeanUtils.copyProperties(orgInfo, org);
				orgList.add(org);
			}
		} else {
			Map<String, AffiliatedOrg> oldOrgMap = new HashMap<String, AffiliatedOrg>();
			for (AffiliatedOrg org : orgList) {
				oldOrgMap.put(org.getId(), org);
			}
			orgList.clear();

			for (AffiliatedOrgInfo orgInfo : affiliatedOrgInfoList) {
				AffiliatedOrg org = oldOrgMap.remove(orgInfo.getId());
				if (org == null) {
					org = new AffiliatedOrg();
				}

				BeanUtils.copyProperties(orgInfo, org);

				orgList.add(org);
			}

			// Now delete anything left over
			for (Entry<String, AffiliatedOrg> entry : oldOrgMap.entrySet()) {
				dao.delete(entry.getValue());
			}
		}

		return orgList;
	}

	public static List<CluFeeAmount> toFeeAmounts(boolean isUpdate, List<CluFeeAmount> caList, List<CurrencyAmountInfo> caInfoList, LuDao dao){
		if(null == caInfoList) {
			return caList;
		}
		
		if (!isUpdate) {
			for (CurrencyAmountInfo caInfo:caInfoList){
				CurrencyAmount ca = new CurrencyAmount();
				ca.setCurrencyQuantity(caInfo.getCurrencyQuantity());
				ca.setCurrencyTypeKey(caInfo.getCurrencyTypeKey());
				CluFeeAmount cluFeeAmount = new CluFeeAmount();
				cluFeeAmount.setCurrencyAmount(ca);
				caList.add(cluFeeAmount);
			}
		} else {
			// Delete existing fee amounts (this assumes feeAmounts are not individually updated)
			for (CluFeeAmount cluFeeAmount:caList) {
				dao.delete(cluFeeAmount);
			}					
			caList.clear();

			for (CurrencyAmountInfo caInfo : caInfoList) {
				CurrencyAmount ca = new CurrencyAmount();

				ca.setCurrencyQuantity(caInfo.getCurrencyQuantity());
				ca.setCurrencyTypeKey(caInfo.getCurrencyTypeKey());
				CluFeeAmount cluFeeAmount = new CluFeeAmount();
				cluFeeAmount.setCurrencyAmount(ca);
				caList.add(cluFeeAmount);
			}
		}

		return caList;
	}
	
	public static CluIdentifier createOfficialIdentifier(CluInfo cluInfo) {
        CluIdentifier officialIdentifier = new CluIdentifier();
        BeanUtils.copyProperties(cluInfo.getOfficialIdentifier(),
                officialIdentifier, new String[] { "code" });

        officialIdentifier
                .setCode(new StringBuilder().append(
                        cluInfo.getOfficialIdentifier().getDivision())
                        .append(
                                cluInfo.getOfficialIdentifier()
                                        .getSuffixCode()).toString());
        return officialIdentifier;
	}

    public static void updateOfficialIdentifier(Clu clu, CluInfo cluInfo) {
        if (clu.getOfficialIdentifier() == null) {
            clu.setOfficialIdentifier(new CluIdentifier());
        }
        BeanUtils.copyProperties(cluInfo.getOfficialIdentifier(), clu
                .getOfficialIdentifier(), new String[] { "id", "code" });

        clu.getOfficialIdentifier().setCode(
                new StringBuilder().append(
                        cluInfo.getOfficialIdentifier().getDivision())
                        .append(
                                cluInfo.getOfficialIdentifier()
                                        .getSuffixCode()).toString());
    }

	public static List<CluIdentifier> createAlternateIdentifiers(CluInfo cluInfo) {
	    List<CluIdentifier> alternateIdentifiers = new ArrayList<CluIdentifier>(0);
	    for (CluIdentifierInfo cluIdInfo : cluInfo.getAlternateIdentifiers()) {
	        CluIdentifier identifier = new CluIdentifier();
	        BeanUtils.copyProperties(cluIdInfo, identifier,
	                new String[] { "code" });

	        identifier.setCode(new StringBuilder().append(
	                cluIdInfo.getDivision()).append(cluIdInfo.getSuffixCode())
	                .toString());
	        alternateIdentifiers.add(identifier);
	    }
	    return alternateIdentifiers;
	}

    public static void updateAlternateIdentifier(Map<String, CluIdentifier> oldAltIdMap, Clu clu, CluInfo cluInfo) {
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
    }
}
