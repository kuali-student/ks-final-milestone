package org.kuali.student.lum.lu.ui.course.client.configuration.course;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBinding;
import org.kuali.student.common.ui.client.configurable.mvc.binding.MultiplicityCompositeBinding;
import org.kuali.student.common.ui.client.configurable.mvc.binding.MultiplicityItemBinding;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityItem;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.UpdatableMultiplicityComposite;
import org.kuali.student.common.ui.client.configurable.mvc.sections.GroupSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.list.KSLabelList;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeHandler;
import org.kuali.student.common.ui.client.widgets.list.impl.SimpleListItems;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.core.assembly.data.Data.Property;
import org.kuali.student.lum.lu.assembly.data.client.LuData;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.AffiliatedOrgInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseActivityConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseActivityContactHoursConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseActivityDurationConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseCourseSpecificLOsConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseJointsConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.FeeInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.FeeInfoHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.SingleUseLoChildSingleUseLosConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.SingleUseLoConstants;
import org.kuali.student.lum.lu.ui.course.client.configuration.LUConstants;
import org.kuali.student.lum.ui.requirements.client.model.RuleInfo;
import org.kuali.student.lum.ui.requirements.client.view.RuleConstants;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Daniel Epstein
 *
 */
public class ViewCourseProposalSummaryConfigurer extends CourseConfigurer {

    
	public ViewCourseProposalSummaryConfigurer(String type, String state,
			String groupName, DataModelDefinition modelDefinition) {
		super();
		this.type = type;
		this.state = state;
		this.groupName = groupName;
		this.modelDefinition = modelDefinition;
	}

    
    public SectionView generateSummarySection(){
        VerticalSectionView section = initSectionView(CourseSections.SUMMARY, LUConstants.SUMMARY_LABEL_KEY);

    	section.enableValidation(false);
        section.addSection(generateSummaryBrief(getH3Title(LUConstants.BRIEF_LABEL_KEY)));
        section.addSection(generateSummaryDetails(getH3Title(LUConstants.FULL_VIEW_LABEL_KEY)));
        return section;
    }

    @Override
	protected VerticalSection generateSummaryBrief(SectionTitle title) {
        VerticalSection section = new VerticalSection(title);
        section.addStyleName(LUConstants.STYLE_SECTION_DIVIDER);
        section.addStyleName(LUConstants.STYLE_SECTION);
 
        addField(section, PROPOSAL + "/" + TITLE, getLabel(LUConstants.TITLE_LABEL_KEY) +":    ", new KSLabel());
        addField(section, COURSE + "/" + SUBJECT_AREA, getLabel(LUConstants.DIVISION_LABEL_KEY), new KSLabel());
        addField(section, COURSE + "/" + COURSE_NUMBER_SUFFIX, getLabel(LUConstants.SUFFIX_CODE_LABEL_KEY), new KSLabel());


        // FIXME wilj: add proposal/delegate/collab person info to assembly
        //addField(section, PROPOSAL + "/" + PROPOSER_PERSON, getLabel(LUConstants.PROPOSER_LABEL_KEY), new ProposerPersonList());
        //addField(section, "proposalInfo/todo", getLabel(LUConstants.DELEGATE_LABEL_KEY), new KSLabel());
        //addField(section, "proposalInfo/todo", getLabel(LUConstants.COLLABORATORS_LABEL_KEY), new KSLabel());

        addField(section, PROPOSAL + "/" + META_INFO + "/" + CREATE_TIME, getLabel(LUConstants.CREATED_DATE_LABEL_KEY), new KSLabel());
        addField(section, PROPOSAL + "/" + META_INFO + "/" + UPDATE_TIME, getLabel(LUConstants.LAST_CHANGED_DATE_LABEL_KEY), new KSLabel());

        //addField(section, COURSE + "/" + DESCRIPTION + "/" + RichTextInfoConstants.PLAIN, getLabel(LUConstants.DESCRIPTION_LABEL_LABEL_KEY), new KSLabel());
       // TODO: Norm: find out why was this prefixed with proposal there is no state on proposal it is on the main object
        //addField(section, PROPOSAL + "/" + CreditCourseProposalConstants.STATE, getLabel(LUConstants.STATUS_LABEL_KEY), new KSLabel());
        return section;
    }
	
    public VerticalSection generateSummaryDetails(SectionTitle title) {
        VerticalSection section = new VerticalSection(title);
        section.addStyleName(LUConstants.STYLE_SECTION_DIVIDER);
        section.addStyleName(LUConstants.STYLE_SECTION);
        
        SectionView governanceSection = generateGovernanceSection();
        governanceSection.setSectionTitle(getH4Title(LUConstants.GOVERNANCE_LABEL_KEY));
        SectionView courseLogisticsSection = generateCourseLogisticsSection();
        courseLogisticsSection.setSectionTitle(getH4Title(LUConstants.LOGISTICS_LABEL_KEY));
        
        section.addSection(governanceSection);
        section.addSection(courseLogisticsSection);
        
        section.addSection(generateCourseInfoSection());
        section.addSection(generateLearningObjectivesSummarySection());
        section.addSection(generateCourseRequisitesSummarySection());
        section.addSection(generateActiveDatesSection());
        section.addSection(generateFinancialsSection());     
        return section;
    }
    
