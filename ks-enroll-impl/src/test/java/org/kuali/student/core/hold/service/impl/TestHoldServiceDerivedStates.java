/*
 * Copyright 2014 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.core.hold.service.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kuali.student.enrollment.class1.hold.service.decorators.HoldServiceDerivedStateDecorator;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.AcademicCalendarServiceMockImpl;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.constants.HoldServiceConstants;
import org.kuali.student.r2.core.hold.dto.AppliedHoldInfo;
import org.kuali.student.r2.core.hold.service.HoldServiceDecorator;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by jonrcook on 9/29/14.
 */
public class TestHoldServiceDerivedStates {
    @Resource
    private HoldServiceDecorator holdService;
    @Resource
    private AcademicCalendarService academicCalendarService;

    public static String principalId = "123";
    public ContextInfo contextInfo;

    @Before
    public void setUp() throws Exception {
        principalId = "123";
        contextInfo = new ContextInfo();
        contextInfo.setPrincipalId(principalId);
        holdService = new HoldServiceDerivedStateDecorator();
        holdService.setNextDecorator(new HoldServiceMapImpl());
        academicCalendarService = new AcademicCalendarServiceMockImpl();
        loadData();
    }

    private void loadTerm(String id,
                         String name,
                         Date startDate,
                         Date endDate,
                         String type,
                         String state,
                         String descrPlain)
            throws Exception {

        TermInfo termInfo = new TermInfo();
        termInfo.setId(id);
        termInfo.setName(name);
        termInfo.setTypeKey(type);
        termInfo.setStateKey(state);
        termInfo.setStartDate(startDate);
        termInfo.setEndDate(endDate);
        termInfo.setDescr(new RichTextHelper().fromPlain(descrPlain));
        academicCalendarService.createTerm(termInfo.getTypeKey(), termInfo, contextInfo);
    }

    public void loadData() throws Exception {
        loadTerm("kuali.atp.Fall3123", "Fall 3123", DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse("08/01/3123"),
                DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse("12/11/3123"), AtpServiceConstants.ATP_FALL_TYPE_KEY,
                AtpServiceConstants.ATP_OFFICIAL_STATE_KEY, "Fall 3123");
    }

    private AppliedHoldInfo setupActiveAppliedHold() {

        AppliedHoldInfo appliedHold = new AppliedHoldInfo();
        appliedHold.setStateKey(HoldServiceConstants.APPLIED_HOLD_ACTIVE_STATE_KEY);
        appliedHold.setTypeKey(HoldServiceConstants.STUDENT_HOLD_TYPE_KEY);

        Calendar shortly = Calendar.getInstance();
        shortly.add(Calendar.MINUTE, -2);
        appliedHold.setEffectiveDate(shortly.getTime());

        Calendar soon = Calendar.getInstance();
        soon.add(Calendar.MINUTE, 2);
        appliedHold.setExpirationDate(soon.getTime());
        return appliedHold;
    }

    private AppliedHoldInfo setupEffectiveAppliedHold() {

        AppliedHoldInfo appliedHold = new AppliedHoldInfo();
        appliedHold.setStateKey(HoldServiceConstants.APPLIED_HOLD_ACTIVE_STATE_KEY);
        appliedHold.setTypeKey(HoldServiceConstants.STUDENT_HOLD_TYPE_KEY);
//        appliedHold.setHoldIssueId("123");

        Calendar aMomentAgo = Calendar.getInstance();
        aMomentAgo.add(Calendar.MINUTE, -2);
        appliedHold.setEffectiveDate(aMomentAgo.getTime());

        Calendar soon = Calendar.getInstance();
        soon.add(Calendar.MINUTE, 2);
        appliedHold.setExpirationDate(soon.getTime());
        return appliedHold;
    }

    @Test
    public void testCanceledState() throws Exception {
        String personId = "123";
        String issueId = "123";

        AppliedHoldInfo appliedHold = setupEffectiveAppliedHold();
        AppliedHoldInfo createdAppliedHold = holdService.createAppliedHold(
                personId, issueId, HoldServiceConstants.STUDENT_HOLD_TYPE_KEY,
                appliedHold, contextInfo);
        createdAppliedHold.setStateKey(HoldServiceConstants.APPLIED_HOLD_CANCELED_STATE_KEY);

        AppliedHoldInfo updatedAppliedHold = holdService.updateAppliedHold(createdAppliedHold.getId(), createdAppliedHold, contextInfo);

        Assert.assertEquals("AppliedHold unexpected state", HoldServiceConstants.APPLIED_HOLD_CANCELED_STATE_KEY,
                updatedAppliedHold.getStateKey());
    }

    @Test
    public void testExpiredState() throws Exception {
        String personId = "123";
        String issueId = "123";

        AppliedHoldInfo appliedHold = setupEffectiveAppliedHold();
        AppliedHoldInfo createdAppliedHold = holdService.createAppliedHold(
                personId, issueId, HoldServiceConstants.STUDENT_HOLD_TYPE_KEY,
                appliedHold, contextInfo);
        Calendar aMomentAgo = Calendar.getInstance();
        aMomentAgo.add(Calendar.MINUTE, -1);
        createdAppliedHold.setExpirationDate(aMomentAgo.getTime());

        AppliedHoldInfo updatedAppliedHold = holdService.updateAppliedHold(createdAppliedHold.getId(), createdAppliedHold, contextInfo);

        Assert.assertEquals("AppliedHold unexpected state", HoldServiceConstants.APPLIED_HOLD_EXPIRED_STATE_KEY,
                updatedAppliedHold.getStateKey());
    }

