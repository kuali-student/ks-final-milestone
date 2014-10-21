/**
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

package org.kuali.student.r2.core.hold.infc;

import java.util.Date;

import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.infc.Relationship;


/**
 * Information about a Hold.
 *
 * @author tom
 * @since Sun May 1 14:22:34 EDT 2011
 */

public interface AppliedHold
    extends Relationship {

    /**
     * The Hold Issue Id.
     * @name Hold Issue Id
     * @required
     * @readOnly
     */
    public String getHoldIssueId();


    /**
     * The Id of the Person.
     * @name Person Id
     * @required
     * @readOnly
     */
    public String getPersonId();


    /**
     * The date this hold was released, or null if not yet released.
     * @name Released Date
     * @deprecated Please use getExpirationDate() instead
     */
    @Deprecated
    public Date getReleasedDate();

    /**
     * A display name for this entity.
     *
     * @name Name
     */
    public String getName();

    /**
     * A description of the entity.
     *
     * @name Description
     */
    public RichTextInfo getDescr();

    /**
     * The date this hold is released.
     * Must be greater than or equal to the effectiveDate specified.
     *
     * @name Expiration Date
     */
    @Override
    public Date getExpirationDate();

    /**
     * The Id of the Application Effective Term
     * Used only if the Issue is term based. Must be within the range specified by
     * HoldIssue.firstApplicationTermId and HoldIssue.lastApplicationTermId.
     * If the effectiveTerm is null then this hold is considered valid from the beginning of time.
     * To evaluate if a student has a hold and this is a term based issue then both the
     * effective dates must match the specified date AND these terms must match the specified term.
     * Note: if you want the hold to be just for a single term then you should put that term in
     * both the applicationEffectiveTermId and the applicationExpirationTermId
     *
     * @name Application Effective Term Id
     */
    public String getApplicationEffectiveTermId();

    /**
     * The Id of the Application Expiration Term
     * Used only if the Issue is term based. Must be within the range specified by
     * HoldIssue.firstApplicationTermId and HoldIssue.lastApplicationTermId.
     * If the expirationTerm is null then this hold is considered valid until the end of time.
     * The expiration term must be the same as or come after the effective term
     * To evaluate if a student has a hold and this is a term based issue then both the
     * effective dates must match the specified date AND these terms must match the specified term.
     * Note: if you want the hold to be just for a single term then you should put that term in
     * both the applicationEffectiveTermId and the applicationExpirationTermId
     *
     * @name Application Expiration Term Id
     */
    public String getApplicationExpirationTermId();
}
