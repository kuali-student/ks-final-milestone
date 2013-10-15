package org.kuali.student.enrollment.class1.state.impl;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.examoffering.dto.ExamOfferingInfo;
import org.kuali.student.enrollment.examoffering.dto.ExamOfferingRelationInfo;
import org.kuali.student.enrollment.examoffering.service.ExamOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.ExamOfferingServiceConstants;
import org.kuali.student.r2.core.class1.state.service.RelatedObjectHelper;

import javax.xml.namespace.QName;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */

public class RelatedObjectHelperAOtoEOImpl implements RelatedObjectHelper {

    private ExamOfferingService examOfferingService;

    public RelatedObjectHelperAOtoEOImpl() {
    }

    @Override
    public Map<String, String> getRelatedObjectsIdAndState(String activityOfferingId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        Map<String, String> idsAndStatesMap = new HashMap<String, String>();

        List<String> eoRelationIds = this.getExamOfferingService().getExamOfferingRelationIdsByActivityOffering(activityOfferingId, contextInfo);

        if(eoRelationIds.isEmpty()){
            return idsAndStatesMap;
        }

        List<ExamOfferingRelationInfo> eoRelations = this.getExamOfferingService().getExamOfferingRelationsByIds(eoRelationIds, contextInfo);
        for(ExamOfferingRelationInfo eoRelation : eoRelations){
            ExamOfferingInfo examOffering = this.getExamOfferingService().getExamOffering(eoRelation.getExamOfferingId(), contextInfo);
            idsAndStatesMap.put(examOffering.getId(), examOffering.getStateKey());
        }

        return idsAndStatesMap;
    }

    protected ExamOfferingService getExamOfferingService(){
        if (examOfferingService == null){
            examOfferingService = (ExamOfferingService) GlobalResourceLoader.getService(new QName(ExamOfferingServiceConstants.NAMESPACE, ExamOfferingServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return  examOfferingService;
    }

}