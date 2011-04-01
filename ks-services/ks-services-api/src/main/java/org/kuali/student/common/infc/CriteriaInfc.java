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
package org.kuali.student.common.infc;

import java.util.List;

/**
 * Criteria for a generic query
 */
public interface CriteriaInfc {

    /**
     * Name: Comparisons
     *
     * The list of comparisons to be applied
     * TODO: Decide if null or empty list is ok?  I.e. can you get ALL?
     */
    public List<? extends ComparisonInfc> getComparisons();

    /**
     * Name: Maximum Results
     *
     * Get the maximum number of results to be returned
     *
     * Specify Null if do not want to limit the results
     */
    public Integer getMaxResults();
}
