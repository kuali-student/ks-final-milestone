/**
 * Copyright 2014 The Kuali Foundation Licensed under the
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
 */
package org.kuali.student.common.uif.service;

import org.kuali.rice.krad.lookup.Lookupable;
import org.kuali.rice.krad.uif.element.Message;

/**
 *
 * @author Kuali Student Team
 */
public interface KSLookupable extends Lookupable {

    /**
     * Override this method to validate whether they are no records on Search and set appropriate message for Message object.
     *
     * @param message Set your message in this object which will be shown below criteria section of lookup screen.
     * @param model This is a Model object which contains criteria values enter by user in criteria section of lookup screen.
     *
     * @see  org.kuali.student.common.uif.service.impl.KSLookupableImpl KSLookupableImpl
     * @see  org.kuali.rice.krad.lookup.LookupForm LookupForm
     */
    public void generateLookupResultsNotFoundMessage(Message message,Object model) ;


}
