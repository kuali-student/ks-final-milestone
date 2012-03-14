package org.kuali.student.lum.lu.ui.course.client.configuration;

import org.kuali.student.common.dto.DtoConstants;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.widgets.KSCharCount;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.core.document.ui.client.widgets.documenttool.DocumentTool;
import org.kuali.student.core.workflow.ui.client.views.CollaboratorSectionView;
import org.kuali.student.lum.common.client.lu.LUUIConstants;
import org.kuali.student.lum.lu.ui.course.client.configuration.CourseProposalConfigurer.CourseSections;
import org.kuali.student.lum.lu.ui.course.client.configuration.CourseProposalConfigurer.KeyListModelWigetBinding;
import org.kuali.student.lum.lu.ui.course.client.controllers.CourseAdminRetireController;
import org.kuali.student.lum.lu.ui.course.client.controllers.CourseRetireByProposalController;
import org.kuali.student.lum.lu.ui.course.client.controllers.CourseProposalController;
import org.kuali.student.lum.lu.ui.course.client.requirements.CourseRequirementsViewController;


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

    }
    
    protected Section generateRetirementInfoSection(Section section) {
        addField(section, PROPOSAL_TITLE_PATH, generateMessageInfo(LUUIConstants.PROPOSAL_TITLE_LABEL_KEY));
        addField(section, COURSE + "/" + RETIREMENT_RATIONALE,
                generateMessageInfo(LUUIConstants.RETIREMENT_RATIONALE_LABEL_KEY));
        addReadOnlyField(section, COURSE + "/" + START_TERM, generateMessageInfo(LUUIConstants.START_TERM_LABEL_KEY));
        addField(section, COURSE + "/" + END_TERM, generateMessageInfo(LUUIConstants.END_TERM_LABEL_KEY));
//        addField(section, COURSE + "/" + OTHER_COMMENTS,
//                generateMessageInfo(LUUIConstants.OTHER_COMMENTS_LABEL_KEY));  // Should be different one ???????!!!!!!!!!!!

        return section;
    }

}
