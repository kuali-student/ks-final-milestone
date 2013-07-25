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
import org.kuali.rice.krad.datadictionary.parse.BeanTagAttribute;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.UifPropertyPaths;
import org.kuali.rice.krad.uif.component.Component;
import org.kuali.rice.krad.uif.component.DataBinding;
import org.kuali.rice.krad.uif.container.CollectionGroup;
import org.kuali.rice.krad.uif.container.Container;
import org.kuali.rice.krad.uif.container.Group;
import org.kuali.rice.krad.uif.element.Action;
import org.kuali.rice.krad.uif.field.DataField;
import org.kuali.rice.krad.uif.field.Field;
import org.kuali.rice.krad.uif.field.FieldGroup;
import org.kuali.rice.krad.uif.field.MessageField;
import org.kuali.rice.krad.uif.layout.CollectionLayoutUtils;
import org.kuali.rice.krad.uif.layout.TableLayoutManager;
import org.kuali.rice.krad.uif.service.ExpressionEvaluatorService;
import org.kuali.rice.krad.uif.util.ColumnCalculationInfo;
import org.kuali.rice.krad.uif.util.ComponentFactory;
import org.kuali.rice.krad.uif.util.ComponentUtils;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krad.uif.widget.RichTable;
import org.kuali.rice.krad.web.form.UifFormBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Kuali Student Team
 */
public class SimpleTableLayoutManager extends TableLayoutManager {

    private boolean useShortLabels;

    private boolean renderSequenceField;
    private boolean generateAutoSequence;
    private Field sequenceFieldPrototype;

    private FieldGroup subCollectionFieldGroupPrototype;
    private Field selectFieldPrototype;

    //private boolean separateAddLine;
    //private Group addLineGroup;

    // internal counter for the data columns (not including sequence, action)
    private int numberOfDataColumns;

    private List<Component> dataFields;

    private RichTable richTable;

    private int actionColumnIndex = -1;
    private String actionColumnPlacement;

    //row details properties
    private Group rowDetailsGroup;
    private boolean rowDetailsOpen;
    private boolean showToggleAllDetails;
    private Action toggleAllDetailsAction;
    private boolean ajaxDetailsRetrieval;
    private Action expandDetailsActionPrototype;

    public SimpleTableLayoutManager() {
        useShortLabels = false;
        renderSequenceField = true;
        generateAutoSequence = false;
        //separateAddLine = false;
        rowDetailsOpen = false;

        dataFields = new ArrayList<Component>();
    }

    /**
     * The following actions are performed:
     *
     * <ul>
     * <li>Sets sequence field prototype if auto sequence is true</li>
     * <li>Initializes the prototypes</li>
     * </ul>
     *
     * @see org.kuali.rice.krad.uif.layout.BoxLayoutManager#performInitialization(org.kuali.rice.krad.uif.view.View,
     *      java.lang.Object, org.kuali.rice.krad.uif.container.Container)
     */
    @Override
    public void performInitialization(View view, Object model, Container container) {
        CollectionGroup collectionGroup = (CollectionGroup) container;

        this.setupDetails(collectionGroup, view);

        if (collectionGroup.isAddViaLightBox()) {
            setSeparateAddLine(true);
        }

        super.performInitialization(view, model, container);

        getRowCssClasses().clear();

        if (generateAutoSequence && !(getSequenceFieldPrototype() instanceof MessageField)) {
            sequenceFieldPrototype = ComponentFactory.getMessageField();
            view.assignComponentIds(getSequenceFieldPrototype());
        }
    }

    /**
     * performApplyModel override.  Takes expressions that may be set in the columnCalculation objects
     * and populates them correctly into those component's propertyExpressions.
     *
     * @param view view instance to which the layout manager belongs
     * @param model Top level object containing the data (could be the form or a
     * top level business object, dto)
     * @param container
     */
    @Override
    public void performApplyModel(View view, Object model, Container container) {
        super.performApplyModel(view, model, container);

    }

