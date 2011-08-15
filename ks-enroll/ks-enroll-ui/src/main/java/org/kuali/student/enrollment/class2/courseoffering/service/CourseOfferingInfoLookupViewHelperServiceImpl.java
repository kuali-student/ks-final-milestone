package org.kuali.student.enrollment.class2.courseoffering.service;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kns.uif.service.impl.LookupViewHelperServiceImpl;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CourseOfferingInfoLookupViewHelperServiceImpl extends LookupViewHelperServiceImpl {

    private transient CourseOfferingService courseOfferingService;
    private static final String TERM_FIELD_NAME = "termKey";
    private static final String SUBJECT_AREA_FIELD_NAME = "subjectArea";

    private static Boolean foundValidCourseOfferings = null;

    @Override
    protected List<?> getSearchResultsWithBounding(Map<String, String> fieldValues, boolean unbounded) {

        ContextInfo context = ContextInfo.newInstance();
        String termKey = fieldValues.get(TERM_FIELD_NAME);
        String subjectArea = fieldValues.get(SUBJECT_AREA_FIELD_NAME);


        /* HACK HACK
         * Hack code! This is a temporary hack so that at least some course offerings are added to the system
         * Remove this when there is a reliable way through the UI to create course offerings
         */

        if(foundValidCourseOfferings == null) {
            try {
                List<String> courseOfferingIds = getCourseOfferingService().getCourseOfferingIdsByTermAndSubjectArea("testTermId1", "CHEM", context);
                foundValidCourseOfferings = !courseOfferingIds.isEmpty();
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
        }

        if(!foundValidCourseOfferings) {
            try {
                getCourseOfferingService().createCourseOfferingFromCanonical("REFERENCECOURSECHEM131", "testTermId1", null, context);
                getCourseOfferingService().createCourseOfferingFromCanonical("REFERENCECOURSECHEM132", "testTermId1", null, context);

                foundValidCourseOfferings = true;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        List<CourseOfferingInfo> courseOfferings;

        try {
            List<String> courseOfferingIds = getCourseOfferingService().getCourseOfferingIdsByTermAndSubjectArea(termKey, subjectArea, context);
            courseOfferings = new ArrayList<CourseOfferingInfo>(courseOfferingIds.size());

            for(String coId : courseOfferingIds) {
                courseOfferings.add(getCourseOfferingService().getCourseOffering(coId, context));
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

    protected CourseOfferingService getCourseOfferingService() {
        if (courseOfferingService == null) {
            courseOfferingService = (CourseOfferingService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/courseOffering", "CourseOfferingService"));
        }

        return courseOfferingService;
    }
}
