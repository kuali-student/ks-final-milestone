/*
 * Copyright 2011 The Kuali Foundation 
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.enrollment.lpr.infc;

public interface LprTransactionItemResult {

    /**
     * The status of the transaction.
     *
     * Returns true in case transaction item had a successful result,
     * false otherwise.
     *
     * @name Status
     * @return
     */
    public Boolean getStatus();

    /**
     * The resulting LPR for this LPR transaction item if its
     * successful.
     *
     * Returns null if unsuccessful, a valid LPR Id in case of
     * success.
     *
     * @name Resulting Lpr Id
     * @return
     */
    public String getResultingLprId();
	
    /**
     * The message for the transaction.  In case of success, there may
     * still be warning messages.
     *
     * @name Message
     */
    public String getMessage();
}

