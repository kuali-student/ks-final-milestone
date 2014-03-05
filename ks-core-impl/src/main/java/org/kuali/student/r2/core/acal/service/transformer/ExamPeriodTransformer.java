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
 * Created by David Yin on 8/7/13
 */
package org.kuali.student.r2.core.acal.service.transformer;

import org.kuali.student.r2.core.acal.dto.ExamPeriodInfo;
import org.kuali.student.r2.core.atp.dto.AtpInfo;

/**
 * This class transforms between ExamPeriodInfo and ATP
 *
 * @author Kuali Student Team
 */
public class ExamPeriodTransformer {

    public ExamPeriodInfo atp2ExamPeriod(AtpInfo atp) {
        ExamPeriodInfo examPeriod = new ExamPeriodInfo();
        if(atp != null){
            examPeriod.setId(atp.getId());
            examPeriod.setName(atp.getName());
            examPeriod.setDescr(atp.getDescr());
            examPeriod.setCode(atp.getCode());
            examPeriod.setStartDate(atp.getStartDate());
            examPeriod.setEndDate(atp.getEndDate());
            examPeriod.setTypeKey(atp.getTypeKey());
            examPeriod.setStateKey(atp.getStateKey());
            examPeriod.setMeta(atp.getMeta());
            examPeriod.setAttributes(atp.getAttributes());
        }

        return examPeriod;
    }

    public AtpInfo ExamPeriod2Atp(ExamPeriodInfo examPeriod) {
        AtpInfo atp = new AtpInfo();

        if (examPeriod != null) {
            atp.setId(examPeriod.getId());
            atp.setName(examPeriod.getName());
            atp.setDescr(examPeriod.getDescr());
            //atp.setCode(termCodeGenerator.generateTermCode(term));
            atp.setStartDate(examPeriod.getStartDate());
            atp.setEndDate(examPeriod.getEndDate());
            atp.setTypeKey(examPeriod.getTypeKey());
            atp.setStateKey(examPeriod.getStateKey());
            atp.setMeta(examPeriod.getMeta());
            atp.setAttributes(examPeriod.getAttributes());
        }

        return atp;
    }

}
