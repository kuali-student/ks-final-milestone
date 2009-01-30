package org.kuali.student.core.organization.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.core.organization.dto.OrgHierarchyInfo;
import org.kuali.student.core.organization.dto.OrgInfo;
import org.kuali.student.core.organization.dto.OrgOrgRelationInfo;
import org.kuali.student.core.organization.dto.OrgOrgRelationTypeInfo;
import org.kuali.student.core.organization.dto.OrgPersonRelationInfo;
import org.kuali.student.core.organization.dto.OrgPersonRelationTypeInfo;
import org.kuali.student.core.organization.dto.OrgPositionRestrictionInfo;
import org.kuali.student.core.organization.dto.OrgTypeInfo;
import org.kuali.student.core.organization.entity.Org;
import org.kuali.student.core.organization.entity.OrgHierarchy;
import org.kuali.student.core.organization.entity.OrgOrgRelation;
import org.kuali.student.core.organization.entity.OrgOrgRelationType;
import org.kuali.student.core.organization.entity.OrgPersonRelation;
import org.kuali.student.core.organization.entity.OrgPersonRelationType;
import org.kuali.student.core.organization.entity.OrgPositionRestriction;
import org.kuali.student.core.organization.entity.OrgType;
import org.kuali.student.core.service.impl.BaseAssembler;
import org.springframework.beans.BeanUtils;

public class OrganizationAssembler extends BaseAssembler{

	public static List<OrgHierarchyInfo> toOrgHierarchyInfos(
			List<OrgHierarchy> orgHierarchys) {
		List<OrgHierarchyInfo> orgHierarchyInfos = new ArrayList<OrgHierarchyInfo>(orgHierarchys.size());
		for(OrgHierarchy orgHierarchy:orgHierarchys){
			orgHierarchyInfos.add(toOrgHierarchyInfo(orgHierarchy));
		}
		return orgHierarchyInfos;
	}

	public static OrgHierarchyInfo toOrgHierarchyInfo(OrgHierarchy orgHierarchy) {
		OrgHierarchyInfo orgHierarchyInfo = new OrgHierarchyInfo();

		BeanUtils.copyProperties(orgHierarchy, orgHierarchyInfo, new String[] { "rootOrgId",
				"attributes" });

		// copy attributes and RootOrg
		orgHierarchyInfo.setAttributes(toAttributeMap(orgHierarchy.getAttributes()));
		orgHierarchyInfo.setRootOrgId(orgHierarchy.getRootOrg().getId());

		return orgHierarchyInfo;
	}

	public static List<OrgInfo> toOrgInfos(List<Org> orgs) {
		List<OrgInfo> orgInfos = new ArrayList<OrgInfo>(orgs.size());
		for(Org org:orgs){
			orgInfos.add(toOrgInfo(org));
		}
		return orgInfos;
	}

	public static OrgInfo toOrgInfo(Org org) {
		OrgInfo orgInfo = new OrgInfo();

		BeanUtils.copyProperties(org, orgInfo, new String[] { "type",
				"attributes", "metaInfo" });

		// copy attributes, metadata, and Type
		orgInfo.setAttributes(toAttributeMap(org.getAttributes()));
		orgInfo.setMetaInfo(toMetaInfo(org.getMeta(), org.getVersionInd()));
		orgInfo.setType(org.getType().getKey());

		return orgInfo;
	}

	public static List<OrgPersonRelationInfo> toOrgPersonRelationInfos(List<OrgPersonRelation> relations) {
		List<OrgPersonRelationInfo> relationInfos = new ArrayList<OrgPersonRelationInfo>(relations.size());
		for (OrgPersonRelation relation : relations) {
			relationInfos.add(toOrgPersonRelationInfo(relation));
		}
		return relationInfos;
	}

	public static OrgPersonRelationInfo toOrgPersonRelationInfo(OrgPersonRelation relation) {
		OrgPersonRelationInfo relationInfo = new OrgPersonRelationInfo();

		BeanUtils.copyProperties(relation, relationInfo, new String[] { "type",
				"attributes", "metaInfo", "orgId"});

		relationInfo.setOrgId(relation.getOrg().getId());
		relationInfo.setAttributes(toAttributeMap(relation.getAttributes()));
		relationInfo.setMetaInfo(toMetaInfo(relation.getMeta(), relation.getVersionInd()));
		relationInfo.setType(relation.getType().getKey());
		relationInfo.setId(relation.getId());
		return relationInfo;
	}

	public static List<OrgOrgRelationInfo> toOrgOrgRelationInfos(
			List<OrgOrgRelation> orgOrgRelations) {
		List<OrgOrgRelationInfo> orgOrgRelationInfo = new ArrayList<OrgOrgRelationInfo>();
		for(OrgOrgRelation orgOrgRelation:orgOrgRelations){
			orgOrgRelationInfo.add(toOrgOrgRelationInfo(orgOrgRelation));
		}
		return orgOrgRelationInfo;
	}

