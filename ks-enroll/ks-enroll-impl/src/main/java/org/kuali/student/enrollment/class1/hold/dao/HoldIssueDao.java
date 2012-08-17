/*
 * Copyright 2007 The Kuali Foundation
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
package org.kuali.student.enrollment.class1.hold.dao;

import java.util.List;
import javax.persistence.Query;

import org.kuali.student.enrollment.class1.hold.model.HoldIssueEntity;
import org.kuali.student.r2.common.dao.GenericEntityDao;

/**
 * This is a description of what this class does - andy don't forget to fill this in.
 *
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 */
public class HoldIssueDao
        extends GenericEntityDao<HoldIssueEntity> {

    public List<HoldIssueEntity> getByOrganizationId(String orgId) {
        Query query = em.createNamedQuery("HoldIssueEntity.getByOrganization");
        query.setParameter("organizationId", orgId);
        return query.getResultList();
    }

    public List<String> getIdsByType(String type) {
        Query query = em.createNamedQuery("HoldIssueEntity.getIdsByType");
        query.setParameter("type", type);
        return query.getResultList();
    }
}
