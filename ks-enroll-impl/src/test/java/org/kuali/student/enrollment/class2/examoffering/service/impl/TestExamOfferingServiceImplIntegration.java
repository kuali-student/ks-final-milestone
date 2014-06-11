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

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.enrollment.exam.dto.ExamInfo;
import org.kuali.student.enrollment.examoffering.dto.ExamOfferingInfo;
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
import org.kuali.student.r2.common.util.constants.ExamOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.ExamServiceConstants;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:examoffering-test-context.xml"})
@Transactional
public class TestExamOfferingServiceImplIntegration extends TestExamOfferingServiceImplConformanceExtendedCrud {

    @Resource
    private TypeService typeService;

    public TypeService getTypeService() {
        return typeService;
    }

    public void setTypeService(TypeService typeService) {
        this.typeService = typeService;
    }


    @Test
    public void testCrudExamOfferingRelation() throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException,
            ReadOnlyException, VersionMismatchException, DependentObjectsExistException {
        //Remove this override once all the exam offering relation crud methods have been implemented
    }

    /* Method Name: searchForExamOfferingIds */
    /* temporarily ignore this method because ExamOfferingServiceMapImpl.searchForExamOfferingIds hasn't been implemented yet */
    @Ignore
    public void test_searchForExamOfferingIds_ignore() throws Exception {

        createExamOfferingTestData();

        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        QueryByCriteria criteria = qbcBuilder.build();

        List<String> examOfferingIds = this.getExamOfferingService().searchForExamOfferingIds(criteria, contextInfo);
        assertNotNull(examOfferingIds);
        assertEquals(1, examOfferingIds.size());
    }

    /* Method Name: searchForExamOfferings */
    /* temporarily ignore this method because ExamOfferingServiceMapImpl.searchForExamOfferings hasn't been implemented yet */
    @Ignore
    public void test_searchForExamOfferings_ignore() throws Exception	{
        createExamOfferingTestData();

        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        QueryByCriteria criteria = qbcBuilder.build();

        List<ExamOfferingInfo> examOfferings = this.getExamOfferingService().searchForExamOfferings(criteria, contextInfo);
        assertNotNull(examOfferings);
        assertEquals(1, examOfferings.size());
        ExamOfferingInfo eoInfo = examOfferings.get(0);
    }

    /* Method Name: searchForExamOfferingRelationIds */
    @Test
    public void test_searchForExamOfferingRelationIds() throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
    }

    /* Method Name: searchForExamOfferingRelations */
    @Test
    public void test_searchForExamOfferingRelations() throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
    }

    private void createExamOfferingTestData() throws Exception {
        createExamOfferingToSearch("typeKey01", "stateKey01", "name01", "descr01");
        createExamOfferingToSearch(ExamOfferingServiceConstants.EXAM_OFFERING_FINAL_TYPE_KEY, "stateKey02", "name02", "descr02");
    }

    private void createExamOfferingToSearch(String typeKey, String stateKey, String name, String descr) throws Exception {
        ExamOfferingInfo toSearch = new ExamOfferingInfo();
        toSearch.setExamId("ExamId1");
        toSearch.setExamPeriodId("ExamPeriodId1");
        toSearch.setScheduleId("ScheduleId1");
        toSearch.setTypeKey(typeKey);
        toSearch.setStateKey(stateKey);
        toSearch.setName(name);
        toSearch.setDescr(RichTextHelper.buildRichTextInfo(descr, descr));
        this.getExamOfferingService().createExamOffering(toSearch.getExamPeriodId(), toSearch.getExamId(), toSearch.getTypeKey(), toSearch, contextInfo);
    }
}