    protected VerticalSection generateLearningObjectivesSummarySection(){
    	VerticalSection learningObjectivesSummarySection = initSection(getH3Title(LUConstants.LEARNING_OBJECTIVE_LABEL_KEY), WITH_DIVIDER);
    	FieldDescriptor fd = addField(learningObjectivesSummarySection, COURSE+"/"+COURSE_SPECIFIC_LOS);
        fd.setWidgetBinding(new CourseLOsBinding());
        fd.setFieldWidget(new CourseLoPanel());
        learningObjectivesSummarySection.addField(fd);
    	return learningObjectivesSummarySection;
    }
    
    public class CourseLOsBinding implements ModelWidgetBinding<CourseLoPanel>{
		@Override
		public void setModelValue(
				CourseLoPanel widget,
				DataModel model, String path) {
			return;
		}

		@Override
		public void setWidgetValue(
				CourseLoPanel widget,
				DataModel model, String path) {
    		widget.update(model,path);
		}
    }
    public class CourseLoPanel extends Composite{
    	private VerticalPanel rootPanel = new VerticalPanel();
    	public CourseLoPanel(){
    		super();
    		initWidget(rootPanel);
    	}
    	public void parseAndDisplay(Data data, int nestLevel){
    		if(data==null){
    			return;
    		}
    		Iterator<Data.Property> iter = data.realPropertyIterator();
    		while(iter.hasNext()){
    			Data.Property prop = iter.next();
    			if(prop.getValue() instanceof Data){
    				Data propData = (Data)prop.getValue();
    				if(Integer.class.equals(prop.getKeyType())||SingleUseLoConstants.CHILD_SINGLE_USE_LOS.equals(prop.getKey())){
    					parseAndDisplay(propData, nestLevel+1);
    				}else if(CreditCourseCourseSpecificLOsConstants.INCLUDED_SINGLE_USE_LO.equals(prop.getKey())||
    						SingleUseLoChildSingleUseLosConstants.CHILD_LO.equals(prop.getKey())){
    					//Do display
    					String loString = propData.query(SingleUseLoConstants.DESCRIPTION+"/"+"plain");
    					for(int i=0;i<nestLevel;i++){
    						loString = "  "+loString;//TODO unsure on how styling work
    					}
    					Data categoryData = propData.get(SingleUseLoConstants.CATEGORIES);
    					if(categoryData!=null){
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
    					KSLabel label= new KSLabel(loString);
    					rootPanel.add(label);
    					
   						if(propData.get(SingleUseLoConstants.CHILD_SINGLE_USE_LOS)!=null){
    						//Parse more here
    						parseAndDisplay((Data)propData.get(SingleUseLoConstants.CHILD_SINGLE_USE_LOS), nestLevel+1);
   						}
    				}
    			}
    		}
    	}
    	public void update(DataModel model, String path){
    		rootPanel.clear();
			Data data = model.getRoot().query(path);
			parseAndDisplay(data,0);
    	}
    }
    protected VerticalSection generateCourseRequisitesSummarySection(){
    	VerticalSection courseRequisitesSummarySection = initSection(getH3Title(LUConstants.REQUISITES_LABEL_KEY), WITH_DIVIDER);
    	FieldDescriptor fd = addField(courseRequisitesSummarySection, COURSE+"/"+STATEMENTS);
        fd.setWidgetBinding(new CourseRequisitesBinding());
        fd.setFieldWidget(new RequisitePanel());
    	courseRequisitesSummarySection.addField(fd);
    	return courseRequisitesSummarySection;
    }
    
    public class CourseRequisitesBinding implements ModelWidgetBinding<RequisitePanel>{
		@Override
		public void setModelValue(
				RequisitePanel widget,
				DataModel model, String path) {
			return;
		}

