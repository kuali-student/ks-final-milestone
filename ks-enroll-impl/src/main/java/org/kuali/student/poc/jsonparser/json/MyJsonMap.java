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

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a JSON map
 *
 * @author Kuali Student Team
 */
public class MyJsonMap extends BaseJsonObject implements Iterable<Pair<String, BaseJsonObject>> {
    List<Pair<String, BaseJsonObject>> keyValues;

    public MyJsonMap() {
        keyValues = new ArrayList<>();
    }

    public void put(String key, BaseJsonObject value) {
        for (int i = 0; i < keyValues.size(); i++) {
            if (keyValues.get(i).getKey().equals(key)) {
                keyValues.add(i, new MutablePair<>(key, value));
                return;
            }
        }
        keyValues.add(new MutablePair<>(key, value));
    }

    public BaseJsonObject get(String key) {
        for (Pair<String, BaseJsonObject> keyValue: keyValues) {
            if (keyValue.getKey().equals(key)) {
                return keyValue.getValue();
            }
        }
        return null;
    }

    @Override
    public String getType() {
        return "JsonMap";
    }

    @Override
    public Iterator<Pair<String, BaseJsonObject>> iterator() {
        return keyValues.iterator();
    }
}
