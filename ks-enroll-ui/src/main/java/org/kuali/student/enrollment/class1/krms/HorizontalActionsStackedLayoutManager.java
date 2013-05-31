package org.kuali.student.enrollment.class1.krms;

import org.kuali.rice.krad.uif.UifConstants;
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
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2013/02/07
 * Time: 5:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class HorizontalActionsStackedLayoutManager extends StackedLayoutManager {

    @Override
    public void buildLine(View view, Object model, CollectionGroup collectionGroup, List<Field> lineFields,
                          List<FieldGroup> subCollectionFields, String bindingPath, List<Action> actions, String idSuffix,
                          Object currentLine, int lineIndex) {

        super.buildLine(view, model, collectionGroup, lineFields, subCollectionFields, bindingPath, actions, idSuffix,
                currentLine, lineIndex);

        if (lineIndex == -1) {
            String nodePath = this.getNodePath(this.getContext());
            if (nodePath != null) {
                for (Group group : this.getStackedGroups()) {
                    //Set the nodepath on the add line group so that progressive rendering can use the #np
                    ComponentUtils.pushObjectToContext(group, UifConstants.ContextVariableNames.NODE_PATH, nodePath);
                }
            }
        }

    }

    private String getNodePath(Map<String, Object> context) {
        if (context.containsKey(UifConstants.ContextVariableNames.NODE_PATH)) {
            return (String) context.get(UifConstants.ContextVariableNames.NODE_PATH);

        } else if (context.containsKey(UifConstants.ContextVariableNames.PARENT)) {
            Component parent = (Component) context.get(UifConstants.ContextVariableNames.PARENT);
            return this.getNodePath(parent.getContext());
        }
        return null;
    }

}
