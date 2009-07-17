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
import org.kuali.student.common.ui.client.configurable.mvc.CustomNestedSection;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.MultiplicityComposite;
import org.kuali.student.common.ui.client.configurable.mvc.MultiplicitySection;
import org.kuali.student.common.ui.client.configurable.mvc.PagedSectionLayout;
import org.kuali.student.common.ui.client.configurable.mvc.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.VerticalSectionView;
import org.kuali.student.common.ui.client.configurable.mvc.Section.FieldLabelType;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTO;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.Type;
import org.kuali.student.common.ui.client.widgets.counting.KSTextArea;
import org.kuali.student.lum.lu.ui.course.client.configuration.LUConstants;
import org.kuali.student.lum.lu.ui.course.client.widgets.Collaborators;

import com.google.gwt.user.client.ui.Widget;


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
        addCourseLogistics(layout);
        addCourseInfoSection(layout);
    }
    
    public static void addCluStartSection(ConfigurableLayout layout){
        VerticalSectionView section = new VerticalSectionView(LuSections.CLU_BEGIN, "Start", CluProposalModelDTO.class);
        section.addField(new FieldDescriptor("courseTitle", "Proposed Course Title", Type.STRING));
        ((PagedSectionLayout)layout).addStartSection(section);
    }

    public static void addAuthorSection(ConfigurableLayout layout){
        VerticalSectionView section = new VerticalSectionView(LuSections.AUTHOR, LUConstants.SECTION_AUTHORS_AND_COLLABORATORS, CluProposalModelDTO.class);        

        section.addWidget(new Collaborators());
        layout.addSection(new String[] {LUConstants.SECTION_PROPOSAL_INFORMATION}, section);        
    }
    
    public static void addGoverenceSection(ConfigurableLayout layout){
        VerticalSectionView section = new VerticalSectionView(LuSections.GOVERNANCE, LUConstants.SECTION_GOVERNANCE, CluProposalModelDTO.class);        
        
        //FIXME: Label should come from messaging, field type should come from dictionary?
        section.addField(new FieldDescriptor("curriculumOversight", "Curriculum Oversight", Type.STRING));        
        section.addField(new FieldDescriptor("campusLocation", "Campus Location", Type.STRING));
        
                
        layout.addSection(new String[] {LUConstants.SECTION_PROPOSAL_INFORMATION}, section);        
    }
    
    public static void addCourseInfoSection(ConfigurableLayout layout){
        VerticalSectionView section = new VerticalSectionView(LuSections.COURSE_INFO, LUConstants.SECTION_COURSE_INFORMATION, CluProposalModelDTO.class);        
        
        //FIXME: Label should be key to messaging, field type should come from dictionary?
        
        
        //COURSE NUMBER
        CustomNestedSection courseNumber = new CustomNestedSection();
        courseNumber.setSectionTitle("Course Number"); //Section title constants?
        courseNumber.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
        courseNumber.addField(new FieldDescriptor("division", null, Type.STRING));//TODO OrgSearch goes here?
        courseNumber.addField(new FieldDescriptor("suffixCode", null, Type.STRING));
        courseNumber.nextRow();
        
            //CROSS LISTED
            VerticalSection crossListed = new VerticalSection();
            crossListed.setSectionTitle("Cross Listed (offered under alternate course numbers)");
            crossListed.setInstructions("Enter Department and/or Subject Code/Course Number.");
            crossListed.addField(new FieldDescriptor("crossListClus", null, Type.LIST, new CrossListedList()));//TODO Key is probably wrong
            
            //OFFERED JOINTLY
            VerticalSection offeredJointly = new VerticalSection();
            offeredJointly.setSectionTitle("Offered Jointly (co-located with another course)");
            offeredJointly.setInstructions("Enter an existing course or proposal.");
            offeredJointly.addField(new FieldDescriptor("jointClus", null, Type.LIST, new OfferedJointlyList()));//TODO Key is probably wrong
            
            //Version Codes
            VerticalSection versionCodes = new VerticalSection();
            versionCodes.setSectionTitle("Version Codes");
            versionCodes.addField(new FieldDescriptor("luLuRelationType.alias", null, Type.LIST, new VersionCodeList()));//TODO Key is probably wrong
            
        courseNumber.addSection(crossListed);
        courseNumber.nextRow();
        courseNumber.addSection(offeredJointly);
        courseNumber.nextRow();
        courseNumber.addSection(versionCodes);
        section.addSection(courseNumber);
        
        VerticalSection proposedCourseTitle = new VerticalSection();
        proposedCourseTitle.setSectionTitle("Proposed Course Title");
        proposedCourseTitle.addField(new FieldDescriptor("longName", null, Type.STRING));
        section.addSection(proposedCourseTitle);
        
        VerticalSection transcriptTitle = new VerticalSection();
        transcriptTitle.setSectionTitle("Transcript Title");
        transcriptTitle.addField(new FieldDescriptor("shortName", null, Type.STRING));
        section.addSection(transcriptTitle);
        
        VerticalSection description = new VerticalSection();
        description.setSectionTitle("Description");
        description.addField(new FieldDescriptor("desc", null, Type.STRING, new KSTextArea()));
        section.addSection(description);
        
        VerticalSection rationale = new VerticalSection();
        rationale.setSectionTitle("Rationale");
        rationale.addField(new FieldDescriptor("marketingDesc", null, Type.STRING, new KSTextArea()));
        section.addSection(rationale);

        layout.addSection(new String[] {LUConstants.SECTION_COURSE_INFORMATION}, section);        
       
    }
    
    public static class CrossListedList extends MultiplicityComposite{

        @Override
        public Widget createItem() {
            MultiplicitySection multi = new MultiplicitySection("CluInfo");//TODO Probably totally wrong
            
            CustomNestedSection ns = new CustomNestedSection();
            ns.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
            ns.addField(new FieldDescriptor("department", "Department", Type.STRING));//TODO OrgSearch goes here, wrong key
            ns.nextRow();
            ns.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
            ns.addField(new FieldDescriptor("division", "Subject Code", Type.STRING));//TODO OrgSearch goes here?
            ns.addField(new FieldDescriptor("suffixCode", "Course Number", Type.STRING));
            multi.addSection(ns);
            
            return multi;
        }
        
    }
    
    public static class OfferedJointlyList extends MultiplicityComposite{

        @Override
        public Widget createItem() {
            MultiplicitySection multi = new MultiplicitySection("CluInfo");//TODO Probably totally wrong
            
            CustomNestedSection ns = new CustomNestedSection();
            ns.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
            ns.addField(new FieldDescriptor("courseTitle", "Course Number or Title", Type.STRING));//TODO wrong key
            multi.addSection(ns);
            
            return multi;
        }
        
    }
    
    public static class VersionCodeList extends MultiplicityComposite{

        @Override
        public Widget createItem() {
            MultiplicitySection multi = new MultiplicitySection("CluInfo");//TODO Probably totally wrong
            
            CustomNestedSection ns = new CustomNestedSection();
            ns.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
            ns.addField(new FieldDescriptor("versionCode", "Code", Type.STRING));//TODO wrong key
            ns.addField(new FieldDescriptor("title", "Title", Type.STRING));//TODO wrong key
            multi.addSection(ns);
            
            return multi;
        }
        
    }
    
    public static void addCourseLogistics(ConfigurableLayout layout){
        VerticalSectionView section = new VerticalSectionView(LuSections.COURSE_LOGISTICS, LUConstants.SECTION_COURSE_LOGISTICS, CluProposalModelDTO.class); 
        
        //CREDITS
        VerticalSection credits = new VerticalSection();
        credits.setSectionTitle("Credits");
        credits.addField(new FieldDescriptor("creditType", "Credit Type", Type.STRING));//TODO CREDIT TYPE ENUMERATION
        credits.addField(new FieldDescriptor("creditInfo", "Credit Value", Type.STRING));
        credits.addField(new FieldDescriptor("maxCredits", "Maximum Credits", Type.STRING));
        
        //LEARNING RESULTS
        VerticalSection learningResults = new VerticalSection();
        learningResults.setSectionTitle("Learning Results");
        learningResults.addField(new FieldDescriptor("evalType", "Evaluation Type", Type.STRING)); //TODO EVAL TYPE ENUMERATION ????
        
        VerticalSection scheduling = new VerticalSection();
        scheduling.setSectionTitle("Scheduling");
        scheduling.addField(new FieldDescriptor("offeredAtpTypes", "Term", Type.STRING)); //TODO TERM ENUMERATION
        scheduling.addField(new FieldDescriptor("stdDuration", "Duration", Type.STRING)); //TODO DURATION ENUMERATION
        
        section.addSection(credits);
        section.addSection(learningResults);
        section.addSection(scheduling);
        section.addField(new FieldDescriptor("courseFormats", "Course Formats", Type.LIST, new CourseFormatList()));
        layout.addSection(new String[] {LUConstants.SECTION_PROPOSAL_INFORMATION}, section);
    }
    
    public static class CourseFormatList extends MultiplicityComposite{
        public static int activityNumber = 1;
        public Widget createItem() {
            return new CourseActivityList();
        }
    }
    
    // This will probably a custom clu activity widget that uses a CluInfo model dto.
    public static class CourseActivityList extends MultiplicityComposite{
        public static int activityNumber = 1;
        public Widget createItem() {
            MultiplicitySection item = new MultiplicitySection("cluInfo");
            CustomNestedSection activity = new CustomNestedSection();
            activity.setSectionTitle("Activity " + activityNumber);
            activity.addField(new FieldDescriptor("clu.type", "Acitivity Type", Type.STRING));
            activity.nextRow();
            activity.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
            activity.addField(new FieldDescriptor("creditInfo", "Credit Value", Type.STRING));
            activity.nextRow();
            activity.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
            activity.addField(new FieldDescriptor("evalType", "Learning Result", Type.STRING));
            activity.nextRow();
            activity.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
            activity.addField(new FieldDescriptor("term", "Term", Type.STRING));
            activity.addField(new FieldDescriptor("duration", "Activity Duration", Type.STRING)); //TODO dropdown need here?
            activity.nextRow();
            activity.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
            activity.addField(new FieldDescriptor("clu.hours", "Contact Hours", Type.STRING));
            //TODO PER WHATEVER
            activity.addField(new FieldDescriptor("clu.method", "Delivery Method", Type.STRING));
            activity.addField(new FieldDescriptor("clu.size", "Class Size", Type.STRING));
            
            activityNumber++;
            item.addSection(activity);
            
            return item;
        }

    }
            
}
