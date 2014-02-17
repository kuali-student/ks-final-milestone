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
import org.kuali.rice.krad.uif.CssConstants;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.UifPropertyPaths;
import org.kuali.rice.krad.uif.component.Component;
import org.kuali.rice.krad.uif.container.CollectionGroup;
import org.kuali.rice.krad.uif.container.Container;
import org.kuali.rice.krad.uif.element.Action;
import org.kuali.rice.krad.uif.field.Field;
import org.kuali.rice.krad.uif.field.FieldGroup;
import org.kuali.rice.krad.uif.layout.CollectionLayoutManager;
import org.kuali.rice.krad.uif.layout.CollectionLayoutUtils;
import org.kuali.rice.krad.uif.layout.TableLayoutManager;
import org.kuali.rice.krad.uif.lifecycle.ViewLifecycle;
import org.kuali.rice.krad.uif.util.ComponentUtils;
import org.kuali.rice.krad.uif.view.ExpressionEvaluator;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krad.web.form.UifFormBase;

import java.util.List;

/**
 * This ia an override of the TableLayoutManager to not show the header and footer of the table.
 *
 * @author Kuali Student Team
 */
public class SimpleTableLayoutManager extends TableLayoutManager {

    /**
     * Setup the column calculations functionality and components
     *
     * @param model the model
     * @param container the parent container
     * @param totalColumns total number of columns in the table
     */
    @Override
    protected void setupColumnCalculations(Object model, Container container, int totalColumns) {
        if(this.getColumnCalculations().size()>0){
            super.setupColumnCalculations(model, container, totalColumns);
        }
    }

    /**
     * Assembles the field instances for the collection line. The given sequence
     * field prototype is copied for the line sequence field. Likewise a copy of
     * the actionFieldPrototype is made and the given actions are set as the
     * items for the action field. Finally the generated items are assembled
     * together into the allRowFields list with the given lineFields.
     *
     * @see CollectionLayoutManager#buildLine(Object, CollectionGroup, List, List, String, List, String, Object, int)
     */
    @Override
    public void buildLine(Object model, CollectionGroup collectionGroup, List<Field> lineFields,
                          List<FieldGroup> subCollectionFields, String bindingPath, List<? extends Component> actions, String idSuffix,
                          Object currentLine, int lineIndex) {

        View view = ViewLifecycle.getView();

        // since expressions are not evaluated on child components yet, we need to evaluate any properties
        // we are going to read for building the table
        ExpressionEvaluator expressionEvaluator = ViewLifecycle.getHelper().getExpressionEvaluatorFactory().createExpressionEvaluator();
        for (Field lineField : lineFields) {
            lineField.pushObjectToContext(UifConstants.ContextVariableNames.PARENT, collectionGroup);
            lineField.pushAllToContext(view.getContext());
            lineField.pushObjectToContext(UifConstants.ContextVariableNames.THEME_IMAGES,
                    view.getTheme().getImageDirectory());
            lineField.pushObjectToContext(UifConstants.ContextVariableNames.COMPONENT, lineField);

            expressionEvaluator.evaluatePropertyExpression(view, lineField.getContext(), lineField,
                    UifPropertyPaths.ROW_SPAN, true);
            expressionEvaluator.evaluatePropertyExpression(view, lineField.getContext(), lineField,
                    UifPropertyPaths.COL_SPAN, true);
            expressionEvaluator.evaluatePropertyExpression(view, lineField.getContext(), lineField,
                    UifPropertyPaths.REQUIRED, true);
            expressionEvaluator.evaluatePropertyExpression(view, lineField.getContext(), lineField,
                    UifPropertyPaths.READ_ONLY, true);
        }

        // if first line for table set number of data columns
        if (this.getAllRowFields().isEmpty()) {
            if (isSuppressLineWrapping()) {
                setNumberOfDataColumns(lineFields.size());
            } else {
                setNumberOfDataColumns(getNumberOfColumns());
            }
        }

        boolean isAddLine = false;

        boolean renderActions = collectionGroup.isRenderLineActions() && !collectionGroup.isReadOnly();
        int extraColumns = 0;
        String rowCss = "";
        boolean addLineInTable =
                collectionGroup.isRenderAddLine() && !collectionGroup.isReadOnly() && !isSeparateAddLine();

        if (collectionGroup.isHighlightNewItems() && ((UifFormBase) model).isAddedCollectionItem(currentLine)) {
            rowCss = collectionGroup.getNewItemsCssClass();
        } else if (isAddLine && addLineInTable) {
            rowCss = collectionGroup.getAddItemCssClass();
            this.addStyleClass(CssConstants.Classes.HAS_ADD_LINE);
        }

        // do not allow null rowCss
        if (rowCss == null) {
            rowCss = "";
        }

        rowCss = StringUtils.removeStart(rowCss, " ");
        this.getRowCssClasses().add(rowCss);

        // set label field rendered to true on line fields and adjust cell properties
        for (Field field : lineFields) {
            field.setLabelRendered(true);
            field.setFieldLabel(null);

            setCellAttributes(field);
        }

        int rowCount = calculateNumberOfRows(lineFields);
        int rowSpan = rowCount + subCollectionFields.size();

        // select field will come after sequence field (if enabled) or be first column
        if (collectionGroup.isIncludeLineSelectionField()) {
            Field selectField = ComponentUtils.copy(getSelectFieldPrototype(), idSuffix);
            CollectionLayoutUtils.prepareSelectFieldForLine(selectField, collectionGroup, bindingPath, currentLine);

            ComponentUtils.updateContextForLine(selectField, currentLine, lineIndex, idSuffix);
            setCellAttributes(selectField);

            this.getAllRowFields().add(selectField);

            extraColumns++;
        }

        // now add the fields in the correct position
        int cellPosition = 0;
        int columnNumber = 0;

        boolean insertActionField = false;

        for (Field lineField : lineFields) {

            cellPosition += lineField.getColSpan();
            columnNumber++;

            this.getAllRowFields().add(lineField);

            //details action
            if (lineField instanceof FieldGroup && ((FieldGroup) lineField).getItems() != null) {
                for (Component component : ((FieldGroup) lineField).getItems()) {
                    if (component != null && component instanceof Action && component.getDataAttributes().get("role")
                            != null && component.getDataAttributes().get("role").equals("detailsLink") &&
                            StringUtils.isBlank(((Action) component).getActionScript())) {
                        ((Action) component).setActionScript("rowDetailsActionHandler(this,'" + this.getId() + "');");
                    }
                }
            }
        }

        // add sub-collection fields to end of data fields
        this.getAllRowFields().addAll(subCollectionFields);
    }
}
