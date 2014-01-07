/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.r2.core.process;

import java.util.Arrays;
import java.util.List;
import org.kuali.student.core.constants.GesServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.core.ges.dto.ParameterInfo;
import org.kuali.student.core.ges.dto.ValueInfo;
import org.kuali.student.core.ges.service.GesService;
import org.kuali.student.core.ges.service.GesServiceDecorator;

/**
 *
 * @author nwright
 */
public class ProcessPocGesServiceDecorator extends GesServiceDecorator {

    public ProcessPocGesServiceDecorator() {
    }

    public ProcessPocGesServiceDecorator(GesService nextDecorator) {
        super();
        this.setNextDecorator(nextDecorator);
        init();
    }

    public void init() {
        ContextInfo contextInfo = new ContextInfo();
        contextInfo.setPrincipalId("POC-Initializer");
        // parameters
        _createParam(GesServiceConstants.PARAMETER_KEY_CREDIT_MINIMUM, "Credit Minimum",
                "Minimum number of credits that a student can register for in a given term",
                GesServiceConstants.GES_VALUE_TYPE_KEY_KUALIDECIMAL, contextInfo);
        _createParam(GesServiceConstants.PARAMETER_KEY_CREDIT_LIMIT, "Credit Limit",
                "Maximum number of credits that a student can register for in a given term",
                GesServiceConstants.GES_VALUE_TYPE_KEY_KUALIDECIMAL, contextInfo);
        _createParam(GesServiceConstants.PARAMETER_KEY_LOAD_CALCULATION_FOR_CREDIT_CHECKS, "Load Calculation for Credit Checks",
                "The Load Calculation to use for credit limit checks", GesServiceConstants.GES_VALUE_TYPE_KEY_STRING, contextInfo);

        // values
        _createValue(GesServiceConstants.PARAMETER_KEY_CREDIT_LIMIT, 1, "6", "kuali.population.everyone",
                "kuali.atp.type.Summer", contextInfo);
        _createValue(GesServiceConstants.PARAMETER_KEY_CREDIT_LIMIT, 2, "9", "kuali.population.everyone",
                "kuali.atp.type.Fall, kuali.atp.type.Spring", contextInfo);
        _createValue(GesServiceConstants.PARAMETER_KEY_CREDIT_LIMIT, 3, "15", "kuali.population.freshman",
                "kuali.atp.type.Fall, kuali.atp.type.Spring", contextInfo);
        _createValue(GesServiceConstants.PARAMETER_KEY_CREDIT_LIMIT, 4, "18", "kuali.population.upperclass",
                "kuali.atp.type.Fall, kuali.atp.type.Spring", contextInfo);
        _createValue(GesServiceConstants.PARAMETER_KEY_CREDIT_LIMIT, 5, "18", "kuali.population.law.school.students", "",
                contextInfo);
        _createValue(GesServiceConstants.PARAMETER_KEY_CREDIT_LIMIT, 6, "17", "kuali.population.graduate", "", contextInfo);

        _createValue(GesServiceConstants.PARAMETER_KEY_CREDIT_MINIMUM, 1, "12", "kuali.population.athletes",
                "kuali.atp.type.Fall, kuali.atp.type.Spring", contextInfo);
        _createValue(GesServiceConstants.PARAMETER_KEY_CREDIT_MINIMUM, 2, "9", "kuali.population.fin.aid.recipients",
                "kuali.atp.type.Fall, kuali.atp.type.Spring", contextInfo);
        _createValue(GesServiceConstants.PARAMETER_KEY_CREDIT_MINIMUM, 3, "1", "kuali.population.everyone",
                "kuali.atp.type.Fall, kuali.atp.type.Spring", contextInfo);
        _createValue(GesServiceConstants.PARAMETER_KEY_CREDIT_MINIMUM, 4, "0", "kuali.population.everyone",
                "kuali.atp.type.Summer", contextInfo);

        _createValue(GesServiceConstants.PARAMETER_KEY_LOAD_CALCULATION_FOR_CREDIT_CHECKS, 1,
                "kuali.academic.record.calculation.type.load.credit.decimal", "kuali.population.everyone", "", contextInfo);

    }

    private ParameterInfo _createParam(String key, String name, String descr, String valueTypeKey, ContextInfo context) {

        ParameterInfo info = new ParameterInfo();
        info.setKey(key);
        info.setValueTypeKey(valueTypeKey);
        info.setTypeKey(GesServiceConstants.GES_PARAMETER_TYPE_KEY);
        info.setStateKey(GesServiceConstants.GES_PARAMETER_ACTIVE_STATE_KEY);
        info.setRequireUniquePriorities(true);
        try {
            info = this.createParameter(info.getValueTypeKey(), info.getTypeKey(), info.getKey(), info, context);
        } catch (Exception ex) {
            throw new RuntimeException("unexpected", ex);
        }
        return info;
    }

    private ValueInfo _createValue(String paramKey, int priority, String value, String populationId, String atpKeys,
            ContextInfo context) {
        ValueInfo info = new ValueInfo();
        info.setParameterId(paramKey);
        info.setTypeKey(GesServiceConstants.GES_VALUE_TYPE_KEY_KUALIDECIMAL);
        info.setStateKey(GesServiceConstants.GES_VALUE_ACTIVE_STATE_KEY);
        info.setPriority(priority);
        info.setStringValue(_nullIt(value));
        info.setPopulationId(populationId);
        // TODO: turn this into a list once the GES service gets fixed
        info.setAtpTypeKey(atpKeys);
        try {
            info = this.createValue(info.getTypeKey(), paramKey, info, context);
        } catch (Exception ex) {
            throw new RuntimeException("unexpected", ex);
        }
        return info;
    }

    private List<String> _splitIt(String str) {
        if (str.trim().isEmpty()) {
            return null;
        }
        String[] strs = str.split(",");
        for (int i = 0; i < strs.length; i++) {
            strs[i] = strs[i].trim();
        }
        List<String> list = Arrays.asList(strs);
        return list;
    }

    private String _nullIt(String str) {
        if (str.trim().isEmpty()) {
            return null;
        }
        return str;
    }
}
