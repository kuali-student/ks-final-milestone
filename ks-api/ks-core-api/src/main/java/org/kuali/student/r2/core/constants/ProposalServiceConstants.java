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
package org.kuali.student.r2.core.constants;

import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.core.proposal.dto.ProposalInfo;
import org.kuali.student.r2.core.proposal.service.ProposalService;

/**
 * This class holds the constants used by the Population service.
 *
 * @author tom
 */
public class ProposalServiceConstants {

    /**
     * Reference Object URI's
     */
    public static final String SERVICE_NAME_LOCAL_PART = ProposalService.class.getSimpleName ();
    public static final String NAMESPACE = CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "proposal";
    public static final String REF_OBJECT_URI_PROPOSAL = NAMESPACE + "/" + ProposalInfo.class.getSimpleName();

    //////////////////////////////////
    // POPULATION RULE
    //////////////////////////////////

    /**
     * PROPOSAL types
     */
    public static final String PROPOSAL_TYPE_COURSE_CREATE_KEY = "kuali.proposal.type.course.create";
    public static final String PROPOSAL_TYPE_COURSE_MODIFY_KEY = "kuali.proposal.type.course.modify";
    public static final String PROPOSAL_TYPE_COURSE_RETIRE_KEY = "kuali.proposal.type.course.retire";

    /**
     * Proposal Doc relation types
     */
    public static final String PROPOSAL_DOC_RELATION_TYPE_CLU_KEY = "kuali.proposal.referenceType.clu";

}
