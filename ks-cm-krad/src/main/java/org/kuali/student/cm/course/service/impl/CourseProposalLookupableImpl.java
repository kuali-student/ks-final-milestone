/**
 * Copyright 2014 The Kuali Foundation Licensed under the
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
 *
 * Created by delyea on 8/22/14
 */
package org.kuali.student.cm.course.service.impl;

import org.kuali.rice.krad.lookup.LookupForm;
import org.kuali.student.cm.course.util.CourseProposalUtil;
import org.kuali.student.cm.proposal.service.impl.ProposalLookupableImpl;

import java.util.List;
import java.util.Map;

/**
 * Lookupable class for Course Proposal objects
 *
 * @author Kuali Student Team
 */
public class CourseProposalLookupableImpl extends ProposalLookupableImpl {

    private static final long serialVersionUID = -6184779361057721403L;

    @Override
    public List<?> performSearch(LookupForm lookupForm, Map<String, String> fieldValues, boolean unbounded) {
        // this will eventually need to be implemented to filter out proposals for courses only and not programs
        return super.performSearch(lookupForm, fieldValues, unbounded);
    }

    @Override
    public String buildHrefForActionLink(String maintenanceMethodToCall, String pageId, String workflowDocId, String proposalType) {
        return CourseProposalUtil.buildCourseProposalUrl(maintenanceMethodToCall, pageId, workflowDocId, proposalType);
    }

}
