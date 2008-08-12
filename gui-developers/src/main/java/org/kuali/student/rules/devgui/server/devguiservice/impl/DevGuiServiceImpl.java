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
        rule.setName("Test business rule name");
        rule.setDescription("Test description");
        rule.setSuccessMessage("Test success message");
        rule.setFailureMessage("Test failure message");
        rule.setBusinessRuleType("Test business rule type");
        rule.setAnchor("test anchor");
        return rule;
    }
}