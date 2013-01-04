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

package org.kuali.student.r2.core.class1.organization.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.kuali.student.common.dao.CrudDao;
import org.kuali.student.common.entity.Attribute;
import org.kuali.student.common.entity.AttributeOwner;
import org.kuali.student.common.entity.Meta;
import org.kuali.student.common.entity.TimeAmount;
import org.kuali.student.common.entity.Type;
import org.kuali.student.common.service.impl.BaseAssembler;
import org.kuali.student.core.organization.dao.OrganizationDao;
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
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.TimeAmountInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.organization.dto.OrgHierarchyInfo;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.organization.dto.OrgOrgRelationInfo;
import org.kuali.student.r2.core.organization.dto.OrgPersonRelationInfo;
import org.kuali.student.r2.core.organization.dto.OrgPositionRestrictionInfo;
import org.kuali.student.r2.core.type.dto.TypeInfo;
import org.springframework.beans.BeanUtils;

public class OrganizationAssembler {
    
    final static Logger logger = Logger.getLogger(BaseAssembler.class);

    public static List<OrgHierarchyInfo> toOrgHierarchyInfos(List<OrgHierarchy> orgHierarchys) {
        List<OrgHierarchyInfo> orgHierarchyInfos = new ArrayList<OrgHierarchyInfo>(orgHierarchys.size());
        for(OrgHierarchy orgHierarchy:orgHierarchys) {
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
                                                                                "attributes", 
                                                                                "descr" });

