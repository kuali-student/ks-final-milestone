/**
 * Copyright 2014 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * Created by venkat on 9/3/14
 */
package org.kuali.student.cm.course.modifiers;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.UifPropertyPaths;
import org.kuali.rice.krad.uif.component.Component;
import org.kuali.rice.krad.uif.container.CollectionGroup;
import org.kuali.rice.krad.uif.container.Group;
import org.kuali.rice.krad.uif.element.Header;
import org.kuali.rice.krad.uif.field.DataField;
import org.kuali.rice.krad.uif.field.DataFieldBase;
import org.kuali.rice.krad.uif.field.Field;
import org.kuali.rice.krad.uif.field.SpaceField;
import org.kuali.rice.krad.uif.layout.GridLayoutManager;
import org.kuali.rice.krad.uif.lifecycle.ViewLifecycle;
import org.kuali.rice.krad.uif.lifecycle.ViewLifecycleUtils;
import org.kuali.rice.krad.uif.modifier.ComparableInfo;
import org.kuali.rice.krad.uif.modifier.CompareFieldCreateModifier;
import org.kuali.rice.krad.uif.util.ComponentFactory;
import org.kuali.rice.krad.uif.util.ComponentUtils;
import org.kuali.rice.krad.uif.util.ObjectPropertyUtils;
import org.kuali.rice.krad.uif.view.ExpressionEvaluator;
import org.kuali.rice.krad.uif.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Kuali Student Team
 */
public class CMCourseFieldCompareModifier extends CompareFieldCreateModifier{

    /**
     * Properties which should not get highlighting or compare-to data.
     */
    private List<String> excludeProperties = new ArrayList<>();

    public List<String> getExcludeProperties() {
        return excludeProperties;
    }

    public void setExcludeProperties(List<String> excludeProperties) {
        this.excludeProperties = excludeProperties;
    }

    @Override
    public void performModification(Object model, Component component) {
        if ((component != null) && !(component instanceof Group)) {
            throw new IllegalArgumentException(
                    "Compare field initializer only support Group components, found type: " + component.getClass());
        }

        if (component == null) {
            return;
        }

        Group group = (Group) component;

        // list to hold the generated compare items
        List<Component> comparisonItems = new ArrayList<Component>();

        // sort comparables by their order property
        List<ComparableInfo> groupComparables = (List<ComparableInfo>) ComponentUtils.sort(getComparables(),
                getDefaultOrderSequence());

        // evaluate expressions on comparables
        Map<String, Object> context = new HashMap<String, Object>();

        View view = ViewLifecycle.getView();

        Map<String, Object> viewContext = view.getContext();
        if (viewContext != null) {
            context.putAll(view.getContext());
        }

        context.put(UifConstants.ContextVariableNames.COMPONENT, component);

        ExpressionEvaluator expressionEvaluator = ViewLifecycle.getExpressionEvaluator();

        for (ComparableInfo comparable : groupComparables) {
            expressionEvaluator.evaluateExpressionsOnConfigurable(view, comparable, context);
        }

        // generate compare header
        if (isGenerateCompareHeaders()) {
            // add space field for label column
            SpaceField spaceField = ComponentFactory.getSpaceField();
            comparisonItems.add(spaceField);

            for (ComparableInfo comparable : groupComparables) {
                Header compareHeaderField = ComponentUtils.copy(getHeaderFieldPrototype(), comparable.getIdSuffix());
                compareHeaderField.setHeaderText(comparable.getHeaderText());
                comparisonItems.add(compareHeaderField);
            }

            // if group is using grid layout, make first row a header
            if (group.getLayoutManager() instanceof GridLayoutManager) {
                ((GridLayoutManager) group.getLayoutManager()).setRenderFirstRowHeader(true);
            }
        }

        // find the comparable to use for comparing value changes (if
        // configured)
        boolean performValueChangeComparison = false;
        String compareValueObjectBindingPath = null;
        for (ComparableInfo comparable : groupComparables) {
            if (comparable.isCompareToForValueChange()) {
                performValueChangeComparison = true;
                compareValueObjectBindingPath = comparable.getBindingObjectPath();
            }
        }

        // generate the compare items from the configured group
        int index = 0;
        List<String> rowCssClasses = new ArrayList<>();

        for (Component item : group.getItems()) {

            //  Determine if this property is in the exclude list.
            boolean excluded = false;
            if (item instanceof DataFieldBase) {
                String bindingPath = ((DataFieldBase) item).getBindingInfo().getBindingPath();
                if (StringUtils.isNotBlank(bindingPath)) {
                    for (String exclude : getExcludeProperties()) {
                        if (bindingPath.endsWith(exclude)) {
                            excluded = true;
                            break;
                        }
                    }
                }
            }

            String rowCSS = "";
            int defaultSuffix = 0;
            boolean suppressLabel = false;

            for (ComparableInfo comparable : groupComparables) {
                String idSuffix = comparable.getIdSuffix();
                if (StringUtils.isBlank(idSuffix)) {
                    idSuffix = UifConstants.IdSuffixes.COMPARE + defaultSuffix;
                }

                Component compareItem = ComponentUtils.copy(item, idSuffix);

                ComponentUtils.setComponentPropertyDeep(compareItem, UifPropertyPaths.BIND_OBJECT_PATH,
                        comparable.getBindingObjectPath());


                List<CollectionGroup> collectionGroups = ViewLifecycleUtils.getElementsOfTypeDeep(compareItem, CollectionGroup.class);

                for (CollectionGroup collectionGroup : collectionGroups){
                    updateCollectionGroupPath(collectionGroup,comparable.getBindingObjectPath());
                }

                if (comparable.isReadOnly()) {
                    compareItem.setReadOnly(true);
                    if (compareItem.getPropertyExpressions().containsKey("readOnly")) {
                        compareItem.getPropertyExpressions().remove("readOnly");
                    }
                }

                // label will be enabled for first comparable only
                if (suppressLabel && (compareItem instanceof Field)) {
                    ((Field) compareItem).getFieldLabel().setRender(false);
                }

                // Do value comparison if, among other things, this component hasn't been excluded.
                if (performValueChangeComparison && comparable.isHighlightValueChange() && !comparable
                        .isCompareToForValueChange() && ! excluded) {
                    boolean valueChanged = performValueComparison(group, compareItem, model,
                            compareValueObjectBindingPath);

                    if (valueChanged){
                        rowCSS = "cm-compare-highlighter";
                    }
                }

                //  If this is an excluded component then don't display the right-column/compare-to data.
                //  Typically it won't exist but if it does it won't be displayed.
                if (excluded) {
                    item.setStyle("display: none;");
                }

                comparisonItems.add(compareItem);

                defaultSuffix++;

                suppressLabel = true;
            }

            rowCssClasses.add(rowCSS);

            index++;
        }

        if (group.getLayoutManager() instanceof GridLayoutManager) {
            ((GridLayoutManager) group.getLayoutManager()).setRowCssClasses(rowCssClasses);
        }
        // update the group's list of components
        group.setItems(comparisonItems);
    }

