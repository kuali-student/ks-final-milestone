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
import org.kuali.student.enrollment.hold.dto.HoldInfo;
import org.kuali.student.enrollment.hold.dto.IssueInfo;
import org.kuali.student.enrollment.hold.dto.RestrictionInfo;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.r2.core.atp.dto.AtpAtpRelationInfo;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.dto.AtpMilestoneRelationInfo;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;

public class TestServiceDictionaries {

    private String getXmlFile(Class<?> clazz) {
        return "ks-" + clazz.getSimpleName() + "-dictionary.xml";
    }

    public List<String> getInputFiles() {
        List<String> inputFiles = new ArrayList<String>();
        // Academic Calendar
        inputFiles.add(getXmlFile(AcademicCalendarInfo.class));
        inputFiles.add(getXmlFile(CampusCalendarInfo.class));        
        inputFiles.add(getXmlFile(TermInfo.class));
        inputFiles.add(getXmlFile(RegistrationDateGroupInfo.class));
        inputFiles.add(getXmlFile(HolidayInfo.class));
        inputFiles.add(getXmlFile(KeyDateInfo.class));
        // LPR
        inputFiles.add(getXmlFile(LuiPersonRelationInfo.class));
        // Hold
        inputFiles.add(getXmlFile(HoldInfo.class));
        inputFiles.add(getXmlFile(IssueInfo.class)); 
        inputFiles.add(getXmlFile(RestrictionInfo.class));
        // ATP
        inputFiles.add(getXmlFile(AtpInfo.class));
        inputFiles.add(getXmlFile(MilestoneInfo.class));
        inputFiles.add(getXmlFile(AtpAtpRelationInfo.class));
        inputFiles.add(getXmlFile(AtpMilestoneRelationInfo.class));
//        // LUI
//        inputFiles.add(getXmlFile(LuiInfo.class));
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
