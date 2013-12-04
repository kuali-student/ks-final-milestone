package org.kuali.student.enrollment.class2.coursewaitlist.service.facade;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.coursewaitlist.dto.CourseWaitListInfo;
import org.kuali.student.enrollment.coursewaitlist.service.CourseWaitListService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseWaitListServiceConstants;

import javax.annotation.Resource;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;

public class CourseWaitListServiceFacadeImpl implements CourseWaitListServiceFacade {
    @Resource
    private CourseOfferingService coService;
    
    @Resource
    private CourseWaitListService courseWaitListService;


    private boolean automaticallyProcessed;

    //@Resource
    private boolean confirmationRequired;

    //@Resource
    private boolean allowHoldUntilEntries;

    //@Resource
    private boolean checkInRequired;

    //This is the attribute defined in COInfo
    //@Resource
    private boolean hasWaitlist;


    /**
     *
     * This method creates/updates new waitListInfo with inactive state for AO from CO.
     * <p>
     *     when activate waitlist in CO level, we want to automatically activate all waitlists in associated AO level.
     * </p>
     *
     * @param coId input Course Offering id
     * @param context context of the call
     */
    public void activateActivityOfferingWaitlistsByCourseOffering(String coId, String termId, ContextInfo context) throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException, VersionMismatchException {
        List<ActivityOfferingInfo> aoInfos = getCoService().getActivityOfferingsByCourseOffering(coId, context) ;
        if (null == aoInfos || aoInfos.isEmpty()){
            return;
        }
        for (ActivityOfferingInfo aoInfo : aoInfos) {
            List<CourseWaitListInfo> waitListInfos = getCourseWaitListService().getCourseWaitListsByActivityOffering(aoInfo.getId(), context);
            if (waitListInfos.isEmpty()){
                //create a new waitListInfo with default setting
                CourseWaitListInfo theWaitListInfo = new CourseWaitListInfo();
                theWaitListInfo.getActivityOfferingIds().add(aoInfo.getId());
                theWaitListInfo.getFormatOfferingIds().add(aoInfo.getFormatOfferingId());
                theWaitListInfo = _setCourseWaitListWithDefaultValues(theWaitListInfo);
                getCourseWaitListService().createCourseWaitList(CourseWaitListServiceConstants.COURSE_WAIT_LIST_WAIT_TYPE_KEY,
                        theWaitListInfo, context);
            }
            else{
                for (CourseWaitListInfo waitListInfo : waitListInfos){
                    // check if any co-located AOs have inactive CO-level WL
                    boolean hasWaitlistCO = true;
                    if(waitListInfo.getActivityOfferingIds().size() > 1) {
                        for (String activityOfferingId : waitListInfo.getActivityOfferingIds()) {
                            if (!StringUtils.equals(activityOfferingId, aoInfo.getId())) {
                                QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
                                qbcBuilder.setPredicates(PredicateFactory.and(
                                        PredicateFactory.like("aoid", activityOfferingId),
                                        PredicateFactory.equalIgnoreCase("atpId", termId)));
                                QueryByCriteria criteria = qbcBuilder.build();
                                List<String> courseOfferingIds = getCoService().searchForCourseOfferingIds(criteria, context);
                                for (String courseOfferingId : courseOfferingIds) {
                                    CourseOfferingInfo coloCourseOfferingInfo = getCoService().getCourseOffering(courseOfferingId, context);
                                    if (!coloCourseOfferingInfo.getHasWaitlist()) {
                                        hasWaitlistCO = false;
                                    }
                                }
                                if (!hasWaitlistCO) {
                                    break;
                                }
                            }
                        }
                    }

                    if (hasWaitlistCO) {
                        waitListInfo = _setCourseWaitListWithDefaultValues(waitListInfo);
                        getCourseWaitListService().updateCourseWaitList(waitListInfo.getId(), waitListInfo, context);
                    }
                }
            }
        }
    }

