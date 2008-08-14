/**
 * 
 */
package org.kuali.student.rules.devgui.server.devguiservice.impl;

import org.kuali.student.rules.devgui.client.DevGuiService;
import org.kuali.student.rules.devgui.client.model.BusinessRule;

/**
 * @author zzraly
 */
public class DevGuiServiceImpl implements DevGuiService {

    public BusinessRule getBusinessRule(String identifier) {
        BusinessRule rule = new BusinessRule();
        System.out.println("ID:" + identifier);
        rule.setId(identifier);
        rule.setName("CHEM 100 course prerequisites");
        rule.setDescription("Prerequsite courses required in order to enroll in CHEM 100.");
        rule.setSuccessMessage("Test success message");
        rule.setFailureMessage("Test failure message");
        rule.setBusinessRuleTypeKey("kuali.coursePrerequisite");
        rule.setAnchorTypeKey("kuali.lui.course.id");
        rule.setAnchor("CHEM 100");
        return rule;
    }
}