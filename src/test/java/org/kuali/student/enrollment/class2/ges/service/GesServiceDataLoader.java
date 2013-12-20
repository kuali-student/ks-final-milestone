package org.kuali.student.enrollment.class2.ges.service;


import org.kuali.student.common.test.AbstractMockServicesAwareDataLoader;
import org.kuali.student.poc.rules.population.PopulationPocConstants;
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
import org.kuali.student.r2.core.constants.PopulationServiceConstants;
import org.kuali.student.r2.core.ges.dto.ParameterInfo;
import org.kuali.student.r2.core.ges.dto.ValueInfo;
import org.kuali.student.r2.core.ges.service.GesService;
import org.kuali.student.r2.core.population.dto.PopulationInfo;
import org.kuali.student.r2.core.population.service.PopulationService;

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
    private PopulationService popService;

    @Override
    protected void initializeData() throws Exception {
        createPopulations();
        createParameters();
        createValues();
    }


    private void createParameters() throws PermissionDeniedException, DataValidationErrorException, InvalidParameterException, ReadOnlyException, OperationFailedException, MissingParameterException, DoesNotExistException {
        ParameterInfo info = generateParameter(PARAMETER_TYPE, STATE_ACTIVE, VALUE_TYPE_NUMERIC, PARAM_KEY_MAX_CREDITS, "1");
        gesService.createParameter(info.getValueTypeKey(), info.getTypeKey(), info.getKey(), info, context);

        info = generateParameter(PARAMETER_TYPE, STATE_ACTIVE, VALUE_TYPE_NUMERIC, PARAM_KEY_MIN_CREDITS_REQUIRED_FOR_PROGRAM, "2");
        gesService.createParameter(info.getValueTypeKey(), info.getTypeKey(), info.getKey(), info, context);
    }

    private void createValues() throws PermissionDeniedException, DataValidationErrorException, InvalidParameterException, ReadOnlyException, OperationFailedException, MissingParameterException, DoesNotExistException {
        //create max.credits values
        ValueInfo info = generateValue(VALUE_TYPE_NUMERIC, STATE_ACTIVE, "1", DateFormatters.DEFAULT_DATE_FORMATTER.parse("2010-06-12"), DateFormatters.DEFAULT_DATE_FORMATTER.parse("2050-01-01"),
                "1", AtpServiceConstants.ATP_FALL_TYPE_KEY, null, "1");
        info.setNumericValue(10L);
        gesService.createValue(info.getTypeKey(), info.getParameterId(), info, context);

        info = generateValue(VALUE_TYPE_NUMERIC, STATE_ACTIVE, "1", DateFormatters.DEFAULT_DATE_FORMATTER.parse("2010-06-12"), DateFormatters.DEFAULT_DATE_FORMATTER.parse("2050-01-01"),
                "1", AtpServiceConstants.ATP_SPRING_TYPE_KEY, null, "2");
        info.setNumericValue(15L);
        gesService.createValue(info.getTypeKey(), info.getParameterId(), info, context);

        info = generateValue(VALUE_TYPE_NUMERIC, STATE_ACTIVE, "1", DateFormatters.DEFAULT_DATE_FORMATTER.parse("2010-06-12"), DateFormatters.DEFAULT_DATE_FORMATTER.parse("2050-01-01"),
                "2", AtpServiceConstants.ATP_SPRING_TYPE_KEY, null, "3");
        info.setNumericValue(20L);
        gesService.createValue(info.getTypeKey(), info.getParameterId(), info, context);

        //create min.credits.required.for.program values
        info = generateValue(VALUE_TYPE_NUMERIC, STATE_ACTIVE, "2", DateFormatters.DEFAULT_DATE_FORMATTER.parse("2010-06-12"), DateFormatters.DEFAULT_DATE_FORMATTER.parse("2050-01-01"),
                "1", null, null, "4");
        info.setNumericValue(122L);
        gesService.createValue(info.getTypeKey(), info.getParameterId(), info, context);
        info = generateValue(VALUE_TYPE_NUMERIC, STATE_ACTIVE, "2", DateFormatters.DEFAULT_DATE_FORMATTER.parse("2010-06-12"), DateFormatters.DEFAULT_DATE_FORMATTER.parse("2050-01-01"),
                "3", null, null, "5");
        info.setNumericValue(70L);
        gesService.createValue(info.getTypeKey(), info.getParameterId(), info, context);
    }


    private void createPopulations() throws PermissionDeniedException, OperationFailedException, InvalidParameterException, ReadOnlyException, MissingParameterException, DataValidationErrorException {
        PopulationInfo populationInfo = generatePopulation(PopulationPocConstants.PROGRAM_LEVEL_UNDERGRADUATE, PopulationServiceConstants.POPULATION_ACTIVE_STATE_KEY, "UndergradPop", "1");
        popService.createPopulation(populationInfo, context);

        populationInfo = generatePopulation(PopulationPocConstants.PROGRAM_BACHELOR_OF_SCIENCE, PopulationServiceConstants.POPULATION_ACTIVE_STATE_KEY, "UndergradBS", "2");
        popService.createPopulation(populationInfo, context);

        populationInfo = generatePopulation(PopulationPocConstants.PROGRAM_LEVEL_GRADUATE, PopulationServiceConstants.POPULATION_ACTIVE_STATE_KEY, "Graduate", "3");
        popService.createPopulation(populationInfo, context);


    }

    private void addPersonToPopulation(){
        //Add a person to a given population

    }
    private PopulationInfo generatePopulation(String typeKey, String stateKey, String name, String id) {
        PopulationInfo populationInfo = new PopulationInfo();
        populationInfo.setTypeKey(typeKey);
        populationInfo.setSupportsGetMembers(true);
        populationInfo.setDescr(RichTextHelper.buildRichTextInfo(name + " " + id, name + " " + id + " formatted"));
        populationInfo.setName(name);
        populationInfo.setStateKey(stateKey);
        populationInfo.setId(id);

        return populationInfo;


    }

    private ValueInfo generateValue(String typeKey, String stateKey, String parameterId, Date effDate, Date expDate,
                                    String populationId, String atpTypeKey, String ruleId, String id) {
        ValueInfo info = new ValueInfo();
        info.setTypeKey(typeKey);
        info.setStateKey(stateKey);
        info.setEffectiveDate(effDate);
        info.setExpirationDate(expDate);
        info.setParameterId(parameterId);
        info.setPopulationId(populationId);
        info.setAtpTypeKey(atpTypeKey);
        info.setId(id);

        return info;
    }

    private ParameterInfo generateParameter(String typeKey, String stateKey, String valueTypeKey, String key, String id) {
        ParameterInfo info = new ParameterInfo();
        info.setKey(key);
        info.setValueTypeKey(valueTypeKey);
        info.setTypeKey(typeKey);
        info.setStateKey(stateKey);
        info.setId(id);

        return info;
    }
}