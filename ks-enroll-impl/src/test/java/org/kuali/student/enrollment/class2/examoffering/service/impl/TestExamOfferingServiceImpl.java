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
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.enrollment.class1.lui.service.impl.LuiServiceDataLoader;
import org.kuali.student.enrollment.examoffering.dto.ExamOfferingInfo;
import org.kuali.student.enrollment.examoffering.dto.ExamOfferingRelationInfo;
import org.kuali.student.enrollment.examoffering.service.ExamOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
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
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.atp.service.impl.AtpTestDataLoader;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.TypeServiceConstants;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:examoffering-test-context.xml"})
@Transactional
public class TestExamOfferingServiceImpl {

    @Resource
    private ExamOfferingService examOfferingService;

    @Resource
    private TypeService typeService;

    @Resource(name = "luiServiceDataLoader")
    protected LuiServiceDataLoader dataLoader = new LuiServiceDataLoader();

    @Resource(name = "atpTestDataLoader")
    protected AtpTestDataLoader atpTestDataLoader = new AtpTestDataLoader();

    public ContextInfo callContext = null;
    public static String principalId = "123";

    @Before
    public void setUp() throws Exception {
        callContext = new ContextInfo();
        callContext.setPrincipalId(principalId);
        dataLoader.loadData();
        atpTestDataLoader.loadExamPeriod();
    }

    @After
    public void cleanup() {
    }

    private ExamOfferingRelationInfo createExamOfferingRelationInfo (){
        ExamOfferingRelationInfo examOfferingRelationInfo = new ExamOfferingRelationInfo();
        examOfferingRelationInfo.setFormatOfferingId("Lui-6");
        examOfferingRelationInfo.setExamOfferingId("Lui-9");
        examOfferingRelationInfo.setActivityOfferingIds(Arrays.asList("AO-01","AO-02","AO-03"));
        examOfferingRelationInfo.setPopulationIds(Arrays.asList("PO-01", "PO-02", "PO-03"));
        return examOfferingRelationInfo;
    }

    @Test
    public void testCrudExamOfferingRelation()
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException,
            DependentObjectsExistException
    {
        ExamOfferingRelationInfo eoRelInfo = createExamOfferingRelationInfo();
        //Create
        ExamOfferingRelationInfo created = examOfferingService.createExamOfferingRelation("Lui-6", "Lui-9",
                LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_FO_TO_EO_TYPE_KEY, eoRelInfo, callContext);

        String examOfferingRelationId = created.getId();
        assertNotNull(created);
        assertNotNull(examOfferingRelationId);
        assertEquals(3, created.getActivityOfferingIds().size());

        //Get
        ExamOfferingRelationInfo retrieved = examOfferingService.getExamOfferingRelation(examOfferingRelationId, callContext);
        assertNotNull(retrieved);
        assertEquals(examOfferingRelationId, retrieved.getId());
        assertEquals(3, created.getActivityOfferingIds().size());

        //Update
        retrieved.setActivityOfferingIds(Arrays.asList("AO-01","AO-02","AO-03","AO-04"));
        ExamOfferingRelationInfo retrievedUpdated = examOfferingService.updateExamOfferingRelation(examOfferingRelationId, retrieved, callContext);
        assertNotNull(retrievedUpdated);
        assertEquals(examOfferingRelationId, retrievedUpdated.getId());
        assertEquals(4, retrievedUpdated.getActivityOfferingIds().size());

        //Delete
        StatusInfo ret = examOfferingService.deleteExamOfferingRelation(examOfferingRelationId, callContext);
        assertTrue(ret.getIsSuccess());
    }

    @Test
    public void testGetExamOfferingsByExamPeriod()
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException,
            DependentObjectsExistException
    {
        List<ExamOfferingInfo> examOfferingInfos = examOfferingService.getExamOfferingsByExamPeriod("examPeriod100", callContext);
        assertNotNull(examOfferingInfos);
        assertEquals(1, examOfferingInfos.size());
        assertEquals(examOfferingInfos.get(0).getId(), "Lui-EO-1");

    }



