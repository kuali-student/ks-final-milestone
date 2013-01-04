/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.r2.lum.lu.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


import org.kuali.student.r1.common.entity.Amount;
import org.kuali.student.r1.common.entity.CurrencyAmount;
import org.kuali.student.r1.common.entity.TimeAmount;
import org.kuali.student.r2.common.dto.AmountInfo;
import org.kuali.student.r2.common.dto.CurrencyAmountInfo;
import org.kuali.student.r2.common.dto.TimeAmountInfo;

import org.kuali.student.r2.common.dto.TypeInfo;
import org.kuali.student.r2.lum.clu.dto.AccreditationInfo;
import org.kuali.student.r2.lum.clu.dto.AdminOrgInfo;
import org.kuali.student.r2.lum.clu.dto.AffiliatedOrgInfo;
import org.kuali.student.r2.lum.clu.dto.CluAccountingInfo;
import org.kuali.student.r2.lum.clu.dto.CluCluRelationInfo;
import org.kuali.student.r2.lum.clu.dto.CluCreditInfo;
import org.kuali.student.r2.lum.clu.dto.CluFeeInfo;
import org.kuali.student.r2.lum.clu.dto.CluFeeRecordInfo;
import org.kuali.student.r2.lum.clu.dto.CluIdentifierInfo;
import org.kuali.student.r2.lum.clu.dto.CluInfo;
import org.kuali.student.r2.lum.clu.dto.CluInstructorInfo;
import org.kuali.student.r2.lum.clu.dto.CluLoRelationInfo;
import org.kuali.student.r2.lum.clu.dto.CluPublicationInfo;
import org.kuali.student.r2.lum.clu.dto.CluResultInfo;
import org.kuali.student.r2.lum.clu.dto.CluSetInfo;
import org.kuali.student.r2.lum.clu.dto.FieldInfo;
import org.kuali.student.r2.lum.clu.dto.LuCodeInfo;
import org.kuali.student.r2.lum.clu.dto.MembershipQueryInfo;
import org.kuali.student.r2.lum.clu.dto.ResultOptionInfo;


import org.kuali.student.r2.lum.lu.dao.LuDao;
import org.kuali.student.r2.lum.lu.entity.AffiliatedOrg;
import org.kuali.student.r2.lum.lu.entity.Clu;
import org.kuali.student.r2.lum.lu.entity.CluAccounting;
import org.kuali.student.r2.lum.lu.entity.CluAccreditation;
import org.kuali.student.r2.lum.lu.entity.CluAdminOrg;
import org.kuali.student.r2.lum.lu.entity.CluAtpTypeKey;
import org.kuali.student.r2.lum.lu.entity.CluCampusLocation;
import org.kuali.student.r2.lum.lu.entity.CluCluRelation;
import org.kuali.student.r2.lum.lu.entity.CluCredit;
import org.kuali.student.r2.lum.lu.entity.CluFee;
import org.kuali.student.r2.lum.lu.entity.CluFeeAmount;
import org.kuali.student.r2.lum.lu.entity.CluFeeAttribute;
import org.kuali.student.r2.lum.lu.entity.CluFeeRecord;
import org.kuali.student.r2.lum.lu.entity.CluFeeRecordAttribute;
import org.kuali.student.r2.lum.lu.entity.CluIdentifier;
import org.kuali.student.r2.lum.lu.entity.CluIdentifierAttribute;
import org.kuali.student.r2.lum.lu.entity.CluInstructor;
import org.kuali.student.r2.lum.lu.entity.CluLoRelation;
import org.kuali.student.r2.lum.lu.entity.CluLoRelationType;
import org.kuali.student.r2.lum.lu.entity.CluPublication;
import org.kuali.student.r2.lum.lu.entity.CluPublicationVariant;
import org.kuali.student.r2.lum.lu.entity.CluResult;
import org.kuali.student.r2.lum.lu.entity.CluResultType;
import org.kuali.student.r2.lum.lu.entity.CluSet;
import org.kuali.student.r2.lum.lu.entity.CluSetAttribute;
import org.kuali.student.r2.lum.lu.entity.CluSetJoinVersionIndClu;
import org.kuali.student.r2.lum.lu.entity.CluSetType;
import org.kuali.student.r2.lum.lu.entity.DeliveryMethodType;
import org.kuali.student.r2.lum.lu.entity.InstructionalFormatType;
import org.kuali.student.r2.lum.lu.entity.LuCode;
import org.kuali.student.r2.lum.lu.entity.LuCodeType;
import org.kuali.student.r2.lum.lu.entity.LuLuRelationType;
import org.kuali.student.r2.lum.lu.entity.LuPublicationType;
import org.kuali.student.r2.lum.lu.entity.LuRichText;
import org.kuali.student.r2.lum.lu.entity.LuType;
import org.kuali.student.r2.lum.lu.entity.MembershipQuery;
import org.kuali.student.r2.lum.lu.entity.ResultOption;
import org.kuali.student.r2.lum.lu.entity.ResultUsageType;
import org.kuali.student.r2.lum.lu.entity.SearchParameter;
import org.kuali.student.r2.lum.lu.entity.SearchParameterValue;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.service.assembly.BaseAssembler;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.infc.SearchParam;
import org.springframework.beans.BeanUtils;

