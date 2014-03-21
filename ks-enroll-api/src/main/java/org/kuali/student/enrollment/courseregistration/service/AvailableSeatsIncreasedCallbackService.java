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
package org.kuali.student.enrollment.courseregistration.service;

import java.util.List;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * A callback service that must be implemented by a consumer and is called when a change has been made to a registration request
 * and it's item.
 *
 * Note: the same callback object may be registered to listen for multiple changes or the consumer may choose to create separate
 * callback objects to handle each.
 * 
 * @version 1.0
 * @author Kuali Student Team (norm)
 */
@WebService(name = "AvailableSeatsIncreasedCallbackService", targetNamespace = AvailableSeatsIncreasedCallbackNamespaceConstants.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface AvailableSeatsIncreasedCallbackService {

    /**
     * Callback for when the number of available seats in an activity offering has increased
     *
     * Multiple changes may be coalesced together for efficiency
     *
     * @param activityOfferingIds ids who's seats have increased
     * @return nothing
     */
    public void availableSeatsIncreased (@WebParam(name = "activityOfferingIds") List<String> activityOfferingIds);

}
