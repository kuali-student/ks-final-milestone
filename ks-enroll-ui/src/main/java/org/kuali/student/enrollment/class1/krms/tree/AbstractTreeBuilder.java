package org.kuali.student.enrollment.class1.krms.tree;

import org.apache.commons.lang.StringEscapeUtils;
import org.kuali.rice.krms.api.repository.RuleManagementService;
import org.kuali.rice.krms.api.repository.proposition.PropositionDefinitionContract;
import org.kuali.rice.krms.api.repository.rule.RuleDefinitionContract;
import org.kuali.student.enrollment.class1.krms.dto.PropositionEditor;
import org.kuali.student.enrollment.class1.krms.dto.RuleEditor;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2013/02/25
 * Time: 2:12 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractTreeBuilder implements TreeBuilder {

    private RuleManagementService ruleManagementService;

    public RuleManagementService getRuleManagementService() {
        return ruleManagementService;
    }

    public void setRuleManagementService(RuleManagementService ruleManagementService) {
        this.ruleManagementService = ruleManagementService;
    }

    protected String buildNodeLabel(RuleDefinitionContract rule, PropositionDefinitionContract prop){
        return StringEscapeUtils.escapeHtml(this.getDescription(prop));
    }

    private String getDescription(PropositionDefinitionContract proposition) {
        if ((proposition == null) || (proposition.getDescription() == null)) {
            return "";
        }

        return proposition.getDescription();
    }

    protected String getPropositionPrefix(RuleEditor rule, PropositionEditor prop){
        if (rule == null){
            return "";
        }

        //Add the proposition with alpha code in the map if it doesn't already exist.
        if (null == prop.getKey()) {
            prop.setKey((String) rule.getAlpha().next());
        }

        //Build the prefix.
        return "<b>" + prop.getKey() + ".</b> ";
    }
}
