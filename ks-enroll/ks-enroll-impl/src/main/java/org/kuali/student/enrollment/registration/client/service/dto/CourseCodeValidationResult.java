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
 * Created by pauldanielrichardson on 8/7/14
 */
package org.kuali.student.enrollment.registration.client.service.dto;

/**
 * This class represents course code validation errors.
 *
 * @author Kuali Student Team
 */
public class CourseCodeValidationResult extends  RegistrationValidationResult {

    private String courseCode;

    public CourseCodeValidationResult(String messageKey, String courseCode) {
        super(messageKey);
        this.courseCode = courseCode;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }
}
