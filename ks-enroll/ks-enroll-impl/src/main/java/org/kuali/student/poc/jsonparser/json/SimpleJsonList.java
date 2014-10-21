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
 * Created by Charles on 7/31/2014
 */
package org.kuali.student.poc.jsonparser.json;

import org.kuali.student.poc.jsonparser.parser.SimpleJsonParseException;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a JSON list
 *
 * @author Kuali Student Team
 */
public class SimpleJsonList extends BaseJsonObject {
    List<BaseJsonObject> objects;

    public SimpleJsonList() {
        objects = new ArrayList<>();
    }

    public int size() {
        return objects.size();
    }

    public BaseJsonObject get(int index) {
        return objects.get(index);
    }

    public void add(BaseJsonObject object) {
        objects.add(object);
    }

    @Override
    public String getJsonType() {
        return "JsonList";
    }

    public List<String> toStringList() throws SimpleJsonParseException {
        List<String> stringList = new ArrayList<>();
        for (BaseJsonObject object: objects) {
            if (object instanceof SimpleJsonString) {
                SimpleJsonString jsonString = (SimpleJsonString) object;
                stringList.add(jsonString.getStringValue());
            } else {
                throw new SimpleJsonParseException("not a string");
            }
        }
        return stringList;
    }
}
