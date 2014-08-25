/*
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
 */

package org.kuali.student.ap.planner.dataobject;

import org.kuali.student.ap.coursesearch.dataobject.WrapperWithRequisites;
import org.kuali.student.ap.coursesearch.util.ScheduledTermsPropertyEditor;

import java.util.List;

/**
 * Data Wrapper for the course summary information displayed in popups
 * Used for beans:
 * PlannerDialogUI.xml#planner_course_summary_details_data
 */
public class CourseSummaryPopoverDetailsWrapper extends WrapperWithRequisites {

    private List<String> scheduledTerms;
    private List<String> projectedTerms;
    private String lastOffered;

    public List<String> getScheduledTerms() {
        return scheduledTerms;
    }

    public void setScheduledTerms(List<String> scheduledTerms) {
        this.scheduledTerms = scheduledTerms;
    }

    public List<String> getProjectedTerms() {
        return projectedTerms;
    }

    public void setProjectedTerms(List<String> projectedTerms) {
        this.projectedTerms = projectedTerms;
    }

    public String getLastOffered() {
        return lastOffered;
    }

    public void setLastOffered(String lastOffered) {
        this.lastOffered = lastOffered;
    }

    public String getScheduledTermsForUI() {
        ScheduledTermsPropertyEditor editor = new ScheduledTermsPropertyEditor();
        editor.setValue(this.getScheduledTerms());
        return editor.getAsText();
    }
}
