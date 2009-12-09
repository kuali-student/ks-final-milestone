package org.kuali.student.common.assembly.search;

import java.util.List;
import java.util.Map;

import org.kuali.student.common.assembly.Assembler;
import org.kuali.student.common.assembly.client.AssemblyException;
import org.kuali.student.common.assembly.client.Metadata;
import org.kuali.student.common.assembly.client.SaveResult;
import org.kuali.student.core.search.newdto.SearchRequest;
import org.kuali.student.core.search.newdto.SearchResult;
import org.kuali.student.core.search.service.SearchService;
import org.kuali.student.core.validation.dto.ValidationResultInfo;

public class BaseSearchAssembler implements Assembler<String,String> {
	private Map<String, SearchService> searchKeyToServiceMap;
	@Override
	public SearchResult search(SearchRequest searchRequest) {
		String searchKey = searchRequest.getSearchKey();
		
		SearchService searchService = searchKeyToServiceMap.get(searchKey);
		
		return searchService.search(searchRequest);
	}
	
	@Override
	public String assemble(String input) throws AssemblyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String disassemble(String input) throws AssemblyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String get(String id) throws AssemblyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Metadata getMetadata(String type, String state)
			throws AssemblyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SaveResult<String> save(String input) throws AssemblyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ValidationResultInfo> validate(String input)
			throws AssemblyException {
		// TODO Auto-generated method stub
		return null;
	}

}
