/*
 * Copyright 2007 The Kuali Foundation
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
package org.kuali.student.lum.lu.ui.course.client.configuration.sectionmanager;

import java.util.Map;

import org.kuali.student.common.validator.Validator;
import org.kuali.student.core.dictionary.dto.FieldDescriptor;
import org.kuali.student.lum.lu.ui.course.client.configuration.DefaultCreateUpdateLayout;
import org.kuali.student.lum.lu.ui.course.client.configuration.LUConstants;
import org.kuali.student.lum.lu.ui.course.client.configuration.SimpleConfigurableSection;
import org.kuali.student.lum.lu.ui.course.client.service.CluProposal;

/**
 * This is a description of what this class does - hjohnson don't forget to fill this in. 
 * 
 * @author Kuali Student Team (kuali-student@googlegroups.com)
 *
 */
public class StudentEligibilityLayoutManager {

    private DefaultCreateUpdateLayout<CluProposal> layout;

    public StudentEligibilityLayoutManager() {
        super();
    }

    public StudentEligibilityLayoutManager(DefaultCreateUpdateLayout<CluProposal> layout) {
        super();
        this.layout = layout;
    }

    public DefaultCreateUpdateLayout<CluProposal> addSection(String type, String state) {

        addCourseRestrictions();
        addPreqsAndCreqs();

        return layout;
    }

    private void addCourseRestrictions() {
        layout.addSection(new String[] {LUConstants.SECTION_STUDENT_ELIGIBILITY, 
                LUConstants.SECTION_COURSE_RESTRICTIONS}, 
                new SimpleConfigurableSection<CluProposal>()                
                .setSectionTitle(LUConstants.SECTION_COURSE_RESTRICTIONS)
                .setInstructions("Instructions here....")
                .setParentLayout(layout)   
        );
    }

    private void addPreqsAndCreqs() {

        layout.addSection(new String[] {LUConstants.SECTION_STUDENT_ELIGIBILITY, 
                LUConstants.SECTION_PREQS_AND_CREQS}, 
                new SimpleConfigurableSection<CluProposal>()                
                .setSectionTitle(LUConstants.SECTION_PREQS_AND_CREQS)
                .setInstructions("Instructions here....")
                .setParentLayout(layout)   
        );
    }

}
