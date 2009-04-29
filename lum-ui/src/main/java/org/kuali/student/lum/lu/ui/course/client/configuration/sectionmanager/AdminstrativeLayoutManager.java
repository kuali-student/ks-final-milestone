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
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.ui.course.client.configuration.DefaultCreateUpdateLayout;
import org.kuali.student.lum.lu.ui.course.client.configuration.LUConstants;
import org.kuali.student.lum.lu.ui.course.client.configuration.SimpleConfigurableSection;

/**
 * This is a description of what this class does - hjohnson don't forget to fill this in. 
 * 
 * @author Kuali Student Team (kuali-student@googlegroups.com)
 *
 */
public class AdminstrativeLayoutManager {
    
    private DefaultCreateUpdateLayout<CluInfo> layout;
    private Map<String, FieldDescriptor> fields;
    private Validator validator;
    
    public AdminstrativeLayoutManager() {
        super();
    }

    public AdminstrativeLayoutManager(DefaultCreateUpdateLayout<CluInfo> layout,
            Map<String, FieldDescriptor> fields, Validator validator) {
        super();
        this.layout = layout;
        this.fields = fields;
        this.validator = validator;
    }

    public DefaultCreateUpdateLayout<CluInfo> addSection(String type, String state) {

        addCredits();
        addActiveDates();
        addFinancials();
        addProgramRequirements();
        
        return layout;
    }
    
    private void addCredits() {

        layout.addSection(new String[] {LUConstants.SECTION_ADMINISTRATIVE, 
                LUConstants.SECTION_CREDITS}, 
                new SimpleConfigurableSection<CluInfo>()                
                .setSectionTitle(LUConstants.SECTION_CREDITS)
                .setInstructions("Instructions here....")
                .setParentLayout(layout)   
        );
    }

    private void addActiveDates() {

        layout.addSection(new String[] {LUConstants.SECTION_ADMINISTRATIVE, 
                LUConstants.SECTION_ACTIVE_DATES}, 
                new SimpleConfigurableSection<CluInfo>()                
                .setSectionTitle(LUConstants.SECTION_ACTIVE_DATES)
                .setInstructions("Instructions here....")
                .setParentLayout(layout)   
        );
    }

    private void addFinancials() {

        layout.addSection(new String[] {LUConstants.SECTION_ADMINISTRATIVE, 
                LUConstants.SECTION_FINANCIALS}, 
                new SimpleConfigurableSection<CluInfo>()                
                .setSectionTitle(LUConstants.SECTION_FINANCIALS)
                .setInstructions("Instructions here....")
                .setParentLayout(layout)   
        );
    }

    private void addProgramRequirements() {

        layout.addSection(new String[] {LUConstants.SECTION_ADMINISTRATIVE, 
                LUConstants.SECTION_PROGRAM_REQUIREMENTS}, 
                new SimpleConfigurableSection<CluInfo>()                
                .setSectionTitle(LUConstants.SECTION_PROGRAM_REQUIREMENTS)
                .setInstructions("Instructions here....")
                .setParentLayout(layout)   
        );
    }
}