		@Override
		public void setWidgetValue(
				RequisitePanel widget,
				DataModel model, String path) {
    		widget.update(model,path);
		}
    }

    
    public class RequisitePanel extends Composite{
    	private VerticalPanel rootPanel = new VerticalPanel();
    	public RequisitePanel(){
    		super();
    		initWidget(rootPanel);
    	}
    	public void update(DataModel model, String path){
			LuData data = (LuData)model.getRoot();
			List<RuleInfo> ruleInfos = data.getRuleInfos();
    		
    		rootPanel.clear();
    		if(ruleInfos!=null){
    			//For every rule info, look up the type and corresponding statement, then add labels to the rootPanel
    			int statementIndex = 0;
    			for(RuleInfo ruleInfo:ruleInfos){
    				String type = ruleInfo.getSelectedStatementType();
    				String typeDisplay="";
    				if(RuleConstants.KS_STATEMENT_TYPE_PREREQ.equals(type)){
    					typeDisplay=LUConstants.PREQS_LABEL_KEY;
    				}else if(RuleConstants.KS_STATEMENT_TYPE_COREQ.equals(type)){
    					typeDisplay=LUConstants.CREQS_LABEL_KEY;
    				}else if(RuleConstants.KS_STATEMENT_TYPE_ENROLLREQ.equals(type)){
    					typeDisplay=LUConstants.EREQS_LABEL_KEY;
    				}else if(RuleConstants.KS_STATEMENT_TYPE_ANTIREQ.equals(type)){
    					typeDisplay=LUConstants.AREQS_LABEL_KEY;
    				}
    				rootPanel.add(getH4Title(typeDisplay));
    				KSLabel rationaleLabel = new KSLabel(ruleInfo.getRationale());
    				rootPanel.add(rationaleLabel);
    				String statement = data.query(path+"/"+statementIndex);
    				KSLabel statementLabel = new KSLabel(statement);
    				rootPanel.add(statementLabel);
    				statementIndex++;
    			}
    		}
    	}
    }
    
    @Override
    protected VerticalSection generateCourseFormatsSection() {
        //COURSE FORMATS
        VerticalSection courseFormats = initSection(getH3Title(LUConstants.FORMATS_LABEL_KEY), WITH_DIVIDER);
        addField(courseFormats, COURSE + "/" + FORMATS, null, new CourseFormatList(COURSE + "/" + FORMATS));
        return courseFormats;
	}
    
    @Override
    protected VerticalSection generateVersionCodesSection() {
        //Version Codes
        VerticalSection versionCodes = new VerticalSection(getH3Title(LUConstants.VERSION_CODES_LABEL_KEY));
        addField(versionCodes, COURSE + "/" + VERSIONS, null, new VersionCodeList(COURSE + "/" + VERSIONS, true));
        //versionCodes.addStyleName("KS-LUM-Section-Divider");
        return versionCodes;
	}

    @Override
    protected VerticalSection generateOfferedJointlySection() {
        // Offered jointly
        VerticalSection offeredJointly = new VerticalSection(getH3Title(LUConstants.JOINT_OFFERINGS_ALT_LABEL_KEY));
        addField(offeredJointly, COURSE + "/" + JOINTS, null, new OfferedJointlyList(COURSE + "/" + JOINTS));
        //offeredJointly.addStyleName("KS-LUM-Section-Divider");
        return offeredJointly;
	}

    @Override
	protected VerticalSection generateCrossListedSection() {
        // Cross-listed
        VerticalSection crossListed = new VerticalSection(getH3Title(LUConstants.CROSS_LISTED_ALT_LABEL_KEY));
        // crossListed.setInstructions("Enter Department and/or Subject Code/Course Number.");
        addField(crossListed, COURSE + "/" + CROSS_LISTINGS, null, new CrossListedList(COURSE + "/" + CROSS_LISTINGS));
        //crossListed.addStyleName("KS-LUM-Section-Divider");
        return crossListed;
	}
    
    @Override
    public SectionView generateCourseLogisticsSection() {
        VerticalSectionView section = initSectionView(CourseSections.COURSE_LOGISTICS, LUConstants.LOGISTICS_LABEL_KEY);

//      instructors.addField(new FieldDescriptor("cluInfo/instructors", null, Type.LIST, new AlternateInstructorList()));
        VerticalSection firstExpectedOfferingSection = initSection(getH3Title("First Expected Offering"), WITH_DIVIDER);
        addField(firstExpectedOfferingSection, COURSE + "/" + FIRST_EXPECTED_OFFERING);
        section.addSection(firstExpectedOfferingSection);
        section.addSection(generateInstructorsSection());
        section.addSection(generateSchedulingSection());
        section.addSection(generateCourseFormatsSection());
        //section.addSection(generateLearningResultsSection()); this section dont work anyways

        return section;
    }
    @Override
    //Sets every field to NON editable
    protected FieldDescriptor addField(Section section, String fieldKey, String fieldLabel, Widget widget, String parentPath) {
        QueryPath path = QueryPath.concat(parentPath, fieldKey);
    	Metadata meta = modelDefinition.getMetadata(path);
    	if(meta!=null){
        	meta.setCanEdit(false);
    	}
    	FieldDescriptor fd = new FieldDescriptor(path.toString(), fieldLabel, meta);
    	if (widget != null) {
    		fd.setFieldWidget(widget);
    	}
    	section.addField(fd);
    	return fd;
    }

   
	public class CourseFormatList extends UpdatableMultiplicityComposite {
    	private final String parentPath;
        public CourseFormatList(String parentPath){
        	super(StyleType.TOP_LEVEL);
        	this.parentPath = parentPath;
            setAddItemLabel(getLabel(LUConstants.COURSE_ADD_FORMAT_LABEL_KEY));
            setItemLabel(getLabel(LUConstants.FORMAT_LABEL_KEY));
            this.readOnly=true;
        }

