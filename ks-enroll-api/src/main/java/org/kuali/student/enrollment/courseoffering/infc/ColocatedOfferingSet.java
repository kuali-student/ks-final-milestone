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
 * Created by mahtabme (Mezba Mahtab) on 10/4/12
 */
package org.kuali.student.enrollment.courseoffering.infc;

import org.kuali.student.enrollment.lui.infc.LuiSet;

import java.util.List;

/**
 * This class represents a colocated set of activity offerings.
 *
 * @author Kuali Student Team
 */
public interface ColocatedOfferingSet extends LuiSet {

    /**
     * List of identifiers of contained ActivityOfferings. Present for enumerated
     * AO Sets.
     */
    public List<String> getOfferingIds();

    /**
     * A flag that holds whether the max enrollment is shared across the AOs or each AO
     * is responsible for an individual max enrollment that is then summed up for the
     * colocation.
     * @return true if the max enrollment is shared across the AOs.
     */
    public Boolean getIsMaxEnrollmentShared();

}
