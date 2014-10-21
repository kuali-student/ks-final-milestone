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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Used to record information within rolloverCourseOffering so it can be accessed in future
 * rolloverCourseOfferings.  Useful for rolling over colocated AOs.
 *
 * @author Kuali Student Team
 */
public class RolloverAssistImpl implements RolloverAssist {
    private Map<String, Map<String, String>> idToSRSSourceIdToSRSTargId = new HashMap<String, Map<String, String>>();
    private Map<String, Map<String, String>> idToSourceWaitlistIdToTargetWaitlistId = new HashMap<String, Map<String, String>>();

    @Override
    public String getRolloverId() {
        Date date = new Date();
        Long millis = date.getTime();
        String millisStr = millis.toString();
        idToSRSSourceIdToSRSTargId.put(millisStr, new HashMap<String, String>());
        idToSourceWaitlistIdToTargetWaitlistId.put(millisStr, new HashMap<String, String>());
        return millisStr;
    }

    @Override
    public boolean deleteRolloverId(String rolloverId) {
        int count = 0;
        if (idToSRSSourceIdToSRSTargId.containsKey(rolloverId)) {
            idToSRSSourceIdToSRSTargId.remove(rolloverId);
            count++;
        }
        if (idToSourceWaitlistIdToTargetWaitlistId.containsKey(rolloverId)) {
            idToSourceWaitlistIdToTargetWaitlistId.remove(rolloverId);
            count++;
        }
        return count == 2;
    }

    @Override
    public boolean mapSourceSRSIdToTargetSRSId(String rolloverId, String sourceSRSId, String targetSRSId) throws OperationFailedException {
        if (sourceSRSId == null || targetSRSId == null) {
            throw new OperationFailedException("source or target SRS ID is null");
        }
        if (!idToSRSSourceIdToSRSTargId.containsKey(rolloverId)) {
            return false;
        }
        idToSRSSourceIdToSRSTargId.get(rolloverId).put(sourceSRSId, targetSRSId);
        return true;
    }

    public String getTargetSRSId(String rolloverId, String sourceSRSId) throws DoesNotExistException {
        if (!idToSRSSourceIdToSRSTargId.containsKey(rolloverId)) {
            throw new DoesNotExistException("rolloverId=" + rolloverId + "does not exist");
        }
        return idToSRSSourceIdToSRSTargId.get(rolloverId).get(sourceSRSId);
    }

    @Override
    public boolean mapSourceSharedWaitlistIdToTargetSharedWaitlistId(String rolloverId, String sourceWaitlistId, String targetWaitlistId)
            throws OperationFailedException {
        if (sourceWaitlistId == null || targetWaitlistId == null) {
            throw new OperationFailedException("source or target waitlist ID is null");
        }
        if (!idToSourceWaitlistIdToTargetWaitlistId.containsKey(rolloverId)) {
            return false;
        }
        idToSourceWaitlistIdToTargetWaitlistId.get(rolloverId).put(sourceWaitlistId, targetWaitlistId);
        return true;
    }

    @Override
    public String getTargetSharedWaitlistId(String rolloverId, String sourceWaitlistId) throws DoesNotExistException {
        if (!idToSourceWaitlistIdToTargetWaitlistId.containsKey(rolloverId)) {
            throw new DoesNotExistException("rolloverId=" + rolloverId + "does not exist");
        }
        return idToSourceWaitlistIdToTargetWaitlistId.get(rolloverId).get(sourceWaitlistId);
    }
}
