package org.kuali.student.core.assembly.search;

import java.util.List;
import java.util.Map;

import org.kuali.student.core.assembly.Assembler;
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.SaveResult;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;
import org.kuali.student.core.search.service.SearchService;
import org.kuali.student.core.validation.dto.ValidationResultInfo;

public class BaseSearchAssembler implements Assembler<String,String> {
	private Map<String, SearchService> searchKeyToServiceMap;
	@Override
	public SearchResult search(SearchRequest searchRequest) {
		String searchKey = searchRequest.getSearchKey();
		
		SearchService searchService = searchKeyToServiceMap.get(searchKey);
		
		try {
			return searchService.search(searchRequest);
		} catch (MissingParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
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
	public Metadata getMetadata(String id, String type, String state)
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
