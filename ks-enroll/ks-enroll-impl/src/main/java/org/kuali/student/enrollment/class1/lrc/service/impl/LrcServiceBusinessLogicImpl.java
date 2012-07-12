/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.enrollment.class1.lrc.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;
import org.kuali.student.r2.common.util.constants.LrcServiceConstants;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValueRangeInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.kuali.student.r2.lum.lrc.service.LrcServiceBusinessLogic;

import javax.xml.namespace.QName;

/**
 *
 * @author nwright
 */
public class LrcServiceBusinessLogicImpl implements LrcServiceBusinessLogic {

    private LRCService lrcService;

    public LRCService getLrcService() {
        if(lrcService == null){
            lrcService = GlobalResourceLoader.getService(new QName(LrcServiceConstants.NAMESPACE,
                    LrcServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return lrcService;
    }

    public void setLrcService(LRCService lrcService) {
        this.lrcService = lrcService;
    }

    /** 
     * Calculate the result values group key for the fixed credit value
     * @param creditValue
     * @param scaleKey
     * @return
     * @throws InvalidParameterException 
     */
    protected String calcFixedCreditRvgKey(String creditValue,
            String scaleKey,
            ContextInfo contextInfo)
            throws InvalidParameterException {

        if (scaleKey.equals(LrcServiceConstants.RESULT_SCALE_KEY_CREDIT_DEGREE)) {
            return "kuali.creditType.credit." + creditValue;
        }
        if (scaleKey.equals(LrcServiceConstants.RESULT_SCALE_KEY_CREDIT_REMEDIAL)) {
            return "kuali.result.group.credit.remedial.fixed." + creditValue;
        }
        throw new InvalidParameterException("unknown/unhandled credit type scale key " + scaleKey);
    }

    /** 
     * Calculate the result values group key for the range credit value
     * @param creditValue
     * @param scaleKey
     * @return
     * @throws InvalidParameterException 
     */
    protected String calcFixedCreditRvgName(String value,
            String scaleKey,
            ContextInfo contextInfo)
            throws InvalidParameterException {
        StringBuilder sb = new StringBuilder();
        sb.append("Credits");
        sb.append(value);
        return sb.toString();
    }

    /**
     * Calculate the fixed credit value key to use that matches the specified value
     * @param creditValue
     * @param scaleKey
     * @return
     * @throws InvalidParameterException 
     */
    protected String calcCreditValueKey(String creditValue,
            String scaleKey,
            ContextInfo contextInfo)
            throws InvalidParameterException {

        if (scaleKey.equals(LrcServiceConstants.RESULT_SCALE_KEY_CREDIT_DEGREE)) {
            return "kuali.result.value.credit.degree." + creditValue;
        }
        if (scaleKey.equals(LrcServiceConstants.RESULT_SCALE_KEY_CREDIT_REMEDIAL)) {
            return "kuali.result.value.credit.remedial." + creditValue;
        }
        throw new InvalidParameterException("unknown/unhandled credit type scale key " + scaleKey);
    }

    @Override
    public ResultValuesGroupInfo getCreateFixedCreditResultValuesGroup(String creditValue,
            String scaleKey,
            ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        String rvgKey = calcFixedCreditRvgKey(creditValue, scaleKey, contextInfo);
        String valueKey = this.calcCreditValueKey(creditValue, scaleKey, contextInfo);
        try {
            ResultValuesGroupInfo rvg = getLrcService().getResultValuesGroup(rvgKey, contextInfo);
            if (!rvg.getTypeKey().equals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_FIXED)) {
                throw new OperationFailedException("Calculated key does not point to a FIXED RVG: " + rvgKey);
            }
            if (!rvg.getResultScaleKey().equals(scaleKey)) {
                throw new OperationFailedException("Calculated key does not point to an RVG of the expected scale key: " + rvgKey);
            }
            if (rvg.getResultValueKeys().size() != 1) {
                throw new OperationFailedException("Calculated key does not point to an RVG with a single value: " + rvgKey);
            }
            if (!rvg.getResultValueKeys().get(0).equals(valueKey)) {
                throw new OperationFailedException("Calculated key does not point to an RVG with the expected value key : " +
                        rvgKey);
            }
            return rvg;
        } catch (DoesNotExistException ex) {
            // ok then create
        }
//      find/create value
        ResultValueInfo value = null;
        try {
            value = getLrcService().getResultValue(valueKey, contextInfo);
        } catch (DoesNotExistException ex) {
            value = new ResultValueInfo();
            value.setKey(valueKey);
            value.setTypeKey(LrcServiceConstants.RESULT_VALUE_TYPE_KEY_VALUE);
            value.setStateKey(LrcServiceConstants.RESULT_VALUE_STATE_APPROVED);
            value.setName(creditValue);
            value.setValue(creditValue);
            value.setNumericValue(creditValue);
            value.setResultScaleKey(scaleKey);
            value.setEffectiveDate(new Date());
            try {
                value = lrcService.createResultValue(value.getResultScaleKey(), value.getTypeKey(), value, contextInfo);
            } catch (AlreadyExistsException ex1) {
                throw new OperationFailedException("unexpected", ex);
            } catch (DataValidationErrorException ex1) {
                throw new OperationFailedException("unexpected", ex);
            } catch (DoesNotExistException ex1) {
                throw new OperationFailedException("unexpected", ex);
            }
        }

        // not found so create it 
        ResultValuesGroupInfo rvg = new ResultValuesGroupInfo();
        rvg.setKey(rvgKey);
        rvg.setTypeKey(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_FIXED);
        rvg.setStateKey(LrcServiceConstants.RESULT_GROUPS_STATE_APPROVED);
        rvg.setName("Fixed Credit Value " + creditValue);
        rvg.setEffectiveDate(new Date());
        rvg.setResultScaleKey(scaleKey);
        rvg.getResultValueKeys().add(valueKey);
        try {
            rvg = getLrcService().createResultValuesGroup(rvg.getResultScaleKey(), rvg.getTypeKey(), rvg, contextInfo);
        } catch (AlreadyExistsException ex) {
            throw new OperationFailedException("unexpected", ex);
        } catch (DataValidationErrorException ex) {
            throw new OperationFailedException("unexpected", ex);
        }
        return rvg;
    }

    /** 
     * Calculate the result values group key for the range credit value
     * @param creditValue
     * @param scaleKey
     * @return
     * @throws InvalidParameterException 
     */
    protected String calcRangeCreditRvgKey(String creditValueMin,
            String creditValueMax,
            String creditValueIncrement,
            String scaleKey,
            ContextInfo contextInfo)
            throws InvalidParameterException {

        if (scaleKey.equals(LrcServiceConstants.RESULT_SCALE_KEY_CREDIT_DEGREE)) {
            StringBuilder sb = new StringBuilder();
            sb.append("kuali.creditType.credit.");
            sb.append(creditValueMin).append("-").append(creditValueMax);
            if (!creditValueIncrement.equals("1")) {
                sb.append(".by.").append(creditValueIncrement);
            }
            return sb.toString();
        }
        if (scaleKey.equals(LrcServiceConstants.RESULT_SCALE_KEY_CREDIT_REMEDIAL)) {
            StringBuilder sb = new StringBuilder();
            sb.append("kuali.result.group.credit.remedial.range.").append(creditValueMin).append("-").append(creditValueMax);
            if (creditValueIncrement.equals("1")) {
                sb.append(".by.").append(creditValueIncrement);
            }
            return sb.toString();
        }
        throw new InvalidParameterException("unknown/unhandled credit type scale key " + scaleKey);
    }

    /** 
     * Calculate the result values group key for the range credit value
     * @param creditValue
     * @param scaleKey
     * @return
     * @throws InvalidParameterException 
     */
    protected String calcRangeCreditRvgName(String creditValueMin,
            String creditValueMax,
            String creditValueIncrement,
            String scaleKey,
            ContextInfo contextInfo)
            throws InvalidParameterException {

        StringBuilder sb = new StringBuilder();
        sb.append(creditValueMin).append(" - ").append(creditValueMax);
        if (!creditValueIncrement.equals("1")) {
            sb.append(" by ").append(creditValueIncrement);
        }
        return sb.toString();
    }

    @Override
    public ResultValuesGroupInfo getCreateRangeCreditResultValuesGroup(String creditValueMin,
            String creditValueMax,
            String creditValueIncrement,
            String scaleKey,
            ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        String rvgKey = calcRangeCreditRvgKey(creditValueMin, creditValueMax, creditValueIncrement, scaleKey, contextInfo);
        try {
            ResultValuesGroupInfo rvg = getLrcService().getResultValuesGroup(rvgKey, contextInfo);
            if (!rvg.getTypeKey().equals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_RANGE)) {
                throw new OperationFailedException("Calculated key does not point to a RANGE RVG: " + rvgKey);
            }
            if (!rvg.getResultScaleKey().equals(scaleKey)) {
                throw new OperationFailedException("Calculated key does not point to an RVG of the expected scale key: " + rvgKey);
            }
            if (rvg.getResultValueRange() == null) {
                throw new OperationFailedException("Calculated key does not point to an RVG with a range: " + rvgKey);
            }
            if (!rvg.getResultValueRange().getMinValue().equals(creditValueMin)) {
                throw new OperationFailedException("Calculated key does not point to an RVG with a range with the same min value: " +
                        rvgKey);
            }
            if (!rvg.getResultValueRange().getMaxValue().equals(creditValueMax)) {
                throw new OperationFailedException("Calculated key does not point to an RVG with a range with the same max value: " +
                        rvgKey);
            }
            if (!rvg.getResultValueRange().getIncrement().equals(creditValueIncrement)) {
                throw new OperationFailedException("Calculated key does not point to an RVG with a range with the same increment value: " +
                        rvgKey);
            }
            return rvg;
        } catch (DoesNotExistException ex) {
            // ok then create
        }

        // not found so create it 
        ResultValuesGroupInfo rvg = new ResultValuesGroupInfo();
        rvg.setKey(rvgKey);
        rvg.setTypeKey(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_RANGE);
        rvg.setStateKey(LrcServiceConstants.RESULT_GROUPS_STATE_APPROVED);
        rvg.setName(this.calcRangeCreditRvgName(creditValueMin, creditValueMax, creditValueIncrement, scaleKey, contextInfo));
        rvg.setEffectiveDate(new Date());
        rvg.setResultScaleKey(scaleKey);
        ResultValueRangeInfo range = new ResultValueRangeInfo();
        range.setMinValue(creditValueMin);
        range.setMaxValue(creditValueMax);
        range.setIncrement(creditValueIncrement);
        rvg.setResultValueRange(range);
        try {
            rvg = getLrcService().createResultValuesGroup(rvg.getResultScaleKey(), rvg.getTypeKey(), rvg, contextInfo);
        } catch (AlreadyExistsException ex) {
            throw new OperationFailedException("unexpected", ex);
        } catch (DataValidationErrorException ex) {
            throw new OperationFailedException("unexpected", ex);
        }
        return rvg;
    }

    /** 
     * Calculate the result values group key for the range credit value
     * @param creditValue
     * @param scaleKey
     * @return
     * @throws InvalidParameterException 
     */
    protected String calcMultipleCreditRvgKey(List<String> values,
            String scaleKey,
            ContextInfo contextInfo)
            throws InvalidParameterException {

        if (scaleKey.equals(LrcServiceConstants.RESULT_SCALE_KEY_CREDIT_DEGREE)) {
            StringBuilder sb = new StringBuilder();
            sb.append("kuali.creditType.credit");
            for (String value : values) {
                sb.append(".");
                sb.append(value);
            }
            return sb.toString();
        }
        if (scaleKey.equals(LrcServiceConstants.RESULT_SCALE_KEY_CREDIT_REMEDIAL)) {
            StringBuilder sb = new StringBuilder();
            sb.append("kuali.result.group.credit.remedial.multiple");
            for (String value : values) {
                sb.append(".");
                sb.append(value);
            }
            return sb.toString();
        }
        throw new InvalidParameterException("unknown/unhandled credit type scale key " + scaleKey);
    }

    /** 
     * Calculate the result values group key for the range credit value
     * @param creditValue
     * @param scaleKey
     * @return
     * @throws InvalidParameterException 
     */
    protected String calcMultipleCreditRvgName(List<String> values,
            String scaleKey,
            ContextInfo contextInfo)
            throws InvalidParameterException {
        StringBuilder sb = new StringBuilder();
        sb.append("Mulitple credits ");
        String comma = "";
        for (String value : values) {
            sb.append(comma);
            sb.append(value);
            comma = ", ";
        }
        return sb.toString();
    }

    /**
     * Calculate the multiple credit value keys to use that matches the specified value
     * @param creditValues
     * @param scaleKey
     * @return
     * @throws InvalidParameterException 
     */
    protected List<String> calcMultipleCreditValueKey(List<String> creditValues,
            String scaleKey,
            ContextInfo contextInfo)
            throws InvalidParameterException {
        List<String> list = new ArrayList<String>();
        for (String value : creditValues) {
            list.add(this.calcCreditValueKey(value, scaleKey, contextInfo));
        }
        return list;
    }

    @Override
    public ResultValuesGroupInfo getCreateMultipleCreditResultValuesGroup(List<String> creditValues,
            String scaleKey,
            ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        String rvgKey = calcMultipleCreditRvgKey(creditValues, scaleKey, contextInfo);
        List<String> valueKeys = this.calcMultipleCreditValueKey(creditValues, scaleKey, contextInfo);
        try {
            ResultValuesGroupInfo rvg = getLrcService().getResultValuesGroup(rvgKey, contextInfo);
            if (!rvg.getTypeKey().equals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_MULTIPLE)) {
                throw new OperationFailedException("Calculated key does not point to a MULTIPLE RVG: " + rvgKey);
            }
            if (!rvg.getResultScaleKey().equals(scaleKey)) {
                throw new OperationFailedException("Calculated key does not point to an RVG of the expected scale key: " + rvgKey);
            }
            if (rvg.getResultValueKeys().size() != valueKeys.size()) {
                throw new OperationFailedException("Calculated key does not point to an RVG with the expected number of credit values: " +
                        rvgKey);
            }
            for (String valueKey : valueKeys) {
                if (!rvg.getResultValueKeys().contains(valueKey)) {
                    throw new OperationFailedException("Calculated key does not point to an RVG with the expected value key : " +
                            rvgKey + " " + valueKey);
                }
            }
            return rvg;
        } catch (DoesNotExistException ex) {
            // ok then create
        }
//      find/create values first
        int i = 0;
        for (String valueKey : valueKeys) {
            String creditValue = creditValues.get(i);
            i++;
            ResultValueInfo value = null;
            try {
                value = getLrcService().getResultValue(valueKey, contextInfo);
            } catch (DoesNotExistException ex) {
                value = new ResultValueInfo();
                value.setKey(valueKey);
                value.setTypeKey(LrcServiceConstants.RESULT_VALUE_TYPE_KEY_VALUE);
                value.setStateKey(LrcServiceConstants.RESULT_VALUE_STATE_APPROVED);
                value.setName(creditValue);
                value.setValue(creditValue);
                value.setNumericValue(creditValue);
                value.setResultScaleKey(scaleKey);
                value.setEffectiveDate(new Date());
                try {
                    value = getLrcService().createResultValue(value.getResultScaleKey(), value.getTypeKey(), value, contextInfo);
                } catch (AlreadyExistsException ex1) {
                    throw new OperationFailedException("unexpected", ex);
                } catch (DataValidationErrorException ex1) {
                    throw new OperationFailedException("unexpected", ex);
                } catch (DoesNotExistException ex1) {
                    throw new OperationFailedException("unexpected", ex);
                }
            }
        }

        // not found so create it 
        ResultValuesGroupInfo rvg = new ResultValuesGroupInfo();
        rvg.setKey(rvgKey);
        rvg.setTypeKey(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_MULTIPLE);
        rvg.setStateKey(LrcServiceConstants.RESULT_GROUPS_STATE_APPROVED);
        rvg.setName(calcMultipleCreditRvgName(creditValues, scaleKey, contextInfo));
        rvg.setEffectiveDate(new Date());
        rvg.setResultScaleKey(scaleKey);
        rvg.getResultValueKeys().addAll(valueKeys);
        try {
            rvg = getLrcService().createResultValuesGroup(rvg.getResultScaleKey(), rvg.getTypeKey(), rvg, contextInfo);
        } catch (AlreadyExistsException ex) {
            throw new OperationFailedException("unexpected", ex);
        } catch (DataValidationErrorException ex) {
            throw new OperationFailedException("unexpected", ex);
        }
        return rvg;
    }

