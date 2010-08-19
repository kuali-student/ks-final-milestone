package org.kuali.student.lum.program.client.framework;

import org.kuali.student.common.ui.client.configurable.mvc.Configurer;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.lum.program.client.framework.Configuration;

/**
 * Should be implemented by classes that intend to provide "display" and "edit" views.
 *
 * @author Igor
 */
public interface EditableConfiguration<T extends Configurer> extends Configuration<T> {
    View getEditView();
}
