/**
 * Copyright 2005-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.class1.krms.dto;

import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingContextBar;
import org.kuali.student.lum.lu.ui.krms.dto.LURuleManagementWrapper;

/**
 * @author Kuali Student Team
 */
public class CORuleManagementWrapper extends LURuleManagementWrapper {

    private CourseOfferingContextBar contextBar = CourseOfferingContextBar.NULL_SAFE_INSTANCE;

    public CourseOfferingContextBar getContextBar() {
        return contextBar;
    }

    public void setContextBar(CourseOfferingContextBar contextBar) {
        this.contextBar = contextBar;
    }

}
