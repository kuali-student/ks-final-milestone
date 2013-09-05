/**
 * Copyright 2005-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.class1.krms.dto;
import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krms.api.repository.action.ActionDefinitionContract;
import org.kuali.rice.krms.api.repository.rule.RuleDefinitionContract;
import org.kuali.rice.krms.dto.ActionEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.dto.RuleTypeInfo;
import org.kuali.student.lum.lu.ui.krms.dto.LURuleEditor;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;

import java.util.Date;
import java.util.Map;

/**
 * @author Kuali Student Team
 */
public class FERuleEditor extends LURuleEditor {

    private static final long serialVersionUID = 1L;

    public FERuleEditor(){
        super();
    }

    public FERuleEditor(RuleDefinitionContract definition){
        super(definition);
    }

    public FERuleEditor(String key, boolean dummy, RuleTypeInfo ruleTypeInfo) {
        super(key, dummy, ruleTypeInfo);
    }

    public String getDay() {
        if(this.getActions().size()>0){
            ActionDefinitionContract action = this.getActions().get(0);
            if(action.getAttributes().containsKey("day")){
                String day = action.getAttributes().get("day");
                return "Day " + day;
            }
        }

        return StringUtils.EMPTY;
    }

    public String getTime() {
        String timeString = StringUtils.EMPTY;
        if(this.getActions().size()>0){
            Map<String, String> attributes = this.getActions().get(0).getAttributes();
            if(attributes.containsKey("startTime")){
                Date timeForDisplay = new Date(Long.parseLong(attributes.get("startTime")));
                timeString = DateFormatters.HOUR_MINUTE_AM_PM_TIME_FORMATTER.format(timeForDisplay);
            }
            if(attributes.containsKey("endTime")){
                Date timeForDisplay = new Date(Long.parseLong(attributes.get("endTime")));
                timeString += "-" + DateFormatters.HOUR_MINUTE_AM_PM_TIME_FORMATTER.format(timeForDisplay);
            }
        }

        return timeString;
    }

}
