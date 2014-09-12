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
 * Created by Eswaran on 8/25/14
 */

package org.kuali.student.cm.course.service;


import org.kuali.student.cm.proposal.form.wrapper.ProposalElementsWrapper;
import org.kuali.student.common.ui.client.util.ExportElement;

import java.util.List;

/**
 *
 * @author Kuali Student Team
 */
public interface ExportCourseHelper {

    public List<ExportElement> constructExportElementBasedOnView(ProposalElementsWrapper wrapper,  boolean isProposal ) ;

}
