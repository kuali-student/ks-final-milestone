package org.kuali.student.lum.lu.ui.course.client.configuration;

import org.kuali.student.common.dto.DtoConstants;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.layouts.MenuSectionController;
import org.kuali.student.common.ui.client.configurable.mvc.sections.RequiredContainer;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract.ButtonStyle;
import org.kuali.student.core.comments.ui.client.widgets.commenttool.CommentTool;
import org.kuali.student.core.comments.ui.client.widgets.commenttool.CommentTool.EditMode;
import org.kuali.student.core.document.ui.client.widgets.documenttool.DocumentTool;
import org.kuali.student.lum.common.client.lu.LUUIConstants;
import org.kuali.student.lum.lu.ui.course.client.controllers.CourseAdminController;
import org.kuali.student.lum.lu.ui.course.client.controllers.CourseProposalController;
import org.kuali.student.lum.lu.ui.course.client.requirements.CourseRequirementsViewController;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;


/**
 * This is the screen configuration and layout for the create/modify admin screens
 * 
 * @author Will
 *
 */
public class CourseAdminConfigurer extends CourseProposalConfigurer{

    protected CourseRequirementsViewController requisitesSection;

    private RequiredContainer requiredContainer = new RequiredContainer();
 

    /**
     * Sets up all the views, sections, and views of the CourseAdminController.  This should be called
     * once for initialization and setup per CourseAdminController instance.
     * 
     * @param layout
     */
    public void configure(final CourseProposalController layout) {
    	type = "course";
        state = DtoConstants.STATE_DRAFT;
        nextState = DtoConstants.STATE_ACTIVE;
        
    	groupName = LUUIConstants.COURSE_GROUP_NAME;

        layout.addContentWidget(layout.getWfUtilities().getProposalStatusLabel());
        if (modelDefinition.getMetadata().isCanEdit()) {
            layout.addInfoWidget(requiredContainer);
            layout.addView(generateCourseAdminView((CourseAdminController) layout));
        } else {
            CourseSummaryConfigurer summaryConfigurer = GWT.create(CourseSummaryConfigurer.class);
            summaryConfigurer.init(type, state, groupName, (DataModelDefinition) modelDefinition, stmtTypes, (Controller) layout, COURSE_PROPOSAL_MODEL);
            layout.removeMenuNavigation();
            layout.addView(summaryConfigurer.generateProposalSummarySection(false));
            final CommentTool commentTool = createCommentTool(layout);
            commentTool.setEditMode(EditMode.VIEW_COMMENT);
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
        
        //Create the requisite section
        requisitesSection = new CourseRequirementsViewController(layout, getLabel(LUUIConstants.REQUISITES_LABEL_KEY), CourseSections.COURSE_REQUISITES, false, false);

        //Create the document upload section
        
        documentTool = new DocumentTool(LUUIConstants.REF_DOC_RELATION_PROPOSAL_TYPE,CourseSections.DOCUMENTS, getLabel(LUUIConstants.TOOL_DOCUMENTS_LABEL_KEY));
        documentTool.setModelDefinition((DataModelDefinition)modelDefinition);
        documentTool.setController(layout);
        documentTool.setTitle(getLabel(LUUIConstants.TOOL_DOCUMENTS_LABEL_KEY));
        documentTool.addStyleName(LUUIConstants.STYLE_SECTION);

        createCommentTool(layout);

        
        //Add course admin sections to view
        view.addSection(courseSection);
        view.addSection(governanceSection);
        view.addSection(logisticsSection);
        view.addSection(loSection);
        view.addSection(this.createHiddenRequisitesSection());
        view.addView(requisitesSection);
        view.addSection(LUUIConstants.ACTIVE_DATES_LABEL_KEY,activeDatesSection);
        view.addSection(financialSection);
        view.addView(documentTool);
        
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
        layout.addMenuItemSection(sections, getLabel(LUUIConstants.TOOL_DOCUMENTS_LABEL_KEY), LUUIConstants.TOOL_DOCUMENTS_LABEL_KEY, documentTool);
        
        //Add buttons to top and bottom of view
        layout.addButtonForView(CourseSections.COURSE_INFO, layout.getApproveAndActivateButton());
        layout.addButtonForView(CourseSections.COURSE_INFO, layout.getSaveButton());
        layout.addButtonForView(CourseSections.COURSE_INFO, layout.getCancelButton());
        layout.addTopButtonForView(CourseSections.COURSE_INFO, layout.getApproveAndActivateButton());
        layout.addTopButtonForView(CourseSections.COURSE_INFO, layout.getSaveButton());
        layout.addTopButtonForView(CourseSections.COURSE_INFO, layout.getCancelButton());

        // Setup show/hide non-required fields configuration.
        this.addDocToolLink();
        requiredContainer.setMainSection(view);

        return view;
    }

    private CommentTool createCommentTool(final MenuSectionController layout) {
        //Create and add the comment tool
        final CommentTool commentTool = new CommentTool(CourseSections.COMMENTS, getLabel(LUUIConstants.TOOL_COMMENTS_LABEL_KEY), "kuali.comment.type.generalRemarks", "Proposal Comments");
        commentTool.setController(layout);        
        layout.addContentWidget(new KSButton("Comments", ButtonStyle.DEFAULT_ANCHOR, new ClickHandler() {
            
            @Override
            public void onClick(ClickEvent event) {
                commentTool.show();
            }
        }));
        return commentTool;
    }
    
    /**
     * Gets the requisite view associated with the CourseAdminConfigurer
     * 
     * @param layout
     * @return The requisite view used by this configurer
     */
    public CourseRequirementsViewController getRequisitesSection() {
    	return requisitesSection;
	}
    
    /**
     * Gets the Document Tool View associated with the CourseAdminConfigurer
     * 
     * @param layout
     * @return The DocumentTool used by this configurer
     */
    public DocumentTool getDocumentTool() {
        return documentTool;
    }

    protected VerticalSection initSection(String labelKey) {
        final VerticalSection section = initSection(SectionTitle.generateH2Title(getLabel(labelKey)), NO_DIVIDER);
        // Add Show All Link on the sections.
        section.addShowAllLink(requiredContainer.createShowAllLink(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                requiredContainer.processInnerSection(section, true);
                section.getShowAllLink().setVisible(false);
            }
        }));

