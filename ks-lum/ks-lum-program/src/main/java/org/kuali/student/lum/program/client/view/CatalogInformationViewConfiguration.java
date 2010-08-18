package org.kuali.student.lum.program.client.view;

import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.configuration.base.AbstractConfiguration;
import org.kuali.student.lum.program.client.properties.ProgramProperties;

/**
 * @author Igor
 */
public class CatalogInformationViewConfiguration extends AbstractConfiguration<ProgramViewConfigurer> {

    private VerticalSectionView mainSection;

    @Override
    public View getView() {
        createShowView();
        return mainSection;
    }

    private void createShowView() {
        mainSection = new VerticalSectionView(ProgramSections.CATALOG_INFO_VIEW, ProgramProperties.get().program_menu_sections_catalogInfo(), ProgramConstants.PROGRAM_MODEL_ID);
        configurer.addReadOnlyField(mainSection, ProgramConstants.DESCRIPTION + "/plain", new MessageKeyInfo(ProgramProperties.get().catalogInformation_descr()));
        configurer.addReadOnlyField(mainSection, ProgramConstants.CATALOG_DESCRIPTION, new MessageKeyInfo(ProgramProperties.get().catalogInformation_catalogDescr()));
        configurer.addReadOnlyField(mainSection, ProgramConstants.CORE_FACULTY_MEMBERS, new MessageKeyInfo(ProgramProperties.get().catalogInformation_publishedInstructors()));
        configurer.addReadOnlyField(mainSection, ProgramConstants.PUBLICATION_TARGETS, new MessageKeyInfo(ProgramProperties.get().catalogInformation_catalogPublicationTargets()));
        configurer.addReadOnlyField(mainSection, ProgramConstants.FULL_PART_TIME, new MessageKeyInfo(ProgramProperties.get().catalogInformation_intensity()));
        configurer.addReadOnlyField(mainSection, ProgramConstants.DURATION, new MessageKeyInfo(ProgramProperties.get().catalogInformation_stdDuration()));
        configurer.addReadOnlyField(mainSection, ProgramConstants.MORE_INFORMATION, new MessageKeyInfo(ProgramProperties.get().catalogInformation_referenceUrl()));
    }
}
