/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 * Created by Charles on 11/15/12
 */
package org.kuali.student.enrollment.class2.courseoffering.refdata;

import org.apache.log4j.Logger;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Stores the content of a spreadsheet with fieldNames and values
 * The map is a list of a header to a list of values (basically, column data)
 *
 * @author Kuali Student Team
 */
public class SpreadsheetData implements Iterable<SpreadsheetRowData> {
    private static Logger LOGGER = Logger.getLogger(SpreadsheetData.class);
    List<String> fieldNames;
    Map<String, List<String>> headerToValueList;
    boolean isValid = false;

    public SpreadsheetData() {

    }

    public void addHeaders(List<String> fieldNames) {
        if (!isValid && fieldNames != null) {
            this.fieldNames = new ArrayList<String>();
            this.fieldNames.addAll(fieldNames); // Copy the fieldNames
            headerToValueList = new TreeMap<String, List<String>>();
            // TODO: Make sure fieldNames have unique names
            for (String field: fieldNames) {
                // Stub out the map
                headerToValueList.put(field, new ArrayList<String>());
            }
            isValid = true;
        } else {
            LOGGER.warn("Field names already set");
        }
    }

    /**
     *
     * @param row A list of strings in the order of the fieldNames
     * @throws InvalidParameterException due to a variety of errors that could occur in row
     */
    public void addRow(List<String> row) throws InvalidParameterException {
        if (row == null) {
            throw new InvalidParameterException("Row is null");
        } else if (!isValid) {
            throw new InvalidParameterException("Headers are not set");
        } else if (row.size() != fieldNames.size()) {
            throw new InvalidParameterException();
        } else {
            int index = 0;
            for (String val: row) {
                if (val == null || val.isEmpty()) {
                    // Put null for empty strings
                    val = null;
                }
                // add the values at the end of appropriate list
                headerToValueList.get(fieldNames.get(index)).add(val);
                index++;
            }
        }
    }

    public int numRows() {
        // All columns should have the same length
        return headerToValueList.get(fieldNames.get(0)).size();
    }

    public SpreadsheetRowData getRowAt(int index) {
        if (index >= numRows() || index < 0) {
            return null;
        } else {
            SpreadsheetRowData rowData = new SpreadsheetRowData();
            for (String field: fieldNames) {
                String value = headerToValueList.get(field).get(index);
                // Added in same order as fieldNames
                rowData.addData(field, value);
            }
            return rowData;
        }
    }

    public void showContents() {
        for (String str: fieldNames) {
            System.err.print(str + "\t");
        }
        System.err.println();
        System.err.println("------------------------------------------------------");
        int rows = numRows();
        for (int i = 0; i < rows; i++) {
            for (String data: getRowAt(i)) {
                if (data == null) {
                    data = "null";
                }
                System.err.print(data + "\t");
            }
            System.err.println();
        }
    }


    @Override
    public Iterator<SpreadsheetRowData> iterator() {
        return new Iterator<SpreadsheetRowData>() {
            int index = 0;
            @Override
            public boolean hasNext() {
                return index < numRows();
            }

            @Override
            public SpreadsheetRowData next() {
                if (!hasNext()) {
                    throw new IndexOutOfBoundsException("No more rows left");
                }
                SpreadsheetRowData rowData = getRowAt(index);
                index++;
                return rowData;
            }

            @Override
            public void remove() {
                throw new RuntimeException("Operation not supported");
            }
        };
    }
}
