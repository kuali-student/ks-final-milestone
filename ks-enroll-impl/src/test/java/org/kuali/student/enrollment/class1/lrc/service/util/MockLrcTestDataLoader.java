/**
 * Copyright 2012 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * Created by mharmath on 12/20/12
 */
package org.kuali.student.enrollment.class1.lrc.service.util;

import org.kuali.student.common.test.mock.data.AbstractMockServicesAwareDataLoader;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.lum.lrc.dto.ResultValueRangeInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.lrc.infc.ResultValueRange;
import org.kuali.student.r2.lum.lrc.service.LRCService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class MockLrcTestDataLoader  {

    private LRCService lrcService;
    private static String principalId = MockLrcTestDataLoader.class.getSimpleName();

    public LRCService getLrcService() {
        return lrcService;
    }

    public void setLrcService(LRCService lrcService) {
        this.lrcService = lrcService;
    }

    public MockLrcTestDataLoader(LRCService lrcService) {
        this.lrcService = lrcService;
    }

    public void loadData() {
        loadResultValuesGroupInfo("kuali.creditType.credit.degree.1.0", "kuali.result.values.group.type.fixed", "kuali.result.values.group.state.approved", "1 Credit", "1 Academic Credit", "kuali.result.scale.credit.degree", "1", "1");
        loadResultValuesGroupInfo("kuali.creditType.credit.degree.2.0", "kuali.result.values.group.type.fixed", "kuali.result.values.group.state.approved", "1 Credit", "1 Academic Credit", "kuali.result.scale.credit.degree", "1", "1");
    }

    public void loadResultValuesGroupInfo(String id, String type, String state, String name, String description, String resultScaleKey, String maxValue, String minValue) {

        ResultValuesGroupInfo info = new ResultValuesGroupInfo();
        ResultValueRangeInfo resultValueRange = new ResultValueRangeInfo();
        ContextInfo context = new ContextInfo();
        context.setPrincipalId(principalId);
        context.setCurrentDate(new Date());
        info.setKey(id);
        info.setTypeKey(type);// use id for code
        info.setStateKey(state);
        info.setName(name);
        info.setDescr(new RichTextHelper().fromPlain(description));
        info.setResultScaleKey(resultScaleKey);
        resultValueRange.setMaxValue(maxValue);
        resultValueRange.setMinValue(minValue);
        info.setResultValueRange(resultValueRange);
        boolean dataExists = true;
        try {
            lrcService.deleteResultValuesGroup(id,context);
//            lrcService.getResultValuesGroup(id,context);
        } catch (Exception e) {
            dataExists = true;
        }
        try {
            if (dataExists) {
                lrcService.createResultValuesGroup(resultScaleKey, id, info, context);
            }
        } catch (Exception e) {
            throw new RuntimeException("error assigning to services: "+e);
        }
    }

}
