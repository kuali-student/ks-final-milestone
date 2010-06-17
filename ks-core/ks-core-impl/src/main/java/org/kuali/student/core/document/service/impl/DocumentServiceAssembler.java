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

import org.kuali.student.core.document.dao.DocumentDao;
import org.kuali.student.core.document.dto.DocumentBinaryInfo;
import org.kuali.student.core.document.dto.DocumentCategoryInfo;
import org.kuali.student.core.document.dto.DocumentInfo;
import org.kuali.student.core.document.dto.DocumentTypeInfo;
import org.kuali.student.core.document.entity.Document;
import org.kuali.student.core.document.entity.DocumentAttribute;
import org.kuali.student.core.document.entity.DocumentCategory;
import org.kuali.student.core.document.entity.DocumentRichText;
import org.kuali.student.core.document.entity.DocumentType;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.service.impl.BaseAssembler;
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
        dto.setMetaInfo(toMetaInfo(entity.getMeta(), entity.getVersionInd()));
        dto.setAttributes(toAttributeMap(entity.getAttributes()));
        dto.setType(entity.getType().getId());
        dto.setDocumentBinaryInfo(new DocumentBinaryInfo());
        dto.getDocumentBinaryInfo().setBinary(entity.getDocument());
        return dto;
    }
    
    public static List<DocumentInfo> toDocumentInfos(List<Document> documents) {
        List<DocumentInfo> list = new ArrayList<DocumentInfo>();
        for (Document document : documents) {
            list.add(toDocumentInfo(document));
        }
        return list;
    }

    public static Document toDocument(DocumentInfo dto, DocumentDao dao) throws InvalidParameterException {
        return toDocument(new Document(), dto, dao);
    }
    public static Document toDocument(Document entity, DocumentInfo dto, DocumentDao dao) throws InvalidParameterException {
        if(entity == null)
            entity = new Document();
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
//        dto.setMetaInfo(toMetaInfo(entity.getMeta(), entity.getVersionInd()));
        dto.setAttributes(toAttributeMap(entity.getAttributes()));
        return dto;
    }

    public static List<DocumentCategoryInfo> toDocumentCategoryInfos(List<DocumentCategory> categories) {
        List<DocumentCategoryInfo> list = new ArrayList<DocumentCategoryInfo>();
        for (DocumentCategory documentCategory : categories) {
            list.add(toDocumentCategoryInfo(documentCategory));
        }
        return list;
    }

    public static DocumentTypeInfo toDocumentTypeInfo(DocumentType entity) {
        DocumentTypeInfo dto = new DocumentTypeInfo();

        BeanUtils.copyProperties(entity, dto,
                new String[] { "attributes"});
//        dto.setMetaInfo(toMetaInfo(entity.getMeta(), entity.getVersionInd()));
        dto.setAttributes(toAttributeMap(entity.getAttributes()));
        return dto;
    }
    
    public static List<DocumentTypeInfo> toDocumentTypeInfos(List<DocumentType> types) {
        List<DocumentTypeInfo> list = new ArrayList<DocumentTypeInfo>();
        for (DocumentType documentType : types) {
            list.add(toDocumentTypeInfo(documentType));
        }
        return list;
    }

}
