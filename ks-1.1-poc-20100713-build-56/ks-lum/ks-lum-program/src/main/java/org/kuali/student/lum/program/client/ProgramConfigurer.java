package org.kuali.student.lum.program.client;

import com.google.gwt.user.client.ui.Label;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.core.workflow.ui.client.widgets.WorkflowEnhancedController;
import org.kuali.student.lum.program.client.properties.ProgramProperties;

/**
 * @author Igor
 */
class ProgramConfigurer extends AbstractProgramConfigurer {

    @Override
    public void configure(WorkflowEnhancedController layout) {
        MajorDisciplineController controller = (MajorDisciplineController) layout;
        configureProgramSections(controller);

    }

    private void configureProgramSections(MajorDisciplineController controller) {
        String programSectionLabel = ProgramProperties.get().program_menu_sections();
        controller.addMenu(programSectionLabel);
        controller.addMenuItem(programSectionLabel, generateProgramDetailsView(), generateProgramDetailsEdit());
        controller.addMenuItem(programSectionLabel, generateEmptyView(ProgramProperties.get().program_menu_sections_specializations()), generateEmptyView(ProgramProperties.get().program_menu_sections_specializations()));
        controller.addMenuItem(programSectionLabel, generateEmptyView(ProgramProperties.get().program_menu_sections_requirements()), generateEmptyView(ProgramProperties.get().program_menu_sections_requirements()));
        controller.addMenuItem(programSectionLabel, generateEmptyView(ProgramProperties.get().program_menu_sections_managingBodies()), generateEmptyView(ProgramProperties.get().program_menu_sections_managingBodies()));
        controller.addMenuItem(programSectionLabel, generateEmptyView(ProgramProperties.get().program_menu_sections_programDescription()), generateEmptyView(ProgramProperties.get().program_menu_sections_programDescription()));
        controller.addMenuItem(programSectionLabel, generateEmptyView(ProgramProperties.get().program_menu_sections_learningResults()), generateEmptyView(ProgramProperties.get().program_menu_sections_learningResults()));
        controller.addMenuItem(programSectionLabel, generateEmptyView(ProgramProperties.get().program_menu_sections_learningObjectives()), generateEmptyView(ProgramProperties.get().program_menu_sections_learningObjectives()));
    }

    private View generateProgramDetailsEdit() {
        VerticalSectionView view = new VerticalSectionView(ProgramSections.PROGRAM_DETAILS_EDIT, ProgramProperties.get().program_menu_sections_programDetails(), MajorDisciplineController.PROGRAM_MODEL_ID);
        view.add(new Label("Something1"));
        return view;
    }

    private View generateProgramDetailsView() {
        VerticalSectionView view = new VerticalSectionView(ProgramSections.PROGRAM_DETAILS_VIEW, ProgramProperties.get().program_menu_sections_programDetails(), MajorDisciplineController.PROGRAM_MODEL_ID);
        view.add(new Label("Something"));
        return view;
    }

    private View generateEmptyView(String label) {
        return new VerticalSectionView(ProgramSections.EMPTY, label, MajorDisciplineController.PROGRAM_MODEL_ID);
    }
}
