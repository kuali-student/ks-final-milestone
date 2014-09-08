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
 * @author Kuali Student Team
 */
package org.kuali.student.cm.course.form.wrapper;

/**
 * Display object of course version history.
 */
public class VersionWrapper {

    private String cluId;
    private Long sequence;
    private String courseStatus;
    private String startTerm;
    private String endTerm;

    private boolean checked;

    public VersionWrapper() {}

    public VersionWrapper(String cluId, Long sequence, String courseStatus, String startTerm, String endTerm) {
        this.cluId = cluId;
        this.sequence = sequence;
        this.courseStatus = courseStatus;
        this.startTerm = startTerm;
        this.endTerm = endTerm;
    }

    public String getCluId() {
        return cluId;
    }

    public void setCluId(String cluId) {
        this.cluId = cluId;
    }

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    public String getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }

    public String getStartTerm() {
        return startTerm;
    }

    public void setStartTerm(String startTerm) {
        this.startTerm = startTerm;
    }

    public String getEndTerm() {
        return endTerm;
    }

    public void setEndTerm(String endTerm) {
        this.endTerm = endTerm;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean isChecked) {
        this.checked = isChecked;
    }
}
