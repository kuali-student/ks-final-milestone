package org.kuali.student.enrollment.registration.engine.service.impl;

import org.apache.cxf.common.util.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationResponseInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationResponseItemInfo;
import org.kuali.student.enrollment.courseregistration.infc.RegistrationRequestItem;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.enrollment.lpr.dto.LprInfo;
import org.kuali.student.enrollment.lpr.service.LprService;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.enrollment.registration.client.service.impl.util.SearchResultHelper;
import org.kuali.student.enrollment.registration.engine.service.RegistrationProcessService;
import org.kuali.student.enrollment.registration.search.service.impl.CourseRegistrationSearchServiceImpl;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.r2.common.util.constants.CourseRegistrationServiceConstants;
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
import java.util.List;

/**
 * Created by swedev on 12/20/13.
 */
public class CourseRegistrationInitilizationServiceImpl implements RegistrationProcessService {
    public static final Logger LOGGER = LoggerFactory.getLogger(CourseRegistrationInitilizationServiceImpl.class);

    private CourseRegistrationService courseRegistrationService;
    private LuiService luiService;
    private LprService lprService;
    private SearchService searchService;

    @Transactional
    @Override
    public RegistrationResponseInfo process(String registrationRequestId) {
        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

        try {
            // get reg request
            RegistrationRequestInfo registrationRequestInfo
                    = getCourseRegistrationService().getRegistrationRequest(registrationRequestId, contextInfo);
            // contextInfo might have a null principalId.  Fill it in with the regRequestInfo's
            contextInfo.setPrincipalId(registrationRequestInfo.getRequestorId());

            List<LprInfo> lprInfos = makeLprsFromRegRequest(registrationRequestInfo, contextInfo);
            if (lprInfos == null || lprInfos.isEmpty()) {
                return makeErrorResponse(registrationRequestId);
            }

        } catch (Exception ex) {
            throw new RuntimeException("Error initializing request", ex);
        }
        return makeErrorResponse(registrationRequestId);
    }

    private RegistrationResponseInfo makeErrorResponse(String regRequestId) {
        RegistrationResponseInfo response = new RegistrationResponseInfo();
        response.setHasFailed(true);
        response.setRegistrationRequestId(regRequestId);
        response.setRegistrationResponseItems(new ArrayList<RegistrationResponseItemInfo>());
        return response;
    }

