/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.rules.validate;

/**
 * This is a description of what this class does - Kamal don't forget to fill this in. 
 * 
 * @author Kuali Student Team (kamal.kuali@gmail.com)
 *
 */
public class ValidationResult {

    private boolean success = false;
    private String ruleName;
    private String ruleDesc;
    private String ruleMessage;
    private ValidationResult subValidationResult;
    
    /**
     * @return the success
     */
    public boolean isSuccess() {
        return success;
    }
    /**
     * @param success the success to set
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }
    /**
     * @return the ruleName
     */
    public String getRuleName() {
        return ruleName;
    }
    /**
     * @param ruleName the ruleName to set
     */
    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }
    /**
     * @return the ruleDesc
     */
    public String getRuleDesc() {
        return ruleDesc;
    }
    /**
     * @param ruleDesc the ruleDesc to set
     */
    public void setRuleDesc(String ruleDesc) {
        this.ruleDesc = ruleDesc;
    }
    /**
     * @return the ruleMessage
     */
    public String getRuleMessage() {
        return ruleMessage;
    }
    /**
     * @param ruleMessage the ruleMessage to set
     */
    public void setRuleMessage(String ruleMessage) {
        this.ruleMessage = ruleMessage;
    }
    /**
     * @return the subValidationResult
     */
    public ValidationResult getSubValidationResult() {
        return subValidationResult;
    }
    /**
     * @param subValidationResult the subValidationResult to set
     */
    public void setSubValidationResult(ValidationResult subValidationResult) {
        this.subValidationResult = subValidationResult;
    }
}
