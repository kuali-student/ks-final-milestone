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

package org.kuali.student.lum.lu.ui.course.client.configuration.course;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBinding;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBindingSupport;
import org.kuali.student.common.ui.client.configurable.mvc.layouts.ConfigurableLayout;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.DisplayMultiplicityComposite;
import org.kuali.student.common.ui.client.configurable.mvc.sections.BaseSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.CollapsableSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.GroupSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.common.ui.client.widgets.list.KSLabelList;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.core.assembly.data.Data.DataType;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.base.MetaInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.base.RichTextInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.AffiliatedOrgInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseActivityConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseActivityContactHoursConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseActivityDurationConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseCourseSpecificLOsConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseCrossListingsConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseDurationConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseExpenditureInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseFormatConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseJointsConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseLearningResultsConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseRevenueInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseVersionsConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.FeeInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.FeeInfoFixedRateFeeConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.LearningObjectiveConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.SingleUseLoConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.SingleUseRepositoryCategoryConstants;
import org.kuali.student.lum.lu.ui.course.client.configuration.LUConstants;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;


/**
 * This is the configuration factory class for viewing a courss.
 *  
 * @author Kuali Student Team
 *
 */
public class ViewCourseConfigurer 
implements 
CreditCourseConstants,
CreditCourseFormatConstants,
CreditCourseActivityConstants,
MetaInfoConstants,
CreditCourseDurationConstants,
FeeInfoConstants,
LearningObjectiveConstants,
CreditCourseCourseSpecificLOsConstants,
SingleUseLoConstants,
CreditCourseRevenueInfoConstants,
CreditCourseExpenditureInfoConstants,
AffiliatedOrgInfoConstants,
CreditCourseVersionsConstants,
CreditCourseJointsConstants,
RichTextInfoConstants,
FeeInfoFixedRateFeeConstants,
CreditCourseLearningResultsConstants
{

    public static final String COURSE_MODEL	= "courseModel";

	//FIXME: [KSCOR-225] Temp paths waiting for DOL changes
    private static final String STATEMENTS_PATH = "statements";
	private static final String ID_TRANSLATION = "id-translation";

    //FIXME: [KSCOR-225] Initialize type and state from selected cluId
    private String type = "course";
    private String state = "draft";
    private String groupName;
    
    private DataModelDefinition modelDefinition;

    public enum Sections{
        BASIC_INFO, COMPREHENSIVE_INFO
    }

    public void setModelDefinition(DataModelDefinition modelDefinition){
        this.modelDefinition = modelDefinition;
    }    
    
    public void generateLayout(ConfigurableLayout layout) {
        groupName = LUConstants.COURSE_GROUP_NAME;

        layout.addSection(new String[] {getLabel(LUConstants.CURRENT_VIEW_LABEL_KEY)}, generateDetails());

    }
    
    private SectionView generateDetails() {
        VerticalSectionView section = initSectionView(Sections.BASIC_INFO, LUConstants.COURSE_INFORMATION_LABEL_KEY);
        section.enableValidation(false);
               
        section.addSection(generateBasicSection());
        section.addSection(generateComprehensiveSection());

        
        return section; 
    }

	private BaseSection generateBasicSection() {
		
		VerticalSection section = initSection(null, false);
		
        addField(section, TRANSCRIPT_TITLE, LUConstants.SHORT_TITLE_LABEL_KEY, new KSLabel());
		addField(section, CreditCourseConstants.COURSE_TITLE, LUConstants.TITLE_LABEL_KEY, new KSLabel());
        addField(section, QueryPath.concat(CreditCourseConstants.DESCRIPTION, RichTextInfoConstants.PLAIN).toString(), LUConstants.DESCRIPTION_LABEL_KEY, new KSLabel());
        addField(section, CreditCourseConstants.STATE, LUConstants.STATE_LABEL_KEY, new KSLabel());        
        addField(section, CreditCourseConstants.TYPE, LUConstants.TYPE_LABEL_KEY, new KSLabel());
        addDisplayTable(section, VERSIONS, getLabel(LUConstants.VERSION_CODES_LABEL_KEY), buildVersionCodeTable(), null);
        addDisplayTable(section, JOINTS, getLabel(LUConstants.JOINT_OFFERINGS_LABEL_KEY), buildOfferedJointlyTable(), null);
        addDisplayTable(section, CROSS_LISTINGS, getLabel(LUConstants.CROSS_LISTED_LABEL_KEY), buildCrossListedTable(), null);
        
        return section;
	}

    private CollapsableSection generateComprehensiveSection() {
    	
  	    CollapsableSection section = new CollapsableSection(getLabel(LUConstants.DISCLOSURE_PANEL_LABEL_KEY));

		VerticalSection logistics = initSection(getH3Title(getLabel(LUConstants.LOGISTICS_LABEL_KEY)), true);
		logistics.addStyleName(LUConstants.STYLE_SECTION_DIVIDER);
        addField(logistics, PRIMARY_INSTRUCTOR, LUConstants.PRIMARY_INSTRUCTOR_LABEL_KEY, new KSLabel());
        addField(logistics, QueryPath.concat(CreditCourseConstants.DURATION, QUANTITY).toString(), LUConstants.DURATION_QUANTITY_LABEL_KEY, new KSLabel());
        addField(logistics, QueryPath.concat(CreditCourseConstants.DURATION, TERM_TYPE).toString(), LUConstants.DURATION_TYPE_LABEL_KEY, new KSLabel());
        addField(logistics, GRADING_OPTIONS,  LUConstants.LEARNING_RESULT_ASSESSMENT_SCALE_LABEL_KEY, new TranslatedStringList(GRADING_OPTIONS));
        addField(logistics, OUTCOME_OPTIONS,  LUConstants.LEARNING_RESULT_OUTCOME_LABEL_KEY, new OutcomesList(OUTCOME_OPTIONS));
        addField(logistics,  FORMATS, LUConstants.FORMATS_LABEL_KEY, new CourseFormatList(FORMATS));

		VerticalSection learningObjectives = initSection(getH3Title(getLabel(LUConstants.LEARNING_OBJECTIVES_LABEL_KEY)), true);
    	addLearningObjectives(learningObjectives);
    	
		VerticalSection governance = initSection(getH3Title(getLabel(LUConstants.GOVERNANCE_LABEL_KEY)), true);
        addField(governance, ACADEMIC_SUBJECT_ORGS, LUConstants.ACADEMIC_SUBJECT_ORGS_KEY, new TranslatedStringList(ACADEMIC_SUBJECT_ORGS));
        addField(governance, CAMPUS_LOCATIONS, LUConstants.CAMPUS_LOCATION_LABEL_KEY, new TranslatedStringList(CAMPUS_LOCATIONS));        
        addField(governance, DEPARTMENT, LUConstants.DEPT_LABEL_KEY, new KSLabel());
 
		VerticalSection scheduling = initSection(getH3Title(getLabel(LUConstants.SCHEDULING_LABEL_KEY)), true);
        addField(scheduling, CreditCourseConstants.EFFECTIVE_DATE, LUConstants.EFFECTIVE_DATE_LABEL_KEY, new KSLabel());
        addField(scheduling, EXPIRATION_DATE, LUConstants.EXPIRATION_DATE_LABEL_KEY, new KSLabel());
        addField(scheduling, FIRST_EXPECTED_OFFERING, LUConstants.FIRST_OFFERING_KEY, new KSLabel());
        
		VerticalSection financials = initSection(getH3Title(getLabel(LUConstants.FINANCIALS_LABEL_KEY)), true);
        addFinancials(financials);       
 
		VerticalSection requisites = initSection(getH3Title(getLabel(LUConstants.REQUISITES_LABEL_KEY)), true);
        addField(requisites, STATEMENTS_PATH, null, new KSLabelList(true));

    	section.addSection(logistics);
        section.addSection(learningObjectives);
        section.addSection(requisites);
        section.addSection(governance);
        section.addSection(scheduling);
        section.addSection(financials);
        
        return section;

    }

	private void addFinancials(Section section) {

        addField(section, FEES , LUConstants.COURSE_FEE_TITLE, new FeesList(FEES));

		String revenuePath=QueryPath.concat(REVENUE_INFO, REVENUE_ORG).toString();
    	addDisplayTable(section, revenuePath, LUConstants.REVENUE, buildRevenueInformationTable(), null);

        String expenditurePath = QueryPath.concat(EXPENDITURE_INFO, EXPENDITURE_ORG).toString();
        addDisplayTable(section,  expenditurePath , LUConstants.EXPENDITURE, buildExpenditureInformationTable(), null);

	}

	private void addLearningObjectives(Section section) {
		String loPath = QueryPath.concat(COURSE_SPECIFIC_LOS).toString();
    	addDisplayTable(section, loPath, null, buildLoTable(loPath), null );
    }

    private class TranslatedStringList extends DisplayMultiplicityComposite {
		private final String parentPath;
        public TranslatedStringList(String parentPath){
            this.parentPath = parentPath;
        }
        @Override
        public Widget createItem() {
            String path = QueryPath.concat(parentPath, CreditCourseConstants._RUNTIME_DATA, String.valueOf(itemCount-1)).toString();
            GroupSection ns = new GroupSection();
            addField(ns, ID_TRANSLATION, null, new KSLabel(), path);

            return ns;
        }
    }

    private class CourseFormatList extends DisplayMultiplicityComposite {
        private final String parentPath;
        public CourseFormatList(String parentPath){
//            super(StyleType.TOP_LEVEL);
            this.parentPath = parentPath;
            this.setItemLabel(getLabel(LUConstants.FORMAT_LABEL_KEY));
        }

        public Widget createItem() {
            String path = QueryPath.concat(parentPath, String.valueOf(itemCount-1)).toString();

            VerticalSection section = new VerticalSection();
            addDisplayTable(section, ACTIVITIES, null, buildCourseActivityTable(path), path);
            return section;
        }
    }

	private DisplayTable buildCourseActivityTable(String parentPath) {

		String path = QueryPath.concat(parentPath, ACTIVITIES).toString();
		DisplayTable table = new DisplayTable();

		FieldDescriptor activityType = buildDisplayTableFieldFD(path, ACTIVITY_TYPE, getLabel(LUConstants.ACTIVITY_TYPE_LABEL_KEY));
		FieldDescriptor durQty = buildDisplayTableFieldFD(path, QueryPath.concat(CreditCourseActivityConstants.DURATION, CreditCourseActivityDurationConstants.QUANTITY).toString(), getLabel(LUConstants.DURATION_QUANTITY_LABEL_KEY));
		FieldDescriptor durType = buildDisplayTableFieldFD(path, QueryPath.concat(CreditCourseActivityConstants.DURATION, CreditCourseActivityDurationConstants.TIME_UNIT).toString(), getLabel(LUConstants.DURATION_TYPE_LABEL_KEY));
		FieldDescriptor hours = buildDisplayTableFieldFD(path, QueryPath.concat(CONTACT_HOURS, CreditCourseActivityContactHoursConstants.HRS).toString(), getLabel(LUConstants.CONTACT_HOURS_LABEL_KEY));
		FieldDescriptor per = buildDisplayTableFieldFD(path,  QueryPath.concat(CONTACT_HOURS, CreditCourseActivityContactHoursConstants.PER).toString(), " ");
		FieldDescriptor estimate = buildDisplayTableFieldFD(path, DEFAULT_ENROLLMENT_ESTIMATE, getLabel(LUConstants.CLASS_SIZE_LABEL_KEY));

        table.addField(activityType);
        table.addField(durQty);
        table.addField(durType);
        table.addField(hours);
        table.addField(per);
        table.addField(estimate);

        return table;

    }

    private class OutcomesList extends DisplayMultiplicityComposite {
		private final String parentPath;
        public OutcomesList(String parentPath){
            this.parentPath = parentPath;
        }
        @Override
        public Widget createItem() {
            String path = QueryPath.concat(parentPath, String.valueOf(itemCount-1)).toString();
            GroupSection ns = new GroupSection();
            addField(ns, OUTCOME_TYPE, null, new KSLabel(), path);

            return ns;
        }
    }



    public class FeesList extends DisplayMultiplicityComposite {

		private final String parentPath;
        public FeesList(String parentPath){
            this.parentPath = parentPath;
        }

        @Override
        public Widget createItem() {
            String path = QueryPath.concat(parentPath, String.valueOf(itemCount-1)).toString();
            GroupSection groupSection = new GroupSection();

            String fixedFeePath = QueryPath.concat(parentPath, String.valueOf(itemCount-1), FeeInfoConstants.FIXED_RATE_FEE).toString();
            String variableFeePath = QueryPath.concat(parentPath, String.valueOf(itemCount-1), FeeInfoConstants.VARIABLE_RATE_FEE).toString();
            String perCreditFeePath = QueryPath.concat(parentPath, String.valueOf(itemCount-1), FeeInfoConstants.PER_CREDIT_FEE).toString();
            String multipleFeePath = QueryPath.concat(parentPath, String.valueOf(itemCount-1), FeeInfoConstants.MULTIPLE_RATE_FEE).toString();

            addDisplayTable(groupSection, FeeInfoConstants.FIXED_RATE_FEE, null,buildFeeDetailsTable(fixedFeePath), path );
            groupSection.nextLine();
            addDisplayTable(groupSection, FeeInfoConstants.PER_CREDIT_FEE, null, buildFeeDetailsTable(perCreditFeePath), path);
            groupSection.nextLine();
            addDisplayTable(groupSection, FeeInfoConstants.VARIABLE_RATE_FEE, null, buildFeeDetailsTable(variableFeePath), path );
            groupSection.nextLine();
            addDisplayTable(groupSection, FeeInfoConstants.MULTIPLE_RATE_FEE, null,buildFeeDetailsTable(multipleFeePath), path );

            return groupSection;
        }
    }

	private DisplayTable buildFeeDetailsTable(String parentPath) {

		DisplayTable table = new DisplayTable();

		FieldDescriptor rateType = buildDisplayTableFieldFD(parentPath,FeeInfoFixedRateFeeConstants.RATE_TYPE, getLabel(LUConstants.RATE_TYPE));
		FieldDescriptor feeType = buildDisplayTableFieldFD(parentPath,FeeInfoFixedRateFeeConstants.FEE_TYPE, getLabel(LUConstants.FEE_TYPE_LABEL_KEY));
		FieldDescriptor amount = buildDisplayTableFieldFD(parentPath,FeeInfoFixedRateFeeConstants.AMOUNT, getLabel(LUConstants.FEE_AMOUNT_LABEL_KEY));

		table.addField(rateType);
		table.addField(feeType);
		table.addField(amount);

		return table;
	}

   private DisplayTable buildExpenditureInformationTable() {

		DisplayTable table = new DisplayTable();
       String expenditurePath = QueryPath.concat(EXPENDITURE_INFO, EXPENDITURE_ORG).toString();

		FieldDescriptor orgId = buildDisplayTableFieldFD(expenditurePath,ORG_ID, getLabel(LUConstants.ORGANIZATION));
		FieldDescriptor percentage = buildDisplayTableFieldFD(expenditurePath,PERCENTAGE, getLabel(LUConstants.PERCENTAGE));

		table.addField(orgId);
		table.addField(percentage);

		return table;
	}

	private DisplayTable buildRevenueInformationTable() {

		DisplayTable table = new DisplayTable();

		String revenuePath=QueryPath.concat(REVENUE_INFO, REVENUE_ORG).toString();
		FieldDescriptor orgId = buildDisplayTableFieldFD(revenuePath,ORG_ID, getLabel(LUConstants.ORGANIZATION));
		FieldDescriptor percentage = buildDisplayTableFieldFD(revenuePath,PERCENTAGE, getLabel(LUConstants.PERCENTAGE));

		table.addField(orgId);
		table.addField(percentage);

		return table;

    }
    
    public class RevenueInformationList extends DisplayMultiplicityComposite {
    	
		private final String parentPath;
        public RevenueInformationList(String parentPath){
            this.parentPath = parentPath;
        }

        @Override
        public Widget createItem() {
            String path = QueryPath.concat(parentPath, String.valueOf(itemCount-1)).toString();
            GroupSection groupSection = new GroupSection();

            addField(groupSection, PERCENTAGE, LUConstants.PERCENTAGE, new KSLabel(), path);
            addField(groupSection, ORG_ID,  LUConstants.ORGANIZATION,new KSLabel(), path );
            return groupSection;
        }
    }

    public class ExpenditureInformationList extends DisplayMultiplicityComposite {
    	
		private final String parentPath;
        public ExpenditureInformationList(String parentPath){
            this.parentPath = parentPath;
        }

        @Override
        public Widget createItem() {
            String path = QueryPath.concat(parentPath, String.valueOf(itemCount-1)).toString();
            GroupSection groupSection = new GroupSection();

            addField(groupSection, PERCENTAGE, LUConstants.PERCENTAGE, new KSLabel(), path);
            addField(groupSection, ORG_ID,  LUConstants.ORGANIZATION,new KSLabel(), path );

            return groupSection;
        }
    }


	private DisplayTable buildVersionCodeTable() {

		DisplayTable table = new DisplayTable();

		FieldDescriptor versionCode = buildDisplayTableFieldFD(VERSIONS, CreditCourseVersionsConstants.VERSION_CODE, LUConstants.VERSION_CODE_LABEL_KEY);
		FieldDescriptor versionTitle = buildDisplayTableFieldFD(VERSIONS, CreditCourseVersionsConstants.VERSION_TITLE, LUConstants.TITLE_LABEL_KEY);

		table.addField(versionCode);
		table.addField(versionTitle);

		return table;

	}

	private DisplayTable buildOfferedJointlyTable() {

		DisplayTable table = new DisplayTable();

		FieldDescriptor subjectCode = buildDisplayTableFieldFD(JOINTS,CreditCourseJointsConstants.SUBJECT_AREA, getLabel(LUConstants.SUBJECT_CODE_LABEL_KEY));
		FieldDescriptor courseNo = buildDisplayTableFieldFD(JOINTS,CreditCourseJointsConstants.COURSE_NUMBER_SUFFIX, getLabel(LUConstants.COURSE_NUMBER_LABEL_KEY));
		FieldDescriptor title = buildDisplayTableFieldFD(JOINTS,CreditCourseJointsConstants.COURSE_TITLE, getLabel(LUConstants.TITLE_LABEL_KEY));

		table.addField(subjectCode);
		table.addField(courseNo);
		table.addField(title);

		return table;
	}
	
	private DisplayTable buildCrossListedTable() {

		DisplayTable table = new DisplayTable();

		FieldDescriptor subject = buildDisplayTableFieldFD(CROSS_LISTINGS, CreditCourseCrossListingsConstants.SUBJECT_AREA, getLabel(LUConstants.SUBJECT_CODE_LABEL_KEY));
		FieldDescriptor courseNo = buildDisplayTableFieldFD(CROSS_LISTINGS, CreditCourseCrossListingsConstants.COURSE_NUMBER_SUFFIX, getLabel(LUConstants.COURSE_NUMBER_LABEL_KEY));

		table.addField(subject);
		table.addField(courseNo);

		return table;

	}
	
    private DisplayTable buildLoTable(String parentPath)  {

		DisplayTable table = new DisplayTable();

		FieldDescriptor description = buildDisplayTableFieldFD(parentPath,QueryPath.concat(INCLUDED_SINGLE_USE_LO, SingleUseLoConstants.DESCRIPTION, PLAIN).toString(), getLabel(LUConstants.DESCRIPTION_LABEL_KEY));
		FieldDescriptor categories = buildDisplayTableFieldFD(parentPath, QueryPath.concat(INCLUDED_SINGLE_USE_LO,CATEGORIES).toString(), getLabel(LUConstants.LO_CATEGORY_KEY));

		table.addField(description);
		table.addChildField(categories, SingleUseRepositoryCategoryConstants.NAME);

		return table;
    }



    private VerticalSectionView initSectionView (Enum<?> viewEnum, String labelKey) {
        VerticalSectionView section = new VerticalSectionView(viewEnum, getLabel(labelKey), CourseConfigurer.CLU_PROPOSAL_MODEL);
        section.addStyleName(LUConstants.STYLE_SECTION);

        return section;
    }
    
    protected VerticalSection initSection(SectionTitle title, boolean withDivider) {
        VerticalSection section;
    	if(title!=null){
        	section = new VerticalSection(title);
        }else{
        	section = new VerticalSection();
        }
        section.addStyleName(LUConstants.STYLE_SECTION);
        if (withDivider)
            section.addStyleName(LUConstants.STYLE_BOTTOM_DIVIDER);
        return section;
    }

    protected String getTabKey() {
    	return getLabel(LUConstants.CURRENT_VIEW_LABEL_KEY);
    }

    protected MessageKeyInfo generateMessageInfo(String labelKey) {
        return new MessageKeyInfo(groupName, type, state, labelKey);
    }
    
    protected String getLabel(String labelKey) {
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

    // TODO - when DOL is pushed farther down into LOBuilder,
    // revert these 5 methods to returning void again.
    protected FieldDescriptor addField(Section section, String fieldKey) {
    	return addField(section, fieldKey, null, null, null);
    }    
    protected FieldDescriptor addField(Section section, String fieldKey, String messageKey) {
    	return addField(section, fieldKey, messageKey, null, null);
    }
    protected FieldDescriptor addField(Section section, String fieldKey, String messageKey, Widget widget) {
    	return addField(section, fieldKey, messageKey, widget, null);
    }
    protected FieldDescriptor addField(Section section, String fieldKey, String messageKey, String parentPath) {
        return addField(section, fieldKey, messageKey, null, parentPath);
    }
    protected FieldDescriptor addField(Section section, String fieldKey, String messageKey, Widget widget, String parentPath) {
        FieldDescriptor fd = buildFieldDescriptor(fieldKey, messageKey, widget,	parentPath, null);

        section.addField(fd);
        
        return fd;
    }


    private void addDisplayTable(Section section, String fieldKey, String fieldLabel, Widget widget, String parentPath) {
        FieldDescriptor fd = buildFieldDescriptor(fieldKey, fieldLabel, widget,	parentPath, new DisplayTableBinding());

        section.addField(fd);
    }

    //FIXME: [KSCOR-225] rework these next 2 methods to combine them
    private FieldDescriptor buildFieldDescriptor(String fieldKey, String messageKey, Widget widget, String parentPath, ModelWidgetBinding binding) {

		QueryPath path = QueryPath.concat(parentPath, fieldKey);
        Metadata meta = modelDefinition.getMetadata(path);

        String modelPath = getFieldPath(path.toString());

        FieldDescriptor fd = new FieldDescriptor(modelPath, generateMessageInfo(messageKey), meta);
        if (widget != null) {
            fd.setFieldWidget(widget);
        }
        if (binding != null) {
        	fd.setWidgetBinding(binding);
	    }
        fd.getFieldElement().setRequired(false);
		return fd;
	}

	private FieldDescriptor buildDisplayTableFieldFD(String parentPath, String fieldKey, String labelKey) {

		QueryPath parent = QueryPath.concat(parentPath);
        int i = parent.size();

		QueryPath fieldMetaPath = QueryPath.concat(parentPath, QueryPath.getWildCard(), fieldKey);
        Metadata meta = modelDefinition.getMetadata(fieldMetaPath);

        String s = getFieldPath(fieldMetaPath.toString());

        QueryPath fieldPath = QueryPath.concat(s);
        String tablePath =  fieldPath.subPath(++i, fieldPath.size()).toString();

        FieldDescriptor fd = new FieldDescriptor(tablePath, generateMessageInfo(labelKey), meta);
        fd.getFieldElement().setRequired(false);

		return fd;

	}

    /**
     * Determine the field path based on whether this field has an initial lookup defined 
     * in the orchestration dictionary. If no lookup defined just return the original field path
     * 
     * @param path
     * @return
     */
    private String getFieldPath(String path) {
    	 
    	String result = path;
    	
    	QueryPath qPath = QueryPath.parse(path);
    	Metadata metadata = modelDefinition.getMetadata(qPath);

    	if(metadata!=null&&metadata.getInitialLookup()!=null){
    		QueryPath translationPath = qPath.subPath(0, qPath.size()-1);
    		if (metadata.getDataType().equals(DataType.STRING)) {
    			translationPath.add(new Data.StringKey("_runtimeData"));
    			translationPath.add(new Data.StringKey((String)qPath.get(qPath.size() - 1).get()));
    			translationPath.add(new Data.StringKey("id-translation"));
    			result = translationPath.toString();			   		
    		}
    	}
    	return result;
    }
    
    private class DisplayTable extends FlexTable{

    	private static final String STYLE_TABLE = "KS-ViewCourseDisplayTable";
		private static final String STYLE_CELL = "KS-ViewCourseDisplayTableCell";
		private static final String STYLE_HEADER_CELL = "KS-ViewCourseDisplayTableHeaderCell";
		private static final String BLANK_STRING = " ";
		private int col = 0;
    	protected int row = 0;
    	private boolean showHeaders = true;

        protected List<FieldDescriptor> fields = new ArrayList<FieldDescriptor>();
        protected Map<String, String> childKeys = new HashMap<String, String>();

    	{
    		setStyleName(STYLE_TABLE);
    	}

    	protected void nextRow() {
    		row++;
    		col=0;
    	}

    	/**
    	 * The field should resolve to a single value field. The value will be added to the next cell in the
    	 * table in the current row
    	 *
    	 */
    	public void addField(FieldDescriptor field){
    		fields.add(field);
    	}

    	/**
    	 * The parentField should resolve to a Data value field. The fieldKey should resolve to a single value
    	 * field in that Data value. The table binding will iterate the values in the Data object
    	 * and concatenate the named fieldKeys into the same cell in the current row of the table
    	 * separated by commas
    	 *
    	 * @param fieldKey
    	 */
    	public void addChildField(FieldDescriptor parentField, String fieldKey){
    		addField(parentField);
    		childKeys.put(parentField.getFieldKey(), fieldKey);
    	}

    	public void buildHeaders() {
    		if (isShowHeaders()) {
    			for (FieldDescriptor fd: fields) {
    				addHeaderCell(fd.getFieldLabel());
    			}
    			nextRow();
    		}
    	}

    	protected void addHeaderCell(String fieldValue){
    		if (isShowHeaders()) {
        		setCellText(row, col, fieldValue);
        		getCellFormatter().addStyleName(row, col++, STYLE_HEADER_CELL);
    		}
    	}

    	protected void addNextCell( String fieldValue){
    		setCellText(row, col++, fieldValue);
    	}

    	private void setCellText(int row, int cell, String fieldValue) {
    		setText(row, cell, fieldValue);
    		getCellFormatter().addStyleName(row, cell, STYLE_CELL);
    	}

    	public void initTable() {
    		clear();
    		while(getRowCount()>0){
    			removeRow(0);
    		}
    		row=0;
    		col=0;
    	}

		public void addEmptyCell() {
			addNextCell(BLANK_STRING);
		}

		public boolean isShowHeaders() {
			return showHeaders;
		}

		public void setShowHeaders(boolean showHeaders) {
			this.showHeaders = showHeaders;
		}
    }

    private class DisplayTableBinding extends ModelWidgetBindingSupport<DisplayTable> {

    	@Override
    	public void setModelValue(DisplayTable table, DataModel model, String path) {
    		// shouldn't ever need this
    	}

    	@Override
    	public void setWidgetValue(DisplayTable table, DataModel model, String path) {
    		table.initTable();

    		QueryPath qPath = QueryPath.parse(path);
    		Data data = null;
    		if(model!=null){
    			data = model.get(qPath);
    		}

    		if (data != null ){
    			Iterator<Data.Property> iter1 = data.realPropertyIterator();
    			if (iter1.hasNext()) {
    				table.buildHeaders();

    				// iterate through data
    				while(iter1.hasNext()){
    	    			Data.Property prop = iter1.next();
    					Data rowData = prop.getValue();

    					// iterate through the fields defined for this table
    					for (FieldDescriptor field: table.fields) {
    						QueryPath fieldPath = QueryPath.parse(field.getFieldKey());
    						Object o = rowData.query(fieldPath);
    						if (o != null) {
    							// multiple values required in the table cell, concatenate values
    							// with comma separator
    							if (o instanceof Data) {
    								Data cellData = (Data)o;
    								// iterate through the field keys to produce a comma delimited list
    								// of values in a single cell of the table
    								if(cellData!=null && cellData.size()>0){
         					    		StringBuilder sb = new StringBuilder();
    									Iterator<Data.Property> iter = cellData.realPropertyIterator();
    									while(iter.hasNext()){
    										Data.Property p = iter.next();
    										Data d = p.getValue();
    										String key = table.childKeys.get(fieldPath.toString());
    										sb.append(d.get(key)).append(", ");
    									}
    									sb.deleteCharAt(sb.lastIndexOf(", "));
    									table.addNextCell((sb.toString()));
    								}
    								else {
    									table.addEmptyCell();
    								}
    							}
    							// a single value required in a single table cell
    						    else {
    								table.addNextCell((o.toString()));
    							}
    						}
    						else {
								table.addEmptyCell();
    						}
    					}
  					table.nextRow();
  			    	}
    			}
    		}
    	}
    }
}