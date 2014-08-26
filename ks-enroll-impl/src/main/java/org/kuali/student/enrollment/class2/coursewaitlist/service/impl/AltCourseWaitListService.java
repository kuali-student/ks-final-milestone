/**
 * Copyright 2014 The Kuali Foundation Licensed under the
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
 * Created by Charles on 8/19/2014
 */
package org.kuali.student.enrollment.class2.coursewaitlist.service.impl;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;

import java.util.List;
import java.util.Map;

/**
 * An alternative to the CourseWaitListService
 *
 * @author Kuali Student Team
 */
public interface AltCourseWaitListService {
    public List<WaitlistInfo> getPeopleToProcessFromWaitlist(List<String> aoIds,
                                                             Map<String, Integer> aoid2openSeatsMap,
                                                             ContextInfo contextInfo)
            throws MissingParameterException, InvalidParameterException, OperationFailedException,
            PermissionDeniedException, DoesNotExistException;

    public class WaitlistInfo {
        public WaitlistInfo(String rgId, String personId, String masterLprId, String atpId) {
            super();
            this.rgId = rgId;
            this.personId = personId;
            this.masterLprId = masterLprId;
            this.atpId = atpId;
        }

        public String rgId;
        public String personId;
        public String masterLprId;
        public String atpId;
    }
}
