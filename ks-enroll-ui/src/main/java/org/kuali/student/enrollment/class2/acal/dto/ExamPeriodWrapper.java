/**
 * Copyright 2013 The Kuali Foundation Licensed under the
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
 * Created by mharmath on 8/8/13
 */
package org.kuali.student.enrollment.class2.acal.dto;


import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.core.acal.dto.ExamPeriodInfo;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.constants.AtpServiceConstants;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class ExamPeriodWrapper extends TimeSetWrapper {

    private String keyDateType;
    private String keyDateNameUI;

    private ExamPeriodInfo examPeriodInfo;
    private TypeInfo typeInfo;

    public ExamPeriodWrapper(){
        setAllDay(true);
        setDateRange(false);
        examPeriodInfo = new ExamPeriodInfo();
        examPeriodInfo.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        RichTextInfo desc = new RichTextInfo();
        desc.setPlain("test");
        examPeriodInfo.setDescr(desc);
        setKeyDateType("Final Examination Period");
        setKeyDateNameUI("Final Examination Period");
    }

    public ExamPeriodWrapper(ExamPeriodInfo examPeriod, boolean isCopy){

        this.setKeyDateType("Final Examination Period");
        this.setKeyDateNameUI("Final Examination Period");

        if (isCopy){
            this.setExamPeriodInfo(new ExamPeriodInfo());
            RichTextInfo desc = new RichTextInfo();
            desc.setPlain(examPeriod.getTypeKey());
            getExamPeriodInfo().setDescr(desc);
            getExamPeriodInfo().setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        }else{
            this.setStartDate(examPeriod.getStartDate());
            this.setEndDate(examPeriod.getEndDate());
            this.setExamPeriodInfo(examPeriod);
            buildDateAndTime();        // don't do this when copying
        }

    }


    public String getKeyDateType() {
        return keyDateType;
    }

    public void setKeyDateType(String keyDateType) {
        if (keyDateType == null || keyDateType.equals("")) {
            this.keyDateType = "Final Examination Period";
        } else {
            this.keyDateType = keyDateType;
        }
    }

    public String getKeyDateNameUI() {
        return keyDateNameUI;
    }

    public void setKeyDateNameUI(String keyDateNameUI) {
        if (keyDateNameUI == null || keyDateNameUI.equals("")) {
            this.keyDateNameUI = "Final Examination Period";
        } else {
            this.keyDateNameUI = keyDateNameUI;
        }
    }

    public ExamPeriodInfo getExamPeriodInfo() {
        return examPeriodInfo;
    }

    public void setExamPeriodInfo(ExamPeriodInfo examPeriodInfo) {
        this.examPeriodInfo = examPeriodInfo;
    }

    public TypeInfo getTypeInfo() {
        return typeInfo;
    }

    public void setTypeInfo(TypeInfo typeInfo) {
        this.typeInfo = typeInfo;
    }

}
