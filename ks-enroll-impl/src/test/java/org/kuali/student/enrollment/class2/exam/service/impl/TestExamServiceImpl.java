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
package org.kuali.student.enrollment.class2.exam.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:exam-test-with-mocks-context.xml"})
@Transactional
public class TestExamServiceImpl extends  TestExamServiceImplConformanceExtendedCrud {

    /* Method Name: searchForExamIds */
    @Test
    public void test_searchForExamIds() throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        //Exam searches are tested in TestExamServiceImplIntegration.
    }

    /* Method Name: searchForExams */
    @Test
    public void test_searchForExams()
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        //Exam searches are tested in TestExamServiceImplIntegration.

    }
}
