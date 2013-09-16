package org.kuali.student.enrollment.class2.coursewaitlist.service;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.coursewaitlist.dto.CourseWaitListInfo;
import org.kuali.student.enrollment.coursewaitlist.service.CourseWaitListService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseWaitListServiceConstants;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;


public class CourseWaitListServiceUtil {

    /* Create a new CourseWaitListInfo (CWLI) for a specified AO and persist it in DB
       1)set AOInfo.id to courseWaitListInfo.activityOfferingIds
       2)set AOInfo.formatOfferingId to courseWaitListInfo.formatOfferingIds
       3)if COInfo.hasWaitList = true, set courseWaitListInfo.stateKey to active and set automaticallyProcessed,
            confirmationRequired, checkInRequired, and allowHoldUntilEntries in courseWaitListInfo to true
       4)if COInfo.hasWaitList = false, set courseWaitListInfo.stateKey to inactive and set automaticallyProcessed,
            confirmationRequired, checkInRequired, and allowHoldUntilEntries in courseWaitListInfo to false
    */
    public static CourseWaitListInfo createCourseWaitlist(ActivityOfferingInfo aoInfo, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, DoesNotExistException, DataValidationErrorException, ReadOnlyException {

        //need to get the value of coInfo.hasWaitList to set stateKey and other default values
        FormatOfferingInfo foInfo = getCourseOfferingService().getFormatOffering(aoInfo.getFormatOfferingId(), context);
        CourseOfferingInfo coInfo = getCourseOfferingService().getCourseOffering(foInfo.getCourseOfferingId(), context);

        return createCourseWaitlist(aoInfo.getFormatOfferingId(), aoInfo.getId(), coInfo.getHasWaitlist(), context);


    }

    /* Create a new CourseWaitListInfo (CWLI) for a specified AO and persist it in DB

        If possible, use this method. The other Method is MUCH less efficient.

       1)set AOInfo.id to courseWaitListInfo.activityOfferingIds
       2)set AOInfo.formatOfferingId to courseWaitListInfo.formatOfferingIds
       3)if COInfo.hasWaitList = true, set courseWaitListInfo.stateKey to active and set automaticallyProcessed,
            confirmationRequired, checkInRequired, and allowHoldUntilEntries in courseWaitListInfo to true
       4)if COInfo.hasWaitList = false, set courseWaitListInfo.stateKey to inactive and set automaticallyProcessed,
            confirmationRequired, checkInRequired, and allowHoldUntilEntries in courseWaitListInfo to false
    */
    public static CourseWaitListInfo createCourseWaitlist(String foId, String aoId, boolean coHasWaitlist, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, DoesNotExistException, DataValidationErrorException, ReadOnlyException {
        CourseWaitListInfo courseWaitListInfo = new CourseWaitListInfo();
        List<String> aoIds = new ArrayList<String>();
        aoIds.add(aoId);
        courseWaitListInfo.setActivityOfferingIds(aoIds);
        List<String> foIds = new ArrayList<String> ();
        foIds.add(foId);
        courseWaitListInfo.setFormatOfferingIds(foIds);
        courseWaitListInfo.setTypeKey(CourseWaitListServiceConstants.COURSE_WAIT_LIST_WAIT_TYPE_KEY);

        //need to get the value of coInfo.hasWaitList to set stateKey and other default values

        if (coHasWaitlist){
            courseWaitListInfo.setStateKey(CourseWaitListServiceConstants.COURSE_WAIT_LIST_ACTIVE_STATE_KEY);
            //default setting is semi-automatic
            courseWaitListInfo.setAllowHoldUntilEntries(true);
            courseWaitListInfo.setAutomaticallyProcessed(true);
            courseWaitListInfo.setConfirmationRequired(true);
            courseWaitListInfo.setCheckInRequired(true);
        }
        else{
            courseWaitListInfo.setStateKey(CourseWaitListServiceConstants.COURSE_WAIT_LIST_INACTIVE_STATE_KEY);
            courseWaitListInfo.setAllowHoldUntilEntries(false);
            courseWaitListInfo.setAutomaticallyProcessed(false);
            courseWaitListInfo.setConfirmationRequired(false);
            courseWaitListInfo.setCheckInRequired(false);
        }
        courseWaitListInfo = getCourseWaitListService().createCourseWaitList(CourseWaitListServiceConstants.COURSE_WAIT_LIST_WAIT_TYPE_KEY,
                courseWaitListInfo, context);
        return courseWaitListInfo;
    }

    protected static CourseOfferingService getCourseOfferingService() {
            return
               (CourseOfferingService) GlobalResourceLoader.getService(new QName(CourseOfferingServiceConstants.NAMESPACE,
                    CourseOfferingServiceConstants.SERVICE_NAME_LOCAL_PART));
    }

    protected static CourseWaitListService getCourseWaitListService() {
        return
          (CourseWaitListService) GlobalResourceLoader.getService(new QName(CourseWaitListServiceConstants.NAMESPACE,
                    CourseWaitListServiceConstants.SERVICE_NAME_LOCAL_PART));

    }

}
