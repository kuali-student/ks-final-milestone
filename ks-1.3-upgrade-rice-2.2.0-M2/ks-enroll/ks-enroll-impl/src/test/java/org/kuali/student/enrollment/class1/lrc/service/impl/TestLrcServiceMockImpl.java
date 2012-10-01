/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.enrollment.class1.lrc.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;

import java.util.List;
import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.enrollment.test.util.AttributeTester;
import org.kuali.student.enrollment.test.util.FloatAsStringTester;
import org.kuali.student.enrollment.test.util.KeyEntityTester;
import org.kuali.student.enrollment.test.util.ListOfStringTester;
import org.kuali.student.enrollment.test.util.MetaTester;
import org.kuali.student.enrollment.test.util.TimeTester;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.common.util.constants.LrcServiceConstants;
import org.kuali.student.r2.lum.lrc.dto.ResultScaleInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValueRangeInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.lrc.infc.ResultValueRange;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:lrc-mock-service-test-context.xml"})
public class TestLrcServiceMockImpl {

    public LRCService getLRCService() {
        return lrcService;
    }

    public void setLRCService(LRCService lrcService) {
        this.lrcService = lrcService;
    }
    @Resource
    private LRCService lrcService;
    public static String principalId = "123";
    public ContextInfo callContext = null;

    @Before
    public void setUp() {
        principalId = "123";
        callContext = new ContextInfo();
        callContext.setPrincipalId(principalId);
//        try {
//            new LprTestDataLoader(lprDao).loadData();
//        } catch (Exception ex) {
//            throw new RuntimeException (ex);
//        }
    }

    @Test
    public void testCrud()
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException,
            AlreadyExistsException,
            DependentObjectsExistException {
        // test create
        ResultScaleInfo expected = new ResultScaleInfo();
        expected.setKey(LrcServiceConstants.RESULT_SCALE_KEY_GRADE_LETTER);
        expected.setTypeKey(LrcServiceConstants.RESULT_SCALE_TYPE_KEY_GRADE);
        expected.setStateKey(LrcServiceConstants.RESULT_SCALE_STATE_APPROVED);
        expected.setEffectiveDate(new Date());
        expected.setExpirationDate(new Date(new Date().getTime() + 1000));
        expected.setName("A-F Letter Graded Scale");
        expected.setDescr(new RichTextHelper().fromPlain("Letter Graded Scale description"));
        new AttributeTester().add2ForCreate(expected.getAttributes());
        ResultScaleInfo actual = lrcService.createResultScale(expected.getTypeKey(), expected,
                callContext);
        assertEquals(expected.getKey(), actual.getKey());
        new KeyEntityTester().check(expected, actual);
        checkRange(expected.getResultValueRange(), actual.getResultValueRange());
        new TimeTester().check(expected.getEffectiveDate(), actual.getEffectiveDate());
        new TimeTester().check(expected.getExpirationDate(), actual.getExpirationDate());
        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterCreate(actual.getMeta());

        // try adding again
        try {
            lrcService.createResultScale(expected.getTypeKey(), expected, callContext);
            fail("should have gotten an already exists exception");
        } catch (AlreadyExistsException e) {
            // ok expected
        }

        // test read
        expected = actual;
        actual = lrcService.getResultScale(actual.getKey(), callContext);
        assertEquals(expected.getKey(), actual.getKey());
        new KeyEntityTester().check(expected, actual);
        checkRange(expected.getResultValueRange(), actual.getResultValueRange());
        new TimeTester().check(expected.getEffectiveDate(), actual.getEffectiveDate());
        new TimeTester().check(expected.getExpirationDate(), actual.getExpirationDate());
        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());

        // test update
        expected = actual;
        expected.setName(expected.getName() + " updated");
        expected.setDescr(new RichTextHelper().fromPlain(expected.getDescr().getPlain() + " updated"));
        expected.setEffectiveDate(new Timestamp(expected.getEffectiveDate().getTime() - 2000));
        expected.setExpirationDate(new Timestamp(expected.getExpirationDate().getTime() + 2000));
//        expected.getResultValueRange().setMinValue("2");
//        expected.getResultValueRange().setMaxValue("99");
//        expected.getResultValueRange().setIncrement("2");
        new AttributeTester().delete1Update1Add1ForUpdate(expected.getAttributes());
        actual = lrcService.updateResultScale(expected.getKey(), expected, callContext);
        assertEquals(expected.getKey(), actual.getKey());
        new KeyEntityTester().check(expected, actual);
        checkRange(expected.getResultValueRange(), actual.getResultValueRange());
        new TimeTester().check(expected.getEffectiveDate(), actual.getEffectiveDate());
        new TimeTester().check(expected.getExpirationDate(), actual.getExpirationDate());
        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterUpdate(expected.getMeta(), actual.getMeta());

