/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 * Created by vgadiyak on 8/17/12
 */
package org.kuali.student.enrollment.class2.courseoffering.dto;

import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class RegistrationGroupWrapper implements Serializable {

    private RegistrationGroupInfo rgInfo;
    private String aoActivityCodeText;
    private String aoStateNameText;
    private String aoTypeNameText;
    private String aoInstructorText;
    private String aoMaxEnrText;

    public RegistrationGroupWrapper() {}

    public RegistrationGroupInfo getRgInfo() {
        return rgInfo;
    }

    public void setRgInfo(RegistrationGroupInfo rgInfo) {
        this.rgInfo = rgInfo;
    }

    public String getAoActivityCodeText() {
        return aoActivityCodeText;
    }

    public void setAoActivityCodeText(String aoActivityCodeText) {
        this.aoActivityCodeText = aoActivityCodeText;
    }

    public String getAoStateNameText() {
        return aoStateNameText;
    }

    public void setAoStateNameText(String aoStateNameText) {
        this.aoStateNameText = aoStateNameText;
    }

    public String getAoTypeNameText() {
        return aoTypeNameText;
    }

    public void setAoTypeNameText(String aoTypeNameText) {
        this.aoTypeNameText = aoTypeNameText;
    }

    public String getAoInstructorText() {
        return aoInstructorText;
    }

    public void setAoInstructorText(String aoInstructorText) {
        this.aoInstructorText = aoInstructorText;
    }

    public String getAoMaxEnrText() {
        return aoMaxEnrText;
    }

    public void setAoMaxEnrText(String aoMaxEnrText) {
        this.aoMaxEnrText = aoMaxEnrText;
    }
}
