/*
 * Copyright 2014 The Kuali Foundation
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
package org.kuali.student.enrollment.academicrecord.service;

import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.student.core.constants.GesServiceConstants;
import org.kuali.student.core.ges.dto.ParameterInfo;
import org.kuali.student.core.ges.dto.ValueInfo;
import org.kuali.student.core.ges.service.GesService;
import org.kuali.student.core.ges.service.ValueType;
import org.kuali.student.core.ges.service.GesServiceDecorator;
import org.kuali.student.enrollment.class2.academicrecord.service.impl.ClassStanding;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.RichTextHelper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Kuali Student Team
 */
public class AcademicRecordIntegrationTestGesServiceDataLoadingDecorator extends GesServiceDecorator {

    public static final String KUALI_POPULATION_EVERYONE = "kuali.population.everyone";

    public static final KualiDecimal FRESHMAN_CLASS_STANDING_THRESHOLD = new KualiDecimal(0);
    public static final KualiDecimal SOPHOMORE_CLASS_STANDING_THRESHOLD = new KualiDecimal(25);
    public static final KualiDecimal JUNIOR_CLASS_STANDING_THRESHOLD = new KualiDecimal(55);
    public static final KualiDecimal SENIOR_CLASS_STANDING_THRESHOLD = new KualiDecimal(85);

    public AcademicRecordIntegrationTestGesServiceDataLoadingDecorator() {
    }

    public AcademicRecordIntegrationTestGesServiceDataLoadingDecorator(GesService nextDecorator) {
        super();
        this.setNextDecorator(nextDecorator);
        init();
    }

    public void init() {
        ContextInfo contextInfo = new ContextInfo();
        contextInfo.setPrincipalId("Test-Initializer");

        _createParam(GesServiceConstants.PARAMETER_KEY_CLASS_STANDING_CREDIT_THRESHOLDS,
                "Class Standing Credit Threshold",
                "Credit threshold for each class standing population",
                ValueType.KUALI_DECIMAL, contextInfo);
        _createValue(GesServiceConstants.PARAMETER_KEY_CLASS_STANDING_CREDIT_THRESHOLDS,
                1, ClassStanding.FRESHMAN.getDescription(), KUALI_POPULATION_EVERYONE, "", contextInfo);
        _createValue(GesServiceConstants.PARAMETER_KEY_CLASS_STANDING_CREDIT_THRESHOLDS,
                2, ClassStanding.SOPHOMORE.getDescription(), KUALI_POPULATION_EVERYONE, "", contextInfo);
        _createValue(GesServiceConstants.PARAMETER_KEY_CLASS_STANDING_CREDIT_THRESHOLDS,
                3, ClassStanding.JUNIOR.getDescription(), KUALI_POPULATION_EVERYONE, "", contextInfo);
        _createValue(GesServiceConstants.PARAMETER_KEY_CLASS_STANDING_CREDIT_THRESHOLDS,
                4, ClassStanding.SENIOR.getDescription(), KUALI_POPULATION_EVERYONE, "", contextInfo);


        _createParam(ClassStanding.FRESHMAN.getDescription(),
                "Class Standing Freshman Credit Threshold",
                "Credit threshold for freshman class standing",
                ValueType.KUALI_DECIMAL, contextInfo);
        _createValue(ClassStanding.FRESHMAN.getDescription(),
                1, FRESHMAN_CLASS_STANDING_THRESHOLD, KUALI_POPULATION_EVERYONE, "", contextInfo);

        _createParam(ClassStanding.SOPHOMORE.getDescription(),
                "Class Standing Sophomore Credit Threshold",
                "Credit threshold for sophomore class standing",
                ValueType.KUALI_DECIMAL, contextInfo);
        _createValue(ClassStanding.SOPHOMORE.getDescription(),
                2, SOPHOMORE_CLASS_STANDING_THRESHOLD, KUALI_POPULATION_EVERYONE, "", contextInfo);

        _createParam(ClassStanding.JUNIOR.getDescription(),
                "Class Standing Junior Credit Threshold",
                "Credit threshold for junior class standing",
                ValueType.KUALI_DECIMAL, contextInfo);
        _createValue(ClassStanding.JUNIOR.getDescription(),
                3, JUNIOR_CLASS_STANDING_THRESHOLD, KUALI_POPULATION_EVERYONE, "", contextInfo);

        _createParam(ClassStanding.SENIOR.getDescription(),
                "Class Standing Senior Credit Threshold",
                "Credit threshold for senior class standing",
                ValueType.KUALI_DECIMAL, contextInfo);
        _createValue(ClassStanding.SENIOR.getDescription(),
                4, SENIOR_CLASS_STANDING_THRESHOLD, KUALI_POPULATION_EVERYONE, "", contextInfo);
    }

    private ParameterInfo _createParam(String key, String name, String descr, ValueType valueType, ContextInfo context) {

        ParameterInfo info = new ParameterInfo();
        info.setKey(key);
        info.setName(name);
        info.setDescr(RichTextHelper.buildRichTextInfo(descr, descr));
        info.setGesValueType(valueType);
        info.setTypeKey(GesServiceConstants.GES_PARAMETER_TYPE_KEY);
        info.setStateKey(GesServiceConstants.GES_PARAMETER_ACTIVE_STATE_KEY);
        info.setRequireUniquePriorities(true);
        try {
            info = this.createParameter(info.getKey(), info.getTypeKey(), info, context);
        } catch (Exception ex) {
            throw new RuntimeException("unexpected", ex);
        }
        return info;
    }

    private ValueInfo _createValue(String paramKey,int priority, String value, String populationId,
                                   String atpTypeKeys,
                                   ContextInfo context) {
        ValueInfo info = new ValueInfo();
        info.setParameterKey(paramKey);
        info.setTypeKey(GesServiceConstants.GES_VALUE_TYPE_KEY);
        info.setStateKey(GesServiceConstants.GES_VALUE_ACTIVE_STATE_KEY);
        info.setPriority(priority);
        info.setStringValue(_nullIt(value));
        info.setPopulationId(populationId);
        info.setAtpTypeKeys(this._splitIt(atpTypeKeys));
        try {
            info = this.createValue(info.getTypeKey(), paramKey, info, context);
        } catch (Exception ex) {
            throw new RuntimeException("unexpected", ex);
        }
        return info;
    }

    private ValueInfo _createValue(String paramKey,int priority, KualiDecimal value, String populationId,
                                   String atpTypeKeys,
                                   ContextInfo context) {
        ValueInfo info = new ValueInfo();
        info.setParameterKey(paramKey);
        info.setTypeKey(GesServiceConstants.GES_VALUE_TYPE_KEY);
        info.setStateKey(GesServiceConstants.GES_VALUE_ACTIVE_STATE_KEY);
        info.setPriority(priority);
        info.setDecimalValue(value);
        info.setPopulationId(populationId);
        info.setAtpTypeKeys(this._splitIt(atpTypeKeys));
        try {
            info = this.createValue(info.getTypeKey(), paramKey, info, context);
        } catch (Exception ex) {
            throw new RuntimeException("unexpected", ex);
        }
        return info;
    }

    private List<String> _splitIt(String str) {
        if (str.trim().isEmpty()) {
            return Collections.EMPTY_LIST;
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
