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
 * Created by Charles on 8/11/2014
 */
package org.kuali.student.poc.jsondataloader;

import org.kuali.student.common.mock.MockService;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.poc.jsonparser.json.BaseJsonObject;
import org.kuali.student.poc.jsonparser.json.SimpleJsonList;
import org.kuali.student.poc.jsonparser.json.SimpleJsonMap;
import org.kuali.student.poc.jsonparser.parser.SimpleJsonParseException;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Loads entities to a mock CourseOfferingService
 *
 * @author Kuali Student Team
 */
public class JsonCourseOfferingDataLoader implements JsonServiceDataLoader {
    CourseOfferingService coService;

    public void setCourseOfferingService(CourseOfferingService coService) {
        this.coService = coService;
    }

    @Override
    public boolean acceptsType(String typeKey) {
        return typeKey.equals(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY) ||
                typeKey.equals(LuiServiceConstants.FORMAT_OFFERING_TYPE_KEY) ||
                typeKey.startsWith(LuiServiceConstants.ACTIVITY_OFFERING_TYPE_KEY_PREFIX) ||
                typeKey.equals(LuiServiceConstants.REGISTRATION_GROUP_TYPE_KEY);
    }

    @Override
    public void clearData() {
        if (coService instanceof MockService) {
            MockService mockService = (MockService) coService;
            mockService.clear();
        }
    }

    public void loadData(SimpleJsonMap jsonObject, ContextInfo contextInfo) throws OperationFailedException {
        String typeKeyStr = jsonObject.getAsString("type");
        if (typeKeyStr.equals(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY)) {
            loadCourseOffering(jsonObject, contextInfo);
        } else if (typeKeyStr.equals(LuiServiceConstants.FORMAT_OFFERING_TYPE_KEY)) {
            loadFormatOffering(jsonObject, contextInfo);
        } else if (typeKeyStr.startsWith(LuiServiceConstants.ACTIVITY_OFFERING_TYPE_KEY_PREFIX)) {
            loadActivityOffering(jsonObject, contextInfo);
        } else if (typeKeyStr.equals(LuiServiceConstants.REGISTRATION_GROUP_TYPE_KEY)) {
            loadRegistrationGroup(jsonObject, contextInfo);
        }
    }

    private void loadCourseOffering(SimpleJsonMap jsonObject, ContextInfo contextInfo) throws OperationFailedException {
        CourseOfferingInfo coInfo = new CourseOfferingInfo();
        String typeKey = jsonObject.getAsString("type");
        String stateKey = jsonObject.getAsString("state");
        String courseId = jsonObject.getAsString("courseId");
        String termId = jsonObject.getAsString("termId");
        String id = jsonObject.getAsString("id");

        coInfo.setId(id);
        coInfo.setTypeKey(typeKey);
        coInfo.setStateKey(stateKey);
        coInfo.setCourseId(courseId);
        coInfo.setTermId(termId);
        try {
            List<String> options = new ArrayList<>();
            coService.createCourseOffering(courseId, termId, typeKey, coInfo, options, contextInfo);
        } catch (Exception e) {
            throw new OperationFailedException(e);
        }
    }

    private void loadFormatOffering(SimpleJsonMap jsonObject, ContextInfo contextInfo) throws OperationFailedException {
        FormatOfferingInfo foInfo = new FormatOfferingInfo();
        String typeKey = jsonObject.getAsString("type");
        String stateKey = jsonObject.getAsString("state");
        String coId = jsonObject.getAsString("coId");
        String formatId = jsonObject.getAsString("formatId");
        String termId = jsonObject.getAsString("termId");
        String id = jsonObject.getAsString("id");
        BaseJsonObject aoListAsJson = jsonObject.get("aoTypes");
        SimpleJsonList jsonList = null;
        List<String> aoTypes = null;
        if (aoListAsJson != null) {
            jsonList = (SimpleJsonList) aoListAsJson;
            try {
                aoTypes = jsonList.toStringList();
            } catch (SimpleJsonParseException e) {
                // skip
            }
        }
        foInfo.setId(id);
        foInfo.setTypeKey(typeKey);
        foInfo.setStateKey(stateKey);
        foInfo.setFormatId(formatId);
        foInfo.setCourseOfferingId(coId);
        foInfo.setTermId(termId);
        foInfo.setActivityOfferingTypeKeys(aoTypes);
        try {
            List<String> options = new ArrayList<>();
            coService.createFormatOffering(coId, formatId, typeKey, foInfo, contextInfo);
        } catch (Exception e) {
            throw new OperationFailedException(e);
        }
    }

    private void loadActivityOffering(SimpleJsonMap jsonObject, ContextInfo contextInfo) throws OperationFailedException {
        ActivityOfferingInfo aoInfo = new ActivityOfferingInfo();
        String typeKey = jsonObject.getAsString("type");
        String stateKey = jsonObject.getAsString("state");
        String coId = jsonObject.getAsString("coId");
        String foId = jsonObject.getAsString("foId");
        String activityId = jsonObject.getAsString("activityId");
        String termId = jsonObject.getAsString("termId");
        String id = jsonObject.getAsString("id");
        Integer maxSeats = jsonObject.getAsInteger("maxSeats");

        aoInfo.setId(id);
        aoInfo.setTypeKey(typeKey);
        aoInfo.setStateKey(stateKey);
        aoInfo.setFormatOfferingId(foId);
        aoInfo.setCourseOfferingId(coId);
        aoInfo.setActivityId(activityId);
        aoInfo.setTermId(termId);
        aoInfo.setMaximumEnrollment(maxSeats);
        try {
            List<String> options = new ArrayList<>();
            coService.createActivityOffering(foId, activityId, typeKey, aoInfo, contextInfo);
        } catch (Exception e) {
            throw new OperationFailedException(e);
        }
    }

    private void loadRegistrationGroup(SimpleJsonMap jsonObject, ContextInfo contextInfo) throws OperationFailedException {
        RegistrationGroupInfo rgInfo = new RegistrationGroupInfo();
        String id = jsonObject.getAsString("id");
        String typeKey = jsonObject.getAsString("type");
        String stateKey = jsonObject.getAsString("state");
        String foId = jsonObject.getAsString("foId");
        String aoClusterId = jsonObject.getAsString("aoClusterId");
        String termId = jsonObject.getAsString("termId");
        BaseJsonObject aoIdsAsJson = jsonObject.get("aoIds");
        SimpleJsonList jsonList = null;
        List<String> aoIds = null;
        if (aoIdsAsJson != null) {
            jsonList = (SimpleJsonList) aoIdsAsJson;
            try {
                aoIds = jsonList.toStringList();
            } catch (SimpleJsonParseException e) {
                // skip
            }
        }
        rgInfo.setId(id);
        rgInfo.setTypeKey(typeKey);
        rgInfo.setStateKey(stateKey);
        rgInfo.setFormatOfferingId(foId);
        rgInfo.setActivityOfferingClusterId(aoClusterId);
        rgInfo.setTermId(termId);
        rgInfo.setActivityOfferingIds(aoIds);
        try {
            List<String> options = new ArrayList<>();
            coService.createRegistrationGroup(foId, aoClusterId, typeKey, rgInfo, contextInfo);
        } catch (Exception e) {
            throw new OperationFailedException(e);
        }
    }
}
