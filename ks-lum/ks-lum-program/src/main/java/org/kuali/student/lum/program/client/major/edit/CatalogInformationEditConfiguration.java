package org.kuali.student.lum.program.client.major.edit;

import org.kuali.student.common.ui.client.configurable.mvc.sections.GroupSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.lum.common.client.configuration.AbstractSectionConfiguration;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.properties.ProgramProperties;

public class CatalogInformationEditConfiguration extends AbstractSectionConfiguration {

     private String parentPath = null;

    private CatalogInformationEditConfiguration() {
        rootSection = new VerticalSectionView(ProgramSections.CATALOG_INFO_EDIT, ProgramProperties.get().program_menu_sections_catalogInfo(), ProgramConstants.PROGRAM_MODEL_ID);
    }

 public CatalogInformationEditConfiguration (String parentPath) {
  this ();
  this.parentPath = parentPath;
 }

    @Override
    protected void buildLayout() {
        configurer.addField(rootSection, ProgramConstants.DESCRIPTION + "/plain", new MessageKeyInfo(ProgramProperties.get().catalogInformation_descr()), null, parentPath);
        configurer.addField(rootSection, ProgramConstants.CATALOG_DESCRIPTION + "/plain", new MessageKeyInfo(ProgramProperties.get().catalogInformation_catalogDescr()), null, parentPath);
        configurer.addField(rootSection, ProgramConstants.CORE_FACULTY_MEMBERS, new MessageKeyInfo(ProgramProperties.get().catalogInformation_publishedInstructors()), null, parentPath);
        configurer.addField(rootSection, ProgramConstants.PUBLICATION_TARGETS, new MessageKeyInfo(ProgramProperties.get().catalogInformation_catalogPublicationTargets()), null, parentPath);
        configurer.addField(rootSection, ProgramConstants.FULL_PART_TIME, new MessageKeyInfo(ProgramProperties.get().catalogInformation_intensity()), null, parentPath);
        GroupSection duration_group = new GroupSection();
        configurer.addField(duration_group, ProgramConstants.DURATION + "/atpDurationTypeKey", new MessageKeyInfo(ProgramProperties.get().catalogInformation_stdDuration()), null, parentPath);
        configurer.addField(duration_group, ProgramConstants.DURATION + "/timeQuantity", new MessageKeyInfo(ProgramProperties.get().catalogInformation_durationCount()), null, parentPath);
        rootSection.addSection(duration_group);
        configurer.addField(rootSection, ProgramConstants.DURATION_NOTES, new MessageKeyInfo(ProgramProperties.get().catalogInformation_durationNotes()), null, parentPath);
        configurer.addField(rootSection, ProgramConstants.MORE_INFORMATION, new MessageKeyInfo(ProgramProperties.get().catalogInformation_referenceUrl()), null, parentPath);
    }
}
