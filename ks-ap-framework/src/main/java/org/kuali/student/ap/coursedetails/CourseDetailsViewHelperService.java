package org.kuali.student.ap.coursedetails;

import org.kuali.rice.krad.uif.service.ViewHelperService;
import org.kuali.rice.krad.web.form.UifFormBase;

/**
 * Created with IntelliJ IDEA.
 * User: rileymg
 * Date: 6/6/14
 * Time: 11:19 AM
 * To change this template use File | Settings | File Templates.
 */
public interface CourseDetailsViewHelperService extends ViewHelperService {
    public void loadCourseSectionDetails(UifFormBase form, String courseId);
}
