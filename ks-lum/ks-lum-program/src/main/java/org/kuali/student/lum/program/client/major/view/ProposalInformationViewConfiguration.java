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
public class ProposalInformationViewConfiguration extends AbstractSectionConfiguration {

    private Controller controller;

    public static ProposalInformationViewConfiguration createSpecial(Controller controller) {
        return new ProposalInformationViewConfiguration(new VerticalSectionView(ProgramSections.PROGRAM_PROPOSAL_VIEW,
                ProgramProperties.get().program_menu_sections_proposalInformation(), ProgramConstants.PROGRAM_MODEL_ID,
                new MajorEditableHeader(ProgramProperties.get().program_menu_sections_proposalInformation(),
                        ProgramSections.PROGRAM_PROPOSAL_EDIT)), controller);
    }

    private ProposalInformationViewConfiguration(SectionView sectionView, Controller controller) {
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
        block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.PROPOSAL_TITLE_PATH, new MessageKeyInfo(ProgramProperties
                .get().proposalInformation_cluProgramTitle())));
        block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.PROPOSAL_TYPE_OF_MODIFICATON_PATH, new MessageKeyInfo(ProgramProperties.get().proposalInformation_cluModificationType())));
        block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.PROPOSAL_ABSTRACT_PATH, new MessageKeyInfo(ProgramProperties.get().proposalInformation_cluAbstractType())));
        block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.PROPOSAL_RATIONALE_PATH, new MessageKeyInfo(ProgramProperties.get().proposalInformation_cluProposalRationale())));
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
        configurer.addReadOnlyField(section, ProgramConstants.PROPOSAL_TITLE_PATH, new MessageKeyInfo(ProgramProperties
                .get().proposalInformation_cluProgramTitle()));
        configurer.addReadOnlyField(section, ProgramConstants.PROPOSAL_TYPE_OF_MODIFICATON_PATH, new MessageKeyInfo(
                ProgramProperties.get().proposalInformation_cluModificationType()));
        configurer.addReadOnlyField(section, ProgramConstants.PROPOSAL_ABSTRACT_PATH, new MessageKeyInfo(
                ProgramProperties.get().proposalInformation_cluAbstractType()));
        configurer.addReadOnlyField(section, ProgramConstants.PROPOSAL_RATIONALE_PATH, new MessageKeyInfo(
                ProgramProperties.get().proposalInformation_cluProposalRationale()));
        return section;
    }
}
