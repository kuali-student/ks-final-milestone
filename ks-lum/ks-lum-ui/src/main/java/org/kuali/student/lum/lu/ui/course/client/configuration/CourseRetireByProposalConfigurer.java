package org.kuali.student.lum.lu.ui.course.client.configuration;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.r1.common.dto.DtoConstants;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.core.document.ui.client.widgets.documenttool.DocumentTool;
import org.kuali.student.core.workflow.ui.client.views.CollaboratorSectionView;
import org.kuali.student.lum.common.client.lu.LUUIConstants;
import org.kuali.student.lum.lu.ui.course.client.controllers.CourseAdminRetireController;
import org.kuali.student.lum.lu.ui.course.client.controllers.CourseProposalController;

import com.google.gwt.core.client.GWT;


/**
 * Shell of Configurer for Retire by Proposal
 * 
 * @author mike
 *
 */
public class CourseRetireByProposalConfigurer extends CourseProposalConfigurer {

    /**
     * Sets up all the views and sections of {@link CourseAdminRetireController}. This
     * should be called once for initialization and setup per
     * {@link CourseAdminRetireController} instance.
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
            courseStatusLabel.setText(getLabel("courseStatusLabel") + ": " + layout.getCourseState());
        else
            courseStatusLabel.setText(getLabel("courseStatusLabel") + ": Unknown");
        layout.addContentWidget(courseStatusLabel);

    	addCluStartSection(layout);
        String sections = getLabel(LUUIConstants.COURSE_SECTIONS);

        layout.addMenu(sections);
                
        //Retirement Info
        layout.addMenuItem(sections, (SectionView)generateRetirementInfoSection(initSectionView(CourseSections.COURSE_INFO, getLabel(LUUIConstants.RETIREMENT_LABEL_KEY))));

        //Collaborators
        layout.addMenuItem(sections, new CollaboratorSectionView(CourseSections.PEOPLE_PERMISSONS, getLabel(LUUIConstants.SECTION_AUTHORS_AND_COLLABORATORS), COURSE_PROPOSAL_MODEL));

        //Documents
        documentTool = new DocumentTool(LUUIConstants.REF_DOC_RELATION_PROPOSAL_TYPE,CourseSections.DOCUMENTS, getLabel(LUUIConstants.TOOL_DOCUMENTS_LABEL_KEY));
        documentTool.setModelDefinition((DataModelDefinition)modelDefinition);
        layout.addMenuItem(sections, documentTool);
        
        //Add common buttons to sections except for sections with specific button behavior
        List<Enum<?>> excludedViews = new ArrayList<Enum<?>>();
        excludedViews.add(CourseSections.DOCUMENTS);
        excludedViews.add(CourseSections.COURSE_REQUISITES);
        layout.addCommonButton(LUUIConstants.COURSE_SECTIONS, layout.getSaveButton(), excludedViews);
        layout.addCommonButton(LUUIConstants.COURSE_SECTIONS, layout.getCancelButton(CourseSections.SUMMARY), excludedViews);

        //Summary
        summaryConfigurer = GWT.create(CourseRetireSummaryConfigurer.class);
        summaryConfigurer.init(type, state, groupName,(DataModelDefinition)modelDefinition, stmtTypes, (Controller)layout, COURSE_PROPOSAL_MODEL);
        layout.addSpecialMenuItem(summaryConfigurer.generateProposalSummarySection(true), "Review and Submit");
        

    }
    
    protected Section generateRetirementInfoSection(Section section) {
    	
        addField(section, PROPOSAL_TITLE_PATH, generateMessageInfo(LUUIConstants.PROPOSED_PROPOSAL_TITLE_LABEL_KEY));
        addField(section, PROPOSAL_PATH + "/" + PROPOSED_RETIREMENT_RATIONALE, // MAKE NEW ONES
                generateMessageInfo(LUUIConstants.RETIREMENT_RATIONALE_LABEL_KEY));
        addReadOnlyField(section, PROPOSAL_PATH + "/" + START_TERM, generateMessageInfo(LUUIConstants.START_TERM_LABEL_KEY));
        addField(section, PROPOSAL_PATH + "/" + PROPOSED_END_TERM, generateMessageInfo(LUUIConstants.PROPOSED_END_TERM_LABEL_KEY));
        addField(section, PROPOSAL_PATH + "/" + PROPOSED_LAST_TERM_OFFERED, generateMessageInfo(LUUIConstants.PROPOSED_LAST_TERM_OFFERED_LABEL_KEY));
        addField(section, PROPOSAL_PATH + "/" + PROPOSED_LAST_COURSE_CATALOG_YEAR, generateMessageInfo(LUUIConstants.PROPOSED_LAST_COURSE_CATALOG_YEAR_LABEL_KEY));
        addField(section, PROPOSAL_PATH + "/" + OTHER_COMMENTS,
                generateMessageInfo(LUUIConstants.OTHER_COMMENTS_LABEL_KEY));  
        
        return section;
    }

}
