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

package org.kuali.student.lum.lo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.dao.CrudDao;
import org.kuali.student.common.exceptions.DoesNotExistException;
import org.kuali.student.common.exceptions.InvalidParameterException;
import org.kuali.student.common.exceptions.VersionMismatchException;
import org.kuali.student.common.service.impl.BaseAssembler;
import org.kuali.student.lum.lo.dao.LoDao;
import org.kuali.student.lum.lo.dto.LoCategoryInfo;
import org.kuali.student.lum.lo.dto.LoCategoryTypeInfo;
import org.kuali.student.lum.lo.dto.LoInfo;
import org.kuali.student.lum.lo.dto.LoLoRelationInfo;
import org.kuali.student.lum.lo.dto.LoLoRelationTypeInfo;
import org.kuali.student.lum.lo.dto.LoRepositoryInfo;
import org.kuali.student.lum.lo.dto.LoTypeInfo;
import org.kuali.student.lum.lo.entity.Lo;
import org.kuali.student.lum.lo.entity.LoAttribute;
import org.kuali.student.lum.lo.entity.LoCategory;
import org.kuali.student.lum.lo.entity.LoCategoryAttribute;
import org.kuali.student.lum.lo.entity.LoCategoryType;
import org.kuali.student.lum.lo.entity.LoCategoryTypeAttribute;
import org.kuali.student.lum.lo.entity.LoLoRelation;
import org.kuali.student.lum.lo.entity.LoLoRelationAttribute;
import org.kuali.student.lum.lo.entity.LoLoRelationType;
import org.kuali.student.lum.lo.entity.LoRepository;
import org.kuali.student.lum.lo.entity.LoRichText;
import org.kuali.student.lum.lo.entity.LoType;
import org.springframework.beans.BeanUtils;

public class LearningObjectiveServiceAssembler extends BaseAssembler {

    public static Lo toLo(boolean isUpdate, LoInfo dto, CrudDao dao) throws InvalidParameterException, DoesNotExistException, VersionMismatchException {
        return toLo(isUpdate, new Lo(), dto, dao);
    }
    
    public static Lo toLo(boolean isUpdate, Lo entity, LoInfo dto, CrudDao dao) throws InvalidParameterException, DoesNotExistException, VersionMismatchException {
        if(null == dto) {
            return null;
        }
        Lo lo;

        if (isUpdate) {
            lo = dao.fetch(Lo.class, dto.getId());
            if (lo == null) {
                throw new DoesNotExistException((new StringBuilder()).append("Lo does not exist for id: ").append(dto.getId()).toString());
            }
            if ( ! String.valueOf(lo.getVersionNumber()).equals(dto.getMetaInfo().getVersionInd()) ) {
                throw new VersionMismatchException("Lo to be updated is not the current version");
            }
        } else {
            lo = new Lo();
        }

        BeanUtils.copyProperties(dto, lo, new String[] { "desc", "loRepository", "loType", "attributes", "metaInfo" });

        lo.setAttributes(toGenericAttributes(LoAttribute.class, dto.getAttributes(), lo, dao));
        lo.setDescr(toRichText(LoRichText.class, dto.getDesc()));

        LoRepository repository = dao.fetch(LoRepository.class, dto.getLoRepositoryKey());
        if(null == repository) {
            throw new InvalidParameterException((new StringBuilder()).append("LoRepository does not exist for id: ").append(dto.getLoRepositoryKey()).toString());
        }
        lo.setLoRepository(repository);
        
        LoType type = dao.fetch(LoType.class, dto.getType());
        if(null == type) {
            throw new InvalidParameterException((new StringBuilder()).append("LoType does not exist for id: ").append(dto.getType()).toString());
        }
        lo.setLoType(type);
        
        return lo;
    }

    public static LoInfo toLoInfo(Lo entity) {
        LoInfo dto = new LoInfo();

        BeanUtils.copyProperties(entity, dto,
                new String[] { "desc", "attributes", "type" });
        dto.setDesc(toRichTextInfo(entity.getDescr()));
        dto.setMetaInfo(toMetaInfo(entity.getMeta(), entity.getVersionNumber()));
        dto.setAttributes(toAttributeMap(entity.getAttributes()));
        dto.setType(entity.getLoType().getId());
        dto.setLoRepositoryKey(entity.getLoRepository() == null? null: entity.getLoRepository().getId());
        return dto;
    }

    public static LoCategory toLoCategory(LoCategoryInfo dto, LoDao dao) throws InvalidParameterException, DoesNotExistException {
        return toLoCategory(new LoCategory(), dto, dao);
    }
    
