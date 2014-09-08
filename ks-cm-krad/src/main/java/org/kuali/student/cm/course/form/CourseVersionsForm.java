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
 * Created by venkat on 7/18/14
 */
package org.kuali.student.cm.course.form;

import org.kuali.rice.krad.uif.util.SessionTransient;
import org.kuali.student.cm.course.form.wrapper.VersionWrapper;
import org.kuali.student.common.uif.form.KSUifForm;

import java.util.List;

/**
 * Model used with the Course Versions view.
 */
public class CourseVersionsForm extends KSUifForm {

    private String versionIndependentId;

    private List<VersionWrapper> versions;

    private String redirectUri;

    private String courseTitle;

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    /**
     * The version independent id of the CLU to fetch the versions for.
     */
    public String getVersionIndependentId() {
        return versionIndependentId;
    }

    public void setVersionIndependentId(String versionIndependentId) {
        this.versionIndependentId = versionIndependentId;
    }

    public List<VersionWrapper> getVersions() {
        return versions;
    }

    public void setVersions(List<VersionWrapper> versions) {
        this.versions = versions;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }
}
