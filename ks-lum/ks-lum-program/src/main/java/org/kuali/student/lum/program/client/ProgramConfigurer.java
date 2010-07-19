package org.kuali.student.lum.program.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Label;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.lum.program.client.configuration.Configuration;
import org.kuali.student.lum.program.client.configuration.EditableConfiguration;
import org.kuali.student.lum.program.client.configuration.ProgramInformationConfiguration;
import org.kuali.student.lum.program.client.configuration.SpecializationsConfiguration;
import org.kuali.student.lum.program.client.properties.ProgramProperties;

/**
 * @author Igor
 */
public class ProgramConfigurer extends AbstractProgramConfigurer {

    private ProgramController controller;

    private EditableConfiguration<ProgramConfigurer> programInformation = GWT.create(ProgramInformationConfiguration.class);
    private Configuration<ProgramConfigurer> specializations = GWT.create(SpecializationsConfiguration.class);

    @Override
    public void configure(ProgramController controller) {
        this.controller = controller;
        specializations.setConfigurer(this);
        programInformation.setConfigurer(this);
        controller.setContentTitle("Biology");
        configureProgramSections();
    }

    private void configureProgramSections() {
        String programSectionLabel = ProgramProperties.get().program_menu_sections();
        controller.addMenu(programSectionLabel);
        controller.addMenuItem(programSectionLabel, programInformation.getView(), programInformation.getEditView());
        controller.addMenuItem(programSectionLabel, specializations.getView());
        controller.addMenuItem(programSectionLabel, generateProgramRequirementsView(), generateProgramRequirementsEdit());
        controller.addMenuItem(programSectionLabel, generateManageBodiesView(), generateManageBodiesEdit());
        controller.addMenuItem(programSectionLabel, generateCatalogInfoView(), generateCatalogInfoEdit());
        controller.addMenuItem(programSectionLabel, generateEmptyView(ProgramProperties.get().program_menu_sections_programDescription()), generateEmptyView(ProgramProperties.get().program_menu_sections_programDescription()));
        controller.addMenuItem(programSectionLabel, generateEmptyView(ProgramProperties.get().program_menu_sections_learningResults()), generateEmptyView(ProgramProperties.get().program_menu_sections_learningResults()));
        controller.addMenuItem(programSectionLabel, generateLearningObjectivesView(), generateLearningObjectivesEdit());
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

    public String getData(String key) {
        return controller.getProgramModel().get(key);
    }

}
