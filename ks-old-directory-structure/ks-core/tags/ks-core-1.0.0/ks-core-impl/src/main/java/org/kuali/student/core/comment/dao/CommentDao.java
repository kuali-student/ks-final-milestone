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

package org.kuali.student.core.comment.dao;

import java.util.List;

import org.kuali.student.core.comment.entity.Comment;
import org.kuali.student.core.comment.entity.CommentType;
import org.kuali.student.core.comment.entity.Reference;
import org.kuali.student.core.comment.entity.Tag;
import org.kuali.student.core.dao.CrudDao;
import org.kuali.student.core.dao.SearchableDao;

/**
 * This is a description of what this class does - lindholm don't forget to fill this in.
 *
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
public interface CommentDao extends CrudDao, SearchableDao {
    public Comment getComment(String referenceId, String referenceTypeKey);
    public List<Comment> getComments(String referenceId, String referenceTypeKey);
    public List<Comment> getCommentsByRefId(String referenceId);
    public List<Comment> getCommentsByType(String referenceId, String referenceTypeKey, String commentTypeKey );
    public List<CommentType> getCommentTypesByReferenceTypeId(String referenceTypeId);
    public Tag getTag(String referenceId, String referenceTypeKey);
    public List<Tag> getTags(String referenceId, String referenceTypeKey);
    public List<Tag> getTagsByRefId(String referenceId);
    public List<Tag> getTagsByType(String referenceId, String referenceTypeKey, String tagTypeKey );
    public Reference getReference(String referenceId, String referenceType);
}
