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

package org.kuali.student.core.comment.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.kuali.student.core.comment.dao.CommentDao;
import org.kuali.student.core.comment.entity.Comment;
import org.kuali.student.core.comment.entity.CommentType;
import org.kuali.student.core.comment.entity.Reference;
import org.kuali.student.core.comment.entity.Tag;
import org.kuali.student.core.dao.impl.AbstractSearchableCrudDaoImpl;

/**
 * This is a description of what this class does - lindholm don't forget to fill this in.
 *
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
public class CommentDaoImpl extends AbstractSearchableCrudDaoImpl implements CommentDao {
    @PersistenceContext(unitName = "Comment")
    @Override
    public void setEm(EntityManager em) {
        super.setEm(em);
    }


    public Comment getComment(String referenceId, String referenceTypeKey){
        Query query = em.createNamedQuery("Comment.getComment");
        query.setParameter("refId", referenceId);
        query.setParameter("refTypeId", referenceTypeKey);
        Comment comment = (Comment) query.getSingleResult();
        return comment;

    }

    public List<Comment> getComments(String referenceId, String referenceTypeKey){
        Query query = em.createNamedQuery("Comment.getComments");
        query.setParameter("refId", referenceId);
        query.setParameter("refTypeId", referenceTypeKey);
        @SuppressWarnings("unchecked")
        List<Comment> comments = query.getResultList();
        return comments;
    }

    public List<Comment> getCommentsByRefId(String referenceId){
        Query query = em.createNamedQuery("Comment.getCommentsByRefId");
        query.setParameter("refId", referenceId);
        @SuppressWarnings("unchecked")
        List<Comment> comments = query.getResultList();
        return comments;
    }

    public List<Comment> getCommentsByType(String referenceId, String referenceTypeKey, String commentTypeKey ){
        Query query = em.createNamedQuery("Comment.getCommentsByType");
        query.setParameter("refId", referenceId);
        query.setParameter("refTypeId", referenceTypeKey);
        query.setParameter("commentTypeId", commentTypeKey);
        @SuppressWarnings("unchecked")
        List<Comment> comments = query.getResultList();
        return comments;
    }

	public List<CommentType> getCommentTypesByReferenceTypeId(String referenceTypeId) {
        Query query = em.createNamedQuery("CommentType.getCommentTypesByReferenceTypeId");
        query.setParameter("referenceTypeId", referenceTypeId);
        @SuppressWarnings("unchecked")
        List<CommentType> comments = query.getResultList();
        return comments;

    }

    public Tag getTag(String referenceId, String referenceTypeKey){
        Query query = em.createNamedQuery("Tag.getTag");
        query.setParameter("refId", referenceId);
        query.setParameter("refTypeId", referenceTypeKey);
        Tag tag = (Tag)query.getSingleResult();
        return tag;
    }
    public List<Tag> getTags(String referenceId, String referenceTypeKey){
        Query query = em.createNamedQuery("Tag.getTags");
        query.setParameter("refId", referenceId);
        query.setParameter("refTypeId", referenceTypeKey);
        @SuppressWarnings("unchecked")
        List<Tag> tags = query.getResultList();
        return tags;
    }

    public List<Tag> getTagsByRefId(String referenceId){
        Query query = em.createNamedQuery("Tag.getTagsByRefId");
        query.setParameter("refId", referenceId);
        @SuppressWarnings("unchecked")
        List<Tag> tags = query.getResultList();
        return tags;
    }

    public List<Tag> getTagsByType(String referenceId, String referenceTypeKey, String tagTypeKey ){
        Query query = em.createNamedQuery("Tag.getTagsByType");
        query.setParameter("refId", referenceId);
        query.setParameter("refTypeId", referenceTypeKey);
        query.setParameter("tagTypeId", tagTypeKey);
        @SuppressWarnings("unchecked")
        List<Tag> tags = query.getResultList();
        return tags;
    }

    public Reference getReference(String referenceId, String referenceType){
        Query query = em.createNamedQuery("Reference.getReference");
        query.setParameter("refId", referenceId);
        query.setParameter("refTypeId", referenceType);
        try{
        Reference reference = (Reference)query.getSingleResult();
        return reference;
        }
        catch(NoResultException e){
            return null;
        }


    }


}
