package org.kuali.student.lum.program.client.core.view;

import org.kuali.student.common.ui.client.configurable.mvc.Configurer;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.common.ui.client.widgets.menus.KSListPanel;
import org.kuali.student.lum.common.client.configuration.AbstractSectionConfiguration;
import org.kuali.student.lum.common.client.lo.TreeStringBinding;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramMsgConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.core.CoreEditableHeader;

/**
 * @author Igor
 */
public class CoreLearningObjectivesViewConfiguration extends AbstractSectionConfiguration {

     public static CoreLearningObjectivesViewConfiguration create(Configurer configurer) {
         return new CoreLearningObjectivesViewConfiguration(configurer, false);
     }

     public static CoreLearningObjectivesViewConfiguration createSpecial(Configurer configurer) {
         return new CoreLearningObjectivesViewConfiguration(configurer, true);
     }

     private CoreLearningObjectivesViewConfiguration(Configurer configurer, boolean isSpecial) {
         this.setConfigurer(configurer);
         String title = getLabel(ProgramMsgConstants.PROGRAM_MENU_SECTIONS_LEARNINGOBJECTIVES);
         if (!isSpecial){
             this.rootSection = new VerticalSectionView(ProgramSections.LEARNING_OBJECTIVES_VIEW, title,
                     ProgramConstants.PROGRAM_MODEL_ID);
         } else {
             this.rootSection = new VerticalSectionView(ProgramSections.LEARNING_OBJECTIVES_VIEW, title, 
                     ProgramConstants.PROGRAM_MODEL_ID, new CoreEditableHeader(title, ProgramSections.LEARNING_OBJECTIVES_EDIT));
         }
     }
     
     protected void buildLayout() {
         configurer.addReadOnlyField(rootSection, ProgramConstants.LEARNING_OBJECTIVES, new MessageKeyInfo(""), new KSListPanel()).setWidgetBinding(new TreeStringBinding());
     }
}
