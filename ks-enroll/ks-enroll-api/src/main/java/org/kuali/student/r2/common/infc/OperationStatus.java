package org.kuali.student.r2.common.infc;

import java.util.List;

/**
 * Represents the status information for a transactional operation.
 * 
 * @author Kuali Student Team (sambit)
 */
public interface OperationStatus {

    /**
     * Gets the operation status for the transaction.
     * 
     * @return
     */

    public String getStatus();

    /**
     * Gets the informational message as a result of this transaction.
     * 
     * @return
     */

    public List<String> getMessages();

    /**
     * Returns the warning messages for this transaction. Returns an empty
     * {@link List} if there are no warnings.
     * 
     * @return
     */
    public List<String> getWarnings();

    /**
     * Returns the error messages for this transaction. Returns an empty
     * {@link List} if there are no errors.
     * 
     * @return
     */
    public List<String> getErrors();

}
