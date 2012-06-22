/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.enrollment.class1.lrc.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
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
    public void testCrudResultScale()
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
        ResultScaleInfo expected = new ResultScaleInfo();
        expected.setKey("scalekey1");
        expected.setTypeKey(LrcServiceConstants.RESULT_SCALE_TYPE_KEY_GRADE);
        expected.setStateKey(LrcServiceConstants.RESULT_SCALE_STATE_APPROVED);
        expected.setEffectiveDate(new Date());
        expected.setExpirationDate(new Date(new Date().getTime() + 1000));
        expected.setName("scale name 1");
        expected.setDescr(new RichTextHelper().fromPlain("scale 1 description"));
        ResultValueRangeInfo range = new ResultValueRangeInfo();
        range.setMinValue("1");
        range.setMaxValue("100");
        range.setIncrement("0.5");
        expected.setResultValueRange(range);
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

        ResultScaleInfo scale2 = new ResultScaleInfo();
        scale2.setKey("scalekey2");
        scale2.setTypeKey(LrcServiceConstants.RESULT_SCALE_TYPE_KEY_GRADE);
        scale2.setStateKey(LrcServiceConstants.RESULT_SCALE_STATE_APPROVED);
        scale2.setEffectiveDate(new Date());
        scale2.setExpirationDate(new Date(new Date().getTime() + 1000));
        scale2.setName("scale name 2");
        scale2.setDescr(new RichTextHelper().fromPlain("scale 2 description"));
        range = new ResultValueRangeInfo();
        range.setMinValue("0");
        range.setMaxValue("4.0");
        range.setIncrement("0.1");
        scale2.setResultValueRange(range);
        scale2 = lrcService.createResultScale(scale2.getTypeKey(), scale2,
                callContext);

        List<String> keys = new ArrayList<String>();
        keys.add(actual.getKey());
        keys.add(scale2.getKey());
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
        if (!keys.remove(actual.getKey())) {
            fail("does not contain " + actual.getKey());
        }
        if (!keys.remove(scale2.getKey())) {
            fail("does not contain " + scale2.getKey());
        }


        // test update
        expected = actual;
        expected.setName("name 1 updated");
        expected.setEffectiveDate(new Timestamp(expected.getEffectiveDate().getTime() - 2000));
        expected.setExpirationDate(new Timestamp(expected.getExpirationDate().getTime() + 2000));
        expected.setName("scale name 1 Updated");
        expected.setDescr(new RichTextHelper().fromPlain("scale 1 description updated"));
        expected.getResultValueRange().setMinValue("2");
        expected.getResultValueRange().setMaxValue("99");
        expected.getResultValueRange().setIncrement("2");
        new AttributeTester().delete1Update1Add1ForUpdate(expected.getAttributes());
        actual = lrcService.updateResultScale(expected.getKey(), expected, callContext);
        assertEquals(expected.getKey(), actual.getKey());
        new KeyEntityTester().check(expected, actual);
        checkRange(expected.getResultValueRange(), actual.getResultValueRange());
        new TimeTester().check(expected.getEffectiveDate(), actual.getEffectiveDate());
        new TimeTester().check(expected.getExpirationDate(), actual.getExpirationDate());
        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterUpdate(expected.getMeta(), actual.getMeta());

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

        // test delete
        StatusInfo status = lrcService.deleteResultScale(expected.getKey(), callContext);
        assertNotNull(status);
        assertTrue(status.getIsSuccess());
        try {
            actual = lrcService.getResultScale(expected.getKey(), callContext);
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
            fail("expected was null but actual had a value with " + toString(actual));
        }
        if (actual == null) {
            fail("actual was null but expected had a value with " + toString(expected));
        }
        assertEquals(expected.getMinValue(), expected.getMinValue());
        assertEquals(expected.getMaxValue(), expected.getMaxValue());
        assertEquals(expected.getIncrement(), expected.getIncrement());
    }

    private String toString(ResultValueRange range) {
        return range.getMinValue() + " to " + range.getMaxValue() + " + " + range.getIncrement();
    }

    @Test
    public void testCrudResultValue()
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException,
            AlreadyExistsException {

        ResultScaleInfo scale = new ResultScaleInfo();
        scale.setKey("scalekey1");
        scale.setTypeKey(LrcServiceConstants.RESULT_SCALE_TYPE_KEY_GRADE);
        scale.setStateKey(LrcServiceConstants.RESULT_SCALE_STATE_APPROVED);
        scale.setEffectiveDate(new Date());
        scale.setExpirationDate(new Date(new Date().getTime() + 1000));
        scale.setName("scale name 1");
        scale.setDescr(new RichTextHelper().fromPlain("scale 1 description"));
        ResultValueRangeInfo range = new ResultValueRangeInfo();
        range.setMinValue("1");
        range.setMaxValue("100");
        range.setIncrement("0.5");
        scale.setResultValueRange(range);
        scale = lrcService.createResultScale(scale.getTypeKey(), scale, callContext);

        // test create
        ResultValueInfo expected = new ResultValueInfo();
        expected.setKey("valueKey1");
        expected.setTypeKey(LrcServiceConstants.RESULT_VALUE_TYPE_KEY_VALUE);
        expected.setStateKey(LrcServiceConstants.RESULT_VALUE_STATE_APPROVED);
        expected.setEffectiveDate(new Date());
        expected.setExpirationDate(new Date(new Date().getTime() + 1000));
        expected.setName("value name 1");
        expected.setDescr(new RichTextHelper().fromPlain("value 1 description"));
        expected.setResultScaleKey(scale.getKey());
        expected.setValue("A");
        expected.setNumericValue("4.0");
        new AttributeTester().add2ForCreate(expected.getAttributes());
        ResultValueInfo actual = lrcService.createResultValue(expected.getResultScaleKey(), expected.getTypeKey(), expected,
                callContext);
        assertEquals(expected.getKey(), actual.getKey());
        new KeyEntityTester().check(expected, actual);
        new TimeTester().check(expected.getEffectiveDate(), actual.getEffectiveDate());
        new TimeTester().check(expected.getExpirationDate(), actual.getExpirationDate());
        assertEquals(expected.getResultScaleKey(), actual.getResultScaleKey());
        assertEquals(expected.getValue(), actual.getValue());
        assertEquals(expected.getNumericValue(), actual.getNumericValue());
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
        assertEquals(expected.getNumericValue(), actual.getNumericValue());
        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());

        // create a 2nd
        ResultValueInfo value2 = new ResultValueInfo();
        value2.setKey("valueKey2");
        value2.setTypeKey(LrcServiceConstants.RESULT_VALUE_TYPE_KEY_VALUE);
        value2.setStateKey(LrcServiceConstants.RESULT_VALUE_STATE_APPROVED);
        value2.setEffectiveDate(new Date());
        value2.setExpirationDate(new Date(new Date().getTime() + 1000));
        value2.setName("value name 2");
        value2.setDescr(new RichTextHelper().fromPlain("value 2 description"));
        value2.setResultScaleKey(scale.getKey());
        value2.setValue("B");
        value2.setNumericValue("3.0");
        value2 = lrcService.createResultValue(value2.getResultScaleKey(), value2.getTypeKey(), value2,
                callContext);

        List<String> keys = new ArrayList<String>();
        keys.add(actual.getKey());
        keys.add(value2.getKey());
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
        if (!keys.remove(value2.getKey())) {
            fail("does ot contain " + value2.getKey());
        }


        // test update
        expected = actual;
        expected.setName("name 1 updated");
        expected.setEffectiveDate(new Timestamp(expected.getEffectiveDate().getTime() - 2000));
        expected.setExpirationDate(new Timestamp(expected.getExpirationDate().getTime() + 2000));
        expected.setDescr(new RichTextHelper().fromPlain("name 1 description updated"));
        expected.setValue("B");
        expected.setValue("3.0");
        new AttributeTester().delete1Update1Add1ForUpdate(expected.getAttributes());
        actual = lrcService.updateResultValue(expected.getKey(), expected, callContext);
        assertEquals(expected.getKey(), actual.getKey());
        new KeyEntityTester().check(expected, actual);
        assertEquals(expected.getResultScaleKey(), actual.getResultScaleKey());
        assertEquals(expected.getValue(), actual.getValue());
        assertEquals(expected.getNumericValue(), actual.getNumericValue());
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
        assertEquals(expected.getNumericValue(), actual.getNumericValue());
        new TimeTester().check(expected.getEffectiveDate(), actual.getEffectiveDate());
        new TimeTester().check(expected.getExpirationDate(), actual.getExpirationDate());
        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());

        // test delete
        StatusInfo status = lrcService.deleteResultValue(expected.getKey(), callContext);
        assertNotNull(status);
        assertTrue(status.getIsSuccess());
        try {
            actual = lrcService.getResultValue(expected.getKey(), callContext);
            fail("Did not receive DoesNotExistException when attempting to get already-deleted Entity");
        } catch (DoesNotExistException dnee) {
            // expected
        }



    }

    @Test
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

        // create scale
        ResultScaleInfo scale = new ResultScaleInfo();
        scale.setKey(LrcServiceConstants.RESULT_SCALE_KEY_GRADE_LETTER);
        scale.setTypeKey(LrcServiceConstants.RESULT_SCALE_TYPE_KEY_GRADE);
        scale.setStateKey(LrcServiceConstants.RESULT_SCALE_STATE_APPROVED);
        scale.setEffectiveDate(new Date());
        scale.setExpirationDate(new Date(new Date().getTime() + 1000));
        scale.setName("scale name 1");
        scale.setDescr(new RichTextHelper().fromPlain("scale 1 description"));
        ResultValueRangeInfo range = new ResultValueRangeInfo();
        range.setMinValue("1");
        range.setMaxValue("100");
        range.setIncrement("0.5");
        scale.setResultValueRange(range);
        scale = lrcService.createResultScale(scale.getTypeKey(), scale, callContext);

        // create an A
        ResultValueInfo valueA = new ResultValueInfo();
        valueA.setKey(LrcServiceConstants.RESULT_VALUE_KEY_GRADE_LETTER_A);
        valueA.setTypeKey(LrcServiceConstants.RESULT_VALUE_TYPE_KEY_VALUE);
        valueA.setStateKey(LrcServiceConstants.RESULT_VALUE_STATE_APPROVED);
        valueA.setEffectiveDate(new Date());
        valueA.setExpirationDate(new Date(new Date().getTime() + 1000));
        valueA.setName("value A");
        valueA.setDescr(new RichTextHelper().fromPlain("value A description"));
        valueA.setResultScaleKey(scale.getKey());
        valueA.setValue("A");
        valueA.setNumericValue("4.0");
        new AttributeTester().add2ForCreate(valueA.getAttributes());
        valueA = lrcService.createResultValue(valueA.getResultScaleKey(), valueA.getTypeKey(), valueA, callContext);

        // create a B
        ResultValueInfo valueB = new ResultValueInfo();
        valueB.setKey(LrcServiceConstants.RESULT_VALUE_KEY_GRADE_LETTER_B);
        valueB.setTypeKey(LrcServiceConstants.RESULT_VALUE_TYPE_KEY_VALUE);
        valueB.setStateKey(LrcServiceConstants.RESULT_VALUE_STATE_APPROVED);
        valueB.setEffectiveDate(new Date());
        valueB.setExpirationDate(new Date(new Date().getTime() + 1000));
        valueB.setName("value B");
        valueB.setDescr(new RichTextHelper().fromPlain("value B description"));
        valueB.setResultScaleKey(scale.getKey());
        valueB.setValue("B");
        valueB.setNumericValue("3.0");
        new AttributeTester().add2ForCreate(valueB.getAttributes());
        valueB = lrcService.createResultValue(valueB.getResultScaleKey(), valueB.getTypeKey(), valueB, callContext);

        // create a C
        ResultValueInfo valueC = new ResultValueInfo();
        valueC.setKey(LrcServiceConstants.RESULT_VALUE_KEY_GRADE_LETTER_C);
        valueC.setTypeKey(LrcServiceConstants.RESULT_VALUE_TYPE_KEY_VALUE);
        valueC.setStateKey(LrcServiceConstants.RESULT_VALUE_STATE_APPROVED);
        valueC.setEffectiveDate(new Date());
        valueC.setExpirationDate(new Date(new Date().getTime() + 1000));
        valueC.setName("value C");
        valueC.setDescr(new RichTextHelper().fromPlain("value C description"));
        valueC.setResultScaleKey(scale.getKey());
        valueC.setValue("C");
        valueC.setNumericValue("2.0");
        new AttributeTester().add2ForCreate(valueC.getAttributes());
        valueC = lrcService.createResultValue(valueC.getResultScaleKey(), valueC.getTypeKey(), valueC, callContext);

        // create a D
        ResultValueInfo valueD = new ResultValueInfo();
        valueD.setKey(LrcServiceConstants.RESULT_VALUE_KEY_GRADE_LETTER_D);
        valueD.setTypeKey(LrcServiceConstants.RESULT_VALUE_TYPE_KEY_VALUE);
        valueD.setStateKey(LrcServiceConstants.RESULT_VALUE_STATE_APPROVED);
        valueD.setEffectiveDate(new Date());
        valueD.setExpirationDate(new Date(new Date().getTime() + 1000));
        valueD.setName("value D");
        valueD.setDescr(new RichTextHelper().fromPlain("value D description"));
        valueD.setResultScaleKey(scale.getKey());
        valueD.setValue("D");
        valueD.setNumericValue("1.0");
        new AttributeTester().add2ForCreate(valueD.getAttributes());
        valueD = lrcService.createResultValue(valueD.getResultScaleKey(), valueD.getTypeKey(), valueD, callContext);


        // create a F
        ResultValueInfo valueF = new ResultValueInfo();
        valueF.setKey(LrcServiceConstants.RESULT_VALUE_KEY_GRADE_LETTER_F);
        valueF.setTypeKey(LrcServiceConstants.RESULT_VALUE_TYPE_KEY_VALUE);
        valueF.setStateKey(LrcServiceConstants.RESULT_VALUE_STATE_APPROVED);
        valueF.setEffectiveDate(new Date());
        valueF.setExpirationDate(new Date(new Date().getTime() + 1000));
        valueF.setName("value F");
        valueF.setDescr(new RichTextHelper().fromPlain("value F description"));
        valueF.setResultScaleKey(scale.getKey());
        valueF.setValue("F");
        valueF.setNumericValue("0.0");
        new AttributeTester().add2ForCreate(valueF.getAttributes());
        valueF = lrcService.createResultValue(valueF.getResultScaleKey(), valueF.getTypeKey(), valueF, callContext);


        // test create
        ResultValuesGroupInfo expected = new ResultValuesGroupInfo();

        expected.setKey(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_LETTER_PASSING_TRANSLATION);
        expected.setTypeKey(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_FIXED);
        expected.setStateKey(LrcServiceConstants.RESULT_VALUE_STATE_APPROVED);
        expected.setEffectiveDate(new Date());
        expected.setExpirationDate(new Date(new Date().getTime() + 1000));
        expected.setName("Passing Letter Grades");
        expected.setDescr(new RichTextHelper().fromPlain("Grades that are considered passing on an A-F letter scale"));
        expected.setResultScaleKey(scale.getKey());
        expected.getResultValueKeys().add(valueA.getKey());
        expected.getResultValueKeys().add(valueB.getKey());
        expected.getResultValueKeys().add(valueC.getKey());
        expected.getResultValueKeys().add(valueD.getKey());
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


        // test create a 2nd one
        ResultValuesGroupInfo group2 = new ResultValuesGroupInfo();

        group2.setKey(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_LETTER);
        group2.setTypeKey(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_FIXED);
        group2.setStateKey(LrcServiceConstants.RESULT_VALUE_STATE_APPROVED);
        group2.setEffectiveDate(new Date());
        group2.setExpirationDate(new Date(new Date().getTime() + 1000));
        group2.setName("Passing Letter Grades");
        group2.setDescr(new RichTextHelper().fromPlain("Grades that are considered passing on an A-F letter scale"));
        group2.setResultScaleKey(scale.getKey());
        group2.getResultValueKeys().add(valueA.getKey());
        group2.getResultValueKeys().add(valueB.getKey());
        group2.getResultValueKeys().add(valueC.getKey());
        group2.getResultValueKeys().add(valueD.getKey());
        group2 = lrcService.createResultValuesGroup(group2.getResultScaleKey(), group2.getTypeKey(),
                group2,
                callContext);

        List<String> keys = new ArrayList<String>();
        keys.add(actual.getKey());
        keys.add(group2.getKey());
        List<ResultValuesGroupInfo> both = lrcService.getResultValuesGroupsByKeys(keys, callContext);
        assertEquals(keys.size(), both.size());
        for (ResultValuesGroupInfo info : both) {
            if (!keys.remove(info.getKey())) {
                fail(info.getKey() + " was not in the return list");
            }
        }
        assertEquals(0, keys.size());

        // test by type
        keys = lrcService.getResultValuesGroupKeysByType(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_FIXED, callContext);
        assertEquals(2, keys.size());
        if (!keys.remove(actual.getKey())) {
            fail("does not contain " + actual.getKey());
        }
        if (!keys.remove(group2.getKey())) {
            fail("does not contain " + group2.getKey());
        }


        // test update
        expected = actual;
        expected.setName("name 1 updated");
        expected.setEffectiveDate(new Timestamp(expected.getEffectiveDate().getTime() - 2000));
        expected.setExpirationDate(new Timestamp(expected.getExpirationDate().getTime() + 2000));
        expected.setDescr(new RichTextHelper().fromPlain("values group 1 description updated"));
        expected.getResultValueKeys().remove(0);
        expected.getResultValueKeys().add(valueF.getKey());
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

        // test delete
        StatusInfo status = lrcService.deleteResultValuesGroup(expected.getKey(), callContext);
        assertNotNull(status);
        assertTrue(status.getIsSuccess());
        try {
            actual = lrcService.getResultValuesGroup(expected.getKey(), callContext);
            fail("Did not receive DoesNotExistException when attempting to get already-deleted Entity");
        } catch (DoesNotExistException dnee) {
            // expected
        }



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
        assertEquals(value, rv.getNumericValue());
        // get again should not create a new one
        this.lrcService.getCreateResultValueForScale(value, LrcServiceConstants.RESULT_SCALE_KEY_CREDIT_DEGREE, callContext);
        assertEquals("kuali.result.value.credit.degree.75", rv.getKey());
        assertEquals(LrcServiceConstants.RESULT_VALUE_TYPE_KEY_VALUE, rv.getTypeKey());
        assertEquals(LrcServiceConstants.RESULT_VALUE_STATE_APPROVED, rv.getStateKey());
        assertEquals(LrcServiceConstants.RESULT_VALUE_TYPE_KEY_VALUE, rv.getTypeKey());
        assertEquals(LrcServiceConstants.RESULT_SCALE_KEY_CREDIT_DEGREE, rv.getResultScaleKey());
        assertEquals(value, rv.getValue());
        assertEquals(value, rv.getNumericValue());
    }
}
