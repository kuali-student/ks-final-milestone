package org.kuali.student.enrollment.class1.lu.termresolver;

import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.rice.krms.api.engine.TermResolver;

import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.lum.clu.service.CluService;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;

public class CourseSetResolver implements TermResolver<Collection<String>> {

    @Resource
    private CluService cluService;

    @Override
    public Set<String> getPrerequisites() {
        return Collections.singleton(RulesExecutionConstants.CONTEXT_INFO_TERM_NAME);
    }

    @Override
    public String getOutput() {
        return RulesExecutionConstants.COURSE_SET_TERM_NAME;
    }

    @Override
    public Set<String> getParameterNames() {
        return Collections.singleton(RulesExecutionConstants.COURSE_SET_ID_TERM_PROPERTY);
    }

    @Override
    public int getCost() {
        // TODO analyze for a relevant cost
        return 0;
    }

    @Override
    public Collection<String> resolve(Map<String, Object> resolvedPrereqs, Map<String, String> parameters) throws TermResolutionException {
        String courseSetId = parameters.get(RulesExecutionConstants.COURSE_SET_ID_TERM_PROPERTY);
        ContextInfo context = (ContextInfo) resolvedPrereqs.get(RulesExecutionConstants.CONTEXT_INFO_TERM_NAME);
        
        if(courseSetId == null) {
            throw new TermResolutionException("No parameter found with name: " + RulesExecutionConstants.COURSE_SET_ID_TERM_PROPERTY, this, parameters);
        }

        List<String> results = null;
        try {
            results = cluService.getCluIdsFromCluSet(courseSetId, context);
        } catch (DoesNotExistException e) {
            throw new TermResolutionException("", this, parameters, e);
        } catch (InvalidParameterException e) {
            throw new TermResolutionException("", this, parameters, e);
        } catch (MissingParameterException e) {
            throw new TermResolutionException("", this, parameters, e);
        } catch (OperationFailedException e) {
            throw new TermResolutionException("", this, parameters, e);
        } catch (PermissionDeniedException e) {
            throw new TermResolutionException("", this, parameters, e);
        }

        return results;
        
    }

}
