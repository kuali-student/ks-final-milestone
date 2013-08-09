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

import org.kuali.student.r1.core.subjectcode.model.SubjectCode;
import org.kuali.student.r1.core.subjectcode.model.SubjectCodeJoinOrg;
import org.kuali.student.r2.common.dao.GenericEntityDao;

import javax.persistence.Query;
import java.util.List;

/**
 * Enumeration Dao class.
 *
 * @Version 2.0
 */
public class SubjectCodeDao extends GenericEntityDao<SubjectCode> {

    @SuppressWarnings("unchecked")
    public List<SubjectCode> getBySubjectCode(String subjectCode) {
        String queryString = "from SubjectCode e ";
        if (subjectCode != null) {
            queryString += "where e.code like :subjectCode ";
        }
        Query query = em.createQuery(queryString);
        if (subjectCode != null) {
            query.setParameter("subjectCode", subjectCode);
        }
        return query.getResultList();
    }
}
