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
        VerticalSectionView section = initSectionView(Sections.BASIC_INFO, LUConstants.COURSE_INFORMATION_LABEL_KEY);
        section.enableValidation(false);
               
        section.addSection(generateBasicSection());
        section.addSection(generateComprehensiveSection());

        
        return section; 
    }

	private BaseSection generateBasicSection() {
		
		VerticalSection section = initSection(null, false);
		
        addField(section, TRANSCRIPT_TITLE, generateMessageInfo(LUConstants.SHORT_TITLE_LABEL_KEY), new KSLabel());
		addField(section, CreditCourseConstants.COURSE_TITLE, generateMessageInfo(LUConstants.TITLE_LABEL_KEY), new KSLabel());
        addField(section, QueryPath.concat(CreditCourseConstants.DESCRIPTION, RichTextInfoConstants.PLAIN).toString(), generateMessageInfo(LUConstants.DESCRIPTION_LABEL_KEY), new KSLabel());
        addField(section, CreditCourseConstants.STATE, generateMessageInfo(LUConstants.STATE_LABEL_KEY), new KSLabel());        
        addField(section, CreditCourseConstants.TYPE, generateMessageInfo(LUConstants.TYPE_LABEL_KEY), new KSLabel());
        addField(section,  VERSIONS, generateMessageInfo(LUConstants.VERSION_CODES_LABEL_KEY), new VersionCodeList(VERSIONS));
        addField(section,  JOINTS, generateMessageInfo(LUConstants.JOINT_OFFERINGS_LABEL_KEY), new OfferedJointlyList(JOINTS));
        addField(section, CROSS_LISTINGS, generateMessageInfo(LUConstants.CROSS_LISTED_LABEL_KEY), new CrossListedList(CROSS_LISTINGS));
        
        return section;
	}

    private CollapsableSection generateComprehensiveSection() {
    	
  	    CollapsableSection section = new CollapsableSection(getLabel(LUConstants.DISCLOSURE_PANEL_LABEL_KEY));

		VerticalSection logistics = initSection(getH3Title(getLabel(LUConstants.LOGISTICS_LABEL_KEY)), true);
		logistics.addStyleName(LUConstants.STYLE_SECTION_DIVIDER);
        addField(logistics, PRIMARY_INSTRUCTOR, generateMessageInfo(LUConstants.PRIMARY_INSTRUCTOR_LABEL_KEY), new KSLabel());
        addField(logistics, QueryPath.concat(CreditCourseConstants.DURATION, QUANTITY).toString(), generateMessageInfo(LUConstants.DURATION_QUANTITY_LABEL_KEY), new KSLabel());
        addField(logistics, QueryPath.concat(CreditCourseConstants.DURATION, TERM_TYPE).toString(), generateMessageInfo(LUConstants.DURATION_TYPE_LABEL_KEY), new KSLabel());
        addField(logistics, GRADING_OPTIONS,  generateMessageInfo(LUConstants.LEARNING_RESULT_ASSESSMENT_SCALE_LABEL_KEY), new TranslatedStringList(GRADING_OPTIONS));
        addField(logistics, OUTCOME_OPTIONS,  generateMessageInfo(LUConstants.LEARNING_RESULT_OUTCOME_LABEL_KEY), new OutcomesList(OUTCOME_OPTIONS));
        addField(logistics,  FORMATS, generateMessageInfo(LUConstants.FORMATS_LABEL_KEY), new CourseFormatList(FORMATS));

		VerticalSection learningObjectives = initSection(getH3Title(getLabel(LUConstants.LEARNING_OBJECTIVES_LABEL_KEY)), true);
    	addLearningObjectives(learningObjectives);
    	
		VerticalSection governance = initSection(getH3Title(getLabel(LUConstants.GOVERNANCE_LABEL_KEY)), true);
        addField(governance, ACADEMIC_SUBJECT_ORGS, generateMessageInfo(LUConstants.ACADEMIC_SUBJECT_ORGS_KEY), new TranslatedStringList(ACADEMIC_SUBJECT_ORGS));
        addField(governance, CAMPUS_LOCATIONS, generateMessageInfo(LUConstants.CAMPUS_LOCATION_LABEL_KEY), new TranslatedStringList(CAMPUS_LOCATIONS));        
        addField(governance, DEPARTMENT, generateMessageInfo(LUConstants.DEPT_LABEL_KEY), new KSLabel());
 
		VerticalSection scheduling = initSection(getH3Title(getLabel(LUConstants.SCHEDULING_LABEL_KEY)), true);
        addField(scheduling, CreditCourseConstants.EFFECTIVE_DATE, generateMessageInfo(LUConstants.EFFECTIVE_DATE_LABEL_KEY), new KSLabel());
        addField(scheduling, EXPIRATION_DATE, generateMessageInfo(LUConstants.EXPIRATION_DATE_LABEL_KEY), new KSLabel());
        addField(scheduling, FIRST_EXPECTED_OFFERING, generateMessageInfo(LUConstants.FIRST_OFFERING_KEY), new KSLabel());
        
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
		
        addField(section, FEES , generateMessageInfo(LUConstants.COURSE_FEE_TITLE), new FeesList(FEES));
		String revenuePath=QueryPath.concat(REVENUE_INFO, REVENUE_ORG).toString();
        addField(section, revenuePath  , generateMessageInfo(LUConstants.REVENUE), new RevenueInformationList(revenuePath));
        String expenditurePath = QueryPath.concat(EXPENDITURE_INFO, EXPENDITURE_ORG).toString();
        addField(section,  expenditurePath , generateMessageInfo(LUConstants.EXPENDITURE), new ExpenditureInformationList(expenditurePath));

	}

	private void addLearningObjectives(Section section) {
		QueryPath loPath = QueryPath.concat(COURSE_SPECIFIC_LOS);
    	Metadata meta = modelDefinition.getMetadata(loPath);

    	FieldDescriptor fd = new FieldDescriptor(loPath.toString(), null, meta);
        fd.setWidgetBinding(new LoTableBinding());
        fd.setFieldWidget(new ViewTable());
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
            addField(activity, QueryPath.concat(CreditCourseActivityConstants.DURATION, CreditCourseActivityDurationConstants.QUANTITY).toString(), generateMessageInfo(LUConstants.DURATION_LITERAL_LABEL_KEY), new KSLabel(), path);
            addField(activity, QueryPath.concat(CreditCourseActivityConstants.DURATION, CreditCourseActivityDurationConstants.TIME_UNIT).toString(), null, new KSLabel(), path);
            addField(activity, QueryPath.concat(CONTACT_HOURS, CreditCourseActivityContactHoursConstants.HRS).toString(), generateMessageInfo(LUConstants.CONTACT_HOURS_LABEL_KEY), new KSLabel(), path);
            addField(activity, QueryPath.concat(CONTACT_HOURS, CreditCourseActivityContactHoursConstants.PER).toString(), null , new KSLabel(), path);
            addField(activity, DEFAULT_ENROLLMENT_ESTIMATE, generateMessageInfo(LUConstants.CLASS_SIZE_LABEL_KEY), new KSLabel(), path);

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
            addField(groupSection, CreditCourseJointsConstants.SUBJECT_AREA, generateMessageInfo(LUConstants.CODE_LABEL_KEY), new KSLabel(), path);
            addField(groupSection, CreditCourseJointsConstants.COURSE_NUMBER_SUFFIX, generateMessageInfo(LUConstants.CODE_LABEL_KEY), new KSLabel(), path);
            addField(groupSection, CreditCourseJointsConstants.COURSE_TITLE, generateMessageInfo(LUConstants.TITLE_LABEL_KEY), new KSLabel(), path);

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
            addField(groupSection,  FeeInfoFixedRateFeeConstants.RATE_TYPE,  generateMessageInfo(LUConstants.RATE_TYPE), new KSLabel(),path);
            addField(groupSection,  FeeInfoFixedRateFeeConstants.AMOUNT,  generateMessageInfo(LUConstants.FEE_AMOUNT_LABEL_KEY), new KSLabel(),path);
            addField(groupSection, FeeInfoFixedRateFeeConstants.FEE_TYPE, generateMessageInfo(LUConstants.FEE_TYPE_LABEL_KEY), new KSLabel(),path );
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

            addField(groupSection, PERCENTAGE, generateMessageInfo(LUConstants.PERCENTAGE), new KSLabel(), path);
            addField(groupSection, ORG_ID,  generateMessageInfo(LUConstants.ORGANIZATION),new KSLabel(), path );
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

            addField(groupSection, PERCENTAGE, generateMessageInfo(LUConstants.PERCENTAGE), new KSLabel(), path);
            addField(groupSection, ORG_ID,  generateMessageInfo(LUConstants.ORGANIZATION),new KSLabel(), path );

            return groupSection;
        }
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
    protected FieldDescriptor addField(Section section, String fieldKey, MessageKeyInfo messageKey) {
    	return addField(section, fieldKey, messageKey, null, null);
    }
    protected FieldDescriptor addField(Section section, String fieldKey, MessageKeyInfo messageKey, Widget widget) {
    	return addField(section, fieldKey, messageKey, widget, null);
    }
    protected FieldDescriptor addField(Section section, String fieldKey, MessageKeyInfo messageKey, String parentPath) {
        return addField(section, fieldKey, messageKey, null, parentPath);
    }
    protected FieldDescriptor addField(Section section, String fieldKey, MessageKeyInfo messageKey, Widget widget, String parentPath) {
        QueryPath path = QueryPath.concat(parentPath, fieldKey);
    	Metadata meta = modelDefinition.getMetadata(path);

    	FieldDescriptor fd = new FieldDescriptor(path.toString(), messageKey, meta);
    	if (widget != null) {
    		fd.setFieldWidget(widget);
    	}
    	section.addField(fd);
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
    
    //FIXME:  Make this more generic and reusable for other fields
    public class ViewTable extends FlexTable{

    	private int col = 0;
    	private int row = 0;
    	
		{
			setStyleName("KS-ViewCourseFlexTable");
		}
    	   	
    	public void nextRow() {
    		row++;
    		col=0;
    	}
    	
    	public void addHeaderField(String fieldValue){
    		setCellText(row, col, fieldValue);
			getCellFormatter().addStyleName(row, col++, "KS-ViewCourseFlexTableHeaderCell");  			
    	}
    	
    	public void addField( String fieldValue){
    		setCellText(row, col++, fieldValue);
    	}

		private void setCellText(int row, int cell, String fieldValue) {
			setText(row, cell, fieldValue);
    		getCellFormatter().addStyleName(row, cell, "KS-ViewCourseFlexTableCell");
		}
    	    	
		private void initTable() {
			clear();
			while(getRowCount()>0){
				removeRow(0);
			}
			row=0;
			col=0;
		}
    }

    //FIXME:  Make this more generic and reusable for other fields
    public class LoTableBinding implements ModelWidgetBinding<ViewTable>{
    	
    	private final String loDescPath = QueryPath.concat(INCLUDED_SINGLE_USE_LO, SingleUseLoConstants.DESCRIPTION, PLAIN).toString();
    	private final String loCategoriesPath = QueryPath.concat(INCLUDED_SINGLE_USE_LO, CATEGORIES).toString();

    	@Override
    	public void setModelValue(ViewTable table, DataModel model,
    			String path) {
    			// shouldn't ever need this
    	}
    	@Override
    	public void setWidgetValue(ViewTable table, DataModel model,
    			String path) {

    		table.initTable();
    		Object value = model.getRoot().query(path);
    		if (value != null && value instanceof Data){
    			Iterator<Data.Property> iter1 = ((Data)value).iterator();
    			if (iter1.hasNext()) {
    				table.addHeaderField(" ");
    				table.addHeaderField("Categories");
    				table.nextRow();

    				// iterate through Los
    				while(iter1.hasNext()){
    					Data.Property prop = iter1.next();
    					Data loData = prop.getValue();
    					if (loData != null) {
    						table.addField(loData.query(loDescPath).toString().trim());
    						Data categoryData = loData.query(loCategoriesPath);
    						table.addField(buildLOCategories(categoryData));  
    						table.nextRow();
    					}
    				}   
    			}					 
    		}
    	}
    	
    	private String buildLOCategories(Data categoryData) {

    		StringBuilder sb = new StringBuilder();

    		if(categoryData!=null && categoryData.size()>0){
    			Iterator<Data.Property> iter = categoryData.iterator();
    			while(iter.hasNext()){
    				Data.Property p = iter.next();
    				Data d = p.getValue();
    				sb.append(d.get(NAME)).append(", ");
    			}
    			sb.deleteCharAt(sb.lastIndexOf(", "));
    		}
    		return sb.toString();
    	}
    }
}