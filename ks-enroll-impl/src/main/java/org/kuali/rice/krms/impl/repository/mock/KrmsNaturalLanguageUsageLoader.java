/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.rice.krms.impl.repository.mock;

import org.kuali.rice.krms.api.repository.RuleManagementService;
import org.kuali.rice.krms.api.repository.language.NaturalLanguageUsage;

/**
 *
 * @author nwright
 */
public class KrmsNaturalLanguageUsageLoader {

    private RuleManagementService ruleManagementService = null;

    public RuleManagementService getRuleManagementService() {
        return ruleManagementService;
    }

    public void setRuleManagementService(RuleManagementService ruleManagementService) {
        this.ruleManagementService = ruleManagementService;
    }
    
    
    public void loadNlUsage(String id, String name, String nameSpace, String description) {
        NaturalLanguageUsage.Builder bldr = NaturalLanguageUsage.Builder.create(name, nameSpace);
        bldr.setId(id);
        bldr.setActive(true);
        bldr.setDescription(description);
        this.getRuleManagementService().createNaturalLanguageUsage(bldr.build());
    }

    public void load() {
        loadNlUsage("10000", "kuali.krms.edit", "KS-SYS", "Kuali Rule Edit");
        loadNlUsage("10001", "kuali.krms.composition", "KS-SYS", "Kuali Rule Composition");
        loadNlUsage("10002", "kuali.krms.example", "KS-SYS", "Kuali Rule Example");
        loadNlUsage("10003", "kuali.krms.preview", "KS-SYS", "Kuali Rule Preview");
        loadNlUsage("10004", "kuali.krms.type.description", "KS-SYS", "Kuali Rule Type Description");
        loadNlUsage("10005", "kuali.krms.catalog", "KS-SYS", "Kuali Rule Catalog");
        loadNlUsage("10006", "kuali.krms.type.instruction", "KS-SYS", "Kuali Rule Type Instructions");
    }


}
