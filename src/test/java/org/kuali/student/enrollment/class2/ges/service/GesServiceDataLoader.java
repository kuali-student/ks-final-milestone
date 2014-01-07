package org.kuali.student.enrollment.class2.ges.service;


import org.kuali.student.common.test.AbstractMockServicesAwareDataLoader;
import org.kuali.student.enrollment.class2.population.PopulationServiceDataLoader;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.core.ges.dto.ParameterInfo;
import org.kuali.student.core.ges.dto.ValueInfo;
import org.kuali.student.core.ges.service.GesService;
import org.kuali.student.r2.core.population.dto.PopulationInfo;

import javax.annotation.Resource;
import java.util.Date;

public class GesServiceDataLoader extends AbstractMockServicesAwareDataLoader {

    public static final String PARAMETER_TYPE = "kuali.org.ges.parameter";

    public static final String STATE_ACTIVE = "kuali.org.ges.state.active";

    public static final String VALUE_TYPE_STRING = "kuali.org.ges.value.string";
    public static final String VALUE_TYPE_BOOLEAN = "kuali.org.ges.value.boolean";
    public static final String VALUE_TYPE_DATE = "kuali.org.ges.value.date";
    public static final String VALUE_TYPE_NUMERIC = "kuali.org.ges.value.numeric";

    public static final String PARAM_KEY_MAX_CREDITS = "max.credits";
    public static final String PARAM_KEY_MIN_CREDITS_REQUIRED_FOR_PROGRAM = "min.credits.required.for.program";

    @Resource
    private GesService gesService;
    @Resource
    private PopulationServiceDataLoader popDataLoader;

    private ParameterInfo maxCreditsParameter;
    private ParameterInfo minCreditsForProgramParameter;

    public ParameterInfo getMaxCreditsParameter() {
        return maxCreditsParameter;
    }

    public void setMaxCreditsParameter(ParameterInfo maxCreditsParameter) {
        this.maxCreditsParameter = maxCreditsParameter;
    }

    public ParameterInfo getMinCreditsForProgramParameter() {
        return minCreditsForProgramParameter;
    }

    public void setMinCreditsForProgramParameter(ParameterInfo minCreditsForProgramParameter) {
        this.minCreditsForProgramParameter = minCreditsForProgramParameter;
    }

    @Override
    protected void initializeData() throws Exception {
        createPopulations();
        createParameters();
        createValues();
    }

    private void createParameters() throws PermissionDeniedException, DataValidationErrorException, InvalidParameterException, ReadOnlyException,
            OperationFailedException, MissingParameterException, DoesNotExistException {
        ParameterInfo param = generateParameter(PARAMETER_TYPE, STATE_ACTIVE, VALUE_TYPE_NUMERIC, PARAM_KEY_MAX_CREDITS, true);
        maxCreditsParameter = gesService.createParameter(param.getValueTypeKey(), param.getTypeKey(), param.getKey(), param, context);

        param = generateParameter(PARAMETER_TYPE, STATE_ACTIVE, VALUE_TYPE_NUMERIC, PARAM_KEY_MIN_CREDITS_REQUIRED_FOR_PROGRAM, true);
        minCreditsForProgramParameter = gesService.createParameter(param.getValueTypeKey(), param.getTypeKey(), param.getKey(), param, context);
    }


    private void createValues() throws PermissionDeniedException, DataValidationErrorException, InvalidParameterException, ReadOnlyException,
            OperationFailedException, MissingParameterException, DoesNotExistException {
        //create max.credits value
        ValueInfo info = generateValue(VALUE_TYPE_NUMERIC, STATE_ACTIVE, maxCreditsParameter.getId(), DateFormatters.DEFAULT_DATE_FORMATTER.parse("2010-06-12"),
                DateFormatters.DEFAULT_DATE_FORMATTER.parse("2050-01-01"), popDataLoader.getUndergraduteStudentPopulationId(), AtpServiceConstants.ATP_FALL_TYPE_KEY, null, 3);
        info.setNumericValue(20L);
        gesService.createValue(info.getTypeKey(), info.getParameterId(), info, context);

        info = generateValue(VALUE_TYPE_NUMERIC, STATE_ACTIVE, maxCreditsParameter.getId(), DateFormatters.DEFAULT_DATE_FORMATTER.parse("2010-06-12"),
                DateFormatters.DEFAULT_DATE_FORMATTER.parse("2050-01-01"), popDataLoader.getFirstYearStudentPopulationId(), AtpServiceConstants.ATP_FALL_TYPE_KEY, null, 1);
        info.setNumericValue(15L);
        gesService.createValue(info.getTypeKey(), info.getParameterId(), info, context);

        info = generateValue(VALUE_TYPE_NUMERIC, STATE_ACTIVE, maxCreditsParameter.getId(), DateFormatters.DEFAULT_DATE_FORMATTER.parse("2010-06-12"),
                DateFormatters.DEFAULT_DATE_FORMATTER.parse("2050-01-01"), popDataLoader.getFirstYearStudentPopulationId(), AtpServiceConstants.ATP_SPRING_TYPE_KEY, null, 2);
        info.setNumericValue(10L);
        gesService.createValue(info.getTypeKey(), info.getParameterId(), info, context);

        //create min.credits.required.for.program values
        info = generateValue(VALUE_TYPE_NUMERIC, STATE_ACTIVE, minCreditsForProgramParameter.getId(), DateFormatters.DEFAULT_DATE_FORMATTER.parse("2010-06-12"),
                DateFormatters.DEFAULT_DATE_FORMATTER.parse("2050-01-01"), popDataLoader.getStudentPopulationId(), null, null, 1);
        info.setNumericValue(122L);
        gesService.createValue(info.getTypeKey(), info.getParameterId(), info, context);
    }


    private void createPopulations() throws Exception {
        popDataLoader.initializeData();
    }

    public PopulationInfo generatePopulation(String typeKey, String stateKey, String name) {
        PopulationInfo populationInfo = new PopulationInfo();
        populationInfo.setTypeKey(typeKey);
        populationInfo.setSupportsGetMembers(true);
        populationInfo.setDescr(RichTextHelper.buildRichTextInfo(name , name + " formatted"));
        populationInfo.setName(name);
        populationInfo.setStateKey(stateKey);

        return populationInfo;
    }

    public ValueInfo generateValue(String typeKey, String stateKey, String parameterId, Date effDate, Date expDate,
                                    String populationId, String atpTypeKey, String ruleId, Integer priority) {
        ValueInfo info = new ValueInfo();
        info.setTypeKey(typeKey);
        info.setStateKey(stateKey);
        info.setEffectiveDate(effDate);
        info.setExpirationDate(expDate);
        info.setParameterId(parameterId);
        info.setPopulationId(populationId);
        info.setAtpTypeKey(atpTypeKey);
        info.setPriority(priority);
        return info;
    }

    public ParameterInfo generateParameter(String typeKey, String stateKey, String valueTypeKey, String key, Boolean requireUniquePriorities) {
        ParameterInfo info = new ParameterInfo();
        info.setKey(key);
        info.setValueTypeKey(valueTypeKey);
        info.setTypeKey(typeKey);
        info.setStateKey(stateKey);
        info.setRequireUniquePriorities(requireUniquePriorities);

        return info;
    }
}
