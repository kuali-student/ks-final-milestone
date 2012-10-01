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

package org.kuali.student.core.document.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.exceptions.DoesNotExistException;
import org.kuali.student.common.exceptions.InvalidParameterException;
import org.kuali.student.common.exceptions.OperationFailedException;
import org.kuali.student.common.service.impl.BaseAssembler;
import org.kuali.student.core.document.dao.DocumentDao;
import org.kuali.student.core.document.dto.DocumentBinaryInfo;
import org.kuali.student.core.document.dto.DocumentCategoryInfo;
import org.kuali.student.core.document.dto.DocumentInfo;
import org.kuali.student.core.document.dto.RefDocRelationInfo;
import org.kuali.student.core.document.entity.Document;
import org.kuali.student.core.document.entity.DocumentAttribute;
import org.kuali.student.core.document.entity.DocumentCategory;
import org.kuali.student.core.document.entity.DocumentRichText;
import org.kuali.student.core.document.entity.RefDocRelation;
import org.kuali.student.core.document.entity.RefDocRelationAttribute;
import org.kuali.student.core.document.entity.RefDocRelationType;
import org.kuali.student.core.document.entity.RefObjectSubType;
import org.kuali.student.core.document.entity.RefObjectType;
import org.springframework.beans.BeanUtils;

/**
 * This is a description of what this class does - lindholm don't forget to fill this in.
 *
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
public class DocumentServiceAssembler extends BaseAssembler {

    public static DocumentInfo toDocumentInfo(Document entity) {
        DocumentInfo dto = new DocumentInfo();

        BeanUtils.copyProperties(entity, dto,
                new String[] { "desc", "attributes", "metaInfo","type", "categoryList", "document" });
        dto.setDesc(toRichTextInfo(entity.getDescr()));
        dto.setMetaInfo(toMetaInfo(entity));
        dto.setAttributes(toAttributeMap(entity.getAttributes()));
        dto.setType(entity.getType().getId());
        dto.setDocumentBinaryInfo(new DocumentBinaryInfo());
        dto.getDocumentBinaryInfo().setBinary(entity.getDocument());
        return dto;
    }
    
    public static List<DocumentInfo> toDocumentInfos(List<Document> entities) {
        List<DocumentInfo> dtos = new ArrayList<DocumentInfo>();
        if(entities!=null){
	        for (Document entity : entities) {
	        	dtos.add(toDocumentInfo(entity));
	        }
       	}
        return dtos;
    }

    public static Document toDocument(Document entity, DocumentInfo dto, DocumentDao dao) throws InvalidParameterException {
        BeanUtils.copyProperties(dto, entity,
                new String[] { "desc", "attributes", "metaInfo", "type", "id", "documentBinaryInfo" });
        entity.setDescr(toRichText(DocumentRichText.class, dto.getDesc()));
        entity.setDocument(dto.getDocumentBinaryInfo().getBinary());
        entity.setAttributes(toGenericAttributes(DocumentAttribute.class, dto.getAttributes(), entity, dao));
        return entity;
    }

    public static DocumentCategoryInfo toDocumentCategoryInfo(DocumentCategory entity) {
        DocumentCategoryInfo dto = new DocumentCategoryInfo();

        BeanUtils.copyProperties(entity, dto,
                new String[] { "desc", "attributes", "type", "documents" });
        dto.setDesc(toRichTextInfo(entity.getDescr()));
        dto.setAttributes(toAttributeMap(entity.getAttributes()));
        return dto;
    }

    public static List<DocumentCategoryInfo> toDocumentCategoryInfos(List<DocumentCategory> entities) {
        List<DocumentCategoryInfo> dtos = new ArrayList<DocumentCategoryInfo>();
        if(entities!=null){
	        for (DocumentCategory entity : entities) {
	        	dtos.add(toDocumentCategoryInfo(entity));
	        }
        }
        return dtos;
    }

	public static RefDocRelationInfo toRefDocRelationInfo(RefDocRelation entity) {
		RefDocRelationInfo dto = new RefDocRelationInfo();
		
		dto.setAttributes(toAttributeMap(entity.getAttributes()));
		dto.setDesc(toRichTextInfo(entity.getDescr()));
		dto.setDocumentId(entity.getDocument().getId());
		dto.setEffectiveDate(entity.getEffectiveDate());
		dto.setExpirationDate(entity.getExpirationDate());
		dto.setId(entity.getId());
		dto.setMetaInfo(toMetaInfo(entity));
		dto.setRefObjectId(entity.getRefObjectId());
		dto.setRefObjectTypeKey(entity.getRefObjectType().getId());
		dto.setState(entity.getState());
		dto.setTitle(entity.getTitle());
		dto.setType(entity.getRefDocRelationType().getId());
		
		return dto;
	}

	public static List<RefDocRelationInfo> toRefDocRelationInfos(
			List<RefDocRelation> entities) {
		List<RefDocRelationInfo> dtos = new ArrayList<RefDocRelationInfo>();
		if(entities!=null){
			for(RefDocRelation entity:entities){
				dtos.add(toRefDocRelationInfo(entity));
			}
		}
		return dtos;
	}

	public static RefDocRelation toRefDocRelation(RefDocRelation entity,
			RefDocRelationInfo dto, DocumentDao dao) throws InvalidParameterException, OperationFailedException {
        
		//Substructures that need to be looked up from persistence
		RefObjectType refObjectType = null; 
        RefDocRelationType refDocRelationType;
        Document document;
        
        //Get the relation type
        try{
        	refDocRelationType = dao.fetch(RefDocRelationType.class, dto.getType());
        }catch(DoesNotExistException e){
        	throw new InvalidParameterException("RefDocRelationType does not exist for key:" + dto.getType());
        } 
        
        //Check that the relation type has subtypes
        if(refDocRelationType.getRefObjectSubTypes()==null){
        	throw new OperationFailedException("No RefObjectSubTypes are defined for this relation Type");
        }
        
        //Check that that the object type is valid for this relation type and get the object type in the process
        for(RefObjectSubType subType:refDocRelationType.getRefObjectSubTypes()){
        	if(dto.getRefObjectTypeKey().equals(subType.getRefObjectType().getId())){
        		refObjectType = subType.getRefObjectType();
        		break;
        	}
        }
        if(refObjectType == null){
        	throw new InvalidParameterException("RefObjectType: " + dto.getRefObjectTypeKey()+
        			" does not exist or is invalid for relation type: " + dto.getType());
        }
        
        //Get the document
        try{
        	document = dao.fetch(Document.class, dto.getDocumentId());
        }catch(DoesNotExistException e){
        	throw new InvalidParameterException("Document does not exist for key:" + dto.getDocumentId());
        } 
        
        //Copy the fields
        entity.setAttributes(toGenericAttributes(RefDocRelationAttribute.class, dto.getAttributes(), entity, dao));
        entity.setDescr(toRichText(DocumentRichText.class, dto.getDesc()));
        entity.setDocument(document);
        entity.setEffectiveDate(dto.getEffectiveDate());
        entity.setExpirationDate(dto.getExpirationDate());
        entity.setId(dto.getId());
        entity.setRefDocRelationType(refDocRelationType);
        entity.setRefObjectId(dto.getRefObjectId());
        entity.setRefObjectType(refObjectType);
        entity.setState(dto.getState());
        entity.setTitle(dto.getTitle());
        
        return entity;
	}

}
