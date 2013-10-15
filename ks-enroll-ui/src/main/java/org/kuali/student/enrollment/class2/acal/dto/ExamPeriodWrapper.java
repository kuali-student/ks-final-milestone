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


import org.apache.commons.lang.StringUtils;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.acal.dto.ExamPeriodInfo;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.constants.AtpServiceConstants;

import java.util.Date;

/**
 * Wrapper class for <code>ExamPeriodInfo</code> dto.
 *
 * @author Kuali Student Team
 */
public class ExamPeriodWrapper {

    private String examPeriodType;
    private String examPeriodNameUI;
    private Date startDate;
    private Date endDate;
    private boolean excludeSaturday = true;
    private boolean excludeSunday = true;

    private ExamPeriodInfo examPeriodInfo;
    private TypeInfo typeInfo;

    public ExamPeriodWrapper(){
        examPeriodInfo = new ExamPeriodInfo();
        examPeriodInfo.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        RichTextInfo desc = new RichTextInfo();
        desc.setPlain("test");
        examPeriodInfo.setDescr(desc);
        setExamPeriodType(AtpServiceConstants.ATP_EXAM_PERIOD_TYPE_KEY);
        setExamPeriodNameUI("Final Exam Period");
    }

    public ExamPeriodWrapper(ExamPeriodInfo examPeriodInfo, boolean isCopy){
        this.startDate = examPeriodInfo.getStartDate();
        this.endDate = examPeriodInfo.getEndDate();
        setExamPeriodType(examPeriodInfo.getTypeKey());
        setExamPeriodNameUI("Final Exam Period");

        if (isCopy){
            setExamPeriodInfo(new ExamPeriodInfo());
            RichTextInfo desc = new RichTextInfo();
            desc.setPlain(examPeriodInfo.getTypeKey());
            getExamPeriodInfo().setDescr(desc);
            getExamPeriodInfo().setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        }else{
            setExamPeriodInfo(examPeriodInfo);
        }

    }


    public String getExamPeriodType() {
        return examPeriodType;
    }

    public void setExamPeriodType(String examPeriodType) {
        if (examPeriodType == null || examPeriodType.equals("")) {
            this.examPeriodType = AtpServiceConstants.ATP_EXAM_PERIOD_TYPE_KEY;
        } else {
            this.examPeriodType = examPeriodType;
        }
    }

    public String getExamPeriodNameUI() {
        return examPeriodNameUI;
    }

    public void setExamPeriodNameUI(String examPeriodNameUI) {
        if (examPeriodNameUI == null || examPeriodNameUI.equals("")) {
            this.examPeriodNameUI = "Final Exam Period";
        } else {
            this.examPeriodNameUI = examPeriodNameUI;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isNew() {
        return StringUtils.isBlank(examPeriodInfo.getId());
    }

    //This is for UI display purpose
    public String getStartDateUI(){
        return formatStartEndDateUI(examPeriodInfo.getStartDate());
    }

    //This is for UI display purpose
    public String getEndDateUI(){
        return formatStartEndDateUI(examPeriodInfo.getEndDate());
    }

    public boolean isExcludeSaturday() {
        return excludeSaturday;
    }

    public void setExcludeSaturday(boolean excludeSaturday) {
        this.excludeSaturday = excludeSaturday;
    }

    public boolean isExcludeSunday() {
        return excludeSunday;
    }

    public void setExcludeSunday(boolean excludeSunday) {
        this.excludeSunday = excludeSunday;
    }

    //This is for UI display purpose
    protected String formatStartEndDateUI(Date date){
        if (date != null) {
            return DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.format(date);
        }else{
            return StringUtils.EMPTY;
        }

    }

}
