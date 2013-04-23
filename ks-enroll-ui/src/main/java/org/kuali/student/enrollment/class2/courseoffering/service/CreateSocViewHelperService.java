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
 * Created by Charles on 4/23/13
 */
package org.kuali.student.enrollment.class2.courseoffering.service;

import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.acal.dto.TermInfo;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public interface CreateSocViewHelperService {
    public SocInfo getMainSocForTerm(String termCode) throws Exception;
    public SocInfo createSocForTerm(String termCode) throws Exception;
    public TermInfo getTermByTermCode(String termCode) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException;
}