    /**
     *
     * This method creates/updates new waitListInfo for AO from CO.
     * <p>
     *     when activate waitlist(with inactive state) in CO level, we want to automatically activate all waitlists (with inactive state) in associated AO level.
     * </p>
     *
     * @param coId input Course Offering id
     * @param context context of the call
     */

    public void deactivateActivityOfferingWaitlistsByCourseOffering(String coId, ContextInfo context) throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException, VersionMismatchException {
        List<ActivityOfferingInfo> aoInfos = coService.getActivityOfferingsByCourseOffering(coId, context) ;
        if (aoInfos == null || aoInfos.isEmpty()){
            return;
        }
        for (ActivityOfferingInfo aoInfo : aoInfos) {
            List<CourseWaitListInfo> waitListInfos = courseWaitListService.getCourseWaitListsByActivityOffering(aoInfo.getId(), context);

            if(waitListInfos == null)    {
                return;
            }
            if (waitListInfos.isEmpty()){
                //create a new waitListInfo with the inactive state
                CourseWaitListInfo theWaitListInfo = new CourseWaitListInfo();
                theWaitListInfo.getActivityOfferingIds().add(aoInfo.getId());
                theWaitListInfo.getFormatOfferingIds().add(aoInfo.getFormatOfferingId());
                theWaitListInfo.setStateKey(CourseWaitListServiceConstants.COURSE_WAIT_LIST_INACTIVE_STATE_KEY);
                courseWaitListService.createCourseWaitList(CourseWaitListServiceConstants.COURSE_WAIT_LIST_WAIT_TYPE_KEY,
                        theWaitListInfo, context);
            }
            else {
                for (CourseWaitListInfo waitListInfo : waitListInfos){
                    waitListInfo.setStateKey(CourseWaitListServiceConstants.COURSE_WAIT_LIST_INACTIVE_STATE_KEY);
                    courseWaitListService.updateCourseWaitList(waitListInfo.getId(), waitListInfo, context);
                }
            }
        }
    }

