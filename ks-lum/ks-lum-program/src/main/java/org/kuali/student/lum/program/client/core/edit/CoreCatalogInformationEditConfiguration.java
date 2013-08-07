package org.kuali.student.lum.program.client.core.edit;

import org.kuali.student.common.ui.client.configurable.mvc.Configurer;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.lum.common.client.configuration.AbstractSectionConfiguration;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramMsgConstants;
import org.kuali.student.lum.program.client.ProgramSections;

/**
 * @author Igor
 */
public class CoreCatalogInformationEditConfiguration extends AbstractSectionConfiguration {

    public CoreCatalogInformationEditConfiguration(Configurer configurer) {
        this.setConfigurer(configurer);
        rootSection = new VerticalSectionView(ProgramSections.CATALOG_INFO_EDIT, getLabel(ProgramMsgConstants.PROGRAM_MENU_SECTIONS_CATALOGINFO), 
                ProgramConstants.PROGRAM_MODEL_ID);
    }

    @Override
    protected void buildLayout() {
        configurer.addField(rootSection, ProgramConstants.DESCRIPTION + "/plain", generateMessageInfo(ProgramMsgConstants.CATALOGINFORMATION_DESCR));
        configurer.addField(rootSection, ProgramConstants.CATALOG_DESCRIPTION + "/plain", generateMessageInfo(ProgramMsgConstants.CATALOGINFORMATION_CATALOGDESCR));
        configurer.addField(rootSection, ProgramConstants.PUBLICATION_TARGETS, generateMessageInfo(ProgramMsgConstants.CATALOGINFORMATION_CATALOGPUBLICATIONTARGETS));
        configurer.addField(rootSection, ProgramConstants.MORE_INFORMATION, generateMessageInfo(ProgramMsgConstants.CATALOGINFORMATION_REFERENCEURL));
    }
}
