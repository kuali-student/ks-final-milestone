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

import org.junit.runner.RunWith;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Test the CourseOfferingSetService rollover business logic with an asynchronous call
 *
 * @autohr andrewlubbers
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:soc-businesslogic-with-fewer-mocks-test-context.xml"})
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Transactional
public class TestCourseOfferingSetServiceBusinessLogicAsync extends TestCourseOfferingSetServiceBusinessLogicWithMocks {

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
}
