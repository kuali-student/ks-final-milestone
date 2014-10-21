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
package org.kuali.student.lum.lrc.service.util;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValueRangeInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;

import java.util.Date;

/**
 * This class provides mock data to test the LrcService
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
        loadResultValueInfo("kuali.result.value.grade.completed.c", "Completed", "Completed", "kuali.result.scale.grade.completed", "2");
        loadResultValueInfo("kuali.result.value.grade.completed.ip", "In-Progress", "In-Progress", "kuali.result.scale.grade.completed", "1");
        loadResultValueInfo("kuali.result.value.grade.completed.nc", "Not-Completed", "Not-Completed", "kuali.result.scale.grade.completed", "0");

        loadResultValueInfo("kuali.result.value.grade.letter.plus.minus.a+", "A+", "A+", LrcServiceConstants.RESULT_SCALE_KEY_GRADE_LETTER_PLUS_MINUS, "12");
        loadResultValueInfo("kuali.result.value.grade.letter.plus.minus.a", "A", "A", LrcServiceConstants.RESULT_SCALE_KEY_GRADE_LETTER_PLUS_MINUS, "11");
        loadResultValueInfo("kuali.result.value.grade.letter.plus.minus.a-", "A-", "A-", LrcServiceConstants.RESULT_SCALE_KEY_GRADE_LETTER_PLUS_MINUS, "10");
        loadResultValueInfo("kuali.result.value.grade.letter.plus.minus.b+", "B+", "B+", LrcServiceConstants.RESULT_SCALE_KEY_GRADE_LETTER_PLUS_MINUS, "9");
        loadResultValueInfo("kuali.result.value.grade.letter.plus.minus.b", "B", "B", LrcServiceConstants.RESULT_SCALE_KEY_GRADE_LETTER_PLUS_MINUS, "8");
        loadResultValueInfo("kuali.result.value.grade.letter.plus.minus.b-", "B-", "B-", LrcServiceConstants.RESULT_SCALE_KEY_GRADE_LETTER_PLUS_MINUS, "7");
        loadResultValueInfo("kuali.result.value.grade.letter.plus.minus.c+", "C+", "C+", LrcServiceConstants.RESULT_SCALE_KEY_GRADE_LETTER_PLUS_MINUS, "6");
        loadResultValueInfo("kuali.result.value.grade.letter.plus.minus.c", "C", "C", LrcServiceConstants.RESULT_SCALE_KEY_GRADE_LETTER_PLUS_MINUS, "5");
        loadResultValueInfo("kuali.result.value.grade.letter.plus.minus.c-", "C-", "C-", LrcServiceConstants.RESULT_SCALE_KEY_GRADE_LETTER_PLUS_MINUS, "4");
        loadResultValueInfo("kuali.result.value.grade.letter.plus.minus.d+", "D+", "D+", LrcServiceConstants.RESULT_SCALE_KEY_GRADE_LETTER_PLUS_MINUS, "3");
        loadResultValueInfo("kuali.result.value.grade.letter.plus.minus.d", "D", "D", LrcServiceConstants.RESULT_SCALE_KEY_GRADE_LETTER_PLUS_MINUS, "2");
        loadResultValueInfo("kuali.result.value.grade.letter.plus.minus.d-", "D-", "D-", LrcServiceConstants.RESULT_SCALE_KEY_GRADE_LETTER_PLUS_MINUS, "1");
        loadResultValueInfo("kuali.result.value.grade.letter.plus.minus.f", "F", "F", LrcServiceConstants.RESULT_SCALE_KEY_GRADE_LETTER_PLUS_MINUS, "0");

        loadResultValuesGroupInfo(LrcServiceConstants.RESULT_GROUP_KEY_KUALI_CREDITTYPE_CREDIT_1_0, LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_FIXED, LrcServiceConstants.RESULT_GROUPS_STATE_APPROVED, "1 Credit", "1 Academic Credit", "kuali.result.scale.credit.degree", "1", "1");
        loadResultValuesGroupInfo(LrcServiceConstants.RESULT_GROUP_KEY_KUALI_CREDITTYPE_CREDIT_2_0, LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_FIXED, LrcServiceConstants.RESULT_GROUPS_STATE_APPROVED, "1 Credit", "1 Academic Credit", "kuali.result.scale.credit.degree", "1", "1");
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

    public void loadResultValueInfo(String id, String name, String description, String resultScaleKey, String numericValue){
        loadResultValueInfo(id, LrcServiceConstants.RESULT_VALUE_TYPE_KEY_VALUE, "kuali.result.value.state.approved", name, description, resultScaleKey, numericValue);
    }

    public void loadResultValueInfo(String id, String type, String state, String name, String description, String resultScaleKey, String numericValue) {
        ContextInfo context = new ContextInfo();
        context.setPrincipalId(principalId);
        context.setCurrentDate(new Date());

        try {
            try {
                lrcService.getResultValue(id, context);
            } catch (DoesNotExistException dne) {

                ResultValueInfo info = new ResultValueInfo();
                info.setKey(id);
                info.setTypeKey(type);// use id for code
                info.setStateKey(state);
                info.setName(name);
                info.setDescr(new RichTextHelper().fromPlain(description));
                info.setResultScaleKey(resultScaleKey);
                info.setNumericValue(numericValue);

                lrcService.createResultValue(resultScaleKey, id, info, context);

            }

        } catch (Exception e) {
            throw new RuntimeException("error assigning to services: "+e);
        }
    }

}
