package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.web.form.LookupForm;

import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.common.util.ContextBuilder;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultInfo;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingSetConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class SocRolloverResultInfoLookupableImpl extends LookupableImpl {
    private transient CourseOfferingSetService courseOfferingSetService = null;
    private ContextInfo contextInfo = null;

    protected List<?> getSearchResults(LookupForm lookupForm, Map<String, String> fieldValues, boolean unbounded) {
        List<SocRolloverResultInfo> socRolloverResultInfos = new ArrayList<SocRolloverResultInfo>();
        String sourceTermId = fieldValues.get(CourseOfferingSetConstants.SOCROLLOVERRESULTINFO_SOURCE_TERM_ID);
        String targetTermId = fieldValues.get(CourseOfferingSetConstants.SOCROLLOVERRESULTINFO_TARGET_TERM_ID);

        //Build up a term search criteria
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.and(
                PredicateFactory.equal(CourseOfferingSetConstants.SOCROLLOVERRESULTINFO_SOURCE_TERM_ID, sourceTermId),
                PredicateFactory.equal(CourseOfferingSetConstants.SOCROLLOVERRESULTINFO_TARGET_TERM_ID, targetTermId)));

        QueryByCriteria criteria = qbcBuilder.build();

        //Perform Search with Service Call
        try{
            List<String> socRolloverResultIds = getCourseOfferingSetService().searchForSocRolloverResultIds(criteria, null);
            for (String socRolloverResultId  : socRolloverResultIds) {
                SocRolloverResultInfo socRolloverResultInfo = getCourseOfferingSetService().getSocRolloverResult(socRolloverResultId, getContextInfo());
                socRolloverResultInfos.add(socRolloverResultInfo);
            }
        }catch (InvalidParameterException e){
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }catch (MissingParameterException e){
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }catch (OperationFailedException e){
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }catch (PermissionDeniedException e){
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }catch(DoesNotExistException e){
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
       return socRolloverResultInfos;

    }

    public CourseOfferingSetService getCourseOfferingSetService() {
        if(courseOfferingSetService == null)
            courseOfferingSetService= CourseOfferingResourceLoader.loadCourseOfferingSetService(courseOfferingSetService);
        return courseOfferingSetService;
    }

    public ContextInfo getContextInfo() {
        if (contextInfo == null){
            contextInfo =  ContextBuilder.loadContextInfo(contextInfo);
        }
        return contextInfo;
    }
}
