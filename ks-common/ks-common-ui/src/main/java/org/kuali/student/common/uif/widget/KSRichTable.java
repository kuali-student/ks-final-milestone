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
import org.kuali.rice.krad.uif.field.Field;
import org.kuali.rice.krad.uif.field.FieldGroup;
import org.kuali.rice.krad.uif.field.LinkField;
import org.kuali.rice.krad.uif.field.MessageField;
import org.kuali.rice.krad.uif.layout.LayoutManager;
import org.kuali.rice.krad.uif.layout.TableLayoutManager;
import org.kuali.rice.krad.uif.widget.RichTable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        final boolean isUseServerPaging = collectionGroup.isUseServerPaging();

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
                Map<String, String> oTemplateOptions = this.getTemplateOptions();

                if (oTemplateOptions == null) {
                    setTemplateOptions(oTemplateOptions = new HashMap<String, String>());
                }

                oTemplateOptions.put(UifConstants.TableToolsKeys.SORT_SKIP_ROWS,
                        "[" + UifConstants.TableToolsValues.ADD_ROW_DEFAULT_INDEX + "]");
            }

            StringBuilder tableToolsColumnOptions = new StringBuilder("[");

            int columnIndex = 0;
            int actionIndex = UifConstants.TableLayoutValues.ACTIONS_COLUMN_RIGHT_INDEX;
            boolean actionFieldVisible = collectionGroup.isRenderLineActions() && !collectionGroup.isReadOnly();

            if (layoutManager instanceof TableLayoutManager) {
                actionIndex = ((TableLayoutManager) layoutManager).getActionColumnIndex();
            }

            if (actionIndex == UifConstants.TableLayoutValues.ACTIONS_COLUMN_LEFT_INDEX && actionFieldVisible) {
                String actionColOptions = constructTableColumnOptions(columnIndex, false, isUseServerPaging, null,
                        null);
                tableToolsColumnOptions.append(actionColOptions + " , ");
                columnIndex++;
            }

            if (layoutManager instanceof TableLayoutManager && ((TableLayoutManager) layoutManager)
                    .isRenderSequenceField()) {

                //add mData handling if using a json data source
                String mDataOption = "";
                if (collectionGroup.isUseServerPaging() || isForceLocalJsonData()) {
                    mDataOption = "\""
                            + UifConstants.TableToolsKeys.MDATA
                            +
                            "\" : function(row,type,newVal){ return _handleColData(row,type,'c"
                            + columnIndex
                            + "',newVal);}, ";
                }

                tableToolsColumnOptions.append("{\""
                        + UifConstants.TableToolsKeys.SORTABLE
                        + "\" : "
                        + false
                        // auto sequence column is never sortable
                        + ", \""
                        + UifConstants.TableToolsKeys.SORT_TYPE
                        + "\" : \""
                        + UifConstants.TableToolsValues.NUMERIC
                        + "\", "
                        + mDataOption
                        + "\""
                        + UifConstants.TableToolsKeys.TARGETS
                        + "\": ["
                        + columnIndex
                        + "]}, ");
                columnIndex++;
                if (actionIndex == 2 && actionFieldVisible) {
                    String actionColOptions = constructTableColumnOptions(columnIndex, false, isUseServerPaging, null,
                            null);
                    tableToolsColumnOptions.append(actionColOptions + " , ");
                    columnIndex++;
                }
            }

            // skip select field if enabled
            if (collectionGroup.isIncludeLineSelectionField()) {
                String colOptions = constructTableColumnOptions(columnIndex, false, isUseServerPaging, null, null);
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
                    String actionColOptions = constructTableColumnOptions(columnIndex, false, isUseServerPaging, null,
                            null);
                    tableToolsColumnOptions.append(actionColOptions);
                } else {
                    tableToolsColumnOptions = new StringBuilder(StringUtils.removeEnd(
                            tableToolsColumnOptions.toString(),
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
                    String actionColOptions = constructTableColumnOptions(columnIndex, false, isUseServerPaging, null,
                            null);
                    tableToolsColumnOptions.append(actionColOptions);
                } else {
                    tableToolsColumnOptions = new StringBuilder(StringUtils.removeEnd(
                            tableToolsColumnOptions.toString(),
                            ", "));
                }

                tableToolsColumnOptions.append("]");
                getTemplateOptions().put(UifConstants.TableToolsKeys.AO_COLUMN_DEFS,
                        tableToolsColumnOptions.toString());
            } else if (layoutManager instanceof TableLayoutManager) {
                List<Field> rowFields = ((TableLayoutManager) layoutManager).getFirstRowFields();

                // build column defs from the the first row of the table
                for (Component component : rowFields) {
                    if (actionFieldVisible && columnIndex + 1 == actionIndex) {
                        String actionColOptions = constructTableColumnOptions(columnIndex, false, isUseServerPaging,
                                null, null);
                        tableToolsColumnOptions.append(actionColOptions + " , ");
                        columnIndex++;
                    }

                    //add mData handling if using a json data source
                    String mDataOption = "";
                    if (collectionGroup.isUseServerPaging() || isForceLocalJsonData()) {
                        mDataOption = "\""
                                + UifConstants.TableToolsKeys.MDATA
                                +
                                "\" : function(row,type,newVal){ return _handleColData(row,type,'c"
                                + columnIndex
                                + "',newVal);}, ";
                    }

                    // for FieldGroup, get the first field from that group
                    if (component instanceof FieldGroup) {
                        component = ((FieldGroup) component).getItems().get(0);
                    }

                    Map<String, String> componentDataAttributes;
                    if (component instanceof DataField) {
                        DataField field = (DataField) component;

                        // if a field is marked as invisible in hiddenColumns, append options and skip sorting
                        if (getHiddenColumns() != null && getHiddenColumns().contains(field.getPropertyName())) {
                            tableToolsColumnOptions.append("{"
                                    + UifConstants.TableToolsKeys.VISIBLE
                                    + ": "
                                    + UifConstants.TableToolsValues.FALSE
                                    + ", "
                                    + mDataOption
                                    + "\""
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
                                        + ", "
                                        + mDataOption
                                        + "\""
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
                    } else if ((component instanceof MessageField)
                            && (componentDataAttributes = component.getDataAttributes()) != null
                            && UifConstants.RoleTypes.ROW_GROUPING.equals(componentDataAttributes.get(
                                    UifConstants.DataAttributes.ROLE))) {
                        //Grouping column is never shown, so skip
                        tableToolsColumnOptions.append("{"
                                + UifConstants.TableToolsKeys.VISIBLE
                                + ": "
                                + UifConstants.TableToolsValues.FALSE
                                + ", "
                                + mDataOption
                                + "\""
                                + UifConstants.TableToolsKeys.TARGETS
                                + "\": ["
                                + columnIndex
                                + "]"
                                + "}, ");
                        columnIndex++;
                    // start KS customization (to allow message field sorting) - KSENROLL-4999
                    } else if (component instanceof MessageField) {
                        componentDataAttributes = component.getDataAttributes();
                        //For message field, sort as a regular String
                        String sortType = "";
                        if (componentDataAttributes != null) {
                            sortType = componentDataAttributes.get("sortType");
                        }
                        if (StringUtils.isBlank(sortType)){
                            sortType = UifConstants.TableToolsValues.DOM_TEXT;
                        }
                        String colOptions = constructTableColumnOptions(columnIndex, true, isUseServerPaging,
                                String.class, sortType);
                        tableToolsColumnOptions.append(colOptions + " , ");
                        columnIndex++;
                    // end KS customization (to allow message field sorting) - KSENROLL-4999
                    } else if (component instanceof LinkField) {
                        String colOptions = constructTableColumnOptions(columnIndex, true, isUseServerPaging,
                                String.class, UifConstants.TableToolsValues.DOM_TEXT);
                        tableToolsColumnOptions.append(colOptions + " , ");
                        columnIndex++;
                    } else {
                        String colOptions = constructTableColumnOptions(columnIndex, false, isUseServerPaging, null,
                                null);
                        tableToolsColumnOptions.append(colOptions + " , ");
                        columnIndex++;
                    }

                }

                if (actionFieldVisible && (actionIndex == -1 || actionIndex >= columnIndex)) {
                    String actionColOptions = constructTableColumnOptions(columnIndex, false, isUseServerPaging, null,
                            null);
                    tableToolsColumnOptions.append(actionColOptions);
                } else {
                    tableToolsColumnOptions = new StringBuilder(StringUtils.removeEnd(
                            tableToolsColumnOptions.toString(),
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

}
