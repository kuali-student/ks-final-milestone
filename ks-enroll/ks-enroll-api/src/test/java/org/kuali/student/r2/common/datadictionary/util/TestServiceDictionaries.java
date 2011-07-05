/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.r2.common.datadictionary.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.enrollment.acal.dto.CampusCalendarInfo;
import org.kuali.student.enrollment.acal.dto.HolidayInfo;
import org.kuali.student.enrollment.acal.dto.KeyDateInfo;
import org.kuali.student.enrollment.acal.dto.RegistrationDateGroupInfo;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo;
import org.kuali.student.enrollment.hold.dto.HoldInfo;
import org.kuali.student.enrollment.hold.dto.IssueInfo;
import org.kuali.student.enrollment.hold.dto.RestrictionInfo;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.enrollment.lui.dto.LuiCapacityInfo;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.dto.LuiLuiRelationInfo;
import org.kuali.student.r2.core.atp.dto.AtpAtpRelationInfo;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.dto.AtpMilestoneRelationInfo;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;

public class TestServiceDictionaries {

    private String calculateXmlFileName(Class<?> clazz) {
        return "ks-" + clazz.getSimpleName() + "-dictionary.xml";
    }

    public List<String> getInputFiles() {
        List<String> inputFiles = new ArrayList<String>();
        inputFiles.add("Academic Calendar (ACAL) Service");
        inputFiles.add(calculateXmlFileName(AcademicCalendarInfo.class));
        inputFiles.add(calculateXmlFileName(CampusCalendarInfo.class));
        inputFiles.add(calculateXmlFileName(TermInfo.class));
        inputFiles.add(calculateXmlFileName(RegistrationDateGroupInfo.class));
        inputFiles.add(calculateXmlFileName(HolidayInfo.class));
        inputFiles.add(calculateXmlFileName(KeyDateInfo.class));
        inputFiles.add("Lui Person Relation (LPR) Service");
        inputFiles.add(calculateXmlFileName(LuiPersonRelationInfo.class));
        inputFiles.add("Hold Service");
        inputFiles.add(calculateXmlFileName(HoldInfo.class));
        inputFiles.add(calculateXmlFileName(IssueInfo.class));
        inputFiles.add(calculateXmlFileName(RestrictionInfo.class));
        inputFiles.add("Academic Time Period (ATP) Service");
        inputFiles.add(calculateXmlFileName(AtpInfo.class));
        inputFiles.add(calculateXmlFileName(MilestoneInfo.class));
        inputFiles.add(calculateXmlFileName(AtpAtpRelationInfo.class));
        inputFiles.add(calculateXmlFileName(AtpMilestoneRelationInfo.class));
        inputFiles.add("Learning Unit Instance (LUI) Service");
        inputFiles.add(calculateXmlFileName(LuiInfo.class));
        inputFiles.add(calculateXmlFileName(LuiLuiRelationInfo.class));
        inputFiles.add(calculateXmlFileName(LuiCapacityInfo.class));
        inputFiles.add("Course Offering Service");
        inputFiles.add(calculateXmlFileName(CourseOfferingInfo.class));
        inputFiles.add(calculateXmlFileName(ActivityOfferingInfo.class));
        inputFiles.add(calculateXmlFileName(RegistrationGroupInfo.class));
        inputFiles.add(calculateXmlFileName(SeatPoolDefinitionInfo.class));
        return inputFiles;
    }

    @Test
    public void testDictionaries() {
        System.out.println("testing dictionary files");
        String projectUrl = "https://test.kuali.org/svn/student/branches/ks-1.3/ks-enroll/ks-enroll-api/src/main/resources/";
        KSDictionaryDocMojo mojo = new KSDictionaryDocMojo();
        mojo.setHtmlDirectory(new File("target/site/services/dictionarydocs"));
        mojo.setProjectUrl(projectUrl);
        mojo.setInputFiles(this.getInputFiles());
        mojo.execute();
    }
}
