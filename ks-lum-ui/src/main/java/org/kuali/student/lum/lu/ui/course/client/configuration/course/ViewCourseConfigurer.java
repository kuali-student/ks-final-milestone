/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.lum.lu.ui.course.client.configuration.course;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.layouts.ConfigurableLayout;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.DisplayMultiplicityComposite;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityComposite.StyleType;
import org.kuali.student.common.ui.client.configurable.mvc.sections.BaseSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.GroupSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.common.ui.client.widgets.KSDisclosureSection;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.list.KSLabelList;
import org.kuali.student.common.ui.client.widgets.list.impl.SimpleListItems;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.base.MetaInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseActivityConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseActivityContactHoursConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseActivityDurationConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseDurationConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseFormatConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseProposalConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.FeeInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.LearningObjectiveConstants;
import org.kuali.student.lum.lu.ui.course.client.configuration.CourseRequisitesSectionView;
import org.kuali.student.lum.lu.ui.course.client.configuration.LUConstants;
import org.kuali.student.lum.lu.ui.course.client.configuration.course.CourseConfigurer.CluActivityType;
import org.kuali.student.lum.lu.ui.course.client.configuration.course.CourseConfigurer.CourseActivityList;
import org.kuali.student.lum.lu.ui.course.client.configuration.course.CourseConfigurer.DurationAtpTypeList;
import org.kuali.student.lum.lu.ui.course.client.configuration.mvc.LuConfigurer.LuSections;

import com.google.gwt.user.client.ui.Widget;


/**
 * This is the configuration factory class for creating a proposal.
 *
 * TODO: The following is a list of items that need to be fixed.
 *  1) All hardcoded drop downs need to be replaced with one populated via an enumeration lookup
 *  2) Any pickers (eg. org, course, needs to be replaced wtih proper lookup based search pickers
 *  
 * @author Kuali Student Team
 *
 */
