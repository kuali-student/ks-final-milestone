/*
 * Copyright 2012 The Kuali Foundation 
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

package org.kuali.student.enrollment.courseoffering.infc;

import org.kuali.student.r2.common.infc.IdEntity;

import java.util.List;

/**
 * The Activity Offering Cluster lists the allowable combinations of 
 * activity offerings by activity type which will constrain the number
 * of RegistrationGroup's that will be generated.  
 * 
 * @author Kuali Student Team
 */

public interface ActivityOfferingCluster
    extends IdEntity {

    /**
     * A display name for this entity.   For ActivityOfferingCluster
     * this represents the published name.
     *
     * @name Name
     */
    @Override
    public String getName();

    /**
     * Private name of ActivityOfferingCluster
     * @name Private Name
     */
    public String getPrivateName();

    /**
     * Gets the format offering Id to which this template applies.
     *
     * @name Format Offering Id
     * @required
     */
    public String getFormatOfferingId();
    
    /**
     * Gets the activity offering Id combinations. Each list within
     * this list contains a list of Activity Offering Ids.
     *
     * This rule says that a RegistrationGroup comprising of a single
     * activity offering Id from each and every list within the
     * activity offering combinations should be created.
     *
     * (needs an example)
     *
     * @name Activity Offering Set
     */
    public List<? extends ActivityOfferingSet> getActivityOfferingSets();
}
