package org.kuali.student.enrollment.class1.state.impl;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
public class RelatedObjectHelperAOtoCOImpl implements RelatedObjectHelper {

    private CourseOfferingService courseOfferingService;

    public RelatedObjectHelperAOtoCOImpl() {
    }

    protected CourseOfferingService getCourseOfferingService(){
        if (courseOfferingService == null){
            courseOfferingService = (CourseOfferingService) GlobalResourceLoader.getService(new QName(CourseOfferingServiceConstants.NAMESPACE, CourseOfferingServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return  courseOfferingService;
    }

    @Override
    public Set<String> getRelatedObjectStateKeys(String activityOfferingId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        Set<String> coStateKeySet = new HashSet<String>();

        coStateKeySet.add(getCourseOfferingInfoByActivityOfferingId(activityOfferingId, contextInfo).getStateKey());

        return coStateKeySet;
    }

    @Override
    public List<String> getRelatedObjectIds(String activityOfferingId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> coIdList = new ArrayList<String>();

        coIdList.add(getCourseOfferingInfoByActivityOfferingId(activityOfferingId, contextInfo).getId());

        return coIdList;
    }

    private CourseOfferingInfo getCourseOfferingInfoByActivityOfferingId(String activityOfferingId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        CourseOfferingInfo courseOfferingInfo = null;

        ActivityOfferingInfo activityOfferingInfo = getCourseOfferingService().getActivityOffering(activityOfferingId, contextInfo);
        courseOfferingInfo = getCourseOfferingService().getCourseOffering(activityOfferingInfo.getCourseOfferingId(), contextInfo);

        return courseOfferingInfo;
    }
}