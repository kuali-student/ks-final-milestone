package org.kuali.student.enrollment.class1.lrr.termresolver;

import java.util.*;

import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.rice.krms.api.engine.TermSpecification;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.enrollment.lpr.service.LuiPersonRelationService;
import org.kuali.student.enrollment.lrr.dto.LearningResultRecordInfo;
import org.kuali.student.enrollment.lrr.infc.LearningResultRecord;
import org.kuali.student.enrollment.lrr.service.LearningResultRecordService;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;

public class CompletedCoursesResolver implements TermResolver<Collection<String>> {

    private LearningResultRecordService lrrService;

    private LuiPersonRelationService lprService;
    private LuiService luiService;

    private final static Set<TermSpecification> prerequisites = new HashSet<TermSpecification>(2);

    static {
        prerequisites.add(RulesExecutionConstants.studentIdTermSpec);
        prerequisites.add(RulesExecutionConstants.contextInfoTermSpec);
    }

    public void setLrrService(LearningResultRecordService lrrService) {
        this.lrrService = lrrService;
    }

    public void setLprService(LuiPersonRelationService lprService) {
        this.lprService = lprService;
    }

    public void setLuiService(LuiService luiService) {
        this.luiService = luiService;
    }

    @Override
    public Set<TermSpecification> getPrerequisites() {
        return prerequisites;
    }

    @Override
    public TermSpecification getOutput() {
        return RulesExecutionConstants.completedCourseIdsTermSpec;
    }

    @Override
    public Set<String> getParameterNames() {
        return Collections.emptySet();
    }

    @Override
    public int getCost() {
        // TODO analyze actual cost
        return 0;
    }

    @Override
    public Collection<String> resolve(Map<TermSpecification, Object> resolvedPrereqs, Map<String, String> parameters) throws TermResolutionException {
        String studentId = resolvedPrereqs.get(RulesExecutionConstants.studentIdTermSpec).toString();
        ContextInfo context = (ContextInfo) resolvedPrereqs.get(RulesExecutionConstants.contextInfoTermSpec);

        Collection<String> results = null;

        try {
            List<LuiPersonRelationInfo> lprs = lprService.getLprsByPerson(studentId, context);

            Map<String, String> lprIdToCluId = new HashMap<String, String>();
            List<String> lprIds = new ArrayList<String>(lprs.size());

            for(LuiPersonRelationInfo lpr : lprs) {
                String luiId = lpr.getLuiId();
                LuiInfo lui = luiService.getLui(luiId, context);
                lprIdToCluId.put(lpr.getId(), lui.getCluId());

                lprIds.add(lpr.getId());
            }

            List<LearningResultRecordInfo> lrrs = lrrService.getLearningResultRecordsForLprIdList(lprIds, context);

            results = new ArrayList<String>();

            for(LearningResultRecord lrr : lrrs) {
                results.add(lprIdToCluId.get(lrr.getLprId()));
            }

        } catch (DoesNotExistException e) {
            throw new TermResolutionException("", this, parameters, e);
        } catch (DisabledIdentifierException e) {
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
