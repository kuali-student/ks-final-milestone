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
 * Created by vgadiyak on 6/10/14
 */
package org.kuali.student.enrollment.registration.client.service.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is more complex validation result for time conflicts
 *
 * @author Kuali Student Team
 */
public class RegistrationValidationConflictCourseResult extends RegistrationValidationResult {

    private List<ConflictCourseResult> conflictingCourses;

    public RegistrationValidationConflictCourseResult() {
        super();
    }

    public RegistrationValidationConflictCourseResult(String messageKey, List<ConflictCourseResult> conflictingCourses) {
        super(messageKey);
        if(this.conflictingCourses==null){
           this.conflictingCourses = new ArrayList<ConflictCourseResult>();
        }
        if (conflictingCourses != null && !conflictingCourses.isEmpty()) {
            this.conflictingCourses.addAll(conflictingCourses);
        }
    }

    public List<ConflictCourseResult> getConflictingCourses() {
        return conflictingCourses;
    }

    public void setConflictingCourses(List<ConflictCourseResult> conflictingCourses) {
        this.conflictingCourses = conflictingCourses;
    }
}
