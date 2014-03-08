package org.kuali.student.enrollment.class2.exam.service.transformer;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.enrollment.exam.dto.ExamInfo;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.lum.clu.dto.CluIdentifierInfo;
import org.kuali.student.r2.lum.clu.dto.CluInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import java.util.List;

public class ExamTransformer {

    // Course Official Identifier
    public static final String EXAM_OFFICIAL_IDENT_TYPE  = "kuali.lu.type.exam.identifier.official";

    private CluService cluService;

    private static final Logger LOG = LoggerFactory.getLogger(ExamTransformer.class);

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
