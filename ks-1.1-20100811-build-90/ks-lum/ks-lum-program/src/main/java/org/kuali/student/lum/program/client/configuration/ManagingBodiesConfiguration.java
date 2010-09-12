package org.kuali.student.lum.program.client.configuration;

import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.sections.BaseSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.CollapsableSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.HorizontalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.lum.program.client.KSPickerBinding;
import org.kuali.student.lum.program.client.view.ProgramViewConfigurer;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.configuration.base.AbstractConfiguration;
import org.kuali.student.lum.program.client.configuration.base.EditableConfiguration;
import org.kuali.student.lum.program.client.properties.ProgramProperties;

/**
 * @author Igor
 */
public class ManagingBodiesConfiguration extends AbstractConfiguration<ProgramViewConfigurer> implements EditableConfiguration<ProgramViewConfigurer> {

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
        CollapsableSection collapsableSection = new CollapsableSection(ProgramProperties.get().managingBodies_seeAll());
        configurer.addField(collapsableSection, ProgramConstants.STUDENT_OVERSIGHT_UNIT, new MessageKeyInfo(ProgramProperties.get().managingBodies_curriculumOversightDivision()));
        editView.addSection(section);
        editView.addSection(collapsableSection);

    }

    private void createShowView() {
        showView = new VerticalSectionView(ProgramSections.MANAGE_BODIES_VIEW, ProgramProperties.get().program_menu_sections_managingBodies(), ProgramConstants.PROGRAM_MODEL_ID);
        HorizontalSection section = new HorizontalSection();
        addField(section, ProgramConstants.STUDENT_OVERSIGHT_UNIT, ProgramProperties.get().managingBodies_curriculumOversightUnit());
        addField(section, ProgramConstants.STUDENT_OVERSIGHT_DIVISION, ProgramProperties.get().managingBodies_curriculumOversightDivision());
        CollapsableSection collapsableSection = new CollapsableSection(ProgramProperties.get().managingBodies_seeAll());
        addField(collapsableSection, ProgramConstants.STUDENT_OVERSIGHT_DIVISION, ProgramProperties.get().managingBodies_curriculumOversightDivision());
        showView.addSection(section);
        showView.addSection(collapsableSection);
    }

    private void addField(BaseSection section, String constant, String label) {
        FieldDescriptor fieldDescriptor = configurer.addField(section, constant, new MessageKeyInfo(label), new KSLabel());
        fieldDescriptor.setWidgetBinding(new KSPickerBinding());
    }
}