    public static LoCategory toLoCategory(LoCategory entity, LoCategoryInfo dto, LoDao dao) throws InvalidParameterException, DoesNotExistException {
        if(entity == null)
            entity = new LoCategory();
        BeanUtils.copyProperties(dto, entity,
                new String[] { "desc", "attributes", "metaInfo", "loRepository", "type", "id"});
        entity.setDesc(toRichText(LoRichText.class, dto.getDesc()));
        entity.setAttributes(toGenericAttributes(LoCategoryAttribute.class, dto.getAttributes(), entity, dao));
        entity.setLoRepository(dao.fetch(LoRepository.class, dto.getLoRepository()));
        entity.setLoCategoryType(dao.fetch(LoCategoryType.class, dto.getType()));
        return entity;
    }

    public static LoCategoryInfo toLoCategoryInfo(LoCategory entity) {
        LoCategoryInfo dto = new LoCategoryInfo();

        BeanUtils.copyProperties(entity, dto,
                new String[] { "desc", "attributes", "loRepository", "loCategoryType" });
        dto.setDesc(toRichTextInfo(entity.getDescr()));
        dto.setMetaInfo(toMetaInfo(entity.getMeta(), entity.getVersionNumber()));
        dto.setAttributes(toAttributeMap(entity.getAttributes()));
        dto.setLoRepository(entity.getLoRepository().getId());
        dto.setType(entity.getLoCategoryType().getId());
        
        return dto;
    }
    
    public static LoRepositoryInfo toLoRepositoryInfo(LoRepository entity) {
        LoRepositoryInfo dto = new LoRepositoryInfo();
        
        BeanUtils.copyProperties(entity, dto,
                new String[] { "desc", "attributes", "rootLo" });
        dto.setDesc(toRichTextInfo(entity.getDescr()));
        dto.setMetaInfo(toMetaInfo(entity.getMeta(), entity.getVersionNumber()));
        dto.setAttributes(toAttributeMap(entity.getAttributes()));
        dto.setRootLoId(entity.getRootLo() == null? null :entity.getRootLo().getId());
        return dto;
    }
    
    public static LoTypeInfo toLoTypeInfo(LoType entity) {
        LoTypeInfo dto = new LoTypeInfo();
        
        BeanUtils.copyProperties(entity, dto,
                new String[] { "attributes" });
        dto.setAttributes(toAttributeMap(entity.getAttributes()));
        return dto;
    }

    
    public static List<LoInfo> toLoInfos(List<Lo> los) {
        List<LoInfo> list = new ArrayList<LoInfo>();
        for (Lo lo : los) {
            list.add(toLoInfo(lo));
        }
        return list;
    }

    public static List<LoCategoryInfo> toLoCategoryInfos(List<LoCategory> categories) {
    	if (null == categories) {
    		return new ArrayList<LoCategoryInfo>(0);
    	}
        List<LoCategoryInfo> list = new ArrayList<LoCategoryInfo>(categories.size());
        for (LoCategory loCategory : categories) {
            list.add(toLoCategoryInfo(loCategory));
        }
        return list;
    }

    public static List<LoRepositoryInfo> toLoRepositoryInfos(List<LoRepository> repositories) {
        List<LoRepositoryInfo> list = new ArrayList<LoRepositoryInfo>();
        for (LoRepository loRepository : repositories) {
            list.add(toLoRepositoryInfo(loRepository));
        }
        return list;
    }

    public static List<LoTypeInfo> toLoTypeInfos(List<LoType> find) {
        List<LoTypeInfo> list = new ArrayList<LoTypeInfo>();
        for (LoType loType : find) {
            list.add(toLoTypeInfo(loType));
        }
        return list;
    }

    public static List<LoLoRelationTypeInfo> toLoLoRelationTypeInfos(List<LoLoRelationType> find) {
        List<LoLoRelationTypeInfo> list = new ArrayList<LoLoRelationTypeInfo>();
        for (LoLoRelationType loType : find) {
            list.add(toLoLoRelationTypeInfo(loType));
        }
        return list;
    }

	public static LoLoRelationTypeInfo toLoLoRelationTypeInfo(LoLoRelationType loLoRelType) {
		LoLoRelationTypeInfo dto = new LoLoRelationTypeInfo();
        BeanUtils.copyProperties(loLoRelType, dto,
                new String[] { "attributes" });
        dto.setAttributes(toAttributeMap(loLoRelType.getAttributes()));
        return dto;
	}
	
