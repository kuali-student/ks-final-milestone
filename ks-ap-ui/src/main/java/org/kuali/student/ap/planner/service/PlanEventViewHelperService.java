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

package org.kuali.student.ap.planner.service;


import org.kuali.rice.krad.uif.service.ViewHelperService;
import org.kuali.student.ap.academicplan.infc.PlanItem;

import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface PlanEventViewHelperService extends ViewHelperService {

    /**
     * Writes the json of a passed in event builder
     * @param success - If actions was successful
     * @param message - Message to display
     * @param response - Pages response object
     * @param eventList - Event builder being used to compile event list
     * @throws IOException - Thrown if write fails
     * @throws ServletException - Thrown if write fails
     */
    public void sendJsonEvents(boolean success, String message, HttpServletResponse response,
                               JsonObjectBuilder eventList) throws IOException, ServletException;

    /**
     * Creates an add plan item event on a passed in event builder
     *
     * @param planItem The plan item to report as added.
     * @param eventList - Event builder being used to compile event list
     * @return The events builder, with the add plan item event added.
     */
    public JsonObjectBuilder makeAddEvent(PlanItem planItem, JsonObjectBuilder eventList);

    /**
     * Creates a remove event on a passed in event builder
     *
     * @param uniqueId - Id of the component being removed
     * @param planItem - PlanItem being removed
     * @param eventList - Event builder being used to compile event list
     * @return The events builder, with the add plan item event added.
     */
    public JsonObjectBuilder makeRemoveEvent(String uniqueId, PlanItem planItem, JsonObjectBuilder eventList);

    /**
     * Creates an update event for a plan item on a passed in event builder
     *
     * @param uniqueId - Id of the component being updated
     * @param planItem - PlanItem being updated
     * @param eventList - Event builder being used to compile event list
     * @return The events builder, with the add plan item event added.
     */
    public JsonObjectBuilder updatePlanItemEvent(String uniqueId, PlanItem planItem, JsonObjectBuilder eventList);

    /**
     * Creates an update event for a term's credits on a passed in event builder
     *
     * @param newTerm - Flag on whether its new or old credits being updated
     * @param termId  - Id of the term being updated
     * @param eventList - Event builder being used to compile event list
     * @return The events builder, with the add plan item event added.
     */
    public JsonObjectBuilder updateTotalCreditsEvent(boolean newTerm, String termId, JsonObjectBuilder eventList);

    /**
     * Creates an update event for a terms note on a passed in event builder
     * @param uniqueId - Id of the component being updated
     * @param termNote - The new term note text
     * @param eventList - Event builder being used to compile event list
     * @return The events builder, with the add plan item event added.
     */
    public JsonObjectBuilder updateTermNoteEvent(String uniqueId, String termNote, JsonObjectBuilder eventList);

    /**
     * Creates an add plan item event on the current transaction.
     *
     * @param planItem
     *            The plan item to report as added.
     * @return The transactional events builder, with the add plan item event
     *         added.
     */
    public JsonObjectBuilder makeAddBookmarkEvent(PlanItem planItem, JsonObjectBuilder eventList);

    /**
     * Creates an update bookmark total count event on the current transaction.
     *
     * @param planItem
     *            The plan item to use to get the plan id, containing the bookmarks
     * @return The transactional events builder, with the add plan item event
     *         added.
     */
    public JsonObjectBuilder makeUpdateBookmarkTotalEvent(PlanItem planItem, JsonObjectBuilder eventList);

    /**
     * Creates an update bookmark total count event on the current transaction.
     *
     * @param learningPlanId
     *            The plan id to use to get the bookmark count from
     * @return The transactional events builder, with the add plan item event
     *         added.
     */
    public JsonObjectBuilder makeUpdateBookmarkTotalEvent(String learningPlanId, JsonObjectBuilder eventList);


    public JsonObjectBuilder makeUpdatePlanItemStatusMessage(List<PlanItem> planItems, JsonObjectBuilder eventList);
}
