package org.kuali.student.lum.program.client.major.view;

import org.kuali.student.common.assembly.data.Metadata;
import org.kuali.student.common.assembly.data.QueryPath;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptorReadOnly;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBinding;
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

    public static ProposalInformationViewConfiguration createSpecial() { 
        return new ProposalInformationViewConfiguration(new VerticalSectionView(ProgramSections.PROGRAM_PROPOSAL_VIEW, ProgramProperties.get().program_menu_sections_proposalInformation(), ProgramConstants.PROGRAM_MODEL_ID, new MajorEditableHeader(ProgramProperties.get().program_menu_sections_proposalInformation(), ProgramSections.PROGRAM_PROPOSAL_EDIT)));
    }

    private ProposalInformationViewConfiguration(SectionView sectionView) {
        rootSection = sectionView;
    }

    @Override
    protected void buildLayout() {
        VerticalSection section = new VerticalSection(); 
        configurer.addReadOnlyField(section, ProgramConstants.PROPOSAL_TITLE_PATH, new MessageKeyInfo(ProgramProperties.get().proposalInformation_cluProgramTitle()));        
        configurer.addReadOnlyField(section, ProgramConstants.PROPOSAL_TYPE_OF_MODIFICATON_PATH, new MessageKeyInfo(ProgramProperties.get().proposalInformation_cluModificationType()));        
        configurer.addReadOnlyField(section, ProgramConstants.PROPOSAL_ABSTRACT_PATH, new MessageKeyInfo(ProgramProperties.get().proposalInformation_cluAbstractType()));
        configurer.addReadOnlyField(section, ProgramConstants.PROPOSAL_RATIONALE_PATH, new MessageKeyInfo(ProgramProperties.get().proposalInformation_cluProposalRationale()));
        rootSection.addSection(section);     
    }
}