        public Widget createItem() {
        	VerticalSection item = new VerticalSection();
        addField(item, ACTIVITIES, null, new CourseActivityList(QueryPath.concat(parentPath, String.valueOf(getAddItemKey()), ACTIVITIES).toString()),
        		parentPath + "/" + String.valueOf(getAddItemKey()));
            return item;
        }
    }

    public class CourseActivityList extends UpdatableMultiplicityComposite {

    	private final String parentPath;
        public CourseActivityList(String parentPath){
        	super(StyleType.SUB_LEVEL);
        	this.parentPath = parentPath;
            setAddItemLabel(getLabel(LUConstants.ADD_ACTIVITY_LABEL_KEY));
            setItemLabel(getLabel(LUConstants.ACTIVITY_LITERAL_LABEL_KEY));
            this.readOnly=true;
        }

        public Widget createItem() {
        String path = QueryPath.concat(parentPath, String.valueOf(getAddItemKey())).toString();
            GroupSection activity = new GroupSection();
            addField(activity, ACTIVITY_TYPE, getLabel(LUConstants.ACTIVITY_TYPE_LABEL_KEY), path);
            activity.nextLine();

            /* CreditInfo is deprecated, needs to be replaced with learning results
            activity.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
            activity.addField(new FieldDescriptor("creditInfo", "Credit Value", Type.STRING));
            activity.nextLine();
            activity.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
            activity.addField(new FieldDescriptor("evalType", "Learning Result", Type.STRING));
            activity.nextLine();
            */

            addField(activity, CreditCourseActivityConstants.DURATION + "/" + CreditCourseActivityDurationConstants.QUANTITY, getLabel(LUConstants.DURATION_LITERAL_LABEL_KEY), path);
            addField(activity, CreditCourseActivityConstants.DURATION + "/" + CreditCourseActivityDurationConstants.TIME_UNIT, getLabel(LUConstants.DURATION_TYPE_LABEL_KEY), null, path);

            activity.nextLine();
            addField(activity, CONTACT_HOURS + "/" + CreditCourseActivityContactHoursConstants.HRS, getLabel(LUConstants.CONTACT_HOURS_LABEL_KEY) , path);
            addField(activity, CONTACT_HOURS + "/" + CreditCourseActivityContactHoursConstants.PER, null,  null, path);
            addField(activity, DEFAULT_ENROLLMENT_ESTIMATE, getLabel(LUConstants.CLASS_SIZE_LABEL_KEY), path);

            return activity;
        }

    }

    // FIXME uncomment and fix AlternateAdminOrgList and AlternateInstructorList
//    public class AlternateAdminOrgList extends MultiplicityCompositeWithLabels {
//        {
//            setAddItemLabel("Add an Alternate Admin Organization");
//            setItemLabel("Organization ID ");
//        }
//
//        @Override
//        public Widget createItem() {
//            MultiplicitySection multi = new MultiplicitySection("");
//
//            GroupSection ns = new GroupSection();
//            ns.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
//            ns.addField(new FieldDescriptor("orgId", "Organization ID", Type.STRING, new OrgPicker() ));
//            multi.addSection(ns);
//
//            return multi;
//        }
//    }
//
//    public class AlternateInstructorList extends MultiplicityCompositeWithLabels {
//        {
//            setAddItemLabel("Add an Alternate Instructor.");
//            setItemLabel("Instructor ID");
//        }
//
//        @Override
//        public Widget createItem() {
//            MultiplicitySection multi = new MultiplicitySection("");
//
//            GroupSection ns = new GroupSection();
//            ns.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
//            ns.addField(new FieldDescriptor("personId", "Instructor ID", Type.STRING /*, new InstructorPicker() */ ));
//            multi.addSection(ns);
//
//            return multi;
//        }
//    }

    public class PersonList extends KSDropDown {
        final SimpleListItems people = new SimpleListItems();

        public PersonList() {
            final PersonList us = this;
            final String userId = Application.getApplicationContext().getUserId();
            
            //FIXME: Commented out search code to display drop down with only current user, and disable select            
            people.addItem(userId, userId);
            us.setListItems(people);
            us.selectItem(userId);
            us.setBlankFirstItem(false);
            this.setEnabled(false);
            
            /*   
            SearchRpcServiceAsync searchRpcServiceAsync = GWT.create(SearchRpcService.class);
            SearchRequest searchRequest = new SearchRequest();
            searchRequest.setSearchKey("person.search.personQuickViewByGivenName");
            searchRequest.setSortColumn("person.resultColumn.GivenName");
            searchRequest.setSortDirection(SortDirection.ASC);            
            searchRpcServiceAsync.search(searchRequest, new AsyncCallback<SearchResult>() {

                @Override
                public void onSuccess(SearchResult result) {
                    for (SearchResultRow r : result.getRows()) {
                        people.addItem(r.getCells().get(0).getValue(), r.getCells().get(1).getValue());
                    }
                    us.setListItems(people);
                    us.selectItem(userId);
                }

                @Override
                public void onFailure(Throwable caught) {
                    Window.alert("Unable to contact the SearchService for the list of users");
                    people.addItem(userId, userId);
                    us.setListItems(people);
                    us.selectItem(userId);
                }
            });
            */
        }