        return section;
    }
    
    private VerticalSection createHiddenRequisitesSection() {
        final VerticalSection section = initSection(SectionTitle.generateH2Title(getLabel(LUUIConstants.REQUISITES_LABEL_KEY)), NO_DIVIDER);
        // Add Show All Link on the sections.
        section.addShowAllLink(requiredContainer.createShowAllLink(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                requisitesSection.asWidget().setVisible(true);
                section.getLayout().setVisible(false);
                section.setSectionId(LUUIConstants.REQUISITES_LABEL_KEY + "_Hidden");
            }
        }));
        
        // Setup show/hide non-required fields configuration.
        requiredContainer.addCallback(new Callback<Boolean>() {

            @Override
            public void exec(Boolean result) {
                requisitesSection.asWidget().setVisible(result);
                section.getLayout().setVisible(!result);
                if (result) {
                    section.setSectionId(LUUIConstants.REQUISITES_LABEL_KEY + "_Hidden");
                } else {
                    section.setSectionId(LUUIConstants.REQUISITES_LABEL_KEY);
                }
            }
        });
        return section;
    }
    
    private void addDocToolLink(){
        Composite container = requiredContainer.createShowAllLink(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                documentTool.showShowAllLink(false);
            }
        });
        
        // Setup show/hide non-required fields configuration.
        requiredContainer.addCallback(new Callback<Boolean>() {

            @Override
            public void exec(Boolean result) {
                documentTool.showShowAllLink(!result);
            }
        });
        documentTool.setShowAllLink(container);
    }
}
