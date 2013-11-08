/**
 * Copyright 2013 The Kuali Foundation Licensed under the
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
 *
 * Created by vgadiyak on 11/7/13
 */
package org.kuali.student.common.util.query;

import org.apache.commons.lang.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class QueryUtil {

    private EntityManager entityManager;
    private Integer maxInClauseElements = 100;
    private Boolean enableMaxIdFetch = Boolean.FALSE;

    /**
     * Returns a query of find by ids. Breaks up the query into multiple ORed IN() clauses if the set of ids is larger
     * than maxInClauseElements
     * @param queryStrStart a reference to the query string
     * @param queryStrEnd a reference to the query string, usually just ")"
     * @param primaryKeyMemberName name of the column that is the primary key
     * @param primaryKeys list of primary key strings
     * @param resultClass result class to be returned
     * @return a typed query that finds entities for the set of keys
     */
    // need this because Oracle has limitation of 4000 chars in string, and we may have more than that
    public TypedQuery buildQuery(StringBuilder queryStringRef, String queryStrEnd, String primaryKeyMemberName, List<String> primaryKeys, Class resultClass) {

        TypedQuery queryRef;

        if (!enableMaxIdFetch) {
            queryStringRef.append(primaryKeyMemberName).append(" IN (:ids)");
            queryRef = entityManager.createQuery(queryStringRef.toString(), resultClass);
            queryRef.setParameter("ids", primaryKeys);
        } else {
            //Max fetchh is enabled so break uip the where clause into multiple IN() clauses
            List<List<String>> brokenLists = new ArrayList<List<String>>();
            List<String> list = new ArrayList<String>();

            if (queryStrEnd != null && !StringUtils.isBlank(queryStrEnd) && StringUtils.equals(queryStrEnd.substring(0,1), ")")) {
                queryStringRef.append("(");
            }

            Iterator<String> itr = primaryKeys.iterator();
            for (int index = 0; itr.hasNext(); index++) {
                if ((index > 0) && (index % maxInClauseElements == 0)) {
                    brokenLists.add(list);
                    if (brokenLists.size() == 1) {
                        queryStringRef.append(primaryKeyMemberName).append(" IN (:ids1)");
                    } else {
                        queryStringRef.append(" OR ").append(primaryKeyMemberName).append(" IN (:ids").append(brokenLists.size()).append(")");
                    }
                    list = new ArrayList<String>();
                }
                list.add(itr.next());
            }
            if (!list.isEmpty()) {
                brokenLists.add(list);
                queryStringRef.append(" OR ").append(primaryKeyMemberName).append(" IN (:ids").append(brokenLists.size()).append(")");
            }

            if (queryStrEnd != null && !StringUtils.isBlank(queryStrEnd)) {
                queryStringRef.append(queryStrEnd);
            }

            queryRef = entityManager.createQuery(queryStringRef.toString(), resultClass);

            for (int i = 1; i <= brokenLists.size(); i++) {
                queryRef.setParameter("ids" + i, brokenLists.get(i - 1));
            }
        }

        return queryRef;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void setMaxInClauseElements(Integer maxInClauseElements) {
        this.maxInClauseElements = maxInClauseElements;
    }

    public void setEnableMaxIdFetch(Boolean enableMaxIdFetch) {
        this.enableMaxIdFetch = enableMaxIdFetch;
    }
}
