package org.kuali.student.core.assembly;

import java.util.List;

import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.SaveResult;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;
import org.kuali.student.core.validation.dto.ValidationResultInfo;

public class PassThroughAssemblerFilter <TargetType, SourceType> implements AssemblerFilter<TargetType, SourceType> {

	@Override
	public void doAssembleFilter(
			org.kuali.student.core.assembly.AssemblerFilter.FilterParamWrapper<SourceType> request,
			org.kuali.student.core.assembly.AssemblerFilter.FilterParamWrapper<TargetType> response,
			org.kuali.student.core.assembly.AssemblerFilter.AssembleFilterChain<TargetType, SourceType> chain) throws AssemblyException {
		chain.doAssembleFilter(request, response);
	}

	@Override
	public void doDisassembleFilter(
			org.kuali.student.core.assembly.AssemblerFilter.FilterParamWrapper<TargetType> request,
			org.kuali.student.core.assembly.AssemblerFilter.FilterParamWrapper<SourceType> response,
			org.kuali.student.core.assembly.AssemblerFilter.DisassembleFilterChain<TargetType, SourceType> chain) throws AssemblyException {
		chain.doDisassembleFilter(request, response);
	}

	@Override
	public void doGetFilter(
			org.kuali.student.core.assembly.AssemblerFilter.FilterParamWrapper<String> id,
			org.kuali.student.core.assembly.AssemblerFilter.FilterParamWrapper<TargetType> response,
			org.kuali.student.core.assembly.AssemblerFilter.GetFilterChain<TargetType, SourceType> chain) throws AssemblyException {
		chain.doGetFilter(id, response);
	}

	@Override
	public void doGetMetadataFilter(
			org.kuali.student.core.assembly.AssemblerFilter.TypeStateFilterParamWrapper request,
			org.kuali.student.core.assembly.AssemblerFilter.FilterParamWrapper<Metadata> response,
			org.kuali.student.core.assembly.AssemblerFilter.GetMetadataFilterChain<TargetType, SourceType> chain) throws AssemblyException {
		chain.doGetMetadataFilter(request, response);
	}

	@Override
	public void doSaveFilter(
			org.kuali.student.core.assembly.AssemblerFilter.FilterParamWrapper<TargetType> request,
			org.kuali.student.core.assembly.AssemblerFilter.FilterParamWrapper<SaveResult<TargetType>> response,
			org.kuali.student.core.assembly.AssemblerFilter.SaveFilterChain<TargetType, SourceType> chain)
			throws AssemblyException {
		chain.doSaveFilter(request, response);
	}

	@Override
	public void doSearchFilter(
			org.kuali.student.core.assembly.AssemblerFilter.FilterParamWrapper<SearchRequest> request,
			org.kuali.student.core.assembly.AssemblerFilter.FilterParamWrapper<SearchResult> response,
			org.kuali.student.core.assembly.AssemblerFilter.SearchFilterChain<TargetType, SourceType> chain) {
		chain.doSearchFilter(request, response);
	}

	@Override
	public void doValidateFilter(
			org.kuali.student.core.assembly.AssemblerFilter.FilterParamWrapper<TargetType> request,
			org.kuali.student.core.assembly.AssemblerFilter.FilterParamWrapper<List<ValidationResultInfo>> response,
			org.kuali.student.core.assembly.AssemblerFilter.ValidateFilterChain<TargetType, SourceType> chain)
			throws AssemblyException {
		chain.doValidateFilter(request, response);
	}



}
