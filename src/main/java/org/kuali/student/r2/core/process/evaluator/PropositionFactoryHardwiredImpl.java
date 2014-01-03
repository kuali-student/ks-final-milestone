package org.kuali.student.r2.core.process.evaluator;

import org.kuali.rice.krms.framework.engine.Proposition;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;

/**
 * Factory for getting the required load calculation rule given a key
 */
public class PropositionFactoryHardwiredImpl implements PropositionFactory {

    public PropositionFactoryHardwiredImpl() {
    }
    // checks
    public static final String RULE_ID_IS_ALIVE = "kuali.rule.is.alive";
    public static final String RULE_ID_CREDIT_LOAD = "kuali.rule.credit.load";
    public static final String RULE_ID_CREDIT_LIMIT = "kuali.rule.credit.limit";
    public static final String RULE_ID_CREDIT_MINIMUM = "kuali.rule.credit.minimum";
    public Proposition getProposition(String ruleId, ContextInfo contextInfo) throws DoesNotExistException,
            OperationFailedException {
        if (ruleId.equals(RULE_ID_IS_ALIVE)) {
            Proposition prop = new IsAliveProposition();
            return prop;
        }
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
        throw new DoesNotExistException("unknown/unsupported proposition " + ruleId);
    }
}
