package org.kuali.student.lum.program.client.edit;

import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.framework.AbstractSectionConfiguration;
import org.kuali.student.lum.program.client.properties.ProgramProperties;

/**
 * @author Igor
 */
public class LearningObjectivesEditConfiguration extends AbstractSectionConfiguration<ProgramEditConfigurer> {

    public LearningObjectivesEditConfiguration() {
        rootSection = new VerticalSectionView(ProgramSections.LEARNING_OBJECTIVES_EDIT, ProgramProperties.get().program_menu_sections_learningObjectives(), ProgramConstants.PROGRAM_MODEL_ID);
    }

    @Override
    protected void buildLayout() {
       // FieldDescriptor fd = addField(rootSection, ProgramConstants.LEARNING_OBJECTIVES, null,
        //        new LOBuilder(type, state, groupName, "kuali.loRepository.key.singleUse", meta),
         //       COURSE);
        //fd.setWidgetBinding(LOBuilderBinding.INSTANCE);
        //los.addStyleName("KS-LUM-Section-Divider");
    }
}
