/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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
 */

package org.kuali.student.lum.lu.ui.course.client.configuration.viewclu;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.configurable.mvc.CustomNestedSection;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.MultiplicityComposite;
import org.kuali.student.common.ui.client.configurable.mvc.MultiplicitySection;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.Section.FieldLabelType;
import org.kuali.student.common.ui.client.configurable.mvc.sections.GroupSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.Type;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.list.KSLabelList;
import org.kuali.student.common.ui.client.widgets.list.impl.SimpleListItems;
import org.kuali.student.lum.lu.ui.course.client.configuration.CourseReqSummaryHolder;
import org.kuali.student.lum.lu.ui.course.client.configuration.CourseRequisitesSummaryView;
import org.kuali.student.lum.lu.ui.course.client.configuration.LUConstants;

import com.google.gwt.user.client.ui.Widget;


/**
 * This is the configuration factory class for creating a proposal. 
 * 
 *  FIXME: The summary view from this configurer needs to be pulled out and updated.
 * 
 * @author Kuali Student Team
 *
 */
public class ViewCluConfigurer {
    
    private static boolean WITH_DIVIDER = true;
    private static boolean NO_DIVIDER = false;

    private static String type;
    private static String state;

    public enum LuSections{
        CLU_BEGIN, AUTHOR,  GOVERNANCE, COURSE_LOGISTICS, COURSE_INFO, LEARNING_OBJECTIVES, 
        COURSE_RESTRICTIONS, COURSE_REQUISITES, ACTIVE_DATES, FINANCIALS, PGM_REQUIREMENTS, 
        ATTACHMENTS, COMMENTS, DOCUMENTS
    }



    private static SectionView generateRequisitesSection() {
//      Window.alert("generateRequisitesSection");
      CourseRequisitesSummaryView sectionCourseReq = new CourseRequisitesSummaryView(LuSections.COURSE_REQUISITES, getLabel(LUConstants.REQUISITES_LABEL_KEY));
      CourseReqSummaryHolder.setView(sectionCourseReq);
      return sectionCourseReq;        
//        VerticalSectionView section = initSectionView(LuSections.COURSE_REQUISITES, LUConstants.REQUISITES_LABEL_KEY); 
//
//        VerticalSection preqSection = initSection(getH3Title(LUConstants.PREQS_LABEL_KEY), WITH_DIVIDER);
//        preqSection.addField(new FieldDescriptor("prerequisites", "TO DO", Type.STRING, new KSLabel()));
//
//        VerticalSection creqSection = initSection(getH3Title(LUConstants.CREQS_LABEL_KEY), WITH_DIVIDER);
//        creqSection.addField(new FieldDescriptor("corequisites", "TO DO", Type.STRING, new KSLabel()));
//
//        section.addSection(preqSection);
//        section.addSection(creqSection);
//
//        return section;        
    }
    
    
    public static VerticalSection generateSummaryDetails(SectionTitle title) {
        VerticalSection section = initSection(title, WITH_DIVIDER);

        VerticalSection governance = initSection(getH4Title(LUConstants.GOVERNANCE_LABEL_KEY), WITH_DIVIDER);
        governance.addSection(generateCurriculumOversight(getH5Title(getLabel(LUConstants.ACADEMIC_SUBJECT_ORGS_KEY)), NO_DIVIDER));
        governance.addSection(generateCampusLocation(getH5Title(getLabel(LUConstants.CAMPUS_LOCATION_LABEL_KEY)), NO_DIVIDER));
        governance.addSection(generateAdminOrgs(getH5Title(getLabel(LUConstants.ADMIN_ORG_LABEL_KEY)), NO_DIVIDER));

        VerticalSection logistics = initSection(getH4Title(LUConstants.LOGISTICS_LABEL_KEY), WITH_DIVIDER);
        logistics.addSection(generateInstructors((getH5Title(LUConstants.INSTRUCTOR_LABEL_KEY)), NO_DIVIDER));
        logistics.addSection(generateCredits(getH5Title(LUConstants.CREDITS_LABEL_KEY), NO_DIVIDER));
        logistics.addSection(generateLearningResults(getH5Title(LUConstants.LEARNING_RESULTS_LABEL_KEY), NO_DIVIDER));
        logistics.addSection(generateScheduling(getH5Title(LUConstants.SCHEDULING_LABEL_KEY), NO_DIVIDER));
//        logistics.addSection(generateFormats(getH5Title(LUConstants.FORMATS_LABEL_KEY), NO_DIVIDER));
       
        
        VerticalSection information = initSection(getH4Title(LUConstants.INFORMATION_LABEL_KEY), WITH_DIVIDER);
        information.addSection(generateIdentifiers(getH5Title(LUConstants.IDENTIFIER_LABEL_KEY), NO_DIVIDER));
        information.addSection(generateShortTitle(null, NO_DIVIDER));
        information.addSection(generateLongTitle(null, NO_DIVIDER));
        information.addSection(generateDescription(null, NO_DIVIDER));
        information.addSection(generateRationale(null, NO_DIVIDER));
        information.addSection(generateRequisitesSection());
               
        VerticalSection financials = initSection(getH4Title(LUConstants.FINANCIALS_LABEL_KEY), WITH_DIVIDER);
        financials.addSection(generateFeeType(null, NO_DIVIDER));
        financials.addSection(generateFeeDescription(null, NO_DIVIDER));

        section.addSection(governance);
        section.addSection(logistics);
        section.addSection(information);
        section.addSection(generateLOs(getH4Title(LUConstants.LEARNING_OBJECTIVES_LABEL_KEY), WITH_DIVIDER));
        section.addSection(financials);
        
        section.addSection(generateDates(getH4Title(LUConstants.ACTIVE_DATES_LABEL_KEY), WITH_DIVIDER));

        return section;
    }
    
