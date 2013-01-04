/*
 * Copyright 2010 The Kuali Foundation 
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

import java.util.List;

import org.kuali.student.r2.common.infc.IdEntity;

/**
 * The LprTransaction represents an object to capture an overall
 * transaction request. The requesting person ID is the person who creates this
 * overall request. There are multiple transaction items,
 * {@link LprTransactionItem}, in a single overall transaction.
 * 
 * @author Kuali Student Team (sambit)
 */

public interface LprTransaction extends IdEntity {

    /**
     * The person who requested for this transaction
     * 
     * Note this does not have to be the same as the person on the transaction 
     * item. It could be, for example, an administrator creating registrations 
     * on behalf of the student.
     *
     * @name Requesting Person Id
     */
    public String getRequestingPersonId();
    
    /**
     * The ATP for which this transaction applies.
     * 
     * This constrains which lui's can be manipulated by this transaction's items. 
     * The items may only refer to this ATP or nested ATPs.
     * 
     * @name ATP Id
     */
    public String getAtpId();
    
    /**
     * Transaction item for this 
     * 
     * @name LPR Transaction Items
     */
    List<? extends LprTransactionItem> getLprTransactionItems();
}