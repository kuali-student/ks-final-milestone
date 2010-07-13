package org.kuali.student.lum.program.client;

import org.kuali.student.common.ui.client.configurable.mvc.Configurer;
import org.kuali.student.core.workflow.ui.client.widgets.ContentConfigurer;
import org.kuali.student.core.workflow.ui.client.widgets.WorkflowEnhancedController;

/**
 * @author Igor
 */
public abstract class AbstractProgramConfigurer extends Configurer {

    public abstract void configure(MajorDisciplineController majorDisciplineController);
}