    protected void updateCollectionGroupPath(CollectionGroup collectionGroup,String path){

        ComponentUtils.setComponentPropertyDeep(collectionGroup, UifPropertyPaths.BIND_OBJECT_PATH,path);
        ComponentUtils.setComponentPropertyDeep(collectionGroup, "fieldBindingObjectPath",path);

        List<CollectionGroup> subCollection = collectionGroup.getSubCollections();
        for (CollectionGroup subCollectionGroup1 : subCollection){
            updateCollectionGroupPath(subCollectionGroup1, path);
        }

        List<? extends Component> dataFields = collectionGroup.getItems();
        for (Component field : dataFields) {
            ComponentUtils.setComponentPropertyDeep(field, UifPropertyPaths.BIND_OBJECT_PATH,path);
            ComponentUtils.setComponentPropertyDeep(field, "fieldBindingObjectPath",path);
            if (field instanceof DataField) {
                ((DataField)field).getBindingInfo().setBindingObjectPath(path);
            }
        }
    }

    protected boolean performValueComparison(Group group, Component compareItem, Object model,
            String compareValueObjectBindingPath) {
        // get any attribute fields for the item so we can compare the values
        List<DataField> itemFields = ViewLifecycleUtils.getElementsOfTypeDeep(compareItem, DataField.class);
        boolean valueChanged = false;
        for (DataField field : itemFields) {
            String fieldBindingPath = field.getBindingInfo().getBindingPath();
            Object fieldValue = ObjectPropertyUtils.getPropertyValue(model, fieldBindingPath);

            String compareBindingPath = StringUtils.replaceOnce(fieldBindingPath,
                    field.getBindingInfo().getBindingObjectPath(), compareValueObjectBindingPath);
            Object compareValue = ObjectPropertyUtils.getPropertyValue(model, compareBindingPath);

            if (!((fieldValue == null) && (compareValue == null))) {
                // if one is null then value changed
                if ((fieldValue == null) || (compareValue == null)) {
                    valueChanged = true;
                } else {
                    // both not null, compare values
                    valueChanged = !fieldValue.equals(compareValue);
                }
            }
        }
        return valueChanged;
    }

}
