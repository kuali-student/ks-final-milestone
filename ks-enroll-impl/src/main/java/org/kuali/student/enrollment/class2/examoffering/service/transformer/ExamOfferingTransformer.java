package org.kuali.student.enrollment.class2.examoffering.service.transformer;

import org.apache.log4j.Logger;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.enrollment.examoffering.dto.ExamOfferingInfo;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.lum.clu.dto.LuCodeInfo;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExamOfferingTransformer {

    private LuiService luiService;

    final Logger LOG = Logger.getLogger(ExamOfferingTransformer.class);

    /**
     * Transform a list of LuiInfos into ExamOfferingInfos. It is the bulk version of lui2ExamOffering transformer
     *
     * @param examOfferingIds     the list of examOfferingIds which is used to retrieve the list of LuiInfos
     * @param eos                   the reference of ExamOfferingInfo list whith points to the transformed ExamOfferingInfo list
     * @param context               information containing the principalId and locale
     *                              information about the caller of service operation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException     ActivityOfferingDisplayInfo is not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException courseOfferingIds, cos, stateService, or context is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException  unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException an authorization failure occurred
     */
    public void luis2ExamOfferings(List<String> examOfferingIds, List<ExamOfferingInfo> eos, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if(examOfferingIds == null || examOfferingIds.isEmpty()){
            LOG.warn("invalid courseOfferingIds");
            return;
        }

        List<LuiInfo> luiInfos = luiService.getLuisByIds(examOfferingIds, context);
        for (LuiInfo luiInfo : luiInfos) {
            ExamOfferingInfo eo = new ExamOfferingInfo();
            lui2ExamOffering(luiInfo, eo, context);

            eos.add(eo);

        }
    }

    /**
     * Transform a LuiInfo into an ExamOfferingInfo.
     *
     * @param lui                   the LuiInfo that is transformed into ExamOfferingInfo
     * @param eo                    the reference of ExamOfferingInfo that is transformed from LuiInfo
     */
    public void lui2ExamOffering(LuiInfo lui, ExamOfferingInfo eo, ContextInfo context) {

        eo.setId(lui.getId());
        eo.setTypeKey(lui.getTypeKey());
        eo.setStateKey(lui.getStateKey());
        eo.setDescr(lui.getDescr());
        eo.setMeta(lui.getMeta());

        //These still need to be mapped
        //eo.setExamPeriodId();
        //eo.setExamId();
        //eo.setScheduleId();
        //eo.setSchedulingStateKey();

        //Dynamic attributes
        List<AttributeInfo> attributes = eo.getAttributes();
        for (Attribute attr : lui.getAttributes()) {
            attributes.add(new AttributeInfo(attr));
        }
        eo.setAttributes(attributes);

        return;
    }

    public void examOffering2Lui(ExamOfferingInfo eo, LuiInfo lui, ContextInfo context) {

        lui.setId(eo.getId());
        lui.setTypeKey(eo.getTypeKey());
        lui.setStateKey(eo.getStateKey());
        lui.setDescr(eo.getDescr());
        lui.setMeta(eo.getMeta());

        //These still need to be mapped
        //eo.getExamPeriodId();
        //eo.getExamId();
        //eo.getScheduleId();
        //eo.getSchedulingStateKey();

        //Dynamic Attributes
        HashMap<String, AttributeInfo> attributesMap = new HashMap<String, AttributeInfo>();
        List<AttributeInfo> attributes = new ArrayList<AttributeInfo>();
        for (AttributeInfo attr : lui.getAttributes()) {
            attributesMap.put(attr.getKey(), attr) ;
        }
        for (AttributeInfo attr : eo.getAttributes()) {
            attributesMap.put(attr.getKey(), attr) ;
        }

        //AttributeInfo courseNumberInternalSuffix = new AttributeInfo();
        //courseNumberInternalSuffix.setKey(CourseOfferingServiceConstants.COURSE_NUMBER_IN_SUFX_ATTR);
        //courseNumberInternalSuffix.setValue(eo.getCourseNumberInternalSuffix());
        //attributesMap.put(CourseOfferingServiceConstants.COURSE_NUMBER_IN_SUFX_ATTR, courseNumberInternalSuffix);

        for (Map.Entry<String, AttributeInfo> entry : attributesMap.entrySet()) {
            attributes.add(entry.getValue());
        }

        lui.setAttributes(attributes);

    }

    public LuiService getLuiService() {
        if(luiService == null){
            luiService = GlobalResourceLoader.getService(new QName(LuiServiceConstants.NAMESPACE, LuiServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return luiService;
    }

    public void setLuiService(LuiService luiService) {
        this.luiService = luiService;
    }

}
