/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.r2.common.krms.data;

import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.student.common.test.mock.data.AbstractMockServicesAwareDataLoader;
import org.kuali.student.core.constants.GesServiceConstants;
import org.kuali.student.core.ges.dto.ParameterInfo;
import org.kuali.student.core.ges.dto.ValueInfo;
import org.kuali.student.core.ges.infc.GesValueTypeEnum;
import org.kuali.student.core.ges.service.GesService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.core.constants.AtpServiceConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author nwright
 */
public class KRMSGesServiceDataLoader extends AbstractMockServicesAwareDataLoader {

    private GesService gesService;

    @Override
    protected void initializeData() throws Exception {
        ContextInfo contextInfo = new ContextInfo();
        contextInfo.setPrincipalId("Test-Initializer");

        // parameters
        _createParam(GesServiceConstants.PARAMETER_KEY_MAX_REPEATABLE, "Max Repeatable",
                "Maximum number of times that a student can repeat a course",
                GesValueTypeEnum.KUALI_DECIMAL, contextInfo);

        // values
        _createValue(GesServiceConstants.PARAMETER_KEY_MAX_REPEATABLE, 1, 2,
                "kuali.population.student.key.everyone", AtpServiceConstants.ATP_FALL_TYPE_KEY, contextInfo);
    }

    private ParameterInfo _createParam(String key, String name, String descr, GesValueTypeEnum gesValueTypeEnum, ContextInfo context) {

        ParameterInfo info = new ParameterInfo();
        info.setKey(key);
        info.setName(name);
        info.setDescr(RichTextHelper.buildRichTextInfo(descr, descr));
        info.setGesGesValueTypeEnum(gesValueTypeEnum);
        info.setTypeKey(GesServiceConstants.GES_PARAMETER_TYPE_KEY);
        info.setStateKey(GesServiceConstants.GES_PARAMETER_ACTIVE_STATE_KEY);
        info.setRequireUniquePriorities(true);
        try {
            info = gesService.createParameter(info.getKey(), info.getTypeKey(), info, context);
        } catch (Exception ex) {
            throw new RuntimeException("unexpected", ex);
        }
        return info;
    }

    private ValueInfo _createValue(String paramKey,int priority, float value, String populationId,
                                   String atpTypeKeys,
                                   ContextInfo context) {
        ValueInfo info = new ValueInfo();
        info.setDecimalValue(new KualiDecimal(value));
        return _createValue(paramKey, priority, populationId, atpTypeKeys, context, info);
    }

    private ValueInfo _createValue(String paramKey,int priority, String populationId,
                                   String atpTypeKeys,
                                   ContextInfo context, ValueInfo info) {
        info.setParameterKey(paramKey);
        info.setTypeKey(GesServiceConstants.GES_VALUE_TYPE_KEY);
        info.setStateKey(GesServiceConstants.GES_VALUE_ACTIVE_STATE_KEY);
        info.setPriority(priority);
        info.setPopulationId(populationId);
        info.setAtpTypeKeys(this._splitIt(atpTypeKeys));
        try {
            info = gesService.createValue(info.getTypeKey(), paramKey, info, context);
        } catch (Exception ex) {
            throw new RuntimeException("unexpected", ex);
        }
        return info;
    }

    private List<String> _splitIt(String str) {
        if (str.trim().isEmpty()) {
            return new ArrayList<>();
        }
        String[] strs = str.split(",");
        for (int i = 0; i < strs.length; i++) {
            strs[i] = strs[i].trim();
        }
        return Arrays.asList(strs);
    }

    public void setGesService(GesService gesService) {
        this.gesService = gesService;
    }
}
