
/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 * <p/>
 * http://www.osedu.org/licenses/ECL-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 * <p/>
 *
 */
package org.kuali.student.common.ui.client.configurable.mvc.multiplicity;

import java.util.List;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;

import com.google.gwt.user.client.ui.FlexTable;

/**
 * A MultiplicityTable displays data in a GWT FlexTable, one cell per field defined, one row per iteration in the supplied data.
 * Data is displayed as defined in the MultiplicityConfiguration passed in the ctor
 * If concatenated fields have been defined the values will be concatenated (comma delimited)  and displayed in a single table cell
 *
 * Fields are included in the table based on the FieldDescriptors added to the configuration
 */
public class MultiplicityTable extends FlexTable {

    private MultiplicityConfiguration config;

    private static final String STYLE_TABLE = "KS-MultiplicityTable";
    private static final String STYLE_TABLE_BORDER = "KS-MultiplicityTableBorder";
    private static final String STYLE_CELL = "KS-MultiplicityTableCell";
    private static final String STYLE_CELL_BORDER = "KS-MultiplicityTableCellBorder";
    private static final String STYLE_HEADER_CELL = "KS-MultiplicityTableHeaderCell";
    private static final String BLANK_STRING = " ";
    private int col = 0;
    protected int row = 0;

    private String parentPath;


    /**
     *
     * Creates an instance of a MultiplicityTable based on the options in the MultiplicityConfiguration
     *
     * @param config
     */
    public MultiplicityTable(MultiplicityConfiguration config) {
        this.config = config;
        setStyleName(STYLE_TABLE);
        if (config.getStyleType() == MultiplicityConfiguration.StyleType.BORDERED_TABLE){
             addStyleName(STYLE_TABLE_BORDER);
        }
    }

    public void initTable() {
        clear();
        while (getRowCount() > 0) {
            removeRow(0);
        }
        row = 0;
        col = 0;
    }

    public void buildHeaders() {
        if (config.isShowHeaders()) {
            //TODO should just be 1 row def for a table - throw exception if > 1?
            for (Integer row  : config.getFields().keySet()) {
                List<MultiplicityFieldConfiguration> fieldConfigs = config.getFields().get(row);
                for (MultiplicityFieldConfiguration fieldConfig : fieldConfigs) {
                    MessageKeyInfo info = fieldConfig.getMessageKeyInfo();
                    String title = Application.getApplicationContext().getUILabel(info.getGroup(), info.getType(), info.getState(), info.getId());
                    addHeaderCell(title);
                }
            }
            nextRow();
        }
    }

    public void addHeaderCell(String fieldValue) {
        if (config.isShowHeaders()) {
            setCellText(row, col, fieldValue);
            getCellFormatter().addStyleName(row, col++, STYLE_HEADER_CELL);
        }
    }

    public void addNextCell(String fieldValue) {
        setCellText(row, col++, fieldValue);
    }

    private void setCellText(int row, int cell, String fieldValue) {
        setText(row, cell, fieldValue);
        getCellFormatter().addStyleName(row, cell, STYLE_CELL);
        if (config.getStyleType() == MultiplicityConfiguration.StyleType.BORDERED_TABLE) {
            getCellFormatter().addStyleName(row, cell, STYLE_CELL_BORDER);
        }
    }

    public void addEmptyCell() {
        addNextCell(BLANK_STRING);
    }

    public void nextRow() {
        row++;
        col = 0;
    }

    public MultiplicityConfiguration getConfig() {
        return config;
    }

    public void setConfig(MultiplicityConfiguration config) {
        this.config = config;
    }

    public String getParentPath() {
        return parentPath;
    }

    public void setParentPath(String parentPath) {
        this.parentPath = parentPath;
    }
}
