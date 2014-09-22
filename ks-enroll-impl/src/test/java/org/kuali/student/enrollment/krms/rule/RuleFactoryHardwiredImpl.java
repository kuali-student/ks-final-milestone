package org.kuali.student.enrollment.krms.rule;

import org.kuali.rice.krms.framework.engine.BasicRule;
import org.kuali.rice.krms.framework.engine.Proposition;
import org.kuali.rice.krms.framework.engine.Rule;
import org.kuali.student.core.krms.rule.RuleFactory;
import org.kuali.student.enrollment.krms.proposition.CreditLimitProposition;
import org.kuali.student.enrollment.krms.proposition.CreditLoadProposition;
import org.kuali.student.enrollment.krms.proposition.CreditMinimumProposition;
import org.kuali.student.enrollment.krms.proposition.RequisitesProposition;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;

/**
 * Created by SW Genis on 2014/06/14.
 */
public class RuleFactoryHardwiredImpl extends
        org.kuali.student.core.krms.rule.RuleFactoryHardwiredImpl
        implements RuleFactory {

    @Override
    public Rule getRule(String ruleId, ContextInfo contextInfo) throws DoesNotExistException, OperationFailedException {
        Proposition proposition = this.getProposition(ruleId, contextInfo);
        return new BasicRule(proposition, null);
    }

    // checks
    public static final String RULE_ID_CREDIT_LOAD = "kuali.rule.credit.load";
    public static final String RULE_ID_CREDIT_LIMIT = "kuali.rule.credit.limit";
    public static final String RULE_ID_CREDIT_MINIMUM = "kuali.rule.credit.minimum";
    public static final String RULE_ID_REQUISITES = "kuali.rule.requisites";

    protected Proposition getProposition(String ruleId, ContextInfo contextInfo) throws DoesNotExistException,
            OperationFailedException {
        Proposition prop = null;
        switch (ruleId) {
            case RULE_ID_CREDIT_LOAD:
                prop = new CreditLoadProposition();
                break;
            case RULE_ID_CREDIT_LIMIT:
                prop = new CreditLimitProposition();
                break;
            case RULE_ID_CREDIT_MINIMUM:
                prop = new CreditMinimumProposition();
                break;
            case RULE_ID_REQUISITES:
                prop = new RequisitesProposition(KSKRMSServiceConstants.AGENDA_TYPE_COURSE_ENROLLMENTELIGIBILITY,
                        KSKRMSServiceConstants.RULE_TYPE_COURSE_ACADEMICREADINESS_ANTIREQ);
                break;
            default:
                super.getProposition(ruleId, contextInfo);
                break;
        }
        return prop;
    }

}