	public static OrgOrgRelationInfo toOrgOrgRelationInfo(
			OrgOrgRelation orgOrgRelation) {
		OrgOrgRelationInfo orgOrgRelationInfo = new OrgOrgRelationInfo();

		BeanUtils.copyProperties(orgOrgRelation, orgOrgRelationInfo, new String[] { "type",
				"attributes", "metaInfo","orgId","relatedOrgId" });

		// copy attributes, metadata, Type, and related orgs
		orgOrgRelationInfo.setAttributes(toAttributeMap(orgOrgRelation.getAttributes()));
		orgOrgRelationInfo.setMetaInfo(toMetaInfo(orgOrgRelation.getMeta(), orgOrgRelation.getVersionInd()));
		orgOrgRelationInfo.setType(orgOrgRelation.getType().getKey());
		orgOrgRelationInfo.setOrgId(orgOrgRelation.getOrg().getId());
		orgOrgRelationInfo.setRelatedOrgId(orgOrgRelation.getRelatedOrg().getId());

		return orgOrgRelationInfo;
	}

	public static OrgPositionRestrictionInfo toOrgPositionRestrictionInfo(OrgPositionRestriction restriction) {
		OrgPositionRestrictionInfo restrictionInfo = new OrgPositionRestrictionInfo();

		BeanUtils.copyProperties(restriction, restrictionInfo, new String[] { "attributes", "metaInfo","orgId","personRelationType" });

		restrictionInfo.setOrgId(restriction.getOrg().getId());
		restrictionInfo.setAttributes(toAttributeMap(restriction.getAttributes()));
		restrictionInfo.setMetaInfo(toMetaInfo(restriction.getMeta(), restriction.getVersionInd()));
		// restrictionInfo.setOrgPersonRelationTypeKey(restriction.getPersonRelationType().getKey());

		return restrictionInfo;

	}
	public static List<OrgPositionRestrictionInfo> toOrgPositionRestrictionInfos(List<OrgPositionRestriction> restrictions) {
		List<OrgPositionRestrictionInfo> restrictionInfos = new ArrayList<OrgPositionRestrictionInfo>(restrictions.size());
		for (OrgPositionRestriction restriction : restrictions) {
			restrictionInfos.add(toOrgPositionRestrictionInfo(restriction));
		}
		return restrictionInfos;
	}

	public static OrgTypeInfo toOrgTypeInfo(OrgType orgType) {
		return toGenericTypeInfo(OrgTypeInfo.class, orgType);
	}

	public static List<OrgTypeInfo> toOrgTypeInfos(List<OrgType> orgTypes) {
		List<OrgTypeInfo> orgTypeInfos = new ArrayList<OrgTypeInfo>(orgTypes.size());
		for (OrgType orgType : orgTypes) {
			orgTypeInfos.add(toOrgTypeInfo(orgType));
		}
		return orgTypeInfos;
	}

	public static OrgPersonRelationTypeInfo toOrgPersonRelationTypeInfo(OrgPersonRelationType orgPersonRelationType) {
		OrgPersonRelationTypeInfo orgPersonRelationTypeInfo = new OrgPersonRelationTypeInfo();
		BeanUtils.copyProperties(orgPersonRelationType, orgPersonRelationTypeInfo, new String[] { "attributes", "orgHierarchy"});

		orgPersonRelationTypeInfo.setAttributes(toAttributeMap(orgPersonRelationType.getAttributes()));
		return orgPersonRelationTypeInfo;
	}

	public static List<OrgPersonRelationTypeInfo> toOrgPersonRelationTypeInfos(List<OrgPersonRelationType> orgPersonRelationTypes) {
		List<OrgPersonRelationTypeInfo> oprtys = new ArrayList<OrgPersonRelationTypeInfo>(orgPersonRelationTypes.size());
		for (OrgPersonRelationType type : orgPersonRelationTypes) {
			oprtys.add(toOrgPersonRelationTypeInfo(type));
		}
		return oprtys;
	}

	public static OrgOrgRelationTypeInfo toOrgOrgRelationTypeInfo(OrgOrgRelationType orgOrgRelationType) {
		OrgOrgRelationTypeInfo orgOrgRelationTypeInfo = new OrgOrgRelationTypeInfo();
		BeanUtils.copyProperties(orgOrgRelationType, orgOrgRelationTypeInfo, new String[] { "attributes", "orgHierarchy"});

		orgOrgRelationTypeInfo.setAttributes(toAttributeMap(orgOrgRelationType.getAttributes()));
		orgOrgRelationTypeInfo.setOrgHierarchyKey(orgOrgRelationType.getOrgHierarchy().getKey());
		return orgOrgRelationTypeInfo;
	}

	public static List<OrgOrgRelationTypeInfo> toOrgOrgRelationTypeInfos(List<OrgOrgRelationType> orgOrgRelationTypes) {
		List<OrgOrgRelationTypeInfo> orgOrgRelationTypeInfos = new ArrayList<OrgOrgRelationTypeInfo>(orgOrgRelationTypes.size());
		for (OrgOrgRelationType orgOrgRelationType : orgOrgRelationTypes) {
			orgOrgRelationTypeInfos.add(toOrgOrgRelationTypeInfo(orgOrgRelationType));
		}

		return orgOrgRelationTypeInfos;
	}
}