    private static VerticalSection generateAdminOrgs(SectionTitle title, boolean withDivider) {
        VerticalSection section = initSection(title, withDivider);
        section.addField(new FieldDescriptor("cluInfo/adminOrg", null, Type.STRING, new KSLabel()));
        //FIXME: need to switch to use the new fields once workflow is fixed
//        section.addField(new FieldDescriptor("cluInfo/primaryAdminOrg/orgId", null, Type.STRING, new KSLabel()));
//        section.addField(new FieldDescriptor("cluInfo/alternateAdminOrgs", null, Type.LIST, new AlternateAdminOrgList()));
        return section;
    }

    private static VerticalSection generateCampusLocation(SectionTitle title, boolean withDivider) {
        VerticalSection section = initSection(title, withDivider);
        section.addField(new FieldDescriptor("cluInfo/campusLocationList", null, Type.LIST, new CampusLocationList()));
        return section;
    }

    private static VerticalSection generateCurriculumOversight(SectionTitle title, boolean withDivider) {
        VerticalSection section = initSection(title, withDivider);
        section.addField(new FieldDescriptor("cluInfo/academicSubjectOrgs", null, Type.LIST, new AcademicSubjectOrgList()));
        return section;
    }

    private static VerticalSection generateLOs(SectionTitle title, boolean withDivider) {
        VerticalSection section = initSection(title, withDivider);
        
        //section.addField(new FieldDescriptor("cluInfo/loInfos", null, Type.LIST, new LearningObjectiveList()));

        return section;
    }
    
    private static VerticalSection generateFeeType(SectionTitle title, boolean withDivider) {
        VerticalSection section = initSection(title, withDivider);
        String fieldLabel = null;
        if (title == null) {
            fieldLabel = getLabel(LUConstants.FEE_TYPE_LABEL_KEY);           
        }
        section.addField(new FieldDescriptor("cluInfo/feeType", fieldLabel, Type.LIST, new KSLabel()));
        return section;
    }
    
    private static VerticalSection generateFeeDescription(SectionTitle title, boolean withDivider) {
        VerticalSection section = initSection(title, withDivider);
        section.addField(new FieldDescriptor("cluInfo/feeAmount", getLabel(LUConstants.CURRENCY_SYMBOL_LABEL_KEY), Type.STRING, new KSLabel()));
        section.addField(new FieldDescriptor("cluInfo/taxable", getLabel(LUConstants.TAXABLE_SYMBOL_LABEL_KEY), Type.STRING, new KSLabel()));//TODO checkboxes go here instead
        section.addField(new FieldDescriptor("cluInfo/feeDesc", getLabel(LUConstants.FEE_DESC_LABEL_KEY), Type.STRING, new KSLabel()));
        section.addField(new FieldDescriptor("cluInfo/internalNotation", getLabel(LUConstants.INTERNAL_FEE_NOTIFICATION_LABEL_KEY), Type.STRING, new KSLabel()));
        return section;
    }
    
