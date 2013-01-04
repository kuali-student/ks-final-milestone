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

package org.kuali.student.r2.core.class1.enumerationmanagement.dao;

import java.util.Date;
import java.util.List;

import org.kuali.student.r2.common.dao.GenericEntityDao;
import org.kuali.student.r2.core.class1.enumerationmanagement.model.EnumeratedValueEntity;

/**
 * Enumerated Value Dao class.
 *
 * @Version 2.0
 */
public class EnumeratedValueDao extends GenericEntityDao<EnumeratedValueEntity> {

    @SuppressWarnings("unchecked")
    public List<EnumeratedValueEntity> getByDate(String enumerationKey, Date contextDate) {
        return em.createQuery("from EnumeratedValueEntity e where e.effectiveDate <= :contextDate and " + 
                "(e.expirationDate is null or e.expirationDate >= :contextDate) and e.enumeration.id = :enumerationKey ")
                .setParameter("enumerationKey", enumerationKey).setParameter("contextDate", contextDate).getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<EnumeratedValueEntity> getByContextAndDate(String enumerationKey, String contextKey, String contextValue, Date contextDate) {
        return em.createQuery("select e from EnumeratedValueEntity e , IN(e.contextValueEntities) c " +
                "where e.effectiveDate <= :contextDate and (e.expirationDate is null or e.expirationDate >= :contextDate) and " + 
                "c.contextValue = :contextValue and c.contextKey = :enumContextKey and e.enumeration.id = :enumKey ")
                .setParameter("contextDate", contextDate).setParameter("contextValue", contextValue).setParameter("enumContextKey", contextKey)
                .setParameter("enumKey", enumerationKey).getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<EnumeratedValueEntity> getByContextTypeAndValue(String enumerationKey, String contextTypeKey, String contextValue) {
        return em.createQuery("select e from EnumeratedValueEntity e JOIN e.contextValueEntities c " + 
                "where c.contextValue = :contextValue and c.contextKey = :enumContextKey and " + 
                "e.enumeration.id = :enumerationKey ").setParameter("enumerationKey", enumerationKey)
                .setParameter("enumContextKey", contextTypeKey).setParameter("contextValue", contextValue).getResultList();
    }
    
    @SuppressWarnings("unchecked")
    public List<EnumeratedValueEntity> getByEnumerationKey(String enumerationKey) {
        return em.createQuery("from EnumeratedValueEntity e where e.enumeration.id = :enumerationKey order by lower(e.code) asc")
                .setParameter("enumerationKey", enumerationKey).getResultList();
    }
    
    public EnumeratedValueEntity getByEnumerationKeyAndCode(String enumerationKey, String code) {
        return (EnumeratedValueEntity) (em.createQuery("select e from EnumeratedValueEntity e where e.enumeration.id = :enumerationKey and e.code = :code")
                .setParameter("enumerationKey", enumerationKey).setParameter("code", code).getSingleResult());
        
    }
}
