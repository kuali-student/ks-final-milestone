/*
 * Copyright 2012 The Kuali Foundation 
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

package org.kuali.student.r2.core.fee.infc;

import org.kuali.student.r2.common.infc.IdNamelessEntity;
import org.kuali.student.r2.common.infc.RichText;


/**
 * A Fee structure.
 *
 * The name "EnrollmentFee" is a temporary name to distinguish this
 * from Fees in CM.
 *
 * @author tom
 */
public interface EnrollmentFee 
    extends IdNamelessEntity {

    /**
     * A description of this fee.
     *
     * @name Description
     */
    public RichText getDescr();

    /**
     * The amount associated with the fee. The units of the fee amount
     * and interpretation depends on the rate type.
     *
     * @name Fee Amount
     */
    public EnrollmentFeeAmount getAmount();

    /**
     * Gets the organization responsible for this fee.
     *
     * @name Org Id
     */
    public String getOrgId();

    /**
     * Gets the reference object URI.
     *
     * @name Reference Object URI
     */
    public String getRefObjectURI();

    /**
     * Gets the reference object.
     *
     * @name Reference Object Id
     */
    public String getRefObjectId();
}