    @Test 
    public void testGetExamOfferingRelationIdsByType ()
            throws Exception {
        
        ExamOfferingRelationInfo eoRelInfo1 = createExamOfferingRelationInfo();
        ExamOfferingRelationInfo eoRelInfo2 = createExamOfferingRelationInfo();
        eoRelInfo2.setExamOfferingId("Lui-10");
        List<String> examOfferingRelationIds;
        //Create
        ExamOfferingRelationInfo created1 = examOfferingService.createExamOfferingRelation("Lui-6", "Lui-9",
                LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_FO_TO_EO_TYPE_KEY, eoRelInfo1, callContext);
        examOfferingService.createExamOfferingRelation("Lui-6", "Lui-10",
                            LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_FO_TO_EO_TYPE_KEY, eoRelInfo2, callContext);

        examOfferingRelationIds = examOfferingService.getExamOfferingRelationIdsByType(LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_FO_TO_EO_TYPE_KEY, callContext);

        assertNotNull(examOfferingRelationIds);
        assertEquals(2, examOfferingRelationIds.size());
        assertTrue(examOfferingRelationIds.contains(created1.getId()));

        //Delete
        for (String examOfferingRelationId :examOfferingRelationIds) {
            StatusInfo ret = examOfferingService.deleteExamOfferingRelation(examOfferingRelationId, callContext);
            assertTrue(ret.getIsSuccess());
        }
    }

    @Test
    public void testGetExamOfferingRelationsByFormatOffering ()
            throws Exception {

        ExamOfferingRelationInfo eoRelInfo1 = createExamOfferingRelationInfo();
        ExamOfferingRelationInfo eoRelInfo2 = createExamOfferingRelationInfo();
        eoRelInfo2.setExamOfferingId("Lui-10");
        //Create
        examOfferingService.createExamOfferingRelation("Lui-6", "Lui-9",
                LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_FO_TO_EO_TYPE_KEY, eoRelInfo1, callContext);
        examOfferingService.createExamOfferingRelation("Lui-6", "Lui-10",
                            LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_FO_TO_EO_TYPE_KEY, eoRelInfo2, callContext);

        List<ExamOfferingRelationInfo> examOfferingRelationInfos = examOfferingService.getExamOfferingRelationsByFormatOffering("Lui-6", callContext);

        assertNotNull(examOfferingRelationInfos);
        assertEquals(2, examOfferingRelationInfos.size());
        assertEquals("Lui-6", examOfferingRelationInfos.get(0).getFormatOfferingId());
        assertEquals("Lui-6", examOfferingRelationInfos.get(1).getFormatOfferingId());

        //Delete
        for (ExamOfferingRelationInfo examOfferingRelationInfo :examOfferingRelationInfos) {
            StatusInfo ret = examOfferingService.deleteExamOfferingRelation(examOfferingRelationInfo.getId(), callContext);
            assertTrue(ret.getIsSuccess());
        }

    }

    @Test
    public void testGetExamOfferingRelationsByIds ()
            throws Exception {

        ExamOfferingRelationInfo eoRelInfo1 = createExamOfferingRelationInfo();
        ExamOfferingRelationInfo eoRelInfo2 = createExamOfferingRelationInfo();
        eoRelInfo2.setExamOfferingId("Lui-10");
        //Create
        ExamOfferingRelationInfo created1 = examOfferingService.createExamOfferingRelation("Lui-6", "Lui-9",
                LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_FO_TO_EO_TYPE_KEY, eoRelInfo1, callContext);
        ExamOfferingRelationInfo created2 = examOfferingService.createExamOfferingRelation("Lui-6", "Lui-10",
                LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_FO_TO_EO_TYPE_KEY, eoRelInfo2, callContext);

        List<String> examOfferingRelationIds = new ArrayList<String>();
        examOfferingRelationIds.add(created1.getId());
        examOfferingRelationIds.add(created2.getId());

        List<ExamOfferingRelationInfo> examOfferingRelationInfos = examOfferingService.getExamOfferingRelationsByIds(examOfferingRelationIds, callContext);

        assertNotNull(examOfferingRelationInfos);
        assertEquals(2, examOfferingRelationInfos.size());
        assertEquals("Lui-6", examOfferingRelationInfos.get(0).getFormatOfferingId());
        assertEquals("Lui-6", examOfferingRelationInfos.get(1).getFormatOfferingId());

        //Delete
        for (ExamOfferingRelationInfo examOfferingRelationInfo :examOfferingRelationInfos) {
            StatusInfo ret = examOfferingService.deleteExamOfferingRelation(examOfferingRelationInfo.getId(), callContext);
            assertTrue(ret.getIsSuccess());
        }

    }


