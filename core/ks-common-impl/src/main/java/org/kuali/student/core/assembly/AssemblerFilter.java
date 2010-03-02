package org.kuali.student.core.assembly;

import java.util.List;

import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.SaveResult;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;
import org.kuali.student.core.validation.dto.ValidationResultInfo;

public interface AssemblerFilter<TargetType, SourceType> {

	public void doGetMetadataFilter(TypeStateFilterParamWrapper request, FilterParamWrapper<Metadata> response, GetMetadataFilterChain<TargetType, SourceType> chain) throws AssemblyException;

	public void doSaveFilter(FilterParamWrapper<TargetType> request, FilterParamWrapper<SaveResult<TargetType>> response, SaveFilterChain<TargetType, SourceType> chain) throws AssemblyException;
	
	public void doAssembleFilter(FilterParamWrapper<SourceType> request, FilterParamWrapper<TargetType> response, AssembleFilterChain<TargetType, SourceType> chain) throws AssemblyException;
	
	public void doDisassembleFilter(FilterParamWrapper<TargetType> request, FilterParamWrapper<SourceType> response, DisassembleFilterChain<TargetType, SourceType> chain) throws AssemblyException;
	
	public void doGetFilter(FilterParamWrapper<String> id, FilterParamWrapper<TargetType> response, GetFilterChain<TargetType, SourceType> chain) throws AssemblyException;
	
	public void doValidateFilter(FilterParamWrapper<TargetType> request, FilterParamWrapper<List<ValidationResultInfo>> response,  ValidateFilterChain<TargetType, SourceType> chain) throws AssemblyException;
	
	public void doSearchFilter(FilterParamWrapper<SearchRequest> request, FilterParamWrapper<SearchResult> response, SearchFilterChain<TargetType, SourceType> chain);
	
	public interface AssemblerManagerAccessable <TargetType, SourceType>{
		public AssemblerFilterManager<TargetType, SourceType> getManager();
	}
	
	public interface GetMetadataFilterChain<TargetType, SourceType> extends AssemblerManagerAccessable<TargetType, SourceType>{
		public void doGetMetadataFilter(TypeStateFilterParamWrapper request, FilterParamWrapper<Metadata> response) throws AssemblyException;
	}
	public interface SaveFilterChain<TargetType, SourceType> extends AssemblerManagerAccessable<TargetType, SourceType>{
		public void doSaveFilter(FilterParamWrapper<TargetType> request, FilterParamWrapper<SaveResult<TargetType>> response) throws AssemblyException;
	}
	public interface AssembleFilterChain<TargetType, SourceType> extends AssemblerManagerAccessable<TargetType, SourceType>{
		public void doAssembleFilter(FilterParamWrapper<SourceType> request, FilterParamWrapper<TargetType> response) throws AssemblyException;
	}
	public interface DisassembleFilterChain<TargetType, SourceType> extends AssemblerManagerAccessable<TargetType, SourceType>{
		public void doDisassembleFilter(FilterParamWrapper<TargetType> request, FilterParamWrapper<SourceType> response) throws AssemblyException;
	}
	public interface GetFilterChain<TargetType, SourceType> extends AssemblerManagerAccessable<TargetType, SourceType>{
		public void doGetFilter(FilterParamWrapper<String> id, FilterParamWrapper<TargetType> response) throws AssemblyException;
	}
	public interface ValidateFilterChain<TargetType, SourceType> extends AssemblerManagerAccessable<TargetType, SourceType>{
		public void doValidateFilter(FilterParamWrapper<TargetType> request, FilterParamWrapper<List<ValidationResultInfo>> response) throws AssemblyException;
	}
	public interface SearchFilterChain<TargetType, SourceType> extends AssemblerManagerAccessable<TargetType, SourceType>{
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
