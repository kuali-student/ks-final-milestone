package org.kuali.student.lum.program.client.credential.view;

import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.common.ui.client.widgets.menus.KSListPanel;
import org.kuali.student.lum.common.client.configuration.AbstractSectionConfiguration;
import org.kuali.student.lum.common.client.lo.TreeStringBinding;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.credential.CredentialEditableHeader;
import org.kuali.student.lum.program.client.properties.ProgramProperties;

/**
 * @author Igor
 */
public class CredentialLearningObjectivesViewConfiguration extends AbstractSectionConfiguration {

    public static CredentialLearningObjectivesViewConfiguration create() {
        return new CredentialLearningObjectivesViewConfiguration(new VerticalSectionView(ProgramSections.LEARNING_OBJECTIVES_VIEW, ProgramProperties.get().program_menu_sections_learningObjectives(), ProgramConstants.PROGRAM_MODEL_ID));
    }

    public static CredentialLearningObjectivesViewConfiguration createSpecial() {
        String title = ProgramProperties.get().program_menu_sections_learningObjectives();
        return new CredentialLearningObjectivesViewConfiguration(new VerticalSectionView(ProgramSections.LEARNING_OBJECTIVES_VIEW, title, ProgramConstants.PROGRAM_MODEL_ID, new CredentialEditableHeader(title, ProgramSections.LEARNING_OBJECTIVES_EDIT)));
    }

    private CredentialLearningObjectivesViewConfiguration(SectionView sectionView) {
        rootSection = sectionView;
    }
    protected void buildLayout() {
        configurer.addReadOnlyField(rootSection, ProgramConstants.LEARNING_OBJECTIVES, new MessageKeyInfo(""), new KSListPanel()).setWidgetBinding(new TreeStringBinding());
    }
}