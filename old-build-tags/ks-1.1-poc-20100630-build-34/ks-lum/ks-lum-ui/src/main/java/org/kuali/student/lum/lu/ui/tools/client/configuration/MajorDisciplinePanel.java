package org.kuali.student.lum.lu.ui.tools.client.configuration;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.ViewComposite;

/**
 * @author Igor
 */
public class MajorDisciplinePanel extends ViewComposite {

    private VerticalPanel content = new VerticalPanel();

    /**
     * Constructs a new view with an associated controller and view name
     *
     * @param controller the controller associated with the view
     * @param name       the view name
     */
    public MajorDisciplinePanel(Controller controller, String name) {
        super(controller, name);
        initWidget(content);
        buildLayout();
    }

    private void buildLayout() {
        content.add(new Label("Hello Program Developers!"));
    }
}
