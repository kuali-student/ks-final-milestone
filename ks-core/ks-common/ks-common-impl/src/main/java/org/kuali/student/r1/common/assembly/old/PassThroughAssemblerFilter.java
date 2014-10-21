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

package org.kuali.student.r1.common.assembly.old;

import java.util.List;

import org.kuali.student.r1.common.assembly.data.AssemblyException;
import org.kuali.student.r1.common.assembly.data.Metadata;
import org.kuali.student.r1.common.assembly.old.data.SaveResult;
import org.kuali.student.r2.common.dto.ValidationResultInfo;

@Deprecated
public class PassThroughAssemblerFilter <TargetType, SourceType> implements AssemblerFilter<TargetType, SourceType> {

	@Override
	public void doAssembleFilter(
			org.kuali.student.r1.common.assembly.old.AssemblerFilter.FilterParamWrapper<SourceType> request,
			org.kuali.student.r1.common.assembly.old.AssemblerFilter.FilterParamWrapper<TargetType> response,
			org.kuali.student.r1.common.assembly.old.AssemblerFilter.AssembleFilterChain<TargetType, SourceType> chain) throws AssemblyException {
		chain.doAssembleFilter(request, response);
	}

	@Override
	public void doDisassembleFilter(
			org.kuali.student.r1.common.assembly.old.AssemblerFilter.FilterParamWrapper<TargetType> request,
			org.kuali.student.r1.common.assembly.old.AssemblerFilter.FilterParamWrapper<SourceType> response,
			org.kuali.student.r1.common.assembly.old.AssemblerFilter.DisassembleFilterChain<TargetType, SourceType> chain) throws AssemblyException {
		chain.doDisassembleFilter(request, response);
	}

	@Override
	public void doGetFilter(
			org.kuali.student.r1.common.assembly.old.AssemblerFilter.FilterParamWrapper<String> id,
			org.kuali.student.r1.common.assembly.old.AssemblerFilter.FilterParamWrapper<TargetType> response,
			org.kuali.student.r1.common.assembly.old.AssemblerFilter.GetFilterChain<TargetType, SourceType> chain) throws AssemblyException {
		chain.doGetFilter(id, response);
	}

	@Override
	public void doGetMetadataFilter(
			org.kuali.student.r1.common.assembly.old.AssemblerFilter.TypeStateFilterParamWrapper request,
			org.kuali.student.r1.common.assembly.old.AssemblerFilter.FilterParamWrapper<Metadata> response,
			org.kuali.student.r1.common.assembly.old.AssemblerFilter.GetMetadataFilterChain<TargetType, SourceType> chain) throws AssemblyException {
		chain.doGetMetadataFilter(request, response);
	}

	@Override
	public void doSaveFilter(
			org.kuali.student.r1.common.assembly.old.AssemblerFilter.FilterParamWrapper<TargetType> request,
			org.kuali.student.r1.common.assembly.old.AssemblerFilter.FilterParamWrapper<SaveResult<TargetType>> response,
			org.kuali.student.r1.common.assembly.old.AssemblerFilter.SaveFilterChain<TargetType, SourceType> chain)
			throws AssemblyException {
		chain.doSaveFilter(request, response);
	}

	@Override
	public void doValidateFilter(
			org.kuali.student.r1.common.assembly.old.AssemblerFilter.FilterParamWrapper<TargetType> request,
			org.kuali.student.r1.common.assembly.old.AssemblerFilter.FilterParamWrapper<List<ValidationResultInfo>> response,
			org.kuali.student.r1.common.assembly.old.AssemblerFilter.ValidateFilterChain<TargetType, SourceType> chain)
			throws AssemblyException {
		chain.doValidateFilter(request, response);
	}



}