    /* Create a new CourseWaitListInfo (CWLI) for a specified AO and persist it in DB
       1)set AOInfo.id to courseWaitListInfo.activityOfferingIds
       2)set AOInfo.formatOfferingId to courseWaitListInfo.formatOfferingIds
       3)if COInfo.hasWaitList = true, set courseWaitListInfo.stateKey to active and set automaticallyProcessed,
            confirmationRequired, checkInRequired, and allowHoldUntilEntries in courseWaitListInfo to true
       4)if COInfo.hasWaitList = false, set courseWaitListInfo.stateKey to inactive and set automaticallyProcessed,
            confirmationRequired, checkInRequired, and allowHoldUntilEntries in courseWaitListInfo to false
    */
    public CourseWaitListInfo createDefaultCourseWaitlist(ActivityOfferingInfo aoInfo, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, DoesNotExistException, DataValidationErrorException, ReadOnlyException {

        //need to get the value of coInfo.hasWaitList to set stateKey and other default values
        FormatOfferingInfo foInfo = coService.getFormatOffering(aoInfo.getFormatOfferingId(), context);
        CourseOfferingInfo coInfo = coService.getCourseOffering(foInfo.getCourseOfferingId(), context);

        return createDefaultCourseWaitlist(aoInfo.getFormatOfferingId(), aoInfo.getId(), coInfo.getHasWaitlist(), context);


    }

    /**
     *  Create a new CourseWaitListInfo (CWLI) for a specified AO and persist it in DB
     *
     *  If possible, use this method. The other Method is MUCH less efficient.
     *
     * 1) Set AOInfo.id to courseWaitListInfo.activityOfferingIds
     * 2) Set AOInfo.formatOfferingId to courseWaitListInfo.formatOfferingIds
     * 3) Set automaticallyProcessed, confirmationRequired, checkInRequired, and allowHoldUntilEntries to values
     *    injected in by spring into this facade (see instance variables by the same name.
     * 4) If COInfo.hasWaitList = true, set courseWaitListInfo.stateKey to active
     * 5) If COInfo.hasWaitList = false, set courseWaitListInfo.stateKey to inactive
     */
    public CourseWaitListInfo createDefaultCourseWaitlist(String foId, String aoId, boolean coHasWaitlist, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, DoesNotExistException, DataValidationErrorException, ReadOnlyException {
        CourseWaitListInfo courseWaitListInfo = new CourseWaitListInfo();

        // Need to get the value of coInfo.hasWaitList to set stateKey and other default values
        _setCourseWaitListWithDefaultValues(courseWaitListInfo);
        if (coHasWaitlist) {
            courseWaitListInfo.setStateKey(CourseWaitListServiceConstants.COURSE_WAIT_LIST_ACTIVE_STATE_KEY);
        } else {
            courseWaitListInfo.setStateKey(CourseWaitListServiceConstants.COURSE_WAIT_LIST_INACTIVE_STATE_KEY);
        }
        courseWaitListInfo.getActivityOfferingIds().add(aoId);
        courseWaitListInfo.getFormatOfferingIds().add(foId);
        // Create the wait list
        courseWaitListInfo = courseWaitListService.createCourseWaitList(CourseWaitListServiceConstants.COURSE_WAIT_LIST_WAIT_TYPE_KEY,
                courseWaitListInfo, context);
        return courseWaitListInfo;
    }

    /*
     * automatic -> automaticallyProcessed = true, confirmationRequired = false
     * semi-automatic -> automaticallyProcessed = true, confirmationRequired = true
     * manual -> automaticallyProcessed = false, confirmationRequired = false 
     */
    private CourseWaitListInfo _setCourseWaitListWithDefaultValues(CourseWaitListInfo courseWaitListInfo) {
        courseWaitListInfo.setStateKey(CourseWaitListServiceConstants.COURSE_WAIT_LIST_ACTIVE_STATE_KEY);
        // default setting is semi-automatic
        courseWaitListInfo.setAutomaticallyProcessed(automaticallyProcessed);
        courseWaitListInfo.setConfirmationRequired(confirmationRequired);

        courseWaitListInfo.setAllowHoldUntilEntries(allowHoldUntilEntries);
        courseWaitListInfo.setCheckInRequired(checkInRequired);
        return courseWaitListInfo;
    }

    public void setCoService(CourseOfferingService coService) {
        this.coService = coService;
    }

    public void setCourseWaitListService(CourseWaitListService courseWaitListService) {
        this.courseWaitListService = courseWaitListService;
    }


    public CourseOfferingService getCoService() {
        if (coService == null) {
            QName qname = new QName(CourseOfferingServiceConstants.NAMESPACE,
                    CourseOfferingServiceConstants.SERVICE_NAME_LOCAL_PART);
            coService = GlobalResourceLoader.getService(qname);
        }
        return coService;
    }

    public CourseWaitListService getCourseWaitListService() {
        if (courseWaitListService == null) {
            QName qname = new QName(CourseWaitListServiceConstants.NAMESPACE,
                    CourseWaitListServiceConstants.SERVICE_NAME_LOCAL_PART);
            courseWaitListService = GlobalResourceLoader.getService(qname);
        }
        return courseWaitListService;
    }

    public void setAllowHoldUntilEntries(boolean allowHoldUntilEntries) {
        this.allowHoldUntilEntries = allowHoldUntilEntries;
    }

    public void setAutomaticallyProcessed(boolean automaticallyProcessed) {
        this.automaticallyProcessed = automaticallyProcessed;
    }
    public void setCheckInRequired(boolean checkInRequired) {
        this.checkInRequired = checkInRequired;
    }

    public void setConfirmationRequired(boolean confirmationRequired) {
        this.confirmationRequired = confirmationRequired;
    }

    public boolean getHasWaitlist() {
        return hasWaitlist;
    }

    public void setHasWaitlist(boolean hasWaitlist) {
        this.hasWaitlist = hasWaitlist;
    }
}
