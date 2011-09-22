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
public class CatalogInformationViewConfiguration extends AbstractSectionConfiguration {

	private Controller controller = null;

    public static CatalogInformationViewConfiguration create() {
        return new CatalogInformationViewConfiguration(new VerticalSectionView(ProgramSections.CATALOG_INFO_VIEW, ProgramProperties.get().program_menu_sections_catalogInfo(), ProgramConstants.PROGRAM_MODEL_ID));
    }

    public static CatalogInformationViewConfiguration createSpecial(Controller controller) {
        String title = ProgramProperties.get().program_menu_sections_catalogInfo();
        return new CatalogInformationViewConfiguration(new VerticalSectionView(ProgramSections.CATALOG_INFO_VIEW, title, ProgramConstants.PROGRAM_MODEL_ID, new MajorEditableHeader(title, ProgramSections.CATALOG_INFO_EDIT)), controller);
    }

    private CatalogInformationViewConfiguration(SectionView sectionView) {
        rootSection = sectionView;
    }

    private CatalogInformationViewConfiguration(SectionView sectionView, Controller controller) {
        rootSection = sectionView;
        this.controller = controller;
    }

    @Override
    protected void buildLayout() {
    	if (controller instanceof MajorProposalController || controller instanceof MajorEditController) 
    		rootSection.addSection(createCatalogInformationSectionEdit());
    	else
    	{
    		TableSection section = new TableSection(SectionTitle.generateEmptyTitle());
       		configurer.addReadOnlyField(section, ProgramConstants.DESCRIPTION + "/plain", new MessageKeyInfo(ProgramProperties.get().catalogInformation_descr()));
       		configurer.addReadOnlyField(section, ProgramConstants.CATALOG_DESCRIPTION + "/" + ProgramConstants.PLAIN_TEXT, new MessageKeyInfo(ProgramProperties.get().catalogInformation_catalogDescr()));
       		configurer.addReadOnlyField(section, ProgramConstants.CORE_FACULTY_MEMBERS, new MessageKeyInfo(ProgramProperties.get().catalogInformation_publishedInstructors()));
       		configurer.addReadOnlyField(section, ProgramConstants.PUBLICATION_TARGETS, new MessageKeyInfo(ProgramProperties.get().catalogInformation_catalogPublicationTargets()));
       		configurer.addReadOnlyField(section, ProgramConstants.FULL_PART_TIME, new MessageKeyInfo(ProgramProperties.get().catalogInformation_intensity()));
       		configurer.addReadOnlyField(section, ProgramConstants.DURATION + "/atpDurationTypeKey", new MessageKeyInfo(ProgramProperties.get().catalogInformation_stdDuration()));
       		configurer.addReadOnlyField(section, ProgramConstants.DURATION + "/timeQuantity", new MessageKeyInfo(ProgramProperties.get().catalogInformation_durationCount()));
       		configurer.addReadOnlyField(section, ProgramConstants.DURATION_NOTES, new MessageKeyInfo(ProgramProperties.get().catalogInformation_durationNotes()));
       		configurer.addReadOnlyField(section, ProgramConstants.MORE_INFORMATION, new MessageKeyInfo(ProgramProperties.get().catalogInformation_referenceUrl()));
            rootSection.addSection(section);
    	}
    }
    
    // Side-by-side comparison (when controller is not null)  
    private SummaryTableSection createCatalogInformationSectionEdit() { 
      	SummaryTableSection section = new SummaryTableSection((Controller) controller);     		
      	section.setEditable(false);
      	section.addSummaryTableFieldBlock(createCatalogInformationSectionEditBlock());

        return section;
    }

  	@SuppressWarnings("unchecked")
  	public SummaryTableFieldBlock createCatalogInformationSectionEditBlock() {
  		SummaryTableFieldBlock block = new SummaryTableFieldBlock();
  		block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.DESCRIPTION + "/plain", new MessageKeyInfo(ProgramProperties.get().catalogInformation_descr())));
  		block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.CATALOG_DESCRIPTION + "/" + ProgramConstants.PLAIN_TEXT, new MessageKeyInfo(ProgramProperties.get().catalogInformation_catalogDescr())));
  		block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.CORE_FACULTY_MEMBERS, new MessageKeyInfo(ProgramProperties.get().catalogInformation_publishedInstructors())));
  		block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.PUBLICATION_TARGETS, new MessageKeyInfo(ProgramProperties.get().catalogInformation_catalogPublicationTargets())));
  		block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.FULL_PART_TIME, new MessageKeyInfo(ProgramProperties.get().catalogInformation_intensity())));
 		block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.DURATION + "/atpDurationTypeKey", new MessageKeyInfo(ProgramProperties.get().catalogInformation_stdDuration())));
 		block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.DURATION + "/timeQuantity", new MessageKeyInfo(ProgramProperties.get().catalogInformation_durationCount())));
 		block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.DURATION_NOTES, new MessageKeyInfo(ProgramProperties.get().catalogInformation_durationNotes())));
 		block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.MORE_INFORMATION, new MessageKeyInfo(ProgramProperties.get().catalogInformation_referenceUrl())));

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
}
