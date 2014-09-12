/**
 * Copyright 2005-2013 The Kuali Foundation
 * 
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/ecl1.php
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.kuali.student.cm.course.form.wrapper;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.common.util.DisplayWrapper;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;


/**
 * This is the helper class for CourseView
 * 
 * @author OpenCollab/rSmart KRAD CM Conversion Alliance!
 */
public class ResultValuesGroupInfoWrapper extends ResultValuesGroupInfo implements DisplayWrapper {
    
    private static final long serialVersionUID = 8595074563846388089L;

    private CreateCourseUIHelper uiHelper;
    private ResultValuesGroupInfo resultValuesGroupInfo;

    public ResultValuesGroupInfoWrapper() {
        this.uiHelper = new CreateCourseUIHelper();
    }

    public ResultValuesGroupInfo getResultValuesGroupInfo() {
        return resultValuesGroupInfo;
    }

    public void setResultValuesGroupInfo(ResultValuesGroupInfo resultValuesGroupInfo) {
        this.resultValuesGroupInfo = resultValuesGroupInfo;
    }

    public CreateCourseUIHelper getUiHelper() {
        return uiHelper;
    }

    public void setUiHelper(CreateCourseUIHelper uiHelper) {
        this.uiHelper = uiHelper;
    }

    /**
     * Helper class to handle result value at the ui.
     */
    public class CreateCourseUIHelper {

        /**
         * Maintain a single property to store fixed, multiple and range result value group types.
         */
        private String resultValue;

        public String getResultValue() {
                return resultValue;
            }

        public void setResultValue(String resultValue) {
                this.resultValue = resultValue;
        }

    }

    @Override
    public boolean isUserEntered() {
        if (StringUtils.isNotBlank(uiHelper.getResultValue())) {
            return true;
        }
        return false;
    }

}
