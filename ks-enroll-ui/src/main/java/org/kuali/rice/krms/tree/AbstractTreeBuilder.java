package org.kuali.rice.krms.tree;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krms.api.repository.NaturalLanguageTree;
import org.kuali.rice.krms.api.repository.RuleManagementService;
import org.kuali.rice.krms.api.repository.language.NaturalLanguageUsage;
import org.kuali.rice.krms.api.repository.proposition.PropositionDefinition;
import org.kuali.rice.krms.api.repository.proposition.PropositionDefinitionContract;
import org.kuali.rice.krms.api.repository.rule.RuleDefinitionContract;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.student.enrollment.class1.krms.dto.EnrolPropositionEditor;
import org.kuali.student.enrollment.class1.krms.dto.EnrolRuleEditor;
import org.kuali.student.enrollment.class2.courseoffering.service.decorators.PermissionServiceConstants;
import org.kuali.student.krms.naturallanguage.util.KsKrmsConstants;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

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

    protected String getDescription(PropositionDefinitionContract proposition) {
        if (proposition == null) {
            return StringUtils.EMPTY;
        }

        //Return translated nl if exist.
        if (proposition instanceof PropositionEditor){
            PropositionEditor editor = (PropositionEditor) proposition;
            return editor.getNaturalLanguageForUsage(this.getNaturalLanguageUsageKey());
        }

        //Return the description
        if (proposition.getDescription() != null){
            return proposition.getDescription();
        }

        return StringUtils.EMPTY;
    }

    protected String getPropositionPrefix(RuleEditor rule, PropositionEditor prop){
        if (rule == null){
            return StringUtils.EMPTY;
        }

        //Add the proposition with alpha code in the map if it doesn't already exist.
        if (null == prop.getKey()) {
            prop.setKey((String) rule.getAlpha().next());
        }

        //Build the prefix.
        return "<b>" + prop.getKey() + ".</b> ";
    }

    public String getNaturalLanguageUsageKey(){
        return  KsKrmsConstants.KRMS_NL_PREVIEW;
    }

}