    @Test
    public void testGetExamOfferingRelationsByExamOffering ()
            throws Exception {

        ExamOfferingRelationInfo eoRelInfo1 = createExamOfferingRelationInfo();
        ExamOfferingRelationInfo eoRelInfo2 = createExamOfferingRelationInfo();
        eoRelInfo2.setFormatOfferingId("Lui-7");

        //Create
        examOfferingService.createExamOfferingRelation("Lui-6", "Lui-9",
                LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_FO_TO_EO_TYPE_KEY, eoRelInfo1, callContext);
        examOfferingService.createExamOfferingRelation("Lui-7", "Lui-9",
                            LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_FO_TO_EO_TYPE_KEY, eoRelInfo2, callContext);

        List<ExamOfferingRelationInfo> examOfferingRelationInfos = examOfferingService.getExamOfferingRelationsByExamOffering("Lui-9", callContext);

        for (ExamOfferingRelationInfo examOfferingRelationInfo : examOfferingRelationInfos){
                assertNotNull(examOfferingRelationInfo);
                assertEquals("Lui-9", examOfferingRelationInfo.getExamOfferingId());
            }

        //Delete
        for (ExamOfferingRelationInfo examOfferingRelationInfo :examOfferingRelationInfos) {
            StatusInfo ret = examOfferingService.deleteExamOfferingRelation(examOfferingRelationInfo.getId(), callContext);
            assertTrue(ret.getIsSuccess());
        }

    }

    @Test
    public void testGetExamOfferingIdsByType()
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException,
            DependentObjectsExistException, AlreadyExistsException {
        createTypes();
        List<String> examOfferingIds = examOfferingService.getExamOfferingIdsByType ("kuali.lu.type.exam.final", callContext);
        assertNotNull(examOfferingIds);
        assertEquals(3, examOfferingIds.size());
    }

    private void createTypes () throws InvalidParameterException, DataValidationErrorException, MissingParameterException, AlreadyExistsException, ReadOnlyException, PermissionDeniedException, OperationFailedException, DoesNotExistException {
        TypeInfo type1 = new TypeInfo();
        type1.setKey("kuali.lu.type.exam.final");
        type1.setName("Exam");
        type1.setDescr(new RichTextHelper().fromPlain("A canonical exam that will be used to instantiate final exam offerings."));
        type1.setEffectiveDate(new Date());
        type1.setRefObjectUri("http://student.kuali.org/wsdl/exam/ExamInfo");
        typeService.createType(type1.getKey(), type1, callContext);

        TypeInfo type2 = new TypeInfo();
        type2.setKey("kuali.lui.type.exam.offering.final");
        type2.setName("Final Exam Offering");
        type2.setDescr(new RichTextHelper().fromPlain("Final Exam Offering"));
        type2.setEffectiveDate(new Date());
        type2.setRefObjectUri("http://student.kuali.org/wsdl/lui/LuiInfo");
        typeService.createType(type2.getKey(), type2, callContext);

        TypeTypeRelationInfo origRel = new TypeTypeRelationInfo();
        origRel.setEffectiveDate(new Date());
        origRel.setTypeKey(TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY);
        origRel.setStateKey(TypeServiceConstants.TYPE_TYPE_RELATION_ACTIVE_STATE_KEY);
        origRel.setRank(1);
        origRel.setOwnerTypeKey(type1.getKey());
        origRel.setRelatedTypeKey(type2.getKey());
        /*AttributeInfo attr = new AttributeInfo();
        attr.setKey("attribute.key");
        attr.setValue("attribute value");
        origRel.getAttributes().add(attr);*/
        typeService.createTypeTypeRelation(origRel.getTypeKey(),
                origRel.getOwnerTypeKey(),
                origRel.getRelatedTypeKey(),
                origRel,
                callContext);
    }

