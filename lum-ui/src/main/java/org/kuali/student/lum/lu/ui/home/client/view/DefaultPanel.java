package org.kuali.student.lum.lu.ui.home.client.view;

import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.ViewComposite;

import com.google.gwt.user.client.ui.Label;

public class DefaultPanel extends ViewComposite{

    public DefaultPanel(Controller controller) {
        // TODO Bsmith - THIS CONSTRUCTOR NEEDS A JAVADOC
        super(controller, "LUM");
        this.initWidget(new Label("Place Directions for menu/introduction here...."));
        // TODO Auto-generated constructor stub
    }

}