public class CluServiceAssembler extends BaseAssembler {

    public static List<TypeInfo> toCluLoRelationTypeInfos(
            List<CluLoRelationType> entities) {
        return toGenericTypeInfoList(CluLoRelationInfo.class, entities);
    }

    public static TypeInfo toCluLoRelationTypeInfo(
            CluLoRelationType entity) {
        return toGenericTypeInfo(CluLoRelationInfo.class, entity);
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
                new String[]{"cluId", "relatedCluId", "cluRelationRequired",
                        "attributes", "meta"});

        dto.setIsCluRelationRequired(entity.isCluRelationRequired());
        dto.setCluId(entity.getClu().getId());
        dto.setRelatedCluId(entity.getRelatedClu().getId());
        dto.setTypeKey(entity.getLuLuRelationType().getId());
        dto.setAttributes(toAttributeMap(entity.getAttributes()));
        dto.setMeta(toMetaInfo(entity.getMeta(), entity.getVersionNumber()));

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
        BeanUtils.copyProperties(entity, dto, new String[]{"cluId",
                "attributes", "meta", "type"});

        dto.setCluId(entity.getClu().getId());
        dto.setAttributes(toAttributeMap(entity.getAttributes()));
        dto.setMeta(toMetaInfo(entity.getMeta(), entity.getVersionNumber()));
        dto.setTypeKey(entity.getType().getId());