public class ViewCourseConfigurer 
implements 
CreditCourseProposalConstants,
CreditCourseConstants,
CreditCourseFormatConstants,
CreditCourseActivityConstants,
MetaInfoConstants,
CreditCourseDurationConstants,
FeeInfoConstants,
LearningObjectiveConstants
{

    
    //FIXME: Need to fix this field key 
    private static final String CONSTANT_ORG_ID = "orgId";
    
    //FIXME:  Initialize type and state from selected cluId
    private String type = "course";
    private String state = "draft";
    private String groupName;
    
    //TODO look up labels
    private String BASIC_VIEW = "Basic";
    private String COMPREHENSIVE_VIEW = "Comprehensive";
    public static final String COURSE_DETAILS_TAB = "Course Details";
    protected static final String COURSE_VIEW = "View";

    private DataModelDefinition modelDefinition;

    public enum Sections{
        BASIC_INFO, COMPREHENSIVE_INFO
    }

    public void setModelDefinition(DataModelDefinition modelDefinition){
        this.modelDefinition = modelDefinition;
    }    
    
    public void generateLayout(ConfigurableLayout layout) {
        groupName = LUConstants.COURSE_GROUP_NAME;

        layout.addSection(new String[] {COURSE_DETAILS_TAB, COURSE_VIEW}, generateBasicSection());
        layout.addSection(new String[] {COURSE_DETAILS_TAB, COURSE_VIEW}, generateComprehensiveSection());

    }
  
    //TODO add course code & title as page/tab header
    private BaseSection generateTitle() {
               
        GroupSection section = new GroupSection();
        addField(section, SUBJECT_AREA, null, new KSLabel());
        addField(section, COURSE_NUMBER_SUFFIX, null, new KSLabel());
        addField(section, TRANSCRIPT_TITLE, null, new KSLabel());

        return section;

    }
    
    private SectionView generateBasicSection() {
        VerticalSectionView section = initSectionView(Sections.BASIC_INFO, BASIC_VIEW);

//        section.addSection(generateTitle());
        
        addField(section, "fees/0/attributes/CourseCode", getLabel(LUConstants.CODE_LABEL_KEY), new KSLabel());
        
        addField(section, CreditCourseConstants.COURSE_TITLE, getLabel(LUConstants.TITLE_LABEL_KEY), new KSLabel());
        addField(section, DESCRIPTION+ "/" + "plain", getLabel(LUConstants.DESCRIPTION_LABEL_KEY), new KSLabel());

        addField(section,  CreditCourseConstants.STATE, "State", new KSLabel());
        
        //FIXME: This is a hack to return id/key field names rather than the id/key. Fix in M5
        addField(section,  "fees/0/attributes/CourseType", "Type", new KSLabel());
        addField(section, "fees/0/attributes/DeptOrgName", getLabel(LUConstants.DEPT_LABEL_KEY), new KSLabel());

//FIXME        addField(section, CREDITS,  getLabel(LUConstants.CREDITS_LABEL_KEY), new KSLabel());
//FIXME        addField(section, "statements", getLabel(LUConstants.REQUISITES_LABEL_KEY), new StatementList());
        addField(section,  FORMATS, getLabel(LUConstants.FORMATS_LABEL_KEY), new CourseFormatList(FORMATS));
//FIXME        addField(section, FEES + "/" + "id", getLabel(LUConstants.FINANCIALS_LABEL_KEY), new KSLabel());
        addField(section, CAMPUS_LOCATIONS, getLabel(LUConstants.CAMPUS_LOCATION_LABEL_KEY), new CampusLocationList());

        addField(section,  PRIMARY_INSTRUCTOR, "Primary Instructor", new KSLabel());
//      TODO        addField(section,  NOTES, null, new KSLabel());
        addField(section, CROSS_LISTINGS, getLabel(LUConstants.CROSS_LISTED_LABEL_KEY), new CrossListedList(CROSS_LISTINGS));    
        
        KSDisclosureSection disclosure =  new KSDisclosureSection("More Details", new KSLabel("Hello"), false);
        section.addWidget(disclosure);


        return section;
    }

    private VerticalSectionView generateComprehensiveSection() {
        VerticalSectionView section = initSectionView(Sections.COMPREHENSIVE_INFO, COMPREHENSIVE_VIEW);

        addField(section, "fees/0/attributes/CourseCode", getLabel(LUConstants.CODE_LABEL_KEY), new KSLabel());

//FIXME  In M4 Only one term offered can be set so don't need a list here. Fix for M5
//      addField(section, CreditCourseConstants.TERMS_OFFERED, "Terms Offered", new TermsOfferedList());
        addField(section, "fees/0/attributes/TermOffered", "Terms Offered", new KSLabel());
        addField(section, CreditCourseConstants.DURATION + "/" + TERM_TYPE, "Term Type", new KSLabel());
        addField(section, CreditCourseConstants.DURATION + "/" + QUANTITY, "Quantity", new KSLabel());
        addField(section, CreditCourseConstants.TRANSCRIPT_TITLE, getLabel(LUConstants.SHORT_TITLE_LABEL_KEY), new KSLabel());

//FIXME  In M4 Only one primary admin org can be set so don't need a list here. Fix for M5
//      addField(section, CreditCourseConstants.ALT_ADMIN_ORGS, null, new AlternateAdminOrgList(ALT_ADMIN_ORGS));
        addField(section,  VERSIONS, getLabel(LUConstants.VERSION_CODES_LABEL_KEY), new VersionCodeList(VERSIONS));
        addField(section,  JOINTS, getLabel(LUConstants.JOINT_OFFERINGS_LABEL_KEY), new OfferedJointlyList(JOINTS));

        addField(section, EFFECTIVE_DATE, getLabel(LUConstants.EFFECTIVE_DATE_LABEL_KEY), new KSLabel());
        addField(section, EXPIRATION_DATE, getLabel(LUConstants.EXPIRATION_DATE_LABEL_KEY), new KSLabel());

//FIXME  In M4 Only one academic subject org can be set so don't need a list here. Fix for M5
//      addField(section, ACADEMIC_SUBJECT_ORGS, getLabel(LUConstants.ACADEMIC_SUBJECT_ORGS_KEY), new AcademicSubjectOrgList());
        addField(section, "fees/0/attributes/OversightName", getLabel(LUConstants.ACADEMIC_SUBJECT_ORGS_KEY), new KSLabel());

        addField(section, COURSE_SPECIFIC_L_OS,  getLabel(LUConstants.LEARNING_OBJECTIVES_LABEL_KEY),  new LearningObjectiveList(COURSE_SPECIFIC_L_OS));
        
        return section;

    }

    private class AlternateAdminOrgList extends DisplayMultiplicityComposite{

        private final String parentPath;
        public AlternateAdminOrgList(String parentPath){
            this.parentPath = parentPath;
        }

        public Widget createItem() {
            String path = QueryPath.concat(parentPath, String.valueOf(itemCount-1)).toString();
            VerticalSection item = new VerticalSection();
            addField(item, CONSTANT_ORG_ID, null, new KSLabel(), path);
            return item;
        }
    }

    private class AlternateInstructorList extends DisplayMultiplicityComposite{

        private final String parentPath;
        public AlternateInstructorList(String parentPath){
            this.parentPath = parentPath;
        }

        public Widget createItem() {
            String path = QueryPath.concat(parentPath, String.valueOf(itemCount-1)).toString();
            VerticalSection item = new VerticalSection();
            addField(item, "personId", null, new KSLabel(), path);
            return item;
        }
    }

    private class CampusLocationList extends KSLabelList {
        public CampusLocationList(){
            SimpleListItems list = new SimpleListItems();

            super.setListItems(list);
        }
    }

    private class CluActivityTypeList extends KSLabelList{
        public CluActivityTypeList(){
            SimpleListItems list = new SimpleListItems();

            super.setListItems(list);
        }
    }

    private class ContactHoursAtpTypeList extends KSLabelList{
        public ContactHoursAtpTypeList(){
            SimpleListItems list = new SimpleListItems();

            super.setListItems(list);
        }

    }

    //FIXME CourseFormatList - not sure what needs to be here
    private class CourseFormatList extends DisplayMultiplicityComposite {
        private final String parentPath;
        public CourseFormatList(String parentPath){
//            super(StyleType.TOP_LEVEL);
            this.parentPath = parentPath;
        }

        public Widget createItem() {
            VerticalSection item = new VerticalSection();
            addField(item, ACTIVITIES, null, new CourseActivityList(QueryPath.concat(parentPath, String.valueOf(itemCount-1), ACTIVITIES).toString()),
                    parentPath + "/" + String.valueOf(itemCount -1));
            return item;
        }
    }

    //FIXME CourseActivityList - not sure what needs to be here
    private class CourseActivityList extends DisplayMultiplicityComposite {

        private final String parentPath;
        public CourseActivityList(String parentPath){
//            super(StyleType.SUB_LEVEL);
            this.parentPath = parentPath;
        }

        public Widget createItem() {
            String path = QueryPath.concat(parentPath, String.valueOf(itemCount-1)).toString();
            GroupSection activity = new GroupSection();
            addField(activity, ACTIVITY_TYPE, null, new KSLabel(), path);
//            activity.nextLine();
//
//            addField(activity, CreditCourseActivityConstants.DURATION + "/" + CreditCourseActivityDurationConstants.QUANTITY, getLabel(LUConstants.DURATION_LITERAL_LABEL_KEY), new KSLabel(), path);
//            addField(activity, CreditCourseActivityConstants.DURATION + "/" + CreditCourseActivityDurationConstants.TIME_UNIT, "Duration Type", new KSLabel(), path);
//
//            activity.nextLine();
//            addField(activity, CONTACT_HOURS + "/" + CreditCourseActivityContactHoursConstants.HRS, "Contact Hours" , new KSLabel(), path);
//            addField(activity, DEFAULT_ENROLLMENT_ESTIMATE, getLabel(LUConstants.CLASS_SIZE_LABEL_KEY), new KSLabel(), path);

            return activity;
        }
    }

    private class CrossListedList extends DisplayMultiplicityComposite {
        private final String parentPath;
        public CrossListedList(String parentPath){
            this.parentPath = parentPath;
        }

        @Override
        public Widget createItem() {
            String path = QueryPath.concat(parentPath, String.valueOf(itemCount-1)).toString();
            GroupSection group = new GroupSection();
            addField(group, SUBJECT_AREA, null, new KSLabel(), path);
            addField(group, COURSE_NUMBER_SUFFIX, null, new KSLabel(), path);

            return group;
        }
    }

    private class AcademicSubjectOrgList extends KSLabelList{

        public AcademicSubjectOrgList(){
            SimpleListItems list = new SimpleListItems();

            super.setListItems(list);
        }

    }

//    private class TermsOfferedList extends DisplayMultiplicityComposite{
//        private final String parentPath;
//        public TermsOfferedList(String parentPath){
//            this.parentPath = parentPath;
//        }
//
//        @Override
//        public Widget createItem() {
//            String path = QueryPath.concat(parentPath, String.valueOf(itemCount-1)).toString();
//            GroupSection group = new GroupSection();
//            addField(group, String.valueOf(itemCount-1), null, new KSLabel(), path);
//
//            return group;
//        }
//    }

    private class TermsOfferedList extends KSLabelList{
        public TermsOfferedList(){
            SimpleListItems list = new SimpleListItems();

            super.setListItems(list);
        }

    }
    
    private class DurationAtpTypeList extends KSLabelList{
        public DurationAtpTypeList(){
            SimpleListItems list = new SimpleListItems();

            super.setListItems(list);
        }
    }

    //FIXME StatementList - not sure what needs to be here
    private class StatementList extends DisplayMultiplicityComposite {

        @Override
        public Widget createItem() {
            GroupSection ns = new GroupSection();
//          addField(ns, "not sure what goes here", null, new KSLabel());

            return ns;
        }
    }
    
    private class LearningObjectiveList extends DisplayMultiplicityComposite {
        private final String parentPath;
        public LearningObjectiveList(String parentPath){
            this.parentPath = parentPath;
        }
        @Override
        public Widget createItem() {
            String path = QueryPath.concat(parentPath, String.valueOf(itemCount-1)).toString();
            GroupSection ns = new GroupSection();
            addField(ns, "includedSingleUseLo/description/plain", null, new KSLabel(), path);

            return ns;
        }
    }

    private class VersionCodeList extends DisplayMultiplicityComposite {
        private final String parentPath;
        public VersionCodeList(String parentPath){
            this.parentPath = parentPath;
        }

        @Override
        public Widget createItem() {
            String path = QueryPath.concat(parentPath, String.valueOf(itemCount-1)).toString();
            GroupSection ns = new GroupSection();
            addField(ns, "subjectArea",  null, new KSLabel(), path);
            addField(ns, "courseNumberSuffix",  null, new KSLabel(), path);
            addField(ns, "versionCode",  null, new KSLabel(), path);

            addField(ns, "versionTitle", null, new KSLabel(), path);

            return ns;
        }
    }

    private class OfferedJointlyList extends DisplayMultiplicityComposite {

        private final String parentPath;
        public OfferedJointlyList(String parentPath){
            this.parentPath = parentPath;
        }

        @Override
        public Widget createItem() {
            String path = QueryPath.concat(parentPath, String.valueOf(itemCount-1)).toString();
            GroupSection groupSection = new GroupSection();
            addField(groupSection, "courseId", "courseId", new KSLabel(), path);
            addField(groupSection, "courseTitle", "courseTitle", new KSLabel(), path);

            return groupSection;
        }
    } 

    private VerticalSectionView initSectionView (Enum<?> viewEnum, String labelKey) {
        VerticalSectionView section = new VerticalSectionView(viewEnum, getLabel(labelKey), CourseConfigurer.CLU_PROPOSAL_MODEL);
        section.addStyleName(LUConstants.STYLE_SECTION);
        section.setSectionTitle(getH1Title(labelKey));

        return section;
    }

    private String getLabel(String labelKey) {
        return Application.getApplicationContext().getUILabel(groupName, type, state, labelKey);
    }

    private SectionTitle getH1Title(String labelKey) {
        return SectionTitle.generateH1Title(getLabel(labelKey));
    } 

    private SectionTitle getH2Title(String labelKey) {
        return SectionTitle.generateH2Title(getLabel(labelKey));
    } 

    private SectionTitle getH3Title(String labelKey) {
        return SectionTitle.generateH3Title(getLabel(labelKey));
    } 

    private SectionTitle getH4Title(String labelKey) {
        return SectionTitle.generateH4Title(getLabel(labelKey));
    } 

    private SectionTitle getH5Title(String labelKey) {
        return SectionTitle.generateH5Title(getLabel(labelKey));
    }


    private void addField(Section section, String fieldKey, String fieldLabel) {
        addField(section, fieldKey, fieldLabel, null, null);
    }
    private void addField(Section section, String fieldKey, String fieldLabel, Widget widget) {
        addField(section, fieldKey, fieldLabel, widget, null);
    }
    private void addField(Section section, String fieldKey, String fieldLabel, String parentPath) {
        addField(section, fieldKey, fieldLabel, null, parentPath);
    }
    private void addField(Section section, String fieldKey, String fieldLabel, Widget widget, String parentPath) {
        QueryPath path = QueryPath.concat(parentPath, fieldKey);
        Metadata meta = modelDefinition.getMetadata(path);

        FieldDescriptor fd = new FieldDescriptor(path.toString(), fieldLabel, meta);
        if (widget != null) {
            fd.setFieldWidget(widget);
        }
        section.addField(fd);
    }

    // TODO look up field label and type from dictionary & messages

//  private Type translateDictType(String dictType) {
//  if (dictType.equalsIgnoreCase("String"))
//  return Type.STRING;
//  else
//  return null;
//  }

//  private FieldDescriptor createMVCFieldDescriptor(String fieldName,
//  String objectKey, String type, String state  ) {

//  Field f = LUDictionaryManager.getInstance().getField(objectKey, type, state, fieldName);

//  FieldDescriptor fd =
//  new FieldDescriptor(f.getKey(),
//  getLabel(type, state, f.getKey() ),
//  translateDictType(f.getFieldDescriptor().getDataType())
//  );
//  return fd;
//  }


//  private String getLabel(String type, String state, String fieldId) {
//  String labelKey = type + ":" + state + ":" + fieldId;
//  System.out.println(labelKey);
//  return context.getMessage(labelKey);
//  }


}