    private static VerticalSection generateScheduling(SectionTitle title, boolean withDivider) {
        VerticalSection section = initSection(title, withDivider);
        
        GroupSection ns = new GroupSection();
        ns.addField(new FieldDescriptor("cluInfo/offeredAtpTypes", getLabel(LUConstants.TERM_LITERAL_LABEL_KEY), Type.LIST, new OfferedAtpTypeList())); 
        ns.nextLine();
 
        section.addSection(ns);
        section.addField(new FieldDescriptor("cluInfo/stdDuration/atpDurationTypeKey", "Duration Type :   ", Type.STRING, new KSLabel())); 
        section.addField(new FieldDescriptor("cluInfo/stdDuration/timeQuantity", getLabel(LUConstants.DURATION_LITERAL_LABEL_KEY), Type.STRING, new KSLabel())); 
//        section.addField(new FieldDescriptor("cluInfo/intensity/atpDurationTypeKey", "Intensity Type :   ", Type.STRING, new KSLabel())); 
//        section.addField(new FieldDescriptor("cluInfo/intensity/timeQuantity", "Intensity Time :   ", Type.STRING, new KSLabel()));

        return section;
    }

    private static VerticalSection generateLearningResults(SectionTitle title, boolean withDivider) {
        VerticalSection section = initSection(title, withDivider);
        section.addField(new FieldDescriptor("evalType", getLabel(LUConstants.EVALUATION_TYPE_LABEL_KEY), Type.STRING, new KSLabel("evalType"))); //TODO EVAL TYPE ENUMERATION ????
        return section;
    }

    private static VerticalSection generateCredits(SectionTitle title, boolean withDivider) {
        VerticalSection section = initSection(title, withDivider);
        section.addField(new FieldDescriptor("cluInfo/creditType", getLabel(LUConstants.CREDIT_LABEL_KEY), Type.STRING, new KSLabel()));//TODO CREDIT TYPE ENUMERATION
        section.addField(new FieldDescriptor("cluInfo/creditValue", getLabel(LUConstants.CREDIT_VALUE_LABEL_KEY), Type.STRING, new KSLabel()));
        section.addField(new FieldDescriptor("cluInfo/maxCredits", getLabel(LUConstants.MAX_CREDITS_LABEL_KEY), Type.STRING, new KSLabel()));
//        section.addField(new FieldDescriptor("cluInfo/defaultEnrollmentEstimate", "Default Enrollment :    ", Type.STRING, new KSLabel()));
//        section.addField(new FieldDescriptor("cluInfo/defaultMaximumEnrollment", "Maximum Enrollment :    ", Type.STRING, new KSLabel()));
        return section;
    }

    private static VerticalSection generateInstructors(SectionTitle title, boolean withDivider) {
        VerticalSection section = initSection(title, withDivider);
        String fieldLabel = null;
        if (title == null) {
            fieldLabel = getLabel(LUConstants.INSTRUCTOR_LABEL_KEY);           
        }
        section.addField(new FieldDescriptor("cluInfo/primaryInstructor/personId", fieldLabel, Type.STRING, new KSLabel()));
//        section.addField(new FieldDescriptor("cluInfo/primaryInstructor/orgId", "Org ID :      ", Type.STRING, new KSLabel()));
        section.addField(new FieldDescriptor("cluInfo/instructors", null, Type.LIST, new AlternateInstructorList()));
        return section;
    }

    private static VerticalSection generateDescription(SectionTitle title, boolean withDivider) {
        VerticalSection section = initSection(title, withDivider);
        String fieldLabel = null;
        if (title == null) {
            fieldLabel = getLabel(LUConstants.DESCRIPTION_LABEL_KEY);           
        }
        section.addField(new FieldDescriptor("cluInfo/desc/plain", fieldLabel, Type.STRING,  new KSLabel()));
        return section;
    }
    private static VerticalSection generateRationale(SectionTitle title, boolean withDivider) {
        VerticalSection section = initSection(title, withDivider);
        String fieldLabel = null;
        section.addField(new FieldDescriptor("cluInfo/marketingDesc/plain", fieldLabel, Type.STRING, new KSLabel()));
        return section;
    }
    
    private static VerticalSection generateShortTitle(SectionTitle title, boolean withDivider) {
        VerticalSection section = initSection(title, withDivider);
        String fieldLabel = null;
        if (title == null) {
            fieldLabel = getLabel(LUConstants.SHORT_TITLE_LABEL_KEY);           
        }
        section.addField(new FieldDescriptor("cluInfo/officialIdentifier/shortName", fieldLabel, Type.STRING, new KSLabel()));
        return section;
    }
    
