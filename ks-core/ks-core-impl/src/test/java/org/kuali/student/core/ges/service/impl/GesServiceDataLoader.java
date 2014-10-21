package org.kuali.student.core.ges.service.impl;


import org.kuali.student.common.test.mock.data.AbstractMockServicesAwareDataLoader;
import org.kuali.student.core.constants.GesServiceConstants;
import org.kuali.student.core.ges.dto.ParameterInfo;
import org.kuali.student.core.ges.dto.ValueInfo;
import org.kuali.student.core.ges.infc.GesValueTypeEnum;
import org.kuali.student.core.ges.service.GesService;
import org.kuali.student.core.population.service.impl.PopulationServiceDataLoader;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.population.dto.PopulationInfo;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GesServiceDataLoader extends AbstractMockServicesAwareDataLoader {

    public static final String PARAM_KEY_MAX_CREDITS = "max.credits";
    public static final String PARAM_KEY_MIN_CREDITS_REQUIRED_FOR_PROGRAM = "min.credits.required.for.program";

    @Resource(name = "gesServiceImpl")
    private GesService gesService;
    @Resource
    private PopulationServiceDataLoader popDataLoader;
    @Resource
    private AtpService atpService;

    private ParameterInfo maxCreditsParameter;
    private ParameterInfo minCreditsForProgramParameter;

    private AtpInfo fallAtp;
    private AtpInfo springAtp;

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


    public AtpInfo getFallAtp() {
        return fallAtp;
    }

    public void setFallAtp(AtpInfo fallAtp) {
        this.fallAtp = fallAtp;
    }

    public AtpInfo getSpringAtp() {
        return springAtp;
    }

    public void setSpringAtp(AtpInfo springAtp) {
        this.springAtp = springAtp;
    }

    @Override
    protected void initializeData() throws Exception {
        createAtps();
        createPopulations();
        createParameters();
        createValues();
    }

    private void createParameters() throws PermissionDeniedException, DataValidationErrorException, InvalidParameterException, ReadOnlyException,
            OperationFailedException, MissingParameterException, DoesNotExistException {
        ParameterInfo param = generateParameter(GesServiceConstants.GES_PARAMETER_TYPE_KEY, GesServiceConstants.GES_PARAMETER_ACTIVE_STATE_KEY,
                GesValueTypeEnum.NUMERIC, PARAM_KEY_MAX_CREDITS, true);
        maxCreditsParameter = gesService.createParameter(param.getKey(), param.getTypeKey(), param, context);

        param = generateParameter(GesServiceConstants.GES_PARAMETER_TYPE_KEY, GesServiceConstants.GES_PARAMETER_ACTIVE_STATE_KEY,
                GesValueTypeEnum.NUMERIC, PARAM_KEY_MIN_CREDITS_REQUIRED_FOR_PROGRAM, true);
        minCreditsForProgramParameter = gesService.createParameter(param.getKey(), param.getTypeKey(), param, context);
    }


    private void createValues() throws PermissionDeniedException, DataValidationErrorException, InvalidParameterException, ReadOnlyException,
            OperationFailedException, MissingParameterException, DoesNotExistException {
        //create max.credits value
        String atpTypeKey = AtpServiceConstants.ATP_FALL_TYPE_KEY;
        ValueInfo info = generateValue(GesServiceConstants.GES_VALUE_ACTIVE_STATE_KEY, maxCreditsParameter.getKey(), DateFormatters.DEFAULT_DATE_FORMATTER.parse("2010-06-12"),
                DateFormatters.DEFAULT_DATE_FORMATTER.parse("2050-01-01"), popDataLoader.getUndergraduteStudentPopulationId(),atpTypeKey , null, 3);
        info.setNumericValue(20L);
        gesService.createValue(info.getTypeKey(), info.getParameterKey(), info, context);

        info = generateValue(GesServiceConstants.GES_VALUE_ACTIVE_STATE_KEY, maxCreditsParameter.getKey(), DateFormatters.DEFAULT_DATE_FORMATTER.parse("2010-06-12"),
                DateFormatters.DEFAULT_DATE_FORMATTER.parse("2050-01-01"), popDataLoader.getFirstYearStudentPopulationId(), atpTypeKey, null, 1);
        info.setNumericValue(15L);
        gesService.createValue(info.getTypeKey(), info.getParameterKey(), info, context);

        atpTypeKey = AtpServiceConstants.ATP_SPRING_TYPE_KEY;
        info = generateValue( GesServiceConstants.GES_VALUE_ACTIVE_STATE_KEY, maxCreditsParameter.getKey(), DateFormatters.DEFAULT_DATE_FORMATTER.parse("2010-06-12"),
                DateFormatters.DEFAULT_DATE_FORMATTER.parse("2050-01-01"), popDataLoader.getFirstYearStudentPopulationId(), atpTypeKey, null, 2);
        info.setNumericValue(10L);
        gesService.createValue(info.getTypeKey(), info.getParameterKey(), info, context);

        //create min.credits.required.for.program values
        info = generateValue(GesServiceConstants.GES_VALUE_ACTIVE_STATE_KEY, minCreditsForProgramParameter.getKey(), DateFormatters.DEFAULT_DATE_FORMATTER.parse("2010-06-12"),
                DateFormatters.DEFAULT_DATE_FORMATTER.parse("2050-01-01"), popDataLoader.getStudentPopulationId(), null, null, 1);
        info.setNumericValue(122L);
        gesService.createValue(info.getTypeKey(), info.getParameterKey(), info, context);
    }


    private void createPopulations() throws Exception {
        popDataLoader.initializeData();
    }

    public PopulationInfo generatePopulation(String typeKey, String stateKey, String name) {
        PopulationInfo populationInfo = new PopulationInfo();
        populationInfo.setTypeKey(typeKey);
        populationInfo.setSupportsGetMembers(true);
        populationInfo.setDescr(RichTextHelper.buildRichTextInfo(name, name + " formatted"));
        populationInfo.setName(name);
        populationInfo.setStateKey(stateKey);

        return populationInfo;
    }

    public ValueInfo generateValue(String stateKey, String parameterKey, Date effDate, Date expDate,
                                    String populationId, String atpTypeKey, String ruleId, Integer priority) {
        ValueInfo info = new ValueInfo();
        info.setTypeKey(GesServiceConstants.GES_VALUE_TYPE_KEY);
        info.setStateKey(stateKey);
        info.setEffectiveDate(effDate);
        info.setExpirationDate(expDate);
        info.setParameterKey(parameterKey);
        info.setPopulationId(populationId);
        info.setAtpTypeKey(atpTypeKey);
        info.setPriority(priority);
        return info;
    }

    public ParameterInfo generateParameter(String typeKey, String stateKey, GesValueTypeEnum gesValueTypeEnum, String key, Boolean requireUniquePriorities) {
        ParameterInfo info = new ParameterInfo();
        info.setKey(key);
        info.setTypeKey(typeKey);
        info.setGesValueTypeEnum(gesValueTypeEnum);
        info.setStateKey(stateKey);
        info.setRequireUniquePriorities(requireUniquePriorities);

        return info;
    }
    private void createAtps() throws PermissionDeniedException, OperationFailedException, InvalidParameterException, ReadOnlyException, MissingParameterException, DataValidationErrorException {
        AtpInfo atpInfo = generateAtp(AtpServiceConstants.ATP_FALL_TYPE_KEY, AtpServiceConstants.ATP_OFFICIAL_STATE_KEY,"6","fallcode", DateFormatters.DEFAULT_DATE_FORMATTER.parse("2010-05-12"), DateFormatters.DEFAULT_DATE_FORMATTER.parse("2100-05-12"));
        fallAtp = atpService.createAtp(atpInfo.getTypeKey(),atpInfo,context);


        atpInfo = generateAtp(AtpServiceConstants.ATP_SPRING_TYPE_KEY, AtpServiceConstants.ATP_OFFICIAL_STATE_KEY,"5","springcode", DateFormatters.DEFAULT_DATE_FORMATTER.parse("2010-06-12"), DateFormatters.DEFAULT_DATE_FORMATTER.parse("2100-06-12"));
        springAtp = atpService.createAtp(atpInfo.getTypeKey(),atpInfo,context);
    }

    public AtpInfo generateAtp(String typeKey, String stateKey , String orgId, String code, Date startDate, Date endDate){
        AtpInfo info = new AtpInfo();
        info.setTypeKey(typeKey);
        info.setStateKey(stateKey);
        info.setName("name: " + typeKey);
        info.setDescr((RichTextHelper.buildRichTextInfo("name: " + typeKey, "name: " + typeKey + " formatted")));
        info.setAdminOrgId(orgId);
        info.setCode(code);
        info.setEndDate(startDate);
        info.setStartDate(endDate);
        return info;

    }
}
