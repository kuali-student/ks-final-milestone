package org.kuali.student.lum.program.client.major.view;

import org.kuali.student.r1.common.assembly.data.Metadata;
import org.kuali.student.r1.common.assembly.data.QueryPath;
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

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Igor
 */
public class ProposalInformationViewConfiguration extends AbstractSectionConfiguration {

    private Controller controller;

	public static ProposalInformationViewConfiguration create(Configurer configurer) {
	    return new ProposalInformationViewConfiguration(configurer);
	}

    public static ProposalInformationViewConfiguration createSpecial(Configurer configurer, Controller controller) {
        return new ProposalInformationViewConfiguration(configurer, controller);
    }
    
    private ProposalInformationViewConfiguration(Configurer configurer, Controller controller) {
        this.setConfigurer(configurer);
        String proposalInformationLabel = getLabel(ProgramMsgConstants.PROGRAM_MENU_SECTIONS_PROPOSALINFORMATION);
        rootSection = new VerticalSectionView(ProgramSections.PROGRAM_PROPOSAL_VIEW,
                proposalInformationLabel, ProgramConstants.PROGRAM_MODEL_ID);
        this.controller = controller;
    }

    private ProposalInformationViewConfiguration(Configurer configurer) {
        this.setConfigurer(configurer);
        String proposalInformationLabel = getLabel(ProgramMsgConstants.PROGRAM_MENU_SECTIONS_PROPOSALINFORMATION);
        rootSection = new VerticalSectionView(ProgramSections.PROGRAM_PROPOSAL_VIEW, proposalInformationLabel, ProgramConstants.PROGRAM_MODEL_ID, new MajorEditableHeader(proposalInformationLabel,
                        ProgramSections.PROGRAM_PROPOSAL_EDIT));
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
        SummaryTableSection section = GWT.create(SummaryTableSection.class);
        section.init((Controller) controller);
        section.setEditable(false);
        section.addSummaryTableFieldBlock(createSummaryTableFieldBlock());
        return section;
    }
    
    public SummaryTableFieldBlock createSummaryTableFieldBlock() {
        SummaryTableFieldBlock block = new SummaryTableFieldBlock();
        block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.PROPOSAL_TITLE_PATH, generateMessageInfo(ProgramMsgConstants.PROPOSALINFORMATION_CLUPROGRAMTITLE)));
        block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.PROPOSAL_TYPE_OF_MODIFICATON_PATH, generateMessageInfo(ProgramMsgConstants.PROPOSALINFORMATION_CLUMODIFICATIONTYPE)));
        block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.PROPOSAL_ABSTRACT_PATH, generateMessageInfo(ProgramMsgConstants.PROPOSALINFORMATION_CLUABSTRACTTYPE)));
        block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.PROPOSAL_RATIONALE_PATH, generateMessageInfo(ProgramMsgConstants.PROPOSALINFORMATION_CLUPROPOSALRATIONALE)));
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
        configurer.addReadOnlyField(section, ProgramConstants.PROPOSAL_TITLE_PATH, generateMessageInfo(ProgramMsgConstants.PROPOSALINFORMATION_CLUPROGRAMTITLE));
        configurer.addReadOnlyField(section, ProgramConstants.PROPOSAL_TYPE_OF_MODIFICATON_PATH, generateMessageInfo(ProgramMsgConstants.PROPOSALINFORMATION_CLUMODIFICATIONTYPE));
        configurer.addReadOnlyField(section, ProgramConstants.PROPOSAL_ABSTRACT_PATH, generateMessageInfo(ProgramMsgConstants.PROPOSALINFORMATION_CLUABSTRACTTYPE));
        configurer.addReadOnlyField(section, ProgramConstants.PROPOSAL_RATIONALE_PATH, generateMessageInfo(ProgramMsgConstants.PROPOSALINFORMATION_CLUPROPOSALRATIONALE));
        return section;
    }
}
