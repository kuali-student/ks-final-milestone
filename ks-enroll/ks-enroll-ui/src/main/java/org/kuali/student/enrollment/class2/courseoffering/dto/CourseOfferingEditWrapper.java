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
 * Created by vgadiyak on 5/25/12
 */
package org.kuali.student.enrollment.class2.courseoffering.dto;

import org.kuali.student.enrollment.acal.constants.AcademicCalendarServiceConstants;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class CourseOfferingEditWrapper implements Serializable {

    private CourseOfferingInfo coInfo;
    private List<FormatOfferingInfo> formatOfferings;
    // TODO: gradingOptionId should eventually come from CourseOfferingInfo - Service Team is working on making it singleton (it's a List currently)
    private String gradingOptionId = "kuali.resultComponent.grade.letter";

    public CourseOfferingEditWrapper(){
        coInfo = new CourseOfferingInfo();
        formatOfferings = new ArrayList<FormatOfferingInfo>();
    }

    public CourseOfferingEditWrapper(CourseOfferingInfo info){
        super();
        coInfo = info;
        // TODO: gradingOptionId should eventually come from CourseOfferingInfo, so no need for all this logic
        List<String> gradingOptionIds = coInfo.getRegistrationGradingOptionIds();
        if (gradingOptionIds.size() > 0) {
            gradingOptionId = gradingOptionIds.get(0);
        }
        // end gradingOptionId
    }

    public CourseOfferingInfo getCoInfo() {
        return coInfo;
    }

    public void setCoInfo(CourseOfferingInfo coInfo) {
        this.coInfo = coInfo;
    }

    public List<FormatOfferingInfo> getFormatOfferings() {
        return formatOfferings;
    }

    public void setFormatOfferings(List<FormatOfferingInfo> formatOfferings) {
        if (formatOfferings == null) {
            formatOfferings = new ArrayList<FormatOfferingInfo>();
        }
        this.formatOfferings = formatOfferings;
    }

    // TODO: Will have to be removed when gradingOptionId will be coming from Course Offering Info
    public String getGradingOptionId() {
        return gradingOptionId;
    }

    public void setGradingOptionId(String gradingOptionId) {
        this.gradingOptionId = gradingOptionId;
    }
    // gradingOptionId
}
