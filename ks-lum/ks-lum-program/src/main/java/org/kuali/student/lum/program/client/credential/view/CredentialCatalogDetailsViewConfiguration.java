package org.kuali.student.lum.program.client.credential.view;

import org.kuali.student.common.ui.client.configurable.mvc.Configurer;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.sections.TableSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.lum.common.client.configuration.AbstractSectionConfiguration;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramMsgConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.credential.CredentialEditableHeader;

/**
 * @author Igor
 */
public class CredentialCatalogDetailsViewConfiguration extends AbstractSectionConfiguration {

    public static CredentialCatalogDetailsViewConfiguration create(Configurer configurer) {
        return new CredentialCatalogDetailsViewConfiguration(configurer, false);
    }

    public static CredentialCatalogDetailsViewConfiguration createSpecial(Configurer configurer) {
        return new CredentialCatalogDetailsViewConfiguration(configurer, false);
    }

    private CredentialCatalogDetailsViewConfiguration(Configurer configurer, boolean isSpecial) {
        this.setConfigurer(configurer);
        String title = getLabel(ProgramMsgConstants.PROGRAM_MENU_SECTIONS_CATALOGINFO);
        if (!isSpecial){
            this.rootSection = new VerticalSectionView(ProgramSections.CATALOG_INFO_VIEW, title, 
                    ProgramConstants.PROGRAM_MODEL_ID);
        } else {
            this.rootSection = new VerticalSectionView(ProgramSections.CATALOG_INFO_VIEW, title, 
                    ProgramConstants.PROGRAM_MODEL_ID, new CredentialEditableHeader(title, ProgramSections.CATALOG_INFO_EDIT));
        }
    }

    @Override
    protected void buildLayout() {
        TableSection section = new TableSection(SectionTitle.generateEmptyTitle());
        configurer.addReadOnlyField(section, ProgramConstants.DESCRIPTION + "/plain", generateMessageInfo(ProgramMsgConstants.CATALOGINFORMATION_DESCR));
        configurer.addReadOnlyField(section, ProgramConstants.MORE_INFORMATION, generateMessageInfo(ProgramMsgConstants.CATALOGINFORMATION_REFERENCEURL));
        rootSection.addSection(section);
    }
}
