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
 * Created by vgadiyak on 9/14/12
 */
package org.kuali.student.enrollment.class2.scheduleofclasses.dto;

import org.apache.commons.lang.StringEscapeUtils;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingDisplayInfo;

import java.util.ArrayList;
import java.util.List;

/* TODO : This class needs refactoring; should inherit from CourseOfferingWrapper; see https://jira.kuali.org/browse/KSENROLL-5931
 */

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class CourseOfferingDisplayWrapper {

    private CourseOfferingDisplayInfo coDisplayInfo;
    private String information;

    public CourseOfferingDisplayWrapper(){
        coDisplayInfo = new CourseOfferingDisplayInfo();
    }

    public CourseOfferingDisplayInfo getCoDisplayInfo() {
        return coDisplayInfo;
    }

    public void setCoDisplayInfo(CourseOfferingDisplayInfo coDisplayInfo) {
        this.coDisplayInfo = coDisplayInfo;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getXmlEscapedFormatted() {
        String formatted = null;
        if(coDisplayInfo != null && coDisplayInfo.getDescr() != null){
            formatted = coDisplayInfo.getDescr().getFormatted();
        }
        if(formatted != null){
            return StringEscapeUtils.escapeXml(formatted);
        }
        return formatted;
    }

}