    public boolean isMultipleSelect(){
        return true;
    }
}

    public class ProposerPersonList extends KSLabelList {
        public ProposerPersonList(){
            SimpleListItems list = new SimpleListItems();

            super.setListItems(list);
        }
    }
    
    public class OfferedJointlyList extends UpdatableMultiplicityComposite {
        {            
            setAddItemLabel(getLabel(LUConstants.ADD_EXISTING_LABEL_KEY));
            setItemLabel(getLabel(LUConstants.JOINT_OFFER_ITEM_LABEL_KEY));
            //setMinEmptyItems(1);
        }

        private final String parentPath;
        public OfferedJointlyList(String parentPath){
            super(StyleType.TOP_LEVEL);
            this.parentPath = parentPath;
            this.readOnly=true;
        }

/*        @Override
        public MultiplicityItem getItemDecorator(StyleType style) {
            return new RemovableItem();
        }*/

        @Override
        public Widget createItem() {
        String path = QueryPath.concat(parentPath, String.valueOf(getAddItemKey())).toString();
            GroupSection ns = new GroupSection();
            addField(ns, CreditCourseJointsConstants.COURSE_ID, getLabel(LUConstants.COURSE_NUMBER_OR_TITLE_LABEL_KEY), null, path);
            return ns;
        }
    }    

    public class CrossListedList extends UpdatableMultiplicityComposite {
        {
            setAddItemLabel(getLabel(LUConstants.ADD_CROSS_LISTED_LABEL_KEY));
            setItemLabel(getLabel(LUConstants.CROSS_LISTED_ITEM_LABEL_KEY));
        }

        private final String parentPath;
        public CrossListedList(String parentPath){
        	super(StyleType.TOP_LEVEL);
        	this.parentPath = parentPath;
        	this.readOnly=true;
        }

/*        @Override
        public MultiplicityItem getItemDecorator(StyleType style) {
            return new RemovableItem();
        }*/

        @Override
        public Widget createItem() {
    	String path = QueryPath.concat(parentPath, String.valueOf(getAddItemKey())).toString();
            GroupSection ns = new GroupSection();
            addField(ns, DEPARTMENT, getLabel(LUConstants.DEPT_LABEL_KEY), null, path);
            ns.nextLine();
            addField(ns, SUBJECT_AREA, getLabel(LUConstants.SUBJECT_CODE_LABEL_KEY), path);
            addField(ns, COURSE_NUMBER_SUFFIX, getLabel(LUConstants.COURSE_NUMBER_LABEL_KEY), path);

            return ns;
        }
    }

    
    protected SectionView generateFinancialsSection() {
        VerticalSectionView section = initSectionView(CourseSections.FINANCIALS, LUConstants.FINANCIALS_LABEL_KEY);

        VerticalSection justiFee = initSection(getH3Title(LUConstants.COURSE_FEE_TITLE), WITH_DIVIDER);
        addField(justiFee, COURSE + "/" + FEES + "/0/" + JUSTIFICATION,  getLabel(LUConstants.JUSTIFICATION_FEE), new KSLabel()); 
        
        FieldDescriptor feeFD = addField(justiFee, COURSE + "/" + FEES, null, new FeeList(COURSE + "/" + FEES, true));
        feeFD.setWidgetBinding(new FeeListBinding());
        
        section.addSection(justiFee);
        
    VerticalSection financialSection = initSection(getH3Title(LUConstants.FINANCIAL_INFORMATION), WITH_DIVIDER);
        
        FinancialInformationList finInfoList = new FinancialInformationList(COURSE + "/" + REVENUE_INFO + "/" + REVENUE_ORG, true);
        finInfoList.setMinEmptyItems(1);
    addField(financialSection, COURSE + "/" + REVENUE_INFO + "/" + REVENUE_ORG, getLabel(LUConstants.REVENUE) , finInfoList);
        
        ExpenditureList expInfoList = new ExpenditureList(COURSE + "/" + EXPENDITURE_INFO + "/" + EXPENDITURE_ORG, true);
        expInfoList.setMinEmptyItems(1);
        addField(financialSection, COURSE + "/" + EXPENDITURE_INFO + "/" + EXPENDITURE_ORG, getLabel(LUConstants.EXPENDITURE), expInfoList);
        
        section.addSection(financialSection);

        return section;
    }
    
    public class VersionCodeList extends UpdatableMultiplicityComposite {
        {
            setAddItemLabel(getLabel(LUConstants.ADD_VERSION_CODE_LABEL_KEY));
            setItemLabel(getLabel(LUConstants.VERSION_CODE_LABEL_KEY));
        }
        private final String parentPath;
        public VersionCodeList(String parentPath, boolean readOnly){
        	super(StyleType.TOP_LEVEL, readOnly);
        	this.parentPath = parentPath;
        	this.readOnly=true;
        }

/*        @Override
        public MultiplicityItem getItemDecorator(StyleType style) {
            return new RemovableItem();
        }*/

