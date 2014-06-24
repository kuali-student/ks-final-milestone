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
 * Specifically, this lists all the conflicts associated with the id field.
 *
 * The id either refers to a RegistrationRequestInfoItem id (thus, a cart item) or an LPR id (either a
 * registered or waitlisted course, since both use LPRs to represent the relationship).  This id is indirectly
 * related to a reg group ID.  We could use the reg group ID, but due to the cart permitting duplicate reg group
 * IDs (but not duplicate RegistrationRequestItemInfo ids) which create problems, we prefer the cart id or LPR id.
 *
 * This id is associated with conflictingItemMap.  The keys in conflictingItemMap are either RegistrationRequestItemInfo
 * ids (thus, cart items) or Lpr ids (thus, registered or waitlisted courses, both of which are represented using
 * LPR ids).
 *
 * This is done to support wireframes where time slots of AO's are highlighted (rather than the entire reg group).
 * In particular, if the id refers to, say, ENGL101-1001 reg group, and that conflicts with CHEM231-1022 reg group
 * which contains a lecture and a lab, and the conflict is with the lecture, then there would be a map with, say,
 * the Lpr id associated with CHEM231-1022 which is then mapped to a list containing the AO id for the lecture.
 * In most cases, the list is likely to have only one item (as it is difficult to conflict with multiple AOs in
 * a reg group since the reg group is, in theory, conflict free).
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
