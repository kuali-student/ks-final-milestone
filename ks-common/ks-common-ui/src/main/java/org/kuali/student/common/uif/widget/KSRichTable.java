/**
 * Copyright 2013 The Kuali Foundation Licensed under the
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
 * Created by venkat on 8/8/13
 */
package org.kuali.student.common.uif.widget;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.component.Component;
import org.kuali.rice.krad.uif.container.CollectionGroup;
import org.kuali.rice.krad.uif.field.DataField;
import org.kuali.rice.krad.uif.field.FieldGroup;
import org.kuali.rice.krad.uif.field.MessageField;
import org.kuali.rice.krad.uif.layout.LayoutManager;
import org.kuali.rice.krad.uif.layout.TableLayoutManager;
import org.kuali.rice.krad.uif.widget.RichTable;

/**
 *
 * @author Kuali Student Team
 */

public class KSRichTable extends RichTable {

    /**
     * Builds column options for sorting
     *
     * @param collectionGroup
     */
    @Override
    protected void buildTableOptions(CollectionGroup collectionGroup) {
        LayoutManager layoutManager = collectionGroup.getLayoutManager();

        // if sub collection exists, don't allow the table sortable
        if (!collectionGroup.getSubCollections().isEmpty()) {
            setDisableTableSort(true);
        }

        if (!isDisableTableSort()) {
            // if rendering add line, skip that row from col sorting
            if (collectionGroup.isRenderAddLine()
                    && !collectionGroup.isReadOnly()
                    && !((layoutManager instanceof TableLayoutManager) && ((TableLayoutManager) layoutManager)
                    .isSeparateAddLine())) {
                getTemplateOptions().put(UifConstants.TableToolsKeys.SORT_SKIP_ROWS,
                        "[" + UifConstants.TableToolsValues.ADD_ROW_DEFAULT_INDEX + "]");
            }

            StringBuilder tableToolsColumnOptions = new StringBuilder("[");

            int columnIndex = 0;
            int actionIndex = -1;
            boolean actionFieldVisible = collectionGroup.isRenderLineActions() && !collectionGroup.isReadOnly();

            if (layoutManager instanceof TableLayoutManager) {
                actionIndex = ((TableLayoutManager) layoutManager).getActionColumnIndex();
            }

            if (actionIndex == 1 && actionFieldVisible) {
                String actionColOptions = constructTableColumnOptions(columnIndex, false, null, null);
                tableToolsColumnOptions.append(actionColOptions + " , ");
                columnIndex++;
            }

            if (layoutManager instanceof TableLayoutManager && ((TableLayoutManager) layoutManager)
                    .isRenderSequenceField()) {
                tableToolsColumnOptions.append("{\""
                        + UifConstants.TableToolsKeys.SORT_TYPE
                        + "\" : \""
                        + UifConstants.TableToolsValues.NUMERIC
                        + "\", "
                        + "\""
                        + UifConstants.TableToolsKeys.TARGETS
                        + "\": ["
                        + columnIndex
                        + "]}, ");
                columnIndex++;
                if (actionIndex == 2 && actionFieldVisible) {
                    String actionColOptions = constructTableColumnOptions(columnIndex, false, null, null);
                    tableToolsColumnOptions.append(actionColOptions + " , ");
                    columnIndex++;
                }
            }

            // skip select field if enabled
            if (collectionGroup.isIncludeLineSelectionField()) {
                String colOptions = constructTableColumnOptions(columnIndex, false, null, null);
                tableToolsColumnOptions.append(colOptions + " , ");
                columnIndex++;
            }

            // if data dictionary defines aoColumns, copy here and skip default sorting/visibility behaviour
            if (!StringUtils.isEmpty(getTemplateOptions().get(UifConstants.TableToolsKeys.AO_COLUMNS))) {
                // get the contents of the JS array string
                String jsArray = getTemplateOptions().get(UifConstants.TableToolsKeys.AO_COLUMNS);
                int startBrace = StringUtils.indexOf(jsArray, "[");
                int endBrace = StringUtils.lastIndexOf(jsArray, "]");
                tableToolsColumnOptions.append(StringUtils.substring(jsArray, startBrace + 1, endBrace) + ", ");

                if (actionFieldVisible && (actionIndex == -1 || actionIndex >= columnIndex)) {
                    String actionColOptions = constructTableColumnOptions(actionIndex, false, null, null);
                    tableToolsColumnOptions.append(actionColOptions);
                } else {
                    tableToolsColumnOptions = new StringBuilder(StringUtils.removeEnd(tableToolsColumnOptions.toString(),
                            ", "));
                }

                tableToolsColumnOptions.append("]");
                getTemplateOptions().put(UifConstants.TableToolsKeys.AO_COLUMNS, tableToolsColumnOptions.toString());
            } else if (!StringUtils.isEmpty(getTemplateOptions().get(UifConstants.TableToolsKeys.AO_COLUMN_DEFS))
                    && isForceAoColumnDefsOverride()) {
                String jsArray = getTemplateOptions().get(UifConstants.TableToolsKeys.AO_COLUMN_DEFS);
                int startBrace = StringUtils.indexOf(jsArray, "[");
                int endBrace = StringUtils.lastIndexOf(jsArray, "]");
                tableToolsColumnOptions.append(StringUtils.substring(jsArray, startBrace + 1, endBrace) + ", ");

                if (actionFieldVisible && (actionIndex == -1 || actionIndex >= columnIndex)) {
                    String actionColOptions = constructTableColumnOptions(actionIndex, false, null, null);
                    tableToolsColumnOptions.append(actionColOptions);
                } else {
                    tableToolsColumnOptions = new StringBuilder(StringUtils.removeEnd(tableToolsColumnOptions.toString(),
                            ", "));
                }

                tableToolsColumnOptions.append("]");
                getTemplateOptions().put(UifConstants.TableToolsKeys.AO_COLUMN_DEFS,
                        tableToolsColumnOptions.toString());
            } else {

                for (Component component : collectionGroup.getItems()) {
                    if (actionFieldVisible && columnIndex + 1 == actionIndex) {
                        String actionColOptions = constructTableColumnOptions(columnIndex, false, null, null);
                        tableToolsColumnOptions.append(actionColOptions + " , ");
                        columnIndex++;
                    }
                    // for FieldGroup, get the first field from that group
                    if (component instanceof FieldGroup) {
                        component = ((FieldGroup) component).getItems().get(0);
                    }

                    if (component instanceof DataField) {
                        DataField field = (DataField) component;

                        // if a field is marked as invisible in hiddenColumns, append options and skip sorting
                        if (getHiddenColumns() != null && getHiddenColumns().contains(field.getPropertyName())) {
                            tableToolsColumnOptions.append("{"
                                    + UifConstants.TableToolsKeys.VISIBLE
                                    + ": "
                                    + UifConstants.TableToolsValues.FALSE
                                    + ", \""
                                    + UifConstants.TableToolsKeys.TARGETS
                                    + "\": ["
                                    + columnIndex
                                    + "]"
                                    + "}, ");
                            // if sortableColumns is present and a field is marked as sortable or unspecified
                        } else if (getSortableColumns() != null && !getSortableColumns().isEmpty()) {
                            if (getSortableColumns().contains(field.getPropertyName())) {
                                tableToolsColumnOptions.append(getDataFieldColumnOptions(columnIndex, collectionGroup,
                                        field) + ", ");
                            } else {
                                tableToolsColumnOptions.append("{'"
                                        + UifConstants.TableToolsKeys.SORTABLE
                                        + "': "
                                        + UifConstants.TableToolsValues.FALSE
                                        + ", \""
                                        + UifConstants.TableToolsKeys.TARGETS
                                        + "\": ["
                                        + columnIndex
                                        + "]"
                                        + "}, ");
                            }
                        } else {// sortable columns not defined
                            String colOptions = getDataFieldColumnOptions(columnIndex, collectionGroup, field);
                            tableToolsColumnOptions.append(colOptions + " , ");
                        }
                        columnIndex++;
                    } else if (component instanceof MessageField
                            && component.getDataAttributes().get("role") != null
                            && component.getDataAttributes().get("role").equals("grouping")) {
                        //Grouping column is never shown, so skip
                        tableToolsColumnOptions.append("{"
                                + UifConstants.TableToolsKeys.VISIBLE
                                + ": "
                                + UifConstants.TableToolsValues.FALSE
                                + ", \""
                                + UifConstants.TableToolsKeys.TARGETS
                                + "\": ["
                                + columnIndex
                                + "]"
                                + "}, ");
                        columnIndex++;
                    } else if (component instanceof MessageField) {
                        //For message field, sort as a regular String
                        String sortType = component.getDataAttributes().get("sortType");
                        if (StringUtils.isBlank(sortType)){
                            sortType = UifConstants.TableToolsValues.DOM_TEXT;
                        }
                        String colOptions = constructTableColumnOptions(columnIndex, true, String.class, sortType);
                        tableToolsColumnOptions.append(colOptions + " , ");
                        columnIndex++;
                    } else {
                        String colOptions = constructTableColumnOptions(columnIndex, false, null, null);
                        tableToolsColumnOptions.append(colOptions + " , ");
                        columnIndex++;
                    }

                }

                if (actionFieldVisible && (actionIndex == -1 || actionIndex >= columnIndex)) {
                    String actionColOptions = constructTableColumnOptions(actionIndex, false, null, null);
                    tableToolsColumnOptions.append(actionColOptions);
                } else {
                    tableToolsColumnOptions = new StringBuilder(StringUtils.removeEnd(tableToolsColumnOptions.toString(),
                            ", "));
                }
                //merge the aoColumnDefs passed in
                if (!StringUtils.isEmpty(getTemplateOptions().get(UifConstants.TableToolsKeys.AO_COLUMN_DEFS))) {
                    String origAoOptions = getTemplateOptions().get(UifConstants.TableToolsKeys.AO_COLUMN_DEFS).trim();
                    origAoOptions = StringUtils.removeStart(origAoOptions, "[");
                    origAoOptions = StringUtils.removeEnd(origAoOptions, "]");
                    tableToolsColumnOptions.append("," + origAoOptions);
                }
                tableToolsColumnOptions.append("]");
                getTemplateOptions().put(UifConstants.TableToolsKeys.AO_COLUMN_DEFS,
                        tableToolsColumnOptions.toString());
            }
        }
    }

    // RICE 2.3 FINAL UPGRADE
    // Added this method to consolidate calls to the superclass method of constructTableColumnOptions
    // TODO KSENROLL-8469 Update calls to this method to properly handle new boolean parameter (isUseServerPaging)
    @Deprecated
    protected String constructTableColumnOptions(int target, boolean isSortable, Class dataTypeClass, String sortDataType) {
        return constructTableColumnOptions(target, isSortable, false, dataTypeClass, sortDataType);
    }

}
