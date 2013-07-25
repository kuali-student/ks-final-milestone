package org.kuali.student.lum.common.client.configuration;

import org.kuali.student.common.ui.client.mvc.Controller;

/**
 * @author Igor
 */
public abstract class AbstractControllerConfiguration extends AbstractSectionConfiguration{

    protected Controller controller;

    public void setController(Controller controller) {
        this.controller = controller;
    }
}
