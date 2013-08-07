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

package org.kuali.student.r1.core.comment.service.impl;

import org.kuali.student.r1.core.comment.dao.CommentDao;
import org.kuali.student.r1.core.comment.entity.*;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.comment.dto.CommentInfo;
import org.kuali.student.r2.core.comment.dto.TagInfo;
import org.kuali.student.r2.core.service.assembly.BaseAssembler;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a description of what this class does - lindholm don't forget to fill this in.
 *
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
@Deprecated
public class CommentServiceAssembler extends BaseAssembler {

    public static CommentInfo toCommentInfo(Comment entity) {
        CommentInfo dto = new CommentInfo();

        dto.setId(entity.getId());
        dto.setTypeKey(entity.getType().getId());
        dto.setStateKey(entity.getState());
        dto.setCommentText(toRichTextInfo(entity.getCommentText()));
        // TODO: this needs to be persisted separately than assuming the commenter IS the person who creates the thing
        if (entity.getMeta() != null) {
          dto.setCommenterId(entity.getMeta().getCreateId());
        }
        dto.setReferenceTypeKey(entity.getReference().getReferenceType().getId());
        dto.setReferenceId(entity.getReference().getReferenceId());
        dto.setEffectiveDate(entity.getEffectiveDate());
        dto.setExpirationDate(entity.getExpirationDate());
        dto.setMeta(toMetaInfo(entity));
        dto.setAttributes(toAttributeList(entity.getAttributes()));
        return dto;
    }

    public static List<CommentInfo> toCommentInfos(List<Comment> entities) {
        List<CommentInfo> dtos = new ArrayList<CommentInfo>(entities.size());
        for (Comment entity : entities) {
            dtos.add(toCommentInfo(entity));
        }
        return dtos;
    }

    public static TagInfo toTagInfo(Tag entity) {
        TagInfo dto = new TagInfo();
        dto.setId(entity.getId());
        dto.setTypeKey(entity.getType().getId());
        dto.setStateKey(entity.getState());
        dto.setNamespace(entity.getNamespace());
        dto.setPredicate(entity.getPredicate());
        dto.setValue(entity.getValue());
        dto.setReferenceTypeKey(entity.getReferennce().getReferenceType().getId());
        dto.setReferenceId(entity.getReferennce().getReferenceId());
        dto.setEffectiveDate(entity.getEffectiveDate());
        dto.setExpirationDate(entity.getExpirationDate());
        dto.setMeta(toMetaInfo (entity));
        dto.setAttributes(toAttributeList(entity.getAttributes()));
        return dto;

    }

    public static List<TagInfo> toTagInfos(List<Tag> entries){
        List<TagInfo> dtos = new ArrayList<TagInfo>(entries.size());
        for(Tag entry: entries){
            dtos.add(toTagInfo(entry));
        }
        return dtos;
    }

    public static TypeInfo toTagTypeInfo(TagType entity){
        return toGenericTypeInfo(entity);
    }

    public static List<TypeInfo> toTagTypeInfos(List<TagType> entries){

        List<TypeInfo> dtos = new ArrayList<TypeInfo>();
        if(entries!=null){
            for (TagType entity : entries) {
                dtos.add(toTagTypeInfo(entity));
            }
        }
        return dtos;
    }

    public static List<TypeInfo> toCommentTypeInfos(List<CommentType> entities) {
        List<TypeInfo> dtos = new ArrayList<TypeInfo>();
        if(entities!=null){
            for (CommentType entity : entities) {
                dtos.add(toCommentTypeInfo(entity));
            }
        }
        return dtos;
    }
    private static TypeInfo toCommentTypeInfo(CommentType entity) {
        return toGenericTypeInfo(entity);
    }


