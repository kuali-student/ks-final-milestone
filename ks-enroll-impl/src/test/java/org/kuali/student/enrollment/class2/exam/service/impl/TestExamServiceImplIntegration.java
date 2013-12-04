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
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.test.util.AttributeTester;
import org.kuali.student.enrollment.exam.dto.ExamInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.common.util.constants.ExamServiceConstants;
import org.kuali.student.r2.lum.lu.dao.LuDao;
import org.kuali.student.r2.lum.lu.entity.LuType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:exam-test-context.xml"})
@Transactional
public class TestExamServiceImplIntegration extends TestExamServiceImplConformanceExtendedCrud {

    @Resource
    private LuDao luDao;

    public LuDao getLuDao() {
        return luDao;
    }

    public void setLuDao(LuDao luDao) {
        this.luDao = luDao;
    }

    @Override
    @Test
    public void testCrudExam()
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException,
            DependentObjectsExistException {
        //Remove this override once all the exam service crud methods have been implemented
    }

    /* Method Name: searchForExamIds */
    @Test
    public void test_searchForExamIds() throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        createExamTestData();

        try {
            QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
            QueryByCriteria criteria = qbcBuilder.build();

            List<String> examIds = this.getExamService().searchForExamIds(criteria, contextInfo);
            assertNotNull(examIds);
            assertEquals(1, examIds.size());
            String examId = examIds.get(0);
        } catch (Exception ex) {
            fail("Exception from service call :" + ex.getMessage());
        }
    }

    /* Method Name: searchForExams */
    @Test
    public void test_searchForExams()
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        createExamTestData();

        try {
            QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
            QueryByCriteria criteria = qbcBuilder.build();

            List<ExamInfo> examList = this.getExamService().searchForExams(criteria, contextInfo);
            assertNotNull(examList);
            assertEquals(1, examList.size());
            ExamInfo coInfo = examList.get(0);
        } catch (Exception ex) {
            fail("Exception from service call :" + ex.getMessage());
        }

    }

    private void createExamTestData() {
        createLuTypes("typeKey01");
        createLuTypes(ExamServiceConstants.EXAM_FINAL_TYPE_KEY);

        createExamToSearch("typeKey01", "stateKey01", "name01", "descr01");
        createExamToSearch(ExamServiceConstants.EXAM_FINAL_TYPE_KEY, "stateKey02", "name02", "descr02");
    }

    private void createLuTypes(String typeKey) {
        LuType type = new LuType();
        type.setId(typeKey);
        luDao.create(type);
    }

    private void createExamToSearch(String typeKey, String stateKey, String name, String descr) {
        try {
            ExamInfo toSearch = new ExamInfo ();
            toSearch.setTypeKey(typeKey);
            toSearch.setStateKey(stateKey);
            toSearch.setName(name);
            toSearch.setDescr(RichTextHelper.buildRichTextInfo(descr, descr));
            this.getExamService().createExam(toSearch.getTypeKey(), toSearch, contextInfo);
        } catch (Exception e) {
            fail("Unable to create exam to search for :" + e.getMessage());
        }
    }
}
