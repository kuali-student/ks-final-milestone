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
 * Created by cmuller on 6/17/14
 */
package org.kuali.student.enrollment.registration.client.service.dto;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class holds time conflict validation results
 *
 * @author Kuali Student Team
 */
public class TimeConflictResult {


    // so, this object will say that id conflicts with this map<otherIds, aoIds
    private String id; // id of the item.
    private Map<String, List<String>> conflictingItemMap; // map that contains a list of conflicting items->aoids

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, List<String>> getConflictingItemMap() {
        if(conflictingItemMap == null){
            conflictingItemMap = new HashMap<String, List<String>>();
        }
        return conflictingItemMap;
    }

    public void setConflictingItemMap(Map<String, List<String>> conflictingItemMap) {
        this.conflictingItemMap = conflictingItemMap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimeConflictResult that = (TimeConflictResult) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
