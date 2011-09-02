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
public class ProposalChangeImpactViewConfiguration extends AbstractSectionConfiguration {

    public static ProposalChangeImpactViewConfiguration createSpecial() { 
        return new ProposalChangeImpactViewConfiguration(new VerticalSectionView(ProgramSections.PROGRAM_PROPOSAL_CHANGE_IMPACT_VIEW, ProgramProperties.get().program_menu_sections_proposalChangeImpact(), ProgramConstants.PROGRAM_MODEL_ID, new MajorEditableHeader(ProgramProperties.get().program_menu_sections_proposalChangeImpact(), ProgramSections.PROGRAM_PROPOSAL_CHANGE_IMPACT_EDIT)));
    }

    private ProposalChangeImpactViewConfiguration(SectionView sectionView) {
        rootSection = sectionView;
    }

    @Override
    protected void buildLayout() {
        VerticalSection section = new VerticalSection(); 
        configurer.addReadOnlyField(section, "proposal/relatedCourseChanges", new MessageKeyInfo(ProgramProperties.get().proposalInformation_cluRelatedCourseChangesType()));        
        configurer.addReadOnlyField(section, "proposal/impactedUnits", new MessageKeyInfo(ProgramProperties.get().proposalInformation_cluImpactedUnitsType()));        
        configurer.addReadOnlyField(section, "proposal/impactedArticulationTransferPrograms", new MessageKeyInfo(ProgramProperties.get().proposalInformation_cluImpactedArticulationTransferProgramsType()));
        configurer.addReadOnlyField(section, "proposal/studentTransitionPlans", new MessageKeyInfo(ProgramProperties.get().proposalInformation_cluStudentTransitionPlansType()));
        rootSection.addSection(section);     
    }    
}
