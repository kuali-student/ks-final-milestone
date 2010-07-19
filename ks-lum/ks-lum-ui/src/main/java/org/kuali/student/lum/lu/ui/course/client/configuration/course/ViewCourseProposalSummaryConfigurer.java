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

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBinding;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.MetadataInterrogator;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.lum.lu.assembly.data.client.LuData;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.base.AcademicSubjectOrgInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.base.MetaInfoConstants;
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
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseProposalConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseProposalInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseRevenueInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.FeeInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.LearningObjectiveConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.SingleUseLoChildSingleUseLosConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.SingleUseLoConstants;
import org.kuali.student.lum.lu.dto.workflow.WorkflowPersonInfo;
import org.kuali.student.lum.lu.ui.course.client.configuration.LUConstants;
import org.kuali.student.lum.lu.ui.course.client.configuration.course.CourseConfigurer.CourseSections;
import org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcServiceAsync;
import org.kuali.student.lum.lu.ui.course.client.service.WorkflowToolRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.WorkflowToolRpcServiceAsync;
import org.kuali.student.lum.ui.requirements.client.model.RuleInfo;
import org.kuali.student.lum.ui.requirements.client.view.RuleConstants;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;

/**
 * Displays a Table Summary view of the CluProposal Model Data
 */

/*
 * These styles will work
.TableIndent-0{font-weight:bold;}
.TableIndent-1{text-indent:10px;}
.TableIndent-2{text-indent:20px;}
.TableIndent-3{text-indent:30px;}
.TableIndent-4{text-indent:40px;}
.TableIndent-5{text-indent:50px;}
.TableIndent-6{text-indent:60px;}
.TableIndent-7{text-indent:70px;}
.TableIndent-8{text-indent:80px;}
.SummaryTable-EvenRow{background-color:  #FAFAFA;}
.SummaryTable-OddRow{background-color: #F0F0F0;}
*/

