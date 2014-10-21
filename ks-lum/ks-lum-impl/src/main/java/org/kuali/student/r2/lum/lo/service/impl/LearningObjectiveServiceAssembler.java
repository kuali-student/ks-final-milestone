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

package org.kuali.student.r2.lum.lo.service.impl;


import org.kuali.student.r2.core.service.assembly.BaseAssembler;
import org.kuali.student.r2.lum.lo.dao.LoDao;
import org.kuali.student.r2.lum.lo.entity.Lo;
import org.kuali.student.r2.lum.lo.entity.LoAttribute;
import org.kuali.student.r2.lum.lo.entity.LoCategory;
import org.kuali.student.r2.lum.lo.entity.LoCategoryAttribute;
import org.kuali.student.r2.lum.lo.entity.LoCategoryType;
import org.kuali.student.r2.lum.lo.entity.LoLoRelation;
import org.kuali.student.r2.lum.lo.entity.LoLoRelationAttribute;
import org.kuali.student.r2.lum.lo.entity.LoLoRelationType;
import org.kuali.student.r2.lum.lo.entity.LoRepository;
import org.kuali.student.r2.lum.lo.entity.LoRichText;
import org.kuali.student.r2.lum.lo.entity.LoType;
import org.kuali.student.r1.common.dao.CrudDao;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.lum.lo.dto.LoCategoryInfo;
import org.kuali.student.r2.lum.lo.dto.LoInfo;
import org.kuali.student.r2.lum.lo.dto.LoLoRelationInfo;
import org.kuali.student.r2.lum.lo.dto.LoRepositoryInfo;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

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
            if ( ! String.valueOf(lo.getVersionNumber()).equals(dto.getMeta().getVersionInd()) ) {
                throw new VersionMismatchException("Lo to be updated is not the current version");
            }
        } else {
            lo = new Lo();
        }

        BeanUtils.copyProperties(dto, lo, new String[] { "descr", "loRepositoryKey", "typeKey", "attributes", "meta" });
        
        lo.setAttributes(toGenericAttributes(LoAttribute.class, dto.getAttributes(), lo, dao));
        lo.setDescr(toRichText(LoRichText.class, dto.getDescr()));
        lo.setState(dto.getStateKey());

        LoRepository repository = dao.fetch(LoRepository.class, dto.getLoRepositoryKey());
        if(null == repository) {
            throw new InvalidParameterException((new StringBuilder()).append("LoRepository does not exist for id: ").append(dto.getLoRepositoryKey()).toString());
        }
        lo.setLoRepository(repository);
        
        LoType type = dao.fetch(LoType.class, dto.getTypeKey());
        if(null == type) {
            throw new InvalidParameterException((new StringBuilder()).append("LoType does not exist for id: ").append(dto.getTypeKey()).toString());
        }
        lo.setLoType(type);
        
        return lo;
    }

    public static LoInfo toLoInfo(Lo entity) {
        LoInfo dto = new LoInfo();

        BeanUtils.copyProperties(entity, dto,
                new String[] { "descr", "attributes", "loType", "meta", "type", "state"  });
        dto.setDescr(toRichTextInfo(entity.getDescr()));
        dto.setMeta(toMetaInfo(entity.getMeta(), entity.getVersionNumber()));
        dto.setAttributes(toAttributeList(entity.getAttributes()));
        dto.setTypeKey(entity.getLoType().getId());
        dto.setStateKey(entity.getState());
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
                new String[] { "descr", "attributes", "meta", "loRepositoryKey", "typeKey", "id", "stateKey"});
        entity.setDesc(toRichText(LoRichText.class, dto.getDescr()));
        entity.setAttributes(toGenericAttributes(LoCategoryAttribute.class, dto.getAttributes(), entity, dao));
        entity.setLoRepository(dao.fetch(LoRepository.class, dto.getLoRepositoryKey()));
        entity.setLoCategoryType(dao.fetch(LoCategoryType.class, dto.getTypeKey()));
        entity.setState(dto.getStateKey());
        return entity;
    }

    public static LoCategoryInfo toLoCategoryInfo(LoCategory entity) {
        LoCategoryInfo dto = new LoCategoryInfo();

        BeanUtils.copyProperties(entity, dto,
                new String[] {"descr", "attributes", "loRepository", "loCategoryType", "state", "meta" });
        dto.setDescr(toRichTextInfo(entity.getDescr()));
        dto.setMeta(toMetaInfo(entity.getMeta(), entity.getVersionNumber()));
        dto.setAttributes(toAttributeList(entity.getAttributes()));
        dto.setLoRepositoryKey(entity.getLoRepository().getId());
        dto.setStateKey(entity.getState());
        dto.setTypeKey(entity.getLoCategoryType().getId());
        return dto;
    }
    
    public static LoRepositoryInfo toLoRepositoryInfo(LoRepository entity) {
        LoRepositoryInfo dto = new LoRepositoryInfo();
        
        BeanUtils.copyProperties(entity, dto,
                new String[] { "descr", "attributes", "rootLo" });
        dto.setKey(entity.getId());
        dto.setDescr(toRichTextInfo(entity.getDescr()));
        dto.setMeta(toMetaInfo(entity.getMeta(), entity.getVersionNumber()));
        dto.setAttributes(toAttributeList(entity.getAttributes()));
        dto.setRootLoId(entity.getRootLo() == null? null :entity.getRootLo().getId());
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
            if ( ! String.valueOf(llRelation.getVersionNumber()).equals(dto.getMeta().getVersionInd()) ) {
                throw new VersionMismatchException("LoLoRelation to be updated is not the current version");
            }
        } else {
            llRelation = new LoLoRelation();
        }

        BeanUtils.copyProperties(dto, llRelation, new String[] { "loId", "relatedLoId", "attributes", "meta" });
        
        llRelation.setAttributes(toGenericAttributes(LoLoRelationAttribute.class, dto.getAttributes(), llRelation, dao));

        Lo lo = null;
        Lo relatedLo = null;
        LoLoRelationType relationType = null;
        try {
	        lo = dao.fetch(Lo.class, dto.getLoId());
	        relatedLo = dao.fetch(Lo.class, dto.getRelatedLoId());
	        relationType = dao.fetch(LoLoRelationType.class, dto.getTypeKey());
        } catch (DoesNotExistException dnee) {
        	throw new DoesNotExistException((null == lo ? "Lo" : (null == relatedLo ? "Related Lo" : "Lo-Lo relation type")) +
        									" does not exist when creating LoLoRelation", dnee);
        }
        
        llRelation.setLo(lo);
        llRelation.setRelatedLo(relatedLo);
        llRelation.setLoLoRelationType(relationType);
        llRelation.setState(dto.getStateKey());
        
        return llRelation;
	}
	
	public static LoLoRelationInfo toLoLoRelationInfo(LoLoRelation entity) {
		LoLoRelationInfo dto = new LoLoRelationInfo();
		
        BeanUtils.copyProperties(entity, dto,
                new String[] {"meta", "lo", "relatedLo", "type", "attributes", "state" });
        dto.setLoId(entity.getLo().getId());
        dto.setRelatedLoId(entity.getRelatedLo().getId());
        dto.setTypeKey(entity.getLoLoRelationType().getId());
        dto.setMeta(toMetaInfo(entity.getMeta(), entity.getVersionNumber()));
        dto.setAttributes(toAttributeList(entity.getAttributes()));
        dto.setStateKey(entity.getState());
        return dto;
	}

	public static List<LoLoRelationInfo> toLoLoRelationInfos( List<LoLoRelation> llRelations) {
		List<LoLoRelationInfo> llRelInfos = new ArrayList<LoLoRelationInfo>();
		for (LoLoRelation llRelation : llRelations) {
			llRelInfos.add(toLoLoRelationInfo(llRelation));
		}
		return llRelInfos;
	}
}