	public static LoLoRelation toLoLoRelation(boolean isUpdate, LoLoRelationInfo dto, CrudDao dao) throws DoesNotExistException, VersionMismatchException, InvalidParameterException {
		return toLoLoRelation(isUpdate, null, dto, dao);
	}
	
	public static LoLoRelation toLoLoRelation(boolean isUpdate, LoLoRelation entity, LoLoRelationInfo dto, CrudDao dao) throws DoesNotExistException, VersionMismatchException, InvalidParameterException {
        if(null == dto) {
            return null;
        }
        LoLoRelation llRelation;

        if (isUpdate) {
            llRelation = dao.fetch(LoLoRelation.class, dto.getId());
            if (llRelation == null) {
                throw new DoesNotExistException((new StringBuilder()).append("LoLoRelation does not exist for id: ").append(dto.getId()).toString());
            }
            if ( ! String.valueOf(llRelation.getVersionNumber()).equals(dto.getMetaInfo().getVersionInd()) ) {
                throw new VersionMismatchException("LoLoRelation to be updated is not the current version");
            }
        } else {
            llRelation = new LoLoRelation();
        }

        BeanUtils.copyProperties(dto, llRelation, new String[] { "lo", "relatedLo", "attributes", "metaInfo" });

        llRelation.setAttributes(toGenericAttributes(LoLoRelationAttribute.class, dto.getAttributes(), llRelation, dao));

        Lo lo = null;
        Lo relatedLo = null;
        LoLoRelationType relationType = null;
        try {
	        lo = dao.fetch(Lo.class, dto.getLoId());
	        relatedLo = dao.fetch(Lo.class, dto.getRelatedLoId());
	        relationType = dao.fetch(LoLoRelationType.class, dto.getType());
        } catch (DoesNotExistException dnee) {
        	throw new DoesNotExistException((null == lo ? "Lo" : (null == relatedLo ? "Related Lo" : "Lo-Lo relation type")) +
        									" does not exist when creating LoLoRelation", dnee);
        }
        
        llRelation.setLo(lo);
        llRelation.setRelatedLo(relatedLo);
        llRelation.setLoLoRelationType(relationType);
        
        return llRelation;
	}
	
	public static LoLoRelationInfo toLoLoRelationInfo(LoLoRelation entity) {
		LoLoRelationInfo dto = new LoLoRelationInfo();
		
        BeanUtils.copyProperties(entity, dto,
                new String[] { "lo", "relatedLo", "type", "attributes" });
        dto.setLoId(entity.getLo().getId());
        dto.setRelatedLoId(entity.getRelatedLo().getId());
        dto.setType(entity.getLoLoRelationType().getId());
        dto.setMetaInfo(toMetaInfo(entity.getMeta(), entity.getVersionNumber()));
        dto.setAttributes(toAttributeMap(entity.getAttributes()));
        return dto;
	}
	
    public static LoCategoryType toLoCategoryType(LoCategoryType entity, LoCategoryTypeInfo dto, LoDao dao) throws InvalidParameterException {
        if(entity == null)
            entity = new LoCategoryType();
        BeanUtils.copyProperties(dto, entity,
                new String[] { "attributes", "metaInfo" });
        entity.setAttributes(toGenericAttributes(LoCategoryTypeAttribute.class, dto.getAttributes(), entity, dao));
        entity.setDescr(dto.getDesc());
        return entity;
    }

	
	public static LoCategoryTypeInfo toLoCategoryTypeInfo( LoCategoryType loCatType) {
		LoCategoryTypeInfo dto = new LoCategoryTypeInfo();
		BeanUtils.copyProperties(loCatType, dto, new String[] { "attributes" });
        dto.setAttributes(toAttributeMap(loCatType.getAttributes()));
        dto.setDesc(loCatType.getDescr());
		return dto;
	}

	public static List<LoCategoryTypeInfo> toLoCategoryTypeInfos(List<LoCategoryType> categoryTypes) {
        ArrayList<LoCategoryTypeInfo> list = new ArrayList<LoCategoryTypeInfo>();
        for (LoCategoryType catType : categoryTypes) {
            list.add(toLoCategoryTypeInfo(catType));
        }
        return list;
	}

	public static List<LoLoRelationInfo> toLoLoRelationInfos( List<LoLoRelation> llRelations) {
		List<LoLoRelationInfo> llRelInfos = new ArrayList<LoLoRelationInfo>();
		for (LoLoRelation llRelation : llRelations) {
			llRelInfos.add(toLoLoRelationInfo(llRelation));
		}
		return llRelInfos;
	}
}
