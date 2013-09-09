package org.kuali.student.enrollment.class1.state.impl;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.core.class1.state.service.RelatedObjectHelper;

import javax.xml.namespace.QName;
import java.util.HashMap;
import java.util.Map;

/**
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */

public class RelatedObjectHelperAOtoFOImpl implements RelatedObjectHelper {

    private CourseOfferingService courseOfferingService;

    public RelatedObjectHelperAOtoFOImpl() {
    }

    @Override
    public Map<String, String> getRelatedObjectsIdAndState(String activityOfferingId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        //TODO - FIXME - these are expensive calls to get ids and states (should be done with one DB call using search)
        Map<String, String> idsAndStatesMap = new HashMap<String, String>();

        FormatOfferingInfo formatOfferingInfo = getFormatOfferingInfoByActivityOfferingId(activityOfferingId, contextInfo);
        idsAndStatesMap.put(formatOfferingInfo.getId(), formatOfferingInfo.getStateKey());

        return idsAndStatesMap;
    }

    private FormatOfferingInfo getFormatOfferingInfoByActivityOfferingId(String activityOfferingId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        FormatOfferingInfo formatOfferingInfo = null;

        ActivityOfferingInfo activityOfferingInfo = getCourseOfferingService().getActivityOffering(activityOfferingId, contextInfo);
        formatOfferingInfo = getCourseOfferingService().getFormatOffering(activityOfferingInfo.getFormatOfferingId(), contextInfo);

        return formatOfferingInfo;
    }

    protected CourseOfferingService getCourseOfferingService(){
        if (courseOfferingService == null){
            courseOfferingService = (CourseOfferingService) GlobalResourceLoader.getService(new QName(CourseOfferingServiceConstants.NAMESPACE, CourseOfferingServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return  courseOfferingService;
    }
}