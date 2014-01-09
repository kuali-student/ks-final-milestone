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

import java.util.List;

import org.kuali.student.r2.common.infc.IdEntity;

/**
 * A transaction item represents a request for creating a new relation
 * of a person (student) to a LUI. The transaction item also handle
 * removing, updating, swapping out an old LUI for a new LUI for a
 * person.
 * 
 * @author Kuali Student Team (sambit)
 */

public interface LprTransactionItem 
    extends IdEntity {

    /**
     * The type of the transaction item.
     * 
     * The types of LPR transaction item are things like ADD, UPDATE,
     * DROP, and SWAP.
     * 
     * @name Type Key
     * @required
     * @readOnly
     */
    @Override
    public String getTypeKey();

    /**
     * The state of this transaction item
     * 
     * The states of the LPR transaction item are things like DRAFT,
     * SUBMITTED, and FAILED.
     * 
     * @name State Key
     * @required
     *
     */
    @Override
    public String getStateKey();

    /**
     * The person id for whom this request is to generate or update
     * the LPR.
     * 
     * @name Person Id
     * @required
     */
    public String getPersonId();

    /**
     * The identifier of the transaction that contains this
     * transaction item.
     * 
     * @name Transaction Id
     * @required
     * @readOnly
     */
    public String getTransactionId();

    /**
     * The LUI id for a new relation request.
     * 
     * @name New LUI Id
     */
    public String getLuiId();

    /**
     * The existing Lpr id for an existing relation for delete or
     * updaterequests.
     * 
     * @name Existing LPR Id
     */
    public String getExistingLprId();

    /**
     * Specify the various request (or registration ) options for
     * creating this relationship.
     * 
     * @name Request Options
     */
    public List<? extends LprTransactionItemRequestOption> getRequestOptions();

    /**
     * Returns the result for this transaction item once it has been
     * processed.
     * 
     * @name LPR Transaction Item Result
     */
    public LprTransactionItemResult getLprTransactionItemResult();

    /**
     * The keys of the result values groups to be applied to the LPR
     * once created.
     * 
     * For example, setting the grading option to pass/fail or the
     * credits to 3 for a course.
     * 
     * @name Result Values Group Keys
     */
    public List<String> getResultValuesGroupKeys();
}