        return dto;
    }

    public static List<CluInfo> toCluInfos(List<Clu> entities) {

        // return an empty list (Effective Java 2ndEd, item #43)
        if (entities == null) {
            return new ArrayList<CluInfo>(0);
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
        BeanUtils.copyProperties(entity, dto, new String[]{
                "officialIdentifier", "alternateIdentifiers", "descr",
                "participatingOrgs", "primaryInstructor", "instructors",
                "stdDuration", "luCodes", "credit", "offeredAtpTypes", "fee",
                "accounting", "intensity",
                "campusLocationList", "accreditationList",
                "adminOrgs", "attributes", "meta", "versionInfo"});
        dto.setOfficialIdentifier(toCluIdentifierInfo(entity.getOfficialIdentifier()));
        dto.setAlternateIdentifiers(toCluIdentifierInfos(entity.getAlternateIdentifiers()));
        dto.setDescr(toRichTextInfo(entity.getDescr()));

        // accreditingOrg Deprecated in v 1.0-rc2 Replaced by Primary and
        // Alternate admin orgs
        dto.setAccreditations(toAccreditationInfos(entity.getAccreditations()));

        dto.setAdminOrgs(toCluAdminOrgInfos(entity.getAdminOrgs()));

        dto.setPrimaryInstructor(toCluInstructorInfo(entity.getPrimaryInstructor()));
        dto.setInstructors(toCluInstructorInfos(entity.getInstructors()));
        dto.setStdDuration(toTimeAmountInfo(entity.getStdDuration()));
        dto.setLuCodes(toLuCodeInfos(entity.getLuCodes()));

        if (entity.getOfferedAtpTypes() != null) {
            List<String> offeredAtpTypes = new ArrayList<String>(entity.getOfferedAtpTypes().size());
            for (CluAtpTypeKey key : entity.getOfferedAtpTypes()) {
                offeredAtpTypes.add(key.getAtpTypeKey());
            }
            dto.setOfferedAtpTypes(offeredAtpTypes);
        }

        dto.setFeeInfo(toCluFeeInfo(entity.getFee()));
        dto.setAccountingInfo(toCluAccountingInfo(entity.getAccounting()));

        dto.setAttributes(toAttributeMap(entity.getAttributes()));
        dto.setMeta(toMetaInfo(entity.getMeta(), entity.getVersionNumber()));

        dto.setTypeKey(entity.getLuType().getId());

        if (entity.getCampusLocations() != null) {
            List<String> campusLocations = new ArrayList<String>(entity.getCampusLocations().size());
            for (CluCampusLocation cluCamp : entity.getCampusLocations()) {
                campusLocations.add(cluCamp.getCampusLocation());
            }
            dto.setCampusLocations(campusLocations);
        }

        dto.setIntensity(toAmountInfo(entity.getIntensity()));

        dto.setVersionInfo(toVersionInfo(entity.getVersion()));

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

        BeanUtils.copyProperties(cluSetInfo, cluSet, new String[]{"id",
                "descr", "attributes", "meta", "membershipQuery"});
        cluSet.setAttributes(toGenericAttributes(
                CluSetAttribute.class, cluSetInfo.getAttributes(), cluSet, luDao));
        cluSet.setType(cluSetInfo.getTypeKey());
        cluSet.setDescr(toRichText(LuRichText.class, cluSetInfo.getDescr()));

        for (String cluId : cluSetInfo.getCluIds()) {
            CluSetJoinVersionIndClu join = new CluSetJoinVersionIndClu();
            join.setCluSet(cluSet);
            join.setCluVersionIndId(cluId);
            cluSet.getCluVerIndIds().add(join);
        }
        for (String cluSetId : cluSetInfo.getCluSetIds()) {
            CluSet c;
            try {
                c = luDao.fetch(CluSet.class, cluSetId);
            } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException ex) {
                throw new DoesNotExistException(cluSetId);
            }
            if (cluSet.getCluSets() == null) {
                cluSet.setCluSets(new ArrayList<CluSet>());
            }
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

        BeanUtils.copyProperties(entity, dto, new String[]{"descr",
                "cluCriteria", "cluSets", "clus", "attributes", "meta", "membershipQuery"});

        dto.setDescr(toRichTextInfo(entity.getDescr()));
        // TODO dto.setCluCriteria()
        if (entity.getCluSets() != null) {
            List<String> cluSetIds = new ArrayList<String>(entity.getCluSets().size());
            for (CluSet id : entity.getCluSets()) {
                cluSetIds.add(id.getId());
            }
            dto.setCluSetIds(cluSetIds);
        }

        List<String> cluIds = new ArrayList<String>(entity.getCluVerIndIds().size());
        for (CluSetJoinVersionIndClu join : entity.getCluVerIndIds()) {
            cluIds.add(join.getCluVersionIndId());
        }
        dto.setCluIds(cluIds);

        dto.setAttributes(toAttributeMap(entity.getAttributes()));
        dto.setMeta(toMetaInfo(entity.getMeta(), entity.getVersionNumber()));

        MembershipQueryInfo mqInfo = toMembershipQueryInfo(entity.getMembershipQuery());
        dto.setMembershipQuery(mqInfo);
        dto.setIsReusable(entity.getIsReusable());
        dto.setIsReferenceable(entity.getIsReferenceable());

        return dto;
    }

    public static MembershipQueryInfo toMembershipQueryInfo(MembershipQuery entity) {
        if (entity == null) {
            return null;
        }
        MembershipQueryInfo dto = new MembershipQueryInfo();
        dto.setId(entity.getId());
        dto.setSearchTypeKey(entity.getSearchTypeKey());
        List<SearchParamInfo> list = new ArrayList<SearchParamInfo>();
        for (SearchParameter param : entity.getSearchParameters()) {
            SearchParamInfo sp = toSearchParam(param);
            list.add(sp);
        }
        dto.setQueryParamValues(list);

        return dto;
    }

    public static SearchParamInfo toSearchParam(SearchParameter entity) {
        SearchParamInfo dto = new SearchParamInfo();
        dto.setKey(entity.getKey());
        List<String> values = new ArrayList<String>();
        for (SearchParameterValue value : entity.getValues()) {
            values.add(value.getValue());
        }
        dto.setValues(values);
        return dto;
    }

    public static MembershipQuery toMembershipQueryEntity(MembershipQueryInfo dto) {
        if (dto == null) {
            return null;
        }
        MembershipQuery entity = new MembershipQuery();
        entity.setSearchTypeKey(dto.getSearchTypeKey());
        List<SearchParameter> list = new ArrayList<SearchParameter>();
        for (SearchParam param : dto.getQueryParamValues()) {
            SearchParameter sp = toSearchParameterEntity(param);
            list.add(sp);
        }
        entity.setSearchParameters(list);

        return entity;
    }

    public static SearchParameter toSearchParameterEntity(SearchParam dto) {
        if (dto == null) {
            return null;
        }
        SearchParameter entity = new SearchParameter();
        entity.setKey(dto.getKey());
        List<SearchParameterValue> values = new ArrayList<SearchParameterValue>();
        for (String s : dto.getValues()) {
            SearchParameterValue value = new SearchParameterValue();
            value.setValue(s);
            values.add(value);
        }
        entity.setValues(values);
        return entity;
    }

    public static List<TypeInfo> toResultUsageTypeInfos(
            List<ResultUsageType> entities) {
        return toGenericTypeInfoList(TypeInfo.class, entities);
    }

    public static TypeInfo toResultUsageTypeInfo(
            ResultUsageType entity) {
        return toGenericTypeInfo(TypeInfo.class, entity);
    }

    public static List<TypeInfo> toDeliveryMethodTypeInfos(
            List<DeliveryMethodType> entities) {
        return toGenericTypeInfoList(TypeInfo.class, entities);
    }

    public static TypeInfo toDeliveryMethodTypeInfo(
            DeliveryMethodType entity) {
        return toGenericTypeInfo(TypeInfo.class, entity);
    }

    public static List<TypeInfo> toInstructionalFormatTypeInfos(
            List<InstructionalFormatType> entities) {
        return toGenericTypeInfoList(TypeInfo.class,
                entities);
    }

    public static TypeInfo toInstructionalFormatTypeInfo(
            InstructionalFormatType entity) {
        return toGenericTypeInfo(TypeInfo.class, entity);
    }

    public static List<TypeInfo> toLuCodeTypeInfos(
            List<LuCodeType> entities) {
        return toGenericTypeInfoList(TypeInfo.class, entities);
    }

    public static TypeInfo toLuCodeTypeInfo(LuCodeType entity) {
        return toGenericTypeInfo(TypeInfo.class, entity);
    }

    public static List<TypeInfo> toCluResultTypeInfos(
            List<CluResultType> entities) {
        return toGenericTypeInfoList(TypeInfo.class, entities);
    }

    public static TypeInfo toCluResultTypeInfo(CluResultType entity) {
        return toGenericTypeInfo(TypeInfo.class, entity);
    }

    public static TypeInfo toCluSetTypeInfo(CluSetType entity) {
        return toGenericTypeInfo(TypeInfo.class, entity);
    }

    public static List<TypeInfo> toCluSetTypeInfos(
            List<CluSetType> entities) {
        return toGenericTypeInfoList(TypeInfo.class, entities);
    }

    public static CluResultInfo toCluResultInfo(CluResult entity) {
        if (entity == null) {
            return null;
        }

        CluResultInfo dto = new CluResultInfo();

        BeanUtils.copyProperties(entity, dto, new String[]{"resultOptions", "desc"});

        List<ResultOptionInfo> resultOptions = toResultOptionInfos(entity.getResultOptions());
        dto.setResultOptions(resultOptions);
        dto.setDescr(toRichTextInfo(entity.getDesc()));
        dto.setCluId(entity.getClu().getId());
        TypeInfo type = toCluResultTypeInfo(entity.getCluResultType());
        if (type != null) {
            dto.setTypeKey(type.getKey());
        }
        dto.setMeta(entity.toDTO());

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

        BeanUtils.copyProperties(entity, dto, new String[]{"resultUsageType", "desc"});
        if (entity.getResultUsageType() != null) {
            dto.setResultUsageTypeKey(entity.getResultUsageType().getId());
        }
        dto.setDescr(toRichTextInfo(entity.getDesc()));
        dto.setMeta(entity.toDTO());

        return dto;
    }

    public static List<TypeInfo> toLuLuRelationTypeInfos(
            List<LuLuRelationType> entities) {
        List<TypeInfo> dtos = new ArrayList<TypeInfo>(
                entities.size());
        if (entities != null) {
            for (LuLuRelationType entity : entities) {
                dtos.add(toLuLuRelationTypeInfo(entity));
            }
        }
        return dtos;

    }

    public static TypeInfo toLuLuRelationTypeInfo(
            LuLuRelationType entity) {
        if (entity == null) {
            return null;
        }
        TypeInfo dto = new TypeInfo();

        BeanUtils.copyProperties(entity, dto, new String[]{"attributes"});
        dto.setAttributes(toAttributeMap(entity.getAttributes()));

        return dto;
    }

    public static List<TypeInfo> toLuTypeInfos(List<LuType> entities) {
        return toGenericTypeInfoList(TypeInfo.class, entities);
    }

    public static TypeInfo toLuTypeInfo(LuType entity) {
        TypeInfo typeInfo = toGenericTypeInfo(TypeInfo.class, entity);
//		typeInfo.setDeliveryMethod(entity.getDeliveryMethod());
//		typeInfo.setInstructionalFormat(entity.getInstructionalFormat());

        return typeInfo;
    }

// TODO: CM2.0 MERGE :: removed lui methods (this is LU)
//    public static List<LuiInfo> toLuiInfos(List<Lui> entities) {
//        List<LuiInfo> dtos = new ArrayList<LuiInfo>(entities.size());
//        if (entities != null) {
//            for (Lui entity : entities) {
//                dtos.add(toLuiInfo(entity));
//            }
//        }
//        return dtos;
//
//    }
//
//    public static LuiInfo toLuiInfo(Lui entity) {
//        if (entity == null) {
//            return null;
//        }
//        LuiInfo luiInfo = new LuiInfo();
//
//        BeanUtils.copyProperties(entity, luiInfo, new String[]{"clu",
//                    "meta", "attributes"});
//
//        luiInfo.setCluId(entity.getClu().getId());
//
//        luiInfo.setMeta(toMetaInfo(entity.getMeta(), entity.getVersionNumber()));
//
//        luiInfo.setAttributes(toAttributeMap(entity.getAttributes()));
//
//        return luiInfo;
//    }
//
//    public static Lui toLui(boolean isUpdate, LuiInfo luiInfo, LuDao dao)
//            throws DoesNotExistException, VersionMismatchException,
//            InvalidParameterException {
//        if (luiInfo == null) {
//            return null;
//        }
//        Lui lui;
//
//        if (isUpdate) {
//            try {
//                lui = dao.fetch(Lui.class, luiInfo.getId());
//            } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException ex) {
//                throw new DoesNotExistException (luiInfo.getId());
//            }
//            if (null == lui) {
//                throw new DoesNotExistException((new StringBuilder()).append(
//                        "Lui does not exist for id: ").append(luiInfo.getId()).toString());
//            }
//            if (!String.valueOf(lui.getVersionNumber()).equals(
//                    luiInfo.getMeta().getVersionInd())) {
//                throw new VersionMismatchException(
//                        "Lui to be updated is not the current version");
//            }
//        } else {
//            lui = new Lui();
//        }
//
//        BeanUtils.copyProperties(luiInfo, lui, new String[]{"cluId",
//                    "attributes", "meta"});
//
//        lui.setAttributes(toGenericAttributes(LuiAttribute.class, luiInfo.getAttributes(), lui, dao));
//
//        Clu clu;
//        try {
//            clu = dao.fetch(Clu.class, luiInfo.getCluId());
//        } catch (org.kuali.student.r2.common.exceptions.DoesNotExistException ex) {
//            throw new DoesNotExistException (luiInfo.getCluId());
//        }
//        if (null == clu) {
//            throw new InvalidParameterException((new StringBuilder()).append(
//                    "Clu does not exist for id: ").append(luiInfo.getCluId()).toString());
//        }
//        lui.setClu(clu);
//        return lui;
//    }
//
//    public static List<LuiLuiRelationInfo> toLuiLuiRelationInfos(
//            List<LuiLuiRelation> entities) {
//        List<LuiLuiRelationInfo> dtos = new ArrayList<LuiLuiRelationInfo>(
//                entities.size());
//        if (entities != null) {
//            for (LuiLuiRelation entity : entities) {
//                dtos.add(toLuiLuiRelationInfo(entity));
//            }
//        }
//        return dtos;
//    }
//
//    public static LuiLuiRelationInfo toLuiLuiRelationInfo(LuiLuiRelation entity) {
//        if (entity == null) {
//            return null;
//        }
//        LuiLuiRelationInfo dto = new LuiLuiRelationInfo();
//
//        BeanUtils.copyProperties(entity, dto, new String[]{"lui",
//                    "relatedLui", "attributes"});
//
//        dto.setLuiId(entity.getLui().getId());
//        dto.setRelatedLuiId(entity.getRelatedLui().getId());
//        dto.setTypeKey(entity.getLuLuRelationType().getId());
//        dto.setAttributes(toAttributeMap(entity.getAttributes()));
//        dto.setMeta(toMetaInfo(entity.getMeta(), entity.getVersionNumber()));
//        return dto;
//    }

    public static List<CluIdentifierInfo> toCluIdentifierInfos(
            List<CluIdentifier> entities) {
        List<CluIdentifierInfo> dtos = new ArrayList<CluIdentifierInfo>(
                entities.size());
        if (entities != null) {
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

        BeanUtils.copyProperties(entity, dto,
                new String[]{"attributes"});
        dto.setAttributes(toAttributeMap(entity.getAttributes()));
        return dto;
    }

    public static List<CluInstructorInfo> toCluInstructorInfos(
            List<CluInstructor> entities) {
        List<CluInstructorInfo> dtos = new ArrayList<CluInstructorInfo>(
                entities.size());
        if (entities != null) {
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
                new String[]{"id", "attributes"});
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
        if (entities != null) {
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

        BeanUtils.copyProperties(entity, dto, new String[]{"attributes",
                "metInfo"});

        dto.setAttributes(toAttributeMap(entity.getAttributes()));
        dto.setMeta(toMetaInfo(entity.getMeta(), entity.getVersionNumber()));

        return dto;
    }

    public static CluCreditInfo toCluCreditInfos(CluCredit entity) {
        if (entity == null) {
            return null;
        }
        CluCreditInfo dto = new CluCreditInfo();

        BeanUtils.copyProperties(entity, dto, new String[]{"id",
                "repeatTime", "minTimeToComplete", "maxTimeToComplete",
                "maxAllowableInactivity", "maxTimeResultsRecognized"});
        dto.setRepeatTime(toTimeAmountInfo(entity.getRepeatTime()));
        dto.setMinTimeToComplete(toTimeAmountInfo(entity.getMinTimeToComplete()));
        dto.setMaxTimeToComplete(toTimeAmountInfo(entity.getMaxTimeToComplete()));
        dto.setMaxAllowableInactivity(toTimeAmountInfo(entity.getMaxAllowableInactivity()));
        dto.setMaxTimeResultsRecognized(toTimeAmountInfo(entity.getMaxTimeResultsRecognized()));

        return dto;
    }

    public static List<TypeInfo> toResultComponentTypeInfo(
            List<String> componentIds) {
        List<TypeInfo> dtos = new ArrayList<TypeInfo>();
        if (componentIds != null) {
            for (String id : componentIds) {
                TypeInfo comp = new TypeInfo();
                comp.setKey(id);
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
        dto.setMeta(toMetaInfo(entity.getMeta(), entity.getVersionNumber()));

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
                new String[]{"affiliatedOrgs", "currencyAmount", "attributes", "descr"});

        dto.setAffiliatedOrgs(toAffiliatedOrgInfos(entity.getAffiliatedOrgs()));
        dto.setFeeAmounts(toFeeAmounts(entity.getFeeAmounts()));
        dto.setDescr(toRichTextInfo(entity.getDescr()));
        dto.setAttributes(toAttributeMap(entity.getAttributes()));
        dto.setMeta(toMetaInfo(entity.getMeta(), entity.getVersionNumber()));

        return dto;
    }

    private static List<CurrencyAmountInfo> toFeeAmounts(List<CluFeeAmount> cluFees) {
        List<CurrencyAmountInfo> feeAmounts = new ArrayList<CurrencyAmountInfo>();

        if (cluFees != null) {
            for (CluFeeAmount cluFeeAmount : cluFees) {
                CurrencyAmountInfo dto = new CurrencyAmountInfo();
                CurrencyAmount ca = cluFeeAmount.getCurrencyAmount();
                if (ca != null) {
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

        cluCredit.setMaxAllowableInactivity(CluServiceAssembler.toTimeAmount(cluCreditInfo.getMaxAllowableInactivity()));
        cluCredit.setMaxTimeResultsRecognized(CluServiceAssembler.toTimeAmount(cluCreditInfo.getMaxTimeResultsRecognized()));
        cluCredit.setMaxTimeToComplete(CluServiceAssembler.toTimeAmount(cluCreditInfo.getMaxTimeToComplete()));
        cluCredit.setMinTimeToComplete(CluServiceAssembler.toTimeAmount(cluCreditInfo.getMinTimeToComplete()));
        cluCredit.setRepeatTime(CluServiceAssembler.toTimeAmount(cluCreditInfo.getRepeatTime()));

        BeanUtils.copyProperties(cluCreditInfo, cluCredit, new String[]{
                "repeatTime", "minTimeToComplete", "maxTimeToComplete",
                "maxAllowableInactivity", "maxTimeResultsRecognized"});

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
        BeanUtils.copyProperties(cluCreditInfo.getMaxTimeToComplete(), entity.getMaxTimeToComplete());

        if (entity.getMinTimeToComplete() == null) {
            entity.setMinTimeToComplete(new TimeAmount());
        }
        BeanUtils.copyProperties(cluCreditInfo.getMinTimeToComplete(), entity.getMinTimeToComplete());

        if (entity.getRepeatTime() == null) {
            entity.setRepeatTime(new TimeAmount());
        }
        BeanUtils.copyProperties(cluCreditInfo.getRepeatTime(), entity.getRepeatTime());

        BeanUtils.copyProperties(cluCreditInfo, entity, new String[]{
                "repeatTime", "minTimeToComplete", "maxTimeToComplete",
                "maxAllowableInactivity", "maxTimeResultsRecognized"});

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

        BeanUtils.copyProperties(entity, dto, new String[]{"attributes"});

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
        BeanUtils.copyProperties(entity, dto, new String[]{"attributes"});
        dto.setAttributes(toAttributeMap(entity.getAttributes()));

        return dto;
    }

    public static List<TypeInfo> toLuPublicationTypeInfos(
            List<LuPublicationType> entities) {
        return toGenericTypeInfoList(TypeInfo.class, entities);
    }

    public static TypeInfo toLuPublicationTypeInfo(
            LuPublicationType entity) {
        return toGenericTypeInfo(TypeInfo.class, entity);
    }

    public static CluFee toCluFee(Clu clu, boolean isUpdate, CluFeeInfo feeInfo,
                                  LuDao dao) throws DoesNotExistException, VersionMismatchException,
            InvalidParameterException {
        if (feeInfo == null) {
            return null;
        }

        CluFee fee = null;

        if (isUpdate) {
            fee = clu.getFee();
            if (!String.valueOf(fee.getVersionNumber()).equals(
                    feeInfo.getMeta().getVersionInd())) {
                throw new VersionMismatchException(
                        "CluFee to be updated is not the current version");
            }
        }
        if (fee == null) {
            fee = new CluFee();
        }

        if (feeInfo.getDescr() != null && (feeInfo.getDescr().getPlain() != null || feeInfo.getDescr().getFormatted() != null)) {
            if (fee.getDescr() == null) {
                fee.setDescr(new LuRichText());
            }
            BeanUtils.copyProperties(feeInfo.getDescr(), fee.getDescr());
        } else if (isUpdate && fee.getDescr() != null) {
            dao.delete(fee.getDescr());
            fee.setDescr(null);
        }

        fee.setAttributes(CluServiceAssembler.toGenericAttributes(
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
                feeRec.setAttributes(CluServiceAssembler.toGenericAttributes(
                        CluFeeRecordAttribute.class, feeRecordInfo.getAttributes(), feeRec, dao));
                if (cluFee.getCluFeeRecords() == null) {
                    cluFee.setCluFeeRecords(new ArrayList<CluFeeRecord>());
                }
                cluFee.getCluFeeRecords().add(feeRec);
            }
        } else {
            Map<String, CluFeeRecord> oldFeeRecMap = new HashMap<String, CluFeeRecord>();
            if (cluFee.getCluFeeRecords() != null) {
                for (CluFeeRecord feeRec : cluFee.getCluFeeRecords()) {
                    oldFeeRecMap.put(feeRec.getId(), feeRec);
                }
                cluFee.getCluFeeRecords().clear();
            }

            // Loop through the new list, if the item exists already update and
            // remove from the list
            // otherwise create a new entry
            for (CluFeeRecordInfo feeRecordInfo : cluFeeRecords) {
                CluFeeRecord feeRec = oldFeeRecMap.remove(feeRecordInfo.getId());
                if (feeRec == null) {
                    feeRec = new CluFeeRecord();
                }

                feeRec.setAffiliatedOrgs(toAffiliatedOrgs(isUpdate, feeRec.getAffiliatedOrgs(), feeRecordInfo.getAffiliatedOrgs(), dao));
                feeRec.setFeeType(feeRecordInfo.getFeeType());
                feeRec.setRateType(feeRecordInfo.getRateType());
                feeRec.setDescr(toRichText(LuRichText.class, feeRecordInfo.getDescr()));
                feeRec.setFeeAmounts(toFeeAmounts(isUpdate, feeRec.getFeeAmounts(), feeRecordInfo.getFeeAmounts(), dao));
                feeRec.setAttributes(CluServiceAssembler.toGenericAttributes(
                        CluFeeRecordAttribute.class, feeRecordInfo.getAttributes(), feeRec, dao));
                if (cluFee.getCluFeeRecords() == null) {
                    cluFee.setCluFeeRecords(new ArrayList<CluFeeRecord>());
                }
                cluFee.getCluFeeRecords().add(feeRec);
            }

            // Now delete anything left over
            for (Entry<String, CluFeeRecord> entry : oldFeeRecMap.entrySet()) {
                dao.delete(entry.getValue());
            }
        }
    }

    public static List<AffiliatedOrg> toAffiliatedOrgs(boolean isUpdate, List<AffiliatedOrg> orgList, List<AffiliatedOrgInfo> affiliatedOrgInfoList, LuDao dao) {
        if (null == affiliatedOrgInfoList) {
            return orgList;
        }
        if (orgList == null) {
            orgList = new ArrayList<AffiliatedOrg>();
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

    public static List<CluFeeAmount> toFeeAmounts(boolean isUpdate, List<CluFeeAmount> caList, List<CurrencyAmountInfo> caInfoList, LuDao dao) {
        if (null == caInfoList) {
            return caList;
        }
        if (caList == null) {
            caList = new ArrayList<CluFeeAmount>(caInfoList.size());
        }

        if (!isUpdate) {
            for (CurrencyAmountInfo caInfo : caInfoList) {
                CurrencyAmount ca = new CurrencyAmount();
                ca.setCurrencyQuantity(caInfo.getCurrencyQuantity());
                ca.setCurrencyTypeKey(caInfo.getCurrencyTypeKey());
                CluFeeAmount cluFeeAmount = new CluFeeAmount();
                cluFeeAmount.setCurrencyAmount(ca);
                caList.add(cluFeeAmount);
            }
        } else {
            // Delete existing fee amounts (this assumes feeAmounts are not individually updated)
            for (CluFeeAmount cluFeeAmount : caList) {
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

    public static CluIdentifier createOfficialIdentifier(CluInfo cluInfo, LuDao dao) throws InvalidParameterException {
        CluIdentifier officialIdentifier = new CluIdentifier();
        BeanUtils.copyProperties(cluInfo.getOfficialIdentifier(),
                officialIdentifier, new String[]{"attributes"});
        officialIdentifier.setAttributes(CluServiceAssembler.toGenericAttributes(
                CluIdentifierAttribute.class, cluInfo.getOfficialIdentifier().getAttributes(), officialIdentifier, dao));

        return officialIdentifier;
    }

    public static void updateOfficialIdentifier(Clu clu, CluInfo cluInfo, LuDao dao) throws InvalidParameterException {
        if (clu.getOfficialIdentifier() == null) {
            clu.setOfficialIdentifier(new CluIdentifier());
        }
        BeanUtils.copyProperties(cluInfo.getOfficialIdentifier(), clu.getOfficialIdentifier(), new String[]{"id", "attributes"});

        clu.getOfficialIdentifier().setAttributes(CluServiceAssembler.toGenericAttributes(
                CluIdentifierAttribute.class, cluInfo.getOfficialIdentifier().getAttributes(), clu.getOfficialIdentifier(), dao));

    }

    public static List<CluIdentifier> createAlternateIdentifiers(CluInfo cluInfo, LuDao dao) throws InvalidParameterException {
        List<CluIdentifier> alternateIdentifiers = new ArrayList<CluIdentifier>(0);
        for (CluIdentifierInfo cluIdInfo : cluInfo.getAlternateIdentifiers()) {
            CluIdentifier identifier = new CluIdentifier();
            BeanUtils.copyProperties(cluIdInfo, identifier, new String[]{"attributes"});

            identifier.setAttributes(CluServiceAssembler.toGenericAttributes(
                    CluIdentifierAttribute.class, cluIdInfo.getAttributes(), identifier, dao));

            alternateIdentifiers.add(identifier);
        }
        return alternateIdentifiers;
    }

    public static void updateAlternateIdentifier(Map<String, CluIdentifier> oldAltIdMap, Clu clu, CluInfo cluInfo, LuDao dao) throws InvalidParameterException {
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
            BeanUtils.copyProperties(cluIdInfo, identifier, new String[]{"attributes"});

            identifier.setAttributes(CluServiceAssembler.toGenericAttributes(
                    CluIdentifierAttribute.class, cluIdInfo.getAttributes(), identifier, dao));

            clu.getAlternateIdentifiers().add(identifier);
        }
    }

    public static List<CluPublicationVariant> toCluPublicationVariants(
            List<FieldInfo> variantInfos, CluPublication cluPub, LuDao luDao) {
        List<CluPublicationVariant> variants = new ArrayList<CluPublicationVariant>();

        if (cluPub.getVariants() == null) {
            cluPub.setVariants(new ArrayList<CluPublicationVariant>());
        }

        // Delete all the old attributes(if the owner is not null)
        for (CluPublicationVariant variant : cluPub.getVariants()) {
            luDao.delete(variant);
        }
        cluPub.getVariants().clear();

        for (FieldInfo variantInfo : variantInfos) {
            CluPublicationVariant variant = new CluPublicationVariant();
            variant.setKey(variantInfo.getId());
            variant.setValue(variantInfo.getValue());
            variant.setOwner(cluPub);
            variants.add(variant);
        }

        return variants;
    }

    public static CluPublicationInfo toCluPublicationInfo(CluPublication cluPub) {
        if (cluPub == null) {
            return null;
        }
        CluPublicationInfo cluPubInfo = new CluPublicationInfo();
        cluPubInfo.setCluId(cluPub.getClu().getId());
        cluPubInfo.setId(cluPub.getId());
        cluPubInfo.setEndCycle(cluPub.getEndCycle());
        cluPubInfo.setStartCycle(cluPub.getStartCycle());
        cluPubInfo.setEffectiveDate(cluPub.getEffectiveDate());
        cluPubInfo.setExpirationDate(cluPub.getExpirationDate());
        cluPubInfo.setStateKey(cluPub.getState());
        cluPubInfo.setTypeKey(cluPub.getType().getId());
        cluPubInfo.setMeta(toMetaInfo(cluPub.getMeta(), cluPub.getVersionNumber()));
        cluPubInfo.setAttributes(CluServiceAssembler.toAttributeMap(cluPub.getAttributes()));
        cluPubInfo.setVariants(CluServiceAssembler.toCluPublicationVariantInfos(cluPub.getVariants()));

        return cluPubInfo;
    }

    private static List<FieldInfo> toCluPublicationVariantInfos(
            List<CluPublicationVariant> variants) {
        if (variants == null) {
            return new ArrayList<FieldInfo>(0);
        }
        List<FieldInfo> fields = new ArrayList<FieldInfo>();
        for (CluPublicationVariant variant : variants) {
            FieldInfo field = new FieldInfo();
            field.setId(variant.getKey());
            field.setValue(variant.getValue());
            fields.add(field);
        }
        return fields;
    }

    public static List<CluPublicationInfo> toCluPublicationInfos(
            List<CluPublication> cluPublications) {
        if (cluPublications == null) {
            return new ArrayList<CluPublicationInfo>(0);
        }
        List<CluPublicationInfo> cluPublicationInfos = new ArrayList<CluPublicationInfo>(cluPublications.size());
        for (CluPublication cluPublication : cluPublications) {
            cluPublicationInfos.add(toCluPublicationInfo(cluPublication));
        }
        return cluPublicationInfos;
    }
}
