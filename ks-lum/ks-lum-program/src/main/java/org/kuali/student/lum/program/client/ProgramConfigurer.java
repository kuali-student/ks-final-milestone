package org.kuali.student.lum.program.client;

import com.google.gwt.user.client.ui.Label;
import org.kuali.student.common.ui.client.configurable.mvc.sections.HorizontalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.lum.program.client.properties.ProgramProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Igor
 */
class ProgramConfigurer extends AbstractProgramConfigurer {

    private ProgramController controller;

    @Override
    public void configure(ProgramController controller) {
        this.controller = controller;
        controller.setContentTitle("Biology");
        configureProgramSections();
    }

    private void configureProgramSections() {
        String programSectionLabel = ProgramProperties.get().program_menu_sections();
        controller.addMenu(programSectionLabel);
        controller.addMenuItem(programSectionLabel, generateProgramDetailsView(), generateProgramDetailsEdit());
        controller.addMenuItem(programSectionLabel, generateEmptyView(ProgramProperties.get().program_menu_sections_specializations()), generateEmptyView(ProgramProperties.get().program_menu_sections_specializations()));
        controller.addMenuItem(programSectionLabel, generateProgramRequirementsView(), generateProgramRequirementsEdit());
        controller.addMenuItem(programSectionLabel, generateManageBodiesView(), generateManageBodiesEdit());
        controller.addMenuItem(programSectionLabel, generateCatalogInfoView(), generateCatalogInfoEdit());
        controller.addMenuItem(programSectionLabel, generateEmptyView(ProgramProperties.get().program_menu_sections_programDescription()), generateEmptyView(ProgramProperties.get().program_menu_sections_programDescription()));
        controller.addMenuItem(programSectionLabel, generateEmptyView(ProgramProperties.get().program_menu_sections_learningResults()), generateEmptyView(ProgramProperties.get().program_menu_sections_learningResults()));
        controller.addMenuItem(programSectionLabel, generateLearningObjectivesView(), generateLearningObjectivesEdit());
    }

    private View generateProgramDetailsEdit() {
        VerticalSectionView view = new VerticalSectionView(ProgramSections.PROGRAM_DETAILS_EDIT, ProgramProperties.get().program_menu_sections_programDetails(), ProgramController.PROGRAM_MODEL_ID);
        HorizontalSection section = new HorizontalSection();
        addField(section, ProgramConstants.SHORT_TITLE, new MessageKeyInfo(ProgramProperties.get().program_menu_sections_programDetails_programTitle()));
        addField(section, ProgramConstants.LONG_TITLE, new MessageKeyInfo(ProgramProperties.get().program_menu_sections_programDetails_longTitle()));
        view.addWidget(section);
        return view;
    }

    private View generateProgramDetailsView() {
        VerticalSectionView view = new VerticalSectionView(ProgramSections.PROGRAM_DETAILS_VIEW, ProgramProperties.get().program_menu_sections_programDetails(), ProgramController.PROGRAM_MODEL_ID);
        HorizontalSection section = new HorizontalSection();
        addField(section, ProgramConstants.SHORT_TITLE, new MessageKeyInfo(ProgramProperties.get().program_menu_sections_programDetails_programTitle()), new Label(getData(ProgramConstants.SHORT_TITLE)));
        addField(section, ProgramConstants.LONG_TITLE, new MessageKeyInfo(ProgramProperties.get().program_menu_sections_programDetails_longTitle()), new Label(getData(ProgramConstants.LONG_TITLE)));
        view.addSection(section);
        return view;
    }

    private View generateProgramRequirementsEdit() {
        VerticalSectionView view = new VerticalSectionView(ProgramSections.PROGRAM_REQUIREMENTS_EDIT, ProgramProperties.get().program_menu_sections_requirements(), ProgramController.PROGRAM_MODEL_ID);
        view.addWidget(new Label("Edit Requirements"));
        return view;
    }

    private View generateProgramRequirementsView() {
        VerticalSectionView view = new VerticalSectionView(ProgramSections.PROGRAM_REQUIREMENTS_VIEW, ProgramProperties.get().program_menu_sections_requirements(), ProgramController.PROGRAM_MODEL_ID);
        view.addWidget(new Label("View Requirements"));
        return view;
    }

    private View generateManageBodiesEdit() {
        VerticalSectionView view = new VerticalSectionView(ProgramSections.MANAGE_BODIES_EDIT, ProgramProperties.get().program_menu_sections_managingBodies(), ProgramController.PROGRAM_MODEL_ID);
        view.addWidget(new Label("Managing Bodies"));
        return view;
    }

    private View generateManageBodiesView() {
        VerticalSectionView view = new VerticalSectionView(ProgramSections.MANAGE_BODIES_VIEW, ProgramProperties.get().program_menu_sections_managingBodies(), ProgramController.PROGRAM_MODEL_ID);
        view.addWidget(new Label("Managing Bodies"));
        return view;
    }

    private View generateCatalogInfoEdit() {
        VerticalSectionView view = new VerticalSectionView(ProgramSections.CATALOG_INFO_EDIT, ProgramProperties.get().program_menu_sections_catalogInfo(), ProgramController.PROGRAM_MODEL_ID);
        view.addWidget(new Label("Catalog Information"));
        return view;
    }

    private View generateCatalogInfoView() {
        VerticalSectionView view = new VerticalSectionView(ProgramSections.CATALOG_INFO_VIEW, ProgramProperties.get().program_menu_sections_catalogInfo(), ProgramController.PROGRAM_MODEL_ID);
        view.addWidget(new Label("Catalog Information"));
        return view;
    }

    private View generateLearningObjectivesEdit() {
        VerticalSectionView view = new VerticalSectionView(ProgramSections.LEARNING_OBJECTIVES_EDIT, ProgramProperties.get().program_menu_sections_learningObjectives(), ProgramController.PROGRAM_MODEL_ID);
        view.addWidget(new Label("Learning Objectives"));
        return view;
    }

    private View generateLearningObjectivesView() {
        VerticalSectionView view = new VerticalSectionView(ProgramSections.LEARNING_OBJECTIVES_VIEW, ProgramProperties.get().program_menu_sections_learningObjectives(), ProgramController.PROGRAM_MODEL_ID);
        view.addWidget(new Label("Learning Objectives"));
        return view;
    }

    private View generateEmptyView(String label) {
        return new VerticalSectionView(ProgramSections.EMPTY, label, ProgramController.PROGRAM_MODEL_ID);
    }

    private String getData(String key) {
        return controller.getProgramModel().get(key);
    }

}
