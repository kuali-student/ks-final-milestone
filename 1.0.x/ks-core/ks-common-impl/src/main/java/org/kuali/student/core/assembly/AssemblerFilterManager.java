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

package org.kuali.student.core.assembly;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.kuali.student.core.assembly.AssemblerFilter.AssembleFilterChain;
import org.kuali.student.core.assembly.AssemblerFilter.DisassembleFilterChain;
import org.kuali.student.core.assembly.AssemblerFilter.FilterParamWrapper;
import org.kuali.student.core.assembly.AssemblerFilter.GetFilterChain;
import org.kuali.student.core.assembly.AssemblerFilter.GetMetadataFilterChain;
import org.kuali.student.core.assembly.AssemblerFilter.SaveFilterChain;
import org.kuali.student.core.assembly.AssemblerFilter.TypeStateFilterParamWrapper;
import org.kuali.student.core.assembly.AssemblerFilter.ValidateFilterChain;
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.SaveResult;
import org.kuali.student.core.validation.dto.ValidationResultInfo;


public class AssemblerFilterManager<TargetType, SourceType> implements Assembler<TargetType, SourceType> {

	private List<AssemblerFilter<TargetType, SourceType>> filters = new ArrayList<AssemblerFilter<TargetType, SourceType>>();
	private Assembler<TargetType, SourceType> target;
	private AssemblerFilterManager<TargetType, SourceType> self;
	
	public void addFilter(AssemblerFilter<TargetType, SourceType> filter){
		filters.add(filter);
	}
	
	public AssemblerFilterManager(Assembler<TargetType, SourceType> target) {
		super();
		this.target = target;
		self = this;
	}

	@Override
	public TargetType assemble(SourceType input) throws AssemblyException {
		final Iterator<AssemblerFilter<TargetType, SourceType>> iterator = filters.iterator();
		
		FilterParamWrapper<SourceType> request  = new FilterParamWrapper<SourceType>();
		request.setValue(input);
		
		FilterParamWrapper<TargetType> response  = new FilterParamWrapper<TargetType>();
		
		new AssembleFilterChain<TargetType, SourceType>(){
			@Override
			public void doAssembleFilter(FilterParamWrapper<SourceType> request,
					FilterParamWrapper<TargetType> response) throws AssemblyException {
				if(iterator.hasNext()){
					iterator.next().doAssembleFilter(request, response, this);
				}else{
					response.setValue(target.assemble(request.getValue()));
				}
			}

			@Override
			public AssemblerFilterManager<TargetType, SourceType> getManager() {
				return self;
			}
			
		}.doAssembleFilter(request, response);
		
		return response.getValue();
	}

	@Override
	public SourceType disassemble(TargetType input) throws AssemblyException {
		final Iterator<AssemblerFilter<TargetType, SourceType>> iterator = filters.iterator();
		
		FilterParamWrapper<TargetType> request  = new FilterParamWrapper<TargetType>();
		request.setValue(input);
		
		FilterParamWrapper<SourceType> response  = new FilterParamWrapper<SourceType>();
		
		new DisassembleFilterChain<TargetType, SourceType>(){
			@Override
			public void doDisassembleFilter(FilterParamWrapper<TargetType> request,
					FilterParamWrapper<SourceType> response) throws AssemblyException {
				if(iterator.hasNext()){
					iterator.next().doDisassembleFilter(request, response, this);
				}else{
					response.setValue(target.disassemble(request.getValue()));
				}
			}
			@Override
			public AssemblerFilterManager<TargetType, SourceType> getManager() {
				return self;
			}
		}.doDisassembleFilter(request, response);
		
		return response.getValue();
	}

