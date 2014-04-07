/**
 * Copyright 2005-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.core.krms;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.component.Component;
import org.kuali.rice.krad.uif.component.DataBinding;
import org.kuali.rice.krad.uif.container.CollectionGroup;
import org.kuali.rice.krad.uif.container.Group;
import org.kuali.rice.krad.uif.container.collections.LineBuilderContext;
import org.kuali.rice.krad.uif.element.Action;
import org.kuali.rice.krad.uif.element.Message;
import org.kuali.rice.krad.uif.field.Field;
import org.kuali.rice.krad.uif.field.FieldGroup;
import org.kuali.rice.krad.uif.layout.StackedLayoutManager;
import org.kuali.rice.krad.uif.layout.StackedLayoutManagerBase;
import org.kuali.rice.krad.uif.lifecycle.ViewLifecycleUtils;
import org.kuali.rice.krad.uif.util.ComponentUtils;
import org.kuali.rice.krad.uif.util.ObjectPropertyUtils;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.util.KRADUtils;
import org.kuali.rice.krad.web.form.UifFormBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Kuali Student Team
 */
public class HorizontalActionsStackedLayoutManager extends StackedLayoutManagerBase {

    @Override
    public void buildLine(LineBuilderContext lineBuilderContext) {
        List<Field> lineFields = lineBuilderContext.getLineFields();
        CollectionGroup collectionGroup = lineBuilderContext.getCollectionGroup();
        int lineIndex = lineBuilderContext.getLineIndex();
        String idSuffix = lineBuilderContext.getIdSuffix();
        Object currentLine = lineBuilderContext.getCurrentLine();
        List<? extends Component> actions = lineBuilderContext.getLineActions();

        // construct new group
        Group lineGroup = null;
        if (lineBuilderContext.isAddLine()) {
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

        if (((UifFormBase) lineBuilderContext.getModel()).isAddedCollectionItem(currentLine)) {
            lineGroup.addStyleClass(collectionGroup.getNewItemsCssClass());
        }

        // any actions that are attached to the group prototype (like the header) need to get action parameters
        // and context set for the collection line
        List<Action> lineGroupActions = ViewLifecycleUtils.getElementsOfTypeDeep(lineGroup, Action.class);
        if (lineGroupActions != null) {
            collectionGroup.getCollectionGroupBuilder().initializeActions(lineGroupActions, collectionGroup, lineIndex);
            ComponentUtils.updateContextsForLine(lineGroupActions, collectionGroup, currentLine, lineIndex, idSuffix);
        }
        ComponentUtils.updateContextForLine(lineGroup, collectionGroup, currentLine, lineIndex, idSuffix);


        // build header text for group
        if (lineBuilderContext.isAddLine()) {
            if (lineGroup.getHeader() != null) {
                Message headerMessage = ComponentUtils.copy(collectionGroup.getAddLineLabel());
                lineGroup.getHeader().setRichHeaderMessage(headerMessage);
            }
        } else {
            // get the collection for this group from the model
            List<Object> modelCollection = ObjectPropertyUtils.getPropertyValue(lineBuilderContext.getModel(),
                    ((DataBinding) collectionGroup).getBindingInfo().getBindingPath());

            String headerText = buildLineHeaderText(modelCollection.get(lineIndex), lineGroup);

            // don't set header if text is blank (could already be set by other means)
            if (StringUtils.isNotBlank(headerText) && lineGroup.getHeader() != null) {
                lineGroup.getHeader().setHeaderText(headerText);
            }
        }

        // stack all fields (including sub-collections) for the group
        List<Component> groupFields = new ArrayList<Component>();
        groupFields.addAll(lineFields);

        // set line actions on group footer
        if (collectionGroup.isRenderLineActions() && !collectionGroup.isReadOnly() && (lineGroup.getFooter() != null)) {

            // add the actions to the line group if isActionsInLineGroup flag is true
            if (isActionsInLineGroup()) {
                groupFields.addAll(actions);
                if (lineBuilderContext.getSubCollectionFields() != null) {
                    groupFields.addAll(lineBuilderContext.getSubCollectionFields());
                }
                lineGroup.setRenderFooter(false);
            }else{
                List<Component> footerFields = new ArrayList<Component>();
                footerFields.addAll(actions);
                if (lineBuilderContext.getSubCollectionFields() != null) {
                    footerFields.addAll(lineBuilderContext.getSubCollectionFields());
                }
                lineGroup.getFooter().setItems(footerFields);
            }
        }

        lineGroup.setItems(groupFields);

        // Must evaluate the client-side state on the lineGroup's disclosure for PlaceholderDisclosureGroup processing
        if (lineBuilderContext.getModel() instanceof ViewModel){
            KRADUtils.syncClientSideStateForComponent(lineGroup.getDisclosure(),
                    ((ViewModel) lineBuilderContext.getModel()).getClientStateForSyncing());
        }

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
