package org.kuali.student.enrollment.registration.engine.service.impl;

import org.apache.cxf.common.util.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.infc.RegistrationGroup;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestItemInfo;
import org.kuali.student.enrollment.courseregistration.infc.RegistrationRequestItem;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.enrollment.courseseatcount.infc.SeatCount;
import org.kuali.student.enrollment.courseseatcount.service.CourseSeatCountService;
import org.kuali.student.enrollment.lpr.dto.LprInfo;
import org.kuali.student.enrollment.lpr.dto.LprTransactionItemInfo;
import org.kuali.student.enrollment.lpr.infc.Lpr;
import org.kuali.student.enrollment.lpr.service.LprService;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.enrollment.registration.client.service.impl.util.RegistrationValidationResultsUtil;
import org.kuali.student.enrollment.registration.client.service.impl.util.SearchResultHelper;
import org.kuali.student.enrollment.registration.engine.dto.RegistrationRequestEngineMessage;
import org.kuali.student.enrollment.registration.engine.dto.RegistrationRequestItemEngineMessage;
import org.kuali.student.enrollment.registration.engine.service.CourseRegistrationEngineService;
import org.kuali.student.enrollment.registration.engine.service.WaitlistManagerService;
import org.kuali.student.enrollment.registration.search.service.impl.CourseRegistrationSearchServiceImpl;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.infc.ValidationResult;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseRegistrationServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseSeatCountServiceConstants;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.constants.SearchServiceConstants;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.service.SearchService;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseRegistrationEngineServiceImpl implements CourseRegistrationEngineService {
    public static final Logger LOGGER = LoggerFactory.getLogger(CourseRegistrationEngineServiceImpl.class);

    // -------------------------------------
    private CourseRegistrationService courseRegistrationService;
    private LuiService luiService;
    private LprService lprService;
    private SearchService searchService;
    private CourseSeatCountService courseSeatCountService;
    private CourseOfferingService courseOfferingService;
    private WaitlistManagerService waitlistManagerService;

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public LprTransactionItemInfo updateLprTransactionItemResult(String lprTransactionId, String lprTransactionItemId, String lprTransactionItemStateKey, String resultingLprId, String message, boolean status, ContextInfo contextInfo) throws DoesNotExistException, PermissionDeniedException, OperationFailedException, VersionMismatchException, InvalidParameterException, MissingParameterException, DataValidationErrorException, ReadOnlyException {
        LprTransactionItemInfo lprTransactionItem = getLprService().getLprTransactionItem(lprTransactionItemId, contextInfo);
        lprTransactionItem.setStateKey(lprTransactionItemStateKey);
        if (LprServiceConstants.LPRTRANS_ITEM_FAILED_STATE_KEY.equals(lprTransactionItemStateKey) ||
                LprServiceConstants.LPRTRANS_ITEM_WAITLIST_AVAILABLE_STATE_KEY.equals(lprTransactionItemStateKey)) {
            lprTransactionItem.getValidationResults().add(new ValidationResultInfo("", ValidationResult.ErrorLevel.ERROR, message));
        }
        lprTransactionItem.setResultingLprId(resultingLprId);

        //update the state and result message;
        return getLprService().changeLprTransactionItem(lprTransactionItem.getId(), lprTransactionItem, contextInfo);

    }

    /**
     * This method will build a LprTransactionInfo object from a regGroup
     *
     * @param regGroupId Registration Group Id
     * @return List of Learning Person Relationships corresponding to the Reg Group, Course, and Activities
     */
    protected List<LprInfo> buildRegisteredLprs(String regGroupId, String termId, String credits,
                                                String gradingOptionKey, Date effDate, ContextInfo context) {
        // RG LPR type key, CO LPR type key, and AO LPR type key (in that order)
        List<LprInfo> result = buildLprsCommon(LprServiceConstants.REGISTRANT_RG_LPR_TYPE_KEY,
                LprServiceConstants.REGISTRANT_CO_LPR_TYPE_KEY,
                LprServiceConstants.REGISTRANT_AO_LPR_TYPE_KEY, regGroupId, termId,
                credits, gradingOptionKey, effDate, context);
        return result;
    }

    protected List<LprInfo> buildLprsCommon(String regGroupLprType,
                                            String courseOfferingLprType,
                                            String activityOfferingLprType,
                                            String regGroupId, String termId, String credits,
                                            String gradingOptionKey, Date effDate, ContextInfo context) {
        List<LprInfo> result = new ArrayList<LprInfo>();
        // Get AO Lui's by reg grup
        //List<LuiInfo> ao1List = getLuiService().getLuisByRelatedLuiAndRelationType(registrationRequestInfo.get);
        LuiService luiServiceLocal = getLuiService();
        try {
            // Get the CO
            List<String> foIds = luiServiceLocal.getLuiIdsByRelatedLuiAndRelationType(regGroupId,
                    LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_FO_TO_RG_TYPE_KEY, context);
            String foId = KSCollectionUtils.getRequiredZeroElement(foIds);
            List<String> coIds = luiServiceLocal.getLuiIdsByRelatedLuiAndRelationType(foId,
                    LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_CO_TO_FO_TYPE_KEY, context);
            String coId = KSCollectionUtils.getRequiredZeroElement(coIds);

            String activeStateKey = LprServiceConstants.ACTIVE_STATE_KEY;
            // Create RG LPR
            LprInfo rgLprCreated = makeLpr(regGroupLprType, regGroupId,
                    null, effDate, termId, credits, gradingOptionKey, activeStateKey, context);
            result.add(rgLprCreated);

            // Create CO LPR
            LprInfo coLprCreated = makeLpr(courseOfferingLprType, coId, rgLprCreated.getMasterLprId(),
                    effDate, termId, credits, gradingOptionKey, activeStateKey, context);
            result.add(coLprCreated);

            // Set credits and gradingOptionsKey to null so only the RG LPR (masterLpr) and CO LPR have those values set.
            credits = null;
            gradingOptionKey = null;

            // Create AO LPRs
            List<String> aoIds = luiServiceLocal.getLuiIdsByLuiAndRelationType(regGroupId,
                    LuiServiceConstants.LUI_LUI_RELATION_REGISTERED_FOR_VIA_RG_TO_AO_TYPE_KEY, context);
            for (String aoId : aoIds) {
                LprInfo aoLprCreated = makeLpr(activityOfferingLprType, aoId, rgLprCreated.getMasterLprId(),
                        effDate, termId, credits, gradingOptionKey, activeStateKey, context);
                result.add(aoLprCreated);
            }

        } catch (Exception ex) {
            throw new RuntimeException("Error building LPR items", ex);
        }
        return result;
    }

    private LprInfo makeLpr(String lprType, String luiId, String masterLprId, Date effDate,
                            String atpId, String credits, String gradingOptionKey, String stateKey, ContextInfo context)
            throws DoesNotExistException, PermissionDeniedException, OperationFailedException,
            InvalidParameterException, ReadOnlyException, MissingParameterException,
            DataValidationErrorException {
        LprInfo lpr = new LprInfo();
        lpr.setTypeKey(lprType);
        lpr.setStateKey(stateKey);
        lpr.setPersonId(context.getPrincipalId());
        lpr.setLuiId(luiId);
        lpr.setMasterLprId(masterLprId);
        lpr.setEffectiveDate(effDate);
        lpr.setAtpId(atpId);
        if (!StringUtils.isEmpty(credits)) {
            lpr.getResultValuesGroupKeys().add(LrcServiceConstants.RESULT_VALUE_KEY_CREDIT_DEGREE_PREFIX + credits);
        }
        if (!StringUtils.isEmpty(gradingOptionKey)) {
            lpr.getResultValuesGroupKeys().add(gradingOptionKey);
        }
        return getLprService().createLpr(lpr.getPersonId(), lpr.getLuiId(), lpr.getTypeKey(), lpr, context);
    }

    private List<String> getLprIdsByMasterLprId(String masterLprId,
                                                ContextInfo contextInfo) throws OperationFailedException {
        return getLprIdsByMasterLprId(masterLprId, null, null, contextInfo);
    }

    private List<String> getLprIdsByMasterLprId(String masterLprId,
                                                String lprType,
                                                List<String> lprStates,
                                                ContextInfo contextInfo) throws OperationFailedException {
        List<String> lprIds = new ArrayList<String>();

        SearchRequestInfo searchRequest = new SearchRequestInfo(CourseRegistrationSearchServiceImpl.LPRIDS_BY_MASTER_LPR_ID_SEARCH_TYPE.getKey());
        searchRequest.addParam(CourseRegistrationSearchServiceImpl.SearchParameters.MASTER_LPR_ID, masterLprId);
        if (lprType != null) {
            searchRequest.addParam(CourseRegistrationSearchServiceImpl.SearchParameters.LPR_TYPE, lprType);
        }
        if (lprStates != null && !lprStates.isEmpty()) {
            searchRequest.addParam(CourseRegistrationSearchServiceImpl.SearchParameters.LPR_STATES, lprStates);
        }
        SearchResultInfo searchResult;
        try {
            searchResult = getSearchService().search(searchRequest, contextInfo);
        } catch (Exception e) {
            throw new OperationFailedException("Search of lpr ids for master lpr id " + masterLprId + " failed: ", e);
        }

        for (SearchResultHelper.KeyValue row : SearchResultHelper.wrap(searchResult)) {
            lprIds.add(row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.LPR_ID));
        }

        return lprIds;
    }

    @Override
    public List<LprInfo> updateOptionsOnRegisteredLprs(String masterLprId, String credits, String gradingOptionId, Date effDate,
                                                       ContextInfo contextInfo)
            throws OperationFailedException, PermissionDeniedException, DataValidationErrorException, VersionMismatchException,
            InvalidParameterException, ReadOnlyException, MissingParameterException, DoesNotExistException {
        // KSENROLL-12144
        List<LprInfo> updatedLprInfos = updateOptionsOnLprsCommon(LprServiceConstants.REGISTRANT_CO_LPR_TYPE_KEY,
                masterLprId, credits, gradingOptionId, effDate, contextInfo);

        return updatedLprInfos;
    }

    @Override
    public List<LprInfo> dropLprs(String masterLprId, ContextInfo contextInfo)
            throws OperationFailedException, PermissionDeniedException, DataValidationErrorException, VersionMismatchException,
            InvalidParameterException, ReadOnlyException, MissingParameterException, DoesNotExistException {
        // Note: this is used on registrant LPRs and waitlist LPRs

        // get lpr ids based on master lpr id
        List<String> lprIds = getLprIdsByMasterLprId(masterLprId, contextInfo);

        return dropLprs(lprIds, contextInfo);

    }

    /**
     * helper method to perform drop in the smallest possible transactional boundary.
     *
     * @param lprIds list of LPR Ids
     * @param contextInfo context of the call
     * @return list of lprs dropped
     * @throws PermissionDeniedException
     * @throws MissingParameterException
     * @throws InvalidParameterException
     * @throws OperationFailedException
     * @throws DoesNotExistException
     * @throws ReadOnlyException
     * @throws DataValidationErrorException
     * @throws VersionMismatchException
     */
    protected List<LprInfo> dropLprs(List<String> lprIds, ContextInfo contextInfo) throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException, VersionMismatchException {
        List<LprInfo> resultLprInfos = new ArrayList<LprInfo>();

        List<LprInfo> lprInfos = getLprService().getLprsByIds(lprIds, contextInfo);

        Date now = new Date();
        for (LprInfo lprInfo : lprInfos) {
            lprInfo.setStateKey(LprServiceConstants.DROPPED_STATE_KEY);
            if (lprInfo.getExpirationDate() == null) {
                // Only update expiration if it's not already set
                lprInfo.setExpirationDate(now); // KSENROLL-12155
            }
            lprInfo = getLprService().updateLpr(lprInfo.getId(), lprInfo, contextInfo);
            resultLprInfos.add(lprInfo);
        }

        return resultLprInfos;
    }

    @Override
    @Transactional
    public RegistrationRequestEngineMessage initializeRegistrationRequest(String regReqId, ContextInfo contextInfo) throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException, VersionMismatchException {
        //get reg request
        RegistrationRequestInfo registrationRequestInfo
                = getCourseRegistrationService().getRegistrationRequest(regReqId, contextInfo);

        //Set the status on the Request to processing. This leaves the message out of sync as far as state and
        registrationRequestInfo.setStateKey(LprServiceConstants.LPRTRANS_PROCESSING_STATE_KEY);
        for(RegistrationRequestItemInfo requestItemInfo:registrationRequestInfo.getRegistrationRequestItems()){
            requestItemInfo.setStateKey(LprServiceConstants.LPRTRANS_ITEM_PROCESSING_STATE_KEY);
        }
        registrationRequestInfo = getCourseRegistrationService().updateRegistrationRequest(registrationRequestInfo.getId(), registrationRequestInfo, contextInfo);

        //Get reg group info
        Map<String, RegistrationGroup> regGroupIdToRegGroupMap = getRegistrationGroupsForRequest(registrationRequestInfo, contextInfo);

        //Create a message to send through the registration engine
        RegistrationRequestEngineMessage registrationMessage = new RegistrationRequestEngineMessage(registrationRequestInfo, regGroupIdToRegGroupMap, contextInfo);


        return registrationMessage;
    }

    @Override
    public List<LprInfo> addWaitlistLprs(String regGroupId, String termId, String credits, String gradingOptionId, Date effDate, ContextInfo contextInfo) {
        List<LprInfo> lprInfos = buildWaitlistLprs(regGroupId, termId, credits, gradingOptionId, effDate, contextInfo);
        return lprInfos;
    }


    /**
     * This method will build a LprTransactionInfo object from a regGroup
     *
     * @param regGroupId Registration Group Id
     * @return List of Learning Person Relationships corresponding to the Reg Group, Course, and Activities
     */
    protected List<LprInfo> buildWaitlistLprs(String regGroupId, String termId, String credits,
                                              String gradingOptionKey, Date effDate, ContextInfo context) {
        // RG LPR type key, CO LPR type key, and AO LPR type key (in that order)
        List<LprInfo> result = buildLprsCommon(LprServiceConstants.WAITLIST_RG_LPR_TYPE_KEY,
                LprServiceConstants.WAITLIST_CO_LPR_TYPE_KEY,
                LprServiceConstants.WAITLIST_AO_LPR_TYPE_KEY,
                regGroupId, termId,
                credits, gradingOptionKey, effDate, context);
        return result;
    }

    @Override
    public List<LprInfo> updateOptionsOnWaitlistLprs(String masterLprId, String credits, String gradingOptionId, Date effDate,
                                                     ContextInfo contextInfo)
            throws OperationFailedException, PermissionDeniedException, MissingParameterException,
            InvalidParameterException, DoesNotExistException, ReadOnlyException, DataValidationErrorException,
            VersionMismatchException {
        // KSENROLL-12144
        List<LprInfo> updatedLprInfos = updateOptionsOnLprsCommon(LprServiceConstants.WAITLIST_CO_LPR_TYPE_KEY,
                masterLprId, credits, gradingOptionId, effDate, contextInfo);

        return updatedLprInfos;
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public List<LprInfo> addLprsFromWaitlist(String masterLprId, ContextInfo contextInfo)
            throws OperationFailedException, PermissionDeniedException, MissingParameterException,
            InvalidParameterException, DoesNotExistException, ReadOnlyException, DataValidationErrorException,
            VersionMismatchException {

        List<String> waitlistLprIds = getLprIdsByMasterLprId(masterLprId, contextInfo);
        List<LprInfo> expiredLprs = expireWaitlistLprs(waitlistLprIds, contextInfo);
        List<LprInfo> registeredLprs = new ArrayList<LprInfo>();
        List<LprInfo> newRegisteredLprs = new ArrayList<LprInfo>();
        LprInfo newRegisteredMasterLpr = null;
        Date now = new Date();

        for (LprInfo waitlistLpr : expiredLprs) {

            LprInfo registeredLpr = new LprInfo(waitlistLpr); // Make a copy
            registeredLpr.setId(null);
            registeredLpr.setMeta(null);
            registeredLpr.setMasterLprId(null);
            registeredLpr.setExpirationDate(null);
            registeredLpr.setEffectiveDate(now);
            registeredLpr.setResultValuesGroupKeys(waitlistLpr.getResultValuesGroupKeys());
            registeredLpr.setTypeKey(this.convertWaitlistTypesToRegisteredTypes(waitlistLpr.getTypeKey()));
            registeredLpr.setStateKey(LprServiceConstants.ACTIVE_STATE_KEY);

            //Get a handle to the RG lpr since this wil lbe the "master" lpr
            if (LprServiceConstants.REGISTRANT_RG_LPR_TYPE_KEY.equals(registeredLpr.getTypeKey())) {
                newRegisteredMasterLpr = registeredLpr;
            } else {
                newRegisteredLprs.add(registeredLpr);
            }

        }

        //If a new master LPR was found create the new registration records
        if (newRegisteredMasterLpr != null) {
            // Create the new one
            newRegisteredMasterLpr = getLprService().createLpr(newRegisteredMasterLpr.getPersonId(), newRegisteredMasterLpr.getLuiId(),
                    newRegisteredMasterLpr.getTypeKey(), newRegisteredMasterLpr, contextInfo);
            registeredLprs.add(newRegisteredMasterLpr);

            for (LprInfo newRegisteredLpr : newRegisteredLprs) {
                //Set the master lpr id on subsequent Lprs
                newRegisteredLpr.setMasterLprId(newRegisteredMasterLpr.getId());
                registeredLprs.add(getLprService().createLpr(newRegisteredLpr.getPersonId(), newRegisteredLpr.getLuiId(),
                        newRegisteredLpr.getTypeKey(), newRegisteredLpr, contextInfo));
            }

        }

        return registeredLprs;
    }

    protected List<LprInfo> expireWaitlistLprs(List<String> waitlistLprIds, ContextInfo contextInfo) throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException, VersionMismatchException {
        List<LprInfo> retList = new ArrayList<>();
        List<LprInfo> waitlistLprs = getLprService().getLprsByIds(waitlistLprIds, contextInfo);
        Date now = new Date();

        for (LprInfo waitlistLpr : waitlistLprs) {
            //Only process active waitlists
            if (LprServiceConstants.ACTIVE_STATE_KEY.equals(waitlistLpr.getStateKey())) {
                //Expire the waitlisted record
                waitlistLpr.setExpirationDate(now);
                waitlistLpr.setStateKey(LprServiceConstants.RECEIVED_LPR_STATE_KEY);

                // Update the orig
                retList.add(getLprService().updateLpr(waitlistLpr.getId(), waitlistLpr, contextInfo));
            }
        }
        return retList;

    }

    private static String convertWaitlistTypesToRegisteredTypes(String waitlistType) {
        String registeredType = null;

        if (waitlistType.equals(LprServiceConstants.WAITLIST_AO_LPR_TYPE_KEY)) {
            registeredType = LprServiceConstants.REGISTRANT_AO_LPR_TYPE_KEY;

        } else if (waitlistType.equals(LprServiceConstants.WAITLIST_CO_LPR_TYPE_KEY)) {
            registeredType = LprServiceConstants.REGISTRANT_CO_LPR_TYPE_KEY;

        } else if (waitlistType.equals(LprServiceConstants.WAITLIST_RG_LPR_TYPE_KEY)) {
            registeredType = LprServiceConstants.REGISTRANT_RG_LPR_TYPE_KEY;

        }
        return registeredType;
    }

    /**
     * Common code to update LPRs (registrant and waitlists)
     * <p/>
     * Must be transactional to perform rollbacks on the multiple updates
     *
     * @param courseOfferingLprType Either LprServiceConstants.REGISTRANT_CO_LPR_TYPE_KEY
     *                              or LprServiceConstants.WAITLIST_CO_LPR_TYPE_KEY
     * @param masterLprId           The RG LPR for either registrant/waitlist LPR
     * @param credits               RVG credit
     * @param gradingOptionId       RVG grading option
     * @param contextInfo           The context
     * @return List of update LprInfos
     */
    protected List<LprInfo> updateOptionsOnLprsCommon(String courseOfferingLprType,
                                                      String masterLprId, String credits, String gradingOptionId, Date effDate,
                                                      ContextInfo contextInfo)
            throws OperationFailedException, PermissionDeniedException, MissingParameterException,
            InvalidParameterException, DoesNotExistException, ReadOnlyException, DataValidationErrorException,
            VersionMismatchException {
        // Fetch the CO LPR
        // Comment out state fetches for now
        List<String> lprStates = new ArrayList<String>();
        lprStates.add(LprServiceConstants.ACTIVE_STATE_KEY);
        // This is the only place keynameToLprTypesMap is used to look up the Course offering LPR type.
        List<String> coLprIds = getLprIdsByMasterLprId(masterLprId, courseOfferingLprType,
                lprStates, contextInfo);
        String coLprId = KSCollectionUtils.getRequiredZeroElement(coLprIds);
        LprInfo expiredCoLpr = expireLpr(coLprId, contextInfo);
        LprInfo updatedCoLpr = new LprInfo(expiredCoLpr); // Make a copy
        updatedCoLpr.setId(null);
        updatedCoLpr.setMeta(null);
        updatedCoLpr.setExpirationDate(null);
        updatedCoLpr.setEffectiveDate(effDate);
        updatedCoLpr.setStateKey(LprServiceConstants.ACTIVE_STATE_KEY);

        updatedCoLpr.setResultValuesGroupKeys(new ArrayList<String>());
        if (!StringUtils.isEmpty(credits)) {
            updatedCoLpr.getResultValuesGroupKeys().add(LrcServiceConstants.RESULT_VALUE_KEY_CREDIT_DEGREE_PREFIX + credits);
        }
        if (!StringUtils.isEmpty(gradingOptionId)) {
            updatedCoLpr.getResultValuesGroupKeys().add(gradingOptionId);
        }

        // Create the new one
        getLprService().createLpr(updatedCoLpr.getPersonId(), updatedCoLpr.getLuiId(),
                updatedCoLpr.getTypeKey(), updatedCoLpr, contextInfo);

        // Also update the master LPR since this is updating credit/reg options
        LprInfo masterLpr = updateMasterLpr(masterLprId, credits, gradingOptionId, effDate, contextInfo);

        // Make a copy of the original master LPR.  This copy will store the original master LPR's info
        // while the original master LPR will be updated.  This is a "trick" to avoid creating new master LPR
        // IDs whenever the master LPR is updated.
        LprInfo masterLprCopy = new LprInfo(masterLpr);
        // Clear out the IDs and meta from copy (so it can save properly)
        masterLprCopy.setId(null);
        masterLprCopy.setMeta(null);
        masterLprCopy.setExpirationDate(new Date()); // Set its expiration date
        masterLprCopy.setStateKey(LprServiceConstants.SUPERCEDED_LPR_STATE_KEY); // Put it expired state

        // Then, create the copy LPR
        getLprService().createLpr(masterLprCopy.getPersonId(),
                masterLprCopy.getLuiId(), masterLprCopy.getTypeKey(), masterLprCopy, contextInfo);

        List<LprInfo> updatedLprInfos = new ArrayList<LprInfo>();
        updatedLprInfos.add(masterLpr);

        return updatedLprInfos;
    }

    /**
     * Transactional helper method to update the masterLpr
     *
     * @param masterLprId
     * @param credits
     * @param gradingOptionId
     * @param contextInfo
     * @return
     * @throws PermissionDeniedException
     * @throws MissingParameterException
     * @throws InvalidParameterException
     * @throws OperationFailedException
     * @throws DoesNotExistException
     * @throws ReadOnlyException
     * @throws DataValidationErrorException
     * @throws VersionMismatchException
     */
    protected LprInfo updateMasterLpr(String masterLprId, String credits, String gradingOptionId, Date effDate, ContextInfo contextInfo) throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException, VersionMismatchException {
        LprInfo masterLpr = getLprService().getLpr(masterLprId, contextInfo);

        if (masterLpr.getExpirationDate() != null) {
            throw new OperationFailedException("Master LPR should have null expiration date");
        }

        masterLpr.setResultValuesGroupKeys(new ArrayList<String>());
        if (!StringUtils.isEmpty(credits)) {
            masterLpr.getResultValuesGroupKeys().add(LrcServiceConstants.RESULT_VALUE_KEY_CREDIT_DEGREE_PREFIX + credits);
        }
        if (!StringUtils.isEmpty(gradingOptionId)) {
            masterLpr.getResultValuesGroupKeys().add(gradingOptionId);
        }
        // Set effective date
        masterLpr.setEffectiveDate(effDate);
        // Update the master LPR
        return getLprService().updateLpr(masterLpr.getId(), masterLpr, contextInfo);

    }

    protected LprInfo expireLpr(String lprId, ContextInfo contextInfo) throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException, VersionMismatchException {

        LprInfo lprInfo = getLprService().getLpr(lprId, contextInfo);
        Date now = new Date();
        lprInfo.setExpirationDate(now);
        lprInfo.setStateKey(LprServiceConstants.SUPERCEDED_LPR_STATE_KEY);

        return getLprService().updateLpr(lprInfo.getId(), lprInfo, contextInfo);

    }
    @Override
    @Transactional
    public void removeCourseWaitlistEntry(RegistrationRequestItemEngineMessage message, ContextInfo contextInfo) throws PermissionDeniedException, ReadOnlyException, OperationFailedException, VersionMismatchException, InvalidParameterException, DataValidationErrorException, MissingParameterException, DoesNotExistException {
        RegistrationRequestItem registrationRequestItem = message.getRequestItem();

        dropLprs(registrationRequestItem.getExistingCourseRegistrationId(), contextInfo);

        LprTransactionItemInfo updatedItem = updateLprTransactionItemResult(message.getRequestItem().getRegistrationRequestId(),
                message.getRequestItem().getId(),
                LprServiceConstants.LPRTRANS_ITEM_SUCCEEDED_STATE_KEY,
                registrationRequestItem.getExistingCourseRegistrationId(),
                RegistrationValidationResultsUtil.marshallSimpleMessage(LprServiceConstants.LPRTRANS_ITEM_WAITLIST_STUDENT_REMOVED_MESSAGE_KEY),
                true,
                contextInfo);

        updateMessageRequestItem(message, updatedItem);
    }

    //Update The message Item with new information
    private void updateMessageRequestItem(RegistrationRequestItemEngineMessage message, LprTransactionItemInfo updatedItem) {
        RegistrationRequestItemInfo requestItem = new RegistrationRequestItemInfo(message.getRequestItem());
        requestItem.getMeta().setVersionInd(updatedItem.getMeta().getVersionInd());
        requestItem.setStateKey(updatedItem.getStateKey());
        message.setRequestItem(requestItem);
    }


    @Override
    public List<SeatCount> getSeatCountsForActivityOfferings(List<String> activityOfferingIds, ContextInfo contextInfo) throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException {
        return getCourseSeatCountService().getSeatCountsForActivityOfferings(activityOfferingIds, contextInfo);
    }

    @Override
    public boolean areSeatsAvailable(List<SeatCount> seatCounts, List<String> aoIds, ContextInfo contextInfo) throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException {

        for (SeatCount seatCount : seatCounts) {
            if (seatCount.getAvailableSeats() <= 0) {
                return false;
            }
        }

        //Simulate getting people off the waitlist
        Map<String, Integer> aoSeatCountsClaimedFromWL = new HashMap<String, Integer>();
        waitlistManagerService.getPeopleToProcessFromWaitlist(aoIds, aoSeatCountsClaimedFromWL, contextInfo);

        //Check that no one on the waitlist is about to claim these seats
        for (String aoId : aoIds) {
            if (aoSeatCountsClaimedFromWL.get(aoId) != null && aoSeatCountsClaimedFromWL.get(aoId) <= 0) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean isThereAWaitlist(List<SeatCount> seatCounts, ContextInfo contextInfo) throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException {
        for (SeatCount seatCount : seatCounts) {
            if (!seatCount.hasWaitList()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isWaitlistFull(List<SeatCount> seatCounts, ContextInfo contextInfo) throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException {
        for (SeatCount seatCount : seatCounts) {
            if (seatCount.getMaxWaitListSize() != null && seatCount.getWaitListSize() >= seatCount.getMaxWaitListSize()) {
                return true;
            }
        }
        return false;
    }

    @Override
    @Transactional
    public void registerPersonForCourse(RegistrationRequestItemEngineMessage message, ContextInfo contextInfo) throws PermissionDeniedException, ReadOnlyException, OperationFailedException, VersionMismatchException, InvalidParameterException, DataValidationErrorException, MissingParameterException, DoesNotExistException {
        String creditStr = message.getRequestItem().getCredits() == null ? "" : message.getRequestItem().getCredits().bigDecimalValue().setScale(1).toPlainString();

        List<LprInfo> registeredLprs = buildRegisteredLprs(message.getRequestItem().getRegistrationGroupId(), message.getRegistrationGroup().getTermId(), creditStr, message.getRequestItem().getGradingOptionId(), message.getRequestItem().getRequestedEffectiveDate(), contextInfo);

        String masterLprId = registeredLprs.get(0).getMasterLprId();
        LprTransactionItemInfo updatedItem =  updateLprTransactionItemResult(message.getRequestItem().getRegistrationRequestId(),
                message.getRequestItem().getId(),
                LprServiceConstants.LPRTRANS_ITEM_SUCCEEDED_STATE_KEY,
                masterLprId,
                RegistrationValidationResultsUtil.marshallSimpleMessage(LprServiceConstants.LPRTRANS_ITEM_PERSON_REGISTERED_MESSAGE_KEY),
                true,
                contextInfo);

        updateMessageRequestItem(message, updatedItem);
    }

    private Map<String, RegistrationGroup> getRegistrationGroupsForRequest(RegistrationRequestInfo registrationRequestInfo, ContextInfo contextInfo) throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException {

        //Look up all the registration group information for the request
        List<String> registrationGroupIds = new ArrayList<String>();
        for (RegistrationRequestItemInfo regRequestItem : registrationRequestInfo.getRegistrationRequestItems()) {
            if (regRequestItem.getRegistrationGroupId() == null) {
                //look up Reg group id here!
                Lpr lpr = getLprService().getLpr(regRequestItem.getExistingCourseRegistrationId(), contextInfo);
                regRequestItem.setRegistrationGroupId(lpr.getLuiId());
            }

            registrationGroupIds.add(regRequestItem.getRegistrationGroupId());

        }

        List<RegistrationGroupInfo> registrationGroups = getCourseOfferingService().getRegistrationGroupsByIds(registrationGroupIds, contextInfo);

        Map<String, RegistrationGroup> regGroupIdToRegGroupMap = new HashMap<String, RegistrationGroup>();
        for (RegistrationGroupInfo registrationGroup : registrationGroups) {
            regGroupIdToRegGroupMap.put(registrationGroup.getId(), registrationGroup);
        }

        return regGroupIdToRegGroupMap;
    }

    public CourseOfferingService getCourseOfferingService() {
        if (courseOfferingService == null) {
            courseOfferingService = GlobalResourceLoader.getService(CourseOfferingServiceConstants.Q_NAME);
        }
        return courseOfferingService;
    }

    public void setCourseOfferingService(CourseOfferingService courseOfferingService) {
        this.courseOfferingService = courseOfferingService;
    }

    public CourseRegistrationService getCourseRegistrationService() {
        if (courseRegistrationService == null) {
            courseRegistrationService = GlobalResourceLoader.getService(CourseRegistrationServiceConstants.Q_NAME);
        }

        return courseRegistrationService;
    }

    public void setCourseRegistrationService(CourseRegistrationService courseRegistrationService) {
        this.courseRegistrationService = courseRegistrationService;
    }

    public LuiService getLuiService() {
        if (luiService == null) {
            luiService = GlobalResourceLoader.getService(new QName(LuiServiceConstants.NAMESPACE, LuiServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return luiService;
    }

    public void setLuiService(LuiService luiService) {
        this.luiService = luiService;
    }

    public LprService getLprService() {
        if (lprService == null) {
            lprService = GlobalResourceLoader.getService(new QName(LprServiceConstants.NAMESPACE, LprServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return lprService;
    }

    public void setLprService(LprService lprService) {
        this.lprService = lprService;
    }

    public SearchService getSearchService() {
        if (searchService == null) {
            searchService = GlobalResourceLoader.getService(new QName(SearchServiceConstants.NAMESPACE, SearchService.class.getSimpleName()));
        }
        return searchService;
    }

    public void setSearchService(SearchService searchService) {
        this.searchService = searchService;
    }

    public CourseSeatCountService getCourseSeatCountService() {
        if (courseSeatCountService == null) {
            courseSeatCountService = GlobalResourceLoader.getService(CourseSeatCountServiceConstants.Q_NAME);
        }

        return courseSeatCountService;
    }

    public void setCourseSeatCountService(CourseSeatCountService courseRegistrationService) {
        this.courseSeatCountService = courseRegistrationService;
    }

    public void setWaitlistManagerService(WaitlistManagerService waitlistManagerService) {
        this.waitlistManagerService = waitlistManagerService;
    }

}
