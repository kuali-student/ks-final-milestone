package org.kuali.student.lum.program.client.view;

import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.framework.AbstractSectionConfiguration;
import org.kuali.student.lum.program.client.properties.ProgramProperties;

/**
 * @author Igor
 */
public class LearningObjectivesViewConfiguration extends AbstractSectionConfiguration<ProgramViewConfigurer> {

    public LearningObjectivesViewConfiguration() {
        rootSection = new VerticalSectionView(ProgramSections.LEARNING_OBJECTIVES_EDIT, ProgramProperties.get().program_menu_sections_learningObjectives(), ProgramConstants.PROGRAM_MODEL_ID);
    }

    protected void buildLayout() {
        //builds layout
    }
}
