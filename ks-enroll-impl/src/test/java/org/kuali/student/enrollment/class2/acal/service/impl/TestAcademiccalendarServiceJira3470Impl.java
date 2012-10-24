/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 */
package org.kuali.student.enrollment.class2.acal.service.impl;

import org.apache.ojb.broker.PBLifeCycleEvent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.enrollment.acal.dto.KeyDateInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:acal-test-context.xml"})
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Transactional
public class TestAcademiccalendarServiceJira3470Impl {
    public static String principalId = "123";
    public ContextInfo callContext = null;

    @Autowired
    @Qualifier("acalServiceAuthDecorator")
    private AcademicCalendarService acalService;

    @Before
    public void setUp() {
           principalId = "123";
           callContext = new ContextInfo();
           callContext.setPrincipalId(principalId);
       }

    @Test
    public void testJira3470CreateAcal() {
     AcademicCalendarInfo acal = new AcademicCalendarInfo();
        acal.setName("testAcal1");
        acal.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        populateRequiredFields(acal);
        try {
            acal.setTypeKey("kuali.atp.type.TEstCalendar");
            acalService.createAcademicCalendar("kuali.atp.type.TEstCalendar", acal, callContext);
        } catch (Exception ex) {
            fail("exception from service call :" + ex.getMessage());
        }

        try {
            acal.setTypeKey(AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY);
            AcademicCalendarInfo created = acalService.createAcademicCalendar(null, acal, callContext);
            assertNotNull(created);
            assertNotNull(created.getId());
        } catch (Exception ex) {
            fail("exception from service call :" + ex.getMessage());
        }
    }

    private void populateRequiredFields(AcademicCalendarInfo acal) {
        acal.setEndDate(new Date());
        acal.setStartDate(new Date());
        RichTextInfo richTextInfo = new RichTextInfo();
        richTextInfo.setPlain("");
        acal.setDescr(richTextInfo);
    }


}
