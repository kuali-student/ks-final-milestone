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

package org.kuali.student.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.kuali.student.core.dao.CrudDao;
import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.dto.TypeInfo;
import org.kuali.student.core.entity.Attribute;
import org.kuali.student.core.entity.AttributeOwner;
import org.kuali.student.core.entity.Meta;
import org.kuali.student.core.entity.RichText;
import org.kuali.student.core.entity.Type;
import org.kuali.student.core.entity.Version;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.versionmanagement.dto.VersionInfo;
import org.springframework.beans.BeanUtils;

public class BaseAssembler {
    
    final static Logger logger = Logger.getLogger(BaseAssembler.class);

	public static Map<String, String> toAttributeMap(
			List<? extends Attribute<?>> attributes) {

		Map<String, String> attributeInfos = new HashMap<String, String>();

		for (Attribute<?> attribute : attributes) {
			attributeInfos.put(attribute.getName(), attribute.getValue());
		}

		return attributeInfos;
	}

	public static <A extends Attribute<O>, O extends AttributeOwner<A>> List<A> toGenericAttributes(
			Class<A> attributeClass, Map<String, String> attributeMap, O owner,
			CrudDao dao) throws InvalidParameterException {
		List<A> attributes = new ArrayList<A>();

		if(owner.getAttributes()==null){
			owner.setAttributes(new ArrayList<A>());
		}
		
		// Delete all the old attributes(if the owner is not null)
		for (A attribute : owner.getAttributes()) {
			dao.delete(attribute);
		}
		owner.getAttributes().clear();

		for (Map.Entry<String, String> attributeEntry : attributeMap.entrySet()) {

			A attribute;
			try {
				attribute = attributeClass.newInstance();
				attribute.setName(attributeEntry.getKey());
				attribute.setValue(attributeEntry.getValue());
				attribute.setOwner(owner);
				attributes.add(attribute);
			} catch (Exception e) {
				logger.error("Exception occured: ", e);
			}
		}

		return attributes;
	}

	/**
	 * @param <T>
	 *            TypeInfo class
	 * @param <S>
	 *            Type Class
	 * @param typeInfoClass
	 *            the class of the resulting typeInfo object
	 * @param typeEntity
	 *            the typeEntity to copy from
	 * @return a new TypeInfo
	 */
	public static <T extends TypeInfo, S extends Type<?>> T toGenericTypeInfo(
			Class<T> typeInfoClass, S typeEntity) {
		if (typeEntity == null) {
			return null;
		}

		T typeInfo;
		try {
			// Create a new TypeInfo based on the <T> class and copy the
			// properties
			typeInfo = typeInfoClass.newInstance();
			BeanUtils.copyProperties(typeEntity, typeInfo,
					new String[] { "attributes" });

			// Copy the attributes
			typeInfo.setAttributes(toAttributeMap(typeEntity.getAttributes()));

			//Copy the description
			typeInfo.setDescr(typeEntity.getDescr());
			
			return typeInfo;

		} catch (Exception e) {
			logger.error("Exception occured: ", e);
		}
		return null;

	}

	public static <T extends TypeInfo, S extends Type<?>> List<T> toGenericTypeInfoList(
			Class<T> typeInfoClass, List<S> typeEntities) {
		List<T> typeInfoList = new ArrayList<T>();
		if(typeEntities!=null){
			for (S typeEntity : typeEntities) {
				typeInfoList.add(toGenericTypeInfo(typeInfoClass, typeEntity));
			}
		}
		return typeInfoList;
	}

	protected static MetaInfo toMetaInfo(Meta meta, long versionInd) {

		MetaInfo metaInfo = new MetaInfo();
		// If there was a meta passed in then copy the values
		if (meta != null) {
			BeanUtils.copyProperties(meta, metaInfo);
		}
		metaInfo.setVersionInd(String.valueOf(versionInd));

		return metaInfo;
	}

	public static <T extends RichText> T toRichText(Class<T> richTextClass, RichTextInfo richTextInfo) {
		if(richTextInfo == null){
			return null;
		}
		
		T richText = null;
		
		try {
    		richText = richTextClass.newInstance();
    		BeanUtils.copyProperties(richTextInfo, richText);
    	} catch (Exception e) {
            throw new RuntimeException(e);
        }
    	
		return richText;
	}

	public static RichTextInfo toRichTextInfo(RichText entity) {
        if(entity==null){
            return null;
        }
        
        RichTextInfo dto = new RichTextInfo();
        BeanUtils.copyProperties(entity, dto, new String[] { "id" });
        
        return dto;
    }
	
	public static VersionInfo toVersionInfo(Version version) {
		if(version==null){
			return null;
		}
		VersionInfo versionInfo = new VersionInfo();
		versionInfo.setCurrentVersionStart(version.getCurrentVersionStart());
		versionInfo.setCurrentVersionEnd(version.getCurrentVersionEnd());
		versionInfo.setSequenceNumber(version.getSequenceNumber());
		versionInfo.setVersionComment(version.getVersionComment());
		versionInfo.setVersionIndId(version.getVersionIndId());
		versionInfo.setVersionedFromId(version.getVersionedFromId());
		
		return versionInfo;
	}
}
