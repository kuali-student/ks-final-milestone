package org.kuali.student.lum.common.client.configuration;

import com.google.gwt.user.client.ui.Widget;
import org.kuali.student.common.ui.client.configurable.mvc.Configurer;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.View;

/**
 * Should be implemented by classes that want to configure a view using configurer.
 *
 * @author Igor
 */
public interface Configuration {
    void setConfigurer(Configurer configurer);

    View getView();

    Widget asWidget();

    Enum<?> getName();

    void applyRestrictions();

    boolean checkPermission(DataModel model);

    void removeRestrictions();
}
