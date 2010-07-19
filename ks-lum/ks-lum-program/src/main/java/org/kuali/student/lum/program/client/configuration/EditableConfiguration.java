package org.kuali.student.lum.program.client.configuration;

import org.kuali.student.common.ui.client.mvc.View;

/**
 * @author Igor
 */
public interface EditableConfiguration<T> extends Configuration<T> {
    View getEditView();
}
