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
 * Created by pauldanielrichardson on 6/23/14
 */
package org.kuali.student.enrollment.registration.client.service.dto;

/**
 * This class contains validation results for max credit violations
 *
 * @author Kuali Student Team
 */
public class RegistrationValidationMaxCreditResult extends RegistrationValidationResult {

    /*
    This is a String to prevent strange rounding errors that might come back from KualiDecimal
    if we set it as a float (e.g. "12.399999"). However, for best results it should be
    numeric in case we need to do math on it client-side (this is not enforced or used at
    this time...more of a best practice for future compatibility).
     */
    private String maxCredits;

    public RegistrationValidationMaxCreditResult(String messageKey, String maxCredits) {
        super(messageKey);
        this.maxCredits = maxCredits;
    }

    public String getMaxCredits() {
        return maxCredits;
    }

    public void setMaxCredits(String maxCredits) {
        this.maxCredits = maxCredits;
    }
}