        @Override
        public Widget createItem() {
    	String path = QueryPath.concat(parentPath, String.valueOf(getAddItemKey())).toString();
            GroupSection ns = new GroupSection();
            addField(ns, "versionCode", getLabel(LUConstants.CODE_LABEL_KEY), path);
            addField(ns, "versionTitle", getLabel(LUConstants.TITLE_LITERAL_LABEL_KEY), path);

            return ns;
        }
    }
    public class RateTypeList extends KSLabel{
        HashMap<String,String> types = new HashMap<String,String>();    	
        public RateTypeList(){
            types.put("kuali.enum.type.rateTypes.variableRateFee",getLabel(LUConstants.VARIABLE_RATE));
            types.put("kuali.enum.type.rateTypes.fixedRateFee", getLabel(LUConstants.FIXED_RATE));
            types.put("kuali.enum.type.rateTypes.multipleRateFee",  getLabel(LUConstants.MULTIPLE_RATE));
            types.put("kuali.enum.type.rateTypes.perCreditFee",getLabel(LUConstants.PER_CREDIT_RATE));
        }
		@Override
		public void setText(String text) {
			super.setText(types.get(text));
		}
    }
    public class FeeTypeList extends KSLabel{
    	HashMap<String,String> types = new HashMap<String,String>();    	
        public FeeTypeList(){
            types.put("kuali.enum.type.feeTypes.labFee", getLabel(LUConstants.LAB_FEE));
            types.put("kuali.enum.type.feeTypes.materialFee",  getLabel(LUConstants.MATERIAL_FEE));
            types.put("kuali.enum.type.feeTypes.studioFee", getLabel(LUConstants.STUDIO_FEE));
            types.put("kuali.enum.type.feeTypes.fieldTripFee", getLabel(LUConstants.FIELD_TRIP_FEE));
            types.put("kuali.enum.type.feeTypes.fieldStudyFee", getLabel(LUConstants.FIELD_STUDY_FEE));
            types.put("kuali.enum.type.feeTypes.administrativeFee", getLabel(LUConstants.ADMINISTRATIVE_FEE));
            types.put("kuali.enum.type.feeTypes.coopFee", getLabel(LUConstants.COOP_FEE));
            types.put("kuali.enum.type.feeTypes.greensFee",  getLabel(LUConstants.GREENS_FEE));
        }
		@Override
		public void setText(String text) {
			super.setText(types.get(text));
		}

    }    
    public class FeeList extends UpdatableMultiplicityComposite {
    	
        {
        setAddItemLabel(getLabel(LUConstants.ADD_A_FEE));
        setItemLabel(getLabel(LUConstants.FEE));
        }
        private final String parentPath;

        public FeeList(String parentPath, boolean readOnly){
            super(StyleType.TOP_LEVEL, readOnly);
            this.parentPath = parentPath;
        }
       
        @Override
        public Widget createItem() {
        	// avoid collisions w/ existing fees in the model
        	return new TransmogrifyingFeeRecordItem(parentPath, itemCount);
        }

        public Widget createItem(int itemCount) {
        	return new TransmogrifyingFeeRecordItem(parentPath, itemCount);
        }
        
        @Override
		public MultiplicityItem addItem() {
        	return addItem(FeeInfoConstants.FIXED_RATE_FEE, 1000 - itemCount);
        }
        
		public MultiplicityItem addItem(String rateType, int modelIdx) {
			itemCount++;
			visualItemCount++;
		    //itemCount = itemsPanel.getWidgetCount();
		    MultiplicityItem item = getItemDecorator(style);
		    Widget itemWidget = createItem(itemCount);
		    ((TransmogrifyingFeeRecordItem) itemWidget).setRateType(rateType, modelIdx);
	    	
		    if (item != null){
			    item.setItemKey(new Integer(itemCount - 1));
			    item.setItemWidget(itemWidget);
			    item.setRemoveCallback(removeCallback);
		    } else if (itemWidget instanceof MultiplicityItem){
		    	item = (MultiplicityItem)itemWidget;
		    	item.setItemKey(new Integer(itemCount -1));
		    }
		    items.add(item);
		    item.redraw();
		    itemsPanel.add(item);
		    
		    return item;
		}
		
		public int getItemCount() {
			return itemCount;
		}
    }
    
    public class MultipleFeeList extends UpdatableMultiplicityComposite {

        private final String parentPath;
        public MultipleFeeList(String parentPath){
            super(StyleType.TOP_LEVEL,true);
            this.parentPath = parentPath;
        }
        @Override
        public Widget createItem() {
            String path = QueryPath.concat(parentPath, String.valueOf(getAddItemKey())).toString();
            GroupSection ns = new GroupSection();
            addField(ns, "another Fee", getLabel(LUConstants.FEE), path );
            
            return ns;
        }
        
    }
	public class FeeListBinding implements ModelWidgetBinding<FeeList> {
		
