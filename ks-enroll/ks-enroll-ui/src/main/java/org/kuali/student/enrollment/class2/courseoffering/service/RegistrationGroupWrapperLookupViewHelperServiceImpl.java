package org.kuali.student.enrollment.class2.courseoffering.service;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kns.authorization.BusinessObjectRestrictions;
import org.kuali.rice.kns.uif.service.impl.LookupViewHelperServiceImpl;
import org.kuali.student.enrollment.class2.courseoffering.dto.RegistrationGroupWrapper;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RegistrationGroupWrapperLookupViewHelperServiceImpl extends LookupViewHelperServiceImpl {

    private transient CourseOfferingService courseOfferingService;
    private static final String TERM_FIELD_NAME = "courseOffering.termKey";
    private static final String SUBJECT_AREA_FIELD_NAME = "courseOffering.subjectArea";

    @Override
    protected List<?> getSearchResultsWithBounding(Map<String, String> fieldValues, boolean unbounded) {

        ContextInfo context = ContextInfo.newInstance();
        String termKey = fieldValues.get(TERM_FIELD_NAME);
        String subjectArea = fieldValues.get(SUBJECT_AREA_FIELD_NAME);

        List<RegistrationGroupWrapper> registrationGroupWrappers;

        try {
            List<String> courseOfferingIds = getCourseOfferingService().getCourseOfferingIdsByTermAndSubjectArea(termKey, subjectArea, context);
            registrationGroupWrappers = new ArrayList<RegistrationGroupWrapper>(courseOfferingIds.size());

            for(String coId : courseOfferingIds) {
                List<RegistrationGroupInfo> regGroups = getCourseOfferingService().getRegGroupsForCourseOffering(coId, context);

                for(RegistrationGroupInfo regGroup : regGroups) {
                    RegistrationGroupWrapper wrapper = new RegistrationGroupWrapper();

                    wrapper.setCourseOffering(getCourseOfferingService().getCourseOffering(coId, context));
                    wrapper.setRegistrationGroup(regGroup);

                    // TODO right now getOfferingsByIdList throws a not supported exception
                    //wrapper.setActivityOfferings(getCourseOfferingService().getActivityOfferingsByIdList(regGroup.getActivityOfferingIds(), context));

                    for(String activityId : regGroup.getActivityOfferingIds()) {
                        wrapper.getActivityOfferings().add(getCourseOfferingService().getActivityOffering(activityId, context));
                    }

                    registrationGroupWrappers.add(wrapper);
                }

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

        return registrationGroupWrappers;
    }

    protected CourseOfferingService getCourseOfferingService() {
        if (courseOfferingService == null) {
            courseOfferingService = (CourseOfferingService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/courseOffering", "CourseOfferingService"));
        }

        return courseOfferingService;
    }

    @Override
    protected String getActionUrls(Object dataObject, List<String> pkNames, BusinessObjectRestrictions dataObjectRestrictions) {
        return "";
    }
}