	@Override
	public TargetType get(String id) throws AssemblyException {
		final Iterator<AssemblerFilter<TargetType, SourceType>> iterator = filters.iterator();
		
		FilterParamWrapper<String> request  = new FilterParamWrapper<String>();
		request.setValue(id);
		
		FilterParamWrapper<TargetType> response  = new FilterParamWrapper<TargetType>();
		
		new GetFilterChain<TargetType, SourceType>(){
			@Override
			public void doGetFilter(FilterParamWrapper<String> request,
					FilterParamWrapper<TargetType> response) throws AssemblyException {
				if(iterator.hasNext()){
					iterator.next().doGetFilter(request, response, this);
				}else{
					response.setValue(target.get(request.getValue()));
				}
			}
			@Override
			public AssemblerFilterManager<TargetType, SourceType> getManager() {
				return self;
			}
		}.doGetFilter(request, response);
		
		return response.getValue();
	}

	@Override
	public Metadata getMetadata(final String idType, final String id, String type, String state)
			throws AssemblyException {
		final Iterator<AssemblerFilter<TargetType, SourceType>> iterator = filters.iterator();
		
		TypeStateFilterParamWrapper request  = new TypeStateFilterParamWrapper();
		request.setType(type);
		request.setState(state);
		
		FilterParamWrapper<Metadata> response  = new FilterParamWrapper<Metadata>();
		
		new GetMetadataFilterChain<TargetType, SourceType>(){
			@Override
			public void doGetMetadataFilter(TypeStateFilterParamWrapper request,
					FilterParamWrapper<Metadata> response) throws AssemblyException {
				if(iterator.hasNext()){
					iterator.next().doGetMetadataFilter(request, response, this);
				}else{
					response.setValue(target.getMetadata(idType, id, request.getType(),request.getState()));
				}
			}
			@Override
			public AssemblerFilterManager<TargetType, SourceType> getManager() {
				return self;
			}
		}.doGetMetadataFilter(request, response);
		
		return response.getValue();
	}

	@Override
	public SaveResult<TargetType> save(TargetType input)
			throws AssemblyException {
		
		final Iterator<AssemblerFilter<TargetType, SourceType>> iterator = filters.iterator();
		
		FilterParamWrapper<TargetType> request  = new FilterParamWrapper<TargetType>();
		request.setValue(input);
		
		FilterParamWrapper<SaveResult<TargetType>> response  = new FilterParamWrapper<SaveResult<TargetType>>();
		
		new SaveFilterChain<TargetType, SourceType>(){
			@Override
			public void doSaveFilter(FilterParamWrapper<TargetType> request,
					FilterParamWrapper<SaveResult<TargetType>> response) throws AssemblyException {
				if(iterator.hasNext()){
					iterator.next().doSaveFilter(request, response, this);
				}else{
					response.setValue(target.save(request.getValue()));
				}
			}
			@Override
			public AssemblerFilterManager<TargetType, SourceType> getManager() {
				return self;
			}
		}.doSaveFilter(request, response);
		
		return response.getValue();
	}

	@Override
	public List<ValidationResultInfo> validate(TargetType input)
			throws AssemblyException {
		final Iterator<AssemblerFilter<TargetType, SourceType>> iterator = filters.iterator();
		
		FilterParamWrapper<TargetType> request  = new FilterParamWrapper<TargetType>();
		request.setValue(input);
		
		FilterParamWrapper<List<ValidationResultInfo>> response  = new FilterParamWrapper<List<ValidationResultInfo>>();
		
		new ValidateFilterChain<TargetType, SourceType>(){
			@Override
			public void doValidateFilter(FilterParamWrapper<TargetType> request,
					FilterParamWrapper<List<ValidationResultInfo>> response) throws AssemblyException {
				if(iterator.hasNext()){
					iterator.next().doValidateFilter(request, response, this);
				}else{
					response.setValue(target.validate(request.getValue()));
				}
			}
			@Override
			public AssemblerFilterManager<TargetType, SourceType> getManager() {
				return self;
			}
		}.doValidateFilter(request, response);
		
		return response.getValue();
	}

	public void setFilters(List<AssemblerFilter<TargetType, SourceType>> filters) {
		this.filters = filters;
	}

	public Assembler<TargetType, SourceType> getTarget() {
		return target;
	}

	public void setTarget(Assembler<TargetType, SourceType> target) {
		this.target = target;
	}

	@Override
	public Metadata getDefaultMetadata() throws AssemblyException {
		return target.getDefaultMetadata();
	}
	

}
