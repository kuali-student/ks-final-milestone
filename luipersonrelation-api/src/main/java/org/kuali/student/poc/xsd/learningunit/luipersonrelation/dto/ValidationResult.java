package org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ValidationResult implements Serializable {

    private static final long serialVersionUID = 5692209282548494108L;
    @XmlElement
    private boolean success;
    @XmlElement
    private String ruleName;
    @XmlElement
    private String ruleDesc;
    @XmlElement
    private String ruleMessage;
    @XmlElement
    private List<ValidationResult> subValidationResult;
    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public String getRuleName() {
        return ruleName;
    }
    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }
    public String getRuleDesc() {
        return ruleDesc;
    }
    public void setRuleDesc(String ruleDesc) {
        this.ruleDesc = ruleDesc;
    }
    public String getRuleMessage() {
        return ruleMessage;
    }
    public void setRuleMessage(String ruleMessage) {
        this.ruleMessage = ruleMessage;
    }
    public List<ValidationResult> getSubValidationResult() {
        return subValidationResult;
    }
    public void setSubValidationResult(List<ValidationResult> subValidationResult) {
        this.subValidationResult = subValidationResult;
    }
}
