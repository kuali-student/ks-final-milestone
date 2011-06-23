/**
 * Copyright 2011 The Kuali Foundation Licensed under the
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
package org.kuali.student.r2.common.util.constants;

import org.kuali.student.enrollment.exemption.dto.ExemptionInfo;
import org.kuali.student.enrollment.exemption.dto.ExemptionRequestInfo;

/**
 * This class holds the constants used by the Exemption service.
 *
 * @author nwright
 */
public class ExemptionServiceConstants {

    /**
     * Reference Object URI's
     */
    public static final String NAMESPACE = CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "hold";
    public static final String REF_OBJECT_URI_EXEMPTION = NAMESPACE + "/" + ExemptionInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_EXEMPTION_REQUEST = NAMESPACE + "/" + ExemptionRequestInfo.class.getSimpleName();

    /**
     * Exemption types
     */
    public static final String DATE_EXEMPTION_TYPE_KEY            = "kuali.exemption.type.date";
    public static final String HOLD_EXEMPTION_TYPE_KEY            = "kuali.exemption.type.hold";
    public static final String LEARNING_RESULT_EXEMPTION_TYPE_KEY = "kuali.exemption.type.learning.result";
    public static final String MILESTONE_EXEMPTION_TYPE_KEY       = "kuali.exemption.type.milestone";
    public static final String RESTRICTION_EXEMPTION_TYPE_KEY     = "kuali.exemption.type.restriction";
    public static final String STATEMENT_EXEMPTION_TYPE_KEY       = "kuali.exemption.type.statement";

    /**
     * Exemption Request types
     */
    public static final String DATE_EXEMPTION_REQUEST_TYPE_KEY           = "kuali.exemption.request.type.date";
    public static final String HOLD_EXEMPTION_REQUEST_TYPE_KEY           = "kuali.exemption.request.type.hold";
    public static final String LEARNING_RESULT_EXEMPTION_REQUESTTYPE_KEY = "kuali.exemption.request.type.learning.result";
    public static final String MILESTONE_EXEMPTION_REQUEST_TYPE_KEY      = "kuali.exemption.request.type.milestone";
    public static final String RESTRICTION_EXEMPTION_REQUEST_TYPE_KEY    = "kuali.exemption.request.type.restriction";
    public static final String STATEMENT_EXEMPTION_REQUEST_TYPE_KEY      = "kuali.exemption.request.type.statement";

    /**
     * States For Exemptions
     */
    public static final String EXEMPTION_PROCESS_KEY          = "kuali.exemption.process";
    public static final String EXEMPTION_ACTIVE_STATE_KEY     = "kuali.exemption.active";
    public static final String EXEMPTION_REVOKED_STATE_KEY    = "kuali.exemption.revoked";
    public static final String[] EXEMPTION_PROCESS_KEYS      = {
	EXEMPTION_ACTIVE_STATE_KEY, EXEMPTION_REVOKED_STATE_KEY
    };

    /**
     * States For Exemption Requests
     */
    public static final String EXEMPTION_REQUEST_PROCESS_KEY        = "kuali.exemption.request.process";
    public static final String EXEMPTION_REQUEST_PROPOSED_STATE_KEY = "kuali.exemption.request.proposed";
    public static final String EXEMPTION_REQUEST_CANCELED_STATE_KEY = "kuali.exemption.request.canceled";
    public static final String EXEMPTION_REQUEST_APPROVED_STATE_KEY = "kuali.exemption.request.approved";
    public static final String EXEMPTION_REQUEST_DENIED_STATE_KEY   = "kuali.exemption.request.denied";
    public static final String[] EXEMPTION_REQUEST_PROCESS_KEYS     = {
	EXEMPTION_REQUEST_PROPOSED_STATE_KEY, EXEMPTION_REQUEST_CANCELED_STATE_KEY, 
	EXEMPTION_REQUEST_APPROVED_STATE_KEY, EXEMPTION_REQUEST_APPROVED_STATE_KEY
    };
}
