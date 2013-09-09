package org.kuali.student.enrollment.class2.coursewaitlist.service.facade;

import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.coursewaitlist.dto.CourseWaitListInfo;
import org.kuali.student.enrollment.coursewaitlist.service.CourseWaitListService;
import org.kuali.student.r2.common.util.constants.CourseWaitListServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

public class CourseWaitListServiceFacadeImpl implements CourseWaitListServiceFacade{
    @Resource(name="coService")
    private CourseOfferingService coService;
    
    @Resource(name="courseWaitListService")
    private CourseWaitListService courseWaitListService;

    public void setCoService(CourseOfferingService coService) {
        this.coService = coService;
    }
    
    public void setCourseWaitListService(CourseWaitListService courseWaitListService) {
        this.courseWaitListService = courseWaitListService;
    }
    
    public void activateActivityOfferingWaitlistsByCourseOffering(String coId, ContextInfo context) throws Exception {
        List<ActivityOfferingInfo> aoInfos = coService.getActivityOfferingsByCourseOffering(coId, context) ;
        if (aoInfos.size() == 0){
            return;
        }
        for (ActivityOfferingInfo aoInfo : aoInfos) {
            List<CourseWaitListInfo> waitListInfos = courseWaitListService.getCourseWaitListsByActivityOffering(aoInfo.getId(), context);
            if (waitListInfos.size() == 0){
                //create a new waitListInfo with default setting
                CourseWaitListInfo theWaitListInfo = new CourseWaitListInfo();
                List aoIds = new ArrayList<String>();
                aoIds.add(aoInfo.getId());
                theWaitListInfo.setActivityOfferingIds(aoIds);
                List foIds = new ArrayList<String>();
                foIds.add(aoInfo.getFormatOfferingId());
                theWaitListInfo.setFormatOfferingIds(foIds);
                theWaitListInfo = activateCourseWaitListWithDefaultValues(theWaitListInfo);
                courseWaitListService.createCourseWaitList(CourseWaitListServiceConstants.COURSE_WAIT_LIST_WAIT_TYPE_KEY,
                                                           theWaitListInfo,context);
            }
            else{
                for (CourseWaitListInfo waitListInfo : waitListInfos){
                    waitListInfo = activateCourseWaitListWithDefaultValues(waitListInfo);
                    courseWaitListService.updateCourseWaitList(waitListInfo.getId(), waitListInfo, context);
                }
            }
        }
    }

    public void deactivateActivityOfferingWaitlistsByCourseOffering(String coId, ContextInfo context) throws Exception {
        List<ActivityOfferingInfo> aoInfos = coService.getActivityOfferingsByCourseOffering(coId, context) ;
        if (aoInfos.size() == 0){
            return;
        }
        for (ActivityOfferingInfo aoInfo : aoInfos) {
            List<CourseWaitListInfo> waitListInfos = courseWaitListService.getCourseWaitListsByActivityOffering(aoInfo.getId(), context);
            if (waitListInfos.size() == 0){
                //create a new waitListInfo with the inactive state
                CourseWaitListInfo theWaitListInfo = new CourseWaitListInfo();
                List aoIds = new ArrayList<String>();
                aoIds.add(aoInfo.getId());
                theWaitListInfo.setActivityOfferingIds(aoIds);
                List foIds = new ArrayList<String>();
                foIds.add(aoInfo.getFormatOfferingId());
                theWaitListInfo.setFormatOfferingIds(foIds);
                theWaitListInfo.setStateKey(CourseWaitListServiceConstants.COURSE_WAIT_LIST_INACTIVE_STATE_KEY);
                courseWaitListService.createCourseWaitList(CourseWaitListServiceConstants.COURSE_WAIT_LIST_WAIT_TYPE_KEY,
                        theWaitListInfo,context);
            }
            else {
                for (CourseWaitListInfo waitListInfo : waitListInfos){
                    waitListInfo.setStateKey(CourseWaitListServiceConstants.COURSE_WAIT_LIST_INACTIVE_STATE_KEY);
                    courseWaitListService.updateCourseWaitList(waitListInfo.getId(), waitListInfo, context);
                }
            }
        }

    }
    /*
     * automatic -> automaticallyProcessed = true, confirmationRequired = false
     * semi-automatic -> automaticallyProcessed = true, confirmationRequired = true
     * manual -> automaticallyProcessed = false, confirmationRequired = false 
     */
    private CourseWaitListInfo activateCourseWaitListWithDefaultValues(CourseWaitListInfo courseWaitListInfo) {
        courseWaitListInfo.setStateKey(CourseWaitListServiceConstants.COURSE_WAIT_LIST_ACTIVE_STATE_KEY);
        //default setting is semi-automatic
        courseWaitListInfo.setAutomaticallyProcessed(true);
        courseWaitListInfo.setConfirmationRequired(true);
        return courseWaitListInfo;
    }

}
