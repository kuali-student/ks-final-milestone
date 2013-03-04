package org.kuali.student.enrollment.class1.krms;

import org.kuali.rice.krad.uif.component.Component;
import org.kuali.rice.krad.uif.container.CollectionGroup;
import org.kuali.rice.krad.uif.container.Container;
import org.kuali.rice.krad.uif.container.Group;
import org.kuali.rice.krad.uif.element.Action;
import org.kuali.rice.krad.uif.field.Field;
import org.kuali.rice.krad.uif.field.FieldGroup;
import org.kuali.rice.krad.uif.layout.StackedLayoutManager;
import org.kuali.rice.krad.uif.util.ComponentUtils;
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
public class HorizontalActionsStackedLayoutManager extends StackedLayoutManager {

    /*private Group headerGroup;

    public Group getHeaderGroup() {
        return headerGroup;
    }

    public void setHeaderGroup(Group headerGroup) {
        this.headerGroup = headerGroup;
    }*/

    @Override
    public void buildLine(View view, Object model, CollectionGroup collectionGroup, List<Field> lineFields,
                          List<FieldGroup> subCollectionFields, String bindingPath, List<Action> actions, String idSuffix,
                          Object currentLine, int lineIndex) {
        super.buildLine(view, model, collectionGroup, lineFields, subCollectionFields, bindingPath, actions, idSuffix,
                currentLine, lineIndex);

        //if ((lineIndex == -1) && (this.getHeaderGroup() != null)){
        //    this.getStackedGroups().add(ComponentUtils.copy(this.getHeaderGroup(), idSuffix));
        //}
    }

    @Override
    public List<Component> getComponentsForLifecycle() {
        return super.getComponentsForLifecycle();
    }

    @Override
    public void performFinalize(View view, Object model, Container container) {
        super.performFinalize(view, model, container);

        for (Group line : this.getStackedGroups()) {
            if(line.getFooter() != null){
                List<Component> footerItems = (List<Component>) line.getFooter().getItems();
                List<Component> lineItems = (List<Component>) line.getItems();
                line.getFooter().setItems(new ArrayList());
                lineItems.addAll(footerItems);
                line.setRenderFooter(false);
                line.setItems(lineItems);
            }
        }
    }
}
