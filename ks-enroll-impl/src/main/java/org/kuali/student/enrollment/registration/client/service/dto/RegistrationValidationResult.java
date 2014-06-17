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
 * Created by pauldanielrichardson on 6/9/14
 */
package org.kuali.student.enrollment.registration.client.service.dto;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * This class contains details of registration validation results for marshalling to and from JSON
 *
 * @author Kuali Student Team
 */
public class RegistrationValidationResult {
    public static final Logger LOGGER = LoggerFactory.getLogger(RegistrationValidationResult.class);

    private String messageKey;

    public RegistrationValidationResult(String messageKey) {
        this.messageKey=messageKey;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }

    public String marshallResult() {
        ObjectMapper mapper=new ObjectMapper();
        String json= null;
        try {
            json = mapper.writeValueAsString(this);
        } catch (IOException ex) {
            LOGGER.error("Unable to marshall result object", ex);
        }
        return json;
    }

}
