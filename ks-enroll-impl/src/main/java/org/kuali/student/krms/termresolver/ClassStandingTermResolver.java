package org.kuali.student.krms.termresolver;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.student.krms.util.KSKRMSExecutionUtil;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.KSKRMSServiceConstants;
import org.kuali.student.r2.core.constants.PopulationServiceConstants;
import org.kuali.student.r2.core.population.dto.PopulationInfo;
import org.kuali.student.r2.core.population.service.PopulationService;

import javax.xml.namespace.QName;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Peggy
 * Date: 2013/13/06
 * Time: 03:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class ClassStandingTermResolver implements TermResolver<Integer> {

    private PopulationService populationService;

    @Override
    public Set<String> getPrerequisites() {

        Set<String> temp = new HashSet<String>(2);
        temp.add(KSKRMSServiceConstants.TERM_PREREQUISITE_PERSON_ID);
        temp.add(KSKRMSServiceConstants.TERM_PREREQUISITE_CONTEXTINFO);
        return Collections.unmodifiableSet(temp);
    }

    @Override
    public String getOutput() {
        return null;  //To change body of implemented methods use File | Settings | File Templates. TERM_PARAMETER_TYPE_CLASS_STANDING_KEY
    }

    @Override
    public Set<String> getParameterNames() {
        return Collections.singleton(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_CLASS_STANDING_KEY);
    }
    @Override
    public int getCost() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Integer resolve(Map<String, Object> resolvedPrereqs, Map<String, String> parameters) throws TermResolutionException {
        ContextInfo context = (ContextInfo) resolvedPrereqs.get(KSKRMSServiceConstants.TERM_PREREQUISITE_CONTEXTINFO);
        String classStadingId = parameters.get(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_CLASS_STANDING_KEY);
        PopulationInfo populationInfo = null;
        if (classStadingId != null) {
            try {
                 populationInfo = this.getPopulationService().getPopulation(classStadingId, context);
            } catch (Exception e) {
                KSKRMSExecutionUtil.convertExceptionsToTermResolutionException(parameters, e, this);
            }

        }
        return 1;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public PopulationService getPopulationService() {
        return populationService;
    }

    public void setPopulationService(PopulationService populationService) {
        this.populationService = populationService;
    }
}