    @Test
    public void testOpenState() throws Exception {
        String personId = "123";
        String issueId = "123";

        AppliedHoldInfo appliedHold = setupEffectiveAppliedHold();
        AppliedHoldInfo createdAppliedHold = holdService.createAppliedHold(
                personId, issueId, HoldServiceConstants.STUDENT_HOLD_TYPE_KEY,
                appliedHold, contextInfo);

        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DAY_OF_MONTH, -1);
        createdAppliedHold.setEffectiveDate(yesterday.getTime());

        AppliedHoldInfo updatedAppliedHold = holdService.updateAppliedHold(createdAppliedHold.getId(),
                createdAppliedHold, contextInfo);

        Assert.assertEquals("AppliedHold unexpected state", HoldServiceConstants.APPLIED_HOLD_OPEN_STATE_KEY,
                updatedAppliedHold.getStateKey());

    }

    @Test
    public void testActiveState() throws Exception {
        String personId = "123";
        String issueId = "123";

        AppliedHoldInfo appliedHold = setupActiveAppliedHold();
        AppliedHoldInfo createdAppliedHold = holdService.createAppliedHold(
                personId, issueId, HoldServiceConstants.STUDENT_HOLD_TYPE_KEY,
                appliedHold, contextInfo);

        Calendar tomorrow = Calendar.getInstance();
        tomorrow.add(Calendar.DAY_OF_MONTH, 1);
        createdAppliedHold.setEffectiveDate(tomorrow.getTime());

        AppliedHoldInfo updatedAppliedHold = holdService.updateAppliedHold(createdAppliedHold.getId(),
                createdAppliedHold, contextInfo);

        Assert.assertEquals("AppliedHold unexpected state", HoldServiceConstants.APPLIED_HOLD_ACTIVE_STATE_KEY,
                updatedAppliedHold.getStateKey());

    }

    @Test
    public void testDeletedState() throws Exception {
        String personId = "123";
        String issueId = "123";

        AppliedHoldInfo appliedHold = setupEffectiveAppliedHold();
        AppliedHoldInfo createdAppliedHold = holdService.createAppliedHold(
                personId, issueId, HoldServiceConstants.STUDENT_HOLD_TYPE_KEY,
                appliedHold, contextInfo);

        createdAppliedHold.setStateKey(HoldServiceConstants.APPLIED_HOLD_DELETED_STATE_KEY);

        AppliedHoldInfo updatedAppliedHold = holdService.updateAppliedHold(createdAppliedHold.getId(),
                createdAppliedHold, contextInfo);

        Assert.assertEquals("AppliedHold unexpected state", HoldServiceConstants.APPLIED_HOLD_DELETED_STATE_KEY,
                updatedAppliedHold.getStateKey());
    }

    @Test
    public void testGetAppliedHoldsByPerson() throws Exception {
        String personId = "123";
        String issueId = "123";

        AppliedHoldInfo appliedHold = setupEffectiveAppliedHold();
        AppliedHoldInfo createdAppliedHold = holdService.createAppliedHold(
                personId, issueId, HoldServiceConstants.STUDENT_HOLD_TYPE_KEY,
                appliedHold, contextInfo);

        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DAY_OF_MONTH, -1);
        createdAppliedHold.setEffectiveDate(yesterday.getTime());

        holdService.updateAppliedHold(createdAppliedHold.getId(), createdAppliedHold, contextInfo);

        List<AppliedHoldInfo> appliedHoldsByPerson = holdService.getAppliedHoldsByPerson(personId, contextInfo);

        String openAppliedHoldStateKey = null;
        for(AppliedHoldInfo appliedHoldByPerson : appliedHoldsByPerson) {
            if(appliedHoldByPerson.getPersonId().equals(personId)) {
                openAppliedHoldStateKey = appliedHoldByPerson.getStateKey();
            }
        }

        Assert.assertEquals("AppliedHold unexpected state", HoldServiceConstants.APPLIED_HOLD_OPEN_STATE_KEY,
                openAppliedHoldStateKey);
    }

    @Test
    public void testGetAppliedHold() throws Exception {
        String personId = "123";
        String issueId = "123";

        AppliedHoldInfo appliedHold = setupEffectiveAppliedHold();
        AppliedHoldInfo createdAppliedHold = holdService.createAppliedHold(
                personId, issueId, HoldServiceConstants.STUDENT_HOLD_TYPE_KEY,
                appliedHold, contextInfo);

        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DAY_OF_MONTH, -1);
        createdAppliedHold.setEffectiveDate(yesterday.getTime());

        holdService.updateAppliedHold(createdAppliedHold.getId(), createdAppliedHold, contextInfo);

        AppliedHoldInfo appliedHoldByHoldIssue = holdService.getAppliedHold(createdAppliedHold.getId(), contextInfo);

        Assert.assertEquals("AppliedHold unexpected state", HoldServiceConstants.APPLIED_HOLD_OPEN_STATE_KEY,
                appliedHoldByHoldIssue.getStateKey());
    }

}
