package org.kuali.student.lum.program.client.bacc.edit;

import org.kuali.student.common.ui.client.configurable.mvc.sections.HorizontalSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.lum.common.client.configuration.AbstractSectionConfiguration;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.properties.ProgramProperties;

/**
 * @author Igor
 */
public class BaccManagingBodiesEditConfiguration extends AbstractSectionConfiguration {

    public BaccManagingBodiesEditConfiguration() {
        rootSection = new VerticalSectionView(ProgramSections.MANAGE_BODIES_EDIT, ProgramProperties.get().program_menu_sections_managingBodies(), ProgramConstants.PROGRAM_MODEL_ID);
    }

    @Override
    protected void buildLayout() {
        HorizontalSection horizontalSection = new HorizontalSection();
        horizontalSection.addSection(createLeftSection());
        horizontalSection.addSection(createRightSection());
        rootSection.addSection(horizontalSection);
    }

    private VerticalSection createLeftSection() {
        VerticalSection section = new VerticalSection();
        configurer.addField(section, ProgramConstants.CURRICULUM_OVERSIGHT_UNIT, new MessageKeyInfo(ProgramProperties.get().managingBodies_curriculumOversightUnit()));
        configurer.addField(section, ProgramConstants.STUDENT_OVERSIGHT_UNIT, new MessageKeyInfo(ProgramProperties.get().managingBodies_studentOversightUnit()));
        return section;
    }

    private VerticalSection createRightSection() {
        VerticalSection section = new VerticalSection();
        configurer.addField(section, ProgramConstants.CURRICULUM_OVERSIGHT_DIVISION, new MessageKeyInfo(ProgramProperties.get().managingBodies_curriculumOversightDivision()));
        configurer.addField(section, ProgramConstants.STUDENT_OVERSIGHT_DIVISION, new MessageKeyInfo(ProgramProperties.get().managingBodies_studentOversightDivision()));
        return section;
    }
}
