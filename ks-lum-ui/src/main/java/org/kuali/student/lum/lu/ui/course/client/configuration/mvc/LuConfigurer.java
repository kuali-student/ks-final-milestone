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

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.configurable.mvc.ConfigurableLayout;
import org.kuali.student.common.ui.client.configurable.mvc.CustomNestedSection;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.MultiplicityComposite;
import org.kuali.student.common.ui.client.configurable.mvc.MultiplicitySection;
import org.kuali.student.common.ui.client.configurable.mvc.PagedSectionLayout;
import org.kuali.student.common.ui.client.configurable.mvc.RequiredEnum;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.ToolView;
import org.kuali.student.common.ui.client.configurable.mvc.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.VerticalSectionView;
import org.kuali.student.common.ui.client.configurable.mvc.Section.FieldLabelType;
import org.kuali.student.common.ui.client.event.ValidateResultEvent;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.Model;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTO;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.Type;
import org.kuali.student.common.ui.client.validator.ModelDTOConstraintSetupFactory;
import org.kuali.student.common.ui.client.widgets.KSDatePicker;
import org.kuali.student.common.ui.client.widgets.KSRichEditor;
import org.kuali.student.common.ui.client.widgets.KSTextArea;
import org.kuali.student.common.ui.client.widgets.commenttool.CommentPanel;
import org.kuali.student.common.ui.client.widgets.documenttool.DocumentTool;
import org.kuali.student.common.ui.client.widgets.list.KSCheckBoxList;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.common.ui.client.widgets.list.impl.SimpleListItems;
import org.kuali.student.common.validator.Validator;
import org.kuali.student.core.dictionary.dto.ObjectStructure;
import org.kuali.student.core.validation.dto.ValidationResultContainer;
import org.kuali.student.lum.lu.ui.course.client.configuration.CustomSectionView;
import org.kuali.student.lum.lu.ui.course.client.configuration.LUConstants;
import org.kuali.student.lum.lu.ui.course.client.widgets.Collaborators;
import org.kuali.student.lum.lu.ui.course.client.widgets.OrgPicker;

