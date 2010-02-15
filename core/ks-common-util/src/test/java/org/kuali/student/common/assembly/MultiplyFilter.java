package org.kuali.student.common.assembly;

import org.kuali.student.common.assembly.PassThroughAssemblerFilter;
import org.kuali.student.common.assembly.client.AssemblyException;
import org.kuali.student.common.assembly.client.SaveResult;

public class MultiplyFilter extends PassThroughAssemblerFilter<Integer,Integer> {

	@Override
	public void doSaveFilter(
			org.kuali.student.common.assembly.AssemblerFilter.FilterParamWrapper<Integer> request,
			org.kuali.student.common.assembly.AssemblerFilter.FilterParamWrapper<SaveResult<Integer>> response,
			org.kuali.student.common.assembly.AssemblerFilter.SaveFilterChain<Integer, Integer> chain)
			throws AssemblyException {
		request.setValue(request.getValue()+1);
		System.out.println("In TestMultiplyFilter before chain was called and adding 1. Request:"+request.getValue());
		chain.doSaveFilter(request, response);
		response.getValue().setValue(response.getValue().getValue()*2);
		System.out.println("In TestMultiplyFilter after chain was called and multiplied by 2. Response:"+response.getValue().getValue());
	}

}
