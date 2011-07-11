package org.kuali.student.lum.lu.ui.course.client.configuration;

import org.kuali.student.common.dto.DtoConstants;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.lum.common.client.lu.LUUIConstants;
import org.kuali.student.lum.lu.ui.course.client.controllers.CourseAdminRetireController;
import org.kuali.student.lum.lu.ui.course.client.controllers.CourseProposalController;
import org.kuali.student.lum.lu.ui.course.client.requirements.CourseRequirementsViewController;

/**
 * This is the screen configuration and layout for the admin retire screen
 * 
 * @author Will
 *
 */
public class CourseAdminRetireConfigurer extends CourseProposalConfigurer{

	protected CourseRequirementsViewController requisitesSection;
    
	/**
     * Sets up all the views and sections of {@link CourseAdminRetireController}.  This should be called
     * once for initialization and setup per {@link CourseAdminRetireController} instance.
     * 
     * @param layout
     */
    @Override
	public void configure(final CourseProposalController layout) {
    	type = "course";
        state = DtoConstants.STATE_RETIRED;
        nextState = DtoConstants.STATE_RETIRED;
        
    	groupName = LUUIConstants.COURSE_GROUP_NAME;

        KSLabel courseStatusLabel = new KSLabel("");
        if (layout.getCourseState() != null)
        	courseStatusLabel.setText("Status: " + layout.getCourseState());
        else
        	courseStatusLabel.setText("Status: Unknown");
        layout.addContentWidget(courseStatusLabel); 
        		
    	layout.addView(generateCourseAdminView((CourseAdminRetireController)layout));
    }

	/**
	 * Configuration for the course admin screens
	 * 
	 * @return view 
	 */
    protected View generateCourseAdminView(final CourseAdminRetireController layout) {
        VerticalSectionView view = 
        	new VerticalSectionView(CourseSections.COURSE_INFO, getLabel(LUUIConstants.RETIREMENT_LABEL_KEY), COURSE_PROPOSAL_MODEL, false);
        view.addStyleName(LUUIConstants.STYLE_SECTION);

        // Create course admin sections
        Section activeDatesSection = generateActiveDatesSection(initSection(LUUIConstants.ACTIVE_DATES_LABEL_KEY));
        Section retirementSection = generateRetirementSection(initSection(LUUIConstants.RETIREMENT_LABEL_KEY));
        
        //Add course admin sections to view
        view.addSection(activeDatesSection);
        view.addSection(retirementSection);
        
        //Add menu items for sections
        String sections = getLabel(LUUIConstants.COURSE_SECTIONS);
        layout.addMenu(sections);
        layout.addMenuItemSection(sections, getLabel(LUUIConstants.ACTIVE_DATES_LABEL_KEY), LUUIConstants.ACTIVE_DATES_LABEL_KEY, activeDatesSection.getLayout());
        layout.addMenuItemSection(sections, getLabel(LUUIConstants.RETIREMENT_LABEL_KEY), LUUIConstants.RETIREMENT_LABEL_KEY, retirementSection.getLayout());
        
        //Add buttons to top and bottom of view
        layout.addButtonForView(CourseSections.COURSE_INFO, layout.getSaveButton());
        layout.addButtonForView(CourseSections.COURSE_INFO, layout.getCancelButton());
        layout.addTopButtonForView(CourseSections.COURSE_INFO, layout.getSaveButton());        
        layout.addTopButtonForView(CourseSections.COURSE_INFO, layout.getCancelButton());    

        return view;
	}
    
    protected Section generateActiveDatesSection(Section section) {
        addField(section, COURSE + "/" + START_TERM, generateMessageInfo(LUUIConstants.START_TERM_LABEL_KEY));
        addField(section, COURSE + "/" + END_TERM, generateMessageInfo(LUUIConstants.END_TERM_LABEL_KEY));

        return section;
    }
    
    protected Section generateRetirementSection(Section section) {
        addField(section, COURSE + "/" + RETIREMENT_RATIONALE, generateMessageInfo(LUUIConstants.RETIREMENT_RATIONALE_LABEL_KEY));
        addField(section, COURSE + "/" + LAST_TERM_OFFERED, generateMessageInfo(LUUIConstants.LAST_TERM_OFFERED_LABEL_KEY));
        addField(section, COURSE + "/" + LAST_PUBLICATION_YEAR, generateMessageInfo(LUUIConstants.LAST_PUBLICATION_YEAR_LABEL_KEY));
        addField(section, COURSE + "/" + SPECIAL_CIRCUMSTANCES, generateMessageInfo(LUUIConstants.SPECIAL_CIRCUMSTANCES_LABEL_KEY));
        
        return section;
    }
        
    protected Section initSection(String labelKey){
    	return initSection(SectionTitle.generateH2Title(getLabel(labelKey)), NO_DIVIDER);	    
    }

    
}
