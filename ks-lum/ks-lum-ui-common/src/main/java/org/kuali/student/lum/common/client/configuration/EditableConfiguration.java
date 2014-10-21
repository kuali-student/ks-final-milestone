package org.kuali.student.lum.common.client.configuration;

import org.kuali.student.common.ui.client.mvc.View;

/**
 * Should be implemented by classes that intend to provide "display" and "edit" views.
 *
 * @author Igor
 */
public interface EditableConfiguration extends Configuration {
    View getEditView();
}
