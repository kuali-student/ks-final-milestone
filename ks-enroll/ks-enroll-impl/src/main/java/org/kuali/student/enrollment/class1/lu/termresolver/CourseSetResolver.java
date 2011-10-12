package org.kuali.student.enrollment.class1.lu.termresolver;

import java.util.*;

import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.rice.krms.api.engine.TermSpecification;
import org.kuali.student.common.exceptions.*;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.lum.lu.service.LuService;

public class CourseSetResolver implements TermResolver<Collection<String>> {

    private LuService luService;

    public void setLuService(LuService luService) {
        this.luService = luService;
    }

    @Override
    public Set<TermSpecification> getPrerequisites() {
        return Collections.singleton(RulesExecutionConstants.contextInfoTermSpec);
    }

    @Override
    public TermSpecification getOutput() {
        return RulesExecutionConstants.courseSetTermSpec;
    }

    @Override
    public Set<String> getParameterNames() {
        return Collections.singleton(RulesExecutionConstants.COURSE_SET_ID_TERM_PROPERTY_NAME);
    }

    @Override
    public int getCost() {
        // TODO analyze for a relevant cost
        return 0;
    }

    @Override
    public Collection<String> resolve(Map<TermSpecification, Object> resolvedPrereqs, Map<String, String> parameters) throws TermResolutionException {
        String courseSetId = parameters.get(RulesExecutionConstants.COURSE_SET_ID_TERM_PROPERTY_NAME);
        ContextInfo context = (ContextInfo) resolvedPrereqs.get(RulesExecutionConstants.contextInfoTermSpec);
        
        if(courseSetId == null) {
            throw new TermResolutionException("No parameter found with name: " + RulesExecutionConstants.COURSE_SET_ID_TERM_PROPERTY_NAME, this, parameters);
        }

        List<String> results = null;
        try {
            results = luService.getCluIdsFromCluSet(courseSetId, context);
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
