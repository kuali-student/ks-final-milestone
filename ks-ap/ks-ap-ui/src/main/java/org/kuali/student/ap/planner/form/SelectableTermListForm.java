/*
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
 */

package org.kuali.student.ap.planner.form;


import java.util.List;

/**
 * Interface for forms that have selectable term lists.
 */
public interface SelectableTermListForm {

    /**
     * Returns a list of term ids in which there exist planned items for a course
     *
     * @return A list of term ids
     */
    public List<String> getPlannedTermIds();

}
