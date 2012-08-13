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
 */
package org.kuali.student.enrollment.class2.acal.service.impl;

import org.apache.commons.httpclient.util.DateUtil;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.class2.acal.service.TermCodeGenerator;
import org.kuali.student.r2.core.constants.AtpServiceConstants;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class TermCodeGeneratorImpl implements TermCodeGenerator {
    private static final String YEAR_ONLY_FORMAT_STRING = "yyyy";

    private static Map<String, Integer> termTypeCodeMap;

    static {
        Map<String, Integer> map = new HashMap<String, Integer>(4);

        map.put(AtpServiceConstants.ATP_WINTER_TYPE_KEY, 1);
        map.put(AtpServiceConstants.ATP_SPRING_TYPE_KEY, 2);
        map.put(AtpServiceConstants.ATP_SUMMER_TYPE_KEY, 3);
        map.put(AtpServiceConstants.ATP_FALL_TYPE_KEY, 4);

        termTypeCodeMap = Collections.unmodifiableMap(map);
    }

    @Override
    public String generateTermCode(TermInfo term) {
        // if the term is not of a type that is handled by the defined formula, return null, since the value for the atp code is undefined at that point
        if(!termTypeCodeMap.containsKey(term.getTypeKey())) {
            return null;
        }

        StringBuilder result = new StringBuilder(DateUtil.formatDate(term.getStartDate(), YEAR_ONLY_FORMAT_STRING));

        result.append(termTypeCodeMap.get(term.getTypeKey()).toString());

        return result.toString();
    }
}