        // copy attributes and RootOrg
        orgHierarchyInfo.setAttributes(toAttributeList(orgHierarchy.getAttributes()));
        orgHierarchyInfo.setRootOrgId(orgHierarchy.getRootOrg().getId());
        RichTextInfo richText = new RichTextInfo();
        richText.setFormatted(orgHierarchy.getDescr());
        richText.setPlain(orgHierarchy.getDescr());
        orgHierarchyInfo.setDescr(richText);
        return orgHierarchyInfo;
    }

    public static List<OrgInfo> toOrgInfos(List<Org> orgs) {
        List<OrgInfo> orgInfos = new ArrayList<OrgInfo>(orgs.size());
        for (Org org : orgs) {
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
                                                              "attributes", 
                                                              "meta", 
                                                              "shortDesc", 
                                                              "longDesc" });
        
        // copy attributes, metadata, and Type
        orgInfo.setAttributes(toAttributeList(org.getAttributes()));
        orgInfo.setMeta(toMetaInfo(org.getMeta(), org.getVersionNumber()));
        orgInfo.setTypeKey(org.getType().getId());
        
        RichTextInfo shortDescr = new RichTextInfo();
        shortDescr.setFormatted(org.getShortDesc());
        shortDescr.setPlain(org.getShortDesc());
        orgInfo.setShortDescr(shortDescr);
	
        RichTextInfo longDescr = new RichTextInfo();
        longDescr.setFormatted(org.getLongDesc());
        longDescr.setPlain(org.getLongDesc());
        orgInfo.setLongDescr(longDescr);
        
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
                                                                        "attributes", 
                                                                        "meta", 
                                                                        "orgId"});
        
        relationInfo.setOrgId(relation.getOrg().getId());
        relationInfo.setAttributes(toAttributeList(relation.getAttributes()));
        relationInfo.setMeta(toMetaInfo(relation.getMeta(), relation.getVersionNumber()));
        relationInfo.setTypeKey(relation.getType().getId());
        //relationInfo.setId(relation.getId());
        return relationInfo;
    }
    
    public static List<OrgOrgRelationInfo> toOrgOrgRelationInfos(List<OrgOrgRelation> orgOrgRelations) {
        List<OrgOrgRelationInfo> orgOrgRelationInfo = new ArrayList<OrgOrgRelationInfo>();
        for(OrgOrgRelation orgOrgRelation:orgOrgRelations){
            orgOrgRelationInfo.add(toOrgOrgRelationInfo(orgOrgRelation));
        }
        return orgOrgRelationInfo;
    }
    
    public static OrgOrgRelationInfo toOrgOrgRelationInfo(OrgOrgRelation orgOrgRelation) {
        if (orgOrgRelation == null) {
            return null;
        }
        
        OrgOrgRelationInfo orgOrgRelationInfo = new OrgOrgRelationInfo();
        
        BeanUtils.copyProperties(orgOrgRelation, orgOrgRelationInfo, new String[] { "type",
                                                                                    "attributes", 
                                                                                    "meta",
                                                                                    "orgId",
                                                                                    "relatedOrgId" });
        
        // copy attributes, metadata, Type, and related orgs
        orgOrgRelationInfo.setAttributes(toAttributeList(orgOrgRelation.getAttributes()));
        orgOrgRelationInfo.setMeta(toMetaInfo(orgOrgRelation.getMeta(), orgOrgRelation.getVersionNumber()));
        orgOrgRelationInfo.setTypeKey(orgOrgRelation.getType().getId());
        orgOrgRelationInfo.setOrgId(orgOrgRelation.getOrg().getId());
        orgOrgRelationInfo.setRelatedOrgId(orgOrgRelation.getRelatedOrg().getId());
        
        return orgOrgRelationInfo;
    }
    
    public static OrgPositionRestrictionInfo toOrgPositionRestrictionInfo(OrgPositionRestriction restriction) {
        OrgPositionRestrictionInfo restrictionInfo = new OrgPositionRestrictionInfo();
        
        BeanUtils.copyProperties(restriction, restrictionInfo, new String[] { 
                "attributes", "meta", "orgId","personRelationType","stdDuration", "descr" });
	
        if (restriction.getStdDuration()!=null) {
            restrictionInfo.setStdDuration(new TimeAmountInfo());
            BeanUtils.copyProperties(restriction.getStdDuration(), restrictionInfo.getStdDuration());
        }
        
        restrictionInfo.setOrgId(restriction.getOrg().getId());
        restrictionInfo.setAttributes(toAttributeList(restriction.getAttributes()));
        restrictionInfo.setMeta(toMetaInfo(restriction.getMeta(), restriction.getVersionNumber()));
        restrictionInfo.setOrgPersonRelationTypeKey(restriction.getPersonRelationType().getId());
        RichTextInfo richText = new RichTextInfo();
        richText.setFormatted(restriction.getDescr());
        richText.setPlain(restriction.getDescr());
        restrictionInfo.setDescr(richText);
        return restrictionInfo;
	}

    public static List<OrgPositionRestrictionInfo> toOrgPositionRestrictionInfos(List<OrgPositionRestriction> restrictions) {
        List<OrgPositionRestrictionInfo> restrictionInfos = new ArrayList<OrgPositionRestrictionInfo>(restrictions.size());
        for (OrgPositionRestriction restriction : restrictions) {
            restrictionInfos.add(toOrgPositionRestrictionInfo(restriction));
        }
        return restrictionInfos;
    }
    
    public static TypeInfo toOrgTypeInfo(OrgType orgType) {
        return toGenericTypeInfo(orgType);
    }
    
    public static List<TypeInfo> toOrgTypeInfos(List<OrgType> orgTypes) {
        List<TypeInfo> orgTypeInfos = new ArrayList<TypeInfo>(orgTypes.size());
        for (OrgType orgType : orgTypes) {
            orgTypeInfos.add(toOrgTypeInfo(orgType));
        }
        return orgTypeInfos;
    }
    
    public static TypeInfo toOrgPersonRelationTypeInfo(OrgPersonRelationType orgPersonRelationType) {
        return toGenericTypeInfo(orgPersonRelationType);
    }
    
    public static List<TypeInfo> toOrgPersonRelationTypeInfos(List<OrgPersonRelationType> orgPersonRelationTypes) {
        List<TypeInfo> oprtys = new ArrayList<TypeInfo>(orgPersonRelationTypes.size());
        for (OrgPersonRelationType type : orgPersonRelationTypes) {
            oprtys.add(toOrgPersonRelationTypeInfo(type));
        }
        return oprtys;
    }
    
    public static TypeInfo toOrgOrgRelationTypeInfo(OrgOrgRelationType orgOrgRelationType) {
        return toGenericTypeInfo(orgOrgRelationType);
    }
    
    public static List<TypeInfo> toOrgOrgRelationTypeInfos(List<OrgOrgRelationType> orgOrgRelationTypes) {
        List<TypeInfo> orgOrgRelationTypeInfos = new ArrayList<TypeInfo>(orgOrgRelationTypes.size());
        for (OrgOrgRelationType orgOrgRelationType : orgOrgRelationTypes) {
            orgOrgRelationTypeInfos.add(toOrgOrgRelationTypeInfo(orgOrgRelationType));
        }
        
        return orgOrgRelationTypeInfos;
    }
    
    public static Org toOrg(boolean isUpdate, OrgInfo orgInfo, OrganizationDao dao)
        throws InvalidParameterException, DoesNotExistException, VersionMismatchException {

        Org org = null;
        if (isUpdate) {
            try {
                org = dao.fetch(Org.class, orgInfo.getId());
            } catch (org.kuali.student.common.exceptions.DoesNotExistException e) {
                throw new DoesNotExistException("Org does not exist for id: " + orgInfo.getId());
            }
            if (org == null) {
                throw new DoesNotExistException("Org does not exist for id: " + orgInfo.getId());
            }
            if (!String.valueOf(org.getVersionNumber()).equals(orgInfo.getMeta().getVersionInd())){
                throw new VersionMismatchException("Org to be updated is not the current version");
            }
        } else {
            org = new Org();
        }
        
        // Copy all basic properties
        BeanUtils.copyProperties(orgInfo, org, new String[] { "type",
                                                              "attributes", 
                                                              "meta", 
                                                              "longDesc", 
                                                              "shortDesc" });
        
        // Copy Description
        org.setLongDesc(orgInfo.getLongDescr().getPlain());
        org.setShortDesc(orgInfo.getShortDescr().getPlain());
        
        // Copy Attributes
        org.setAttributes(toGenericAttributes(OrgAttribute.class, orgInfo.getAttributes(), org, dao));
        
        // Search for and copy the type
        OrgType orgType = null;
        try {
            orgType = dao.fetch(OrgType.class, orgInfo.getTypeKey());
        } catch (org.kuali.student.common.exceptions.DoesNotExistException e) {
            throw new DoesNotExistException("OrgType does not exist for id: " + orgInfo.getTypeKey());
        }
        if (orgType == null) {
            throw new InvalidParameterException("OrgType does not exist for id: " + orgInfo.getTypeKey());
        }
        org.setType(orgType);
        
        return org;
    }
    
    public static OrgOrgRelation toOrgOrgRelation(boolean isUpdate,
                                                  OrgOrgRelationInfo orgOrgRelationInfo,
                                                  OrganizationDao dao) 
        throws DoesNotExistException, VersionMismatchException, InvalidParameterException {
	
	OrgOrgRelation orgOrgRelation;
        if (isUpdate) {
            try {
                orgOrgRelation = dao.fetch(OrgOrgRelation.class, orgOrgRelationInfo.getId());
            } catch (org.kuali.student.common.exceptions.DoesNotExistException e) {
                throw new DoesNotExistException("OrgOrgRelation does not exist for id: " + orgOrgRelationInfo.getId());
            }
            if (orgOrgRelation == null) {
                throw new DoesNotExistException("OrgOrgRelation does not exist for id: " + orgOrgRelationInfo.getId());
            }
            if (!String.valueOf(orgOrgRelation.getVersionNumber()).equals(orgOrgRelationInfo.getMeta().getVersionInd())){
                throw new VersionMismatchException("OrgOrgRelation to be updated is not the current version");
            }
        } else {
            orgOrgRelation = new OrgOrgRelation();
        }
        
        // Copy all basic properties
        BeanUtils.copyProperties(orgOrgRelationInfo, orgOrgRelation, new String[] { "type",
                                                                                    "attributes", 
                                                                                    "meta", 
                                                                                    "org", 
                                                                                    "relatedOrg" });
        
        // Copy Attributes
        orgOrgRelation.setAttributes(toGenericAttributes(OrgOrgRelationAttribute.class, orgOrgRelationInfo.getAttributes(), orgOrgRelation, dao));
        
        // Search for and copy the org
        Org org = null;
        try {
            org = dao.fetch(Org.class, orgOrgRelationInfo.getOrgId());
        } catch (org.kuali.student.common.exceptions.DoesNotExistException e) {
            throw new DoesNotExistException("Org does not exist for id: " + orgOrgRelationInfo.getOrgId());
        }
        if (org == null) {
            throw new InvalidParameterException("Org does not exist for id: " + orgOrgRelationInfo.getOrgId());
        }
        orgOrgRelation.setOrg(org);
        
        // Search for and copy the related org
        Org relatedOrg = null;
        try {
            relatedOrg = dao.fetch(Org.class, orgOrgRelationInfo.getRelatedOrgId());
        } catch (org.kuali.student.common.exceptions.DoesNotExistException e) {
            throw new DoesNotExistException("RelatedOrg does not exist for id: " + orgOrgRelationInfo.getRelatedOrgId());
        }
        if (relatedOrg == null) {
            throw new InvalidParameterException("RelatedOrg does not exist for id: " + orgOrgRelationInfo.getRelatedOrgId());
        }
        orgOrgRelation.setRelatedOrg(relatedOrg);
	
        // Search for and copy the type
        OrgOrgRelationType orgOrgRelationType = null;
        try {
            orgOrgRelationType = dao.fetch(OrgOrgRelationType.class, orgOrgRelationInfo.getTypeKey());
        } catch (org.kuali.student.common.exceptions.DoesNotExistException e) {
            throw new DoesNotExistException("OrgOrgRelationType does not exist for id: " + orgOrgRelationInfo.getTypeKey());
        }
        if (orgOrgRelationType == null) {
            throw new InvalidParameterException(
                                                "OrgOrgRelationType does not exist for id: " + orgOrgRelationInfo.getTypeKey());
        }
        orgOrgRelation.setType(orgOrgRelationType);
        
        return orgOrgRelation;
    }
    
    public static OrgPersonRelation toOrgPersonRelation(boolean isUpdate,
                                                        OrgPersonRelationInfo orgPersonRelationInfo,
                                                        OrganizationDao dao) 
        throws InvalidParameterException, VersionMismatchException, DoesNotExistException {

        OrgPersonRelation orgPersonRelation = null;
        if (isUpdate) {
            try {
                orgPersonRelation = dao.fetch(OrgPersonRelation.class, orgPersonRelationInfo.getId());
            } catch (org.kuali.student.common.exceptions.DoesNotExistException e) {
                throw new DoesNotExistException("OrgOrgRelation does not exist for id: " + orgPersonRelationInfo.getId());
            }
            if (orgPersonRelation == null) {
                throw new DoesNotExistException("OrgOrgRelation does not exist for id: " + orgPersonRelationInfo.getId());
            }
            if (!String.valueOf(orgPersonRelation.getVersionNumber()).equals(orgPersonRelationInfo.getMeta().getVersionInd())){
                throw new VersionMismatchException("OrgOrgRelation to be updated is not the current version");
            }
        } else {
            orgPersonRelation = new OrgPersonRelation();
        }
        
        // Copy all basic properties
        BeanUtils.copyProperties(orgPersonRelationInfo, orgPersonRelation, new String[] { "type",
                                                                                          "attributes", "meta", "org", "personId" });
        
        // Copy Attributes
        orgPersonRelation.setAttributes(toGenericAttributes(OrgPersonRelationAttribute.class, orgPersonRelationInfo.getAttributes(), orgPersonRelation, dao));
        
        // Search for and copy the org
        Org org = null;
        try {
            org = dao.fetch(Org.class, orgPersonRelationInfo.getOrgId());
        } catch (org.kuali.student.common.exceptions.DoesNotExistException e) {
            throw new DoesNotExistException("Org does not exist for id: " + orgPersonRelationInfo.getOrgId());
        }
        if (org == null) {
            throw new InvalidParameterException("Org does not exist for id: " + orgPersonRelationInfo.getOrgId());
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
        OrgPersonRelationType orgPersonRelationType = null;
        try {
            orgPersonRelationType = dao.fetch(OrgPersonRelationType.class, orgPersonRelationInfo.getTypeKey());
        } catch (org.kuali.student.common.exceptions.DoesNotExistException e) {
            throw new DoesNotExistException("OrgPersonRelationType does not exist for id: " + orgPersonRelationInfo.getTypeKey());
        }
        if (orgPersonRelationType == null) {
            throw new InvalidParameterException("OrgPersonRelationType does not exist for id: " + orgPersonRelationInfo.getTypeKey());
        }
        orgPersonRelation.setType(orgPersonRelationType);
        
        return orgPersonRelation;
    }
    
    public static OrgPositionRestriction toOrgPositionRestriction(boolean isUpdate,
                                                                  OrgPositionRestrictionInfo orgPositionRestrictionInfo,
                                                                  OrganizationDao dao) 
        throws DoesNotExistException, VersionMismatchException, InvalidParameterException {
        
        OrgPositionRestriction orgPositionRestriction = null;
        if (isUpdate) {
            try {
                orgPositionRestriction = dao.fetch(OrgPositionRestriction.class, orgPositionRestrictionInfo.getId());
            } catch (org.kuali.student.common.exceptions.DoesNotExistException e) {
                throw new DoesNotExistException("OrgPositionRestriction does not exist for id: " + orgPositionRestrictionInfo.getId());
            }
            if (orgPositionRestriction == null) {
                throw new DoesNotExistException("OrgPositionRestriction does not exist for id: " + orgPositionRestrictionInfo.getId());
            }
            if (!String.valueOf(orgPositionRestriction.getVersionNumber()).equals(orgPositionRestrictionInfo.getMeta().getVersionInd())){
                throw new VersionMismatchException("OrgPositionRestriction to be updated is not the current version");
            }
        } else {
            orgPositionRestriction = new OrgPositionRestriction();
        }
        
        // Copy all basic properties
        BeanUtils.copyProperties(orgPositionRestrictionInfo, orgPositionRestriction, new String[] { "personRelationType",
                                                                                                    "attributes", 
                                                                                                    "meta", 
                                                                                                    "org", 
                                                                                                    "stdDuration", 
                                                                                                    "descr" });
        
        //Copy Description
        orgPositionRestriction.setDescr(orgPositionRestrictionInfo.getDescr().getPlain());
	
        //Copy std duration
        if (orgPositionRestrictionInfo.getStdDuration()!=null) {
            orgPositionRestriction.setStdDuration(new TimeAmount());
            BeanUtils.copyProperties(orgPositionRestrictionInfo.getStdDuration(), orgPositionRestriction.getStdDuration());
        }
        // Copy Attributes
        orgPositionRestriction.setAttributes(toGenericAttributes(OrgPositionRestrictionAttribute.class, orgPositionRestrictionInfo.getAttributes(), orgPositionRestriction, dao));
        
        // Search for and copy the org
        Org org = null;
        try {
            org = dao.fetch(Org.class, orgPositionRestrictionInfo.getOrgId());
        } catch (org.kuali.student.common.exceptions.DoesNotExistException e) {
            throw new DoesNotExistException("Org does not exist for id: " + orgPositionRestrictionInfo.getOrgId());
        }
        if (org == null) {
            throw new InvalidParameterException("Org does not exist for id: " + orgPositionRestrictionInfo.getOrgId());
        }
        orgPositionRestriction.setOrg(org);
        
        // Search for and copy the type
        OrgPersonRelationType orgPersonRelationType = null;
        try {
            orgPersonRelationType = dao.fetch(OrgPersonRelationType.class, orgPositionRestrictionInfo.getOrgPersonRelationTypeKey());
        } catch (org.kuali.student.common.exceptions.DoesNotExistException e) {
            throw new DoesNotExistException("OrgPersonRelationType does not exist for id: " + orgPositionRestrictionInfo.getOrgPersonRelationTypeKey());
        }
        if (orgPersonRelationType == null) {
            throw new InvalidParameterException("OrgPersonRelationType does not exist for id: " + orgPositionRestrictionInfo.getOrgPersonRelationTypeKey());
        }
        orgPositionRestriction.setPersonRelationType(orgPersonRelationType);
        
        orgPositionRestriction.setDescr(orgPositionRestrictionInfo.getDescr().getPlain());
	
        return orgPositionRestriction;
    }
    
    public static <A extends Attribute<O>, O extends AttributeOwner<A>> List<A> toGenericAttributes(Class<A> attributeClass, List<AttributeInfo> attributeList, O owner, CrudDao dao) throws InvalidParameterException {
        
        List<A> attributes = new ArrayList<A>();
        
        if (owner.getAttributes()==null) {
            owner.setAttributes(new ArrayList<A>());
        }
        
        Map<String, A> currentAttributes = new HashMap<String,A>();
        
        // Find all the old attributes(if the owner is not null)
        for (A attribute : owner.getAttributes()) {
            currentAttributes.put(attribute.getName(), attribute);
        }
        
        //Clear out the attributes
        owner.getAttributes().clear();
        
        if (attributeList==null) {
            return attributes;
        }
        
        //Update anything that exists, or create a new attribute if it doesn't
        for (AttributeInfo attributeEntry : attributeList) {            
            A attribute;
            if (currentAttributes.containsKey(attributeEntry.getKey())) {
                attribute = currentAttributes.remove(attributeEntry.getKey());
            } else {
                try {
                    attribute = attributeClass.newInstance();
                } catch(Exception e) {
                    throw new RuntimeException("Error copying attributes.",e);
                }
                attribute.setName(attributeEntry.getKey());
                attribute.setOwner(owner);
            }
            attribute.setValue(attributeEntry.getValue());
            attributes.add(attribute);
        }
        
        //Delete leftovers here if behavior is desired
        
        return attributes;
    }
    
	
    protected static MetaInfo toMetaInfo(Meta meta, Long versionInd) {
        
        MetaInfo metaInfo = new MetaInfo();
        // If there was a meta passed in then copy the values
        if (meta != null) {
            BeanUtils.copyProperties(meta, metaInfo);
        }
        if (versionInd==null) {
            metaInfo.setVersionInd(null);
        } else {
            metaInfo.setVersionInd(versionInd.toString());
        }
        
        return metaInfo;
    }
    
    public static List<AttributeInfo> toAttributeList(List<? extends Attribute<?>> attributes) {
        
        List<AttributeInfo> attributeInfos = new ArrayList<AttributeInfo>();

        for (Attribute<?> attribute : attributes) {
            AttributeInfo attributeInfo = new AttributeInfo();
            attributeInfo.setKey(attribute.getName());
            attributeInfo.setValue(attribute.getValue());
            attributeInfos.add(attributeInfo);
        }
        
        return attributeInfos;
    }
    
    public static <S extends Type<?>> TypeInfo toGenericTypeInfo(S typeEntity) {
        if (typeEntity == null) {
            return null;
        }
        
        TypeInfo typeInfo;
        try {
            // Create a new TypeInfo based on the <T> class and copy the
            // properties
            typeInfo = new TypeInfo();
            BeanUtils.copyProperties(typeEntity, typeInfo,
                                     new String[] { "attributes", "descr" });
            
            typeInfo.setKey(typeEntity.getId());
            
            // Copy the attributes
            typeInfo.setAttributes(toAttributeList(typeEntity.getAttributes()));
            
            //Copy the description
            RichTextInfo richText = new RichTextInfo();
            richText.setFormatted(typeEntity.getDescr());
            richText.setPlain(typeEntity.getDescr());
            typeInfo.setDescr(richText);
            
            return typeInfo;

        } catch (Exception e) {
            logger.error("Exception occured: ", e);
        }

        return null;
    }
}
