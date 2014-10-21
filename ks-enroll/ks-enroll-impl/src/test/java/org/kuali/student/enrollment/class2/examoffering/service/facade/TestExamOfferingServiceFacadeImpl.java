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
package org.kuali.student.enrollment.class2.examoffering.service.facade;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.CourseOfferingServiceTestDataLoader;
import org.kuali.student.enrollment.class2.examoffering.service.impl.ExamOfferingServiceTestDataLoader;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.enrollment.examoffering.dto.ExamOfferingRelationInfo;
import org.kuali.student.enrollment.examoffering.infc.ExamOffering;
import org.kuali.student.enrollment.examoffering.service.ExamOfferingService;
import org.kuali.student.lum.lrc.service.util.MockLrcTestDataLoader;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.ExamOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.LuServiceConstants;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:eo-businesslogic-test-with-mocks-context.xml"})
public class TestExamOfferingServiceFacadeImpl {

    @Resource
    private ExamOfferingServiceFacadeImpl examOfferingBusinessLogic; //Use the impl because this is what we want to test.

    @Resource
    private CourseService courseService;

    @Resource
    private CourseOfferingService courseOfferingService;

    @Resource
    private CourseOfferingSetService socService;

    @Resource
    private LRCService lrcService;

    @Resource
    private ExamOfferingService examOfferingService;

    @Resource
    private CourseOfferingServiceTestDataLoader coDataLoader;

    @Resource
    private ExamOfferingServiceTestDataLoader eoDataLoader;

    public ContextInfo contextInfo = null;
    public static String principalId = "123";

    @Before
    public void setUp() throws Exception {
        new MockLrcTestDataLoader(this.lrcService).loadData();
        coDataLoader.createStateTestData();
        coDataLoader.beforeTest(false);

        eoDataLoader.beforeTest();

        principalId = "123";
        contextInfo = new ContextInfo();
        contextInfo.setPrincipalId(principalId);
    }

    @After
    public void afterTest() throws Exception {
        coDataLoader.afterTest();
        eoDataLoader.afterTest();
    }

    @Test
    public void testGenerateFinalExamOffering() throws InvalidParameterException, AlreadyExistsException,
            OperationFailedException, MissingParameterException, PermissionDeniedException, ReadOnlyException,
            DataValidationErrorException, DoesNotExistException, VersionMismatchException {

        String coId = CourseOfferingServiceTestDataLoader.CHEM123_COURSE_OFFERING_ID;
        CourseOfferingInfo co = this.getCourseOfferingService().getCourseOffering(coId, contextInfo);
        co.getAttributes().add(new AttributeInfo(CourseOfferingServiceConstants.FINAL_EXAM_DRIVER_ATTR, LuServiceConstants.LU_EXAM_DRIVER_AO_KEY));
        co = this.getCourseOfferingService().updateCourseOffering(co.getId(), co, contextInfo);

        List<String> optionKeys = new ArrayList<String>();
        ExamOfferingContext context = new ExamOfferingContext(co);
        context.setExamPeriodId(this.getExamOfferingBusinessLogic().getExamPeriodId(co.getTermId(), contextInfo));
        this.getExamOfferingBusinessLogic().setGenerateEODynamically(true);
        this.getExamOfferingBusinessLogic().generateFinalExamOffering(context, optionKeys, contextInfo);

        List<ExamOfferingRelationInfo> eoRelations = this.getExamOfferingService().getExamOfferingRelationsByFormatOffering(
                CourseOfferingServiceTestDataLoader.CHEM123_LEC_AND_LAB_FORMAT_OFFERING_ID, contextInfo);
        assertEquals(2, eoRelations.size());
        for(ExamOfferingRelationInfo eoRelation : eoRelations){
            assertEquals(1, eoRelation.getActivityOfferingIds().size());
        }

        co.getAttributes().get(0).setValue(LuServiceConstants.LU_EXAM_DRIVER_CO_KEY);
        this.getCourseOfferingService().updateCourseOffering(co.getId(), co, contextInfo);

        context = new ExamOfferingContext(co);
        context.setExamPeriodId(this.getExamOfferingBusinessLogic().getExamPeriodId(co.getTermId(), contextInfo));
        this.getExamOfferingBusinessLogic().generateFinalExamOffering(context, optionKeys, contextInfo);

        eoRelations = this.getExamOfferingService().getExamOfferingRelationsByFormatOffering(
                CourseOfferingServiceTestDataLoader.CHEM123_LEC_AND_LAB_FORMAT_OFFERING_ID, contextInfo);
        assertEquals(3, eoRelations.size());
        int counter = 0;
        for(ExamOfferingRelationInfo eoRelation : eoRelations){
            ExamOffering eo = this.getExamOfferingService().getExamOffering(eoRelation.getExamOfferingId(), contextInfo);
            if(!ExamOfferingServiceConstants.EXAM_OFFERING_CANCELED_STATE_KEY.equals(eo.getStateKey())){
                assertEquals(5, eoRelation.getActivityOfferingIds().size());
            } else {
                counter++;
            }
        }
        assertEquals(2, counter);
    }

