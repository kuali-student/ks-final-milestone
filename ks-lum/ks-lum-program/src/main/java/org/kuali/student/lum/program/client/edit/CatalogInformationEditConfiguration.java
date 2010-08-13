package org.kuali.student.lum.program.client.edit;

import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.configuration.base.AbstractConfiguration;
import org.kuali.student.lum.program.client.properties.ProgramProperties;
import org.kuali.student.lum.program.client.view.ProgramViewConfigurer;

public class CatalogInformationEditConfiguration extends AbstractConfiguration<ProgramViewConfigurer> {
    private VerticalSectionView editView;

    public CatalogInformationEditConfiguration() {
    	super();
    }
    
    @Override
    public View getView() {
        if (editView == null) {
        	editView = createEditView();
        }
        return editView;
    }
    
    private VerticalSectionView createEditView() {
    	VerticalSectionView view = new VerticalSectionView(ProgramSections.CATALOG_INFO_EDIT, ProgramProperties.get().program_menu_sections_catalogInfo(), ProgramConstants.PROGRAM_MODEL_ID);
        configurer.addField(view, ProgramConstants.DESCRIPTION, new MessageKeyInfo(ProgramProperties.get().catalogInformation_descr()));
        configurer.addField(view, ProgramConstants.CATALOG_DESCRIPTION, new MessageKeyInfo(ProgramProperties.get().catalogInformation_catalogDescr()));
//@TODO show Core Faculty Members if not empty
        configurer.addField(view, ProgramConstants.CORE_FACULTY_MEMBERS, new MessageKeyInfo(ProgramProperties.get().catalogInformation_publishedInstructors()));
//@TODO "Not Published" disallows Catalog checkboxes
        configurer.addField(view, ProgramConstants.PUBLICATION_TARGETS, new MessageKeyInfo(ProgramProperties.get().catalogInformation_catalogPublicationTargets()));
        configurer.addField(view, ProgramConstants.FULL_PART_TIME, new MessageKeyInfo(ProgramProperties.get().catalogInformation_intensity()));
        configurer.addField(view, ProgramConstants.DURATION, new MessageKeyInfo(ProgramProperties.get().catalogInformation_stdDuration()));
        configurer.addField(view, ProgramConstants.MORE_INFORMATION, new MessageKeyInfo(ProgramProperties.get().catalogInformation_referenceUrl()));
    	return view;
    }

}
