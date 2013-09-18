package org.kuali.student.enrollment.class2.exam.service.transformer;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.identity.PersonService;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.student.enrollment.class2.courseoffering.service.transformer.OfferingInstructorTransformer;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingCrossListingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.exam.dto.ExamInfo;
import org.kuali.student.enrollment.lpr.dto.LprInfo;
import org.kuali.student.enrollment.lpr.service.LprService;
import org.kuali.student.enrollment.lui.dto.LuiIdentifierInfo;
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
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.lum.clu.dto.CluIdentifierInfo;
import org.kuali.student.r2.lum.clu.dto.CluInfo;
import org.kuali.student.r2.lum.clu.dto.CluResultInfo;
import org.kuali.student.r2.lum.clu.dto.LuCodeInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ExamTransformer {

    // Course Official Identifier
    public static final String EXAM_OFFICIAL_IDENT_TYPE  = "kuali.lu.type.exam.identifier.official";

    private CluService cluService;

    final Logger LOG = Logger.getLogger(ExamTransformer.class);

    /**
     * Transform a list of CluInfos into ExamInfos. It is the bulk version of clu2exam transformer
     *
     * @param examIds   the list of courseOfferingIds which is used to retrieve the list of LuiInfos
     * @param exams     the reference of ExamInfo list whith points to the transformed ExamInfo list
     * @param context   information containing the principalId and locale
     *                  information about the caller of service operation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException     ActivityOfferingDisplayInfo is not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException courseOfferingIds, cos, stateService, or context is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException  unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException an authorization failure occurred
     */
    public void clus2Exams(List<String> examIds, List<ExamInfo> exams, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if(examIds == null || examIds.isEmpty()){
            LOG.warn("invalid courseOfferingIds");
            return;
        }

        List<CluInfo> cluInfos = this.getCluService().getClusByIds(examIds, context);
        for (CluInfo cluInfo : cluInfos) {
            ExamInfo exam = new ExamInfo();
            clu2Exam(cluInfo, exam, context);

            exams.add(exam);

        }
    }

    /**
     * Transform a CluInfo into an ExamInfo.
     *
     * @param clu   the CluInfo that is transformed into ExamInfo
     * @param exam  the reference of ExamInfo that is transformed from CluInfo
     */
    public void clu2Exam(CluInfo clu, ExamInfo exam, ContextInfo context) {

        exam.setId(clu.getId());
        exam.setTypeKey(clu.getTypeKey());
        exam.setStateKey(clu.getStateKey());
        exam.setDescr(clu.getDescr());
        exam.setMeta(clu.getMeta());
        exam.setName(clu.getOfficialIdentifier().getShortName());

        //Dynamic attributes
        List<AttributeInfo> attributes = exam.getAttributes();
        for (Attribute attr : clu.getAttributes()) {
            attributes.add(new AttributeInfo(attr));
        }
        exam.setAttributes(attributes);

        return;
    }

    public void exam2Clu(ExamInfo exam, CluInfo clu, ContextInfo context) {

        clu.setId(exam.getId());
        clu.setTypeKey(exam.getTypeKey());
        clu.setStateKey(exam.getStateKey());
        clu.setDescr(exam.getDescr());
        clu.setMeta(exam.getMeta());

        CluIdentifierInfo identifier = clu.getOfficialIdentifier();
        if(identifier == null){
            identifier = new CluIdentifierInfo();
        }
        identifier.setTypeKey(EXAM_OFFICIAL_IDENT_TYPE);
        identifier.setStateKey(exam.getStateKey());
        identifier.setShortName(exam.getName());
        clu.setOfficialIdentifier(identifier);
        clu.setAttributes(exam.getAttributes());

        return;
    }

    public CluService getCluService() {
        if(cluService == null){
            cluService = GlobalResourceLoader.getService(new QName(CluServiceConstants.CLU_NAMESPACE, CluServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return cluService;
    }

    public void setCluService(CluService cluService) {
        this.cluService = cluService;
    }

}
