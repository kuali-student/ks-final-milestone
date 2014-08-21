/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.core.hold.infc;

import org.kuali.student.r2.common.infc.IdEntity;

import java.util.Date;


/**
 * Information about an Issue.
 *
 * @author tom
 * @since Sun May 1 14:22:34 EDT 2011
 */

public interface HoldIssue
        extends IdEntity {

    /**
     * The organization related to this hold issue.
     * @name Organization Id
     * @required
     */
    public String getOrganizationId();

    /**
     * The flag that stores if the HoldIssue is a Term+Date based.
     * @required
     */
    public Boolean getIsHoldIssueTermAndDateBased ();

    /**
     * The flag that stores if the HoldIssue is only Date-based.
     * @required
     */
    public Boolean getIsHoldIssueDateBasedOnly ();

    /**
     * Gets the first applied date as of which this HoldIssue can be applied to students.
     */
    public Date getFirstAppliedDate ();

    /**
     * Gets the last applied date as of which this HoldIssue can no longer be applied to students.
     */
    public Date getLastAppliedDate ();

    /**
     * Gets the id of the first application term as of which this (Term-based) HoldIssue may be applied to students.
     */
    public String getFirstApplicationTermId();

    /**
     * Gets the id of the last application term as of which this (Term-based) HoldIssue may no longer be applied to students.
     */
    public String getLastApplicationTermId();

    /**
     * Gets the flag that specifies whether or not a history the application of this Hold to a student is to be maintained.
     */
    public Boolean getMaintainHistoryOfApplicationOfHold ();

    /**
     * Gets the Hold Code (the public identifier for the Hold) for the HoldIssue.
     */
    public String getHoldCode ();
}
