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
package org.kuali.student.cm.uif.view;

import org.apache.commons.collections.iterators.EntrySetMapIterator;
import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.datadictionary.parse.BeanTag;
import org.kuali.rice.krad.lookup.LookupForm;
import org.kuali.rice.krad.lookup.LookupInputField;
import org.kuali.rice.krad.lookup.LookupView;
import org.kuali.rice.krad.uif.UifPropertyPaths;
import org.kuali.rice.krad.uif.component.Component;
import org.kuali.rice.krad.uif.container.Group;
import org.kuali.rice.krad.uif.control.Control;
import org.kuali.rice.krad.uif.control.TextAreaControl;
import org.kuali.rice.krad.uif.control.TextControl;
import org.kuali.rice.krad.uif.element.Action;
import org.kuali.rice.krad.uif.element.Message;
import org.kuali.rice.krad.uif.field.FieldGroup;
import org.kuali.rice.krad.uif.field.InputField;
import org.kuali.rice.krad.uif.util.ComponentUtils;
import org.kuali.rice.krad.uif.util.LifecycleElement;
import org.kuali.rice.krad.uif.view.ExpressionEvaluator;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krad.util.KRADConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * View type for lookups
 *
 * <p>
 * Supports doing a search against a data object class or performing a more advanced query. The view
 * type is primarily made up of two groups, the search (or criteria) group and the results group. Many
 * options are supported on the view to enable/disable certain features, like what actions are available
 * on the search results.
 * </p>
 *
 * <p>
 * Works in conjunction with <code>LookupableImpl</code> which customizes the view and carries out the
 * business functionality
 * </p>
 *
 * @author Kuali Rice Team (rice.collab@kuali.org)
 */
@BeanTag(name = "proposalLookupView-bean", parent = "Uif-LookupView")
public class ProposalLookupView extends LookupView {
    private static final long serialVersionUID = 716926008488403612L;

    /**
     * @see org.kuali.rice.krad.uif.container.ContainerBase#performApplyModel(Object,
     * org.kuali.rice.krad.uif.util.LifecycleElement)
     */
    @Override
    public void performApplyModel(Object model, LifecycleElement parent) {
        LookupForm lookupForm = (LookupForm) model;

        // rice 2.4 upgrade: commented out
/*
        if (!isRenderSearchButtons()) {
            getCriteriaGroup().getFooter().setRender(false);
        }
*/

        if (!isRenderLookupCriteria()) {
            getCriteriaGroup().setRender(false);
        }

        // rice 2.4 upgrade: commented out
/*
        if (!isRenderHeader()) {
            getHeader().setRender(false);
        }
*/

        View view = lookupForm.getView();
        setupLookupCriteriaFields(view);

        // Get the search action button for trigger on change and trigger on enter
        Group actionGroup = getCriteriaGroup().getFooter();
        Action searchButton = findSearchButton(actionGroup.getItems());

        // Only add trigger on script if an action with methodToCall search exists
        if (searchButton != null) {
            String searchButtonId = searchButton.getId();

            for (Component criteriaField : getCriteriaGroup().getItems()) {
                addTriggerScripts(searchButtonId, criteriaField);
            }
        }

        if (this.isRender() && StringUtils.isNotEmpty(getProgressiveRender())) {
            // progressive anded with render, will not render at least one of the two are false
            ExpressionEvaluator expressionEvaluator = view.getViewHelperService().getExpressionEvaluatorFactory().createExpressionEvaluator();

            String adjustedProgressiveRender = expressionEvaluator.replaceBindingPrefixes(view, this,
                    getProgressiveRender());

            Boolean progRenderEval = (Boolean) expressionEvaluator.evaluateExpression(getContext(),
                    adjustedProgressiveRender);

            this.setRender(progRenderEval);
        }

        // setup summary message field if necessary
        if (getInstructionalMessage() != null && StringUtils.isBlank(getInstructionalMessage().getMessageText())) {
            getInstructionalMessage().setMessageText(getInstructionalText());
        }

        if (getLayoutManager() != null) {
            getLayoutManager().performApplyModel(model, parent);
        }

        if (getTheme() != null) {
            view.getViewHelperService().getExpressionEvaluatorFactory().createExpressionEvaluator().evaluateExpressionsOnConfigurable(view, getTheme(),
                    getContext());

            getTheme().configureThemeDefaults();
        }

        //handle parentLocation breadcrumb chain
        getParentLocation().constructParentLocationBreadcrumbItems(view, model, view.getContext());
    }

