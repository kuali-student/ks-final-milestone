package org.kuali.student.krms.termresolver;

import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.student.krms.util.KSKRMSExecutionUtil;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;
import org.kuali.student.r2.core.population.dto.PopulationInfo;
import org.kuali.student.r2.core.population.service.PopulationService;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Kuali Student Team
 */
public class PopulationTermResolver implements TermResolver<Boolean> {

    private PopulationService populationService;

    @Override
    public Set<String> getPrerequisites() {
        Set<String> prereqs = new HashSet<String>(2);
        prereqs.add(KSKRMSServiceConstants.TERM_PREREQUISITE_PERSON_ID);
        prereqs.add(KSKRMSServiceConstants.TERM_PREREQUISITE_CONTEXTINFO);
        return Collections.unmodifiableSet(prereqs);
    }

    @Override
    public String getOutput() {
        return KSKRMSServiceConstants.TERM_RESOLVER_POPULATION;
    }

    @Override
    public Set<String> getParameterNames() {
        return Collections.singleton(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_POPULATION_KEY);
    }
    @Override
    public int getCost() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Boolean resolve(Map<String, Object> resolvedPrereqs, Map<String, String> parameters) throws TermResolutionException {
        ContextInfo context = (ContextInfo) resolvedPrereqs.get(KSKRMSServiceConstants.TERM_PREREQUISITE_CONTEXTINFO);
        String populationId = parameters.get(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_POPULATION_KEY);
        PopulationInfo populationInfo = null;
        if (populationId != null) {
            try {
                 populationInfo = this.getPopulationService().getPopulation(populationId, context);
                 return true;
            } catch (Exception e) {
                KSKRMSExecutionUtil.convertExceptionsToTermResolutionException(parameters, e, this);
            }

        }
        return false;
    }

    public PopulationService getPopulationService() {
        return populationService;
    }

    public void setPopulationService(PopulationService populationService) {
        this.populationService = populationService;
    }
}
