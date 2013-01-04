package org.kuali.student.r2.common.criteria;

import org.kuali.rice.core.api.criteria.GenericQueryResults;
import org.kuali.rice.core.api.criteria.QueryByCriteria;

public interface CriteriaLookupService {

    /**
     * Looks up a type based on a query criteria.
     *
     * @param queryClass the class to lookup
     * @param criteria the criteria to lookup against. cannot be null.
     * @param <T> the type that is being looked up.
     * @return the results. will never be null.
     * @throws IllegalArgumentException if the criteria is null
     */
    <T> GenericQueryResults<T> lookup(final Class<T> queryClass, final QueryByCriteria criteria);

    /**
     * Looks up a type based on a query criteria.
     *
     * @param queryClass the class to lookup
     * @param criteria the criteria to lookup against. cannot be null.
     * @param <T> the type that is being looked up.
     * @return the results. will never be null.
     * @throws IllegalArgumentException if the criteria is null
     */
    <T> GenericQueryResults<T> lookup(final Class<T> queryClass, final QueryByCriteria criteria, final LookupCustomizer<T> customizer);
    
}