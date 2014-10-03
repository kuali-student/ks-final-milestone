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
 * Created by jmorris on 7/7/14
 */
package org.kuali.student.enrollment.registration.client.service.dto;

import org.codehaus.jackson.annotate.JsonRawValue;
import org.kuali.student.r2.common.dto.ValidationResultInfo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is a data structure used to return the output of
 * an eligibility check in the REST service.
 *
 * @author Kuali Student Team
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EligibilityCheckResult", propOrder = {
        "isEligible", "messages", "userId"})
public class EligibilityCheckResult {
    private boolean isEligible;
    private String userId;

    @JsonRawValue
    private List<String> messages;


    public EligibilityCheckResult() {}

    public EligibilityCheckResult(List<ValidationResultInfo> reasons) {
        List<String> messages = new ArrayList<>();
        for (ValidationResultInfo reason : reasons) {
            messages.add(reason.getMessage());
        }

        setMessages(messages);
    }

    public boolean getIsEligible() {
        return isEligible;
    }

    public void setIsEligible(boolean isEligible) {
        this.isEligible = isEligible;
    }

    public List<String> getMessages() {
        if (messages == null) {
            messages = new ArrayList<>(0);
        }

        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