    public static Comment toComment (boolean isUpdate,CommentInfo dto,CommentDao commentDao) throws InvalidParameterException, DoesNotExistException{
        Comment entity = new Comment();
        entity.setId(dto.getId());
        CommentType type = commentDao.fetch(CommentType.class, dto.getTypeKey());
        if (type == null) {
            throw new InvalidParameterException("Comment Type does not exist for id: " + dto.getTypeKey());
        }
        entity.setType(type);
        entity.setState(dto.getStateKey());
        entity.setCommentText(toRichText(CommentRichText.class, dto.getCommentText()));
        Reference reference = commentDao.getReference(dto.getReferenceId(), dto.getReferenceTypeKey());
        if (reference == null) {
            throw new InvalidParameterException(
                    "Reference does not exist for id: " + dto.getReferenceId());
        }
        entity.setReference(reference);
        entity.setEffectiveDate(dto.getEffectiveDate());
        entity.setExpirationDate(dto.getExpirationDate());
        dto.setMeta(toMetaInfo(entity.getMeta(), entity.getVersionNumber()));
        entity.setAttributes(toGenericAttributes(CommentAttribute.class, dto.getAttributes(), entity, commentDao));
        return entity;
    }
    
    public static Tag toTag(boolean isUpdate,TagInfo dto, CommentDao dao) throws InvalidParameterException, DoesNotExistException{
        Tag entity = new Tag();
        entity.setId(dto.getId());
        TagType type = dao.fetch(TagType.class,dto.getTypeKey());
        if (type == null) {
            throw new InvalidParameterException(
                    "Tag Type does not exist for id: " + dto.getTypeKey());
        }
        entity.setType(type);
        entity.setState(dto.getStateKey());
        entity.setNamespace(dto.getNamespace());
        entity.setPredicate(dto.getPredicate());
        entity.setValue (dto.getValue());
        Reference reference = dao.getReference(dto.getReferenceId(), dto.getReferenceTypeKey());
        if (reference == null) {
            throw new InvalidParameterException(
                    "Reference does not exist for id: " + dto.getReferenceId());
        }
        entity.setEffectiveDate(dto.getEffectiveDate());
        entity.setExpirationDate(dto.getExpirationDate ());
        entity.setReference(reference);
        entity.setAttributes(toGenericAttributes(TagAttribute.class,dto.getAttributes(),entity,dao));
        return entity;
    }

    public static Comment toComment(Comment entity, String referenceId,
            String referenceTypeKey, CommentInfo dto, CommentDao commentDao)
            throws InvalidParameterException, DoesNotExistException {

        entity.setId(dto.getId());
        CommentType type = commentDao.fetch(CommentType.class, dto.getType());
        if (type == null) {
            throw new InvalidParameterException(
                    "Comment Type does not exist for id: " + dto.getType());
        }
        entity.setType(type);
        entity.setState(dto.getStateKey());
        entity.setCommentText(toRichText(CommentRichText.class, dto.getCommentText()));
        Reference reference = commentDao.getReference(referenceId, referenceTypeKey);
        if (reference == null) {
            reference = new Reference();
            reference.setReferenceId(referenceId);
            try {
                ReferenceType referenceType = commentDao.fetch(ReferenceType.class, referenceTypeKey);
                reference.setReferenceType(referenceType);
                commentDao.create(reference);
            } catch (DoesNotExistException e) {
                throw new InvalidParameterException(e.getMessage());
            }
        }
        entity.setReference(reference);
        entity.setEffectiveDate(dto.getEffectiveDate());
        entity.setExpirationDate(dto.getExpirationDate());
        dto.setMeta(toMetaInfo(entity.getMeta(), entity.getVersionNumber()));
        entity.setAttributes(toGenericAttributes(CommentAttribute.class, dto.getAttributes(), entity, commentDao));
        return entity;
    }
//
//	public static List<ReferenceTypeInfo> toReferenceTypeInfos(
//			List<ReferenceType> entities) {
//		List<ReferenceTypeInfo> dtos = new ArrayList<ReferenceTypeInfo>(entities.size());
//		for (ReferenceType entity : entities) {
//			dtos.add(toReferenceType(entity));
//		}
//		return dtos;
//
//	}
//
//	private static ReferenceTypeInfo toReferenceType(ReferenceType entity) {
//		ReferenceTypeInfo dto = new ReferenceTypeInfo();
//        BeanUtils.copyProperties(entity, dto,new String[]{"attributes"});
//        // dto.setAttributes(toAttributeList(entity.getAttributes()));
//        return dto;
//	}

}
