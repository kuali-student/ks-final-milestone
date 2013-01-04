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

package org.kuali.student.r1.core.subjectcode.dao;

import org.kuali.student.r1.core.subjectcode.model.SubjectCodeJoinOrg;
import org.kuali.student.r2.common.dao.GenericEntityDao;

import java.util.List;

/**
 * Enumeration Dao class.
 *
 * @Version 2.0
 */
public class SubjectCodeJoinOrgDao extends GenericEntityDao<SubjectCodeJoinOrg> {

    @SuppressWarnings("unchecked")
    public List<SubjectCodeJoinOrg> getBySubjectCodeAndOrgId(String subjectCode, String orgId) {
        if (orgId == null){
            return em.createQuery("from SubjectCodeJoinOrg e where e.subjectCode.code = :subjectCode ")
                    .setParameter("subjectCode", subjectCode).getResultList();
        } else if (subjectCode == null){
            return em.createQuery("from SubjectCodeJoinOrg e where e.orgId = :orgId").setParameter("orgId", orgId).getResultList();
        }
        return em.createQuery("from SubjectCodeJoinOrg e where e.subjectCode.code = :subjectCode " +
                " and e.orgId = :orgId")
                .setParameter("subjectCode", subjectCode).setParameter("orgId", orgId).getResultList();
    }

}
