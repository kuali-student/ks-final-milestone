/*
 * Copyright 2010 The Kuali Foundation 
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.core.search.infc;

import java.util.List;
import org.kuali.student.r2.core.search.dto.SortDirection;

public interface SearchResult {

    /**
     * The starting position of the results.
     *
     * @name Starting Position
     * @required
     */    
    public Integer getStartAt();

    /**
     * The total number of search results.
     *
     * @name Total Results
     * @required
     */
    public Integer getTotalResults();
    
    /**
     * The search result rows.
     *
     * @name Search Result Rows
     * @required
     */
    public List<? extends SearchResultRow> getRows();
    
    /**
     * The sorting column.
     *
     * @name Sort Column
     */
    public String getSortColumn();

    /**
     * The sort direction.
     *
     * @name Sort Direction
     */    
    public SortDirection getSortDirection();
}
