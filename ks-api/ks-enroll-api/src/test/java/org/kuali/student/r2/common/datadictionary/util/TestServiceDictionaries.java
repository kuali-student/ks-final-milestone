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

import org.junit.Test;
import org.kuali.rice.krad.datadictionary.DataObjectEntry;
import org.kuali.student.r2.core.acal.dto.AcademicCalendarInfo;
import org.kuali.student.r2.core.acal.dto.HolidayCalendarInfo;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.dto.HolidayInfo;
import org.kuali.student.r2.core.acal.dto.AcalEventInfo;
import org.kuali.student.r2.core.acal.dto.KeyDateInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo;
import org.kuali.student.enrollment.lpr.dto.LprInfo;
import org.kuali.student.enrollment.lui.dto.LuiCapacityInfo;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.dto.LuiLuiRelationInfo;
import org.kuali.student.r2.core.atp.dto.AtpAtpRelationInfo;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;
import org.kuali.student.r2.core.hold.dto.AppliedHoldInfo;
import org.kuali.student.r2.core.hold.dto.HoldIssueInfo;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestServiceDictionaries {

    private String calculateXmlFileName(Class<?> clazz) {
        return "ks-" + clazz.getSimpleName() + "-dictionary.xml";
    }

    private List<String> getInputFiles() {
        List<String> inputFiles = new ArrayList<String>();
//       Academic Calendar (ACAL) Service
        inputFiles.add(calculateXmlFileName(AcademicCalendarInfo.class));
        inputFiles.add(calculateXmlFileName(HolidayCalendarInfo.class));
        inputFiles.add(calculateXmlFileName(TermInfo.class));
        inputFiles.add(calculateXmlFileName(HolidayInfo.class));
        inputFiles.add(calculateXmlFileName(AcalEventInfo.class));
        inputFiles.add(calculateXmlFileName(KeyDateInfo.class));
//       Lui Person Relation (LPR) Service
        inputFiles.add(calculateXmlFileName(LprInfo.class));
//      Hold Service
        inputFiles.add(calculateXmlFileName(AppliedHoldInfo.class));
        inputFiles.add(calculateXmlFileName(HoldIssueInfo.class));
//      Academic Time Period (ATP) Service
        inputFiles.add(calculateXmlFileName(AtpInfo.class));
        inputFiles.add(calculateXmlFileName(MilestoneInfo.class));
        inputFiles.add(calculateXmlFileName(AtpAtpRelationInfo.class));
//       Learning Unit Instance (LUI) Service
        inputFiles.add(calculateXmlFileName(LuiInfo.class));
        inputFiles.add(calculateXmlFileName(LuiLuiRelationInfo.class));
        inputFiles.add(calculateXmlFileName(LuiCapacityInfo.class));
//       Course Offering Service"
        inputFiles.add(calculateXmlFileName(CourseOfferingInfo.class));
        inputFiles.add(calculateXmlFileName(ActivityOfferingInfo.class));
        inputFiles.add(calculateXmlFileName(SeatPoolDefinitionInfo.class));
        return inputFiles;
    }

    @Test
    public void testDictionaries() {
        List<String> inputFiles = getInputFiles();
        // must include base dictionary files
        inputFiles.add("ks-base-dictionary.xml");

        ConfigurableApplicationContext ac = new ClassPathXmlApplicationContext(
                inputFiles.toArray(new String[inputFiles.size()]));
        Map<String, DataObjectEntry> dataObjectEntries = ac.getBeansOfType(DataObjectEntry.class);
        ac.close();

        for (DataObjectEntry entry : dataObjectEntries.values()) {
            entry.completeValidation();
        }
    }
}
