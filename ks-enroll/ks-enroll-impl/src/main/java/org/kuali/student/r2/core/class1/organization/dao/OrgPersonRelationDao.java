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

package org.kuali.student.r2.core.class1.organization.dao;

import java.util.List;

import org.kuali.student.core.organization.entity.OrgPersonRelation;
import org.kuali.student.enrollment.dao.GenericEntityDao;

/**
 * Enumeration Dao class.
 *
 * @Version 2.0
 */
public class OrgPersonRelationDao extends GenericEntityDao<OrgPersonRelation> {

    @SuppressWarnings("unchecked")
    public List<OrgPersonRelation> getOrgPersonRelationsByTypeAndOrg(String orgPersonRelationTypeKey, String orgId) {
        return em.createQuery("from OrgPersonRelation r where r.orgPersonRelationType.id = :orgPersonRelationTypeKey and" +
        		" r.org.id = :orgId")
                .setParameter("orgPersonRelationTypeKey", orgPersonRelationTypeKey).setParameter("orgId", orgId).getResultList();
    }
    
    @SuppressWarnings("unchecked")
    public List<OrgPersonRelation> getOrgPersonRelationsByPerson(String personId) {
        return em.createQuery("from OrgPersonRelation r where r.personId = :personId")
                .setParameter("personId", personId).getResultList();
    }
    
    @SuppressWarnings("unchecked")
    public List<OrgPersonRelation> getOrgPersonRelationsByTypeAndPerson(String orgPersonRelationTypeKey, String personId) {
        return em.createQuery("from OrgPersonRelation r where r.orgPersonRelationType.id = :orgPersonRelationTypeKey and" +
                " r.personId = :personId")
                .setParameter("orgPersonRelationTypeKey", orgPersonRelationTypeKey).setParameter("personId", personId).getResultList();
    }
    
    @SuppressWarnings("unchecked")
    public List<OrgPersonRelation> getOrgPersonRelationsByOrgAndPerson(String orgId, String personId) {
        return em.createQuery("from OrgPersonRelation r where r.org.id = :orgId and r.personId = :personId")
                .setParameter("orgId", orgId).setParameter("personId", personId).getResultList();
    }
    
    @SuppressWarnings("unchecked")
    public List<OrgPersonRelation> getOrgPersonRelationsByTypeAndOrgAndPerson(String orgPersonRelationTypeKey, String orgId, String personId) {
        return em.createQuery("from OrgPersonRelation r where r.orgPersonRelationType.id = :orgPersonRelationTypeKey and" +
                " r.org.id = :orgId and r.personId = :personId")
                .setParameter("orgPersonRelationTypeKey", orgPersonRelationTypeKey).setParameter("orgId", orgId)
                .setParameter("personId", personId).getResultList();
    }
    
    
}
