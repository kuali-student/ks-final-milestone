package org.kuali.student.lum.program.client.major.view;

import org.kuali.student.r1.common.assembly.data.Metadata;
import org.kuali.student.r1.common.assembly.data.QueryPath;
import org.kuali.student.common.ui.client.configurable.mvc.Configurer;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptorReadOnly;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBinding;
import org.kuali.student.common.ui.client.configurable.mvc.sections.TableSection;
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
import org.kuali.student.lum.program.client.major.edit.MajorEditController;
import org.kuali.student.lum.program.client.major.proposal.MajorProposalController;

import com.google.gwt.user.client.ui.Widget;

/**
 * @author Igor
 */
public class CatalogInformationViewConfiguration extends AbstractSectionConfiguration {

	private Controller controller = null;

    public static CatalogInformationViewConfiguration create(Configurer configurer) {
        return new CatalogInformationViewConfiguration(configurer);
    }

    public static CatalogInformationViewConfiguration createSpecial(Configurer configurer, Controller controller) {
        return new CatalogInformationViewConfiguration(configurer, controller);
    }

    private CatalogInformationViewConfiguration(Configurer configurer) {
        this.setConfigurer(configurer);
        String title = getLabel(ProgramMsgConstants.PROGRAM_MENU_SECTIONS_CATALOGINFO);
        rootSection = new VerticalSectionView(ProgramSections.CATALOG_INFO_VIEW, title, ProgramConstants.PROGRAM_MODEL_ID);
    }

    private CatalogInformationViewConfiguration(Configurer configurer, Controller controller) {
        this.setConfigurer(configurer);
        String title = getLabel(ProgramMsgConstants.PROGRAM_MENU_SECTIONS_CATALOGINFO);
        rootSection = new VerticalSectionView(ProgramSections.CATALOG_INFO_VIEW, title, ProgramConstants.PROGRAM_MODEL_ID, new MajorEditableHeader(title, ProgramSections.CATALOG_INFO_EDIT));
        this.controller = controller;
    }

    @Override
    protected void buildLayout() {
    	if (controller instanceof MajorProposalController || controller instanceof MajorEditController) 
    		rootSection.addSection(createCatalogInformationSectionEdit());
    	else
    	{
    		TableSection section = new TableSection(SectionTitle.generateEmptyTitle());
       		configurer.addReadOnlyField(section, ProgramConstants.DESCRIPTION + "/plain", generateMessageInfo(ProgramMsgConstants.CATALOGINFORMATION_DESCR));
       		configurer.addReadOnlyField(section, ProgramConstants.CATALOG_DESCRIPTION + "/" + ProgramConstants.PLAIN_TEXT, generateMessageInfo(ProgramMsgConstants.CATALOGINFORMATION_CATALOGDESCR));
       		configurer.addReadOnlyField(section, ProgramConstants.CORE_FACULTY_MEMBERS, generateMessageInfo(ProgramMsgConstants.CATALOGINFORMATION_PUBLISHEDINSTRUCTORS));
       		configurer.addReadOnlyField(section, ProgramConstants.PUBLICATION_TARGETS, generateMessageInfo(ProgramMsgConstants.CATALOGINFORMATION_CATALOGPUBLICATIONTARGETS));
       		configurer.addReadOnlyField(section, ProgramConstants.FULL_PART_TIME, generateMessageInfo(ProgramMsgConstants.CATALOGINFORMATION_INTENSITY));
       		configurer.addReadOnlyField(section, ProgramConstants.DURATION + "/atpDurationTypeKey", generateMessageInfo(ProgramMsgConstants.CATALOGINFORMATION_STDDURATION));
       		configurer.addReadOnlyField(section, ProgramConstants.DURATION + "/timeQuantity", generateMessageInfo(ProgramMsgConstants.CATALOGINFORMATION_DURATIONCOUNT));
       		configurer.addReadOnlyField(section, ProgramConstants.DURATION_NOTES, generateMessageInfo(ProgramMsgConstants.CATALOGINFORMATION_DURATIONNOTES));
       		configurer.addReadOnlyField(section, ProgramConstants.MORE_INFORMATION, generateMessageInfo(ProgramMsgConstants.CATALOGINFORMATION_REFERENCEURL));
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

  	public SummaryTableFieldBlock createCatalogInformationSectionEditBlock() {
  		SummaryTableFieldBlock block = new SummaryTableFieldBlock();
  		block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.DESCRIPTION + "/plain", generateMessageInfo(ProgramMsgConstants.CATALOGINFORMATION_DESCR)));
  		block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.CATALOG_DESCRIPTION + "/" + ProgramConstants.PLAIN_TEXT, generateMessageInfo(ProgramMsgConstants.CATALOGINFORMATION_CATALOGDESCR)));
  		block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.CORE_FACULTY_MEMBERS, generateMessageInfo(ProgramMsgConstants.CATALOGINFORMATION_PUBLISHEDINSTRUCTORS)));
  		block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.PUBLICATION_TARGETS, generateMessageInfo(ProgramMsgConstants.CATALOGINFORMATION_CATALOGPUBLICATIONTARGETS)));
  		block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.FULL_PART_TIME, generateMessageInfo(ProgramMsgConstants.CATALOGINFORMATION_INTENSITY)));
 		block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.DURATION + "/atpDurationTypeKey", generateMessageInfo(ProgramMsgConstants.CATALOGINFORMATION_STDDURATION)));
 		block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.DURATION + "/timeQuantity", generateMessageInfo(ProgramMsgConstants.CATALOGINFORMATION_DURATIONCOUNT)));
 		block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.DURATION_NOTES, generateMessageInfo(ProgramMsgConstants.CATALOGINFORMATION_DURATIONNOTES)));
 		block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.MORE_INFORMATION, generateMessageInfo(ProgramMsgConstants.CATALOGINFORMATION_REFERENCEURL)));

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
