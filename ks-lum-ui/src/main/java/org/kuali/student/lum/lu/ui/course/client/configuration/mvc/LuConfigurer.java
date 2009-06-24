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
package org.kuali.student.lum.lu.ui.course.client.configuration.mvc;

import org.kuali.student.common.ui.client.configurable.mvc.ConfigurableLayout;
import org.kuali.student.common.ui.client.configurable.mvc.PagedSectionLayout;
import org.kuali.student.common.ui.client.configurable.mvc.SimpleConfigurableSection;
import org.kuali.student.common.ui.client.configurable.mvc.ConfigurableLayoutSection.FieldType;
import org.kuali.student.lum.lu.ui.course.client.configuration.LUConstants;


/**
 * This is the configuration factory class for creating a proposal. 
 * 
 * @author Kuali Student Team
 *
 */
public class LuConfigurer {
    
    public enum LuSections{
        CLU_BEGIN, AUTHOR, GOVERNANCE, COURSE_LOGISTICS, COURSE_INFO, LEARNING_OBJECTIVES, 
        COURSE_RESTRICTIONS, PRE_CO_REQUISTES, ACTIVE_DATES, FINANCIALS, PGM_REQUIREMENTS, ATTACHMENTS
    }
    
    public static void configureCluProposal(ConfigurableLayout layout){
        addCluStartSection(layout);
        addAuthorSection(layout);
        addGoverenceSection(layout);
        addCourseInfoSection(layout);
    }
    
    public static void addCluStartSection(ConfigurableLayout layout){
        SimpleConfigurableSection section = new SimpleConfigurableSection(LuSections.CLU_BEGIN, "Start");
        section.addField("courseTitle", "Proposed Course Title", FieldType.TEXTBOX);
        ((PagedSectionLayout)layout).addStartSection(section);
    }

    public static void addAuthorSection(ConfigurableLayout layout){
        SimpleConfigurableSection section = new SimpleConfigurableSection(LuSections.AUTHOR, LUConstants.SECTION_AUTHORS_AND_COLLABORATORS);        

        layout.addSection(new String[] {LUConstants.SECTION_PROPOSAL_INFORMATION}, section);        
    }
    
    public static void addGoverenceSection(ConfigurableLayout layout){
        SimpleConfigurableSection section = new SimpleConfigurableSection(LuSections.GOVERNANCE, LUConstants.SECTION_GOVERNANCE);        
        
        //FIXME: Label should come from messaging, field type should come from dictionary?
        section.addField("curriculumOversight", "Curriculum Oversight", FieldType.TEXTBOX);        
        section.addField("campusLocation", "Campus Location", FieldType.TEXTBOX);
                
        layout.addSection(new String[] {LUConstants.SECTION_PROPOSAL_INFORMATION}, section);        
    }
    
    public static void addCourseInfoSection(ConfigurableLayout layout){
        SimpleConfigurableSection section = new SimpleConfigurableSection(LuSections.COURSE_INFO, LUConstants.SECTION_COURSE_INFORMATION);        
        
        //FIXME: Label should be key to messaging, field type should come from dictionary?
        section.addField("courseNumber", "Course Number", FieldType.TEXTBOX);        
        section.addField("courseTitle", "Proposed Course Title", FieldType.TEXTBOX);

        layout.addSection(new String[] {LUConstants.SECTION_COURSE_INFORMATION}, section);        
       
    }

    
}
