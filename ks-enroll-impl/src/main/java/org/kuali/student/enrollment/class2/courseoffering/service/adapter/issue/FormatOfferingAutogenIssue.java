/**
 * Copyright 2013 The Kuali Foundation Licensed under the
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
 * Created by Charles on 3/5/13
 */
package org.kuali.student.enrollment.class2.courseoffering.service.adapter.issue;

import java.util.ArrayList;
import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class FormatOfferingAutogenIssue {
    private String formatOfferingId;
    private List<FormatOfferingAutogenSubIssue> subIssues;

    public FormatOfferingAutogenIssue(String formatOfferingId) {
        this.formatOfferingId = formatOfferingId;
        subIssues = new ArrayList<FormatOfferingAutogenSubIssue>();
    }

    public String getFormatOfferingId() {
        return formatOfferingId;
    }

    public List<FormatOfferingAutogenSubIssue> getSubIssues() {
        return subIssues;
    }
}
