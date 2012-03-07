package org.kuali.student.lum.program.client.major.edit;

import org.kuali.student.common.ui.client.configurable.mvc.Configurer;
import org.kuali.student.common.ui.client.configurable.mvc.sections.GroupSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.lum.common.client.configuration.AbstractSectionConfiguration;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramMsgConstants;
import org.kuali.student.lum.program.client.ProgramSections;

public class CatalogInformationEditConfiguration extends AbstractSectionConfiguration {

    public CatalogInformationEditConfiguration(Configurer configurer) {
        this.setConfigurer(configurer);
        rootSection = new VerticalSectionView(ProgramSections.CATALOG_INFO_EDIT, getLabel(ProgramMsgConstants.PROGRAM_MENU_SECTIONS_CATALOGINFO), ProgramConstants.PROGRAM_MODEL_ID);
    }

    @Override
    protected void buildLayout() {
        configurer.addField(rootSection, ProgramConstants.DESCRIPTION + "/plain", generateMessageInfo(ProgramMsgConstants.CATALOGINFORMATION_DESCR));
        configurer.addField(rootSection, ProgramConstants.CATALOG_DESCRIPTION + "/plain", generateMessageInfo(ProgramMsgConstants.CATALOGINFORMATION_CATALOGDESCR));
        configurer.addField(rootSection, ProgramConstants.CORE_FACULTY_MEMBERS, generateMessageInfo(ProgramMsgConstants.CATALOGINFORMATION_PUBLISHEDINSTRUCTORS));
        configurer.addField(rootSection, ProgramConstants.PUBLICATION_TARGETS, generateMessageInfo(ProgramMsgConstants.CATALOGINFORMATION_CATALOGPUBLICATIONTARGETS));
        configurer.addField(rootSection, ProgramConstants.FULL_PART_TIME, generateMessageInfo(ProgramMsgConstants.CATALOGINFORMATION_INTENSITY));
        GroupSection duration_group = new GroupSection();
        configurer.addField(duration_group, ProgramConstants.DURATION + "/atpDurationTypeKey", generateMessageInfo(ProgramMsgConstants.CATALOGINFORMATION_STDDURATION));
        configurer.addField(duration_group, ProgramConstants.DURATION + "/timeQuantity", generateMessageInfo(ProgramMsgConstants.CATALOGINFORMATION_DURATIONCOUNT));
        rootSection.addSection(duration_group);
        configurer.addField(rootSection, ProgramConstants.DURATION_NOTES, generateMessageInfo(ProgramMsgConstants.CATALOGINFORMATION_DURATIONNOTES));
        configurer.addField(rootSection, ProgramConstants.MORE_INFORMATION, generateMessageInfo(ProgramMsgConstants.CATALOGINFORMATION_REFERENCEURL));
    }
}