    private static VerticalSection generateLongTitle(SectionTitle title, boolean withDivider) {
        VerticalSection section = initSection(title, withDivider);
        String fieldLabel = null;
        if (title == null) {
            fieldLabel = getLabel(LUConstants.TITLE_LABEL_KEY);
        }
        section.addField(new FieldDescriptor("cluInfo/officialIdentifier/longName", fieldLabel, Type.STRING, new KSLabel()));
        return section;
    }

    private static VerticalSection generateVersionCodes(SectionTitle title, boolean withDivider) {
        VerticalSection section = initSection(title, withDivider);
        section.addField(new FieldDescriptor("versionCodes", null, Type.STRING, new VersionCodeList()));
        return section;
    }

    private static VerticalSection generateJointOfferings(SectionTitle title, boolean withDivider) {
        VerticalSection section = initSection(title, withDivider);
        section.addField(new FieldDescriptor("offeredJointly", null, Type.LIST, new OfferedJointlyList()));
        
        return section;
    }

    private static VerticalSection generateCrossListed(SectionTitle title, boolean withDivider) {
        VerticalSection section = initSection(title, withDivider);
        section.setSectionTitle(getH3Title(LUConstants.CROSS_LISTED_LABEL_KEY));
        section.addField(new FieldDescriptor("crossListClus", null, Type.LIST, new CrossListedList()));
//        section.addField(new FieldDescriptor("crossListClus", "TO DO", Type.STRING, new KSLabel()));
        
        return section;
    }

    private static VerticalSection generateIdentifiers(SectionTitle title, boolean withDivider) {
        VerticalSection section = initSection(title, withDivider);
//        section.addField(new FieldDescriptor("cluInfo/officialIdentifier/code", "Code:    ", Type.STRING, new KSLabel()));
        section.addField(new FieldDescriptor("cluInfo/officialIdentifier/division", getLabel(LUConstants.DIVISION_LABEL_KEY), Type.STRING, new KSLabel()));
//        section.addField(new FieldDescriptor("cluInfo/officialIdentifier/level", "Level:    ", Type.STRING, new KSLabel()));
//        section.addField(new FieldDescriptor("cluInfo/officialIdentifier/variation", "Variation:    ", Type.STRING, new KSLabel()));
        section.addField(new FieldDescriptor("cluInfo/officialIdentifier/level", getLabel(LUConstants.LEVEL_LABEL_KEY), Type.STRING, new KSLabel()));
    
        section.addField(new FieldDescriptor("cluInfo/alternateIdentifiers", null, Type.LIST, new AlternateIdentifierList()));
       return section;
    }

    private static VerticalSection generateDates(SectionTitle title, boolean withDivider) {
        VerticalSection section = initSection(title, withDivider);
        
        section.addField(new FieldDescriptor("cluInfo/effectiveDate", getLabel(LUConstants.EFFECTIVE_DATE_LABEL_KEY), Type.DATE, new KSLabel()));
        section.addField(new FieldDescriptor("cluInfo/expirationDate", getLabel(LUConstants.EXPIRATION_DATE_LABEL_KEY), Type.DATE, new KSLabel()));
        return section;
    }

    public static class AcademicSubjectOrgList extends KSLabelList {
        public AcademicSubjectOrgList(){
            SimpleListItems list = new SimpleListItems();

            super.setListItems(list);
        }
    }
    
    public static class CampusLocationList extends KSLabelList {
        public CampusLocationList(){
            SimpleListItems list = new SimpleListItems();

            super.setListItems(list);
        }
    }

    public static class OfferedAtpTypeList extends KSLabelList {
        public OfferedAtpTypeList(){
            SimpleListItems list = new SimpleListItems();

            super.setListItems(list);
        }
    }


    private static class AlternateInstructorList extends MultiplicityComposite{

        protected AlternateInstructorList () {
            this.setItemLabel(getLabel(LUConstants.INSTRUCTOR_LABEL_KEY));      
            this.setDisplayOnly();         
        }

        @Override
        public Widget createItem() {
            MultiplicitySection multi = new MultiplicitySection("CluInstructorInfo");

            CustomNestedSection ns = new CustomNestedSection();
            ns.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
            ns.addField(new FieldDescriptor("personId", null, Type.STRING, new KSLabel()));
//            ns.addField(new FieldDescriptor("orgId", null, Type.STRING, new KSLabel()));
            multi.addSection(ns);

            return multi;
        }

    }

