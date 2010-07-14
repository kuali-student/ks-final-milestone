package org.kuali.student.lum.program.client;

import org.kuali.student.common.ui.client.configurable.mvc.Configurer;

/**
 * @author Igor
 */
public abstract class AbstractProgramConfigurer extends Configurer {

    public abstract void configure(ProgramController programController);
}
