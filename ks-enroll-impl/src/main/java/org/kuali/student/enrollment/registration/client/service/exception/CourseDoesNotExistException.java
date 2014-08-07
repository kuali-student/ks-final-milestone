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
 * Created by pauldanielrichardson on 8/6/14
 */
package org.kuali.student.enrollment.registration.client.service.exception;

import org.kuali.student.r2.common.exceptions.DoesNotExistException;

/**
 * This class is a custom exception for use in the cart RESTful services
 *
 * @author Kuali Student Team
 */
public class CourseDoesNotExistException extends DoesNotExistException {
    private static final long serialVersionUID = 1L;
    private String messageKey;
    private String courseCode;

    public CourseDoesNotExistException(String messageKey, String message) {
        super(message);
        this.messageKey = messageKey;
    }

    public CourseDoesNotExistException(String messageKey, String courseCode, String message) {
        super(message);
        this.messageKey = messageKey;
        this.courseCode = courseCode;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public String getCourseCode() {
        return courseCode;
    }
}
