
/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */package org.kuali.student.enrollment.courseoffering.service;


import org.kuali.student.r2.common.dto.StatusInfo;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.List;

@WebService(name = "CourseOfferingCallbackService", targetNamespace = CourseOfferingCallbackNamespaceConstants.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface CourseOfferingCallbackService {

    /**
     * Callback for when CourseOfferings are created.
     *
     *
     * @param courseOfferingIds ids created.
     * @return nothing
     */
    public StatusInfo newCourseOfferings(@WebParam(name = "courseOfferingIds") List<String> courseOfferingIds);

    /**
     * Callback for when CourseOfferings are updated.
     *
     *
     * @param courseOfferingIds ids updated.
     * @return nothing
     */
    public StatusInfo updateCourseOfferings(@WebParam(name = "courseOfferingIds") List<String> courseOfferingIds);

    /**
     * Callback for when CourseOfferings are deleted.
     *
     *
     * @param courseOfferingIds ids deleted.
     * @return nothing
     */
    public StatusInfo deleteCourseOfferings(@WebParam(name = "courseOfferingIds") List<String> courseOfferingIds);
}
