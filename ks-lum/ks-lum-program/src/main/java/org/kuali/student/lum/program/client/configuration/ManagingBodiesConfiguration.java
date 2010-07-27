package org.kuali.student.lum.program.client.configuration;

import org.kuali.student.common.ui.client.configurable.mvc.sections.HorizontalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.common.ui.client.widgets.search.KSPickerView;
import org.kuali.student.lum.program.client.ProgramConfigurer;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.configuration.base.AbstractConfiguration;
import org.kuali.student.lum.program.client.configuration.base.EditableConfiguration;
import org.kuali.student.lum.program.client.properties.ProgramProperties;

/**
 * @author Igor
 */
public class ManagingBodiesConfiguration extends AbstractConfiguration<ProgramConfigurer> implements EditableConfiguration<ProgramConfigurer> {

    private VerticalSectionView showView;

    private VerticalSectionView editView;

    @Override
    public View getView() {
        if (showView == null) {
            createShowView();
        }
        return showView;
    }

    @Override
    public View getEditView() {
        if (editView == null) {
            createEditView();
        }
        return editView;
    }

    private void createEditView() {
        editView = new VerticalSectionView(ProgramSections.MANAGE_BODIES_EDIT, ProgramProperties.get().program_menu_sections_managingBodies(), ProgramConstants.PROGRAM_MODEL_ID);
        HorizontalSection section = new HorizontalSection();
        configurer.addField(section, ProgramConstants.STUDENT_OVERSIGHT_UNIT, new MessageKeyInfo(ProgramProperties.get().managingBodies_curriculumOversightDivision()));
        configurer.addField(section, ProgramConstants.STUDENT_OVERSIGHT_DIVISION, new MessageKeyInfo(ProgramProperties.get().managingBodies_curriculumOversightDivision()));
        editView.addSection(section);

    }

    private void createShowView() {
        showView = new VerticalSectionView(ProgramSections.MANAGE_BODIES_VIEW, ProgramProperties.get().program_menu_sections_managingBodies(), ProgramConstants.PROGRAM_MODEL_ID);
        HorizontalSection section = new HorizontalSection();
        configurer.addField(section, ProgramConstants.STUDENT_OVERSIGHT_UNIT, new MessageKeyInfo(ProgramProperties.get().managingBodies_curriculumOversightDivision()), new KSPickerView());
        configurer.addField(section, ProgramConstants.STUDENT_OVERSIGHT_DIVISION, new MessageKeyInfo(ProgramProperties.get().managingBodies_curriculumOversightDivision()), new KSPickerView());
        showView.addSection(section);
    }
}
