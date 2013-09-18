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
package org.kuali.student.enrollment.class2.examoffering.service.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.CourseOfferingServiceTestDataLoader;
import org.kuali.student.enrollment.class2.examoffering.service.ExamOfferingServiceBusinessLogic;
import org.kuali.student.lum.lrc.service.util.MockLrcTestDataLoader;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:examoffering-test-context.xml"})
@Transactional
public class TestExamOfferingServiceBusinessLogicImpl {

    @Resource
    private ExamOfferingServiceBusinessLogic examOfferingBusinessLogic;

    @Resource
    private CourseService courseService;

    @Resource
    protected LRCService lrcService;

    @Resource
    protected CourseOfferingServiceTestDataLoader dataLoader;

    public ContextInfo contextInfo = null;
    public static String principalId = "123";

    @Before
    public void setUp() throws Exception {
        new MockLrcTestDataLoader(this.lrcService).loadData();
        dataLoader.createStateTestData();
        dataLoader.beforeTest(false);

        principalId = "123";
        contextInfo = new ContextInfo();
        contextInfo.setPrincipalId(principalId);
    }

    @After
    public void afterTest() throws Exception {
        dataLoader.afterTest();
    }

    public ExamOfferingServiceBusinessLogic getExamOfferingBusinessLogic() {
        return examOfferingBusinessLogic;
    }

    public void setExamOfferingBusinessLogic(ExamOfferingServiceBusinessLogic examOfferingBusinessLogic) {
        this.examOfferingBusinessLogic = examOfferingBusinessLogic;
    }

    @Test
    public void testGenerateFinalExamOffering() throws InvalidParameterException, AlreadyExistsException,
            OperationFailedException, MissingParameterException, PermissionDeniedException, ReadOnlyException,
            DataValidationErrorException, DoesNotExistException {
        List<String> optionKeys = new ArrayList<String>();
        this.getExamOfferingBusinessLogic().generateFinalExamOffering("CO-1", "termId", optionKeys, contextInfo);
    }

    @Test
    public void testGenerateFinalExamOfferingsPerCO() throws MissingParameterException, PermissionDeniedException,
            InvalidParameterException, OperationFailedException, DoesNotExistException {
        this.getExamOfferingBusinessLogic().generateFinalExamOfferingsPerCO("CO-1", contextInfo);
    }

    @Test
    public void testRemoveFinalExamOfferingsPerCO() throws MissingParameterException, PermissionDeniedException,
            InvalidParameterException, OperationFailedException, DoesNotExistException {
        this.getExamOfferingBusinessLogic().removeFinalExamOfferingsPerCO("CO-1", contextInfo);
    }

    @Test
    public void testGenerateFinalExamOfferingsPerAO() throws MissingParameterException, PermissionDeniedException,
            InvalidParameterException, OperationFailedException, DoesNotExistException {
        this.getExamOfferingBusinessLogic().generateFinalExamOfferingsPerCO("CO-1", contextInfo);
    }

    @Test
    public void testRemoveFinalExamOfferingsPerAO() throws MissingParameterException, PermissionDeniedException,
            InvalidParameterException, OperationFailedException, DoesNotExistException {
        this.getExamOfferingBusinessLogic().removeFinalExamOfferingsPerAO("CO-1", contextInfo);
    }

}
