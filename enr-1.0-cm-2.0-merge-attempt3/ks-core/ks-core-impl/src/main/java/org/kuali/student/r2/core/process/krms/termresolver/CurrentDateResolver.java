/**
 * Copyright 2011 The Kuali Foundation Licensed under the
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
 */

package org.kuali.student.r2.core.process.krms.termresolver;

import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.student.common.util.krms.RulesExecutionConstants;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: andy
 * Date: 12/16/11
 * Time: 3:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class CurrentDateResolver implements TermResolver<Date> {

    @Override
    public Set<String> getPrerequisites() {
        return Collections.emptySet();
    }

    @Override
    public String getOutput() {
        return RulesExecutionConstants.CURRENT_DATE_TERM_NAME;
    }

    @Override
    public Set<String> getParameterNames() {
        return Collections.emptySet();
    }

    @Override
    public int getCost() {
        // TODO Analyze, though probably not much to check here
        return 0;
    }

    @Override
    public Date resolve(Map<String, Object> resolvedPrereqs, Map<String, String> parameters) throws TermResolutionException {
        Calendar cal = Calendar.getInstance();

        cal.set(2011, 11, 30);

        return cal.getTime();
    }
}
