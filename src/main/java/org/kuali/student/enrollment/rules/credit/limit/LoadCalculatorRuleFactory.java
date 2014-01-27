package org.kuali.student.enrollment.rules.credit.limit;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;

/**
 * Factory for getting a properly configured load calculation rule given a key
 */
public interface LoadCalculatorRuleFactory  {

    
    /**
     * Get the rule associated with the rule type 
     * 
     * @param loadLevelCalcuationTypeKey
     * @param contextInfo
     * @return a fully configured load calculator ready to use
     * @throws DoesNotExistException if the load level calculation type key does not exist
     * @throws OperationFailedException 
     */
    public LoadCalculator getLoadCalculatorForRuleType (String loadLevelCalcuationTypeKey, ContextInfo contextInfo) 
            throws DoesNotExistException, OperationFailedException; 
}
