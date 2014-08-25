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

package org.kuali.student.ap.planner.service;

import org.kuali.rice.krad.uif.service.ViewHelperService;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.ap.planner.form.AddCourseToPlanForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface PlannerViewHelperService extends ViewHelperService{

    /**
     * Creates and fills in the needed information for displaying a dialog to add a course to the plan.
     *
     * @param submittedForm - Original form submitted
     * @param dialogForm - Dialog form to build on
     * @param request - Server request object
     * @param response - Server response object
     * @return Filled in form for displaying the Add to Plan Dialog for Courses
     */
    public UifFormBase loadAddToPlanDialogForm(UifFormBase submittedForm, AddCourseToPlanForm dialogForm, HttpServletRequest request, HttpServletResponse response);
}
