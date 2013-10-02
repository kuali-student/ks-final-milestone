/**
 * Copyright 2013 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * Created by Charles on 10/2/13
 */
package org.kuali.student.enrollment.class2.coursewaitlist.service.facade;

import javax.xml.namespace.QName;

/**
 * Mostly to hold the constant for the QName to get it off the bus
 *
 * @author Kuali Student Team
 */
public class CourseWaitListServiceFacadeConstants {
    public static final QName CWLS_FACADE_QNAME =
            new QName("http://student.kuali.org/wsdl/courseWaitListServiceFacade", "courseWaitListServiceFacade");
    public static QName getQName() {
        return CWLS_FACADE_QNAME;
    }
}
