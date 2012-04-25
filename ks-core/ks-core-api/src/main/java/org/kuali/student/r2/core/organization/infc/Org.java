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

package org.kuali.student.r2.core.organization.infc;

import java.util.List;

import org.kuali.student.r2.common.infc.HasEffectiveDates;
import org.kuali.student.r2.common.infc.IdNamelessEntity;
import org.kuali.student.r2.common.infc.RichText;

/**
 * Information about a single organization. 
 *
 * Changes: LongName and LongDesc are the standard Name and Desc.
 *
 * @author tom
 */ 

public interface Org 
    extends IdNamelessEntity, HasEffectiveDates {

     /**
     * Full name of the organization.
     *
     * @name Long Name
     * @required
     */
    public String getLongName();
    
    /**
     * Shortened format or abbreviation of the organization's name.
     *
     * @name Short Name
     */
    public String getShortName();

    /**
     * The name used for sorting, for cases when alphabetical sorting
     * by shortName or longName is undesirable.
     *
     * @name Sort Name
     * @required
     */
    public String getSortName();

    /**
     * Narrative description of the organization.
     *
     * @name Short Description
     */
    public RichText getLongDescr();
    
    /**
     * A brief description of the organization.
     *
     * @name Short Description
     */
    public RichText getShortDescr();

    /**
     * A list of organization code info structures. These are
     * structures so that many different types of codes can be
     * associated with the org. This allows them to be put into
     * categories.
     *
     * @name Org Codes
     * @required
     */
    public List<? extends OrgCode> getOrgCodes();
}