import com.google.gwt.user.client.Window;
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
        COURSE_REQUISITES, ACTIVE_DATES, FINANCIALS, PGM_REQUIREMENTS, ATTACHMENTS, COMMENTS, DOCUMENTS,
        DEMO_SECTION
    }
               
    public static void configureCluProposal(ConfigurableLayout layout){       
        addCluStartSection(layout);
        addDemoSection(layout);
        addGovernanceSection(layout);
        addCourseLogistics(layout);
        addCourseInfoSection(layout);
        addCourseRequisitesSection(layout);
        addActiveDatesSection(layout);
        addFinancialsSection(layout);
        addProgramRequirements(layout);
        
        layout.addTool(new CollaboratorTool());
        layout.addTool(new CommentPanel(LuSections.COMMENTS, LUConstants.TOOL_COMMENTS));
        layout.addTool(new DocumentTool(LuSections.DOCUMENTS, LUConstants.TOOL_DOCUMENTS));
    }
    
    public static void addCluStartSection(ConfigurableLayout layout){
        VerticalSectionView section = new VerticalSectionView(LuSections.CLU_BEGIN, "Start", ProposalInfoModelDTO.class);
        section.setSectionTitle(SectionTitle.generateH1Title("Proposal Information"));
        section.setInstructions("Enter the following to save a draft of the clu proposal.");

        section.addField(new FieldDescriptor("name", "Proposed Course Title", Type.STRING));
        //This will need to be a person picker
        section.addField(new FieldDescriptor("proposerPerson", "Originiating Faculty Member",Type.STRING)) ;
        ((PagedSectionLayout)layout).addStartSection(section);
    }
    
    private static void addDemoSection(ConfigurableLayout layout) {
        VerticalSectionView section = new VerticalSectionView(LuSections.DEMO_SECTION, "Demo Section", CluProposalModelDTO.class);
                 
        
        VerticalSection proposedCourseTitle = new VerticalSection();
        proposedCourseTitle.addField(new FieldDescriptor("/officialIdentifier/longName", "Proposed Course Title", Type.STRING));
        proposedCourseTitle.setRequiredState(RequiredEnum.REQUIRED);
        section.addSection(proposedCourseTitle);
        
        //section.addField(new FieldDescriptor("/publishingInfo/primaryInstructor/personId", "PrimaryInstructor Id", Type.STRING));
        
        final FieldDescriptor subjectFD = new FieldDescriptor("studySubjectArea", "Study Subject Area", Type.STRING, RequiredEnum.REQUIRED);

        subjectFD.setValidationCallBack(new Callback<Boolean>() {
            @Override
            public void exec(Boolean result) {
                // Widget w = subjectFD.getFieldWidget();
                // TextBox textBox = (TextBox) w;

                ModelDTOConstraintSetupFactory bc = new ModelDTOConstraintSetupFactory();
                final Validator val = new Validator(bc, true);
                final ValidateResultEvent e = new ValidateResultEvent();

                Controller.findController(subjectFD.getFieldWidget()).requestModel(CluProposalModelDTO.class, new ModelRequestCallback<ModelDTO>() {
                    public void onModelReady(Model<ModelDTO> m) {
                        String key = m.get().getClassName();
                        ObjectStructure objStructure = Application.getApplicationContext().getDictionaryData(key);
                        List<ValidationResultContainer> results = val.validateTypeStateObject((ModelDTO) m.get(), objStructure);
                        e.setValidationResult(results);// filled by calling the real validate code
                        Controller.findController(subjectFD.getFieldWidget()).fireApplicationEvent(e);
                    }
                    @Override
                    public void onRequestFail(Throwable cause) {
                        Window.alert("Failed to get model");
                    }
                });
            }
        });

        section.addField(subjectFD);

        section.addField(new FieldDescriptor("referenceURL", "Reference URL", Type.STRING, RequiredEnum.OPTIONAL));
        section.addField(new FieldDescriptor("nextReviewPeriod", "Next Review Period", Type.STRING));
        
        
        VerticalSection description = new VerticalSection();
        description.setSectionTitle(SectionTitle.generateH2Title("Description"));
        description.addField(new FieldDescriptor("desc", null, Type.MODELDTO, new KSRichEditor()));
        section.addSection(description);
        
        VerticalSection rationale = new VerticalSection();
        rationale.setSectionTitle(SectionTitle.generateH2Title("Rationale"));
        rationale.addField(new FieldDescriptor("marketingDesc", null, Type.MODELDTO, new KSRichEditor()));
        section.addSection(rationale);
        
        VerticalSection startDate = new VerticalSection();
        startDate.setSectionTitle(SectionTitle.generateH2Title("Start Date"));
        startDate.addField(new FieldDescriptor("effectiveDate", "When will this course be active?", Type.DATE, new KSDatePicker()));
        
        VerticalSection endDate = new VerticalSection();
        endDate.setSectionTitle(SectionTitle.generateH2Title("End Date"));
        endDate.addField(new FieldDescriptor("expirationDate", "When will this course become inactive?", Type.DATE, new KSDatePicker()));
        
        section.addSection(startDate);
        section.addSection(endDate);
        
        layout.addSection(new String[] {LUConstants.SECTION_PROPOSAL_INFORMATION}, section);
    }
    
    /**
     * Adds a section for adding or modifying rules associated with a given course (program).
     * 
     * @param layout - a content pane to which this section is added to
     * @return
     */
    private static void addCourseRequisitesSection(ConfigurableLayout layout) {
        CustomSectionView section = new CustomSectionView(LuSections.COURSE_REQUISITES, LUConstants.SECTION_COURSE_REQUISITES, CluProposalModelDTO.class);
        section.setSectionTitle(SectionTitle.generateH1Title(LUConstants.SECTION_COURSE_REQUISITES));

        VerticalSection startDate = new VerticalSection();
        startDate.setSectionTitle(SectionTitle.generateH2Title("Test"));
                
        //CREDITS
		/* TO Do - once we have a section that allows for flow between rule screens
        VerticalSection enrollmentSection = new VerticalSection();
        enrollmentSection.setSectionTitle(SectionTitle.generateH2Title("Enrollment Restrictions"));
        enrollmentSection.addField(new FieldDescriptor("rationalle", "Rationalle", Type.STRING));
        enrollmentSection.addField(new FieldDescriptor("rules", "Rules", Type.STRING));
        enrollmentSection.addWidget(new KSButton());
        section.addSection(enrollmentSection);   */
        
        layout.addSection(new String[] {LUConstants.SECTION_COURSE_REQUISITES}, section); 
    }    
    
    private static void addActiveDatesSection(ConfigurableLayout layout) {
        VerticalSectionView section = new VerticalSectionView(LuSections.ACTIVE_DATES, LUConstants.SECTION_ACTIVE_DATES, CluProposalModelDTO.class);
        section.setSectionTitle(SectionTitle.generateH1Title(LUConstants.SECTION_ACTIVE_DATES));

        VerticalSection startDate = new VerticalSection();
        startDate.setSectionTitle(SectionTitle.generateH2Title("Start Date"));
        startDate.addField(new FieldDescriptor("effectiveDate", "When will this course be active?", Type.DATE, new KSDatePicker()));
        
        VerticalSection endDate = new VerticalSection();
        endDate.setSectionTitle(SectionTitle.generateH2Title("End Date"));
        endDate.addField(new FieldDescriptor("expirationDate", "When will this course become inactive?", Type.DATE, new KSDatePicker()));
        
        section.addSection(startDate);
        section.addSection(endDate);
        
        layout.addSection(new String[] {LUConstants.SECTION_ADMINISTRATIVE}, section); 
    }
    
    private static void addFinancialsSection(ConfigurableLayout layout) {
        VerticalSectionView section = new VerticalSectionView(LuSections.FINANCIALS, LUConstants.SECTION_FINANCIALS, CluProposalModelDTO.class);
        section.setSectionTitle(SectionTitle.generateH1Title(LUConstants.SECTION_FINANCIALS));

        //TODO ALL KEYS in this section are place holders until we know actual keys
        VerticalSection feeType = new VerticalSection();
        feeType.setSectionTitle(SectionTitle.generateH2Title("Fee Type"));
        feeType.addField(new FieldDescriptor("feeType", null, Type.STRING));
        
        VerticalSection feeAmount = new VerticalSection();
        feeAmount.setSectionTitle(SectionTitle.generateH2Title("Fee Amount"));
        feeAmount.addField(new FieldDescriptor("feeAmount", "$", Type.STRING));
        feeAmount.addField(new FieldDescriptor("taxable", "Taxable", Type.STRING));//TODO checkboxes go here instead
        feeAmount.addField(new FieldDescriptor("feeDesc", "Description", Type.STRING, new KSTextArea()));
        feeAmount.addField(new FieldDescriptor("internalNotation", "Internal Fee Notation", Type.STRING, new KSTextArea()));
        
        section.addSection(feeType);
        section.addSection(feeAmount);
        layout.addSection(new String[] {LUConstants.SECTION_ADMINISTRATIVE}, section);
    }
    
    private static void addProgramRequirements(ConfigurableLayout layout) {
        VerticalSectionView section = new VerticalSectionView(LuSections.PGM_REQUIREMENTS, LUConstants.SECTION_PROGRAM_REQUIREMENTS, CluProposalModelDTO.class);
        section.setSectionTitle(SectionTitle.generateH1Title(LUConstants.SECTION_PROGRAM_REQUIREMENTS));
        
        layout.addSection(new String[] {LUConstants.SECTION_ADMINISTRATIVE}, section);
        
    }
       
    public static void addGovernanceSection(ConfigurableLayout layout){
        VerticalSectionView section = new VerticalSectionView(LuSections.GOVERNANCE, LUConstants.SECTION_GOVERNANCE, CluProposalModelDTO.class);        
        section.setSectionTitle(SectionTitle.generateH1Title(LUConstants.SECTION_GOVERNANCE));
        
        section.addField(new FieldDescriptor("academicSubjectOrgs", "Curriculum Oversight", Type.STRING, new OrgListPicker()));        
        section.addField(new FieldDescriptor("campusLocationList", "Campus Location", Type.STRING, new CampusLocationList()));       
        section.addField(new FieldDescriptor("adminOrg", "Administering Organization", Type.STRING, new OrgPicker()));
        
        layout.addSection(new String[] {LUConstants.SECTION_PROPOSAL_INFORMATION}, section);        
    }
    
    public static void addCourseInfoSection(ConfigurableLayout layout){
        VerticalSectionView section = new VerticalSectionView(LuSections.COURSE_INFO, LUConstants.SECTION_COURSE_INFORMATION, CluProposalModelDTO.class);        
        section.setSectionTitle(SectionTitle.generateH1Title(LUConstants.SECTION_COURSE_INFORMATION));
        
        //FIXME: Label should be key to messaging, field type should come from dictionary?
        
        
        //COURSE NUMBER
        CustomNestedSection courseNumber = new CustomNestedSection();
        courseNumber.setSectionTitle(SectionTitle.generateH2Title("Course Number")); //Section title constants)
        courseNumber.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
        courseNumber.addField(new FieldDescriptor("division", null, Type.STRING));//TODO OrgSearch goes here?
        courseNumber.addField(new FieldDescriptor("suffixCode", null, Type.STRING));
        courseNumber.nextRow();
        
            //CROSS LISTED
            VerticalSection crossListed = new VerticalSection();
            crossListed.setSectionTitle(SectionTitle.generateH2Title("Cross Listed (offered under alternate course numbers)"));
            crossListed.setInstructions("Enter Department and/or Subject Code/Course Number.");
            crossListed.addField(new FieldDescriptor("crossListClus", null, Type.LIST, new CrossListedList()));//TODO Key is probably wrong
            
            //OFFERED JOINTLY
            VerticalSection offeredJointly = new VerticalSection();
            offeredJointly.setSectionTitle(SectionTitle.generateH2Title("Offered Jointly (co-located with another course)"));
            offeredJointly.setInstructions("Enter an existing course or proposal.");
            offeredJointly.addField(new FieldDescriptor("jointClus", null, Type.LIST, new OfferedJointlyList()));//TODO Key is probably wrong
            
            //Version Codes
            VerticalSection versionCodes = new VerticalSection();
            versionCodes.setSectionTitle(SectionTitle.generateH2Title("Version Codes"));
            versionCodes.addField(new FieldDescriptor("luLuRelationType.alias", null, Type.LIST, new VersionCodeList()));//TODO Key is probably wrong
            
        courseNumber.addSection(crossListed);
        courseNumber.nextRow();
        courseNumber.addSection(offeredJointly);
        courseNumber.nextRow();
        courseNumber.addSection(versionCodes);
        section.addSection(courseNumber);
        
        VerticalSection proposedCourseTitle = new VerticalSection();
        proposedCourseTitle.setSectionTitle(SectionTitle.generateH2Title("Proposed Course Title"));
        proposedCourseTitle.addField(new FieldDescriptor("longName", null, Type.STRING));
        section.addSection(proposedCourseTitle);
        
        VerticalSection transcriptTitle = new VerticalSection();
        transcriptTitle.setSectionTitle(SectionTitle.generateH2Title("Transcript Title"));
        transcriptTitle.addField(new FieldDescriptor("shortName", null, Type.STRING));
        section.addSection(transcriptTitle);
        
        VerticalSection description = new VerticalSection();
        description.setSectionTitle(SectionTitle.generateH2Title("Description"));
        description.addField(new FieldDescriptor("desc", null, Type.STRING, new KSTextArea()));
        section.addSection(description);
        
        VerticalSection rationale = new VerticalSection();
        rationale.setSectionTitle(SectionTitle.generateH2Title("Rationale"));
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
        section.setSectionTitle(SectionTitle.generateH1Title(LUConstants.SECTION_COURSE_LOGISTICS));
        
        //CREDITS
        VerticalSection credits = new VerticalSection();
        credits.setSectionTitle(SectionTitle.generateH2Title("Credits"));
        credits.addField(new FieldDescriptor("creditType", "Credit Type", Type.STRING));//TODO CREDIT TYPE ENUMERATION
        credits.addField(new FieldDescriptor("creditInfo", "Credit Value", Type.STRING));
        credits.addField(new FieldDescriptor("maxCredits", "Maximum Credits", Type.STRING));
        
        //LEARNING RESULTS
        VerticalSection learningResults = new VerticalSection();
        learningResults.setSectionTitle(SectionTitle.generateH2Title("Learning Results"));
        learningResults.addField(new FieldDescriptor("evalType", "Evaluation Type", Type.STRING)); //TODO EVAL TYPE ENUMERATION ????
        
        VerticalSection scheduling = new VerticalSection();
        scheduling.setSectionTitle(SectionTitle.generateH2Title("Scheduling"));
        scheduling.addField(new FieldDescriptor("offeredAtpTypes", "Term", Type.STRING)); //TODO TERM ENUMERATION
        scheduling.addField(new FieldDescriptor("stdDuration", "Duration", Type.STRING)); //TODO DURATION ENUMERATION
        
        section.addSection(credits);
        section.addSection(learningResults);
        section.addSection(scheduling);
        section.addField(new FieldDescriptor("courseFormats", "Course Formats", Type.LIST, new CourseFormatList()));
        layout.addSection(new String[] {LUConstants.SECTION_PROPOSAL_INFORMATION}, section);
    }
    
    public static class CourseFormatList extends MultiplicityComposite{
        public int formatNumber = 1;
        public Widget createItem() {
            return new CourseActivityList();
        }
    }
    
    // This will probably a custom clu activity widget that uses a CluInfo model dto.
    public static class CourseActivityList extends MultiplicityComposite{
        public int activityNumber = 1;
        public Widget createItem() {
            MultiplicitySection item = new MultiplicitySection("cluInfo");
            CustomNestedSection activity = new CustomNestedSection();
            activity.setSectionTitle(SectionTitle.generateH2Title("Activity " + activityNumber));
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
    
    // TODO look up field label and type from dictionary & messages
 
//    private static Type translateDictType(String dictType) {
//        if (dictType.equalsIgnoreCase("String"))
//            return Type.STRING;
//        else
//            return null;
//    }
//  
//    private static FieldDescriptor createMVCFieldDescriptor(String fieldName, 
//            String objectKey, String type, String state  ) {
//
//        Field f = LUDictionaryManager.getInstance().getField(objectKey, type, state, fieldName);
//
//        FieldDescriptor fd = 
//            new FieldDescriptor(f.getKey(), 
//                    getLabel(type, state, f.getKey() ),   
//                    translateDictType(f.getFieldDescriptor().getDataType())               
//            );
//        return fd;
//    }
//
//
//    private static String getLabel(String type, String state, String fieldId) {
//        String labelKey = type + ":" + state + ":" + fieldId;
//        System.out.println(labelKey);
//        return context.getMessage(labelKey);
//    }
//
    
    //FIXME: This is a temp widget impl for the Curriculum Oversight field. Don't yet know if this
    //will be a multiple org select field, in which case we need a multiple org select picker widget.
    //Otherwise if it's single org picker, need a way to bind a HasText widget to ModelDTOList
    public static class OrgListPicker extends KSSelectItemWidgetAbstract{
        private OrgPicker orgPicker;
                
        public OrgListPicker(){
            orgPicker = new OrgPicker();
            initWidget(orgPicker);
        }
        
        public void deSelectItem(String id) {
            throw new UnsupportedOperationException();
        }

        public List<String> getSelectedItems() {
            ArrayList<String> selectedItems = new ArrayList<String>();
            selectedItems.add(orgPicker.getValue());            
            return selectedItems;
        }

        public boolean isEnabled() {
            return true;
        }

        public void onLoad() {            
        }

        public void redraw() {
            throw new UnsupportedOperationException();                        
        }

        public void selectItem(String id) {
            orgPicker.setValue(id);            
        }

        public void setEnabled(boolean b) {
            throw new UnsupportedOperationException();            
        }
        
        public boolean isMultipleSelect(){
            return true;
        }
    }
    
    public static class CampusLocationList extends KSCheckBoxList{
        public CampusLocationList(){
            SimpleListItems campusLocations = new SimpleListItems();
            
            campusLocations.addItem("North County Campus", "North County Campus");
            campusLocations.addItem("South County Campus", "South County Campus");
            campusLocations.addItem("Extended Studies Campus", "Extended Studies Campus");
            campusLocations.addItem("All Campuses", "All Campuses");
            
            super.setListItems(campusLocations);
        }
    }
    
    public static class CollaboratorTool extends ToolView{
        public CollaboratorTool(){
            super(LuSections.AUTHOR, LUConstants.SECTION_AUTHORS_AND_COLLABORATORS);            
        }
        
        @Override
        protected Widget createWidget() {
            return new Collaborators();
        }
        
    }    
}