        // test read after update
        expected = actual;
        actual = lrcService.getResultScale(actual.getKey(), callContext);
        assertEquals(expected.getKey(), actual.getKey());
        new KeyEntityTester().check(expected, actual);
        checkRange(expected.getResultValueRange(), actual.getResultValueRange());
        new TimeTester().check(expected.getEffectiveDate(), actual.getEffectiveDate());
        new TimeTester().check(expected.getExpirationDate(), actual.getExpirationDate());
        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());

        ResultScaleInfo letterGradedScale = actual;

        // Add another scale so we can test searching
        ResultScaleInfo creditScale = new ResultScaleInfo();
        creditScale.setKey(LrcServiceConstants.RESULT_SCALE_KEY_CREDIT_DEGREE);
        creditScale.setTypeKey(LrcServiceConstants.RESULT_SCALE_TYPE_KEY_CREDIT);
        creditScale.setStateKey(LrcServiceConstants.RESULT_SCALE_STATE_APPROVED);
        creditScale.setEffectiveDate(new Date());
        creditScale.setExpirationDate(new Date(new Date().getTime() + 1000));
        creditScale.setName("Degree Credit");
        creditScale.setDescr(new RichTextHelper().fromPlain("Degree Credit"));
        creditScale = lrcService.createResultScale(creditScale.getTypeKey(), creditScale,
                callContext);

        // Add 3rd scale so we can test searching
        expected = new ResultScaleInfo();
        expected.setKey(LrcServiceConstants.RESULT_SCALE_KEY_GRADE_PERCENTAGE);
        expected.setTypeKey(LrcServiceConstants.RESULT_SCALE_TYPE_KEY_GRADE);
        expected.setStateKey(LrcServiceConstants.RESULT_SCALE_STATE_APPROVED);
        expected.setEffectiveDate(new Date());
        expected.setExpirationDate(new Date(new Date().getTime() + 1000));
        expected.setName("Pass/Fail Scale");
        expected.setDescr(new RichTextHelper().fromPlain("pass/fail scale"));
        ResultValueRangeInfo range = new ResultValueRangeInfo();
        range.setMinValue("0");
        range.setMaxValue("99");
        range.setIncrement("1");
        expected.setResultValueRange(range);
        actual = lrcService.createResultScale(expected.getTypeKey(), expected,
                callContext);
        checkRange(expected.getResultValueRange(), actual.getResultValueRange());

        // test update of range
        expected = actual;
        expected.getResultValueRange().setMinValue("1");
        expected.getResultValueRange().setMaxValue("100");
        expected.getResultValueRange().setIncrement(".1");
        actual = lrcService.updateResultScale(expected.getKey(), expected, callContext);
        assertEquals(expected.getKey(), actual.getKey());
        new KeyEntityTester().check(expected, actual);
        checkRange(expected.getResultValueRange(), actual.getResultValueRange());
        new TimeTester().check(expected.getEffectiveDate(), actual.getEffectiveDate());
        new TimeTester().check(expected.getExpirationDate(), actual.getExpirationDate());
        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterUpdate(expected.getMeta(), actual.getMeta());
        ResultScaleInfo percentScale = actual;

        // bulk find
        List<String> keys = new ArrayList<String>();
        keys.add(letterGradedScale.getKey());
        keys.add(percentScale.getKey());
        keys.add(creditScale.getKey());
        List<ResultScaleInfo> both = lrcService.getResultScalesByKeys(keys, callContext);
        assertEquals(keys.size(), both.size());
        for (ResultScaleInfo info : both) {
            if (!keys.remove(info.getKey())) {
                fail(info.getKey() + " was not in the return list");
            }
        }
        assertEquals(0, keys.size());


        // test by type
        keys = lrcService.getResultScaleKeysByType(LrcServiceConstants.RESULT_SCALE_TYPE_KEY_GRADE, callContext);
        assertEquals(2, keys.size());
        if (!keys.remove(letterGradedScale.getKey())) {
            fail("does not contain " + letterGradedScale.getKey());
        }
        if (!keys.remove(percentScale.getKey())) {
            fail("does not contain " + percentScale.getKey());
        }

        // Add another scale so we can test searching
        ResultScaleInfo passFailScale = new ResultScaleInfo();
        passFailScale.setKey(LrcServiceConstants.RESULT_SCALE_KEY_GRADE_PF);
        passFailScale.setTypeKey(LrcServiceConstants.RESULT_SCALE_TYPE_KEY_GRADE);
        passFailScale.setStateKey(LrcServiceConstants.RESULT_SCALE_STATE_APPROVED);
        passFailScale.setEffectiveDate(new Date());
        passFailScale.setExpirationDate(new Date(new Date().getTime() + 1000));
        passFailScale.setName("Pass/Fail");
        passFailScale.setDescr(new RichTextHelper().fromPlain("Pass Fail"));
        passFailScale = lrcService.createResultScale(passFailScale.getTypeKey(), passFailScale,
                callContext);

        this.testCrudResultValue();

        // test delete scale
        StatusInfo status = lrcService.deleteResultScale(letterGradedScale.getKey(), callContext);
        assertNotNull(status);
        assertTrue(status.getIsSuccess());
        try {
            actual = lrcService.getResultScale(letterGradedScale.getKey(), callContext);
            fail("Did not receive DoesNotExistException when attempting to get already-deleted Entity");
        } catch (DoesNotExistException dnee) {
            // expected
        }
        status = lrcService.deleteResultScale(percentScale.getKey(), callContext);
        assertNotNull(status);
        assertTrue(status.getIsSuccess());
        try {
            actual = lrcService.getResultScale(percentScale.getKey(), callContext);
            fail("Did not receive DoesNotExistException when attempting to get already-deleted Entity");
        } catch (DoesNotExistException dnee) {
            // expected
        }
        status = lrcService.deleteResultScale(passFailScale.getKey(), callContext);
        assertNotNull(status);
        assertTrue(status.getIsSuccess());
        try {
            actual = lrcService.getResultScale(passFailScale.getKey(), callContext);
            fail("Did not receive DoesNotExistException when attempting to get already-deleted Entity");
        } catch (DoesNotExistException dnee) {
            // expected
        }

    }

    private void checkRange(ResultValueRangeInfo expected,
            ResultValueRangeInfo actual) {
        if (expected == null && actual == null) {
            return;
        }
        if (expected == null) {
            fail("expected range was null but actual had a structure with fields=" + toString(actual));
        }
        if (actual == null) {
            fail("actual range was null but expected had a structure with fields=" + toString(expected));
        }
        assertEquals(expected.getMinValue(), expected.getMinValue());
        assertEquals(expected.getMaxValue(), expected.getMaxValue());
        assertEquals(expected.getIncrement(), expected.getIncrement());
    }

    private String toString(ResultValueRange range) {
        return range.getMinValue() + " to " + range.getMaxValue() + " + " + range.getIncrement();
    }

    public void testCrudResultValue()
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException,
            AlreadyExistsException,
            DependentObjectsExistException {

        // test create
        ResultValueInfo expected = new ResultValueInfo();
        expected.setKey(LrcServiceConstants.RESULT_VALUE_KEY_GRADE_LETTER_A);
        expected.setTypeKey(LrcServiceConstants.RESULT_VALUE_TYPE_KEY_VALUE);
        expected.setStateKey(LrcServiceConstants.RESULT_VALUE_STATE_APPROVED);
        expected.setEffectiveDate(new Date());
        expected.setExpirationDate(new Date(new Date().getTime() + 1000));
        expected.setName("Grade A+");
        expected.setDescr(new RichTextHelper().fromPlain("value A+ description"));
        expected.setResultScaleKey(LrcServiceConstants.RESULT_SCALE_KEY_GRADE_LETTER);
        expected.setValue("A+");
        expected.setNumericValue("4.5");
        new AttributeTester().add2ForCreate(expected.getAttributes());
        ResultValueInfo actual = lrcService.createResultValue(expected.getResultScaleKey(), expected.getTypeKey(), expected,
                callContext);
        assertEquals(expected.getKey(), actual.getKey());
        new KeyEntityTester().check(expected, actual);
        new TimeTester().check(expected.getEffectiveDate(), actual.getEffectiveDate());
        new TimeTester().check(expected.getExpirationDate(), actual.getExpirationDate());
        assertEquals(expected.getResultScaleKey(), actual.getResultScaleKey());
        assertEquals (expected.getValue(), actual.getValue());
        new FloatAsStringTester ().check(expected.getNumericValue(), actual.getNumericValue());
        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterCreate(actual.getMeta());

        // try adding again
        try {
            lrcService.createResultValue(expected.getResultScaleKey(), expected.getTypeKey(), expected, callContext);
            fail("should have gotten an already exists exception");
        } catch (AlreadyExistsException e) {
            // ok expected
        }
        // test read
        expected = actual;
        actual = lrcService.getResultValue(actual.getKey(), callContext);
        assertEquals(expected.getKey(), actual.getKey());
        new KeyEntityTester().check(expected, actual);
        new TimeTester().check(expected.getEffectiveDate(), actual.getEffectiveDate());
        new TimeTester().check(expected.getExpirationDate(), actual.getExpirationDate());
        assertEquals(expected.getResultScaleKey(), actual.getResultScaleKey());
        assertEquals(expected.getValue(), actual.getValue());
        new FloatAsStringTester ().check(expected.getNumericValue(), actual.getNumericValue());
        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());

        // test update
        expected = actual;
        expected.setName("Grade A");
        expected.setEffectiveDate(new Timestamp(expected.getEffectiveDate().getTime() - 2000));
        expected.setExpirationDate(new Timestamp(expected.getExpirationDate().getTime() + 2000));
        expected.setDescr(new RichTextHelper().fromPlain("Grade A description"));
        expected.setValue("A");
        expected.setValue("4.0");
        new AttributeTester().delete1Update1Add1ForUpdate(expected.getAttributes());
        actual = lrcService.updateResultValue(expected.getKey(), expected, callContext);
        assertEquals(expected.getKey(), actual.getKey());
        new KeyEntityTester().check(expected, actual);
        assertEquals(expected.getResultScaleKey(), actual.getResultScaleKey());
        assertEquals(expected.getValue(), actual.getValue());
        new FloatAsStringTester ().check(expected.getNumericValue(), actual.getNumericValue());
        new TimeTester().check(expected.getEffectiveDate(), actual.getEffectiveDate());
        new TimeTester().check(expected.getExpirationDate(), actual.getExpirationDate());
        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterUpdate(expected.getMeta(), actual.getMeta());

        // test read
        expected = actual;
        actual = lrcService.getResultValue(actual.getKey(), callContext);
        assertEquals(expected.getKey(), actual.getKey());
        new KeyEntityTester().check(expected, actual);
        assertEquals(expected.getResultScaleKey(), actual.getResultScaleKey());
        assertEquals(expected.getValue(), actual.getValue());
        new FloatAsStringTester ().check(expected.getNumericValue(), actual.getNumericValue());
        new TimeTester().check(expected.getEffectiveDate(), actual.getEffectiveDate());
        new TimeTester().check(expected.getExpirationDate(), actual.getExpirationDate());
        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());

        ResultValueInfo gradeA = actual;

        // create a 2nd
        ResultValueInfo gradeB = new ResultValueInfo();
        gradeB.setKey(LrcServiceConstants.RESULT_VALUE_KEY_GRADE_LETTER_B);
        gradeB.setTypeKey(LrcServiceConstants.RESULT_VALUE_TYPE_KEY_VALUE);
        gradeB.setStateKey(LrcServiceConstants.RESULT_VALUE_STATE_APPROVED);
        gradeB.setEffectiveDate(new Date());
        gradeB.setExpirationDate(new Date(new Date().getTime() + 1000));
        gradeB.setName("B grade");
        gradeB.setDescr(new RichTextHelper().fromPlain("value B description"));
        gradeB.setResultScaleKey(LrcServiceConstants.RESULT_SCALE_KEY_GRADE_LETTER);
        gradeB.setValue("B");
        gradeB.setNumericValue("3.0");
        gradeB = lrcService.createResultValue(gradeB.getResultScaleKey(), gradeB.getTypeKey(), gradeB,
                callContext);

        List<String> keys = new ArrayList<String>();
        keys.add(actual.getKey());
        keys.add(gradeB.getKey());
        List<ResultValueInfo> both = lrcService.getResultValuesByKeys(keys, callContext);
        assertEquals(keys.size(), both.size());
        for (ResultValueInfo info : both) {
            if (!keys.remove(info.getKey())) {
                fail(info.getKey() + " was not in the return list");
            }
        }
        assertEquals(0, keys.size());
        // test by type
        keys = lrcService.getResultValueKeysByType(LrcServiceConstants.RESULT_VALUE_TYPE_KEY_VALUE, callContext);
        assertEquals(2, keys.size());
        if (!keys.remove(actual.getKey())) {
            fail("does ot contain " + actual.getKey());
        }
        if (!keys.remove(gradeB.getKey())) {
            fail("does ot contain " + gradeB.getKey());
        }

        // finish the grades for letter grading

        // create a C
        ResultValueInfo gradeC = new ResultValueInfo();
        gradeC.setKey(LrcServiceConstants.RESULT_VALUE_KEY_GRADE_LETTER_C);
        gradeC.setTypeKey(LrcServiceConstants.RESULT_VALUE_TYPE_KEY_VALUE);
        gradeC.setStateKey(LrcServiceConstants.RESULT_VALUE_STATE_APPROVED);
        gradeC.setEffectiveDate(new Date());
        gradeC.setExpirationDate(new Date(new Date().getTime() + 1000));
        gradeC.setName("value C");
        gradeC.setDescr(new RichTextHelper().fromPlain("value C description"));
        gradeC.setResultScaleKey(LrcServiceConstants.RESULT_SCALE_KEY_GRADE_LETTER);
        gradeC.setValue("C");
        gradeC.setNumericValue("2.0");
        gradeC = lrcService.createResultValue(gradeC.getResultScaleKey(), gradeC.getTypeKey(), gradeC, callContext);

        // create a D
        ResultValueInfo gradeD = new ResultValueInfo();
        gradeD.setKey(LrcServiceConstants.RESULT_VALUE_KEY_GRADE_LETTER_D);
        gradeD.setTypeKey(LrcServiceConstants.RESULT_VALUE_TYPE_KEY_VALUE);
        gradeD.setStateKey(LrcServiceConstants.RESULT_VALUE_STATE_APPROVED);
        gradeD.setEffectiveDate(new Date());
        gradeD.setExpirationDate(new Date(new Date().getTime() + 1000));
        gradeD.setName("value D");
        gradeD.setDescr(new RichTextHelper().fromPlain("value D description"));
        gradeD.setResultScaleKey(LrcServiceConstants.RESULT_SCALE_KEY_GRADE_LETTER);
        gradeD.setValue("D");
        gradeD.setNumericValue("1.0");
        gradeD = lrcService.createResultValue(gradeD.getResultScaleKey(), gradeD.getTypeKey(), gradeD, callContext);

        // create a F
        ResultValueInfo gradeF = new ResultValueInfo();
        gradeF.setKey(LrcServiceConstants.RESULT_VALUE_KEY_GRADE_LETTER_F);
        gradeF.setTypeKey(LrcServiceConstants.RESULT_VALUE_TYPE_KEY_VALUE);
        gradeF.setStateKey(LrcServiceConstants.RESULT_VALUE_STATE_APPROVED);
        gradeF.setEffectiveDate(new Date());
        gradeF.setExpirationDate(new Date(new Date().getTime() + 1000));
        gradeF.setName("value F");
        gradeF.setDescr(new RichTextHelper().fromPlain("value F description"));
        gradeF.setResultScaleKey(LrcServiceConstants.RESULT_SCALE_KEY_GRADE_LETTER);
        gradeF.setValue("F");
        gradeF.setNumericValue("0.0");
        gradeF = lrcService.createResultValue(gradeF.getResultScaleKey(), gradeF.getTypeKey(), gradeF, callContext);

        // create a PF P
        ResultValueInfo gradePfP = new ResultValueInfo();
        gradePfP.setKey(LrcServiceConstants.RESULT_VALUE_KEY_GRADE_PF_P);
        gradePfP.setTypeKey(LrcServiceConstants.RESULT_VALUE_TYPE_KEY_VALUE);
        gradePfP.setStateKey(LrcServiceConstants.RESULT_VALUE_STATE_APPROVED);
        gradePfP.setEffectiveDate(new Date());
        gradePfP.setExpirationDate(new Date(new Date().getTime() + 1000));
        gradePfP.setName("value PF P");
        gradePfP.setDescr(new RichTextHelper().fromPlain("value P description"));
        gradePfP.setResultScaleKey(LrcServiceConstants.RESULT_SCALE_KEY_GRADE_PF);
        gradePfP.setValue("P");
        gradePfP.setNumericValue("1.0");
        gradePfP = lrcService.createResultValue(gradePfP.getResultScaleKey(), gradePfP.getTypeKey(), gradePfP, callContext);

        // create a PF P
        ResultValueInfo gradePfF = new ResultValueInfo();
        gradePfF.setKey(LrcServiceConstants.RESULT_VALUE_KEY_GRADE_PF_F);
        gradePfF.setTypeKey(LrcServiceConstants.RESULT_VALUE_TYPE_KEY_VALUE);
        gradePfF.setStateKey(LrcServiceConstants.RESULT_VALUE_STATE_APPROVED);
        gradePfF.setEffectiveDate(new Date());
        gradePfF.setExpirationDate(new Date(new Date().getTime() + 1000));
        gradePfF.setName("value F");
        gradePfF.setDescr(new RichTextHelper().fromPlain("value F description"));
        gradePfF.setResultScaleKey(LrcServiceConstants.RESULT_SCALE_KEY_GRADE_PF);
        gradePfF.setValue("F");
        gradePfF.setNumericValue("0.0");
        gradePfF = lrcService.createResultValue(gradePfF.getResultScaleKey(), gradePfF.getTypeKey(), gradePfF, callContext);

        // this should get the F associated with the PF not the F associated with the Letter Graded scale
        actual = lrcService.getResultValueForScaleAndValue(LrcServiceConstants.RESULT_SCALE_KEY_GRADE_PF, "F", callContext);
        assertEquals(LrcServiceConstants.RESULT_VALUE_KEY_GRADE_PF_F, actual.getKey());
        try {
            lrcService.getResultValueForScaleAndValue(LrcServiceConstants.RESULT_SCALE_KEY_GRADE_LETTER, "P", callContext);
            fail("should not have found a P value in a letter grading scale");
        } catch (DoesNotExistException ex) {
            // ok expected
        }
        assertEquals(LrcServiceConstants.RESULT_VALUE_KEY_GRADE_PF_F, actual.getKey());

        this.testCrudResultValuesGroup();

        // test delete
        StatusInfo status = lrcService.deleteResultValue(LrcServiceConstants.RESULT_VALUE_KEY_GRADE_LETTER_A, callContext);
        assertNotNull(status);
        assertTrue(status.getIsSuccess());
        try {
            actual = lrcService.getResultValue(LrcServiceConstants.RESULT_VALUE_KEY_GRADE_LETTER_A, callContext);
            fail("Did not receive DoesNotExistException when attempting to get already-deleted Entity");
        } catch (DoesNotExistException dnee) {
            // expected
        }
        status = lrcService.deleteResultValue(LrcServiceConstants.RESULT_VALUE_KEY_GRADE_LETTER_B, callContext);
        assertNotNull(status);
        assertTrue(status.getIsSuccess());
        try {
            actual = lrcService.getResultValue(LrcServiceConstants.RESULT_VALUE_KEY_GRADE_LETTER_B, callContext);
            fail("Did not receive DoesNotExistException when attempting to get already-deleted Entity");
        } catch (DoesNotExistException dnee) {
            // expected
        }
        status = lrcService.deleteResultValue(LrcServiceConstants.RESULT_VALUE_KEY_GRADE_LETTER_C, callContext);
        status = lrcService.deleteResultValue(LrcServiceConstants.RESULT_VALUE_KEY_GRADE_LETTER_D, callContext);
        status = lrcService.deleteResultValue(LrcServiceConstants.RESULT_VALUE_KEY_GRADE_LETTER_F, callContext);
        status = lrcService.deleteResultValue(LrcServiceConstants.RESULT_VALUE_KEY_GRADE_PF_P, callContext);
        status = lrcService.deleteResultValue(LrcServiceConstants.RESULT_VALUE_KEY_GRADE_PF_F, callContext);
    }

    public void testCrudResultValuesGroup()
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException,
            AlreadyExistsException {

        // test create
        ResultValuesGroupInfo expected = new ResultValuesGroupInfo();

        expected.setKey(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_LETTER_PASSING_TRANSLATION);
        expected.setTypeKey(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_MULTIPLE);
        expected.setStateKey(LrcServiceConstants.RESULT_VALUE_STATE_APPROVED);
        expected.setEffectiveDate(new Date());
        expected.setExpirationDate(new Date(new Date().getTime() + 1000));
        expected.setName("Passing Letter Grades");
        expected.setDescr(new RichTextHelper().fromPlain("Grades that are considered passing on an A-F letter scale"));
        expected.setResultScaleKey(LrcServiceConstants.RESULT_SCALE_KEY_GRADE_LETTER);
        expected.getResultValueKeys().add(LrcServiceConstants.RESULT_VALUE_KEY_GRADE_LETTER_A);
        expected.getResultValueKeys().add(LrcServiceConstants.RESULT_VALUE_KEY_GRADE_LETTER_B);
        expected.getResultValueKeys().add(LrcServiceConstants.RESULT_VALUE_KEY_GRADE_LETTER_C);
        expected.getResultValueKeys().add(LrcServiceConstants.RESULT_VALUE_KEY_GRADE_LETTER_D);
        expected.getResultValueKeys().add(LrcServiceConstants.RESULT_VALUE_KEY_GRADE_LETTER_F);
        new AttributeTester().add2ForCreate(expected.getAttributes());
        ResultValuesGroupInfo actual = lrcService.createResultValuesGroup(expected.getResultScaleKey(), expected.getTypeKey(),
                expected,
                callContext);
        assertEquals(expected.getKey(), actual.getKey());
        new KeyEntityTester().check(expected, actual);
        new TimeTester().check(expected.getEffectiveDate(), actual.getEffectiveDate());
        new TimeTester().check(expected.getExpirationDate(), actual.getExpirationDate());
        assertEquals(expected.getResultScaleKey(), actual.getResultScaleKey());
        new ListOfStringTester().check(expected.getResultValueKeys(), actual.getResultValueKeys());
        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterCreate(actual.getMeta());

        // try adding again
        try {
            lrcService.createResultValuesGroup(expected.getResultScaleKey(), expected.getTypeKey(), expected, callContext);
            fail("should have gotten an already exists exception");
        } catch (AlreadyExistsException e) {
            // ok expected
        }

        // test read
        expected = actual;
        actual = lrcService.getResultValuesGroup(actual.getKey(), callContext);
        assertEquals(expected.getKey(), actual.getKey());
        new KeyEntityTester().check(expected, actual);
        new TimeTester().check(expected.getEffectiveDate(), actual.getEffectiveDate());
        new TimeTester().check(expected.getExpirationDate(), actual.getExpirationDate());
        assertEquals(expected.getResultScaleKey(), actual.getResultScaleKey());
        new ListOfStringTester().check(expected.getResultValueKeys(), actual.getResultValueKeys());
        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());
        ResultValuesGroupInfo passingGradeTranslation = actual;

        // test update
        expected = actual;
        expected.setName("name 1 updated");
        expected.setEffectiveDate(new Timestamp(expected.getEffectiveDate().getTime() - 2000));
        expected.setExpirationDate(new Timestamp(expected.getExpirationDate().getTime() + 2000));
        expected.setDescr(new RichTextHelper().fromPlain("values group 1 description updated"));
        expected.getResultValueKeys().remove(0);
        expected.getResultValueKeys().add(LrcServiceConstants.RESULT_VALUE_KEY_GRADE_LETTER_F);
        new AttributeTester().delete1Update1Add1ForUpdate(expected.getAttributes());
        actual = lrcService.updateResultValuesGroup(expected.getKey(), expected, callContext);
        assertEquals(expected.getKey(), actual.getKey());
        new KeyEntityTester().check(expected, actual);
        assertEquals(expected.getResultScaleKey(), actual.getResultScaleKey());
        new ListOfStringTester().check(expected.getResultValueKeys(), actual.getResultValueKeys());
        new TimeTester().check(expected.getEffectiveDate(), actual.getEffectiveDate());
        new TimeTester().check(expected.getExpirationDate(), actual.getExpirationDate());
        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterUpdate(expected.getMeta(), actual.getMeta());

        // test read
        expected = actual;
        actual = lrcService.getResultValuesGroup(actual.getKey(), callContext);
        assertEquals(expected.getKey(), actual.getKey());
        new KeyEntityTester().check(expected, actual);
        assertEquals(expected.getResultScaleKey(), actual.getResultScaleKey());
        new ListOfStringTester().check(expected.getResultValueKeys(), actual.getResultValueKeys());
        new TimeTester().check(expected.getEffectiveDate(), actual.getEffectiveDate());
        new TimeTester().check(expected.getExpirationDate(), actual.getExpirationDate());
        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());

        // test create a 2nd one
        ResultValuesGroupInfo gradeLetter = new ResultValuesGroupInfo();
        gradeLetter.setKey(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_LETTER);
        gradeLetter.setTypeKey(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_MULTIPLE);
        gradeLetter.setStateKey(LrcServiceConstants.RESULT_VALUE_STATE_APPROVED);
        gradeLetter.setEffectiveDate(new Date());
        gradeLetter.setExpirationDate(new Date(new Date().getTime() + 1000));
        gradeLetter.setName("Letter Grades");
        gradeLetter.setDescr(new RichTextHelper().fromPlain("Grades on an A-F letter scale"));
        gradeLetter.setResultScaleKey(LrcServiceConstants.RESULT_SCALE_KEY_GRADE_LETTER);
        gradeLetter.getResultValueKeys().add(LrcServiceConstants.RESULT_VALUE_KEY_GRADE_LETTER_A);
        gradeLetter.getResultValueKeys().add(LrcServiceConstants.RESULT_VALUE_KEY_GRADE_LETTER_B);
        gradeLetter.getResultValueKeys().add(LrcServiceConstants.RESULT_VALUE_KEY_GRADE_LETTER_C);
        gradeLetter.getResultValueKeys().add(LrcServiceConstants.RESULT_VALUE_KEY_GRADE_LETTER_D);
        gradeLetter.getResultValueKeys().add(LrcServiceConstants.RESULT_VALUE_KEY_GRADE_LETTER_F);
        gradeLetter = lrcService.createResultValuesGroup(gradeLetter.getResultScaleKey(), gradeLetter.getTypeKey(),
                gradeLetter,
                callContext);

        // test create a 3rd one
        ResultValuesGroupInfo gradePF = new ResultValuesGroupInfo();
        gradePF.setKey(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PASSFAIL);
        gradePF.setTypeKey(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_MULTIPLE);
        gradePF.setStateKey(LrcServiceConstants.RESULT_VALUE_STATE_APPROVED);
        gradePF.setEffectiveDate(new Date());
        gradePF.setExpirationDate(new Date(new Date().getTime() + 1000));
        gradePF.setName("Passing Letter Grades");
        gradePF.setDescr(new RichTextHelper().fromPlain("Grades on the PF scale"));
        gradePF.setResultScaleKey(LrcServiceConstants.RESULT_SCALE_KEY_GRADE_LETTER);
        gradePF.getResultValueKeys().add(LrcServiceConstants.RESULT_VALUE_KEY_GRADE_PF_P);
        gradePF.getResultValueKeys().add(LrcServiceConstants.RESULT_VALUE_KEY_GRADE_PF_F);
        gradePF = lrcService.createResultValuesGroup(gradePF.getResultScaleKey(), gradePF.getTypeKey(),
                gradePF,
                callContext);

           // test create a 4th one
        ResultValuesGroupInfo creditRange1To4 = new ResultValuesGroupInfo();
        creditRange1To4.setKey(LrcServiceConstants.RESULT_GROUP_KEY_KUALI_CREDITTYPE_CREDIT_1_MINUS4);
        creditRange1To4.setTypeKey(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_RANGE);
        creditRange1To4.setStateKey(LrcServiceConstants.RESULT_VALUE_STATE_APPROVED);
        creditRange1To4.setEffectiveDate(new Date());
        creditRange1To4.setExpirationDate(new Date(new Date().getTime() + 1000));
        creditRange1To4.setName("1-4 credits");
        creditRange1To4.setDescr(new RichTextHelper().fromPlain("1-4 credits"));
        creditRange1To4.setResultScaleKey(LrcServiceConstants.RESULT_SCALE_KEY_CREDIT_DEGREE);
        creditRange1To4.setResultValueRange(new ResultValueRangeInfo ());
        creditRange1To4.getResultValueRange().setMinValue("1");
        creditRange1To4.getResultValueRange().setMaxValue("4");
        creditRange1To4.getResultValueRange().setIncrement("1");
        creditRange1To4 = lrcService.createResultValuesGroup(creditRange1To4.getResultScaleKey(), creditRange1To4.getTypeKey(),
                creditRange1To4,
                callContext);
        // test create a 4th one
        ResultValuesGroupInfo creditFixed2 = new ResultValuesGroupInfo();
        creditFixed2.setKey(LrcServiceConstants.RESULT_GROUP_KEY_KUALI_CREDITTYPE_CREDIT_2_0);
        creditFixed2.setTypeKey(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_FIXED);
        creditFixed2.setStateKey(LrcServiceConstants.RESULT_VALUE_STATE_APPROVED);
        creditFixed2.setEffectiveDate(new Date());
        creditFixed2.setExpirationDate(new Date(new Date().getTime() + 1000));
        creditFixed2.setName("2 credits");
        creditFixed2.setDescr(new RichTextHelper().fromPlain("2 credits"));
        creditFixed2.setResultScaleKey(LrcServiceConstants.RESULT_SCALE_KEY_CREDIT_DEGREE);
        creditFixed2.getResultValueKeys().add(LrcServiceConstants.RESULT_VALUE_KEY_GRADE_PF_P);
        creditFixed2 = lrcService.createResultValuesGroup(creditFixed2.getResultScaleKey(), creditFixed2.getTypeKey(),
                creditFixed2,
                callContext);
        List<String> keys = new ArrayList<String>();
        keys.add(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_LETTER);
        keys.add(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_LETTER_PASSING_TRANSLATION);
        List<ResultValuesGroupInfo> both = lrcService.getResultValuesGroupsByKeys(keys, callContext);
        assertEquals(keys.size(), both.size());
        for (ResultValuesGroupInfo info : both) {
            if (!keys.remove(info.getKey())) {
                fail(info.getKey() + " was not in the return list");
            }
        }
        assertEquals(0, keys.size());

        // test by type
        keys = lrcService.getResultValuesGroupKeysByType(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_MULTIPLE, callContext);
        assertEquals(3, keys.size());
        if (!keys.remove(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_LETTER)) {
            fail("does not contain " + LrcServiceConstants.RESULT_GROUP_KEY_GRADE_LETTER);
        }
        if (!keys.remove(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_LETTER_PASSING_TRANSLATION)) {
            fail("does not contain " + LrcServiceConstants.RESULT_GROUP_KEY_GRADE_LETTER_PASSING_TRANSLATION);
        }
        if (!keys.remove(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PASSFAIL)) {
            fail("does not contain " + LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PASSFAIL);
        }



        // test the convenience methods
        List<String> expectedKeys = new ArrayList<String>();
        expectedKeys.add(LrcServiceConstants.RESULT_VALUE_KEY_GRADE_LETTER_A);
        expectedKeys.add(LrcServiceConstants.RESULT_VALUE_KEY_GRADE_LETTER_B);
        expectedKeys.add(LrcServiceConstants.RESULT_VALUE_KEY_GRADE_LETTER_C);
        expectedKeys.add(LrcServiceConstants.RESULT_VALUE_KEY_GRADE_LETTER_D);
        expectedKeys.add(LrcServiceConstants.RESULT_VALUE_KEY_GRADE_LETTER_F);
        List<ResultValueInfo> rvs = lrcService.getResultValuesForResultValuesGroup(
                LrcServiceConstants.RESULT_GROUP_KEY_GRADE_LETTER, callContext);
        assertEquals(expectedKeys.size(), rvs.size());
        for (ResultValueInfo rv : rvs) {
            if (!expectedKeys.remove(rv.getKey())) {
                fail(rv.getKey() + " was not expected");
            }
        }
        assertEquals(0, expectedKeys.size());


        expectedKeys = new ArrayList<String>();
        expectedKeys.add(LrcServiceConstants.RESULT_VALUE_KEY_GRADE_LETTER_A);
        expectedKeys.add(LrcServiceConstants.RESULT_VALUE_KEY_GRADE_LETTER_B);
        expectedKeys.add(LrcServiceConstants.RESULT_VALUE_KEY_GRADE_LETTER_C);
        expectedKeys.add(LrcServiceConstants.RESULT_VALUE_KEY_GRADE_LETTER_D);
        expectedKeys.add(LrcServiceConstants.RESULT_VALUE_KEY_GRADE_LETTER_F);
        expectedKeys.add(LrcServiceConstants.RESULT_VALUE_KEY_GRADE_PF_P);
        expectedKeys.add(LrcServiceConstants.RESULT_VALUE_KEY_GRADE_PF_F);
        List<String> groupKeys = new ArrayList<String>();
        groupKeys.add(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_LETTER);
        groupKeys.add(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_LETTER_PASSING_TRANSLATION);
        groupKeys.add(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PASSFAIL);
        // this should get un duplicated values across many groups
        rvs = lrcService.getResultValuesForResultValuesGroups(groupKeys, callContext);
        assertEquals(expectedKeys.size(), rvs.size());
        for (ResultValueInfo rv : rvs) {
            if (!expectedKeys.remove(rv.getKey())) {
                fail(rv.getKey() + " was not expected");
            }
        }
        assertEquals(0, expectedKeys.size());

        
        // check that I can get the RVGs by grade related
        expectedKeys.add(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_LETTER);
        expectedKeys.add(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_LETTER_PASSING_TRANSLATION);
        expectedKeys.add(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PASSFAIL);
        // this should get un duplicated values across many groups
        List<ResultValuesGroupInfo> rvgs = lrcService.getResultValuesGroupsByResultScaleType(LrcServiceConstants.RESULT_SCALE_TYPE_KEY_GRADE, callContext);
        assertEquals(expectedKeys.size(), rvgs.size());
        for (ResultValuesGroupInfo rvg : rvgs) {
            if (!expectedKeys.remove(rvg.getKey())) {
                fail(rvg.getKey() + " was not expected");
            }
        }
        assertEquals(0, expectedKeys.size());
  
        // check that I can get the RVGs that are credit related
        expectedKeys.add(LrcServiceConstants.RESULT_GROUP_KEY_KUALI_CREDITTYPE_CREDIT_2_0);
        expectedKeys.add(LrcServiceConstants.RESULT_GROUP_KEY_KUALI_CREDITTYPE_CREDIT_1_MINUS4);
        // this should get un duplicated values across many groups
        rvgs = lrcService.getResultValuesGroupsByResultScaleType(LrcServiceConstants.RESULT_SCALE_TYPE_KEY_CREDIT, callContext);
        assertEquals(expectedKeys.size(), rvgs.size());
        for (ResultValuesGroupInfo rvg : rvgs) {
            if (!expectedKeys.remove(rvg.getKey())) {
                fail(rvg.getKey() + " was not expected");
            }
        }
        assertEquals(0, expectedKeys.size());
        
  
        
        // test delete
        StatusInfo status = lrcService.deleteResultValuesGroup(
                LrcServiceConstants.RESULT_GROUP_KEY_GRADE_LETTER_PASSING_TRANSLATION, callContext);
        assertNotNull(status);
        assertTrue(status.getIsSuccess());
        try {
            actual = lrcService.getResultValuesGroup(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_LETTER_PASSING_TRANSLATION,
                    callContext);
            fail("Did not receive DoesNotExistException when attempting to get already-deleted Entity");
        } catch (DoesNotExistException dnee) {
            // expected
        }

        status = lrcService.deleteResultValuesGroup(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_LETTER, callContext);
        assertNotNull(status);
        assertTrue(status.getIsSuccess());
        try {
            actual = lrcService.getResultValuesGroup(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_LETTER, callContext);
            fail("Did not receive DoesNotExistException when attempting to get already-deleted Entity");
        } catch (DoesNotExistException dnee) {
            // expected
        }
        status = lrcService.deleteResultValuesGroup(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PASSFAIL, callContext);
        assertNotNull(status);
        assertTrue(status.getIsSuccess());
        try {
            actual = lrcService.getResultValuesGroup(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PASSFAIL, callContext);
            fail("Did not receive DoesNotExistException when attempting to get already-deleted Entity");
        } catch (DoesNotExistException dnee) {
            // expected
        }
        
        status = lrcService.deleteResultValuesGroup(LrcServiceConstants.RESULT_GROUP_KEY_KUALI_CREDITTYPE_CREDIT_2_0, callContext);
        status = lrcService.deleteResultValuesGroup(LrcServiceConstants.RESULT_GROUP_KEY_KUALI_CREDITTYPE_CREDIT_1_MINUS4, callContext);
    }

    @Test
    public void testBusinessLogic()
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException,
            AlreadyExistsException {


        ResultValuesGroupInfo rvg = lrcService.getCreateFixedCreditResultValuesGroup("22",
                LrcServiceConstants.RESULT_SCALE_KEY_CREDIT_DEGREE, callContext);
        assertEquals("kuali.creditType.credit.22", rvg.getKey());
        assertEquals(1, rvg.getResultValueKeys().size());
        assertEquals("kuali.result.value.credit.degree.22", rvg.getResultValueKeys().get(0));
        assertEquals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_FIXED, rvg.getTypeKey());
        // repeated calls should return the same thing without createing
        rvg = lrcService.getCreateFixedCreditResultValuesGroup("22",
                LrcServiceConstants.RESULT_SCALE_KEY_CREDIT_DEGREE, callContext);
        assertEquals("kuali.creditType.credit.22", rvg.getKey());
        assertEquals(1, rvg.getResultValueKeys().size());
        assertEquals("kuali.result.value.credit.degree.22", rvg.getResultValueKeys().get(0));
        assertEquals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_FIXED, rvg.getTypeKey());
        for (String rvKey : rvg.getResultValueKeys()) {
            ResultValueInfo rv = this.lrcService.getResultValue(rvKey, callContext);
            assertEquals("22", rv.getValue());
        }

        rvg = lrcService.getCreateRangeCreditResultValuesGroup("22", "24", "1",
                LrcServiceConstants.RESULT_SCALE_KEY_CREDIT_DEGREE, callContext);
        assertEquals("kuali.creditType.credit.22-24", rvg.getKey());
        assertEquals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_RANGE, rvg.getTypeKey());
        // repeated calls should return the same thing without createing
        rvg = lrcService.getCreateRangeCreditResultValuesGroup("22", "24", "1",
                LrcServiceConstants.RESULT_SCALE_KEY_CREDIT_DEGREE, callContext);
        assertEquals("kuali.creditType.credit.22-24", rvg.getKey());
        assertEquals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_RANGE, rvg.getTypeKey());


        List<String> values = new ArrayList<String>();
        values.add("1");
        values.add("12");
        values.add("16");
        values.add("33");
        List<String> expValueKeys = new ArrayList<String>();
        expValueKeys.add("kuali.result.value.credit.degree.1");
        expValueKeys.add("kuali.result.value.credit.degree.12");
        expValueKeys.add("kuali.result.value.credit.degree.16");
        expValueKeys.add("kuali.result.value.credit.degree.33");

        rvg = lrcService.getCreateMultipleCreditResultValuesGroup(values,
                LrcServiceConstants.RESULT_SCALE_KEY_CREDIT_DEGREE, callContext);
        assertEquals("kuali.creditType.credit.1.12.16.33", rvg.getKey());
        assertEquals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_MULTIPLE, rvg.getTypeKey());
        new ListOfStringTester().check(rvg.getResultValueKeys(), expValueKeys);
        for (String rvKey : rvg.getResultValueKeys()) {
            ResultValueInfo rv = this.lrcService.getResultValue(rvKey, callContext);
            if (!values.contains(rv.getValue())) {
                fail("Result value does not have an expected value");
            }
        }

        // repeated calls should return the same thing without createing
        rvg = lrcService.getCreateMultipleCreditResultValuesGroup(values,
                LrcServiceConstants.RESULT_SCALE_KEY_CREDIT_DEGREE, callContext);
        assertEquals("kuali.creditType.credit.1.12.16.33", rvg.getKey());
        assertEquals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_MULTIPLE, rvg.getTypeKey());
        new ListOfStringTester().check(rvg.getResultValueKeys(), expValueKeys);
        for (String rvKey : rvg.getResultValueKeys()) {
            ResultValueInfo rv = this.lrcService.getResultValue(rvKey, callContext);
            if (!values.contains(rv.getValue())) {
                fail("Result value does not have an expected value");
            }
        }

        String value = "75";
        ResultValueInfo rv = this.lrcService.getCreateResultValueForScale(value,
                LrcServiceConstants.RESULT_SCALE_KEY_CREDIT_DEGREE, callContext);
        assertEquals("kuali.result.value.credit.degree.75", rv.getKey());
        assertEquals(LrcServiceConstants.RESULT_VALUE_TYPE_KEY_VALUE, rv.getTypeKey());
        assertEquals(LrcServiceConstants.RESULT_VALUE_STATE_APPROVED, rv.getStateKey());
        assertEquals(LrcServiceConstants.RESULT_VALUE_TYPE_KEY_VALUE, rv.getTypeKey());
        assertEquals(LrcServiceConstants.RESULT_SCALE_KEY_CREDIT_DEGREE, rv.getResultScaleKey());
        assertEquals(value, rv.getValue());
        new FloatAsStringTester ().check(value, rv.getNumericValue());
        // get again should not create a new one
        this.lrcService.getCreateResultValueForScale(value, LrcServiceConstants.RESULT_SCALE_KEY_CREDIT_DEGREE, callContext);
        assertEquals("kuali.result.value.credit.degree.75", rv.getKey());
        assertEquals(LrcServiceConstants.RESULT_VALUE_TYPE_KEY_VALUE, rv.getTypeKey());
        assertEquals(LrcServiceConstants.RESULT_VALUE_STATE_APPROVED, rv.getStateKey());
        assertEquals(LrcServiceConstants.RESULT_VALUE_TYPE_KEY_VALUE, rv.getTypeKey());
        assertEquals(LrcServiceConstants.RESULT_SCALE_KEY_CREDIT_DEGREE, rv.getResultScaleKey());
        assertEquals(value, rv.getValue());
        new FloatAsStringTester ().check(value, rv.getNumericValue());
    }
}
