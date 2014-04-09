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
 * Created by chongzhu on 2/6/14
 */
package org.kuali.student.cm.course.form;

import org.kuali.rice.krad.web.form.UifFormBase;

/**
 * Form for create course.
 */
public class CourseInitialForm extends UifFormBase {

    private String createCourseInitialAction;

    private boolean useReviewProcess;

    private boolean curriculumSpecialistUser;

    public CourseInitialForm() {
        super();
        // assume user is not a Curriculum Specialist (CS) user
        curriculumSpecialistUser = false;
        // default to true as only CS users are able to disable curriculum review
        useReviewProcess = true;
    }

    public String getCreateCourseInitialAction() {
        return createCourseInitialAction;
    }

    public void setCreateCourseInitialAction(String createCourseInitialAction) {
        this.createCourseInitialAction = createCourseInitialAction;
    }

    public boolean isUseReviewProcess() {
        return useReviewProcess;
    }

    public void setUseReviewProcess(boolean useReviewProcess) {
        this.useReviewProcess = useReviewProcess;
    }

    public boolean isCurriculumSpecialistUser() {
        return curriculumSpecialistUser;
    }

    public void setCurriculumSpecialistUser(boolean curriculumSpecialistUser) {
        this.curriculumSpecialistUser = curriculumSpecialistUser;
    }

}
