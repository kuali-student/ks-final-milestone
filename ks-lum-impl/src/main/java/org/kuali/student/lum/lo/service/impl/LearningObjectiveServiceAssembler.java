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
package org.kuali.student.lum.lo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.core.dao.CrudDao;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.service.impl.BaseAssembler;
import org.kuali.student.lum.lo.dao.LoDao;
import org.kuali.student.lum.lo.dto.LoCategoryInfo;
import org.kuali.student.lum.lo.dto.LoHierarchyInfo;
import org.kuali.student.lum.lo.dto.LoInfo;
import org.kuali.student.lum.lo.dto.LoTypeInfo;
import org.kuali.student.lum.lo.entity.Lo;
import org.kuali.student.lum.lo.entity.LoAttribute;
import org.kuali.student.lum.lo.entity.LoCategory;
import org.kuali.student.lum.lo.entity.LoCategoryAttribute;
import org.kuali.student.lum.lo.entity.LoHierarchy;
import org.kuali.student.lum.lo.entity.LoRichText;
import org.kuali.student.lum.lo.entity.LoType;
import org.springframework.beans.BeanUtils;

public class LearningObjectiveServiceAssembler extends BaseAssembler {

	public static LoRichText toLoRichText(RichTextInfo richTextInfo) {
		if(richTextInfo == null){
			return null;
		}
		LoRichText richText = new LoRichText();
		BeanUtils.copyProperties(richTextInfo, richText);
		return richText;
	}

    public static RichTextInfo toRichTextInfo(LoRichText entity) {
        if(entity==null){
            return null;
        }

        RichTextInfo dto = new RichTextInfo();

        BeanUtils.copyProperties(entity, dto, new String[] { "id" });

        return dto;

    }

    public static Lo toLo(LoInfo dto, CrudDao dao) throws InvalidParameterException {
        return toLo(new Lo(), dto, dao);
    }
    public static Lo toLo(Lo entity, LoInfo dto, CrudDao dao) throws InvalidParameterException {
        if(entity == null)
            entity = new Lo();
        BeanUtils.copyProperties(dto, entity,
                new String[] { "desc", "attributes", "metaInfo", "type", "id" });
        entity.setDesc(toLoRichText(dto.getDesc()));
        entity.setAttributes(toGenericAttributes(LoAttribute.class, dto.getAttributes(), entity, dao));
        return entity;
    }

    public static LoInfo toLoInfo(Lo entity) {
        LoInfo dto = new LoInfo();

        BeanUtils.copyProperties(entity, dto,
                new String[] { "desc", "attributes", "type", "loHierarchy" });
        dto.setDesc(toRichTextInfo(entity.getDesc()));
        dto.setMetaInfo(toMetaInfo(entity.getMeta(), entity.getVersionInd()));
        dto.setAttributes(toAttributeMap(entity.getAttributes()));
        dto.setType(entity.getLoType().getId());
        dto.setLoHierarchy(entity.getLoHierarchy() == null? null: entity.getLoHierarchy().getId());
        return dto;
    }

    public static LoCategory toLoCategory(LoCategoryInfo dto, LoDao dao) throws InvalidParameterException {
        return toLoCategory(new LoCategory(), dto, dao);
    }
    public static LoCategory toLoCategory(LoCategory entity, LoCategoryInfo dto, LoDao dao) throws InvalidParameterException {
        if(entity == null)
            entity = new LoCategory();
        BeanUtils.copyProperties(dto, entity,
                new String[] { "desc", "attributes", "metaInfo", "loHierarchy", "id" });
        entity.setDesc(toLoRichText(dto.getDesc()));
        entity.setAttributes(toGenericAttributes(LoCategoryAttribute.class, dto.getAttributes(), entity, dao));
        return entity;
    }

    public static LoCategoryInfo toLoCategoryInfo(LoCategory entity) {
        LoCategoryInfo dto = new LoCategoryInfo();

        BeanUtils.copyProperties(entity, dto,
                new String[] { "desc", "attributes", "loHierarchy" });
        dto.setDesc(toRichTextInfo(entity.getDesc()));
        dto.setMetaInfo(toMetaInfo(entity.getMeta(), entity.getVersionInd()));
        dto.setAttributes(toAttributeMap(entity.getAttributes()));
        dto.setLoHierarchy(entity.getLoHierarchy().getId());
        return dto;
    }
    
    public static LoHierarchyInfo toLoHierarchyInfo(LoHierarchy entity) {
        LoHierarchyInfo dto = new LoHierarchyInfo();
        
        BeanUtils.copyProperties(entity, dto,
                new String[] { "desc", "attributes", "rootLo" });
        dto.setDesc(toRichTextInfo(entity.getDesc()));
        dto.setMetaInfo(toMetaInfo(entity.getMeta(), entity.getVersionInd()));
        dto.setAttributes(toAttributeMap(entity.getAttributes()));
        dto.setRootLoId(entity.getRootLo() == null? null :entity.getRootLo().getId());
        return dto;
    }
    
    public static LoTypeInfo toLoTypeInfo(LoType entity) {
        LoTypeInfo dto = new LoTypeInfo();
        
        BeanUtils.copyProperties(entity, dto,
                new String[] { "attributes" });
//        dto.setMetaInfo(toMetaInfo(entity.getMeta(), entity.getVersionInd()));
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
        List<LoCategoryInfo> list = new ArrayList<LoCategoryInfo>();
        for (LoCategory loCategory : categories) {
            list.add(toLoCategoryInfo(loCategory));
        }
        return list;
    }

    public static List<LoHierarchyInfo> toLoHierarchyInfos(List<LoHierarchy> hierarchies) {
        List<LoHierarchyInfo> list = new ArrayList<LoHierarchyInfo>();
        for (LoHierarchy loHierarchy : hierarchies) {
            list.add(toLoHierarchyInfo(loHierarchy));
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

}
