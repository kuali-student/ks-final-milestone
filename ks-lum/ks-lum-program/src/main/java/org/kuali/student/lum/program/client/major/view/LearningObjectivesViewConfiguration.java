package org.kuali.student.lum.program.client.major.view;

import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.common.ui.client.widgets.menus.KSListPanel;
import org.kuali.student.lum.common.client.configuration.AbstractSectionConfiguration;
import org.kuali.student.lum.common.client.lo.TreeStringBinding;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.properties.ProgramProperties;
import org.kuali.student.lum.program.client.widgets.EditableHeader;

/**
 * @author Igor
 */
public class LearningObjectivesViewConfiguration extends AbstractSectionConfiguration {


    public static LearningObjectivesViewConfiguration create() {
        return new LearningObjectivesViewConfiguration(new VerticalSectionView(ProgramSections.LEARNING_OBJECTIVES_VIEW, ProgramProperties.get().program_menu_sections_learningObjectives(), ProgramConstants.PROGRAM_MODEL_ID));
    }

    public static LearningObjectivesViewConfiguration createSpecial() {
        String title = ProgramProperties.get().program_menu_sections_learningObjectives();
        return new LearningObjectivesViewConfiguration(new VerticalSectionView(ProgramSections.LEARNING_OBJECTIVES_VIEW, title, ProgramConstants.PROGRAM_MODEL_ID, new EditableHeader(title, ProgramSections.LEARNING_OBJECTIVES_EDIT)));
    }

    private LearningObjectivesViewConfiguration(SectionView sectionView) {
        rootSection = sectionView;
    }

    protected void buildLayout() {
        configurer.addReadOnlyField(rootSection, ProgramConstants.LEARNING_OBJECTIVES, new MessageKeyInfo(""), new KSListPanel()).setWidgetBinding(new TreeStringBinding());
    }
}
