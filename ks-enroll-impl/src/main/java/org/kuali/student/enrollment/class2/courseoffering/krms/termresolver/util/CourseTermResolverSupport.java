package org.kuali.student.enrollment.class2.courseoffering.krms.termresolver.util;

import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.krms.util.KSKRMSExecutionUtil;
import org.kuali.student.r2.core.versionmanagement.dto.VersionDisplayInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by SW Genis on 2014/06/14.
 */
public abstract class CourseTermResolverSupport<T> implements TermResolver<T> {

    private CluService cluService;

    public Set<String> getPrerequisites() {
        Set<String> prereqs = new HashSet<String>(2);
        prereqs.add(RulesExecutionConstants.CONTEXT_INFO_TERM.getName());
        return Collections.unmodifiableSet(prereqs);
    }

    public List<String> getCluIdsFromVersionIndId(String cluId, Map<String, String> parameters, ContextInfo context) throws TermResolutionException {

        //Retrieve the version independent clu id.
        List<String> courseIds = new ArrayList<String>();
        try{
            List<VersionDisplayInfo> versions = this.getCluService().getVersions(CluServiceConstants.CLU_NAMESPACE_URI, cluId, context);
            for (VersionDisplayInfo version : versions) {
                courseIds.add(version.getVersionedFromId());
            }
        } catch (Exception e) {
            KSKRMSExecutionUtil.convertExceptionsToTermResolutionException(parameters, e, this);
        }
        return courseIds;
    }

    public List<String> getCluIdsForCluSet(String cluSetId, Map<String, String> parameters, ContextInfo context) throws TermResolutionException {
        //Retrieve the list of cluIds from the cluset.
        try{
            return this.getCluService().getAllCluIdsInCluSet(cluSetId, context);
        } catch (Exception e) {
            KSKRMSExecutionUtil.convertExceptionsToTermResolutionException(parameters, e, this);
        }
        return null;
    }

    public CluService getCluService() {
        return cluService;
    }

    public void setCluService(CluService cluService) {
        this.cluService = cluService;
    }

}
