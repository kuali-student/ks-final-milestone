package org.kuali.student.lum.lu.ui.course.client.configuration;

import java.util.ArrayList;

import org.kuali.student.common.dto.DtoConstants;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.lum.common.client.lu.LUUIConstants;
import org.kuali.student.lum.lu.ui.course.client.controllers.CourseAdminController;
import org.kuali.student.lum.lu.ui.course.client.controllers.CourseProposalController;

/**
 * This is the screen configuration and layout for the create/modify admin screens
 * 
 * @author Will
 *
 */
public class CourseAdminConfigurer extends CourseConfigurer{

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

    	if (modelDefinition.getMetadata().isCanEdit()) {
            String sections = getLabel(LUUIConstants.COURSE_SECTIONS);
            layout.addMenu(sections);            
            layout.addMenuItem(sections, generateCourseAdminSection());
            ArrayList<Enum<?>> excludedSections = new ArrayList<Enum<?>>();
            layout.addCommonButton(LUUIConstants.COURSE_SECTIONS, layout.getSaveButton(), excludedSections);
            layout.addCommonButton(LUUIConstants.COURSE_SECTIONS, ((CourseAdminController)layout).getApproveButton(), excludedSections);
            layout.addCommonButton(LUUIConstants.COURSE_SECTIONS, ((CourseAdminController)layout).getApproveAndActivateButton(), excludedSections);            
    	}
    }

	/**
	 * Configuration for the course admin screens
	 * 
	 * @return view 
	 */
    protected View generateCourseAdminSection() {
        VerticalSectionView section = 
        	new VerticalSectionView(CourseSections.COURSE_INFO, getLabel(LUUIConstants.INFORMATION_LABEL_KEY), COURSE_PROPOSAL_MODEL, false);
        section.addStyleName(LUUIConstants.STYLE_SECTION);

                
        section.addSection(generateCourseInfoSection(initSection(LUUIConstants.INFORMATION_LABEL_KEY)));
        section.addSection(generateGovernanceSection(initSection(LUUIConstants.GOVERNANCE_LABEL_KEY)));
        section.addSection(generateCourseLogisticsSection(initSection(LUUIConstants.LOGISTICS_LABEL_KEY)));
        
        //Add learning objective section
        Section loSection = initSection(LUUIConstants.LEARNING_OBJECTIVES_LABEL_KEY);
        loSection.addSection(generateLearningObjectivesNestedSection());        
        section.addSection(loSection);
        
        section.addSection(generateActiveDatesSection(initSection(LUUIConstants.ACTIVE_DATES_LABEL_KEY)));
        section.addSection(generateFinancialsSection(initSection(LUUIConstants.FINANCIALS_LABEL_KEY)));
                

        return section;
	}
    
    protected Section initSection(String labelKey){
    	return initSection(SectionTitle.generateH2Title(getLabel(labelKey)), NO_DIVIDER);	    
    }
}
