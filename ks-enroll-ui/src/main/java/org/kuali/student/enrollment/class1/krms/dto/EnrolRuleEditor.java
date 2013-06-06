package org.kuali.student.enrollment.class1.krms.dto;

import org.kuali.rice.krms.api.repository.proposition.PropositionDefinitionContract;
import org.kuali.rice.krms.api.repository.rule.RuleDefinitionContract;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.dto.RuleTypeInfo;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2012/12/03
 * Time: 3:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class EnrolRuleEditor extends RuleEditor {

    private static final long serialVersionUID = 1L;

    public EnrolRuleEditor(){
        super();
    }

    public EnrolRuleEditor(RuleDefinitionContract definition){
        super(definition);
    }

    public EnrolRuleEditor(String key, boolean dummy, RuleTypeInfo ruleTypeInfo) {
        super(key, dummy, ruleTypeInfo);
    }

    @Override
    protected PropositionEditor createPropositionEditor(PropositionDefinitionContract definition){
        return new EnrolPropositionEditor(definition);
    }
}