    /**
     * Helper method to do any lookup specific changes to the criteria fields
     */
    private void setupLookupCriteriaFields(View view) {
        HashMap<Integer, Component> dateRangeFieldMap = new HashMap<Integer, Component>();

        ExpressionEvaluator expressionEvaluator =
                view.getViewHelperService().getExpressionEvaluatorFactory().createExpressionEvaluator();

        int rangeIndex = 0;
        for (Component criteriaField : getCriteriaGroup().getItems()) {

            if (criteriaField instanceof InputField){
                // Set the max length on the controls to allow for wildcards
                Control control = ((InputField)criteriaField).getControl();
                if (control instanceof TextControl) {
                    ((TextControl) control).setMaxLength(null);
                } else if (control instanceof TextAreaControl) {
                    ((TextAreaControl) control).setMaxLength(null);
                }

                if (((LookupInputField)criteriaField).isRanged()) {
                    // Create field group
                    FieldGroup rangeFieldGroup = ComponentUtils.copy(getRangeFieldGroupPrototype(), criteriaField.getId());
                    rangeFieldGroup.setLabel(((LookupInputField)criteriaField).getLabel());

                    // Evaluate and set the required property and reset the required message on the 'to' label
                    expressionEvaluator.evaluatePropertyExpression(view, criteriaField.getContext(), criteriaField,
                            "required", true);
                    rangeFieldGroup.setRequired(criteriaField.getRequired());
                    // rice 2.4 upgrade : requiredMessage is no longer available
//                    ((LookupInputField) criteriaField).getFieldLabel().setRequiredMessage(new Message());

                    // Evaluate and set the render property
                    expressionEvaluator.evaluatePropertyExpression(view, criteriaField.getContext(), criteriaField,
                            UifPropertyPaths.RENDER, true);
                    rangeFieldGroup.setRender(criteriaField.isRender());

                    List<Component> fieldGroupItems = new ArrayList<Component>();

                    // Create a new from date field
                    LookupInputField fromDate = (LookupInputField) ComponentUtils.copy(criteriaField,
                            KRADConstants.LOOKUP_DEFAULT_RANGE_SEARCH_LOWER_BOUND_LABEL);
                    fromDate.getBindingInfo().setBindingName(
                            KRADConstants.LOOKUP_RANGE_LOWER_BOUND_PROPERTY_PREFIX + fromDate.getPropertyName());
                    fromDate.setPropertyName(
                            KRADConstants.LOOKUP_RANGE_LOWER_BOUND_PROPERTY_PREFIX + fromDate.getPropertyName());

                    // Set the criteria fields labels
                    fromDate.setLabel("");
                    fromDate.getFieldLabel().setRenderColon(false);
                    ((LookupInputField)criteriaField).getFieldLabel().setRender(false);

                    // Add the cirteria fields to the field group
                    fieldGroupItems.add(fromDate);
                    fieldGroupItems.add(getRangedToMessage());
                    fieldGroupItems.add(criteriaField);
                    rangeFieldGroup.setItems(fieldGroupItems);

                    // Add fieldgroup to map with index as key
                    dateRangeFieldMap.put(rangeIndex, rangeFieldGroup);
                }
                rangeIndex++;
            }

        }

        // Replace original fields with range fieldgroups
        List<Component> itemList = (List<Component>)getCriteriaGroup().getItems();
        for (Map.Entry<Integer, Component> entry : dateRangeFieldMap.entrySet()) {
                itemList.set(entry.getKey(), entry.getValue());
        }

        getCriteriaGroup().setItems(itemList);
    }


    /**
     * Finds an Action with the search methodToCall from a list of Actions
     *
     * @param componentList list of components
     * @return the Action component with methodToCall of search
     */
    private Action findSearchButton(List<? extends Component> componentList) {
        List<? extends Action> actionList = ComponentUtils.getComponentsOfType(componentList, Action.class);
        for (Action action : actionList) {
            String methodToCall = action.getMethodToCall();
            if (methodToCall != null && methodToCall.equals("search")) {
                return action;
            }
        }
        return null;
    }

    /**
    * Adds an on change script to fields with the isTriggerOnChange set to true. Also prevents adds script to execute
    * search on enter when focus is in a criteris field
    *
    * @param searchButtonId the id of the search button
    * @param criteriaField that the script will be added to
    */
   private void addTriggerScripts(String searchButtonId, Component criteriaField) {
       if (criteriaField instanceof LookupInputField) {

           criteriaField.setOnKeyPressScript("if(e.which == 13) { e.preventDefault();jQuery('#" + searchButtonId + "' ).click();}");

           // rice 2.4 upgrade: commented out
/*
           if (isTriggerOnChange() || ((LookupInputField)criteriaField).isTriggerOnChange()) {
               criteriaField.setOnChangeScript("jQuery('#" + searchButtonId + "' ).click();");
           }
*/
       }
   }

}
