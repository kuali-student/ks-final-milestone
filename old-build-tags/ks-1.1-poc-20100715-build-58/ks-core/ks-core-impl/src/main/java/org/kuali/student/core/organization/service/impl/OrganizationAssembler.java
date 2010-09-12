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

package org.kuali.student.core.organization.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.core.dto.TimeAmountInfo;
import org.kuali.student.core.entity.TimeAmount;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.core.organization.dao.OrganizationDao;
import org.kuali.student.core.organization.dto.OrgHierarchyInfo;
import org.kuali.student.core.organization.dto.OrgInfo;
import org.kuali.student.core.organization.dto.OrgOrgRelationInfo;
import org.kuali.student.core.organization.dto.OrgOrgRelationTypeInfo;
import org.kuali.student.core.organization.dto.OrgPersonRelationInfo;
import org.kuali.student.core.organization.dto.OrgPersonRelationTypeInfo;
import org.kuali.student.core.organization.dto.OrgPositionRestrictionInfo;
import org.kuali.student.core.organization.dto.OrgTypeInfo;
import org.kuali.student.core.organization.entity.Org;
import org.kuali.student.core.organization.entity.OrgAttribute;
import org.kuali.student.core.organization.entity.OrgHierarchy;
import org.kuali.student.core.organization.entity.OrgOrgRelation;
import org.kuali.student.core.organization.entity.OrgOrgRelationAttribute;
import org.kuali.student.core.organization.entity.OrgOrgRelationType;
import org.kuali.student.core.organization.entity.OrgPersonRelation;
import org.kuali.student.core.organization.entity.OrgPersonRelationAttribute;
import org.kuali.student.core.organization.entity.OrgPersonRelationType;
import org.kuali.student.core.organization.entity.OrgPositionRestriction;
import org.kuali.student.core.organization.entity.OrgPositionRestrictionAttribute;
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
		if (orgHierarchy == null) {
			return null;
		}
		
		OrgHierarchyInfo orgHierarchyInfo = new OrgHierarchyInfo();

		BeanUtils.copyProperties(orgHierarchy, orgHierarchyInfo, new String[] { "rootOrgId",
				"attributes" });

		// copy attributes and RootOrg
		orgHierarchyInfo.setAttributes(toAttributeMap(orgHierarchy.getAttributes()));
		orgHierarchyInfo.setRootOrgId(orgHierarchy.getRootOrg().getId());
		orgHierarchyInfo.setDescr(orgHierarchy.getDescr());
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
		if (org == null) {
			return null;
		}
		
		OrgInfo orgInfo = new OrgInfo();

		BeanUtils.copyProperties(org, orgInfo, new String[] { "type",
				"attributes", "metaInfo" });

		// copy attributes, metadata, and Type
		orgInfo.setAttributes(toAttributeMap(org.getAttributes()));
		orgInfo.setMetaInfo(toMetaInfo(org.getMeta(), org.getVersionInd()));
		orgInfo.setType(org.getType().getId());

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
		relationInfo.setType(relation.getType().getId());
		//relationInfo.setId(relation.getId());
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
		if (orgOrgRelation == null) {
			return null;
		}
		OrgOrgRelationInfo orgOrgRelationInfo = new OrgOrgRelationInfo();

		BeanUtils.copyProperties(orgOrgRelation, orgOrgRelationInfo, new String[] { "type",
				"attributes", "metaInfo","orgId","relatedOrgId" });

		// copy attributes, metadata, Type, and related orgs
		orgOrgRelationInfo.setAttributes(toAttributeMap(orgOrgRelation.getAttributes()));
		orgOrgRelationInfo.setMetaInfo(toMetaInfo(orgOrgRelation.getMeta(), orgOrgRelation.getVersionInd()));
		orgOrgRelationInfo.setType(orgOrgRelation.getType().getId());
		orgOrgRelationInfo.setOrgId(orgOrgRelation.getOrg().getId());
		orgOrgRelationInfo.setRelatedOrgId(orgOrgRelation.getRelatedOrg().getId());

		return orgOrgRelationInfo;
	}

	public static OrgPositionRestrictionInfo toOrgPositionRestrictionInfo(OrgPositionRestriction restriction) {
		OrgPositionRestrictionInfo restrictionInfo = new OrgPositionRestrictionInfo();

		BeanUtils.copyProperties(restriction, restrictionInfo, new String[] { "attributes", "metaInfo","orgId","personRelationType","stdDuration" });
		
		if(restriction.getStdDuration()!=null){
			restrictionInfo.setStdDuration(new TimeAmountInfo());
			BeanUtils.copyProperties(restriction.getStdDuration(), restrictionInfo.getStdDuration());
		}
		
		restrictionInfo.setOrgId(restriction.getOrg().getId());
		restrictionInfo.setAttributes(toAttributeMap(restriction.getAttributes()));
		restrictionInfo.setMetaInfo(toMetaInfo(restriction.getMeta(), restriction.getVersionInd()));
		restrictionInfo.setOrgPersonRelationTypeKey(restriction.getPersonRelationType().getId());
		restrictionInfo.setDesc(restriction.getDescr());
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
		if (orgOrgRelationType == null) {
			return null;
		}
		
		OrgOrgRelationTypeInfo orgOrgRelationTypeInfo = new OrgOrgRelationTypeInfo();
		BeanUtils.copyProperties(orgOrgRelationType, orgOrgRelationTypeInfo, new String[] { "attributes", "orgHierarchy"});

		orgOrgRelationTypeInfo.setAttributes(toAttributeMap(orgOrgRelationType.getAttributes()));
		orgOrgRelationTypeInfo.setOrgHierarchyKey(orgOrgRelationType.getOrgHierarchy().getId());
		return orgOrgRelationTypeInfo;
	}

	public static List<OrgOrgRelationTypeInfo> toOrgOrgRelationTypeInfos(List<OrgOrgRelationType> orgOrgRelationTypes) {
		List<OrgOrgRelationTypeInfo> orgOrgRelationTypeInfos = new ArrayList<OrgOrgRelationTypeInfo>(orgOrgRelationTypes.size());
		for (OrgOrgRelationType orgOrgRelationType : orgOrgRelationTypes) {
			orgOrgRelationTypeInfos.add(toOrgOrgRelationTypeInfo(orgOrgRelationType));
		}

		return orgOrgRelationTypeInfos;
	}

	public static Org toOrg(boolean isUpdate, OrgInfo orgInfo, OrganizationDao dao)
			throws InvalidParameterException, DoesNotExistException, VersionMismatchException {
		Org org;
		if (isUpdate) {
			org = dao.fetch(Org.class, orgInfo.getId());
			if (org == null) {
				throw new DoesNotExistException("Org does not exist for id: " + orgInfo.getId());
			}
			if (!String.valueOf(org.getVersionInd()).equals(orgInfo.getMetaInfo().getVersionInd())){
				throw new VersionMismatchException("Org to be updated is not the current version");
			}
		} else {
			org = new Org();
		}

		// Copy all basic properties
		BeanUtils.copyProperties(orgInfo, org, new String[] { "type",
				"attributes", "metaInfo", "orgPersonRelationTypes" });

		// Copy Attributes
		org.setAttributes(toGenericAttributes(OrgAttribute.class, orgInfo.getAttributes(), org, dao));

		// Search for and copy the type
		OrgType orgType = dao.fetch(OrgType.class, orgInfo.getType());
		if (orgType == null) {
			throw new InvalidParameterException(
					"OrgType does not exist for id: " + orgInfo.getType());
		}
		org.setType(orgType);

		return org;
	}

	public static OrgOrgRelation toOrgOrgRelation(boolean isUpdate,
			OrgOrgRelationInfo orgOrgRelationInfo,
			OrganizationDao dao) throws DoesNotExistException, VersionMismatchException, InvalidParameterException {
		OrgOrgRelation orgOrgRelation;
		if (isUpdate) {
			orgOrgRelation = dao.fetch(OrgOrgRelation.class, orgOrgRelationInfo.getId());
			if (orgOrgRelation == null) {
				throw new DoesNotExistException("OrgOrgRelation does not exist for id: " + orgOrgRelationInfo.getId());
			}
			if (!String.valueOf(orgOrgRelation.getVersionInd()).equals(orgOrgRelationInfo.getMetaInfo().getVersionInd())){
				throw new VersionMismatchException("OrgOrgRelation to be updated is not the current version");
			}
		} else {
			orgOrgRelation = new OrgOrgRelation();
		}

		// Copy all basic properties
		BeanUtils.copyProperties(orgOrgRelationInfo, orgOrgRelation, new String[] { "type",
				"attributes", "metaInfo", "org", "relatedOrg" });

		// Copy Attributes
		orgOrgRelation.setAttributes(toGenericAttributes(OrgOrgRelationAttribute.class, orgOrgRelationInfo.getAttributes(), orgOrgRelation, dao));

		// Search for and copy the org
		Org org = dao.fetch(Org.class, orgOrgRelationInfo.getOrgId());
		if (org == null) {
			throw new InvalidParameterException(
					"Org does not exist for id: " + orgOrgRelationInfo.getOrgId());
		}
		orgOrgRelation.setOrg(org);

		// Search for and copy the related org
		Org relatedOrg = dao.fetch(Org.class, orgOrgRelationInfo.getRelatedOrgId());
		if (relatedOrg == null) {
			throw new InvalidParameterException(
					"RelatedOrg does not exist for id: " + orgOrgRelationInfo.getRelatedOrgId());
		}
		orgOrgRelation.setRelatedOrg(relatedOrg);
		
		// Search for and copy the type
		OrgOrgRelationType orgOrgRelationType = dao.fetch(OrgOrgRelationType.class, orgOrgRelationInfo.getType());
		if (orgOrgRelationType == null) {
			throw new InvalidParameterException(
					"OrgOrgRelationType does not exist for id: " + orgOrgRelationInfo.getType());
		}
		orgOrgRelation.setType(orgOrgRelationType);

		return orgOrgRelation;
	}

	public static OrgPersonRelation toOrgPersonRelation(boolean isUpdate,
			OrgPersonRelationInfo orgPersonRelationInfo,
			OrganizationDao dao) throws InvalidParameterException, VersionMismatchException, DoesNotExistException {
		OrgPersonRelation orgPersonRelation;
		if (isUpdate) {
			orgPersonRelation = dao.fetch(OrgPersonRelation.class, orgPersonRelationInfo.getId());
			if (orgPersonRelation == null) {
				throw new DoesNotExistException("OrgOrgRelation does not exist for id: " + orgPersonRelationInfo.getId());
			}
			if (!String.valueOf(orgPersonRelation.getVersionInd()).equals(orgPersonRelationInfo.getMetaInfo().getVersionInd())){
				throw new VersionMismatchException("OrgOrgRelation to be updated is not the current version");
			}
		} else {
			orgPersonRelation = new OrgPersonRelation();
		}

		// Copy all basic properties
		BeanUtils.copyProperties(orgPersonRelationInfo, orgPersonRelation, new String[] { "type",
				"attributes", "metaInfo", "org", "personId" });

		// Copy Attributes
		orgPersonRelation.setAttributes(toGenericAttributes(OrgPersonRelationAttribute.class, orgPersonRelationInfo.getAttributes(), orgPersonRelation, dao));

		// Search for and copy the org
		Org org = dao.fetch(Org.class, orgPersonRelationInfo.getOrgId());
		if (org == null) {
			throw new InvalidParameterException(
					"Org does not exist for id: " + orgPersonRelationInfo.getOrgId());
		}
		orgPersonRelation.setOrg(org);

		// Search for and copy the related org
		//TODO Use the person Service here
//		Person person = personService.findPerson(orgPersonRelationInfo.getPersonId());
//		if (person == null) {
//			throw new InvalidParameterException(
//					"Person does not exist for id: " + orgPersonRelationInfo.getPersonId());
//		}
//		orgPersonRelation.setPersonId(person.getId());
		orgPersonRelation.setPersonId(orgPersonRelationInfo.getPersonId());
		
		// Search for and copy the type
		OrgPersonRelationType orgPersonRelationType = dao.fetch(OrgPersonRelationType.class, orgPersonRelationInfo.getType());
		if (orgPersonRelationType == null) {
			throw new InvalidParameterException(
					"OrgPersonRelationType does not exist for id: " + orgPersonRelationInfo.getType());
		}
		orgPersonRelation.setType(orgPersonRelationType);

		return orgPersonRelation;
	}

	public static OrgPositionRestriction toOrgPositionRestriction(boolean isUpdate,
			OrgPositionRestrictionInfo orgPositionRestrictionInfo,
			OrganizationDao dao) throws DoesNotExistException, VersionMismatchException, InvalidParameterException {
		OrgPositionRestriction orgPositionRestriction;
		if (isUpdate) {
			orgPositionRestriction = dao.fetch(OrgPositionRestriction.class, orgPositionRestrictionInfo.getId());
			if (orgPositionRestriction == null) {
				throw new DoesNotExistException("OrgPositionRestriction does not exist for id: " + orgPositionRestrictionInfo.getId());
			}
			if (!String.valueOf(orgPositionRestriction.getVersionInd()).equals(orgPositionRestrictionInfo.getMetaInfo().getVersionInd())){
				throw new VersionMismatchException("OrgPositionRestriction to be updated is not the current version");
			}
		} else {
			orgPositionRestriction = new OrgPositionRestriction();
		}

		// Copy all basic properties
		BeanUtils.copyProperties(orgPositionRestrictionInfo, orgPositionRestriction, new String[] { "personRelationType",
				"attributes", "metaInfo", "org", "stdDuration" });
		if(orgPositionRestrictionInfo.getStdDuration()!=null){
			orgPositionRestriction.setStdDuration(new TimeAmount());
			BeanUtils.copyProperties(orgPositionRestrictionInfo.getStdDuration(), orgPositionRestriction.getStdDuration());
		}
		// Copy Attributes
		orgPositionRestriction.setAttributes(toGenericAttributes(OrgPositionRestrictionAttribute.class, orgPositionRestrictionInfo.getAttributes(), orgPositionRestriction, dao));

		// Search for and copy the org
		Org org = dao.fetch(Org.class, orgPositionRestrictionInfo.getOrgId());
		if (org == null) {
			throw new InvalidParameterException(
					"Org does not exist for id: " + orgPositionRestrictionInfo.getOrgId());
		}
		orgPositionRestriction.setOrg(org);

		// Search for and copy the type
		OrgPersonRelationType orgPersonRelationType = dao.fetch(OrgPersonRelationType.class, orgPositionRestrictionInfo.getOrgPersonRelationTypeKey());
		if (orgPersonRelationType == null) {
			throw new InvalidParameterException(
					"OrgPersonRelationType does not exist for id: " + orgPositionRestrictionInfo.getOrgPersonRelationTypeKey());
		}
		orgPositionRestriction.setPersonRelationType(orgPersonRelationType);

		orgPositionRestriction.setDescr(orgPositionRestrictionInfo.getDesc());
		
		return orgPositionRestriction;
	}
}
