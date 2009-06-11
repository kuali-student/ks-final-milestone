/*
 * Copyright 2009 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
import org.kuali.student.core.comment.entity.CommentType;
import org.kuali.student.core.comment.entity.Reference;
import org.kuali.student.core.comment.entity.Tag;
import org.kuali.student.core.comment.entity.TagAttribute;
import org.kuali.student.core.comment.entity.TagType;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.entity.RichText;
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
                new String[] { "commentText", "attributes", "type" });

        dto.setCommentText(toRichTextInfo(entity.getCommentText()));
        dto.setAttributes(toAttributeMap(entity.getAttributes()));
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

    public static RichTextInfo toRichTextInfo(RichText entity) {
        if(entity==null){
            return null;
        }

        RichTextInfo dto = new RichTextInfo();

        BeanUtils.copyProperties(entity, dto, new String[] { "id" });

        return dto;
    }


    public static TagInfo toTagInfo(Tag entity) {
        TagInfo dto = new TagInfo();
        BeanUtils.copyProperties(entity, dto, new String[]{"attributes","type","ref"});
        dto.setAttributes(toAttributeMap(entity.getAttributes()));
        dto.setType(entity.getType().getId());
        dto.setReferenceId(entity.getReferennce().getReferenceId());
        dto.setReferenceTypeKey(entity.getReferennce().getReferenceType());
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
        // TODO lindholm - THIS METHOD NEEDS JAVADOCS
        return null;
    }
    
    public static Tag toTag(boolean isUpdate,TagInfo dto, CommentDao dao) throws InvalidParameterException, DoesNotExistException{
        
        Tag entity = new Tag();
        BeanUtils.copyProperties(dto,entity,new String[]{"ref","type","attributes","metaInfo"});
        entity.setAttributes(toGenericAttributes(TagAttribute.class,dto.getAttributes(),entity,dao));
        
        Reference ref = dao.fetch(Reference.class,dto.getReferenceId());
        if (ref == null) {
            throw new InvalidParameterException(
                    "Reference does not exist for id: " + dto.getReferenceId());
        }
        
        entity.setReference(ref);
        
        TagType type = dao.fetch(TagType.class,dto.getType());
        if (type == null) {
            throw new InvalidParameterException(
                    "Tag Type does not exist for id: " + dto.getType());
        }
        entity.setType(type);
        return entity;
    }


}