    private List<LprInfo> makeLprsFromRegRequest(RegistrationRequestInfo registrationRequestInfo, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException, DataValidationErrorException, VersionMismatchException {
        if (!LprServiceConstants.LPRTRANS_NEW_STATE_KEY.equals(registrationRequestInfo.getStateKey()) &&
                !LprServiceConstants.LPRTRANS_DISCARDED_STATE_KEY.equals(registrationRequestInfo.getStateKey())) {
            // If this state is not new, then it's been processed, so skip.
            LOGGER.info("Request item already processed");
            return null;
        } else {
            // By changing to processing, we avoid reprocessing the request
            getCourseRegistrationService().changeRegistrationRequestState(registrationRequestInfo.getId(),
                    LprServiceConstants.LPRTRANS_PROCESSING_STATE_KEY, contextInfo);
            registrationRequestInfo = getCourseRegistrationService().getRegistrationRequest(registrationRequestInfo.getId(), contextInfo);
        }

        List<LprInfo> lprInfos = new ArrayList<LprInfo>();

        for (RegistrationRequestItem registrationRequestItem : registrationRequestInfo.getRegistrationRequestItems()) {
            String creditStr = registrationRequestItem.getCredits()==null?"":registrationRequestItem.getCredits().bigDecimalValue().setScale(1).toPlainString();
            if (registrationRequestItem.getTypeKey().equals(LprServiceConstants.REQ_ITEM_ADD_TYPE_KEY)) {
                lprInfos.addAll(addLprInfo(registrationRequestItem.getRegistrationGroupId(), registrationRequestInfo.getTermId(), creditStr, registrationRequestItem.getGradingOptionId(), contextInfo));
            } else if (registrationRequestItem.getTypeKey().equals(LprServiceConstants.REQ_ITEM_DROP_TYPE_KEY)) {
                lprInfos.addAll(dropLprInfos(registrationRequestItem.getExistingCourseRegistrationId(), contextInfo));
            } else if (registrationRequestItem.getTypeKey().equals(LprServiceConstants.REQ_ITEM_UPDATE_TYPE_KEY)) {
                lprInfos.addAll(updateLprInfos(registrationRequestItem.getExistingCourseRegistrationId(), creditStr, registrationRequestItem.getGradingOptionId(), contextInfo));
            }
        }

        return lprInfos;
    }

    /**
     * This method will build a LprTransactionInfo object from a regGroup
     *
     * @param regGroupId Registration Group Id
     * @return List of Learning Person Relationships corresponding to the Reg Group, Course, and Activities
     */
    protected List<LprInfo> buildLprItems(String regGroupId, String termId, String credits, String gradingOptionKey, ContextInfo context) {
        List<LprInfo> result = new ArrayList<LprInfo>();
        // Get AO Lui's by reg grup
        //List<LuiInfo> ao1List = getLuiService().getLuisByRelatedLuiAndRelationType(registrationRequestInfo.get);
        LuiService luiServiceLocal = getLuiService();
        try {
            Date effDate = new Date();
            // Get the CO
            List<String> foIds = luiServiceLocal.getLuiIdsByRelatedLuiAndRelationType(regGroupId,
                    LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_FO_TO_RG_TYPE_KEY, context);
            String foId = KSCollectionUtils.getRequiredZeroElement(foIds);
            List<String> coIds = luiServiceLocal.getLuiIdsByRelatedLuiAndRelationType(foId,
                    LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_CO_TO_FO_TYPE_KEY, context);
            String coId = KSCollectionUtils.getRequiredZeroElement(coIds);

            // Create RG LPR
            LprInfo rgLprCreated = makeLpr(LprServiceConstants.REGISTRANT_RG_TYPE_KEY, regGroupId, null, effDate, termId, credits, gradingOptionKey, context);
            result.add(rgLprCreated);

            // Create CO LPR
            LprInfo coLprCreated = makeLpr(LprServiceConstants.REGISTRANT_CO_TYPE_KEY, coId, rgLprCreated.getMasterLprId(), effDate, termId, credits, gradingOptionKey, context);
            result.add(coLprCreated);

            // Create AO LPRs
            List<String> aoIds = luiServiceLocal.getLuiIdsByLuiAndRelationType(regGroupId,
                    LuiServiceConstants.LUI_LUI_RELATION_REGISTERED_FOR_VIA_RG_TO_AO_TYPE_KEY, context);
            for (String aoId : aoIds) {
                LprInfo aoLprCreated = makeLpr(LprServiceConstants.REGISTRANT_AO_TYPE_KEY, aoId, rgLprCreated.getMasterLprId(), effDate, termId, credits, gradingOptionKey, context);
                result.add(aoLprCreated);
            }

        } catch (Exception ex) {
            LOGGER.error("Error building LPR items", ex);
            return new ArrayList<LprInfo>();
        }
        return result;
    }

    private LprInfo makeLpr(String lprType, String luiId, String masterLuiId, Date effDate, String atpId, String credits, String gradingOptionKey, ContextInfo context)
            throws DoesNotExistException, PermissionDeniedException, OperationFailedException,
            InvalidParameterException, ReadOnlyException, MissingParameterException,
            DataValidationErrorException {
        LprInfo lpr = new LprInfo();
        lpr.setTypeKey(lprType);
        lpr.setStateKey(LprServiceConstants.PLANNED_STATE_KEY);
        lpr.setPersonId(context.getPrincipalId());
        lpr.setLuiId(luiId);
        lpr.setMasterLprId(masterLuiId);
        lpr.setEffectiveDate(effDate);
        lpr.setAtpId(atpId);
        if (!StringUtils.isEmpty(credits)) {
            lpr.getResultValuesGroupKeys().add(LrcServiceConstants.RESULT_VALUE_KEY_CREDIT_DEGREE_PREFIX + credits);
        }
        if (!StringUtils.isEmpty(gradingOptionKey)) {
            lpr.getResultValuesGroupKeys().add(gradingOptionKey);
        }
        LprInfo lprCreated = getLprService().createLpr(lpr.getPersonId(), lpr.getLuiId(),
                lpr.getTypeKey(), lpr, context);
        return lprCreated;
    }

    private List<String> getLprIdsByMasterLprId (String masterLprId, ContextInfo contextInfo) throws OperationFailedException {
        List<String> lprIds = new ArrayList<String>();

        SearchRequestInfo searchRequest = new SearchRequestInfo(CourseRegistrationSearchServiceImpl.LPRIDS_BY_MASTER_LPR_ID_SEARCH_TYPE.getKey());
        searchRequest.addParam(CourseRegistrationSearchServiceImpl.SearchParameters.MASTER_LPR_ID, masterLprId);
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

    private List<LprInfo> addLprInfo(String regGroupId, String termId, String credits, String gradingOptionId, ContextInfo contextInfo) {
        List<LprInfo> lprInfos = new ArrayList<LprInfo>();
        lprInfos.addAll(buildLprItems(regGroupId, termId, credits, gradingOptionId, contextInfo));
        return lprInfos;
    }

    private List<LprInfo> updateLprInfos(String masterLprId, String credits, String gradingOptionId, ContextInfo contextInfo)
            throws OperationFailedException, PermissionDeniedException, DataValidationErrorException, VersionMismatchException,
            InvalidParameterException, ReadOnlyException, MissingParameterException, DoesNotExistException {
        //get lpr ids based on master lpr id
        List<String> lprIds = getLprIdsByMasterLprId(masterLprId, contextInfo);

        List<LprInfo> lprInfos = getLprService().getLprsByIds(lprIds, contextInfo);
        List<LprInfo> updatedLprInfos = new ArrayList<LprInfo>();
        for (LprInfo lprInfo : lprInfos) {
            lprInfo.setResultValuesGroupKeys(new ArrayList<String>());
            if (!StringUtils.isEmpty(credits)) {
                lprInfo.getResultValuesGroupKeys().add(LrcServiceConstants.RESULT_VALUE_KEY_CREDIT_DEGREE_PREFIX + credits);
            }
            if (!StringUtils.isEmpty(gradingOptionId)) {
                lprInfo.getResultValuesGroupKeys().add(gradingOptionId);
            }
            lprInfo = getLprService().updateLpr(lprInfo.getId(), lprInfo, contextInfo);
            updatedLprInfos.add(lprInfo);
        }

        return updatedLprInfos;
    }

    private List<LprInfo> dropLprInfos(String masterLprId, ContextInfo contextInfo)
            throws OperationFailedException, PermissionDeniedException, DataValidationErrorException, VersionMismatchException,
            InvalidParameterException, ReadOnlyException, MissingParameterException, DoesNotExistException {
        //get lpr ids based on master lpr id
        List<String> lprIds = getLprIdsByMasterLprId(masterLprId, contextInfo);

        List<LprInfo> lprInfos = getLprService().getLprsByIds(lprIds, contextInfo);
        List<LprInfo> resultLprInfos = new ArrayList<LprInfo>();
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

    public CourseRegistrationService getCourseRegistrationService() {
        if (courseRegistrationService == null) {
            courseRegistrationService = (CourseRegistrationService) GlobalResourceLoader.getService(CourseRegistrationServiceConstants.Q_NAME);
        }

        return courseRegistrationService;
    }

    public void setCourseRegistrationService(CourseRegistrationService courseRegistrationService) {
        this.courseRegistrationService = courseRegistrationService;
    }

    public LuiService getLuiService() {
        if (luiService == null) {
            luiService = (LuiService)
                    GlobalResourceLoader.getService(new QName(LuiServiceConstants.NAMESPACE, LuiServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return luiService;
    }

    public void setLuiService(LuiService luiService) {
        this.luiService = luiService;
    }

    public LprService getLprService() {
        if (lprService == null) {
            lprService = (LprService)
                    GlobalResourceLoader.getService(new QName(LprServiceConstants.NAMESPACE, LprServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return lprService;
    }

    public void setLprService(LprService lprService) {
        this.lprService = lprService;
    }

    public SearchService getSearchService() {
        if (searchService == null) {
            searchService = (SearchService) GlobalResourceLoader.getService(new QName(SearchServiceConstants.NAMESPACE, SearchService.class.getSimpleName()));
        }
        return searchService;
    }

    public void setSearchService(SearchService searchService) {
        this.searchService = searchService;
    }
}