    @Test
    public void testGenerateFinalExamOfferingsPerCO() throws MissingParameterException, PermissionDeniedException,
            InvalidParameterException, OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException {

        List<String> optionKeys = new ArrayList<String>();
        CourseOfferingInfo co = this.getCourseOfferingService().getCourseOffering(CourseOfferingServiceTestDataLoader.CHEM123_COURSE_OFFERING_ID, contextInfo);
        ExamOfferingContext context = new ExamOfferingContext(co);
        context.setExamPeriodId(ExamOfferingServiceTestDataLoader.PERIOD_ONE_ID);
        this.getExamOfferingBusinessLogic().setGenerateEODynamically(true);
        this.getExamOfferingBusinessLogic().generateFinalExamOfferingsPerFO(context, optionKeys, contextInfo);

        List<ExamOfferingRelationInfo> eoRelations = this.getExamOfferingService().getExamOfferingRelationsByFormatOffering(
                CourseOfferingServiceTestDataLoader.CHEM123_LEC_AND_LAB_FORMAT_OFFERING_ID, contextInfo);
        assertEquals(1, eoRelations.size());
        for(ExamOfferingRelationInfo eoRelation : eoRelations){
            assertEquals(5, eoRelation.getActivityOfferingIds().size());
            assertNotNull(eoRelation.getFormatOfferingId());
            assertNotNull(eoRelation.getExamOfferingId());

            ExamOffering eo = this.getExamOfferingService().getExamOffering(eoRelation.getExamOfferingId(), contextInfo);
            assertNotNull(eo);
        }

        // Test delete of co exam relationships
        this.getExamOfferingBusinessLogic().removeFinalExamOfferingsFromCO(CourseOfferingServiceTestDataLoader.CHEM123_COURSE_OFFERING_ID,
                contextInfo);

        List<ExamOfferingRelationInfo> newRelations = this.getExamOfferingService().getExamOfferingRelationsByFormatOffering(
                CourseOfferingServiceTestDataLoader.CHEM123_LEC_AND_LAB_FORMAT_OFFERING_ID, contextInfo);
        assertTrue(newRelations.isEmpty());

        for(ExamOfferingRelationInfo eoRelation : eoRelations){
            try{
                ExamOffering eo = this.getExamOfferingService().getExamOffering(eoRelation.getExamOfferingId(), contextInfo);
                fail("Service should throw does not exist exception");
            } catch (DoesNotExistException dnee){
                assertNotNull(dnee.getMessage());
                assertEquals(eoRelation.getExamOfferingId(), dnee.getMessage());
            }
        }
    }

