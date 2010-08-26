package org.kuali.student.lum.program.client.view;

import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityConfiguration;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityConfiguration.MultiplicityType;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityConfiguration.StyleType;
import org.kuali.student.common.ui.client.configurable.mvc.sections.MultiplicitySection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.framework.AbstractSectionConfiguration;
import org.kuali.student.lum.program.client.properties.ProgramProperties;

/**
 * @author Igor
 */
public class SpecializationsViewConfiguration extends AbstractSectionConfiguration<ProgramViewConfigurer> {

    public SpecializationsViewConfiguration() {
        rootSection = new VerticalSectionView(ProgramSections.SPECIALIZATIONS, ProgramProperties.get().program_menu_sections_specializations(), ProgramConstants.PROGRAM_MODEL_ID);
    }

    @Override
    protected void buildLayout() {

        VerticalSection section = new VerticalSection();
        section.addSection(createSpecializationsSection());

        rootSection.addSection(section);
    }

     private Section createSpecializationsSection() {

         MultiplicityConfiguration config = new MultiplicityConfiguration(MultiplicityType.TABLE, StyleType.BORDERLESS_TABLE, getMetaData(ProgramConstants.VARIATIONS));
         config.setShowHeaders(false);
         config.setTitle(SectionTitle.generateH4Title(ProgramProperties.get().programSpecialization_instructions()));

         config.setParent(ProgramConstants.VARIATIONS , null,  null, getMetaData(ProgramConstants.VARIATIONS));
         
         config.addField(ProgramConstants.CODE, ProgramProperties.get().programInformation_code(), ProgramConstants.VARIATIONS,
                 getMetaData(ProgramConstants.CODE));
         config.addField(ProgramConstants.LONG_TITLE, ProgramProperties.get().programInformation_titleFull(), ProgramConstants.VARIATIONS,
                 getMetaData(ProgramConstants.LONG_TITLE));
         MultiplicitySection section = new MultiplicitySection(config);

         return section;
     }

    private Metadata getMetaData(String fieldKey) {
        QueryPath path = QueryPath.concat(fieldKey);
        return configurer.getModelDefinition().getMetadata(path);
    }



    


}
