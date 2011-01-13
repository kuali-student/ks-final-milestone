package org.kuali.student.lum.program.client.major.edit;

import org.kuali.student.common.ui.client.configurable.mvc.sections.GroupSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.lum.common.client.configuration.AbstractSectionConfiguration;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.properties.ProgramProperties;

public class CatalogInformationEditConfiguration extends AbstractSectionConfiguration {

    public CatalogInformationEditConfiguration() {
        rootSection = new VerticalSectionView(ProgramSections.CATALOG_INFO_EDIT, ProgramProperties.get().program_menu_sections_catalogInfo(), ProgramConstants.PROGRAM_MODEL_ID);
    }

    @Override
    protected void buildLayout() {
        configurer.addField(rootSection, ProgramConstants.DESCRIPTION + "/plain", new MessageKeyInfo(ProgramProperties.get().catalogInformation_descr()));
        configurer.addField(rootSection, ProgramConstants.CATALOG_DESCRIPTION + "/plain", new MessageKeyInfo(ProgramProperties.get().catalogInformation_catalogDescr()));
        configurer.addField(rootSection, ProgramConstants.CORE_FACULTY_MEMBERS, new MessageKeyInfo(ProgramProperties.get().catalogInformation_publishedInstructors()));
        configurer.addField(rootSection, ProgramConstants.PUBLICATION_TARGETS, new MessageKeyInfo(ProgramProperties.get().catalogInformation_catalogPublicationTargets()));
        configurer.addField(rootSection, ProgramConstants.FULL_PART_TIME, new MessageKeyInfo(ProgramProperties.get().catalogInformation_intensity()));
        GroupSection duration_group = new GroupSection();
        configurer.addField(duration_group, ProgramConstants.DURATION + "/atpDurationTypeKey", new MessageKeyInfo(ProgramProperties.get().catalogInformation_stdDuration()));
        configurer.addField(duration_group, ProgramConstants.DURATION + "/timeQuantity", new MessageKeyInfo(ProgramProperties.get().catalogInformation_durationCount()));
        rootSection.addSection(duration_group);
        configurer.addField(rootSection, ProgramConstants.DURATION_NOTES, new MessageKeyInfo(ProgramProperties.get().catalogInformation_durationNotes()));
        configurer.addField(rootSection, ProgramConstants.MORE_INFORMATION, new MessageKeyInfo(ProgramProperties.get().catalogInformation_referenceUrl()));
    }
}
