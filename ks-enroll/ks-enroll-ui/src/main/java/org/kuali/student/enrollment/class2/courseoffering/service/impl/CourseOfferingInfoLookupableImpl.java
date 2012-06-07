package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.common.util.ContextBuilder;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class is leftover from Core Slice. Delete when no longer needed or un deprecate if needed.
 */

public class CourseOfferingInfoLookupableImpl extends LookupableImpl {

    private transient CourseOfferingService courseOfferingService;
    private ContextInfo contextInfo = null;

    @Override
    protected List<?> getSearchResults(LookupForm lookupForm, Map<String, String> fieldValues, boolean unbounded) {

        String termKey = fieldValues.get(CourseOfferingConstants.COURSEOFFERING_TERM_ID);
        String subjectArea = fieldValues.get(CourseOfferingConstants.COURSEOFFERING_SUBJECT_AREA);

        List<CourseOfferingInfo> courseOfferings;

        try {
            List<String> courseOfferingIds = getCourseOfferingService().getCourseOfferingIdsByTermAndSubjectArea(termKey, subjectArea, getContextInfo());
            courseOfferings = new ArrayList<CourseOfferingInfo>(courseOfferingIds.size());

            for(String coId : courseOfferingIds) {
                courseOfferings.add(getCourseOfferingService().getCourseOffering(coId, getContextInfo()));
            }

        } catch (DoesNotExistException e) {
            throw new RuntimeException(e);
        } catch (InvalidParameterException e) {
            throw new RuntimeException(e);
        } catch (MissingParameterException e) {
            throw new RuntimeException(e);
        } catch (OperationFailedException e) {
            throw new RuntimeException(e);
        } catch (PermissionDeniedException e) {
            throw new RuntimeException(e);
        }

        return courseOfferings;
    }

    public CourseOfferingService getCourseOfferingService() {
        if(courseOfferingService == null)
            courseOfferingService= CourseOfferingResourceLoader.loadCourseOfferingService();
        return courseOfferingService;
    }

    public ContextInfo getContextInfo() {
        if (contextInfo == null){
            contextInfo =  ContextBuilder.loadContextInfo();
        }
        return contextInfo;
    }
}
