/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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
 */

package org.kuali.student.brms.factfinder.runtime;

import java.util.List;

import org.kuali.student.brms.factfinder.dto.FactResultInfo;
import org.kuali.student.brms.factfinder.dto.FactStructureInfo;
import org.kuali.student.brms.factfinder.dto.FactTypeInfo;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.OperationFailedException;

public interface FactFinder {
    /**
     * Retrieves the list of Facts Types known by this service.
     * 
     * @return list of Agenda types
     * @throws OperationFailedException
     */
    public List<FactTypeInfo> getFactTypes() throws OperationFailedException;

    /**
     * Retrieves information about a type of Fact, listing all the keys required for its computation.
     * 
     * @return list of business rule types
     * @throws OperationFailedException
     */
    public FactTypeInfo getFactType(String factTypeKey) throws OperationFailedException, DoesNotExistException;

    /**
     * Retrieves the result of computation of the Fact by the service.
     * 
     * @return
     * @throws OperationFailedException
     */
    public FactResultInfo getFact(String factTypeKey, FactStructureInfo factStructure) throws OperationFailedException, DoesNotExistException;    
}
