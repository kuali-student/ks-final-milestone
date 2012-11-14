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

import org.apache.commons.lang.time.DateUtils;
import org.kuali.rice.kim.api.identity.IdentityService;
import org.kuali.rice.kim.api.identity.entity.Entity;
import org.kuali.rice.kim.api.identity.personal.EntityBioDemographicsContract;
import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.student.common.util.krms.RulesExecutionConstants;

import java.text.ParseException;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author alubbers
 */
public class StudentDeceasedTermResolver implements TermResolver<Boolean> {

    private IdentityService identityService;

    private final static Set<String> preRequisites;

    static {
        Set<String> temp = new HashSet<String>(2);
        temp.add(RulesExecutionConstants.STUDENT_ID_TERM_NAME);
        temp.add(RulesExecutionConstants.CURRENT_DATE_TERM_NAME);

        preRequisites = Collections.unmodifiableSet(temp);
    }

    public void setIdentityService(IdentityService identityService) {
        this.identityService = identityService;
    }

    @Override
    public Set<String> getPrerequisites() {
        return preRequisites;
    }

    @Override
    public String getOutput() {
        return RulesExecutionConstants.STUDENT_DECEASED_TERM_NAME;
    }

    @Override
    public Set<String> getParameterNames() {
        return Collections.emptySet();
    }

    @Override
    public int getCost() {
        // TODO analyze
        return 0;
    }

    @Override
    public Boolean resolve(Map<String, Object> resolvedPrereqs, Map<String, String> parameters) throws TermResolutionException {

        String studentId = (String) resolvedPrereqs.get(RulesExecutionConstants.STUDENT_ID_TERM_NAME);
        Date currentDate = (Date) resolvedPrereqs.get(RulesExecutionConstants.CURRENT_DATE_TERM_NAME);

        Entity entity = identityService.getEntity(studentId);

        Date deceasedDate = null;

        if(entity.getBioDemographics().getDeceasedDate() != null) {
            try {
                deceasedDate = DateUtils.parseDate(entity.getBioDemographics().getDeceasedDate(), new String[]{EntityBioDemographicsContract.DECEASED_DATE_FORMAT});
            } catch (ParseException e) {
                throw new TermResolutionException(e.getMessage(), this, parameters, e);
            }
        }

        if(deceasedDate != null) {
            return deceasedDate.before(currentDate);
        }

        return false;
    }
}
