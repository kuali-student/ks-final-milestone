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
 * Created by jmorris on 10/7/14
 */
package org.kuali.student.enrollment.registration.client.service.exception;

/**
 * This class is thrown when a course registration option is not valid for a given reg group.
 *
 * @author Kuali Student Team
 */
public class InvalidOptionException extends Exception {
    private String messageKey;
    private String option;

    public InvalidOptionException(String messageKey, String option) {
        this.messageKey = messageKey;
        this.option = option;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public String getOption() {
        return option;
    }
}
