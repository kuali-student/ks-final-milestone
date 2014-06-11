package org.kuali.student.ap.coursesearch.service;

import org.kuali.rice.krad.uif.service.ViewHelperService;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.ap.coursesearch.dataobject.CourseOfferingDetailsWrapper;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.ap.coursesearch.dataobject.ActivityOfferingDetailsWrapper;
import org.kuali.student.r2.core.acal.infc.Term;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: rileymg
 * Date: 6/6/14
 * Time: 11:19 AM
 * To change this template use File | Settings | File Templates.
 */
public interface CourseDetailsViewHelperService extends ViewHelperService {
    public void loadCourseSectionDetails(UifFormBase form, String courseId) throws Exception;

    public Map<String, List<CourseOfferingDetailsWrapper>> processCourseOfferingsByTerm(List<String> courseIds, List<Term> terms) throws Exception;

    public ActivityOfferingDetailsWrapper convertAOInfoToWrapper(ActivityOfferingInfo ao, boolean regGroupIdForSingleFO) throws Exception;
}
