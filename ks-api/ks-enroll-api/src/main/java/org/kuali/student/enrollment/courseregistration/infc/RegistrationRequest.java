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

package org.kuali.student.enrollment.courseregistration.infc;

import java.util.List;

import org.kuali.student.r2.common.infc.IdEntity;

/**
 * All changes to a student registration is performed by submitting a
 * RegistrationRequest. This request represents an overall transaction
 * among a set of RegistrationRequestItems (register, add, drop,
 * update, etc.)
 *
 * For every transactional operation from the application, a new
 * Request is created.
 *
 * RegistrationRequests are created and persisted separate from the
 * submission and processing. Updating a RegistrationRequest prior to
 * submission can create the concept of a "cart." Once the
 * RegistrationRequest is submitted for porcessing it cannot be
 * updated.
 * 
 * The RegistrationRequest State represents the state of this request
 * e.g. NEW, DRAFT, SUBMITTED, FAILED.
 *  
 * @author Kuali Student Team (sambit)
 */

public interface RegistrationRequest 
    extends IdEntity {

    /**
     * The person who is making this request. For a student
     * self-registering, the requestor is the Id of the student.  In
     * the case of an adminsitrator registering or updating a
     * registration on behalf of a student, the requestor is the
     * administrator.
     *
     * In either case, the student Id is included in each
     * RegistrationRequestItem.
     * 
     * @name Requestor Id
     * @required
     * @readOnly on update
     * @impl LprTransaction.requestingPersonId
     */
    public String getRequestorId();

    /**
     * The Term in which this registration takes place.
     * 
     * @name Term Id
     * @required
     * @readOnly on update
     * @impl LprTransaction.atpId
     */
    public String getTermId();

    /**
     * A list of specific registration request items.
     * 
     * @name Registration Request Items
     * @impl LprTransaction.LprTransactionItems
     */
    public List<? extends RegistrationRequestItem> getRegistrationRequestItems();
}
