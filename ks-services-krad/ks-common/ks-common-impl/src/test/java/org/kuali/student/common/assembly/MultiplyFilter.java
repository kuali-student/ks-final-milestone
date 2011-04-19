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

package org.kuali.student.common.assembly;

import org.apache.log4j.Logger;
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.core.assembly.old.PassThroughAssemblerFilter;
import org.kuali.student.core.assembly.old.data.SaveResult;

public class MultiplyFilter extends PassThroughAssemblerFilter<Integer,Integer> {
	final Logger LOG = Logger.getLogger(MultiplyFilter.class);
	
	@Override
	public void doSaveFilter(
			org.kuali.student.core.assembly.old.AssemblerFilter.FilterParamWrapper<Integer> request,
			org.kuali.student.core.assembly.old.AssemblerFilter.FilterParamWrapper<SaveResult<Integer>> response,
			org.kuali.student.core.assembly.old.AssemblerFilter.SaveFilterChain<Integer, Integer> chain)
			throws AssemblyException {
		request.setValue(request.getValue()+1);
		LOG.info("In TestMultiplyFilter before chain was called and adding 1. Request:"+request.getValue());
		chain.doSaveFilter(request, response);
		response.getValue().setValue(response.getValue().getValue()*2);
		LOG.info("In TestMultiplyFilter after chain was called and multiplied by 2. Response:"+response.getValue().getValue());
		LOG.info("Filtered call to get yields:" + chain.getManager().get("12345"));
		LOG.info("UnFiltered call to get yields:" + chain.getManager().getTarget().get("12345"));
		
	}

	@Override
	public void doGetFilter(
			org.kuali.student.core.assembly.old.AssemblerFilter.FilterParamWrapper<String> id,
			org.kuali.student.core.assembly.old.AssemblerFilter.FilterParamWrapper<Integer> response,
			org.kuali.student.core.assembly.old.AssemblerFilter.GetFilterChain<Integer, Integer> chain)
			throws AssemblyException {
		response.setValue(new Integer(2));
	}

}
