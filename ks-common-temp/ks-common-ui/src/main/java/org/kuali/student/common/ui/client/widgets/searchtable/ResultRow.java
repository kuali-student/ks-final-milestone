/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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
 */

package org.kuali.student.common.ui.client.widgets.searchtable;

import java.util.HashMap;
import java.util.Map;

import org.kuali.student.common.dto.Idable;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ResultRow implements IsSerializable, Idable{
    private String id;
    private Map<String, String> columnValues = new HashMap<String, String>();
    
    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id; 
    }

    public String getValue(String columnKey) {
        return columnValues.get(columnKey);
    }

    public void setValue(String columnKey, String value) {
        columnValues.put(columnKey, value);        
    }

}
