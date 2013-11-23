package org.kuali.student.enrollment.class2.coursewaitlist.service.facade;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.enrollment.class2.coursewaitlist.model.CourseWaitListEntity;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.coursewaitlist.dto.CourseWaitListInfo;
import org.kuali.student.enrollment.coursewaitlist.service.CourseWaitListService;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
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
import org.kuali.student.r2.core.class1.search.ActivityOfferingSearchServiceImpl;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.core.search.service.SearchService;

import javax.annotation.Resource;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CourseWaitListServiceFacadeImpl implements CourseWaitListServiceFacade {
    @Resource
    private CourseOfferingService coService;

    @Resource
    private CourseWaitListService courseWaitListService;

    private SearchService searchService;

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
        List<ActivityOfferingInfo> aoInfos = getAOFOIdsByCOId(coId);
        if (null == aoInfos || aoInfos.isEmpty()){
            return;
        }

        // get all WLs by AO ids
        ArrayList<String> aoIds = new ArrayList<String>();
        for (ActivityOfferingInfo aoInfo : aoInfos) {
            aoIds.add(aoInfo.getId());
        }
        HashMap<String, List<CourseWaitListInfo>> hm = getWaitListsByAOIds(aoIds);

        // Now check if AO has corresponding WL. If not -> create the default one.
        for (ActivityOfferingInfo aoInfo : aoInfos) {
            if (!hm.containsKey(aoInfo.getId()) || hm.get(aoInfo.getId()).isEmpty()){
                //create a new waitListInfo with default setting
                CourseWaitListInfo theWaitListInfo = createNewWaitList(aoInfo, null);
                getCourseWaitListService().createCourseWaitList(CourseWaitListServiceConstants.COURSE_WAIT_LIST_WAIT_TYPE_KEY,
                        theWaitListInfo, context);
            } else {
                for (CourseWaitListInfo waitListInfo : hm.get(aoInfo.getId())){
                    // check if any co-located AOs have inactive CO-level WL. If so the WL will have to stay inactive, no action is needed
                    boolean hasWaitlistCO = true;

                    // WL size > 1 only when we have co-located AOs, so do the check per CO for each AO
                    if(waitListInfo.getActivityOfferingIds().size() > 1) {
                        ArrayList<String> colocatedAOIds = new ArrayList<String>();
                        colocatedAOIds.addAll(waitListInfo.getActivityOfferingIds());
                        colocatedAOIds.remove(aoInfo.getId());
                        hasWaitlistCO = getHasWaitList(coId, colocatedAOIds, context);
                        if (!hasWaitlistCO) {
                            break;
                        }
                    }

                    // All co-located WLs on CO-level are active, therefore update the existing WL to Active
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
        List<ActivityOfferingInfo> aoInfos = getAOFOIdsByCOId(coId);
        if (aoInfos == null || aoInfos.isEmpty()){
            return;
        }

        // get all WLs by AO ids
        ArrayList<String> aoIds = new ArrayList<String>();
        for (ActivityOfferingInfo aoInfo : aoInfos) {
            aoIds.add(aoInfo.getId());
        }
        HashMap<String, List<CourseWaitListInfo>> hm = getWaitListsByAOIds(aoIds);

        for (ActivityOfferingInfo aoInfo : aoInfos) {
            if (!hm.containsKey(aoInfo.getId()) || hm.get(aoInfo.getId()).isEmpty()){
                //create a new waitListInfo with the inactive state
                CourseWaitListInfo theWaitListInfo = createNewWaitList(aoInfo, CourseWaitListServiceConstants.COURSE_WAIT_LIST_INACTIVE_STATE_KEY);
                courseWaitListService.createCourseWaitList(CourseWaitListServiceConstants.COURSE_WAIT_LIST_WAIT_TYPE_KEY,
                        theWaitListInfo, context);
            } else {
                for (CourseWaitListInfo waitListInfo : hm.get(aoInfo.getId())){
                    waitListInfo.setStateKey(CourseWaitListServiceConstants.COURSE_WAIT_LIST_INACTIVE_STATE_KEY);
                    courseWaitListService.updateCourseWaitList(waitListInfo.getId(), waitListInfo, context);
                }
            }
        }
    }

    private HashMap<String, List<CourseWaitListInfo>> getWaitListsByAOIds(ArrayList<String> aoIDs)
        throws InvalidParameterException, MissingParameterException,
                OperationFailedException, PermissionDeniedException {
        HashMap<String, List<CourseWaitListInfo>> hm = new HashMap();
        List<Object[]> results  = ActivityOfferingSearchServiceImpl.searchForWLsByAoIds(aoIDs);
        for(Object[] resultRow : results){
            CourseWaitListEntity entity = (CourseWaitListEntity) resultRow[0];
            CourseWaitListInfo courseWaitListInfo = entity.toDto();
            String aoId = (String)resultRow[1];
            if (!hm.containsKey(aoId)) {
                ArrayList<CourseWaitListInfo> listWL = new ArrayList<CourseWaitListInfo>();
                listWL.add(courseWaitListInfo);
                hm.put(aoId, listWL);
            } else {
                ArrayList<CourseWaitListInfo> listWL = (ArrayList<CourseWaitListInfo>) hm.get(aoId);
                listWL.add(courseWaitListInfo);
                hm.put(aoId, listWL);
            }
        }

        return hm;
    }

    private List<ActivityOfferingInfo> getAOFOIdsByCOId(String coId) throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException {
        SearchRequestInfo sr = new SearchRequestInfo(ActivityOfferingSearchServiceImpl.AO_AND_FO_IDS_BY_CO_ID_SEARCH_KEY);
        sr.addParam(ActivityOfferingSearchServiceImpl.SearchParameters.CO_ID, coId);
        SearchResultInfo searchResult = getSearchService().search(sr, null);

        List<ActivityOfferingInfo> aoInfos = new ArrayList<ActivityOfferingInfo>();
        if (!searchResult.getRows().isEmpty()) {
            for(SearchResultRowInfo srRow : searchResult.getRows()){
                List<SearchResultCellInfo> srCells = srRow.getCells();
                if(srCells != null && srCells.size() > 0){
                    ActivityOfferingInfo aoInfo = new ActivityOfferingInfo();
                    for(SearchResultCellInfo cell : srCells){
                        if(ActivityOfferingSearchServiceImpl.SearchResultColumns.AO_ID.equals(cell.getKey())){
                            aoInfo.setId(cell.getValue());
                        } else if(ActivityOfferingSearchServiceImpl.SearchResultColumns.FO_ID.equals(cell.getKey())){
                            aoInfo.setFormatOfferingId(cell.getValue());
                        }
                    }
                    aoInfos.add(aoInfo);
                }
            }
        }
        return aoInfos;
    }

    private boolean getHasWaitList(String coId, ArrayList<String> aoIds, ContextInfo context) throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException {
        boolean hasWaitlistCO = true;

        SearchRequestInfo sr = new SearchRequestInfo(ActivityOfferingSearchServiceImpl.WL_IND_BY_AO_IDS_SEARCH_KEY);
        sr.addParam(ActivityOfferingSearchServiceImpl.SearchParameters.AO_IDS, aoIds);
        sr.addParam(ActivityOfferingSearchServiceImpl.SearchParameters.CO_ID, coId);
        SearchResultInfo searchResult = getSearchService().search(sr, null);
        if (!searchResult.getRows().isEmpty()) {
            for(SearchResultRowInfo srRow : searchResult.getRows()){
                List<SearchResultCellInfo> srCells = srRow.getCells();
                 for (SearchResultCellInfo cell: srCells) {
                     if (ActivityOfferingSearchServiceImpl.SearchResultColumns.WL_IND.equals(cell.getKey())){
                         if (!StringUtils.equals(cell.getValue(), "true")) {
                             hasWaitlistCO = false;
                             break;
                         }
                     }
                }
            }
        }

        return hasWaitlistCO;
    }

    private CourseWaitListInfo createNewWaitList(ActivityOfferingInfo aoInfo, String stateKey) {
        CourseWaitListInfo theWaitListInfo = new CourseWaitListInfo();
        theWaitListInfo.getActivityOfferingIds().add(aoInfo.getId());
        theWaitListInfo.getFormatOfferingIds().add(aoInfo.getFormatOfferingId());
        if (stateKey != null && StringUtils.equals(stateKey, CourseWaitListServiceConstants.COURSE_WAIT_LIST_INACTIVE_STATE_KEY)) {
            theWaitListInfo.setStateKey(stateKey);
        } else {
            theWaitListInfo = _setCourseWaitListWithDefaultValues(theWaitListInfo);
        }

        return theWaitListInfo;
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

    public SearchService getSearchService() {
        if (searchService == null) {
            searchService = (SearchService) GlobalResourceLoader.getService(new QName(CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "search", SearchService.class.getSimpleName()));
        }
        return searchService;
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
