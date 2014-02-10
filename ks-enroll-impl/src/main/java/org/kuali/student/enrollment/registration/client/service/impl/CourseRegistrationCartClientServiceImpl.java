package org.kuali.student.enrollment.registration.client.service.impl;


import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestItemInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationResponseInfo;
import org.kuali.student.enrollment.courseregistration.infc.RegistrationRequest;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.enrollment.registration.client.service.CourseRegistrationCartClientService;
import org.kuali.student.enrollment.registration.client.service.dto.ActivityOfferingLocationTimeResult;
import org.kuali.student.enrollment.registration.client.service.dto.ActivityOfferingScheduleResult;
import org.kuali.student.enrollment.registration.client.service.dto.CartItemInfoResult;
import org.kuali.student.enrollment.registration.client.service.dto.RegGroupSearchResult;
import org.kuali.student.enrollment.registration.client.service.dto.ScheduleLocationResult;
import org.kuali.student.enrollment.registration.client.service.dto.ScheduleTimeResult;
import org.kuali.student.enrollment.registration.client.service.impl.util.CourseRegistrationAndScheduleOfClassesUtil;
import org.kuali.student.enrollment.registration.search.service.impl.CourseRegistrationSearchServiceImpl;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.TimeOfDayInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.TimeOfDayHelper;
import org.kuali.student.r2.common.util.constants.CourseRegistrationServiceConstants;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;

import javax.security.auth.login.LoginException;
import javax.ws.rs.core.Response;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 */
public class CourseRegistrationCartClientServiceImpl implements CourseRegistrationCartClientService {

    public static final Logger LOGGER = Logger.getLogger(CourseRegistrationCartClientServiceImpl.class);
    private CourseRegistrationService courseRegistrationService;

    @Override
    public Response submitCartRS(String userId, String cartId) {
        Response.ResponseBuilder response;

        try {
            submitCart(userId, cartId);
            response = Response.ok(Boolean.TRUE);
        } catch (Throwable t) {
            LOGGER.warn(t);
            response = Response.serverError().entity(t.getMessage());
        }

        return response.build();
    }

    @Override
    public RegistrationResponseInfo submitCart(String userId, String cartId) throws InvalidParameterException, MissingParameterException, DoesNotExistException, OperationFailedException, PermissionDeniedException, AlreadyExistsException, LoginException {
        ContextInfo contextInfo = getContextAndCheckLogin(userId);

        //Make sure that the user is the owner of the cart!
        CourseRegistrationInfo courseRegistrationInfo = getCourseRegistrationService().getCourseRegistration(cartId, contextInfo);
        if (!StringUtils.equals(courseRegistrationInfo.getPersonId(), contextInfo.getPrincipalId())) {
            throw new PermissionDeniedException("User does not have permission to submit on this registration cart");
        }

        //Call submit on the registration service
        RegistrationResponseInfo registrationResponse = getCourseRegistrationService().submitRegistrationRequest(cartId, contextInfo);

        return registrationResponse;
    }

    @Override
    public CartItemInfoResult addCourseToCart(String cartId, String courseCode, String termId, String termCode, String regGroupCode, String gradingOptionId, String credits) throws MissingParameterException, PermissionDeniedException, InvalidParameterException, OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException, VersionMismatchException {
        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

        RegistrationRequestInfo cart = getCourseRegistrationService().getRegistrationRequest(cartId, contextInfo);

        // get the regGroup
        RegGroupSearchResult rg = CourseRegistrationAndScheduleOfClassesUtil.getRegGroup(termCode, courseCode, regGroupCode, null, contextInfo);

        // get the registration group, returns default (from Course Offering) credits (as creditId) and grading options (as a string of options)
        CourseOfferingInfo courseOfferingInfo = CourseRegistrationAndScheduleOfClassesUtil.getCourseOfferingIdCreditGrading(rg.getCourseOfferingId(), courseCode, termId, null);


        RegistrationRequestItemInfo registrationRequestItem = new RegistrationRequestItemInfo();
        registrationRequestItem.setTypeKey(LprServiceConstants.REQ_ITEM_ADD_TYPE_KEY);
        registrationRequestItem.setStateKey(LprServiceConstants.LPRTRANS_ITEM_NEW_STATE_KEY);
        registrationRequestItem.setRegistrationGroupId(rg.getRegGroupId());
        registrationRequestItem.setPersonId(cart.getRequestorId());
        registrationRequestItem.setCredits(new KualiDecimal(credits));
        registrationRequestItem.setGradingOptionId(gradingOptionId);

        cart.getRegistrationRequestItems().add(registrationRequestItem);

        getCourseRegistrationService().updateRegistrationRequest(cartId, cart, contextInfo);


        CartItemInfoResult cartItemInfo = getCartItemInfoResult(rg.getActivityOfferingIds(), contextInfo);

        cartItemInfo.setCredits(credits);
        cartItemInfo.setGrading(gradingOptionId);

        return cartItemInfo;
    }

