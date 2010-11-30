package org.kuali.student.lum.program.client.credential.view;

import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.sections.TableSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.lum.common.client.configuration.AbstractSectionConfiguration;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.credential.CredentialEditableHeader;
import org.kuali.student.lum.program.client.properties.ProgramProperties;

/**
 * @author Igor
 */
public class CredentialCatalogDetailsViewConfiguration extends AbstractSectionConfiguration {

    public static CredentialCatalogDetailsViewConfiguration create() {
        return new CredentialCatalogDetailsViewConfiguration(new VerticalSectionView(ProgramSections.CATALOG_INFO_VIEW, ProgramProperties.get().program_menu_sections_catalogInfo(), ProgramConstants.PROGRAM_MODEL_ID));
    }

    public static CredentialCatalogDetailsViewConfiguration createSpecial() {
        String title = ProgramProperties.get().program_menu_sections_catalogInfo();
        return new CredentialCatalogDetailsViewConfiguration(new VerticalSectionView(ProgramSections.CATALOG_INFO_VIEW, title, ProgramConstants.PROGRAM_MODEL_ID, new CredentialEditableHeader(title, ProgramSections.CATALOG_INFO_EDIT)));
    }

    private CredentialCatalogDetailsViewConfiguration(SectionView sectionView) {
        rootSection = sectionView;
    }

    @Override
    protected void buildLayout() {
        TableSection section = new TableSection(SectionTitle.generateEmptyTitle());
        configurer.addReadOnlyField(section, ProgramConstants.DESCRIPTION + "/plain", new MessageKeyInfo(ProgramProperties.get().catalogInformation_descr()));
        configurer.addReadOnlyField(section, ProgramConstants.MORE_INFORMATION, new MessageKeyInfo(ProgramProperties.get().catalogInformation_referenceUrl()));
        rootSection.addSection(section);
    }
}
