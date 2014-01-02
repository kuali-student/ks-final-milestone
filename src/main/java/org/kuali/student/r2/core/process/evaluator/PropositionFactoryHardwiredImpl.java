package org.kuali.student.r2.core.process.evaluator;

import org.kuali.rice.krms.framework.engine.Proposition;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.core.process.krms.proposition.AlwaysFalseCheckProposition;
import org.kuali.student.r2.core.process.krms.proposition.IsAliveProposition;

/**
 * Factory for getting the required load calculation rule given a key
 */
public class PropositionFactoryHardwiredImpl implements PropositionFactory {

    public PropositionFactoryHardwiredImpl() {
    }
    // checks
    public static final String RULE_ID_IS_ALIVE = "kuali.rule.is.alive";
    public Proposition getProposition(String ruleId, ContextInfo contextInfo) throws DoesNotExistException,
            OperationFailedException {
        if (ruleId.equals(RULE_ID_IS_ALIVE)) {
            IsAliveProposition prop = new IsAliveProposition();
            return prop;
        }
        throw new DoesNotExistException("unknown/unsupported proposition " + ruleId);
    }
}
