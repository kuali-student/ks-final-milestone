package org.kuali.student.common.assembly;

import java.util.List;

import org.kuali.student.common.assembly.client.AssemblyException;
import org.kuali.student.common.assembly.client.Metadata;
import org.kuali.student.common.assembly.client.SaveResult;
import org.kuali.student.core.search.newdto.SearchRequest;
import org.kuali.student.core.search.newdto.SearchResult;
import org.kuali.student.core.validation.dto.ValidationResultInfo;

public interface AssemblerFilter<TargetType, SourceType> {

	public void doGetMetadataFilter(TypeStateFilterParamWrapper request, FilterParamWrapper<Metadata> response, GetMetadataFilterChain chain) throws AssemblyException;

	public void doSaveFilter(FilterParamWrapper<TargetType> request, FilterParamWrapper<SaveResult<TargetType>> response, SaveFilterChain<TargetType, SourceType> chain) throws AssemblyException;
	
	public void doAssembleFilter(FilterParamWrapper<SourceType> request, FilterParamWrapper<TargetType> response, AssembleFilterChain<TargetType, SourceType> chain) throws AssemblyException;
	
	public void doDisassembleFilter(FilterParamWrapper<TargetType> request, FilterParamWrapper<SourceType> response, DisassembleFilterChain<TargetType, SourceType> chain) throws AssemblyException;
	
	public void doGetFilter(FilterParamWrapper<String> id, FilterParamWrapper<TargetType> response, GetFilterChain<TargetType> chain) throws AssemblyException;
	
	public void doValidateFilter(FilterParamWrapper<TargetType> request, FilterParamWrapper<List<ValidationResultInfo>> response,  ValidateFilterChain<TargetType> chain) throws AssemblyException;
	
	public void doSearchFilter(FilterParamWrapper<SearchRequest> request, FilterParamWrapper<SearchResult> response, SearchFilterChain chain);
	
	public interface GetMetadataFilterChain{
		public void doGetMetadataFilter(TypeStateFilterParamWrapper request, FilterParamWrapper<Metadata> response) throws AssemblyException;
	}
	public interface SaveFilterChain<TargetType, SourceType>{
		public void doSaveFilter(FilterParamWrapper<TargetType> request, FilterParamWrapper<SaveResult<TargetType>> response) throws AssemblyException;
	}
	public interface AssembleFilterChain<TargetType, SourceType>{
		public void doAssembleFilter(FilterParamWrapper<SourceType> request, FilterParamWrapper<TargetType> response) throws AssemblyException;
	}
	public interface DisassembleFilterChain<TargetType, SourceType>{
		public void doDisassembleFilter(FilterParamWrapper<TargetType> request, FilterParamWrapper<SourceType> response) throws AssemblyException;
	}
	public interface GetFilterChain<TargetType>{
		public void doGetFilter(FilterParamWrapper<String> id, FilterParamWrapper<TargetType> response) throws AssemblyException;
	}
	public interface ValidateFilterChain<TargetType>{
		public void doValidateFilter(FilterParamWrapper<TargetType> request, FilterParamWrapper<List<ValidationResultInfo>> response) throws AssemblyException;
	}
	public interface SearchFilterChain{
		public void doSearchFilter(FilterParamWrapper<SearchRequest> request, FilterParamWrapper<SearchResult> response);
	}
	
	public class FilterParamWrapper<T>{
		T value;
		public T getValue(){
			return value;
		}
		public void setValue(T newValue){
			value = newValue;
		}
	}
	public class TypeStateFilterParamWrapper{
		String type;
		String state;
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		
	}

	
}
