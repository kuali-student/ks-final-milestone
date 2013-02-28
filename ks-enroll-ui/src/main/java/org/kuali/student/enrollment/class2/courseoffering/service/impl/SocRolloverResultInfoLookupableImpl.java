package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;

import java.util.List;
import java.util.Map;

public class SocRolloverResultInfoLookupableImpl extends LookupableImpl {
    private transient CourseOfferingSetService courseOfferingSetService = null;
    private static final long serialVersionUID = 1L;
    protected List<?> getSearchResults(LookupForm lookupForm, Map<String, String> fieldValues, boolean unbounded) {
        List<SocRolloverResultInfo> socRolloverResultInfos ;
        String sourceTermId = fieldValues.get(CourseOfferingConstants.SOCROLLOVERRESULTINFO_SOURCE_TERM_ID);
        String targetTermId = fieldValues.get(CourseOfferingConstants.SOCROLLOVERRESULTINFO_TARGET_TERM_ID);

        //Build up a term search criteria
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        if (StringUtils.isNotBlank(targetTermId) && !targetTermId.isEmpty()) {
            if (StringUtils.isNotBlank(sourceTermId) && !sourceTermId.isEmpty()){
                qbcBuilder.setPredicates(PredicateFactory.and(
                    PredicateFactory.equal(CourseOfferingConstants.SOCROLLOVERRESULTINFO_SOURCE_TERM_ID, sourceTermId),
                    PredicateFactory.equal(CourseOfferingConstants.SOCROLLOVERRESULTINFO_TARGET_TERM_ID, targetTermId)));
            }
            else {
                qbcBuilder.setPredicates(PredicateFactory.equal(CourseOfferingConstants.SOCROLLOVERRESULTINFO_TARGET_TERM_ID, targetTermId));
            }
        }else if (StringUtils.isNotBlank(sourceTermId) && !sourceTermId.isEmpty()){
            qbcBuilder.setPredicates(PredicateFactory.equal(CourseOfferingConstants.SOCROLLOVERRESULTINFO_SOURCE_TERM_ID, sourceTermId));
        }
        QueryByCriteria criteria = qbcBuilder.build();

        //Perform Search with Service Call
        try{
            socRolloverResultInfos = getCourseOfferingSetService().searchForSocRolloverResults(criteria, null);
        }catch (Exception e){
            throw new RuntimeException("Error searching for terms",e);
        }

       return socRolloverResultInfos;

    }

    public CourseOfferingSetService getCourseOfferingSetService() {
        if(courseOfferingSetService == null)
            courseOfferingSetService= CourseOfferingResourceLoader.loadCourseOfferingSetService();
        return courseOfferingSetService;
    }

}
