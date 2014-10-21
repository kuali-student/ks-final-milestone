package org.kuali.student.core.krms.tree;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.rice.krms.tree.RuleEditTreeBuilder;
import org.kuali.rice.krms.util.NaturalLanguageHelper;
import org.kuali.student.r1.common.rice.StudentIdentityConstants;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: SW Genis
 * Date: 2013/07/18
 * Time: 5:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class KSRuleEditTreeBuilder extends RuleEditTreeBuilder{

    private NaturalLanguageHelper nlHelper;

    protected String getDescription(PropositionEditor proposition) {
        if (proposition == null) {
            return StringUtils.EMPTY;
        }

        //Get the natural language for the usage key.
        Map<String, String> nlMap = proposition.getNaturalLanguage();
        if(!nlMap.containsKey(this.getNaturalLanguageUsageKey())){
            this.getNlHelper().setNaturalLanguageForUsage(proposition, this.getNaturalLanguageUsageKey(), StudentIdentityConstants.KS_NAMESPACE_CD);
        }

        //Return empty string if nl does is null.
        String description = nlMap.get(this.getNaturalLanguageUsageKey());
        if (description==null){
            return StringUtils.EMPTY;
        }

        return description;
    }

    public String getNaturalLanguageUsageKey(){
        return KSKRMSServiceConstants.KRMS_NL_RULE_EDIT;
    }

    public NaturalLanguageHelper getNlHelper() {
        return nlHelper;
    }

    public void setNlHelper(NaturalLanguageHelper nlHelper) {
        this.nlHelper = nlHelper;
    }

}
