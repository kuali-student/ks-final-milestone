package org.kuali.student.core.krms.rule;

import org.kuali.rice.krms.framework.engine.Proposition;
import org.kuali.rice.krms.framework.engine.Rule;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;

/**
 * Factory for getting a properly configured load calculation rule given a key
 */
public interface RuleFactory {


    /**
     * Get the rule associated with the rule id
     *
     * @param ruleId
     * @param contextInfo
     * @return a fully configured rule ready to use
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException if the proposition id does not exist
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     */
    public Rule getRule(String ruleId, ContextInfo contextInfo)
            throws DoesNotExistException, OperationFailedException; 
}
