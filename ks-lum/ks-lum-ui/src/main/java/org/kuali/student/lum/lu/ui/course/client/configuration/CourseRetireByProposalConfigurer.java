package org.kuali.student.lum.lu.ui.course.client.configuration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import org.kuali.student.common.assembly.data.Data;
import org.kuali.student.common.assembly.data.Data.Property;
import org.kuali.student.common.dto.DtoConstants;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptorReadOnly;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBinding;
import org.kuali.student.common.ui.client.configurable.mvc.sections.HorizontalSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.core.document.ui.client.widgets.documenttool.DocumentTool;
import org.kuali.student.core.workflow.ui.client.views.CollaboratorSectionView;
import org.kuali.student.lum.common.client.lu.LUUIConstants;
import org.kuali.student.lum.lu.assembly.data.client.constants.orch.CreditCourseConstants;
import org.kuali.student.lum.lu.ui.course.client.configuration.CourseProposalConfigurer.CourseSections;
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
    	HorizontalSection layoutSection = new HorizontalSection();
    	
    	VerticalSection leftSection = new VerticalSection();
    	VerticalSection rightSection = generateReferenceDataSection();
    	
        addField(leftSection, PROPOSAL_TITLE_PATH, generateMessageInfo(LUUIConstants.PROPOSAL_TITLE_LABEL_KEY));
        addField(leftSection, PROPOSAL_PATH + "/" + PROPOSED_RETIREMENT_RATIONALE, // MAKE NEW ONES
                generateMessageInfo(LUUIConstants.RETIREMENT_RATIONALE_LABEL_KEY));
        addReadOnlyField(leftSection, PROPOSAL_PATH + "/" + START_TERM, generateMessageInfo(LUUIConstants.START_TERM_LABEL_KEY));
        addField(leftSection, PROPOSAL_PATH + "/" + PROPOSED_END_TERM, generateMessageInfo(LUUIConstants.END_TERM_LABEL_KEY));
        addField(leftSection, PROPOSAL_PATH + "/" + PROPOSED_LAST_TERM_OFFERED, generateMessageInfo(LUUIConstants.END_TERM_LABEL_KEY));
        addField(leftSection, PROPOSAL_PATH + "/" + PROPOSED_LAST_COURSE_CATALOG_YEAR, generateMessageInfo(LUUIConstants.END_TERM_LABEL_KEY));
        addField(leftSection, PROPOSAL_PATH + "/" + OTHER_COMMENTS,
                generateMessageInfo(LUUIConstants.OTHER_COMMENTS_LABEL_KEY));  // Should be different one ???????!!!!!!!!!!!
        
        layoutSection.addSection(leftSection);
        layoutSection.addSection(rightSection);
        section.addSection(layoutSection);
        
        return section;
    }
    
    protected VerticalSection generateReferenceDataSection() {
        VerticalSection section = new VerticalSection(SectionTitle.generateH2Title(getLabel("ReferenceData")));
        section.addStyleName("readOnlySection");
        section.addStyleName("readOnlyNeedsToBeOnTheRight");
        
        addReadOnlyFieldJustText(section, COURSE + "/" + CreditCourseConstants.COURSE_TITLE, generateMessageInfo(LUUIConstants.COURSE_TITLE_LABEL_KEY));
        addReadOnlyFieldJustText(section, COURSE + "/" + CreditCourseConstants.COURSE_CODE, generateMessageInfo(LUUIConstants.COURSE_NUMBER_LABEL_KEY));
        
        //Add the crosslisted/joint Reference Data with custom binding
        FieldDescriptorReadOnly xlistsAndJoints = new FieldDescriptorReadOnly(CreditCourseConstants.CROSSLISTED_AND_JOINTS, generateMessageInfo(LUUIConstants.CROSSLISTED_AND_JOINTS_LABEL_KEY), null, new KSLabel());
        xlistsAndJoints.setWidgetBinding(new ModelWidgetBinding<KSLabel>() {
			public void setModelValue(KSLabel widget, DataModel model,
					String path) {
			}
			public void setWidgetValue(KSLabel widget, DataModel model,
					String path) {
				TreeSet<String> codes = new TreeSet<String>();
				Data crossListings = model.getRoot().get(CreditCourseConstants.CROSS_LISTINGS);
				for(Property property:crossListings){
					codes.add((String) ((Data)property.getValue()).get(CreditCourseConstants.COURSE_CODE));
				}
				Data joints = model.getRoot().get(CreditCourseConstants.JOINTS);
				for(Property property:joints){
					String subjectArea = (String) ((Data)property.getValue()).get(CreditCourseConstants.SUBJECT_AREA);
					String courseNumberSuffix = (String) ((Data)property.getValue()).get(CreditCourseConstants.COURSE_NUMBER_SUFFIX);
					codes.add(subjectArea + courseNumberSuffix);
				}
				String output="";
				for(Iterator<String> iter=codes.iterator();iter.hasNext();){
					String code = iter.next();
					output += code;
					if(iter.hasNext()){
						output += ", ";
					}
				}
				widget.setText(output);
			}
        	
        });
        section.addField(xlistsAndJoints);
        
        addReadOnlyFieldJustText(section, COURSE + "/" + CreditCourseConstants.CURRICULUM_OVERSIGHT_ORGS_, generateMessageInfo(LUUIConstants.ACADEMIC_SUBJECT_ORGS_KEY));
        
        return section;
    }

    //Makes a read only field with no helptext/instructions/examples/constraint text
    protected FieldDescriptor addReadOnlyFieldJustText(Section section, String fieldKey, MessageKeyInfo messageKey){
    	FieldDescriptor fd = addReadOnlyField(section, fieldKey, messageKey);
        fd.getFieldElement().setHelp(null);
        fd.getFieldElement().setInstructions(null);
        fd.getFieldElement().setExamples(null);
        fd.getFieldElement().setConstraintText(null);
    	return fd;
    }
}
