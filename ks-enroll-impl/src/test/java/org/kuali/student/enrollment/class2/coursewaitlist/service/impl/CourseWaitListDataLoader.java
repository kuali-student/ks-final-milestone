/*
 * Copyright 2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Lic+ense is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.class2.coursewaitlist.service.impl;


import org.kuali.student.common.test.mock.data.AbstractMockServicesAwareDataLoader;
import org.kuali.student.enrollment.coursewaitlist.dto.CourseWaitListEntryInfo;
import org.kuali.student.enrollment.coursewaitlist.dto.CourseWaitListInfo;
import org.kuali.student.enrollment.coursewaitlist.service.CourseWaitListService;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CourseWaitListDataLoader extends AbstractMockServicesAwareDataLoader {

    public static final String COURSE_WAIT_LIST_TYPE_KEY = "kuali.coursewait.list.type.course.registration";
    public static final String COURSE_WAIT_LIST_ENTRY_TYPE_KEY = "kuali.coursewait.list.entry.type";

    public static final String COURSE_WAIT_LIST_PROCESSING_AUTO_TYPE_KEY = "kuali.coursewait.list.processing.type.automatic";

    @Resource
    private CourseWaitListService courseWaitListService;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");



    @Override
    protected void initializeData() throws Exception {
        createCourseWaitLists();
    }


    public void createCourseWaitLists() throws ParseException, DoesNotExistException, PermissionDeniedException,
            OperationFailedException, InvalidParameterException, ReadOnlyException,
            MissingParameterException, DataValidationErrorException {
        for (int i = 0; i < 10; i++) {
            CourseWaitListInfo info = new CourseWaitListInfo();
            info.setAutomaticallyProcessed(true);
            info.setConfirmationRequired(false);
            info.setMaxSize(10 + i);
            info.setRegisterInFirstAvailableActivityOffering(true);
            info.setAllowHoldUntilEntries(true);
            info.setStateKey("active");
            info.setTypeKey(COURSE_WAIT_LIST_TYPE_KEY + "." + (i + 1));
            info.setCheckInRequired(false);
            info.setEffectiveDate(dateFormat.parse("20130611"));
            info.setExpirationDate(dateFormat.parse("20500101"));

            List<String> activityOfferingIds = new ArrayList<String>();
            for (int j = 0; j <= i; j++) {
                activityOfferingIds.add("activityOfferingId" + j);
            }
            info.setActivityOfferingIds(activityOfferingIds);

            List<String> formatOfferingIds = new ArrayList<String>();
            for (int j = 0; j <= i; j++) {
                formatOfferingIds.add("formatOfferingId" + j);
            }
            info.setFormatOfferingIds(formatOfferingIds);

            info = courseWaitListService.createCourseWaitList(info.getTypeKey(), info, context);

            for (int j = 0; j <= i; j++) {
                createWaitListEntry(info, "registrationGroupId" + j, "studentId" + j, j + 1);
            }
        }
    }

    public void createWaitListEntry(CourseWaitListInfo courseWaitList, String regGroupId, String studentId, Integer position) throws ParseException,
            DoesNotExistException, PermissionDeniedException, OperationFailedException, InvalidParameterException,
            ReadOnlyException, MissingParameterException, DataValidationErrorException {
        CourseWaitListEntryInfo info = new CourseWaitListEntryInfo();
        info.setCourseWaitListId(courseWaitList.getId());
        info.setRegistrationGroupId(regGroupId);
        info.setTypeKey(COURSE_WAIT_LIST_ENTRY_TYPE_KEY);
        info.setStateKey("active");
        info.setEffectiveDate(dateFormat.parse("20130611"));
        info.setExpirationDate(dateFormat.parse("20500101"));
        info.setStudentId(studentId);
        info.setPosition(position);


        info = courseWaitListService.createCourseWaitListEntry(courseWaitList.getId(), studentId, info.getTypeKey(), info, context);
    }
}
