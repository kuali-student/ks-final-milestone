package org.kuali.student.lum.program.client.major.view;

import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.sections.GroupSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.TableSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.common.client.configuration.AbstractSectionConfiguration;
import org.kuali.student.lum.program.client.properties.ProgramProperties;

/**
 * @author Igor
 */
public class CatalogInformationViewConfiguration extends AbstractSectionConfiguration {

    public CatalogInformationViewConfiguration() {
        rootSection = new VerticalSectionView(ProgramSections.CATALOG_INFO_VIEW, ProgramProperties.get().program_menu_sections_catalogInfo(), ProgramConstants.PROGRAM_MODEL_ID);
    }

    @Override
    protected void buildLayout() {
        TableSection section = new TableSection(SectionTitle.generateEmptyTitle());
        configurer.addReadOnlyField(section, ProgramConstants.DESCRIPTION + "/plain", new MessageKeyInfo(ProgramProperties.get().catalogInformation_descr()));
        configurer.addReadOnlyField(section, ProgramConstants.CATALOG_DESCRIPTION, new MessageKeyInfo(ProgramProperties.get().catalogInformation_catalogDescr()));
        configurer.addReadOnlyField(section, ProgramConstants.CORE_FACULTY_MEMBERS, new MessageKeyInfo(ProgramProperties.get().catalogInformation_publishedInstructors()));
        configurer.addReadOnlyField(section, ProgramConstants.PUBLICATION_TARGETS, new MessageKeyInfo(ProgramProperties.get().catalogInformation_catalogPublicationTargets()));
        configurer.addReadOnlyField(section, ProgramConstants.FULL_PART_TIME, new MessageKeyInfo(ProgramProperties.get().catalogInformation_intensity()));
        GroupSection duration_group = new GroupSection();
        configurer.addReadOnlyField(duration_group, ProgramConstants.DURATION + "/" + "atpDurationTypeKey", new MessageKeyInfo(ProgramProperties.get().catalogInformation_stdDuration()));
        configurer.addReadOnlyField(duration_group, ProgramConstants.DURATION + "/" + "timeQuantity", new MessageKeyInfo(ProgramProperties.get().catalogInformation_durationCount()));
        section.addSection(duration_group);
        configurer.addReadOnlyField(section, ProgramConstants.DURATION_NOTES, new MessageKeyInfo(ProgramProperties.get().catalogInformation_durationNotes()));        
        configurer.addReadOnlyField(section, ProgramConstants.MORE_INFORMATION, new MessageKeyInfo(ProgramProperties.get().catalogInformation_referenceUrl()));
        rootSection.addSection(section);
    }
}
