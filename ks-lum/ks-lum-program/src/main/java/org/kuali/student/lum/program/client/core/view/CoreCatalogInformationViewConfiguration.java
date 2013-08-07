package org.kuali.student.lum.program.client.core.view;

import org.kuali.student.common.ui.client.configurable.mvc.Configurer;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.sections.TableSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.lum.common.client.configuration.AbstractSectionConfiguration;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramMsgConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.core.CoreEditableHeader;

/**
 * @author Igor
 */
public class CoreCatalogInformationViewConfiguration extends AbstractSectionConfiguration {

     public static CoreCatalogInformationViewConfiguration create(Configurer configurer) {
        return new CoreCatalogInformationViewConfiguration(configurer, false);
    }

    public static CoreCatalogInformationViewConfiguration createSpecial(Configurer configurer) {
        return new CoreCatalogInformationViewConfiguration(configurer, true);
    }

    private CoreCatalogInformationViewConfiguration(Configurer configurer, boolean isSpecial) {
        this.setConfigurer(configurer);
        String title = getLabel(ProgramMsgConstants.PROGRAM_MENU_SECTIONS_CATALOGINFO);
        if (!isSpecial){
            this.rootSection = new VerticalSectionView(ProgramSections.CATALOG_INFO_VIEW, title, 
                    ProgramConstants.PROGRAM_MODEL_ID);
        } else {
            this.rootSection = new VerticalSectionView(ProgramSections.CATALOG_INFO_VIEW, title, 
                    ProgramConstants.PROGRAM_MODEL_ID, new CoreEditableHeader(title, ProgramSections.CATALOG_INFO_EDIT));
        }
    }

    @Override
    protected void buildLayout() {
        TableSection section = new TableSection(SectionTitle.generateEmptyTitle());
        configurer.addReadOnlyField(section, ProgramConstants.DESCRIPTION + "/plain", generateMessageInfo(ProgramMsgConstants.CATALOGINFORMATION_DESCR));
        configurer.addReadOnlyField(section, ProgramConstants.CATALOG_DESCRIPTION + "/plain", generateMessageInfo(ProgramMsgConstants.CATALOGINFORMATION_CATALOGDESCR));
        configurer.addReadOnlyField(section, ProgramConstants.PUBLICATION_TARGETS, generateMessageInfo(ProgramMsgConstants.CATALOGINFORMATION_CATALOGPUBLICATIONTARGETS));
        configurer.addReadOnlyField(section, ProgramConstants.MORE_INFORMATION, generateMessageInfo(ProgramMsgConstants.CATALOGINFORMATION_REFERENCEURL));
        rootSection.addSection(section);
    }
}
