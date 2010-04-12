package org.kuali.student.common.assembly;

import org.kuali.student.core.assembly.PassThroughAssemblerFilter;
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.core.assembly.data.SaveResult;

public class MultiplyFilter extends PassThroughAssemblerFilter<Integer,Integer> {

	@Override
	public void doSaveFilter(
			org.kuali.student.core.assembly.AssemblerFilter.FilterParamWrapper<Integer> request,
			org.kuali.student.core.assembly.AssemblerFilter.FilterParamWrapper<SaveResult<Integer>> response,
			org.kuali.student.core.assembly.AssemblerFilter.SaveFilterChain<Integer, Integer> chain)
			throws AssemblyException {
		request.setValue(request.getValue()+1);
		System.out.println("In TestMultiplyFilter before chain was called and adding 1. Request:"+request.getValue());
		chain.doSaveFilter(request, response);
		response.getValue().setValue(response.getValue().getValue()*2);
		System.out.println("In TestMultiplyFilter after chain was called and multiplied by 2. Response:"+response.getValue().getValue());
		System.out.println("Filtered call to get yields:" + chain.getManager().get("12345"));
		System.out.println("UnFiltered call to get yields:" + chain.getManager().getTarget().get("12345"));
		
	}

	@Override
	public void doGetFilter(
			org.kuali.student.core.assembly.AssemblerFilter.FilterParamWrapper<String> id,
			org.kuali.student.core.assembly.AssemblerFilter.FilterParamWrapper<Integer> response,
			org.kuali.student.core.assembly.AssemblerFilter.GetFilterChain<Integer, Integer> chain)
			throws AssemblyException {
		response.setValue(new Integer(2));
	}

}
