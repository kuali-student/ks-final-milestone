/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.r2.core.class1.lrc.service.impl;

import java.sql.Timestamp;
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

        // test update
        expected = actual;
        expected.setName("name 1 updated");
        expected.setEffectiveDate(new Timestamp(expected.getEffectiveDate().getTime() - 2000));
        expected.setExpirationDate(new Timestamp(expected.getExpirationDate().getTime() + 2000));
        expected.setName("scale name 1 Updated");
        expected.setDescr(new RichTextHelper().fromPlain("scale 1 description updated"));
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

        // create an A
        ResultValueInfo valueA = new ResultValueInfo();
        valueA.setKey("valueA");
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
        valueB.setKey("valueB");
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

        // create a B
        ResultValueInfo valueC = new ResultValueInfo();
        valueC.setKey("valueC");
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


        // test create
        ResultValuesGroupInfo expected = new ResultValuesGroupInfo();

        expected.setKey("valuesGroupKey1");
        expected.setTypeKey(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_FIXED);
        expected.setStateKey(LrcServiceConstants.RESULT_VALUE_STATE_APPROVED);
        expected.setEffectiveDate(new Date());
        expected.setExpirationDate(new Date(new Date().getTime() + 1000));
        expected.setName("values group name 1");
        expected.setDescr(new RichTextHelper().fromPlain("values group 1 description"));
        expected.setResultScaleKey(scale.getKey());
        expected.getResultValueKeys().add(valueA.getKey());
        expected.getResultValueKeys().add(valueB.getKey());
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

        // test update
        expected = actual;
        expected.setName("name 1 updated");
        expected.setEffectiveDate(new Timestamp(expected.getEffectiveDate().getTime() - 2000));
        expected.setExpirationDate(new Timestamp(expected.getExpirationDate().getTime() + 2000));
        expected.setName("scale name 1 Updated");
        expected.setDescr(new RichTextHelper().fromPlain("values group 1 description updated"));
        expected.getResultValueKeys().remove(0);
        expected.getResultValueKeys().add(valueC.getKey());
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
}
