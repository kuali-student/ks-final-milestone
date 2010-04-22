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

import java.util.Iterator;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBinding;
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
import org.kuali.student.lum.lu.ui.course.client.configuration.LUConstants;

import com.google.gwt.user.client.ui.FlexTable;
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

	private static final String ID_TRANSLATION = "id-translation";
	//FIXME: Temp paths waiting for DOL changes
    private static final String STATEMENTS_PATH = "statements";

    //FIXME:  Initialize type and state from selected cluId
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
        VerticalSectionView section = initSectionView(Sections.BASIC_INFO, LUConstants.COURSE_DETAILS_LABEL_KEY);
        section.enableValidation(false);
               
        section.addSection(generateBasicSection());
        section.addSection(generateComprehensiveSection());

        
        return section; 
    }

	private BaseSection generateBasicSection() {
		
		VerticalSection section = new VerticalSection();
		
		addField(section, CreditCourseConstants.COURSE_TITLE, getLabel(LUConstants.TITLE_LABEL_KEY), new KSLabel());
        addField(section, QueryPath.concat(CreditCourseConstants.DESCRIPTION, RichTextInfoConstants.PLAIN).toString(), getLabel(LUConstants.DESCRIPTION_LABEL_KEY), new KSLabel());

        addField(section, CreditCourseConstants.STATE, getLabel(LUConstants.STATE_LABEL_KEY), new KSLabel());
        
        addField(section, CreditCourseConstants.TYPE, getLabel(LUConstants.TYPE_LABEL_KEY), new KSLabel());
        addField(section, DEPARTMENT, getLabel(LUConstants.DEPT_LABEL_KEY), new KSLabel());

        addField(section, OUTCOME_OPTIONS,  getLabel(LUConstants.LEARNING_RESULT_OUTCOME_LABEL_KEY), new OutcomesList(OUTCOME_OPTIONS));
        addField(section, GRADING_OPTIONS,  getLabel(LUConstants.LEARNING_RESULT_ASSESSMENT_SCALE_LABEL_KEY), new TranslatedStringList(GRADING_OPTIONS));

        addField(section, STATEMENTS_PATH, getLabel(LUConstants.REQUISITES_LABEL_KEY), new KSLabelList(true));
        addField(section,  FORMATS, getLabel(LUConstants.FORMATS_LABEL_KEY), new CourseFormatList(FORMATS));

        addField(section, FEES , getLabel(LUConstants.COURSE_FEE_TITLE), new FeesList(FEES));
        addField(section, CAMPUS_LOCATIONS, getLabel(LUConstants.CAMPUS_LOCATION_LABEL_KEY), new TranslatedStringList(CAMPUS_LOCATIONS));

        addField(section, PRIMARY_INSTRUCTOR, getLabel(LUConstants.PRIMARY_INSTRUCTOR_LABEL_KEY), new KSLabel());
        addField(section, CROSS_LISTINGS, getLabel(LUConstants.CROSS_LISTED_LABEL_KEY), new CrossListedList(CROSS_LISTINGS));
        
        return section;
	}

    private CollapsableSection generateComprehensiveSection() {
    	
  	    CollapsableSection section = new CollapsableSection("Click for Details");

//        addField(section, TERMS_OFFERED, getLabel(LUConstants.TERMS_OFFERED_LABEL_KEY), new TermsOfferedList(TERMS_OFFERED));
        addField(section, FIRST_EXPECTED_OFFERING, getLabel(LUConstants.FIRST_OFFERING_KEY), new KSLabel());
        addField(section, QueryPath.concat(CreditCourseConstants.DURATION, TERM_TYPE).toString(), getLabel(LUConstants.DURATION_TYPE_LABEL_KEY), new KSLabel());
        addField(section, QueryPath.concat(CreditCourseConstants.DURATION, QUANTITY).toString(), getLabel(LUConstants.DURATION_QUANTITY_LABEL_KEY), new KSLabel());
        addField(section, TRANSCRIPT_TITLE, getLabel(LUConstants.SHORT_TITLE_LABEL_KEY), new KSLabel());

        addField(section,  VERSIONS, getLabel(LUConstants.VERSION_CODES_LABEL_KEY), new VersionCodeList(VERSIONS));
        addField(section,  JOINTS, getLabel(LUConstants.JOINT_OFFERINGS_LABEL_KEY), new OfferedJointlyList(JOINTS));

        addField(section, CreditCourseConstants.EFFECTIVE_DATE, getLabel(LUConstants.EFFECTIVE_DATE_LABEL_KEY), new KSLabel());
        addField(section, EXPIRATION_DATE, getLabel(LUConstants.EXPIRATION_DATE_LABEL_KEY), new KSLabel());

        addField(section, ACADEMIC_SUBJECT_ORGS, getLabel(LUConstants.ACADEMIC_SUBJECT_ORGS_KEY), new TranslatedStringList(ACADEMIC_SUBJECT_ORGS));
        
        addFinancials(section);

    	addLearningObjectives(section);
        
        return section;

    }

	private void addFinancials(CollapsableSection section) {
		String revenuePath=QueryPath.concat(REVENUE_INFO, REVENUE_ORG).toString();
        addField(section, revenuePath  , getLabel(LUConstants.REVENUE), new RevenueInformationList(revenuePath));
        String expenditurePath = QueryPath.concat(EXPENDITURE_INFO, EXPENDITURE_ORG).toString();
        addField(section,  expenditurePath , getLabel(LUConstants.EXPENDITURE), new ExpenditureInformationList(expenditurePath));
	}

	private void addLearningObjectives(CollapsableSection section) {
		QueryPath loPath = QueryPath.concat(COURSE_SPECIFIC_LOS);
    	Metadata meta = modelDefinition.getMetadata(loPath);

    	FieldDescriptor fd = new FieldDescriptor(loPath.toString(), getLabel(LUConstants.LEARNING_OBJECTIVES_LABEL_KEY), meta);
        fd.setWidgetBinding(new LoTableBinding());
        fd.setFieldWidget(new LoTable());
   		section.addField(fd);
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
            VerticalSection item = new VerticalSection();
            addField(item, ACTIVITIES, null, new CourseActivityList(QueryPath.concat(parentPath, String.valueOf(itemCount-1), ACTIVITIES).toString()),
                    QueryPath.concat(parentPath, String.valueOf(itemCount -1)).toString());
            return item;
        }
    }

    private class CourseActivityList extends DisplayMultiplicityComposite {

        private final String parentPath;
        public CourseActivityList(String parentPath){
//            super(StyleType.SUB_LEVEL);
            this.parentPath = parentPath;
            this.setItemLabel(getLabel(LUConstants.ACTIVITY_LITERAL_LABEL_KEY));
        }

        public Widget createItem() {
            String path = QueryPath.concat(parentPath, String.valueOf(itemCount-1)).toString();
            GroupSection activity = new GroupSection();

            addField(activity, ACTIVITY_TYPE, null, new KSLabel(), path);
            addField(activity, QueryPath.concat(CreditCourseActivityConstants.DURATION, CreditCourseActivityDurationConstants.QUANTITY).toString(), getLabel(LUConstants.DURATION_LITERAL_LABEL_KEY), new KSLabel(), path);
            addField(activity, QueryPath.concat(CreditCourseActivityConstants.DURATION, CreditCourseActivityDurationConstants.TIME_UNIT).toString(), null, new KSLabel(), path);
            addField(activity, QueryPath.concat(CONTACT_HOURS, CreditCourseActivityContactHoursConstants.HRS).toString(), getLabel(LUConstants.CONTACT_HOURS_LABEL_KEY), new KSLabel(), path);
            addField(activity, QueryPath.concat(CONTACT_HOURS, CreditCourseActivityContactHoursConstants.PER).toString(), null , new KSLabel(), path);
            addField(activity, DEFAULT_ENROLLMENT_ESTIMATE, getLabel(LUConstants.CLASS_SIZE_LABEL_KEY), new KSLabel(), path);

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
            addField(group, CreditCourseVersionsConstants.SUBJECT_AREA, null, new KSLabel(), path);
            addField(group, CreditCourseVersionsConstants.COURSE_NUMBER_SUFFIX, null, new KSLabel(), path);

            return group;
        }
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

    private class VersionCodeList extends DisplayMultiplicityComposite {
		private final String parentPath;
        public VersionCodeList(String parentPath){
            this.parentPath = parentPath;
        }

        @Override
        public Widget createItem() {
            String path = QueryPath.concat(parentPath, String.valueOf(itemCount-1)).toString();
            GroupSection ns = new GroupSection();
            addField(ns, CreditCourseVersionsConstants.SUBJECT_AREA,  null, new KSLabel(), path);
            addField(ns, CreditCourseVersionsConstants.COURSE_NUMBER_SUFFIX,  null, new KSLabel(), path);
            addField(ns, CreditCourseVersionsConstants.VERSION_CODE,  null, new KSLabel(), path);

            addField(ns, CreditCourseVersionsConstants.VERSION_TITLE, null, new KSLabel(), path);

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
            addField(groupSection, CreditCourseJointsConstants.SUBJECT_AREA, getLabel(LUConstants.CODE_LABEL_KEY), new KSLabel(), path);
            addField(groupSection, CreditCourseJointsConstants.COURSE_NUMBER_SUFFIX, getLabel(LUConstants.CODE_LABEL_KEY), new KSLabel(), path);
            addField(groupSection, CreditCourseJointsConstants.COURSE_TITLE, getLabel(LUConstants.TITLE_LABEL_KEY), new KSLabel(), path);

            return groupSection;
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
            addField(groupSection, FeeInfoConstants.FIXED_RATE_FEE, null, new FeeDetailsList(fixedFeePath), path );

            groupSection.nextLine();
            String perCreditFeePath = QueryPath.concat(parentPath, String.valueOf(itemCount-1), FeeInfoConstants.PER_CREDIT_FEE).toString();
            addField(groupSection, FeeInfoConstants.PER_CREDIT_FEE, null, new FeeDetailsList(perCreditFeePath), path );
            
            groupSection.nextLine();
            String variableFeePath = QueryPath.concat(parentPath, String.valueOf(itemCount-1), FeeInfoConstants.VARIABLE_RATE_FEE).toString();
            addField(groupSection, FeeInfoConstants.VARIABLE_RATE_FEE, null, new FeeDetailsList(variableFeePath), path );
            
            groupSection.nextLine();
            String multipleFeePath = QueryPath.concat(parentPath, String.valueOf(itemCount-1), FeeInfoConstants.MULTIPLE_RATE_FEE).toString();
            addField(groupSection, FeeInfoConstants.MULTIPLE_RATE_FEE, null, new FeeDetailsList(multipleFeePath), path );

            return groupSection;
        }
    }
    

    public class FeeDetailsList extends DisplayMultiplicityComposite {
		private final String parentPath;
        public FeeDetailsList(String parentPath){
            this.parentPath = parentPath;
        }

        @Override
        public Widget createItem() {
            String path = QueryPath.concat(parentPath, String.valueOf(itemCount-1)).toString();
            GroupSection groupSection = new GroupSection();
            addField(groupSection,  FeeInfoFixedRateFeeConstants.RATE_TYPE,  getLabel(LUConstants.RATE_TYPE), new KSLabel(),path);
            addField(groupSection,  FeeInfoFixedRateFeeConstants.AMOUNT,  getLabel(LUConstants.FEE_AMOUNT_LABEL_KEY), new KSLabel(),path);
            addField(groupSection, FeeInfoFixedRateFeeConstants.FEE_TYPE, getLabel(LUConstants.FEE_TYPE_LABEL_KEY), new KSLabel(),path );
            return groupSection;
       	
        }
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

            addField(groupSection, PERCENTAGE, getLabel(LUConstants.PERCENTAGE), new KSLabel(), path);
            addField(groupSection, ORG_ID,  getLabel(LUConstants.ORGANIZATION),new KSLabel(), path );
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

            addField(groupSection, PERCENTAGE, getLabel(LUConstants.PERCENTAGE), new KSLabel(), path);
            addField(groupSection, ORG_ID,  getLabel(LUConstants.ORGANIZATION),new KSLabel(), path );

            return groupSection;
        }
    }

    private VerticalSectionView initSectionView (Enum<?> viewEnum, String labelKey) {
        VerticalSectionView section = new VerticalSectionView(viewEnum, getLabel(labelKey), CourseConfigurer.CLU_PROPOSAL_MODEL);
        section.addStyleName(LUConstants.STYLE_SECTION);

        return section;
    }

    protected String getTabKey() {
    	return getLabel(LUConstants.CURRENT_VIEW_LABEL_KEY);
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
        
        String modelPath = getFieldPath(path.toString());

        FieldDescriptor fd = new FieldDescriptor(modelPath, fieldLabel, meta);
        if (widget != null) {
            fd.setFieldWidget(widget);
        }
        section.addField(fd);
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
    
    public class LoTable extends FlexTable{

    	private int col = 0;
    	private int row = 0;

    	public void addLoDescription(String fieldValue){
       		col = 0;
    		// FIXME: add some proper styling
    		setText(++row, col++, " - ");
    		setText(row, col++, fieldValue);
    		setText(row, col++, "  :");
    		
    	}

    	public void addLoCategoryName(String fieldValue){

    		setText(row, col++, fieldValue+ ",");
    		// FIXME: add some proper styling
    		//			getCellFormatter().setStyleName(rowCount, 0, "TableIndent-"+indent);
    		//			if(rowCount%2==0){
    		//				getRowFormatter().addStyleName(rowCount, "SummaryTable-EvenRow");
    		//			}else{
    		//				getRowFormatter().addStyleName(rowCount, "SummaryTable-OddRow");
    		//			}
    	}

		private void initTable() {
			LoTable.this.clear();
			while(LoTable.this.getRowCount()>0){
				LoTable.this.removeRow(0);
			}
			row=0;
			col=0;
		}

    }

    public class LoTableBinding implements ModelWidgetBinding<LoTable>{
    	
    	private final String loDescPath = QueryPath.concat(INCLUDED_SINGLE_USE_LO, SingleUseLoConstants.DESCRIPTION, PLAIN).toString();
    	private final String categoryNamePath = QueryPath.concat(INCLUDED_SINGLE_USE_LO, CATEGORIES).toString();

    	@Override
    	public void setModelValue(LoTable table, DataModel model,
    			String path) {
    			// shouldn't ever need this
    	}
    	@Override
    	public void setWidgetValue(LoTable table, DataModel model,
    			String path) {
    		
    		table.initTable();
    		Object value = model.getRoot().query(path);
    		if (value != null && value instanceof Data){
    	 		Iterator<Data.Property> iter1 = ((Data)value).iterator();
    			// iterate through Los
    			while(iter1.hasNext()){
    				Data.Property prop = iter1.next();
    				Data d = prop.getValue();
    				if (d != null) {
    					table.addLoDescription((String)d.query(loDescPath));

    					Data categoryData = d.query(categoryNamePath);
    					Iterator<Data.Property> iter2 = categoryData.iterator();
    					// iterate through Los
    					while(iter2.hasNext()){
    	    				Data.Property prop2 = iter2.next();
    	    				Data d2 = prop2.getValue();
    						table.addLoCategoryName((String)d2.get(NAME));      				
    					}			
    				}
    			}					 
    		}
    	}
    }
}