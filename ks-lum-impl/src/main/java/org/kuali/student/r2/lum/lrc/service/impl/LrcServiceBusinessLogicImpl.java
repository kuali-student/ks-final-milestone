/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.r2.lum.lrc.service.impl;

import org.apache.log4j.Logger;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValueRangeInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.kuali.student.r2.lum.lrc.service.LrcServiceBusinessLogic;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author nwright
 */
public class LrcServiceBusinessLogicImpl implements LrcServiceBusinessLogic {

    private static final Logger LOG = Logger.getLogger(LrcServiceBusinessLogicImpl.class);

    private LRCService lrcService;

    /** 
     * Calculate the result values group key for the fixed credit value
     * @param creditValue value
     * @param scaleKey key
     * @return the calculated key
     * @throws InvalidParameterException 
     */
    protected String calcFixedCreditRvgKey(String creditValue,
            String scaleKey)
            throws InvalidParameterException {

        if (scaleKey.equals(LrcServiceConstants.RESULT_SCALE_KEY_CREDIT_DEGREE)) {
            return LrcServiceConstants.RESULT_GROUP_KEY_KUALI_CREDITTYPE_CREDIT_BASE_OLD + creditValue;
        }
        if (scaleKey.equals(LrcServiceConstants.RESULT_SCALE_KEY_CREDIT_REMEDIAL)) {
            return LrcServiceConstants.RESULT_GROUP_KEY_CREDIT_REMEDIAL_FIXED_BASE + creditValue;
        }
        throw new InvalidParameterException("unknown/unhandled credit type scale key " + scaleKey);
    }

    /** 
     * Calculate the result values group key for the range credit value
     * @param value value
     * @return name of fixed RVG
     * @throws InvalidParameterException 
     */
    protected String calcFixedCreditRvgName(String value)
            throws InvalidParameterException {
        StringBuilder sb = new StringBuilder();
        sb.append(value);
        sb.append(" Credits");
        return sb.toString();
    }

