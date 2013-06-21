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
package org.kuali.student.enrollment.class2.waitlist.service.impl;


import org.kuali.student.common.test.mock.data.AbstractMockServicesAwareDataLoader;
import org.kuali.student.enrollment.waitlist.dto.WaitListEntryInfo;
import org.kuali.student.enrollment.waitlist.dto.WaitListInfo;
import org.kuali.student.enrollment.waitlist.service.WaitListService;import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class WaitListDataLoader extends AbstractMockServicesAwareDataLoader {

    public static final String WAIT_LIST_TYPE_KEY = "kuali.wait.list.type.course.registration";
    public static final String WAIT_LIST_ENTRY_TYPE_KEY = "kuali.wait.list.entry.type";

    public static final String WAIT_LIST_PROCESSING_AUTO_TYPE_KEY = "kuali.wait.list.processing.type.automatic";

    @Resource
    private WaitListService waitListService;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");



    @Override
    protected void initializeData() throws Exception {
        createWaitLists();
    }


    public void createWaitLists() throws ParseException, DoesNotExistException, PermissionDeniedException,
            OperationFailedException, InvalidParameterException, ReadOnlyException,
            MissingParameterException, DataValidationErrorException {
        for (int i = 0; i < 10; i++) {
            WaitListInfo info = new WaitListInfo();
            info.setWaitListProcessingTypeKey(WAIT_LIST_PROCESSING_AUTO_TYPE_KEY);
            info.setMaxSize(10 + i);
            info.setAllowHoldListEntries(true);
            info.setStateKey("active");
            info.setTypeKey(WAIT_LIST_TYPE_KEY + "." + (i + 1));
            info.setCheckInRequired(false);
            info.setOfferingTypeKey(LuiServiceConstants.ACTIVITY_OFFERING_GROUP_TYPE_KEY);
            info.setEffectiveDate(dateFormat.parse("20130611"));
            info.setExpirationDate(dateFormat.parse("20500101"));

            List<String> offeringIds = new ArrayList<String>();
            for (int j = 0; j <= i; j++) {
                offeringIds.add("offeringId" + j);
            }
            info.setAssociatedOfferingIds(offeringIds);

            info = waitListService.createWaitList(info.getTypeKey(), info, context);

            for (int j = 0; j <= i; j++) {
                createWaitListEntry(info, info.getAssociatedOfferingIds().get(j), "studentId" + j, j + 1);
            }
        }
    }

    public void createWaitListEntry(WaitListInfo waitList, String offeringId, String studentId, Integer position) throws ParseException,
            DoesNotExistException, PermissionDeniedException, OperationFailedException, InvalidParameterException,
            ReadOnlyException, MissingParameterException, DataValidationErrorException {
        WaitListEntryInfo info = new WaitListEntryInfo();
        info.setWaitListId(waitList.getId());
        info.setOfferingId(offeringId);
        info.setTypeKey(WAIT_LIST_ENTRY_TYPE_KEY);
        info.setStateKey("active");
        info.setEffectiveDate(dateFormat.parse("20130611"));
        info.setExpirationDate(dateFormat.parse("20500101"));
        info.setStudentId(studentId);
        info.setPosition(position);


        info = waitListService.createWaitListEntry(waitList.getId(), studentId, info.getTypeKey(), info, context);
    }
}