    /**
     * Calculate key to use for the result value
     * @param resultValue the value of the result
     * @param groupInfo the group to which it is associated
     * @param contextInfo context
     * @return the calculated value
     */
    protected String calcResultValueKey(String resultValue,
            String scaleKey,
            ContextInfo contextInfo) {
        StringBuilder sb = new StringBuilder();
        sb.append(scaleKey.replace(".scale.", ".value."));
        sb.append(".");
        sb.append(resultValue);
        return sb.toString();
    }

    @Override
    public ResultValueInfo getCreateResultValueForScale(String resultValue,
            String scaleKey,
            ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        String resultValueKey = this.calcResultValueKey(resultValue, scaleKey, contextInfo);
        try {
            ResultValueInfo info = this.getLrcService().getResultValue(resultValueKey, contextInfo);
            return info;
        } catch (DoesNotExistException ex) {
            // ok so we create it
        }
        // create the value
        ResultValueInfo value = new ResultValueInfo();
        value.setKey(resultValueKey);
        value.setTypeKey(LrcServiceConstants.RESULT_VALUE_TYPE_KEY_VALUE);
        value.setStateKey(LrcServiceConstants.RESULT_VALUE_STATE_APPROVED);
        value.setName(resultValue);
        value.setValue(resultValue);
        try {
            Float.parseFloat(resultValue);
            value.setNumericValue(resultValue);
        } catch (NumberFormatException ex) {
            // ok not a floating point number
        }
        value.setResultScaleKey(scaleKey);
        value.setEffectiveDate(new Date());
        try {
            value = getLrcService().createResultValue(value.getResultScaleKey(), value.getTypeKey(), value, contextInfo);
        } catch (AlreadyExistsException ex) {
            throw new OperationFailedException("unexpected", ex);
        } catch (DataValidationErrorException ex) {
            throw new OperationFailedException("unexpected", ex);
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("unexpected", ex);
        }
        return value;
    }
}
