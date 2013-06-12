package org.kuali.student.enrollment.class1.krms;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.component.Component;
import org.kuali.rice.krad.uif.component.DataBinding;
import org.kuali.rice.krad.uif.container.CollectionGroup;
import org.kuali.rice.krad.uif.container.Container;
import org.kuali.rice.krad.uif.container.Group;
import org.kuali.rice.krad.uif.element.Action;
import org.kuali.rice.krad.uif.field.Field;
import org.kuali.rice.krad.uif.field.FieldGroup;
import org.kuali.rice.krad.uif.layout.StackedLayoutManager;
import org.kuali.rice.krad.uif.util.ComponentUtils;
import org.kuali.rice.krad.uif.util.ObjectPropertyUtils;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krad.web.form.UifFormBase;

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

        boolean isAddLine = lineIndex == -1;

        // construct new group
        Group lineGroup = null;
        if (isAddLine) {
            setStackedGroups(new ArrayList<Group>());

            if (this.getAddLineGroup() == null) {
                lineGroup = ComponentUtils.copy(this.getLineGroupPrototype(), idSuffix);
            } else {
                lineGroup = ComponentUtils.copy(getAddLineGroup(), idSuffix);
                lineGroup.addStyleClass(collectionGroup.getAddItemCssClass());
            }

            if (collectionGroup.isAddViaLightBox()) {
                String actionScript = "showLightboxComponent('" + lineGroup.getId() + "');";
                if (StringUtils.isNotBlank(collectionGroup.getAddViaLightBoxAction().getActionScript())) {
                    actionScript = collectionGroup.getAddViaLightBoxAction().getActionScript() + actionScript;
                }
                collectionGroup.getAddViaLightBoxAction().setActionScript(actionScript);
                lineGroup.setStyle("display: none");
            }
        } else {
            lineGroup = ComponentUtils.copy(this.getLineGroupPrototype(), idSuffix);
        }

        if (((UifFormBase) model).isAddedCollectionItem(currentLine)) {
            lineGroup.addStyleClass(collectionGroup.getNewItemsCssClass());
        }

        ComponentUtils.updateContextForLine(lineGroup, currentLine, lineIndex, idSuffix);

        // build header text for group
        String headerText = "";
        if (isAddLine) {
            headerText = collectionGroup.getAddLabel();
        } else {
            // get the collection for this group from the model
            List<Object> modelCollection = ObjectPropertyUtils.getPropertyValue(model,
                    ((DataBinding) collectionGroup).getBindingInfo().getBindingPath());

            headerText = buildLineHeaderText(modelCollection.get(lineIndex), lineGroup);
        }

        // don't set header if text is blank (could already be set by other means)
        if (StringUtils.isNotBlank(headerText) && lineGroup.getHeader() != null) {
            lineGroup.getHeader().setHeaderText(headerText);
        }

        // stack all fields (including sub-collections) for the group
        List<Component> groupFields = new ArrayList<Component>();
        groupFields.addAll(lineFields);

        // set line actions on group footer
        if (collectionGroup.isRenderLineActions() && !collectionGroup.isReadOnly() && (lineGroup.getFooter() != null)) {

            // add the actions to the line group if isActionsInLineGroup flag is true
            if (isActionsInLineGroup()) {
                groupFields.addAll(actions);
                lineGroup.setRenderFooter(false);
            }else{
                lineGroup.getFooter().setItems(actions);
            }
        }

        groupFields.addAll(subCollectionFields);

        lineGroup.setItems(groupFields);
        this.getStackedGroups().add(lineGroup);

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
