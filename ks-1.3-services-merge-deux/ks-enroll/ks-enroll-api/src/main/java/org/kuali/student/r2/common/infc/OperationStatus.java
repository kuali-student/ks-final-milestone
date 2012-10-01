/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License.  You may btain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */

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
     * @name Status
     */
    public String getStatus();

    /**
     * Gets the informational message as a result of this transaction.
     * 
     * @name Messages
     */

    public List<String> getMessages();

    /**
     * Returns the warning messages for this transaction. Returns an
     * empty list if there are no warnings.
     * 
     * @name Warnings
     */
    public List<String> getWarnings();

    /**
     * Returns the error messages for this transaction. Returns an
     * empty list if there are no errors.
     * 
     * @name Errors
     */
    public List<String> getErrors();
}
