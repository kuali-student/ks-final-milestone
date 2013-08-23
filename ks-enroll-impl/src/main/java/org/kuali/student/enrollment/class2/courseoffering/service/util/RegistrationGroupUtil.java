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
 * Created by Charles on 10/9/12
 */
package org.kuali.student.enrollment.class2.courseoffering.service.util;

import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class provides utility functions for Registration group management to the application
 *
 * @author Kuali Student Team
 */
public class RegistrationGroupUtil {

    public static void orderActivityOfferingIdsInRegistrationGroup(RegistrationGroupInfo rgInfo,
                                                                   final Map<String, String> aoIdsToAoTypes) {

        Collections.sort(rgInfo.getActivityOfferingIds(),
                new Comparator<String>() {
                    @Override
                    public int compare(String aoIdFirst, String aoIdSecond) {
                        String firstType = aoIdsToAoTypes.get(aoIdFirst);
                        String secondType = aoIdsToAoTypes.get(aoIdSecond);
                        if (aoIdFirst == null || aoIdSecond == null) {
                            throw new RuntimeException("aoIdsToAoTypes does not contain types for all ao IDs");
                        }
                        int compare = ActivityOfferingTypePrioritizer.compare(firstType, secondType);
                        return compare;  //To change body of implemented methods use File | Settings | File Templates.
                    }

                    @Override
                    public boolean equals(Object obj) {
                        return super.equals(obj);  // Put something in to satisfy the interface
                    }

                    @Override
                    public int hashCode(){
                        return super.hashCode();
                    }
                });
    }

    public static Map<String, String> createAoIdsToAoTypesMap(List<String> aoIds,
                                                              CourseOfferingService coService,
                                                              ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, DoesNotExistException,
                   PermissionDeniedException, OperationFailedException {

        Map<String, String> aoIdsToAoTypes = new HashMap<String, String>();
        List<ActivityOfferingInfo> aoInfoList = coService.getActivityOfferingsByIds(aoIds, contextInfo);
        for (ActivityOfferingInfo aoInfo: aoInfoList) {
            aoIdsToAoTypes.put(aoInfo.getId(), aoInfo.getTypeKey());
        }
        return aoIdsToAoTypes;
    }
}
