package org.kuali.student.lum.program.client.major.view;

import org.kuali.student.common.assembly.data.Metadata;
import org.kuali.student.common.assembly.data.QueryPath;
import org.kuali.student.common.ui.client.configurable.mvc.Configurer;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptorReadOnly;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBinding;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.common.ui.client.widgets.table.summary.SummaryTableFieldBlock;
import org.kuali.student.common.ui.client.widgets.table.summary.SummaryTableFieldRow;
import org.kuali.student.common.ui.client.widgets.table.summary.SummaryTableSection;
import org.kuali.student.lum.common.client.configuration.AbstractSectionConfiguration;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramMsgConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.major.MajorEditableHeader;
import org.kuali.student.lum.program.client.major.proposal.MajorProposalController;

import com.google.gwt.user.client.ui.Widget;

/**
 * @author Igor
 */
public class ProposalChangeImpactViewConfiguration extends AbstractSectionConfiguration {
    
    private Controller controller;

    public static ProposalChangeImpactViewConfiguration create(Configurer configurer) { 
        return new ProposalChangeImpactViewConfiguration(configurer);
    }

    public static ProposalChangeImpactViewConfiguration createSpecial(Configurer configurer, Controller controller) { 
        return new ProposalChangeImpactViewConfiguration(configurer, controller);
    }

    private ProposalChangeImpactViewConfiguration(Configurer configurer) {
        this.setConfigurer(configurer);
        String title = getLabel(ProgramMsgConstants.PROGRAM_MENU_SECTIONS_PROPOSALCHANGEIMPACT);
        rootSection = new VerticalSectionView(ProgramSections.PROGRAM_PROPOSAL_CHANGE_IMPACT_VIEW, 
                title, ProgramConstants.PROGRAM_MODEL_ID);
    }

    private ProposalChangeImpactViewConfiguration(Configurer configurer, Controller controller) {
        this.setConfigurer(configurer);
        String title = getLabel(ProgramMsgConstants.PROGRAM_MENU_SECTIONS_PROPOSALCHANGEIMPACT);
        rootSection = new VerticalSectionView(ProgramSections.PROGRAM_PROPOSAL_CHANGE_IMPACT_VIEW, 
                title, ProgramConstants.PROGRAM_MODEL_ID, new MajorEditableHeader(title, 
                        ProgramSections.PROGRAM_PROPOSAL_CHANGE_IMPACT_EDIT));
        this.controller = controller;
    }

    @Override
    protected void buildLayout() {
        if (controller instanceof MajorProposalController) {
        	rootSection.addSection(createEditableSection());
        } else {
        	rootSection.addSection(createReadOnlySection());
        }
    }  
    
    private Section createEditableSection() {
        SummaryTableSection section = new SummaryTableSection(controller);             
        section.setEditable(false);
        section.addSummaryTableFieldBlock(createSummaryTableFieldBlock());
        return section;
    }
    
    public SummaryTableFieldBlock createSummaryTableFieldBlock() {
        SummaryTableFieldBlock block = new SummaryTableFieldBlock();
        block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.PROPOSAL_RELATED_COURSE_CHANGES_PATH, generateMessageInfo(ProgramMsgConstants.PROPOSALINFORMATION_CLURELATEDCOURSECHANGESTYPE)));
        block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.PROPOSAL_IMPACTED_UNITS_PATH, generateMessageInfo(ProgramMsgConstants.PROPOSALINFORMATION_CLUIMPACTEDUNITSTYPE)));
        block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.PROPOSAL_IMPACTED_ARTICULATION_TRANSFER_PATH, generateMessageInfo(ProgramMsgConstants.PROPOSALINFORMATION_CLUIMPACTEDARTICULATIONTRANSFERPROGRAMSTYPE)));
        block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.PROPOSAL_STUDENT_TRANSITION_PLANS_PATH, generateMessageInfo(ProgramMsgConstants.PROPOSALINFORMATION_CLUSTUDENTTRANSITIONPLANSTYPE)));
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
        configurer.addReadOnlyField(section, ProgramConstants.PROPOSAL_RELATED_COURSE_CHANGES_PATH, generateMessageInfo(ProgramMsgConstants.PROPOSALINFORMATION_CLURELATEDCOURSECHANGESTYPE));        
        configurer.addReadOnlyField(section, ProgramConstants.PROPOSAL_IMPACTED_UNITS_PATH, generateMessageInfo(ProgramMsgConstants.PROPOSALINFORMATION_CLUIMPACTEDUNITSTYPE));        
        configurer.addReadOnlyField(section, ProgramConstants.PROPOSAL_IMPACTED_ARTICULATION_TRANSFER_PATH, generateMessageInfo(ProgramMsgConstants.PROPOSALINFORMATION_CLUIMPACTEDARTICULATIONTRANSFERPROGRAMSTYPE));
        configurer.addReadOnlyField(section, ProgramConstants.PROPOSAL_STUDENT_TRANSITION_PLANS_PATH, generateMessageInfo(ProgramMsgConstants.PROPOSALINFORMATION_CLUSTUDENTTRANSITIONPLANSTYPE));
        return section;
    }

}