    @Test
    public void testGenerateFinalExamOfferingsPerAO() throws MissingParameterException, PermissionDeniedException,
            InvalidParameterException, OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException, VersionMismatchException {

        List<String> optionKeys = new ArrayList<String>();
        CourseOfferingInfo co = this.getCourseOfferingService().getCourseOffering(CourseOfferingServiceTestDataLoader.CHEM123_COURSE_OFFERING_ID, contextInfo);
        ExamOfferingContext context = new ExamOfferingContext(co);
        context.setExamPeriodId(ExamOfferingServiceTestDataLoader.PERIOD_ONE_ID);

        //Test the validation
        this.getExamOfferingBusinessLogic().setGenerateEODynamically(false);
        ExamOfferingResult result = this.getExamOfferingBusinessLogic().generateFinalExamOfferingsPerAO(context, optionKeys, contextInfo);
        assertEquals(result.getKey(), ExamOfferingServiceConstants.EXAM_OFFERING_BULK_PROCESS_NOT_COMPLETED);

        //Update the bulk proces completed time on the soc.
        AttributeInfo attr = new AttributeInfo(CourseOfferingSetServiceConstants.EO_SLOTTING_STATE_COMPLETED, DateFormatters.SERVER_DATE_PARSER_FORMATTER.format(new Date()));
        context.getSoc().getAttributes().add(attr);   // The soc should now be populated on the context.
        this.getSocService().updateSoc("soc1", context.getSoc(), contextInfo);

        this.getExamOfferingBusinessLogic().generateFinalExamOfferingsPerAO(context, optionKeys, contextInfo);

        List<ExamOfferingRelationInfo> eoRelations = this.getExamOfferingService().getExamOfferingRelationsByFormatOffering(
                CourseOfferingServiceTestDataLoader.CHEM123_LEC_AND_LAB_FORMAT_OFFERING_ID, contextInfo);
        assertEquals(2, eoRelations.size());
        for(ExamOfferingRelationInfo eoRelation : eoRelations){
            assertEquals(1, eoRelation.getActivityOfferingIds().size());
            assertNotNull(eoRelation.getFormatOfferingId());
            assertNotNull(eoRelation.getExamOfferingId());

            ExamOffering eo = this.getExamOfferingService().getExamOffering(eoRelation.getExamOfferingId(), contextInfo);
            assertNotNull(eo);
        }

        // Test delete of ao exam relationships.
        this.getExamOfferingBusinessLogic().removeFinalExamOfferingsFromCO(CourseOfferingServiceTestDataLoader.CHEM123_COURSE_OFFERING_ID,
                contextInfo);

        List<ExamOfferingRelationInfo> newRelations = this.getExamOfferingService().getExamOfferingRelationsByFormatOffering(
                CourseOfferingServiceTestDataLoader.CHEM123_LEC_AND_LAB_FORMAT_OFFERING_ID, contextInfo);
        assertTrue(newRelations.isEmpty());

        for(ExamOfferingRelationInfo eoRelation : eoRelations){
            try{
                ExamOffering eo = this.getExamOfferingService().getExamOffering(eoRelation.getExamOfferingId(), contextInfo);
                fail("Service should throw does not exist exception");
            } catch (DoesNotExistException dnee){
                assertNotNull(dnee.getMessage());
                assertEquals(eoRelation.getExamOfferingId(), dnee.getMessage());
            }
        }
    }

    public ExamOfferingServiceFacadeImpl getExamOfferingBusinessLogic() {
        return examOfferingBusinessLogic;
    }

    public void setExamOfferingBusinessLogic(ExamOfferingServiceFacadeImpl examOfferingBusinessLogic) {
        this.examOfferingBusinessLogic = examOfferingBusinessLogic;
    }

    public CourseService getCourseService() {
        return courseService;
    }

    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    public CourseOfferingService getCourseOfferingService() {
        return courseOfferingService;
    }

    public void setCourseOfferingService(CourseOfferingService courseOfferingService) {
        this.courseOfferingService = courseOfferingService;
    }

    public CourseOfferingSetService getSocService() {
        return socService;
    }

    public void setSocService(CourseOfferingSetService socService) {
        this.socService = socService;
    }

    public LRCService getLrcService() {
        return lrcService;
    }

    public void setLrcService(LRCService lrcService) {
        this.lrcService = lrcService;
    }

    public ExamOfferingService getExamOfferingService() {
        return examOfferingService;
    }

    public void setExamOfferingService(ExamOfferingService examOfferingService) {
        this.examOfferingService = examOfferingService;
    }

    public CourseOfferingServiceTestDataLoader getCoDataLoader() {
        return coDataLoader;
    }

    public void setCoDataLoader(CourseOfferingServiceTestDataLoader coDataLoader) {
        this.coDataLoader = coDataLoader;
    }

    public ExamOfferingServiceTestDataLoader getEoDataLoader() {
        return eoDataLoader;
    }

    public void setEoDataLoader(ExamOfferingServiceTestDataLoader eoDataLoader) {
        this.eoDataLoader = eoDataLoader;
    }
}
