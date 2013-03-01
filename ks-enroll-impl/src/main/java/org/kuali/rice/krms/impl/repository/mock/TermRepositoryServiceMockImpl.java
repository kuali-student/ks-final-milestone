/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may	obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.rice.krms.impl.repository.mock;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.kuali.rice.core.api.exception.RiceIllegalArgumentException;
import org.kuali.rice.krms.api.repository.term.TermDefinition;
import org.kuali.rice.krms.api.repository.term.TermRepositoryService;
import org.kuali.rice.krms.api.repository.term.TermResolverDefinition;
import org.kuali.student.common.mock.MockService;


public class TermRepositoryServiceMockImpl implements MockService, TermRepositoryService
{
	// cache variable 
	// The LinkedHashMap is just so the values come back in a predictable order
	private Map<String, TermDefinition> termDefinitionMap = new LinkedHashMap<String, TermDefinition>();

	@Override
	public void clear()
	{
		this.termDefinitionMap.clear ();
	}

	
	@Override
	public List<TermResolverDefinition> findTermResolversByNamespace(String namespace)
		throws RiceIllegalArgumentException
	{
		// UNKNOWN
		throw new RiceIllegalArgumentException ("findTermResolversByNamespace has not been implemented");
	}
	
	@Override
	public TermDefinition getTerm(String termId)
		throws RiceIllegalArgumentException
	{
		// GET_BY_ID
		if (!this.termDefinitionMap.containsKey(termId)) {
		   throw new RiceIllegalArgumentException(termId);
		}
		return this.termDefinitionMap.get (termId);
	}
}

