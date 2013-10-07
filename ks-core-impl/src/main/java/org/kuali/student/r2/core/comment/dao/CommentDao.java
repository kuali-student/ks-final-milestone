/*
 * Copyright 2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.r2.core.comment.dao;

import org.kuali.student.r2.common.dao.GenericEntityDao;
import org.kuali.student.r2.core.comment.model.CommentEntity;

import javax.persistence.Query;
import java.util.List;

public class CommentDao extends GenericEntityDao<CommentEntity> {

    public List<String> getIdsByType(String type) {
        Query query = em.createNamedQuery(CommentEntity.COMMENT_QUERY_GET_IDS_BY_TYPE);
        query.setParameter("type", type);
        return query.getResultList();
    }

    public List<CommentEntity> getCommentsByRefObjectIdAndRefObjectType(String id, String type) {
        Query query = em.createNamedQuery(CommentEntity.COMMENT_QUERY_GET_COMMENTS_BY_REFERENCE_ID_REFERENCE_TYPE);
        query.setParameter("id", id);
        query.setParameter("type", type);
        return query.getResultList();
    }

}