    /**
     * Calculate the fixed credit value key to use that matches the specified value
     * @param creditValue value
     * @param scaleKey scale key
     * @return fixed credit value key
     * @throws InvalidParameterException 
     */
    protected String calcCreditValueKey(String creditValue,
            String scaleKey)
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
        String rvgKey = calcFixedCreditRvgKey(creditValue, scaleKey);
        String valueKey = this.calcCreditValueKey(creditValue, scaleKey);
        try {
            ResultValuesGroupInfo rvg = getLrcService().getResultValuesGroup(rvgKey, contextInfo);
            if (!rvg.getTypeKey().equals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_FIXED)) {
                throw new OperationFailedException("Calculated key["+rvgKey+"] does not point to a FIXED RVG: Expected["+rvg.getTypeKey()+"]:Actual[" + LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_FIXED +"]");
            }
            if (!rvg.getResultScaleKey().equals(scaleKey)) {
                throw new OperationFailedException("Calculated key["+rvgKey+"] does not point to an RVG of the expected scale key: Expected["+rvg.getResultScaleKey()+"]:Actual[" + scaleKey +"]");
            }
            if (rvg.getResultValueKeys().size() != 1) {
                throw new OperationFailedException("Calculated key["+rvgKey+"] does not point to an RVG with a single value: " + rvgKey);
            }
            if (!rvg.getResultValueKeys().get(0).equals(valueKey)) {
                throw new OperationFailedException("Calculated key["+rvgKey+"] does not point to an RVG with the expected value key : Expected["+rvg.getResultValueKeys().get(0)+"]:Actual[" + valueKey +"]");
            }
            return rvg;
        } catch (DoesNotExistException ex) {
            // ok then create
        }

        //  find/create value
        findCreateResultValue(valueKey,creditValue,scaleKey,contextInfo);

        // not found so create it 
        ResultValuesGroupInfo rvg = new ResultValuesGroupInfo();
        rvg.setKey(rvgKey);
        rvg.setTypeKey(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_FIXED);
        rvg.setStateKey(LrcServiceConstants.RESULT_GROUPS_STATE_APPROVED);
        rvg.setName(calcFixedCreditRvgName(creditValue)); //TODO this isn't always a credit!
        rvg.setDescr(new RichTextInfo());
        rvg.getDescr().setFormatted(creditValue + " Academic credits");//TODO this isn't always a credit!
        rvg.getDescr().setPlain(rvg.getDescr().getFormatted());
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
     * @param creditValueMin min credit value of range
     * @param creditValueMax max credit value of range
     * @param creditValueIncrement increment of range
     * @param scaleKey scale key
     * @return result value group key
     * @throws InvalidParameterException 
     */
    protected String calcRangeCreditRvgKey(String creditValueMin,
            String creditValueMax,
            String creditValueIncrement,
            String scaleKey)
            throws InvalidParameterException {

        if (scaleKey.equals(LrcServiceConstants.RESULT_SCALE_KEY_CREDIT_DEGREE)) {
            StringBuilder sb = new StringBuilder();
            sb.append(LrcServiceConstants.RESULT_GROUP_KEY_KUALI_CREDITTYPE_CREDIT_BASE_OLD);
            sb.append(creditValueMin).append("-").append(creditValueMax);
            if (!creditValueIncrement.equals("1")) {
                sb.append(".by.").append(creditValueIncrement);
            }
            return sb.toString();
        }
        if (scaleKey.equals(LrcServiceConstants.RESULT_SCALE_KEY_CREDIT_REMEDIAL)) {
            StringBuilder sb = new StringBuilder();
            //TODO this needs to be a constant and the DB needs to match these values.
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
     * @param creditValueMin min credit value of range
     * @param creditValueMax max credit value of range
     * @param creditValueIncrement increment of range
     * @return result value group name
     * @throws InvalidParameterException 
     */
    protected String calcRangeCreditRvgName(String creditValueMin,
            String creditValueMax,
            String creditValueIncrement)
            throws InvalidParameterException {

        StringBuilder sb = new StringBuilder();
        sb.append(creditValueMin).append(" - ").append(creditValueMax);
        if (creditValueIncrement!=null && !"1".equals(creditValueIncrement) && !"1.0".equals(creditValueIncrement)) {
            sb.append(" by ").append(creditValueIncrement);
        }
        return sb.toString();
    }

    /**
     * calculate all the values needed to create the range
     * @param creditValueMin min credit value of range
     * @param creditValueMax max credit value of range
     * @param creditValueIncrement increment of range
     * @return a list of all the values in the range using the increment
     * @throws InvalidParameterException
     */
    protected List<String> calcRangeCreditValues(String creditValueMin, String creditValueMax, String creditValueIncrement) throws InvalidParameterException {
        List<String> values = new ArrayList<String>();
        float min = Float.parseFloat(creditValueMin);
        float max = Float.parseFloat(creditValueMax);
        float increment = Float.parseFloat(creditValueIncrement);

        if (increment <= 0) {
            throw new InvalidParameterException(String.format("The creditValueIncrement was <= 0. " +
                    "If that happens then the following loop will cause the system to run out of memory." +
                    "Variables: creditValueMin[%s] creditValueMax[%s] creditValueIncrement[%s]", creditValueMin, creditValueMax, creditValueIncrement));
        }
        if ((max - min) / increment > 500) {
            LOG.warn(String.format("calcRangeCreditValues is about to create a large list of values, which may result in memory issues. " +
                    "Variables: creditValueMin[%s] creditValueMax[%s] creditValueIncrement[%s]", creditValueMin, creditValueMax, creditValueIncrement));
        }

        for (float f = min; f <= max; f += increment) {
            values.add(String.valueOf(f));
        }
        return values;
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
        String rvgKey = calcRangeCreditRvgKey(creditValueMin, creditValueMax, creditValueIncrement, scaleKey);
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
            if (!stringNumberEquals(rvg.getResultValueRange().getMinValue(),creditValueMin)) {
                throw new OperationFailedException("Calculated key does not point to an RVG with a range with the same min value: " +
                        rvgKey);
            }
            if (!stringNumberEquals(rvg.getResultValueRange().getMaxValue(), creditValueMax)) {
                throw new OperationFailedException("Calculated key does not point to an RVG with a range with the same max value: " +
                        rvgKey);
            }
            if (!stringNumberEquals(rvg.getResultValueRange().getIncrement(),creditValueIncrement)) {
                throw new OperationFailedException("Calculated key does not point to an RVG with a range with the same increment value: " +
                        rvgKey);
            }
            return rvg;
        } catch (DoesNotExistException ex) {
            // ok then create
        }

        //find/create result values
        List<String> values = calcRangeCreditValues(creditValueMin, creditValueMax, creditValueIncrement);
        List<String> valueKeys = calcCreditValueKeys(values, scaleKey);
        findCreateResultValues(valueKeys,values,scaleKey,contextInfo);

        // not found so create it 
        ResultValuesGroupInfo rvg = new ResultValuesGroupInfo();
        rvg.setKey(rvgKey);
        rvg.setTypeKey(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_RANGE);
        rvg.setStateKey(LrcServiceConstants.RESULT_GROUPS_STATE_APPROVED);
        rvg.setName(this.calcRangeCreditRvgName(creditValueMin, creditValueMax, creditValueIncrement));
        rvg.setDescr(new RichTextInfo());
        rvg.getDescr().setFormatted(rvg.getName());
        rvg.getDescr().setPlain(rvg.getDescr().getFormatted());
        rvg.setEffectiveDate(new Date());
        rvg.setResultScaleKey(scaleKey);
        rvg.setResultValueKeys(valueKeys);
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
     * This method converts the two input strings into floats and returns true if they're equal, false otherwise.
     *
     * This is needed because  getCreateRangeCreditResultValuesGroup was throwing errors saying strings
     * 1.0 != 1.
     *
     * @param value1 value 1
     * @param value2 value 2
     * @return value1.equals(value2)
     */
    protected static boolean stringNumberEquals(String value1, String value2){

        Float f1 = new Float(value1);
        Float f2 = new Float(value2);
        return f1.equals(f2);
    }

    /** 
     * Calculate the result values group key for the range credit value
     * @param values values
     * @param scaleKey scale key
     * @return a multiple credit RVG key
     * @throws InvalidParameterException 
     */
    protected String calcMultipleCreditRvgKey(List<String> values,
            String scaleKey)
            throws InvalidParameterException {

        if (scaleKey.equals(LrcServiceConstants.RESULT_SCALE_KEY_CREDIT_DEGREE)) {
            StringBuilder sb = new StringBuilder();

            // so the base string has a "." at the end... remove it
            String baseType = LrcServiceConstants.RESULT_GROUP_KEY_KUALI_CREDITTYPE_CREDIT_BASE_OLD.substring(0,
                                LrcServiceConstants.RESULT_GROUP_KEY_KUALI_CREDITTYPE_CREDIT_BASE_OLD.length() - 1);

            sb.append(baseType);
            sb.append(".");

            Collections.sort(values, new Comparator<String>() {
                public int compare(String o1, String o2) {
                    return Float.compare(Float.parseFloat(o1),Float.parseFloat(o2));
                }
            });

            for (Iterator<String> i = values.iterator();i.hasNext();) {
                String value = i.next();
                sb.append(value);
                if(i.hasNext()){
                    sb.append(",");
                }
            }
            return sb.toString();
        }
        if (scaleKey.equals(LrcServiceConstants.RESULT_SCALE_KEY_CREDIT_REMEDIAL)) {
            StringBuilder sb = new StringBuilder();
            sb.append("kuali.result.group.credit.remedial.multiple.");
            for (Iterator<String> i = values.iterator();i.hasNext();) {
                String value = i.next();
                sb.append(value);
                if(i.hasNext()){
                    sb.append(",");
                }
            }
            return sb.toString();
        }
        throw new InvalidParameterException("unknown/unhandled credit type scale key " + scaleKey);
    }

    /** 
     * Calculate the result values group key for the range credit value
     * @param values list of credit values
     * @return readable name of multiple credits
     * @throws InvalidParameterException 
     */
    protected String calcMultipleCreditRvgName(List<String> values)
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

    protected String calcMultipleCreditRvgDescr(List<String> values)
            throws InvalidParameterException {
        StringBuilder sb = new StringBuilder();
        String comma = "";
        for (String value : values) {
            sb.append(comma);
            sb.append(value);
            comma = ", ";
        }
        sb.append(" Academic Credits");
        return sb.toString();
    }
    /**
     * Calculate the value keys to use that matches the specified value
     * @param creditValues list of credit values
     * @param scaleKey scale key
     * @return all value keys that match the values and scale
     * @throws InvalidParameterException 
     */
    protected List<String> calcCreditValueKeys(List<String> creditValues,
            String scaleKey)
            throws InvalidParameterException {
        List<String> list = new ArrayList<String>();
        for (String value : creditValues) {
            list.add(this.calcCreditValueKey(value, scaleKey));
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
        String rvgKey = calcMultipleCreditRvgKey(creditValues, scaleKey);
        List<String> valueKeys = this.calcCreditValueKeys(creditValues, scaleKey);
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

        // find/create values first
        findCreateResultValues(valueKeys, creditValues, scaleKey, contextInfo);

        // not found so create it 
        ResultValuesGroupInfo rvg = new ResultValuesGroupInfo();
        rvg.setKey(rvgKey);
        rvg.setTypeKey(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_MULTIPLE);
        rvg.setStateKey(LrcServiceConstants.RESULT_GROUPS_STATE_APPROVED);
        rvg.setName(calcMultipleCreditRvgName(creditValues));
        rvg.setDescr(new RichTextInfo());
        rvg.getDescr().setFormatted(calcMultipleCreditRvgDescr(creditValues));
        rvg.getDescr().setPlain(rvg.getDescr().getFormatted());
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
     * Looks up each result value and if it does not exist, it makes it
     * @param valueKeys value key
     * @param creditValues tis list must match the order of the value keys
     * @param scaleKey scale key
     * @param contextInfo context
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws PermissionDeniedException
     * @throws OperationFailedException
     */
    private void findCreateResultValues(List<String> valueKeys, List<String> creditValues, String scaleKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {
        int i = 0;
        for (String valueKey : valueKeys) {
            String creditValue = creditValues.get(i);
            i++;
            findCreateResultValue(valueKey, creditValue, scaleKey, contextInfo);
        }
    }

    /**
     * Ensures that the result values exist
     * @param valueKey value key
     * @param creditValue actual value
     * @param scaleKey scale key
     * @param contextInfo context
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws PermissionDeniedException
     * @throws OperationFailedException
     */
    private void findCreateResultValue(String valueKey, String creditValue, String scaleKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {
        try {
            getLrcService().getResultValue(valueKey, contextInfo);
        } catch (DoesNotExistException ex) {
            ResultValueInfo value = new ResultValueInfo();
            value.setKey(valueKey);
            value.setTypeKey(LrcServiceConstants.RESULT_VALUE_TYPE_KEY_VALUE);
            value.setStateKey(LrcServiceConstants.RESULT_VALUE_STATE_APPROVED);
            value.setName(creditValue);
            value.setValue(creditValue);
            value.setNumericValue(creditValue);
            value.setResultScaleKey(scaleKey);
            value.setEffectiveDate(new Date());
            try {
                getLrcService().createResultValue(value.getResultScaleKey(), value.getTypeKey(), value, contextInfo);
            } catch (AlreadyExistsException ex1) {
                throw new OperationFailedException("unexpected", ex);
            } catch (DataValidationErrorException ex1) {
                throw new OperationFailedException("unexpected", ex);
            } catch (DoesNotExistException ex1) {
                throw new OperationFailedException("unexpected", ex);
            }
        }
    }

    /**
     * Calculate key to use for the result value
     * @param resultValue the value of the result
     * @param scaleKey key used for getting the proper scale.
     * @return the calculated value
     */
    protected String calcResultValueKey(String resultValue,
            String scaleKey) {
        StringBuilder sb = new StringBuilder();
        sb.append(scaleKey.replace(".scale.", ".value."));
        sb.append(".");
        if(resultValue.contains(sb.toString())){
            return resultValue;
        }
        sb.append(resultValue);
        return sb.toString();
    }

    /**
     * Calculate value to use for the result value from the key
     * @param resultValueKey the value key
     * @param scaleKey key used for getting the proper scale.
     * @return the calculated value
     */
    private String calcResultValueFromKeyAndScale(String resultValueKey,
                                        String scaleKey) {
        StringBuilder sb = new StringBuilder();
        sb.append(scaleKey.replace(".scale.", ".value."));
        sb.append(".");
        return resultValueKey.replace(sb.toString(), "");
    }

    @Override
    public ResultValueInfo getCreateResultValueForScale(String resultValue,
            String scaleKey,
            ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        String resultValueKey = this.calcResultValueKey(resultValue, scaleKey);
        if(resultValue.equals(resultValueKey)){
            resultValue = calcResultValueFromKeyAndScale(resultValue, scaleKey);
        }
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
        value.setDescr(new RichTextInfo(resultValue,resultValue));
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

    public LRCService getLrcService() {
        if(lrcService == null){
            lrcService = GlobalResourceLoader.getService(new QName(LrcServiceConstants.NAMESPACE,
                    "LearningResultService"));
        }
        return lrcService;
    }

    public void setLrcService(LRCService lrcService) {
        this.lrcService = lrcService;
    }

}
