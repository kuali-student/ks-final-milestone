package org.kuali.student.lum.program.client.core.view;

import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.lum.common.client.configuration.AbstractSectionConfiguration;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.core.CoreEditableHeader;
import org.kuali.student.lum.program.client.properties.ProgramProperties;
import org.kuali.student.lum.program.client.widgets.EditableHeader;

/**
 * @author Igor
 */
public class CoreManagingBodiesViewConfiguration extends AbstractSectionConfiguration {
 public static CoreManagingBodiesViewConfiguration create() {
        return new CoreManagingBodiesViewConfiguration(new VerticalSectionView(ProgramSections.MANAGE_BODIES_VIEW, ProgramProperties.get().program_menu_sections_managingBodies(), ProgramConstants.PROGRAM_MODEL_ID));
    }

    public static CoreManagingBodiesViewConfiguration createSpecial() {
        String title = ProgramProperties.get().program_menu_sections_managingBodies();
        return new CoreManagingBodiesViewConfiguration(new VerticalSectionView(ProgramSections.MANAGE_BODIES_VIEW, title, ProgramConstants.PROGRAM_MODEL_ID, new CoreEditableHeader(title, ProgramSections.MANAGE_BODIES_EDIT)));
    }

    private CoreManagingBodiesViewConfiguration(SectionView sectionView) {
        rootSection = sectionView;
    }

    @Override
    protected void buildLayout() {
        VerticalSection section = createMainSection();
        rootSection.addSection(section);
    }

    private VerticalSection createMainSection() {
        VerticalSection section = new VerticalSection();
        configurer.addReadOnlyField(section, ProgramConstants.CURRICULUM_OVERSIGHT_DIVISION, new MessageKeyInfo(ProgramProperties.get().managingBodies_curriculumOversightUnit()));
        configurer.addReadOnlyField(section, ProgramConstants.CURRICULUM_OVERSIGHT_UNIT, new MessageKeyInfo(ProgramProperties.get().managingBodies_curriculumOversightDivision()));
        configurer.addReadOnlyField(section, ProgramConstants.STUDENT_OVERSIGHT_DIVISION, new MessageKeyInfo(ProgramProperties.get().managingBodies_studentOversightDivision()));
        configurer.addReadOnlyField(section, ProgramConstants.STUDENT_OVERSIGHT_UNIT, new MessageKeyInfo(ProgramProperties.get().managingBodies_studentOversightUnit()));
        return section;
    }
}
