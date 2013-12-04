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
 * Created by Charles on 7/17/13
 */
package org.kuali.student.enrollment.class2.courseofferingset.service.facade;

import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;

/**
 * This serves to cache a mapping between source schedule request sets and target schedule request sets
 * in support of rolling over co-located AO IDs.
 *
 * @author Kuali Student Team
 */
public interface RolloverAssist {
    public String getRolloverId();
    public boolean deleteRolloverId(String rolloverId);
    public boolean mapSourceSRSIdToTargetSRSId(String rolloverId, String sourceSRSId, String targetSRSId)
            throws OperationFailedException;
    public String getTargetSRSId(String rolloverId, String sourceSRSId) throws DoesNotExistException;

    /**
     * Maps shared waitlists from source term to target term.  If they aren't shared, this isn't used.
     * @param rolloverId A time stamp used to identify this particular rollover
     * @param sourceWaitlistId A waitlist ID in the source term for CourseWaitListInfo
     * @param targetWaitlistId A waitlist ID in the target term for CourseWaitListInfo
     * @return true if if there is a rolloverId in the hashmap
     */
    public boolean mapSourceSharedWaitlistIdToTargetSharedWaitlistId(String rolloverId,
                                                                     String sourceWaitlistId,
                                                                     String targetWaitlistId) throws OperationFailedException;

    /**
     * Returns a target waitlist ID assuming rollover ID and sourceWaitlistId exists
     * @param rolloverId Rollover ID
     * @param sourceWaitlistId ID for shared source waitlist
     * @return target waitlist ID
     * @throws DoesNotExistException
     */
    public String getTargetSharedWaitlistId(String rolloverId, String sourceWaitlistId) throws DoesNotExistException;
}
