package org.kuali.student.common.assembly;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.cxf.aegis.type.xml.SourceType;
import org.kuali.student.common.assembly.AssemblerFilter.AssembleFilterChain;
import org.kuali.student.common.assembly.AssemblerFilter.DisassembleFilterChain;
import org.kuali.student.common.assembly.AssemblerFilter.GetFilterChain;
import org.kuali.student.common.assembly.AssemblerFilter.GetMetadataFilterChain;
import org.kuali.student.common.assembly.AssemblerFilter.SaveFilterChain;
import org.kuali.student.common.assembly.AssemblerFilter.SearchFilterChain;
import org.kuali.student.common.assembly.AssemblerFilter.ValidateFilterChain;
import org.kuali.student.common.assembly.AssemblerFilter.FilterParamWrapper;
import org.kuali.student.common.assembly.AssemblerFilter.TypeStateFilterParamWrapper;
import org.kuali.student.common.assembly.client.AssemblyException;
import org.kuali.student.common.assembly.client.Metadata;
import org.kuali.student.common.assembly.client.SaveResult;
import org.kuali.student.core.search.newdto.SearchRequest;
import org.kuali.student.core.search.newdto.SearchResult;
import org.kuali.student.core.validation.dto.ValidationResultInfo;


public class AssemblerFilterManager<TargetType, SourceType> implements Assembler<TargetType, SourceType> {

	private List<AssemblerFilter<TargetType, SourceType>> filters = new ArrayList<AssemblerFilter<TargetType, SourceType>>();
	private Assembler<TargetType, SourceType> target;
	
	public void addFilter(AssemblerFilter<TargetType, SourceType> filter){
		filters.add(filter);
	}
	
	public AssemblerFilterManager(Assembler<TargetType, SourceType> target) {
		super();
		this.target = target;
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
			
		}.doDisassembleFilter(request, response);
		
		return response.getValue();
	}

	@Override
	public TargetType get(String id) throws AssemblyException {
		final Iterator<AssemblerFilter<TargetType, SourceType>> iterator = filters.iterator();
		
		FilterParamWrapper<String> request  = new FilterParamWrapper<String>();
		request.setValue(id);
		
		FilterParamWrapper<TargetType> response  = new FilterParamWrapper<TargetType>();
		
		new GetFilterChain<TargetType>(){
			@Override
			public void doGetFilter(FilterParamWrapper<String> request,
					FilterParamWrapper<TargetType> response) throws AssemblyException {
				if(iterator.hasNext()){
					iterator.next().doGetFilter(request, response, this);
				}else{
					response.setValue(target.get(request.getValue()));
				}
			}
			
		}.doGetFilter(request, response);
		
		return response.getValue();
	}

	@Override
	public Metadata getMetadata(String type, String state)
			throws AssemblyException {
		final Iterator<AssemblerFilter<TargetType, SourceType>> iterator = filters.iterator();
		
		TypeStateFilterParamWrapper request  = new TypeStateFilterParamWrapper();
		request.setType(type);
		request.setState(state);
		
		FilterParamWrapper<Metadata> response  = new FilterParamWrapper<Metadata>();
		
		new GetMetadataFilterChain(){
			@Override
			public void doGetMetadataFilter(TypeStateFilterParamWrapper request,
					FilterParamWrapper<Metadata> response) throws AssemblyException {
				if(iterator.hasNext()){
					iterator.next().doGetMetadataFilter(request, response, this);
				}else{
					response.setValue(target.getMetadata(request.getType(),request.getState()));
				}
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
			
		}.doSaveFilter(request, response);
		
		return response.getValue();
	}

	@Override
	public SearchResult search(SearchRequest searchRequest) {
		final Iterator<AssemblerFilter<TargetType, SourceType>> iterator = filters.iterator();
		
		FilterParamWrapper<SearchRequest> request  = new FilterParamWrapper<SearchRequest>();
		request.setValue(searchRequest);
		
		FilterParamWrapper<SearchResult> response  = new FilterParamWrapper<SearchResult>();
		
		new SearchFilterChain(){
			@Override
			public void doSearchFilter(FilterParamWrapper<SearchRequest> request,
					FilterParamWrapper<SearchResult> response) {
				if(iterator.hasNext()){
					iterator.next().doSearchFilter(request, response, this);
				}else{
					response.setValue(target.search(request.getValue()));
				}
			}
			
		}.doSearchFilter(request, response);
		
		return response.getValue();
	}

	@Override
	public List<ValidationResultInfo> validate(TargetType input)
			throws AssemblyException {
		final Iterator<AssemblerFilter<TargetType, SourceType>> iterator = filters.iterator();
		
		FilterParamWrapper<TargetType> request  = new FilterParamWrapper<TargetType>();
		request.setValue(input);
		
		FilterParamWrapper<List<ValidationResultInfo>> response  = new FilterParamWrapper<List<ValidationResultInfo>>();
		
		new ValidateFilterChain<TargetType>(){
			@Override
			public void doValidateFilter(FilterParamWrapper<TargetType> request,
					FilterParamWrapper<List<ValidationResultInfo>> response) throws AssemblyException {
				if(iterator.hasNext()){
					iterator.next().doValidateFilter(request, response, this);
				}else{
					response.setValue(target.validate(request.getValue()));
				}
			}
			
		}.doValidateFilter(request, response);
		
		return response.getValue();
	}

	public void setFilters(List<AssemblerFilter<TargetType, SourceType>> filters) {
		this.filters = filters;
	}
}