    /**
     * Sets up the final column count for rendering based on whether the
     * sequence and action fields have been generated, sets up column calculations, and richTable rowGrouping
     * options
     *
     * @see org.kuali.rice.krad.uif.layout.LayoutManagerBase#performFinalize(org.kuali.rice.krad.uif.view.View,
     *      java.lang.Object, org.kuali.rice.krad.uif.container.Container)
     */
    @Override
    public void performFinalize(View view, Object model, Container container) {
        super.performFinalize(view, model, container);

        UifFormBase formBase = (UifFormBase) model;

        CollectionGroup collectionGroup = (CollectionGroup) container;

        int totalColumns = getNumberOfDataColumns();
        if (renderSequenceField) {
            totalColumns++;
        }

        if (collectionGroup.isIncludeLineSelectionField()) {
            totalColumns++;
        }

        setNumberOfColumns(totalColumns);

    }

    /**
     * Assembles the field instances for the collection line. The given sequence
     * field prototype is copied for the line sequence field. Likewise a copy of
     * the actionFieldPrototype is made and the given actions are set as the
     * items for the action field. Finally the generated items are assembled
     * together into the dataFields list with the given lineFields.
     *
     * @see org.kuali.rice.krad.uif.layout.CollectionLayoutManager#buildLine(org.kuali.rice.krad.uif.view.View,
     *      java.lang.Object, org.kuali.rice.krad.uif.container.CollectionGroup,
     *      java.util.List, java.util.List, java.lang.String, java.util.List,
     *      java.lang.String, java.lang.Object, int)
     */
    public void buildLine(View view, Object model, CollectionGroup collectionGroup, List<Field> lineFields,
                          List<FieldGroup> subCollectionFields, String bindingPath, List<Action> actions, String idSuffix,
                          Object currentLine, int lineIndex) {

        // since expressions are not evaluated on child components yet, we need to evaluate any properties
        // we are going to read for building the table
        ExpressionEvaluatorService expressionEvaluatorService = KRADServiceLocatorWeb.getExpressionEvaluatorService();
        for (Field lineField : lineFields) {
            lineField.pushObjectToContext(UifConstants.ContextVariableNames.PARENT, collectionGroup);
            lineField.pushAllToContext(view.getViewHelperService().getCommonContext(view, lineField));

            expressionEvaluatorService.evaluatePropertyExpression(view, model, lineField.getContext(), lineField,
                    UifPropertyPaths.ROW_SPAN, true);
            expressionEvaluatorService.evaluatePropertyExpression(view, model, lineField.getContext(), lineField,
                    UifPropertyPaths.COL_SPAN, true);
            expressionEvaluatorService.evaluatePropertyExpression(view, model, lineField.getContext(), lineField,
                    UifPropertyPaths.REQUIRED, true);
            expressionEvaluatorService.evaluatePropertyExpression(view, model, lineField.getContext(), lineField,
                    UifPropertyPaths.READ_ONLY, true);
        }

        // if first line for table set number of data columns
        if (dataFields.isEmpty()) {
            if (isSuppressLineWrapping()) {
                setNumberOfDataColumns(lineFields.size());
            } else {
                setNumberOfDataColumns(getNumberOfColumns());
            }
        }

        boolean isAddLine = false;

        // If first row or row wrap is happening
        if (lineIndex == -1 || (lineFields.size() != numberOfDataColumns
                && ((lineIndex + 1) * numberOfDataColumns) < lineFields.size())) {
            isAddLine = true;
        }

        // set label field rendered to true on line fields and adjust cell properties
        for (Field field : lineFields) {
            field.setLabelRendered(true);
            field.setFieldLabel(null);

            setCellAttributes(field);
        }

        int rowCount = calculateNumberOfRows(lineFields);
        int rowSpan = rowCount + subCollectionFields.size();

        // sequence field is always first and should span all rows for the line
        if (renderSequenceField) {
            Component sequenceField = null;
            if (!isAddLine) {
                sequenceField = ComponentUtils.copy(getSequenceFieldPrototype(), idSuffix);

                //Ignore in validation processing
                sequenceField.addDataAttribute(UifConstants.DataAttributes.VIGNORE, "yes");

                if (generateAutoSequence && (sequenceField instanceof MessageField)) {
                    ((MessageField) sequenceField).setMessageText(Integer.toString(lineIndex + 1));
                }
            } else {
                sequenceField = ComponentUtils.copy(collectionGroup.getAddLineLabel(), idSuffix);

                // adjusting add line label to match sequence prototype cells attributes
                sequenceField.setCellWidth(getSequenceFieldPrototype().getCellWidth());
                sequenceField.setCellStyle(getSequenceFieldPrototype().getCellStyle());
            }

            sequenceField.setRowSpan(rowSpan);

            if (sequenceField instanceof DataBinding) {
                ((DataBinding) sequenceField).getBindingInfo().setBindByNamePrefix(bindingPath);
            }

            setCellAttributes(sequenceField);

            ComponentUtils.updateContextForLine(sequenceField, currentLine, lineIndex, idSuffix);
            dataFields.add(sequenceField);

        }

        // select field will come after sequence field (if enabled) or be first column
        if (collectionGroup.isIncludeLineSelectionField()) {
            Field selectField = ComponentUtils.copy(getSelectFieldPrototype(), idSuffix);
            CollectionLayoutUtils.prepareSelectFieldForLine(selectField, collectionGroup, bindingPath, currentLine);

            ComponentUtils.updateContextForLine(selectField, currentLine, lineIndex, idSuffix);
            setCellAttributes(selectField);

            dataFields.add(selectField);

        }

        for (Field lineField : lineFields) {

            dataFields.add(lineField);

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

        // update colspan on sub-collection fields
        for (FieldGroup subCollectionField : subCollectionFields) {
            subCollectionField.setColSpan(numberOfDataColumns);
        }

        // add sub-collection fields to end of data fields
        dataFields.addAll(subCollectionFields);
    }

    /**
     * Calculates how many rows will be needed per collection line to display
     * the list of fields. Assumption is made that the total number of cells the
     * fields take up is evenly divisible by the configured number of columns
     *
     * @param items list of items that make up one collection line
     * @return number of rows
     */
    protected int calculateNumberOfRows(List<? extends Field> items) {
        int rowCount = 0;

        // check flag that indicates only one row should be created
        if (isSuppressLineWrapping()) {
            return 1;
        }

        // If Overflow is greater than 0 then calculate the col span for the last item in the overflowed row
        if (items.size() % getNumberOfDataColumns() > 0) {
            //get the last line item
            Field field = items.get(items.size() - 1);

            int colSize = 0;
            for (Field f : items) {
                colSize += f.getColSpan();
            }

            field.setColSpan(1 + (numberOfDataColumns - (colSize % numberOfDataColumns)));
            rowCount = ((items.size() / getNumberOfDataColumns()) + 1);
        } else {
            rowCount = items.size() / getNumberOfDataColumns();
        }
        return rowCount;
    }

    /**
     * @see org.kuali.rice.krad.uif.layout.CollectionLayoutManager#getSupportedContainer()
     */
    @Override
    public Class<? extends Container> getSupportedContainer() {
        return CollectionGroup.class;
    }

    /**
     * @see org.kuali.rice.krad.uif.layout.LayoutManagerBase#getComponentsForLifecycle()
     */
    @Override
    public List<Component> getComponentsForLifecycle() {
        List<Component> components = super.getComponentsForLifecycle();

        components.add(richTable);
        //components.add(addLineGroup);
        components.addAll(dataFields);

        if (isShowToggleAllDetails()) {
            components.add(toggleAllDetailsAction);
        }

        return components;
    }

    /**
     * @see org.kuali.rice.krad.uif.layout.LayoutManager#getComponentPrototypes()
     */
    @Override
    public List<Component> getComponentPrototypes() {
        List<Component> components = super.getComponentPrototypes();

        components.add(getHeaderLabelPrototype());
        components.add(getSequenceFieldPrototype());
        components.add(getActionFieldPrototype());
        components.add(getSubCollectionFieldGroupPrototype());
        components.add(getSelectFieldPrototype());

        return components;
    }

    /**
     * Indicates whether the short label for the collection field should be used
     * as the table header or the regular label
     *
     * @return true if short label should be used, false if long label
     *         should be used
     */
    @BeanTagAttribute(name = "useShortLabels")
    public boolean isUseShortLabels() {
        return this.useShortLabels;
    }

    /**
     * Setter for the use short label indicator
     *
     * @param useShortLabels
     */
    public void setUseShortLabels(boolean useShortLabels) {
        this.useShortLabels = useShortLabels;
    }

    /**
     * Indicates whether the sequence field should be rendered for the
     * collection
     *
     * @return true if sequence field should be rendered, false if not
     */
    @BeanTagAttribute(name = "renderSequenceField")
    public boolean isRenderSequenceField() {
        return this.renderSequenceField;
    }

    /**
     * Setter for the render sequence field indicator
     *
     * @param renderSequenceField
     */
    public void setRenderSequenceField(boolean renderSequenceField) {
        this.renderSequenceField = renderSequenceField;
    }

    /**
     * Attribute name to use as sequence value. For each collection line the
     * value of this field on the line will be retrieved and used as the
     * sequence value
     *
     * @return sequence property name
     */
    @BeanTagAttribute(name = "sequencePropertyName")
    public String getSequencePropertyName() {
        if ((getSequenceFieldPrototype() != null) && (getSequenceFieldPrototype() instanceof DataField)) {
            return ((DataField) getSequenceFieldPrototype()).getPropertyName();
        }

        return null;
    }

    /**
     * Setter for the sequence property name
     *
     * @param sequencePropertyName
     */
    public void setSequencePropertyName(String sequencePropertyName) {
        if ((getSequenceFieldPrototype() != null) && (getSequenceFieldPrototype() instanceof DataField)) {
            ((DataField) getSequenceFieldPrototype()).setPropertyName(sequencePropertyName);
        }
    }

    /**
     * Indicates whether the sequence field should be generated with the current
     * line number
     *
     * <p>
     * If set to true the sequence field prototype will be changed to a message
     * field (if not already a message field) and the text will be set to the
     * current line number
     * </p>
     *
     * @return true if the sequence field should be generated from the
     *         line number, false if not
     */
    @BeanTagAttribute(name = "generateAutoSequence")
    public boolean isGenerateAutoSequence() {
        return this.generateAutoSequence;
    }

    /**
     * Setter for the generate auto sequence field
     *
     * @param generateAutoSequence
     */
    public void setGenerateAutoSequence(boolean generateAutoSequence) {
        this.generateAutoSequence = generateAutoSequence;
    }

    /**
     * {@code Field} instance to serve as a prototype for the
     * sequence field. For each collection line this instance is copied and
     * adjusted as necessary
     *
     * @return Attribute field instance
     */
    @BeanTagAttribute(name = "sequenceFieldPrototype", type = BeanTagAttribute.AttributeType.SINGLEBEAN)
    public Field getSequenceFieldPrototype() {
        return this.sequenceFieldPrototype;
    }

    /**
     * Setter for the sequence field prototype
     *
     * @param sequenceFieldPrototype
     */
    public void setSequenceFieldPrototype(Field sequenceFieldPrototype) {
        this.sequenceFieldPrototype = sequenceFieldPrototype;
    }

    /**
     * @see org.kuali.rice.krad.uif.layout.CollectionLayoutManager#getSubCollectionFieldGroupPrototype()
     */
    @BeanTagAttribute(name = "subCollectionFieldGroupPrototype", type = BeanTagAttribute.AttributeType.SINGLEBEAN)
    public FieldGroup getSubCollectionFieldGroupPrototype() {
        return this.subCollectionFieldGroupPrototype;
    }

    /**
     * Setter for the sub-collection field group prototype
     *
     * @param subCollectionFieldGroupPrototype
     */
    public void setSubCollectionFieldGroupPrototype(FieldGroup subCollectionFieldGroupPrototype) {
        this.subCollectionFieldGroupPrototype = subCollectionFieldGroupPrototype;
    }

    /**
     * Field instance that serves as a prototype for creating the select field on each line when
     * {@link org.kuali.rice.krad.uif.container.CollectionGroup#isIncludeLineSelectionField()} is true
     *
     * <p>
     * This prototype can be used to set the control used for the select field (generally will be a checkbox control)
     * in addition to styling and other setting. The binding path will be formed with using the
     * {@link org.kuali.rice.krad.uif.container.CollectionGroup#getLineSelectPropertyName()} or if not set the
     * framework
     * will use {@link org.kuali.rice.krad.web.form.UifFormBase#getSelectedCollectionLines()}
     * </p>
     *
     * @return select field prototype instance
     */
    @BeanTagAttribute(name = "selectFieldPrototype", type = BeanTagAttribute.AttributeType.SINGLEBEAN)
    public Field getSelectFieldPrototype() {
        return selectFieldPrototype;
    }

    /**
     * Setter for the prototype instance for select fields
     *
     * @param selectFieldPrototype
     */
    public void setSelectFieldPrototype(Field selectFieldPrototype) {
        this.selectFieldPrototype = selectFieldPrototype;
    }

    /**
     * Indicates whether the add line should be rendered in a separate group, or as part of the table (first line)
     *
     * <p>
     * When separate add line is enabled, the fields for the add line will be placed in the {@link #getAddLineGroup()}.
     * This group can be used to configure the add line presentation. In addition to the fields, the header on the
     * group (unless already set) will be set to
     * {@link org.kuali.rice.krad.uif.container.CollectionGroup#getAddLabel()} and the add line actions will
     * be placed into the group's footer.
     * </p>
     *
     * @return true if add line should be separated, false if it should be placed into the table
     */
    //@BeanTagAttribute(name = "separateAddLine")
    //public boolean isSeparateAddLine() {
    //    return separateAddLine;
    //}

    /**
     * Setter for the separate add line indicator
     *
     * @param separateAddLine
     */
    //public void setSeparateAddLine(boolean separateAddLine) {
    //    this.separateAddLine = separateAddLine;
    //}

    /**
     * When {@link #isSeparateAddLine()} is true, this group will be used to render the add line
     *
     * <p>
     * This group can be used to configure how the add line will be rendered. For example the layout manager configured
     * on the group will be used to rendered the add line fields. If the header (title) is not set on the group, it
     * will be set from
     * {@link org.kuali.rice.krad.uif.container.CollectionGroup#getAddLabel()}. In addition,
     * {@link org.kuali.rice.krad.uif.container.CollectionGroup#getAddLineActions()} will be added to the group
     * footer items.
     * </p>
     *
     * @return Group instance for the collection add line
     */
    //@BeanTagAttribute(name = "addLineGroup", type = BeanTagAttribute.AttributeType.SINGLEBEAN)
    //public Group getAddLineGroup() {
    //    return addLineGroup;
    //}

    /**
     * Setter for the add line Group
     *
     * @param addLineGroup
     */
    //public void setAddLineGroup(Group addLineGroup) {
    //    this.addLineGroup = addLineGroup;
    //}

    /**
     * List of {@code Component} instances that make up the tables body. Pulled
     * by the layout manager template to send through the Grid layout
     *
     * @return table body fields
     */
    public List<Component> getDataFields() {
        return this.dataFields;
    }

    /**
     * Widget associated with the table to add functionality such as sorting,
     * paging, and export
     *
     * @return RichTable instance
     */
    @BeanTagAttribute(name = "richTable", type = BeanTagAttribute.AttributeType.SINGLEBEAN)
    public RichTable getRichTable() {
        return this.richTable;
    }

    /**
     * Setter for the rich table widget
     *
     * @param richTable
     */
    public void setRichTable(RichTable richTable) {
        this.richTable = richTable;
    }

    /**
     * @return the numberOfDataColumns
     */
    @BeanTagAttribute(name = "numberOfDataColumns")
    public int getNumberOfDataColumns() {
        return this.numberOfDataColumns;
    }

    /**
     * @param numberOfDataColumns the numberOfDataColumns to set
     */
    public void setNumberOfDataColumns(int numberOfDataColumns) {
        this.numberOfDataColumns = numberOfDataColumns;
    }

    /**
     * @see org.kuali.rice.krad.uif.widget.RichTable#getHiddenColumns()
     */
    @BeanTagAttribute(name = "hiddenColumns", type = BeanTagAttribute.AttributeType.SETVALUE)
    public Set<String> getHiddenColumns() {
        if (richTable != null) {
            return richTable.getHiddenColumns();
        }

        return null;
    }

    /**
     * @see org.kuali.rice.krad.uif.widget.RichTable#setHiddenColumns(java.util.Set<java.lang.String>)
     */
    public void setHiddenColumns(Set<String> hiddenColumns) {
        if (richTable != null) {
            richTable.setHiddenColumns(hiddenColumns);
        }
    }

    /**
     * @see org.kuali.rice.krad.uif.widget.RichTable#getSortableColumns()
     */
    @BeanTagAttribute(name = "sortableColumns", type = BeanTagAttribute.AttributeType.SETVALUE)
    public Set<String> getSortableColumns() {
        if (richTable != null) {
            return richTable.getSortableColumns();
        }

        return null;
    }

    /**
     * @see org.kuali.rice.krad.uif.widget.RichTable#setSortableColumns(java.util.Set<java.lang.String>)
     */
    public void setSortableColumns(Set<String> sortableColumns) {
        if (richTable != null) {
            richTable.setSortableColumns(sortableColumns);
        }
    }

    /**
     * Indicates the index of the action column
     *
     * @return the action column index
     */
    @BeanTagAttribute(name = "actionColumnIndex")
    public int getActionColumnIndex() {
        return actionColumnIndex;
    }

    /**
     * Indicates the actions column placement
     *
     * <p>
     * Valid values are 'LEFT', 'RIGHT' or any valid number. The default is 'RIGHT' or '-1'. The column placement index
     * takes all displayed columns, including sequence and selection columns, into account.
     * </p>
     *
     * @return the action column placement
     */
    @BeanTagAttribute(name = "actionColumnPlacement")
    public String getActionColumnPlacement() {
        return actionColumnPlacement;
    }

    /**
     * Setter for the action column placement
     *
     * @param actionColumnPlacement action column placement string
     */
    public void setActionColumnPlacement(String actionColumnPlacement) {
        this.actionColumnPlacement = actionColumnPlacement;

        if ("LEFT".equals(actionColumnPlacement)) {
            actionColumnIndex = 1;
        } else if ("RIGHT".equals(actionColumnPlacement)) {
            actionColumnIndex = -1;
        } else if (StringUtils.isNumeric(actionColumnPlacement)) {
            actionColumnIndex = Integer.parseInt(actionColumnPlacement);
        }
    }

    /**
     * The row details info group to use when using a TableLayoutManager with the a richTable.
     *
     * <p>This group will be displayed when the user clicks the "Details" link/image on a row.
     * This allows extra/long data to be hidden in table rows and then revealed during interaction
     * with the table without the need to leave the page.  Allows for any group content.</p>
     *
     * <p>Does not currently work with javascript required content.</p>
     *
     * @return rowDetailsGroup component
     */
    @BeanTagAttribute(name = "rowDetailsGroup", type = BeanTagAttribute.AttributeType.SINGLEBEAN)
    public Group getRowDetailsGroup() {
        return rowDetailsGroup;
    }

    /**
     * Set the row details info group
     *
     * @param rowDetailsGroup row details group
     */
    public void setRowDetailsGroup(Group rowDetailsGroup) {
        this.rowDetailsGroup = rowDetailsGroup;
    }

    /**
     * Creates the details group for the line using the information setup through the setter methods of this
     * interface.  Line details are currently only supported in TableLayoutManagers which use richTable.
     *
     * @param collectionGroup the CollectionGroup for this TableLayoutManager
     * @param view the current view
     */
    public void setupDetails(CollectionGroup collectionGroup, View view) {
        if (getRowDetailsGroup() == null || this.getRichTable() == null || !this.getRichTable().isRender()) {
            return;
        }

        //data attribute to mark this group to open itself when rendered
        collectionGroup.addDataAttribute(UifConstants.DataAttributes.DETAILS_DEFAULT_OPEN, Boolean.toString(this.rowDetailsOpen));

        toggleAllDetailsAction.addDataAttribute("open", Boolean.toString(this.rowDetailsOpen));
        toggleAllDetailsAction.addDataAttribute("tableid", this.getId());

        this.getRowDetailsGroup().setHidden(true);

        FieldGroup detailsFieldGroup = ComponentFactory.getFieldGroup();

        TreeMap<String, String> dataAttributes = new TreeMap<String, String>();
        dataAttributes.put("role", "detailsFieldGroup");
        detailsFieldGroup.setDataAttributes(dataAttributes);

        Action rowDetailsAction = this.getExpandDetailsActionPrototype();
        rowDetailsAction.addDataAttribute(UifConstants.DataAttributes.ROLE, "detailsLink");
        rowDetailsAction.setId(collectionGroup.getId() + "_detLink");

        List<Component> detailsItems = new ArrayList<Component>();
        detailsItems.add(rowDetailsAction);

        dataAttributes = new TreeMap<String, String>();
        dataAttributes.put("role", "details");
        this.getRowDetailsGroup().setDataAttributes(dataAttributes);

        if (ajaxDetailsRetrieval) {
            this.getRowDetailsGroup().setRender(false);
            this.getRowDetailsGroup().setDisclosedByAction(true);
        }

        detailsItems.add(getRowDetailsGroup());
        detailsFieldGroup.setItems(detailsItems);
        detailsFieldGroup.setId(collectionGroup.getId() + "_detGroup");
        view.assignComponentIds(detailsFieldGroup);


        List<Component> theItems = new ArrayList<Component>();
        theItems.add(detailsFieldGroup);
        theItems.addAll(collectionGroup.getItems());
        collectionGroup.setItems(theItems);
    }

    /**
     * A list of all the columns to be calculated
     *
     * <p>
     * The list must contain valid column indexes. The indexes takes all displayed columns into account.
     * </p>
     *
     * @return the total columns list
     */
    public List<String> getColumnsToCalculate() {
        return null;
    }

    /**
     * Gets showTotal. showTotal shows/calculates the total field when true, otherwise it is not rendered.
     * <br/>
     * <b>Only used when renderOnlyLeftTotalLabels is TRUE, this overrides the ColumnConfigurationInfo setting.
     * Otherwise, the ColumnConfigurationInfo setting takes precedence.</b>
     *
     * @return true if showing the total, false otherwise.
     */
    @BeanTagAttribute(name = "showTotal")
    public boolean isShowTotal() {
        return false;
    }

    /**
     * Gets showTotal. showTotal shows/calculates the total field when true, otherwise it is not rendered.
     * <br/>
     * <b>Only used when renderOnlyLeftTotalLabels is TRUE, this overrides the ColumnConfigurationInfo setting.
     * Otherwise, the ColumnConfigurationInfo setting takes precedence.</b>
     *
     * @return true if showing the page total, false otherwise.
     */
    @BeanTagAttribute(name = "showPageTotal")
    public boolean isShowPageTotal() {
        return false;
    }

    /**
     * Gets showGroupTotal. showGroupTotal shows/calculates the total field for each grouping when true (and only
     * when the table actually has grouping turned on), otherwise it is not rendered.
     * <br/>
     * <b>Only used when renderOnlyLeftTotalLabels is TRUE, this overrides the ColumnConfigurationInfo setting.
     * Otherwise, the ColumnConfigurationInfo setting takes precedence.</b>
     *
     * @return true if showing the group total, false otherwise.
     */
    @BeanTagAttribute(name = "showGroupTotal")
    public boolean isShowGroupTotal() {
        return false;
    }

    /**
     * Gets the column calculations.  This is a list of ColumnCalcuationInfo that when set provides calculations
     * to be performed on the columns they specify.  These calculations appear in the table's footer.  This feature is
     * only available when using richTable functionality.
     *
     * @return the columnCalculations to use
     */
    @BeanTagAttribute(name = "columnCalculations", type = BeanTagAttribute.AttributeType.LISTBEAN)
    public List<ColumnCalculationInfo> getColumnCalculations() {
        return null;
    }

    /**
     * When true, labels for the totals fields will only appear in the left most column.  Showing of the totals
     * is controlled by the settings on the TableLayoutManager itself when this property is true.
     *
     * @return true when rendering totals footer labels in the left-most column, false otherwise
     */
    @BeanTagAttribute(name = "renderOnlyLeftTotalLabels")
    public boolean isRenderOnlyLeftTotalLabels() {
        return false;
    }

    /**
     * Gets the footer calculation components to be used by the layout.  These are set by the framework and cannot
     * be set directly.
     *
     * @return the list of components for the footer
     */
    public List<Component> getFooterCalculationComponents() {
        return null;
    }

    /**
     * If true, all details will be opened by default when the table loads.  Can only be used on tables that have
     * row details setup.
     *
     * @return true if row details
     */
    public boolean isRowDetailsOpen() {
        return rowDetailsOpen;
    }

    /**
     * Set if row details should be open on table load
     *
     * @param rowDetailsOpen
     */
    public void setRowDetailsOpen(boolean rowDetailsOpen) {
        this.rowDetailsOpen = rowDetailsOpen;
    }

    /**
     * If true, the toggleAllDetailsAction will be shown.  This button allows all details to
     * be open/closed simultaneously.
     *
     * @return true if the action button to toggle all row details opened/closed
     */
    public boolean isShowToggleAllDetails() {
        return showToggleAllDetails;
    }

    /**
     * Set if the toggleAllDetailsAction should be shown
     *
     * @param showToggleAllDetails
     */
    public void setShowToggleAllDetails(boolean showToggleAllDetails) {
        this.showToggleAllDetails = showToggleAllDetails;
    }

    /**
     * The toggleAllDetailsAction action component used to toggle all row details open/closed.  This property is set
     * by the default configuration and should not be reset in most cases.
     *
     * @return Action component to use for the toggle action button
     */
    public Action getToggleAllDetailsAction() {
        return toggleAllDetailsAction;
    }

    /**
     * Set the toggleAllDetailsAction action component used to toggle all row details open/closed.  This property is
     * set
     * by the default configuration and should not be reset in most cases.
     *
     * @param toggleAllDetailsAction
     */
    public void setToggleAllDetailsAction(Action toggleAllDetailsAction) {
        this.toggleAllDetailsAction = toggleAllDetailsAction;
    }

    /**
     * If true, when a row details open action is performed, it will get the details content from the server the first
     * time it is opened.  The methodToCall will be a component "refresh" call by default (this can be set on
     * expandDetailsActionPrototype) and the additional action parameters sent to the server will be those set
     * on the expandDetailsActionPrototype (lineIndex will be sent by default).
     *
     * @return true if ajax row details retrieval will be used
     */
    public boolean isAjaxDetailsRetrieval() {
        return ajaxDetailsRetrieval;
    }

    /**
     * Set if row details content should be retrieved fromt he server
     *
     * @param ajaxDetailsRetrieval
     */
    public void setAjaxDetailsRetrieval(boolean ajaxDetailsRetrieval) {
        this.ajaxDetailsRetrieval = ajaxDetailsRetrieval;
    }

    /**
     * The Action prototype used for the row details expand link.  Should be set to "Uif-ExpandDetailsAction" or
     * "Uif-ExpandDetailsImageAction".  Properties can be configured to allow for different methodToCall and
     * actionParameters to be set for ajax row details retrieval.
     *
     * @return the Action details link prototype
     */
    public Action getExpandDetailsActionPrototype() {
        return expandDetailsActionPrototype;
    }

    /**
     * Set the expand details Action prototype link
     *
     * @param expandDetailsActionPrototype
     */
    public void setExpandDetailsActionPrototype(Action expandDetailsActionPrototype) {
        this.expandDetailsActionPrototype = expandDetailsActionPrototype;
    }
}
