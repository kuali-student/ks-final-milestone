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
 * Created by Charles on 11/16/12
 */
package org.kuali.student.enrollment.class2.courseoffering.refdata;

import org.kuali.student.r2.common.helper.EntityMergeHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * One row of data (using key-value pairs where key is header name)
 *
 * @author Kuali Student Team
 */
public class SpreadsheetRowData implements Iterable<String> {
    Map<String, String> fieldNamesToValue = new HashMap<String, String>();
    List<String> fieldNames = new ArrayList<String>();

    public boolean addData(String field, String data) {
        if (fieldNamesToValue.containsKey(field)) {
            return false;
        }
        fieldNamesToValue.put(field, data);
        fieldNames.add(field);
        return true;
    }

    public String getValue(String fieldName) {
        return fieldNamesToValue.get(fieldName);
    }

    @Override
    public Iterator<String> iterator() {
        return new Iterator<String>() {
            Iterator<String> iter = fieldNames.iterator();
            String fieldName = null;
            @Override
            public boolean hasNext() {
                return iter.hasNext();
            }

            @Override
            public String next() {
                fieldName = iter.next();
                String nextVal = getValue(fieldName);
                return nextVal;
            }

            @Override
            public void remove() {
                fieldNamesToValue.remove(fieldName);
            }
        };
    }
}
