/*
 * Copyright 2013 The Kuali Foundation 
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

package org.kuali.student.core.appliedrate.infc;

import org.kuali.student.r2.common.infc.Relationship;

import java.util.List;

/**
 * This relationship applies a CatalogRate to a Canonical Course. The
 * Relationship is defined between the canonical Format and the
 * CatalogRate but may be qualified to a specific set of Activities
 * within the canonical Format.
 *
 * @author Kuali Student Services
 */

public interface CatalogRateCanonicalRelation
    extends Relationship {

    /**
     * The Id for the catalog rate.
     * 
     * @return the CatalogRate Id
     * @name CatalogRate Id
     * @required
     * @readOnly
     */
    public String getCatalogRateId();

    /**
     * The Id for the Format.
     * 
     * @return the Format Id
     * @name Format Id
     * @required
     * @readOnly
     */
    public String getFormatId();

    /**
     * The list of Activities within the Format to which this
     * CatalogRate applies. This is an optional qualifier where if
     * this list is empty, the CatalogRate applies to all Activities
     * in the Format.
     * 
     * @return a list of Activity Ids
     * @name Activity Ids
     */
    public List<String> getActivityIds();
}
