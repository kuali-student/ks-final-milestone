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
package org.kuali.student.core.ges.service.decorators;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krms.api.engine.EngineResults;
import org.kuali.rice.krms.api.engine.ResultEvent;
import org.kuali.rice.krms.framework.engine.Proposition;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.core.constants.GesServiceConstants;
import org.kuali.student.core.ges.dto.GesCriteriaInfo;
import org.kuali.student.core.ges.dto.ParameterInfo;
import org.kuali.student.core.ges.dto.ValueInfo;
import org.kuali.student.core.ges.service.GesServiceDecorator;
import org.kuali.student.core.process.evaluator.KRMSEvaluator;
import org.kuali.student.core.process.evaluator.PropositionFactory;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.population.service.PopulationService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GesServiceEvaluationDecorator extends GesServiceDecorator {

    //////////////////////////////
    // DATA VARIABLES
    //////////////////////////////

    private PopulationService populationService;
    private AtpService atpService;
    private PropositionFactory propositionFactory;
    private KRMSEvaluator krmsEvaluator;

    //////////////////////////////
    // GETTERS & SETTERS
    //////////////////////////////


    public PopulationService getPopulationService() {
        return populationService;
    }

    public void setPopulationService(PopulationService populationService) {
        this.populationService = populationService;
    }

    public AtpService getAtpService() {
        return atpService;
    }

    public void setAtpService(AtpService atpService) {
        this.atpService = atpService;
    }

    public PropositionFactory getPropositionFactory() {
        return propositionFactory;
    }

    public void setPropositionFactory(PropositionFactory propositionFactory) {
        this.propositionFactory = propositionFactory;
    }

    public KRMSEvaluator getKrmsEvaluator() {
        return krmsEvaluator;
    }

    public void setKrmsEvaluator(KRMSEvaluator krmsEvaluator) {
        this.krmsEvaluator = krmsEvaluator;
    }

    //////////////////////////
    // FUNCTIONALS
    //////////////////////////

    @Override
    public List<ValueInfo> evaluateValues(String parameterKey, GesCriteriaInfo criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {
        return evaluateValuesOnDate(parameterKey, criteria, pullEvaluationDate(contextInfo), contextInfo);

    }

    @Override
    public List<ValueInfo> evaluateValuesOnDate(String parameterKey, GesCriteriaInfo criteria, Date onDate, ContextInfo contextInfo)
            throws InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {
        List<ValueInfo> resultValues = new ArrayList<ValueInfo>();
        if (isParamActive(pullParameter(parameterKey,contextInfo))) {
            List<ValueInfo> values = getValuesByParameter(parameterKey, contextInfo);
            Collections.sort(values, new ValuePriorityComparator());
            String atpTypeKey = null;
            if (values.size() > 0 && StringUtils.isNotEmpty(criteria.getAtpId())) {
                atpTypeKey = pullAtpTypeKey(criteria.getAtpId(), contextInfo);
            }
            for (ValueInfo value : values) {
                if (isActive(value, onDate) && isValueApplicable(value, criteria, atpTypeKey, onDate, contextInfo)) {
                    resultValues.add(value);
                }
            }
        }
        return resultValues;
    }

    @Override
    public ValueInfo evaluateValue(String parameterKey, GesCriteriaInfo criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            , DoesNotExistException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {
        return evaluateValueOnDate(parameterKey, criteria, pullEvaluationDate(contextInfo), contextInfo);

    }

    @Override
    public ValueInfo evaluateValueOnDate(String parameterKey, GesCriteriaInfo criteria, Date onDate, ContextInfo contextInfo)
            throws InvalidParameterException
            , DoesNotExistException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {
        if (isParamActive(pullParameter(parameterKey,contextInfo))) {
            List<ValueInfo> values = getValuesByParameter(parameterKey, contextInfo);
            Collections.sort(values, new ValuePriorityComparator());
            String atpTypeKey = null;
            if (values.size() > 0 && StringUtils.isNotEmpty(criteria.getAtpId())) {
                atpTypeKey = pullAtpTypeKey(criteria.getAtpId(), contextInfo);
            }
            for (ValueInfo value : values) {
                if (isActive(value, onDate) && isValueApplicable(value, criteria, atpTypeKey, onDate, contextInfo)) {
                    return value;
                }
            }
        }
        throw new DoesNotExistException("There are not any applicable values for the given criteria");
    }

    private Date pullEvaluationDate(ContextInfo contextInfo) {
        Date currentDate = contextInfo.getCurrentDate();
        if (currentDate == null) {
            currentDate = new Date();
        }
        return currentDate;
    }

    private boolean isValueApplicable(ValueInfo value, GesCriteriaInfo criteria, String atpTypeKey, Date onDate, ContextInfo contextInfo)
            throws MissingParameterException, PermissionDeniedException, InvalidParameterException, OperationFailedException {
        return isAtpActive(value.getAtpTypeKeys(), atpTypeKey, onDate, contextInfo) &&
                isInPopulation(value.getPopulationId(), criteria, onDate, contextInfo) &&
                evaluateRule(value.getRuleId(), criteria, onDate, contextInfo);

    }

    private boolean isActive(ValueInfo value, Date date) {
        return value.getStateKey().equals(GesServiceConstants.GES_VALUE_ACTIVE_STATE_KEY) &&
                (value.getEffectiveDate() == null || !date.before(value.getEffectiveDate()) &&
                        (value.getExpirationDate() == null || !date.after(value.getExpirationDate())));
    }

    private boolean isInPopulation(String popId, GesCriteriaInfo criteria, Date date, ContextInfo contextInfo)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException {
        try {
            return StringUtils.isEmpty(popId) || StringUtils.isEmpty(criteria.getPersonId()) ||
                    populationService.isMemberAsOfDate(criteria.getPersonId(), popId, date, contextInfo);
        } catch (DoesNotExistException e) {
            throw new OperationFailedException("Unable to determine if is member of population.", e);
        }
    }

    private boolean isParamActive(ParameterInfo parameterInfo) {
        if (parameterInfo.getStateKey().equals(GesServiceConstants.GES_PARAMETER_ACTIVE_STATE_KEY)){
            return true;
        }
        return false;
    }

    private ParameterInfo pullParameter(String parameterKey,ContextInfo contextInfo) throws OperationFailedException,
            MissingParameterException,
            InvalidParameterException,
            PermissionDeniedException {
        try{
            return getParameter(parameterKey,contextInfo);
        }catch (DoesNotExistException e){
            throw new OperationFailedException("Parameter key is not valid: " + parameterKey ,e);
        }
    }

    private boolean evaluateRule(String ruleId, GesCriteriaInfo criteria, Date date, ContextInfo contextInfo) throws OperationFailedException {
        if (ruleId==null) return true; // if there is no rule id then there is nothing to be evaluated, and it's true.
        Proposition prop = null;
        try {
            propositionFactory.getProposition(ruleId, contextInfo);
        }
        catch (DoesNotExistException e) {
            throw new OperationFailedException("Proposition not found for rule with rule id " + ruleId, e);
        }
        Map<String, Object> executionFacts = new LinkedHashMap<String, Object>();
        executionFacts.put(RulesExecutionConstants.GES_CRITERIA_TERM.getName(), criteria);
        executionFacts.put(RulesExecutionConstants.CONTEXT_INFO_TERM.getName(), contextInfo);
        EngineResults engineResults = krmsEvaluator.evaluateProposition(prop, executionFacts);
        Exception ex = KRMSEvaluator.checkForExceptionDuringExecution(engineResults);
        boolean result;
        if (ex != null) {
            throw new OperationFailedException ("Unexpected exception while executing rules", ex);
        }
        List<ResultEvent> resultEvents = engineResults.getResultsOfType(ResultEvent.PROPOSITION_EVALUATED);
        if(resultEvents.size() == 1) {
            result = resultEvents.get(0).getResult();
        } else {
            throw new OperationFailedException("Unexpected result size for rule results");
        }
        return result;
    }

    private boolean isAtpActive(List<String> atpTypeKeys, String atpTypeKey, Date date, ContextInfo contextInfo) throws MissingParameterException, PermissionDeniedException, InvalidParameterException, OperationFailedException {
        if (atpTypeKeys.isEmpty() || StringUtils.isEmpty(atpTypeKey)) {
            return true;
        }
        for (String key : atpTypeKeys) {

            if (key.contains(atpTypeKey)) {
                return true;
            }
        }
        return false;
    }
    private String pullAtpTypeKey(String atpId, ContextInfo contextInfo) throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException {
        try {
            AtpInfo info = atpService.getAtp(atpId, contextInfo);
            return info.getTypeKey();
        } catch (DoesNotExistException e) {
            throw new OperationFailedException("Invalid atpid " + atpId, e);
        }
    }


    private class ValuePriorityComparator implements Comparator<ValueInfo> {

        @Override
        public int compare(ValueInfo o1, ValueInfo o2) {
            int priorityOne = o1.getPriority() == null ? Integer.MAX_VALUE : o1.getPriority();
            int priorityTwo = o2.getPriority() == null ? Integer.MAX_VALUE : o2.getPriority();

            return priorityOne - priorityTwo;
        }
    }
}
