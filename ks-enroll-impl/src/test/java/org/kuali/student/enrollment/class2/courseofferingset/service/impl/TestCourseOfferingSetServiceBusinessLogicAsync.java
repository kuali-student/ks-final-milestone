/**
 * Copyright 2011 The Kuali Foundation Licensed under the
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

package org.kuali.student.enrollment.class2.courseofferingset.service.impl;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.core.constants.AcademicCalendarServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Test the CourseOfferingSetService rollover business logic with an asynchronous call
 *
 * @author andrewlubbers
 *
 */
@Ignore   //(fix me: ksenroll-4355)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:soc-businesslogic-with-fewer-mocks-test-context.xml"})
public class TestCourseOfferingSetServiceBusinessLogicAsync extends TestCourseOfferingSetServiceBusinessLogicWithMocks {

    private static final long SCHEDULING_TEST_TIMEOUT = 1000l * 60l * 1l;

    private static final Long FIVE_MINUTES = 1000l * 60l * 5l;

    @Override
    protected SocInfo getTargetSocAfterRollover(String sourceSocId, String targetTermId, List<String> optionKeys, ContextInfo callContext) throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
        SocInfo targetSoc = socService.rolloverSoc(sourceSocId, targetTermId, optionKeys, callContext);

        Long start = System.currentTimeMillis();
        // poll for 5 minutes
        for(;;) {
            Long now = System.currentTimeMillis();
            if(now - FIVE_MINUTES > start) {
                break;
            }

            List<String> resultIds = socService.getSocRolloverResultIdsByTargetSoc(targetSoc.getId(), callContext);
            if(resultIds.isEmpty()) {
                try {
                    Thread.sleep(250l);
                } catch (InterruptedException e) {
                }
            }
            else {
                // expecting only one result
                SocRolloverResultInfo result = socService.getSocRolloverResult(resultIds.get(0), callContext);

                // once the result has a non-running state, return
                if(!result.getStateKey().equals(CourseOfferingSetServiceConstants.RUNNING_RESULT_STATE_KEY) && !result.getStateKey().equals(CourseOfferingSetServiceConstants.SUBMITTED_RESULT_STATE_KEY)) {
                    break;
                }
            }
        }


        return targetSoc;
    }

    @Override
    protected List<String> buildRolloverOptionKeys() {
        List<String> result = new ArrayList<String>();
        result.add(CourseOfferingSetServiceConstants.LOG_SUCCESSES_OPTION_KEY);
        return result;
    }

    @Test
    public void testStartScheduleSoc() throws InvalidParameterException, DataValidationErrorException, MissingParameterException, DoesNotExistException, ReadOnlyException, PermissionDeniedException, OperationFailedException {
        SocInfo orig = new SocInfo();
        orig.setName("SCHEDULING SOC");
        orig.setDescr(new RichTextHelper().toRichTextInfo("description plain 1", "description formatted 1"));
        orig.setTypeKey(CourseOfferingSetServiceConstants.MAIN_SOC_TYPE_KEY);
        orig.setStateKey(CourseOfferingSetServiceConstants.DRAFT_SOC_STATE_KEY);
        orig.setTermId("myTermId");
        orig.setSubjectArea("ENG");
        orig.setUnitsContentOwnerId("myUnitId");

        // also create the matching term so the scheduler calls don't break
        TermInfo term = new TermInfo();
        term.setId("myTermId");
        term.setCode("MYTERM");
        term.setTypeKey(AtpServiceConstants.ATP_FALL_TYPE_KEY);
        term.setStateKey(AcademicCalendarServiceConstants.TERM_DRAFT_STATE_KEY);
        term.setStartDate(new Date());
        term.setEndDate(new Date());

        SocInfo info = socService.createSoc(orig.getTermId(), orig.getTypeKey(), orig, callContext);
        //  Do state changes so that log entries are correct and state is correct for scheduling.
        socService.updateSocState(info.getId(), CourseOfferingSetServiceConstants.LOCKED_SOC_STATE_KEY, callContext);
        socService.updateSocState(info.getId(), CourseOfferingSetServiceConstants.SOC_SCHEDULING_STATE_IN_PROGRESS, callContext);
        info = socService.getSoc(info.getId(), callContext);

        acalService.createTerm(term.getTypeKey(), term, callContext);


        StatusInfo status = socService.startScheduleSoc(info.getId(), new ArrayList<String>(), callContext);
        assertTrue(status.getIsSuccess());

        long startPoll = System.currentTimeMillis();

        // poll for completion
        SocInfo updated = socService.getSoc(info.getId(), callContext);
        while (!updated.getSchedulingStateKey().equals(CourseOfferingSetServiceConstants.SOC_SCHEDULING_STATE_COMPLETED)) {
            long timeNow = System.currentTimeMillis();
            if (timeNow - startPoll > SCHEDULING_TEST_TIMEOUT) {
                fail("Scheduling a blank SOC exeeded timeout");
            }

            try {
                Thread.sleep(2000l);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            callContext.setCurrentDate(new Date());
            updated = socService.getSoc(info.getId(), callContext);
        }

        assertEquals(CourseOfferingSetServiceConstants.SOC_SCHEDULING_STATE_COMPLETED, updated.getSchedulingStateKey());
        assertNotNull(updated.getLastSchedulingRunStarted());
        assertNotNull(updated.getLastSchedulingRunCompleted());
    }
}