    @Override
    public Response updateCartItemRS(String userId, String cartId, String cartItemId, String credits, String grading) {
        Response.ResponseBuilder response;

        try {
            //This will need to be changed to the cartItemResponse object in the future!
            response = Response.ok(updateCartItem(userId, cartId, cartItemId, credits, grading));
        } catch (Throwable t) {
            LOGGER.warn(t);
            response = Response.serverError().entity(t.getMessage());
        }

        return response.build();
    }

    @Override
    //This will need to be changed to the cartItemResponse object in the future!
    public RegistrationRequestItemInfo updateCartItem(String userId, String cartId, String cartItemId, String credits, String grading) throws LoginException, InvalidParameterException, MissingParameterException, DoesNotExistException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, ReadOnlyException, VersionMismatchException {
        ContextInfo contextInfo = getContextAndCheckLogin(userId);

        //Get the Cart from services
        RegistrationRequestInfo cart = getCourseRegistrationService().getRegistrationRequest(cartId, contextInfo);

        //Check that it is the users' cart
        if (!StringUtils.equals(cart.getRequestorId(), contextInfo.getPrincipalId())) {
            throw new PermissionDeniedException("User does not have permission to edit items on this registration cart");
        }

        //Find the matching cartItem id to edit the registration options
        for (RegistrationRequestItemInfo requestItem : cart.getRegistrationRequestItems()) {
            if (StringUtils.equals(cartItemId, requestItem.getId())) {
                //Set the Item registration options
                requestItem.setCredits(new KualiDecimal(credits));
                requestItem.setGradingOptionId(grading);
                //Save the newly updated cart
                RegistrationRequestInfo updatedCart = getCourseRegistrationService().updateRegistrationRequest(cartId, cart, contextInfo);
                //Look for the updated request item to return
                for (RegistrationRequestItemInfo updatedRequestItem : updatedCart.getRegistrationRequestItems()) {
                    if (StringUtils.equals(cartItemId, updatedRequestItem.getId())) {
                        return updatedRequestItem;
                    }
                }
            }
        }
        throw new DoesNotExistException("No matching cart item was found.");
    }