    private static class AlternateIdentifierList extends MultiplicityComposite{

        protected AlternateIdentifierList () {
            this.setItemLabel(getLabel(LUConstants.ALT_IDENTIFIER_LABEL_KEY));      
            this.setDisplayOnly();         
        }

        @Override
        public Widget createItem() {
            MultiplicitySection multi = new MultiplicitySection("CluIdentifierInfo");

            CustomNestedSection ns = new CustomNestedSection();
            ns.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
//            ns.addField(new FieldDescriptor("code", null, Type.STRING, new KSLabel()));
            ns.addField(new FieldDescriptor("division", null, Type.STRING, new KSLabel()));
            ns.addField(new FieldDescriptor("level", null, Type.STRING, new KSLabel()));
//            ns.addField(new FieldDescriptor("variation", null, Type.STRING, new KSLabel()));
//            ns.addField(new FieldDescriptor("suffixCode", null, Type.STRING, new KSLabel()));
            multi.addSection(ns);

            return multi;
        }

    }

    private static class AlternateAdminOrgList extends MultiplicityComposite{

        protected AlternateAdminOrgList () {
            this.setItemLabel(getLabel(LUConstants.ALT_ADMIN_ORG_LABEL_KEY));      
            this.setDisplayOnly();         
        }

        @Override
        public Widget createItem() {
            MultiplicitySection multi = new MultiplicitySection("AdminOrgInfo");

            CustomNestedSection ns = new CustomNestedSection();
            ns.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
            ns.addField(new FieldDescriptor("orgId", null, Type.STRING, new KSLabel()));
            multi.addSection(ns);

            return multi;
        }

    }
    public static class CrossListedList extends MultiplicityComposite {        
        protected CrossListedList () {
            this.setItemLabel(getLabel(LUConstants.CROSS_LISTED_ITEM_LABEL_KEY));      
            this.setDisplayOnly();         
        }
        
        @Override
        public Widget createItem() {
            MultiplicitySection multi = new MultiplicitySection("CluInfo"); // ??
            
            CustomNestedSection ns = new CustomNestedSection();
            ns.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
            ns.addField(new FieldDescriptor("cluInfo/department", getLabel(LUConstants.DEPT_LABEL_KEY), Type.STRING, new KSLabel()));
            ns.nextRow();
            ns.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
            ns.addField(new FieldDescriptor("cluInfo/division", getLabel(LUConstants.DIVISION_LABEL_KEY), Type.STRING, new KSLabel()));
            ns.addField(new FieldDescriptor("cluInfo/level", getLabel(LUConstants.SUFFIX_CODE_LABEL_KEY), Type.STRING, new KSLabel()));
            multi.addSection(ns);
            
            return multi;
        }
    }  
    
    public static class OfferedJointlyList extends MultiplicityComposite {

        protected OfferedJointlyList () {
            this.setItemLabel(getLabel(LUConstants.JOINT_OFFER_ITEM_LABEL_KEY));      
            this.setDisplayOnly();         
        }

        @Override
        public Widget createItem() {
            MultiplicitySection multi = new MultiplicitySection("CluInfo"); 
            
            CustomNestedSection ns = new CustomNestedSection();
            ns.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
            ns.addField(new FieldDescriptor("cluInfo/courseTitle", getLabel(LUConstants.TITLE_LABEL_KEY), Type.STRING , new KSLabel() ));
            multi.addSection(ns);
            
            return multi;
        }
    }

    public static class VersionCodeList extends MultiplicityComposite {
        protected VersionCodeList () {
            this.setItemLabel(getLabel(LUConstants.VERSION_CODE_LABEL_KEY));      
            this.setDisplayOnly();         
        }

        @Override
        public Widget createItem() {
            MultiplicitySection multi = new MultiplicitySection("CluInfo");
            
            CustomNestedSection ns = new CustomNestedSection();
            ns.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
            ns.addField(new FieldDescriptor("cluInfo/versionCode", getLabel(LUConstants.VERSION_CODE_LABEL_KEY), Type.STRING, new KSLabel()));
            ns.addField(new FieldDescriptor("cluInfo/title", getLabel(LUConstants.TITLE_LABEL_KEY), Type.STRING, new KSLabel()));
            multi.addSection(ns);
            
            return multi;
        }
    }



