package org.kuali.student.lum.lu.ui.course.client.configuration;

import org.kuali.student.common.dto.DtoConstants;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.lum.common.client.lu.LUUIConstants;
import org.kuali.student.lum.lu.ui.course.client.controllers.CourseAdminController;
import org.kuali.student.lum.lu.ui.course.client.controllers.CourseProposalController;
import org.kuali.student.lum.lu.ui.course.client.requirements.CourseRequirementsViewController;

/**
 * This is the screen configuration and layout for the create/modify admin screens
 * 
 * @author Will
 *
 */
public class CourseAdminConfigurer extends CourseProposalConfigurer{

	protected CourseRequirementsViewController requisitesSection;

    /**
     * Sets up all the views, sections, and views of the CourseAdminController.  This should be called
     * once for initialization and setup per CourseAdminController instance.
     * 
     * @param layout
     */
    public void configure(final CourseProposalController layout) {
    	type = "course";
        state = DtoConstants.STATE_DRAFT;
        nextState = DtoConstants.STATE_APPROVED;
        
    	groupName = LUUIConstants.COURSE_GROUP_NAME;

        layout.addContentWidget(layout.getWfUtilities().getWorkflowStatusLabel());
    	if (modelDefinition.getMetadata().isCanEdit()) {
            layout.addView(generateCourseAdminView((CourseAdminController)layout));
    	} else {
			CourseSummaryConfigurer summaryConfigurer = new CourseSummaryConfigurer(type, state, groupName, (DataModelDefinition)modelDefinition, stmtTypes, (Controller)layout, COURSE_PROPOSAL_MODEL);
			layout.removeMenuNavigation();
			layout.addView(summaryConfigurer.generateProposalSummarySection(false));    		
    	}
    }

	/**
	 * Configuration for the course admin screens
	 * 
	 * @return view 
	 */
    protected View generateCourseAdminView(final CourseAdminController layout) {
        VerticalSectionView view = 
        	new VerticalSectionView(CourseSections.COURSE_INFO, getLabel(LUUIConstants.INFORMATION_LABEL_KEY), COURSE_PROPOSAL_MODEL, false);
        view.addStyleName(LUUIConstants.STYLE_SECTION);

        // Create course admin sections
        Section courseSection = generateCourseInfoSection(initSection(LUUIConstants.INFORMATION_LABEL_KEY)); 
        Section governanceSection = generateGovernanceSection(initSection(LUUIConstants.GOVERNANCE_LABEL_KEY));
        Section logisticsSection = generateCourseLogisticsSection(initSection(LUUIConstants.LOGISTICS_LABEL_KEY));
        Section loSection = initSection(LUUIConstants.LEARNING_OBJECTIVES_LABEL_KEY);
        loSection.addSection(generateLearningObjectivesNestedSection());
        Section activeDatesSection = generateActiveDatesSection(initSection(LUUIConstants.ACTIVE_DATES_LABEL_KEY));
        Section financialSection = generateFinancialsSection(initSection(LUUIConstants.FINANCIALS_LABEL_KEY));
                
        //Add course admin sections to view
        view.addSection(courseSection);
        view.addSection(governanceSection);
        view.addSection(logisticsSection);
        view.addSection(loSection);
        requisitesSection = getRequisitesSection(layout);
        view.addView(requisitesSection);
        view.addSection(activeDatesSection);
        view.addSection(financialSection);
        
        //Add menu items for sections
        String sections = getLabel(LUUIConstants.COURSE_SECTIONS);
        layout.addMenu(sections);
        layout.addMenuItemSection(sections, getLabel(LUUIConstants.INFORMATION_LABEL_KEY), LUUIConstants.INFORMATION_LABEL_KEY, courseSection.getLayout());
        layout.addMenuItemSection(sections, getLabel(LUUIConstants.GOVERNANCE_LABEL_KEY), LUUIConstants.GOVERNANCE_LABEL_KEY, governanceSection.getLayout());
        layout.addMenuItemSection(sections, getLabel(LUUIConstants.LOGISTICS_LABEL_KEY), LUUIConstants.LOGISTICS_LABEL_KEY, logisticsSection.getLayout());
        layout.addMenuItemSection(sections, getLabel(LUUIConstants.LEARNING_OBJECTIVE_LABEL_KEY), LUUIConstants.LEARNING_OBJECTIVE_LABEL_KEY, loSection.getLayout());
        layout.addMenuItemSection(sections, getLabel(LUUIConstants.REQUISITES_LABEL_KEY), LUUIConstants.REQUISITES_LABEL_KEY, requisitesSection);
        layout.addMenuItemSection(sections, getLabel(LUUIConstants.ACTIVE_DATES_LABEL_KEY), LUUIConstants.ACTIVE_DATES_LABEL_KEY, activeDatesSection.getLayout());
        layout.addMenuItemSection(sections, getLabel(LUUIConstants.FINANCIALS_LABEL_KEY), LUUIConstants.FINANCIALS_LABEL_KEY, financialSection.getLayout());
        
        //Add buttons to top and bottom of view
        layout.addButtonForView(CourseSections.COURSE_INFO, layout.getApproveAndActivateButton());
        layout.addButtonForView(CourseSections.COURSE_INFO, layout.getApproveButton());
        layout.addButtonForView(CourseSections.COURSE_INFO, layout.getSaveButton());
        layout.addButtonForView(CourseSections.COURSE_INFO, layout.getCancelButton());
        layout.addTopButtonForView(CourseSections.COURSE_INFO, layout.getApproveAndActivateButton());
        layout.addTopButtonForView(CourseSections.COURSE_INFO, layout.getApproveButton());
        layout.addTopButtonForView(CourseSections.COURSE_INFO, layout.getSaveButton());        
        layout.addTopButtonForView(CourseSections.COURSE_INFO, layout.getCancelButton());    
        
        return view;
	}
    
    public CourseRequirementsViewController getRequisitesSection(CourseAdminController layout) {
		if(requisitesSection == null){
			requisitesSection = new CourseRequirementsViewController(layout, getLabel(LUUIConstants.REQUISITES_LABEL_KEY), CourseSections.COURSE_REQUISITES, false, false);
		}
    	return requisitesSection;
	}

	protected Section initSection(String labelKey){
    	return initSection(SectionTitle.generateH2Title(getLabel(labelKey)), NO_DIVIDER);	    
    }
}