    private CartItemInfoResult getCartItemInfoResult(List<String> aoIDs, ContextInfo contextInfo) throws OperationFailedException, InvalidParameterException {
        CartItemInfoResult cartItemInfo = new CartItemInfoResult();
        HashMap<String, ActivityOfferingScheduleResult> hmSchedules = new HashMap<String, ActivityOfferingScheduleResult>();

        SearchRequestInfo searchRequest = new SearchRequestInfo(CourseRegistrationSearchServiceImpl.AO_SCHEDULES_BY_AO_IDS_SEARCH_TYPE.getKey());
        searchRequest.addParam(CourseRegistrationSearchServiceImpl.SearchParameters.AO_IDS, aoIDs);

        SearchResultInfo searchResult;
        try {
            searchResult = CourseRegistrationAndScheduleOfClassesUtil.getSearchService().search(searchRequest, contextInfo);
        } catch (Exception e) {
            throw new OperationFailedException("Search of activity offering schedules failed: ", e);
        }

        for (SearchResultRowInfo row : searchResult.getRows()) {
            String luiId = "", luiName = "", roomCode = "", buildingCode = "", weekdays = "", startTimeMs = "", endTimeMs = "";
            for (SearchResultCellInfo cellInfo : row.getCells()) {
                if (CourseRegistrationSearchServiceImpl.SearchResultColumns.LUI_ID.equals(cellInfo.getKey())) {
                    luiId = cellInfo.getValue();
                } else if (CourseRegistrationSearchServiceImpl.SearchResultColumns.LUI_NAME.equals(cellInfo.getKey())) {
                    luiName = cellInfo.getValue();
                } else if (CourseRegistrationSearchServiceImpl.SearchResultColumns.ROOM_CODE.equals(cellInfo.getKey())) {
                    roomCode = cellInfo.getValue();
                } else if (CourseRegistrationSearchServiceImpl.SearchResultColumns.BUILDING_CODE.equals(cellInfo.getKey())) {
                    buildingCode = cellInfo.getValue();
                } else if (CourseRegistrationSearchServiceImpl.SearchResultColumns.WEEKDAYS.equals(cellInfo.getKey())) {
                    weekdays = cellInfo.getValue();
                } else if (CourseRegistrationSearchServiceImpl.SearchResultColumns.START_TIME_MS.equals(cellInfo.getKey())) {
                    startTimeMs = cellInfo.getValue();
                } else if (CourseRegistrationSearchServiceImpl.SearchResultColumns.END_TIME_MS.equals(cellInfo.getKey())) {
                    endTimeMs = cellInfo.getValue();
                }

                // setting location and time
                // AO location
                ScheduleLocationResult location = new ScheduleLocationResult();
                location.setBuilding(buildingCode);
                location.setRoom(roomCode);
                // AO times
                ScheduleTimeResult time = new ScheduleTimeResult();
                TimeOfDayInfo startTime = TimeOfDayHelper.setMillis(Long.valueOf(startTimeMs));
                time.setStartTime(TimeOfDayHelper.formatTimeOfDay(startTime));
                TimeOfDayInfo endTime = TimeOfDayHelper.setMillis(Long.valueOf(endTimeMs));
                time.setEndTime(TimeOfDayHelper.formatTimeOfDay(endTime));
                time.setDays(weekdays);
                // Combining location + time for AO
                ActivityOfferingLocationTimeResult aoLocationTime = new ActivityOfferingLocationTimeResult();
                aoLocationTime.setLocation(location);
                aoLocationTime.setTime(time);
                // now add location + time to the final result
                if (!hmSchedules.containsKey(luiId)) {
                    ActivityOfferingScheduleResult aoSchedule = new ActivityOfferingScheduleResult();
                    aoSchedule.setActivityOfferingType(luiName);
                    List<ActivityOfferingLocationTimeResult> aoLocationTimes = new ArrayList<ActivityOfferingLocationTimeResult>();
                    aoLocationTimes.add(aoLocationTime);
                    aoSchedule.setActivityOfferingLocationTime(aoLocationTimes);
                } else {
                    hmSchedules.get(luiId).getActivityOfferingLocationTime().add(aoLocationTime);
                }
            }
        }

        return cartItemInfo;
    }

    private ContextInfo getContextAndCheckLogin(String userId) throws LoginException {
        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

        if (!StringUtils.isEmpty(userId)) {
            contextInfo.setPrincipalId(userId);
        }

        if (StringUtils.isEmpty(contextInfo.getPrincipalId())) {
            throw new LoginException("User must be logged in to access this service");
        }

        return contextInfo;
    }

    public CourseRegistrationService getCourseRegistrationService() {
        if (courseRegistrationService == null) {
            courseRegistrationService = (CourseRegistrationService) GlobalResourceLoader.getService(new QName(CourseRegistrationServiceConstants.NAMESPACE, CourseRegistrationServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return courseRegistrationService;
    }

    public void setCourseRegistrationService(CourseRegistrationService courseRegistrationService) {
        this.courseRegistrationService = courseRegistrationService;
    }
}
