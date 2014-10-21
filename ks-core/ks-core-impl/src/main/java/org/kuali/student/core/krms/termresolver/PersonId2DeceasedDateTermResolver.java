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

package org.kuali.student.core.krms.termresolver;

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
public class PersonId2DeceasedDateTermResolver implements TermResolver<Date> {

    private final static Set<String> prereqs;

    private IdentityService identityService;

    static {
        Set<String> temp = new HashSet<>(1);
        temp.add(RulesExecutionConstants.PERSON_ID_TERM.getName());

        prereqs = Collections.unmodifiableSet(temp);
    }

    @Override
    public Set<String> getPrerequisites() {
        return prereqs;
    }

    @Override
    public String getOutput() {
        return RulesExecutionConstants.STUDENT_DECEASED_DATE_TERM.getName();
    }

    @Override
    public Set<String> getParameterNames() {
        return Collections.emptySet();
    }

    @Override
    public int getCost() {
        return 5;
    }

    @Override
    public Date resolve(Map<String, Object> resolvedPrereqs, Map<String, String> parameters) throws TermResolutionException {

        String personId = (String) resolvedPrereqs.get(RulesExecutionConstants.PERSON_ID_TERM.getName());

        Entity entity = getIdentityService().getEntity(personId);
        Date deceasedDate = null;
        if(entity.getBioDemographics().getDeceasedDate() != null) {
            try {
                deceasedDate = DateUtils.parseDate(entity.getBioDemographics().getDeceasedDate(), new String[]{EntityBioDemographicsContract.DECEASED_DATE_FORMAT});
            } catch (ParseException e) {
                throw new TermResolutionException(e.getMessage(), this, parameters, e);
            }
        }
        return deceasedDate;

    }

    public IdentityService getIdentityService() {
        return identityService;
    }

    public void setIdentityService(IdentityService identityService) {
        this.identityService = identityService;
    }
}
