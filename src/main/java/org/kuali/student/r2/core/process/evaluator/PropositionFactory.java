package org.kuali.student.r2.core.process.evaluator;

import org.kuali.rice.krms.framework.engine.Proposition;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;

/**
 * Factory for getting a properly configured load calculation rule given a key
 */
public interface PropositionFactory  {

    
    /**
     * Get the proposition associated with the proposition id
     * 
     * @param ruleId
     * @param contextInfo
     * @return a fully configured proposition ready to use
     * @throws DoesNotExistException if the proposition id does not exist
     * @throws OperationFailedException 
     */
    public Proposition getProposition (String ruleId, ContextInfo contextInfo) 
            throws DoesNotExistException, OperationFailedException; 
}
