package org.kuali.student.lum.lu.ui.course.client.configuration;

import org.kuali.student.common.dto.DtoConstants;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.lum.common.client.lu.LUUIConstants;
import org.kuali.student.lum.lu.assembly.data.client.constants.base.RichTextInfoConstants;
import org.kuali.student.lum.lu.ui.course.client.controllers.CourseAdminWithoutVersionController;
import org.kuali.student.lum.lu.ui.course.client.controllers.CourseProposalController;

/**
 * This is the screen configuration and layout for the modify without version admin screens
 * 
 * @author Will
 *
 */
public class CourseAdminWithoutVersionConfigurer extends CourseProposalConfigurer{

    /**
     * Sets up all the views, sections, and views of the CourseAdminController.  This should be called
     * once for initialization and setup per CourseAdminController instance.
     * 
     * @param layout
     */
    @Override
	public void configure(final CourseProposalController layout) {
    	type = "course";
        state = DtoConstants.STATE_APPROVED;
        nextState = DtoConstants.STATE_ACTIVE;
        
    	groupName = LUUIConstants.COURSE_GROUP_NAME;

    	layout.addView(generateCourseAdminView((CourseAdminWithoutVersionController)layout));
    }

	/**
	 * Configuration for the course admin screens
	 * 
	 * @return view 
	 */
    protected View generateCourseAdminView(final CourseAdminWithoutVersionController layout) {
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
        view.addSection(activeDatesSection);
        view.addSection(financialSection);
        
        //Add menu items for sections
        String sections = getLabel(LUUIConstants.COURSE_SECTIONS);
        layout.addMenu(sections);
        layout.addMenuItemSection(sections, getLabel(LUUIConstants.INFORMATION_LABEL_KEY), LUUIConstants.INFORMATION_LABEL_KEY, courseSection);
        layout.addMenuItemSection(sections, getLabel(LUUIConstants.GOVERNANCE_LABEL_KEY), LUUIConstants.GOVERNANCE_LABEL_KEY, governanceSection);
        layout.addMenuItemSection(sections, getLabel(LUUIConstants.LOGISTICS_LABEL_KEY), LUUIConstants.LOGISTICS_LABEL_KEY, logisticsSection);
        layout.addMenuItemSection(sections, getLabel(LUUIConstants.LEARNING_OBJECTIVE_LABEL_KEY), LUUIConstants.LEARNING_OBJECTIVE_LABEL_KEY, loSection);
        layout.addMenuItemSection(sections, getLabel(LUUIConstants.ACTIVE_DATES_LABEL_KEY), LUUIConstants.ACTIVE_DATES_LABEL_KEY, activeDatesSection);
        layout.addMenuItemSection(sections, getLabel(LUUIConstants.FINANCIALS_LABEL_KEY), LUUIConstants.FINANCIALS_LABEL_KEY, financialSection);
        
        //Add buttons to top and bottom of view
        layout.addButtonForView(CourseSections.COURSE_INFO, layout.getSaveButton());
        layout.addButtonForView(CourseSections.COURSE_INFO, layout.getActivateButton());
        layout.addButtonForView(CourseSections.COURSE_INFO, layout.getCancelButton());
        layout.addTopButtonForView(CourseSections.COURSE_INFO, layout.getSaveButton());        
        layout.addTopButtonForView(CourseSections.COURSE_INFO, layout.getActivateButton());
        layout.addTopButtonForView(CourseSections.COURSE_INFO, layout.getCancelButton());    

        return view;
	}
    
    protected Section initSection(String labelKey){
    	return initSection(SectionTitle.generateH2Title(getLabel(labelKey)), NO_DIVIDER);	    
    }

    /**
     * Override {@link CourseProposalConfigurer#generateCourseInfoSection(Section)} to remove configuration
     * of proposal fields.
     *  
     */
    @Override
    public Section generateCourseInfoSection(Section section) {
        addField(section, COURSE + "/" + COURSE_TITLE, generateMessageInfo(LUUIConstants.COURSE_TITLE_LABEL_KEY));
        addField(section, COURSE + "/" + TRANSCRIPT_TITLE, generateMessageInfo(LUUIConstants.SHORT_TITLE_LABEL_KEY));
        section.addSection(generateCourseNumberSection());
    	FieldDescriptor instructorsFd = addField(section, COURSE + "/" + INSTRUCTORS, generateMessageInfo(LUUIConstants.INSTRUCTORS_LABEL_KEY));
        instructorsFd.setWidgetBinding(new KeyListModelWigetBinding("personId"));

        section.addSection(generateDescriptionRationaleSection());
        
        return section;
    }
    
    /**
     * Override  {@link CourseProposalConfigurer#generateDescriptionRationaleSection()} to remove configuration of
     * proposal fields.
     * 
     */
    @Override
    protected VerticalSection generateDescriptionRationaleSection() {
        SectionTitle title = getH4Title(LUUIConstants.PROPOSAL_TITLE_SECTION_LABEL_KEY);
        VerticalSection description = initSection(title, !WITH_DIVIDER);
        title.setStyleName("cluProposalTitleSection");
        addField(description, COURSE + "/" + DESCRIPTION + "/" + RichTextInfoConstants.PLAIN, generateMessageInfo(LUUIConstants.DESCRIPTION_LABEL_KEY));
        return description;
    }
    

}
