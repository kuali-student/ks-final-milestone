package org.kuali.student.lum.program.client.bacc.view;

import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.common.ui.client.widgets.menus.KSListPanel;
import org.kuali.student.lum.common.client.configuration.AbstractSectionConfiguration;
import org.kuali.student.lum.common.client.lo.TreeStringBinding;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.bacc.BaccEditableHeader;
import org.kuali.student.lum.program.client.properties.ProgramProperties;
import org.kuali.student.lum.program.client.widgets.EditableHeader;

/**
 * @author Igor
 */
public class BaccLearningObjectivesViewConfiguration extends AbstractSectionConfiguration {

    public static BaccLearningObjectivesViewConfiguration create() {
        return new BaccLearningObjectivesViewConfiguration(new VerticalSectionView(ProgramSections.LEARNING_OBJECTIVES_VIEW, ProgramProperties.get().program_menu_sections_learningObjectives(), ProgramConstants.PROGRAM_MODEL_ID));
    }

    public static BaccLearningObjectivesViewConfiguration createSpecial() {
        String title = ProgramProperties.get().program_menu_sections_learningObjectives();
        return new BaccLearningObjectivesViewConfiguration(new VerticalSectionView(ProgramSections.LEARNING_OBJECTIVES_VIEW, title, ProgramConstants.PROGRAM_MODEL_ID, new BaccEditableHeader(title, ProgramSections.LEARNING_OBJECTIVES_EDIT)));
    }

    private BaccLearningObjectivesViewConfiguration(SectionView sectionView) {
        rootSection = sectionView;
    }
    protected void buildLayout() {
        configurer.addReadOnlyField(rootSection, ProgramConstants.LEARNING_OBJECTIVES, new MessageKeyInfo(""), new KSListPanel()).setWidgetBinding(new TreeStringBinding());
    }
}