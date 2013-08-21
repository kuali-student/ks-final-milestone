/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 */
package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.kuali.rice.core.api.criteria.GenericQueryResults;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.mock.MockService;
import org.kuali.student.r2.common.criteria.CriteriaLookupService;
import org.kuali.student.r2.common.criteria.LookupCustomizer;

import java.util.ArrayList;
import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class CriteriaLookupServiceMockImpl implements CriteriaLookupService,
        MockService {
    @Override
    public <T> GenericQueryResults<T> lookup(Class<T> queryClass, QueryByCriteria criteria) {
        GenericQueryResults.Builder<T> results = GenericQueryResults.Builder.<T>create();
        results.setTotalRowCount(0);
        results.setResults(new ArrayList<T>());
        return results.build();
    }

    @Override
    public <T> GenericQueryResults<T> lookup(Class<T> queryClass, QueryByCriteria criteria, LookupCustomizer<T> customizer) {
        GenericQueryResults.Builder<T> results = GenericQueryResults.Builder.<T>create();
        results.setTotalRowCount(0);
        results.setResults(new ArrayList<T>());
        return results.build();
    }

    @Override
    public void clear() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public <T> GenericQueryResults<String> lookupIds(Class<T> queryClass, QueryByCriteria criteria) {

        return null;
    }

    @Override
    public <T> GenericQueryResults<String> lookupIds(Class<T> queryClass, QueryByCriteria criteria, LookupCustomizer<T> customizer) {

        return null;
    }

    @Override
    public <T> GenericQueryResults<List<String>> genericLookup(final Class<T> queryClass, final QueryByCriteria criteria, List<String> fields){
        return null;
    }

    @Override
    public <T> GenericQueryResults<List<String>> genericLookup(final Class<T> queryClass, final QueryByCriteria criteria, final LookupCustomizer<T> customizer, List<String> fields){
        return null;

    }

}
