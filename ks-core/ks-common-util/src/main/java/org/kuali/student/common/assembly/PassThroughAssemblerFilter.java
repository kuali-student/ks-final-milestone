package org.kuali.student.common.assembly;

import java.util.List;

import org.kuali.student.common.assembly.client.AssemblyException;
import org.kuali.student.common.assembly.client.Metadata;
import org.kuali.student.common.assembly.client.SaveResult;
import org.kuali.student.core.search.newdto.SearchRequest;
import org.kuali.student.core.search.newdto.SearchResult;
import org.kuali.student.core.validation.dto.ValidationResultInfo;

public class PassThroughAssemblerFilter <TargetType, SourceType> implements AssemblerFilter<TargetType, SourceType> {

	@Override
	public void doAssembleFilter(
			org.kuali.student.common.assembly.AssemblerFilter.FilterParamWrapper<SourceType> request,
			org.kuali.student.common.assembly.AssemblerFilter.FilterParamWrapper<TargetType> response,
			org.kuali.student.common.assembly.AssemblerFilter.AssembleFilterChain<TargetType, SourceType> chain) throws AssemblyException {
		chain.doAssembleFilter(request, response);
	}

	@Override
	public void doDisassembleFilter(
			org.kuali.student.common.assembly.AssemblerFilter.FilterParamWrapper<TargetType> request,
			org.kuali.student.common.assembly.AssemblerFilter.FilterParamWrapper<SourceType> response,
			org.kuali.student.common.assembly.AssemblerFilter.DisassembleFilterChain<TargetType, SourceType> chain) throws AssemblyException {
		chain.doDisassembleFilter(request, response);
	}

	@Override
	public void doGetFilter(
			org.kuali.student.common.assembly.AssemblerFilter.FilterParamWrapper<String> id,
			org.kuali.student.common.assembly.AssemblerFilter.FilterParamWrapper<TargetType> response,
			org.kuali.student.common.assembly.AssemblerFilter.GetFilterChain<TargetType> chain) throws AssemblyException {
		chain.doGetFilter(id, response);
	}

	@Override
	public void doGetMetadataFilter(
			org.kuali.student.common.assembly.AssemblerFilter.TypeStateFilterParamWrapper request,
			org.kuali.student.common.assembly.AssemblerFilter.FilterParamWrapper<Metadata> response,
			org.kuali.student.common.assembly.AssemblerFilter.GetMetadataFilterChain chain) throws AssemblyException {
		chain.doGetMetadataFilter(request, response);
	}

	@Override
	public void doSaveFilter(
			org.kuali.student.common.assembly.AssemblerFilter.FilterParamWrapper<TargetType> request,
			org.kuali.student.common.assembly.AssemblerFilter.FilterParamWrapper<SaveResult<TargetType>> response,
			org.kuali.student.common.assembly.AssemblerFilter.SaveFilterChain<TargetType, SourceType> chain)
			throws AssemblyException {
		chain.doSaveFilter(request, response);
	}

	@Override
	public void doSearchFilter(
			org.kuali.student.common.assembly.AssemblerFilter.FilterParamWrapper<SearchRequest> request,
			org.kuali.student.common.assembly.AssemblerFilter.FilterParamWrapper<SearchResult> response,
			org.kuali.student.common.assembly.AssemblerFilter.SearchFilterChain chain) {
		chain.doSearchFilter(request, response);
	}

	@Override
	public void doValidateFilter(
			org.kuali.student.common.assembly.AssemblerFilter.FilterParamWrapper<TargetType> request,
			org.kuali.student.common.assembly.AssemblerFilter.FilterParamWrapper<List<ValidationResultInfo>> response,
			org.kuali.student.common.assembly.AssemblerFilter.ValidateFilterChain<TargetType> chain)
			throws AssemblyException {
		chain.doValidateFilter(request, response);
	}



}
