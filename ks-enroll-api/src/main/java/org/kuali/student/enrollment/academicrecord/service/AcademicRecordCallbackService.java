
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
 */
package org.kuali.student.enrollment.academicrecord.service;

import org.kuali.student.r2.common.dto.StatusInfo;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.List;
import org.kuali.student.r2.common.dto.ContextInfo;

@WebService(name = "AcademicRecordCallbackService", targetNamespace = AcademicRecordCallbackNamespaceConstants.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface AcademicRecordCallbackService {

    /**
     * Callback for when Student Course Records are created.
     *
     *
     * @param studentCourseRecordIds ids created.
     * @param contextInfo context of the caller
     * @return statusInfo
     */
    public StatusInfo newStudentCourseRecords(@WebParam(name = "studentCourseRecordIds") List<String> studentCourseRecordIds,
           @WebParam(name = "contextInfo")  ContextInfo contextInfo);

    /**
     * Callback for when StudentCourseRecords are updated.
     *
     *
     * @param studentCourseRecordIds ids updated.
     * @param contextInfo context of the caller
     * @return statusInfo
     */
    public StatusInfo updateStudentCourseRecords(@WebParam(name = "studentCourseRecordIds") List<String> studentCourseRecordIds,
            @WebParam(name = "contextInfo") ContextInfo contextInfo);

    /**
     * Callback for when Student Course Records are deleted.
     *
     *
     * @param studentCourseRecordIds ids deleted.
     * @param contextInfo context of the caller
     * @return statusInfo
     */
    public StatusInfo deleteStudentCourseRecords(@WebParam(name = "studentCourseRecordIds") List<String> studentCourseRecordIds,
           @WebParam(name = "contextInfo")  ContextInfo contextInfo);
}
