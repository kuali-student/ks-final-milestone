package org.kuali.student.enrollment.krms.proposition;

import org.kuali.rice.krms.framework.engine.Proposition;
import org.kuali.student.core.krms.proposition.IsAliveProposition;
import org.kuali.student.core.krms.proposition.PropositionFactory;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;

/**
 * Factory for getting the required load calculation rule given a key
 */
public class PropositionFactoryHardwiredImpl extends
        org.kuali.student.core.krms.proposition.PropositionFactoryHardwiredImpl
        implements PropositionFactory {

    public PropositionFactoryHardwiredImpl() {
    }
    // checks
    public static final String RULE_ID_CREDIT_LOAD = "kuali.rule.credit.load";
    public static final String RULE_ID_CREDIT_LIMIT = "kuali.rule.credit.limit";
    public static final String RULE_ID_CREDIT_MINIMUM = "kuali.rule.credit.minimum";

    @Override
    public Proposition getProposition(String ruleId, ContextInfo contextInfo) throws DoesNotExistException,
            OperationFailedException {
       
        if (ruleId.equals(RULE_ID_CREDIT_LOAD)) {
            Proposition prop = new CreditLoadProposition();
            return prop;
        }
        if (ruleId.equals(RULE_ID_CREDIT_LIMIT)) {
            Proposition prop = new CreditLimitProposition();
            return prop;
        }
        if (ruleId.equals(RULE_ID_CREDIT_MINIMUM)) {
            Proposition prop = new CreditMinimumProposition();
            return prop;
        }
        return super.getProposition(ruleId, contextInfo);
    }
}
