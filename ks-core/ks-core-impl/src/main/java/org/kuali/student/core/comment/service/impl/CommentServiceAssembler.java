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
package org.kuali.student.core.comment.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.core.comment.dao.CommentDao;
import org.kuali.student.core.comment.dto.CommentInfo;
import org.kuali.student.core.comment.dto.CommentTypeInfo;
import org.kuali.student.core.comment.dto.TagInfo;
import org.kuali.student.core.comment.dto.TagTypeInfo;
import org.kuali.student.core.comment.entity.Comment;
import org.kuali.student.core.comment.entity.CommentAttribute;
import org.kuali.student.core.comment.entity.CommentRichText;
import org.kuali.student.core.comment.entity.CommentType;
import org.kuali.student.core.comment.entity.Reference;
import org.kuali.student.core.comment.entity.ReferenceType;
import org.kuali.student.core.comment.entity.Tag;
import org.kuali.student.core.comment.entity.TagAttribute;
import org.kuali.student.core.comment.entity.TagType;
import org.kuali.student.core.dto.ReferenceTypeInfo;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.service.impl.BaseAssembler;
import org.springframework.beans.BeanUtils;

/**
 * This is a description of what this class does - lindholm don't forget to fill this in.
 *
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
public class CommentServiceAssembler extends BaseAssembler {

    public static CommentInfo toCommentInfo(Comment entity) {
        CommentInfo dto = new CommentInfo();

        BeanUtils.copyProperties(entity, dto,
                new String[] { "commentText", "attributes", "type", "metaInfo" });

        dto.setCommentText(toRichTextInfo(entity.getCommentText()));
        dto.setAttributes(toAttributeMap(entity.getAttributes()));
        dto.setMetaInfo(toMetaInfo(entity.getMeta(), entity.getVersionInd()));
        dto.setType(entity.getType().getId());

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
        BeanUtils.copyProperties(entity, dto, new String[]{"attributes","type","reference"});
        dto.setAttributes(toAttributeMap(entity.getAttributes()));
        dto.setType(entity.getType().getId());
        dto.setReferenceId(entity.getReferennce().getReferenceId());
        dto.setReferenceTypeKey(entity.getReferennce().getReferenceType().getId());
        return dto;

    }

    public static List<TagInfo> toTagInfos(List<Tag> entries){
        List<TagInfo> dtos = new ArrayList<TagInfo>(entries.size());
        for(Tag entry: entries){
            dtos.add(toTagInfo(entry));
        }
        return dtos;
    }

    public static TagTypeInfo toTagTypeInfo(TagType entity){
        TagTypeInfo dto = new TagTypeInfo();
        BeanUtils.copyProperties(entity, dto,new String[]{"attributes"});
        dto.setAttributes(toAttributeMap(entity.getAttributes()));
        return dto;
    }

    public static List<TagTypeInfo> toTagTypeInfos(List<TagType> entries){
        List<TagTypeInfo> dtos = new ArrayList<TagTypeInfo>(entries.size());
        for(TagType entity: entries){
            dtos.add(toTagTypeInfo(entity));
        }

        return dtos;
    }

    public static List<CommentTypeInfo> toCommentTypeInfos(List<CommentType> entities) {
        List<CommentTypeInfo> dtos = new ArrayList<CommentTypeInfo>(entities.size());
        for (CommentType entity : entities) {
            dtos.add(toCommentTypeInfo(entity));
        }
        return dtos;
    }

    private static CommentTypeInfo toCommentTypeInfo(CommentType entity) {
    	CommentTypeInfo dto = new CommentTypeInfo();
        BeanUtils.copyProperties(entity, dto,new String[]{"attributes"});
        dto.setAttributes(toAttributeMap(entity.getAttributes()));
        return dto;
    }

    public static Comment toComment(boolean isUpdate,CommentInfo dto,CommentDao dao) throws InvalidParameterException, DoesNotExistException{

        Comment entity = new Comment();
        BeanUtils.copyProperties(dto,entity,new String[]{"reference","commentText", "attributes", "type", "metaInfo"});
        entity.setAttributes(toGenericAttributes(CommentAttribute.class,dto.getAttributes(),entity,dao));
        Reference reference = dao.getReference(dto.getReferenceId(), dto.getReferenceTypeKey());
        if (reference == null) {
            throw new InvalidParameterException(
                    "Reference does not exist for id: " + dto.getReferenceId());
        }
        entity.setReference(reference);
        CommentType type = dao.fetch(CommentType.class,dto.getType());
        if (type == null) {
            throw new InvalidParameterException(
                    "Tag Type does not exist for id: " + dto.getType());
        }
        entity.setType(type);
        entity.setCommentText(toRichText(CommentRichText.class, dto.getCommentText()));
        entity.setAttributes(toGenericAttributes(CommentAttribute.class, dto.getAttributes(), entity, dao));
		dto.setMetaInfo(toMetaInfo(entity.getMeta(), entity.getVersionInd()));
        // TODO comment type
        return entity;
    }
    public static Tag toTag(boolean isUpdate,TagInfo dto, CommentDao dao) throws InvalidParameterException, DoesNotExistException{

        Tag entity = new Tag();
        BeanUtils.copyProperties(dto,entity,new String[]{"reference","type","attributes"});
        entity.setAttributes(toGenericAttributes(TagAttribute.class,dto.getAttributes(),entity,dao));

        Reference reference = dao.getReference(dto.getReferenceId(), dto.getReferenceTypeKey());
        if (reference == null) {
            throw new InvalidParameterException(
                    "Reference does not exist for id: " + dto.getReferenceId());
        }
        entity.setReference(reference);
        TagType type = dao.fetch(TagType.class,dto.getType());
        if (type == null) {
            throw new InvalidParameterException(
                    "Tag Type does not exist for id: " + dto.getType());
        }
        entity.setType(type);
        return entity;
    }

    public static Comment toComment(String referenceId,
			String referenceTypeKey, CommentInfo dto,
			CommentDao commentDao) throws InvalidParameterException, DoesNotExistException {
    	Comment entity = new Comment();

    	return toComment(entity, referenceId, referenceTypeKey, dto, commentDao);
    }

    public static Comment toComment(Comment entity, String referenceId,
			String referenceTypeKey, CommentInfo dto, CommentDao commentDao)
			throws InvalidParameterException, DoesNotExistException {
		BeanUtils.copyProperties(dto, entity, new String[] { "id, reference",
				"commentText", "attributes", "type", "metaInfo" });
		entity.setCommentText(toRichText(CommentRichText.class, dto.getCommentText()));

		entity.setAttributes(toGenericAttributes(CommentAttribute.class, dto
				.getAttributes(), entity, commentDao));
		CommentType type = commentDao.fetch(CommentType.class, dto.getType());
		if (type == null) {
			throw new InvalidParameterException(
					"Tag Type does not exist for id: " + dto.getType());
		}
		entity.setType(type);
		dto.setMetaInfo(toMetaInfo(entity.getMeta(), entity.getVersionInd()));

		Reference reference = commentDao.getReference(referenceId,
				referenceTypeKey);
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

		return entity;
    }

	public static List<ReferenceTypeInfo> toReferenceTypeInfos(
			List<ReferenceType> entities) {
		List<ReferenceTypeInfo> dtos = new ArrayList<ReferenceTypeInfo>(entities.size());
		for (ReferenceType entity : entities) {
			dtos.add(toReferenceType(entity));
		}
		return dtos;

	}

	private static ReferenceTypeInfo toReferenceType(ReferenceType entity) {
		ReferenceTypeInfo dto = new ReferenceTypeInfo();

        BeanUtils.copyProperties(entity, dto,new String[]{"attributes"});
        dto.setAttributes(toAttributeMap(entity.getAttributes()));
        return dto;
	}
	
}