		@Override
		public void setModelValue(FeeList widget, DataModel model, String path) {
			// passthru; can't subclass
			MultiplicityCompositeBinding.INSTANCE.setModelValue(widget, model, path);
		}

		/*
		 * Yeah, duped this due to architectural issues. Technical debt, or
		 * fodder for DOL simplification?
		 */
		@Override
		public void setWidgetValue(FeeList widget, DataModel model, String path) {
			
			widget.clear();

			
	        QueryPath qPath = QueryPath.parse(path);
	        Data data = null;
	        if(model!=null){
	        	data = model.get(qPath);
	        }

	        if (data != null) {
	            Iterator<Property> itr = data.realPropertyIterator();
	            if (itr.hasNext()) {
	            	FeeInfoHelper feeHelper = FeeInfoHelper.wrap((Data) itr.next().getValue());
	            	if (null != feeHelper.getFixedRateFee()) {
	            		addRateTypeSpecificItems(feeHelper.getFixedRateFee().realPropertyIterator(), FeeInfoConstants.FIXED_RATE_FEE, widget, model, path);
        			}
	            	if (null != feeHelper.getVariableRateFee()) {
	            		addRateTypeSpecificItems(feeHelper.getVariableRateFee().realPropertyIterator(), FeeInfoConstants.VARIABLE_RATE_FEE, widget, model, path);
	            	}
	            	if (null != feeHelper.getPerCreditFee()) {
	            		addRateTypeSpecificItems(feeHelper.getPerCreditFee().realPropertyIterator(), FeeInfoConstants.PER_CREDIT_FEE, widget, model, path);
	            	}
	            	if (null != feeHelper.getMultipleRateFee()) {
	            		addRateTypeSpecificItems(feeHelper.getMultipleRateFee().realPropertyIterator(), FeeInfoConstants.MULTIPLE_RATE_FEE, widget, model, path);
	            	}
            	}
            }
        }

		private void addRateTypeSpecificItems(Iterator<Property> iterator, String rateType, FeeList widget, DataModel model, String path) {
			while (iterator.hasNext()) {
                Property p = (Property) iterator.next();

                if (p.getKey() instanceof Integer) {
                	// use the index the model has for this item (as it's model index, not item index)
                    MultiplicityItem item = widget.addItem(rateType, ((Integer) p.getKey()).intValue());
                    
                    // FIXME: Is assumption correct that data passed to setValue() has already been persisted
                    item.setCreated(false);
                    MultiplicityItemBinding.INSTANCE.setWidgetValue(item, model, path);
                }
			}
		}
	}
	
    public class TransmogrifyingFeeRecordItem extends GroupSection {
    	
		private String parentPath;
		private FeeTypeList feeTypeList = new FeeTypeList();
		private RateTypeList rateTypeList = new RateTypeList();
		private GroupSection dropDownSection;
		private GroupSection fieldSection;
        private String fixedPath = "0/" + FeeInfoConstants.FIXED_RATE_FEE + "/";
        private String variablePath = "0/" + FeeInfoConstants.VARIABLE_RATE_FEE + "/";
        private String multiplePath = "0/" + FeeInfoConstants.MULTIPLE_RATE_FEE + "/";
        private String perCreditPath = "0/" + FeeInfoConstants.PER_CREDIT_FEE + "/";
        private SelectionChangeHandler dropDownHandler;
            
		public TransmogrifyingFeeRecordItem(String parentPath, int itemCount) {
			
            final int itemNumber = itemCount;
            this.parentPath = parentPath;


		}

		public void setRateType(String rateType, int modelIdx) {
			clearSection();
            buildRateSpecificDropDownSection(rateType, modelIdx);
            addSection(dropDownSection);
            buildRateSpecificFieldSection(rateType, modelIdx);
            addSection(fieldSection);
		}
		
		private void clearSection() {
			if (null != dropDownSection) {
				removeSection(dropDownSection);
			}
			if (null != fieldSection) {
				removeSection(fieldSection);
			}
		}
		
