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

package org.kuali.student.ap.planner.service.impl;

import org.kuali.rice.krad.uif.service.impl.ViewHelperServiceImpl;
import org.kuali.student.ap.academicplan.infc.PlanItem;
import org.kuali.student.ap.planner.service.PlanEventViewHelperService;
import org.kuali.student.ap.planner.util.PlanEventUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 *
 */
public class PlanEventViewHelperServiceImpl extends ViewHelperServiceImpl implements PlanEventViewHelperService {

    private static final Logger LOG = LoggerFactory.getLogger(PlanEventViewHelperServiceImpl.class);

    public JsonObjectBuilder makeAddEvent(PlanItem planItem, JsonObjectBuilder eventList) {
        return PlanEventUtils.makeAddEvent(planItem,eventList);
    }

    public JsonObjectBuilder makeRemoveEvent(String uniqueId, PlanItem planItem, JsonObjectBuilder eventList) {
        return PlanEventUtils.makeRemoveEvent(uniqueId, planItem, eventList);
    }

    public JsonObjectBuilder updatePlanItemEvent(String uniqueId, PlanItem planItem, JsonObjectBuilder eventList) {
        return PlanEventUtils.updatePlanItemEvent(uniqueId, planItem, eventList);
    }

    public JsonObjectBuilder updateTotalCreditsEvent(boolean newTerm, String termId, JsonObjectBuilder eventList) {
        return PlanEventUtils.updateTotalCreditsEvent(newTerm,termId,eventList);
    }

    public JsonObjectBuilder updateTermNoteEvent(String uniqueId, String termId, String termNote, JsonObjectBuilder eventList) {
        return PlanEventUtils.updateTermNoteEvent(uniqueId,termId, termNote,eventList);
    }

    public void sendJsonEvents(boolean success, String message, HttpServletResponse response,
                               JsonObjectBuilder eventList) throws IOException, ServletException {
        PlanEventUtils.sendJsonEvents(success,message,response,eventList);
    }

    public JsonObjectBuilder makeAddBookmarkEvent(PlanItem planItem, JsonObjectBuilder eventList) {
        return PlanEventUtils.makeAddBookmarkEvent(planItem, eventList);
    }

    public JsonObjectBuilder makeUpdateBookmarkTotalEvent(PlanItem planItem, JsonObjectBuilder eventList) {
        return PlanEventUtils.makeUpdateBookmarkTotalEvent(planItem, eventList);
    }

    public JsonObjectBuilder makeUpdateBookmarkTotalEvent(String learningPlanId, JsonObjectBuilder eventList) {
        return PlanEventUtils.makeUpdateBookmarkTotalEvent(learningPlanId, eventList);
    }

    public JsonObjectBuilder makeUpdatePlanItemStatusMessage(List<PlanItem> planItems, JsonObjectBuilder eventList) {
        return PlanEventUtils.makeUpdatePlanItemStatusMessage(planItems, eventList);
    }
}
