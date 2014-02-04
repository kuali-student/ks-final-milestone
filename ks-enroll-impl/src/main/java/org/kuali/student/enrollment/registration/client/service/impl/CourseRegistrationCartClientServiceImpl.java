package org.kuali.student.enrollment.registration.client.service.impl;


import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationResponseInfo;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.enrollment.registration.client.service.CourseRegistrationCartClientService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.CourseRegistrationServiceConstants;

import javax.security.auth.login.LoginException;
import javax.ws.rs.core.Response;
import javax.xml.namespace.QName;

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
            response = Response.ok(submitCart(userId, cartId));
        } catch (Throwable t) {
            LOGGER.warn(t);
            response = Response.serverError().entity(t.getMessage());
        }

        return response.build();
    }

    @Override
    public RegistrationResponseInfo submitCart(String userId, String cartId) throws InvalidParameterException, MissingParameterException, DoesNotExistException, OperationFailedException, PermissionDeniedException, AlreadyExistsException, LoginException {
        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

        if (!StringUtils.isEmpty(userId)) {
            contextInfo.setPrincipalId(userId);
        }

        if (StringUtils.isEmpty(contextInfo.getPrincipalId())) {
            throw new LoginException("User must be logged in to access this service");
        }

        //Make sure that the user is the owner of the cart!
        CourseRegistrationInfo courseRegistrationInfo = getCourseRegistrationService().getCourseRegistration(cartId, contextInfo);
        if(!StringUtils.equals(courseRegistrationInfo.getPersonId(), contextInfo.getPrincipalId())){
            throw new PermissionDeniedException("User does not have permission to submit on this registration cart");
        }

        //Call submit on the registration service
        RegistrationResponseInfo registrationResponse = getCourseRegistrationService().submitRegistrationRequest(cartId, contextInfo);

        return registrationResponse;
    }

    public CourseRegistrationService getCourseRegistrationService() {
        if (courseRegistrationService == null) {
            courseRegistrationService = (CourseRegistrationService) GlobalResourceLoader.getService(new QName(CourseRegistrationServiceConstants.NAMESPACE, CourseRegistrationServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return courseRegistrationService;
    }

}