    	private void buildRateSpecificDropDownSection(String rateType, int modelIdx) {
    		dropDownSection = new GroupSection();
    		
    		if (FeeInfoConstants.FIXED_RATE_FEE.equals(rateType)) {
	            ViewCourseProposalSummaryConfigurer.this.addField(dropDownSection, fixedPath + modelIdx + "/feeType", "Fee Type", feeTypeList, parentPath);
	            ViewCourseProposalSummaryConfigurer.this.addField(dropDownSection, fixedPath + modelIdx + "/rateType", "Rate Type", rateTypeList, parentPath);
    		} else if (FeeInfoConstants.MULTIPLE_RATE_FEE.equals(rateType)) {
    			ViewCourseProposalSummaryConfigurer.this.addField(dropDownSection, multiplePath + modelIdx + "/feeType", "Fee Type", feeTypeList, parentPath);
    			ViewCourseProposalSummaryConfigurer.this.addField(dropDownSection, multiplePath + modelIdx + "/rateType", "Rate Type", rateTypeList, parentPath);
    		} else if (FeeInfoConstants.PER_CREDIT_FEE.equals(rateType)) {
    			ViewCourseProposalSummaryConfigurer.this.addField(dropDownSection, perCreditPath + modelIdx + "/feeType", "Fee Type", feeTypeList, parentPath);
    			ViewCourseProposalSummaryConfigurer.this.addField(dropDownSection, perCreditPath + modelIdx + "/rateType", "Rate Type", rateTypeList, parentPath);
    		} else if (FeeInfoConstants.VARIABLE_RATE_FEE.equals(rateType)) {
    			ViewCourseProposalSummaryConfigurer.this.addField(dropDownSection, variablePath + modelIdx + "/feeType", "Fee Type", feeTypeList, parentPath);
    			ViewCourseProposalSummaryConfigurer.this.addField(dropDownSection, variablePath + modelIdx + "/rateType", "Rate Type", rateTypeList, parentPath);
    		}
            rateTypeList.setText(rateType);
    	}
    	
    	private void buildRateSpecificFieldSection(String rateType, int modelIdx) {
    		fieldSection = new GroupSection();
    		if (FeeInfoConstants.FIXED_RATE_FEE.equals(rateType)) {
    			ViewCourseProposalSummaryConfigurer.this.addField(fieldSection, fixedPath + modelIdx + "/amount", "Amount", parentPath );
    		} else if (FeeInfoConstants.MULTIPLE_RATE_FEE.equals(rateType)) {
    			ViewCourseProposalSummaryConfigurer.this.addField(fieldSection, multiplePath + modelIdx + "/amount", "Amount", new MultipleFeeList(parentPath + "/" + multiplePath), parentPath );
    		} else if (FeeInfoConstants.PER_CREDIT_FEE.equals(rateType)) {
    			ViewCourseProposalSummaryConfigurer.this.addField(fieldSection, perCreditPath + modelIdx + "/amount", "Amount (PerCredit)", parentPath );
    		} else if (FeeInfoConstants.VARIABLE_RATE_FEE.equals(rateType)) {
    			ViewCourseProposalSummaryConfigurer.this.addField(fieldSection, variablePath + modelIdx + "/minAmount", "Amount", parentPath );
    			ViewCourseProposalSummaryConfigurer.this.addField(fieldSection, variablePath + modelIdx + "/maxAmount", "To", parentPath );
    		}
            rateTypeList.setText(rateType);
    	}
	}
	
    // TODO -if the sleaze for forcing a single item to 100% is to stay longer than short-term, factor
    // common sleaziness up into a superclass
    public class FinancialInformationList extends UpdatableMultiplicityComposite {
        {
            setAddItemLabel(getLabel(LUConstants.ADD_ANOTHER_ORGANIZATION));
            setItemLabel(getLabel(LUConstants.ORGANIZATION));
        }
        private final String parentPath;
        public FinancialInformationList(String parentPath, boolean readOnly){
            super(StyleType.TOP_LEVEL, readOnly);
            this.parentPath = parentPath;
            this.readOnly=true;
        }
        @Override
        public Widget createItem() {
            String path = QueryPath.concat(parentPath, String.valueOf(getAddItemKey())).toString();
            GroupSection ns = new GroupSection();
            addField(ns, AffiliatedOrgInfoConstants.ORG_ID, getLabel(LUConstants.REVENUE), path );
            FieldDescriptor fd = addField(ns, PERCENTAGE, getLabel(LUConstants.AMOUNT), path);
            fd.getFieldWidget();
            
            // Do our own validationCallback to make sure they add up to 100%?
            
            return ns;
        }
    }
    public class ExpenditureList extends UpdatableMultiplicityComposite {

        {
            setAddItemLabel(getLabel(LUConstants.ADD_ANOTHER_ORGANIZATION));
            setItemLabel(getLabel(LUConstants.ORGANIZATION));
        }
        private final String parentPath;
        public ExpenditureList(String parentPath, boolean readOnly){
            super(StyleType.TOP_LEVEL, readOnly);
            this.parentPath = parentPath;
            this.readOnly=true;
        }
        @Override
        public Widget createItem() {
            String path = QueryPath.concat(parentPath, String.valueOf(getAddItemKey())).toString();
            GroupSection ns = new GroupSection();
            addField(ns, "expenditureOrg",  getLabel(LUConstants.EXPENDITURE),new KSLabel(),path );
            addField(ns, "totalPercentage", getLabel(LUConstants.AMOUNT),new KSLabel(),path);

            return ns;
        }
        @Override
        public MultiplicityItem addItem() {
        	MultiplicityItem returnItem = super.addItem();

        	if (getItems().size() == 1) {
        		// needs to be 100% for a single expenditure org
        		Widget txtBox = ((GroupSection) returnItem.getItemWidget()).getFields().get(1).getFieldWidget();
        		((KSLabel) txtBox).setText("100");
        	}
        	return returnItem;
        }
    }      
}
