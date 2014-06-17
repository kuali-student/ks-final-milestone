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
import java.util.ArrayList;
import java.util.List;

/**
 * This class holds time conflict validation results
 *
 * @author Kuali Student Team
 */
public class TimeConflictResult {

    private List<String> ids;
    private List<List<String>> conflicts;

    public TimeConflictResult(){
        ids = new ArrayList<String>();
        conflicts = new ArrayList<List<String>>();
    }

    public TimeConflictResult(List<String> ids, List<List<String>> timeSlots){
        this.ids = ids;
        this.conflicts = timeSlots;
    }

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public List<List<String>> getConflicts() {
        return conflicts;
    }

    public void setConflicts(List<List<String>> conflicts) {
        this.conflicts = conflicts;
    }


}