    private static class CourseFormatList extends MultiplicityComposite{
        public int formatNumber = 1;

        protected CourseFormatList () {
            this.setItemLabel(getLabel(LUConstants.FORMATS_LABEL_KEY));      
            this.setDisplayOnly();         
        }

        public Widget createItem() {
            MultiplicitySection item = new MultiplicitySection("CluInfo");

            item.addField(new FieldDescriptor("activities", null, Type.LIST, new CourseActivityList()));
            return new CourseActivityList();
        }
    }

    // This will probably a custom clu activity widget that uses a CluInfo model dto.
    private static class CourseActivityList extends MultiplicityComposite{
        public int activityNumber = 1;

        protected CourseActivityList () {
            this.setItemLabel(getLabel(LUConstants.ACTIVITY_LITERAL_LABEL_KEY));      
            this.setDisplayOnly();         
        }

        public Widget createItem() {
            MultiplicitySection item = new MultiplicitySection("CluInfo");
            CustomNestedSection activity = new CustomNestedSection();
            activity.addField(new FieldDescriptor("cluInfo/type", getLabel(LUConstants.ACTIVITY_TYPE_LABEL_KEY), Type.STRING, new KSLabel()));
            activity.nextRow();

            /* CreditInfo is deprecated, needs to be replaced with learning results
            activity.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
            activity.addField(new FieldDescriptor("creditInfo", "Credit Value", Type.STRING));
            activity.nextRow();
            activity.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
            activity.addField(new FieldDescriptor("evalType", "Learning Result", Type.STRING));
            activity.nextRow();
            */

            activity.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
            activity.addField(new FieldDescriptor("cluInfo/term", getLabel(LUConstants.TERM_LITERAL_LABEL_KEY), Type.STRING, new KSLabel()));
            activity.addField(new FieldDescriptor("cluInfo/stdDuration/timeQuantity", getLabel(LUConstants.DURATION_LITERAL_LABEL_KEY), Type.INTEGER, new KSLabel())); //TODO dropdown need here?
            activity.nextRow();
            activity.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
            activity.addField(new FieldDescriptor("cluInfo/intensity/timeQuantity", getLabel(LUConstants.CONTACT_HOURS_LABEL_KEY), Type.STRING, new KSLabel()));
            //TODO PER WHATEVER
            activity.addField(new FieldDescriptor("defaultEnrollmentEstimate", getLabel(LUConstants.CLASS_SIZE_LABEL_KEY), Type.STRING, new KSLabel()));

            item.addSection(activity);

            return item;
        }

    }
        
/*    public static class CollaboratorTool extends ToolView{
        public CollaboratorTool(){
            super(LuSections.AUTHOR, LUConstants.SECTION_AUTHORS_AND_COLLABORATORS);            
        }

        @Override
        protected Widget createWidget() {
            return new Collaborators();
        }

    }  */  

    private static String getLabel(String fieldId) {
        return Application.getApplicationContext().getUILabel(LUConstants.COURSE_GROUP_NAME, type, state, fieldId);   //FIXME: set proper group name
    }

    public static void setType(String type) {
        ViewCluConfigurer.type = type;
    }

    public static void setState(String state) {
        ViewCluConfigurer.state = state;
    }
    
    private static VerticalSection initSection(SectionTitle title, boolean withDivider) {
        VerticalSection section = new VerticalSection();
        if (title !=  null) {
          section.setSectionTitle(title);
        }
        section.addStyleName(LUConstants.STYLE_SECTION);
        if (withDivider)
            section.addStyleName(LUConstants.STYLE_SECTION_DIVIDER);
        return section;
    }
    
    private static SectionTitle getH1Title(String labelKey) {
        return SectionTitle.generateH2Title(getLabel(labelKey));
    }
    
    private static SectionTitle getH2Title(String labelKey) {
        return SectionTitle.generateH2Title(getLabel(labelKey));
    } 
    
    private static SectionTitle getH3Title(String labelKey) {
        return SectionTitle.generateH3Title(getLabel(labelKey));
    } 
    
    private static SectionTitle getH4Title(String labelKey) {
        return SectionTitle.generateH4Title(getLabel(labelKey));
    } 
    
    private static SectionTitle getH5Title(String labelKey) {
        return SectionTitle.generateH5Title(getLabel(labelKey));
    }
    
}