    @Test
    public void getExamOfferingRelationIdsByActivityOffering ()
            throws Exception {

        ExamOfferingRelationInfo eoRelInfo = createExamOfferingRelationInfo();
        //Create
        eoRelInfo.setActivityOfferingIds(Arrays.asList("AO-01","AO-02","AO-03","Lui-2"));
        examOfferingService.createExamOfferingRelation("Lui-6", "Lui-9",
                LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_FO_TO_EO_TYPE_KEY, eoRelInfo, callContext);

        //Retrieve IDs
        List<String> examOfferingRelationIds = examOfferingService.getExamOfferingRelationIdsByActivityOffering("Lui-2", callContext);
        assertNotNull(examOfferingRelationIds);
        assertTrue("Relation IDs should be non-empty", !examOfferingRelationIds.isEmpty());
        //test for aoId match
        for (String examOfferingRelationId : examOfferingRelationIds) {
            ExamOfferingRelationInfo retrieved = examOfferingService.getExamOfferingRelation(examOfferingRelationId, callContext);
            assertNotNull(retrieved);
            assertTrue(retrieved.getActivityOfferingIds().contains("Lui-2"));
        }

        //Delete
        for (String examOfferingRelationId : examOfferingRelationIds) {
            StatusInfo ret = examOfferingService.deleteExamOfferingRelation(examOfferingRelationId, callContext);
            assertTrue(ret.getIsSuccess());
        }

    }

    @Test
    public void testSearchForExamOfferingRelationIds() throws Exception {
        ExamOfferingRelationInfo eoRelInfo1 = createExamOfferingRelationInfo();
        ExamOfferingRelationInfo eoRelInfo2 = createExamOfferingRelationInfo();
        eoRelInfo2.setExamOfferingId("Lui-10");

        //Create
        examOfferingService.createExamOfferingRelation("Lui-6", "Lui-9",
                LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_FO_TO_EO_TYPE_KEY, eoRelInfo1, callContext);
        examOfferingService.createExamOfferingRelation("Lui-6", "Lui-10",
                            LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_FO_TO_EO_TYPE_KEY, eoRelInfo2, callContext);

        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.and(PredicateFactory.equal("lui.id", "Lui-6")));
        QueryByCriteria criteria = qbcBuilder.build();

        //Retrieve Ids
        List<String> relIds = examOfferingService.searchForExamOfferingRelationIds(criteria, callContext);

        //Retrieve ExamOfferingRelationInfos
        List<ExamOfferingRelationInfo> examOfferingRelationInfos = examOfferingService.getExamOfferingRelationsByFormatOffering("Lui-6", callContext);

        assertNotNull(relIds);
        for (ExamOfferingRelationInfo examOfferingRelationInfo : examOfferingRelationInfos) {
            assertTrue(relIds.contains(examOfferingRelationInfo.getId()));
        }

        //Delete
        for (ExamOfferingRelationInfo examOfferingRelationInfo : examOfferingRelationInfos) {
            StatusInfo ret = examOfferingService.deleteExamOfferingRelation(examOfferingRelationInfo.getId(), callContext);
            assertTrue(ret.getIsSuccess());
        }
    }

    @Test
    public void testSearchForExamOfferingRelations() throws Exception {
        ExamOfferingRelationInfo eoRelInfo = createExamOfferingRelationInfo();
        examOfferingService.createExamOfferingRelation("Lui-6", "Lui-9",
                LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_FO_TO_EO_TYPE_KEY, eoRelInfo, callContext);

        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.and(
                PredicateFactory.like("lui.id", "Lui-6"),
                PredicateFactory.equalIgnoreCase("relatedLui.id", "Lui-9")));
        QueryByCriteria criteria = qbcBuilder.build();

        List<ExamOfferingRelationInfo> examOfferingRelationInfos = examOfferingService.searchForExamOfferingRelations(criteria, callContext);
        assertNotNull(examOfferingRelationInfos);
        assertEquals("Lui-6", examOfferingRelationInfos.get(0).getFormatOfferingId());
        assertEquals("Lui-9", examOfferingRelationInfos.get(0).getExamOfferingId());

        //Delete
        for (ExamOfferingRelationInfo examOfferingRelationInfo : examOfferingRelationInfos) {
            StatusInfo ret = examOfferingService.deleteExamOfferingRelation(examOfferingRelationInfo.getId(), callContext);
            assertTrue(ret.getIsSuccess());
        }
    }

    public TypeService getTypeService() {
        return typeService;
    }

    public void setTypeService(TypeService typeService) {
        this.typeService = typeService;
    }
}
