package org.kuali.student.enrollment.registration.engine.service.impl;

import org.apache.log4j.Logger;
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
import org.kuali.student.enrollment.registration.engine.service.RegistrationProcessService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.CourseRegistrationServiceConstants;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by swedev on 12/20/13.
 */
public class CourseRegistrationInitilizationServiceImpl implements RegistrationProcessService {
    public static final Logger LOGGER = Logger.getLogger(CourseRegistrationInitilizationServiceImpl.class);

    private CourseRegistrationService courseRegistrationService;
    private LuiService luiService;
    private LprService lprService;

    @Override
    public RegistrationResponseInfo process(String registrationRequestId) {
        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

        try {
            // get reg request
            RegistrationRequestInfo registrationRequestInfo
                    = getCourseRegistrationService().getRegistrationRequest(registrationRequestId, contextInfo);

//            List<LprInfo> lprInfos = makeLprsFromRegRequest(registrationRequestInfo, contextInfo);
//            if (lprInfos == null || lprInfos.isEmpty()) {
//                return makeErrorResponse(registrationRequestId);
//            }


        } catch (Exception ex){
            ex.printStackTrace();
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

    private List<LprInfo> makeLprsFromRegRequest(RegistrationRequestInfo registrationRequestInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (LprServiceConstants.LPRTRANS_NEW_STATE_KEY.equals(registrationRequestInfo.getStateKey())) {
            // If this state is not new, then it's been processed, so skip.
            LOGGER.info("Request item already processed");
            return null;
        } else {
            // By changing to processing, we avoid reprocessing the request
            courseRegistrationService.changeRegistrationRequestState(registrationRequestInfo.getId(),
                    LprServiceConstants.LPRTRANS_PROCESSING_STATE_KEY, contextInfo);
        }
        List<LprInfo> lprInfos = new ArrayList<LprInfo>();

        for (RegistrationRequestItem registrationRequestItem : registrationRequestInfo.getRegistrationRequestItems()){
            if (registrationRequestItem.getTypeKey().equals(LprServiceConstants.REQ_ITEM_ADD_TYPE_KEY)){
                lprInfos.addAll(buildLprItems(registrationRequestItem.getRegistrationGroupId(), contextInfo));
            }
        }
        return lprInfos;
    }

    /**
     * This method will build a LprTransactionInfo object from a regGroup
     *
     * @param regGroupId
     * @return
     */
    protected List<LprInfo> buildLprItems(String regGroupId, ContextInfo context){
        List<LprInfo> result = new ArrayList<LprInfo>();
        // Get AO Lui's by reg grup
        //List<LuiInfo> ao1List = getLuiService().getLuisByRelatedLuiAndRelationType(registrationRequestInfo.get);
        LuiService luiServiceLocal = getLuiService();
        try {
            // Create AO LPRs
            List<String> aoIds = luiServiceLocal.getLuiIdsByLuiAndRelationType(regGroupId,
                    LuiServiceConstants.LUI_LUI_RELATION_REGISTERED_FOR_VIA_RG_TO_AO_TYPE_KEY, context);
            for (String aoId: aoIds) {
                LprInfo aoLprCreated = makeLpr(LprServiceConstants.REGISTRANT_AO_TYPE_KEY, aoId, context);
                result.add(aoLprCreated);
            }
            // Create RG LPR
            LprInfo rgLprCreated = makeLpr(LprServiceConstants.REGISTRANT_RG_TYPE_KEY, regGroupId, context);
            result.add(rgLprCreated);
            // Get the CO
            List<String> foIds = luiServiceLocal.getLuiIdsByRelatedLuiAndRelationType(regGroupId,
                    LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_FO_TO_RG_TYPE_KEY, context);
            String foId = KSCollectionUtils.getRequiredZeroElement(foIds);
            List<String> coIds = luiServiceLocal.getLuiIdsByRelatedLuiAndRelationType(foId,
                    LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_CO_TO_FO_TYPE_KEY, context);
            String coId = KSCollectionUtils.getRequiredZeroElement(coIds);
            // Create CO LPR
            LprInfo coLprCreated = makeLpr(LprServiceConstants.REGISTRANT_CO_TYPE_KEY, coId, context);
            result.add(rgLprCreated);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return result;
    }

    private LprInfo makeLpr(String lprType, String luiId, ContextInfo context)
            throws DoesNotExistException, PermissionDeniedException, OperationFailedException,
            InvalidParameterException, ReadOnlyException, MissingParameterException,
            DataValidationErrorException {
        LprInfo lpr = new LprInfo();
        lpr.setTypeKey(lprType);
        lpr.setStateKey(LprServiceConstants.PLANNED_STATE_KEY);
        lpr.setPersonId(context.getPrincipalId());
        lpr.setLuiId(luiId);
        LprInfo lprCreated = lprService.createLpr(lpr.getPersonId(), lpr.getLuiId(),
                lpr.getTypeKey(), lpr, context);
        return lprCreated;
    }

    public CourseRegistrationService getCourseRegistrationService() {
        if (courseRegistrationService == null){
            courseRegistrationService = (CourseRegistrationService) GlobalResourceLoader.getService(CourseRegistrationServiceConstants.Q_NAME);
        }

        return courseRegistrationService;
    }

    public void setCourseRegistrationService(CourseRegistrationService courseRegistrationService) {
        this.courseRegistrationService = courseRegistrationService;
    }

    public LuiService getLuiService() {
        if (luiService == null){
            luiService = (LuiService)
                    GlobalResourceLoader.getService(new QName(LuiServiceConstants.NAMESPACE, LuiServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return luiService;
    }

    public void setLuiService(LuiService luiService) {
        this.luiService = luiService;
    }

    public LprService getLprService() {
        if (lprService == null){
            lprService = (LprService)
                    GlobalResourceLoader.getService(new QName(LprServiceConstants.NAMESPACE, LprServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return lprService;
    }

    public void setLprService(LprService lprService) {
        this.lprService = lprService;
    }
}
