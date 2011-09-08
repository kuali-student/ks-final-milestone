package org.kuali.student.lum.program.client.major.view;

import org.kuali.student.common.assembly.data.Metadata;
import org.kuali.student.common.assembly.data.QueryPath;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptorReadOnly;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBinding;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.sections.TableSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.common.ui.client.widgets.table.summary.SummaryTableFieldBlock;
import org.kuali.student.common.ui.client.widgets.table.summary.SummaryTableFieldRow;
import org.kuali.student.common.ui.client.widgets.table.summary.SummaryTableSection;
import org.kuali.student.lum.common.client.configuration.AbstractSectionConfiguration;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.major.MajorEditableHeader;
import org.kuali.student.lum.program.client.major.edit.MajorEditController;
import org.kuali.student.lum.program.client.major.proposal.MajorProposalController;
import org.kuali.student.lum.program.client.properties.ProgramProperties;

import com.google.gwt.user.client.ui.Widget;

/**
 * @author Igor
 */
public class ProposalChangeImpactViewConfiguration extends AbstractSectionConfiguration {
    
    private Controller controller;

    public static ProposalChangeImpactViewConfiguration createSpecial(Controller controller) { 
        return new ProposalChangeImpactViewConfiguration(new VerticalSectionView(ProgramSections.PROGRAM_PROPOSAL_CHANGE_IMPACT_VIEW, ProgramProperties.get().program_menu_sections_proposalChangeImpact(), ProgramConstants.PROGRAM_MODEL_ID, new MajorEditableHeader(ProgramProperties.get().program_menu_sections_proposalChangeImpact(), ProgramSections.PROGRAM_PROPOSAL_CHANGE_IMPACT_EDIT)), controller);
    }

    private ProposalChangeImpactViewConfiguration(SectionView sectionView, Controller controller) {
        rootSection = sectionView;
        this.controller = controller;
    }

    @Override
    protected void buildLayout() {
        Section section = null;
        if (controller instanceof MajorProposalController) {
            section = createEditableSection();
        } else {
            section = createReadOnlySection();
        }
        rootSection.addSection(section);
    }  
    
    private Section createEditableSection() {
        SummaryTableSection section = new SummaryTableSection(controller);             
        section.setEditable(false);
        section.addSummaryTableFieldBlock(createSummaryTableFieldBlock());
        return section;
    }
    
    public SummaryTableFieldBlock createSummaryTableFieldBlock() {
        SummaryTableFieldBlock block = new SummaryTableFieldBlock();
        block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.PROPOSAL_RELATED_COURSE_CHANGES_PATH, new MessageKeyInfo(ProgramProperties.get().proposalInformation_cluRelatedCourseChangesType())));
        block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.PROPOSAL_IMPACTED_UNITS_PATH, new MessageKeyInfo(ProgramProperties.get().proposalInformation_cluImpactedUnitsType())));
        block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.PROPOSAL_IMPACTED_ARTICULATION_TRANSFER_PATH, new MessageKeyInfo(ProgramProperties.get().proposalInformation_cluImpactedArticulationTransferProgramsType())));
        block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.PROPOSAL_STUDENT_TRANSITION_PLANS_PATH, new MessageKeyInfo(ProgramProperties.get().proposalInformation_cluStudentTransitionPlansType())));
        return block;
    }
    
    protected SummaryTableFieldRow getFieldRow(String fieldKey, MessageKeyInfo messageKey) {
        return getFieldRow(fieldKey, messageKey, null, null, null, null, false);
    }
     
    protected SummaryTableFieldRow getFieldRow(String fieldKey,
            MessageKeyInfo messageKey, Widget widget, Widget widget2,
            String parentPath, ModelWidgetBinding<?> binding, boolean optional) 
    {
        QueryPath path = QueryPath.concat(parentPath, fieldKey);
        Metadata meta = configurer.getModelDefinition().getMetadata(path);

        FieldDescriptorReadOnly fd = new FieldDescriptorReadOnly(path.toString(), messageKey, meta);
        if (widget != null) {
            fd.setFieldWidget(widget);
        }
        if (binding != null) {
            fd.setWidgetBinding(binding);
        }
        fd.setOptional(optional);

        FieldDescriptorReadOnly fd2 = new FieldDescriptorReadOnly(path.toString(), messageKey, meta);
        if (widget2 != null) {
            fd2.setFieldWidget(widget2);
        }
        if (binding != null) {
            fd2.setWidgetBinding(binding);
        }
        fd2.setOptional(optional);

        SummaryTableFieldRow fieldRow = new SummaryTableFieldRow(fd, fd2);

        return fieldRow;
    }
    
    private VerticalSection createReadOnlySection() {
        VerticalSection section = new VerticalSection(); 
        configurer.addReadOnlyField(section, ProgramConstants.PROPOSAL_RELATED_COURSE_CHANGES_PATH, new MessageKeyInfo(ProgramProperties.get().proposalInformation_cluRelatedCourseChangesType()));        
        configurer.addReadOnlyField(section, ProgramConstants.PROPOSAL_IMPACTED_UNITS_PATH, new MessageKeyInfo(ProgramProperties.get().proposalInformation_cluImpactedUnitsType()));        
        configurer.addReadOnlyField(section, ProgramConstants.PROPOSAL_IMPACTED_ARTICULATION_TRANSFER_PATH, new MessageKeyInfo(ProgramProperties.get().proposalInformation_cluImpactedArticulationTransferProgramsType()));
        configurer.addReadOnlyField(section, ProgramConstants.PROPOSAL_STUDENT_TRANSITION_PLANS_PATH, new MessageKeyInfo(ProgramProperties.get().proposalInformation_cluStudentTransitionPlansType()));
        return section;
    }
}
