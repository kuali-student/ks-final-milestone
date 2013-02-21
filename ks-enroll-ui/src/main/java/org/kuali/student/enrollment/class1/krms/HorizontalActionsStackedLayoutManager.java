package org.kuali.student.enrollment.class1.krms;

import org.kuali.rice.krad.uif.component.Component;
import org.kuali.rice.krad.uif.container.Container;
import org.kuali.rice.krad.uif.container.Group;
import org.kuali.rice.krad.uif.layout.StackedLayoutManager;
import org.kuali.rice.krad.uif.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2013/02/07
 * Time: 5:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class HorizontalActionsStackedLayoutManager extends StackedLayoutManager{

    @Override
    public void performFinalize(View view, Object model, Container container) {
        super.performFinalize(view, model, container);

        for (Group line : this.getStackedGroups()) {
            List<Component> footerItems = (List<Component>)line.getFooter().getItems();
            List<Component> lineItems = (List<Component>)line.getItems();
            line.getFooter().setItems(new ArrayList());
            lineItems.addAll(footerItems);
            line.setRenderFooter(false);
            line.setItems(lineItems);
        }
    }
}
