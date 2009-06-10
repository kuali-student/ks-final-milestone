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
package org.kuali.student.core.comment.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.kuali.student.core.comment.dao.CommentDao;
import org.kuali.student.core.comment.entity.Reference;
import org.kuali.student.core.comment.entity.ReferenceType;
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
    
    
    public List<Tag> getTags(String refId, String refType){
        Query query = em.createNamedQuery("Tag.getTagByType");
        query.setParameter("id", refId);
        query.setParameter("typeId", refType);
        List<Tag> tags = query.getResultList();
        return tags;
    }


}
