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

package org.kuali.student.ap.planner.util;

import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.ap.coursesearch.CreditsFormatter;
import org.kuali.student.ap.coursesearch.form.CourseSectionDetailsDialogForm;

/**
 * OptionsFinder implementation that builds options based on a CourseOffering's possible credit values
 */
public class CourseOfferingCreditListBuilder extends CourseCreditListBuilder {

    @Override
    /**
     * Get the range based off of the CourseOffering passed in through the model (form)
     * @param model - Model object (CourseSectionDetailsDialogForm)
     * @return The Range of credits from the CourseOffering
     */
    protected CreditsFormatter.Range getRange(ViewModel model) {
        CourseSectionDetailsDialogForm form = (CourseSectionDetailsDialogForm)model;

        return CreditsFormatter.getRange(form.getCourseOffering());
    }
}