public class ViewCourseProposalSummaryConfigurer implements
		CreditCourseProposalConstants, CreditCourseProposalInfoConstants,
		CreditCourseConstants, CreditCourseFormatConstants,
		CreditCourseActivityConstants, MetaInfoConstants,
		CreditCourseDurationConstants, FeeInfoConstants,
		LearningObjectiveConstants, AcademicSubjectOrgInfoConstants,
		AffiliatedOrgInfoConstants, CreditCourseRevenueInfoConstants,
		CreditCourseExpenditureInfoConstants {
	
	protected String type = "course";
    protected String state = "draft";
    protected String groupName;
    protected DataModelDefinition modelDefinition;
    //Some of the lists in the course configurer are not proper enums so they must be hard oded lookups
    protected HashMap<String,String> hardCodedTranslationMap;
    protected HashMap<String,String> pathToLabelTranslationMap;
    
    private WorkflowToolRpcServiceAsync workflowRpcServiceAsync = GWT.create(WorkflowToolRpcService.class);
    CreditCourseProposalRpcServiceAsync cluProposalRpcServiceAsync = GWT.create(CreditCourseProposalRpcService.class);
    
	//Override paths for course and proposal so they are root
	public static final String PROPOSAL = "";
	public static final String COURSE = "";
    
    public ViewCourseProposalSummaryConfigurer(String type, String state,
			String groupName, DataModelDefinition modelDefinition) {
		super();
		this.type = type;
		this.state = state;
		this.groupName = groupName;
		this.modelDefinition = modelDefinition;
		
    	hardCodedTranslationMap = new HashMap<String,String>();
    	hardCodedTranslationMap.put("kuali.enum.type.feeTypes.labFee", getLabel(LUConstants.LAB_FEE));
    	hardCodedTranslationMap.put("kuali.enum.type.feeTypes.materialFee",  getLabel(LUConstants.MATERIAL_FEE));
    	hardCodedTranslationMap.put("kuali.enum.type.feeTypes.studioFee", getLabel(LUConstants.STUDIO_FEE));
    	hardCodedTranslationMap.put("kuali.enum.type.feeTypes.fieldTripFee", getLabel(LUConstants.FIELD_TRIP_FEE));
    	hardCodedTranslationMap.put("kuali.enum.type.feeTypes.fieldStudyFee", getLabel(LUConstants.FIELD_STUDY_FEE));
    	hardCodedTranslationMap.put("kuali.enum.type.feeTypes.administrativeFee", getLabel(LUConstants.ADMINISTRATIVE_FEE));
    	hardCodedTranslationMap.put("kuali.enum.type.feeTypes.coopFee", getLabel(LUConstants.COOP_FEE));
    	hardCodedTranslationMap.put("kuali.enum.type.feeTypes.greensFee",  getLabel(LUConstants.GREENS_FEE));
    	hardCodedTranslationMap.put("variableRateFee", getLabel(LUConstants.VARIABLE_RATE));
    	hardCodedTranslationMap.put("fixedRateFee", getLabel(LUConstants.FIXED_RATE));
    	hardCodedTranslationMap.put("multipleRateFee", getLabel(LUConstants.MULTIPLE_RATE));
    	hardCodedTranslationMap.put("perCreditFee", getLabel(LUConstants.PER_CREDIT_RATE));

    	pathToLabelTranslationMap = new HashMap<String,String>();
    	pathToLabelTranslationMap.put("justification", getLabel(LUConstants.JUSTIFICATION_FEE));
    	pathToLabelTranslationMap.put("variableRateFee", getLabel(LUConstants.VARIABLE_RATE));
    	pathToLabelTranslationMap.put("fixedRateFee", getLabel(LUConstants.FIXED_RATE));
    	pathToLabelTranslationMap.put("multipleRateFee", getLabel(LUConstants.MULTIPLE_RATE));
    	pathToLabelTranslationMap.put("perCreditFee", getLabel(LUConstants.PER_CREDIT_RATE));
    	pathToLabelTranslationMap.put("outcomeType", getLabel(LUConstants.LEARNING_RESULT_OUTCOME_TYPE_LABEL_KEY));
    	pathToLabelTranslationMap.put("amount", getLabel(LUConstants.AMOUNT));
    	pathToLabelTranslationMap.put("feeType", "Fee Type");
    	pathToLabelTranslationMap.put("rateType", "Rate Type");
    	pathToLabelTranslationMap.put("minAmount", "Amount");
    	pathToLabelTranslationMap.put("maxAmount", "To");
	}

	public VerticalSectionView generateSummarySection(){
    	
        VerticalSectionView summaryTableSection = new VerticalSectionView(CourseSections.SUMMARY, getLabel(LUConstants.SUMMARY_LABEL_KEY), CourseConfigurer.CLU_PROPOSAL_MODEL);
        summaryTableSection.addStyleName(LUConstants.STYLE_SECTION);
            	
    	QueryPath path = QueryPath.concat(null, null);
    	Metadata meta = modelDefinition.getMetadata(path);

    	FieldDescriptor fd = new FieldDescriptor(path.toString(), null, meta);
        fd.setWidgetBinding(new SummaryTableBinding());
        fd.setFieldWidget(new SummaryTable());
   		summaryTableSection.addField(fd);
    	
    	return summaryTableSection;
    }
    
    protected String getLabel(String labelKey) {
    	String label = Application.getApplicationContext().getUILabel(groupName, type, state, labelKey);
    	//If this label ends up the same as the key (no label was found) try the translation map instead
    	if(label!=null && label.equals(labelKey) && pathToLabelTranslationMap.containsKey(labelKey)){
    		label = pathToLabelTranslationMap.get(labelKey);
    	}
    	return label;
    }

    
    public class SummaryTableBinding implements ModelWidgetBinding<SummaryTable>{
		@Override
		public void setModelValue(SummaryTable summaryTable, DataModel model,
				String path) {
		}
		@Override
		public void setWidgetValue(SummaryTable summaryTable, DataModel model,
				String path) {
			if(!summaryTable.isPopulated()){
				
				//Summary Brief
				summaryTable.addField(getLabel(LUConstants.BRIEF_LABEL_KEY), 0);
				summaryTable.addField(LUConstants.TITLE_LABEL_KEY, COURSE + "/" + COURSE_TITLE, model, 1);
				summaryTable.addField(LUConstants.DIVISION_LABEL_KEY, COURSE + "/" + SUBJECT_AREA, model, 1);
				summaryTable.addField(LUConstants.COURSE_NUMBER_LABEL_KEY, COURSE + "/" + COURSE_NUMBER_SUFFIX, model, 1);
				summaryTable.addField(LUConstants.CREATED_DATE_LABEL_KEY, PROPOSAL + "/" + META_INFO + "/" + CREATE_TIME, model, 1);
				summaryTable.addField(LUConstants.LAST_CHANGED_DATE_LABEL_KEY, PROPOSAL + "/" + META_INFO + "/" + UPDATE_TIME, model, 1);

				//Authors and rationale
				summaryTable.addField(LUConstants.PROPOSAL_TITLE_LABEL_KEY, PROPOSAL + "/" + TITLE, model, 1);
				summaryTable.addField(LUConstants.PROPOSAL_RATIONALE_LABEL_KEY, PROPOSAL + "/" + RATIONALE, model, 1);
				summaryTable.addField(LUConstants.PROPOSAL_PERSON_LABEL_KEY, PROPOSAL + "/" + META_INFO + "/" + CREATE_ID, model, 1);
				
				//Governance
				summaryTable.addField(LUConstants.GOVERNANCE_LABEL_KEY, 0);
				summaryTable.addField(LUConstants.ACADEMIC_SUBJECT_ORGS_KEY, COURSE + "/" + ACADEMIC_SUBJECT_ORGS, model, 1);
				summaryTable.addField(LUConstants.CAMPUS_LOCATION_LABEL_KEY, COURSE + "/" + CAMPUS_LOCATIONS, model, 1);
				summaryTable.addField(LUConstants.ADMIN_ORG_LABEL_KEY, COURSE + "/" + DEPARTMENT, model, 1);
				
				//Course Logistics
				summaryTable.addField(LUConstants.LOGISTICS_LABEL_KEY, 0);
				summaryTable.addField(LUConstants.FIRST_OFFERING_KEY, COURSE + "/" + FIRST_EXPECTED_OFFERING, model, 1);
				summaryTable.addField(LUConstants.INSTRUCTOR_LABEL_KEY, COURSE + "/" + PRIMARY_INSTRUCTOR, model, 1);
				summaryTable.addField(LUConstants.SCHEDULING_LABEL_KEY, 1);
				summaryTable.addField(LUConstants.DURATION_LITERAL_LABEL_KEY, COURSE + "/" + CreditCourseConstants.DURATION + "/" + QUANTITY, model, 2);
		        summaryTable.addField(LUConstants.DURATION_TYPE_LABEL_KEY, COURSE + "/" + CreditCourseConstants.DURATION + "/" + TERM_TYPE, model, 2);
		        summaryTable.addField(LUConstants.LEARNING_RESULTS_LABEL_KEY, 1);
		        summaryTable.addField(LUConstants.LEARNING_RESULT_ASSESSMENT_SCALE_LABEL_KEY, COURSE + "/" + CreditCourseConstants.GRADING_OPTIONS, model, 2);
		        summaryTable.addField(LUConstants.LEARNING_RESULT_OUTCOME_LABEL_KEY, COURSE + "/" + CreditCourseConstants.OUTCOME_OPTIONS, model, 2);
		        summaryTable.addField(LUConstants.FORMATS_LABEL_KEY, 1);
		        populateCourseFormats(model, summaryTable);

		        //Course Information
		        summaryTable.addField(LUConstants.INFORMATION_LABEL_KEY, 0);
				summaryTable.addField(LUConstants.DIVISION_LABEL_KEY, COURSE + "/" + SUBJECT_AREA, model, 1);
				summaryTable.addField(LUConstants.SUFFIX_CODE_LABEL_KEY, COURSE + "/" + COURSE_NUMBER_SUFFIX, model, 1);
				populateCrossListed(model, summaryTable); 
				populateJointOfferings(model, summaryTable); 
				populateVersionCodes(model, summaryTable); 
				summaryTable.addField(LUConstants.SHORT_TITLE_LABEL_KEY, COURSE + "/" + TRANSCRIPT_TITLE, model, 1);
				summaryTable.addField(LUConstants.TITLE_LABEL_KEY, COURSE + "/" + COURSE_TITLE, model, 1);
				summaryTable.addField(LUConstants.DESCRIPTION_LABEL_KEY, COURSE + "/" + DESCRIPTION, model, 1);
				
				//Learning Objectives
		        summaryTable.addField(LUConstants.LEARNING_OBJECTIVE_LABEL_KEY, 0);
		        populateLearningObjectives((Data)model.get(COURSE + "/" + COURSE_SPECIFIC_LOS), summaryTable, 1);
		        
		        //CourseRequisites
		        /*
		        summaryTable.addField(LUConstants.REQUISITES_LABEL_KEY, 0);
		        populateCourseRequisites(model, summaryTable, 1);
		        */
		        
		        //Active Dates
		        summaryTable.addField(LUConstants.ACTIVE_DATES_LABEL_KEY, 0);
				summaryTable.addField("First Expected Offering", COURSE + "/" + FIRST_EXPECTED_OFFERING, model, 1);
				summaryTable.addField(LUConstants.START_DATE_LABEL_KEY, COURSE + "/" + CreditCourseConstants.EFFECTIVE_DATE, model, 1);
				summaryTable.addField(LUConstants.END_DATE_LABEL_KEY, COURSE + "/" + EXPIRATION_DATE, model, 1);
				
				//Financials
				/*
				summaryTable.addField(LUConstants.FINANCIALS_LABEL_KEY, COURSE + "/" + FEES, model, 0);
				summaryTable.addField(LUConstants.FINANCIAL_INFORMATION, 1);
				populateFinancialRevenue(model, summaryTable, 2);
				populateFinancialExpenditure(model, summaryTable, 2);
				*/
				//summaryTable.addField(LUConstants.REVENUE, COURSE + "/" + REVENUE_INFO + "/" + REVENUE_ORG, model, 2);
				//summaryTable.addField(LUConstants.EXPENDITURE, COURSE + "/" + EXPENDITURE_INFO + "/" + EXPENDITURE_ORG, model, 2);
				
				//People & Permissions
				/*
				summaryTable.addField(LUConstants.SECTION_AUTHORS_AND_COLLABORATORS, 0);
				populatePeopleAndPermissions(model, summaryTable, 0);
				*/
				
				summaryTable.setPopulated(true);	        
	        }

		}

	}
	private void populateFinancialExpenditure(DataModel model,
			SummaryTable summaryTable, int indent) {
		Data expenditureData = model.get(COURSE + "/" + EXPENDITURE_INFO + "/" + EXPENDITURE_ORG);
        if(expenditureData!=null){
        	Iterator<Data.Property> expenditureIter = expenditureData.realPropertyIterator();
        	while(expenditureIter.hasNext()){
        		Data.Property expenditureProp = expenditureIter.next();
        		if(expenditureProp.getValue() != null && expenditureProp.getValue() instanceof Data && expenditureProp.getKey() instanceof Integer){
        			
        			summaryTable.addField(getLabel(LUConstants.EXPENDITURE) + " " + ((Integer)expenditureProp.getKey()+1), indent);
              		
        			String expenditurePath = COURSE + "/" + EXPENDITURE_INFO + "/" + EXPENDITURE_ORG + "/" + expenditureProp.getKey().toString() + "/";
        			
        			summaryTable.addField(LUConstants.EXPENDITURE, expenditurePath + AffiliatedOrgInfoConstants.ORG_ID, model, indent + 1);
        			summaryTable.addField(LUConstants.AMOUNT, expenditurePath + PERCENTAGE, model, indent + 1);
        		}
        	}
        }
	}
	private void populateFinancialRevenue(DataModel model,
			SummaryTable summaryTable, int indent) {
		Data revenueData = model.get(COURSE + "/" + REVENUE_INFO + "/" + REVENUE_ORG);
        if(revenueData!=null){
        	Iterator<Data.Property> revenueIter = revenueData.realPropertyIterator();
        	while(revenueIter.hasNext()){
        		Data.Property revenueProp = revenueIter.next();
        		if(revenueProp.getValue() != null && revenueProp.getValue() instanceof Data && revenueProp.getKey() instanceof Integer){
        			
        			summaryTable.addField(getLabel(LUConstants.REVENUE) + " " + ((Integer)revenueProp.getKey()+1), indent);
              		
        			String revenuePath = COURSE + "/" + REVENUE_INFO + "/" + REVENUE_ORG + "/" + revenueProp.getKey().toString() + "/";
        			
        			summaryTable.addField(LUConstants.REVENUE, revenuePath + AffiliatedOrgInfoConstants.ORG_ID, model, indent + 1);
        			summaryTable.addField(LUConstants.AMOUNT, revenuePath + PERCENTAGE, model, indent + 1);
        		}
        	}
        }
	}	
    public void populateCourseRequisites(DataModel model, SummaryTable summaryTable, int indent) {
		LuData data = (LuData)model.getRoot();
		List<RuleInfo> ruleInfos = data.getRuleInfos();
		RuleInfo ruleInfo;
		
		//ensure sequence of rule types based on rule summary screen
		if ((ruleInfo = getRuleInfo(ruleInfos, RuleConstants.KS_STATEMENT_TYPE_ENROLLREQ)) != null) {
		    summaryTable.addField(LUConstants.EREQS_LABEL_KEY, ruleInfo.getNaturalLanguageForRuleEdit(), indent);
		}
        if ((ruleInfo = getRuleInfo(ruleInfos, RuleConstants.KS_STATEMENT_TYPE_PREREQ)) != null) {
            summaryTable.addField(LUConstants.PREQS_LABEL_KEY, ruleInfo.getNaturalLanguageForRuleEdit(), indent);
        }
        if ((ruleInfo = getRuleInfo(ruleInfos, RuleConstants.KS_STATEMENT_TYPE_COREQ)) != null) {
            summaryTable.addField(LUConstants.CREQS_LABEL_KEY, ruleInfo.getNaturalLanguageForRuleEdit(), indent);
        }
        if ((ruleInfo = getRuleInfo(ruleInfos, RuleConstants.KS_STATEMENT_TYPE_ANTIREQ)) != null) {
            summaryTable.addField(LUConstants.AREQS_LABEL_KEY, ruleInfo.getNaturalLanguageForRuleEdit(), indent);
        }        				
	}
    
    private RuleInfo getRuleInfo(List<RuleInfo> ruleInfos, String luStatementTypeKey) {   
        if ((ruleInfos != null) && !ruleInfos.isEmpty()) {
            for (RuleInfo ruleInfo : ruleInfos) {
                if (ruleInfo.getSelectedStatementType() != null &&
                        ruleInfo.getSelectedStatementType().equals(luStatementTypeKey)) {                
                    return ruleInfo;
                }                
            }
        }     
        return null;
    }
	
	public void populateCourseFormats(DataModel model, SummaryTable summaryTable) {
        Data formatsData = model.get(COURSE + "/" + FORMATS);
        if(formatsData!=null){
        	Iterator<Data.Property> formatIter = formatsData.realPropertyIterator();
        	while(formatIter.hasNext()){
        		Data.Property formatProp = formatIter.next();
        		if(formatProp.getValue() != null && formatProp.getValue() instanceof Data){
        			if(formatProp.getKey() instanceof Integer){
        				summaryTable.addField(getLabel(LUConstants.FORMAT_LABEL_KEY) + " " + ((Integer)formatProp.getKey()+1), 2);
        			}else{
        				summaryTable.addField(getLabel(LUConstants.FORMAT_LABEL_KEY) + " " + formatProp.getKey(), 2);
        			}
        			Data activitiesData = ((Data)formatProp.getValue()).get(ACTIVITIES);
        			if(activitiesData != null){
        				Iterator<Data.Property> activityIter = activitiesData.realPropertyIterator();
        				while(activityIter.hasNext()){
        					Data.Property activityProp = activityIter.next();
        					if(activityProp.getKey() instanceof Integer){
        						summaryTable.addField(getLabel(LUConstants.ACTIVITY_LITERAL_LABEL_KEY) + " " + ((Integer)activityProp.getKey()+1), 3);
        					}else{
        						summaryTable.addField(getLabel(LUConstants.ACTIVITY_LITERAL_LABEL_KEY) + " " + activityProp.getKey(), 3);
        					}
        					String activityPath = COURSE + "/" + FORMATS+ "/" + formatProp.getKey().toString() + "/" + ACTIVITIES + "/" + activityProp.getKey().toString() + "/";
        					
        					summaryTable.addField(LUConstants.ACTIVITY_TYPE_LABEL_KEY, activityPath + ACTIVITY_TYPE, model, 4);
        					summaryTable.addField(LUConstants.DURATION_LITERAL_LABEL_KEY, activityPath + CreditCourseActivityConstants.DURATION + "/" + CreditCourseActivityDurationConstants.QUANTITY, model, 4);
        					summaryTable.addField(LUConstants.DURATION_TYPE_LABEL_KEY, activityPath + CreditCourseActivityConstants.DURATION + "/" + CreditCourseActivityDurationConstants.TIME_UNIT, model, 4);
        					summaryTable.addField(LUConstants.CONTACT_HOURS_LABEL_KEY, activityPath + CONTACT_HOURS + "/" + CreditCourseActivityContactHoursConstants.HRS, model, 4);
        					summaryTable.addField(CreditCourseActivityContactHoursConstants.PER, activityPath + CONTACT_HOURS + "/" + CreditCourseActivityContactHoursConstants.PER, model, 4);
        					summaryTable.addField(LUConstants.CLASS_SIZE_LABEL_KEY, activityPath + DEFAULT_ENROLLMENT_ESTIMATE, model, 4);
        				}
        			}
        		}
        	}
        }
    }
	
	public void populateLearningObjectives(Data data, SummaryTable summaryTable, int nestLevel) {

		if(data==null){
			return;
		}
		Iterator<Data.Property> iter = data.realPropertyIterator();
		while(iter.hasNext()){
			Data.Property prop = iter.next();
			if(prop.getValue() instanceof Data){
				Data propData = (Data)prop.getValue();
				if(Integer.class.equals(prop.getKeyType())||SingleUseLoConstants.CHILD_SINGLE_USE_LOS.equals(prop.getKey())){
					populateLearningObjectives(propData, summaryTable, nestLevel+1);
				}else if(CreditCourseCourseSpecificLOsConstants.INCLUDED_SINGLE_USE_LO.equals(prop.getKey())||
						SingleUseLoChildSingleUseLosConstants.CHILD_LO.equals(prop.getKey())){
					//Do display
					String loString = propData.query(SingleUseLoConstants.DESCRIPTION+"/"+"plain");
					for(int i=0;i<nestLevel;i++){
						loString = "-"+loString;//TODO unsure on how styling work
					}
					Data categoryData = propData.get(SingleUseLoConstants.CATEGORIES);
					if(categoryData!=null && categoryData.size()>0){
						loString+=" (";
						Iterator<Data.Property> catIter = categoryData.realPropertyIterator();
						while(catIter.hasNext()){
							Data.Property category = catIter.next();
							if(category.getValue()!=null){
								String categoryDesc = ((Data)category.getValue()).get(SingleUseLoConstants.NAME);
								loString+=categoryDesc;
								if(catIter.hasNext()){
									loString+=", ";
								}
							}
						}
						loString+=")";
					}
					summaryTable.addField("", loString, nestLevel);
					
					if(propData.get(SingleUseLoConstants.CHILD_SINGLE_USE_LOS)!=null){
						//Parse more here
						populateLearningObjectives((Data)propData.get(SingleUseLoConstants.CHILD_SINGLE_USE_LOS), summaryTable, nestLevel+1);
					}
				}
			}
		}
	}

	public void populateVersionCodes(DataModel model, SummaryTable summaryTable) {
		summaryTable.addField(LUConstants.VERSION_CODES_LABEL_KEY, 1);

        Data versionCodesData = model.get(COURSE + "/" + VERSIONS);
        if(versionCodesData!=null){
        	Iterator<Data.Property> versionCodesIter = versionCodesData.realPropertyIterator();
        	while(versionCodesIter.hasNext()){
        		Data.Property versionCodeProp = versionCodesIter.next();
        		if(versionCodeProp.getValue() != null && versionCodeProp.getValue() instanceof Data){
	        		summaryTable.addField(getLabel(LUConstants.VERSION_CODE_LABEL_KEY) + " " + ((Integer)versionCodeProp.getKey()+1), 2);

        			String versionCodePath = COURSE + "/" + VERSIONS + "/" + versionCodeProp.getKey().toString() + "/";
        			summaryTable.addField(LUConstants.CODE_LABEL_KEY, versionCodePath + "versionCode", model, 3);
        			summaryTable.addField(LUConstants.TITLE_LITERAL_LABEL_KEY, versionCodePath + "versionTitle", model, 3);
        		}
        	}
        }
	}


	public void populateJointOfferings(DataModel model,
			SummaryTable summaryTable) {
		summaryTable.addField(LUConstants.JOINT_OFFERINGS_ALT_LABEL_KEY, 1);

        Data jointsData = model.get(COURSE + "/" + JOINTS);
        if(jointsData!=null){
        	Iterator<Data.Property> jointsIter = jointsData.realPropertyIterator();
        	while(jointsIter.hasNext()){
        		Data.Property jointProp = jointsIter.next();
        		if(jointProp.getValue() != null && jointProp.getValue() instanceof Data){
	        		summaryTable.addField(getLabel(LUConstants.JOINT_OFFER_ITEM_LABEL_KEY) + " " + ((Integer)jointProp.getKey()+1), 2);

        			String jointPath = COURSE + "/" + JOINTS + "/" + jointProp.getKey().toString() + "/";
        			summaryTable.addField(LUConstants.COURSE_NUMBER_OR_TITLE_LABEL_KEY, jointPath + CreditCourseJointsConstants.COURSE_ID, model, 3);
        		}
        	}
        }
	}


	public void populatePeopleAndPermissions(DataModel model, final SummaryTable summaryTable, final int nestLevel){
		String proposalId = model.get(PROPOSAL + "/" + "id");
		if(proposalId!=null){
			cluProposalRpcServiceAsync.getWorkflowIdFromDataId(proposalId, new AsyncCallback<String>(){
				public void onFailure(Throwable caught) {
					Window.alert("Getting Workflow Id failed");
				}
				public void onSuccess(String workflowId) {
					workflowRpcServiceAsync.getCollaborators(workflowId, new AsyncCallback<List<WorkflowPersonInfo>>(){
						public void onFailure(Throwable caught) {
							Window.alert("Getting Collaborators failed");
						}
						public void onSuccess(List<WorkflowPersonInfo> result) {
							for(WorkflowPersonInfo person: result){
								String nameString = "";
								if(person.getFirstName() != null && person.getLastName() != null){
									nameString = person.getFirstName() + " " + person.getLastName();
								}
								else{
									nameString = person.getPrincipalId();
								}
								
								StringBuffer permString = new StringBuffer("");
								int count = 0;
								for(String perm: person.getPermList()){
									if(perm != null){
										if(count > 0){
											permString.append(", " + perm);
										}
										else{
											permString.append(perm);
										}
										count++;
									}
								}
								
								StringBuffer actionString = new StringBuffer("");
								count = 0;
								for(String action: person.getActionList()){
									if(action != null){
										if(count > 0){
											actionString.append(", " + action);
										}
										else{
											actionString.append(action);
										}
										count++;
									}
								}
								
								if(summaryTable!=null){
									summaryTable.addField("Name",nameString, nestLevel+1);
									summaryTable.addField("Permissions", permString.toString(), nestLevel+2);
									summaryTable.addField("Workflow Action", actionString.toString(), nestLevel+2);
								}
							}
						}
					});
				}
			});
		}
	}
	
	public void populateCrossListed(DataModel model, SummaryTable summaryTable) {
		summaryTable.addField(LUConstants.CROSS_LISTED_ALT_LABEL_KEY, 1);

        Data crossListingsData = model.get(COURSE + "/" + CROSS_LISTINGS);
        if(crossListingsData!=null){
        	Iterator<Data.Property> crossListingsIter = crossListingsData.realPropertyIterator();
        	while(crossListingsIter.hasNext()){
        		Data.Property crossListingProp = crossListingsIter.next();
        		if(crossListingProp.getValue() != null && crossListingProp.getValue() instanceof Data){
	        		summaryTable.addField(getLabel(LUConstants.CROSS_LISTED_ITEM_LABEL_KEY) + " " + ((Integer)crossListingProp.getKey()+1), 2);

        			String crossListingPath = COURSE + "/" + CROSS_LISTINGS + "/" + crossListingProp.getKey().toString() + "/";
        			summaryTable.addField(LUConstants.DEPT_LABEL_KEY, crossListingPath + DEPARTMENT, model, 3);
        			summaryTable.addField(LUConstants.SUBJECT_CODE_LABEL_KEY, crossListingPath + SUBJECT_AREA, model, 3);
        			summaryTable.addField(LUConstants.COURSE_NUMBER_LABEL_KEY, crossListingPath + COURSE_NUMBER_SUFFIX, model, 3);        			
        		}
        	}
        }
	}

	public class SummaryTable extends FlexTable{
		private boolean populated;
		{
			setStyleName("SummaryTable");
		}
		public void addField(String fieldLabel, int indent){
			addField(fieldLabel, null, indent);
		}
		
		public void addField(String labelKey, String path, DataModel model,
				int indent) {
			QueryPath qPath = QueryPath.parse(path);
			Metadata metadata = model.getMetadata(qPath);
			Object value = model.getRoot().query(path);
			if(value instanceof String){
				String stringValue = (String)value; 
				if(metadata!=null&&metadata.getInitialLookup()!=null){
					//Do translation here
            		QueryPath translationPath = qPath.subPath(0, qPath.size()-1);
        	        translationPath.add(new Data.StringKey("_runtimeData"));
        	        translationPath.add(new Data.StringKey((String)qPath.get(qPath.size() - 1).get()));
        	        translationPath.add(new Data.StringKey("id-translation"));
        	        
        	        String translation = model.get(translationPath.toString());
        	        if(translation != null){
        	        	stringValue = translation;
        	        }else if(hardCodedTranslationMap.containsKey(stringValue)){
        	        	stringValue=hardCodedTranslationMap.get(stringValue);
        	        }
				}
				addField(labelKey, stringValue, indent);
			}else if(value instanceof Data){
				//Add the header value 
				if(labelKey!=null){
					addField(labelKey, indent);
				}else{
					indent--;
					if(indent<0){
						indent=0;
					}
				}
				
				//Parse through each value
				Iterator<Data.Property> propIter = ((Data)value).iterator();
				while(propIter.hasNext()){
					Data.Property prop = propIter.next();
                    if(!"_runtimeData".equals(prop.getKey())&&!"id".equals(prop.getKey())){
						String propertyPath = path+"/"+prop.getKey();
						String propertyLabel=null;
						Metadata propertyMetadata = model.getMetadata(QueryPath.parse(propertyPath));
						if(propertyMetadata != null){
							propertyLabel = propertyMetadata.getName();
						}
						if(propertyLabel==null){
							if(prop.getKey() instanceof Integer){
								propertyLabel = null;//""+((Integer)prop.getKey() + 1);
							}else{
								propertyLabel = prop.getKey().toString();
							}
						}
						if(MetadataInterrogator.isRepeating(metadata) && 
								prop.getKey() instanceof Integer &&
								(propertyMetadata==null||
										(!Data.DataType.DATA.equals(propertyMetadata.getDataType())&&
										!Data.DataType.LIST.equals(propertyMetadata.getDataType())))){
		                    QueryPath translationPath = new QueryPath();
		                    translationPath.add(new Data.StringKey(qPath.toString()));
		                    translationPath.add(new Data.StringKey("_runtimeData"));
		                    translationPath.add(new Data.IntegerKey((Integer)prop.getKey()));
		                    translationPath.add(new Data.StringKey("id-translation"));
		                    String translation = model.get(translationPath.toString());
		                    
		                    String stringValue = value.toString();
		        	        if(translation != null){
		        	        	stringValue = translation;
		        	        }
		    				addField(propertyLabel, stringValue, indent+1);
						} else {
							addField(propertyLabel,propertyPath,model,indent+1);
						}
                    }
				}
			}else{
				if(value != null){
					if(value instanceof Date){
						addField(labelKey, DateTimeFormat.getShortDateFormat().format((Date) value), indent);
					}else{
						addField(labelKey, value.toString(), indent);
					}
				}else{
					addField(labelKey, null, indent);
				}
			}
			
		}

		public void addField(String fieldLabel, String fieldValue, int indent){
			int rowCount = getRowCount();
	    	setText(rowCount, 0, getLabel(fieldLabel));
			setText(rowCount, 1, fieldValue);
			getCellFormatter().setStyleName(rowCount, 0, "TableIndent-"+indent);
			if(rowCount%2==0){
				getRowFormatter().addStyleName(rowCount, "SummaryTable-EvenRow");
			}else{
				getRowFormatter().addStyleName(rowCount, "SummaryTable-OddRow");
			}
		}

		public void setPopulated(boolean populated) {
			SummaryTable.this.populated = populated;
		}

		public boolean isPopulated() {
			return populated;
		}

		@Override
		protected void onUnload() {
			SummaryTable.this.clear();
			while(SummaryTable.this.getRowCount()>0){
				SummaryTable.this.removeRow(0);
			}
			populated=false;
		}
	
	}

}
